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

package de.metas.cucumber.stepdefs.order;

import de.metas.order.OrderLineId;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessInfo;
import de.metas.util.Services;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_OrderLine;
import org.junit.jupiter.api.Assertions;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@RequiredArgsConstructor
public class C_OrderLine_Split_StepDef
{
	@NonNull private final C_OrderLine_StepDefData orderLineTable;

	@NonNull private final IADProcessDAO processDAO = Services.get(IADProcessDAO.class);

	@Nullable
	private String lastValidationErrorMessage;

	@When("the C_OrderLine_SplitQty process is run on {string} with QtyToSplitOff = {bigdecimal}")
	public void runSplit(@NonNull final String orderLineIdentifier, @NonNull final BigDecimal qtyToSplitOff)
	{
		final I_C_OrderLine orderLine = orderLineTable.get(orderLineIdentifier);
		final OrderLineId orderLineId = OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID());
		final AdProcessId processId = processDAO.retrieveProcessIdByValue("C_OrderLine_SplitQty");

		ProcessInfo.builder()
				.setAD_Process_ID(processId.getRepoId())
				.setRecord(I_C_OrderLine.Table_Name, orderLineId.getRepoId())
				.addParameter("QtyToSplitOff", qtyToSplitOff)
				.buildAndPrepareExecution()
				.executeSync();
	}

	@When("the C_OrderLine_SplitQty process is run on {string} with QtyToSplitOff = {bigdecimal} expecting validation failure")
	public void runSplitExpectFailure(@NonNull final String orderLineIdentifier, @NonNull final BigDecimal qtyToSplitOff)
	{
		try
		{
			runSplit(orderLineIdentifier, qtyToSplitOff);
			Assertions.fail("Expected validation failure for QtyToSplitOff = " + qtyToSplitOff + " on order line " + orderLineIdentifier);
		}
		catch (final AdempiereException e)
		{
			// expected — message asserted in subsequent Then-step
			lastValidationErrorMessage = e.getLocalizedMessage();
		}
	}

	@Then("the validation error message includes {string}")
	public void assertErrorMessageIncludes(@NonNull final String expectedSubstring)
	{
		Assertions.assertNotNull(lastValidationErrorMessage, "No validation error was captured");
		Assertions.assertTrue(
				lastValidationErrorMessage.contains(expectedSubstring),
				"Expected error to contain '" + expectedSubstring + "' but was: " + lastValidationErrorMessage);
	}
}
