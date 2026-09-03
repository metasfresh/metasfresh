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

package de.metas.cucumber.stepdefs.deliveryplanning;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.shipment.M_InOut_StepDefData;
import de.metas.i18n.Language;
import de.metas.process.AdProcessId;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessCalledFrom;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessExecutor;
import de.metas.process.ProcessInfo;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_Delivery_Planning;
import org.compiere.model.I_M_InOut;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Drives the two <b>production</b> delivery-planning generate processes -
 * {@code M_Delivery_Planning_GenerateReceipt} and {@code M_Delivery_Planning_GenerateShipment} - through the
 * real {@link ProcessExecutor}, exactly as the WebUI's action menu does.
 * <p>
 * Why this exists rather than hand-building an {@code M_InOut}: both processes generate the document AND
 * complete it inside one call, and the order in which they do that is behaviour under test. A scenario that
 * instead creates a draft {@code M_InOut}, sets {@code M_Delivery_Planning_ID} on it and only then completes it
 * is asserting against an ordering production never produces, and would keep passing over a
 * {@code TIMING_AFTER_COMPLETE} interceptor that is inert in production.
 */
@RequiredArgsConstructor
public class M_Delivery_Planning_Generate_StepDef
{
	private static final String PROCESS_VALUE_GenerateReceipt = "M_Delivery_Planning_GenerateReceipt";
	private static final String PROCESS_VALUE_GenerateShipment = "M_Delivery_Planning_GenerateShipment";

	@NonNull private final M_Delivery_Planning_StepDefData deliveryPlanningTable;
	@NonNull private final M_InOut_StepDefData inOutTable;

	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	private final IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Runs the production generate-receipt process on the given delivery planning.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>ReceiptDate</b> — (required, date) the receipt's movement date<br>
	 *   <b>Qty</b> — (required, number) quantity to receive<br>
	 *   <b>OPT.IsGenerateB2BShipment</b> — (optional, boolean, default false) also generate the coupled B2B shipment<br>
	 *   <b>OPT.M_InOut_ID</b> — (optional, identifier-ref) alias to store the generated receipt under<br>
	 * @cucumber.depends StepDefData: M_Delivery_Planning_StepDefData, M_InOut_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When the delivery planning identified by deliveryPlanning_1 generates a receipt:
	 *   | ReceiptDate | Qty | OPT.M_InOut_ID |
	 *   | 2023-02-05  | 5   | receipt_1      |
	 * </pre>
	 */
	@When("^the delivery planning identified by (.*) generates a receipt:$")
	public void generateReceipt(@NonNull final String deliveryPlanningIdentifier, @NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRows.of(dataTable).singleRow();

		final int deliveryPlanningId = deliveryPlanningTable.get(deliveryPlanningIdentifier).getM_Delivery_Planning_ID();
		final LocalDate receiptDate = row.getAsLocalDate("ReceiptDate");
		final BigDecimal qty = row.getAsBigDecimal("Qty");
		final boolean isGenerateB2BShipment = row.getAsOptionalBoolean("IsGenerateB2BShipment").isTrue();

		final AdProcessId processId = adProcessDAO.retrieveProcessIdByValue(PROCESS_VALUE_GenerateReceipt);

		executeProcess(
				processId,
				deliveryPlanningId,
				ProcessInfo.builder()
						.setCtx(Env.getCtx())
						.setProcessCalledFrom(ProcessCalledFrom.Unknown)
						.setAD_Process_ID(processId.getRepoId())
						.setAD_PInstance(adPInstanceDAO.createAD_PInstance(processId))
						.setReportLanguage(Language.getBaseLanguage())
						.setRecord(I_M_Delivery_Planning.Table_Name, deliveryPlanningId)
						.addParameter("ReceiptDate", TimeUtil.asTimestamp(receiptDate))
						.addParameter("Qty", qty)
						.addParameter("IsGenerateB2BShipment", isGenerateB2BShipment));

		storeGeneratedInOut(row, deliveryPlanningId, /* isSOTrx */ false);
	}

	/**
	 * Runs the production generate-shipment process on the given delivery planning.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>DeliveryDate</b> — (required, date) the shipment's movement date<br>
	 *   <b>Qty</b> — (required, number) quantity to ship<br>
	 *   <b>OPT.M_InOut_ID</b> — (optional, identifier-ref) alias to store the generated shipment under<br>
	 * @cucumber.depends StepDefData: M_Delivery_Planning_StepDefData, M_InOut_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When the delivery planning identified by deliveryPlanning_1 generates a shipment:
	 *   | DeliveryDate | Qty | OPT.M_InOut_ID |
	 *   | 2023-02-05   | 7   | shipment_1     |
	 * </pre>
	 */
	@When("^the delivery planning identified by (.*) generates a shipment:$")
	public void generateShipment(@NonNull final String deliveryPlanningIdentifier, @NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRows.of(dataTable).singleRow();

		final int deliveryPlanningId = deliveryPlanningTable.get(deliveryPlanningIdentifier).getM_Delivery_Planning_ID();
		final LocalDate deliveryDate = row.getAsLocalDate("DeliveryDate");
		final BigDecimal qty = row.getAsBigDecimal("Qty");

		final AdProcessId processId = adProcessDAO.retrieveProcessIdByValue(PROCESS_VALUE_GenerateShipment);

		executeProcess(
				processId,
				deliveryPlanningId,
				ProcessInfo.builder()
						.setCtx(Env.getCtx())
						.setProcessCalledFrom(ProcessCalledFrom.Unknown)
						.setAD_Process_ID(processId.getRepoId())
						.setAD_PInstance(adPInstanceDAO.createAD_PInstance(processId))
						.setReportLanguage(Language.getBaseLanguage())
						.setRecord(I_M_Delivery_Planning.Table_Name, deliveryPlanningId)
						.addParameter("DeliveryDate", deliveryDate)
						.addParameter("Qty", qty));

		storeGeneratedInOut(row, deliveryPlanningId, /* isSOTrx */ true);
	}

	private void executeProcess(
			@NonNull final AdProcessId processId,
			final int deliveryPlanningId,
			@NonNull final ProcessInfo.ProcessInfoBuilder processInfoBuilder)
	{
		final ProcessInfo processInfo = processInfoBuilder.build();
		adPInstanceDAO.saveProcessInfoOnly(processInfo);

		final ProcessExecutionResult result = ProcessExecutor.builder(processInfo)
				.executeSync()
				.getResult();

		assertThat(result).isNotNull();
		assertThat(result.getThrowable())
				.as("AD_Process_ID=%s on M_Delivery_Planning_ID=%s shall not throw", processId, deliveryPlanningId)
				.isNull();
		assertThat(result.isError())
				.as("AD_Process_ID=%s on M_Delivery_Planning_ID=%s failed: %s", processId, deliveryPlanningId, result.getSummary())
				.isFalse();
	}

	/**
	 * Stores the document the process just generated under the row's optional {@code M_InOut_ID} alias.
	 * <p>
	 * Looked up by {@code M_Delivery_Planning_ID} (newest first) rather than through the planning's own
	 * {@code M_InOut_ID}, because that back-link is written by the interceptor under test - a lookup through it
	 * would silently find nothing exactly when the interceptor is broken.
	 */
	private void storeGeneratedInOut(
			@NonNull final DataTableRow row,
			final int deliveryPlanningId,
			final boolean isSOTrx)
	{
		row.getAsOptionalIdentifier(I_M_InOut.COLUMNNAME_M_InOut_ID).ifPresent(identifier -> {
			final I_M_InOut generated = queryBL.createQueryBuilder(I_M_InOut.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_M_InOut.COLUMNNAME_M_Delivery_Planning_ID, deliveryPlanningId)
					.addEqualsFilter(I_M_InOut.COLUMNNAME_IsSOTrx, isSOTrx)
					// newest first: at this point - right after generation, before any reversal - the newest
					// M_InOut carrying this planning id is the one the process just created
					.orderByDescending(I_M_InOut.COLUMNNAME_M_InOut_ID)
					.create()
					.first();

			assertThat(generated)
					.as("No M_InOut with M_Delivery_Planning_ID=%s and IsSOTrx=%s was generated", deliveryPlanningId, isSOTrx)
					.isNotNull();

			inOutTable.putOrReplace(identifier, generated);
		});
	}
}
