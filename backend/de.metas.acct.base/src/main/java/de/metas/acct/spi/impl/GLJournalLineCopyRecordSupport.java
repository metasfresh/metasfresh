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

import com.google.common.collect.ImmutableSet;
import de.metas.copy_with_details.GeneralCopyRecordSupport;
import lombok.NonNull;
import org.compiere.model.I_GL_JournalLine;
import org.compiere.model.PO;
import org.compiere.model.copy.ValueToCopy;

public class GLJournalLineCopyRecordSupport extends GeneralCopyRecordSupport
{
	/**
	 * List of column names which needs to be copied directly when using copy with details functionality (even if those columns are marked as IsComputed)
	 */
	private static final ImmutableSet<String> COLUMNNAMES_ToCopyDirectly = ImmutableSet.of(
			I_GL_JournalLine.COLUMNNAME_GL_JournalLine_Group,
			I_GL_JournalLine.COLUMNNAME_IsAllowAccountDR,
			I_GL_JournalLine.COLUMNNAME_IsAllowAccountCR
	);

	@Override
	protected ValueToCopy getValueToCopy_Before(@NonNull final PO to, @NonNull final PO from, @NonNull final String columnName)
	{
		return COLUMNNAMES_ToCopyDirectly.contains(columnName) ? ValueToCopy.DIRECT_COPY : ValueToCopy.NOT_SPECIFIED;
	}

}
