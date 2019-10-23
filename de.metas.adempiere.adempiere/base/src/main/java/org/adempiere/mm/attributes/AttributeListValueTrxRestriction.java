package org.adempiere.mm.attributes;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_M_AttributeValue;

import de.metas.lang.SOTrx;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


public enum AttributeListValueTrxRestriction implements ReferenceListAwareEnum
{
	ANY_TRANSACTION(null), //
	SALES(X_M_AttributeValue.AVAILABLETRX_SO), //
	PURCHASE(X_M_AttributeValue.AVAILABLETRX_PO), //
	;

	@Getter
	private final String code;

	AttributeListValueTrxRestriction(final String code)
	{
		this.code = code;
	}

	public static AttributeListValueTrxRestriction ofCode(@Nullable final String code)
	{
		if (code == null)
		{
			return ANY_TRANSACTION;
		}
		else if (SALES.code.contentEquals(code))
		{
			return SALES;
		}
		else if (PURCHASE.code.contentEquals(code))
		{
			return PURCHASE;
		}
		else
		{
			throw new AdempiereException("Unknown code: " + code);
		}
	}

	public boolean isMatchingSOTrx(@Nullable final SOTrx soTrx)
	{
		if (soTrx == null)
		{
			return true;
		}

		if (this == ANY_TRANSACTION)
		{
			return true;
		}

		return (this == SALES && soTrx.isSales())
				|| (this == PURCHASE && soTrx.isPurchase());
	}
}