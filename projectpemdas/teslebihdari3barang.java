
import java.util.*;

public class teslebihdari3barang {
    static int totalWaktu = 0;
    static int totalBahan = 0;
    static int totalUntung = 0;
    static int kapasitasWaktu;
    static int kapasitasBahan;
    static int kebutuhan[][];
    static int maxTime;
    static int maxMaterial;
    static int jumlahProduk;
    static int kombinasi[][];
    static int jumlahKombinasi = 0;
    static int tempKombinasi[];
    static int keuntungan[] = new int[100000];
    static int optimal = 0;

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        System.out.println("=== Program Optimasi Produksi Bengkel ===");

        System.out.print("Masukkan kapasitas waktu kerja mesin (jam): ");
        maxTime = in.nextInt();
        if (maxTime<0){
            System.out.println("Input tidak bisa negatif!");
            return;
        }
        
        System.out.print("Masukkan kapasitas bahan baku (unit): ");
        maxMaterial = in.nextInt();
        if (maxMaterial<0){
            System.out.println("Input tidak bisa negatif!");
            return;
        }
        System.out.print("Masukkan Jumlah Produk (minimal 2): ");
        jumlahProduk = in.nextInt();
        if (jumlahProduk<0){
            System.out.println("Input tidak bisa negatif!");
            return;
        }
        // produk[A][0] = waktu kerja produk A
        // produk[A][1] = bahan baku produk A
        kebutuhan = new int[jumlahProduk][3];
        //proses input
        for (int i = 0, charProduk = 65; i < jumlahProduk; i++, charProduk++) {
            System.out.printf("Masukkan waktu kerja untuk Produk %c (jam): ", charProduk);
            kebutuhan[i][0] = in.nextInt();
            if (kebutuhan[i][0]<0){
                System.out.println("Input tidak bisa negatif!");
                return;
            }
            
            System.out.printf("Masukkan bahan baku untuk Produk %c (unit): ", charProduk);
            kebutuhan[i][1] = in.nextInt();
            if (kebutuhan[i][1]<0){
                System.out.println("Input tidak bisa negatif!");
                return;
            }
            System.out.printf("Masukkan keuntungan per unit Produk %c (unit): ", charProduk);
            kebutuhan[i][2] = in.nextInt();
            if (kebutuhan[i][2]<0){
                System.out.println("Input tidak bisa negatif!");
                return;
            }
        }

        kombinasi = new int[1000000][jumlahProduk + 2];
        tempKombinasi = new int[jumlahProduk + 2];
        iterasiProduk(0);
        printCombinations();

    }

    static void iterasiProduk(int indeksProduk) {
        if (indeksProduk == jumlahProduk) { // Basis rekursi: semua produk telah diiterasi
            if (totalWaktu <= maxTime && totalBahan <= maxMaterial) { // Validasi batasan
                for (int j = 0; j < jumlahProduk + 2; j++) {
                    kombinasi[jumlahKombinasi][j] = tempKombinasi[j];
                }
                keuntungan[jumlahKombinasi] = totalUntung;
                if (jumlahKombinasi != 0) {
                    if (keuntungan[optimal] < keuntungan[jumlahKombinasi]) {
                        optimal = jumlahKombinasi;
                    }
                }
                jumlahKombinasi++;
            }
            return;
        }

        // Rekursi: Iterasi jumlah untuk produk ke-indeksProduk
        for (int i = 0; i <= maxTime / kebutuhan[indeksProduk][0]; i++) {
            int tambahanWaktu = kebutuhan[indeksProduk][0] * i;
            int tambahanBahan = kebutuhan[indeksProduk][1] * i;
            int tambahanKeuntungan = kebutuhan[indeksProduk][2] * i;

            // Validasi apakah tambahan ini tidak melebihi batas
            if (totalWaktu + tambahanWaktu <= maxTime && totalBahan + tambahanBahan <= maxMaterial) {
                totalWaktu += tambahanWaktu;
                totalBahan += tambahanBahan;
                totalUntung += tambahanKeuntungan;
                tempKombinasi[indeksProduk] = i;
                tempKombinasi[jumlahProduk] = totalWaktu;
                tempKombinasi[jumlahProduk + 1] = totalBahan;

                // Rekursi ke produk berikutnya
                iterasiProduk(indeksProduk + 1);

                // Backtrack
                totalWaktu -= tambahanWaktu;
                totalBahan -= tambahanBahan;
                totalUntung -= tambahanKeuntungan;
                tempKombinasi[indeksProduk] = 0;
                tempKombinasi[jumlahProduk] = 0;
                tempKombinasi[jumlahProduk + 1] = 0;
            } else {
                break; // Hentikan iterasi jika sumber daya tidak mencukupi
            }
        }
    }

    static String center(String banner) {
        String x = "";
        int padding = 13 - banner.length();
        int rightPadding = (padding % 2 != 0) ? padding / 2 + 1 : padding / 2;
        int leftPadding = padding - rightPadding;
        for (int i = 0; i < rightPadding; i++) {
            x += " ";
        }
        x += banner;
        for (int i = 0; i < leftPadding; i++) {
            x += " ";
        }

        return x;
    }

    static void printCombinations() {
        System.out.println("=== Proses Pencarian Kombinasi ===");

        String line = "";
        for (int i = 0; i < jumlahProduk + 3; i++) {
            line += "-";
            line += "-------------";
        }
        line += "-";
        String banner = "";
        for (int i = 0, charProduk = 65; i < jumlahProduk; i++) {
            banner += "|";
            banner += center(String.format("Produk %c", charProduk + i));
        }
        banner += "| Waktu Mesin |  Bahan Baku |  Keuntungan |";
        System.out.println(line);
        System.out.println(banner);
        System.out.println(line);

        for (int i = 0; i < jumlahKombinasi; i++) {
            String data = "";
            for (int j = 0; j < jumlahProduk + 2; j++) {
                String nilai = "" + kombinasi[i][j];
                data += "|" + center(nilai);
            }
            data += "|" + center(String.format("Rp%d", keuntungan[i])) + "|";

            if (i == optimal) {
                data += "**(Optimal)**";
            }
            System.out.println(data);
            System.out.println(line);
        }
    System.out.println("=== Hasil Optimasi ===");
    System.out.println("Produk Optimal: ");
    for(int i = 0,charProduct=65;i<jumlahProduk;i++){
        System.out.printf("Produk %c: %d\n",charProduct+i, kombinasi[optimal][i]);
    }
    System.out.printf("Keuntungan Maksimal: Rp%d\n",keuntungan[optimal]);
    }
}
