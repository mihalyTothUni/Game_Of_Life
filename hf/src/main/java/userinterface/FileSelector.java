package userinterface;

import javax.swing.*;
import java.io.File;
import java.nio.file.*;

/**
 * A dropdown menu that lists files in a directory and updates when the directory changes
 */
public class FileSelector extends JComboBox<String> {
    private final Path directory; // Directory to list files from

    /**
     * Constructor
     * @param directoryPath The path to the directory to list files from
     */
    public FileSelector(String directoryPath) {
        this.directory = Paths.get(directoryPath);
        refreshFileList(); // Initial population
        watchDirectory();  // Monitor changes
    }

    /**
     * Refreshes the list of files in the directory
     */
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

    /**
     * Monitors the directory for changes and refreshes the list of files when a change is detected
     */
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
