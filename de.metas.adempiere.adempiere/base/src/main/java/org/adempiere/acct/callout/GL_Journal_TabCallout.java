package org.adempiere.acct.callout;

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


import org.adempiere.ad.ui.spi.TabCalloutAdapter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.GridTab;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalBatch;

/**
 * @author al
 */
public class GL_Journal_TabCallout extends TabCalloutAdapter
{
	@Override
	public void onNew(final GridTab gridTab)
	{
		final I_GL_Journal glJournal = InterfaceWrapperHelper.create(gridTab, I_GL_Journal.class);

		//
		// fresh 07569: copy description from glJournalBatch to glJournal
		final I_GL_JournalBatch glJournalBatch = glJournal.getGL_JournalBatch();
		glJournal.setDescription(glJournalBatch.getDescription());
	}
}
