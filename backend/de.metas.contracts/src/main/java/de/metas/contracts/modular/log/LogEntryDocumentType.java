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

package de.metas.contracts.modular.log;

import de.metas.contracts.model.X_ModCntr_Log;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

@AllArgsConstructor
public enum LogEntryDocumentType implements ReferenceListAwareEnum
{
	PURCHASE_ORDER(X_ModCntr_Log.MODCNTR_LOG_DOCUMENTTYPE_PurchaseOrder),
	SupplyAgreement(X_ModCntr_Log.MODCNTR_LOG_DOCUMENTTYPE_SupplyAgreement),
	MATERIAL_RECEIPT(X_ModCntr_Log.MODCNTR_LOG_DOCUMENTTYPE_MaterialReceipt),
	PRODUCTION(X_ModCntr_Log.MODCNTR_LOG_DOCUMENTTYPE_Production),
	CONTRACT_PREFINANCING(X_ModCntr_Log.MODCNTR_LOG_DOCUMENTTYPE_ContractPrefinancing),
	CONTRACT_SETTING(X_ModCntr_Log.MODCNTR_LOG_DOCUMENTTYPE_ContractSetting),
	SALES_ORDER(X_ModCntr_Log.MODCNTR_LOG_DOCUMENTTYPE_SalesOrder),
	SHIPMENT_DISPOSITION(X_ModCntr_Log.MODCNTR_LOG_DOCUMENTTYPE_ShipmentDisposition),
	SHIPMENT(X_ModCntr_Log.MODCNTR_LOG_DOCUMENTTYPE_Shipment),
	FINAL_SETTLEMENT(X_ModCntr_Log.MODCNTR_LOG_DOCUMENTTYPE_FinalSettlement),
	DEFINITIVE_FINAL_SETTLEMENT(X_ModCntr_Log.MODCNTR_LOG_DOCUMENTTYPE_DefinitiveFinalSettlement),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<LogEntryDocumentType> index = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	@NonNull
	public static LogEntryDocumentType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static LogEntryDocumentType ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}
}
