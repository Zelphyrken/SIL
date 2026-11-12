# PRODUCT REQUIREMENTS DOCUMENT (PRD)
## Sistem Manajemen Operasional UMKM Laundry
**Aplikasi Desktop Berbasis Java OOP dan Java Swing**

| Informasi | Keterangan |
|---|---|
| Nama Proyek | Sistem Manajemen Operasional UMKM Laundry |
| Jenis Proyek | Tugas Project Pemrograman Berorientasi Objek |
| Versi Dokumen | 1.0 |
| Status | Spesifikasi Awal Pengembangan |
| Platform | Desktop |
| Bahasa Pemrograman | Java (JDK 17 atau lebih baru) |
| Antarmuka | Java Swing (`javax.swing`) |
| Penyimpanan Data | Sementara dalam memori |
| Pengguna Utama | Kasir dan Pegawai Laundry |
| Metodologi | Object-Oriented Analysis (OOA), Object-Oriented Design (OOD), Object-Oriented Programming (OOP) |
| Tingkat Kompleksitas | Dasar–Menengah, sesuai modul perkuliahan |

---

# 1. PENDAHULUAN

## 1.1 Latar Belakang

Usaha Mikro, Kecil, dan Menengah (UMKM) di bidang laundry merupakan salah satu usaha jasa yang membutuhkan pengelolaan pesanan secara teratur. Dalam kegiatan operasional sehari-hari, pegawai laundry melakukan berbagai aktivitas, seperti menerima pesanan pelanggan, mencatat jenis layanan, menimbang cucian, menghitung biaya, mengelola antrean pengerjaan, hingga menyerahkan cucian yang telah selesai.

Pada usaha laundry yang masih menggunakan pencatatan manual, beberapa permasalahan dapat terjadi, seperti kesalahan perhitungan biaya, kurang teraturnya antrean pesanan, kesulitan mengetahui status pengerjaan, serta keterlambatan pemberian informasi kepada pelanggan.

Untuk membantu mengatasi permasalahan tersebut, dikembangkan **Sistem Manajemen Operasional UMKM Laundry**, yaitu aplikasi desktop sederhana menggunakan bahasa pemrograman Java dan antarmuka Java Swing.

Aplikasi ini dirancang untuk membantu pegawai mengelola kegiatan operasional laundry secara lebih terstruktur, mulai dari penerimaan pesanan hingga pengambilan cucian oleh pelanggan.

Selain memiliki manfaat operasional, pengembangan aplikasi ini bertujuan menerapkan konsep Pemrograman Berorientasi Objek (OOP) yang telah dipelajari dalam perkuliahan, yaitu class, object, encapsulation, inheritance, abstract class, interface, dan polymorphism.

## 1.2 Rumusan Masalah

Permasalahan yang menjadi dasar pengembangan aplikasi adalah:

1. Bagaimana membantu pegawai laundry mencatat pesanan pelanggan secara terstruktur?
2. Bagaimana menghitung biaya laundry secara otomatis berdasarkan jenis layanan dan berat cucian?
3. Bagaimana menerapkan sistem antrean FIFO agar pesanan diproses sesuai urutan kedatangan?
4. Bagaimana membatasi jumlah pesanan yang sedang diproses berdasarkan kapasitas mesin?
5. Bagaimana memantau perubahan status pesanan dari penerimaan hingga pengambilan?
6. Bagaimana membantu pegawai menyiapkan pemberitahuan WhatsApp ketika pesanan selesai?
7. Bagaimana menerapkan konsep OOP dalam perancangan dan implementasi aplikasi sesuai materi perkuliahan?

## 1.3 Tujuan Pengembangan

Tujuan pengembangan aplikasi ini adalah:

- Membuat aplikasi desktop yang dapat digunakan untuk mencatat dan mengelola pesanan laundry.
- Mengotomatisasi perhitungan biaya berdasarkan paket layanan.
- Mengatur antrean pesanan berdasarkan prinsip First In, First Out (FIFO).
- Membantu pegawai memantau pesanan yang menunggu, sedang diproses, dan telah selesai.
- Mendukung pemberitahuan kepada pelanggan melalui tautan WhatsApp.
- Mengimplementasikan konsep dasar hingga menengah Java OOP secara nyata.
- Menghasilkan aplikasi yang memiliki hubungan konsisten antara Use Case Diagram, Class Diagram, Sequence Diagram, dan kode program.
- Menghasilkan program yang mudah dipahami, dijelaskan, diuji, dan dipresentasikan sebagai tugas perkuliahan.

---

# 2. DESKRIPSI UMUM PRODUK

## 2.1 Ringkasan Produk

Sistem Manajemen Operasional UMKM Laundry merupakan aplikasi desktop yang digunakan oleh pegawai laundry untuk mengelola aktivitas penerimaan dan pengerjaan cucian.

Ketika pelanggan datang, pegawai memasukkan nama pelanggan, nomor WhatsApp, jenis layanan, dan berat cucian. Sistem secara otomatis menghitung total biaya serta mencatat pesanan ke dalam antrean.

Pesanan yang diterima memiliki status awal `BELUM_MULAI`. Ketika terdapat mesin kosong, pegawai dapat memulai pengerjaan pesanan berdasarkan urutan antrean FIFO.

Sistem memiliki kapasitas maksimal empat pesanan yang dapat dikerjakan secara bersamaan untuk merepresentasikan empat mesin cuci.

Setelah proses laundry selesai, pegawai menandai pesanan sebagai `SELESAI`. Pegawai kemudian dapat membuka WhatsApp dengan pesan pemberitahuan yang telah disiapkan.

Ketika pelanggan mengambil cucian, pegawai mengonfirmasi pengambilan sehingga status berubah menjadi `DIAMBIL`.

Aplikasi tidak melakukan pengendalian mesin cuci secara fisik. Kapasitas empat mesin hanya direpresentasikan melalui batas jumlah pesanan aktif di dalam sistem.

## 2.2 Sasaran Pengguna

### Kasir

Kasir bertanggung jawab menerima pesanan, memasukkan data pelanggan, memilih layanan, menghitung biaya, mengirim pemberitahuan, dan mengonfirmasi pengambilan cucian.

### Pegawai Laundry

Pegawai laundry bertanggung jawab memantau antrean, memulai pengerjaan pesanan, dan menandai pesanan yang telah selesai.

### Pemilik Usaha (Owner)

Pemilik usaha dapat menggunakan tampilan aplikasi yang sama untuk melihat kondisi pesanan. Pada versi ini tidak diperlukan sistem login ataupun pembagian hak akses khusus.

## 2.3 Lingkungan Penggunaan

Aplikasi digunakan pada komputer lokal di tempat usaha laundry.

Program berjalan menggunakan Java Runtime yang sesuai dan tidak membutuhkan server ataupun database.

Fungsi utama aplikasi dapat digunakan tanpa koneksi internet. Koneksi internet diperlukan ketika pengguna ingin membuka tautan WhatsApp.

---

# 3. RUANG LINGKUP PROYEK

## 3.1 Fitur yang Termasuk dalam Pengembangan

Fitur utama yang wajib dikembangkan meliputi:

1. Pencatatan pesanan laundry baru.
2. Pencatatan informasi pelanggan.
3. Pemilihan paket layanan laundry.
4. Perhitungan biaya otomatis.
5. Pembuatan ID pesanan otomatis.
6. Pengelolaan antrean FIFO.
7. Pembatasan kapasitas empat pesanan yang sedang diproses.
8. Perubahan status pengerjaan.
9. Monitoring pesanan melalui tabel.
10. Pemberitahuan WhatsApp menggunakan tautan browser.
11. Konfirmasi pengambilan pesanan.
12. Validasi input dan penanganan kesalahan sederhana.

## 3.2 Fitur yang Tidak Termasuk

Untuk menjaga tingkat kesulitan sesuai materi perkuliahan, fitur berikut tidak dikembangkan pada versi awal:

- Login dan autentikasi pengguna.
- Pembagian hak akses yang kompleks.
- Database MySQL, PostgreSQL, atau sistem penyimpanan permanen.
- Integrasi payment gateway.
- Pembayaran digital dan pencatatan transaksi keuangan.
- Pembuatan invoice PDF atau pencetakan struk.
- Laporan keuangan dan grafik analitik.
- Pengelolaan stok bahan laundry.
- Pengaturan jadwal karyawan.
- Integrasi langsung dengan mesin cuci.
- Pengiriman pesan otomatis melalui WhatsApp API.
- Sistem cloud, hosting, atau sinkronisasi antarkomputer.
- Pemrosesan paralel menggunakan thread.
- Framework atau library eksternal.

Fokus utama aplikasi adalah pengelolaan pesanan dan penerapan konsep OOP.

---

# 4. KEBUTUHAN FUNGSIONAL

Setiap kebutuhan fungsional diberi kode untuk memudahkan implementasi dan pengujian.

## FR-01 — Input Pesanan Baru

**Deskripsi:** Sistem menyediakan formulir untuk menerima pesanan laundry baru.

Data yang dimasukkan adalah nama pelanggan, nomor WhatsApp, jenis layanan, dan berat cucian dalam kilogram.

**Aturan:**
- Semua input wajib diisi.
- Pegawai memilih salah satu layanan yang tersedia.
- Setelah validasi berhasil, sistem membuat objek pesanan baru.
- Pesanan baru otomatis memiliki status `BELUM_MULAI`.
- Pesanan dimasukkan ke antrean FIFO dan daftar seluruh pesanan.
- Formulir dikosongkan setelah pesanan berhasil disimpan.

**Kriteria penerimaan:** Ketika seluruh data valid dan pengguna menekan tombol simpan, pesanan baru muncul pada tabel antrean menunggu.

## FR-02 — Pengelolaan Jenis Layanan

Sistem menyediakan dua jenis layanan laundry.

| Layanan | Harga | Perhitungan |
|---|---:|---|
| Cuci Kering | Rp5.000/kg | Berat × 5.000 |
| Cuci Setrika | Rp7.000/kg | Berat × 7.000 |

Kedua layanan diimplementasikan sebagai class turunan dari abstract class `Layanan`.

Setiap class memiliki implementasi method `hitungTotalBiaya(double beratKg)`.

**Kriteria penerimaan:** Sistem menghasilkan total biaya sesuai dengan jenis layanan dan berat yang dipilih.

## FR-03 — Perhitungan Total Biaya

Sistem menghitung biaya secara otomatis menggunakan rumus:

**Total Biaya = Berat Cucian (kg) × Harga Layanan per Kg**

Contoh:

Pelanggan memilih Cuci Setrika dengan berat 2,5 kg.

Total Biaya = 2,5 × Rp7.000 = **Rp17.500**

Ketentuan:
- Perhitungan dilakukan melalui method milik objek `Layanan`.
- Nilai total diperbarui ketika layanan atau berat berubah.
- Hasil ditampilkan dalam format Rupiah.
- Berat yang belum valid menampilkan estimasi `Rp0`.
- Total biaya dihitung ulang ketika pesanan disimpan.
- Jumlah biaya ditampilkan dalam Rupiah tanpa angka desimal, menggunakan pembulatan ke rupiah terdekat apabila diperlukan.

**Kriteria penerimaan:** Perubahan berat atau layanan langsung memengaruhi estimasi biaya tanpa harus menyimpan pesanan.

## FR-04 — Pembuatan ID Otomatis

Sistem memberikan ID berbeda untuk setiap pesanan.

Format ID:

`ORD-001`, `ORD-002`, `ORD-003`, dan seterusnya.

ID dibuat menggunakan counter sederhana dalam class `LaundryManager`.

Ketentuan:
- ID tidak boleh sama selama aplikasi masih berjalan.
- Nomor bertambah setiap kali pesanan berhasil dicatat.
- Counter dimulai kembali ketika aplikasi dibuka ulang karena belum menggunakan penyimpanan permanen.

**Kriteria penerimaan:** Dua pesanan yang dibuat secara berurutan memiliki ID berbeda.

## FR-05 — Pengelolaan Antrean FIFO

Sistem menggunakan prinsip First In, First Out, yaitu pesanan yang masuk lebih dahulu harus mulai diproses lebih dahulu.

Implementasi menggunakan struktur data Java:

`Queue<Pesanan>` dan `LinkedList<Pesanan>`.

Ketentuan:
- Pesanan baru masuk ke bagian belakang antrean.
- Pesanan yang diproses diambil dari bagian depan antrean.
- Pesanan tidak boleh dipilih secara acak untuk memulai pengerjaan.
- Urutan antrean tidak boleh berubah tanpa proses yang ditentukan.
- Antrean hanya menampung pesanan berstatus `BELUM_MULAI`.

**Kriteria penerimaan:** Jika pesanan A, B, dan C masuk secara berurutan, proses pengerjaan dimulai dari A, kemudian B, lalu C.

## FR-06 — Pembatasan Kapasitas Mesin

Sistem menerapkan batas maksimal **empat pesanan berstatus `DIPROSES`** secara bersamaan.

Ketentuan:
- Kapasitas mesin dinyatakan menggunakan konstanta bernilai 4.
- Jika jumlah pesanan aktif kurang dari empat, pesanan berikutnya dapat diproses.
- Jika jumlah pesanan aktif mencapai empat, sistem menolak pemrosesan tambahan.
- Ketika salah satu pesanan selesai, kapasitas tersedia kembali.
- Perhitungan kapasitas berdasarkan jumlah pesanan aktif, bukan simulasi mesin fisik.

**Kriteria penerimaan:** Sistem tidak pernah mengizinkan lebih dari empat pesanan memiliki status `DIPROSES` pada waktu yang sama.

## FR-07 — Penyelesaian Pesanan

Pegawai dapat menandai pesanan yang sedang diproses sebagai selesai.

Ketentuan:
- Pesanan harus berstatus `DIPROSES`.
- Pegawai memilih pesanan dari tabel sedang diproses.
- Sistem mengubah status menjadi `SELESAI`.
- Pesanan berpindah ke tabel siap diambil.
- Kapasitas pengerjaan berkurang satu.

**Aturan penting:** Penyelesaian pesanan tidak harus mengikuti FIFO. Pesanan yang diproses belakangan boleh selesai lebih dahulu.

**Kriteria penerimaan:** Pesanan B dapat ditandai selesai sebelum pesanan A selama keduanya berstatus `DIPROSES`.

## FR-08 — Konfirmasi Pengambilan

Kasir dapat menandai bahwa pelanggan telah mengambil cucian.

Ketentuan:
- Pesanan harus berstatus `SELESAI`.
- Kasir memilih pesanan yang bersangkutan.
- Setelah dikonfirmasi, status berubah menjadi `DIAMBIL`.
- Pesanan tidak lagi muncul pada tabel siap diambil.
- Data pesanan tetap berada dalam daftar seluruh pesanan selama aplikasi berjalan.

**Kriteria penerimaan:** Pesanan yang telah diambil tidak lagi ditampilkan sebagai pesanan aktif.

## FR-09 — Monitoring Status Pesanan

Sistem menyediakan tampilan monitoring menggunakan `JTable`.

Monitoring terdiri atas tiga tabel:

| Tabel | Status | Informasi Utama |
|---|---|---|
| Antrean Menunggu | `BELUM_MULAI` | ID, nama, layanan, berat, biaya |
| Sedang Diproses | `DIPROSES` | ID, nama, layanan, berat, biaya |
| Siap Diambil | `SELESAI` | ID, nama, nomor WhatsApp, layanan, biaya |

Ketentuan:
- Data ditampilkan berdasarkan status sebenarnya.
- Tabel tidak dapat diedit langsung.
- Tombol tindakan bekerja berdasarkan baris yang dipilih.
- Data tabel diperbarui setelah terjadi perubahan status.
- Sistem menampilkan kapasitas mesin aktif, misalnya `2 / 4`.

## FR-10 — Notifikasi WhatsApp

Sistem membantu kasir menyiapkan pesan pemberitahuan bahwa cucian telah selesai.

Fitur menggunakan tautan:

`https://wa.me/`

Isi pesan minimal mencakup:
- Nama pelanggan.
- ID pesanan.
- Jenis layanan.
- Berat cucian.
- Total biaya.
- Informasi bahwa cucian telah selesai dan siap diambil.

Contoh pesan:

> Halo Bapak/Ibu Andi, cucian Anda dengan nomor pesanan ORD-001 telah selesai dan siap diambil. Layanan: Cuci Setrika, Berat: 2,5 kg, Total Biaya: Rp17.500. Terima kasih telah menggunakan layanan laundry kami.

Ketentuan:
- Fitur hanya tersedia untuk pesanan berstatus `SELESAI`.
- Nomor telepon dinormalisasi ke format internasional Indonesia.
- Awalan `0` diubah menjadi `62`.
- Pesan dikodekan menggunakan `URLEncoder`.
- Sistem membuka browser melalui `Desktop.getDesktop().browse(...)` apabila didukung.
- Jika browser tidak tersedia, sistem menampilkan pesan dan tautan yang dapat disalin.
- Pengiriman pesan tetap dilakukan oleh kasir melalui WhatsApp.

**Kriteria penerimaan:** Tombol WhatsApp menyiapkan tautan dengan nomor tujuan dan pesan yang sesuai.

## FR-11 — Validasi Input

Sistem wajib memvalidasi data sebelum membuat pesanan.

| Kondisi | Respons Sistem |
|---|---|
| Nama kosong | Menampilkan peringatan |
| Nomor WhatsApp kosong | Menampilkan peringatan |
| Nomor WhatsApp tidak valid | Menolak penyimpanan |
| Berat kosong | Menampilkan peringatan |
| Berat bukan angka | Menampilkan peringatan |
| Berat ≤ 0 | Menolak penyimpanan |
| Tidak ada pesanan menunggu | Menampilkan informasi antrean kosong |
| Kapasitas mesin penuh | Menolak proses tambahan |
| Tombol tabel ditekan tanpa memilih baris | Meminta pengguna memilih pesanan |
| Perubahan status tidak sesuai urutan | Menolak perubahan |
| Browser WhatsApp gagal dibuka | Menampilkan tautan dan pesan manual |

Validasi berat menggunakan `try-catch` untuk menangani `NumberFormatException`.

Nomor WhatsApp minimal berisi sembilan digit setelah mengabaikan tanda `+` awal. Untuk versi awal, input yang diterima menggunakan format Indonesia, seperti `08...`, `628...`, atau `+628...`. Format yang tidak sesuai ditolak dengan pesan yang jelas.

---

# 5. ALUR KERJA SISTEM

## 5.1 Siklus Status Pesanan

Setiap pesanan memiliki empat status.

| Status | Pengertian |
|---|---|
| `BELUM_MULAI` | Pesanan diterima dan menunggu pengerjaan |
| `DIPROSES` | Pesanan sedang dikerjakan |
| `SELESAI` | Pesanan selesai dan siap diambil |
| `DIAMBIL` | Pesanan telah diambil pelanggan |

Perubahan status mengikuti urutan:

**BELUM_MULAI → DIPROSES → SELESAI → DIAMBIL**

Ketentuan:
- Perubahan status hanya dapat bergerak ke tahap berikutnya.
- Pesanan tidak dapat langsung berubah dari `BELUM_MULAI` menjadi `SELESAI`.
- Pesanan yang sudah `DIAMBIL` tidak memiliki proses lanjutan.
- Status tidak dapat dikembalikan ke tahap sebelumnya dalam versi awal.

## 5.2 Alur Penerimaan Pesanan

1. Pelanggan datang membawa cucian.
2. Kasir membuka tab penerimaan pesanan.
3. Kasir memasukkan identitas pelanggan.
4. Kasir memilih paket layanan.
5. Kasir memasukkan berat cucian.
6. Sistem menghitung estimasi biaya.
7. Kasir menekan tombol penerimaan pesanan.
8. Sistem memvalidasi seluruh data.
9. Sistem membuat objek `Pelanggan` dan `Pesanan`.
10. Sistem membuat ID pesanan.
11. Sistem menambahkan pesanan ke antrean FIFO.
12. Sistem menampilkan pemberitahuan bahwa pesanan berhasil dicatat.

## 5.3 Alur Pemrosesan Antrean

1. Pegawai membuka tab monitoring.
2. Sistem menampilkan urutan antrean dan kapasitas mesin.
3. Pegawai menekan tombol proses antrean terdepan.
4. Sistem memeriksa apakah antrean tersedia.
5. Sistem memeriksa apakah kapasitas masih tersedia.
6. Jika kedua kondisi terpenuhi, pesanan pertama diambil dari antrean.
7. Status pesanan berubah menjadi `DIPROSES`.
8. Sistem memperbarui tabel dan indikator kapasitas.

## 5.4 Alur Penyelesaian Cucian

1. Pegawai melihat tabel sedang diproses.
2. Pegawai memilih pesanan yang telah selesai.
3. Pegawai menekan tombol tandai selesai.
4. Sistem memeriksa status pesanan.
5. Sistem mengubah status menjadi `SELESAI`.
6. Pesanan berpindah ke tabel siap diambil.
7. Jumlah mesin aktif berkurang.

## 5.5 Alur Pemberitahuan dan Pengambilan

1. Kasir memilih pesanan pada tabel siap diambil.
2. Kasir menekan tombol WhatsApp.
3. Sistem membuat pesan pemberitahuan.
4. Sistem membuka tautan WhatsApp atau menyediakan tautan manual.
5. Kasir mengirimkan pesan kepada pelanggan.
6. Pelanggan datang untuk mengambil cucian.
7. Kasir memilih pesanan dan menekan konfirmasi pengambilan.
8. Status pesanan berubah menjadi `DIAMBIL`.

---

# 6. PERANCANGAN OBJECT-ORIENTED ANALYSIS (OOA)

Tahap OOA berfokus pada identifikasi kebutuhan, aktor, objek, serta fungsi yang harus disediakan sistem.

## 6.1 Identifikasi Aktor

Aktor yang terlibat:

**Kasir**
- Menerima pesanan.
- Memasukkan data pelanggan.
- Menghitung biaya.
- Memantau status.
- Membuka notifikasi WhatsApp.
- Mengonfirmasi pengambilan.

**Pegawai Laundry**
- Melihat antrean.
- Memulai pengerjaan.
- Memantau kapasitas.
- Menandai pesanan selesai.

Owner dapat menggunakan fungsi monitoring yang sama, tetapi tidak diperlukan aktor atau hak akses tambahan pada implementasi awal.

## 6.2 Identifikasi Use Case

Use case utama:

| ID | Use Case | Aktor |
|---|---|---|
| UC-01 | Menerima Pesanan | Kasir |
| UC-02 | Menghitung Biaya Laundry | Kasir |
| UC-03 | Melihat Antrean Pesanan | Kasir, Pegawai |
| UC-04 | Memproses Pesanan | Pegawai |
| UC-05 | Menandai Pesanan Selesai | Pegawai |
| UC-06 | Melihat Status Pesanan | Kasir, Pegawai |
| UC-07 | Menyiapkan Notifikasi WhatsApp | Kasir |
| UC-08 | Mengonfirmasi Pengambilan | Kasir |

Use Case Diagram harus memperlihatkan aktor di luar batas sistem dan use case di dalam batas sistem.

Perhitungan biaya dapat dimodelkan sebagai bagian dari proses penerimaan pesanan, sedangkan monitoring status dapat digunakan oleh kedua aktor.

## 6.3 Identifikasi Kandidat Class

Berdasarkan kebutuhan, kandidat class utama adalah:

| Class | Tanggung Jawab |
|---|---|
| `Pelanggan` | Menyimpan informasi pelanggan |
| `Pesanan` | Menyimpan informasi transaksi dan status |
| `Layanan` | Mendefinisikan layanan laundry |
| `CuciKering` | Menghitung biaya layanan cuci kering |
| `CuciSetrika` | Menghitung biaya layanan cuci setrika |
| `LaundryManager` | Mengelola antrean dan perubahan status |

Class antarmuka dan utilitas ditambahkan pada tahap perancangan untuk menghubungkan logika dengan tampilan.

---

# 7. PERANCANGAN OBJECT-ORIENTED DESIGN (OOD)

Tahap OOD berfokus pada penentuan class, atribut, method, hubungan antarclass, dan interaksi objek yang akan diimplementasikan.

## 7.1 Struktur Class

### A. Class `Pelanggan`

**Atribut:**
- `private String idPelanggan`
- `private String nama`
- `private String noWhatsapp`

**Method:**
- Constructor
- `getIdPelanggan()`
- `getNama()`
- `setNama()`
- `getNoWhatsapp()`
- `setNoWhatsapp()`

Data pelanggan menggunakan encapsulation agar atribut tidak dapat diubah langsung dari luar class.

Pada versi awal, satu objek pelanggan dibuat saat menerima pesanan. Tidak diperlukan fitur master pelanggan atau pencarian pelanggan lama.

### B. Abstract Class `Layanan`

**Atribut:**
- `protected String namaLayanan`
- `protected double hargaPerKg`

**Method:**
- Constructor
- `getNamaLayanan()`
- `getHargaPerKg()`
- `public abstract double hitungTotalBiaya(double beratKg)`
- `toString()`

Class ini menjadi superclass untuk jenis layanan laundry.

### C. Class `CuciKering`

**Inheritance:** `extends Layanan`

**Atribut:** Menggunakan atribut yang diwarisi dari `Layanan`.

**Method:**
- Constructor dengan harga Rp5.000/kg.
- Override `hitungTotalBiaya()`.

### D. Class `CuciSetrika`

**Inheritance:** `extends Layanan`

**Method:**
- Constructor dengan harga Rp7.000/kg.
- Override `hitungTotalBiaya()`.

### E. Class `Pesanan`

**Atribut:**
- `private String idPesanan`
- `private Pelanggan pelanggan`
- `private Layanan layanan`
- `private double beratKg`
- `private double totalBiaya`
- `private StatusPesanan status`
- `private LocalDateTime waktuMasuk`

**Method:**
- Constructor
- Getter seluruh atribut
- `lanjutkanStatus()`
- Method untuk menampilkan waktu dalam format `HH:mm - dd/MM/yyyy`

Ketika objek dibuat, total biaya dihitung melalui objek `Layanan` dan status ditetapkan menjadi `BELUM_MULAI`.

### F. Enum `StatusPesanan`

Berisi:
- `BELUM_MULAI`
- `DIPROSES`
- `SELESAI`
- `DIAMBIL`

Enum digunakan sebagai kumpulan nilai status yang tetap. Penggunaannya merupakan fitur pendukung Java yang sederhana, bukan konsep OOP tingkat lanjut yang harus menjadi fokus utama pembahasan.

### G. Class `LaundryManager`

Class ini bertanggung jawab atas pengelolaan pesanan.

**Atribut:**
- `private Queue<Pesanan> antrianFifo`
- `private List<Pesanan> daftarSemuaPesanan`
- `private static final int KAPASITAS_MESIN = 4`
- `private int counterOrder`
- Counter sederhana untuk ID pelanggan apabila diperlukan.

**Method:**
- `generateIdPesanan()`
- `tambahPesanan(Pesanan pesanan)`
- `prosesAntrianBerikutnya()`
- `tandaiSelesai(String idPesanan)`
- `konfirmasiDiambil(String idPesanan)`
- `getPesananByStatus(StatusPesanan status)`
- `getDaftarAntrianFifo()`
- `getJumlahSedangDiproses()`
- `getKapasitasMesin()`

Method harus memeriksa kondisi dan menolak perubahan yang tidak sesuai aturan.

### H. Interface `Notifiable`

**Method:**
- `void kirimNotifikasi(Pesanan pesanan) throws Exception`

Interface digunakan untuk mendefinisikan kontrak sederhana pengiriman notifikasi.

### I. Class `WhatsAppHelper`

**Implementasi:** `implements Notifiable`

**Method:**
- `formatNomorInternasional()`
- `buatPesanNotifikasi()`
- `kirimNotifikasi()`

Class ini bertanggung jawab membuat pesan dan membuka browser.

Interface tidak perlu diperluas menjadi sistem notifikasi kompleks.

## 7.2 Hubungan Antarclass

Hubungan utama yang harus ditampilkan pada Class Diagram:

1. `CuciKering` mewarisi `Layanan`.
2. `CuciSetrika` mewarisi `Layanan`.
3. `Pesanan` memiliki referensi ke `Pelanggan`.
4. `Pesanan` memiliki referensi ke `Layanan`.
5. `Pesanan` menggunakan `StatusPesanan`.
6. `LaundryManager` mengelola banyak objek `Pesanan`.
7. `WhatsAppHelper` mengimplementasikan `Notifiable`.
8. Class antarmuka berinteraksi dengan `LaundryManager`.

Gunakan simbol UML yang sesuai, terutama generalization untuk inheritance, realization untuk implementasi interface, dan association untuk hubungan antarobjek.

Jangan menambahkan inheritance apabila hubungan antarkelas sebenarnya bukan hubungan *is-a*.

## 7.3 Sequence Diagram

Minimal dibuat tiga Sequence Diagram utama.

**SD-01 — Penerimaan Pesanan**

Partisipan:
- Kasir
- `PanelInputPesanan`
- `Layanan`
- `Pesanan`
- `LaundryManager`

Urutan:
1. Kasir memasukkan data.
2. Panel memvalidasi data.
3. Panel meminta perhitungan biaya ke objek layanan.
4. Panel membuat objek pesanan.
5. Panel mengirimkan pesanan ke manager.
6. Manager menyimpan pesanan ke antrean.
7. Panel menampilkan hasil.

**SD-02 — Pemrosesan Antrean FIFO**

Partisipan:
- Pegawai
- `PanelMonitoringPesanan`
- `LaundryManager`
- `Pesanan`

Urutan:
1. Pegawai menekan tombol proses.
2. Panel memanggil manager.
3. Manager memeriksa antrean dan kapasitas.
4. Manager mengambil pesanan pertama.
5. Status pesanan diubah menjadi DIPROSES.
6. Panel memperbarui tabel.

**SD-03 — Penyelesaian dan Pengambilan**

Partisipan:
- Pegawai/Kasir
- `PanelMonitoringPesanan`
- `LaundryManager`
- `Pesanan`
- `WhatsAppHelper`

Urutan:
1. Pegawai memilih pesanan.
2. Manager mengubah status menjadi SELESAI.
3. Panel menampilkan pesanan siap diambil.
4. Kasir meminta pemberitahuan WhatsApp.
5. Helper membuat dan membuka tautan.
6. Kasir mengonfirmasi pengambilan.
7. Manager mengubah status menjadi DIAMBIL.

Sequence Diagram perlu memperlihatkan lifeline, message, activation bar jika diperlukan, dan alur alternatif ketika validasi gagal.

---

# 8. DESAIN ANTARMUKA PENGGUNA (UI)

## 8.1 Prinsip Desain

Antarmuka dirancang dengan prinsip:
- Sederhana dan mudah dipahami.
- Konsisten dalam penggunaan tombol dan label.
- Menampilkan informasi penting secara jelas.
- Meminimalkan langkah operasional pegawai.
- Menggunakan komponen standar Java Swing.
- Tidak membutuhkan library desain tambahan.

## 8.2 Jendela Utama

Aplikasi menggunakan `JFrame` sebagai jendela utama dan `JTabbedPane` untuk navigasi.

Dua tab utama:

1. **Terima Pesanan Baru**
2. **Antrean & Proses Cucian**

`MainFrame` menyediakan satu objek `LaundryManager` yang digunakan bersama oleh kedua panel.

## 8.3 Tab Terima Pesanan Baru

Komponen:

| Komponen | Jenis |
|---|---|
| Judul Halaman | `JLabel` |
| Nama Pelanggan | `JTextField` |
| Nomor WhatsApp | `JTextField` |
| Paket Layanan | `JComboBox<Layanan>` |
| Berat Cucian | `JTextField` |
| Estimasi Total | `JLabel` |
| Tombol Simpan | `JButton` |

Contoh tata letak:

**PENERIMAAN PESANAN LAUNDRY**

Nama Pelanggan: [________________]

Nomor WhatsApp: [________________]

Jenis Layanan: [Cuci Kering ▼]

Berat Cucian (kg): [________________]

**Estimasi Total: Rp0**

[Terima & Masukkan Antrean]

Label total biaya diperbarui saat pengguna mengubah berat atau memilih layanan.

## 8.4 Tab Antrean dan Proses Cucian

Bagian atas menampilkan:

**Kapasitas Mesin Aktif: 0 / 4**

Di bawahnya terdapat tiga bagian:

### Bagian A — Antrean Menunggu

Tabel menampilkan pesanan berstatus `BELUM_MULAI`.

Tombol:
**Proses Antrean Terdepan (FIFO)**

### Bagian B — Sedang Diproses

Tabel menampilkan pesanan berstatus `DIPROSES`.

Tombol:
**Tandai Selesai**

### Bagian C — Siap Diambil

Tabel menampilkan pesanan berstatus `SELESAI`.

Tombol:
- **Kirim Notifikasi WhatsApp**
- **Konfirmasi Sudah Diambil**

Tabel dapat ditampilkan secara vertikal menggunakan beberapa `JPanel` agar mudah dibaca.

## 8.5 Ketentuan Desain GUI

- Gunakan `JScrollPane` untuk tabel.
- Gunakan `DefaultTableModel` dengan sel tidak dapat diedit langsung.
- Gunakan `JOptionPane` untuk notifikasi dan peringatan.
- Gunakan padding sederhana menggunakan `EmptyBorder`.
- Gunakan ukuran huruf yang jelas dan konsisten.
- Gunakan tombol dengan label tindakan yang mudah dipahami.
- Hindari animasi, visualisasi kompleks, atau library UI tambahan.

---

# 9. STRUKTUR PROYEK JAVA

Gunakan pengelompokan file sederhana berdasarkan tanggung jawabnya, tanpa mewajibkan implementasi MVC formal.

```text
src/
└── com/
    └── laundry/
        ├── Main.java
        │
        ├── model/
        │   ├── StatusPesanan.java
        │   ├── Layanan.java
        │   ├── CuciKering.java
        │   ├── CuciSetrika.java
        │   ├── Pelanggan.java
        │   └── Pesanan.java
        │
        ├── service/
        │   └── LaundryManager.java
        │
        ├── util/
        │   ├── Notifiable.java
        │   └── WhatsAppHelper.java
        │
        └── ui/
            ├── MainFrame.java
            ├── PanelInputPesanan.java
            └── PanelMonitoringPesanan.java
```

Total terdapat **13 file Java**.

Tanggung jawabnya:

| Bagian | Fungsi |
|---|---|
| `model` | Menyimpan data dan perilaku objek |
| `service` | Mengelola proses operasional laundry |
| `util` | Menangani bantuan notifikasi |
| `ui` | Menampilkan serta menerima interaksi pengguna |
| `Main.java` | Menjalankan aplikasi |

Pengelompokan ini digunakan untuk mempermudah pembacaan kode, bukan untuk menerapkan arsitektur perangkat lunak yang kompleks.

---

# 10. KEBUTUHAN NONFUNGSIONAL

## NFR-01 — Kemudahan Penggunaan

Aplikasi harus dapat digunakan oleh pegawai yang memiliki pemahaman komputer dasar.

Setiap fungsi utama memiliki tombol dan label yang jelas.

## NFR-02 — Kemudahan Pemeliharaan

Setiap class memiliki tugas yang jelas dan tidak mencampurkan terlalu banyak tanggung jawab.

Penamaan class, atribut, dan method menggunakan istilah yang mudah dipahami.

## NFR-03 — Portabilitas

Aplikasi menggunakan Java Standard Library agar dapat dijalankan pada sistem operasi yang mendukung JDK.

Tidak diperlukan penginstalan framework atau library eksternal.

## NFR-04 — Keandalan

Kesalahan input tidak boleh menyebabkan aplikasi berhenti secara tiba-tiba.

Kesalahan pengguna ditangani melalui validasi dan pesan peringatan.

## NFR-05 — Kinerja

Penambahan pesanan, perubahan status, dan pembaruan tabel harus memberikan respons yang wajar untuk penggunaan skala kecil pada satu komputer.

Pengujian awal dilakukan menggunakan puluhan hingga sekitar 100 pesanan selama satu sesi aplikasi.

## NFR-06 — Penyimpanan Data

Seluruh data disimpan sementara dalam `ArrayList` dan `Queue`.

**Batasan penting:** Semua data akan hilang ketika aplikasi ditutup atau dihentikan. Hal ini diterima untuk versi pembelajaran, tetapi harus dijelaskan kepada pengguna aplikasi dan dalam laporan.

## NFR-07 — Keamanan Dasar

- Atribut penting menggunakan akses `private`.
- Data input divalidasi.
- Nomor WhatsApp digunakan hanya untuk kebutuhan pencatatan dan pembuatan tautan.
- Aplikasi tidak menyimpan kata sandi atau token layanan eksternal.
- Data pelanggan tidak perlu dicetak ke konsol untuk aktivitas normal.

---

# 11. PENERAPAN KONSEP OOP

Implementasi harus menunjukkan hubungan langsung antara konsep dalam modul dan kode aplikasi.

| Konsep | Penerapan |
|---|---|
| Class | `Pelanggan`, `Pesanan`, `LaundryManager` |
| Object | Objek pelanggan, pesanan, dan layanan |
| Attribute | Nama pelanggan, berat cucian, harga, status |
| Method | `hitungTotalBiaya()`, `tambahPesanan()`, `lanjutkanStatus()` |
| Constructor | Inisialisasi pelanggan, pesanan, dan layanan |
| Encapsulation | Atribut `private` dengan getter dan setter |
| Inheritance | `CuciKering extends Layanan` |
| Abstract Class | Class dasar `Layanan` |
| Abstract Method | `hitungTotalBiaya()` |
| Method Overriding | Implementasi perhitungan pada setiap jenis layanan |
| Polymorphism | Referensi `Layanan` yang menunjuk objek `CuciKering` atau `CuciSetrika` |
| Interface | `Notifiable` |
| Implements | `WhatsAppHelper implements Notifiable` |
| Object Interaction | Panel memanggil manager; manager mengelola pesanan |

Penggunaan konsep tersebut harus memiliki fungsi nyata dalam program, bukan sekadar dibuat untuk memenuhi daftar konsep.

Struktur `Queue`, `ArrayList`, dan `LinkedList` digunakan untuk membantu pengelolaan data, tetapi tidak perlu menjadi fokus pembahasan utama dibandingkan konsep OOP.

---

# 12. SKENARIO PENGUJIAN

Pengujian menggunakan metode fungsional sederhana dengan membandingkan input, tindakan, dan hasil yang diharapkan.

| ID | Skenario | Hasil yang Diharapkan |
|---|---|---|
| TC-01 | Input pesanan valid | Pesanan berhasil dibuat |
| TC-02 | Nama pelanggan kosong | Muncul peringatan |
| TC-03 | Nomor WhatsApp kosong | Muncul peringatan |
| TC-04 | Nomor WhatsApp tidak valid | Pesanan ditolak |
| TC-05 | Berat bukan angka | Muncul peringatan |
| TC-06 | Berat bernilai 0 atau negatif | Pesanan ditolak |
| TC-07 | Pilih Cuci Kering 2 kg | Total Rp10.000 |
| TC-08 | Pilih Cuci Setrika 2,5 kg | Total Rp17.500 |
| TC-09 | Tambahkan tiga pesanan berurutan | Urutan FIFO tetap |
| TC-10 | Proses antrean pertama | Pesanan paling depan berubah menjadi DIPROSES |
| TC-11 | Proses ketika antrean kosong | Muncul informasi |
| TC-12 | Proses ketika empat pesanan aktif | Proses tambahan ditolak |
| TC-13 | Selesaikan pesanan kedua sebelum pertama | Perubahan berhasil |
| TC-14 | Tandai selesai tanpa memilih baris | Muncul peringatan |
| TC-15 | Kirim WhatsApp untuk pesanan selesai | Tautan dan pesan disiapkan |
| TC-16 | Browser tidak tersedia | Tautan dan pesan dapat disalin |
| TC-17 | Konfirmasi pengambilan | Status menjadi DIAMBIL |
| TC-18 | Perubahan status tidak sesuai urutan | Perubahan ditolak |
| TC-19 | Berpindah tab monitoring | Tabel menampilkan data terbaru |
| TC-20 | Tutup dan jalankan ulang aplikasi | Data sebelumnya tidak tersedia sesuai batasan penyimpanan sementara |

## 12.1 Pengujian FIFO dan Kapasitas

Contoh skenario:

Lima pesanan masuk secara berurutan:

`ORD-001 → ORD-002 → ORD-003 → ORD-004 → ORD-005`

Ketika tombol proses ditekan sebanyak empat kali:

- ORD-001 menjadi DIPROSES.
- ORD-002 menjadi DIPROSES.
- ORD-003 menjadi DIPROSES.
- ORD-004 menjadi DIPROSES.
- ORD-005 tetap BELUM_MULAI.

Kapasitas menunjukkan **4 / 4**.

Ketika ORD-003 selesai, kapasitas menjadi **3 / 4**.

Tombol proses berikutnya harus memulai ORD-005.

Skenario tersebut membuktikan bahwa FIFO berlaku untuk urutan memulai pengerjaan, sedangkan penyelesaian tidak harus mengikuti urutan kedatangan.

---

# 13. KRITERIA KEBERHASILAN PRODUK

Pengembangan dianggap memenuhi spesifikasi apabila:

1. Aplikasi dapat dijalankan dari `Main.java`.
2. Seluruh file Java dapat dikompilasi tanpa kesalahan sintaks.
3. Formulir mampu menerima pesanan yang valid.
4. Validasi mencegah input yang tidak sesuai.
5. Total biaya dihitung berdasarkan jenis layanan dan berat cucian.
6. ID pesanan dibuat otomatis dan unik selama satu sesi.
7. Antrean menerapkan prinsip FIFO.
8. Maksimal empat pesanan dapat diproses bersamaan.
9. Pesanan dapat diselesaikan tanpa mengikuti urutan FIFO.
10. Seluruh perubahan status berjalan sesuai aturan.
11. Data pada tabel diperbarui setelah tindakan pengguna.
12. Tautan WhatsApp dapat dibuat dengan informasi pesanan yang benar.
13. Seluruh fungsi utama dapat dijelaskan melalui konsep OOP.
14. Use Case Diagram, Class Diagram, dan Sequence Diagram sesuai dengan implementasi.
15. Skenario pengujian utama menghasilkan hasil yang diharapkan.

---

# 14. KELUARAN DAN DOKUMENTASI PROYEK

Hasil akhir pengembangan terdiri atas:

## 14.1 Aplikasi

Source code Java lengkap beserta antarmuka Java Swing.

## 14.2 Dokumentasi Analisis (OOA)

- Deskripsi kebutuhan.
- Identifikasi aktor.
- Identifikasi use case.
- Use Case Diagram.
- Identifikasi class dan hubungan.
- Class Diagram.

## 14.3 Dokumentasi Desain (OOD)

- Penjelasan interaksi objek.
- Sequence Diagram penerimaan pesanan.
- Sequence Diagram pemrosesan antrean.
- Sequence Diagram penyelesaian dan pengambilan.

## 14.4 Dokumentasi Implementasi

- Penjelasan struktur folder.
- Penjelasan class dan method.
- Penerapan konsep OOP.
- Dokumentasi antarmuka.
- Petunjuk menjalankan aplikasi.
- Hasil pengujian.

## 14.5 Struktur Laporan

Susunan laporan yang disarankan:

**BAB I — PENDAHULUAN**
- Latar belakang.
- Rumusan masalah.
- Tujuan.
- Manfaat aplikasi.

**BAB II — ANALISIS SISTEM (OOA)**
- Gambaran sistem.
- Identifikasi kebutuhan.
- Identifikasi aktor.
- Use Case Diagram.
- Class Diagram.

**BAB III — PERANCANGAN SISTEM (OOD)**
- Desain interaksi objek.
- Sequence Diagram.
- Penjelasan alur proses.

**BAB IV — IMPLEMENTASI (OOP)**
- Teknologi yang digunakan.
- Struktur class.
- Implementasi konsep OOP.
- Tampilan aplikasi.
- Pengujian fungsional.

**BAB V — PENUTUP**
- Kesimpulan.
- Saran pengembangan.

Susunan ini merupakan rekomendasi penyajian laporan, bukan ketentuan format bab yang dinyatakan dalam rubrik.

---

# 15. KESESUAIAN DENGAN RUBRIK PENILAIAN

Berdasarkan dokumen Sistem Penilaian Tugas Projek, distribusi penilaian adalah:

| Komponen | Bobot |
|---|---:|
| Pendahuluan | 5% |
| OOA — Use Case Diagram | 5% |
| OOA — Class Diagram | 10% |
| OOD — Sequence Diagram | 10% |
| OOP — Aplikasi | 15% |
| Penutup | 5% |
| Penilaian Individual — Ketepatan Jawaban | 50% |
| **Total** | **100%** |

Oleh karena itu, pengembangan harus memprioritaskan ketepatan rancangan, fungsi aplikasi, dan kemampuan menjelaskan program.

Setiap anggota kelompok sebaiknya memahami:
- Fungsi setiap class.
- Alasan penggunaan abstract class.
- Perbedaan inheritance dan interface.
- Cara polymorphism bekerja.
- Cara objek saling berinteraksi.
- Alur FIFO.
- Mekanisme perubahan status.
- Hubungan antara diagram dan kode.

Tidak perlu menambahkan fitur kompleks jika fitur tersebut justru menyulitkan pemahaman atau tidak berkaitan dengan persyaratan.

---

# 16. BATASAN DAN RISIKO PROYEK

## 16.1 Data Tidak Permanen

Karena tidak menggunakan database, semua pesanan hilang ketika aplikasi ditutup.

**Penanganan:** Jelaskan batasan ini pada laporan dan petunjuk penggunaan. Penyimpanan permanen dapat menjadi saran pengembangan berikutnya.

## 16.2 Notifikasi Tidak Dikirim Otomatis

Aplikasi hanya membuka tautan WhatsApp.

**Penanganan:** Gunakan istilah "Siapkan/Buka Notifikasi WhatsApp" pada penjelasan fitur agar tidak menimbulkan kesan bahwa sistem mengirim pesan tanpa tindakan pengguna.

## 16.3 Tidak Ada Simulasi Mesin Fisik

Aplikasi hanya menghitung jumlah pesanan yang sedang diproses.

**Penanganan:** Jelaskan bahwa kapasitas empat mesin merupakan aturan operasional, bukan pengendalian perangkat keras.

## 16.4 Program Digunakan pada Satu Komputer

Tidak terdapat sinkronisasi antarpengguna atau antarkomputer.

**Penanganan:** Aplikasi difokuskan untuk penggunaan lokal sesuai lingkup project.

## 16.5 Risiko Ketidaksesuaian Diagram dan Kode

Perubahan implementasi dapat menyebabkan UML tidak lagi sesuai.

**Penanganan:** Setelah kode selesai, periksa kembali seluruh diagram dan perbarui bagian yang berbeda.

---

# 17. TAHAPAN PENGEMBANGAN

Pengembangan dilaksanakan secara bertahap agar mudah dipahami dan diuji.

### Tahap 1 — Analisis Kebutuhan

Menentukan aktor, fitur, alur proses, dan aturan sistem.

**Hasil:** Daftar kebutuhan dan Use Case Diagram.

### Tahap 2 — Perancangan Class

Mengidentifikasi class, atribut, method, inheritance, interface, dan hubungan antarclass.

**Hasil:** Class Diagram.

### Tahap 3 — Perancangan Interaksi

Menggambarkan interaksi aktor dan objek untuk proses utama.

**Hasil:** Sequence Diagram.

### Tahap 4 — Implementasi Model

Membuat class `Pelanggan`, `Pesanan`, `Layanan`, `CuciKering`, `CuciSetrika`, dan `StatusPesanan`.

**Hasil:** Objek dan logika dasar aplikasi.

### Tahap 5 — Implementasi Pengelolaan Pesanan

Membuat `LaundryManager`, antrean FIFO, pembatasan kapasitas, dan perubahan status.

**Hasil:** Proses operasional aplikasi.

### Tahap 6 — Implementasi GUI

Membuat jendela utama, formulir penerimaan, dan monitoring pesanan.

**Hasil:** Antarmuka Java Swing yang dapat digunakan.

### Tahap 7 — Implementasi WhatsApp

Membuat helper dan interface untuk menyiapkan pemberitahuan.

**Hasil:** Fitur pembukaan tautan WhatsApp.

### Tahap 8 — Pengujian

Menguji penerimaan, perhitungan, antrean, kapasitas, perubahan status, dan notifikasi.

**Hasil:** Catatan pengujian dan perbaikan kesalahan.

### Tahap 9 — Dokumentasi dan Presentasi

Menyusun laporan akhir, memperbarui UML, dan mempersiapkan penjelasan setiap fitur serta konsep OOP.

**Hasil:** Aplikasi dan laporan yang siap dikumpulkan.

---

# 18. DEFINITION OF DONE

Project dinyatakan selesai apabila seluruh kondisi berikut terpenuhi:

- [ ] Semua kebutuhan utama dalam PRD telah diimplementasikan.
- [ ] Seluruh source code tersedia dan tersusun rapi.
- [ ] Program dapat dikompilasi dan dijalankan.
- [ ] Penerimaan pesanan berfungsi.
- [ ] Perhitungan biaya sesuai tarif.
- [ ] Antrean FIFO berjalan dengan benar.
- [ ] Kapasitas maksimal empat pesanan dipatuhi.
- [ ] Status pesanan berubah sesuai urutan.
- [ ] Penyelesaian pesanan dapat dilakukan secara independen.
- [ ] Monitoring menampilkan data sesuai status.
- [ ] Fitur tautan WhatsApp berfungsi atau menyediakan alternatif manual.
- [ ] Validasi dan penanganan kesalahan telah diuji.
- [ ] Konsep OOP diterapkan dengan benar.
- [ ] Use Case Diagram sesuai kebutuhan.
- [ ] Class Diagram sesuai struktur program.
- [ ] Sequence Diagram sesuai interaksi aktual.
- [ ] Laporan selesai disusun.
- [ ] Seluruh bagian program dapat dijelaskan saat presentasi.

---

# 19. KESIMPULAN

Sistem Manajemen Operasional UMKM Laundry dirancang sebagai aplikasi desktop sederhana menggunakan Java dan Java Swing untuk mendukung penerimaan pesanan, perhitungan biaya, pengelolaan antrean, monitoring pengerjaan, serta pengambilan cucian.

Sistem menerapkan konsep OOP melalui penggunaan class dan object, encapsulation, inheritance, abstract class, interface, serta polymorphism. Pengembangan mengikuti pendekatan OOA, OOD, dan OOP sehingga terdapat hubungan yang jelas antara kebutuhan sistem, rancangan UML, dan implementasi program.

PRD ini menjadi acuan utama dalam pembuatan aplikasi, pengujian, penyusunan laporan, dan persiapan presentasi tugas.

**Prinsip utama pengembangan adalah menghasilkan aplikasi yang benar, sederhana, fungsional, dan profesional sesuai tingkat materi perkuliahan, bukan aplikasi dengan teknologi atau fitur yang terlalu kompleks.**