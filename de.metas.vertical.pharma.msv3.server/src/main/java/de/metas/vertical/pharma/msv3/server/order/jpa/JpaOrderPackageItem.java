package de.metas.vertical.pharma.msv3.server.order.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import de.metas.vertical.pharma.msv3.protocol.order.DeliverySpecifications;
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
@Table(name = "msv3_order_package_item", //
		indexes = { //
				@Index(name = "order_package_item_order_package_id", columnList = "order_package_id", unique = false),
				@Index(name = "order_package_item_ol_cand_id", columnList = "mf_ol_cand_id", unique = false/*might be unique, but i'm not 100% sure*/)
		})
@Getter
@Setter
@ToString
public class JpaOrderPackageItem extends AbstractEntity
{
	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull
	private JpaOrderPackage orderPackage;
	private long pzn;
	private int qty;
	private DeliverySpecifications deliverySpecifications;

	@Column(name="mf_ol_cand_id")
	private int mfOlCandId;
}
