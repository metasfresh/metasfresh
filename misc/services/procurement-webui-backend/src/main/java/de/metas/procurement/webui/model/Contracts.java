package de.metas.procurement.webui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;




import de.metas.procurement.webui.Application;
import de.metas.procurement.webui.repository.ContractRepository;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class Contracts
{
	// services
	private static final transient Logger logger = LoggerFactory.getLogger(Contracts.class);
	@Autowired
	private ContractRepository contractRepository;

	private final BPartner bpartner;
	private List<Contract> _contracts;

	public Contracts(final BPartner bpartner)
	{
		super();
		Application.autowire(this);

		this.bpartner = bpartner;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("bpartner", bpartner)
				.add("contracts", _contracts)
				.toString();
	}

	public BPartner getBPartner()
	{
		return bpartner;
	}

	public List<Contract> getContracts()
	{
		if (_contracts == null)
		{
			synchronized (this)
			{
				if (_contracts == null)
				{
					final List<Contract> contractsList = contractRepository.findByBpartnerAndDeletedFalse(bpartner);
					_contracts = ImmutableList.copyOf(contractsList);
				}
			}
		}
		return _contracts;
	}

	public void resetContractsCache()
	{
		synchronized (this)
		{
			_contracts = null;
		}
	}

	public List<Product> getProducts()
	{
		final Set<Product> products = new TreeSet<>(Product.COMPARATOR_Id);
		for (final Contract contract : getContracts())
		{
			products.addAll(contract.getProducts());
		}

		final List<Product> productsList = new ArrayList<>(products);
		return productsList;
	}

	public ContractLine getContractLineOrNull(final Product product, final Date date)
	{
		final List<ContractLine> matchingLinesWithRfq = new LinkedList<>();
		final List<ContractLine> matchingLinesOthers = new LinkedList<>();
		for (final Contract contract : getContracts())
		{
			if (!contract.matchesDate(date))
			{
				continue;
			}

			final ContractLine contractLine = contract.getContractLineForProductOrNull(product);
			if (contractLine == null)
			{
				continue;
			}

			if (contract.isRfq())
			{
				matchingLinesWithRfq.add(contractLine);
			}
			else
			{
				matchingLinesOthers.add(contractLine);
			}
		}

		// Contracts with RfQ (priority)
		if (!matchingLinesWithRfq.isEmpty())
		{
			if (matchingLinesWithRfq.size() > 1)
			{
				logger.warn("More then one matching contracts (with RfQ) found for {}/{}: {}", product, date, matchingLinesWithRfq);
			}
			return matchingLinesWithRfq.get(0);
		}

		// Contracts without RfQ
		if (!matchingLinesOthers.isEmpty())
		{
			if (matchingLinesOthers.size() > 1)
			{
				logger.warn("More then one matching contracts found for {}/{}: {}", product, date, matchingLinesOthers);
			}
			return matchingLinesOthers.get(0);
		}

		// No matching contract line
		return null;
	}
}
