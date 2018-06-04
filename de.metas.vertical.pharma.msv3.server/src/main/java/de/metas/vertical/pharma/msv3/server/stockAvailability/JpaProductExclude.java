package de.metas.vertical.pharma.msv3.server.stockAvailability;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import de.metas.vertical.pharma.msv3.server.jpa.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

@Entity
@Table(name = "msv3_product_exclude", //
		uniqueConstraints = @UniqueConstraint(name = "msv3_product_exclude_uq", columnNames = { "pzn", "bpartner_id" }), //
		indexes = @Index(name = "msv3_product_exclude_sync_token", columnList = "sync_token") //
)
@Getter
@Setter
@ToString
public class JpaProductExclude extends AbstractEntity
{
	/** Pharma-Zentral-Nummer */
	private long pzn;

	@Column(name = "bpartner_id")
	private int bpartnerId;

	@Column(name = "sync_token")
	@NotNull
	private String syncToken = UUID.randomUUID().toString();

}
