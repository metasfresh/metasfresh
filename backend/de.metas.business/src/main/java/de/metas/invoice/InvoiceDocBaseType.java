package de.metas.invoice;

import de.metas.document.DocBaseType;
import de.metas.lang.SOTrx;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.business
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

public enum InvoiceDocBaseType implements ReferenceListAwareEnum
{
	VendorInvoice(DocBaseType.APInvoice, SOTrx.PURCHASE, false),//
	VendorCreditMemo(DocBaseType.APCreditMemo, SOTrx.PURCHASE, true),//
	CustomerInvoice(DocBaseType.ARInvoice, SOTrx.SALES, false),//
	CustomerCreditMemo(DocBaseType.ARCreditMemo, SOTrx.SALES, true), //
	//
	/**
	 * Legacy commission/salary invoice
	 */
	@Deprecated
	AEInvoice(DocBaseType.GehaltsrechnungAngestellter, SOTrx.PURCHASE, false),
	/**
	 * Legacy invoice for recurrent payment
	 */
	@Deprecated
	AVInvoice(DocBaseType.InterneRechnungLieferant, SOTrx.PURCHASE, false),
	;

	@NonNull @Getter private final DocBaseType docBaseType;
	@NonNull @Getter private final SOTrx soTrx;
	@Getter private final boolean creditMemo;

	private static final ValuesIndex<InvoiceDocBaseType> index = ReferenceListAwareEnums.index(values());

	InvoiceDocBaseType(@NonNull final DocBaseType docBaseType, @NonNull final SOTrx soTrx, final boolean creditMemo)
	{
		this.docBaseType = docBaseType;
		this.soTrx = soTrx;
		this.creditMemo = creditMemo;
	}

	public static InvoiceDocBaseType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	public static InvoiceDocBaseType ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}

	public static InvoiceDocBaseType ofSOTrxAndCreditMemo(@NonNull final SOTrx soTrx, final boolean creditMemo)
	{
		if (soTrx.isSales())
		{
			return !creditMemo ? CustomerInvoice : CustomerCreditMemo;
		}
		else // purchase
		{
			return !creditMemo ? VendorInvoice : VendorCreditMemo;
		}
	}

	public static InvoiceDocBaseType ofDocBaseType(@NonNull final DocBaseType docBaseType)
	{
		return ofCode(docBaseType.getCode());
	}

	@Override
	public String getCode()
	{
		return getDocBaseType().getCode();
	}

	public boolean isSales()
	{
		return getSoTrx().isSales();
	}

	public boolean isPurchase()
	{
		return getSoTrx().isPurchase();
	}

	/**
	 * @return is Account Payable (AP), aka purchase
	 * @see #isPurchase()
	 */
	public boolean isAP() {return isPurchase();}

	public boolean isCustomerInvoice()
	{
		return this == CustomerInvoice;
	}

	public boolean isCustomerCreditMemo()
	{
		return this == CustomerCreditMemo;
	}
}
