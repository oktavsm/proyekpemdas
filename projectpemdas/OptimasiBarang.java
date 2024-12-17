import java.util.Scanner;

public class OptimasiBarang {
    // Variabel global untuk menyimpan input kapasitas, kebutuhan produk, dan keuntungan
    static int maxTime;
    static int maxMaterial;

    static int aTime;
    static int aMaterial;
    static int aAdvantage;

    static int bTime;
    static int bMaterial;
    static int bAdvantage;

    // Array untuk menyimpan kombinasi produk dan keuntungan, serta hasil optimal
    static int[][] combinations = new int[1000][3];
    static int[] optimalAdvantage = new int[3];

    // Fungsi untuk mendapatkan input dari pengguna
    static void getData() {
        Scanner in = new Scanner(System.in);

        System.out.println("=== Program Optimasi Produksi Bengkel ===");

        // Input data produksi (kapasitas, kebutuhan, dan keuntungan)
        for (int i = 0; i < 1; i++) {
            System.out.print("Masukkan kapasitas waktu kerja mesin (jam): ");
            maxTime = in.nextInt();

            System.out.print("Masukkan kapasitas bahan baku (unit): ");
            maxMaterial = in.nextInt();

            System.out.print("Masukkan waktu kerja untuk Produk A (jam): ");
            aTime = in.nextInt();

            System.out.print("Masukkan bahan baku untuk Produk A (unit): ");
            aMaterial = in.nextInt();

            System.out.print("Masukkan keuntungan per unit Produk A: ");
            aAdvantage = in.nextInt();

            System.out.print("Masukkan waktu kerja untuk Produk B (jam): ");
            bTime = in.nextInt();

            System.out.print("Masukkan bahan baku untuk Produk B (unit): ");
            bMaterial = in.nextInt();

            System.out.print("Masukkan keuntungan per unit Produk B: ");
            bAdvantage = in.nextInt();

            // Validasi input untuk memastikan semua nilai positif
            if(maxTime < 0 || maxMaterial < 0 || aTime < 0 || aMaterial < 0 || aAdvantage < 0 || bTime < 0 || bMaterial < 0 || bAdvantage < 0){
                System.out.println("\n=== Masukan tidak valid, silakan input kembali ===\n");
                i = -1;
            }
        }

        in.close();
    }

    // Fungsi untuk menghitung semua kombinasi produksi yang memungkinkan
    static void getCombination() {
        int length = 0;

        int maxCombinationA = Math.min(maxTime / aTime, maxMaterial / aMaterial);
        int maxCombinationB = Math.min(maxTime / bTime, maxMaterial / bMaterial);

        // Loop untuk mencari kombinasi produk A dan B berdasarkan batas waktu dan bahan
        for (int a = 0; a <= maxCombinationA; a++) {
            for (int b = 0; b <= maxCombinationB; b++) {
                int totalTime = a * aTime + b * bTime;
                int totalMaterial = a * aMaterial + b * bMaterial;
                int totalAdvantage = a * aAdvantage + b * bAdvantage;

                // Menyimpan kombinasi jika memenuhi batasan waktu dan bahan
                if (totalTime <= maxTime && totalMaterial <= maxMaterial) {
                    combinations[length][0] = a;
                    combinations[length][1] = b;
                    combinations[length][2] = totalAdvantage;
                    length++;
                }
            }
        }

        // Mencari kombinasi yang menghasilkan keuntungan maksimal
        findOptimalAdvantage(length);

        // Menampilkan semua kombinasi
        printCombinations(length);
    }

    // Fungsi untuk mencari keuntungan optimal dari kombinasi yang tersedia
    static void findOptimalAdvantage(int length) {
        for (int i = 0; i < length; i++) {
            if (combinations[i][2] > optimalAdvantage[2]) {
                optimalAdvantage[0] = combinations[i][0];
                optimalAdvantage[1] = combinations[i][1];
                optimalAdvantage[2] = combinations[i][2];
            }
        }
    }

    // Fungsi untuk mencetak semua kombinasi yang memungkinkan
    static void printCombinations(int length) {
        System.out.println("=== Proses Pencarian Kombinasi ===");

        for (int i = 0; i < length; i++) {
            int a = combinations[i][0];
            int b = combinations[i][1];
            int advantage = combinations[i][2];

            System.out.printf("Kombinasi: Produk A = %d, Produk B = %d -> Keuntungan = Rp%d\n", a, b, advantage);

            if (advantage == optimalAdvantage[2]) {
                System.out.println("(Optimal)");
            }
        }
    }

    // Fungsi untuk mencetak hasil optimasi produksi
    static void printOptimalAdvantage() {
        System.out.println("\n=== Hasil Optimasi ===");
        System.out.println("Produk Optimal:");
        System.out.println("Produk A = " + optimalAdvantage[0] + " unit");
        System.out.println("Produk B = " + optimalAdvantage[1] + " unit");
        System.out.println("Keuntungan maksimal: Rp" + optimalAdvantage[2]);
    }

    // Fungsi utama untuk menjalankan program
    public static void main(String[] args) {
        getData();
        getCombination();
        printOptimalAdvantage();
    }
}
