# Sistem Informasi Laundry (SIL)

Aplikasi desktop Java Swing untuk mencatat pesanan laundry, menghitung biaya, mengelola antrean FIFO, membatasi empat pesanan yang sedang diproses, dan menyiapkan notifikasi WhatsApp.

## Kebutuhan

- JDK 17 atau lebih baru.
- Tidak memerlukan database atau library eksternal.

## Menjalankan

Dari direktori proyek, gunakan PowerShell:

```powershell
New-Item -ItemType Directory -Force build | Out-Null
javac -encoding UTF-8 -d build (Get-ChildItem src -Recurse -Filter *.java | ForEach-Object FullName)
java -cp build com.laundry.Main
```

Di Linux/macOS: `javac -encoding UTF-8 -d build $(find src -name '*.java')` lalu `java -cp build com.laundry.Main`.

## Penggunaan

1. Isi nama, nomor WhatsApp Indonesia (`08...`, `628...`, atau `+628...`), layanan, dan berat dalam kg. Berat boleh memakai koma atau titik desimal. Estimasi biaya diperbarui saat berat atau layanan berubah.
2. Simpan pesanan. Tab antrean akan terbuka. Tombol **Proses Antrean Terdepan** selalu mengambil pesanan pertama yang menunggu, selama jumlah pesanan diproses kurang dari empat.
3. Pilih baris pada tabel **Sedang Diproses** dan tekan **Tandai Selesai**. Urutan penyelesaian boleh berbeda dari urutan kedatangan.
4. Pilih baris pada **Siap Diambil** untuk membuka tautan WhatsApp. Kasir perlu memeriksa dan mengirim pesan sendiri. Jika browser gagal dibuka, tautan dan pesan dapat disalin dari dialog.
5. Setelah cucian diambil, pilih pesanan dan tekan **Konfirmasi Sudah Diambil**.

Semua data hanya berada dalam memori dan hilang saat aplikasi ditutup. Kapasitas empat mesin adalah aturan operasional di aplikasi, bukan kendali mesin fisik.

## Pengujian

```powershell
javac -encoding UTF-8 -cp build -d build (Get-ChildItem test -Recurse -Filter *.java | ForEach-Object FullName)
java '-Djava.awt.headless=true' -cp build com.laundry.LaundryManagerTest
```

Dokumentasi analisis, diagram UML, dan catatan pengujian ada di [docs/LAPORAN.md](docs/LAPORAN.md). Spesifikasi lengkap ada di [PRD.md](PRD.md).
