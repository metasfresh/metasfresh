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

package de.metas.contracts.modular.computing.tbd.salescontract.pfcontract;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
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
import org.springframework.stereotype.Component;

import static de.metas.contracts.modular.ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED;

/**
 * @deprecated If needed, please move/use code in the new computing methods in package de.metas.contracts.modular.computing.salescontract
 */
@Deprecated
@Component
class SalesProFormaModularContractLogsHandler extends AbstractModularContractLogHandler
{
	private final static AdMessageKey MSG_ON_COMPLETE_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.workpackage.impl.SalesProFormaModularContractLogsHandler.CompleteLogDescription");

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	@Getter @NonNull private final SalesContractProFormaModularContractHandler computingMethod;
	@Getter @NonNull private final String supportedTableName = I_C_Flatrate_Term.Table_Name;
	@Getter @NonNull private final LogEntryDocumentType logEntryDocumentType = LogEntryDocumentType.PRO_FORMA_SO_MODULAR_CONTRACT;

	SalesProFormaModularContractLogsHandler(final @NonNull ModularContractService modularContractService,
			final @NonNull SalesContractProFormaModularContractHandler computingMethod)
	{
		super(modularContractService);
		this.computingMethod = computingMethod;
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull final CreateLogRequest request)
	{
		final TableRecordReference recordRef = request.getHandleLogsRequest().getTableRecordReference();
		final I_C_Flatrate_Term modularContractRecord = flatrateBL.getById(FlatrateTermId.ofRepoId(recordRef.getRecordIdAssumingTableName(getSupportedTableName())));
		final ProductId productId = ProductId.ofRepoId(modularContractRecord.getM_Product_ID());

		final OrderId orderId = OrderId.ofRepoId(modularContractRecord.getC_Order_Term_ID());

		final I_C_Order order = orderBL.getById(orderId);
		final WarehouseId warehouseId = WarehouseId.ofRepoId(order.getM_Warehouse_ID());
		final BPartnerId warehousePartnerId = warehouseBL.getBPartnerId(warehouseId);

		final Quantity quantity = Quantitys.of(modularContractRecord.getPlannedQtyPerUnit(),
											   UomId.ofRepoIdOrNull(modularContractRecord.getC_UOM_ID()),
											   productId);

		final String productName = productBL.getProductValueAndName(productId);

		final String description = msgBL.getBaseLanguageMsg(MSG_ON_COMPLETE_DESCRIPTION, productName, quantity);

		final BPartnerId billBPartnerId = BPartnerId.ofRepoId(modularContractRecord.getBill_BPartner_ID());
		final ProductPrice priceActual = flatrateBL.extractPriceActual(modularContractRecord);
		final Money amount = quantity != null && priceActual != null
				? priceActual.computeAmount(quantity)
				: null;

		return ExplainedOptional.of(LogEntryCreateRequest.builder()
											.contractId(request.getContractId())
											.productId(productId)
											.productName(request.getProductName())
											.referencedRecord(TableRecordReference.of(I_C_Flatrate_Term.Table_Name, request.getContractId()))
											.producerBPartnerId(billBPartnerId)
											.invoicingBPartnerId(billBPartnerId)
											.collectionPointBPartnerId(warehousePartnerId)
											.warehouseId(warehouseId)
											.documentType(getLogEntryDocumentType())
											.contractType(getLogEntryContractType())
											.soTrx(SOTrx.SALES)
											.processed(false)
											.quantity(quantity)
											.transactionDate(LocalDateAndOrgId.ofTimestamp(modularContractRecord.getStartDate(),
																						   OrgId.ofRepoId(modularContractRecord.getAD_Org_ID()),
																						   orgDAO::getTimeZone))
											.year(request.getModularContractSettings().getYearAndCalendarId().yearId())
											.description(description)
											.modularContractTypeId(request.getTypeId())
											.configModuleId(request.getConfigId().getModularContractModuleId())
											.priceActual(priceActual)
											.amount(amount)
											.build());
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(@NonNull final CreateLogRequest createLogRequest)
	{
		throw new AdempiereException(MSG_ERROR_DOC_ACTION_UNSUPPORTED);
	}
}
