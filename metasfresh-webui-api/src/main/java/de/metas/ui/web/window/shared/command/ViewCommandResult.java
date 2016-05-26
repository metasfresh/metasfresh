package de.metas.ui.web.window.shared.command;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

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
public final class ViewCommandResult implements Serializable
{
	@JsonCreator
	public static final ViewCommandResult of(@JsonProperty("r") final Object resultObj)
	{
		if(resultObj == null)
		{
			return NULL;
		}
		return new ViewCommandResult(resultObj);
	}
	
	public static final transient ViewCommandResult NULL = new ViewCommandResult(null);

	@JsonProperty("r")
	private final Object resultObj;

	private ViewCommandResult(final Object resultObj)
	{
		this.resultObj = resultObj;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(resultObj)
				.toString();
	}

	@JsonIgnore
	public <T> T getValue()
	{
		@SuppressWarnings("unchecked")
		final T resultCasted = (T)resultObj;
		return resultCasted;
	}

}
