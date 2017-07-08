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

	@Id(autoincrement = true)
	private long id;
	@NotNull
	private String name;
	@NotNull
	private String passwd;
	private long typeId;

	@ToOne(joinProperty = "typeId")
	private PwdType mType;

	/** Used to resolve relations */
	@Generated(hash = 2040040024)
	private transient DaoSession daoSession;

	/** Used for active entity operations. */
	@Generated(hash = 1189424892)
	private transient PasswordDao myDao;

	@Generated(hash = 2080047043)
	private transient Long mType__resolvedKey;

	@Generated(hash = 565943725)
	public Password() {
	}

	@Generated(hash = 1707634554)
	public Password(long id, @NotNull String name, @NotNull String passwd,
			long typeId) {
		this.id = id;
		this.name = name;
		this.passwd = passwd;
		this.typeId = typeId;
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

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public long getTypeId() {
		return typeId;
	}

	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}

	/** To-one relationship, resolved on first access. */
	@Generated(hash = 1289311896)
	public PwdType getMType() {
		long __key = this.typeId;
		if (mType__resolvedKey == null || !mType__resolvedKey.equals(__key)) {
			final DaoSession daoSession = this.daoSession;
			if (daoSession == null) {
				throw new DaoException("Entity is detached from DAO context");
			}
			PwdTypeDao targetDao = daoSession.getPwdTypeDao();
			PwdType mTypeNew = targetDao.load(__key);
			synchronized (this) {
				mType = mTypeNew;
				mType__resolvedKey = __key;
			}
		}
		return mType;
	}

	/** called by internal mechanisms, do not call yourself. */
	@Generated(hash = 1613437256)
	public void setMType(@NotNull PwdType mType) {
		if (mType == null) {
			throw new DaoException(
					"To-one property 'typeId' has not-null constraint; cannot set to-one to null");
		}
		synchronized (this) {
			this.mType = mType;
			typeId = mType.getId();
			mType__resolvedKey = typeId;
		}
	}

	/**
	 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
	 * Entity must attached to an entity context.
	 */
	@Generated(hash = 128553479)
	public void delete() {
		if (myDao == null) {
			throw new DaoException("Entity is detached from DAO context");
		}
		myDao.delete(this);
	}

	/**
	 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
	 * Entity must attached to an entity context.
	 */
	@Generated(hash = 1942392019)
	public void refresh() {
		if (myDao == null) {
			throw new DaoException("Entity is detached from DAO context");
		}
		myDao.refresh(this);
	}

	/**
	 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
	 * Entity must attached to an entity context.
	 */
	@Generated(hash = 713229351)
	public void update() {
		if (myDao == null) {
			throw new DaoException("Entity is detached from DAO context");
		}
		myDao.update(this);
	}

	/** called by internal mechanisms, do not call yourself. */
	@Generated(hash = 175960626)
	public void __setDaoSession(DaoSession daoSession) {
		this.daoSession = daoSession;
		myDao = daoSession != null ? daoSession.getPasswordDao() : null;
	}


}
