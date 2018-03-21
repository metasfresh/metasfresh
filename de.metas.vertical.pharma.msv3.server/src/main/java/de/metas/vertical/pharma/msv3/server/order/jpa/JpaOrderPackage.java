package de.metas.vertical.pharma.msv3.server.order.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import de.metas.vertical.pharma.msv3.protocol.order.OrderType;
import de.metas.vertical.pharma.msv3.server.jpa.AbstractEntity;
import lombok.Getter;
import lombok.NonNull;
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
@Table(name = "order_package")
@ToString
public class JpaOrderPackage extends AbstractEntity
{
	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull
	@Getter
	@Setter
	private JpaOrder order;

	@Getter
	@Setter
	private String documentNo;

	@Getter
	@Setter
	private OrderType orderType;

	@Getter
	@Setter
	/** One of 4 predefined or one free identifier. May deviate from the request identifier and be replaced by one of the 4 predefined identifiers (see specifications). */
	private String orderIdentification;

	@Getter
	@Setter
	private int supportId;

	@Getter
	@Setter
	private String packingMaterialId;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "orderPackage", cascade = CascadeType.ALL)
	@Getter
	private final List<JpaOrderPackageItem> items = new ArrayList<>();

	public void addItems(@NonNull final List<JpaOrderPackageItem> items)
	{
		items.forEach(item -> item.setOrderPackage(this));
		this.items.addAll(items);
	}

}
