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

package de.metas.contracts.modular.computing.tbd.purchasecontract.pp;

import de.metas.bpartner.BPartnerId;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDeleteRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.log.LogSubEntryId;
import de.metas.contracts.modular.settings.ModularContractModuleId;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.workpackage.AbstractModularContractLogHandler;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.IMsgBL;
import de.metas.lang.SOTrx;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.eevolution.api.CostCollectorType;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPCostCollectorId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.X_PP_Cost_Collector;
import org.springframework.stereotype.Component;

/**
 * @deprecated If needed, please move/use code in the new computing methods in package de.metas.contracts.modular.computing.purchasecontract
 */
@Deprecated
@Component
public class PPCostCollectorLog extends AbstractModularContractLogHandler
{
	private static final AdMessageKey MSG_DESCRIPTION_ISSUE = AdMessageKey.of("de.metas.contracts.modular.impl.IssueReceiptModularContractHandler.Description.Issue");
	private static final AdMessageKey MSG_DESCRIPTION_RECEIPT = AdMessageKey.of("de.metas.contracts.modular.impl.IssueReceiptModularContractHandler.Description.Receipt");

	@NonNull private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	@NonNull private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IMsgBL msgBL = Services.get(IMsgBL.class);
	@NonNull private final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);
	@NonNull private final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository;

	@Getter @NonNull private final PPCostCollectorModularContractHandler computingMethod;
	@Getter @NonNull private final String supportedTableName = I_PP_Cost_Collector.Table_Name;
	@Getter @NonNull private final LogEntryDocumentType logEntryDocumentType = LogEntryDocumentType.PRODUCTION;

	public PPCostCollectorLog(@NonNull final ModularContractService modularContractService,
			final @NonNull ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository,
			final @NonNull PPCostCollectorModularContractHandler computingMethod)
	{
		super(modularContractService);
		this.modCntrInvoicingGroupRepository = modCntrInvoicingGroupRepository;
		this.computingMethod = computingMethod;
	}

	public boolean applies(@NonNull final CreateLogRequest request)
	{
		final TableRecordReference recordRef = request.getRecordRef();
		if (recordRef.getTableName().equals(getSupportedTableName()))
		{
			final I_PP_Cost_Collector ppCostCollector = ppCostCollectorBL.getById(PPCostCollectorId.ofRepoId(recordRef.getRecord_ID()));
			return request.getProductId().equals(ProductId.ofRepoId(ppCostCollector.getM_Product_ID()));
		}
		return false;
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull final CreateLogRequest createLogRequest)
	{
		final TableRecordReference recordRef = createLogRequest.getRecordRef();
		final I_PP_Cost_Collector ppCostCollector = ppCostCollectorBL.getById(PPCostCollectorId.ofRepoId(recordRef.getRecordIdAssumingTableName(getSupportedTableName())));
		final FlatrateTermId contractId = createLogRequest.getContractId();
		final ModularContractSettings modularContractSettings = createLogRequest.getModularContractSettings();

		final I_C_Flatrate_Term modularContractRecord = flatrateDAO.getById(contractId);

		final I_PP_Order ppOrderRecord = ppOrderBL.getById(PPOrderId.ofRepoId(ppCostCollector.getPP_Order_ID()));

		final I_C_UOM uom = uomDAO.getById(UomId.ofRepoId(ppCostCollector.getC_UOM_ID()));

		final I_M_Product product = productBL.getById(ProductId.ofRepoId(ppCostCollector.getM_Product_ID()));

		final Quantity collectorMovementQty = Quantity.of(ppCostCollector.getMovementQty(), uom);
		final Quantity modCntrLogQty;
		final String description;

		final CostCollectorType costCollectorType = CostCollectorType.ofCode(ppCostCollector.getCostCollectorType());
		if (costCollectorType.isMaterialReceiptOrCoProduct())
		{
			modCntrLogQty = collectorMovementQty.abs();
			description = msgBL.getBaseLanguageMsg(MSG_DESCRIPTION_RECEIPT, modCntrLogQty.abs().toString(), product.getName());
		}
		else
		{
			modCntrLogQty = collectorMovementQty.isPositive() ? collectorMovementQty.negate() : collectorMovementQty;
			description = msgBL.getBaseLanguageMsg(MSG_DESCRIPTION_ISSUE, modCntrLogQty.abs().toString(), product.getName());
		}

		final ProductId productId = ProductId.ofRepoId(ppCostCollector.getM_Product_ID());

		final LocalDateAndOrgId transactionDate = LocalDateAndOrgId.ofTimestamp(ppCostCollector.getMovementDate(),
				OrgId.ofRepoId(ppCostCollector.getAD_Org_ID()),
				orgDAO::getTimeZone);

		final YearAndCalendarId yearAndCalendarId = createLogRequest.getModularContractSettings().getYearAndCalendarId();
		final InvoicingGroupId invoicingGroupId = modCntrInvoicingGroupRepository.getInvoicingGroupIdFor(productId, yearAndCalendarId)
				.orElse(null);

		return ExplainedOptional.of(LogEntryCreateRequest.builder()
				.contractId(contractId)
				.referencedRecord(TableRecordReference.of(I_PP_Order.Table_Name, ppCostCollector.getPP_Order_ID()))
				.subEntryId(LogSubEntryId.ofCostCollectorId(PPCostCollectorId.ofRepoId(ppCostCollector.getPP_Cost_Collector_ID())))
				.productId(productId)
				.productName(createLogRequest.getProductName())
				.invoicingBPartnerId(BPartnerId.ofRepoIdOrNull(modularContractRecord.getBill_BPartner_ID()))
				.warehouseId(WarehouseId.ofRepoId(ppOrderRecord.getM_Warehouse_ID()))
				.documentType(getLogEntryDocumentType())
				.contractType(getLogEntryContractType())
				.soTrx(SOTrx.PURCHASE)
				.quantity(modCntrLogQty)
				.transactionDate(transactionDate)
				.year(yearAndCalendarId.yearId())
				.description(description)
				.modularContractTypeId(createLogRequest.getTypeId())
				.configModuleId(createLogRequest.getConfigId().getModularContractModuleId())
				.collectionPointBPartnerId(BPartnerId.ofRepoIdOrNull(modularContractRecord.getDropShip_BPartner_ID()))
				.invoicingGroupId(invoicingGroupId)
				.build());
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(@NonNull final CreateLogRequest createLogRequest)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	@NonNull
	public LogEntryDeleteRequest toLogEntryDeleteRequest(@NonNull final HandleLogsRequest handleLogsRequest, final @NonNull ModularContractModuleId modularContractModuleId)
	{
		final TableRecordReference recordRef = handleLogsRequest.getTableRecordReference();
		final PPCostCollectorId ppCostCollectorId = PPCostCollectorId.ofRepoId(recordRef.getRecordIdAssumingTableName(getSupportedTableName()));
		final I_PP_Cost_Collector ppCostCollector = ppCostCollectorBL.getById(ppCostCollectorId);

		return LogEntryDeleteRequest.builder()
				.referencedModel(TableRecordReference.of(I_PP_Order.Table_Name, ppCostCollector.getPP_Order_ID()))
				.subEntryId(LogSubEntryId.ofCostCollectorId(ppCostCollectorId))
				.flatrateTermId(handleLogsRequest.getContractId())
				.logEntryContractType(getLogEntryContractType())
				.modularContractModuleId(modularContractModuleId)
				.build();
	}
}
