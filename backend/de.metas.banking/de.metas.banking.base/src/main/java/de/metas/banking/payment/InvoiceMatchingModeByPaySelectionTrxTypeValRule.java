package de.metas.banking.payment;

import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.ad.validationRule.AbstractJavaValidationRule;
import org.adempiere.ad.validationRule.IValidationContext;
import org.compiere.model.I_C_PaySelection;
import org.compiere.util.NamePair;

import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;

/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class InvoiceMatchingModeByPaySelectionTrxTypeValRule extends AbstractJavaValidationRule
{
	private static final String PARAM_PaySelectionTrxType = I_C_PaySelection.COLUMNNAME_PaySelectionTrxType;
	private static final ImmutableSet<String> PARAMETERS = ImmutableSet.of(PARAM_PaySelectionTrxType);

	@Override
	public Set<String> getParameters(@Nullable final String contextTableName)
	{
		return PARAMETERS;
	}

	@Override
	public boolean accept(@Nullable final IValidationContext evalCtx, @Nullable final NamePair item)
	{
		final PaySelectionTrxType trxType = extractPaySelectionTrxType(evalCtx);
		if (trxType == null)
		{
			return true;
		}

		final InvoiceMatchingMode invoiceMatchingMode = extractInvoiceMatchingMode(item);
		if (invoiceMatchingMode == null)
		{
			return false;
		}

		return invoiceMatchingMode.isCompatibleWith(trxType);
	}

	private boolean isContextAvailable(@Nullable final IValidationContext evalCtx)
	{
		return evalCtx != null
				&& evalCtx != IValidationContext.NULL
				&& evalCtx != IValidationContext.DISABLED;
	}

	private PaySelectionTrxType extractPaySelectionTrxType(@Nullable final IValidationContext evalCtx)
	{
		if (!isContextAvailable(evalCtx))
		{
			return null;
		}

		final String code = evalCtx.get_ValueAsString(PARAM_PaySelectionTrxType);
		if (Check.isBlank(code))
		{
			return null;
		}

		return PaySelectionTrxType.ofCode(code);
	}

	private InvoiceMatchingMode extractInvoiceMatchingMode(@Nullable final NamePair item)
	{
		return item != null
				? InvoiceMatchingMode.ofNullableCode(item.getID())
				: null;
	}

}
