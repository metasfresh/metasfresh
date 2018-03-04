package de.metas.ui.web.handlingunits.trace;

import java.util.Date;
import java.util.Map;
import java.util.OptionalInt;
import java.util.function.BiFunction;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.StringUtils;

import com.google.common.collect.ImmutableMap;

import de.metas.handlingunits.model.I_M_HU_Trace;
import de.metas.handlingunits.trace.HUTraceEventQuery;
import de.metas.handlingunits.trace.HUTraceEventQuery.EventTimeOperator;
import de.metas.handlingunits.trace.HUTraceEventQuery.RecursionMode;
import de.metas.handlingunits.trace.HUTraceType;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class HuTraceQueryCreator
{
	private static final Map<String, BiFunction<HUTraceEventQuery, DocumentFilterParam, HUTraceEventQuery>> FIELD_NAME_2_UPDATE_METHOD = //
			ImmutableMap.<String, BiFunction<HUTraceEventQuery, DocumentFilterParam, HUTraceEventQuery>> builder()
					.put(I_M_HU_Trace.COLUMNNAME_AD_Org_ID, HuTraceQueryCreator::updateOrgIdFromParameter)
					.put(I_M_HU_Trace.COLUMNNAME_C_DocType_ID, HuTraceQueryCreator::updateDocTypeIdFromParameter)
					.put(I_M_HU_Trace.COLUMNNAME_DocStatus, HuTraceQueryCreator::updateDocStatusFromParameter)
					.put(I_M_HU_Trace.COLUMNNAME_HUTraceType, HuTraceQueryCreator::updateTypeFromParameter)
					.put(I_M_HU_Trace.COLUMNNAME_M_HU_ID, HuTraceQueryCreator::updateTopLevelHuIdFromParameter)
					.put(I_M_HU_Trace.COLUMNNAME_M_HU_Trace_ID, HuTraceQueryCreator::updateHuTraceIdFromParameter)
					.put(I_M_HU_Trace.COLUMNNAME_M_HU_Trx_Line_ID, HuTraceQueryCreator::updateHuTrxLineIdFromParameter)
					.put(I_M_HU_Trace.COLUMNNAME_M_InOut_ID, HuTraceQueryCreator::updateInOutIdFromParameter)
					.put(I_M_HU_Trace.COLUMNNAME_M_Movement_ID, HuTraceQueryCreator::updateMovementIdFromParameter)
					.put(I_M_HU_Trace.COLUMNNAME_M_Product_ID, HuTraceQueryCreator::updateProductIdFromParameter)
					.put(I_M_HU_Trace.COLUMNNAME_M_ShipmentSchedule_ID, HuTraceQueryCreator::updateShipmentScheduleIdFromParameter)
					.put(I_M_HU_Trace.COLUMNNAME_PP_Cost_Collector_ID, HuTraceQueryCreator::updatePpCostCollectorIdFromParameter)
					.put(I_M_HU_Trace.COLUMNNAME_PP_Order_ID, HuTraceQueryCreator::updatePpOrderIdFromParameter)
					.put(I_M_HU_Trace.COLUMNNAME_VHU_ID, HuTraceQueryCreator::updateVhuIdFromParameter)
					.put(I_M_HU_Trace.COLUMNNAME_VHU_Source_ID, HuTraceQueryCreator::updateVhuSourceIdFromParameter)
					.put(I_M_HU_Trace.COLUMNNAME_VHUStatus, HuTraceQueryCreator::updateVhuStatusFromParameter)
					.put(I_M_HU_Trace.COLUMNNAME_EventTime, HuTraceQueryCreator::updateEventTimeFromParameter)
					.build();

	public static HUTraceEventQuery createTraceQueryFromDocumentFilter(@NonNull final DocumentFilter documentFilter)
	{
		try
		{
			return createTraceQueryFromDocumentFilter0(documentFilter);
		}
		catch (final AdempiereException e)
		{
			throw e.appendParametersToMessage()
					.setParameter("documentFilter", documentFilter);
		}
	}

	private static HUTraceEventQuery createTraceQueryFromDocumentFilter0(
			@NonNull final DocumentFilter documentFilter)
	{
		HUTraceEventQuery query = HUTraceEventQuery.builder()

				// this is sortof the cornerstone of https://github.com/metasfresh/metasfresh-webui-api/issues/632
				.recursionMode(RecursionMode.BOTH)

				.build();
		for (final DocumentFilterParam filterParam : documentFilter.getParameters())
		{
			query = updateQueryFromParams(query, filterParam);
		}
		return query;
	}

	private static HUTraceEventQuery updateQueryFromParams(
			@NonNull final HUTraceEventQuery query,
			@NonNull final DocumentFilterParam parameter)
	{
		final BiFunction<HUTraceEventQuery, DocumentFilterParam, HUTraceEventQuery> queryUpdateFunction = getUpdateMethodForParameterOrThrowException(parameter);

		return queryUpdateFunction.apply(query, parameter);
	}

	private static BiFunction<HUTraceEventQuery, DocumentFilterParam, HUTraceEventQuery> getUpdateMethodForParameterOrThrowException(
			@NonNull final DocumentFilterParam parameter)
	{
		final String paramName = parameter.getFieldName();

		final BiFunction<HUTraceEventQuery, DocumentFilterParam, HUTraceEventQuery> queryUpdateFunction = //
				FIELD_NAME_2_UPDATE_METHOD.get(paramName);

		if (queryUpdateFunction == null)
		{
			final String message = StringUtils.formatMessage("The given filterparam has an unexpected fieldName={}", paramName);
			throw new AdempiereException(message)
					.setParameter("documentFilterParam", parameter);
		}
		return queryUpdateFunction;
	}

	private static HUTraceEventQuery updateEventTimeFromParameter(
			@NonNull final HUTraceEventQuery query,
			@NonNull final DocumentFilterParam parameter)
	{
		errorIfQueryValueNotNull("EventTime", query.getEventTime(), query);

		switch (parameter.getOperator())
		{
			case EQUAL:
				final Date value = (Date)parameter.getValue();
				return query
						.withEventTimeOperator(EventTimeOperator.EQUAL)
						.withEventTime(value.toInstant());
			case BETWEEN:
				final Date valueFrom = (Date)parameter.getValue();
				final Date valueTo = (Date)parameter.getValueTo();
				return query
						.withEventTimeOperator(EventTimeOperator.BETWEEN)
						.withEventTime(valueFrom.toInstant()).withEventTimeTo(valueTo.toInstant());
			default:
				throw new AdempiereException("Unexpected operator=" + parameter.getOperator() + " in parameter")
						.appendParametersToMessage()
						.setParameter("HUTraceEventQuery", query)
						.setParameter("DocumentFilterParam", parameter);
		}
	}

	private static HUTraceEventQuery updateProductIdFromParameter(
			@NonNull final HUTraceEventQuery query,
			@NonNull final DocumentFilterParam parameter)
	{
		errorfIfNotEqualsOperator(parameter);
		errorIfQueryValueGreaterThanZero("ProductId", query.getProductId(), query);

		final LookupValue value = (LookupValue)parameter.getValue();
		return query.withProductId(value.getIdAsInt());
	}

	private static HUTraceEventQuery updateShipmentScheduleIdFromParameter(
			@NonNull final HUTraceEventQuery query,
			@NonNull final DocumentFilterParam parameter)
	{
		errorIfQueryValueGreaterThanZero("ShipmentScheduleId", query.getShipmentScheduleId(), query);

		final LookupValue value = (LookupValue)parameter.getValue();
		return query.withShipmentScheduleId(value.getIdAsInt());
	}

	private static HUTraceEventQuery updatePpCostCollectorIdFromParameter(
			@NonNull final HUTraceEventQuery query,
			@NonNull final DocumentFilterParam parameter)
	{
		errorIfQueryValueGreaterThanZero("PpCostCollectorId", query.getPpCostCollectorId(), query);

		final LookupValue value = (LookupValue)parameter.getValue();
		return query.withPpCostCollectorId(value.getIdAsInt());
	}

	private static HUTraceEventQuery updatePpOrderIdFromParameter(
			@NonNull final HUTraceEventQuery query,
			@NonNull final DocumentFilterParam parameter)
	{
		errorIfQueryValueGreaterThanZero("PpOrderId", query.getPpOrderId(), query);

		final LookupValue value = (LookupValue)parameter.getValue();
		return query.withPpOrderId(value.getIdAsInt());
	}

	private static HUTraceEventQuery updateVhuIdFromParameter(
			@NonNull final HUTraceEventQuery query,
			@NonNull final DocumentFilterParam parameter)
	{
		errorIfQueryValueGreaterThanZero("VhuId", query.getVhuId(), query);

		final LookupValue value = (LookupValue)parameter.getValue();
		return query.withVhuId(value.getIdAsInt());
	}

	private static HUTraceEventQuery updateVhuSourceIdFromParameter(
			@NonNull final HUTraceEventQuery query,
			@NonNull final DocumentFilterParam parameter)
	{
		errorIfQueryValueGreaterThanZero("VhuSourceId", query.getVhuSourceId(), query);

		final LookupValue value = (LookupValue)parameter.getValue();
		return query.withVhuSourceId(value.getIdAsInt());
	}

	private static HUTraceEventQuery updateTypeFromParameter(
			@NonNull final HUTraceEventQuery query,
			@NonNull final DocumentFilterParam parameter)
	{
		errorIfQueryValueNotNull("Type", query.getType(), query);

		final LookupValue value = (LookupValue)parameter.getValue();
		return query.withType(HUTraceType.valueOf(value.getIdAsString()));
	}

	private static HUTraceEventQuery updateVhuStatusFromParameter(
			@NonNull final HUTraceEventQuery query, @NonNull final DocumentFilterParam parameter)
	{
		errorIfQueryValueNotEmpty("VhuStatus", query.getVhuStatus(), query);

		final LookupValue value = (LookupValue)parameter.getValue();
		return query.withVhuStatus(value.getIdAsString());
	}

	private static HUTraceEventQuery updateMovementIdFromParameter(
			@NonNull final HUTraceEventQuery query, @NonNull final DocumentFilterParam parameter)
	{
		errorIfQueryValueGreaterThanZero("MovementId", query.getMovementId(), query);

		final LookupValue value = (LookupValue)parameter.getValue();
		return query.withMovementId(value.getIdAsInt());
	}

	private static HUTraceEventQuery updateHuTrxLineIdFromParameter(
			@NonNull final HUTraceEventQuery query,
			@NonNull final DocumentFilterParam parameter)
	{
		errorIfQueryValueGreaterThanZero("TopLevelHuId", query.getHuTrxLineId(), query);

		final IntegerLookupValue value = (IntegerLookupValue)parameter.getValue();
		return query.withHuTrxLineId(value.getIdAsInt());
	}

	private static HUTraceEventQuery updateTopLevelHuIdFromParameter(
			@NonNull final HUTraceEventQuery query,
			@NonNull final DocumentFilterParam parameter)
	{
		errorIfQueryValueGreaterThanZero("TopLevelHuId", query.getTopLevelHuId(), query);

		final LookupValue value = (LookupValue)parameter.getValue();
		return query.withTopLevelHuId(value.getIdAsInt());
	}

	private static HUTraceEventQuery updateInOutIdFromParameter(
			@NonNull final HUTraceEventQuery query,
			@NonNull final DocumentFilterParam parameter)
	{
		errorIfQueryValueGreaterThanZero("InOutId", query.getInOutId(), query);

		final LookupValue value = (LookupValue)parameter.getValue();
		return query.withInOutId(value.getIdAsInt());
	}

	private static HUTraceEventQuery updateDocStatusFromParameter(
			@NonNull final HUTraceEventQuery query,
			@NonNull final DocumentFilterParam parameter)
	{
		errorIfQueryValueNotEmpty("DocStatus", query.getDocStatus(), query);

		final LookupValue value = (LookupValue)parameter.getValue();
		return query.withDocStatus(value.getIdAsString());
	}

	private static HUTraceEventQuery updateDocTypeIdFromParameter(
			@NonNull final HUTraceEventQuery query,
			@NonNull final DocumentFilterParam parameter)
	{
		if (query.getDocTypeId().isPresent())
		{
			errorIfQueryValueGreaterThanZero("DocTypeId", query.getDocTypeId().getAsInt(), query);
		}
		final LookupValue value = (LookupValue)parameter.getValue();
		return query.withDocTypeId(OptionalInt.of(value.getIdAsInt()));
	}

	private static HUTraceEventQuery updateHuTraceIdFromParameter(@NonNull final HUTraceEventQuery query,
			@NonNull final DocumentFilterParam parameter)
	{
		if (query.getHuTraceEventId().isPresent())
		{
			errorIfQueryValueGreaterThanZero("HuTraceEventId", query.getHuTraceEventId().getAsInt(), query);
		}
		final LookupValue value = (LookupValue)parameter.getValue();
		return query.withHuTraceEventId(OptionalInt.of(value.getIdAsInt()));
	}

	private static HUTraceEventQuery updateOrgIdFromParameter(
			@NonNull final HUTraceEventQuery query,
			@NonNull final DocumentFilterParam parameter)
	{
		errorIfQueryValueGreaterThanZero("OrgId", query.getOrgId(), query);

		final IntegerLookupValue value = (IntegerLookupValue)parameter.getValue();
		return query.withOrgId(value.getIdAsInt());
	}

	private static void errorIfQueryValueGreaterThanZero(
			@NonNull final String field,
			final int value,
			@NonNull final HUTraceEventQuery query)
	{
		if (value > 0)
		{
			final String message = StringUtils.formatMessage("The given HUTraceEventQuery already has {}={}", field, value);
			throw new AdempiereException(message).setParameter("HUTraceEventQuery", query);
		}
	}

	private static void errorIfQueryValueNotEmpty(
			@NonNull final String field,
			@Nullable final String value, // empty can mean "" or null
			@NonNull final HUTraceEventQuery query)
	{
		if (!Check.isEmpty(value))
		{
			final String message = StringUtils.formatMessage("The given HUTraceEventQuery already has {}={}", field, value);
			throw new AdempiereException(message).setParameter("HUTraceEventQuery", query);
		}
	}

	private static void errorIfQueryValueNotNull(
			@NonNull final String field,
			final Object value,
			@NonNull final HUTraceEventQuery query)
	{
		if (value != null)
		{
			final String message = StringUtils.formatMessage("The given HUTraceEventQuery already has {}={}", field, value);
			throw new AdempiereException(message).setParameter("HUTraceEventQuery", query);
		}
	}

	private static void errorfIfNotEqualsOperator(@NonNull final DocumentFilterParam parameter)
	{
		if (!Operator.EQUAL.equals(parameter.getOperator()))
		{
			final String message = StringUtils.formatMessage("The given DocumentFilterParam needs to have an EQUAL operator, but has {}", parameter.getOperator());
			throw new AdempiereException(message).setParameter("DocumentFilterParam", parameter);
		}
	}
}
