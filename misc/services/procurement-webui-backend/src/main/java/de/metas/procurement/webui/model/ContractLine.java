package de.metas.procurement.webui.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.google.common.base.MoreObjects;
import lombok.NonNull;



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
@Table(name = "contract_line" //
, uniqueConstraints = @UniqueConstraint(name = "contract_line_uq", columnNames = { "contract_id", "product_id" })  //
)
@SuppressWarnings("serial")
public class ContractLine extends AbstractEntity
{
	@ManyToOne(fetch = FetchType.EAGER)
	@NonNull
	private Contract contract;
	@ManyToOne
	@NonNull
	private Product product;

	@Override
	protected void toString(final MoreObjects.ToStringHelper toStringHelper)
	{
		toStringHelper
				.add("product", product);
	}

	public void setContract(final Contract contract)
	{
		this.contract = contract;
	}

	public Contract getContract()
	{
		return contract;
	}

	public Product getProduct()
	{
		return product;
	}

	public void setProduct(final Product product)
	{
		this.product = product;
	}
}
