package com.example.corpu.utils;

import com.example.corpu.utils.jsonparser.TrimStringDeserializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.net.URL;
import java.text.Normalizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {
    public static boolean isMaxLength(String str, Integer maxLength) {
        return str.length() > maxLength;
    }

    public static boolean isValidRegex(String str, String pattern) {
        return Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(str).matches();
    }


    public static boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static <E> E trimObject(E obj, Class<E> eClass) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        module.addDeserializer(String.class, new TrimStringDeserializer());
        objectMapper.registerModule(module);
        return objectMapper.readValue(objectMapper.writeValueAsString(obj), eClass);
    }


    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replace('đ','d').replace('Đ','D');

    }

    public static boolean isValidIPAddress(String ip)
    {

        // Regex for digit from 0 to 255.
        String zeroTo255
                = "(\\d{1,2}|(0|1)\\"
                + "d{2}|2[0-4]\\d|25[0-5])";

        // Regex for a digit from 0 to 255 and
        // followed by a dot, repeat 4 times.
        // this is the regex to validate an IP address.
        String regex
                = zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255;

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the IP address is empty
        // return false
        if (ip == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given IP address
        // and regular expression.
        Matcher m = p.matcher(ip);

        // Return if the IP address
        // matched the ReGex
        return m.matches();
    }
}
