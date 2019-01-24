package de.metas.vertical.pharma.msv3.server.stockAvailability.jpa;

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
@Table(name = "msv3_stock_availability", //
		uniqueConstraints = @UniqueConstraint(name = "stock_availability_mf_pzn_uq", columnNames = { "mf_pzn" }), //
		indexes = {
				@Index(name = "stock_availability_sync_token", columnList = "mf_sync_token", unique = false),
				@Index(name = "mf_event_version", columnList = "mf_event_version", unique = false) // used when deleting all records with a lower event version
		})
@Getter
@Setter
@ToString
public class JpaStockAvailability extends AbstractEntity
{
	/** Pharma-Zentral-Nummer */
	@Column(name = "mf_pzn")
	private long mfPzn;

	private int mfQty;

	@Column(name = "mf_sync_token", nullable = false)
	@NotNull
	private String mfSyncToken;

	@Column(name = "mf_event_version")
	private int mfEventVersion;
}
