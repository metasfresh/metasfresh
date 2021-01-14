package de.metas.procurement.webui.model;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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

@ToString
public class Contracts
{
	private static final Logger logger = LoggerFactory.getLogger(Contracts.class);

	@Getter
	private final BPartner bpartner;
	@Getter
	private final ImmutableList<Contract> contracts;

	public Contracts(
			@NonNull final BPartner bpartner,
			@NonNull final List<Contract> contracts)
	{
		this.bpartner = bpartner;
		this.contracts = ImmutableList.copyOf(contracts);
	}

	public List<Product> getProducts()
	{
		final TreeSet<Product> products = new TreeSet<>(Product.COMPARATOR_Id);
		for (final Contract contract : getContracts())
		{
			products.addAll(contract.getProducts());
		}

		return new ArrayList<>(products);
	}

	@Nullable
	public ContractLine getContractLineOrNull(final Product product, final LocalDate date)
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
