package com.github.luiswolff.tests;

import java.util.regex.Pattern;

import org.apache.commons.codec.language.ColognePhonetic;
import org.apache.commons.codec.language.Metaphone;
import org.apache.commons.codec.language.Soundex;

public enum PhoneticEncoder {

    SOUNDEX {

        @Override
        String encode(String value) {
            try {
                return Soundex.US_ENGLISH.soundex(value);
            } catch (IllegalArgumentException e) {
                return "unsupported";
            }
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