package de.metas.handlingunits.shipmentschedule.api.impl;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.shipmentschedule.api.M_ShipmentSchedule_QuantityTypeToUse;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.project.ProjectId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

/**
 * Tests for {@link HUShipmentScheduleBL#extractSingleProjectIdOrNull(List)}.
 * <p>
 * The method extracts a single common projectId from shipment schedule candidates.
 * Nulls are filtered out and don't prevent project inheritance.
 * <p>
 * Test cases (following same pattern as extractCommonProjectId):
 * <ul>
 *     <li>(G1, null) => G1 - Nulls don't prevent inheritance</li>
 *     <li>(G1, G2) => null - Different projects return null</li>
 *     <li>(null, null) => null - All nulls return null</li>
 * </ul>
 */
class HUShipmentScheduleBL_extractSingleProjectIdOrNull_Test
{
	private static final int PROJECT_ID_1 = 100;
	private static final int PROJECT_ID_2 = 200;

	private HUShipmentScheduleBL huShipmentScheduleBL;
	private IHUContext huContext;
	private HUTestHelper huTestHelper;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		huShipmentScheduleBL = new HUShipmentScheduleBL();
		huContext = mock(IHUContext.class);
		huTestHelper = new HUTestHelper();

		InterfaceWrapperHelper.newInstance(I_M_Product.class);
	}

	/**
	 * Case 1: (G1, null) => G1
	 * <p>
	 * When some candidates have a project and others have null,
	 * the common project should be extracted.
	 * Nulls should not prevent project inheritance.
	 */
	@Test
	void extractSingleProjectIdOrNull_oneProjectWithNulls_returnsProject()
	{
		// given
		final ShipmentScheduleWithHU candidate1 = createCandidate(PROJECT_ID_1);
		final ShipmentScheduleWithHU candidate2 = createCandidate(null);
		final ShipmentScheduleWithHU candidate3 = createCandidate(PROJECT_ID_1);
		final ShipmentScheduleWithHU candidate4 = createCandidate(null);

		final List<ShipmentScheduleWithHU> candidates = ImmutableList.of(
				candidate1,
				candidate2,
				candidate3,
				candidate4
		);

		// when
		final ProjectId result = huShipmentScheduleBL.extractSingleProjectIdOrNull(candidates);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getRepoId()).isEqualTo(PROJECT_ID_1);
	}

	/**
	 * Case 1 variant: Single project with null
	 * <p>
	 * When only one candidate has a project and another has null,
	 * the project should be extracted.
	 */
	@Test
	void extractSingleProjectIdOrNull_singleProjectWithNull_returnsProject()
	{
		// given
		final ShipmentScheduleWithHU candidateWithProject = createCandidate(PROJECT_ID_1);
		final ShipmentScheduleWithHU candidateWithNull = createCandidate(null);

		final List<ShipmentScheduleWithHU> candidates = ImmutableList.of(
				candidateWithProject,
				candidateWithNull
		);

		// when
		final ProjectId result = huShipmentScheduleBL.extractSingleProjectIdOrNull(candidates);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getRepoId()).isEqualTo(PROJECT_ID_1);
	}

	/**
	 * Case 1 variant: All same project (no nulls)
	 * <p>
	 * When all candidates have the same project, it should be extracted.
	 */
	@Test
	void extractSingleProjectIdOrNull_allSameProject_returnsProject()
	{
		// given
		final ShipmentScheduleWithHU candidate1 = createCandidate(PROJECT_ID_1);
		final ShipmentScheduleWithHU candidate2 = createCandidate(PROJECT_ID_1);
		final ShipmentScheduleWithHU candidate3 = createCandidate(PROJECT_ID_1);

		final List<ShipmentScheduleWithHU> candidates = ImmutableList.of(
				candidate1,
				candidate2,
				candidate3
		);

		// when
		final ProjectId result = huShipmentScheduleBL.extractSingleProjectIdOrNull(candidates);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getRepoId()).isEqualTo(PROJECT_ID_1);
	}

	/**
	 * Case 1 variant: Single candidate with project
	 * <p>
	 * When there's only one candidate with a project, it should be extracted.
	 */
	@Test
	void extractSingleProjectIdOrNull_singleCandidateWithProject_returnsProject()
	{
		// given
		final ShipmentScheduleWithHU candidate = createCandidate(PROJECT_ID_1);
		final List<ShipmentScheduleWithHU> candidates = ImmutableList.of(candidate);

		// when
		final ProjectId result = huShipmentScheduleBL.extractSingleProjectIdOrNull(candidates);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getRepoId()).isEqualTo(PROJECT_ID_1);
	}

	/**
	 * Case 2: (G1, G2) => null
	 * <p>
	 * When candidates reference different projects,
	 * no common project can be extracted.
	 */
	@Test
	void extractSingleProjectIdOrNull_multipleProjects_returnsNull()
	{
		// given
		final ShipmentScheduleWithHU candidateWithProject1 = createCandidate(PROJECT_ID_1);
		final ShipmentScheduleWithHU candidateWithProject2 = createCandidate(PROJECT_ID_2);

		final List<ShipmentScheduleWithHU> candidates = ImmutableList.of(
				candidateWithProject1,
				candidateWithProject2
		);

		// when
		final ProjectId result = huShipmentScheduleBL.extractSingleProjectIdOrNull(candidates);

		// then
		assertThat(result).isNull();
	}

	/**
	 * Case 2 variant: Multiple projects with nulls
	 * <p>
	 * When candidates reference different projects (even with nulls present),
	 * no common project can be extracted.
	 */
	@Test
	void extractSingleProjectIdOrNull_multipleProjectsWithNulls_returnsNull()
	{
		// given
		final ShipmentScheduleWithHU candidateWithProject1 = createCandidate(PROJECT_ID_1);
		final ShipmentScheduleWithHU candidateWithNull = createCandidate(null);
		final ShipmentScheduleWithHU candidateWithProject2 = createCandidate(PROJECT_ID_2);

		final List<ShipmentScheduleWithHU> candidates = ImmutableList.of(
				candidateWithProject1,
				candidateWithNull,
				candidateWithProject2
		);

		// when
		final ProjectId result = huShipmentScheduleBL.extractSingleProjectIdOrNull(candidates);

		// then
		assertThat(result).isNull();
	}

	/**
	 * Case 2 variant: Three different projects
	 * <p>
	 * When candidates reference multiple different projects,
	 * no common project can be extracted.
	 */
	@Test
	void extractSingleProjectIdOrNull_threeDifferentProjects_returnsNull()
	{
		// given
		final ShipmentScheduleWithHU candidate1 = createCandidate(PROJECT_ID_1);
		final ShipmentScheduleWithHU candidate2 = createCandidate(PROJECT_ID_2);
		final ShipmentScheduleWithHU candidate3 = createCandidate(300);

		final List<ShipmentScheduleWithHU> candidates = ImmutableList.of(
				candidate1,
				candidate2,
				candidate3
		);

		// when
		final ProjectId result = huShipmentScheduleBL.extractSingleProjectIdOrNull(candidates);

		// then
		assertThat(result).isNull();
	}

	/**
	 * Case 3: (null, null) => null
	 * <p>
	 * When all candidates have null projects,
	 * no common project can be extracted.
	 */
	@Test
	void extractSingleProjectIdOrNull_allNulls_returnsNull()
	{
		// given
		final ShipmentScheduleWithHU candidate1 = createCandidate(null);
		final ShipmentScheduleWithHU candidate2 = createCandidate(null);
		final ShipmentScheduleWithHU candidate3 = createCandidate(null);

		final List<ShipmentScheduleWithHU> candidates = ImmutableList.of(
				candidate1,
				candidate2,
				candidate3
		);

		// when
		final ProjectId result = huShipmentScheduleBL.extractSingleProjectIdOrNull(candidates);

		// then
		assertThat(result).isNull();
	}

	/**
	 * Case 3 variant: Single candidate with null
	 * <p>
	 * When there's only one candidate with null project,
	 * no common project can be extracted.
	 */
	@Test
	void extractSingleProjectIdOrNull_singleCandidateWithNull_returnsNull()
	{
		// given
		final ShipmentScheduleWithHU candidate = createCandidate(null);
		final List<ShipmentScheduleWithHU> candidates = ImmutableList.of(candidate);

		// when
		final ProjectId result = huShipmentScheduleBL.extractSingleProjectIdOrNull(candidates);

		// then
		assertThat(result).isNull();
	}

	/**
	 * Edge case: Empty list
	 * <p>
	 * When no candidates are provided, no common project can be extracted.
	 */
	@Test
	void extractSingleProjectIdOrNull_emptyList_returnsNull()
	{
		// given
		final List<ShipmentScheduleWithHU> candidates = ImmutableList.of();

		// when
		final ProjectId result = huShipmentScheduleBL.extractSingleProjectIdOrNull(candidates);

		// then
		assertThat(result).isNull();
	}

	/**
	 * Edge case: Multiple nulls followed by single project
	 * <p>
	 * Order shouldn't matter - nulls should be filtered out.
	 */
	@Test
	void extractSingleProjectIdOrNull_nullsBeforeProject_returnsProject()
	{
		// given
		final ShipmentScheduleWithHU candidate1 = createCandidate(null);
		final ShipmentScheduleWithHU candidate2 = createCandidate(null);
		final ShipmentScheduleWithHU candidate3 = createCandidate(PROJECT_ID_1);

		final List<ShipmentScheduleWithHU> candidates = ImmutableList.of(
				candidate1,
				candidate2,
				candidate3
		);

		// when
		final ProjectId result = huShipmentScheduleBL.extractSingleProjectIdOrNull(candidates);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getRepoId()).isEqualTo(PROJECT_ID_1);
	}

	/**
	 * Edge case: Interleaved nulls and project
	 * <p>
	 * Position of nulls shouldn't affect the result.
	 */
	@Test
	void extractSingleProjectIdOrNull_interleavedNullsAndProject_returnsProject()
	{
		// given
		final ShipmentScheduleWithHU candidate1 = createCandidate(null);
		final ShipmentScheduleWithHU candidate2 = createCandidate(PROJECT_ID_1);
		final ShipmentScheduleWithHU candidate3 = createCandidate(null);
		final ShipmentScheduleWithHU candidate4 = createCandidate(PROJECT_ID_1);
		final ShipmentScheduleWithHU candidate5 = createCandidate(null);

		final List<ShipmentScheduleWithHU> candidates = ImmutableList.of(
				candidate1,
				candidate2,
				candidate3,
				candidate4,
				candidate5
		);

		// when
		final ProjectId result = huShipmentScheduleBL.extractSingleProjectIdOrNull(candidates);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getRepoId()).isEqualTo(PROJECT_ID_1);
	}

	/**
	 * Helper method to create a ShipmentScheduleWithHU candidate with the specified project ID.
	 *
	 * @param projectId the project ID to set, or null for no project
	 * @return the created ShipmentScheduleWithHU
	 */
	private ShipmentScheduleWithHU createCandidate(final Integer projectId)
	{
		final I_M_ShipmentSchedule shipmentSchedule = newInstance(I_M_ShipmentSchedule.class);
		if (projectId != null)
		{
			shipmentSchedule.setC_Project_ID(projectId);
		}

		// Create a zero quantity for the candidate

		final StockQtyAndUOMQty zeroQty = StockQtyAndUOMQtys.create(BigDecimal.TEN, huTestHelper.pTomatoProductId, BigDecimal.TEN, huTestHelper.uomEachId);

		return ShipmentScheduleWithHU.ofShipmentScheduleWithoutHu(
				huContext,
				shipmentSchedule,
				zeroQty,
				M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER
		);
	}
}
