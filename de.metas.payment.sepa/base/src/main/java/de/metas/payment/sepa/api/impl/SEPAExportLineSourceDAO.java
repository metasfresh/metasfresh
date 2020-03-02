package de.metas.payment.sepa.api.impl;

/*
 * #%L
 * de.metas.payment.sepa
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


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.compiere.model.Query;

import de.metas.payment.sepa.api.ISEPAExportLineSourceDAO;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;

public class SEPAExportLineSourceDAO implements ISEPAExportLineSourceDAO
{

	@Override
	public Iterator<I_SEPA_Export_Line> retrieveRetrySEPAExportLines(Properties ctx, int adOrgId, String trxName)
	{
		final StringBuilder sqlWhere = new StringBuilder("1=1");
		final List<Object> params = new ArrayList<Object>();

		if (adOrgId > 0)
		{
			sqlWhere.append(" AND " + I_SEPA_Export_Line.COLUMNNAME_AD_Org_ID + " =? ");
			params.add(adOrgId);
		}

		sqlWhere.append(" AND " + I_SEPA_Export_Line.COLUMNNAME_SEPA_Export_Line_Retry_ID + " IS NULL");

		final Iterator<I_SEPA_Export_Line> retryExportLines =
				new Query(ctx, I_SEPA_Export_Line.Table_Name, sqlWhere.toString(), trxName)
						.setParameters(params)
						.iterate(I_SEPA_Export_Line.class);

		return retryExportLines;
	}

}
