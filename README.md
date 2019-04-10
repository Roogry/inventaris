peminjaman = call missed = red

pengembalian = replay = indigo

petugas = person = blue

peminjam = people = green

jenis = label = yellow

ruang = check box = pink

report = pie chart = orange

>**getReport**
```sql
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
)AS dto
```

>**kembalikan peminjaman after update**
```sql
BEGIN
	DECLARE v_jumlah, v_id INT DEFAULT 0;
    
    SELECT jumlah FROM detail_pinjam WHERE id_peminjaman = NEW.id_peminjaman INTO v_jumlah;
    SELECT id_inventaris FROM detail_pinjam WHERE id_peminjaman = NEW.id_peminjaman INTO v_id;
    
    IF NEW.status_peminjaman = 2 THEN
    UPDATE inventaris SET jumlah = jumlah + v_jumlah WHERE id_inventaris = v_id;
    END IF;
END
```

>**kurangi detail after insert**
```sql
BEGIN
	DECLARE v_jumlah INT DEFAULT 0;
    
    SELECT jumlah FROM inventaris WHERE inventaris.id_inventaris = NEW.id_inventaris INTO v_jumlah;
    
    IF v_jumlah >= NEW.jumlah THEN
    
    UPDATE inventaris SET jumlah = jumlah-NEW.jumlah WHERE inventaris.id_inventaris = NEW.id_inventaris;
    
    ELSE
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Jumlah yang dimasukkan melebihi stok yang tersedia';
    END IF;
END
```
