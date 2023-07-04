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
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.log.LogEntryCreateRequest;
import de.metas.contracts.modular.log.LogEntryDocumentType;
import de.metas.contracts.modular.log.LogEntryReverseRequest;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.contracts.modular.settings.ModularContractType;
import de.metas.contracts.modular.settings.ModularContractTypeId;
import de.metas.contracts.modular.settings.ModuleConfig;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.IInOutDAO;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
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
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class PurchaseOrderLineModularContractHandler implements IModularContractTypeHandler<I_C_OrderLine>
{
	private static final AdMessageKey MSG_REACTIVATE_NOT_ALLOWED = AdMessageKey.of("de.metas.contracts.modular.impl.PurchaseOrderLineModularContractHandler.ReactivateNotAllowed");

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	public final ModularContractLogDAO modularContractLogDAO;
	public final ModularContractSettingsDAO modularContractSettingsDAO;

	public PurchaseOrderLineModularContractHandler(@NonNull final ModularContractLogDAO modularContractLogDAO,
			@NonNull final ModularContractSettingsDAO modularContractSettingsDAO)
	{
		this.modularContractLogDAO = modularContractLogDAO;
		this.modularContractSettingsDAO = modularContractSettingsDAO;
	}

	@NonNull
	@Override
	public Class<I_C_OrderLine> getType()
	{
		return I_C_OrderLine.class;
	}

	@Override
	public boolean applies(@NonNull final I_C_OrderLine orderLine)
	{
		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(orderLine.getC_Order_ID()));
		return SOTrx.ofBoolean(order.isSOTrx()).isPurchase();
	}

	@Override
	@NonNull
	public Optional<LogEntryCreateRequest> createLogEntryCreateRequest(
			@NonNull final I_C_OrderLine orderLine,
			@NonNull final FlatrateTermId modularContractId)
	{
		final ModularContractSettings modularContractSettings = modularContractSettingsDAO.getByFlatrateTermIdOrNull(modularContractId);
		if (modularContractSettings == null)
		{
			return Optional.empty();
		}

		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(orderLine.getC_Order_ID()));
		final Optional<ModularContractTypeId> modularContractTypeId = modularContractSettings.getModuleConfigs()
				.stream()
				.filter(config -> config.isMatchingClassName(PurchaseOrderLineModularContractHandler.class.getName()))
				.map(ModuleConfig::getModularContractType)
				.map(ModularContractType::getId)
				.findFirst();

		final I_C_UOM uomId = uomDAO.getById(UomId.ofRepoId(orderLine.getC_UOM_ID()));
		final Quantity quantity = Quantity.of(orderLine.getQtyEntered(), uomId);
		final Money amount = Money.of(orderLine.getLineNetAmt(), CurrencyId.ofRepoId(orderLine.getC_Currency_ID()));

		return modularContractTypeId.map(contractTypeId -> LogEntryCreateRequest.builder()
				.contractId(modularContractId)
				.productId(ProductId.ofRepoId(orderLine.getM_Product_ID()))
				.referencedRecord(TableRecordReference.of(I_C_OrderLine.Table_Name, orderLine.getC_OrderLine_ID()))
				.producerBPartnerId(BPartnerId.ofRepoId(order.getC_BPartner_ID()))
				.invoicingBPartnerId(BPartnerId.ofRepoId(order.getBill_BPartner_ID()))
				.collectionPointBPartnerId(BPartnerId.ofRepoId(order.getC_BPartner_ID()))
				.warehouseId(WarehouseId.ofRepoId(order.getM_Warehouse_ID()))
				.documentType(LogEntryDocumentType.PURCHASE_ORDER)
				.soTrx(SOTrx.PURCHASE)
				.processed(false)
				.quantity(quantity)
				.amount(amount)
				.transactionDate(LocalDateAndOrgId.ofTimestamp(order.getDateOrdered(),
															   OrgId.ofRepoId(orderLine.getAD_Org_ID()),
															   orgDAO::getTimeZone))
				.year(modularContractSettings.getYearAndCalendarId().yearId())
				.description(null)
				.modularContractTypeId(contractTypeId)
				.build());
	}

	@Override
	public @NonNull Optional<LogEntryReverseRequest> createLogEntryReverseRequest(
			@NonNull final I_C_OrderLine model,
			@NonNull final FlatrateTermId flatrateTermId)
	{
		return Optional.of(LogEntryReverseRequest.builder()
								   .referencedModel(TableRecordReference.of(I_C_OrderLine.Table_Name, model.getC_OrderLine_ID()))
								   .flatrateTermId(flatrateTermId)
								   .build());
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(@NonNull final I_C_OrderLine orderLine)
	{
		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(orderLine.getC_Order_ID()));
		if (order.isSOTrx())
		{
			return Stream.empty();
		}

		return flatrateDAO.getByOrderLineId(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID()))
				.map(flatrateTerm -> FlatrateTermId.ofRepoId(flatrateTerm.getC_Flatrate_Term_ID()))
				.stream();
	}

	public void validateDocAction(
			@NonNull final I_C_OrderLine ignored,
			@NonNull final ModularContractService.ModelAction action)
	{
		if (action == ModularContractService.ModelAction.REVERSED || action == ModularContractService.ModelAction.REACTIVATED)
		{
			throw new AdempiereException(MSG_REACTIVATE_NOT_ALLOWED);
		}
	}

	@Override
	public void voidLinkedDocuments(@NonNull final I_C_OrderLine model, @NonNull final FlatrateTermId flatrateTermId)
	{
		final List<I_M_InOutLine> generatedInOutLines = inOutDAO.retrieveLinesForOrderLine(model);

		if (!generatedInOutLines.isEmpty())
		{
			throw new AdempiereException("PO cannot be voided!");
		}

		final I_C_Flatrate_Term contract = flatrateDAO.getById(flatrateTermId);

		flatrateBL.voidIt(contract);
	}
}
