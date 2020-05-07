package de.metas.document.archive.esb.api;

/*
 * #%L
 * de.metas.document.archive.esb.api
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.util.Arrays;

public class ArchiveSetDataRequest
{
	private int sessionId;

	// NOTE: this goes to AD_Archive.AD_Table_ID=754, Record_ID=this.adArchiveId
	private int adArchiveId;
	private byte[] data;

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + adArchiveId;
		result = prime * result + Arrays.hashCode(data);
		result = prime * result + sessionId;
		return result;
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
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final ArchiveSetDataRequest other = (ArchiveSetDataRequest)obj;
		if (adArchiveId != other.adArchiveId)
		{
			return false;
		}
		if (!Arrays.equals(data, other.data))
		{
			return false;
		}
		if (sessionId != other.sessionId)
		{
			return false;
		}
		return true;
	}

	public int getSessionId()
	{
		return sessionId;
	}

	public void setSessionId(final int sessionId)
	{
		this.sessionId = sessionId;
	}

	public int getAdArchiveId()
	{
		return adArchiveId;
	}

	public void setAdArchiveId(final int adArchiveId)
	{
		this.adArchiveId = adArchiveId;
	}

	public byte[] getData()
	{
		return data;
	}

	public void setData(final byte[] data)
	{
		this.data = data;
	}

}
