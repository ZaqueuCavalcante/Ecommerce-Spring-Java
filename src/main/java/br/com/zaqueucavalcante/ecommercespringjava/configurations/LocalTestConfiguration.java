package br.com.zaqueucavalcante.ecommercespringjava.configurations;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.zaqueucavalcante.ecommercespringjava.entities.Category;
import br.com.zaqueucavalcante.ecommercespringjava.entities.Client;
import br.com.zaqueucavalcante.ecommercespringjava.entities.CreditCardPayment;
import br.com.zaqueucavalcante.ecommercespringjava.entities.Order;
import br.com.zaqueucavalcante.ecommercespringjava.entities.OrderItem;
import br.com.zaqueucavalcante.ecommercespringjava.entities.Payment;
import br.com.zaqueucavalcante.ecommercespringjava.entities.Product;
import br.com.zaqueucavalcante.ecommercespringjava.entities.enums.UserType;
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
		Client u1 = new Client(null, "Maria Brown", "maria@gmail.com", UserType.PHYSICAL_PERSON, "123456");
		Client u2 = new Client(null, "Alex Green", "alex@gmail.com", UserType.LEGAL_PERSON, "123456");
		
		Order o1 = new Order(null, Instant.parse("2019-06-20T19:53:07Z"), u1);
		Order o2 = new Order(null, Instant.parse("2019-07-21T03:42:10Z"), u2);
		Order o3 = new Order(null, Instant.parse("2019-07-22T15:21:22Z"), u1);

		Category cat1 = new Category(null, "Electronics");
		Category cat2 = new Category(null, "Books");
		Category cat3 = new Category(null, "Computers");
		Category cat4 = new Category(null, "Movies");
		Category cat5 = new Category(null, "Smartphones");

		Product p1 = new Product(null, "The Lord of the Rings", "Lorem ipsum dolor sit amet, consectetur.", "", 90.5);
		Product p2 = new Product(null, "Smart TV", "Nulla eu imperdiet purus. Maecenas ante.", "", 2190.0);
		Product p3 = new Product(null, "Macbook Pro", "Nam eleifend maximus tortor, at mollis.", "", 1250.0);
		Product p4 = new Product(null, "PC Gamer", "Donec aliquet odio ac rhoncus cursus.", "", 1200.0);
		Product p5 = new Product(null, "Rails for Dummies", "Cras fringilla convallis sem vel faucibus.", "", 100.99);
		
		categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5));
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));
		categoryRepository.flush();
		productRepository.flush();
		
		cat1.addProducts(Arrays.asList(p2, p3, p4));
		cat2.addProducts(Arrays.asList(p1, p5));
		cat3.addProducts(Arrays.asList(p3, p4));
		
		OrderItem oi1 = new OrderItem(o1, p1, 2, p1.getPrice(), 15.0);
		OrderItem oi2 = new OrderItem(o1, p3, 1, p3.getPrice(), 10.0);
		OrderItem oi3 = new OrderItem(o2, p3, 2, p3.getPrice(), 5.0);
		OrderItem oi4 = new OrderItem(o3, p5, 2, p5.getPrice(), 6.0);
	
		clientRepository.saveAll(Arrays.asList(u1, u2));
		orderRepository.saveAll(Arrays.asList(o1, o2, o3));
		categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3));
		productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5));
		orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3, oi4));
//		
//		Payment pay1 = new CreditCardPayment(null, Instant.parse("2019-06-20T21:53:07Z"), o1, 12);
//		o1.setPayment(pay1);

//		orderRepository.save(o1);
//		paymentRepository.save(pay1);
		
		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
//		State MG = new State(null, "Minas Gerais");
//		State PE = new State(null, "Pernambuco");
//		State SP = new State(null, "São Paulo");
//		
//		City recife = new City(null, "Recife", PE);
//		City saoPaulo = new City(null, "São Paulo", SP);
//		City caruaru = new City(null, "Caruaru", PE);
//		City bh = new City(null, "Belo Horizonte", MG);
//		
//		stateRepository.saveAll(Arrays.asList(MG, PE, SP));
//		cityRepository.saveAll(Arrays.asList(recife, saoPaulo, caruaru, bh));
//		
//		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
//		u1.addPhones(Arrays.asList("384638468", "53468468"));
//		u2.addPhones(Arrays.asList("538468444", "68468484"));
//		
//		Address ad1 = new Address(null, recife, "Rua da moeda", "Agamenon", "55015", u1);
//		Address ad2 = new Address(null, saoPaulo, "Santa Efigênia", "Paulista", "52846", u2);
//		
//		addressRepository.saveAll(Arrays.asList(ad1, ad2));
//		
//		u1.addAdresses(Arrays.asList(ad1, ad2));
		
//		clientRepository.saveAll(Arrays.asList(u1, u2));
	}
}
