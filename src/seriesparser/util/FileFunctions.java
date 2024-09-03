package seriesparser.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileFunctions {
    public static String readFile(final String path, final Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static String load(final String file) {
        String listContent;
        try {
            listContent = FileFunctions.readFile(file, Charset.defaultCharset());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        return listContent;
    }

    public static void betterMkdir(final File directory) {
        if (!directory.exists()) {
            boolean created;
            try {
                created = directory.mkdir();
            } catch (final SecurityException e) {
                created = false;
            }
            if (!created) {
                System.err.println("Insufficient permissions: Could not create directory " + directory.getPath());
            }
        }
    }
}
