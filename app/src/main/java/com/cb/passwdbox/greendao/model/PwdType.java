package com.cb.passwdbox.greendao.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class PwdType {

	@Id
	private String id;
	@NotNull
	private String name;
	private String descriptor;

	@Generated(hash = 1314085744)
	public PwdType() {
	}

	@Generated(hash = 1837887992)
	public PwdType(String id, @NotNull String name, String descriptor) {
		this.id = id;
		this.name = name;
		this.descriptor = descriptor;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
