package de.metas.handlingunits.report.labels;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuUnitType;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.process.api.IMHUProcessDAO;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.report.HUToReport;
import de.metas.organization.OrgId;
import de.metas.process.AdProcessId;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verify;

/**
 * Printing the label of an LU whose crates (TUs) may have no label config of their own.
 * With {@code failOnMissingLabelConfig(true)} (default for e.g. the WebUI label processes) a missing label config fails, also for a crate.
 * With {@code failOnMissingLabelConfig(false)} (as HU consolidation and picking do) a HU without a label config is skipped, nothing fails;
 * a crate that has its own label config still gets its label.
 */
class HULabelPrintCommandTest
{
	private static final BPartnerId CUSTOMER = BPartnerId.ofRepoId(2810);
	/** an LU label process without an M_HU_Process row (here: none exists), so the labels are collected the standard way (LU + each included TU) */
	private static final AdProcessId LU_LABEL_PROCESS = AdProcessId.ofRepoId(584998);
	private static final AdProcessId TU_LABEL_PROCESS = AdProcessId.ofRepoId(584999);

	private HULabelService huLabelService;
	private HULabelConfigRepository labelConfigRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		huLabelService = mock(HULabelService.class);
		labelConfigRepository = new HULabelConfigRepository();
	}

	private void givenLabelConfig(@NonNull final HuUnitType huUnitType, @NonNull final AdProcessId printFormatProcessId)
	{
		labelConfigRepository.create(HULabelConfigCreateRequest.builder()
				.orgId(OrgId.MAIN)
				.seqNo(SeqNo.ofInt(10))
				.bpartnerId(CUSTOMER)
				.sourceDocType(HULabelSourceDocType.Picking)
				.huUnitType(huUnitType)
				.printFormatProcessId(printFormatProcessId)
				.autoPrint(true)
				.build());
	}

	private void givenLULabelConfigOnly()
	{
		givenLabelConfig(HuUnitType.LU, LU_LABEL_PROCESS);
		// no TU label config, and the legacy sysconfig de.metas.ui.web.picking.PickingLabel.AD_Process_ID is not set
	}

	private Optional<HULabelPrintAction> preparePrint(@NonNull final HUToReport hu, final boolean failOnMissingLabelConfig)
	{
		return HULabelPrintCommand.builder()
				.handlingUnitsBL(Services.get(IHandlingUnitsBL.class))
				.huLabelConfigService(new HULabelConfigService(labelConfigRepository))
				.huProcessDAO(Services.get(IMHUProcessDAO.class))
				.huQRCodesService(mock(HUQRCodesService.class))
				.huLabelService(huLabelService)
				.request(HULabelPrintRequest.builder()
						.sourceDocType(HULabelSourceDocType.Picking)
						.hu(hu)
						.onlyIfAutoPrint(false)
						.failOnMissingLabelConfig(failOnMissingLabelConfig)
						.build())
				.build()
				.prepare();
	}

	private static TestHU luWithTwoCrates()
	{
		return TestHU.builder()
				.huId(HuId.ofRepoId(1000))
				.unitType(HuUnitType.LU)
				.topLevel(true)
				.includedHU(TestHU.builder().huId(HuId.ofRepoId(1001)).unitType(HuUnitType.TU).build())
				.includedHU(TestHU.builder().huId(HuId.ofRepoId(1002)).unitType(HuUnitType.TU).build())
				.build();
	}

	/** The default, e.g. for the WebUI label processes (WEBUI_M_HU_PrintReceiptLabel, WEBUI_Picking_TU_Label): a crate without a label config fails. */
	@Test
	void luLabelConfigured_cratesNotConfigured_failingOnMissingConfig_failsWithTheMissingConfigMessage()
	{
		givenLULabelConfigOnly();

		assertThatThrownBy(() -> preparePrint(luWithTwoCrates(), true))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("de.metas.ui.web.picking.PickingLabel.AD_Process_ID");
	}

	/** HU consolidation / picking setup: the crates have no label config of their own, they are skipped, only the LU label is printed. */
	@Test
	void luLabelConfigured_cratesNotConfigured_notFailingOnMissingConfig_onlyTheLULabelIsPrinted()
	{
		givenLULabelConfigOnly();
		final TestHU lu = luWithTwoCrates();

		preparePrint(lu, false)
				.orElseThrow(() -> new AssertionError("the LU label is printed"))
				.print();

		final ArgumentCaptor<HULabelDirectPrintRequest> printed = ArgumentCaptor.forClass(HULabelDirectPrintRequest.class);
		verify(huLabelService).printNow(printed.capture());
		assertThat(printed.getAllValues()).hasSize(1);
		assertThat(printed.getValue().getPrintFormatProcessId()).isEqualTo(LU_LABEL_PROCESS);
		assertThat(printed.getValue().getHus()).extracting(HUToReport::getHUId).containsExactly(lu.getHUId());
	}

	/** A crate that has its own label config still gets its label, also when not failing on a missing config. */
	@Test
	void luLabelConfigured_cratesConfigured_notFailingOnMissingConfig_luAndCrateLabelsArePrinted()
	{
		givenLULabelConfigOnly();
		givenLabelConfig(HuUnitType.TU, TU_LABEL_PROCESS);
		final TestHU lu = luWithTwoCrates();

		preparePrint(lu, false)
				.orElseThrow(() -> new AssertionError("labels are printed"))
				.print();

		final ArgumentCaptor<HULabelDirectPrintRequest> printed = ArgumentCaptor.forClass(HULabelDirectPrintRequest.class);
		verify(huLabelService, atLeastOnce()).printNow(printed.capture());
		assertThat(printed.getAllValues())
				.filteredOn(r -> r.getPrintFormatProcessId().equals(LU_LABEL_PROCESS))
				.flatExtracting(HULabelDirectPrintRequest::getHus)
				.extracting(HUToReport::getHUId).containsExactly(lu.getHUId());
		assertThat(printed.getAllValues())
				.filteredOn(r -> r.getPrintFormatProcessId().equals(TU_LABEL_PROCESS))
				.flatExtracting(HULabelDirectPrintRequest::getHus)
				.extracting(HUToReport::getHUId).containsExactlyInAnyOrder(HuId.ofRepoId(1001), HuId.ofRepoId(1002));
	}

	/** No label config at all (neither LU nor crates): nothing to print, nothing fails. */
	@Test
	void noLabelConfigAtAll_notFailingOnMissingConfig_nothingIsPrinted()
	{
		assertThat(preparePrint(luWithTwoCrates(), false)).isEmpty();
		verifyNoInteractions(huLabelService);
	}

	/** No label config at all, but failing on a missing config: fails with the missing-config message. */
	@Test
	void noLabelConfigAtAll_failingOnMissingConfig_failsWithTheMissingConfigMessage()
	{
		assertThatThrownBy(() -> preparePrint(luWithTwoCrates(), true))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("de.metas.ui.web.picking.PickingLabel.AD_Process_ID");
	}

	@Value
	@Builder
	private static class TestHU implements HUToReport
	{
		@NonNull HuId huId;
		@NonNull HuUnitType unitType;
		boolean topLevel;
		@Singular("includedHU") List<HUToReport> includedHUs;

		@Override
		public HuId getHUId() {return huId;}

		@Override
		public BPartnerId getBPartnerId() {return CUSTOMER;}

		@Override
		public HuUnitType getHUUnitType() {return unitType;}

		@Override
		public List<HUToReport> getIncludedHUs() {return ImmutableList.copyOf(includedHUs);}
	}
}
