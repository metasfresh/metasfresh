package de.metas.acct.spi.impl;

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


import java.util.List;

import org.adempiere.model.GeneralCopyRecordSupport;
import org.compiere.model.I_GL_JournalLine;
import org.compiere.model.PO;

import com.google.common.collect.ImmutableList;

public class GLJournalLineCopyRecordSupport extends GeneralCopyRecordSupport
{
	/**
	 * List of column names which needs to be copied directly when using copy with details functionality (even if those columns are marked as IsComputed)
	 */
	private static final List<String> COLUMNNAMES_ToCopyDirectly = ImmutableList.of(
			I_GL_JournalLine.COLUMNNAME_GL_JournalLine_Group,
			I_GL_JournalLine.COLUMNNAME_IsAllowAccountDR,
			I_GL_JournalLine.COLUMNNAME_IsAllowAccountCR
			);

	@Override
	public Object getValueToCopy(final PO to, final PO from, final String columnName)
	{
		if (COLUMNNAMES_ToCopyDirectly.contains(columnName))
		{
			return from.get_Value(columnName);
		}
		
		//
		// Fallback to super:
		return super.getValueToCopy(to, from, columnName);
	}

}
