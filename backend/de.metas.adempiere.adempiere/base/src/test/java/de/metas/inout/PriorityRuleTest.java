/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.inout;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class PriorityRuleTest
{
	/**
	 * The constants are DECLARED {@code High, Medium, Low, Urgent, Minor}, so an ordinal-based comparator would put
	 * {@code Urgent} fourth instead of first.
	 */
	@Test
	void highToLow_ranksMostUrgentFirst_notInDeclarationOrder()
	{
		assertThat(Arrays.stream(PriorityRule.values()).sorted(PriorityRule.HIGH_TO_LOW))
				.containsExactly(
						PriorityRule.Urgent,
						PriorityRule.High,
						PriorityRule.Medium,
						PriorityRule.Low,
						PriorityRule.Minor);
	}
}
