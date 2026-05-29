package de.metas.cucumber.stepdefs.pporder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.hu.M_HU_StepDefData;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.cache.CacheMgt;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_MobileUI_MFG_Config;
import org.compiere.SpringContextHolder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for testing the on-the-fly issue schedule creation endpoint
 * ({@code POST /api/v2/manufacturing/issueSchedule/createOnTheFly}).
 * <p>
 * gh#28943
 */
@RequiredArgsConstructor
public class ManufacturingIssueScheduleOnTheFly_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final TestContext testContext;
	@NonNull private final M_HU_StepDefData huTable;
	@NonNull private final PP_Order_StepDefData ppOrderTable;

	/**
	 * Sets the global {@code MobileUI_MFG_Config.IsAllowIssuingAnyHU} flag.
	 * Creates a config record if none exists.
	 */
	@And("set MobileUI_MFG_Config IsAllowIssuingAnyHU to {string}")
	public void setIsAllowIssuingAnyHU(@NonNull final String value)
	{
		Check.assumeNotEmpty(value, "value");
		final boolean isAllow = "Y".equals(value);

		// Update ALL existing config records (across all clients)
		final java.util.List<I_MobileUI_MFG_Config> configs = queryBL.createQueryBuilder(I_MobileUI_MFG_Config.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();

		if (!configs.isEmpty())
		{
			for (final I_MobileUI_MFG_Config config : configs)
			{
				config.setIsAllowIssuingAnyHU(isAllow);
				org.adempiere.model.InterfaceWrapperHelper.save(config);
			}
		}
		else
		{
			final I_MobileUI_MFG_Config newConfig = org.adempiere.model.InterfaceWrapperHelper.newInstance(I_MobileUI_MFG_Config.class);
			newConfig.setIsAllowIssuingAnyHU(isAllow);
			org.adempiere.model.InterfaceWrapperHelper.save(newConfig);
		}

		// Reset ALL caches — the globalConfigsCache in MobileUIManufacturingConfigRepository
		// has a bug: it's keyed on MobileUI_UserProfile_MFG table (wrong) instead of MobileUI_MFG_Config.
		// So updating MobileUI_MFG_Config doesn't auto-invalidate it. Force a full cache reset.
		CacheMgt.get().reset();
	}

	/**
	 * Sets the HUStatus of an M_HU record via {@link IHUStatusBL}.
	 */
	@And("set M_HU status:")
	public void setHUStatus(@NonNull final DataTable dataTable)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);

		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier huIdentifier = row.getAsIdentifier("M_HU_ID");
			final I_M_HU hu = huTable.get(huIdentifier);
			final String huStatus = row.getAsString("HUStatus");

			final de.metas.handlingunits.IHUContext huContext = handlingUnitsBL.createMutableHUContext();
			huStatusBL.setHUStatus(huContext, hu, huStatus);
			org.adempiere.model.InterfaceWrapperHelper.save(hu);
		});
	}

	/**
	 * Creates a JSON payload for {@code POST /api/v2/manufacturing/issueSchedule/createOnTheFly}
	 * and stores it in the test context.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code WorkflowProcess.Identifier} — use {@code from_last_response} to extract wfProcessId from the last REST response</li>
	 *   <li>{@code M_HU_ID.Identifier} — identifier of the HU to scan</li>
	 * </ul>
	 */
	@And("create JsonCreateIssueScheduleOnTheFlyRequest and store it in context:")
	public void createOnTheFlyRequest(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRows.of(dataTable).singleRow();

		// Get wfProcessId from last response
		final String wfProcessId;
		final String workflowProcessIdentifier = row.getAsString("WorkflowProcess.Identifier");
		if ("from_last_response".equals(workflowProcessIdentifier))
		{
			final String lastResponseBody = testContext.getApiResponse().getContent();
			try
			{
				final ObjectMapper mapper = new ObjectMapper();
				wfProcessId = mapper.readTree(lastResponseBody).get("id").asText();
			}
			catch (final Exception e)
			{
				throw new RuntimeException("Failed to extract wfProcessId from last response: " + lastResponseBody, e);
			}
		}
		else
		{
			throw new RuntimeException("WorkflowProcess.Identifier must be 'from_last_response' for now; got: " + workflowProcessIdentifier);
		}

		// Get HU QR code
		final StepDefDataIdentifier huIdentifier = row.getAsIdentifier("M_HU_ID");
		final I_M_HU hu = huTable.get(huIdentifier);
		final HUQRCodesService huQRCodesService = SpringContextHolder.instance.getBean(HUQRCodesService.class);
		final List<HUQRCode> qrCodes = huQRCodesService.getQRCodesByHuId(de.metas.handlingunits.HuId.ofRepoId(hu.getM_HU_ID()));
		Check.assumeNotEmpty(qrCodes, "HU must have a QR code: {}", huIdentifier);
		final String huQRCode = qrCodes.get(0).toGlobalQRCodeString();

		// Build JSON
		final ObjectMapper mapper = new ObjectMapper();
		final ObjectNode payload = mapper.createObjectNode();
		payload.put("wfProcessId", wfProcessId);
		payload.put("huQRCode", huQRCode);

		testContext.setRequestPayload(payload.toString());
	}

	/**
	 * Verifies the count of {@code PP_Order_IssueSchedule} records for a PP_Order.
	 */
	@Then("verify PP_Order_IssueSchedule count for PP_Order:")
	public void verifyIssueScheduleCount(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier ppOrderIdentifier = row.getAsIdentifier("PP_Order_ID");
			final int expectedCount = row.getAsInt("ExpectedCount");

			final org.eevolution.model.I_PP_Order ppOrder = ppOrderTable.get(ppOrderIdentifier);
			final int actualCount = queryBL.createQueryBuilder(de.metas.handlingunits.model.I_PP_Order_IssueSchedule.class)
					.addEqualsFilter(de.metas.handlingunits.model.I_PP_Order_IssueSchedule.COLUMNNAME_PP_Order_ID, ppOrder.getPP_Order_ID())
					.addOnlyActiveRecordsFilter()
					.create()
					.count();

			assertThat(actualCount)
					.as("PP_Order_IssueSchedule count for PP_Order %s", ppOrderIdentifier)
					.isEqualTo(expectedCount);
		});
	}

	/**
	 * Verifies PP_Order_IssueSchedule record details.
	 *
	 * <p>Required columns: {@code PP_Order_ID.Identifier}, {@code M_Product_ID.Identifier}, {@code M_HU_ID.Identifier}
	 * <p>Optional columns: {@code SeqNo}
	 */
	@Then("verify PP_Order_IssueSchedule:")
	public void verifyIssueSchedule(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier ppOrderIdentifier = row.getAsIdentifier("PP_Order_ID");
			final org.eevolution.model.I_PP_Order ppOrder = ppOrderTable.get(ppOrderIdentifier);

			final StepDefDataIdentifier huIdentifier = row.getAsIdentifier("M_HU_ID");
			final I_M_HU hu = huTable.get(huIdentifier);

			final List<de.metas.handlingunits.model.I_PP_Order_IssueSchedule> schedules = queryBL
					.createQueryBuilder(de.metas.handlingunits.model.I_PP_Order_IssueSchedule.class)
					.addEqualsFilter(de.metas.handlingunits.model.I_PP_Order_IssueSchedule.COLUMNNAME_PP_Order_ID, ppOrder.getPP_Order_ID())
					.addEqualsFilter(de.metas.handlingunits.model.I_PP_Order_IssueSchedule.COLUMNNAME_IssueFrom_HU_ID, hu.getM_HU_ID())
					.addOnlyActiveRecordsFilter()
					.create()
					.list();

			assertThat(schedules)
					.as("PP_Order_IssueSchedule for PP_Order %s and HU %s", ppOrderIdentifier, huIdentifier)
					.hasSize(1);

			final de.metas.handlingunits.model.I_PP_Order_IssueSchedule schedule = schedules.get(0);

			row.getAsOptionalInt("SeqNo").ifPresent(expectedSeqNo ->
					assertThat(schedule.getSeqNo())
							.as("SeqNo")
							.isEqualTo(expectedSeqNo));
		});
	}
}
