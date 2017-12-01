/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configuracion;

/**
 *
 * @author jorge
 */
public class ws_config {
    
    private static String pathToServer;
    
    public ws_config() {
        //this.setPath("http://animalitos.local/apis/");
    }
    
    public void setPath(String pathToServer) {
        ws_config.pathToServer = pathToServer;
    }
    
    public String getPath(){
        return ws_config.pathToServer;
    }
}
