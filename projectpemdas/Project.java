import java.util.Scanner;

public class Project {
    static int maxTime;
    static int maxMaterial;

    static int aTime;
    static int aMaterial;
    static int aAdvantage;

    static int bTime;
    static int bMaterial;
    static int bAdvantage;

    static int[][] combinations = new int[100][3];
    static int[] optimalAdvantage = new int[3];

    static void getData() {
        Scanner in = new Scanner(System.in);

        System.out.println("=== Program Optimasi Produksi Bengkel ===");

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

        in.close();
    }

    static void getCombination() {
        int length = 0;

        for (int a = 0; a <= maxTime / aTime; a++) {
            for (int b = 0; b <= maxTime / bTime; b++) {
                int totalTime = a * aTime + b * bTime;
                int totalMaterial = a * aMaterial + b * bMaterial;
                int totalAdvantage = a * aAdvantage + b * bAdvantage;

                if (totalTime <= maxTime && totalMaterial <= maxMaterial) {
                    combinations[length][0] = a;
                    combinations[length][1] = b;
                    combinations[length][2] = totalAdvantage;
                    length++;
                }
            }
        }

        findOptimalAdvantage(length);
        printCombinations(length);
    }

    static void findOptimalAdvantage(int length) {
        for (int i = 0; i < length; i++) {
            if (combinations[i][2] > optimalAdvantage[2]) {
                optimalAdvantage[0] = combinations[i][0];
                optimalAdvantage[1] = combinations[i][1];
                optimalAdvantage[2] = combinations[i][2];
            }
        }
    }

    static void printCombinations(int length) {
        System.out.println("=== Proses Pencarian Kombinasi ===");

        for (int i = 0; i < length; i++) {
            int a = combinations[i][0];
            int b = combinations[i][1];
            int advantage = combinations[i][2];

            System.out.printf("Kombinasi: Produk A = %d, Produk B = %d -> Keuntungan = Rp%d\n", a, b, advantage);

            if (advantage == optimalAdvantage[2]) {
                System.out.println("**(Optimal)**");
            }
        }
    }

    static void printOptimalAdvantage() {
        System.out.println("\n=== Hasil Optimasi ===");
        System.out.printf("Produk Optimal:\nProduk A = %d unit\nProduk B = %d unit\nKeuntungan Maksimal: Rp%d\n", 
                optimalAdvantage[0], optimalAdvantage[1], optimalAdvantage[2]);
    }

    public static void main(String[] args) {
        getData();
        getCombination();
        printOptimalAdvantage();
    }
}
