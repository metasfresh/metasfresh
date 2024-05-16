/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.commission.licensefee.model;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPGroupId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.lang.Percent;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class LicenseFeeSettingsTest
{
	final BPGroupId bpGroupId_1 = BPGroupId.ofRepoId(1);
	final BPGroupId bpGroupId_2 = BPGroupId.ofRepoId(2);

	@Test
	public void givenLicenseFeeSettingsWithMultipleLines_whenGetLineForBPMatchGroup_thenReturnFirstMatch()
	{
		// given
		final LicenseFeeSettings settings = mockLicenseFeeSettings();

		//	when
		final LicenseFeeSettingsLine licenseFeeSettingsLine_1 = settings.getLineForBPGroupId(bpGroupId_1)
				.orElse(null);

		// then
		assertThat(licenseFeeSettingsLine_1).isNotNull();
		assertThat(licenseFeeSettingsLine_1.getBpGroupIdMatch()).isEqualTo(bpGroupId_1);
		assertThat(licenseFeeSettingsLine_1.getSeqNo()).isEqualTo(10);
		assertThat(licenseFeeSettingsLine_1.getPercentOfBasedPoints()).isEqualTo(Percent.ONE_HUNDRED);

		//	when
		final LicenseFeeSettingsLine licenseFeeSettingsLine_2 = settings.getLineForBPGroupId(bpGroupId_2)
				.orElse(null);

		// then
		assertThat(licenseFeeSettingsLine_2).isNotNull();
		assertThat(licenseFeeSettingsLine_2.getBpGroupIdMatch()).isEqualTo(bpGroupId_2);
		assertThat(licenseFeeSettingsLine_2.getSeqNo()).isEqualTo(30);
		assertThat(licenseFeeSettingsLine_2.getPercentOfBasedPoints()).isEqualTo(Percent.of(10));
	}


	private LicenseFeeSettings mockLicenseFeeSettings()
	{
		final LicenseFeeSettingsId licenseFeeSettingsId = LicenseFeeSettingsId.ofRepoId(1);

		return LicenseFeeSettings.builder()
				.licenseFeeSettingsId(licenseFeeSettingsId)
				.commissionProductId(ProductId.ofRepoId(1))
				.pointsPrecision(2)
				.orgId(OrgId.ANY)
				.lines(ImmutableList.of(LicenseFeeSettingsLine.builder()
												.licenseFeeSettingsLineId(LicenseFeeSettingsLineId.ofRepoId(licenseFeeSettingsId, 1))
												.bpGroupIdMatch(bpGroupId_1)
												.seqNo(10)
												.percentOfBasedPoints(Percent.ONE_HUNDRED)
												.active(true)
												.build(),
										LicenseFeeSettingsLine.builder()
												.licenseFeeSettingsLineId(LicenseFeeSettingsLineId.ofRepoId(licenseFeeSettingsId, 1))
												.bpGroupIdMatch(bpGroupId_1)
												.seqNo(20)
												.percentOfBasedPoints(Percent.ONE_HUNDRED)
												.active(true)
												.build(),
										LicenseFeeSettingsLine.builder()
												.licenseFeeSettingsLineId(LicenseFeeSettingsLineId.ofRepoId(licenseFeeSettingsId, 2))
												.bpGroupIdMatch(bpGroupId_2)
												.seqNo(30)
												.percentOfBasedPoints(Percent.of(10))
												.active(true)
												.build()))
				.build();
	}
}
