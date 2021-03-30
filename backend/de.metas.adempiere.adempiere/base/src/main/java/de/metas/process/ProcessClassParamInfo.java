package de.metas.process;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.api.IRangeAwareParams;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.reflect.ClassReference;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

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
public final class ProcessClassParamInfo
{
	private static final Logger logger = LogManager.getLogger(ProcessClassParamInfo.class);

	static ArrayKey createFieldUniqueKey(final Field field)
	{
		// NOTE: when building the make, make sure we don't have any references to Class, Field or other java reflection classes
		return ArrayKey.of(field.getType().getName(), field.getDeclaringClass().getName(), field.getName());
	}

	static ArrayKey createParameterUniqueKey(final String parameterName, final boolean parameterTo)
	{
		return ArrayKey.of(parameterName, parameterTo);
	}

	private final boolean parameterTo;
	private final String parameterName;
	private final ArrayKey parameterKey;

	private final boolean mandatory;

	private final BarcodeScannerType barcodeScannerType;

	// NOTE: NEVER EVER store the process class as field because we want to have a weak reference to it to prevent ClassLoader memory leaks nightmare.
	// Remember that we are caching this object.
	private final ArrayKey fieldKey;
	@Getter(AccessLevel.PRIVATE)
	private final ClassReference<?> fieldTypeRef;

	@Builder
	private ProcessClassParamInfo(
			@NonNull final String parameterName,
			final boolean parameterTo,
			@NonNull final Field field,
			boolean mandatory,
			@Nullable final BarcodeScannerType barcodeScannerType)
	{
		Check.assumeNotEmpty(parameterName, "parameter name not empty");

		this.parameterName = parameterName;
		this.parameterTo = parameterTo;
		this.parameterKey = createParameterUniqueKey(parameterName, parameterTo);

		this.fieldKey = createFieldUniqueKey(field);
		this.fieldTypeRef = ClassReference.of(field.getType());

		this.mandatory = mandatory;

		this.barcodeScannerType = barcodeScannerType;
	}

	public Class<?> getFieldType()
	{
		return fieldTypeRef.getReferencedClass();
	}

	/**
	 * Loads process instance's parameter value from source to <code>processField</code>
	 *
	 * @param processInstance the process object where we will set the annotated fields to be the loaded parameters. Note that it needs to be an {@link IContextAware}, because we might need to load
	 *            records from the given <code>source</code>
	 * @param processField
	 * @param source
	 * @param failIfNotValid
	 */
	public void loadParameterValue(
			@NonNull final JavaProcess processInstance,
			@NonNull final Field processField,
			@NonNull final IRangeAwareParams source,
			final boolean failIfNotValid)
	{
		//
		// Get the parameter value from source
		final Object value = extractParameterValue(processInstance, processField, source);

		//
		// Handle the case when the value is null
		if (value == null)
		{
			if (mandatory)
			{
				if (failIfNotValid)
				{
					throw new FillMandatoryException(parameterName);
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
		try
		{
			if (!processField.isAccessible())
			{
				processField.setAccessible(true);
			}
			processField.set(processInstance, value);
		}
		catch (final Throwable e)
		{
			throw new IllegalStateException("Failed setting the value of " + processField
					+ "\n Parameter info: " + this
					+ "\n Process instance: " + processInstance, e);
		}
	}

	private Object extractParameterValue(
			final JavaProcess processInstance,
			final Field processField,
			final IRangeAwareParams source)
	{
		if (!source.hasParameter(parameterName))
		{
			logger.debug("Given source does not contain this instance's parameterName={}; -> return null", parameterName);
			return null;
		}

		//
		// Get the parameter value from source
		final Object value;
		final Class<?> fieldType = processField.getType();
		if (fieldType.isAssignableFrom(BigDecimal.class))
		{
			value = parameterTo ? source.getParameter_ToAsBigDecimal(parameterName) : source.getParameterAsBigDecimal(parameterName);
		}
		else if (fieldType.isAssignableFrom(int.class))
		{
			value = parameterTo
					? source.getParameter_ToAsInt(parameterName, 0)
					: source.getParameterAsInt(parameterName, 0);
		}
		else if (boolean.class.equals(fieldType))
		{
			value = parameterTo ? source.getParameter_ToAsBool(parameterName) : source.getParameterAsBool(parameterName);
		}
		else if (Boolean.class.equals(fieldType))
		{
			final String valueStr = parameterTo ? source.getParameter_ToAsString(parameterName) : source.getParameterAsString(parameterName);
			value = DisplayType.toBoolean(valueStr, (Boolean)null);
		}
		//
		// Dates
		else if (java.util.Date.class.isAssignableFrom(fieldType))
		{
			// this catches both Date and Timestamp
			value = parameterTo ? source.getParameter_ToAsTimestamp(parameterName) : source.getParameterAsTimestamp(parameterName);
		}
		else if (LocalDate.class.equals(fieldType))
		{
			value = TimeUtil.asLocalDate(parameterTo ? source.getParameter_ToAsTimestamp(parameterName) : source.getParameterAsTimestamp(parameterName));
		}
		else if (LocalDateTime.class.equals(fieldType))
		{
			value = TimeUtil.asLocalDateTime(parameterTo ? source.getParameter_ToAsTimestamp(parameterName) : source.getParameterAsTimestamp(parameterName));
		}
		else if (LocalTime.class.equals(fieldType))
		{
			value = TimeUtil.asLocalTime(parameterTo ? source.getParameter_ToAsTimestamp(parameterName) : source.getParameterAsTimestamp(parameterName));
		}
		else if (ZonedDateTime.class.equals(fieldType))
		{
			value = TimeUtil.asZonedDateTime(parameterTo ? source.getParameter_ToAsTimestamp(parameterName) : source.getParameterAsTimestamp(parameterName));
		}
		else if (Instant.class.equals(fieldType))
		{
			value = TimeUtil.asInstant(parameterTo ? source.getParameter_ToAsTimestamp(parameterName) : source.getParameterAsTimestamp(parameterName));
		}
		else if (RepoIdAware.class.isAssignableFrom(fieldType))
		{
			final int valueInt = parameterTo
					? source.getParameter_ToAsInt(parameterName, -1)
					: source.getParameterAsInt(parameterName, -1);

			@SuppressWarnings("unchecked")
			final Class<? extends RepoIdAware> repoIdAwareType = (Class<? extends RepoIdAware>)fieldType;
			value = RepoIdAwares.ofRepoIdOrNull(valueInt, repoIdAwareType);
		}
		else if (ReferenceListAwareEnum.class.isAssignableFrom(fieldType))
		{
			final String valueStr = parameterTo
					? source.getParameter_ToAsString(parameterName)
					: source.getParameterAsString(parameterName);

			@SuppressWarnings("unchecked")
			final Class<? extends ReferenceListAwareEnum> referenceListAwareClass = (Class<? extends ReferenceListAwareEnum>)fieldType;
			value = ReferenceListAwareEnums.ofNullableCode(valueStr, referenceListAwareClass);
		}
		//
		else if (fieldType.isAssignableFrom(String.class))
		{
			value = parameterTo ? source.getParameter_ToAsString(parameterName) : source.getParameterAsString(parameterName);
		}
		else if (InterfaceWrapperHelper.isModelInterface(fieldType))
		{
			final int id = parameterTo
					? source.getParameter_ToAsInt(parameterName, -1)
					: source.getParameterAsInt(parameterName, -1);
			if (id <= 0)
			{
				value = null;
			}
			else
			{
				Object valueOld;
				try
				{
					if (!processField.isAccessible())
					{
						processField.setAccessible(true);
					}
					valueOld = processField.get(processInstance);
				}
				catch (IllegalArgumentException | IllegalAccessException e)
				{
					// shall not happen
					throw AdempiereException.wrapIfNeeded(e);
				}

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
}
