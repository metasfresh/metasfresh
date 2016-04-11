package de.metas.procurement.webui.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.google.gwt.thirdparty.guava.common.base.Objects.ToStringHelper;

/*
 * #%L
 * de.metas.procurement.webui
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Entity
@Table(name = "bpartner_user" //
, uniqueConstraints = @UniqueConstraint(name = "bpartner_user_email", columnNames = { "email" })  //
)
@SuppressWarnings("serial")
public class User extends AbstractEntity
{
	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull
	private BPartner bpartner;

	@NotNull
	private String email;

	private String password;

	private String language;

	private String passwordResetKey;

	public User()
	{
		super();
	}

	@Override
	protected void toString(final ToStringHelper toStringHelper)
	{
		toStringHelper
				.add("email", email);
	}

	public BPartner getBpartner()
	{
		return bpartner;
	}

	public void setBpartner(BPartner bpartner)
	{
		this.bpartner = bpartner;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setLanguage(String language)
	{
		this.language = language;
	}

	public String getLanguage()
	{
		return language;
	}

	public void setPassword(final String password)
	{
		this.password = password;
	}

	public String getPasswordResetKey()
	{
		return passwordResetKey;
	}

	public void setPasswordResetKey(String passwordResetKey)
	{
		this.passwordResetKey = passwordResetKey;
	}
}
