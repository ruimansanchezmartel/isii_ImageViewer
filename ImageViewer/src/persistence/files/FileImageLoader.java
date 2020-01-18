package persistence.files;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import model.Image;
import persistence.ImageLoader;

public class FileImageLoader implements ImageLoader {

    private final static String[] ImageExtensions = new String[] {"bmp", "gif", "jpeg", "jpg", "png", "raw", "tif", "tiff"};
    private final File[] files;

    public FileImageLoader(String folder) {
        this.files = new File(folder).listFiles(withImageExtension());
    }

    @Override
    public Image load() {
        return imageAt(0);
    }

    public Image imageAt(int index) {
        return new Image() {
            @Override
            public byte[] bitmap() {
                try {
                    FileInputStream fileInputStream = new FileInputStream(files[index]);
                    return read(fileInputStream);
                } catch (IOException ex) {
                    return new byte[0];
                }
            }

            private byte[] read(FileInputStream fileInputStream) throws IOException {
                byte[] buffer = new byte[4096];
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                while (true) {
                    int length = fileInputStream.read(buffer);
                    if (length < 0) {
                        break;
                    }
                    byteArrayOutputStream.write(buffer, 0, length);
                }
                return byteArrayOutputStream.toByteArray();
            }

            @Override
            public Image next() {
                return (index < files.length - 1) ? imageAt(index + 1) : imageAt(0);
            }

            @Override
            public Image previous() {
                return (index > 0) ? imageAt((index - 1)) : imageAt(files.length - 1);
            }
        };
    }

    private FilenameFilter withImageExtension() {
        return new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                for (String extension : ImageExtensions) {
                    if (name.endsWith(extension)) return true;
                }
                return false;
            }
        };
    }
}
