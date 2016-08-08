package de.metas.ui.web.window_old.datasource;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

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
public class SaveResult implements Serializable
{
	public static SaveResult of(final int recordIndex, final Object recordId)
	{
		return new SaveResult(recordIndex, recordId);
	}

	@JsonProperty("recordIndex")
	private final int recordIndex;
	@JsonProperty("recordId")
	private final Object recordId;

	private SaveResult(@JsonProperty("recordIndex") final int recordIndex, @JsonProperty("recordId") final Object recordId)
	{
		super();
		this.recordIndex = recordIndex;
		this.recordId = recordId;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("recordIndex", recordIndex)
				.add("recordId", recordId)
				.toString();
	}

	public int getRecordIndex()
	{
		return recordIndex;
	}

	public Object getRecordId()
	{
		return recordId;
	}
}
