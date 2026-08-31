/*
 * #%L
 * de.metas.deliveryplanning.base
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

package de.metas.deliveryplanning.process;

import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.SelectionSize;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * Pins the selection guards the delivery planning actions share.
 */
class DeliveryPlanningProcessHelperTest
{
	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
	}

	private static IProcessPreconditionsContext contextWith(final SelectionSize selectionSize)
	{
		final IProcessPreconditionsContext context = mock(IProcessPreconditionsContext.class, CALLS_REAL_METHODS);
		doReturn(selectionSize).when(context).getSelectionSize();
		return context;
	}

	@Nested
	class CheckAnySelection
	{
		@Test
		@DisplayName("rejects when nothing is selected")
		void rejectsEmptySelection()
		{
			final ProcessPreconditionsResolution resolution =
					DeliveryPlanningProcessHelper.checkAnySelection(contextWith(SelectionSize.ofSize(0)));

			assertThat(resolution.isAccepted()).isFalse();
			assertThat(resolution.getRejectReason().translate("en_US"))
					.contains(ProcessPreconditionsResolution.MSG_NO_ROWS_SELECTED.toAD_Message());
		}

		@Test
		@DisplayName("accepts one row, many rows and select-all")
		void acceptsAnyNonEmptySelection()
		{
			assertThat(DeliveryPlanningProcessHelper.checkAnySelection(contextWith(SelectionSize.ofSize(1))).isAccepted()).isTrue();
			assertThat(DeliveryPlanningProcessHelper.checkAnySelection(contextWith(SelectionSize.ofSize(7))).isAccepted()).isTrue();
			assertThat(DeliveryPlanningProcessHelper.checkAnySelection(contextWith(SelectionSize.ofAll())).isAccepted()).isTrue();
		}
	}

	@Nested
	class CheckAtMostSelected
	{
		@Test
		@DisplayName("accepts a selection up to and including the cap")
		void acceptsUpToTheCap()
		{
			assertThat(DeliveryPlanningProcessHelper.checkAtMostSelected(contextWith(SelectionSize.ofSize(1)), 3).isAccepted()).isTrue();
			assertThat(DeliveryPlanningProcessHelper.checkAtMostSelected(contextWith(SelectionSize.ofSize(3)), 3).isAccepted()).isTrue();
		}

		@Test
		@DisplayName("rejects a selection above the cap, naming the cap")
		void rejectsAboveTheCap()
		{
			final ProcessPreconditionsResolution resolution =
					DeliveryPlanningProcessHelper.checkAtMostSelected(contextWith(SelectionSize.ofSize(4)), 3);

			assertThat(resolution.isAccepted()).isFalse();
			assertThat(resolution.getRejectReason().translate("en_US")).contains("3");
		}
	}

	@Nested
	class CheckSingleSelection
	{
		@Test
		@DisplayName("accepts exactly one row")
		void acceptsSingleRow()
		{
			assertThat(DeliveryPlanningProcessHelper.checkSingleSelection(contextWith(SelectionSize.ofSize(1))).isAccepted()).isTrue();
		}

		@Test
		@DisplayName("rejects two rows and select-all")
		void rejectsMoreThanOne()
		{
			final ProcessPreconditionsResolution twoRows =
					DeliveryPlanningProcessHelper.checkSingleSelection(contextWith(SelectionSize.ofSize(2)));

			assertThat(twoRows.isAccepted()).isFalse();
			assertThat(twoRows.getRejectReason().translate("en_US"))
					.contains(ProcessPreconditionsResolution.MSG_ONLY_ONE_SELECTED_ROW_ALLOWED.toAD_Message());

			assertThat(DeliveryPlanningProcessHelper.checkSingleSelection(contextWith(SelectionSize.ofAll())).isAccepted()).isFalse();
		}

	}
}
