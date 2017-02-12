package de.metas.acct;

import java.util.Properties;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.ISingletonService;

import de.metas.acct.model.I_Fact_Acct_Log;
import de.metas.acct.model.I_Fact_Acct_Summary;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * {@link I_Fact_Acct_Log} DAO.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IFactAcctLogDAO extends ISingletonService
{
	String PROCESSINGTAG_NULL = null;

	/**
	 * Retrieves an iterable stream of {@link I_Fact_Acct_Log}s which were not already tagged.
	 * 
	 * This method is also tagging those logs, to prevent other business logic to consider them.
	 * 
	 * @param ctx
	 * @param limit
	 * @return
	 */
	IFactAcctLogIterable tagAndRetrieve(Properties ctx, final int limit);

	/**
	 * Retrieves the {@link I_Fact_Acct_Summary} in which the given {@link IFactAcctSummaryKey} shall be aggregated.
	 * 
	 * @param ctx
	 * @param key
	 * @return {@link I_Fact_Acct_Summary} or <code>null</code>
	 */
	I_Fact_Acct_Summary retrieveLastMatchingFactAcctSummary(Properties ctx, IFactAcctSummaryKey key);

	IQueryBuilder<I_Fact_Acct_Summary> retrieveCurrentAndNextMatchingFactAcctSummaryQuery(Properties ctx, IFactAcctSummaryKey key);

	/**
	 * @param ctx
	 * @param processingTag
	 * @return true if there are any {@link I_Fact_Acct_Log}s tagged with given tag.
	 */
	boolean hasLogs(Properties ctx, String processingTag);

	void updateFactAcctEndingBalanceForTag(String processingTag);
}
