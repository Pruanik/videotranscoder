Программа выполняющая транскодирование видео для подготовки к стримингу по протоколу DASH.

Процесс происходит в несколько этапов:
1. Перекодирование видео дорожки в H.264 (под DASH формат) [ffmpeg]
2. Перекодирование аудио дорожки в ACC (под DASH формат) [ffmpeg]
3. Сведение обеих дорожек в один mp4 контейнер  [MP4Box]
4. Разбиение на файла на блоки и создание манифеста. [MP4Box]

Сейчас при работе с видео частью, комп кушает +5Gb оперативки.

Планы на будущее:
- Доделать шаги 3 и 4.
- Возможно сделать шаги 1 и 2 в двух потоках (если не сожрет всю оперативку)
- Добавить Jetty/Tomcat для вывода веб формы добавления видео файлов
- Добавить JDBC для хранения информации о видео и манифест файлах
- Добавление апплета для показа видео (dash.js)
- Добавление сервлета для передачи манифеста и сгенерированных блоков

При работе пользовался ресурсами:
https://github.com/bramp/ffmpeg-cli-wrapper
http://itmultimedia.ru/sozdanie-kontenta-mpeg-dash-s-pomoshhyu-mp4box-i-ffmpeg/
https://ru.smedialink.com/razrabotka/ffmpeg-i-ego-vrapper-dlya-java/
