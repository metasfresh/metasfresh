/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.workflow;

import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.StringUtils;
import de.metas.workflow.execution.WFActivity;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_AD_WF_NextCondition;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@ToString
public class WFNodeTransitionCondition
{
	private static final Logger log = LogManager.getLogger(WFNodeTransitionCondition.class);

	private final int adColumnId;
	private final boolean andJoin;
	@NonNull
	private final String operation;
	@Nullable
	private final String conditionValue1;
	@Nullable
	private final String conditionValue2;

	@Builder
	private WFNodeTransitionCondition(
			final int adColumnId,
			final boolean andJoin,
			@NonNull final String operation,
			final String conditionValue1,
			final String conditionValue2)
	{
		Check.assumeGreaterThanZero(adColumnId, "adColumnId");

		this.adColumnId = adColumnId;
		this.andJoin = andJoin;
		this.operation = operation;
		this.conditionValue1 = conditionValue1;
		this.conditionValue2 = conditionValue2;
	}

	public boolean isOr()
	{
		return !andJoin;
	}

	public boolean evaluate(final WFActivity activity)
	{
		Object valueObj = activity.getDocumentColumnValueByColumnId(adColumnId);
		if (valueObj == null)
			valueObj = "";

		String value1 = getDecodedValue(this.conditionValue1, activity);    // F3P: added value decoding
		if (value1 == null)
			value1 = "";

		String value2 = getDecodedValue(this.conditionValue2, activity);    // F3P: added value decoding
		if (value2 == null)
			value2 = "";

		if (X_AD_WF_NextCondition.OPERATION_Sql.equals(operation))
		{
			final String resultInfo = "PO:{" + valueObj + "} " + operation + " Condition:{" + value1 + "}";
			throw new AdempiereException("SQL Operator not implemented yet: " + resultInfo);
		}

		final boolean result;
		if (valueObj instanceof Number)
		{
			result = evaluateAsNumbers((Number)valueObj, value1, value2);
		}
		else if (valueObj instanceof Boolean)
		{
			result = evaluateAsBooleans((Boolean)valueObj, value1);
		}
		else
		{
			result = evaluateAsStrings(valueObj, value1, value2);
		}

		return result;
	}    //	evaluate

	/**
	 * F3P: Decode value string, for each substring enclosed in @:
	 * COL= remaining value is interpreted as a column of the associated record
	 *
	 * @param sValue value to be decoded
	 */
	private String getDecodedValue(
			final String sValue,
			final WFActivity activity)
	{
		String sRet = sValue;

		if (sValue != null && sValue.startsWith("@"))
		{
			if (sValue.startsWith("@COL="))
			{
				final Object o = activity.getDocumentColumnValueByColumnName(sValue.substring(5));
				if (o != null)
				{
					sRet = o.toString();
				}
			}
		}

		return sRet;
	}

	/**
	 * Compare Number
	 *
	 * @param valueObj comparator
	 * @param value1   first value
	 * @param value2   second value
	 * @return true if operation
	 */
	private boolean evaluateAsNumbers(final Number valueObj, final String value1, final String value2)
	{
		final BigDecimal valueObjB;
		try
		{
			valueObjB = NumberUtils.asBigDecimal(valueObj);
		}
		catch (final Exception e)
		{
			log.debug("compareNumber - valueObj={}", valueObj, e);
			return evaluateAsStrings(valueObj, value1, value2);
		}

		final BigDecimal value1B;
		try
		{
			value1B = NumberUtils.asBigDecimal(value1);
		}
		catch (final Exception e)
		{
			log.debug("compareNumber - value1={}", value1, e);
			return evaluateAsStrings(valueObj, value1, value2);
		}

		if (X_AD_WF_NextCondition.OPERATION_Eq.equals(operation))
		{
			return valueObjB.compareTo(value1B) == 0;
		}
		else if (X_AD_WF_NextCondition.OPERATION_Gt.equals(operation))
		{
			return valueObjB.compareTo(value1B) > 0;
		}
		else if (X_AD_WF_NextCondition.OPERATION_GtEq.equals(operation))
		{
			return valueObjB.compareTo(value1B) >= 0;
		}
		else if (X_AD_WF_NextCondition.OPERATION_Le.equals(operation))
		{
			return valueObjB.compareTo(value1B) < 0;
		}
		else if (X_AD_WF_NextCondition.OPERATION_LeEq.equals(operation))
		{
			return valueObjB.compareTo(value1B) <= 0;
		}
		else if (X_AD_WF_NextCondition.OPERATION_Like.equals(operation))
		{
			return valueObjB.compareTo(value1B) == 0;
		}
		else if (X_AD_WF_NextCondition.OPERATION_NotEq.equals(operation))
		{
			return valueObjB.compareTo(value1B) != 0;
		}
		else if (X_AD_WF_NextCondition.OPERATION_Sql.equals(operation))
		{
			throw new IllegalArgumentException("SQL not Implemented");
		}
		else if (X_AD_WF_NextCondition.OPERATION_X.equals(operation))
		{
			if (valueObjB.compareTo(value1B) < 0)
				return false;
			//	To
			try
			{
				final BigDecimal value2B = NumberUtils.asBigDecimal(value2);
				return valueObjB.compareTo(value2B) <= 0;
			}
			catch (final Exception e)
			{
				log.debug("compareNumber - value2={}", value2, e);
				return false;
			}
		}
		else
		{
			throw new AdempiereException("Unknown Operation=" + operation);
		}
	}    //	compareNumber

	/**
	 * Compare String
	 *
	 * @param valueObj comparator
	 * @param value1S  first value
	 * @param value2S  second value
	 * @return true if operation
	 */
	private boolean evaluateAsStrings(final Object valueObj, final String value1S, final String value2S)
	{
		final String valueObjS = String.valueOf(valueObj);
		//
		if (X_AD_WF_NextCondition.OPERATION_Eq.equals(operation))
			return valueObjS.compareTo(value1S) == 0;
		else if (X_AD_WF_NextCondition.OPERATION_Gt.equals(operation))
			return valueObjS.compareTo(value1S) > 0;
		else if (X_AD_WF_NextCondition.OPERATION_GtEq.equals(operation))
			return valueObjS.compareTo(value1S) >= 0;
		else if (X_AD_WF_NextCondition.OPERATION_Le.equals(operation))
			return valueObjS.compareTo(value1S) < 0;
		else if (X_AD_WF_NextCondition.OPERATION_LeEq.equals(operation))
			return valueObjS.compareTo(value1S) <= 0;
		else if (X_AD_WF_NextCondition.OPERATION_Like.equals(operation))
			return valueObjS.compareTo(value1S) == 0;
		else if (X_AD_WF_NextCondition.OPERATION_NotEq.equals(operation))
			return valueObjS.compareTo(value1S) != 0;
			//
		else if (X_AD_WF_NextCondition.OPERATION_Sql.equals(operation))
			throw new IllegalArgumentException("SQL not Implemented");
			//
		else if (X_AD_WF_NextCondition.OPERATION_X.equals(operation))
		{
			if (valueObjS.compareTo(value1S) < 0)
				return false;
			//	To
			return valueObjS.compareTo(value2S) <= 0;
		}
		//
		throw new IllegalArgumentException("Unknown Operation=" + operation);
	}    //	compareString

	/**
	 * Compare Boolean
	 *
	 * @param valueObj comparator
	 * @param value1S  first value
	 * @return true if operation
	 */
	private boolean evaluateAsBooleans(final Boolean valueObj, final String value1S)
	{
		final Boolean value1B = StringUtils.toBoolean(value1S);
		//
		if (X_AD_WF_NextCondition.OPERATION_Eq.equals(operation))
		{
			return valueObj.equals(value1B);
		}
		else if (X_AD_WF_NextCondition.OPERATION_NotEq.equals(operation))
		{
			return !valueObj.equals(value1B);
		}
		else
		{
			throw new AdempiereException("Not Supported =" + operation);
		}
	}
}
