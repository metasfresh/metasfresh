package de.metas.procurement.webui.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import lombok.NonNull;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.time.LocalDate;

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
@Table(name = "product_supply"//
		, uniqueConstraints = @UniqueConstraint(name = "product_supply_uq", columnNames = { "bpartner_id", "product_id", "day" })           //
)
@SelectBeforeUpdate
public class ProductSupply extends AbstractSyncConfirmAwareEntity
{
	public static ProductSupply build(final BPartner bpartner, final Product product, final ContractLine contractLine, final LocalDate day)
	{
		//
		// Validate
		if (contractLine != null)
		{
			final BPartner contractBPartner = contractLine.getContract().getBpartner();
			if (!Objects.equal(bpartner, contractBPartner))
			{
				throw new IllegalArgumentException("BPartner not matching the contract");
			}

			final Product contractProduct = contractLine.getProduct();
			if (!Objects.equal(product, contractProduct))
			{
				throw new IllegalArgumentException("Product not matching the contract");
			}
		}
		final ProductSupply productSupply = new ProductSupply();
		productSupply.setBpartner(bpartner);
		productSupply.setProduct(product);
		productSupply.setContractLine(contractLine);
		productSupply.setDay(day);
		return productSupply;
	}

	@ManyToOne
	@Lazy
	@NonNull
	private BPartner bpartner;

	@ManyToOne
	@NonNull
	private Product product;

	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)        // don't fail if the record was not found
	@SuppressWarnings("deprecation")
	@org.hibernate.annotations.ForeignKey(name = "none")         // deprecated but see http://stackoverflow.com/questions/27040735/jpa-association-without-foreign-key
	@Nullable
	private ContractLine contractLine;

	@NonNull
	private BigDecimal qty = BigDecimal.ZERO;

	@NonNull
	private java.sql.Date day;

	protected ProductSupply()
	{
	}

	@Override
	protected void toString(final MoreObjects.ToStringHelper toStringHelper)
	{
		toStringHelper
				.add("product", product)
				.add("bpartner", bpartner)
				.add("day", day)
				.add("qty", qty)
				.add("contractLine", contractLine);
	}

	public BPartner getBpartner()
	{
		return bpartner;
	}

	private void setBpartner(final BPartner bpartner)
	{
		this.bpartner = bpartner;
	}

	public Product getProduct()
	{
		return product;
	}

	private void setProduct(final Product product)
	{
		this.product = product;
	}

	@Nullable
	public ContractLine getContractLine()
	{
		return contractLine;
	}

	public void setContractLine(@Nullable final ContractLine contractLine)
	{
		this.contractLine = contractLine;
	}

	public BigDecimal getQty()
	{
		return qty;
	}

	public void setQty(final BigDecimal qty)
	{
		this.qty = qty;
	}

	@Nullable
	public LocalDate getDay()
	{
		return day != null ? day.toLocalDate() : null;
	}

	private void setDay(@NonNull final LocalDate day)
	{
		this.day = java.sql.Date.valueOf(day);
	}
}
