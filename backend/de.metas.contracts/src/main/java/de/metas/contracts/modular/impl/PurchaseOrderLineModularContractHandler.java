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
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.contracts.modular.settings.ModularContractType;
import de.metas.contracts.modular.settings.ModularContractTypeId;
import de.metas.contracts.modular.settings.ModuleConfig;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
public class PurchaseOrderLineModularContractHandler implements IModularContractTypeHandler<I_C_OrderLine>
{
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	public final ModularContractLogDAO modularContractLogDAO;
	public final ModularContractSettingsDAO modularContractSettingsDAO;

	public PurchaseOrderLineModularContractHandler(@NonNull final ModularContractLogDAO modularContractLogDAO,
			@NonNull final ModularContractSettingsDAO modularContractSettingsDAO)
	{
		this.modularContractLogDAO = modularContractLogDAO;
		this.modularContractSettingsDAO = modularContractSettingsDAO;
	}

	@Override
	public boolean probablyAppliesTo(@NonNull final Object model)
	{
		return model.getClass().isAssignableFrom(I_C_OrderLine.class);
	}

	@Override
	@NonNull
	public Optional<LogEntryCreateRequest> createLogEntryCreateRequest(@NonNull final I_C_OrderLine model, @NonNull final FlatrateTermId flatrateTermId)
	{
		final ModularContractSettings modularContractSettings = modularContractSettingsDAO.getByFlatrateTermIdOrNull(flatrateTermId);
		if (modularContractSettings == null)
		{
			return Optional.empty();
		}

		final I_C_Order order = orderDAO.getById(OrderId.ofRepoId(model.getC_Order_ID()));
		final Optional<ModularContractTypeId> modularContractTypeId = modularContractSettings.getModuleConfigs()
				.stream()
				.filter(config -> config.isMatchingClassName(PurchaseOrderLineModularContractHandler.class.getName()))
				.map(ModuleConfig::getModularContractType)
				.map(ModularContractType::getId)
				.findFirst();

		final I_C_UOM uomId = uomDAO.getById(UomId.ofRepoId(model.getC_UOM_ID()));
		final Quantity quantity = Quantity.of(model.getQtyOrdered(), uomId);
		final Money amount = Money.of(model.getLineNetAmt(), CurrencyId.ofRepoId(model.getC_Currency_ID()));

		return modularContractTypeId.map(contractTypeId -> LogEntryCreateRequest.builder()
				.contractId(flatrateTermId)
				.productId(ProductId.ofRepoId(model.getM_Product_ID()))
				.referencedRecord(TableRecordReference.of(I_C_OrderLine.Table_Name, model.getC_OrderLine_ID()))
				.producerBPartnerId(BPartnerId.ofRepoId(order.getC_BPartner_ID()))
				.invoicingBPartnerId(BPartnerId.ofRepoId(order.getBill_BPartner_ID()))
				.collectionPointBPartnerId(BPartnerId.ofRepoId(order.getC_BPartner_ID()))
				.warehouseId(WarehouseId.ofRepoId(order.getM_Warehouse_ID()))
				.documentType(LogEntryDocumentType.PURCHASE_ORDER)
				.soTrx(SOTrx.PURCHASE)
				.processed(false)
				.quantity(quantity)
				.amount(amount)
				.transactionDate(InstantAndOrgId.ofTimestamp(order.getDateOrdered(), OrgId.ofRepoId(model.getAD_Org_ID())).toLocalDateAndOrgId(orgDAO::getTimeZone))
				.year(modularContractSettings.getYearAndCalendarId().yearId())
				.description(null)
				.modularContractTypeId(contractTypeId)
				.build());
	}

	@Override
	public @NonNull Optional<LogEntryReverseRequest> createLogEntryReverseRequest(@NonNull final I_C_OrderLine model, final @NonNull FlatrateTermId flatrateTermId)
	{
		return Optional.of(LogEntryReverseRequest.builder()
				.referencedModel(TableRecordReference.of(I_C_OrderLine.Table_Name, model.getC_OrderLine_ID()))
				.flatrateTermId(flatrateTermId)
				.build());
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(@NonNull final I_C_OrderLine model)
	{
		if (!probablyAppliesTo(model))
		{
			return Stream.empty();
		}

		final I_C_Order order = orderDAO.getById(OrderId.ofRepoId(model.getC_Order_ID()));
		if (order.isSOTrx())
		{
			return Stream.empty();
		}
		final I_C_Flatrate_Term flatrateTerm = flatrateDAO.getByOrderLineId(OrderLineId.ofRepoId(model.getC_OrderLine_ID()));
		return Stream.of(FlatrateTermId.ofRepoId(flatrateTerm.getC_Flatrate_Term_ID()));
	}
}
