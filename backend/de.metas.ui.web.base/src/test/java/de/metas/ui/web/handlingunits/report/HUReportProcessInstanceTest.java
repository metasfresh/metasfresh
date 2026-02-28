package de.metas.ui.web.handlingunits.report;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuUnitType;
import de.metas.handlingunits.report.HUToReport;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class HUReportProcessInstanceTest
{
	@Test
	void getHUsToProcess_emptyInput_returnsEmpty()
	{
		final Set<HUToReport> empty = ImmutableSet.of();
		assertThat(HUReportProcessInstance.getHUsToProcess(empty)).isEmpty();
	}

	@Test
	void getHUsToProcess_onlyLUs_returnsLUs()
	{
		final HUToReport lu1 = mockHUToReport(HuId.ofRepoId(1), HuUnitType.LU);
		final HUToReport lu2 = mockHUToReport(HuId.ofRepoId(2), HuUnitType.LU);

		final List<HUToReport> result = HUReportProcessInstance.getHUsToProcess(ImmutableSet.of(lu1, lu2));

		assertThat(result).containsExactlyInAnyOrder(lu1, lu2);
	}

	@Test
	void getHUsToProcess_onlyTUs_returnsTUs()
	{
		final HUToReport tu1 = mockHUToReport(HuId.ofRepoId(10), HuUnitType.TU);
		final HUToReport tu2 = mockHUToReport(HuId.ofRepoId(11), HuUnitType.TU);

		final List<HUToReport> result = HUReportProcessInstance.getHUsToProcess(ImmutableSet.of(tu1, tu2));

		assertThat(result).containsExactlyInAnyOrder(tu1, tu2);
	}

	@Test
	void MSG_NoSSCC_constant_isSet()
	{
		assertThat(HUReportProcessInstance.MSG_NoSSCC).isEqualTo("HU_Labels_SSCC_LU.NoSSCC");
	}

	private static HUToReport mockHUToReport(final HuId huId, final HuUnitType unitType)
	{
		return new HUToReport()
		{
			@Override
			public HuId getHUId() { return huId; }

			@Override
			public BPartnerId getBPartnerId() { return null; }

			@Override
			public HuUnitType getHUUnitType() { return unitType; }

			@Override
			public boolean isTopLevel() { return true; }

			@Override
			public List<HUToReport> getIncludedHUs() { return ImmutableList.of(); }

			@Override
			public Stream<HUToReport> streamRecursively() { return Stream.of(this); }
		};
	}
}
