package de.metas.invoicecandidate.spi.impl;

/*
 * #%L
 * de.metas.swat.base
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

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.util.Services;
import org.compiere.model.Query;

import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.invoicecandidate.api.InvoiceCandidate_Constants;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.ordercandidate.model.I_C_OLCand;

public class C_OLCand_HandlerDAO
{
	public Iterator<I_C_OLCand> retrieveMissingCandidates(final Properties ctx, final int limit, final String trxName)
	{
		final I_AD_InputDataSource dataSource = Services.get(IInputDataSourceDAO.class).retrieveInputDataSource(ctx, InvoiceCandidate_Constants.DATA_DESTINATION_INTERNAL_NAME, true, trxName);
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
		
		final StringBuilder whereClause = new StringBuilder();
		final List<Object> params = new ArrayList<Object>();

		// Only which are for our datasource
		whereClause.append(I_C_OLCand.COLUMNNAME_AD_DataDestination_ID).append("=?");
		params.add(dataSource.getAD_InputDataSource_ID());

		// Only those which were not created
		whereClause.append(" AND ").append(I_C_OLCand.COLUMNNAME_C_OLCand_ID).append(" NOT IN (")
				.append(" SELECT ").append(I_C_Invoice_Candidate.COLUMNNAME_Record_ID)
				.append(" FROM ").append(I_C_Invoice_Candidate.Table_Name)
				.append(" WHERE ").append(I_C_Invoice_Candidate.COLUMNNAME_AD_Table_ID).append("=?")
				.append(")");
		params.add(adTableDAO.retrieveTableId(I_C_OLCand.Table_Name));

		return new Query(ctx, I_C_OLCand.Table_Name, whereClause.toString(), trxName)
				.setParameters(params)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(I_C_OLCand.COLUMNNAME_C_OLCand_ID)
				.setLimit(limit)
				.iterate(I_C_OLCand.class);
	}

}
