package de.metas.ui.web.handlingunits.trace;

import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.OptionalInt;
import java.util.function.BiFunction;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.document.DocTypeId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU_Trace;
import de.metas.handlingunits.trace.HUTraceEventQuery;
import de.metas.handlingunits.trace.HUTraceEventQuery.EventTimeOperator;
import de.metas.handlingunits.trace.HUTraceEventQuery.RecursionMode;
import de.metas.handlingunits.trace.HUTraceType;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.util.Check;
import de.metas.util.StringUtils;
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

final class HuTraceQueryCreator
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
				final Instant value = parameter.getValueAsInstant();
				return query
						.withEventTimeOperator(EventTimeOperator.EQUAL)
						.withEventTime(value);
			case BETWEEN:
				final Instant valueFrom = parameter.getValueAsInstant();
				final Instant valueTo = parameter.getValueToAsInstant();
				return query
						.withEventTimeOperator(EventTimeOperator.BETWEEN)
						.withEventTime(valueFrom).withEventTimeTo(valueTo);
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
		errorIfQueryValueNotNull("ProductId", query.getProductId(), query);

		return query.withProductId(ProductId.ofRepoIdOrNull(extractInt(parameter)));
	}

	private static HUTraceEventQuery updateShipmentScheduleIdFromParameter(
			@NonNull final HUTraceEventQuery query,
			@NonNull final DocumentFilterParam parameter)
	{
		errorIfQueryValueNotNull("ShipmentScheduleId", query.getShipmentScheduleId(), query);

		return query.withShipmentScheduleId(ShipmentScheduleId.ofRepoIdOrNull(extractInt(parameter)));
	}

	private static HUTraceEventQuery updatePpCostCollectorIdFromParameter(
			@NonNull final HUTraceEventQuery query,
			@NonNull final DocumentFilterParam parameter)
	{
		errorIfQueryValueGreaterThanZero("PpCostCollectorId", query.getPpCostCollectorId(), query);

		return query.withPpCostCollectorId(extractInt(parameter));
	}

	private static HUTraceEventQuery updatePpOrderIdFromParameter(
			@NonNull final HUTraceEventQuery query,
			@NonNull final DocumentFilterParam parameter)
	{
		errorIfQueryValueGreaterThanZero("PpOrderId", query.getPpOrderId(), query);

		return query.withPpOrderId(extractInt(parameter));
	}

	private static HUTraceEventQuery updateVhuIdFromParameter(
			@NonNull final HUTraceEventQuery query,
			@NonNull final DocumentFilterParam parameter)
	{
		errorIfQueryValueNotEmpty("VhuId", query.getVhuIds(), query);

		return query.withVhuIds(extractHuIds(parameter));
	}

	private static HUTraceEventQuery updateVhuSourceIdFromParameter(
			@NonNull final HUTraceEventQuery query,
			@NonNull final DocumentFilterParam parameter)
	{
		errorIfQueryValueNotNull("VhuSourceId", query.getVhuSourceId(), query);

		return query.withVhuSourceId(extractHuId(parameter));
	}

	private static HUTraceEventQuery updateTypeFromParameter(
			@NonNull final HUTraceEventQuery query,
			@NonNull final DocumentFilterParam parameter)
	{
		errorIfQueryValueNotNull("Type", query.getType(), query);

		return query.withType(HUTraceType.valueOf(extractString(parameter)));
	}

	private static HUTraceEventQuery updateVhuStatusFromParameter(
			@NonNull final HUTraceEventQuery query, @NonNull final DocumentFilterParam parameter)
	{
		errorIfQueryValueNotEmpty("VhuStatus", query.getVhuStatus(), query);

		return query.withVhuStatus(extractString(parameter));
	}

	private static HUTraceEventQuery updateMovementIdFromParameter(
			@NonNull final HUTraceEventQuery query, @NonNull final DocumentFilterParam parameter)
	{
		errorIfQueryValueGreaterThanZero("MovementId", query.getMovementId(), query);

		return query.withMovementId(extractInt(parameter));
	}

	private static HUTraceEventQuery updateHuTrxLineIdFromParameter(
			@NonNull final HUTraceEventQuery query,
			@NonNull final DocumentFilterParam parameter)
	{
		errorIfQueryValueGreaterThanZero("TopLevelHuId", query.getHuTrxLineId(), query);

		return query.withHuTrxLineId(extractInt(parameter));
	}

	private static HUTraceEventQuery updateTopLevelHuIdFromParameter(
			@NonNull final HUTraceEventQuery query,
			@NonNull final DocumentFilterParam parameter)
	{
		errorIfQueryValueNotEmpty("TopLevelHuId", query.getTopLevelHuIds(), query);

		return query.withTopLevelHuIds(extractHuIds(parameter));
	}

	private static HUTraceEventQuery updateInOutIdFromParameter(
			@NonNull final HUTraceEventQuery query,
			@NonNull final DocumentFilterParam parameter)
	{
		errorIfQueryValueGreaterThanZero("InOutId", query.getInOutId(), query);

		return query.withInOutId(extractInt(parameter));
	}

	private static HUTraceEventQuery updateDocStatusFromParameter(
			@NonNull final HUTraceEventQuery query,
			@NonNull final DocumentFilterParam parameter)
	{
		errorIfQueryValueNotEmpty("DocStatus", query.getDocStatus(), query);

		return query.withDocStatus(extractString(parameter));
	}

	private static HUTraceEventQuery updateDocTypeIdFromParameter(
			@NonNull final HUTraceEventQuery query,
			@NonNull final DocumentFilterParam parameter)
	{
		if (query.getDocTypeId().isPresent())
		{
			errorIfQueryValueNotNull("DocTypeId", query.getDocTypeId().orElse(null), query);
		}

		return query.withDocTypeId(DocTypeId.optionalOfRepoId(extractInt(parameter)));
	}

	private static HUTraceEventQuery updateHuTraceIdFromParameter(@NonNull final HUTraceEventQuery query,
			@NonNull final DocumentFilterParam parameter)
	{
		if (query.getHuTraceEventId().isPresent())
		{
			errorIfQueryValueGreaterThanZero("HuTraceEventId", query.getHuTraceEventId().getAsInt(), query);
		}

		return query.withHuTraceEventId(OptionalInt.of(extractInt(parameter)));
	}

	private static HUTraceEventQuery updateOrgIdFromParameter(
			@NonNull final HUTraceEventQuery query,
			@NonNull final DocumentFilterParam parameter)
	{
		errorIfQueryValueNotNull("OrgId", query.getOrgId(), query);

		return query.withOrgId(OrgId.ofRepoIdOrNull(extractInt(parameter)));
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

	private static void errorIfQueryValueNotEmpty(
			@NonNull final String field,
			@Nullable final Collection<?> value,
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

	private static HuId extractHuId(@NonNull final DocumentFilterParam parameter)
	{
		return HuId.ofRepoIdOrNull(extractInt(parameter));
	}

	private static ImmutableSet<HuId> extractHuIds(@NonNull final DocumentFilterParam parameter)
	{
		final HuId huId = extractHuId(parameter);
		return huId != null ? ImmutableSet.of(huId) : ImmutableSet.of();
	}

	private static int extractInt(@NonNull final DocumentFilterParam parameter)
	{
		final Object value = Check.assumeNotNull(parameter.getValue(), "Given paramter may not have a null value; parameter={}", parameter);

		if (value instanceof LookupValue)
		{
			final LookupValue lookupValue = (LookupValue)value;
			return lookupValue.getIdAsInt();
		}
		else if (value instanceof Integer)
		{
			return (Integer)value;
		}
		else
		{
			throw new AdempiereException("Unable to extract an integer ID from parameter=" + parameter);
		}
	}

	private static String extractString(@NonNull final DocumentFilterParam parameter)
	{
		final Object value = Check.assumeNotNull(parameter.getValue(), "Given paramter may not have a null value; parameter={}", parameter);

		if (value instanceof LookupValue)
		{
			final LookupValue lookupValue = (LookupValue)value;
			return lookupValue.getIdAsString();
		}
		else if (value instanceof String)
		{
			return (String)value;
		}

		Check.fail("Unable to extract a String from parameter={}", parameter);
		return null; // not reached
	}
}
