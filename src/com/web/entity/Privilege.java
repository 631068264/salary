package com.web.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table
@JsonIgnoreProperties(value = { "parent" })
public class Privilege implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 33894293225966135L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;// 描述
	private String url;// 权限URL

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "parentid")
	private Privilege parent;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "parent")
	private List<Privilege> children;

	public Privilege() {
		super();
		// 必须要有报错：No default constructor for entity
	}

	public Privilege(String name, String url, Privilege parent) {
		this.name = name;
		this.url = url;
		this.parent = parent;
	}

	public Privilege getParent() {
		return parent;
	}

	public void setParent(Privilege parent) {
		this.parent = parent;
	}

	public List<Privilege> getChildren() {
		return children;
	}

	public void setChildren(List<Privilege> children) {
		this.children = children;
	}

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
