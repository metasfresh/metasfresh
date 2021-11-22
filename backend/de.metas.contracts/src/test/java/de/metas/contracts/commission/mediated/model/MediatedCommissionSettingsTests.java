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

package de.metas.contracts.commission.mediated.model;

import com.google.common.collect.ImmutableList;
import de.metas.organization.OrgId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.lang.Percent;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class MediatedCommissionSettingsTests
{
	@Test
	public void givenMediatedSettingsWithMultipleLines_whenGetLineForProductCategory_thenReturnFirstMatch()
	{
		//given
		final ProductCategoryId productCategoryId_1 = ProductCategoryId.ofRepoId(1);
		final ProductCategoryId productCategoryId_2 = ProductCategoryId.ofRepoId(2);
		final ProductCategoryId productCategoryId_3 = ProductCategoryId.ofRepoId(3);

		final MediatedCommissionSettingsId commissionSettingsId = MediatedCommissionSettingsId.ofRepoId(1);
		final OrgId orgId = OrgId.ofRepoId(1);

		final MediatedCommissionSettings mediatedCommissionSettings = MediatedCommissionSettings.builder()
				.commissionProductId(ProductId.ofRepoId(1))
				.orgId(orgId)
				.pointsPrecision(2)
				.commissionSettingsId(commissionSettingsId)
				.lines(ImmutableList.of(
						MediatedCommissionSettingsLine.builder()
								.mediatedCommissionSettingsLineId(MediatedCommissionSettingsLineId.ofRepoId(3, commissionSettingsId))
								.orgId(orgId)
								.percentOfBasedPoints(Percent.ONE_HUNDRED)
								.seqNo(4)
								.productCategoryId(productCategoryId_3)
								.build(),
						MediatedCommissionSettingsLine.builder()
								.mediatedCommissionSettingsLineId(MediatedCommissionSettingsLineId.ofRepoId(100, commissionSettingsId))
								.orgId(orgId)
								.percentOfBasedPoints(Percent.ONE_HUNDRED)
								.seqNo(3)
								.productCategoryId(null)
								.build(),
						MediatedCommissionSettingsLine.builder()
								.mediatedCommissionSettingsLineId(MediatedCommissionSettingsLineId.ofRepoId(2, commissionSettingsId))
								.orgId(orgId)
								.percentOfBasedPoints(Percent.ONE_HUNDRED)
								.seqNo(2)
								.productCategoryId(productCategoryId_2)
								.build(),
						MediatedCommissionSettingsLine.builder()
								.mediatedCommissionSettingsLineId(MediatedCommissionSettingsLineId.ofRepoId(1, commissionSettingsId))
								.orgId(orgId)
								.percentOfBasedPoints(Percent.ONE_HUNDRED)
								.seqNo(1)
								.productCategoryId(productCategoryId_1)
								.build()
				))
				.build();

		//when
		MediatedCommissionSettingsLine mediatedCommissionSettingsLine = mediatedCommissionSettings.getLineForProductCategory(productCategoryId_1).get();

		//then
		assertThat(mediatedCommissionSettingsLine.getMediatedCommissionSettingsLineId().getRepoId()).isEqualTo(1);

		//when
		mediatedCommissionSettingsLine = mediatedCommissionSettings.getLineForProductCategory(productCategoryId_2).get();

		//then
		assertThat(mediatedCommissionSettingsLine.getMediatedCommissionSettingsLineId().getRepoId()).isEqualTo(2);

		//when
		mediatedCommissionSettingsLine = mediatedCommissionSettings.getLineForProductCategory(productCategoryId_3).get();

		//then
		assertThat(mediatedCommissionSettingsLine.getMediatedCommissionSettingsLineId().getRepoId()).isEqualTo(100);
	}
}
