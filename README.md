# Sistem Informasi Laundry (SIL)

Aplikasi desktop Java Swing untuk mencatat pesanan laundry, menghitung biaya, mengelola antrean FIFO, membatasi empat pesanan yang sedang diproses, dan menyiapkan notifikasi WhatsApp.

## Prasyarat

- JDK 17 atau lebih baru, lengkap dengan `java` dan `javac`. JRE saja tidak cukup untuk kompilasi.
- Sesi desktop grafis untuk menampilkan Java Swing. Terminal server tanpa desktop hanya dapat menjalankan tes logika.
- Tidak memerlukan database, Maven, Gradle, atau library eksternal.

Semua perintah kompilasi dan menjalankan aplikasi di bawah ini dijalankan dari **root proyek**, yaitu folder yang berisi `README.md`, `src/`, dan `test/`. Folder `build/` dibuat otomatis dan berisi hasil kompilasi. Ulangi kompilasi setelah mengubah source code.

## Windows (PowerShell)

1. Pasang JDK jika belum tersedia. Contoh dengan Windows Package Manager:

   ```powershell
   winget install --id Microsoft.OpenJDK.21 -e
   ```

   Alternatifnya, pasang JDK melalui [installer Microsoft OpenJDK](https://learn.microsoft.com/en-us/windows/dev-environment/java) atau [installer Temurin](https://adoptium.net/installation/windows/). Buka PowerShell baru setelah instalasi agar perubahan `PATH` terbaca.

2. Periksa versi dan masuk ke root proyek. Ganti path bila proyek disimpan di tempat lain:

   ```powershell
   java -version
   javac -version
   Set-Location 'D:\Project Code\PBO project\SIL\SIL'
   ```

3. Kompilasi dan jalankan aplikasi:

   ```powershell
   New-Item -ItemType Directory -Force build | Out-Null
   javac --release 17 -encoding UTF-8 -d build (Get-ChildItem src -Recurse -Filter *.java | ForEach-Object FullName)
   java -cp build com.laundry.Main
   ```

   Jendela **Sistem Informasi Laundry** akan terbuka. Tutup jendelanya untuk menghentikan aplikasi.

## Arch Linux

1. Pasang JDK 17 dari repositori resmi Arch:

   ```bash
   sudo pacman -Syu jdk17-openjdk
   ```

   Paket dan pengaturan Java dijelaskan di [Arch Packages](https://archlinux.org/packages/extra/x86_64/jdk17-openjdk/) dan [ArchWiki](https://wiki.archlinux.org/title/Java). Jika `java` atau `javac` masih menunjuk ke versi lain yang lebih lama, periksa `archlinux-java status` lalu pilih JDK 17 dengan `sudo archlinux-java set java-17-openjdk`.

2. Periksa versi, lalu masuk ke root proyek. Ganti path contoh sesuai lokasi proyek:

   ```bash
   java -version
   javac -version
   cd "/lokasi/proyek/SIL"
   ```

3. Kompilasi dan jalankan dari terminal dalam sesi desktop:

   ```bash
   mkdir -p build
   javac --release 17 -encoding UTF-8 -d build $(find src -type f -name '*.java')
   java -cp build com.laundry.Main
   ```

## Fedora Workstation

1. Pasang JDK dan runtime grafis OpenJDK. Paket JDK 25 tersedia pada Fedora 43–45 dan tetap dapat menghasilkan bytecode Java 17 melalui `--release 17`:

   ```bash
   sudo dnf install java-25-openjdk-devel java-25-openjdk
   ```

   Lihat paket resmi [JDK](https://packages.fedoraproject.org/pkgs/java-25-openjdk/java-25-openjdk-devel/) dan [runtime grafis](https://packages.fedoraproject.org/pkgs/java-25-openjdk/java-25-openjdk/). Jika Fedora yang digunakan menyediakan versi JDK berbeda, pilih paket `java-<versi>-openjdk-devel` dan `java-<versi>-openjdk` dengan versi minimal 17.

2. Periksa versi, lalu masuk ke root proyek. Ganti path contoh sesuai lokasi proyek:

   ```bash
   java -version
   javac -version
   cd "/lokasi/proyek/SIL"
   ```

3. Kompilasi dan jalankan dari terminal dalam sesi desktop:

   ```bash
   mkdir -p build
   javac --release 17 -encoding UTF-8 -d build $(find src -type f -name '*.java')
   java -cp build com.laundry.Main
   ```

Jika kompilasi gagal, perbaiki pesan kesalahan dari `javac` sebelum menjalankan perintah `java`. Pada Linux, terminal harus berada dalam sesi grafis; `HeadlessException` biasanya berarti jendela Swing tidak mempunyai display.

## Penggunaan

1. Isi nama, nomor WhatsApp Indonesia (`08...`, `628...`, atau `+628...`), layanan, dan berat dalam kg. Berat boleh memakai koma atau titik desimal. Estimasi biaya diperbarui saat berat atau layanan berubah.
2. Simpan pesanan. Halaman antrean akan terbuka. Tombol **Proses Antrean Terdepan** selalu mengambil pesanan pertama yang menunggu, selama jumlah pesanan diproses kurang dari empat.
3. Pilih baris pada tabel **Sedang Diproses** dan tekan **Tandai Selesai**. Urutan penyelesaian boleh berbeda dari urutan kedatangan.
4. Pilih baris pada **Siap Diambil** untuk membuka tautan WhatsApp. Kasir perlu memeriksa dan mengirim pesan sendiri. Jika browser gagal dibuka, tautan dan pesan dapat disalin dari dialog.
5. Setelah cucian diambil, pilih pesanan dan tekan **Konfirmasi Sudah Diambil**.

Semua data hanya berada dalam memori dan hilang saat aplikasi ditutup. Kapasitas empat mesin adalah aturan operasional di aplikasi, bukan kendali mesin fisik.

## Menjalankan Tes Logika

Tes ini tidak membuka GUI. Kompilasi aplikasi terlebih dahulu dengan langkah untuk sistem operasi masing-masing.

Windows (PowerShell):

```powershell
javac --release 17 -encoding UTF-8 -cp build -d build (Get-ChildItem test -Recurse -Filter *.java | ForEach-Object FullName)
java '-Djava.awt.headless=true' -cp build com.laundry.LaundryManagerTest
```

Arch Linux dan Fedora (Bash):

```bash
javac --release 17 -encoding UTF-8 -cp build -d build $(find test -type f -name '*.java')
java -Djava.awt.headless=true -cp build com.laundry.LaundryManagerTest
```

Jika berhasil, terminal menampilkan `LULUS: 50 pemeriksaan logika pesanan.`

## Jika Aplikasi Tidak Berjalan

- `java` atau `javac` tidak ditemukan: pastikan JDK terpasang, buka terminal baru, lalu cek kedua versi. Di Windows, pastikan direktori `bin` JDK ada di `PATH`.
- `Could not find or load main class com.laundry.Main`: jalankan perintah dari root proyek dan kompilasi ulang ke folder `build/`.
- `UnsupportedClassVersionError`: pastikan `java -version` menunjukkan versi 17 atau lebih baru. Versi `java` yang menjalankan program tidak boleh lebih tua dari target kompilasi.
- Jendela tidak muncul di Linux: jalankan dari desktop grafis, bukan terminal tanpa display; pastikan runtime OpenJDK yang terpasang bukan varian *headless* saja.

Dokumentasi analisis, diagram UML, dan catatan pengujian ada di [docs/LAPORAN.md](docs/LAPORAN.md). Spesifikasi lengkap ada di [PRD.md](PRD.md).
