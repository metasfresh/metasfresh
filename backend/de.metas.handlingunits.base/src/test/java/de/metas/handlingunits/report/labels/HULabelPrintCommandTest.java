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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Printing the label of an LU whose crates (TUs) have no label config of their own:
 * only the label asked for (the LU's) must be configured; a crate without a label config is skipped, it does not abort the LU label.
 */
class HULabelPrintCommandTest
{
	private static final BPartnerId CUSTOMER = BPartnerId.ofRepoId(2810);
	/** an LU label process without an M_HU_Process row, so the labels are collected the standard way (LU + each included TU) */
	private static final AdProcessId LU_LABEL_PROCESS = AdProcessId.ofRepoId(585458);

	private HULabelService huLabelService;
	private HULabelConfigRepository labelConfigRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		huLabelService = mock(HULabelService.class);
		labelConfigRepository = new HULabelConfigRepository();
	}

	private void givenLULabelConfigOnly()
	{
		labelConfigRepository.create(HULabelConfigCreateRequest.builder()
				.orgId(OrgId.MAIN)
				.seqNo(SeqNo.ofInt(10))
				.bpartnerId(CUSTOMER)
				.sourceDocType(HULabelSourceDocType.Picking)
				.huUnitType(HuUnitType.LU)
				.printFormatProcessId(LU_LABEL_PROCESS)
				.autoPrint(true)
				.build());
		// no TU label config, and the legacy sysconfig de.metas.ui.web.picking.PickingLabel.AD_Process_ID is not set
	}

	private Optional<HULabelPrintAction> preparePrint(@NonNull final HUToReport hu)
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
						.failOnMissingLabelConfig(true)
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

	@Test
	void luLabelConfigured_cratesNotConfigured_onlyTheLULabelIsPrinted()
	{
		givenLULabelConfigOnly();
		final TestHU lu = luWithTwoCrates();

		final Optional<HULabelPrintAction> printAction = preparePrint(lu);

		assertThat(printAction).as("the LU label is printed").isPresent();
		printAction.get().print();

		final ArgumentCaptor<HULabelDirectPrintRequest> printed = ArgumentCaptor.forClass(HULabelDirectPrintRequest.class);
		verify(huLabelService).printNow(printed.capture());
		assertThat(printed.getAllValues()).hasSize(1);
		assertThat(printed.getValue().getPrintFormatProcessId()).isEqualTo(LU_LABEL_PROCESS);
		assertThat(printed.getValue().getHus()).extracting(HUToReport::getHUId).containsExactly(lu.getHUId());
	}

	@Test
	void luLabelNotConfigured_failsWithTheMissingConfigMessage()
	{
		// no label config at all: the label asked for (the LU's) cannot be printed
		assertThatThrownBy(() -> preparePrint(luWithTwoCrates()))
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
