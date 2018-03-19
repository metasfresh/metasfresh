package de.metas.vertical.pharma.msv3.server.order.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import de.metas.vertical.pharma.msv3.protocol.order.OrderStatus;
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
@Table(name = "order_header", uniqueConstraints = @UniqueConstraint(name = "order_header_uq", columnNames = { "documentNo", "bpartnerId" }))
@Getter
@Setter
@ToString
public class JpaOrder extends AbstractEntity
{
	@NotNull
	private Integer bpartnerId;
	@NotNull
	private String documentNo;
	@NotNull
	private Integer supportId;
	@NotNull
	private Boolean nightOperation;
	@NotNull
	private OrderStatus orderStatus;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
	private final List<JpaOrderPackage> orderPackages = new ArrayList<>();

	public void addOrderPackages(@NonNull final List<JpaOrderPackage> orderPackages)
	{
		orderPackages.forEach(orderPackage -> orderPackage.setOrder(this));
		this.orderPackages.addAll(orderPackages);
	}
}
