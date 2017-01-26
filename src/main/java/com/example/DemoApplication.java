package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		//test();
        mapTest();
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
