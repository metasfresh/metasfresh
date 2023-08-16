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

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.Check;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IContractChangeBL;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
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
import de.metas.document.engine.IDocumentBL;
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
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static de.metas.contracts.IContractChangeBL.ChangeTerm_ACTION_VoidSingleContract;

@Component
public class PurchaseOrderLineModularContractHandler implements IModularContractTypeHandler<I_C_OrderLine>
{
	private static final AdMessageKey MSG_REACTIVATE_NOT_ALLOWED = AdMessageKey.of("de.metas.contracts.modular.impl.PurchaseOrderLineModularContractHandler.ReactivateNotAllowed");
	private static final AdMessageKey MSG_VOID_NOT_ALLOWED = AdMessageKey.of("de.metas.contracts.modular.impl.PurchaseOrderLineModularContractHandler.VoidNotAllowed");
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IContractChangeBL contractChangeBL = Services.get(IContractChangeBL.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final ModularContractSettingsDAO modularContractSettingsDAO;

	public PurchaseOrderLineModularContractHandler(@NonNull final ModularContractSettingsDAO modularContractSettingsDAO)
	{
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
			throw new AdempiereException("Invalid contract - missing settings")
					.appendParametersToMessage()
					.setParameter("C_OrderLine_ID", orderLine.getC_OrderLine_ID())
					.setParameter("ModularContractID", modularContractId);
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
				.contractType(LogEntryContractType.MODULAR_CONTRACT)
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
		switch (action)
		{
			case COMPLETED, VOIDED -> {}
			case REACTIVATED, REVERSED -> throw new AdempiereException(MSG_REACTIVATE_NOT_ALLOWED);
			default -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED);
		}
	}

	@Override
	public void cancelLinkedContractsIfAllowed(@NonNull final I_C_OrderLine model, @NonNull final FlatrateTermId flatrateTermId)
	{
		final List<I_M_InOutLine> generatedInOutLines = inOutDAO.retrieveLinesForOrderLine(model);

		if (!generatedInOutLines.isEmpty())
		{
			final Set<Integer> inoutIds = generatedInOutLines.stream()
					.map(I_M_InOutLine::getM_InOut_ID)
					.collect(ImmutableSet.toImmutableSet());

			throw new AdempiereException(MSG_VOID_NOT_ALLOWED, inoutIds);
		}

		final I_C_Flatrate_Term contract = flatrateDAO.getById(flatrateTermId);

		// dev-note: for now set fallback endDate one year from start date
		final Timestamp endDate = Optional.ofNullable(contract.getEndDate())
				.orElse(TimeUtil.addYears(contract.getStartDate(), 1));

		final IContractChangeBL.ContractChangeParameters parameters = IContractChangeBL.ContractChangeParameters.builder()
				.changeDate(endDate)
				.action(ChangeTerm_ACTION_VoidSingleContract)
				.build();

		contractChangeBL.cancelContract(contract, parameters);
	}

	@Override
	public void createContractIfRequired(final @NonNull I_C_OrderLine orderLine)
	{
		if (!isModularContractLine(orderLine))
		{
			return;
		}

		if (flatrateBL.existsTermForOrderLine(orderLine))
		{
			return;
		}

		createModularContract(orderLine);
	}

	private boolean isModularContractLine(@NonNull final I_C_OrderLine orderLine)
	{
		return Optional.ofNullable(ConditionsId.ofRepoIdOrNull(orderLine.getC_Flatrate_Conditions_ID()))
				.map(flatrateBL::isModularContract)
				.orElse(false);
	}

	@NonNull
	private I_C_Flatrate_Term createModularContract(@NonNull final I_C_OrderLine orderLine)
	{
		final ConditionsId conditionsId = ConditionsId.ofRepoId(orderLine.getC_Flatrate_Conditions_ID());
		validateContractSettingEligible(conditionsId);

		final I_C_Flatrate_Term newTerm = flatrateBL.createContractForOrderLine(orderLine);

		documentBL.processEx(newTerm, X_C_Flatrate_Term.DOCACTION_Complete, X_C_Flatrate_Term.DOCSTATUS_Completed);

		return newTerm;
	}

	private void validateContractSettingEligible(@NonNull final ConditionsId conditionsId)
	{
		final ModularContractSettings settings = modularContractSettingsDAO.getByFlatrateConditonsIdOrNull(conditionsId);

		Check.assume(settings != null, "ModularContractSettings should not be null at this stage!");

		if (settings.getModuleConfigs().isEmpty())
		{
			throw new AdempiereException("Could not create contract! Missing module configs for modular contract term")
					.appendParametersToMessage()
					.setParameter("ConditionsId", conditionsId)
					.markAsUserValidationError();
		}
	}
}
