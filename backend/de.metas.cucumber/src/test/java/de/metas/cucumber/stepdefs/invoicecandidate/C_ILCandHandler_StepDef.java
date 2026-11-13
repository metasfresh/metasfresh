/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.cucumber.stepdefs.invoicecandidate;

import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.util.Services;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Temporarily overrides settings of the invoice candidate handlers ({@code C_ILCandHandler}); the original values are restored after the scenario.
 */
public class C_ILCandHandler_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Original {@code IsAllowDifferentShipmentsInSameInvoiceLine} value by {@code C_ILCandHandler_ID}.
	 * Restored by {@link #restoreHandlersAfterScenario()}.
	 */
	private final Map<Integer, Boolean> priorIsAllowDifferentShipmentsByHandlerId = new LinkedHashMap<>();

	/**
	 * Sets {@code C_ILCandHandler.IsAllowDifferentShipmentsInSameInvoiceLine} on the active invoice candidate handler(s) of the given table for the current scenario;
	 * the original value is restored after the scenario.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns (none — parameters are in the step text, not a DataTable)
	 * @cucumber.depends (none)
	 * @cucumber.example
	 * <pre>
	 * Given temporarily set IsAllowDifferentShipmentsInSameInvoiceLine=true on the C_ILCandHandler of table C_OrderLine
	 * </pre>
	 */
	@And("temporarily set IsAllowDifferentShipmentsInSameInvoiceLine={word} on the C_ILCandHandler of table {word}")
	public void temporarily_set_IsAllowDifferentShipmentsInSameInvoiceLine(@NonNull final String valueStr, @NonNull final String tableName)
	{
		if (!"true".equals(valueStr) && !"false".equals(valueStr))
		{
			throw new AdempiereException("Expected true or false but got: " + valueStr);
		}
		final boolean value = Boolean.parseBoolean(valueStr);

		final List<I_C_ILCandHandler> handlers = queryBL.createQueryBuilderOutOfTrx(I_C_ILCandHandler.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_ILCandHandler.COLUMNNAME_TableName, tableName)
				.orderBy(I_C_ILCandHandler.COLUMNNAME_C_ILCandHandler_ID)
				.create()
				.list();
		if (handlers.isEmpty())
		{
			throw new AdempiereException("No active C_ILCandHandler found for TableName=" + tableName);
		}

		for (final I_C_ILCandHandler handler : handlers)
		{
			priorIsAllowDifferentShipmentsByHandlerId.putIfAbsent(handler.getC_ILCandHandler_ID(), handler.isAllowDifferentShipmentsInSameInvoiceLine());
			handler.setIsAllowDifferentShipmentsInSameInvoiceLine(value);
			// saving fires the model cache invalidation, so InvoiceCandidateHandlerDAO.retrieveAll sees the new value
			InterfaceWrapperHelper.save(handler);
		}
	}

	/**
	 * Restores the original {@code IsAllowDifferentShipmentsInSameInvoiceLine} values of the handlers changed in the scenario.
	 */
	@After
	public void restoreHandlersAfterScenario()
	{
		if (priorIsAllowDifferentShipmentsByHandlerId.isEmpty())
		{
			return;
		}

		for (final Map.Entry<Integer, Boolean> entry : priorIsAllowDifferentShipmentsByHandlerId.entrySet())
		{
			final I_C_ILCandHandler handler = InterfaceWrapperHelper.load(entry.getKey(), I_C_ILCandHandler.class);
			handler.setIsAllowDifferentShipmentsInSameInvoiceLine(entry.getValue());
			InterfaceWrapperHelper.save(handler);
		}
		priorIsAllowDifferentShipmentsByHandlerId.clear();
	}
}
