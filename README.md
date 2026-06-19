# Devam Takip Sistemi

## Proje Açıklaması

Bu proje Java Swing ve MySQL kullanılarak geliştirilmiş bir Devam Takip Sistemidir.

Sistem sayesinde:

* Kullanıcı girişi yapılabilir.
* Öğrenci ekleme, listeleme ve silme işlemleri yapılabilir.
* Ders ekleme, listeleme ve silme işlemleri yapılabilir.
* Yoklama kayıtları tutulabilir.
* Devamsızlık raporları oluşturulabilir.

## Kullanılan Teknolojiler

* Java
* Java Swing
* MySQL
* JDBC
* IntelliJ IDEA

## Veritabanı Tabloları

### users

* id
* username
* password
* role

### students

* id
* student_no
* name
* surname
* department

### courses

* id
* course_code
* course_name

### attendance

* id
* student_id
* course_id
* attendance_date
* status

## Sistem Özellikleri

### Kullanıcı Girişi

Kullanıcı adı ve şifre kontrolü yapılmaktadır.

### Öğrenci Yönetimi

* Öğrenci ekleme
* Öğrenci listeleme
* Öğrenci silme

### Ders Yönetimi

* Ders ekleme
* Ders listeleme
* Ders silme

### Yoklama Yönetimi

* Öğrenci seçme
* Ders seçme
* Tarih seçme
* Geldi / Gelmedi bilgisi kaydetme

### Devamsızlık Raporu

Attendance tablosundaki "Gelmedi" kayıtları sayılarak devamsızlık raporu oluşturulmaktadır.

## Veritabanı Bağlantısı

Veritabanı bağlantısı JDBC kullanılarak gerçekleştirilmiştir.

## SQL Dosyası eklendi
Hocam MySQL verileri GitHub'a gitmediği için database.sql dosyası oluşturdum. Bu dosya çalıştırıldığında veritabanı, tablolar ve admin kullanıcı otomatik oluşturuluyor.
Kullanıcı adı : Admin
Şifre :1234

## Geliştirici

Furkan Efe Aslan
