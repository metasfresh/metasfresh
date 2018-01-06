package de.metas.testsupport;

import java.math.BigDecimal;
import java.util.Objects;

import javax.annotation.Nullable;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.ModelColumn;
import org.assertj.core.api.AbstractAssert;

import com.google.common.base.Optional;

import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@SuppressWarnings("rawtypes")
public class ModelAssert extends AbstractAssert<ModelAssert, Object>
{
	public ModelAssert(@Nullable final Object actual)
	{
		super(actual, ModelAssert.class);
	}

	public static ModelAssert assertThat(@Nullable final Object actual)
	{
		return new ModelAssert(actual);
	}

	public ModelAssert hasSameIdAs(@NonNull final Object otherModel)
	{
		isNotNull();
		final int otherId = InterfaceWrapperHelper.getId(otherModel);
		final int actualId = InterfaceWrapperHelper.getId(actual);
		if (otherId != actualId)
		{
			failWithMessage("Expected\nmodel <%s>\nto have the same ID as \ngiven model <%s>", actual, otherModel);
		}

		return this;
	}

	public ModelAssert hasValueGreaterThanZero(@NonNull final ModelColumn column)
	{
		final Object value = retrieveValueNotNull(column);

		if (value instanceof BigDecimal)
		{
			final BigDecimal bdValue = (BigDecimal)value;
			if (bdValue.signum() <= 0)
			{
				failWithMessage("Expected column <%s> of \nmodel <%s>\nto be > 0, but is <%s>", column.getColumnName(), actual, bdValue);
			}
		}
		else if (value instanceof Integer)
		{
			final Integer intValue = (Integer)value;

			if (intValue <= 0)
			{
				failWithMessage("Expected column <%s> of \nmodel <%s>\nto be > 0, but is <%s>", column.getColumnName(), actual, intValue);
			}
		}
		return this;
	}

	public ModelAssert hasNonNullValue(@NonNull final ModelColumn column, @NonNull final Object value)
	{
		final Object actualValue = retrieveValueNotNull(column);
		if (!Objects.equals(value, actualValue))
		{
			failWithMessage("Expected column <%s> of \nmodel <%s>\nto be equal to <%s>, but is <%s>", column.getColumnName(), actual, value, actualValue);
		}
		return this;
	}

	@NonNull
	private Object retrieveValueNotNull(@NonNull final ModelColumn column)
	{
		isNotNull();
		final Optional<Object> value = InterfaceWrapperHelper.getValue(actual, column.getColumnName());
		if (!value.isPresent())
		{
			failWithMessage("Expected column <%s> of \nmodel <%s>\nto have a value", column.getColumnName(), actual);
		}
		if (value.get() == null)
		{
			failWithMessage("Expected column <%s> of \nmodel <%s>\nto have a non-null value", column.getColumnName(), actual);
		}

		return value.get();
	}
}
