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

package de.metas.handlingunits.modular.impl;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.contracts.modular.settings.ModularContractType;
import de.metas.contracts.modular.settings.ModularContractTypeId;
import de.metas.contracts.modular.settings.ModuleConfig;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.i18n.AdMessageKey;
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
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
public class PPOrderQtyModularContractHandler implements IModularContractTypeHandler<I_PP_Order_Qty>
{
	private static final AdMessageKey MSG_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.impl.IssueReceiptModularContractHandler.Description");
	
	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	private final ModularContractSettingsDAO modularContractSettingsDAO;

	public PPOrderQtyModularContractHandler(@NonNull final ModularContractSettingsDAO modularContractSettingsDAO)
	{
		this.modularContractSettingsDAO = modularContractSettingsDAO;
	}

	@Override
	@NonNull
	public Class<I_PP_Order_Qty> getType()
	{
		return I_PP_Order_Qty.class;
	}

	@Override
	public boolean applies(@NonNull final I_PP_Order_Qty issueReceiptQty)
	{
		return ppOrderBL.isModularOrder(PPOrderId.ofRepoId(issueReceiptQty.getPP_Order_ID()));
	}

	@Override
	public boolean applies(final @NonNull LogEntryContractType logEntryContractType)
	{
		return logEntryContractType.isModularContractType();
	}

	@Override
	@NonNull
	public Optional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull final I_PP_Order_Qty ppOrderQty, final @NonNull FlatrateTermId modularContractId)
	{
		final ModularContractSettings modularContractSettings = modularContractSettingsDAO.getByFlatrateTermIdOrNull(modularContractId);
		if (modularContractSettings == null)
		{
			throw new AdempiereException("Invalid contract missing settings")
					.appendParametersToMessage()
					.setParameter("PP_Order_ID", ppOrderQty.getPP_Order_ID())
					.setParameter("ModularContractID", modularContractId);
		}

		final Optional<ModularContractTypeId> modularContractTypeId = modularContractSettings.getModuleConfigs()
				.stream()
				.filter(config -> config.isMatchingClassName(PPOrderQtyModularContractHandler.class.getName()))
				.map(ModuleConfig::getModularContractType)
				.map(ModularContractType::getId)
				.findFirst();

		if (modularContractTypeId.isEmpty())
		{
			return Optional.empty();
		}

		final I_C_Flatrate_Term modularContractRecord = flatrateDAO.getById(modularContractId);
		final I_PP_Order ppOrderRecord = ppOrderBL.getById(PPOrderId.ofRepoId(ppOrderQty.getPP_Order_ID()));

		final I_C_UOM uomId = uomDAO.getById(UomId.ofRepoId(ppOrderQty.getC_UOM_ID()));
		final Quantity quantity = Quantity.of(ppOrderQty.getQty(), uomId);

		final ProductId productId = ProductId.ofRepoId(ppOrderQty.getM_Product_ID());
		final String description = msgBL.getMsg(MSG_DESCRIPTION, ImmutableList.of(quantity.abs().toString(), productId.getRepoId()));

		return modularContractTypeId.map(contractTypeId -> LogEntryCreateRequest.builder()
				.contractId(modularContractId)
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
				.year(modularContractSettings.getYearAndCalendarId().yearId())
				.description(description)
				.modularContractTypeId(contractTypeId)
				.build());
	}

	@Override
	@NonNull
	public Optional<LogEntryReverseRequest> createLogEntryReverseRequest(final @NonNull I_PP_Order_Qty issueReceiptQty, final @NonNull FlatrateTermId flatrateTermId)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	@NonNull
	public Stream<FlatrateTermId> streamContractIds(@NonNull final I_PP_Order_Qty issueReceiptQty)
	{
		return Optional.of(issueReceiptQty.getPP_Order_ID())
				.map(PPOrderId::ofRepoId)
				.map(ppOrderBL::getById)
				.map(I_PP_Order::getModular_Flatrate_Term_ID)
				.map(FlatrateTermId::ofRepoIdOrNull)
				.stream();
	}

	@Override
	public void validateDocAction(@NonNull final I_PP_Order_Qty issueReceiptQty, @NonNull final ModularContractService.ModelAction action)
	{
		if (action != ModularContractService.ModelAction.COMPLETED)
		{
			throw new AdempiereException("Unsupported model action!")
					.appendParametersToMessage()
					.setParameter("Action", action)
					.setParameter("PP_Order_Qty_ID", issueReceiptQty.getPP_Order_Qty_ID());
		}
	}
}
