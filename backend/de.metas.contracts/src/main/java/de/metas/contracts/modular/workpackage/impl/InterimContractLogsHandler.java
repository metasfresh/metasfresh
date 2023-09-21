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

package de.metas.contracts.modular.workpackage.impl;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.contracts.modular.ModularContract_Constants;
import de.metas.contracts.modular.interim.logImpl.InterimContractHandler;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDeleteRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.log.ModularContractLogEntry;
import de.metas.contracts.modular.log.ModularContractLogService;
import de.metas.contracts.modular.workpackage.IModularContractLogHandler;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.Language;
import de.metas.i18n.TranslatableStrings;
import de.metas.lang.SOTrx;
import de.metas.order.OrderLineId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_InventoryLine;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class InterimContractLogsHandler implements IModularContractLogHandler<I_C_Flatrate_Term>
{
	private final static AdMessageKey MSG_ON_COMPLETE_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.interimContractCompleteLogDescription");

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

	private final ModularContractLogService modularContractLogService;
	private final InterimContractHandler contractHandler;

	@Override
	public LogAction getLogAction(@NonNull final HandleLogsRequest<I_C_Flatrate_Term> request)
	{
		return switch (request.getModelAction())
				{
					case COMPLETED -> LogAction.CREATE;
					case RECREATE_LOGS -> LogAction.RECOMPUTE;
					default -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED);
				};
	}

	@Override
	public BooleanWithReason doesRecordStateRequireLogCreation(@NonNull final I_C_Flatrate_Term model)
	{
		if (!DocStatus.ofCode(model.getDocStatus()).isCompleted())
		{
			return BooleanWithReason.falseBecause("The C_Flatrate_Term.DocStatus is " + model.getDocStatus());
		}

		return BooleanWithReason.TRUE;
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull final CreateLogRequest<I_C_Flatrate_Term> createLogRequest)
	{
		final I_C_Flatrate_Term flatrateTermRecord = createLogRequest.getHandleLogsRequest().getModel();

		final FlatrateTermId modularContractId = FlatrateTermId.ofRepoId(flatrateTermRecord.getModular_Flatrate_Term_ID());

		final Optional<ModularContractLogEntry> modularContractLogEntryOptional = modularContractLogService.getLastModularContractLog(
				modularContractId,
				OrderLineId.ofRepoId(flatrateTermRecord.getC_OrderLine_Term_ID()));

		if (modularContractLogEntryOptional.isEmpty())
		{
			return ExplainedOptional.emptyBecause("No ModularContractLogEntry found for"
														  + " modularContractId: " + modularContractId + ","
														  + " orderLineTermId: " + flatrateTermRecord.getC_OrderLine_Term_ID()
														  + "! No logs will be created");
		}

		final ModularContractLogEntry modularContractLogEntry = modularContractLogEntryOptional.get();

		final String productName = productBL.getProductValueAndName(modularContractLogEntry.getProductId());
		final String description = TranslatableStrings.adMessage(MSG_ON_COMPLETE_DESCRIPTION, productName, modularContractLogEntry.getQuantity())
				.translate(Language.getBaseAD_Language());
		
		return ExplainedOptional.of(LogEntryCreateRequest.builder()
											.contractId(modularContractId)
											.productId(ProductId.ofRepoId(flatrateTermRecord.getM_Product_ID()))
											.referencedRecord(TableRecordReference.of(I_C_Flatrate_Term.Table_Name, createLogRequest.getContractId()))
											.producerBPartnerId(modularContractLogEntry.getProducerBPartnerId())
											.invoicingBPartnerId(modularContractLogEntry.getInvoicingBPartnerId())
											.collectionPointBPartnerId(modularContractLogEntry.getCollectionPointBPartnerId())
											.warehouseId(modularContractLogEntry.getWarehouseId())
											.documentType(LogEntryDocumentType.CONTRACT_PREFINANCING)
											.contractType(LogEntryContractType.INTERIM)
											.soTrx(SOTrx.PURCHASE)
											.processed(false)
											.quantity(modularContractLogEntry.getQuantity())
											.amount(modularContractLogEntry.getAmount())
											.transactionDate(LocalDateAndOrgId.ofTimestamp(flatrateTermRecord.getStartDate(),
																						   OrgId.ofRepoId(flatrateTermRecord.getAD_Org_ID()),
																						   orgDAO::getTimeZone))
											.year(modularContractLogEntry.getYear())
											.description(description)
											.modularContractTypeId(createLogRequest.getTypeId())
											.configId(createLogRequest.getConfigId())
											.priceActual(flatrateBL.extractPriceActual(flatrateTermRecord))
											.build());
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(@NonNull final HandleLogsRequest<I_C_Flatrate_Term> handleLogsRequest)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public @NonNull IModularContractTypeHandler<I_C_Flatrate_Term> getModularContractTypeHandler()
	{
		return contractHandler;
	}

	@Override
	@NonNull
	public LogEntryDeleteRequest getDeleteRequestFor(@NonNull final HandleLogsRequest<I_C_Flatrate_Term> handleLogsRequest)
	{
		final I_C_Flatrate_Term interimContract = handleLogsRequest.getModel();
		final FlatrateTermId modularContractId = FlatrateTermId.ofRepoId(interimContract.getModular_Flatrate_Term_ID());

		return LogEntryDeleteRequest.builder()
				.referencedModel(handleLogsRequest.getModelRef())
				.flatrateTermId(modularContractId)
				.logEntryContractType(handleLogsRequest.getLogEntryContractType())
				.build();
	}
}
