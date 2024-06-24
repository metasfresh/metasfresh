package de.metas.async.exceptions;

/*
 * #%L
 * de.metas.async
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

import de.metas.async.model.I_C_Queue_Element;
import org.adempiere.exceptions.AdempiereException;

/**
 * Exception thrown when system tries to load the underlying object from an {@link I_C_Queue_Element} but the record is currently(temporary) not available for various reasons (e.g. underlying element
 * was created in a transaction why is not yet commited).
 * 
 * @author tsa
 * 
 */
public class PackageItemNotAvailableException extends AdempiereException
{
	private static final long serialVersionUID = 4271514302298190437L;

	private final String tableName;
	private final int recordId;

	public PackageItemNotAvailableException(final String tableName, final int recordId)
	{
		super(buildMsg(tableName, recordId));
		this.tableName = tableName;
		this.recordId = recordId;
	}

	private static String buildMsg(String tableName, int recordId)
	{
		return "@NotFound@ @Record_ID@ (@TableName@:" + tableName + ", @Record_ID@:" + recordId + ")";
	}

	public String getTableName()
	{
		return tableName;
	}

	public int getRecordId()
	{
		return recordId;
	}
}
