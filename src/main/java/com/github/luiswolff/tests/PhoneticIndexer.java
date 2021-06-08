package com.github.luiswolff.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.commons.codec.language.ColognePhonetic;
import org.apache.commons.codec.language.Metaphone;
import org.apache.commons.codec.language.Soundex;

public class PhoneticIndexer {

    private final Encoder encoder;

    public PhoneticIndexer(Encoder encoder) {
        this.encoder = encoder;
    }

    public Map<String, List<String>> index(File source) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(source))) {
            return in.lines().collect(Collectors.groupingBy(encoder::encode, TreeMap::new, Collectors.toList()));
        }
    }

    public static void main(String[] args) throws IOException {
        String encoding = "SOUNDEX";
        String source = "data/lastnames.txt";

        for (String arg : args) {
            if (arg.toUpperCase().startsWith("ENCODING=")) {
                encoding = arg.split("=", 2)[1];
            } else if (arg.toUpperCase().startsWith("SOURCE=")) {
                source = arg.split("=", 2)[1];
            }
        }
        new PhoneticIndexer(Encoder.valueOf(encoding)).index(new File(source)).entrySet().forEach(System.out::println); // NOSONAR
    }

    public enum Encoder {

        SOUNDEX {

            @Override
            String encode(String value) {
                return Soundex.US_ENGLISH.soundex(value);
            }

        },
        COLOGNE_PHONETIC {

            @Override
            String encode(String value) {
                return new ColognePhonetic().colognePhonetic(value);
            }
        },
        METAPHONE {

            @Override
            String encode(String value) {
                return new Metaphone().metaphone(value);
            }
        };

        abstract String encode(String value);
    }
}
