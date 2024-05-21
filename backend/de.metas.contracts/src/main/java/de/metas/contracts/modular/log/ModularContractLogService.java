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

import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_ModCntr_Log;
import de.metas.contracts.modular.workpackage.ModularContractLogHandlerRegistry;
import de.metas.i18n.AdMessageKey;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.lock.api.LockOwner;
import de.metas.order.OrderLineId;
import de.metas.process.PInstanceId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ModularContractLogService
{
	private static final AdMessageKey MSG_ERROR_DOCUMENT_LINE_DELETION = AdMessageKey.of("documentLineDeletionErrorBecauseOfRelatedModuleContractLog");
	private static final String PRODUCT_PRICE_NULL_ASSUMPTION_ERROR_MSG = "ProductPrices of billable modular contract logs shouldn't be null";
	public static final AdTableId INVOICE_LINE_TABLE_ID = AdTableId.ofRepoId(Services.get(IADTableDAO.class).retrieveTableId(I_C_InvoiceLine.Table_Name));

	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	@NonNull private final ModularContractLogDAO modularContractLogDAO;

	public void throwErrorIfLogExistsForDocumentLine(@NonNull final TableRecordReference tableRecordReference)
	{
		if (modularContractLogDAO.hasAnyModularLogs(tableRecordReference))
		{
			throw new AdempiereException(MSG_ERROR_DOCUMENT_LINE_DELETION);
		}
	}

	public void changeBillableStatus(
			@NonNull final ModularContractLogQuery query,
			final boolean isBillable)
	{
		modularContractLogDAO.changeBillableStatus(query, isBillable);
	}

	@NonNull
	public Optional<ModularContractLogEntry> getLastModularContractLog(
			@NonNull final FlatrateTermId modularFlatrateTermId,
			@NonNull final OrderLineId orderLineId)
	{
		return modularContractLogDAO.getLastModularContractLog(modularFlatrateTermId, orderLineId);
	}

	public void throwErrorIfProcessedLogsExistForRecord(
			@NonNull final TableRecordReference tableRecordReference,
			@NonNull final AdMessageKey errorMessage)
	{
		if (hasAnyProcessedLogs(tableRecordReference))
		{
			throw new AdempiereException(errorMessage);
		}
	}

	private boolean hasAnyProcessedLogs(@NonNull final TableRecordReference tableRecordReference)
	{
		final ModularContractLogQuery query = ModularContractLogQuery.builder()
				.referenceSet(TableRecordReferenceSet.of(tableRecordReference))
				.processed(true)
				.build();

		return modularContractLogDAO.anyMatch(query);
	}

	public void setICProcessed(
			@NonNull final ModularContractLogQuery query,
			@NonNull final InvoiceCandidateId invoiceCandidateId)
	{
		modularContractLogDAO.setICProcessed(query, invoiceCandidateId);
	}

	@NonNull
	public ModularContractLogEntriesList getModularContractLogEntries(@NonNull final ModularContractLogQuery query)
	{
		return modularContractLogDAO.getModularContractLogEntries(query);
	}

	@Nullable
	public PInstanceId getModularContractLogEntrySelection(@NonNull final ModularContractLogQuery query)
	{
		return modularContractLogDAO.getModularContractLogEntrySelection(query);
	}

	@NonNull
	public IQueryFilter<I_ModCntr_Log> getModularContractLogEntryFilter(@NonNull final ModularContractLogQuery query)
	{
		return modularContractLogDAO.getModularContractLogEntryFilter(query);
	}

	@NonNull
	public Stream<ModularContractLogEntry> streamModularContractLogEntries(@NonNull final ModularContractLogQuery query)
	{
		return modularContractLogDAO.streamModularContractLogEntries(query);
	}

	@NonNull
	public ImmutableSet<FlatrateTermId> getModularContractIds(@NonNull final ModularContractLogQuery query)
	{
		return modularContractLogDAO.getModularContractIds(query);
	}

	@Nullable
	public PInstanceId getSelection(@NonNull final LockOwner lockOwner)
	{
		return modularContractLogDAO.getSelection(lockOwner);
	}

	public void validateLogPrices(@NonNull final ModularContractLogEntriesList logs)
	{
		if (logs.isEmpty())
		{
			return;
		}

		final ProductPrice productPriceToMatch = logs.getFirstPriceActual();
		Check.assumeNotNull(productPriceToMatch, PRODUCT_PRICE_NULL_ASSUMPTION_ERROR_MSG);
		logs.forEach(log -> validateLogPrice(log.getPriceActual(), productPriceToMatch));
	}

	private void validateLogPrice(@Nullable final ProductPrice productPrice, @NonNull final ProductPrice productPriceToMatch)
	{
		Check.assumeNotNull(productPrice, PRODUCT_PRICE_NULL_ASSUMPTION_ERROR_MSG);
		Check.assume(productPrice.isEqualByComparingTo(productPriceToMatch), "ProductPrices of billable modular contract logs should be identical", productPrice, productPriceToMatch);
	}

	public StockQtyAndUOMQty getStockQtyAndQtyInUOM(@NonNull final ModularContractLogEntriesList logs, @NonNull final UomId targetUomId)
	{
		final Quantity qtyInTargetUOM = logs.getQtySum(targetUomId, uomConversionBL);

		final ProductId productId = logs.getSingleProductId();
		final UomId stockUomId = productBL.getStockUOMId(productId);
		final Quantity qtyInStockUOM = uomConversionBL.convertQuantityTo(qtyInTargetUOM, productId, stockUomId);

		return StockQtyAndUOMQty.builder()
				.productId(productId)
				.stockQty(qtyInStockUOM)
				.uomQty(qtyInTargetUOM)
				.build();
	}

	public void updatePriceAndAmount(@NonNull final ModCntrLogPriceUpdateRequest request, @NonNull final ModularContractLogHandlerRegistry logHandlerRegistry)
	{
		modularContractLogDAO.save(modularContractLogDAO.getModularContractLogEntries(ModularContractLogQuery.builder()
						.flatrateTermId(request.flatrateTermId())
						.processed(false)
						.contractModuleId(request.modularContractModuleId())
								.excludedReferencedTableId(INVOICE_LINE_TABLE_ID)
						.build())
				.withPriceActualAndCalculateAmount(request.unitPrice(), uomConversionBL, logHandlerRegistry));
	}

}
