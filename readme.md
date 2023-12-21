[Here is the Refence of This Project ](https://www.youtube.com/watch?v=VUxJrb4ghlo&list=PL4O1nDpoa5KQ7eAF4uJa5Tj51YXoBq1Zh)

<h3>Notes</h3>
aggregate = transaction boundary for certain subject  
commandHandler is should be idempotent (operasi atau fungsi dapat diaplikasikan beberapa kali tanpa menghasilkan efek yang berbeda)

list of domains
- conference
- conference family
- conference edition

in this project we will took  conference as our main aggregate

<h3>Deskripsi Singkat</h3>
ini project spring boot yang menerapkan CQRS dengan evensourcing menggunakan AXON framework dan jpa sebagai eventstore nya.
project ini di rancang untuk bisa scaleable / suport microservices.
modules:
1. command : sisi command bisa jalan sendiri
2. query : sisi query bisa jalan sendiri
3. core : library bersama command n query
4. main : untuk menjalanakan project sebagai aplikasi monolith


<h3>todo list</h3>
- locking db/ distributed locking
- reque message yang gagal karena duplikat/tertentu
- snapshot agregate
- reset agregate
- recreate projection
- testable