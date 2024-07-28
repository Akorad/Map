package ru.Darvin;

import java.util.Collection;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        YourOwnHashMap<Integer, String> map = new YourOwnHashMap<>();
        YourOwnHashMap<Integer, String> map2 = new YourOwnHashMap<>();
        YourOwnHashMap<Integer, String> map3 = new YourOwnHashMap<>();
        YourOwnHashMap<Integer, String> map4 = new YourOwnHashMap<>();

        map.put(1,"Apple");
        map.put(2,"Banana");
        map.put(3,"Orange");
        for (int i = 4; i < 10000; i++) {
            map.put(i,"Apple"+i);
        }
        System.out.println("Значение ключа 5040: " + map.get(5040));
        System.out.println("Значение ключа 50400 через getOrDefault: " + map.getOrDefault(50400, "Значение не найдено"));
        System.out.println("Содержит значение «Banana»: " + map.containsValue("Banana"));
        System.out.println("Есть ли ключ 5040: " + map.containsKey(5040));
        System.out.println("Добовление значения с помощью  putIfAbsent" + map.putIfAbsent(50400, "Apple50400"));
        System.out.println("Значение ключа 50400 через getOrDefault: " + map.getOrDefault(50400, "Значение не найдено"));
//        Collection<String> values = map.values();
//        for (String value : values) {
//            System.out.println("Значение черз Collection "+value);
//        }
        map.remove(3);
        System.out.println("Размер map после удаления «Orange»: " + map.size());
//        System.out.println("Вывод всех значений:");
//        for (YourOwnHashMap.Entry<Integer,String> entry : map.entrySet()) {
//            System.out.println("Ключ: " + entry.getKey() + ", Значение: " + entry.getValue());
//        }
        map.clear();
        System.out.println("Размер map после выполнения метода clear " + map.size());
        System.out.println("Проверка map на пустоту " + map.isEmpty());
        map2.put(1,"one");
        map2.put(2,"two");
        map2.put(3,"three");
        map3.put(1,"one");
        map3.put(2,"two");
        map3.put(3,"three");
        System.out.println("Map одинаковы " + map2.equals(map3));
        map4.put(4,"four");
        map4.put(5,"five");
        map3.putAll(map4);
        System.out.println("Проверка новых ключей в map3 " + map3.get(4));

    }
}
