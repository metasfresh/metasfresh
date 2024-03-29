/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.modular.workpackage;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.contracts.modular.ModularContract_Constants;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDeleteRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.log.LogSubEntryId;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.workpackage.IModularContractLogHandler;
import de.metas.handlingunits.modular.impl.PPCostCollectorModularContractHandler;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.BooleanWithReason;
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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPCostCollectorId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Cost_Collector;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.X_PP_Cost_Collector;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PPCostCollectorLogHandler implements IModularContractLogHandler<I_PP_Cost_Collector>
{
	private static final AdMessageKey MSG_DESCRIPTION_ISSUE = AdMessageKey.of("de.metas.contracts.modular.impl.IssueReceiptModularContractHandler.Description.Issue");
	private static final AdMessageKey MSG_DESCRIPTION_RECEIPT = AdMessageKey.of("de.metas.contracts.modular.impl.IssueReceiptModularContractHandler.Description.Receipt");

	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	@NonNull
	private final PPCostCollectorModularContractHandler contractHandler;
	@NonNull
	private final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository;

	@Override
	public LogAction getLogAction(@NonNull final HandleLogsRequest<I_PP_Cost_Collector> request)
	{
		return switch (request.getModelAction())
				{
					case COMPLETED -> LogAction.CREATE;
					case RECREATE_LOGS -> LogAction.RECOMPUTE;
					default -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED);
				};
	}

	@Override
	public BooleanWithReason doesRecordStateRequireLogCreation(@NonNull final I_PP_Cost_Collector model)
	{
		if (!model.isProcessed())
		{
			return BooleanWithReason.falseBecause("The PP_Cost_Collector.Processed is false");
		}

		return BooleanWithReason.TRUE;
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull final CreateLogRequest<I_PP_Cost_Collector> createLogRequest)
	{
		final I_PP_Cost_Collector ppCostCollector = createLogRequest.getHandleLogsRequest().getModel();
		final FlatrateTermId contractId = createLogRequest.getContractId();
		final ModularContractSettings modularContractSettings = createLogRequest.getModularContractSettings();

		final I_C_Flatrate_Term modularContractRecord = flatrateDAO.getById(contractId);

		final I_PP_Order ppOrderRecord = ppOrderBL.getById(PPOrderId.ofRepoId(ppCostCollector.getPP_Order_ID()));

		final I_C_UOM uom = uomDAO.getById(UomId.ofRepoId(ppCostCollector.getC_UOM_ID()));

		final I_M_Product product = productBL.getById(ProductId.ofRepoId(ppCostCollector.getM_Product_ID()));

		final Quantity collectorMovementQty = Quantity.of(ppCostCollector.getMovementQty(), uom);
		final Quantity modCntrLogQty;
		final String description;

		if (ppCostCollector.getCostCollectorType().equals(X_PP_Cost_Collector.COSTCOLLECTORTYPE_MaterialReceipt)
				|| ppCostCollector.getCostCollectorType().equals(X_PP_Cost_Collector.COSTCOLLECTORTYPE_MixVariance))
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

		final InvoicingGroupId invoicingGroupId = modCntrInvoicingGroupRepository.getInvoicingGroupIdFor(productId, transactionDate.toInstant(orgDAO::getTimeZone))
				.orElse(null);

		return ExplainedOptional.of(LogEntryCreateRequest.builder()
											.contractId(contractId)
											.referencedRecord(TableRecordReference.of(I_PP_Order.Table_Name, ppCostCollector.getPP_Order_ID()))
											.subEntryId(LogSubEntryId.ofCostCollectorId(PPCostCollectorId.ofRepoId(ppCostCollector.getPP_Cost_Collector_ID())))
											.productId(productId)
											.invoicingBPartnerId(BPartnerId.ofRepoIdOrNull(modularContractRecord.getBill_BPartner_ID()))
											.warehouseId(WarehouseId.ofRepoId(ppOrderRecord.getM_Warehouse_ID()))
											.documentType(LogEntryDocumentType.PRODUCTION)
											.contractType(LogEntryContractType.MODULAR_CONTRACT)
											.soTrx(SOTrx.PURCHASE)
											.quantity(modCntrLogQty)
											.transactionDate(transactionDate)
											.year(modularContractSettings.getYearAndCalendarId().yearId())
											.description(description)
											.modularContractTypeId(createLogRequest.getTypeId())
											.configId(createLogRequest.getConfigId())
											.collectionPointBPartnerId(BPartnerId.ofRepoIdOrNull(modularContractRecord.getDropShip_BPartner_ID()))
											.invoicingGroupId(invoicingGroupId)
											.build());
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(@NonNull final HandleLogsRequest<I_PP_Cost_Collector> handleLogsRequest)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public @NonNull IModularContractTypeHandler<I_PP_Cost_Collector> getModularContractTypeHandler()
	{
		return contractHandler;
	}

	@Override
	public @NonNull Optional<ProductId> getProductId(final @NonNull HandleLogsRequest<I_PP_Cost_Collector> handleLogsRequest)
	{
		return Optional.of(handleLogsRequest.getModel().getM_Product_ID())
				.map(ProductId::ofRepoId);
	}

	@Override
	@NonNull
	public LogEntryDeleteRequest getDeleteRequestFor(@NonNull final HandleLogsRequest<I_PP_Cost_Collector> handleLogsRequest)
	{
		final I_PP_Cost_Collector ppCostCollector = handleLogsRequest.getModel();

		return LogEntryDeleteRequest.builder()
				.referencedModel(TableRecordReference.of(I_PP_Order.Table_Name, ppCostCollector.getPP_Order_ID()))
				.subEntryId(LogSubEntryId.ofCostCollectorId(PPCostCollectorId.ofRepoId(ppCostCollector.getPP_Cost_Collector_ID())))
				.flatrateTermId(handleLogsRequest.getContractId())
				.logEntryContractType(handleLogsRequest.getLogEntryContractType())
				.build();
	}
}
