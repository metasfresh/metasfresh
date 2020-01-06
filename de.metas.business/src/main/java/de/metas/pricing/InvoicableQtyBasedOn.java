package de.metas.pricing;

import static de.metas.util.Check.isEmpty;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_M_ProductPrice;

import lombok.Getter;

/*
 * #%L
 * de.metas.business
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

public enum InvoicableQtyBasedOn
{
	/** Please keep in sync with {@link X_M_ProductPrice#INVOICABLEQTYBASEDON_CatchWeight}. */
	CatchWeight(X_M_ProductPrice.INVOICABLEQTYBASEDON_CatchWeight),

	/** Please keep in sync with {@link X_M_ProductPrice#INVOICABLEQTYBASEDON_Nominal}. */
	NominalWeight(X_M_ProductPrice.INVOICABLEQTYBASEDON_Nominal);

	public static InvoicableQtyBasedOn fromRecordString(@Nullable final String invoicableQtyBasedOn)
	{
		if (isEmpty(invoicableQtyBasedOn, true))
		{
			return NominalWeight; // default
		}
		else if (X_M_ProductPrice.INVOICABLEQTYBASEDON_CatchWeight.equals(invoicableQtyBasedOn))
		{
			return CatchWeight;
		}
		else if (X_M_ProductPrice.INVOICABLEQTYBASEDON_Nominal.equals(invoicableQtyBasedOn))
		{
			return NominalWeight;
		}

		throw new AdempiereException("Unsupported invoicableQtyBasedOn value: " + invoicableQtyBasedOn);
	}

	@Getter
	private final String recordString;

	private InvoicableQtyBasedOn(String recordString)
	{
		this.recordString = recordString;
	}

	public boolean isCatchWeight()
	{
		return CatchWeight.equals(this);
	}
}
