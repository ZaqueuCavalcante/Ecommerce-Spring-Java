package br.com.zaqueucavalcante.ecommercespringjava.entities.products;

import br.com.zaqueucavalcante.ecommercespringjava.entities.orders.Order;
import br.com.zaqueucavalcante.ecommercespringjava.entities.orders.OrderItem;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product_table")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String description;
	private String imageUrl;
	private Double price;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "product_category_table",
			   joinColumns = @JoinColumn(name = "product_id"),
			   inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> categories = new HashSet<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "id.product")
	private Set<OrderItem> orderItems = new HashSet<>();

	public Product() {}
	
	public Product(Long id, String name, String description, String imageUrl, Double price) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.imageUrl = imageUrl;
		this.price = price;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Set<Category> getCategories() {
		return categories;
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public void addCategory(Category category) {
		categories.add(category);
	}
	
	public void addCategories(List<Category> categoryList) {
		categories.addAll(categoryList);
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public void addOrderItem(OrderItem orderItem) {
		orderItems.add(orderItem);
	}
	
	public void addItems(List<OrderItem> orderItemList) {
		orderItems.addAll(orderItemList);
	}
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@JsonIgnore
	public Set<Order> getOrders() {
		Set<Order> orders = new HashSet<>();
		for (OrderItem orderItem : this.orderItems) {
			orders.add(orderItem.getOrder());
		}
		return orders;
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
