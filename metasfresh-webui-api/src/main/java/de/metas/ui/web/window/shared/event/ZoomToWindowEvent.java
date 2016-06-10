package de.metas.ui.web.window.shared.event;

import java.util.Objects;

import org.adempiere.model.ZoomInfoFactory.ZoomInfo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.base.Preconditions;

/*
 * #%L
 * metasfresh-webui
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
public final class ZoomToWindowEvent extends ModelEvent
{
	public static ZoomToWindowEvent of(final ZoomInfo zoomInfo)
	{
		Preconditions.checkNotNull(zoomInfo, "zoomInfo");
		return new ZoomToWindowEvent(zoomInfo.getAD_Window_ID());
	}

	@JsonCreator
	public static ZoomToWindowEvent of(@JsonProperty("AD_Window_ID") final int AD_Window_ID)
	{
		return new ZoomToWindowEvent(AD_Window_ID);
	}

	@JsonProperty("AD_Window_ID")
	private final int AD_Window_ID;

	private ZoomToWindowEvent(final int AD_Window_ID)
	{
		super();
		this.AD_Window_ID = AD_Window_ID;
	}

	@Override
	protected void toString(final ToStringHelper toStringHelper)
	{
		toStringHelper.add("AD_Window_ID", AD_Window_ID);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(AD_Window_ID);
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

		if (!(obj instanceof ZoomToWindowEvent))
		{
			return false;
		}

		final ZoomToWindowEvent other = (ZoomToWindowEvent)obj;
		return Objects.equals(AD_Window_ID, other.AD_Window_ID);
	}

	@JsonIgnore
	public int getAD_Window_ID()
	{
		return AD_Window_ID;
	}

}
