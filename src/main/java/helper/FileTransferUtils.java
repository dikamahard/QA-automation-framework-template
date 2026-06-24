package helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.stream.Stream;
import java.util.zip.ZipFile;

public class FileTransferUtils {

    public static void downloadFile(String downloadDir, Runnable triggerDownload, String desiredFileName) {
        System.out.println("Start downloadFile to : " + downloadDir);
        try {
            // Set the destination directory as path
            Path targetDownloadPath = Paths.get(downloadDir);

            // Ensure the destination directory exists
            Files.createDirectories(targetDownloadPath);

            // Clean folder before download
            try (Stream<Path> files = Files.list(targetDownloadPath)) {
                files.forEach(path -> {
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to delete file: " + path, e);
                    }
                });
            }

            // What triggers the download pass as parameter and run here
            triggerDownload.run();
            System.out.println("Download button is clicked");
            Thread.sleep(5000);

            //rename the file to desired name
            Path downloadedFile = Files.list(targetDownloadPath)
                    .filter(p -> !p.toString().endsWith(".crdownload"))
                    .max(Comparator.comparingLong(p -> p.toFile().lastModified()))
                    .orElseThrow();

            Files.move(
                    downloadedFile,
                    targetDownloadPath.resolve(desiredFileName),
                    StandardCopyOption.REPLACE_EXISTING
            );

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Failed to download file", e);
        }
    }

    public static boolean zipContainsFile(String zipPath, String expectedFileName) {
        try (ZipFile zipFile = new ZipFile(zipPath)) {

            return zipFile.stream()
                    .anyMatch(entry -> entry.getName().endsWith(expectedFileName));

        } catch (IOException e) {
            throw new RuntimeException("Failed to read ZIP file: " + zipPath, e);
        }
    }

    public static void deleteFile(String directory) {
        System.out.println("Start deleteFile on : " + directory);

        Path targetDeletePath = Paths.get(directory);

        try {

            Files.delete(targetDeletePath);
            System.out.println("Deleted the file: " + targetDeletePath.getFileName());
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such file or directory%n", targetDeletePath);
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", targetDeletePath);
        } catch (IOException x) {
            // Catches file permission problems and other I/O errors
            System.err.println("I/O error: " + x);
        }
    }

    public static void copyFile(String sourceDirectory, String targetDirectory) {
        System.out.println("Copy file from : " + sourceDirectory + " to : " + targetDirectory);

        Path srcDir = Paths.get(sourceDirectory);
        Path targetDir = Paths.get(targetDirectory);

        try {
            if (!Files.exists(srcDir)) {
                throw new FileNotFoundException("Source file not found: " + srcDir);
            }

            // make sure directory exist, create if it doesn't
            Files.createDirectories(targetDir.getParent());

            Files.copy(
                    srcDir,
                    targetDir,
                    StandardCopyOption.REPLACE_EXISTING
            );
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
