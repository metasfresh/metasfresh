package de.metas.procurement.webui.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import de.metas.procurement.webui.util.LanguageKey;
import lombok.NonNull;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;




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
		, uniqueConstraints = @UniqueConstraint(name = "bpartner_user_email", columnNames = {
		"email" //
		, "deleted_id" // FRESH-176: use it as part of the unique index just to emulate partial indexes on JPA
})   //
		, indexes = @Index(name = "bpartner_user_email_idx", columnList = "email, deleted ") // index mainly used on login via UserRepository.findByEmailAndDeletedFalse
)
public class User extends AbstractEntity
{
	@ManyToOne(fetch = FetchType.EAGER)
	@NonNull
	private BPartner bpartner;

	@NonNull
	private String email;

	private String password;

	private String language;

	@Nullable
	private String passwordResetKey;

	@Nullable
	private Long deleted_id;

	public User()
	{
		super();
	}

	@Override
	protected void toString(final MoreObjects.ToStringHelper toStringHelper)
	{
		toStringHelper
				.add("email", email)
				.add("language", language)
		// WARNING: never ever output the password
		;
	}

	public Long getBpartnerId()
	{
		return getBpartner().getId();
	}

	public BPartner getBpartner()
	{
		return bpartner;
	}

	public void setBpartner(final BPartner bpartner)
	{
		this.bpartner = bpartner;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(final String email)
	{
		this.email = email;
	}

	public void setLanguage(final String language)
	{
		this.language = language;
	}

	public String getLanguage()
	{
		return language;
	}

	public LanguageKey getLanguageKeyOrDefault()
	{
		final LanguageKey languageKey = LanguageKey.ofNullableString(getLanguage());
		return languageKey != null ? languageKey : LanguageKey.getDefault();
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(final String password)
	{
		this.password = password;
	}

	@Nullable
	public String getPasswordResetKey()
	{
		return passwordResetKey;
	}

	public void setPasswordResetKey(@Nullable final String passwordResetKey)
	{
		this.passwordResetKey = passwordResetKey;
	}

	public void markDeleted()
	{
		setDeleted(true);
		deleted_id = getId(); // FRESH-176: set the delete_id to current ID just to avoid the unique constraint
	}

	public void markNotDeleted()
	{
		setDeleted(false);
		deleted_id = null; // FRESH-176: set the delete_id to NULL just to make sure the the unique index is enforced
	}

	@Nullable
	@VisibleForTesting
	public Long getDeleted_id()
	{
		return deleted_id;
	}
}
