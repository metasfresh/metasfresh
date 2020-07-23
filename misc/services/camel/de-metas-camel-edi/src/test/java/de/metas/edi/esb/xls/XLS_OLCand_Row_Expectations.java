package de.metas.edi.esb.xls;

/*
 * #%L
 * de.metas.edi.esb
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


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import de.metas.edi.esb.excelimport.Excel_OLCand_Row;
import de.metas.edi.esb.jaxb.metasfresh.XLSImpCOLCandType;

public class XLS_OLCand_Row_Expectations
{
	private XLS_OLCand_Row_Expectation commonRowExpectation;
	private final List<XLS_OLCand_Row_Expectation> rowExpectations = new ArrayList<XLS_OLCand_Row_Expectation>();

	public XLS_OLCand_Row_Expectations()
	{
		super();
	}

	public void assertExpected(
			final Map<Integer, Excel_OLCand_Row> lineNo2row,
			final Map<Integer, Map<String, Object>> lineNo2rowData)
	{
		//
		// Apply common row expectations, if any
		if (commonRowExpectation != null)
		{
			for (final Excel_OLCand_Row row : lineNo2row.values())
			{
				commonRowExpectation.assertExpected(row);
			}
		}

		Assert.assertFalse("We have at least one expectation defined", rowExpectations.isEmpty());
		for (XLS_OLCand_Row_Expectation expectation : rowExpectations)
		{
			final int lineNo = expectation.getLineNo();
			final Excel_OLCand_Row row = lineNo2row.get(lineNo);
			System.out.println("row: " + row);
			System.out.println("row (raw data): " + lineNo2rowData.get(lineNo));
			expectation.assertExpected(row);
		}
	}

	public void assertExpected(final Map<Integer, XLSImpCOLCandType> lineNo2xml)
	{
		//
		// Apply common row expectations, if any
		if (commonRowExpectation != null)
		{
			for (final XLSImpCOLCandType xml : lineNo2xml.values())
			{
				commonRowExpectation.assertExpected(xml);
			}
		}

		Assert.assertFalse("We have at least one expectation defined", rowExpectations.isEmpty());
		for (XLS_OLCand_Row_Expectation expectation : rowExpectations)
		{
			final int lineNo = expectation.getLineNo();
			final XLSImpCOLCandType xml = lineNo2xml.get(lineNo);
			expectation.assertExpected(xml);
		}

	}

	public XLS_OLCand_Row_Expectations setCommonExpectation(XLS_OLCand_Row_Expectation commonRowExpectation)
	{
		this.commonRowExpectation = commonRowExpectation;
		return this;
	}

	public XLS_OLCand_Row_Expectations addExpectation(XLS_OLCand_Row_Expectation rowExpectation)
	{
		this.rowExpectations.add(rowExpectation);
		return this;
	}

}
