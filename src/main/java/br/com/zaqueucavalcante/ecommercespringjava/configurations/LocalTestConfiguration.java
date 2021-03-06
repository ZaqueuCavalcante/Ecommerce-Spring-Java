package br.com.zaqueucavalcante.ecommercespringjava.configurations;

import br.com.zaqueucavalcante.ecommercespringjava.entities.addresses.Address;
import br.com.zaqueucavalcante.ecommercespringjava.entities.addresses.City;
import br.com.zaqueucavalcante.ecommercespringjava.entities.addresses.State;
import br.com.zaqueucavalcante.ecommercespringjava.entities.clients.Client;
import br.com.zaqueucavalcante.ecommercespringjava.entities.clients.ClientType;
import br.com.zaqueucavalcante.ecommercespringjava.entities.orders.Order;
import br.com.zaqueucavalcante.ecommercespringjava.entities.orders.OrderItem;
import br.com.zaqueucavalcante.ecommercespringjava.entities.payments.Boleto;
import br.com.zaqueucavalcante.ecommercespringjava.entities.payments.CreditCard;
import br.com.zaqueucavalcante.ecommercespringjava.entities.payments.Payment;
import br.com.zaqueucavalcante.ecommercespringjava.entities.products.Category;
import br.com.zaqueucavalcante.ecommercespringjava.entities.products.Product;
import br.com.zaqueucavalcante.ecommercespringjava.entities.users.UserProfile;
import br.com.zaqueucavalcante.ecommercespringjava.repositories.*;
import br.com.zaqueucavalcante.ecommercespringjava.services.EmailService;
import br.com.zaqueucavalcante.ecommercespringjava.services.MockEmailService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Arrays;

@Configuration
@Profile("test")
public class LocalTestConfiguration implements CommandLineRunner {

	private final ClientRepository clientRepository;
	private final OrderRepository orderRepository;
	private final PaymentRepository paymentRepository;
	private final CategoryRepository categoryRepository;
	private final ProductRepository productRepository;
	private final OrderItemRepository orderItemRepository;
	private final StateRepository stateRepository;
	private final CityRepository cityRepository;
	private final AddressRepository addressRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public LocalTestConfiguration(ClientRepository clientRepository, OrderRepository orderRepository,
								  PaymentRepository paymentRepository, CategoryRepository categoryRepository,
								  ProductRepository productRepository, OrderItemRepository orderItemRepository,
								  StateRepository stateRepository, CityRepository cityRepository,
								  AddressRepository addressRepository, BCryptPasswordEncoder passwordEncoder) {
		this.clientRepository = clientRepository;
		this.orderRepository = orderRepository;
		this.paymentRepository = paymentRepository;
		this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
		this.orderItemRepository = orderItemRepository;
		this.stateRepository = stateRepository;
		this.cityRepository = cityRepository;
		this.addressRepository = addressRepository;
		this.passwordEncoder = passwordEncoder;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public void run(String... args) throws Exception {
		Category electronics = new Category(null, "Electronics");
		Category books = new Category(null, "Books");
		Category computers = new Category(null, "Computers");
		Category movies = new Category(null, "Movies");

		Product p1 = new Product(null, "The Lord of the Rings", "Lorem ipsum dolor sit amet, consectetur.", "image_url", 90.50);
		Product p2 = new Product(null, "Smart TV", "Nulla eu imperdiet purus. Maecenas ante.", "image_url", 2190.00);
		Product p3 = new Product(null, "Macbook Pro", "Nam eleifend maximus tortor, at mollis.", "image_url", 1250.00);
		Product p4 = new Product(null, "PC Gamer", "Donec aliquet odio ac rhoncus cursus.", "image_url", 1200.50);
		Product p5 = new Product(null, "Rails for Dummies", "Cras fringilla convallis sem vel faucibus.", "image_url", 100.00);
		Product p6 = new Product(null, "Tenet", "Nolan best film.", "image_url", 50.00);

		electronics.addProducts(Arrays.asList(p2, p3, p4));
		books.addProducts(Arrays.asList(p1, p5));
		computers.addProducts(Arrays.asList(p3, p4));
		movies.addProduct(p6);

		categoryRepository.saveAll(Arrays.asList(electronics, books, computers, movies));
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6));
		categoryRepository.flush();
		productRepository.flush();

		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
		Client maria = new Client(null, "Maria Brown", "maria@gmail.com", ClientType.PHYSICAL_PERSON, "5138438486");
		Client alex = new Client(null, "Alex Green", "alex@gmail.com", ClientType.LEGAL_PERSON, "24358412351");
		maria.setPassword(passwordEncoder.encode("123"));
		alex.setPassword(passwordEncoder.encode("321"));

		maria.addPhones(Arrays.asList("384638468", "53468468"));
		alex.addPhones(Arrays.asList("538468444", "68468484"));
		
		alex.addProfile(UserProfile.ADMIN);

		State MG = new State(null, "Minas Gerais");
		State PE = new State(null, "Pernambuco");
		State SP = new State(null, "São Paulo");

		City recife = new City(null, "Recife", PE);
		City saoPaulo = new City(null, "São Paulo", SP);
		City caruaru = new City(null, "Caruaru", PE);
		City bh = new City(null, "Belo Horizonte", MG);
		
		stateRepository.saveAll(Arrays.asList(MG, PE, SP));
		cityRepository.saveAll(Arrays.asList(recife, saoPaulo, caruaru, bh));

		Address ad1 = new Address(null, recife, "Rua da moeda", "Agamenon", "55015", maria);
		Address ad2 = new Address(null, saoPaulo, "Santa Efigênia", "Paulista", "52846", alex);
		
		maria.addAddress(ad1);
		alex.addAddress(ad2);

		Order o1 = new Order(null, Instant.parse("2019-06-20T19:53:07Z"), maria);
		Order o2 = new Order(null, Instant.parse("2019-07-21T03:42:10Z"), alex);
		Order o3 = new Order(null, Instant.parse("2019-07-22T15:21:22Z"), maria);
		
		o1.setDeliveryAddress(ad1);
		o2.setDeliveryAddress(ad2);
		o3.setDeliveryAddress(ad1);

		OrderItem oi1 = new OrderItem(o1, p1, 3, 15.0);
		OrderItem oi2 = new OrderItem(o1, p3, 1, 10.0);
		OrderItem oi3 = new OrderItem(o2, p3, 4, 5.0);
		OrderItem oi4 = new OrderItem(o3, p5, 2, 6.0);

		Payment pay1 = new CreditCard(null, Instant.parse("2019-06-20T21:53:07Z"), o1, 12);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Payment pay2 = new Boleto(null, Instant.parse("2019-08-20T21:53:07Z"), o2, sdf.parse("30/09/2019 10:32"));

		clientRepository.saveAll(Arrays.asList(maria, alex));

		addressRepository.saveAll(Arrays.asList(ad1, ad2));

		orderRepository.saveAll(Arrays.asList(o1, o2, o3));
		orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3, oi4));
		paymentRepository.saveAll(Arrays.asList(pay1, pay2));
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}

}
