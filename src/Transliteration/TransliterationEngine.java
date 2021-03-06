package Transliteration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class TransliterationEngine {

    public String transliterate(String data) {
        String[] words = data.toLowerCase().split(" ");
        StringBuilder result = new StringBuilder();
        CharacterMapping englishMapping = new ItransAsciiMapping();
        CharacterMapping hindiMapping = new HindiMapping();
        for (String currentWord : words) {
            result.append(transliterateHelper(currentWord, englishMapping, hindiMapping));
            result.append(" ");
        }

        return result.toString().trim();
    }

    private String transliterateHelper(String data, CharacterMapping englishMapping, CharacterMapping hindiMapping) {
        int maxTokenLength = computeMaxTokenLength(englishMapping);
        int inputLength = data.length();
        boolean followsConsonant = false;
        StringBuilder buffer = new StringBuilder(inputLength);
        String tokenBuffer = "";

        HashMap<String, String> tokenMap = createTokenMap(englishMapping, hindiMapping);
        HashMap<String, String> vowelMap = createVowelMap(englishMapping, hindiMapping);
        HashMap<String, String[]> englishTokenGroups = englishMapping.getMapping();
        HashMap<String, String[]> hindiTokenGroups = hindiMapping.getMapping();
        
        for(int i = 0; i < inputLength || !tokenBuffer.isEmpty(); i++) {

            if (i < inputLength) {
                char currentChar = data.charAt(i);
                int difference = maxTokenLength - tokenBuffer.length();
                if(difference > 0 && i < inputLength) {
                    tokenBuffer += currentChar;
                    if (difference > 1) {
                        continue;
                    }
                }
            }

            for(int j = 0; j < maxTokenLength; j++) {
                int tokenLength = maxTokenLength - j;
                tokenLength = (tokenLength > tokenBuffer.length()) ? tokenBuffer.length() : tokenLength;
                String token = tokenBuffer.substring(0, tokenLength);
                String tempLetter = tokenMap.get(token);
                if (tempLetter != null) {
                    if (followsConsonant) {
                        String vowelMark = vowelMap.get(token);
                        if (vowelMark != null) {
                            buffer.insert(0, vowelMark);
                        } else if (!token.equals("a")) {
                            buffer.insert(0, hindiTokenGroups.get("virama")[0]);
                            buffer.insert(0, tempLetter);
                        } else if (token.equals("a") && tokenBuffer.length() == 1 && i >= inputLength) {
                            buffer.insert(0, vowelMap.get("A"));
                        }

                    } else {
                        buffer.insert(0, tempLetter);
                    }
                    followsConsonant = Arrays.asList(englishTokenGroups.get("consonants")).contains(token);

                    tokenBuffer = tokenBuffer.substring(tokenLength);
                    break;
                } else if (j == maxTokenLength - 1) {
                    if (followsConsonant) {
                        followsConsonant = false;
                    }
                    buffer.insert(0, token);
                    tokenBuffer = tokenBuffer.substring(1);
                }
            }
        }

        return buffer.reverse().toString();
    }

    private int computeMaxTokenLength(CharacterMapping mappingData) {
        int max = -1;
        for (Map.Entry<String, String[]> entry : mappingData.getMapping().entrySet()) {
            for (String token : entry.getValue()) {
                if (token.length() > max){
                    max = token.length();
                }
            }
        }

        return max;
    }

    private HashMap<String, String> createTokenMap(CharacterMapping from, CharacterMapping to) {
        HashMap<String, String> tokenMapping = new HashMap<String, String>();

        HashMap<String, String[]> fromMapping = from.getMapping();
        HashMap<String, String[]> toMapping = to.getMapping();
        HashMap<String, String[]> alternates = from.getAlternateMapping();
        for (Map.Entry<String, String[]> fromEntry : fromMapping.entrySet()) {
            String[] fromTokens = fromEntry.getValue();
            String[] toTokens = toMapping.get(fromEntry.getKey());

            // create mapping for for all letters and alternates
            for (int i = 0; i < fromTokens.length; i++) {
                String key = fromTokens[i];
                String value = toTokens[i];
                tokenMapping.put(key, value);
                String[] alt = alternates.get(key);
                if (alt != null) {
                    for (String currentAlternate : alt) {
                        tokenMapping.put(currentAlternate, value);
                    }
                }
            }
        }
        return tokenMapping;
    }

    // Create alternate mapping for vowel marks when vowels follow consonants
    private HashMap<String, String> createVowelMap(CharacterMapping from, CharacterMapping to) {
        HashMap<String, String> vowelMap = new HashMap<String, String>();

        String[] fromList = from.getMapping().get("vowels");
        String[] toList = to.getMapping().get("vowelMarks");
        HashMap<String, String[]> alternates = from.getAlternateMapping();

        for (int i = 1; i < fromList.length; i++) {
            String key = fromList[i];
            String value = toList[i - 1];
            vowelMap.put(key, value);
            String[] alt = alternates.get(key);
            if (alt != null) {
                for (String currentAlternate : alt) {
                    vowelMap.put(currentAlternate, value);
                }
            }
        }

        return vowelMap;
    }
}
