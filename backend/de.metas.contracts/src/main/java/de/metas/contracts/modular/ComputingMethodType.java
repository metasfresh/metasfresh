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
	INTERIM_CONTRACT(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_Interim_Contract),
	Receipt(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_Receipt),
	PurchaseInformativeLogs(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_InformativeLogs),
	DefinitiveInvoiceRawProduct(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_DefinitiveInvoiceRawProduct),
	DefinitiveInvoiceProcessedProduct(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_DefinitiveInvoiceProcessedProduct),
	DefinitiveInvoiceStorageCost(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_DefinitiveInvoiceStorageCost),
	DefinitiveInvoiceAverageAVOnShippedQty(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_DefinitiveInvoiceAverageAVOnShippedQty),
	SalesOnRawProduct(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_SalesOnRawProduct),
	SalesOnProcessedProduct(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_SalesOnProcessedProduct),
	SalesOnRawProductShippedQty(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_SalesOnRawProductShippedQty),
	SalesOnProcessedProductShippedQty(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_SalesOnProcessedProductShippedQty),
	CoProduct(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_CoProduct),
	AddValueOnRawProduct(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_AddValueOnRawProduct),
	AddValueOnProcessedProduct(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_AddValueOnProcessedProduct),
	SubtractValueOnRawProduct(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_SubtractValueOnRawProduct),
	ReductionCalibration(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_ReductionCalibration),
	PurchaseStorageCost(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_StorageCost),
	PurchaseAverageAddedValueOnShippedQuantity(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_AverageAddedValueOnShippedQuantity),
	AddValueOnInterim(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_AddValueOnInterim),
	SubtractValueOnInterim(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_SubtractValueOnInterim),
	SalesStorageCost(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_SalesStorageCost),
	Sales(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_Sales),
	SalesAV(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_SalesAV),
	SalesAverageAddedValueOnShippedQuantity(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_SalesAverageAVOnShippedQty),
	SalesInformativeLogs(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_SalesInformativeLogs),
	AVReceiptUntilDate(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_AVReceiptUntilDate),
	SalesInterest(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_SalesInterest),

	// Methods not needed in this increment
	@Deprecated IMPORT_LOG_DEPRECATED(X_ModCntr_Type.MODULARCONTRACTHANDLERTYPE_ImportLog_NotUsed),
	AvCo("AvCo"),
	SvCo("SvCo"),
	SvProcessed("SvProcessed");

	public static final ImmutableSet<ComputingMethodType> INTERIM_INVOICE_SPECIFIC_METHODS = ImmutableSet.of(INTERIM_CONTRACT);

	public static final ImmutableSet<ComputingMethodType> DEFINITIVE_INVOICE_SPECIFIC_METHODS = ImmutableSet.of(
			DefinitiveInvoiceRawProduct,
			DefinitiveInvoiceProcessedProduct,
			DefinitiveInvoiceStorageCost,
			DefinitiveInvoiceAverageAVOnShippedQty
	);

	public static final ImmutableSet<ComputingMethodType> DEFINITIVE_INVOICE_SPECIFIC_SALES_METHODS = ImmutableSet.of(
			DefinitiveInvoiceRawProduct,
			DefinitiveInvoiceProcessedProduct
	);

	public static final ImmutableSet<ComputingMethodType> FINAL_INVOICE_SPECIFIC_METHODS = ImmutableSet.of(
			INTERIM_CONTRACT,
			Receipt,
			SalesOnRawProduct,
			SalesOnProcessedProduct,
			SalesOnRawProductShippedQty,
			SalesOnProcessedProductShippedQty,
			CoProduct,
			AddValueOnRawProduct,
			AddValueOnProcessedProduct,
			SubtractValueOnRawProduct,
			ReductionCalibration,
			PurchaseStorageCost,
			PurchaseAverageAddedValueOnShippedQuantity,
			AvCo,
			SvCo,
			SvProcessed,
			Sales,
			SalesAV,
			SalesStorageCost,
			SalesAverageAddedValueOnShippedQuantity,
			AVReceiptUntilDate,
			SalesInterest
	);

	public static final ImmutableSet<ComputingMethodType> PURCHASE_SALES_METHODS = ImmutableSet.of(
			SalesOnRawProduct,
			SalesOnProcessedProduct,
			SalesOnRawProductShippedQty,
			SalesOnProcessedProductShippedQty
	);

	public static final ImmutableSet<ComputingMethodType> PURCHASE_FINAL_INVOICE_SPECIFIC_METHODS = ImmutableSet.of(
			INTERIM_CONTRACT,
			Receipt,
			SalesOnRawProduct,
			SalesOnProcessedProduct,
			SalesOnRawProductShippedQty,
			SalesOnProcessedProductShippedQty,
			CoProduct,
			AddValueOnRawProduct,
			AddValueOnProcessedProduct,
			SubtractValueOnRawProduct,
			ReductionCalibration,
			PurchaseStorageCost,
			PurchaseAverageAddedValueOnShippedQuantity,
			AddValueOnInterim,
			SubtractValueOnInterim,
			AvCo,
			SvCo,
			SvProcessed,
			AVReceiptUntilDate
	);

	public static final ImmutableSet<ComputingMethodType> SALES_FINAL_INVOICE_SPECIFIC_METHODS = ImmutableSet.of(
			SalesStorageCost,
			Sales,
			SalesAV,
			SalesAverageAddedValueOnShippedQuantity,
			SalesInterest
	);

	public static final ImmutableSet<ComputingMethodType> INTEREST_SPECIFIC_METHODS = ImmutableSet.of(AddValueOnInterim,
			SubtractValueOnInterim
	);

	//If not all interim amt has been covered by shipping notifications, it is possible that some unprocessed logs exist for the AddValueOnInterim/SubtractValueOnInterim
	public static final ImmutableSet<ComputingMethodType> PURCHASE_FINAL_INVOICE_EXCEPT_INTEREST_SPECIFIC_METHODS = ImmutableSet.of(
			INTERIM_CONTRACT,
			Receipt,
			SalesOnRawProduct,
			SalesOnProcessedProduct,
			SalesOnRawProductShippedQty,
			SalesOnProcessedProductShippedQty,
			CoProduct,
			AddValueOnRawProduct,
			AddValueOnProcessedProduct,
			SubtractValueOnRawProduct,
			ReductionCalibration,
			PurchaseStorageCost,
			PurchaseAverageAddedValueOnShippedQuantity,
			AvCo,
			SvCo,
			SvProcessed
	);

	private static final ImmutableSet<ComputingMethodType> SUBTRACTED_VALUE_METHODS = ImmutableSet.of(
			SubtractValueOnRawProduct,
			SubtractValueOnInterim,
			SvCo,
			SvProcessed
	);

	public static final ImmutableSet<ComputingMethodType> INITIAL_PRICE_FROM_CONTRACT_METHODS = ImmutableSet.of(
			Receipt,
			SalesOnRawProduct,
			DefinitiveInvoiceRawProduct,
			INTERIM_CONTRACT,
			Sales
	);

	public static final ImmutableSet<ComputingMethodType> SCALE_PRICE_METHODS = ImmutableSet.of(
			PurchaseAverageAddedValueOnShippedQuantity,
			SalesAverageAddedValueOnShippedQuantity,
			DefinitiveInvoiceAverageAVOnShippedQty
	);

	public static final ImmutableSet<ComputingMethodType> AVERAGE_CONTRACT_SPECIFIC_PRICE_METHODS = ImmutableSet.of(
			SalesOnRawProductShippedQty,
			SalesOnProcessedProductShippedQty,
			DefinitiveInvoiceRawProduct,
			DefinitiveInvoiceProcessedProduct
	);

	public static final ImmutableSet<ComputingMethodType> NEED_UOM_MATCHING_SETTINGS_RAW_PRODUCT_METHODS = ImmutableSet.of(
			AddValueOnRawProduct,
			SubtractValueOnRawProduct,
			SalesAV,
			AVReceiptUntilDate
	);

	public static final ImmutableSet<ComputingMethodType> NEED_UOM_MATCHING_SETTINGS_PROCESSED_PRODUCT_METHODS = ImmutableSet.of(
			AddValueOnProcessedProduct
	);

	public static final ImmutableSet<ComputingMethodType> NEED_UOM_EACH_METHODS = ImmutableSet.of(
			SalesStorageCost,
			PurchaseStorageCost,
			DefinitiveInvoiceStorageCost,
			SalesInterest
	);


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

	public boolean isPurchaseFinalInvoiceSpecificMethod()
	{
		return PURCHASE_FINAL_INVOICE_SPECIFIC_METHODS.contains(this);
	}

	public boolean isInterestSpecificMethod() { return INTEREST_SPECIFIC_METHODS.contains(this); }
}
