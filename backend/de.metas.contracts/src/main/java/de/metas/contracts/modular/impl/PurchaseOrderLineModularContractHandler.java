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
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IContractChangeBL;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.ModularContract_Constants;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.contracts.modular.settings.ModularContractSettingsDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.IInOutDAO;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static de.metas.contracts.IContractChangeBL.ChangeTerm_ACTION_VoidSingleContract;

@Component
public class PurchaseOrderLineModularContractHandler implements IModularContractTypeHandler<I_C_OrderLine>
{
	private static final String CREATED_FROM_PURCHASE_ORDER_LINE_DYN_ATTRIBUTE = "SourcePurchaseOrderLine";
	private static final String INTERIM_CONTRACT_DYN_ATTRIBUTE = "InterimContract";

	private static final AdMessageKey MSG_REACTIVATE_NOT_ALLOWED = AdMessageKey.of("de.metas.contracts.modular.impl.PurchaseOrderLineModularContractHandler.ReactivateNotAllowed");
	private static final AdMessageKey MSG_VOID_NOT_ALLOWED = AdMessageKey.of("de.metas.contracts.modular.impl.PurchaseOrderLineModularContractHandler.VoidNotAllowed");
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IContractChangeBL contractChangeBL = Services.get(IContractChangeBL.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final ModularContractSettingsDAO modularContractSettingsDAO;

	public PurchaseOrderLineModularContractHandler(@NonNull final ModularContractSettingsDAO modularContractSettingsDAO)
	{
		this.modularContractSettingsDAO = modularContractSettingsDAO;
	}

	@Nullable
	public static I_C_OrderLine getSourcePurchaseOrderLine(@NonNull final I_C_Flatrate_Term contract)
	{
		return InterfaceWrapperHelper.getDynAttribute(contract, CREATED_FROM_PURCHASE_ORDER_LINE_DYN_ATTRIBUTE);
	}

	public static void crossLinkInterimContractAndSourcePurchaseOrderLine(@NonNull final I_C_Flatrate_Term interimContract, @NonNull final I_C_OrderLine sourcePurchaseOrderLine)
	{
		setSourcePurchaseOrderLine(interimContract, sourcePurchaseOrderLine);
		InterfaceWrapperHelper.setDynAttribute(sourcePurchaseOrderLine, INTERIM_CONTRACT_DYN_ATTRIBUTE, interimContract);
	}

	@Nullable
	private static I_C_Flatrate_Term getInterimContract(@NonNull final I_C_OrderLine orderLine)
	{
		return InterfaceWrapperHelper.getDynAttribute(orderLine, INTERIM_CONTRACT_DYN_ATTRIBUTE);
	}

	private static void setSourcePurchaseOrderLine(final @NonNull I_C_Flatrate_Term contract, final @NonNull I_C_OrderLine sourcePurchaseOrderLine)
	{
		InterfaceWrapperHelper.setDynAttribute(contract, CREATED_FROM_PURCHASE_ORDER_LINE_DYN_ATTRIBUTE, sourcePurchaseOrderLine);
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
	public boolean applies(final @NonNull LogEntryContractType logEntryContractType)
	{
		return logEntryContractType.isModularContractType();
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(@NonNull final I_C_OrderLine orderLine)
	{
		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(orderLine.getC_Order_ID()));
		if (order.isSOTrx())
		{
			return Stream.empty();
		}

		return flatrateDAO.getByOrderLineId(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID()), TypeConditions.MODULAR_CONTRACT)
				.map(flatrateTerm -> FlatrateTermId.ofRepoId(flatrateTerm.getC_Flatrate_Term_ID()))
				.stream();
	}

	public void validateDocAction(
			@NonNull final I_C_OrderLine ignored,
			@NonNull final ModelAction action)
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

	public void handleAction(
			@NonNull final I_C_OrderLine orderLine,
			@NonNull final ModelAction modelAction,
			@NonNull final ModularContractService contractService)
	{
		if (modelAction != ModelAction.COMPLETED)
		{
			return;
		}

		//dev-note: the interim contract that was just created as a result of completing the current purchase order
		final I_C_Flatrate_Term interimContract = PurchaseOrderLineModularContractHandler.getInterimContract(orderLine);
		if (interimContract != null)
		{
			Check.assumeNotNull(interimContract.getEndDate(), "End Date shouldn't be null");

			contractService.invokeWithModel(interimContract, modelAction, LogEntryContractType.INTERIM);
		}
	}

	private boolean isModularContractLine(@NonNull final I_C_OrderLine orderLine)
	{
		return Optional.ofNullable(ConditionsId.ofRepoIdOrNull(orderLine.getC_Flatrate_Conditions_ID()))
				.map(flatrateBL::isModularContract)
				.orElse(false);
	}

	private void createModularContract(@NonNull final I_C_OrderLine orderLine)
	{
		final ConditionsId conditionsId = ConditionsId.ofRepoId(orderLine.getC_Flatrate_Conditions_ID());
		validateContractSettingEligible(conditionsId);

		final I_C_Flatrate_Term modularContract = flatrateBL.createContractForOrderLine(orderLine);

		setSourcePurchaseOrderLine(modularContract, orderLine);

		flatrateBL.complete(modularContract);
	}

	private void validateContractSettingEligible(@NonNull final ConditionsId conditionsId)
	{
		final ModularContractSettings settings = modularContractSettingsDAO.getByFlatrateConditionsId(conditionsId);
		if (settings.getModuleConfigs().isEmpty())
		{
			throw new AdempiereException("Could not create contract! Missing module configs for modular contract term")
					.appendParametersToMessage()
					.setParameter("ConditionsId", conditionsId)
					.markAsUserValidationError();
		}
	}
}
