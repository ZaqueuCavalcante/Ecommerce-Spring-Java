package br.com.zaqueucavalcante.ecommercespringjava.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.zaqueucavalcante.ecommercespringjava.entities.primarykeys.OrderItemPrimaryKey;

@Entity
@Table(name = "order_item_table")
public class OrderItem implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@EmbeddedId
	private OrderItemPrimaryKey id = new OrderItemPrimaryKey();

	private Integer quantity;
	private Double price;
	private Double discountPercentage;

	public OrderItem() {
	}

	public OrderItem(Order order, Product product, Integer quantity, Double discountPercentage) {
		super();
		id.setOrder(order);
		id.setProduct(product);
		this.quantity = quantity;
		this.price = product.getPrice();
		this.discountPercentage = discountPercentage;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@JsonIgnore
	public Order getOrder() {
		return id.getOrder();
	}

	public void setOrder(Order order) {
		id.setOrder(order);
	}

	public Product getProduct() {
		return id.getProduct();
	}

	public void setProduct(Product product) {
		id.setProduct(product);
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}
	
	public Double getPriceWithDiscount() {
		return (price * (1 - discountPercentage/100.00));
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(Double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	public Double getSubTotal() {
		return this.getPriceWithDiscount() * quantity;
	}
	
	// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - //
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getProduct().getName());
		builder.append(" [quantity=");
		builder.append(quantity);
		builder.append(", price=");
		builder.append(price);
		builder.append(", discountPercentage=");
		builder.append(discountPercentage);
		builder.append(", subtotal=");
		builder.append(getSubTotal());
		builder.append("]");
		return builder.toString();
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
		OrderItem other = (OrderItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
