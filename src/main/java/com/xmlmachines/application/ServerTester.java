package com.xmlmachines.application;
  
import java.io.IOException;
  
import com.xmlmachines.providers.GrizzlyContainerProvider;
  
public class ServerTester {
  
    public static void main(String[] args) throws IOException {
        GrizzlyContainerProvider.getInstance().startServer();
    }
}