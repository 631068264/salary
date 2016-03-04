package com.web.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 工资
 * 
 * @author Administrator
 * 
 */
@Entity
@Table
@JsonIgnoreProperties(value = { "user" })
public class Salary implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Transient
	private Double total;// 总收入
	@Transient
	private Double pay;// 实际收入

	private Double deduct;// 罚金
	private Double basePay;// 基本工资
	private Double welfare;// 福利
	private Double bonus;// 奖金

	// 用户一对一
	@OneToOne(mappedBy = "salary")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getTotal() {
		total = this.basePay + this.welfare + this.bonus;
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Double getPay() {
		pay = this.total - this.deduct;
		return pay;
	}

	public void setPay(Double pay) {
		this.pay = pay;
	}

	public Double getDeduct() {
		return deduct;
	}

	public void setDeduct(Double deduct) {
		if (deduct == null) {
			deduct = 0.0;
		}
		this.deduct = deduct;
	}

	public Double getBasePay() {
		return basePay;
	}

	public void setBasePay(Double basePay) {
		if (basePay == null) {
			basePay = 0.0;
		}
		this.basePay = basePay;
	}

	public Double getWelfare() {
		return welfare;
	}

	public void setWelfare(Double welfare) {
		if (welfare == null) {
			welfare = 0.0;
		}
		this.welfare = welfare;
	}

	public Double getBonus() {
		return bonus;
	}

	public void setBonus(Double bonus) {
		if (bonus == null) {
			bonus = 0.0;
		}
		this.bonus = bonus;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
