package de.metas.acct;

import java.util.Date;

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
 * The aggregation dimension used for aggregating {@link I_Fact_Acct_Log}s into {@link I_Fact_Acct_Summary} records.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IFactAcctSummaryKey
{
	/**
	 * @return string identifier of this key.
	 */
	String asString();

	int getAD_Client_ID();

	int getAD_Org_ID();

	int getC_Period_ID();
	
	Date getDateAcct();

	String getPostingType();

	int getC_AcctSchema_ID();

	int getC_ElementValue_ID();

	int getPA_ReportCube_ID();
}
