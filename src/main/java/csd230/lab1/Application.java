package csd230.lab1;


import csd230.lab1.entities.BookEntity;
import csd230.lab1.repositories.BookEntityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	private final BookEntityRepository bookRepository;


	public Application(BookEntityRepository bookRepository) {
		this.bookRepository = bookRepository;
	}


	public void run(String... args) {
		if (bookRepository.count() > 0) {
			return;
		}


		BookEntity first = new BookEntity();
		first.setTitle("Spring MVC Basics");
		first.setPrice(29.99);
		first.setCopies(5);
		first.setAuthor("Course Example");
		bookRepository.save(first);


		BookEntity second = new BookEntity();
		second.setTitle("Thymeleaf in Practice");
		second.setPrice(34.99);
		second.setCopies(4);
		second.setAuthor("Course Example");
		bookRepository.save(second);
	}
}



