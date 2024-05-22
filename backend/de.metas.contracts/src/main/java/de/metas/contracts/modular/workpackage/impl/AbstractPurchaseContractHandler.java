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
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.invgroup.InvoicingGroupId;
import de.metas.contracts.modular.invgroup.interceptor.ModCntrInvoicingGroupRepository;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.workpackage.AbstractModularContractLogHandler;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.IMsgBL;
import de.metas.lang.SOTrx;
import de.metas.money.Money;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Warehouse;

import static de.metas.contracts.modular.ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED;

public abstract class AbstractPurchaseContractHandler extends AbstractModularContractLogHandler
{
	private final static AdMessageKey MSG_ON_INTERIM_COMPLETE_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.interimContractCompleteLogDescription");
	private final static AdMessageKey MSG_ON_MODULAR_COMPLETE_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.modularContractCompleteLogDescription");

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	@NonNull private final ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository;

	@NonNull @Getter private final String supportedTableName = I_C_Flatrate_Term.Table_Name;

	public AbstractPurchaseContractHandler(@NonNull final ModularContractService modularContractService,
			final @NonNull ModCntrInvoicingGroupRepository modCntrInvoicingGroupRepository)
	{
		super(modularContractService);
		this.modCntrInvoicingGroupRepository = modCntrInvoicingGroupRepository;
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull final CreateLogRequest request)
	{
		final TableRecordReference tableRecordReference = request.getRecordRef();
		final FlatrateTermId flatrateTermId = FlatrateTermId.ofRepoId(tableRecordReference.getRecordIdAssumingTableName(getSupportedTableName()));
		final I_C_Flatrate_Term flatrateTermRecord = flatrateBL.getById(flatrateTermId);
		final I_C_Flatrate_Term modularContractRecord;
		final boolean isInterimContract;
		if (TypeConditions.ofCode(flatrateTermRecord.getType_Conditions()).isModularContractType())
		{
			modularContractRecord = flatrateTermRecord;
			isInterimContract = false;
		}
		else
		{
			isInterimContract = true;
			final FlatrateTermId modularContractId = FlatrateTermId.ofRepoId(flatrateTermRecord.getModular_Flatrate_Term_ID());
			modularContractRecord = flatrateBL.getById(modularContractId);
		}

		final ProductId productId = ProductId.ofRepoId(modularContractRecord.getM_Product_ID());

		final OrderId orderId = OrderId.ofRepoId(modularContractRecord.getC_Order_Term_ID());

		final I_C_Order order = orderBL.getById(orderId);
		final WarehouseId warehouseId = WarehouseId.ofRepoId(order.getM_Warehouse_ID());
		final I_M_Warehouse warehouseRecord = warehouseBL.getById(warehouseId);

		final Quantity quantity = Quantitys.of(modularContractRecord.getPlannedQtyPerUnit(),
				UomId.ofRepoIdOrNull(modularContractRecord.getC_UOM_ID()),
				productId);

		final String productName = productBL.getProductValueAndName(productId);

		final AdMessageKey msgToUse = isInterimContract ? MSG_ON_INTERIM_COMPLETE_DESCRIPTION : MSG_ON_MODULAR_COMPLETE_DESCRIPTION;
		final String description = msgBL.getBaseLanguageMsg(msgToUse, productName, quantity);

		final BPartnerId billBPartnerId = BPartnerId.ofRepoId(modularContractRecord.getBill_BPartner_ID());
		final ProductPrice priceActual = flatrateBL.extractPriceActual(modularContractRecord);
		final Money amount = quantity != null && priceActual != null
				? priceActual.computeAmount(quantity)
				: null;

		final LocalDateAndOrgId transactionDate = LocalDateAndOrgId.ofTimestamp(flatrateTermRecord.getStartDate(),
				OrgId.ofRepoId(flatrateTermRecord.getAD_Org_ID()),
				orgDAO::getTimeZone);

		final YearAndCalendarId yearAndCalendarId = request.getModularContractSettings().getYearAndCalendarId();
		final InvoicingGroupId invoicingGroupId = modCntrInvoicingGroupRepository.getInvoicingGroupIdFor(productId, yearAndCalendarId)
				.orElse(null);

		return ExplainedOptional.of(LogEntryCreateRequest.builder()
				.contractId(request.getContractId())
				.productId(productId)
				.productName(request.getProductName())
				.referencedRecord(TableRecordReference.of(I_C_Flatrate_Term.Table_Name, request.getContractId()))
				.producerBPartnerId(billBPartnerId)
				.invoicingBPartnerId(billBPartnerId)
				.collectionPointBPartnerId(BPartnerId.ofRepoId(warehouseRecord.getC_BPartner_ID()))
				.warehouseId(warehouseId)
				.documentType(getLogEntryDocumentType())
				.contractType(getLogEntryContractType())
				.soTrx(SOTrx.ofBoolean(order.isSOTrx()))
				.processed(false)
				.quantity(quantity)
				.transactionDate(transactionDate)
				.year(yearAndCalendarId.yearId())
				.description(description)
				.modularContractTypeId(request.getTypeId())
				.configModuleId(request.getConfigId().getModularContractModuleId())
				.priceActual(priceActual)
				.amount(amount)
				.invoicingGroupId(invoicingGroupId)
				.isBillable(false)
				.build());
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(@NonNull final CreateLogRequest createLogRequest)
	{
		throw new AdempiereException(MSG_ERROR_DOC_ACTION_UNSUPPORTED);
	}
}
