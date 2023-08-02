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

package de.metas.contracts.modular.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.calendar.standard.YearId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.FlatrateTermRequest.ModularFlatrateTermRequest;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.ModularContract_Constants;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.contracts.modular.settings.ModularContractType;
import de.metas.contracts.modular.settings.ModularContractTypeId;
import de.metas.contracts.modular.settings.ModuleConfig;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
public class ShipmentLineModularContractHandler implements IModularContractTypeHandler<I_M_InOutLine>
{
	private static final AdMessageKey MSG_INFO_SHIPMENT_COMPLETED = AdMessageKey.of("de.metas.contracts.ShipmentCompleted");
	private static final AdMessageKey MSG_INFO_SHIPMENT_REVERSED = AdMessageKey.of("de.metas.contracts.ShipmentReversed");

	private final ModularContractSettingsDAO modularContractSettingsDAO;

	private final IInOutDAO inoutDao = Services.get(IInOutDAO.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	public ShipmentLineModularContractHandler(@NonNull final ModularContractSettingsDAO modularContractSettingsDAO)
	{
		this.modularContractSettingsDAO = modularContractSettingsDAO;
	}

	@Override
	@NonNull
	public Class<I_M_InOutLine> getType()
	{
		return I_M_InOutLine.class;
	}

	@Override
	public boolean applies(final @NonNull I_M_InOutLine inOutLineRecord)
	{
		final I_M_InOut inOutRecord = inoutDao.getById(InOutId.ofRepoId(inOutLineRecord.getM_InOut_ID()));
		return (SOTrx.ofBoolean(inOutRecord.isSOTrx()).isSales() && inOutLineRecord.getC_Order_ID() > 0);
	}

	@Override
	public @NonNull Optional<LogEntryCreateRequest> createLogEntryCreateRequest(final @NonNull I_M_InOutLine inOutLineRecord, final @NonNull FlatrateTermId flatrateTermId)
	{
		final ModularContractSettings modularContractSettings = modularContractSettingsDAO.getByFlatrateTermIdOrNull(flatrateTermId);
		if (modularContractSettings == null)
		{
			return Optional.empty();
		}

		final I_M_InOut inOutRecord = inoutDao.getById(InOutId.ofRepoId(inOutLineRecord.getM_InOut_ID()));
		final I_C_Flatrate_Term flatrateTermRecord = flatrateDAO.getById(flatrateTermId);
		final I_C_UOM uomId = uomDAO.getById(UomId.ofRepoId(inOutLineRecord.getC_UOM_ID()));
		final Quantity quantity = Quantity.of(inOutLineRecord.getMovementQty(), uomId);

		final ITranslatableString msgText = msgBL.getTranslatableMsgText(MSG_INFO_SHIPMENT_COMPLETED);

		final Optional<ModularContractTypeId> modularContractTypeId = modularContractSettings.getModuleConfigs()
				.stream()
				.filter(config -> config.isMatchingClassName(ShipmentLineModularContractHandler.class.getName()))
				.map(ModuleConfig::getModularContractType)
				.map(ModularContractType::getId)
				.findFirst();

		return modularContractTypeId.map(contractTypeId -> LogEntryCreateRequest.builder()
				.contractId(flatrateTermId)
				.productId(ProductId.ofRepoId(inOutLineRecord.getM_Product_ID()))
				.referencedRecord(TableRecordReference.of(I_M_InOutLine.Table_Name, inOutLineRecord.getM_InOutLine_ID()))
				.collectionPointBPartnerId(BPartnerId.ofRepoId(inOutRecord.getC_BPartner_ID()))
				.producerBPartnerId(BPartnerId.ofRepoId(inOutRecord.getC_BPartner_ID()))
				.invoicingBPartnerId(BPartnerId.ofRepoId(flatrateTermRecord.getBill_BPartner_ID()))
				.warehouseId(WarehouseId.ofRepoId(inOutRecord.getM_Warehouse_ID()))
				.documentType(LogEntryDocumentType.SHIPMENT)
				.soTrx(SOTrx.SALES)
				.processed(false)
				.quantity(quantity)
				.amount(null)
				.transactionDate(LocalDateAndOrgId.ofTimestamp(inOutRecord.getMovementDate(), OrgId.ofRepoId(inOutLineRecord.getAD_Org_ID()), orgDAO::getTimeZone))
				.year(modularContractSettings.getYearAndCalendarId().yearId())
				.description(msgText.translate(Env.getAD_Language()))
				.modularContractTypeId(contractTypeId)
				.build());
	}

	@Override
	public @NonNull Optional<LogEntryReverseRequest> createLogEntryReverseRequest(final @NonNull I_M_InOutLine inOutLineRecord, final @NonNull FlatrateTermId flatrateTermId)
	{
		final ITranslatableString msgText = msgBL.getTranslatableMsgText(MSG_INFO_SHIPMENT_REVERSED);

		return Optional.of(LogEntryReverseRequest.builder()
								   .referencedModel(TableRecordReference.of(I_M_InOutLine.Table_Name, inOutLineRecord.getM_InOutLine_ID()))
								   .flatrateTermId(flatrateTermId)
								   .description(msgText.translate(Env.getAD_Language()))
								   .build());
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(@NonNull final I_M_InOutLine inOutLineRecord)
	{
		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(inOutLineRecord.getC_Order_ID()));

		final WarehouseId warehouseId = WarehouseId.ofRepoId(order.getM_Warehouse_ID()); // C_Order.M_Warehouse_ID is mandatory and (warehouseBL.getBPartnerId demands NonNull

		final YearId harvestingYearId = YearId.ofRepoIdOrNull(order.getHarvesting_Year_ID());

		final ModularFlatrateTermRequest request = ModularFlatrateTermRequest.builder()
				.bPartnerId(warehouseBL.getBPartnerId(warehouseId))
				.productId(ProductId.ofRepoId(inOutLineRecord.getM_Product_ID()))
				.yearId(harvestingYearId)
				.soTrx(SOTrx.PURCHASE) // in this handler we want the *purchase* flatrate-terms that led to this (sales-)shipment
				.build();

		return streamModularContracts(request)
				.map(I_C_Flatrate_Term::getC_Flatrate_Term_ID)
				.map(FlatrateTermId::ofRepoId);
	}

	@NonNull
	private Stream<I_C_Flatrate_Term> streamModularContracts(@NonNull final ModularFlatrateTermRequest request)
	{
		return flatrateBL.streamModularFlatrateTerms(request);
	}

	@Override
	public void validateDocAction(final @NonNull I_M_InOutLine model, final ModularContractService.@NonNull ModelAction action)
	{
		switch (action)
		{
			case VOIDED, REACTIVATED -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_NOT_ALLOWED);
			case COMPLETED,REVERSED ->
			{
				// allow the docAction
			}
			default -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED);
		}
	}
}
