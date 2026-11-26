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

package de.metas.cucumber.stepdefs.billofmaterial;

import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.productCategory.M_Product_Category_StepDefData;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessInfo;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.process.PP_Product_BOM_Check;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * Runs the process {@code PP_Product_BOM_Check} (menu "Verify BOMs") and checks its outcome.
 */
@RequiredArgsConstructor
public class PP_Product_BOM_Check_StepDef
{
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	@NonNull private final PP_Product_BOM_StepDefData productBOMTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_Product_Category_StepDefData productCategoryTable;

	@Nullable private ProcessExecutionResult result;
	/** the BOM record the process was started on */
	@Nullable private I_PP_Product_BOM runBom;

	/**
	 * Runs the process on a BOM record, without a product category (verifies the BOM's product).
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: PP_Product_BOM_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When run PP_Product_BOM_Check for PP_Product_BOM bom
	 * </pre>
	 */
	@When("run PP_Product_BOM_Check for PP_Product_BOM {word}")
	public void run_PP_Product_BOM_Check(@NonNull final String bomIdentifier)
	{
		run(bomIdentifier, null);
	}

	/**
	 * Runs the process on a BOM record, with the parameter {@code M_Product_Category_ID} (verifies all BOM products of the category).
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: PP_Product_BOM_StepDefData, M_Product_Category_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When run PP_Product_BOM_Check for PP_Product_BOM bom with M_Product_Category category_1
	 * </pre>
	 */
	@When("run PP_Product_BOM_Check for PP_Product_BOM {word} with M_Product_Category {word}")
	public void run_PP_Product_BOM_Check_with_category(@NonNull final String bomIdentifier, @NonNull final String categoryIdentifier)
	{
		final I_M_Product_Category category = productCategoryTable.get(categoryIdentifier);
		run(bomIdentifier, category);
	}

	private void run(@NonNull final String bomIdentifier, @Nullable final I_M_Product_Category category)
	{
		final I_PP_Product_BOM bom = productBOMTable.get(bomIdentifier);

		runBom = bom;

		final ProcessInfo.ProcessInfoBuilder processInfo = ProcessInfo.builder()
				.setAD_ProcessByClassname(PP_Product_BOM_Check.class.getName())
				.setRecord(I_PP_Product_BOM.Table_Name, bom.getPP_Product_BOM_ID());
		if (category != null)
		{
			processInfo.addParameter(I_M_Product.COLUMNNAME_M_Product_Category_ID, category.getM_Product_Category_ID());
		}

		// no onErrorThrowException: a failed run is kept in the result, so that the next steps can assert on it
		result = processInfo.buildAndPrepareExecution().executeSync().getResult();
	}

	@NonNull
	private ProcessExecutionResult getResult()
	{
		assertThat(result).as("PP_Product_BOM_Check was not run in this scenario").isNotNull();
		return result;
	}

	/**
	 * Checks that the process ended without error and that its end message is the given AD_Message (translated).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then the PP_Product_BOM_Check result is OK with message key OK
	 * </pre>
	 */
	@Then("the PP_Product_BOM_Check result is OK with message key {word}")
	public void result_is_OK_with_message_key(@NonNull final String messageKey)
	{
		assertResultIsOK(messageKey, new Object[] {});
	}

	/**
	 * Same as above, for a message with parameters.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Param</b> — (required) one value per row, in the order of the message parameters {0}, {1}, ...; the identifier of a product stands for its Value<br>
	 * @cucumber.example
	 * <pre>
	 * Then the PP_Product_BOM_Check result is OK with message key PP_Product_BOM_Check.CategoryRunOK and parameters:
	 *   | Param |
	 *   | 1     |
	 * </pre>
	 */
	@Then("the PP_Product_BOM_Check result is OK with message key {word} and parameters:")
	public void result_is_OK_with_message_key_and_parameters(@NonNull final String messageKey, @NonNull final DataTable dataTable)
	{
		final Object[] params = DataTableRows.of(dataTable).stream()
				.map(row -> resolveParam(row.getAsString("Param")))
				.toArray();
		assertResultIsOK(messageKey, params);
	}

	/** a Param that is the identifier of a product of the scenario stands for that product's Value */
	@NonNull
	private String resolveParam(@NonNull final String param)
	{
		return productTable.getOptional(param).map(I_M_Product::getValue).orElse(param);
	}

	private void assertResultIsOK(@NonNull final String messageKey, @NonNull final Object[] params)
	{
		final ProcessExecutionResult processResult = getResult();
		if (processResult.isError())
		{
			// report the BOM product's IsVerified in the failure text (a failed run leaves it N); it is only reported here, not asserted
			final I_M_Product bomProduct = InterfaceWrapperHelper.load(runBom.getM_Product_ID(), I_M_Product.class);
			fail("PP_Product_BOM_Check ended with an error, summary=%s, throwable=%s; M_Product.IsVerified of the BOM product %s is %s",
					processResult.getSummary(), processResult.getThrowable(), bomProduct.getValue(), bomProduct.isVerified() ? "Y" : "N");
		}

		final String expectedMessage = msgBL.getMsg(Env.getCtx(), AdMessageKey.of(messageKey), params);
		assertThat(processResult.getSummary())
				.as("end message of PP_Product_BOM_Check, expected AD_Message %s", messageKey)
				.isEqualTo(expectedMessage);
	}

	/**
	 * Checks that the process ended with an error that is a user validation error and that its message contains the given texts.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>MessageContains</b> — (required) text that the end message must contain; one row per text<br>
	 * @cucumber.example
	 * <pre>
	 * Then the PP_Product_BOM_Check result is user validation error containing:
	 *   | MessageContains |
	 *   | not found       |
	 * </pre>
	 */
	@Then("the PP_Product_BOM_Check result is user validation error containing:")
	public void result_is_user_validation_error(@NonNull final DataTable dataTable)
	{
		final ProcessExecutionResult processResult = getResult();
		assertThat(processResult.isError()).as("PP_Product_BOM_Check ended with an error, summary=%s", processResult.getSummary()).isTrue();
		assertThat(AdempiereException.isUserValidationError(processResult.getThrowable()))
				.as("throwable is a user validation error: %s", processResult.getThrowable())
				.isTrue();
		DataTableRows.of(dataTable).forEach(row -> assertThat(processResult.getSummary()).contains(row.getAsString("MessageContains")));
	}

	/**
	 * Checks the process log (AD_PInstance_Log): per row one log line that names the product (by its Value) and contains the reason text.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Product_ID</b> — (required, identifier-ref) the product the log line is about<br>
	 *   <b>ReasonContains</b> — (required) text that the log line must contain<br>
	 * @cucumber.depends StepDefData: M_Product_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then the PP_Product_BOM_Check process log contains:
	 *   | M_Product_ID | ReasonContains  |
	 *   | product_1               | no default BOM  |
	 * </pre>
	 */
	@Then("the PP_Product_BOM_Check process log contains:")
	public void process_log_contains(@NonNull final DataTable dataTable)
	{
		final ProcessExecutionResult processResult = getResult();
		processResult.syncLogsToDB();
		final String logInfo = processResult.getLogInfo();
		final List<String> logLines = Arrays.asList(logInfo.split("\n"));

		DataTableRows.of(dataTable).forEach(row -> {
			final I_M_Product product = row.getAsIdentifier(I_M_Product.COLUMNNAME_M_Product_ID).lookupNotNullIn(productTable);
			final String reasonContains = row.getAsString("ReasonContains");
			assertThat(logLines)
					.as("process log of PP_Product_BOM_Check, expected a line with product Value %s and text '%s'", product.getValue(), reasonContains)
					.anySatisfy(line -> assertThat(line).contains(product.getValue()).contains(reasonContains));
		});
	}

	/**
	 * Checks how many AD_Issue rows were written by the process run (those with its {@code AD_PInstance_ID}).
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then count of new AD_Issue rows since the run is 0
	 * </pre>
	 */
	@Then("count of new AD_Issue rows since the run is {int}")
	public void count_of_new_AD_Issue_rows_since_the_run_is(final int expectedCount)
	{
		final PInstanceId pInstanceId = getResult().getPinstanceId();
		assertThat(pInstanceId).as("AD_PInstance_ID of the PP_Product_BOM_Check run").isNotNull();
		final int count = DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT COUNT(1) FROM AD_Issue WHERE AD_PInstance_ID=?", pInstanceId.getRepoId());
		assertThat(count).as("count of AD_Issue rows of AD_PInstance_ID=%s", pInstanceId.getRepoId()).isEqualTo(expectedCount);
	}
}
