package userinterface;

import javax.swing.*;
import java.io.File;
import java.nio.file.*;

public class FileSelector extends JComboBox<String> {
    private final Path directory;

    public FileSelector(String directoryPath) {
        this.directory = Paths.get(directoryPath);
        refreshFileList(); // Initial population
        watchDirectory();  // Monitor changes
    }

    private void refreshFileList() {
        removeAllItems(); // Clear existing items
        File dir = directory.toFile();
        if (dir.exists() && dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    addItem(file.getName());
                }
            }
        }
    }
    
    // Refresh the list if there is a change in the directory
    private void watchDirectory() {
        new Thread(() -> {
            try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
                directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);

                while (true) {
                    WatchKey key = watchService.take(); // Wait for an event
                    for (WatchEvent<?> event : key.pollEvents()) {
                        refreshFileList(); // Update dropdown
                    }
                    key.reset();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
