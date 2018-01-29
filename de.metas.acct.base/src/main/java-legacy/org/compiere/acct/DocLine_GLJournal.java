package org.compiere.acct;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_GL_JournalLine;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

class DocLine_GLJournal extends DocLine
{
	private int acctSchemaId = 0;

	public DocLine_GLJournal(final I_GL_JournalLine glJournalLine, final Doc_GLJournal doc)
	{
		super(InterfaceWrapperHelper.getPO(glJournalLine), doc);
	}

	public final int getC_AcctSchema_ID()
	{
		return acctSchemaId;
	}

	public final void setC_AcctSchema_ID(final int acctSchemaId)
	{
		this.acctSchemaId = acctSchemaId;
	}
}
