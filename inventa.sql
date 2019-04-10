-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: 08 Apr 2019 pada 14.16
-- Versi Server: 10.1.25-MariaDB
-- PHP Version: 7.1.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `inventa`
--

DELIMITER $$
--
-- Prosedur
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `getReport` (IN `dfrom` VARCHAR(10), IN `dto` VARCHAR(10))  NO SQL
SELECT(
	SELECT COUNT(*) FROM peminjaman WHERE tanggal_pinjam BETWEEN dfrom AND dto
) AS total_terpinjam,
(
 	SELECT COUNT(*) FROM peminjaman WHERE tanggal_pinjam BETWEEN dfrom AND dto AND status_peminjaman = 1   
) AS masih_terpinjam,
(
 	SELECT COUNT(*) FROM peminjaman WHERE tanggal_pinjam BETWEEN dfrom AND dto AND status_peminjaman = 2   
) AS sudah_dikembalikan,
(
 	SELECT COUNT(*) FROM petugas WHERE id_level != 1
) AS petugas,
(
 	SELECT COUNT(*) FROM inventaris
) AS inventaris,
(
    SELECT dto
)AS dto$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Struktur dari tabel `detail_pinjam`
--

CREATE TABLE `detail_pinjam` (
  `id_detail_pinjam` int(5) NOT NULL,
  `id_inventaris` int(5) NOT NULL,
  `jumlah` int(5) NOT NULL,
  `id_peminjaman` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `detail_pinjam`
--

INSERT INTO `detail_pinjam` (`id_detail_pinjam`, `id_inventaris`, `jumlah`, `id_peminjaman`) VALUES
(1, 1, 1, 4),
(2, 1, 1, 6),
(5, 6, 50, 9),
(7, 1, 1, 11);

--
-- Trigger `detail_pinjam`
--
DELIMITER $$
CREATE TRIGGER `kurangkanInvent` AFTER INSERT ON `detail_pinjam` FOR EACH ROW BEGIN
	DECLARE v_jumlah INT DEFAULT 0;
    
    SELECT jumlah FROM inventaris WHERE inventaris.id_inventaris = NEW.id_inventaris INTO v_jumlah;
    
    IF v_jumlah >= NEW.jumlah THEN
    
    UPDATE inventaris SET jumlah = jumlah-NEW.jumlah WHERE inventaris.id_inventaris = NEW.id_inventaris;
    
    ELSE
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Jumlah yang dimasukkan melebihi stok yang tersedia';
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktur dari tabel `inventaris`
--

CREATE TABLE `inventaris` (
  `id_inventaris` int(5) NOT NULL,
  `nama` varchar(20) NOT NULL,
  `kondisi` tinyint(1) NOT NULL COMMENT '1=baru, 2=baik, 3=kurang baik, 4=rusak',
  `keterangan` text COMMENT 'Pakai Habis, Barang pinjam',
  `jumlah` int(5) NOT NULL,
  `id_jenis` int(5) NOT NULL,
  `tanggal_register` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id_ruang` int(5) NOT NULL,
  `kode_inventaris` varchar(15) NOT NULL,
  `id_petugas` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `inventaris`
--

INSERT INTO `inventaris` (`id_inventaris`, `nama`, `kondisi`, `keterangan`, `jumlah`, `id_jenis`, `tanggal_register`, `id_ruang`, `kode_inventaris`, `id_petugas`) VALUES
(1, 'Laptop', 2, 'Barang Pakai Habis', 38, 2, '2019-04-04 08:46:04', 13, 'IS0001', 1),
(6, 'Mouse', 2, 'Barang Pinjam', 50, 2, '2019-04-06 03:20:11', 15, 'IS0006', 1),
(8, 'Kursi', 1, 'Barang Pakai Habis', 100, 4, '2019-04-08 05:59:50', 2, 'IS0007', 1);

-- --------------------------------------------------------

--
-- Struktur dari tabel `jenis`
--

CREATE TABLE `jenis` (
  `id_jenis` int(5) NOT NULL,
  `nama_jenis` varchar(20) NOT NULL,
  `kode_jenis` varchar(15) NOT NULL,
  `keterangan` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `jenis`
--

INSERT INTO `jenis` (`id_jenis`, `nama_jenis`, `kode_jenis`, `keterangan`) VALUES
(1, 'Alat Tulis', 'JN0001', ''),
(2, 'Elektronik', 'JN0002', ''),
(3, 'Perkakas', 'JN0003', ''),
(4, 'Mebel', 'JN0004', ''),
(5, 'Alat Kebersihan', 'JN0005', ''),
(6, 'Kendaraan', 'JN0006', '');

-- --------------------------------------------------------

--
-- Struktur dari tabel `level`
--

CREATE TABLE `level` (
  `id_level` int(5) NOT NULL,
  `nama_level` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `level`
--

INSERT INTO `level` (`id_level`, `nama_level`) VALUES
(1, 'Admin'),
(2, 'Operator'),
(3, 'Guru'),
(4, 'Pegawai'),
(5, 'Siswa');

-- --------------------------------------------------------

--
-- Struktur dari tabel `peminjam`
--

CREATE TABLE `peminjam` (
  `id_peminjam` int(5) NOT NULL,
  `nama_peminjam` varchar(20) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `alamat` varchar(50) NOT NULL,
  `status_peminjam` tinyint(1) NOT NULL COMMENT '1=Guru, 2=Pegawai, 3=Siswa'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `peminjam`
--

INSERT INTO `peminjam` (`id_peminjam`, `nama_peminjam`, `username`, `password`, `alamat`, `status_peminjam`) VALUES
(1, 'Jodie Mantra', 'jo', 'pass', 'Jl. Perum Bina Permai II / No.52', 1),
(2, 'Venny Sukma', 've', 'pass', 'Jl. Ayani No. 129', 3),
(7, 'Cikal Taruna', 'ci', 'pass', '', 2);

-- --------------------------------------------------------

--
-- Struktur dari tabel `peminjaman`
--

CREATE TABLE `peminjaman` (
  `id_peminjaman` int(5) NOT NULL,
  `tanggal_pinjam` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `tanggal_kembali` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status_peminjaman` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1=dipinjam, 2=dikembalikan',
  `id_peminjam` int(5) NOT NULL,
  `kode_peminjaman` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `peminjaman`
--

INSERT INTO `peminjaman` (`id_peminjaman`, `tanggal_pinjam`, `tanggal_kembali`, `status_peminjaman`, `id_peminjam`, `kode_peminjaman`) VALUES
(1, '2019-04-04 14:46:27', '0000-00-00 00:00:00', 1, 1, 'PN14985630'),
(2, '2019-04-04 14:46:44', '0000-00-00 00:00:00', 1, 1, 'PN37548296'),
(3, '2019-04-04 14:50:18', '2019-04-04 15:02:01', 2, 1, 'PN09142573'),
(4, '2019-04-04 14:50:46', '2019-04-04 14:56:50', 2, 1, 'PN21438957'),
(6, '2019-04-05 15:39:52', '2019-04-05 15:40:38', 2, 1, 'PN03814762'),
(7, '2019-04-06 01:06:00', '2019-04-06 02:18:29', 2, 1, 'PN23859061'),
(8, '2019-04-06 02:44:29', '2019-04-06 04:39:05', 2, 1, 'PN29716584'),
(9, '2019-04-06 03:20:52', '2019-04-06 03:21:14', 2, 1, 'PN39601785'),
(10, '2019-04-06 04:40:01', '0000-00-00 00:00:00', 1, 1, 'PN87350419'),
(11, '2019-04-06 04:40:07', '2019-04-08 05:58:16', 2, 1, 'PN32041859'),
(12, '2019-04-07 15:04:36', '0000-00-00 00:00:00', 1, 1, 'PN74216839'),
(13, '2019-04-07 15:05:15', '2019-04-07 15:05:30', 2, 2, 'PN14903785'),
(14, '2019-04-07 15:05:38', '0000-00-00 00:00:00', 1, 2, 'PN76821953'),
(15, '2019-04-08 05:57:12', '0000-00-00 00:00:00', 1, 1, 'PN62938140');

--
-- Trigger `peminjaman`
--
DELIMITER $$
CREATE TRIGGER `kembalikanInvent` AFTER UPDATE ON `peminjaman` FOR EACH ROW BEGIN
	DECLARE v_jumlah, v_id INT DEFAULT 0;
    
    SELECT jumlah FROM detail_pinjam WHERE id_peminjaman = NEW.id_peminjaman INTO v_jumlah;
    SELECT id_inventaris FROM detail_pinjam WHERE id_peminjaman = NEW.id_peminjaman INTO v_id;
    
    IF NEW.status_peminjaman = 2 THEN
    UPDATE inventaris SET jumlah = jumlah + v_jumlah WHERE id_inventaris = v_id;
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Struktur dari tabel `petugas`
--

CREATE TABLE `petugas` (
  `id_petugas` int(5) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `nama_petugas` varchar(20) NOT NULL,
  `id_level` int(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `petugas`
--

INSERT INTO `petugas` (`id_petugas`, `username`, `password`, `nama_petugas`, `id_level`) VALUES
(1, 'admin', 'pass', 'Admin Roogry', 1),
(2, 'Ojo', 'pass', 'Operator Jodie', 2),
(10, 'Oge', 'pass', 'Operator Genadi', 2),
(11, 'Ove', 'pass', 'Operator Venny', 2);

-- --------------------------------------------------------

--
-- Struktur dari tabel `ruang`
--

CREATE TABLE `ruang` (
  `id_ruang` int(5) NOT NULL,
  `nama_ruang` varchar(20) DEFAULT NULL,
  `kode_ruang` varchar(15) DEFAULT NULL,
  `keterangan` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `ruang`
--

INSERT INTO `ruang` (`id_ruang`, `nama_ruang`, `kode_ruang`, `keterangan`) VALUES
(1, 'Ruang Guru', 'RG0001', 'Gedung A Lantai 1'),
(2, 'Gudang', 'RG0002', 'Gedung A Lantai 1'),
(3, 'Koperasi', 'RG0003', 'Gedung E Lantai 1'),
(4, 'Ruang Tata Usaha', 'RG0004', 'Gedung A Lantai 2'),
(5, 'Perpustakaan', 'RG0005', 'Gedung D Lantai 1'),
(6, 'Ruang Osis', 'RG0006', 'Gedung B Lantai 1'),
(7, 'Lab Bangunan', 'RG0007', 'Gedung C & D Lantai 1, dan Gedung E Lantai 2'),
(8, 'Bengkel Bangunan', 'RG0008', 'Gedung F Lantai 1'),
(9, 'Bengkel Mesin', 'RG0009', 'Gedung J Lantai 1'),
(10, 'Lab Elektro', 'RG0010', 'Gedung G Lantai 2'),
(11, 'Lab Audio & Video', 'RG0011', 'Gedung H Lantai 2'),
(12, 'Bengkel Sepeda Motor', 'RG0012', 'Gedung G Lantai 1'),
(13, 'Lab RPL', 'RG0013', 'Gedung D Lantai 2'),
(14, 'Lab TKJ', 'RG0014', 'Gedung D Lantai 3'),
(15, 'Lab MM', 'RG0015', 'Gedung J Lantai 3');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `detail_pinjam`
--
ALTER TABLE `detail_pinjam`
  ADD PRIMARY KEY (`id_detail_pinjam`),
  ADD KEY `id_inventaris` (`id_inventaris`),
  ADD KEY `id_peminjaman` (`id_peminjaman`);

--
-- Indexes for table `inventaris`
--
ALTER TABLE `inventaris`
  ADD PRIMARY KEY (`id_inventaris`),
  ADD KEY `id_jenis` (`id_jenis`),
  ADD KEY `id_ruang` (`id_ruang`),
  ADD KEY `id_petugas` (`id_petugas`);

--
-- Indexes for table `jenis`
--
ALTER TABLE `jenis`
  ADD PRIMARY KEY (`id_jenis`);

--
-- Indexes for table `level`
--
ALTER TABLE `level`
  ADD PRIMARY KEY (`id_level`);

--
-- Indexes for table `peminjam`
--
ALTER TABLE `peminjam`
  ADD PRIMARY KEY (`id_peminjam`);

--
-- Indexes for table `peminjaman`
--
ALTER TABLE `peminjaman`
  ADD PRIMARY KEY (`id_peminjaman`),
  ADD KEY `id_pegawai` (`id_peminjam`);

--
-- Indexes for table `petugas`
--
ALTER TABLE `petugas`
  ADD PRIMARY KEY (`id_petugas`),
  ADD KEY `id_level` (`id_level`);

--
-- Indexes for table `ruang`
--
ALTER TABLE `ruang`
  ADD PRIMARY KEY (`id_ruang`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `detail_pinjam`
--
ALTER TABLE `detail_pinjam`
  MODIFY `id_detail_pinjam` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT for table `inventaris`
--
ALTER TABLE `inventaris`
  MODIFY `id_inventaris` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT for table `jenis`
--
ALTER TABLE `jenis`
  MODIFY `id_jenis` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `level`
--
ALTER TABLE `level`
  MODIFY `id_level` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `peminjam`
--
ALTER TABLE `peminjam`
  MODIFY `id_peminjam` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `peminjaman`
--
ALTER TABLE `peminjaman`
  MODIFY `id_peminjaman` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT for table `petugas`
--
ALTER TABLE `petugas`
  MODIFY `id_petugas` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
--
-- AUTO_INCREMENT for table `ruang`
--
ALTER TABLE `ruang`
  MODIFY `id_ruang` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `detail_pinjam`
--
ALTER TABLE `detail_pinjam`
  ADD CONSTRAINT `detail_pinjam_ibfk_1` FOREIGN KEY (`id_inventaris`) REFERENCES `inventaris` (`id_inventaris`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `detail_pinjam_ibfk_2` FOREIGN KEY (`id_peminjaman`) REFERENCES `peminjaman` (`id_peminjaman`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `inventaris`
--
ALTER TABLE `inventaris`
  ADD CONSTRAINT `inventaris_ibfk_1` FOREIGN KEY (`id_jenis`) REFERENCES `jenis` (`id_jenis`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `inventaris_ibfk_2` FOREIGN KEY (`id_ruang`) REFERENCES `ruang` (`id_ruang`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `inventaris_ibfk_3` FOREIGN KEY (`id_petugas`) REFERENCES `petugas` (`id_petugas`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `peminjaman`
--
ALTER TABLE `peminjaman`
  ADD CONSTRAINT `peminjaman_ibfk_1` FOREIGN KEY (`id_peminjam`) REFERENCES `peminjam` (`id_peminjam`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `petugas`
--
ALTER TABLE `petugas`
  ADD CONSTRAINT `petugas_ibfk_1` FOREIGN KEY (`id_level`) REFERENCES `level` (`id_level`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
