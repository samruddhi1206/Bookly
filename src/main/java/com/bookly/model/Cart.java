package com.bookly.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Cart
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
   private Integer id;
  
  @ManyToOne
  private UserDetail user;
  
  @ManyToOne
  private AddBook book;
  
  
  private Integer quantity;
  
  
  @Transient
  private Double totalPrice;
  
  @Transient
  private Double totalOrderPrice;
  
   public Double getTotalOrderPrice() {
	   return totalOrderPrice;
    }
    public void setTotalOrderPrice(Double totalOrderPrice) {
	    this.totalOrderPrice = totalOrderPrice;
   }
   public Double getTotalPrice() {
	   return totalPrice;
   }
   public void setTotalPrice(Double totalPrice) {
	   this.totalPrice = totalPrice;
   }
   public Integer getId() {
	   return id;
   }
   public void setId(Integer id) {
	   this.id = id;
   }
   public UserDetail getUser() {
	   return user;
   }
   public void setUser(UserDetail user) {
	   this.user = user;
   }
   public AddBook getBook() {
	   return book;
   }
   public void setBook(AddBook book) {
	   this.book = book;
   }
   public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

   
   
   
   
}
