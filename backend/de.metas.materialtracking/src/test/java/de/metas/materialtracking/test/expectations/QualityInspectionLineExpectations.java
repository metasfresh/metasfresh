package de.metas.materialtracking.test.expectations;

/*
 * #%L
 * de.metas.materialtracking
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.materialtracking.model.I_PP_Order_Report;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionLine;
import de.metas.materialtracking.qualityBasedInvoicing.QualityInspectionLineType;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

/**
 * One instance expectations for one PP_Order (quality inspection or not). Multiple instances are bundles into one {@link QualityInspectionExpectations} instance.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class QualityInspectionLineExpectations
{
	private final List<QualityInspectionLineExpectation> expectations = new ArrayList<>();

	private final boolean isQualityInspection;

	/**
	 *
	 * @param isQualityInspection <code>false</code> if this is about a "simple/regular" PP_Order (a.k.a. "Auslagerung"), <code>true</code> if is is about a genuine quality inspection.
	 */
	public QualityInspectionLineExpectations(final boolean isQualityInspection)
	{
		this.isQualityInspection = isQualityInspection;
	}

	public QualityInspectionLineExpectation newExpectation(final QualityInspectionLineType qualityInspectionLineType)
	{
		final QualityInspectionLineExpectation expectation = new QualityInspectionLineExpectation(qualityInspectionLineType);
		expectations.add(expectation);

		return expectation;
	}

	/**
	 * @return {@link QualityInspectionLineExpectation} of given type; never return null
	 */
	public QualityInspectionLineExpectation expectation(final QualityInspectionLineType type)
	{
		final List<QualityInspectionLineExpectation> expectationsFound = new ArrayList<QualityInspectionLineExpectation>();
		for (final QualityInspectionLineExpectation expectation : expectations)
		{
			if (expectation.getQualityInspectionLineType() == type)
			{
				expectationsFound.add(expectation);
			}
		}

		if (expectationsFound.isEmpty())
		{
			fail("No expection found for type: " + type);
		}
		else if (expectationsFound.size() > 1)
		{
			fail("More then one expection found for type " + type + ": " + expectationsFound);
		}

		return expectationsFound.getFirst();
	}

	public void assertExpectedQualityInspectionLines(final List<IQualityInspectionLine> lines)
	{
		final int actualLinesCount = lines.size();
		final int expectedLinesCount = expectations.size();
		assertThat(actualLinesCount).as("Invalid expected lines count").isEqualTo(expectedLinesCount);

		for (int i = 0; i < actualLinesCount; i++)
		{
			final IQualityInspectionLine line = lines.get(i);
			final QualityInspectionLineExpectation expectation = expectations.get(i);

			expectation.assertExpected(line);
		}
	}

	public void assertExpectedPPOrderReportLines(final List<I_PP_Order_Report> lines)
	{
		if (!isQualityInspection)
		{
			assertThat(lines).as("Only qualityInspections shall have PPOrderReportLines").isEmpty();
		}

		final int actualLinesCount = lines.size();
		final int expectedLinesCount = expectations.size();
		assertThat(actualLinesCount).as("Invalid expected lines count").isEqualTo(expectedLinesCount);

		for (int i = 0; i < actualLinesCount; i++)
		{
			final I_PP_Order_Report line = lines.get(i);
			final QualityInspectionLineExpectation expectation = expectations.get(i);

			expectation.assertExpected(line);
		}
	}
}
