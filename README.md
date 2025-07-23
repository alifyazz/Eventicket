# Even Ticket

Eventicket adalah aplikasi web untuk pemesanan tiket acara (event ticketing) yang dirancang untuk memudahkan pengguna dalam menemukan, memilih, dan membeli tiket event secara online. Aplikasi ini mendukung skenario pengguna terdaftar maupun guest, serta menyediakan antarmuka sederhana untuk admin mengelola event.

## Fitur Utama

* **Daftar Event**: Menampilkan katalog acara dengan informasi tanggal, lokasi, harga, dan deskripsi.
* **Pencarian & Filter**: Memungkinkan pengguna mencari event berdasarkan nama, kategori, atau rentang tanggal.
* **Detail Event**: Halaman khusus menampilkan informasi lengkap dan galeri gambar acara.
* **Registrasi & Login**: Pengguna dapat membuat akun baru atau login untuk riwayat pemesanan.
* **Checkout & Pembayaran**: Proses pemesanan tiket dengan keranjang belanja, pilihan guest checkout, dan integrasi mock payment.
* **Dashboard Admin**: Panel backend untuk admin menambah, mengubah, atau menghapus data event.

## Kelebihan

* **User-Friendly**: Antarmuka responsif dengan navigasi intuitif.
* **Arsitektur MVC**: Kode terstruktur menggunakan pola Model-View-Controller untuk pemisahan logika.
* **Mudah Dikelola**: Konfigurasi berbasis Maven memudahkan manajemen dependensi dan build.
* **Skalabilitas**: Desain modular memudahkan penambahan fitur baru.

## Teknologi yang Digunakan

* **Backend**: Java 11+, Spring Boot (atau Java Servlets)
* **Frontend**: HTML5, CSS3, JavaScript
* **Build Tool**: Maven

## Cara Menjalankan Aplikasi

1. **Prasyarat**:

   * Java JDK 11 atau lebih baru
   * Maven 3.6+
2. **Clone Repository**:

   ```bash
   git clone https://github.com/alifyazz/Eventicket.git
   cd Eventicket
   ```
3. **Build & Install**:

   ```bash
   mvn clean install
   ```
4. **Jalankan Aplikasi**:

   * Dengan Maven:

     ```bash
     mvn spring-boot:run
     ```
   * Atau menjalankan JAR:

     ```bash
     java -jar target/Eventicket-1.0-SNAPSHOT.jar
     ```
5. **Akses Web**: Buka browser dan kunjungi `http://localhost:8080` untuk melihat aplikasi.

---

Semoga membantu! Jika ada pertanyaan lebih lanjut atau fitur tambahan yang diinginkan, silakan buat issue di repo ini.
