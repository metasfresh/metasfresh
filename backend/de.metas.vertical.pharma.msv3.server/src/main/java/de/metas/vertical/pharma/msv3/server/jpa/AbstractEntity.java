package de.metas.vertical.pharma.msv3.server.jpa;

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

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

/*
 * #%L
 * metasfresh-pharma.msv3.server
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@MappedSuperclass
@Data
@EqualsAndHashCode(of = "id")
public abstract class AbstractEntity
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

	@Setter(AccessLevel.NONE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Setter(AccessLevel.NONE)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdated;

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
}
