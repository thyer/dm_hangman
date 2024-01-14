package dm_hangman.state;

import java.util.Map;
import java.util.Set;

public class DoctrinalMasteryDictionary{
    private final Map<String, String> internalDictionary;
    public DoctrinalMasteryDictionary(Map<String, String> doctrinalMasteries){
        this.internalDictionary = doctrinalMasteries;
    }

    public Set<String> getReferences(){
        return internalDictionary.keySet();
    }

    public String getDM(String reference){
        return internalDictionary.get(reference);
    }
}
