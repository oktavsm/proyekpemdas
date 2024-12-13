import java.util.*;

public class OptimasiProduksi {
    static int totalWaktuKerja = 0;
    static int totalBahanBaku = 0;
    static int totalKeuntungan = 0;
    static int kapasitasWaktuKerja;
    static int kapasitasBahanBaku;
    static int kebutuhanProduk[][];
    static int batasWaktuKerja;
    static int batasBahanBaku;
    static int jumlahJenisProduk;
    static int daftarKombinasi[][];
    static int jumlahKombinasi = 0;
    static int kombinasiSementara[];
    static int daftarKeuntungan[] = new int[100000];
    static int indeksKombinasiOptimal = 0;

    public static void main(String[] args) {

        Scanner scannerInput = new Scanner(System.in);

        System.out.println("=== Program Optimasi Produksi Bengkel ===");

        // Input kapasitas waktu kerja mesin
        System.out.print("Masukkan kapasitas waktu kerja mesin (jam): ");
        batasWaktuKerja = scannerInput.nextInt();
        if (batasWaktuKerja < 0) {
            System.out.println("Input tidak bisa negatif!");
            return;
        }

        // Input kapasitas bahan baku
        System.out.print("Masukkan kapasitas bahan baku (unit): ");
        batasBahanBaku = scannerInput.nextInt();
        if (batasBahanBaku < 0) {
            System.out.println("Input tidak bisa negatif!");
            return;
        }

        // Input jumlah produk yang akan dioptimasi
        System.out.print("Masukkan jumlah jenis produk (minimal 2): ");
        jumlahJenisProduk = scannerInput.nextInt();
        if (jumlahJenisProduk < 0) {
            System.out.println("Input tidak bisa negatif!");
            return;
        }

        // Array untuk menyimpan kebutuhan waktu, bahan, dan keuntungan tiap produk
        kebutuhanProduk = new int[jumlahJenisProduk][3];

        // Proses input kebutuhan waktu, bahan, dan keuntungan untuk setiap produk
        for (int indeksProduk = 0, kodeProduk = 65; indeksProduk < jumlahJenisProduk; indeksProduk++, kodeProduk++) {
            System.out.printf("Masukkan waktu kerja untuk Produk %c (jam): ", kodeProduk);
            kebutuhanProduk[indeksProduk][0] = scannerInput.nextInt();
            if (kebutuhanProduk[indeksProduk][0] < 0) {
                System.out.println("Input tidak bisa negatif!");
                return;
            }

            System.out.printf("Masukkan bahan baku untuk Produk %c (unit): ", kodeProduk);
            kebutuhanProduk[indeksProduk][1] = scannerInput.nextInt();
            if (kebutuhanProduk[indeksProduk][1] < 0) {
                System.out.println("Input tidak bisa negatif!");
                return;
            }

            System.out.printf("Masukkan keuntungan per unit Produk %c: ", kodeProduk);
            kebutuhanProduk[indeksProduk][2] = scannerInput.nextInt();
            if (kebutuhanProduk[indeksProduk][2] < 0) {
                System.out.println("Input tidak bisa negatif!");
                return;
            }
        }

        // Inisialisasi array untuk kombinasi dan kombinasi sementara
        daftarKombinasi = new int[1000000][jumlahJenisProduk + 2];
        kombinasiSementara = new int[jumlahJenisProduk + 2];

        // Mulai iterasi untuk mencari kombinasi optimal
        cariKombinasiProduk(0);

        // Cetak hasil kombinasi
        cetakKombinasi();
    }

    static void cariKombinasiProduk(int indeksProduk) {
        if (indeksProduk == jumlahJenisProduk) { // Basis rekursi: semua produk telah diiterasi
            if (totalWaktuKerja <= batasWaktuKerja && totalBahanBaku <= batasBahanBaku) { // Validasi batasan
                for (int indeksKolom = 0; indeksKolom < jumlahJenisProduk + 2; indeksKolom++) {
                    daftarKombinasi[jumlahKombinasi][indeksKolom] = kombinasiSementara[indeksKolom];
                }
                daftarKeuntungan[jumlahKombinasi] = totalKeuntungan;
                if (jumlahKombinasi != 0) {
                    if (daftarKeuntungan[indeksKombinasiOptimal] < daftarKeuntungan[jumlahKombinasi]) {
                        indeksKombinasiOptimal = jumlahKombinasi;
                    }
                }
                jumlahKombinasi++;
            }
            return;
        }

        // Rekursi: Iterasi jumlah untuk produk ke-indeksProduk
        for (int jumlahProduk = 0; jumlahProduk <= batasWaktuKerja / kebutuhanProduk[indeksProduk][0]; jumlahProduk++) {
            int tambahanWaktuKerja = kebutuhanProduk[indeksProduk][0] * jumlahProduk;
            int tambahanBahanBaku = kebutuhanProduk[indeksProduk][1] * jumlahProduk;
            int tambahanKeuntungan = kebutuhanProduk[indeksProduk][2] * jumlahProduk;

            // Validasi apakah tambahan ini tidak melebihi batas
            if (totalWaktuKerja + tambahanWaktuKerja <= batasWaktuKerja
                    && totalBahanBaku + tambahanBahanBaku <= batasBahanBaku) {
                totalWaktuKerja += tambahanWaktuKerja;
                totalBahanBaku += tambahanBahanBaku;
                totalKeuntungan += tambahanKeuntungan;
                kombinasiSementara[indeksProduk] = jumlahProduk;
                kombinasiSementara[jumlahJenisProduk] = totalWaktuKerja;
                kombinasiSementara[jumlahJenisProduk + 1] = totalBahanBaku;

                // Rekursi ke produk berikutnya
                cariKombinasiProduk(indeksProduk + 1);

                // mengembalikan nilai nilai untuk mencoba kombinasi lainnya
                totalWaktuKerja -= tambahanWaktuKerja;
                totalBahanBaku -= tambahanBahanBaku;
                totalKeuntungan -= tambahanKeuntungan;
                kombinasiSementara[indeksProduk] = 0;
                kombinasiSementara[jumlahJenisProduk] = 0;
                kombinasiSementara[jumlahJenisProduk + 1] = 0;
            } else {
                break; // Hentikan iterasi jika sumber daya tidak mencukupi
            }
        }
    }

    static String formatTengah(String teks) {
        //memformat teks menjadi center, batas teks pada tabel adalah 13 karakter
        String hasilFormat = "";
        int padding = 13 - teks.length();
        int paddingKanan = (padding % 2 != 0) ? padding / 2 + 1 : padding / 2;
        int paddingKiri = padding - paddingKanan;
        for (int i = 0; i < paddingKanan; i++) {
            hasilFormat += " ";
        }
        hasilFormat += teks;
        for (int i = 0; i < paddingKiri; i++) {
            hasilFormat += " ";
        }

        return hasilFormat;
    }

    static void cetakKombinasi() {
        System.out.println("=== Proses Pencarian Kombinasi ===");

        // Header tabel
        String garis = "";
        for (int kolom = 0; kolom < jumlahJenisProduk + 3; kolom++) {
            garis += "-";
            garis += "-------------";
        }
        garis += "-";
        String headerTabel = "";
        for (int indeksProduk = 0, kodeProduk = 65; indeksProduk < jumlahJenisProduk; indeksProduk++, kodeProduk++) {
            headerTabel += "|";
            headerTabel += formatTengah(String.format("Produk %c", kodeProduk));
        }
        headerTabel += "| Waktu Mesin |  Bahan Baku |  Keuntungan |";
        System.out.println(garis);
        System.out.println(headerTabel);
        System.out.println(garis);

        // Isi tabel kombinasi
        for (int indeksKombinasi = 0; indeksKombinasi < jumlahKombinasi; indeksKombinasi++) {
            String isiTabel = "";
            for (int kolom = 0; kolom < jumlahJenisProduk + 2; kolom++) {
                String nilai = "" + daftarKombinasi[indeksKombinasi][kolom];
                isiTabel += "|" + formatTengah(nilai);
            }
            isiTabel += "|" + formatTengah(String.format("Rp%d", daftarKeuntungan[indeksKombinasi])) + "|";

            if (indeksKombinasi == indeksKombinasiOptimal) {
                isiTabel += "**(Optimal)**";
            }
            System.out.println(isiTabel);
            System.out.println(garis);
        }

        // Hasil akhir kombinasi optimal
        System.out.println("=== Hasil Optimasi ===");
        System.out.println("Produk Optimal: ");
        for (int indeksProduk = 0, charProduct = 65; indeksProduk < jumlahJenisProduk; indeksProduk++) {
            System.out.printf("Produk %c: %d\n", charProduct + indeksProduk,
                    daftarKombinasi[indeksKombinasiOptimal][indeksProduk]);
        }
        System.out.printf("Keuntungan Maksimal: Rp%d\n", daftarKeuntungan[indeksKombinasiOptimal]);
    }
}