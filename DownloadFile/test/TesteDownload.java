
import static controller.Download.downloadArquivo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Carlos Henrique
 */
public class TesteDownload {

    public static void main(String[] args) {
        /**
         * link para o download do bpa ftp://arpoador.datasus.gov.br/siasus/BPA/
         * link para download do kit com as tabelas atualizadas
         * ftp://arpoador.datasus.gov.br/siasus/sia/
         */
//        String site = "ftp://arpoador.datasus.gov.br/siasus/BPA/";
//        String site = "ftp://arpoador.datasus.gov.br/siasus/sia/";
    String site = "http://iconeweb.com.br/dw/putty.exe";
//        String site = "http://iconeweb.com.br/dw/outros/putty_reg_98.zip";
//        String site = "http://iconeweb.com.br/dw/SumatraPDF-2.1.1-install.exe";
        String pathLocal = "/home/henrique/";
        downloadArquivo(site);
        System.out.println("\nConcluído");
//
    }

}
