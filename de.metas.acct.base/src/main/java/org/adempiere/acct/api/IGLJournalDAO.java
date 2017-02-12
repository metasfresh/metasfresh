package org.adempiere.acct.api;

import java.util.Date;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_GL_Journal;
import org.compiere.model.I_GL_JournalBatch;

public interface IGLJournalDAO extends ISingletonService

{
	/**
	 * Retrieve all the active GL_Journal entries (if any) for the GL_JournalBatch given as parameter
	 * 
	 * @param batch
	 * @return
	 */
	List<I_GL_Journal> retrieveJournalsForBatch(I_GL_JournalBatch batch);

	/**
	 * Retrieve all the GL_Journal documents that are marked as posted but do not actually have fact accounts.
	 * Exclude the entries that don't have either Credit or Debit amounts. These entries will produce 0 in posting
	 * 
	 * @param ctx
	 * @param startTime
	 * @return
	 */
	List<I_GL_Journal> retrievePostedWithoutFactAcct(Properties ctx, Date startTime);
}
