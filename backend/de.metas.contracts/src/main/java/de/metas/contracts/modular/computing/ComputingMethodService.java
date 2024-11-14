/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.modular.computing;

import com.google.common.collect.ImmutableSet;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_I_ModCntr_Log;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.ModularContract_Constants;
import de.metas.contracts.modular.log.ModularContractLogEntriesList;
import de.metas.contracts.modular.log.ModularContractLogQuery;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.contracts.modular.settings.ModularContractModuleId;
import de.metas.contracts.modular.settings.ModularContractSettingsService;
import de.metas.contracts.modular.settings.ModuleConfig;
import de.metas.currency.CurrencyPrecision;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutLineId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lang.SOTrx;
import de.metas.money.Money;
import de.metas.product.IProductBL;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.shippingnotification.model.I_M_Shipping_NotificationLine;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_InventoryLine;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.model.I_PP_Cost_Collector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static de.metas.contracts.modular.ModelAction.COMPLETED;
import static de.metas.contracts.modular.ModelAction.RECREATE_LOGS;
import static de.metas.contracts.modular.ModularContract_Constants.MSG_REACTIVATE_NOT_ALLOWED;

@Service
@RequiredArgsConstructor
public class ComputingMethodService
{
	private static final AdMessageKey MSG_ERROR_DOC_ACTION_NOT_ALLOWED_PROCESSED_LOGS = AdMessageKey.of("de.metas.contracts.modular.calculation.CalculationMethodService.DocActionNotAllowedForProcessedLogsError");

	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);
	@NonNull private final IInOutDAO inoutDao = Services.get(IInOutDAO.class);
	@NonNull private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);

	@NonNull private final ModularContractLogService contractLogService;
	@NonNull private final ModularContractSettingsService modularContractSettingsService;

	public static ComputingMethodService newInstanceForJUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new ComputingMethodService(ModularContractLogService.newInstanceForJUnitTesting(),
										  ModularContractSettingsService.newInstanceForJUnitTesting());
	}

	public void validateAction(@NonNull final TableRecordReference recordRef, @NonNull final ModelAction action)
	{
		if (!(action.equals(COMPLETED) || action.equals(RECREATE_LOGS)))
		{
			contractLogService.throwErrorIfProcessedLogsExistForRecord(recordRef, MSG_ERROR_DOC_ACTION_NOT_ALLOWED_PROCESSED_LOGS);
		}

		switch (recordRef.getTableName())
		{
			case I_M_InOutLine.Table_Name ->
			{
				switch (action)
				{
					case COMPLETED, REVERSED, RECREATE_LOGS -> {}
					case REACTIVATED ->
					{
						final SOTrx soTrx = SOTrx.ofBoolean(inoutDao.getByLineIdInTrx(InOutLineId.ofRepoId(recordRef.getRecord_ID())).isSOTrx());
						if (soTrx.isSales())
						{
							throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_NOT_ALLOWED);
						}
					}
					case VOIDED -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_NOT_ALLOWED);
					default -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED);
				}
			}
			case I_C_OrderLine.Table_Name ->
			{
				switch (action)
				{
					case COMPLETED, RECREATE_LOGS -> {}
					case REACTIVATED, REVERSED, VOIDED -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_NOT_ALLOWED);
					default -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED);
				}
			}
			case I_C_Flatrate_Term.Table_Name, I_PP_Cost_Collector.Table_Name ->
			{
				switch (action)
				{
					case COMPLETED, RECREATE_LOGS -> {}
					default -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED);
				}
			}
			case I_I_ModCntr_Log.Table_Name ->
			{
				switch (action)
				{
					case COMPLETED, REVERSED -> {}
					default -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED);
				}
			}
			case I_M_InventoryLine.Table_Name ->
			{
				switch (action)
				{
					case COMPLETED, REVERSED, VOIDED, RECREATE_LOGS -> {}
					case REACTIVATED -> throw new AdempiereException(MSG_REACTIVATE_NOT_ALLOWED);
					default -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED);
				}
			}
			case I_M_Shipping_NotificationLine.Table_Name, I_C_InvoiceLine.Table_Name ->
			{
				switch (action)
				{
					case COMPLETED, REVERSED, RECREATE_LOGS -> {}
					default -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED);
				}
			}
			default -> throw new AdempiereException("Table " + recordRef.getTableName() + " isn't supported");
		}
	}

	@NonNull
	public ModularContractLogEntriesList retrieveLogsForCalculation(@NonNull final ComputingRequest request)
	{
		final ModularContractLogEntriesList logs = contractLogService.getModularContractLogEntries(
				ModularContractLogQuery.builder()
						.flatrateTermId(request.getFlatrateTermId())
						.contractModuleId(request.getModularContractModuleId())
						.processed(false)
						.billable(true)
						.lockOwner(request.getLockOwner())
						.build());

		if (!logs.isEmpty())
		{
			logs.assertSingleProductId(request.getProductId());
		}
		return logs;

	}

	public Quantity getQtySumInStockUOM(@NonNull final ModularContractLogEntriesList logs)
	{
		final UomId stockUOMId = productBL.getStockUOMId(logs.getSingleProductId());
		return logs.getQtySum(stockUOMId, uomConversionBL);
	}

	@NonNull
	public Quantity getQtyXStorageDaysSum(@NonNull final ModularContractLogEntriesList logs, @NonNull final UomId targetUomId)
	{
		return logs.getQtyXStorageDaysSum(targetUomId, uomConversionBL);
	}

	public Quantity getQtySum(@NonNull final ModularContractLogEntriesList logs, @NonNull final UomId targetUomId)
	{
		return logs.getQtySum(targetUomId, uomConversionBL);
	}

	@NonNull
	public ComputingResponse toZeroResponse(final @NotNull ComputingRequest request)
	{
		final UomId stockUOMId = productBL.getStockUOMId(request.getProductId());
		return ComputingResponse.builder()
				.ids(ImmutableSet.of())
				.price(ProductPrice.builder()
							   .productId(request.getProductId())
							   .money(Money.zero(request.getCurrencyId()))
							   .uomId(stockUOMId)
							   .build())
				.qty(Quantitys.zero(stockUOMId))
				.build();
	}

	@NonNull
	public ProductPrice productPriceToUOM(@NonNull final ProductPrice priceWithPriceUOM, @NonNull final UomId stockUOMId)
	{
		// use 12 digit precision, because it will be rounded on IC creation according to priceList precision
		final CurrencyPrecision precision = CurrencyPrecision.ofInt(12);
		return priceWithPriceUOM.convertToUom(stockUOMId, precision, uomConversionBL);
	}

	public boolean isFinalInvoiceLineForComputingMethod(
			@NonNull final InvoiceLineId invoiceLineId,
			@NonNull final ComputingMethodType computingMethodType)
	{
		return isFinalInvoiceLineForComputingMethod(invoiceLineId, ImmutableSet.of(computingMethodType));
	}

	public boolean isFinalInvoiceLineForComputingMethod(
			@NonNull final InvoiceLineId invoiceLineId,
			@NonNull final Collection<ComputingMethodType> computingMethodTypes)
	{
		return isFinalInvoiceLineForComputingMethod(invoiceLineId, computingMethodTypes, null);
	}

	public boolean isFinalInvoiceLineForComputingMethod(
			@NonNull final InvoiceLineId invoiceLineId,
			@NonNull final Collection<ComputingMethodType> computingMethodTypes,
			@Nullable final ColumnOption columnOption)
	{
		final I_C_InvoiceLine invoiceLineRecord = invoiceBL.getLineById(invoiceLineId);
		if(!invoiceBL.isFinalInvoiceOrFinalCreditMemo(InvoiceId.ofRepoId(invoiceLineRecord.getC_Invoice_ID())))
		{
			return false;
		}
		final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandDAO.retrieveIcForIl(invoiceLineRecord);

		if (invoiceCandidates.isEmpty())
		{
			return false;
		}

		final ModularContractModuleId modularContractModuleId = ModularContractModuleId.ofRepoIdOrNull(CollectionUtils.extractSingleElement(invoiceCandidates, I_C_Invoice_Candidate::getModCntr_Module_ID));
		if(modularContractModuleId == null)
		{
			return false;
		}
		final ModuleConfig moduleConfig = modularContractSettingsService.getByModuleId(modularContractModuleId);
		return moduleConfig.isMatchingAnyOf(computingMethodTypes) && (columnOption == null || moduleConfig.isMatching(columnOption));
	}
}
