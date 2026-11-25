package de.metas.handlingunits.report.labels;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuUnitType;
import de.metas.handlingunits.model.I_M_HU_Label_Config;
import de.metas.i18n.ExplainedOptional;
import de.metas.organization.OrgId;
import de.metas.process.AdProcessId;
import de.metas.report.PrintCopies;
import de.metas.util.lang.SeqNo;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the label-config selection invariant that the me03 #30763 fix relies on:
 * given a query whose bpartner is the consignee, {@link HULabelConfigRepository#getFirstMatching}
 * must select that consignee's per-BPartner config.
 *
 * <p>The matching path is <b>strictly {@code SeqNo}-ascending, first-match-wins</b>
 * ({@code HULabelConfigMap} sorts by {@code SeqNo}; {@code HULabelConfigRoute.isMatching} treats a
 * {@code null}-bpartner route as a catch-all that matches <i>any</i> query bpartner). So a
 * per-BPartner config wins over the null-bpartner catch-all only when it has the <b>lower SeqNo</b> —
 * which is exactly the config ordering the fix relies on (AC5: the catch-all must sit at a high SeqNo
 * as a true fallback, per-BPartner configs at lower SeqNos). The matching path itself is NOT touched
 * by the fix; the fix persists the consignee on the picking LU at close so this selection fires at
 * pick time. This test locks that unchanged matching behaviour.
 */
class HULabelConfigRepositoryTest
{
	private static final BPartnerId CONSIGNEE = BPartnerId.ofRepoId(2810);
	private static final BPartnerId OTHER_CONSIGNEE = BPartnerId.ofRepoId(2402);
	private static final AdProcessId CONSIGNEE_PROCESS = AdProcessId.ofRepoId(585458);
	private static final AdProcessId CATCHALL_PROCESS = AdProcessId.ofRepoId(540000);

	private HULabelConfigRepository repository;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		repository = new HULabelConfigRepository();
	}

	private void createConfig(
			final int seqNo,
			final BPartnerId bpartnerId,
			final AdProcessId processId,
			final boolean autoPrint,
			final int autoPrintCopies)
	{
		final I_M_HU_Label_Config record = InterfaceWrapperHelper.newInstance(I_M_HU_Label_Config.class);
		record.setSeqNo(seqNo);
		record.setHU_SourceDocType(HULabelSourceDocType.Picking.getCode());
		record.setC_BPartner_ID(bpartnerId != null ? bpartnerId.getRepoId() : 0);
		record.setIsApplyToLUs(true);
		record.setIsApplyToTUs(true);
		record.setIsApplyToCUs(true);
		record.setLabelReport_Process_ID(processId.getRepoId());
		record.setIsAutoPrint(autoPrint);
		record.setAutoPrintCopies(autoPrintCopies);
		InterfaceWrapperHelper.save(record);
	}

	private HULabelConfigQuery pickingLuQueryFor(final BPartnerId bpartnerId)
	{
		return HULabelConfigQuery.builder()
				.sourceDocType(HULabelSourceDocType.Picking)
				.huUnitType(HuUnitType.LU)
				.bpartnerId(bpartnerId)
				.build();
	}

	@Test
	void create_persistsTheConfigForOneHUUnitType_andItIsMatched()
	{
		final HULabelConfigRoute created = repository.create(HULabelConfigCreateRequest.builder()
				.orgId(OrgId.MAIN)
				.seqNo(SeqNo.ofInt(10))
				.bpartnerId(CONSIGNEE)
				.sourceDocType(HULabelSourceDocType.Picking)
				.huUnitType(HuUnitType.LU)
				.printFormatProcessId(CONSIGNEE_PROCESS)
				.autoPrint(true)
				.build());

		assertThat(created.getAcceptHUUnitTypes()).containsExactly(HuUnitType.LU);
		assertThat(created.getBpartnerId()).isEqualTo(CONSIGNEE);
		assertThat(created.getSourceDocType()).isEqualTo(HULabelSourceDocType.Picking);
		assertThat(created.getConfig().getPrintFormatProcessId()).isEqualTo(CONSIGNEE_PROCESS);
		assertThat(created.getConfig().isAutoPrint()).isTrue();
		assertThat(created.getConfig().getAutoPrintCopies()).isEqualTo(PrintCopies.ONE);

		final ExplainedOptional<HULabelConfig> matched = repository.getFirstMatching(pickingLuQueryFor(CONSIGNEE));
		assertThat(matched.isPresent()).isTrue();
		assertThat(matched.get().getPrintFormatProcessId()).isEqualTo(CONSIGNEE_PROCESS);
		assertThat(repository.getFirstMatching(pickingLuQueryFor(OTHER_CONSIGNEE)).isPresent()).isFalse();
	}

	@Test
	void create_withoutBPartnerAndSourceDocType_matchesAny()
	{
		repository.create(HULabelConfigCreateRequest.builder()
				.orgId(OrgId.MAIN)
				.seqNo(SeqNo.ofInt(10))
				.huUnitType(HuUnitType.VHU)
				.printFormatProcessId(CATCHALL_PROCESS)
				.build());

		final HULabelConfigQuery query = HULabelConfigQuery.builder()
				.sourceDocType(HULabelSourceDocType.Inventory)
				.huUnitType(HuUnitType.VHU)
				.bpartnerId(OTHER_CONSIGNEE)
				.build();
		assertThat(repository.getFirstMatching(query).isPresent()).isTrue();
		assertThat(repository.getFirstMatching(pickingLuQueryFor(OTHER_CONSIGNEE)).isPresent()).isFalse();
	}

	@Nested
	class GetFirstMatching
	{
		@Test
		void selectsPerBPartnerConfig_whenQueryBPartnerIsConsignee()
		{
			// AC5 config ordering: per-BPartner config at a LOWER SeqNo, catch-all as a high-SeqNo true fallback.
			createConfig(100, CONSIGNEE, CONSIGNEE_PROCESS, true, 2);
			createConfig(9999, null, CATCHALL_PROCESS, true, 1);

			final ExplainedOptional<HULabelConfig> result = repository.getFirstMatching(pickingLuQueryFor(CONSIGNEE));

			assertThat(result.isPresent()).as("a matching config must be found for the consignee").isTrue();
			assertThat(result.get().getPrintFormatProcessId())
					.as("the consignee's per-BPartner config must be selected, not the catch-all")
					.isEqualTo(CONSIGNEE_PROCESS);
			assertThat(result.get().getAutoPrintCopies().toInt())
					.as("the consignee config's copy count must be used")
					.isEqualTo(2);
		}

		@Test
		void fallsThroughToCatchAll_whenNoPerBPartnerConfig()
		{
			// A consignee (OTHER_CONSIGNEE) with NO specific config must fall through to the catch-all fallback.
			createConfig(100, CONSIGNEE, CONSIGNEE_PROCESS, true, 2);
			createConfig(9999, null, CATCHALL_PROCESS, true, 1);

			final ExplainedOptional<HULabelConfig> result = repository.getFirstMatching(pickingLuQueryFor(OTHER_CONSIGNEE));

			assertThat(result.isPresent()).as("the catch-all must match a consignee without a specific config").isTrue();
			assertThat(result.get().getPrintFormatProcessId())
					.as("a consignee without a per-BPartner config falls through to the catch-all fallback")
					.isEqualTo(CATCHALL_PROCESS);
		}
	}
}
