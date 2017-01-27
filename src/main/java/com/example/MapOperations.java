package com.example;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by z003rv2s on 27.01.2017.
 */
public class MapOperations {




    public void testMap() {

        Person p1 = new Person(20, "Huseyin");
        Person p2 = new Person(19, "Ali");
        Person p3 = new Person(25, "Hasan");
        Person p4 = new Person(34, "Mehmet");
        Person p6 = new Person(34, "Keramettin");
        Person p5 = new Person(34, "Mustafa");
        Person p7 = new Person(10, "Galip");
        Person p8 = new Person(19, "Omer");
        Person p9 = new Person(25, "Murat");


        List<Person> people = new ArrayList<>();
        people.add(p1);
        people.add(p2);
        people.add(p3);
        people.add(p4);
        people.add(p5);
        people.add(p7);
        people.add(p8);
        people.add(p9);
        people.add(p6);

        Map<Integer, List<Person>> mappedList1 = people.subList(0, 5).stream()
                .collect(Collectors.groupingBy(Person::getAge));

        Map<Integer, List<Person>> mappedList2 = people.subList(5, people.size()).stream()
                .collect(Collectors.groupingBy(Person::getAge));

        System.out.println("Map1 : *******************");
        printMap(mappedList1);
        System.out.println("Map2 : *******************");
        printMap(mappedList2);

        mappedList2.entrySet().stream()
                .forEach(
                        entry ->
                                mappedList1.merge(
                                    entry.getKey(),
                                    entry.getValue(),
                                    (l1, l2) -> {      //l1: list1 value of mappedList1, l2: list2 value of mappedList2
                                        l1.addAll(l2);
                                        return l1;
                                    }
                ));

        System.out.println("Map1 merged : *******************");
        printMap(mappedList1);

        testBiMap();
    }

    private void printMap(Map<Integer, List<Person>> map){

        map.keySet().stream()
                .forEach(key -> System.out.println(key + " : " + map.get(key)));
    }

    private void printMapS(Map<String, List<PersonWithGender>> map){

        map.keySet().stream()
                .forEach(key -> System.out.println(key + " : " + map.get(key)));
    }

    private void printBiMap(Map<Integer, Map<String, List<PersonWithGender>>> bimap){

        bimap.keySet().stream()
                .forEach(key -> {
                    System.out.println();
                    System.out.println(key + " -> ");
                    printMapS(bimap.get(key));
                });
    }

    public void testBiMap() {
        PersonWithGender p1 = new PersonWithGender(20, "Huseyin", "M");
        PersonWithGender p2 = new PersonWithGender(19, "Ali", "M");
        PersonWithGender p3 = new PersonWithGender(25, "Hasan", "M");
        PersonWithGender p4 = new PersonWithGender(34, "Mehmet", "M");
        PersonWithGender p6 = new PersonWithGender(34, "Zeynep", "F");
        PersonWithGender p5 = new PersonWithGender(34, "Mustafa", "M");
        PersonWithGender p7 = new PersonWithGender(10, "Galip", "M");
        PersonWithGender p8 = new PersonWithGender(19, "Meltem", "F");
        PersonWithGender p9 = new PersonWithGender(25, "Azize", "F");


        List<PersonWithGender> people = new ArrayList<>();
        people.add(p1);
        people.add(p2);
        people.add(p3);
        people.add(p4);
        people.add(p5);
        people.add(p7);
        people.add(p8);
        people.add(p9);
        people.add(p6);

        Map<Integer, Map<String, List<PersonWithGender>>> bimap = new HashMap<>();

        people.forEach(
                person ->
                        bimap.computeIfAbsent(
                                person.getAge(),
                                HashMap::new
                        ).merge(
                                person.getGender(),
                                new ArrayList<>(Arrays.asList(person)),
                                (l1, l2) -> {
                                    l1.addAll(l2);
                                    return l1;
                                }
                        )

        );

        System.out.println("Bi Map Content *******************");
        printBiMap(bimap);
    }
}
