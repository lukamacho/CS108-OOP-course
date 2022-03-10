package servlets;

import java.util.HashMap;

public class AccountManager {
    private HashMap<String,String> hashMap;

    public AccountManager(){
        hashMap = new HashMap<>();
        hashMap.put("Patrick","1234");
        hashMap.put("Molly","FloPup");
    }
    public boolean exists(String key){
        return hashMap.containsKey(key);
    }
    public boolean correctUser(String key, String value){
        if(!hashMap.containsKey(key)){
            return false;
        }
        return hashMap.get(key).equals(value);
    }
    public void createNewAccount(String key, String value){
        hashMap.put(key,value);
    }
}

