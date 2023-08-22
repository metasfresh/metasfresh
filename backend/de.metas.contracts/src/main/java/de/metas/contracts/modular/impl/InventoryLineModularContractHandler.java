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

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.ModularContract_Constants;
import de.metas.contracts.modular.log.LogEntryContractType;
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
import de.metas.inventory.IInventoryBL;
import de.metas.inventory.InventoryId;
import de.metas.lang.SOTrx;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
public class InventoryLineModularContractHandler implements IModularContractTypeHandler<I_M_InventoryLine>
{
	private static final AdMessageKey MSG_REACTIVATE_NOT_ALLOWED = AdMessageKey.of("de.metas.contracts.modular.impl.InventoryLineModularContractHandler.ReactivateNotAllowed");
	private static final AdMessageKey MSG_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.impl.InventoryLineModularContractHandler.Description");
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IInventoryBL inventoryBL = Services.get(IInventoryBL.class);

	private final ModularContractSettingsDAO modularContractSettingsDAO;

	public InventoryLineModularContractHandler(@NonNull final ModularContractSettingsDAO modularContractSettingsDAO)
	{
		this.modularContractSettingsDAO = modularContractSettingsDAO;
	}

	@NonNull
	@Override
	public Class<I_M_InventoryLine> getType()
	{
		return I_M_InventoryLine.class;
	}

	@Override
	public boolean applies(@NonNull final I_M_InventoryLine inventoryLine)
	{
		return FlatrateTermId.ofRepoIdOrNull(inventoryLine.getModular_Flatrate_Term_ID()) != null;
	}

	@Override
	public boolean applies(final @NonNull LogEntryContractType logEntryContractType)
	{
		return logEntryContractType.isModularContractType();
	}

	@Override
	@NonNull
	public Optional<LogEntryCreateRequest> createLogEntryCreateRequest(
			@NonNull final I_M_InventoryLine inventoryLine,
			@NonNull final FlatrateTermId modularContractId)
	{
		final I_M_Inventory inventory = inventoryBL.getById(InventoryId.ofRepoId(inventoryLine.getM_Inventory_ID()));

		final InventoryId reversalId = InventoryId.ofRepoIdOrNull(inventory.getReversal_ID());
		if (reversalId != null)
		{
			return Optional.empty();
		}

		final ModularContractSettings modularContractSettings = modularContractSettingsDAO.getByFlatrateTermIdOrNull(modularContractId);
		if (modularContractSettings == null)
		{
			throw new AdempiereException("Invalid contract missing settings")
					.appendParametersToMessage()
					.setParameter("M_InventoryLine_ID", inventoryLine.getM_InventoryLine_ID())
					.setParameter("ModularContractID", modularContractId);
		}

		final Optional<ModularContractTypeId> modularContractTypeId = modularContractSettings.getModuleConfigs()
				.stream()
				.filter(config -> config.isMatchingClassName(InventoryLineModularContractHandler.class.getName()))
				.map(ModuleConfig::getModularContractType)
				.map(ModularContractType::getId)
				.findFirst();

		if (modularContractTypeId.isEmpty())
		{
			return Optional.empty();
		}

		final Quantity quantity = inventoryBL.getMovementQty(inventoryLine);

		final String description = msgBL.getMsg(MSG_DESCRIPTION, ImmutableList.of(quantity.abs().toString()));

		final I_C_Flatrate_Term modularContractRecord = flatrateDAO.getById(modularContractId);

		return modularContractTypeId.map(contractTypeId -> LogEntryCreateRequest.builder()
				.contractId(modularContractId)
				.productId(ProductId.ofRepoId(inventoryLine.getM_Product_ID()))
				.referencedRecord(TableRecordReference.of(I_M_InventoryLine.Table_Name, inventoryLine.getM_InventoryLine_ID()))
				.invoicingBPartnerId(BPartnerId.ofRepoIdOrNull(modularContractRecord.getBill_BPartner_ID()))
				.warehouseId(WarehouseId.ofRepoIdOrNull(inventory.getM_Warehouse_ID()))
				.documentType(LogEntryDocumentType.INVENTORY)
				.contractType(LogEntryContractType.MODULAR_CONTRACT)
				.soTrx(SOTrx.PURCHASE)
				.quantity(quantity)
				.transactionDate(LocalDateAndOrgId.ofTimestamp(inventory.getMovementDate(),
															   OrgId.ofRepoId(inventoryLine.getAD_Org_ID()),
															   orgDAO::getTimeZone))
				.year(modularContractSettings.getYearAndCalendarId().yearId())
				.description(description)
				.modularContractTypeId(contractTypeId)
				.build());
	}

	@Override
	public @NonNull Optional<LogEntryReverseRequest> createLogEntryReverseRequest(
			@NonNull final I_M_InventoryLine inventoryLine,
			@NonNull final FlatrateTermId modularContractId)
	{
		final I_M_Inventory inventory = inventoryBL.getById(InventoryId.ofRepoId(inventoryLine.getM_Inventory_ID()));

		final InventoryId reversalId = InventoryId.ofRepoIdOrNull(inventory.getReversal_ID());
		if (reversalId != null)
		{
			return Optional.empty();
		}

		return Optional.of(LogEntryReverseRequest.builder()
								   .referencedModel(TableRecordReference.of(inventoryLine))
								   .flatrateTermId(modularContractId)
								   .logEntryContractType(LogEntryContractType.MODULAR_CONTRACT)
								   .build());
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(@NonNull final I_M_InventoryLine inventoryLine)
	{
		return Stream.ofNullable(FlatrateTermId.ofRepoIdOrNull(inventoryLine.getModular_Flatrate_Term_ID()));
	}

	public void validateDocAction(
			@NonNull final I_M_InventoryLine ignored,
			@NonNull final ModularContractService.ModelAction action)
	{
		switch (action)
		{
			case COMPLETED, REVERSED, VOIDED ->
			{
			}
			case REACTIVATED -> throw new AdempiereException(MSG_REACTIVATE_NOT_ALLOWED);
			default -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED);
		}
	}
}
