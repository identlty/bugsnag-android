package com.bugsnag.android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URLConnection;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
class IOUtils {
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
    private static final int EOF = -1;

    public static void closeQuietly(@Nullable final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (@NonNull final Exception ioe) {
            // ignore
        }
    }

    public static void close(@Nullable final URLConnection conn) {
        if (conn instanceof HttpURLConnection) {
            ((HttpURLConnection) conn).disconnect();
        }
    }

    public static int copy(@NonNull final Reader input,
                           @NonNull final Writer output) throws IOException {
        char[] buffer = new char[DEFAULT_BUFFER_SIZE];
        long count = 0;
        int read;
        while (EOF != (read = input.read(buffer))) {
            output.write(buffer, 0, read);
            count += read;
        }

        if (count > Integer.MAX_VALUE) {
            return -1;
        }

        return (int) count;
    }
}
