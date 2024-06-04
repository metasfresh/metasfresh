/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.contracts.modular;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.contracts.model.X_ModCntr_Type;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

@Getter
@AllArgsConstructor
public enum ComputingMethodType implements ReferenceListAwareEnum
{

	@Deprecated INVENTORY_LINE_MODULAR_DEPRECATED(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_InventoryLine_Modular_NotUsed),
	@Deprecated MATERIAL_RECEIPT_LINE_INTERIM_DEPRECATED(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_MaterialReceiptLine_Interim_NotUsed),
	@Deprecated MATERIAL_RECEIPT_LINE_MODULAR_DEPRECATED(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_MaterialReceiptLine_Modular_NotUsed),
	@Deprecated PPCOSTCOLLECTOR_MODULAR_DEPRECATED(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_PPCostCollector_Modular_NotUsed),
	@Deprecated PURCHASE_INVOICE_LINE_INTERIM_DEPRECATED(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_PurchaseInvoiceLine_Interim_NotUsed),
	@Deprecated PURCHASE_MODULAR_CONTRACT_DEPRECATED(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_PurchaseModularContract_NotUsed),
	@Deprecated PURCHASE_ORDER_LINE_MODULAR_DEPRECATED(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_PurchaseOrderLine_Modular_NotUsed),
	@Deprecated SALES_CONTRACT_PRO_FORMA_MODULAR_DEPRECATED(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_SalesContractProForma_Modular_NotUsed),
	@Deprecated SALES_ORDER_LINE_PRO_FORMA_PO_MODULAR_DEPRECATED(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_SalesOrderLineProFormaPO_Modular_NotUsed),
	@Deprecated SHIPPING_NOTIFICATION_FOR_PURCHASE_MODULAR_DEPRECATED(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_ShippingNotificationForPurchase_Modular_NotUsed),
	@Deprecated SHIPPING_NOTIFICATION_FOR_SALES_MODULAR_DEPRECATED(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_ShippingNotificationForSales_Modular_NotUsed),
	@Deprecated SO_LINE_FOR_PO_MODULAR_DEPRECATED(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_SOLineForPO_Modular_NotUsed),
	@Deprecated SALES_INVOICE_LINE_MODULAR_DEPRECATED(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_SalesInvoiceLine_Modular_NotUsed),
	@Deprecated SALES_MODULAR_CONTRACT_DEPRECATED(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_SalesModularContract_NotUsed),
	@Deprecated SALES_ORDER_LINE_MODULAR_DEPRECATED(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_SalesOrderLine_Modular_NotUsed),
	@Deprecated SALES_ORDER_LINE_PRO_FORMA_MODULAR_DEPRECATED(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_SalesOrderLineProForma_Modular_NotUsed),
	@Deprecated SHIPMENT_LINE_FOR_PO_MODULAR_DEPRECATED(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_ShipmentLineForPO_Modular_NotUsed),
	@Deprecated SHIPMENT_LINE_FOR_SO_MODULAR_DEPRECATED(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_ShipmentLineForSO_Modular_NotUsed),
	@Deprecated IMPORT_LOG_DEPRECATED(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_ImportLog_NotUsed),

	INTERIM_CONTRACT(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_Interim_Contract),
	Receipt(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_Receipt),
	InformativeLogs(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_InformativeLogs),
	DefinitiveInvoiceRawProduct(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_DefinitiveInvoiceRawProduct),
	DefinitiveInvoiceProcessedProduct(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_DefinitiveInvoiceProcessedProduct),
	SalesOnRawProduct(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_SalesOnRawProduct),
	SalesOnProcessedProduct(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_SalesOnProcessedProduct),
	CoProduct(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_CoProduct),
	AddValueOnRawProduct(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_AddValueOnRawProduct),
	AddValueOnProcessedProduct(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_AddValueOnProcessedProduct),
	SubtractValueOnRawProduct(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_SubtractValueOnRawProduct),
	ReductionCalibration(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_ReductionCalibration),
	StorageCost(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_StorageCost),
	AverageAddedValueOnShippedQuantity(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_AverageAddedValueOnShippedQuantity),
	AddValueOnInterim(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_AddValueOnInterim),
	SubtractValueOnInterim(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_SubtractValueOnInterim),
	AvCo("AvCo"),	// Methods not needed in this increment
	SvCo("SvCo"),
	SvProcessed("SvProcessed");

	public static final ImmutableList<ComputingMethodType> DEFINITIVE_INVOICE_SPECIFIC_METHODS = ImmutableList.of(DefinitiveInvoiceRawProduct,
			DefinitiveInvoiceProcessedProduct);
	public static final ImmutableList<ComputingMethodType> FINAL_INVOICE_SPECIFIC_METHODS = ImmutableList.of(INTERIM_CONTRACT,
			Receipt,
			SalesOnRawProduct,
			SalesOnProcessedProduct,
			CoProduct,
			AddValueOnRawProduct,
			AddValueOnProcessedProduct,
			SubtractValueOnRawProduct,
			ReductionCalibration,
			StorageCost,
			AverageAddedValueOnShippedQuantity,
			AddValueOnInterim,
			SubtractValueOnInterim,
			AvCo,
			SvCo,
			SvProcessed);

	public static final ImmutableList<ComputingMethodType> INTEREST_SPECIFIC_METHODS = ImmutableList.of(AddValueOnInterim,
			SubtractValueOnInterim);

	private static final ImmutableSet<ComputingMethodType> SUBTRACTED_VALUE_METHODS = ImmutableSet.of(
			SubtractValueOnRawProduct,
			SubtractValueOnInterim,
			SvCo,
			SvProcessed);

	private static final ReferenceListAwareEnums.ValuesIndex<ComputingMethodType> index = ReferenceListAwareEnums.index(values());

	@NonNull
	private final String code;

	@NonNull
	public static ComputingMethodType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static ComputingMethodType ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	public boolean isSubtractedValue()
	{
		return SUBTRACTED_VALUE_METHODS.contains(this);
	}
}
