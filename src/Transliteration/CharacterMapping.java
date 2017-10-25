package Transliteration;

import java.util.HashMap;

public interface CharacterMapping {

    // Get mapping for all available tokens
    public HashMap<String, String[]> getMapping();

    // get alternate mappings for tokens within the current language
    public HashMap<String, String[]> getAlternateMapping();
}
