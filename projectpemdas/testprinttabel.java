
import java.util.*;
public class testprinttabel {
    public static void main(String[] args) {
    int jumlahProduk =4;
    int charProduk = 65; 
    String line = "";
    for (int i = 0;i<jumlahProduk+3;i++){
        line+="-";
        line+="-------------";
    }       
    line+="-";
    String banner ="";
    for (int i = 0;i<jumlahProduk;i++){
        banner+="|";
        banner+=center(String.format("Produk %c",charProduk+i));
    }
    banner += "| Waktu Mesin |  Bahan Baku |  Keuntungan |";
    System.out.println(line);
    System.out.println(banner);
    System.out.println(line);
    }

    static String center(String banner){
        String x = "";
        int padding = 13-banner.length();
        int rightPadding = (padding%2!=0)?padding/2+1:padding/2;
        int leftPadding = padding-rightPadding;
        for(int i=0;i<rightPadding;i++){
            x+=" ";
        }
        x+=banner;
        for(int i=0;i<leftPadding;i++){
            x+=" ";
        }

        return x;
    }
}
