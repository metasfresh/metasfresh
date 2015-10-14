package org.adempiere.acct.api.impl;

/*
 * #%L
 * ADempiere ERP - Base
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


import org.adempiere.acct.api.IGLJournalBL;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.X_GL_Journal;

public class GLJournalBL implements IGLJournalBL
{
	@Override
	public boolean isComplete(final I_GL_Journal glJournal)
	{
		final String ds = glJournal.getDocStatus();
		return X_GL_Journal.DOCSTATUS_Completed.equals(ds)
				|| X_GL_Journal.DOCSTATUS_Closed.equals(ds)
				|| X_GL_Journal.DOCSTATUS_Reversed.equals(ds);
	}
}
