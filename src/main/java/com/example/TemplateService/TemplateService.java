package com.example.TemplateService;

import org.springframework.stereotype.Service;
import lombok.NonNull;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.StorageOptions;
import org.apache.tika.mime.MimeTypeException;
import org.apache.tika.mime.MimeTypes;
import org.apache.tika.mime.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;


@Service
public class TemplateService {
    private final Bucket bucket = StorageOptions
            .newBuilder()
            .setHost("http://localhost:4443")
            .build()
            .getService()
            .get("sample-bucket");
    private final List<Blob> filteredList = new ArrayList<>();
    private final MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
    private final SortedSet<MediaType> TypeNames = allTypes.getMediaTypeRegistry().getTypes();
    private final Set<String> extList = TypeNames
            .stream()
            .map(n -> {
                try {
                    return allTypes.forName(n.toString()).getExtension();
                } catch (MimeTypeException e) {
                    throw new RuntimeException(e);
                }
            })
            .filter(n -> !n.isEmpty())
            .collect(Collectors.toSet());

    private void filterByFileName(String fileName) {
        filteredList.clear();
        Iterable<Blob> blobIterable = bucket.list().iterateAll();
        for (Blob blob : blobIterable) {
            if (blob.getName().contentEquals(fileName)) {
                filteredList.add(blob);
                return;
            }
            if (blob.getName().contains(fileName + ".")) {
                filteredList.add(blob);
            }
        }
    }

    private void filterByLocale(String locale) {
        if (locale.isEmpty()) {
            for (Blob Item : filteredList) {
                if (Item.getName().contains(".en")) {
                    filteredList.set(0, Item);
                    return;
                }
            }
        }

        filteredList.removeIf(blob -> !(blob.getName().contains("." + locale)));
    }

    private Blob filterByExtension(String ext, String fileName) {
        if (ext.isEmpty() && fileName.contains(".")) {
            return filteredList.stream().findFirst().orElseThrow(
                    () -> new TemplateNotFoundException("Template not found")
            );
        }
        if (ext.isEmpty() && !fileName.contains(".")){
            for (String mappedExt : extList) {
                for (Blob Item : filteredList) {
                    if (Item.getName().endsWith(mappedExt)) {
                        return Item;
                    }
                }
            }
            throw new TemplateNotFoundException("Template not found");
        }
        for (Blob Item : filteredList) {
            if (Item.getName().endsWith(ext)) {
                return Item;
            }
        }
        throw new TemplateNotFoundException("Template not found");
    }

    public Blob getQueriedFile(@NonNull TemplateEntity template) {

        if (template.getFileName().contains(".")) {
            if (!template.getFileExtension().isEmpty()) {
                template.setFileExtension("");
            }
            if (!template.getLocale().isEmpty()) {
                template.setLocale("");
            }
        }

        filterByFileName(template.getFileName());
        filterByLocale(template.getLocale());

        return filterByExtension(template.getFileExtension(), template.getFileName());
    }
}
