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


import java.util.List;

import org.adempiere.acct.api.IGLJournalDAO;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.EqualsQueryFilter;
import org.adempiere.util.Services;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalBatch;

public class GLJournalDAO implements IGLJournalDAO
{

	@Override
	public List<I_GL_Journal> retrieveJournalsForBatch(final I_GL_JournalBatch batch)
	{
		final List<I_GL_Journal> journals = Services.get(IQueryBL.class).createQueryBuilder(I_GL_Journal.class)
				.setContext(batch)
				.filter(new EqualsQueryFilter<I_GL_Journal>(I_GL_Journal.COLUMNNAME_GL_JournalBatch_ID, batch.getGL_JournalBatch_ID()))
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_GL_Journal.class);
		
		return journals;
	}

}
