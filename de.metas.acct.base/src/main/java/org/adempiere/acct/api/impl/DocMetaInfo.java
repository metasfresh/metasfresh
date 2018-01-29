package org.adempiere.acct.api.impl;

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


import java.lang.reflect.Constructor;

import org.adempiere.acct.api.IDocMetaInfo;
import org.adempiere.util.Check;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.acct.Doc;

/**
 * Immutable {@link IDocMetaInfo} implementation
 *
 * @author tsa
 *
 */
/* package */final class DocMetaInfo implements IDocMetaInfo
{
	private final int adTableId;
	private final String tableName;
	private final Class<? extends Doc<?>> docClass;
	private final Constructor<? extends Doc<?>> docConstructor;

	public DocMetaInfo(final int adTableId,
			final String tableName,
			final Class<? extends Doc<?>> docClass,
			final Constructor<? extends Doc<?>> docConstructor)
	{
		super();

		Check.assume(adTableId > 0, "adTableId > 0");
		this.adTableId = adTableId;

		Check.assumeNotEmpty(tableName, "tableName not empty");
		this.tableName = tableName;

		Check.assumeNotNull(docClass, "docClass not null");
		this.docClass = docClass;

		Check.assumeNotNull(docConstructor, "docConstructor not null");
		this.docConstructor = docConstructor;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public int getAD_Table_ID()
	{
		return adTableId;
	}

	@Override
	public String getTableName()
	{
		return tableName;
	}

	@Override
	public Class<? extends Doc<?>> getDocClass()
	{
		return docClass;
	}

	@Override
	public Constructor<? extends Doc<?>> getDocConstructor()
	{
		return docConstructor;
	}
}
