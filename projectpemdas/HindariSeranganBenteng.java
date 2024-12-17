public class dafi {
    
}
aimport java.util.Scanner;

public class HindariSeranganBenteng {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        
        char[][] papan = new char[8][8];
        
        for (int i = 0; i < 8; i++){
            String baris = in.next();
            for (int j = 0; j < 8; j++){
                papan[i][j] = baris.charAt(j);
            }
        }
        
        String[] aman = hitungPetakAman(papan);
        
        System.out.println("Oleh karena itu, anda dapat menempatkan bidak anda tanpa ditangkap pada "+ aman.length + " petak: petak ");
        for (int i = 0; i < aman.length; i++){
            if (i > 0){
                if (i == aman.length - 1){
                    System.out.print(" dan ");
                } else {
                    System.out.print(", ");
                }
            }
            System.out.print("petak " + aman[i]);
        }
        System.out.println(".");
    }
    
    static String[] hitungPetakAman(char[][] papan){
        boolean[] barisTerancam = new boolean[8];
        boolean[] kolomTerancam = new boolean[8];
        
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if(papan[i][j] == '#'){
                    barisTerancam[i] = true;
                    kolomTerancam[j] = true;
                }
            }
        }
        
        int bagianAman = 0;
        for (int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if (papan[i][j] == '.' && !barisTerancam[i] && !kolomTerancam[j]){
                    bagianAman++;
                }
            }
        }
        String[] hasil = new String[bagianAman];
        int index = 0;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (papan[i][j] == '.' && !barisTerancam[i] && !kolomTerancam[j]){
                    hasil[index++] = "(" + (i + 1) + "," + (j + 1) + ")";
                }
            }
        }
        return hasil;
    }
}