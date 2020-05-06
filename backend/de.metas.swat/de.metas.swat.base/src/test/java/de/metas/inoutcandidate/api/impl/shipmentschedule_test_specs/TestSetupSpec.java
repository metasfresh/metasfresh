package de.metas.inoutcandidate.api.impl.shipmentschedule_test_specs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import lombok.Builder;
import lombok.Builder.ObtainVia;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
public class TestSetupSpec
{
	Map<String, UomSpec> names2uoms;

	Map<String, ProductSpec> values2products;

	List<StockSpec> stocks;

	List<OrderSpec> orders;

	List<OrderLineSpec> orderLines;

	List<ShipmentScheduleSpec> shipmentSchedules;

	@Builder(toBuilder = true)
	private TestSetupSpec(
			@Singular @ObtainVia(method = "getUoms") List<UomSpec> uoms,
			@Singular @ObtainVia(method = "getProducts") List<ProductSpec> products,
			@Singular List<StockSpec> stocks,
			@Singular List<OrderSpec> orders,
			@Singular List<OrderLineSpec> orderLines,
			@Singular List<ShipmentScheduleSpec> shipmentSchedules)
	{
		this.names2uoms = Maps.uniqueIndex(uoms, UomSpec::getName);
		this.values2products = Maps.uniqueIndex(products, ProductSpec::getValue);
		this.stocks = stocks;
		this.orders = orders;
		this.orderLines = orderLines;
		this.shipmentSchedules = shipmentSchedules;
	}

	public TestSetupSpec withProduct(ProductSpec productSpec)
	{
		final HashMap<String, ProductSpec> modifiedProducts = new HashMap<>(values2products);
		modifiedProducts.put(productSpec.getValue(), productSpec);

		return this.toBuilder().clearProducts().products(modifiedProducts.values()).build();
	}

	public List<ProductSpec> getProducts()
	{
		return ImmutableList.copyOf(values2products.values());
	}

	public List<UomSpec> getUoms()
	{
		return ImmutableList.copyOf(names2uoms.values());
	}
}
