# test phonetic search (Java)

This project generates indexes by a phonetic code of the lines of an input file.
By default the soundex hashes of the lines in the file `data/lastnames.txt` are generated.

## Usage

Run the following classes with you preferred IDE:

* `com.github.luiswolff.tests.PhoneticIndexer.java`

You can also submit one of the following optional command line arguments:

 * `source=<file_path>`: Defines the source file to use
 * `encoding=<[SOUNDEX*, COLOGNE_PHONETIC, METAPHONE]>`: Defines the hash code generator to use

## Dependencies

The projects uses the following Maven dependencies:

 * **commons-codec:commons-codec:1.15**