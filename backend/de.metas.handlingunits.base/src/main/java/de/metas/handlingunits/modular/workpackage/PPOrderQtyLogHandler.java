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

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.contracts.modular.ModularContract_Constants;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.workpackage.IModularContractLogHandler;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.modular.impl.PPOrderQtyModularContractHandler;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.IMsgBL;
import de.metas.lang.SOTrx;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
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
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PPOrderQtyLogHandler implements IModularContractLogHandler<I_PP_Order_Qty>
{
	private static final AdMessageKey MSG_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.impl.IssueReceiptModularContractHandler.Description");

	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	@NonNull
	private final PPOrderQtyModularContractHandler contractHandler;

	@Override
	public LogAction getLogAction(@NonNull final HandleLogsRequest<I_PP_Order_Qty> request)
	{
		return switch (request.getModelAction())
				{
					case COMPLETED -> LogAction.CREATE;
					case REACTIVATED -> LogAction.REVERSE;
					default -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED);
				};
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull final CreateLogRequest<I_PP_Order_Qty> request)
	{
		final I_PP_Order_Qty ppOrderQty = request.getHandleLogsRequest().getModel();

		final I_C_Flatrate_Term modularContractRecord = flatrateDAO.getById(request.getContractId());
		final I_PP_Order ppOrderRecord = ppOrderBL.getById(PPOrderId.ofRepoId(ppOrderQty.getPP_Order_ID()));

		final I_C_UOM uomId = uomDAO.getById(UomId.ofRepoId(ppOrderQty.getC_UOM_ID()));
		final Quantity quantity = Quantity.of(ppOrderQty.getQty(), uomId);

		final ProductId productId = ProductId.ofRepoId(ppOrderQty.getM_Product_ID());
		final String description = msgBL.getMsg(MSG_DESCRIPTION, ImmutableList.of(quantity.abs().toString(), productId.getRepoId()));

		return ExplainedOptional.of(LogEntryCreateRequest.builder()
											.contractId(request.getContractId())
											.referencedRecord(TableRecordReference.of(I_PP_Order.Table_Name, ppOrderQty.getPP_Order_ID()))
											.productId(ProductId.ofRepoId(ppOrderQty.getM_Product_ID()))
											.invoicingBPartnerId(BPartnerId.ofRepoIdOrNull(modularContractRecord.getBill_BPartner_ID()))
											.warehouseId(WarehouseId.ofRepoId(ppOrderRecord.getM_Warehouse_ID()))
											.documentType(LogEntryDocumentType.PRODUCTION)
											.contractType(LogEntryContractType.MODULAR_CONTRACT)
											.soTrx(SOTrx.PURCHASE)
											.quantity(quantity)
											.transactionDate(LocalDateAndOrgId.ofTimestamp(ppOrderQty.getMovementDate(),
																						   OrgId.ofRepoId(ppOrderQty.getAD_Org_ID()),
																						   orgDAO::getTimeZone))
											.year(request.getModularContractSettings().getYearAndCalendarId().yearId())
											.description(description)
											.modularContractTypeId(request.getTypeId())
											.build());
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(@NonNull final HandleLogsRequest<I_PP_Order_Qty> handleLogsRequest, @NonNull final FlatrateTermId contractId)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public @NonNull IModularContractTypeHandler<I_PP_Order_Qty> getModularContractTypeHandler()
	{
		return contractHandler;
	}
}
