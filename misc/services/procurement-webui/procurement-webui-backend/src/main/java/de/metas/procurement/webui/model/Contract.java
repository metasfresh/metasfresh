package de.metas.procurement.webui.model;

import com.google.common.base.MoreObjects;
import de.metas.procurement.webui.util.DateUtils;
import lombok.NonNull;

import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Entity
@Table(name = "contract")
public class Contract extends AbstractEntity
{
	@NonNull
	private java.sql.Date dateFrom;
	@NonNull
	private java.sql.Date dateTo;

	@ManyToOne(fetch = FetchType.EAGER)
	@NonNull
	private BPartner bpartner;

	private String rfq_uuid;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "contract", cascade = CascadeType.REMOVE)
	private List<ContractLine> contractLines = new ArrayList<>();

	public Contract()
	{
	}

	@Override
	protected void toString(final MoreObjects.ToStringHelper toStringHelper)
	{
		toStringHelper
				.add("dateFrom", dateFrom)
				.add("dateTo", dateTo)
				.add("lines", contractLines)
				.add("bpartner", bpartner);
	}

	public BPartner getBpartner()
	{
		return bpartner;
	}

	public void setBpartner(final BPartner bpartner)
	{
		this.bpartner = bpartner;
	}

	public LocalDate getDateFrom()
	{
		return DateUtils.toLocalDate(dateFrom);
	}

	public void setDateFrom(@NonNull final LocalDate dateFrom)
	{
		this.dateFrom = DateUtils.toSqlDate(dateFrom);
	}

	public LocalDate getDateTo()
	{
		return DateUtils.toLocalDate(dateTo);
	}

	public void setDateTo(@NonNull final LocalDate dateTo)
	{
		this.dateTo = DateUtils.toSqlDate(dateTo);
	}

	public void setRfq_uuid(final String rfq_uuid)
	{
		this.rfq_uuid = rfq_uuid;
	}

	public boolean isRfq()
	{
		return rfq_uuid != null;
	}

	public List<ContractLine> getContractLines()
	{
		return Collections.unmodifiableList(contractLines);
	}

	@Nullable
	public ContractLine getContractLineForProductOrNull(final Product product)
	{
		for (final ContractLine contractLine : getContractLines())
		{
			if (Product.COMPARATOR_Id.compare(contractLine.getProduct(), product) != 0)
			{
				continue;
			}

			return contractLine;
		}

		return null;
	}

	public Collection<Product> getProducts()
	{
		final Set<Product> products = new TreeSet<>(Product.COMPARATOR_Id);
		for (final ContractLine contractLine : getContractLines())
		{
			if (contractLine.isDeleted())
			{
				continue;
			}

			final Product product = contractLine.getProduct();
			if (product.isDeleted())
			{
				continue;
			}

			products.add(product);
		}

		return products;
	}

	public boolean matchesDate(final LocalDate date)
	{
		return DateUtils.between(date, getDateFrom(), getDateTo());
	}
}
