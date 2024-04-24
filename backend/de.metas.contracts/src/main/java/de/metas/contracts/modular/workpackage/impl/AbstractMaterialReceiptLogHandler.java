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

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.workpackage.IModularContractLogHandler;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.IMsgBL;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.lang.SOTrx;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;

@RequiredArgsConstructor
public abstract class AbstractMaterialReceiptLogHandler implements IModularContractLogHandler
{
	private final static AdMessageKey MSG_ON_REVERSE_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.receiptReverseLogDescription");
	private final static AdMessageKey MSG_ON_COMPLETE_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.receiptCompleteLogDescription");

	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	@NonNull
	private final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository;

	@NonNull
	private final ModularContractService modularContractService;

	@Override
	public @NonNull String getSupportedTableName()
	{
		return I_M_InOutLine.Table_Name;
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(
			@NonNull final IModularContractLogHandler.CreateLogRequest request)
	{
		final TableRecordReference recordRef = request.getRecordRef();
		final I_M_InOutLine inOutLineRecord = inOutBL.getLineByIdInTrx(InOutLineId.ofRepoId(recordRef.getRecordIdAssumingTableName(getSupportedTableName())));
		final I_M_InOut inOutRecord = inOutBL.getById(InOutId.ofRepoId(inOutLineRecord.getM_InOut_ID()));
		final I_C_Flatrate_Term flatrateTermRecord = flatrateDAO.getById(request.getContractId());
		final Quantity quantity = inOutBL.getQtyEntered(inOutLineRecord);

		final ProductId productId = ProductId.ofRepoId(inOutLineRecord.getM_Product_ID());
		final String productName = productBL.getProductValueAndName(productId);
		final String description = msgBL.getBaseLanguageMsg(MSG_ON_COMPLETE_DESCRIPTION, productName, quantity);

		final boolean isBillable = getLogEntryContractType().isInterimContractType()
				? inOutRecord.isInterimInvoiceable()
				: true;

		final LocalDateAndOrgId transactionDate = LocalDateAndOrgId.ofTimestamp(inOutRecord.getMovementDate(),
																				OrgId.ofRepoId(inOutLineRecord.getAD_Org_ID()),
																				orgDAO::getTimeZone);

		final InvoicingGroupId invoicingGroupId = modCntrInvoicingGroupRepository.getInvoicingGroupIdFor(productId, transactionDate.toInstant(orgDAO::getTimeZone))
				.orElse(null);

		final ProductPrice contractSpecificPrice = modularContractService.getContractSpecificPrice(request.getConfigId().getModularContractModuleId(),
																								   request.getContractId());

		return ExplainedOptional.of(LogEntryCreateRequest.builder()
											.contractId(request.getContractId())
											.productId(ProductId.ofRepoId(inOutLineRecord.getM_Product_ID()))
											.referencedRecord(TableRecordReference.of(I_M_InOutLine.Table_Name, inOutLineRecord.getM_InOutLine_ID()))
											.collectionPointBPartnerId(BPartnerId.ofRepoId(inOutRecord.getC_BPartner_ID()))
											.producerBPartnerId(BPartnerId.ofRepoId(inOutRecord.getC_BPartner_ID()))
											.invoicingBPartnerId(BPartnerId.ofRepoId(flatrateTermRecord.getBill_BPartner_ID()))
											.warehouseId(WarehouseId.ofRepoId(inOutRecord.getM_Warehouse_ID()))
											.documentType(LogEntryDocumentType.MATERIAL_RECEIPT)
											.contractType(getLogEntryContractType())
											.soTrx(SOTrx.PURCHASE)
											.processed(false)
											.quantity(quantity)
											.transactionDate(transactionDate)
											.year(request.getModularContractSettings().getYearAndCalendarId().yearId())
											.description(description)
											.modularContractTypeId(request.getTypeId())
											.configId(request.getConfigId())
											.productName(request.getProductName())
											.invoicingGroupId(invoicingGroupId)
											.isBillable(isBillable)
											.priceActual(contractSpecificPrice)
											.amount(contractSpecificPrice.computeAmount(quantity))
											.build());
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(final @NonNull IModularContractLogHandler.HandleLogsRequest request)
	{
		final InOutLineId inOutLineId = InOutLineId.ofRepoId(request.getTableRecordReference().getRecordIdAssumingTableName(getSupportedTableName()));
		final I_M_InOutLine inOutLineRecord = inOutBL.getLineByIdInTrx(inOutLineId);

		final Quantity quantity = inOutBL.getQtyEntered(inOutLineRecord);

		final ProductId productId = ProductId.ofRepoId(inOutLineRecord.getM_Product_ID());
		final String productName = productBL.getProductValueAndName(productId);
		final String description = msgBL.getBaseLanguageMsg(MSG_ON_REVERSE_DESCRIPTION, productName, quantity);

		return ExplainedOptional.of(LogEntryReverseRequest.builder()
											.referencedModel(request.getTableRecordReference())
											.flatrateTermId(request.getContractId())
											.description(description)
											.logEntryContractType(getLogEntryContractType())
											.contractModuleId(request.getContractInfo()
																		 .getModularContractSettings()
																		 .getModuleConfigOrError(request.getComputingMethodType(), productId)
																		 .getId().getModularContractModuleId())
											.build());
	}
}
