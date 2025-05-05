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

package de.metas.contracts.modular.workpackage.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.calendar.standard.YearAndCalendarId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.computing.IComputingMethodHandler;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.workpackage.AbstractModularContractLogHandler;
import de.metas.contracts.modular.workpackage.IModularContractLogHandler;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.IMsgBL;
import de.metas.inventory.IInventoryBL;
import de.metas.inventory.InventoryId;
import de.metas.inventory.InventoryLineId;
import de.metas.lang.SOTrx;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.model.I_M_Warehouse;

import java.text.MessageFormat;
import java.util.Optional;

public abstract class AbstractDefinitiveInvoiceInventoryLogHandler  extends AbstractModularContractLogHandler
{

	private static final AdMessageKey MSG_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.impl.InventoryLineModularContractHandler.Description");
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	@NonNull private final IInventoryBL inventoryBL = Services.get(IInventoryBL.class);
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull private final IMsgBL msgBL = Services.get(IMsgBL.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

	@NonNull private final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository;

	@Getter @NonNull private final IComputingMethodHandler computingMethod;
	@Getter @NonNull private final String supportedTableName = I_M_InventoryLine.Table_Name;
	@Getter @NonNull private final LogEntryDocumentType logEntryDocumentType = LogEntryDocumentType.INVENTORY;

	protected AbstractDefinitiveInvoiceInventoryLogHandler(@NonNull final ModularContractService modularContractService,
			final @NonNull ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository,
			final @NonNull IComputingMethodHandler computingMethod)
	{
		super(modularContractService);
		this.modCntrInvoicingGroupRepository = modCntrInvoicingGroupRepository;
		this.computingMethod = computingMethod;
	}

	@Override
	@NonNull
	public ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(
			@NonNull final CreateLogRequest createLogRequest)
	{
		final TableRecordReference recordRef = createLogRequest.getRecordRef();
		final I_M_InventoryLine inventoryLine = inventoryBL.getLineById(InventoryLineId.ofRepoId(recordRef.getRecordIdAssumingTableName(getSupportedTableName())));

		final I_M_Inventory inventory = inventoryBL.getById(InventoryId.ofRepoId(inventoryLine.getM_Inventory_ID()));

		if (inventoryBL.isReversal(inventory))
		{
			return ExplainedOptional.emptyBecause(MessageFormat.format("InventoryId: {0} | The enqueued inventory is a reversal, the log entries are created only for the reversed inventory",
																	   inventory.getM_Inventory_ID()));
		}

		final Quantity quantity = inventoryBL.getMovementQty(inventoryLine);

		final String description = msgBL.getBaseLanguageMsg(MSG_DESCRIPTION, quantity.abs().toString());

		final I_C_Flatrate_Term modularContractRecord = flatrateDAO.getById(createLogRequest.getContractId());

		final ProductId productId = ProductId.ofRepoId(inventoryLine.getM_Product_ID());

		final LocalDateAndOrgId transactionDate = LocalDateAndOrgId.ofTimestamp(inventory.getMovementDate(),
																				OrgId.ofRepoId(inventoryLine.getAD_Org_ID()),
																				orgDAO::getTimeZone);

		final YearAndCalendarId yearAndCalendarId = createLogRequest.getModularContractSettings().getYearAndCalendarId();
		final InvoicingGroupId invoicingGroupId = modCntrInvoicingGroupRepository.getInvoicingGroupIdFor(productId, yearAndCalendarId)
				.orElse(null);

		final WarehouseId warehouseId = WarehouseId.ofRepoIdOrNull(inventory.getM_Warehouse_ID());
		final BPartnerId collectionPointBPartnerId = Optional.ofNullable(warehouseId)
				.map(warehouseBL::getById)
				.map(I_M_Warehouse::getC_BPartner_ID)
				.map(BPartnerId::ofRepoId)
				.orElse(null);

		final ProductPrice contractSpecificPrice = getContractSpecificPriceWithFlags(createLogRequest).toProductPrice();

		return ExplainedOptional.of(LogEntryCreateRequest.builder()
											.contractId(createLogRequest.getContractId())
											.productId(productId)
											.referencedRecord(createLogRequest.getRecordRef())
											.collectionPointBPartnerId(collectionPointBPartnerId)
											.producerBPartnerId(collectionPointBPartnerId)
											.invoicingBPartnerId(BPartnerId.ofRepoIdOrNull(modularContractRecord.getBill_BPartner_ID()))
											.warehouseId(warehouseId)
											.documentType(getLogEntryDocumentType())
											.contractType(getLogEntryContractType())
											.soTrx(SOTrx.PURCHASE)
											.processed(false)
											.quantity(quantity)
											.transactionDate(transactionDate)
											.year(yearAndCalendarId.yearId())
											.description(description)
											.modularContractTypeId(createLogRequest.getTypeId())
											.configModuleId(createLogRequest.getConfigId().getModularContractModuleId())
											.productName(createLogRequest.getProductName())
											.invoicingGroupId(invoicingGroupId)
											.isBillable(true)
											.priceActual(contractSpecificPrice)
											.amount(contractSpecificPrice.computeAmount(quantity, uomConversionBL))
											.build());
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(@NonNull final IModularContractLogHandler.CreateLogRequest createLogRequest)
	{
		final TableRecordReference recordRef = createLogRequest.getRecordRef();
		final I_M_InventoryLine inventoryLine = inventoryBL.getLineById(InventoryLineId.ofRepoId(recordRef.getRecordIdAssumingTableName(I_M_InventoryLine.Table_Name)));
		final I_M_Inventory inventory = inventoryBL.getById(InventoryId.ofRepoId(inventoryLine.getM_Inventory_ID()));

		if (inventoryBL.isReversal(inventory))
		{
			return ExplainedOptional.emptyBecause(MessageFormat.format("InventoryId: {0} | The enqueued inventory is a reversal, the log entries are created only for the reversed inventory",
																	   inventory.getM_Inventory_ID()));
		}

		final Quantity quantity = inventoryBL.getMovementQty(inventoryLine);

		final String description = msgBL.getBaseLanguageMsg(MSG_DESCRIPTION, quantity.abs().toString());

		return ExplainedOptional.of(LogEntryReverseRequest.builder()
											.referencedModel(recordRef)
											.flatrateTermId(createLogRequest.getContractId())
											.logEntryContractType(LogEntryContractType.MODULAR_CONTRACT)
											.contractModuleId(createLogRequest.getModularContractModuleId())
											.description(description)
											.build());
	}
}
