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
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.contracts.modular.ModularContract_Constants;
import de.metas.contracts.modular.impl.ShipmentLineModularContractHandler;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.workpackage.IModularContractLogHandler;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.lang.SOTrx;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
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
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ShipmentLineLogHandler implements IModularContractLogHandler<I_M_InOutLine>
{
	private static final AdMessageKey MSG_INFO_SHIPMENT_COMPLETED = AdMessageKey.of("de.metas.contracts.ShipmentCompleted");
	private static final AdMessageKey MSG_INFO_SHIPMENT_REVERSED = AdMessageKey.of("de.metas.contracts.ShipmentReversed");

	private final IInOutDAO inoutDao = Services.get(IInOutDAO.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	private final ShipmentLineModularContractHandler contractHandler;

	@Override
	public LogAction getLogAction(@NonNull final HandleLogsRequest<I_M_InOutLine> request)
	{
		return switch (request.getModelAction())
				{
					case COMPLETED -> LogAction.CREATE;
					case REVERSED, REACTIVATED, VOIDED -> LogAction.REVERSE;
					default -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED);
				};
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull final CreateLogRequest<I_M_InOutLine> request)
	{
		final I_M_InOutLine inOutLineRecord = request.getHandleLogsRequest().getModel();

		final I_M_InOut inOutRecord = inoutDao.getById(InOutId.ofRepoId(inOutLineRecord.getM_InOut_ID()));
		final I_C_Flatrate_Term flatrateTermRecord = flatrateBL.getById(request.getContractId());
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(flatrateTermRecord.getBill_BPartner_ID());

		final UomId uomId = UomId.ofRepoId(inOutLineRecord.getC_UOM_ID());
		final Quantity quantity = Quantitys.create(inOutLineRecord.getMovementQty(), uomId);

		final ITranslatableString msgText = msgBL.getTranslatableMsgText(MSG_INFO_SHIPMENT_COMPLETED);

		return ExplainedOptional.of(LogEntryCreateRequest.builder()
											.contractId(request.getContractId())
											.productId(ProductId.ofRepoId(inOutLineRecord.getM_Product_ID()))
											.referencedRecord(TableRecordReference.of(I_M_InOutLine.Table_Name, inOutLineRecord.getM_InOutLine_ID()))
											.collectionPointBPartnerId(bPartnerId)
											.producerBPartnerId(bPartnerId)
											.invoicingBPartnerId(bPartnerId)
											.warehouseId(WarehouseId.ofRepoId(inOutRecord.getM_Warehouse_ID()))
											.documentType(LogEntryDocumentType.SHIPMENT)
											.contractType(LogEntryContractType.MODULAR_CONTRACT)
											.soTrx(SOTrx.PURCHASE)
											.processed(false)
											.quantity(quantity)
											.amount(null)
											.transactionDate(LocalDateAndOrgId.ofTimestamp(inOutRecord.getMovementDate(), OrgId.ofRepoId(inOutLineRecord.getAD_Org_ID()), orgDAO::getTimeZone))
											.year(request.getModularContractSettings().getYearAndCalendarId().yearId())
											.description(msgText.translate(Env.getAD_Language()))
											.modularContractTypeId(request.getTypeId())
											.build());
	}

	@Override
	public @NonNull ExplainedOptional<LogEntryReverseRequest> createLogEntryReverseRequest(
			@NonNull final HandleLogsRequest<I_M_InOutLine> handleLogsRequest,
			@NonNull final FlatrateTermId contractId)
	{
		final ITranslatableString msgText = msgBL.getTranslatableMsgText(MSG_INFO_SHIPMENT_REVERSED);

		return ExplainedOptional.of(LogEntryReverseRequest.builder()
											.referencedModel(TableRecordReference.of(I_M_InOutLine.Table_Name, handleLogsRequest.getModel().getM_InOutLine_ID()))
											.flatrateTermId(contractId)
											.description(msgText.translate(Env.getAD_Language()))
											.logEntryContractType(LogEntryContractType.MODULAR_CONTRACT)
											.build());
	}

	@Override
	public @NonNull IModularContractTypeHandler<I_M_InOutLine> getModularContractTypeHandler()
	{
		return contractHandler;
	}

}
