# Optimasi Produksi Bengkel

Proyek ini berisi dua program utama untuk melakukan optimasi produksi pada sebuah bengkel dengan batasan waktu kerja mesin dan bahan baku. Program ditulis dalam bahasa Java.

## Fitur

- **OptimasiBarang.java**  
  Mengoptimalkan keuntungan produksi untuk 2 jenis produk (A dan B) berdasarkan input kapasitas waktu, bahan baku, kebutuhan, dan keuntungan tiap produk.

- **OptimasiProduksi.java**  
  Mendukung optimasi untuk lebih dari 2 jenis produk. Program ini menggunakan pendekatan rekursif untuk mencari kombinasi produk yang memberikan keuntungan maksimal dengan batasan waktu dan bahan baku.

## Cara Menjalankan

1. **Kompilasi**

   ```sh
   javac Source\ Codes/OptimasiBarang.java
   javac Source\ Codes/OptimasiProduksi.java
   ```

2. **Jalankan**
   ```sh
   java OptimasiBarang
   java projectpemdas.OptimasiProduksi
   ```

## Input

- Kapasitas waktu kerja mesin (jam)
- Kapasitas bahan baku (unit)
- Untuk setiap produk: waktu kerja per unit, bahan baku per unit, keuntungan per unit

## Output

- Daftar semua kombinasi produksi yang memungkinkan beserta keuntungannya
- Kombinasi optimal dengan keuntungan maksimal

## Struktur Folder

- `Source Codes/OptimasiBarang.java` — Optimasi untuk 2 produk
- `Source Codes/OptimasiProduksi.java` — Optimasi untuk banyak produk

---

**Catatan:**  
Pastikan Java sudah terinstal di
