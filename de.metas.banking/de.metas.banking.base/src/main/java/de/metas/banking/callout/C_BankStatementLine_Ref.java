package de.metas.banking.callout;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.banking.model.IBankStatementLineOrRef;
import de.metas.banking.model.I_C_BankStatementLine_Ref;
import lombok.NonNull;

/*
 * #%L
 * de.metas.banking.base
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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Callout(I_C_BankStatementLine_Ref.class)
public class C_BankStatementLine_Ref
{
	public static final C_BankStatementLine_Ref instance = new C_BankStatementLine_Ref();

	private C_BankStatementLine_Ref()
	{
		super();
	}

	@CalloutMethod(columnNames = {
			I_C_BankStatementLine_Ref.COLUMNNAME_TrxAmt,
			I_C_BankStatementLine_Ref.COLUMNNAME_C_Currency_ID,
			I_C_BankStatementLine_Ref.COLUMNNAME_DiscountAmt,
			I_C_BankStatementLine_Ref.COLUMNNAME_IsOverUnderPayment,
			I_C_BankStatementLine_Ref.COLUMNNAME_OverUnderAmt,
			I_C_BankStatementLine_Ref.COLUMNNAME_WriteOffAmt,
	})
	public void onAmountsChanged(final I_C_BankStatementLine_Ref line, final ICalloutField field)
	{
		final String colName = field.getColumnName();
		setBankStatementLineOrRefFieldsAmounts(line, colName);
	}

	private void setBankStatementLineOrRefFieldsAmounts(@NonNull final I_C_BankStatementLine_Ref lineRef, @NonNull final String colName)
	{
		if (lineRef.getC_Invoice_ID() <= 0)
		{
			final IBankStatementLineOrRef lineOrRef = InterfaceWrapperHelper.create(lineRef, IBankStatementLineOrRef.class);
			BankStatementLineOrRefHelper.setBankStatementLineOrRefAmountsToZero(lineOrRef);
		}
		else
		{
			final IBankStatementLineOrRef lineOrRef = InterfaceWrapperHelper.create(lineRef, IBankStatementLineOrRef.class);
			BankStatementLineOrRefHelper.setBankStatementLineOrRefAmountsWhenSomeAmountChanged(lineOrRef, colName);
		}
	}

	@CalloutMethod(columnNames = I_C_BankStatementLine_Ref.COLUMNNAME_C_Invoice_ID)
	public void onInvoiceIdChanged(final I_C_BankStatementLine_Ref line)
	{
		if (line.getC_Invoice_ID() <= 0)
		{
			return;
		}

		final IBankStatementLineOrRef lineOrRef = InterfaceWrapperHelper.create(line, IBankStatementLineOrRef.class);
		BankStatementLineOrRefHelper.setBankStatementLineOrRefFieldsWhenInvoiceChanged(lineOrRef);
	}

	@CalloutMethod(columnNames = I_C_BankStatementLine_Ref.COLUMNNAME_C_Payment_ID)
	public void onPaymentIdChanged(final I_C_BankStatementLine_Ref line)
	{
		final IBankStatementLineOrRef lineOrRef = InterfaceWrapperHelper.create(line, IBankStatementLineOrRef.class);
		BankStatementLineOrRefHelper.setPaymentDetails(lineOrRef);
	}
}
