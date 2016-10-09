package logic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileManager {

    private final String imagesFolder = "images";
    private final String imageExt = ".jpg";


    public String setPath(final String imageName) {
        String directory = getMainDirectory() + File.separator;
        return directory + imagesFolder + File.separator + imageName + imageExt;
    }

    public void copyImage(final File source, final String imageName) throws IOException {
        File target = new File(setPath(imageName));
        createImageDirectory();
        Files.copy(source.toPath(), target.toPath());
    }

    public void renameImageFile(final String oldName, final String newName) throws IOException {
        File source = new File(setPath(oldName));
        Files.move(source.toPath(), source.toPath().resolveSibling(newName + imageExt));
    }

    public String getImageName(final File imagePath) {
        String path = imagePath.toString();
        return path.substring(path.lastIndexOf(File.separator) + 1, path.indexOf("."));
    }

    public void deleteImage(final String imageName) {
        File imageToDelete = new File(setPath(imageName));
        imageToDelete.delete();
    }

    private void createImageDirectory() {
        String directory = getMainDirectory() + File.separator + imagesFolder;
        File file = new File(directory);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    private String getMainDirectory() {
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        return new File(path).getParent();
    }
}