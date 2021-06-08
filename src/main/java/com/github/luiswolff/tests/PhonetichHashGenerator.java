package com.github.luiswolff.tests;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class PhonetichHashGenerator {

    private final PhoneticEncoder encoder;

    public PhonetichHashGenerator(PhoneticEncoder encoder) {
        this.encoder = encoder;
    }

    public void writeHashes(InputStream source, Consumer<String> sink) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(source))) {
            String line;
            while ((line = in.readLine()) != null) {
                if (line.isEmpty()) break;
                sink.accept(encoder.encode(line));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String encoding = "SOUNDEX";
        String source = null;

        for (String arg : args) {
            if (arg.toUpperCase().startsWith("ENCODING=")) {
                encoding = arg.split("=", 2)[1];
            } else if (arg.toUpperCase().startsWith("SOURCE=")) {
                source = arg.split("=", 2)[1];
            }
        }
        new PhonetichHashGenerator(PhoneticEncoder.valueOf(encoding)).writeHashes(source != null ? new FileInputStream(source) : System.in, System.out::println); // NOSONAR
    }
}
