package ru.Darvin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class YourOwnHashMap<K, V> {
    private static final int INITIAL_CAPACITY = 10240; //начальная емкость корзины (больше быстрее)
    private LinkedList<Entry<K, V>>[] buckets; /* массив списков для хранения элементов хеш-таблицы (мы храним связный список LinkedList который
    будет хранить связный список ключ значения с одинаковым хеш-кодом)*/

    private int size = 0; //(количество элементов в массиве)

    //через конструктор для каждой корзины создаем LinkedList
    public YourOwnHashMap() {
        buckets = new LinkedList[INITIAL_CAPACITY]; //создаем массив ссылок на LinkedList
        for (int i = 0; i < INITIAL_CAPACITY; i++) { //циклом инициализируем каждую корзину пустым списком LinkedList
            buckets[i] = new LinkedList<>();
        }
    }

    // определение хэш кода для ключа
    private int hash(K key) {
        return Math.abs(key.hashCode() % INITIAL_CAPACITY);
    }

    //добавление записи в таблицу или обновлении при том же ключе
    public void put(K key, V value) {
        int bucketIndex = hash(key); //вычисляем индекс корзины для данного ключа
        LinkedList<Entry<K, V>> bucket = buckets[bucketIndex]; //получаем связный список для вычисленного индекса
        for (Entry<K, V> entry : bucket) { //перебираем все записи в списке
            if (entry.key.equals(key)) { //если ключ уже существует в списке обновляем значение и выходим
                entry.value = value;
                return;
            }
        }
        bucket.add(new Entry<>(key, value)); //если ключ не найден добавляем новую запись
        size++; // увеличиваем счетчик количества элементов
    }

    // помещает в коллекцию новый объект с ключом k и значением v, если в коллекции еще нет элемента с подобным ключом
    public V putIfAbsent(K key, V value) {
        V existingValue = get(key);
        if (existingValue == null) {
            put(key, value);
            return null;
        } else {
            return existingValue;
        }
    }

    //возвращает значение по ключу
    public V get(K key) {
        int bucketIndex = hash(key); //вычисляем индекс корзины для данного ключа
        LinkedList<Entry<K, V>> bucket = buckets[bucketIndex]; //получаем связный список для вычисленного индекса
        for (Entry<K, V> entry : bucket) { //перебираем все записи в списке
            if (entry.key.equals(key)) { //если ключ найден, возвращаем значение.
                return entry.value;
            }
        }
        return null; //если не найден возвращаем null
    }

    // возвращает значение объекта, ключ которого равен k. Если такого элемента не окажется, то возвращается значение defaultValue
    public V getOrDefault(K key, V defaultValue) {
        V value = get(key); // поиск по ключу
        return value != null ? value : defaultValue; // возвращает дефолтное значение если ключ равен null
    }

    //удаляет значение по ключу
    public void remove(K key) {
        int bucketIndex = hash(key); //вычисляем индекс корзины для данного ключа
        LinkedList<Entry<K, V>> bucket = buckets[bucketIndex]; //получаем связный список для вычисленного индекса
        if (bucket.removeIf(entry -> entry.key.equals(key))) { // если ключ найден, то удаляем из списка и уменьшаем счетчик
            size--;
        }
    }

    //проверка записи с заданным ключом
    public boolean containsKey(K key) {
        int bucketIndex = hash(key); //вычисляем индекс корзины для данного ключа
        LinkedList<Entry<K, V>> bucket = buckets[bucketIndex]; //получаем связный список для вычисленного индекса
        for (Entry<K, V> entry : bucket) { //переберем все записи в списке
            if (entry.key.equals(key)) { //если ключ найден, возвращаем значение true.
                return true;
            }
        }
        return false; //если не найден возвращаем false
    }

    //возвращает true, если коллекция содержит значение v
    public boolean containsValue(V value) {
        for (LinkedList<Entry<K, V>> bucket : buckets) { //переберем все корзины
            for (Entry<K, V> entry : bucket) { //перебираем все записи в каждой корзине
                if (entry.value.equals(value)) { //если значение найдено, то возвращаем true
                    return true;
                }
            }
        }
        return false;
    }

    //возвращение количества записей в таблице
    public int size() {
        return size;
    }

    //возвращает true, если коллекция пуста
    public boolean isEmpty() {
        return size == 0;
    }

    // Метод для очистки всей хеш-таблицы
    public void clear() {
        for (int i = 0; i < INITIAL_CAPACITY; i++) { //перебираем все записи в списке
            buckets[i].clear(); //удаляет все элементы из списка.
        }
        size = 0; //сбрасываем счетчик до 0
    }

    //возвращает набор элементов коллекции
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entrySet = new HashSet<>();
        for (LinkedList<Entry<K, V>> bucket : buckets) {
            entrySet.addAll(bucket);
        }
        return entrySet;
    }

    // возвращает набор всех значений отображения
    public Collection<V> values() {
        List<V> values = new ArrayList<>();
        for (LinkedList<Entry<K, V>> bucket : buckets) {
            for (Entry<K, V> entry : bucket) {
                values.add(entry.value);
            }
        }
        return values;
    }

    // добавляет в коллекцию все объекты из отображения map
    public void putAll(YourOwnHashMap<? extends K, ? extends V> map) {
        for (Entry<? extends K, ? extends V> entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    //возвращает true, если коллекция идентична коллекции, передаваемой через параметр obj
    public boolean equals(Object obj) {
        if (this == obj) return true; //сравниваем не одинаковые ли это объекты
        if (obj == null || getClass() != obj.getClass()) return false; //проверяем что объект не равен нулю и что его класс совпадает
        YourOwnHashMap<?, ?> that = (YourOwnHashMap<?, ?>) obj; // приводим объект к типу YourOwnHashMap и сравниваем их наборы записей
        return this.entrySet().equals(that.entrySet());
    }



    public static class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Entry<?, ?> entry = (Entry<?, ?>) obj;
            return key.equals(entry.key) && value.equals(entry.value);
        }

        @Override
        public int hashCode() {
            return key.hashCode() ^ value.hashCode();
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}
