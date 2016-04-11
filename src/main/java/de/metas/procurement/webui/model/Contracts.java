package de.metas.procurement.webui.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.google.gwt.thirdparty.guava.common.base.Objects;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;

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

public class Contracts
{
	private final BPartner bpartner;
	private final List<Contract> contracts;

	public Contracts(final BPartner bpartner, final List<Contract> contractsList)
	{
		super();
		this.bpartner = bpartner;
		contracts = ImmutableList.copyOf(contractsList);
	}

	@Override
	public String toString()
	{
		return Objects.toStringHelper(this)
				.add("bpartner", bpartner)
				.add("contracts", contracts)
				.toString();
	}

	public BPartner getBPartner()
	{
		return bpartner;
	}

	public List<Contract> getContracts()
	{
		return Collections.unmodifiableList(contracts);
	}

	public List<Product> getProducts()
	{
		final Set<Product> products = new TreeSet<>(Product.COMPARATOR_Id);
		for (final Contract contract : contracts)
		{
			products.addAll(contract.getProducts());
		}

		final List<Product> productsList = new ArrayList<>(products);
		return productsList;
	}

	public ContractLine getContractLineOrNull(final Product product, final Date date)
	{
		for (final Contract contract : contracts)
		{
			if (!contract.matchesDate(date))
			{
				continue;
			}

			final ContractLine contractLine = contract.getContractLineForProductOrNull(product);
			if (contractLine != null)
			{
				return contractLine;
			}
		}

		return null;
	}
}
