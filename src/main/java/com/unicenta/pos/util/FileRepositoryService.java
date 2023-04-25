package com.unicenta.pos.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.repo.DefaultRepositoryService;
import net.sf.jasperreports.repo.InputStreamResource;
import net.sf.jasperreports.repo.Resource;

public class FileRepositoryService extends DefaultRepositoryService {
    private List<File> folders;
    private boolean resolveAbsolutePath;

    public FileRepositoryService(JasperReportsContext jasperReportsContext, List<File> folders, boolean resolveAbsolutePath) {
        super(jasperReportsContext);
        this.folders = folders;
        this.resolveAbsolutePath = resolveAbsolutePath;
    }

    @Override
    public <K extends Resource> K getResource(String uri, Class<K> resourceType) {
        if (InputStreamResource.class.isAssignableFrom(resourceType)) {
            File file = null;
            for (File folder : folders) {
                File potentialFile = new File(folder, uri);
                if (potentialFile.exists() && potentialFile.isFile()) {
                    file = potentialFile;
                    break;
                }
            }

            if (file == null && resolveAbsolutePath) {
                file = new File(uri);
                if (!file.exists() || !file.isFile()) {
                    file = null;
                }
            }

            if (file != null) {
                try {
                    InputStream inputStream = new FileInputStream(file);
                    ExtendedInputStreamResource inputStreamResource = new ExtendedInputStreamResource();
                    inputStreamResource.setInputStream(inputStream);
                    inputStreamResource.setMimeType(getFileExtension(file.getName()));
                    return resourceType.cast(inputStreamResource);
                } catch (FileNotFoundException e) {
                    // Log the exception or handle it as needed
                }
            }
        }

        return super.getResource(uri, resourceType);
    }

    private String getFileExtension(String fileName) {
        int lastIndexOfDot = fileName.lastIndexOf('.');
        if (lastIndexOfDot > 0 && lastIndexOfDot < fileName.length() - 1) {
            return fileName.substring(lastIndexOfDot + 1);
        }
        return "";
    }

    public static class ExtendedInputStreamResource extends InputStreamResource {
        private String mimeType;

        public String getMimeType() {
            return mimeType;
        }

        public void setMimeType(String mimeType) {
            this.mimeType = mimeType;
        }
    }
}