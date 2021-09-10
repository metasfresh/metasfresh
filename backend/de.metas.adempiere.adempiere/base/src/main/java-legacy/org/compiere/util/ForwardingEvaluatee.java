/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package org.compiere.util;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@EqualsAndHashCode
@ToString
public abstract class ForwardingEvaluatee implements Evaluatee
{
	protected final Evaluatee delegate;

	public ForwardingEvaluatee(@NonNull final Evaluatee delegate) {this.delegate = delegate;}

	@Nullable
	@Override
	public <T> T get_ValueAsObject(final String variableName)
	{
		return delegate.get_ValueAsObject(variableName);
	}

	@Nullable
	@Override
	public String get_ValueAsString(final String variableName)
	{
		return delegate.get_ValueAsString(variableName);
	}

	@Nullable
	@Override
	public Integer get_ValueAsInt(final String variableName, @Nullable final Integer defaultValue)
	{
		return delegate.get_ValueAsInt(variableName, defaultValue);
	}

	@Nullable
	@Override
	public Boolean get_ValueAsBoolean(final String variableName, @Nullable final Boolean defaultValue)
	{
		return delegate.get_ValueAsBoolean(variableName, defaultValue);
	}

	@Nullable
	@Override
	public BigDecimal get_ValueAsBigDecimal(final String variableName, @Nullable final BigDecimal defaultValue)
	{
		return delegate.get_ValueAsBigDecimal(variableName, defaultValue);
	}

	@Nullable
	@Override
	public Date get_ValueAsDate(final String variableName, @Nullable final Date defaultValue)
	{
		return delegate.get_ValueAsDate(variableName, defaultValue);
	}

	@Override
	public Optional<Object> get_ValueIfExists(final @NonNull String variableName, final @NonNull Class<?> targetType)
	{
		return delegate.get_ValueIfExists(variableName, targetType);
	}
}
