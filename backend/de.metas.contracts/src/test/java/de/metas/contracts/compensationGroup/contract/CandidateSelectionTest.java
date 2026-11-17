package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.contracts.compensationGroup.contract.CandidateSelection.PositionSelection;
import de.metas.order.OrderLineId;
import de.metas.order.compensationGroup.GroupCompensationBase;
import de.metas.product.ProductCategoryId;
import de.metas.project.ProjectId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.contracts
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

class CandidateSelectionTest
{
	private static final ProjectId POSITION_A = ProjectId.ofRepoId(1);
	private static final ProjectId POSITION_B = ProjectId.ofRepoId(2);
	private static final GroupCompensationBase GOODS = GroupCompensationBase.of(ProductCategoryId.ofRepoId(10), null);
	private static final GroupCompensationBase PACKAGING = GroupCompensationBase.of(null, ProductCategoryId.ofRepoId(20));

	@Test
	void getMatchedBases_unionOfTheLinesBases()
	{
		final CandidateSelection selection = new CandidateSelection(ImmutableList.of(
				line(1, POSITION_A, GOODS),
				line(2, POSITION_A),
				line(3, POSITION_B, GOODS, PACKAGING)));

		assertThat(selection.getMatchedBases()).containsExactlyInAnyOrder(GOODS, PACKAGING);
	}

	@Nested
	class SplitByPosition
	{
		@Test
		void onePerPosition_inTheOrderOfThePositionsFirstLine_linesWithoutPositionTogether()
		{
			final CandidateSelection selection = new CandidateSelection(ImmutableList.of(
					line(1, POSITION_B, GOODS),
					line(2, null, GOODS),
					line(3, POSITION_A, GOODS),
					line(4, POSITION_B, PACKAGING),
					line(5, null, PACKAGING)));

			final ImmutableList<PositionSelection> positionSelections = selection.splitByPosition();

			assertThat(positionSelections).extracting(PositionSelection::getPositionId).containsExactly(POSITION_B, null, POSITION_A);
			assertThat(positionSelections).extracting(positionSelection -> positionSelection.getCandidates().getLineIds()).containsExactly(
					ImmutableList.of(OrderLineId.ofRepoId(1), OrderLineId.ofRepoId(4)),
					ImmutableList.of(OrderLineId.ofRepoId(2), OrderLineId.ofRepoId(5)),
					ImmutableList.of(OrderLineId.ofRepoId(3)));
		}

		@Test
		void none_givesNoSelection()
		{
			assertThat(CandidateSelection.NONE.splitByPosition()).isEmpty();
		}

		@Test
		void eachPositionKeepsOnlyItsOwnLinesBases()
		{
			final CandidateSelection selection = new CandidateSelection(ImmutableList.of(
					line(1, POSITION_A, GOODS),
					line(2, POSITION_B, PACKAGING)));

			assertThat(selection.splitByPosition()).extracting(positionSelection -> positionSelection.getCandidates().getMatchedBases()).containsExactly(
					ImmutableSet.of(GOODS),
					ImmutableSet.of(PACKAGING));
		}
	}

	private static CandidateLine line(final int lineId, @Nullable final ProjectId positionId, final GroupCompensationBase... matchedBases)
	{
		return new CandidateLine(OrderLineId.ofRepoId(lineId), positionId, ImmutableSet.copyOf(matchedBases));
	}
}
