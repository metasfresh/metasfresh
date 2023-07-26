package org.compiere.report.core;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.List;
import java.util.Map;

import de.metas.util.Check;

/* package */class RModelAggregatedValues
{
	private final int groupsCount;
	/** place for overall total */
	private final int totalIndex;

	private final int functionsCount;
	/** "function index" to "column index" */
	final int[] functionColumnIndexes;
	/** "function index" to "function name" */
	final String[] functionNames;
	/** "function index" -> "group level" -> "aggregated value" */
	final IRModelAggregatedValue[][] functionValues;

	public RModelAggregatedValues(final IRModelMetadata metadata)
	{
		super();

		Check.assumeNotNull(metadata, "metadata not null");
		// this.metadata = metadata;

		this.groupsCount = metadata.getGroupsCount();
		Check.assume(groupsCount >= 0, "groupCount >= 0");

		final Map<Integer, String> functions = metadata.getFunctions();
		Check.assumeNotNull(functions, "functions not null");

		this.totalIndex = groupsCount;  // place for overall total

		this.functionsCount = functions.size();
		this.functionColumnIndexes = new int[functionsCount]; // function index -> column index
		this.functionNames = new String[functionsCount]; // function index -> function name
		this.functionValues = new IRModelAggregatedValue[functionsCount][groupsCount + 1]; // function index -> group level -> value

		//
		// Init function indexes and arrays
		{
			int funcIdx = 0;
			for (final Map.Entry<Integer, String> columnIndex2functionName : functions.entrySet())
			{
				functionColumnIndexes[funcIdx] = columnIndex2functionName.getKey(); // column index
				functionNames[funcIdx] = columnIndex2functionName.getValue(); // function name
				// Init function values array
				for (int groupIdx = 0; groupIdx < functionValues[funcIdx].length; groupIdx++)
				{
					functionValues[funcIdx][groupIdx] = createAggregatedValue(functionNames[funcIdx]);
				}
				//
				funcIdx++;
			}
		}
	}

	private IRModelAggregatedValue createAggregatedValue(final String functionName)
	{
		if (RModel.FUNCTION_SUM.equals(functionName))
		{
			return new SumRModelAggregatedValue();
		}
		else if (RModel.FUNCTION_COUNT.equals(functionName))
		{
			return new CountRModelAggregatedValue();
		}
		else if (RModel.FUNCTION_ACCOUNT_BALANCE.equals(functionName))
		{
			return new AcctBalanceRModelAggregatedValue();
		}
		else if (RModel.FUNCTION_ProductQtySum.equals(functionName))
		{
			return new ProductQtyRModelAggregatedValue();
		}
		else
		{
			return NullRModelAggregatedValue.instance;
		}
	}

	/**
	 * Add source row to be aggregated by our current functions.
	 * 
	 * @param calculationCtx
	 * @param row
	 */
	public void addSourceRow(final RModelCalculationContext calculationCtx, final List<Object> row)
	{
		for (int funcIndex = 0; funcIndex < functionColumnIndexes.length; funcIndex++)
		{
			final int columnIndex = functionColumnIndexes[funcIndex];
			calculationCtx.setColumnIndex(columnIndex);
			
			final Object columnValue = row.get(columnIndex);

			//
			// Iterate all groups/levels and aggregate the source row
			for (int level = 0; level < functionValues[funcIndex].length; level++)
			{
				final IRModelAggregatedValue functionValue = functionValues[funcIndex][level];
				functionValue.add(calculationCtx, row, columnValue);
			}   // for all group levels
		}   // for all functions
	}

	public Object getColumnAggregatedValue(final RModelCalculationContext calculationCtx, final List<Object> groupRow)
	{
		final int columnIndex = calculationCtx.getColumnIndex();
		final int level = calculationCtx.getLevel();

		for (int funcIndex = 0; funcIndex < functionColumnIndexes.length; funcIndex++)
		{
			if (columnIndex == functionColumnIndexes[funcIndex])
			{
				final IRModelAggregatedValue functionValue = functionValues[funcIndex][level];
				final Object value = functionValue.getAggregatedValue(calculationCtx, groupRow);
				functionValue.reset();
				return value;
			}
		}

		// Case: we deal with a column which is not aggregated (no function on it)
		return null;
	}

	public Object getColumnTotal(final RModelCalculationContext calculationCtx, final List<Object> totalsRow)
	{
		calculationCtx.setLevel(totalIndex);
		return getColumnAggregatedValue(calculationCtx, totalsRow);
	}
}
