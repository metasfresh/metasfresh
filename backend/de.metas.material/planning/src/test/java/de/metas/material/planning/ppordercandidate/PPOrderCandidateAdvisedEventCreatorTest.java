/*
 * #%L
 * metasfresh-material-planning
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

package de.metas.material.planning.ppordercandidate;

import com.google.common.collect.ImmutableList;
import de.metas.business.BusinessTestHelper;
import de.metas.material.planning.event.MaterialRequest;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class PPOrderCandidateAdvisedEventCreatorTest
{
	private UomId uomId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		uomId = UomId.ofRepoId(BusinessTestHelper.createUOM("uom").getC_UOM_ID());
	}

	@Test
	void createMaterialRequests_1()
	{
		final ImmutableList<MaterialRequest> materialRequests = PPOrderCandidateAdvisedEventCreator.createMaterialRequests(
				MaterialRequest.builder()
						.qtyToSupply(Quantitys.of("31", uomId))
						.build(),
				Quantitys.of("15", uomId)
		);

		assertThat(materialRequests)
				.extracting(l -> l.getQtyToSupply().toBigDecimal().toString())
				.containsExactly("15", "15", "1");
	}

	@Test
	void createMaterialRequests_2()
	{
		final ImmutableList<MaterialRequest> materialRequests = PPOrderCandidateAdvisedEventCreator.createMaterialRequests(
				MaterialRequest.builder()
						.qtyToSupply(Quantitys.of("31", uomId))
						.build(),
				null
		);

		assertThat(materialRequests)
				.extracting(l -> l.getQtyToSupply().toBigDecimal().toString())
				.containsExactly("31");
	}

	@Test
	void createMaterialRequests_3()
	{
		final ImmutableList<MaterialRequest> materialRequests = PPOrderCandidateAdvisedEventCreator.createMaterialRequests(
				MaterialRequest.builder()
						.qtyToSupply(Quantitys.of("31", uomId))
						.build(),
				Quantitys.of("31", uomId)
		);

		assertThat(materialRequests)
				.extracting(l -> l.getQtyToSupply().toBigDecimal().toString())
				.containsExactly("31");
	}

	@Test
	void createMaterialRequests_4()
	{
		final ImmutableList<MaterialRequest> materialRequests = PPOrderCandidateAdvisedEventCreator.createMaterialRequests(
				MaterialRequest.builder()
						.qtyToSupply(Quantitys.of("31", uomId))
						.build(),
				Quantitys.of("32", uomId)
		);

		assertThat(materialRequests)
				.extracting(l -> l.getQtyToSupply().toBigDecimal().toString())
				.containsExactly("31");
	}

	@Test
	void createMaterialRequests_5()
	{
		final ImmutableList<MaterialRequest> materialRequests = PPOrderCandidateAdvisedEventCreator.createMaterialRequests(
				MaterialRequest.builder()
						.qtyToSupply(Quantitys.of("31", uomId))
						.build(),
				Quantitys.zero(uomId)
		);

		assertThat(materialRequests)
				.extracting(l -> l.getQtyToSupply().toBigDecimal().toString())
				.containsExactly("31");
	}
}