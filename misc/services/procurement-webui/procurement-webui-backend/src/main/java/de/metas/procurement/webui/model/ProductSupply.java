package de.metas.procurement.webui.model;

import com.google.common.base.MoreObjects;
import de.metas.procurement.webui.util.DateUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
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
import java.util.Objects;

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
	@Getter
	@Setter
	private ContractLine contractLine;

	@NonNull
	@Getter
	@Setter
	private BigDecimal qty = BigDecimal.ZERO;

	@NonNull
	@Getter
	@Setter
	private BigDecimal qtyUserEntered = BigDecimal.ZERO;

	@NonNull
	private java.sql.Date day;

	protected ProductSupply()
	{
	}

	@Builder
	private ProductSupply(
			@NonNull final BPartner bpartner,
			@NonNull final Product product,
			@Nullable final ContractLine contractLine,
			@NonNull final LocalDate day)
	{
		//
		// Validate
		if (contractLine != null)
		{
			final BPartner contractBPartner = contractLine.getContract().getBpartner();
			if (!Objects.equals(bpartner, contractBPartner))
			{
				throw new IllegalArgumentException("BPartner not matching the contract");
			}

			final Product contractProduct = contractLine.getProduct();
			if (!Objects.equals(product, contractProduct))
			{
				throw new IllegalArgumentException("Product not matching the contract");
			}
		}

		this.bpartner = bpartner;
		this.product = product;
		this.contractLine = contractLine;
		this.day = DateUtils.toSqlDate(day);
	}

	@Override
	protected void toString(final MoreObjects.ToStringHelper toStringHelper)
	{
		toStringHelper
				.omitNullValues()
				.add("product", product)
				.add("bpartner", bpartner)
				.add("day", day)
				.add("qtyUserEntered", qtyUserEntered)
				.add("qty", qty)
				.add("contractLine", contractLine);
	}

	public String getBpartnerUUID()
	{
		return bpartner.getUuid();
	}

	public String getProductUUID()
	{
		return product.getUuid();
	}

	public Long getProductId()
	{
		return product.getId();
	}

	public LocalDate getDay()
	{
		return day.toLocalDate();
	}

	public boolean isQtyConfirmedByUser()
	{
		return getQty().compareTo(getQtyUserEntered()) == 0;
	}
}
