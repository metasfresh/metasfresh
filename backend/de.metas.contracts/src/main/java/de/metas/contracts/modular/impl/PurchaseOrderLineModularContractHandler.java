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

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.location.adapter.ContractDocumentLocationAdapterFactory;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Data;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
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
import de.metas.document.engine.IDocumentBL;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.save;

@Component
public class PurchaseOrderLineModularContractHandler implements IModularContractTypeHandler<I_C_OrderLine>
{
	private static final Logger logger = LogManager.getLogger(PurchaseOrderLineModularContractHandler.class);

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
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
		return I_C_OrderLine.class.isAssignableFrom(model.getClass());
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

		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(model.getC_Order_ID()));
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

		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(model.getC_Order_ID()));
		if (order.isSOTrx())
		{
			return Stream.empty();
		}

		final FlatrateTermId flatrateTermId = Optional.ofNullable(flatrateDAO.getByOrderLineId(OrderLineId.ofRepoId(model.getC_OrderLine_ID())))
				.map(I_C_Flatrate_Term::getC_Flatrate_Term_ID)
				.map(FlatrateTermId::ofRepoIdOrNull)
				.orElse(null);

		return Stream.ofNullable(flatrateTermId);
	}

	@Override
	public @NonNull Stream<ConditionsId> getContractConditionIds(@NonNull final I_C_OrderLine model)
	{
		if (!probablyAppliesTo(model))
		{
			return Stream.empty();
		}

		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(model.getC_Order_ID()));
		if (order.isSOTrx())
		{
			return Stream.empty();
		}

		final ConditionsId conditionsId = ConditionsId.ofRepoIdOrNull(model.getC_Flatrate_Conditions_ID());
		return Stream.ofNullable(conditionsId);
	}

	public void createModularContractIfRequired(@NonNull final I_C_OrderLine model)
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(model.getC_OrderLine_ID());
		final I_C_Flatrate_Term existingContract = flatrateDAO.getByOrderLineId(orderLineId);

		if (existingContract != null)
		{
			logger.info("Order line skipped! Contract already existing for OrderLineId={}.", orderLineId);
			return;
		}

		createContract(model);
	}

	@NonNull
	private I_C_Flatrate_Term createContract(@NonNull final I_C_OrderLine orderLine)
	{
		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(orderLine.getC_Order_ID()));
		final I_C_Flatrate_Term newContract = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Term.class);

		//order references
		newContract.setC_OrderLine_Term_ID(orderLine.getC_OrderLine_ID());
		newContract.setC_Order_Term_ID(orderLine.getC_Order_ID());

		//qty of the whole contract
		newContract.setPlannedQtyPerUnit(orderLine.getQtyEnteredInPriceUOM());
		newContract.setC_UOM_ID(orderLine.getPrice_UOM_ID());

		//price & tax
		newContract.setM_Product_ID(orderLine.getM_Product_ID());
		newContract.setPriceActual(orderLine.getPriceActual());
		newContract.setC_Currency_ID(orderLine.getC_Currency_ID());

		Check.assume(orderLine.getC_TaxCategory_ID() > 0, "C_TaxCategory_ID must be set!");
		newContract.setC_TaxCategory_ID(orderLine.getC_TaxCategory_ID());
		newContract.setIsTaxIncluded(order.isTaxIncluded());

		//contract dates
		newContract.setStartDate(order.getDatePromised());
		newContract.setMasterStartDate(order.getDatePromised());

		//bill partner info
		final BPartnerLocationAndCaptureId billToLocationId = orderBL.getBillToLocationId(order);

		final BPartnerContactId billToContactId = BPartnerContactId.ofRepoIdOrNull(billToLocationId.getBpartnerId(), order.getBill_User_ID());
		ContractDocumentLocationAdapterFactory
				.billLocationAdapter(newContract)
				.setFrom(billToLocationId, billToContactId);

		final I_C_BPartner billPartner = bPartnerDAO.getById(billToLocationId.getBpartnerId());
		final I_C_Flatrate_Data existingData = flatrateDAO.retrieveOrCreateFlatrateData(billPartner);
		newContract.setC_Flatrate_Data(existingData);

		final ConditionsId conditionsId = ConditionsId.ofRepoIdOrNull(orderLine.getC_Flatrate_Conditions_ID());
		Check.assume(conditionsId != null, "ConditionsId should not be null at this stage!");

		final I_C_Flatrate_Conditions conditions = flatrateDAO.getConditionsById(conditionsId);
		newContract.setC_Flatrate_Conditions_ID(conditions.getC_Flatrate_Conditions_ID());
		newContract.setType_Conditions(conditions.getType_Conditions());

		//doc
		newContract.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Drafted);
		newContract.setDocAction(X_C_Flatrate_Term.DOCACTION_Complete);

		save(newContract);

		documentBL.processEx(newContract, X_C_Flatrate_Term.DOCACTION_Complete, X_C_Flatrate_Term.DOCSTATUS_Completed);

		return newContract;
	}
}
