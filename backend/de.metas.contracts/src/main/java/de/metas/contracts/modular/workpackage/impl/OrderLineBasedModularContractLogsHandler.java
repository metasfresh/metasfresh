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
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.contracts.modular.impl.SalesModularContractHandler;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.workpackage.IModularContractLogHandler;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.Language;
import de.metas.i18n.TranslatableStrings;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Warehouse;
import org.springframework.stereotype.Component;

import static de.metas.contracts.modular.ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED;

@Component
@RequiredArgsConstructor
class OrderLineBasedModularContractLogsHandler implements IModularContractLogHandler<I_C_Flatrate_Term>
{
	private final static AdMessageKey MSG_ON_COMPLETE_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.modularContractCompleteLogDescription");

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

	private final SalesModularContractHandler contractHandler;

	@Override
	@NonNull
	public LogAction getLogAction(@NonNull final HandleLogsRequest<I_C_Flatrate_Term> request)
	{
		return switch (request.getModelAction())
				{
					case COMPLETED -> LogAction.CREATE;
					default -> throw new AdempiereException(MSG_ERROR_DOC_ACTION_UNSUPPORTED);
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
	public @NonNull ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull final CreateLogRequest<I_C_Flatrate_Term> request)
	{
		final I_C_Flatrate_Term modularContractRecord = request.getHandleLogsRequest().getModel();
		final ProductId productId = ProductId.ofRepoId(modularContractRecord.getM_Product_ID());

		final OrderId orderId = OrderId.ofRepoId(modularContractRecord.getC_Order_Term_ID());

		final I_C_Order order = orderBL.getById(orderId);
		final WarehouseId warehouseId = WarehouseId.ofRepoId(order.getM_Warehouse_ID());
		final I_M_Warehouse warehouseRecord = warehouseBL.getById(warehouseId);

		final Quantity quantity = Quantitys.create(modularContractRecord.getPlannedQtyPerUnit(),
												   UomId.ofRepoIdOrNull(modularContractRecord.getC_UOM_ID()),
												   productId);

		final String productName = productBL.getProductValueAndName(productId);

		final String description = TranslatableStrings.adMessage(MSG_ON_COMPLETE_DESCRIPTION, productName, quantity)
				.translate(Language.getBaseAD_Language());

		final BPartnerId billBPartnerId = BPartnerId.ofRepoId(modularContractRecord.getBill_BPartner_ID());
		final SOTrx soTrx = SOTrx.ofBoolean(order.isSOTrx());

		return ExplainedOptional.of(LogEntryCreateRequest.builder()
											.contractId(request.getContractId())
											.productId(productId)
											.referencedRecord(TableRecordReference.of(I_C_Flatrate_Term.Table_Name, request.getContractId()))
											.producerBPartnerId(billBPartnerId)
											.invoicingBPartnerId(billBPartnerId)
											.collectionPointBPartnerId(BPartnerId.ofRepoId(warehouseRecord.getC_BPartner_ID()))
											.warehouseId(warehouseId)
											.documentType(soTrx.isSales() ? LogEntryDocumentType.SALES_MODULAR_CONTRACT : LogEntryDocumentType.PURCHASE_MODULAR_CONTRACT)
											.contractType(LogEntryContractType.MODULAR_CONTRACT)
											.soTrx(soTrx)
											.processed(false)
											.quantity(quantity)
											.transactionDate(LocalDateAndOrgId.ofTimestamp(modularContractRecord.getStartDate(),
																						   OrgId.ofRepoId(modularContractRecord.getAD_Org_ID()),
																						   orgDAO::getTimeZone))
											.year(request.getModularContractSettings().getYearAndCalendarId().yearId())
											.description(description)
											.modularContractTypeId(request.getTypeId())
											.build());
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(
			@NonNull final HandleLogsRequest<I_C_Flatrate_Term> handleLogsRequest,
			@NonNull final FlatrateTermId contractId)
	{
		throw new AdempiereException(MSG_ERROR_DOC_ACTION_UNSUPPORTED);
	}

	@Override
	public @NonNull IModularContractTypeHandler<I_C_Flatrate_Term> getModularContractTypeHandler()
	{
		return contractHandler;
	}
}
