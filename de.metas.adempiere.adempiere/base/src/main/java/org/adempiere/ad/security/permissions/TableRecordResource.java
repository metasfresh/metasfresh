package org.adempiere.ad.security.permissions;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import javax.annotation.concurrent.Immutable;

import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.adempiere.util.lang.ObjectUtils;

import de.metas.util.Check;

/**
 * Table Record resource.
 * 
 * @author tsa
 *
 */
@Immutable
public final class TableRecordResource implements Resource
{
	public static final TableRecordResource of(final int adTableId, final int recordId)
	{
		return new TableRecordResource(adTableId, recordId);
	}

	private final int AD_Table_ID;
	private final int Record_ID;
	private int hashcode = 0;

	private TableRecordResource(final int AD_Table_ID, final int Record_ID)
	{
		super();

		Check.assume(AD_Table_ID > 0, "AD_Table_ID > 0");
		this.AD_Table_ID = AD_Table_ID;

		Check.assume(Record_ID >= 0, "Record_ID > 0");
		this.Record_ID = Record_ID;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public int hashCode()
	{
		if (hashcode == 0)
		{
			hashcode = new HashcodeBuilder()
					.append(31) // seed
					.append(AD_Table_ID)
					.append(Record_ID)
					.toHashcode();
		}
		return hashcode;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final TableRecordResource other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(AD_Table_ID, other.AD_Table_ID)
				.append(Record_ID, other.Record_ID)
				.isEqual();
	}

	public int getAD_Table_ID()
	{
		return AD_Table_ID;
	}

	public int getRecord_ID()
	{
		return Record_ID;
	}

}
