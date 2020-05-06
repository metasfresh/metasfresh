package de.metas.procurement.webui.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import com.google.gwt.thirdparty.guava.common.base.Objects;
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

@MappedSuperclass
@SuppressWarnings("serial")
public abstract class AbstractEntity implements Serializable
{
	@Id
	@GeneratedValue
	private Long id;

	private boolean deleted = false;

	@NotNull
	private String uuid = UUID.randomUUID().toString();

	//
	// Versioning and created/updated timestamps
	protected static final int VERSION_INITIAL = 0;
	@Version
	private final int version = VERSION_INITIAL;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdated;

	AbstractEntity()
	{
		super();
	}
	
	@PreUpdate
	@PrePersist
	public void updateCreatedUpdated()
	{
		final Date now = new Date();
		this.dateUpdated = now;
		if (dateCreated == null)
		{
			dateCreated = now;
		}
	}

	@Override
	public final String toString()
	{
		final ToStringHelper toStringHelper = Objects.toStringHelper(this);
		toString(toStringHelper);
		return toStringHelper
				.add("id", id)
				.add("version", version)
				.add("deleted", deleted)
				.add("uuid", uuid)
				.toString();
	}

	protected void toString(final ToStringHelper toStringHelper)
	{
		// nothing at this level
	}

	public final Long getId()
	{
		return id;
	}

	public final String getUuid()
	{
		return uuid;
	}

	public final void setUuid(final String uuid)
	{
		this.uuid = uuid;
	}

	public final int getVersion()
	{
		return version;
	}

	public final void setDeleted(final boolean deleted)
	{
		this.deleted = deleted;
	}

	public final boolean isDeleted()
	{
		return deleted;
	}

	@Override
	public final int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Objects.hashCode(id);
		return result;
	}

	@Override
	public final boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}

		if (getClass() != obj.getClass())
		{
			return false;
		}

		final AbstractEntity other = (AbstractEntity)obj;
		return Objects.equal(id, other.id);
	}
	
	protected Date getDateCreated()
	{
		return dateCreated;
	}
	
	protected Date getDateUpdated()
	{
		return dateUpdated;
	}

}
