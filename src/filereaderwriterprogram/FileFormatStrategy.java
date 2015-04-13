
package filereaderwriterprogram;

import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author kritterbusch
 */
public interface FileFormatStrategy {
    /**
     * taking String data, place into object
     * @param data
     * @return  
     */
    public abstract List<LinkedHashMap<String,String>> decode(String data, boolean hasHeader);
    
    /**
     * taking object data, placing into string
     * @param data
     * @return 
     */
    public abstract String encode(List<LinkedHashMap<String,String>> data);
    
    
}
