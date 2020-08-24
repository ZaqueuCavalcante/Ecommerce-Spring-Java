package br.com.zaqueucavalcante.ecommercespringjava.configurations;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.zaqueucavalcante.ecommercespringjava.entities.Address;
import br.com.zaqueucavalcante.ecommercespringjava.entities.BoletoPayment;
import br.com.zaqueucavalcante.ecommercespringjava.entities.Category;
import br.com.zaqueucavalcante.ecommercespringjava.entities.City;
import br.com.zaqueucavalcante.ecommercespringjava.entities.Client;
import br.com.zaqueucavalcante.ecommercespringjava.entities.CreditCardPayment;
import br.com.zaqueucavalcante.ecommercespringjava.entities.Order;
import br.com.zaqueucavalcante.ecommercespringjava.entities.OrderItem;
import br.com.zaqueucavalcante.ecommercespringjava.entities.Payment;
import br.com.zaqueucavalcante.ecommercespringjava.entities.Product;
import br.com.zaqueucavalcante.ecommercespringjava.entities.State;
import br.com.zaqueucavalcante.ecommercespringjava.entities.enums.ClientType;
import br.com.zaqueucavalcante.ecommercespringjava.repositories.AddressRepository;
import br.com.zaqueucavalcante.ecommercespringjava.repositories.CategoryRepository;
import br.com.zaqueucavalcante.ecommercespringjava.repositories.CityRepository;
import br.com.zaqueucavalcante.ecommercespringjava.repositories.ClientRepository;
import br.com.zaqueucavalcante.ecommercespringjava.repositories.OrderItemRepository;
import br.com.zaqueucavalcante.ecommercespringjava.repositories.OrderRepository;
import br.com.zaqueucavalcante.ecommercespringjava.repositories.PaymentRepository;
import br.com.zaqueucavalcante.ecommercespringjava.repositories.ProductRepository;
import br.com.zaqueucavalcante.ecommercespringjava.repositories.StateRepository;

@Configuration
@Profile("test")
public class LocalTestConfiguration implements CommandLineRunner {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private AddressRepository addressRepository;

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public void run(String... args) throws Exception {
		Category eletronics = new Category(null, "Electronics");
		Category books = new Category(null, "Books");
		Category computers = new Category(null, "Computers");
		Category movies = new Category(null, "Movies");

		Product p1 = new Product(null, "The Lord of the Rings", "Lorem ipsum dolor sit amet, consectetur.", "image_url", 90.50);
		Product p2 = new Product(null, "Smart TV", "Nulla eu imperdiet purus. Maecenas ante.", "image_url", 2190.00);
		Product p3 = new Product(null, "Macbook Pro", "Nam eleifend maximus tortor, at mollis.", "image_url", 1250.00);
		Product p4 = new Product(null, "PC Gamer", "Donec aliquet odio ac rhoncus cursus.", "image_url", 1200.50);
		Product p5 = new Product(null, "Rails for Dummies", "Cras fringilla convallis sem vel faucibus.", "image_url", 100.00);
		Product p6 = new Product(null, "Tenet", "Nolan best film.", "image_url", 50.00);

		eletronics.addProducts(Arrays.asList(p2, p3, p4));
		books.addProducts(Arrays.asList(p1, p5));
		computers.addProducts(Arrays.asList(p3, p4));
		movies.addProduct(p6);

		categoryRepository.saveAll(Arrays.asList(eletronics, books, computers, movies));
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6));
		categoryRepository.flush();
		productRepository.flush();

		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
		Client maria = new Client(null, "Maria Brown", "maria@gmail.com", ClientType.PHYSICAL_PERSON, "5138438486");
		Client alex = new Client(null, "Alex Green", "alex@gmail.com", ClientType.LEGAL_PERSON, "24358412351");

		maria.addPhones(Arrays.asList("384638468", "53468468"));
		alex.addPhones(Arrays.asList("538468444", "68468484"));

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

		Payment pay1 = new CreditCardPayment(null, Instant.parse("2019-06-20T21:53:07Z"), o1, 12);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Payment pay2 = new BoletoPayment(null, Instant.parse("2019-08-20T21:53:07Z"), o2, sdf.parse("30/09/2019 10:32"));

		clientRepository.saveAll(Arrays.asList(maria, alex));

		addressRepository.saveAll(Arrays.asList(ad1, ad2));

		orderRepository.saveAll(Arrays.asList(o1, o2, o3));
		orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3, oi4));
		paymentRepository.saveAll(Arrays.asList(pay1, pay2));
	}
}
