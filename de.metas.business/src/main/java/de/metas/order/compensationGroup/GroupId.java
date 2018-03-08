package de.metas.order.compensationGroup;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
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

@Value
public class GroupId
{
	public static GroupId of(final String documentTableName, final int documentId, final int orderCompensationGroupId)
	{
		return new GroupId(documentTableName, documentId, orderCompensationGroupId);
	}

	@Getter(AccessLevel.PRIVATE)
	private final String documentTableName;
	@Getter(AccessLevel.PRIVATE)
	private final int documentId;
	private final int orderCompensationGroupId;

	private GroupId(@NonNull final String documentTableName, final int documentId, final int orderCompensationGroupId)
	{
		this.documentTableName = documentTableName;

		Check.assume(documentId > 0, "documentId > 0");
		this.documentId = documentId;

		Check.assume(orderCompensationGroupId > 0, "orderCompensationGroupId > 0");
		this.orderCompensationGroupId = orderCompensationGroupId;
	}

	public void assertDocumentTableName(final String expectedDocumentTableName)
	{
		if (!getDocumentTableName().equals(expectedDocumentTableName))
		{
			throw new AdempiereException("Invalid group document table name: " + this + ". Expected " + expectedDocumentTableName);
		}
	}

	public int getDocumentIdAssumingTableName(final String expectedDocumentTableName)
	{
		assertDocumentTableName(expectedDocumentTableName);
		return getDocumentId();
	}
}
