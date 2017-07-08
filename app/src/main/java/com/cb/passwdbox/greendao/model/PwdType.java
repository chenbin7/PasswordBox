package com.cb.passwdbox.greendao.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class PwdType {

	@Id(autoincrement = true)
	private long id;
	@NotNull
	private String name;
	private String descriptor;

	@Generated(hash = 1314085744)
	public PwdType() {
	}

	@Generated(hash = 522542111)
	public PwdType(long id, @NotNull String name, String descriptor) {
		this.id = id;
		this.name = name;
		this.descriptor = descriptor;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescriptor() {
		return descriptor;
	}

	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}
}
