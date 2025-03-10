## Refleksi DailyTaskManager

Program ini adalah program untuk menampilkan tugas yang saya buat menggunakan: Array untuk menyimpan task dengan jumlah elemen terbatas -hanya 5 saja. Memudahkan manipulasi data sederhana seperti update, menandai selesai, dan undo tugas yang dihapus. Linked List untuk daftar tugas yang dinamis. Cocok karena ukuran data yang tidak tetap dan memungkinkan penambahan atau penghapusan elemen dengan mudah. Stack untuk fitur undo. Tugas yang dihapus atau ditandai selesai disimpan di stack, sehingga dapat dikembalikan dengan mudah sesuai konsep LIFO (Last In First Out).

Saya membuat program ini dengan menambahkan beberapa inovasi seperti penambahan penginputan nama untuk user dan juga menyapa user sesuai waktunya -Credit untuk Gerrard dan Keane. Saya juga menambahkan asci art untuk menambah nilai estetika pada output serta beberapa warna. Dari segi fitur saya menambahkan fitur untuk mengurutkan berdasarkan deadline dan prioritas dengan memindahkan data dari linked list ke ArrayList dan sorting dengan comparator, undo task di linked list dengan menggunakan stack, serta fitur search task.

Alur program sendiri berawal dari user yang menginput namanya dan dapat memilih berbagai fitur yang telah tersedia di menu, seperti melihat, menambah, menghapus, mengurutkan, dan mencari tugas, juga fitur undo untuk membatalkan penghapusan atau penyelesaian tugas, dan tugas-tugas juga dapat ditampilkan dengan mencakup informasi tenggat dan prioritas.

Dalam membuat code program ini saya sedikit bingung dengan cara untuk menambahkan waktu agar dapat menyapa sesuai waktu kepada user sehingga saya meminta bantuan chat gpt untuk itu dan saya dibantu dengan menggunakan local time now -seperti yang dilakukan keane juga, serta saya menggunakan chronounit yang saya dapatkan dari chat gpt untuk membantu menghitung selisih waktu. Selain itu saya juga mendapatkan beberapa bantuan referensi dari output Vivian dan Gerrard.