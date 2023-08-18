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
import de.metas.calendar.standard.CalendarId;
import de.metas.calendar.standard.YearId;
import de.metas.common.util.Check;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.FlatrateTermRequest.ModularFlatrateTermQuery;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.ModularContract_Constants;
import de.metas.contracts.modular.log.LogEntryContractType;
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
import de.metas.i18n.IMsgBL;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
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
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_UOM;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class SalesInvoiceLineModularContractHandler implements IModularContractTypeHandler<I_C_InvoiceLine>
{
	private static final AdMessageKey MSG_ON_COMPLETE_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.impl.SalesInvoiceLineModularContractHandler.OnComplete.Description");
	private static final AdMessageKey MSG_ON_REVERSE_DESCRIPTION = AdMessageKey.of("de.metas.contracts.modular.impl.SalesInvoiceLineModularContractHandler.OnReverse.Description");

	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final ModularContractSettingsDAO modularContractSettingsDAO;
	private final ModularContractLogDAO contractLogDAO;

	public SalesInvoiceLineModularContractHandler(
			@NonNull final ModularContractSettingsDAO modularContractSettingsDAO,
			@NonNull final ModularContractLogDAO contractLogDAO)
	{
		this.modularContractSettingsDAO = modularContractSettingsDAO;
		this.contractLogDAO = contractLogDAO;
	}

	@Override
	public @NonNull Class<I_C_InvoiceLine> getType()
	{
		return I_C_InvoiceLine.class;
	}

	@Override
	public boolean applies(final @NonNull I_C_InvoiceLine invoiceLine)
	{
		final I_C_Invoice invoice = invoiceBL.getById(InvoiceId.ofRepoId(invoiceLine.getC_Invoice_ID()));
		final SOTrx soTrx = SOTrx.ofBoolean(invoice.isSOTrx());
		if (soTrx.isPurchase())
		{
			return false;
		}

		final CalendarId harvestingCalendarId = CalendarId.ofRepoIdOrNull(invoice.getC_Harvesting_Calendar_ID());
		if (harvestingCalendarId == null)
		{
			return false;
		}

		final YearId harvestingYearId = YearId.ofRepoIdOrNull(invoice.getHarvesting_Year_ID());
		if (harvestingYearId == null)
		{
			return false;
		}

		final BPartnerId warehousePartnerId = Optional.ofNullable(WarehouseId.ofRepoIdOrNull(invoice.getM_Warehouse_ID()))
				.map(warehouseBL::getBPartnerId)
				.orElse(null);

		if (warehousePartnerId == null)
		{
			return false;
		}

		final ModularFlatrateTermQuery modularFlatrateTermQuery = ModularFlatrateTermQuery.builder()
				.calendarId(harvestingCalendarId)
				.yearId(harvestingYearId)
				.bPartnerId(warehousePartnerId)
				.soTrx(SOTrx.PURCHASE)
				.typeConditions(TypeConditions.MODULAR_CONTRACT)
				.productId(ProductId.ofRepoId(invoiceLine.getM_Product_ID()))
				.build();

		return isModularContractInProgress(modularFlatrateTermQuery);
	}

	@Override
	public @NonNull Optional<LogEntryCreateRequest> createLogEntryCreateRequest(
			final @NonNull I_C_InvoiceLine invoiceLine,
			final @NonNull FlatrateTermId modularContractId)
	{
		final I_C_Flatrate_Term contract = flatrateBL.getById(modularContractId);
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(contract.getBill_BPartner_ID());

		final ModularContractSettings modularContractSettings = modularContractSettingsDAO.getByFlatrateTermIdOrNull(modularContractId);
		Check.assume(modularContractSettings != null, "ModularContractSettings should not be null ad this stage! Invalid contract ID {}", modularContractId);

		final Optional<ModularContractTypeId> modularContractTypeId = modularContractSettings.getModuleConfigs()
				.stream()
				.filter(config -> config.isMatchingClassName(SalesInvoiceLineModularContractHandler.class.getName()))
				.map(ModuleConfig::getModularContractType)
				.map(ModularContractType::getId)
				.findFirst();

		final I_C_UOM uomId = uomDAO.getById(UomId.ofRepoId(invoiceLine.getC_UOM_ID()));
		final Quantity quantity = Quantity.of(invoiceLine.getQtyInvoiced(), uomId);

		final I_C_Invoice invoice = invoiceBL.getById(InvoiceId.ofRepoId(invoiceLine.getC_Invoice_ID()));
		final Money amount = Money.of(invoiceLine.getLineNetAmt(), CurrencyId.ofRepoId(invoice.getC_Currency_ID()));

		final ProductId productId = ProductId.ofRepoId(invoiceLine.getM_Product_ID());

		final String description = msgBL.getMsg(MSG_ON_COMPLETE_DESCRIPTION, ImmutableList.of(String.valueOf(productId.getRepoId()), quantity.toString()));

		return modularContractTypeId.map(contractTypeId -> LogEntryCreateRequest.builder()
				.referencedRecord(TableRecordReference.of(I_C_InvoiceLine.Table_Name, invoiceLine.getC_InvoiceLine_ID()))
				.contractId(modularContractId)
				.collectionPointBPartnerId(bPartnerId)
				.producerBPartnerId(bPartnerId)
				.invoicingBPartnerId(bPartnerId)
				.warehouseId(WarehouseId.ofRepoId(invoice.getM_Warehouse_ID()))
				.productId(productId)
				.documentType(LogEntryDocumentType.SALES_INVOICE)
				.contractType(LogEntryContractType.MODULAR_CONTRACT)
				.soTrx(SOTrx.PURCHASE)
				.processed(false)
				.quantity(quantity)
				.amount(amount)
				.transactionDate(LocalDateAndOrgId.ofTimestamp(invoice.getDateInvoiced(),
															   OrgId.ofRepoId(invoiceLine.getAD_Org_ID()),
															   orgDAO::getTimeZone))
				.year(modularContractSettings.getYearAndCalendarId().yearId())
				.description(description)
				.modularContractTypeId(contractTypeId)
				.build());
	}

	@Override
	public @NonNull Optional<LogEntryReverseRequest> createLogEntryReverseRequest(
			final @NonNull I_C_InvoiceLine invoiceLine,
			final @NonNull FlatrateTermId flatrateTermId)
	{
		final LogEntryReverseRequest request = LogEntryReverseRequest.builder()
				.referencedModel(TableRecordReference.of(I_C_InvoiceLine.Table_Name, invoiceLine.getC_InvoiceLine_ID()))
				.flatrateTermId(flatrateTermId)
				.build();

		final I_C_UOM uom = uomDAO.getById(UomId.ofRepoId(invoiceLine.getC_UOM_ID()));
		final BigDecimal loggedQty = contractLogDAO.retrieveQuantityFromExistingLog(request);

		final Quantity quantity = Quantity.of(loggedQty, uom);

		final String description = msgBL.getMsg(MSG_ON_REVERSE_DESCRIPTION, ImmutableList.of(String.valueOf(invoiceLine.getM_Product_ID()), quantity.toString()));

		return Optional.of(request.toBuilder()
								   .description(description)
								   .build());
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(@NonNull final I_C_InvoiceLine invoiceLine)
	{
		final I_C_Invoice invoice = invoiceBL.getById(InvoiceId.ofRepoId(invoiceLine.getC_Invoice_ID()));

		final CalendarId harvestingCalendarId = CalendarId.ofRepoIdOrNull(invoice.getC_Harvesting_Calendar_ID());

		final YearId harvestingYearId = YearId.ofRepoIdOrNull(invoice.getHarvesting_Year_ID());

		final WarehouseId warehouseId = WarehouseId.ofRepoIdOrNull(invoice.getM_Warehouse_ID());
		Check.assume(warehouseId != null, "WarehouseId should not be null at this stage!");

		final ModularFlatrateTermQuery request = ModularFlatrateTermQuery.builder()
				.calendarId(harvestingCalendarId)
				.yearId(harvestingYearId)
				.bPartnerId(warehouseBL.getBPartnerId(warehouseId))
				.productId(ProductId.ofRepoId(invoiceLine.getM_Product_ID()))
				.typeConditions(TypeConditions.MODULAR_CONTRACT)
				.soTrx(SOTrx.PURCHASE)
				.build();

		return streamModularContracts(request)
				.map(I_C_Flatrate_Term::getC_Flatrate_Term_ID)
				.map(FlatrateTermId::ofRepoId);
	}

	@Override
	public void validateDocAction(final @NonNull I_C_InvoiceLine model, final ModularContractService.@NonNull ModelAction action)
	{
		switch (action)
		{
			case COMPLETED, REVERSED -> {}
			default -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED);
		}
	}
}
