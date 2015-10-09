## Бесконечная последовательность
Возьмём бесконечную цифровую последовательность, образованную склеиванием последовательных положительных чисел: S = 123456789101112131415...
Определите первое вхождение заданной последовательности A в бесконечной последовательности S (нумерация начинается с 1).

## Запуск
```
javac SequenceMatcher.java
java SequenceMatcher input.txt
```
## Формат входных данных
Входные данные состоят из образцов, по одному в каждой строке, и берутся из текстового файла, имя которого указывается в аргументах командной строки.

## Выходные данные
Для соответствующего образца выводится номер позиции первого вхождения в бесконечной последовательности

## Пример
Входные данные:
```
123
1123
023
```
Выходные данные:
```
1
3382
582
```
## Описание алгоритма
Для решения данной задачи прекрасно подходит алгоритм Ахо-Корасик. Он ищет вхождения всех образцов в тексте. Вся последовательность не хранится, а генерируется только один ее член.
