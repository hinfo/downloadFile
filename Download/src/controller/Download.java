/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import sun.net.ftp.FtpClient;
import view.TelaPrincipal;

/**
 *
 * @author henrique
 */
public class Download {

    /**
     * @param args the command line arguments
     */
        public static Boolean terminou = false;
        public static int count;
    public static void downloadArquivo(String stringUrl, String pathLocal) {
        try {

            String nomeArquivoLocal = getNomeArquivo(stringUrl);
            System.out.println("Baixando o arquivo " + nomeArquivoLocal);
            URL url = new URL(stringUrl + nomeArquivoLocal);
            InputStream is = url.openStream();
            FileOutputStream fos = new FileOutputStream(pathLocal + nomeArquivoLocal);
            byte[] buffer = new byte[4096];
            int bytes = -1;
            count = 0;
         
//                  TelaPrincipal.progress.setMaximum(count);
            int kont = 0;      
            while ((bytes = is.read(buffer)) != -1){
                fos.write(buffer, 0, bytes);
                terminou = false;

            }
            //Nao se esqueca de sempre fechar as streams apos seu uso!
            is.close();
            
            fos.close();
            count = 0;
            terminou = true;
            System.out.println("Arquivo baixado");
            
        } catch (Exception e) {
            System.out.println("Erro ao realizar o download \n" + e);

        }
        
    }

    public static String getNomeArquivo(String urlSite) throws MalformedURLException, IOException {
        String nomeArquivo = null;
        int tamArq = 0;
        URL url = new URL(urlSite);
        URLConnection conn = url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

        String linha = null;
        //lista os arquivos 
        String[] aux = null;
        while ((linha = br.readLine()) != null) {
//            System.out.println(linha);
            if (linha.contains("BDSIA") && linha.contains("exe")) {
                aux = linha.split(" ");
//                                System.out.print("data: " + aux[0]);
//                                System.out.print(" | hora: " + aux[2]);
//                                System.out.println(" | Tamanho do arquivo " + aux[16]);
//                                System.out.println(" ");
                //Geralmente o último arquivo é o mais atualizado
                nomeArquivo = aux[17];
                tamArq = Integer.parseInt(aux[16]);

            } else if (linha.contains("BPA") && linha.contains("exe")) {
                //Geralmente fica apenas o mais atualizado no diretório
                aux = linha.split(" ");
                nomeArquivo = aux[17];
                tamArq = Integer.parseInt(aux[16]);
            }
        }
                System.out.println("Tamanho do arquivo " + tamArq*0.9);
        return nomeArquivo;
    }
}
