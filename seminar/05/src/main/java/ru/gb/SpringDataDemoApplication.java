package ru.gb;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.gb.model.User;
import ru.gb.repository.UserRepository;

import java.util.List;

@SpringBootApplication
public class SpringDataDemoApplication {

	/**
	 * JDBC JPA
	 * JDBC (Java DataBase Connectivity) - библиотека внутри Java для работы с базами данных.
	 * Driver, Connection, Statement, ...
	 *
	 * JPA (Jakarta Persistence API) - набор соглашений по работе с реляционными моделями.
	 * Основная идея - "замапить" вашу DB-модель на java-классы и работать с строками таблиц как с объектами.
	 * JPA - это не реализация, а протокол (api, спецификация).
	 * Hibernate - это одна из реализаций JPA (еще одна реализация - EclipseLink)
	 *
	 * spring-data-jdbc - набор готовых инструментов для взаимодействия с базой данных.
	 * По сути, обворачивает стандратный JDBC и предоставляет удобные интерфейсы для настройки и взамодействия с БД.
	 *
	 * spring-data-commons
	 * spring-data-jpa - набор готовых инструментов для работы с JPA.
	 * spring-data-mongo
	 * spring-data-redis
	 *
	 * // [controller] -> [service] -> [repository]
	 * ReaderService, BookService, IssueService
	 * UltraService(ReaderService, BookService, IssueService)
	 * getReaderByIssue
	 * God-Object
	 * SOLID (I)
	 *
	 */

	@SneakyThrows
	public static void main(String[] args) {
//		RestTemplate restTemplate = new RestTemplate();
//
//		WebClient webClient = WebClient.create("http://localhost:8180");
//		User user = webClient.get()
//			.uri("/api/users/1")
//			.retrieve()
//			.bodyToMono(User.class)
//			.block();

		ConfigurableApplicationContext context = SpringApplication.run(SpringDataDemoApplication.class, args);
		UserRepository userRepository = context.getBean(UserRepository.class);

		for (int i = 15; i <= 25; i++) {
			User user = new User();
			user.setId((long) i);
			user.setName("User #" + i);
			user.setAge(i);
			userRepository.save(user);
		}

//		List<User> objects = userRepository.myquery2();
//		String stop = "";

		System.out.println(userRepository.myquery("User #15"));

		// [25 24 23] [22 21 20] [19
		PageRequest request = PageRequest.of(2, 3, Sort.Direction.DESC, "id");
		Page<User> page = userRepository.findByAgeGreaterThan(request, 10);
		System.out.println(page.getContent());
		System.out.println(page.getTotalPages());
		System.out.println(page.getTotalElements());
		System.out.println(page.getNumber());


		JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
//		jdbcTemplate.execute(statement -> {
//
//		});

//
////		JdbcTemplate
//
//		jdbcTemplate.execute("create table users(id bigint, name varchar(512))");
//		jdbcTemplate.execute("insert into users(id, name) values(1, 'Igor')");
//
//		List<User> users = jdbcTemplate.query("select id, name from users",
//			(rs, rowNum) -> new User(rs.getLong("id"), rs.getString("name")));
//
//
//		DataSource dataSource = context.getBean(DataSource.class);
//		try (Connection connection = dataSource.getConnection()) {
//			try (Statement statement = connection.createStatement()) {
//				statement.execute("create table users(id bigint, name varchar(512))");
//			}
//
//			try (Statement statement = connection.createStatement()) {
//				statement.execute("insert into users(id, name) values(1, 'Igor')");
//			}
//
//			try (Statement statement = connection.createStatement()) {
//				ResultSet resultSet = statement.executeQuery("select id, name from users");
//				while (resultSet.next()) {
//					System.out.println(resultSet.getInt("id"));
//					System.out.println(resultSet.getString("name"));
//				}
//			}
//		}
	}

//	@Data
//	@AllArgsConstructor
//	static class User {
//		long id;
//		String name;
//	}

}
