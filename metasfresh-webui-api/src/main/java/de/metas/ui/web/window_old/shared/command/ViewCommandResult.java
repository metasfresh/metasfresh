package de.metas.ui.web.window_old.shared.command;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.MoreObjects;

import de.metas.ui.web.window_old.shared.datatype.NullValue;

/*
 * #%L
 * metasfresh-webui-api
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@SuppressWarnings("serial")
@JsonSerialize(using = ViewCommandResult_JSONSerializer.class)
@JsonDeserialize(using = ViewCommandResult_JSONDeserializer.class)
public final class ViewCommandResult implements Serializable
{
	@JsonCreator
	public static final ViewCommandResult of(final Object resultObj)
	{
		if (NullValue.isNull(resultObj))
		{
			return NULL;
		}
		return new ViewCommandResult(resultObj);
	}

	public static final transient ViewCommandResult NULL = new ViewCommandResult(null);

	private final Object resultObj;

	private ViewCommandResult(final Object resultObj)
	{
		super();
		this.resultObj = resultObj;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.addValue(resultObj)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(resultObj);
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}

		if (!(obj instanceof ViewCommandResult))
		{
			return false;
		}

		final ViewCommandResult other = (ViewCommandResult)obj;
		return Objects.equals(resultObj, other.resultObj);
	}

	public <T> T getValue()
	{
		@SuppressWarnings("unchecked")
		final T resultCasted = (T)resultObj;
		return resultCasted;
	}

}
