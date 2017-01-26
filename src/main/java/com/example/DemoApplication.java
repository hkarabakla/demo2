package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileFilter;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		//test();
        //mapTest();
        //reductionTest();
        //collectorTest();

		DateOperations d = new DateOperations();
		d.testDate();
	}

	private static void collectorTest() {
        Person p1 = new Person(20, "Huseyin");
        Person p2 = new Person(19, "Ali");
        Person p3 = new Person(25, "Hasan");
        Person p4 = new Person(34, "Mehmet");
        Person p6 = new Person(34, "Keramettin");
        Person p5 = new Person(34, "Mustafa");

        List<Person> people = new ArrayList<>();
        people.add(p1);
        people.add(p2);
        people.add(p3);
        people.add(p4);
        people.add(p6);
        people.add(p5);

        Predicate<Person> ageTest = person -> person.getAge() > 20;
        Predicate<String> nameTest = name -> name.length() > 5;

        String names = people
                .stream()
                .filter(ageTest)
                .map(Person::getName)
                .filter(nameTest)
                .collect(Collectors.joining(" , "));

        List<String> nameList = people
                .stream()
                .filter(ageTest)
                .map(Person::getName)
                .filter(nameTest)
                .collect(Collectors.toList());

        //Fully person map grouped by age
        Map<Integer, List<Person>> peopleMap = people
                .stream()
                .filter(ageTest)
                .collect(Collectors.groupingBy(Person::getAge));

        //Map grouped by age and counting information
        Map<Integer, Long> countingMap =people
                .stream()
                .filter(ageTest)
                .collect(Collectors.groupingBy(
                        Person::getAge,
                        Collectors.counting()));

        //Map grouped by age and only names popped up as list
        Map<Integer, List<String>> nameMapList = people
                .stream()
                .filter(ageTest)
                .collect(Collectors.groupingBy(
                        Person::getAge,
                        Collectors.mapping(
                                Person::getName,
                                Collectors.toList())));

        //Map grouped by age and only names popped up as comma separated
        Map<Integer, String> nameMapListComma = people
                .stream()
                .filter(ageTest)
                .collect(Collectors.groupingBy(
                        Person::getAge,
                        Collectors.mapping(
                                Person::getName,
                                Collectors.joining(" , ")
                        )
                ));

        //Map grouped by age and only names popped up as ordered list
        Map<Integer, Set<String>> nameMapListOrdered = people
                .stream()
                .filter(ageTest)
                .collect(Collectors.groupingBy(
                        Person::getAge,
                        Collectors.mapping(
                                Person::getName,
                                Collectors.toCollection(TreeSet::new)
                        )
                ));

        //Map grouped by name length and only names popped up as list
        Map<Integer, List<String>> nameMap = people
                .stream()
                .filter(ageTest)
                .map(Person::getName)
                .collect(Collectors.groupingBy(o -> o.length()));

        System.out.println("Names : " + names);
        System.out.println("Names : " + nameList);
        System.out.println("People : " + peopleMap);
        System.out.println("People : " + nameMapList);
        System.out.println("People : " + nameMapListOrdered);
        System.out.println("People : " + nameMapListComma);
        System.out.println("People : " + countingMap);
        System.out.println("Name Map : " + nameMap);
    }

	private static void reductionTest() {
	    List<Integer> agesList = Arrays.asList(3,5,7,9);

	    Optional<Integer> sum = agesList
                .stream()
                .filter(integer -> integer < 5)
                .reduce(Integer::max);

	    System.out.println("Sum : " + sum);

    }

	private static void mapTest() {

	    List<Integer> list1 = Arrays.asList(1,2,3,4,5,6,7);
        List<Integer> list2 = Arrays.asList(5,6,7);
        List<Integer> list3 = Arrays.asList(2,3,4,5,6);

        List<List<Integer>> listOfLists = Arrays.asList(list1, list2, list3);

        System.out.println(listOfLists);

        Consumer<Integer> sizePrinter = System.out::println;

        listOfLists
                .stream()
                .map(integers -> integers.size())
                .forEach(sizePrinter);

        Function<List<?>, Integer> sizePicker = List::size;

        listOfLists
                .stream()
                .map(sizePicker)
                .forEach(sizePrinter);

        Function<List<Integer>, Stream<Integer>> flatMapper = l -> l.stream();

        listOfLists
                .stream()
                .flatMap(flatMapper)
                .forEach(sizePrinter);

    }

	private static void test() {

		FileFilter fileFilter = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".yml");
			}
		};

		File currentDir = new File("D:\\docker-files");
		File[] files = currentDir.listFiles(fileFilter);
		System.out.println("Files length " + files.length);

		FileFilter lambdaFilter = (File file) -> file.getName().endsWith(".yml");
		files = currentDir.listFiles(fileFilter);
		System.out.println("Files length " + files.length);

		MyFunctionalInterface myFilter = (File file) -> {
			System.out.println("Files length " + file.getName());
			return true;
		};

		myFilter.accept(currentDir);

		Consumer<String> stringConsumer = s -> System.out.print(s);
		Consumer<String> secondConsumer = stringConsumer.andThen(s -> System.out.print(" (" + s +") -> printed."));
		Consumer<String> thirdConsumer = secondConsumer.andThen(s -> System.out.println(" (" + s +") -> printx."));

		List<String> names = Arrays.asList("Name1", "Name2", "Name3");
		printList(names, thirdConsumer);

		Predicate<String> endsWith1 = s -> s.endsWith("1");
		Predicate<String> endsWith3 = s -> s.endsWith("3");

		List<String> filteredList = filterList(names, endsWith1);
		//filteredList.forEach(System.out::println);

		filteredList = filterList(names, endsWith1.or(endsWith3.negate()));
		filteredList.forEach(System.out::println);

		Person p1 = new Person(20, "Person-1");
		Person p2 = new Person(19, "Person-2");
		Person p3 = new Person(25, "Person-3");
		Person p4 = new Person(10, "Person-4");
		Person p5 = new Person(34, "Person-5");

		List<Person> people = new ArrayList<>();
		people.add(p1);
		people.add(p2);
		people.add(p3);
		people.add(p4);
		people.add(p5);

		Stream<Person> personStream = people.stream();
		Predicate<Person> olderThan20 = person -> person.getAge() > 20;
		Predicate<Person> youngerThan20 = person -> person.getAge() < 20;
		Consumer<Person> namePrinterConsumer = person -> System.out.println(person.getName());
		personStream
				.peek(namePrinterConsumer)
				.filter(olderThan20.and(youngerThan20))
				.forEach(namePrinterConsumer);

		System.out.print("Test commit");
	}

	public static List<String> filterList(List<String> unfilteredList, Predicate<String> predicate) {
		List<String> filteredList = new ArrayList<>();
		for (String s: unfilteredList) {
			if(predicate.test(s)) {
				filteredList.add(s);
			}
		}

		return filteredList;
	}

	public static void printList(List<String> list, Consumer<String> consumer) {
		for (String i : list) {
			consumer.accept(i);
		}
	}
}
