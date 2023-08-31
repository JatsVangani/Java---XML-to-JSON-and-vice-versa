package org.xmljsonconverter;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.*;

public class Main {
    public static String inputDir = System.getProperty("user.dir")+"\\Input";
    public static String outputDir = System.getProperty("user.dir")+"\\Output";

    public static void main(String[] args) {

        jsonToXml();
        xmlToJson();

    }

    private static void xmlToJson(){

        String xmlString=readXml();
        String jsonString=objectCreator(xmlString);
        saveAsJson(jsonString);
    }


    private static void jsonToXml(){
        String jsonString=readJson();
        String xmlString=objectToXml(jsonString);
        saveAsXml(xmlString);
    }
   private static String readXml(){

       String xmlPath=inputDir+"\\sample.xml";

       try (BufferedReader br = new BufferedReader(new FileReader(xmlPath))) {
           StringBuilder xmlContent = new StringBuilder();
           String line;
           while ((line = br.readLine()) != null) {
               xmlContent.append(line).append(System.lineSeparator());
           }
           String xmlString = xmlContent.toString();
           return xmlString;
       } catch (IOException e) {
           e.printStackTrace();
       }
       return null;
    }

    private static String readJson() {
        String jsonPath = outputDir + "\\output.json";

        try (BufferedReader br = new BufferedReader(new FileReader(jsonPath))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                jsonContent.append(line).append(System.lineSeparator());
            }
            return jsonContent.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String objectToXml(String jsonContent) {
        ObjectMapper objectMapper = new ObjectMapper();
        XmlMapper xmlMapper = new XmlMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(jsonContent);
            String xmlString = xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
            return xmlString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    private static String objectCreator(String xmlString){

        XmlMapper xmlMapper=new XmlMapper();
        ObjectMapper objectMapper=new ObjectMapper();

        try {
            JsonNode xmlNode = xmlMapper.readTree(xmlString);

            String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(xmlNode);

            return jsonString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void saveAsJson(String jsonString){
        String outputPath = outputDir+"\\output.json";

        try (FileWriter fileWriter = new FileWriter(outputPath)) {
            fileWriter.write(jsonString);
            System.out.println("JSON data written to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void saveAsXml(String xmlContent) {
        String outputPath = outputDir + "\\output.xml";

        try (FileWriter fileWriter = new FileWriter(outputPath)) {
            fileWriter.write(xmlContent);
            System.out.println("XML data written to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}