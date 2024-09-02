package seriesparser.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileLoader {
    public static String readFile(final String path, final Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public static String load(final String file) {
        String listContent;
        try {
            listContent = FileLoader.readFile(file, Charset.defaultCharset());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        return listContent;
    }
}
