/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filereaderwriterprogram;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * @author kritterbusch
 */
public class XmlFileFormatter implements FileFormatStrategy {
   private final static String DELIMETER_NEWLINE = "\\n";
    private final static String NEWLINE = "\n";
    private final static String COMMA = ",";
    private final static String QUOTE = "\"";
    private final static String EMPTY = "";
    private final static String DIAMOND_OPERATOR = ">";
    

    /**
     * Reads a string of XML file, decodes into a list of HashMaps.
     *
     * @param data
     * @param hasHeader
     * @return
     */
    @Override
    public List<LinkedHashMap<String, String>> decode(String data, boolean hasHeader) {
        List<LinkedHashMap<String, String>> records
                = new ArrayList<LinkedHashMap<String, String>>();

        String[] lines = data.split(DELIMETER_NEWLINE);
        int i = 0;
        String[] header = null;
        if (hasHeader) {
            i = 1;
            header = lines[0].split(DIAMOND_OPERATOR);
        }

        // if file contains no header, it generates a header
        for (; i < lines.length; i++) {
            LinkedHashMap<String, String> record = new LinkedHashMap<String, String>();
            String[] rowData = lines[i].split(DIAMOND_OPERATOR);
            for (int j = 0; j < rowData.length; j++) {
                if (hasHeader && header != null) {
                    record.put(header[j], rowData[j]);
                } else {
                    record.put("DATA_" + j, rowData[j]);
                }
                
            }
            records.add(record);
        }
        return records;
    }

    /**
     * Turns list of HashMaps of decoded data into a string (XML formatted)
     *
     * @param data
     * @param hasHeader
     * @return
     */
    @Override
    public String encode(List<LinkedHashMap<String, String>> data) {
        StringBuilder encodedData = new StringBuilder();

        LinkedHashMap<String, String> headerLine = data.get(0);
        Set<String> fields = headerLine.keySet();

        Iterator headerIterator = fields.iterator();
        while (headerIterator.hasNext()) {
            encodedData.append(QUOTE).append(headerIterator.next().toString().replace(QUOTE, EMPTY)).append(QUOTE).append(COMMA);
        }

        int lastCharPosition = encodedData.length() - 1;
        encodedData.replace(lastCharPosition, lastCharPosition + 1, NEWLINE);

        

        for (LinkedHashMap<String, String> dataRow : data) {
            Iterator lineIterator = fields.iterator();
            while (lineIterator.hasNext()) {
                encodedData.append(QUOTE).append(dataRow.get(lineIterator.next()).replace(QUOTE, EMPTY))
                        .append(QUOTE).append(COMMA);
            }
            lastCharPosition = encodedData.length() - 1;
            encodedData.replace(lastCharPosition, lastCharPosition + 1, NEWLINE);
        }

        lastCharPosition = encodedData.length() - 1;
        encodedData.replace(lastCharPosition, lastCharPosition + 1, NEWLINE);

        return encodedData.toString();
    }
}
