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

package de.metas.contracts.modular.computing.tbd.salescontract.shipment;

import de.metas.bpartner.BPartnerId;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.log.ModularContractLogQuery;
import de.metas.contracts.modular.workpackage.AbstractModularContractLogHandler;
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
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Warehouse;
import org.springframework.stereotype.Component;

/**
 * @deprecated If needed, please move/use code in the new computing methods in package de.metas.contracts.modular.computing.salescontract
 */
@Deprecated
@Component
class ShipmentLineForSOLogHandler extends AbstractModularContractLogHandler
{
	private static final AdMessageKey MSG_INFO_SHIPMENT_SO_COMPLETED = AdMessageKey.of("de.metas.contracts.modular.impl.ShipmentLineForSOLogHandler.OnComplete.Description");
	private static final AdMessageKey MSG_INFO_SHIPMENT_SO_REVERSED = AdMessageKey.of("de.metas.contracts.modular.impl.ShipmentLineForSOLogHandler.OnReverse.Description");

	private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	@NonNull private final ModularContractLogDAO contractLogDAO;
	@NonNull private final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository;

	@Getter @NonNull private final ShipmentLineForSOModularContractHandler computingMethod;
	@Getter @NonNull private final String supportedTableName = I_M_InOutLine.Table_Name;
	@Getter @NonNull private final LogEntryDocumentType logEntryDocumentType = LogEntryDocumentType.SHIPMENT;

	ShipmentLineForSOLogHandler(final @NonNull ModularContractLogDAO contractLogDAO,
			final @NonNull ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository,
			final @NonNull ModularContractService modularContractService,
			final @NonNull ShipmentLineForSOModularContractHandler computingMethod)
	{
		super(modularContractService);
		this.contractLogDAO = contractLogDAO;
		this.modCntrInvoicingGroupRepository = modCntrInvoicingGroupRepository;
		this.computingMethod = computingMethod;
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull final CreateLogRequest createLogRequest)
	{
		final TableRecordReference recordRef = createLogRequest.getRecordRef();
		final I_M_InOutLine inOutLineRecord = inOutBL.getLineByIdInTrx(InOutLineId.ofRepoId(recordRef.getRecordIdAssumingTableName(I_M_InOutLine.Table_Name)));
		final I_M_InOut inOutRecord = inOutBL.getById(InOutId.ofRepoId(inOutLineRecord.getM_InOut_ID()));
		final I_C_Flatrate_Term flatrateTermRecord = flatrateBL.getById(createLogRequest.getContractId());

		final BPartnerId bPartnerId = BPartnerId.ofRepoId(flatrateTermRecord.getBill_BPartner_ID());

		final Quantity quantity = inOutBL.getQtyEntered(inOutLineRecord);
		final WarehouseId warehouseId = WarehouseId.ofRepoId(inOutRecord.getM_Warehouse_ID());
		final I_M_Warehouse warehouse = warehouseDAO.getById(warehouseId);

		final ProductId productId = ProductId.ofRepoId(inOutLineRecord.getM_Product_ID());

		final String productName = productBL.getProductValueAndName(productId);
		final String description = msgBL.getBaseLanguageMsg(MSG_INFO_SHIPMENT_SO_COMPLETED, productName, quantity.toString());

		final LocalDateAndOrgId transactionDate = LocalDateAndOrgId.ofTimestamp(inOutRecord.getMovementDate(),
																				OrgId.ofRepoId(inOutLineRecord.getAD_Org_ID()),
																				orgDAO::getTimeZone);

		final YearAndCalendarId yearAndCalendarId = createLogRequest.getModularContractSettings().getYearAndCalendarId();
		final InvoicingGroupId invoicingGroupId = modCntrInvoicingGroupRepository.getInvoicingGroupIdFor(productId, yearAndCalendarId)
				.orElse(null);

		return ExplainedOptional.of(LogEntryCreateRequest.builder()
											.contractId(createLogRequest.getContractId())
											.productId(ProductId.ofRepoId(inOutLineRecord.getM_Product_ID()))
											.productName(createLogRequest.getProductName())
											.referencedRecord(recordRef)
											.collectionPointBPartnerId(BPartnerId.ofRepoId(warehouse.getC_BPartner_ID()))
											.producerBPartnerId(bPartnerId)
											.invoicingBPartnerId(bPartnerId)
											.warehouseId(warehouseId)
											.documentType(getLogEntryDocumentType())
											.contractType(getLogEntryContractType())
											.soTrx(SOTrx.SALES)
											.processed(false)
											.quantity(quantity)
											.amount(null)
											.transactionDate(transactionDate)
											.year(yearAndCalendarId.yearId())
											.description(description)
											.modularContractTypeId(createLogRequest.getTypeId())
											.configModuleId(createLogRequest.getConfigId().getModularContractModuleId())
											.invoicingGroupId(invoicingGroupId)
											.build());
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(@NonNull final CreateLogRequest createLogRequest)
	{
		final TableRecordReference recordRef = createLogRequest.getRecordRef();
		final I_M_InOutLine inOutLineRecord = inOutBL.getLineByIdInTrx(InOutLineId.ofRepoId(recordRef.getRecordIdAssumingTableName(I_M_InOutLine.Table_Name)));

		final TableRecordReference inOutLineRef = TableRecordReference.of(I_M_InOutLine.Table_Name, inOutLineRecord.getM_InOutLine_ID());

		final Quantity quantity = contractLogDAO.retrieveQuantityFromExistingLog(ModularContractLogQuery.builder()
																						 .flatrateTermId(createLogRequest.getContractId())
																						 .referenceSet(TableRecordReferenceSet.of(inOutLineRef))
																						 .build());

		final ProductId productId = ProductId.ofRepoId(inOutLineRecord.getM_Product_ID());
		final String productName = productBL.getProductValueAndName(productId);

		final String description = msgBL.getBaseLanguageMsg(MSG_INFO_SHIPMENT_SO_REVERSED, productName, quantity.toString());

		return ExplainedOptional.of(LogEntryReverseRequest.builder()
											.referencedModel(recordRef)
											.flatrateTermId(createLogRequest.getContractId())
											.description(description)
											.logEntryContractType(LogEntryContractType.MODULAR_CONTRACT)
											.contractModuleId(createLogRequest.getModularContractModuleId())
											.build());
	}
}
