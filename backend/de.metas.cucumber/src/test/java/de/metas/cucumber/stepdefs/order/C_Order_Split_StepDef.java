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

import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
import de.metas.util.Services;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for invoking the {@code C_Order_Split} AD_Process and asserting its outcome.
 *
 * <h3>Steps provided:</h3>
 * <ul>
 *   <li>{@code the C_Order_Split process is invoked on "<identifier>"} — runs the process; fails if the process result is an error</li>
 *   <li>{@code the C_Order_Split process is invoked on "<identifier>" expecting validation failure} — runs the process, captures the error message; asserts an error WAS raised</li>
 *   <li>{@code the validation error message includes "<substring>"} — asserts the captured error message contains the expected substring</li>
 *   <li>{@code the continuation order for "<identifier>" is found and stored as "<newIdentifier>"} — looks up the NEW SO by POReference=oldSO.DocumentNo and registers it</li>
 *   <li>{@code no continuation order exists for "<identifier>"} — asserts that no C_Order with POReference=oldSO.DocumentNo was created</li>
 *   <li>{@code the continuation order "<identifier>" has exactly <N> line(s)} — asserts line count on the continuation order</li>
 *   <li>{@code the continuation order "<identifier>" has exactly 1 line with QtyEntered=<qty> for product "<productIdentifier>"} — asserts a specific line's qty</li>
 *   <li>{@code the continuation order "<identifier>" has no line for product "<productIdentifier>"} — asserts a product does NOT appear in the continuation order</li>
 * </ul>
 */
@RequiredArgsConstructor
public class C_Order_Split_StepDef
{
	@NonNull private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final C_Order_StepDefData orderTable;
	@NonNull private final M_Product_StepDefData productTable;

	/** Stores the last validation-failure message from the process, available for assertion by the next Then step. */
	@Nullable
	private String lastValidationErrorMessage;

	// ---- process invocation ----

	/**
	 * Invokes the {@code C_Order_Split} process on the order identified by {@code orderIdentifier}.
	 * Fails immediately if the process returns an error result.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * When the C_Order_Split process is invoked on "so_os10"
	 * </pre>
	 */
	@When("the C_Order_Split process is invoked on {string}")
	public void invokeOrderSplit(@NonNull final String orderIdentifier)
	{
		lastValidationErrorMessage = null;

		final I_C_Order order = orderTable.get(StepDefDataIdentifier.ofString(orderIdentifier));
		final ProcessExecutionResult result = buildAndExecuteProcess(order);

		if (result.isError())
		{
			throw new AssertionError("C_Order_Split process returned an error (expected success): " + result.getSummary());
		}
	}

	/**
	 * Invokes the {@code C_Order_Split} process on the order identified by {@code orderIdentifier},
	 * expecting it to throw an {@link AdempiereException} due to a validation guard.
	 * Captures the exception message for assertion by {@code the validation error message includes ...}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * When the C_Order_Split process is invoked on "so_os60" expecting validation failure
	 * </pre>
	 */
	@When("the C_Order_Split process is invoked on {string} expecting validation failure")
	public void invokeOrderSplitExpectingValidationFailure(@NonNull final String orderIdentifier)
	{
		lastValidationErrorMessage = null;

		final I_C_Order order = orderTable.get(StepDefDataIdentifier.ofString(orderIdentifier));
		try
		{
			final ProcessExecutionResult result = buildAndExecuteProcess(order);
			if (result.isError())
			{
				// Process returned an error result (not an exception) — treat as validation failure
				lastValidationErrorMessage = result.getSummary();
			}
			else
			{
				throw new AssertionError("C_Order_Split process was expected to fail with a validation error, but it succeeded.");
			}
		}
		catch (final AdempiereException ex)
		{
			lastValidationErrorMessage = ex.getMessage();
		}
		catch (final Exception ex)
		{
			// Any other runtime exception from the process is also treated as a validation failure
			lastValidationErrorMessage = ex.getMessage();
		}

		assertThat(lastValidationErrorMessage)
				.as("Expected a non-null validation error message but got null")
				.isNotNull();
	}

	/**
	 * Asserts that the most recently captured validation error message contains the given {@code expectedSubstring}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the validation error message includes "C_Order_Split_NoShipments"
	 * </pre>
	 */
	@Then("the validation error message includes {string}")
	public void validationErrorMessageIncludes(@NonNull final String expectedSubstring)
	{
		assertThat(lastValidationErrorMessage)
				.as("Validation error message should contain '" + expectedSubstring + "'")
				.contains(expectedSubstring);
	}

	// ---- continuation order assertions ----

	/**
	 * Looks up the NEW (continuation) SO whose {@code POReference} equals the DocumentNo of the OLD SO,
	 * and registers it in {@link C_Order_StepDefData} under {@code newOrderIdentifier} so that
	 * subsequent {@code validate the created orders} steps can reference it.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the continuation order for "so_os10" is found and stored as "new_so_os10"
	 * </pre>
	 */
	@Then("the continuation order for {string} is found and stored as {string}")
	public void continuationOrderIsFoundAndStoredAs(
			@NonNull final String oldOrderIdentifier,
			@NonNull final String newOrderIdentifier)
	{
		final I_C_Order oldOrder = orderTable.get(StepDefDataIdentifier.ofString(oldOrderIdentifier));
		final String oldDocumentNo = oldOrder.getDocumentNo();

		final I_C_Order newOrder = queryBL.createQueryBuilder(I_C_Order.class)
				.addEqualsFilter(I_C_Order.COLUMNNAME_POReference, oldDocumentNo)
				.addEqualsFilter(I_C_Order.COLUMNNAME_IsSOTrx, true)
				.create()
				.firstOnlyOrNull(I_C_Order.class);

		assertThat(newOrder)
				.as("Expected a continuation SO with POReference='%s' but found none", oldDocumentNo)
				.isNotNull();

		orderTable.putOrReplace(StepDefDataIdentifier.ofString(newOrderIdentifier), newOrder);
	}

	/**
	 * Asserts that NO continuation SO was created for the given old order
	 * (i.e. no C_Order with POReference=oldSO.DocumentNo exists).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And no continuation order exists for "so_os60"
	 * </pre>
	 */
	@And("no continuation order exists for {string}")
	public void noContinuationOrderExistsFor(@NonNull final String oldOrderIdentifier)
	{
		final I_C_Order oldOrder = orderTable.get(StepDefDataIdentifier.ofString(oldOrderIdentifier));
		final String oldDocumentNo = oldOrder.getDocumentNo();

		final int count = queryBL.createQueryBuilder(I_C_Order.class)
				.addEqualsFilter(I_C_Order.COLUMNNAME_POReference, oldDocumentNo)
				.addEqualsFilter(I_C_Order.COLUMNNAME_IsSOTrx, true)
				.create()
				.count();

		assertThat(count)
				.as("Expected no continuation SO with POReference='%s', but found %d", oldDocumentNo, count)
				.isZero();
	}

	/**
	 * Asserts that the continuation order (already registered under {@code continuationOrderIdentifier})
	 * has exactly {@code expectedLineCount} active C_OrderLine rows.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And the continuation order "new_so_os20" has exactly 2 lines
	 * </pre>
	 */
	@And("the continuation order {string} has exactly {int} lines")
	public void continuationOrderHasExactlyNLines(
			@NonNull final String continuationOrderIdentifier,
			final int expectedLineCount)
	{
		final I_C_Order continuationOrder = orderTable.get(StepDefDataIdentifier.ofString(continuationOrderIdentifier));
		InterfaceWrapperHelper.refresh(continuationOrder);

		final int actualCount = queryBL.createQueryBuilder(I_C_OrderLine.class)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, continuationOrder.getC_Order_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.count();

		assertThat(actualCount)
				.as("Continuation order '%s' should have exactly %d lines but has %d", continuationOrderIdentifier, expectedLineCount, actualCount)
				.isEqualTo(expectedLineCount);
	}

	/**
	 * Asserts that the continuation order has exactly 1 active C_OrderLine for the given product
	 * with the specified QtyEntered.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And the continuation order "new_so_os10" has exactly 1 line with QtyEntered=2 for product "product_os"
	 * </pre>
	 */
	@And("the continuation order {string} has exactly 1 line with QtyEntered={int} for product {string}")
	public void continuationOrderHasLineWithQtyForProduct(
			@NonNull final String continuationOrderIdentifier,
			final int expectedQtyEntered,
			@NonNull final String productIdentifier)
	{
		final I_C_Order continuationOrder = orderTable.get(StepDefDataIdentifier.ofString(continuationOrderIdentifier));
		InterfaceWrapperHelper.refresh(continuationOrder);

		final I_M_Product product = productTable.get(StepDefDataIdentifier.ofString(productIdentifier));

		final List<I_C_OrderLine> matchingLines = queryBL.createQueryBuilder(I_C_OrderLine.class)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, continuationOrder.getC_Order_ID())
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_C_OrderLine.class);

		assertThat(matchingLines)
				.as("Expected exactly 1 line for product '%s' in continuation order '%s'", productIdentifier, continuationOrderIdentifier)
				.hasSize(1);

		final I_C_OrderLine line = matchingLines.get(0);
		assertThat(line.getQtyEntered())
				.as("QtyEntered for product '%s' in continuation order '%s'", productIdentifier, continuationOrderIdentifier)
				.isEqualByComparingTo(BigDecimal.valueOf(expectedQtyEntered));
	}

	/**
	 * Asserts that the continuation order has NO active C_OrderLine for the given product.
	 * Used to verify that fully-delivered or over-delivered lines from the OLD SO
	 * were NOT copied to the NEW SO.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And the continuation order "new_so_os20" has no line for product "product_os"
	 * </pre>
	 */
	@And("the continuation order {string} has no line for product {string}")
	public void continuationOrderHasNoLineForProduct(
			@NonNull final String continuationOrderIdentifier,
			@NonNull final String productIdentifier)
	{
		final I_C_Order continuationOrder = orderTable.get(StepDefDataIdentifier.ofString(continuationOrderIdentifier));
		InterfaceWrapperHelper.refresh(continuationOrder);

		final I_M_Product product = productTable.get(StepDefDataIdentifier.ofString(productIdentifier));

		final int count = queryBL.createQueryBuilder(I_C_OrderLine.class)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, continuationOrder.getC_Order_ID())
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.count();

		assertThat(count)
				.as("Continuation order '%s' should NOT have a line for product '%s', but found %d", continuationOrderIdentifier, productIdentifier, count)
				.isZero();
	}

	// ---- private helpers ----

	private ProcessExecutionResult buildAndExecuteProcess(@NonNull final I_C_Order order)
	{
		final AdProcessId processId = adProcessDAO.retrieveProcessIdByValue("C_Order_Split");
		assertThat(processId).as("AD_Process 'C_Order_Split' must exist in the database").isNotNull();

		return ProcessInfo.builder()
				.setAD_Process_ID(processId.getRepoId())
				.setRecord(I_C_Order.Table_Name, order.getC_Order_ID())
				.buildAndPrepareExecution()
				.executeSync();
	}
}
