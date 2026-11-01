package de.metas.frontend_testing.expectations.assertions;

import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static de.metas.frontend_testing.expectations.assertions.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 * #%L
 * de.metas.frontend-testing
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

public class AssertThatTest
{
	@Test
	public void numericallyEqual_differentScale_passes()
	{
		assertThatCode(() -> assertThat(new BigDecimal("0.00200")).as("QtyBook").isEqualByComparingTo(new BigDecimal("0.002")))
				.doesNotThrowAnyException();
	}

	@Test
	public void aDifferentValueFails()
	{
		assertThatThrownBy(() -> assertThat(new BigDecimal("0.5")).as("QtyBook").isEqualByComparingTo(new BigDecimal("0.002")))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Expected QtyBook to be <0.002> but was <0.5>");
	}

	@Test
	public void aNonBigDecimalActualIsReportedAsATypeProblem_notAValueProblem()
	{
		// "was <5>" for an Integer 5 reads as a value mismatch while the actual defect is a caller
		// handing in something that cannot be compared numerically at all.
		assertThatThrownBy(() -> assertThat(Integer.valueOf(5)).as("QtyBook").isEqualByComparingTo(new BigDecimal("5")))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("to be the BigDecimal <5> but was a java.lang.Integer <5>");
	}

	@Test
	public void aNullActualIsReportedAsATypeProblem()
	{
		assertThatThrownBy(() -> assertThat(null).as("QtyBook").isEqualByComparingTo(new BigDecimal("5")))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("to be the BigDecimal <5> but was null");
	}
}
