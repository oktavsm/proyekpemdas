import java.util.Scanner;

public class OptimasiBarang {
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

        System.out.print("Masukkan waktu kerja untuk Produk A: ");
        aTime = in.nextInt();

        System.out.print("Masukkan bahan baku untuk Produk A: ");
        aMaterial = in.nextInt();

        System.out.print("Masukkan keuntungan per unit Produk A: ");
        aAdvantage = in.nextInt();

        System.out.print("Masukkan waktu kerja untuk Produk B: ");
        bTime = in.nextInt();

        System.out.print("Masukkan bahan baku untuk Produk B: ");
        bMaterial = in.nextInt();

        System.out.print("Masukkan keuntungan per unit Produk B: ");
        bAdvantage = in.nextInt();
    }

    static void getCombination() {
        int probA = Math.min(maxTime / aTime, maxMaterial / aMaterial);
        int probB = Math.min(maxTime / bTime, maxMaterial / bMaterial);

        int length = 0;

        for (int a = 0; a <= probA; a++) {
            for (int b = 0; b <= probB; b++) {
                int totalTime = a * aTime + b * bTime;
                int totalMaterial = a * aMaterial + b * bMaterial;

                if (totalTime <= maxTime && totalMaterial <= maxMaterial) {
                    combinations[length][0] = a;
                    combinations[length][1] = b;
                    combinations[length][2] = a * aAdvantage + b * bAdvantage;
                    length++;
                }
            }
        }

        findOptimalAdvantage(combinations, length);
        printCombinations(combinations, length);
    }

    static void printCombinations(int[][] tempCombinations, int length) {
        System.out.println("=== Proses Pencarian Kombinasi ===");

        for (int i = 0; i < length; i++) {
            System.out.printf(
                "Kombinasi: Produk A = %d, Produk B = %d -> Waktu Mesin = %d, Bahan Baku = %d, Keuntungan = Rp%d\n", tempCombinations[i][0], tempCombinations[i][1], tempCombinations[i][0] * aTime + tempCombinations[i][1] * bTime, tempCombinations[i][0] * aMaterial + tempCombinations[i][1] * bMaterial, tempCombinations[i][2]
            );

            if (tempCombinations[i][2] == optimalAdvantage[2]) {
                System.out.println("**(Optimal)**");
            }
        }
    }

    static void findOptimalAdvantage(int[][] combinations, int length) {
        optimalAdvantage[2] = 0;
        for (int i = 0; i < length; i++) {
            if (combinations[i][2] > optimalAdvantage[2]) {
                optimalAdvantage[0] = combinations[i][0];
                optimalAdvantage[1] = combinations[i][1];
                optimalAdvantage[2] = combinations[i][2];
            }
        }
    }

    static void printOptimalAdvantage() {
        System.out.println("=== Hasil Optimasi ===");
        System.out.println("Produk Optimal:");
        System.out.println("Produk A = " + optimalAdvantage[0] + " unit");
        System.out.println("Produk B = " + optimalAdvantage[1] + " unit");
        System.out.println("Keuntungan Maksimal: Rp" + optimalAdvantage[2]);
    }

    public static void main(String[] args) {
        getData();
        getCombination();
    }
}