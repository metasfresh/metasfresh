package de.metas.manufacturing.dispo;

import java.util.Date;

import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;

import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-manufacturing-dispo
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Builder
@Getter
@ToString
public class Candidate
{
	public enum Type
	{
		DEMAND, SUPPLY, STOCK
	};

	/**
	 * the supply type is relevant if the candidate's type is {@link Type#SUPPLY}.
	 *
	 * @author metas-dev <dev@metasfresh.com>
	 *
	 */
	public enum SupplyType
	{
		DISTRIBUTION, PRODUCTION

		/* PURCHASE this comes later */
	};

	@NonNull
	private final I_M_Product product;

	@NonNull
	private final I_M_Locator locator;

	/**
	 * The projected overall quantity which we expect at the time of {@link #getDate()}.
	 */
	@NonNull
	private final Quantity quantity;

	@NonNull
	private final Type type;

	private final SupplyType supplyType;

	private final ITableRecordReference referencedRecord;

	/**
	 * The projected date at which we expect this candidate's {@link #getQuantity()}.
	 */
	@NonNull
	private final Date date;

	public Candidate withOtherQuantity(final Quantity quantity)
	{
		return builder()
				.locator(locator)
				.product(product)
				.date(date)
				.type(type)
				.supplyType(supplyType)
				.quantity(quantity)
				.build();
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(type)
				.append(supplyType)
				.append(quantity)
				.append(product.getM_Product_ID())
				.append(locator)
				.append(date.getTime())
				.append(referencedRecord)
				.toHashcode();
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		final Candidate other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(type, other.type)
				.append(supplyType, other.supplyType)
				.append(quantity, other.quantity)
				.append(product.getM_Product_ID(), other.product.getM_Product_ID())
				.append(locator.getM_Locator_ID(), other.locator.getM_Locator_ID())
				.append(date.getTime(), other.date.getTime())
				.append(referencedRecord, other.referencedRecord)
				.isEqual();
	}
}
