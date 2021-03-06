****Программа выполняющая транскодирование видео для подготовки к стримингу по протоколу DASH.

Процесс происходит в несколько этапов:
0. Получить информацию о входном файле для того чтобы учесть битрейт, разрешение, частоту кадра и т.д.
1. Перекодирование видео дорожки в H.264 (под DASH формат) [ffmpeg]
2. Перекодирование аудио дорожки в ACC (под DASH формат) [ffmpeg]
3. Сведение обеих дорожек в mp4 контейнеры  [MP4Box]
4. Разбиение файлов на блоки и создание манифеста. [MP4Box]

Планы на будущее:
- Возможно сделать шаги 1 и 2 в двух потоках (если не сожрет всю оперативку)
- Добавить Jetty/Tomcat для вывода веб формы добавления видео файлов
- Добавить JDBC для хранения информации о видео и манифест файлах
- Добавление апплета для показа видео (dash.js)
- Добавление сервлета для передачи манифеста и сгенерированных блоков

При работе пользовался ресурсами:

*[Библиотека обертка для FFMpeg](https://github.com/bramp/ffmpeg-cli-wrapper)
*[Статья об использовании bash команд](http://itmultimedia.ru/sozdanie-kontenta-mpeg-dash-s-pomoshhyu-mp4box-i-ffmpeg/)
*[Статья об опыте использования Wrapper FFMpeg](https://ru.smedialink.com/razrabotka/ffmpeg-i-ego-vrapper-dlya-java/)


Шпаргалка по командам для терминала:

ffmpeg -i input.mp4 -vcodec libx264 -preset slow -x264opts fps=24:bitrate=2000:pass=1:vbv-maxrate=4000:vbv-bufsize=9000:keyint=96:min-keyint=96:scenecut=0:no-scenecut -vf scale=1280:720 input_video_b2000k.264

ffmpeg -i input.mp4 -acodec libfdk_aac -b:a 128000 input_audio_b128k.aac

MP4Box -add input_video_b2000k.264 input_video.mp4
MP4Box -add input_audio_b128k.aac input_audio.mp4

MP4Box -dash 4000 -frag 4000 -rap -segment-name %s/seg_ -url-template -out final.mpd input_video.mp4 input_audio.mp4

Пометки: 
- Сейчас при работе с видео частью, комп кушает +5Gb оперативки.
- кодек libfdk_aac не обязателен, можно использовать простой acc который уже идет в стандартной сборке ffmpeg.
- файл весом 162Мб - 25:30 после конвертации стал весить 410 МБ, надо поиграть с битрейтом
- dash.js хорошо работает с получившемся файлом