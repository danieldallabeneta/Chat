package Utils;

/**
 *
 * @author danie
 */
public class ServerUtils {
    
    private ServerUtils() {};
    private static ServerUtils instance;
    private String ip;
    private int porta;
    
    
    public synchronized static ServerUtils getInstance() {
        if (instance == null) {
            instance = new ServerUtils();
        }
        return instance;
    }
    
    public void setIp(String ip){
        this.ip = ip;
    }
    
    public String getIp(){
        return this.ip;
    }
    
    public void setPorta(int porta){
        this.porta = porta;
    }
    
    public int getPorta(){
        return this.porta;
    }
    
}
