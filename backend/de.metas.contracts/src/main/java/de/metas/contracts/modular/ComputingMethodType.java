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

import de.metas.contracts.model.X_ModCntr_Type;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

@AllArgsConstructor
public enum ComputingMethodType implements ReferenceListAwareEnum
{
	INTERIM_CONTRACT(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_Interim_Contract),
	INVENTORY_LINE_MODULAR(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_InventoryLine_Modular),
	MATERIAL_RECEIPT_LINE_INTERIM(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_MaterialReceiptLine_Interim),
	MATERIAL_RECEIPT_LINE_MODULAR(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_MaterialReceiptLine_Modular),
	PPCOSTCOLLECTOR_MODULAR(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_PPCostCollector_Modular),
	PURCHASE_INVOICE_LINE_INTERIM(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_PurchaseInvoiceLine_Interim),
	PURCHASE_MODULAR_CONTRACT(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_PurchaseModularContract),
	PURCHASE_ORDER_LINE_MODULAR(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_PurchaseOrderLine_Modular),
	SO_LINE_FOR_PO_MODULAR(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_SOLineForPO_Modular),
	SALES_CONTRACT_PRO_FORMA_MODULAR(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_SalesContractProForma_Modular),
	SALES_INVOICE_LINE_MODULAR(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_SalesInvoiceLine_Modular),
	SALES_MODULAR_CONTRACT(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_SalesModularContract),
	SALES_ORDER_LINE_MODULAR(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_SalesOrderLine_Modular),
	SALES_ORDER_LINE_PRO_FORMA_MODULAR(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_SalesOrderLineProForma_Modular),
	SALES_ORDER_LINE_PRO_FORMA_PO_MODULAR(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_SalesOrderLineProFormaPO_Modular),
	SHIPMENT_LINE_FOR_PO_MODULAR(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_ShipmentLineForPO_Modular),
	SHIPMENT_LINE_FOR_SO_MODULAR(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_ShipmentLineForSO_Modular),
	SHIPPING_NOTIFICATION_FOR_PURCHASE_MODULAR(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_ShippingNotificationForPurchase_Modular),
	SHIPPING_NOTIFICATION_FOR_SALES_MODULAR(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_ShippingNotificationForSales_Modular),
	IMPORT_LOG(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_ImportLog),
	//TODO deactivate/remove old and add new in refList
	RECEIPT("Receipt"),
	INTERIM("Interim");

	@Getter
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

	private static final ReferenceListAwareEnums.ValuesIndex<ComputingMethodType> index = ReferenceListAwareEnums.index(values());
}
