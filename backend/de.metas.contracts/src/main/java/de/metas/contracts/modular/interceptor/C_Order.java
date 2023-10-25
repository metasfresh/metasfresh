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

package de.metas.contracts.modular.interceptor;

import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.contracts.modular.log.ModularContractLogDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import static de.metas.contracts.modular.ModelAction.COMPLETED;
import static de.metas.contracts.modular.ModelAction.REACTIVATED;
import static de.metas.contracts.modular.ModelAction.REVERSED;
import static de.metas.contracts.modular.ModelAction.VOIDED;

/**
 * Glue-code that invokes the {@link ModularContractService} on certain order events.
 * <p/>
 * Note: even now where we don't yet implemented modular sales contracts, we need to act on sales orders, too.<br/>
 * That's because the eventual sales (and their totalamounts) can play a role in the purchase contracts final settlement.
 */
@Component
@Interceptor(I_C_Order.class)
public class C_Order
{
	private static final AdMessageKey MSG_HARVESTING_DETAILS_CHANGES_NOT_ALLOWED = AdMessageKey.of("de.metas.contracts.modular.interceptor.C_Order.HarvestingDetailsChangeNotAllowed");

	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	private final ModularContractService contractService;
	private final ModularContractLogDAO contractLogDAO;

	public C_Order(
			@NonNull final ModularContractService contractService,
			@NonNull final ModularContractLogDAO contractLogDAO)
	{
		this.contractService = contractService;
		this.contractLogDAO = contractLogDAO;
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void afterComplete(@NonNull final I_C_Order orderRecord)
	{
		invokeHandlerForEachLine(orderRecord, COMPLETED);
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_VOID)
	public void afterVoid(@NonNull final I_C_Order orderRecord)
	{
		invokeHandlerForEachLine(orderRecord, VOIDED);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REVERSEACCRUAL, ModelValidator.TIMING_AFTER_REVERSECORRECT })
	public void afterReverse(@NonNull final I_C_Order orderRecord)
	{
		invokeHandlerForEachLine(orderRecord, REVERSED);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REACTIVATE })
	public void afterReactivate(@NonNull final I_C_Order orderRecord)
	{
		invokeHandlerForEachLine(orderRecord, REACTIVATED);
	}

	@CalloutMethod(columnNames = { I_C_Order.COLUMNNAME_C_Harvesting_Calendar_ID, I_C_Order.COLUMNNAME_Harvesting_Year_ID })
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_C_Order.COLUMNNAME_C_Harvesting_Calendar_ID, I_C_Order.COLUMNNAME_Harvesting_Year_ID })
	public void validateHarvestingDatesForSalesOrder(@NonNull final I_C_Order orderRecord)
	{
		final SOTrx soTrx = SOTrx.ofBoolean(orderRecord.isSOTrx());
		if (soTrx.isPurchase())
		{
			return;
		}

		final boolean hasAnyModularLogs = orderDAO.retrieveOrderLines(orderRecord)
				.stream()
				.map(record -> TableRecordReference.of(I_C_OrderLine.Table_Name, record.getC_OrderLine_ID()))
				.anyMatch(contractLogDAO::hasAnyModularLogs);

		if (!hasAnyModularLogs)
		{
			return;
		}

		throw new AdempiereException(MSG_HARVESTING_DETAILS_CHANGES_NOT_ALLOWED)
				.markAsUserValidationError();
	}

	private void invokeHandlerForEachLine(
			@NonNull final I_C_Order orderRecord,
			@NonNull final ModelAction modelAction)
	{
		orderDAO.retrieveOrderLines(orderRecord)
				.forEach(line -> contractService.invokeWithModel(line, modelAction, LogEntryContractType.MODULAR_CONTRACT));
	}
}
