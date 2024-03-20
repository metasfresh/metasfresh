package de.metas.process;

import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.api.IRangeAwareParams;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.reflect.FieldReference;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Contains information about process class fields which were marked as parameters (i.e. annotated with {@link Param}).
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Value
public class ProcessClassParamInfo
{
	private static final Logger logger = LogManager.getLogger(ProcessClassParamInfo.class);

	boolean parameterTo;
	@NonNull String externalParameterName;
	@NonNull String internalParameterName;
	boolean mandatory;

	BarcodeScannerType barcodeScannerType;

	// NOTE: NEVER EVER store the process class as field because we want to have a weak reference to it to prevent ClassLoader memory leaks nightmare.
	// Remember that we are caching this object.
	@NonNull FieldReference fieldRef;
	@Nullable FieldReference nestedParamsFieldRef;

	@lombok.Builder
	private ProcessClassParamInfo(
			@NonNull final String externalParameterName,
			@NonNull final String internalParameterName,
			final boolean parameterTo,
			@NonNull final Field field,
			boolean mandatory,
			@Nullable final BarcodeScannerType barcodeScannerType,
			@Nullable Field nestedParamsField)
	{
		Check.assumeNotEmpty(externalParameterName, "externalParameterName not empty");
		Check.assumeNotEmpty(internalParameterName, "internalParameterName not empty");

		this.externalParameterName = externalParameterName;
		this.internalParameterName = internalParameterName;
		this.parameterTo = parameterTo;

		this.fieldRef = FieldReference.of(field);

		this.mandatory = mandatory;

		this.barcodeScannerType = barcodeScannerType;

		this.nestedParamsFieldRef = nestedParamsField != null ? FieldReference.of(nestedParamsField) : null;
	}

	public Field getField() {return fieldRef.getField();}

	public Class<?> getFieldType() {return fieldRef.getField().getType();}

	/**
	 * Loads process instance's parameter value from source to <code>processField</code>
	 *
	 * @param processInstance the process object where we will set the annotated fields to be the loaded parameters. Note that it needs to be an {@link IContextAware}, because we might need to load
	 *                        records from the given <code>source</code>
	 */
	public void loadParameterValue(
			@NonNull final JavaProcess processInstance,
			@NonNull final IRangeAwareParams source,
			final boolean failIfNotValid)
	{
		//
		// Get the parameter value from source
		final Object value = extractParameterValue(processInstance, source);

		//
		// Handle the case when the value is null
		if (value == null)
		{
			if (mandatory)
			{
				if (failIfNotValid)
				{
					throw new FillMandatoryException(externalParameterName);
				}
				else
				{
					return;
				}
			}

			final Class<?> fieldType = getFieldType();
			if (fieldType.isPrimitive())
			{
				// don't set a primitive type to null
				return;
			}
		}

		//
		// Set the value to given process instance
		setFieldValue(processInstance, value);
	}

	private Object extractParameterValue(
			final JavaProcess processInstance,
			final IRangeAwareParams source)
	{
		if (!source.hasParameter(externalParameterName))
		{
			logger.debug("Given source does not contain this instance's parameterName={}; -> return null", externalParameterName);
			return null;
		}

		//
		// Get the parameter value from source
		final Object value;
		final Class<?> fieldType = getFieldType();
		if (fieldType.isAssignableFrom(BigDecimal.class))
		{
			value = parameterTo ? source.getParameter_ToAsBigDecimal(externalParameterName) : source.getParameterAsBigDecimal(externalParameterName);
		}
		else if (fieldType.isAssignableFrom(int.class))
		{
			value = parameterTo
					? source.getParameter_ToAsInt(externalParameterName, 0)
					: source.getParameterAsInt(externalParameterName, 0);
		}
		else if (boolean.class.equals(fieldType))
		{
			value = parameterTo ? source.getParameter_ToAsBool(externalParameterName) : source.getParameterAsBool(externalParameterName);
		}
		else if (Boolean.class.equals(fieldType))
		{
			final String valueStr = parameterTo ? source.getParameter_ToAsString(externalParameterName) : source.getParameterAsString(externalParameterName);
			value = DisplayType.toBoolean(valueStr, null);
		}
		//
		// Dates
		else if (java.util.Date.class.isAssignableFrom(fieldType))
		{
			// this catches both Date and Timestamp
			value = parameterTo ? source.getParameter_ToAsTimestamp(externalParameterName) : source.getParameterAsTimestamp(externalParameterName);
		}
		else if (LocalDate.class.equals(fieldType))
		{
			value = TimeUtil.asLocalDate(parameterTo ? source.getParameter_ToAsTimestamp(externalParameterName) : source.getParameterAsTimestamp(externalParameterName));
		}
		else if (LocalDateTime.class.equals(fieldType))
		{
			value = TimeUtil.asLocalDateTime(parameterTo ? source.getParameter_ToAsTimestamp(externalParameterName) : source.getParameterAsTimestamp(externalParameterName));
		}
		else if (LocalTime.class.equals(fieldType))
		{
			value = TimeUtil.asLocalTime(parameterTo ? source.getParameter_ToAsTimestamp(externalParameterName) : source.getParameterAsTimestamp(externalParameterName));
		}
		else if (ZonedDateTime.class.equals(fieldType))
		{
			value = TimeUtil.asZonedDateTime(parameterTo ? source.getParameter_ToAsTimestamp(externalParameterName) : source.getParameterAsTimestamp(externalParameterName));
		}
		else if (Instant.class.equals(fieldType))
		{
			value = TimeUtil.asInstant(parameterTo ? source.getParameter_ToAsTimestamp(externalParameterName) : source.getParameterAsTimestamp(externalParameterName));
		}
		else if (RepoIdAware.class.isAssignableFrom(fieldType))
		{
			final int valueInt = parameterTo
					? source.getParameter_ToAsInt(externalParameterName, -1)
					: source.getParameterAsInt(externalParameterName, -1);

			@SuppressWarnings("unchecked") final Class<? extends RepoIdAware> repoIdAwareType = (Class<? extends RepoIdAware>)fieldType;
			value = RepoIdAwares.ofRepoIdOrNull(valueInt, repoIdAwareType);
		}
		else if (ReferenceListAwareEnum.class.isAssignableFrom(fieldType))
		{
			final String valueStr = parameterTo
					? source.getParameter_ToAsString(externalParameterName)
					: source.getParameterAsString(externalParameterName);

			@SuppressWarnings("unchecked") final Class<? extends ReferenceListAwareEnum> referenceListAwareClass = (Class<? extends ReferenceListAwareEnum>)fieldType;
			value = ReferenceListAwareEnums.ofNullableCode(valueStr, referenceListAwareClass);
		}
		//
		else if (fieldType.isAssignableFrom(String.class))
		{
			value = parameterTo ? source.getParameter_ToAsString(externalParameterName) : source.getParameterAsString(externalParameterName);
		}
		else if (InterfaceWrapperHelper.isModelInterface(fieldType))
		{
			final int id = parameterTo
					? source.getParameter_ToAsInt(externalParameterName, -1)
					: source.getParameterAsInt(externalParameterName, -1);
			if (id <= 0)
			{
				value = null;
			}
			else
			{
				final Object valueOld = getFieldValue(processInstance);

				final int idOld = valueOld == null ? -1 : InterfaceWrapperHelper.getId(valueOld);
				if (id != idOld)
				{
					value = InterfaceWrapperHelper.create(processInstance.getCtx(), id, fieldType, ITrx.TRXNAME_ThreadInherited);
				}
				else
				{
					value = valueOld;
				}
			}
		}
		else
		{
			throw new IllegalStateException("Field type " + fieldType + " is not supported for " + this);
		}

		return value;
	}

	private void setFieldValue(final @NonNull JavaProcess processInstance, final Object value)
	{
		setFieldValue(getField(), getParamsHolderInstance(processInstance), value);
	}

	@Nullable
	public Object getFieldValue(final JavaProcess processInstance)
	{
		return getFieldValue(getField(), getParamsHolderInstance(processInstance));
	}

	private Object getParamsHolderInstance(@NonNull final JavaProcess processInstance)
	{
		if (nestedParamsFieldRef != null)
		{
			final Field nestedParamsField = nestedParamsFieldRef.getField();
			Object nestedParamsInstance = getFieldValue(nestedParamsField, processInstance);
			if (nestedParamsInstance == null)
			{
				nestedParamsInstance = createNewInstance(nestedParamsField.getType());
				setFieldValue(nestedParamsField, processInstance, nestedParamsInstance);
			}

			return nestedParamsInstance;
		}
		else
		{
			return processInstance;
		}
	}

	private static Object createNewInstance(final Class<?> clazz)
	{
		try
		{
			return clazz.newInstance();
		}
		catch (AdempiereException ex)
		{
			throw ex;
		}
		catch (Throwable ex)
		{
			throw new AdempiereException("Failed creating a new instance of " + clazz, ex);
		}
	}

	@Nullable
	private static Object getFieldValue(@NonNull final Field field, @NonNull final Object instance)
	{
		try
		{
			if (!field.isAccessible())
			{
				field.setAccessible(true);
			}
			return field.get(instance);
		}
		catch (Throwable e)
		{
			throw new IllegalStateException("Failed getting the value of " + field + " from " + instance, e);
		}
	}

	private static void setFieldValue(@NonNull final Field field, @NonNull final Object instance, @Nullable Object fieldValue)
	{
		try
		{
			if (!field.isAccessible())
			{
				field.setAccessible(true);
			}
			field.set(instance, fieldValue);
		}
		catch (Throwable e)
		{
			throw new IllegalStateException("Failed setting the value `" + fieldValue + "` to " + field + " on " + instance, e);
		}

	}
}
