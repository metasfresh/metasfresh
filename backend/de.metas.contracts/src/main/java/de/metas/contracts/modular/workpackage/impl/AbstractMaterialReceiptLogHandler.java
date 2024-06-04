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
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.computing.IComputingMethodHandler;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.workpackage.AbstractModularContractLogHandler;
import de.metas.contracts.modular.workpackage.IModularContractLogHandler;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.IMsgBL;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
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
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;

public abstract class AbstractMaterialReceiptLogHandler extends AbstractModularContractLogHandler
{
	private final static AdMessageKey MSG_ON_REVERSE_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.receiptReverseLogDescription");
	private final static AdMessageKey MSG_ON_COMPLETE_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.receiptCompleteLogDescription");

	@NonNull private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	@NonNull private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	@NonNull private final IMsgBL msgBL = Services.get(IMsgBL.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository;

	@NonNull @Getter private final String supportedTableName = I_M_InOutLine.Table_Name;

	@NonNull @Getter private final LogEntryDocumentType logEntryDocumentType = LogEntryDocumentType.MATERIAL_RECEIPT;

	@NonNull @Getter private final IComputingMethodHandler computingMethod;

	protected AbstractMaterialReceiptLogHandler(final @NonNull ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository,
			final @NonNull ModularContractService modularContractService,
			final @NonNull IComputingMethodHandler computingMethod)
	{
		super(modularContractService);
		this.modCntrInvoicingGroupRepository = modCntrInvoicingGroupRepository;
		this.computingMethod = computingMethod;
	}

	@Override
	public boolean applies(@NonNull final CreateLogRequest request)
	{
		final TableRecordReference tableRef = request.getRecordRef();
		if (!tableRef.tableNameEqualsTo(I_M_InOutLine.Table_Name))
		{
			return false;
		}
		final I_M_InOut inOut = inOutDAO.getByLineIdInTrx(InOutLineId.ofRepoId(tableRef.getRecord_ID()));

		return !inOut.isSOTrx();
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(
			@NonNull final IModularContractLogHandler.CreateLogRequest request)
	{
		final InOutLineId receiptLineId = getReceiptLineId(request.getRecordRef());
		final I_M_InOutLine receiptLineRecord = inOutBL.getLineByIdInTrx(receiptLineId);
		final I_M_InOut receiptRecord = inOutBL.getById(InOutId.ofRepoId(receiptLineRecord.getM_InOut_ID()));
		final I_C_Flatrate_Term contractRecord = flatrateDAO.getById(request.getContractId());
		final Quantity quantity = inOutBL.getQtyEntered(receiptLineRecord);

		final ProductId productId = getProductId(request, receiptLineRecord);
		final String productName = productBL.getProductValueAndName(productId);
		final String description = msgBL.getBaseLanguageMsg(MSG_ON_COMPLETE_DESCRIPTION, productName, quantity);

		final boolean isBillable = getLogEntryContractType().isInterimContractType()
				? receiptRecord.isInterimInvoiceable()
				: true;

		final LocalDateAndOrgId transactionDate = extractMovementDate(receiptRecord);

		final YearAndCalendarId yearAndCalendarId = request.getModularContractSettings().getYearAndCalendarId();
		final InvoicingGroupId invoicingGroupId = modCntrInvoicingGroupRepository.getInvoicingGroupIdFor(productId, yearAndCalendarId)
				.orElse(null);

		final ProductPrice contractSpecificPrice = getContractSpecificPriceWithFlags(request).toProductPrice();

		return ExplainedOptional.of(LogEntryCreateRequest.builder()
				.contractId(request.getContractId())
				.productId(productId)
				.referencedRecord(request.getRecordRef())
				.collectionPointBPartnerId(BPartnerId.ofRepoId(receiptRecord.getC_BPartner_ID()))
				.producerBPartnerId(BPartnerId.ofRepoId(receiptRecord.getC_BPartner_ID()))
				.invoicingBPartnerId(BPartnerId.ofRepoId(contractRecord.getBill_BPartner_ID()))
				.warehouseId(WarehouseId.ofRepoId(receiptRecord.getM_Warehouse_ID()))
				.documentType(getLogEntryDocumentType())
				.contractType(getLogEntryContractType())
				.soTrx(SOTrx.PURCHASE)
				.processed(false)
				.quantity(quantity)
				.transactionDate(transactionDate)
				.year(yearAndCalendarId.yearId())
				.description(description)
				.modularContractTypeId(request.getTypeId())
				.configModuleId(request.getConfigId().getModularContractModuleId())
				.productName(request.getProductName())
				.invoicingGroupId(invoicingGroupId)
				.isBillable(isBillable)
				.priceActual(contractSpecificPrice)
				.amount(contractSpecificPrice.computeAmount(quantity, uomConversionBL))
				.build());
	}

	@NonNull
	private LocalDateAndOrgId extractMovementDate(final I_M_InOut receiptRecord)
	{
		return LocalDateAndOrgId.ofTimestamp(receiptRecord.getMovementDate(), OrgId.ofRepoId(receiptRecord.getAD_Org_ID()), orgDAO::getTimeZone);
	}

	protected final InOutLineId getReceiptLineId(final TableRecordReference recordRef)
	{
		return recordRef.getIdAssumingTableName(I_M_InOutLine.Table_Name, InOutLineId::ofRepoId);
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(final @NonNull IModularContractLogHandler.CreateLogRequest request)
	{
		final InOutLineId inOutLineId = getReceiptLineId(request.getRecordRef());
		final I_M_InOutLine inOutLineRecord = inOutBL.getLineByIdInTrx(inOutLineId);

		final Quantity quantity = inOutBL.getQtyEntered(inOutLineRecord);

		final ProductId productId = ProductId.ofRepoId(inOutLineRecord.getM_Product_ID());
		final String productName = productBL.getProductValueAndName(productId);
		final String description = msgBL.getBaseLanguageMsg(MSG_ON_REVERSE_DESCRIPTION, productName, quantity);

		return ExplainedOptional.of(LogEntryReverseRequest.builder()
				.referencedModel(request.getRecordRef())
				.flatrateTermId(request.getContractId())
				.description(description)
				.logEntryContractType(getLogEntryContractType())
				.contractModuleId(request.getModularContractModuleId())
				.build());
	}

	@NonNull
	protected ProductId getProductId(
			@NonNull final IModularContractLogHandler.CreateLogRequest request,
			@NonNull final I_M_InOutLine receiptLineRecord)
	{
		return request.getProductId();
	}
}
