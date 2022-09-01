package de.metas.acct.aggregation;

import de.metas.acct.model.I_Fact_Acct_Log;
import de.metas.util.ISingletonService;
import org.adempiere.ad.dao.QueryLimit;
import org.compiere.model.IQuery;

import java.util.Properties;

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
 * {@link I_Fact_Acct_Log} BL.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IFactAcctLogBL extends ISingletonService
{

	/**
	 * Process all pending {@link I_Fact_Acct_Log}s.
	 * 
	 * @param limit maximum amount of logs to process or {@link IQuery#NO_LIMIT}.
	 */
	FactAcctLogProcessResult processAll(Properties ctx, QueryLimit limit);
}
