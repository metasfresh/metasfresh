package de.metas.document;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Value
@JsonIncludeProperties({ "docBaseType", "docSubType" })
public class DocBaseAndSubType
{
	public static DocBaseAndSubType of(@NonNull final DocBaseType docBaseType)
	{
		return interner.intern(new DocBaseAndSubType(docBaseType, DocSubType.ANY));
	}

	public static DocBaseAndSubType of(@NonNull final String docBaseType, @Nullable final String docSubType)
	{
		return interner.intern(new DocBaseAndSubType(DocBaseType.ofCode(docBaseType), DocSubType.ofNullableCode(docSubType)));
	}

	public static DocBaseAndSubType of(@NonNull final DocBaseType docBaseType, @Nullable final String docSubType)
	{
		return interner.intern(new DocBaseAndSubType(docBaseType, DocSubType.ofNullableCode(docSubType)));
	}

	public static DocBaseAndSubType of(@NonNull final DocBaseType docBaseType, @NonNull final DocSubType docSubType)
	{
		return interner.intern(new DocBaseAndSubType(docBaseType, docSubType));
	}

	private static final Interner<DocBaseAndSubType> interner = Interners.newStrongInterner();

	@NonNull DocBaseType docBaseType;
	@NonNull DocSubType docSubType;

	private DocBaseAndSubType(
			@NonNull final DocBaseType docBaseType,
			@NonNull final DocSubType docSubType)
	{
		this.docBaseType = docBaseType;
		this.docSubType = docSubType;
	}

	// DocBaseAndSubTypeChecks
	public boolean isSalesInvoice() { return docBaseType.isSalesInvoice() && docSubType.isNone(); }
	public boolean isFinalInvoice() { return docBaseType.isPurchaseInvoice() && docSubType.isFinalInvoice(); }
	public boolean isFinalCreditMemo() { return docBaseType.isPurchaseCreditMemo() && docSubType.isFinalCreditMemo(); }
	public boolean isInterimInvoice() { return docBaseType.isPurchaseInvoice() && docSubType.IsInterimInvoice(); }
	public boolean isDefinitiveInvoice() { return docBaseType.isPurchaseInvoice() && docSubType.isDefinitiveInvoice(); }
	public boolean isDefinitiveCreditMemo() { return docBaseType.isPurchaseInvoice() && docSubType.isDefinitiveCreditMemo(); }
	public boolean isSalesFinalInvoice() { return docBaseType.isSalesInvoice() && docSubType.isFinalInvoice(); }
	public boolean isSalesFinalCreditMemo() { return docBaseType.isSalesCreditMemo() && docSubType.isFinalCreditMemo(); }
	public boolean isProformaSO() { return docBaseType.isSalesOrder() && isProformaSubType(); }
	public boolean isProformaShipment() { return docBaseType.isShipment() && isProformaSubType(); }
	public boolean isProformaShippingNotification() { return docBaseType.isShippingNotification() && isProformaSubType(); }
	public boolean isPrepaySO() { return docBaseType.isSalesOrder() && docSubType.isPrepay(); }
	public boolean isInternalVendorInvoice() { return docBaseType.isPurchaseOrder() && docSubType.isInternalVendorInvoice(); }
	public boolean isDeliveryInstruction() { return docBaseType.isShipperTransportation() && docSubType.isDeliveryInstruction(); }

	// SubTypeChecks
	public boolean isProformaSubType() { return docSubType.isProforma(); }


}
