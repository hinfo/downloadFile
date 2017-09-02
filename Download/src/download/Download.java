/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package download;

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

/**
 *
 * @author henrique
 */
public class Download {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String site = "ftp://arpoador.datasus.gov.br/siasus/sia/";
//        String site = "http://www.iconeweb.com.br/download/suporte_remoto_iconeweb.exe";
        String pathLocal = "/home/henrique/";
        System.out.println("Baixando o arquivo....");
        gravaArquivoDeURL(site, pathLocal);
        System.out.println("Concluído");

    }

    public static void gravaArquivoDeURL(String stringUrl, String pathLocal) {

        try {

            String nomeArquivoLocal = getNomeArquivo(stringUrl);
            URL url = new URL(stringUrl + nomeArquivoLocal);
            InputStream is = url.openStream();
            FileOutputStream fos = new FileOutputStream(pathLocal + nomeArquivoLocal);
            byte[] buffer = new byte[4096];
            int bytes = -1;
            while ((bytes = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytes);

            }
            //Nao se esqueca de sempre fechar as streams apos seu uso!
            is.close();
            fos.close();
        } catch (Exception e) {
            System.out.println("Erro ao realizar o download \n" + e);

        }
    }

    public static String getNomeArquivo(String urlSite) throws MalformedURLException, IOException {
        String nomeArquivo;
        URL url = new URL(urlSite);
        URLConnection conn = url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

        String linha = null;
        //lista os arquivos 
        String[] aux = null;
        while ((linha = br.readLine()) != null) {
            if (linha.contains("BDSIA") && linha.contains("exe")) {
                aux = linha.split(" ");
//                                System.out.print("data: " + aux[0]);
//                                System.out.print(" | hora: " + aux[2]);
//                                System.out.print(" | arquivo " + aux[17]);
//                                System.out.println(" ");

            }
        }
        nomeArquivo = aux[17];
        return nomeArquivo;
    }
}
