package com.github.luiswolff.tests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class PhoneticIndexer {

    private final PhoneticEncoder encoder;

    public PhoneticIndexer(PhoneticEncoder encoder) {
        this.encoder = encoder;
    }

    public Map<String, List<String>> index(Collection<String> source) {
        return source.stream().filter(s -> !s.isEmpty()).collect(Collectors.groupingBy(encoder::encode, TreeMap::new, Collectors.toList()));
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
        List<String> data = new LinkedList<>();
        if (source != null) {
            try (BufferedReader in = new BufferedReader(new FileReader(source))) {
                in.lines().forEach(data::add);
            }
        } else {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
                while (true) {
                    System.out.print("Type value (leave empty to see result): "); // NOSONAR
                    String line;
                    if ((line = in.readLine()) != null && !line.isEmpty()) {
                        data.add(line);
                    } else {
                        break;
                    }
                }
            }
        }
        new PhoneticIndexer(PhoneticEncoder.valueOf(encoding)).index(Collections.unmodifiableList(data)).entrySet().forEach(System.out::println); // NOSONAR
    }
}
