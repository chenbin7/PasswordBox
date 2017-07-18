package com.cb.passwdbox.greendao.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;
import com.cb.passwdbox.greendao.db.DaoSession;
import com.cb.passwdbox.greendao.db.PwdTypeDao;
import com.cb.passwdbox.greendao.db.PasswordDao;

@Entity
public class Password {

	@Id
	private String id;
	@NotNull
	private String name;
	@NotNull
	private String passwd;
	private String typeId;

	@Generated(hash = 565943725)
	public Password() {
	}

	@Generated(hash = 1706973647)
	public Password(String id, @NotNull String name, @NotNull String passwd,
			String typeId) {
		this.id = id;
		this.name = name;
		this.passwd = passwd;
		this.typeId = typeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
}
