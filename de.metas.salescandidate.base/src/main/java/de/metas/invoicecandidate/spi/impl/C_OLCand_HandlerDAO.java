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


import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;

import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.invoicecandidate.api.InvoiceCandidate_Constants;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.ordercandidate.model.I_C_OLCand;

class C_OLCand_HandlerDAO
{
	public IQueryBuilder<I_C_OLCand> retrieveMissingCandidatesQuery(final Properties ctx, final String trxName)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_C_OLCand> queryBuilder = queryBL.createQueryBuilder(I_C_OLCand.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient();

		//
		// Only which are for our data source
		{
			final I_AD_InputDataSource dataSource = Services.get(IInputDataSourceDAO.class).retrieveInputDataSource(ctx, InvoiceCandidate_Constants.DATA_DESTINATION_INTERNAL_NAME, true, trxName);
			queryBuilder.addEqualsFilter(I_C_OLCand.COLUMN_AD_DataDestination_ID, dataSource.getAD_InputDataSource_ID());
		}

		//
		// Only those which were not already created
		{
			final IQuery<I_C_Invoice_Candidate> existingICsQuery = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class, ctx, trxName)
					.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_C_OLCand.class))
					.create();
			
			queryBuilder.addNotInSubQueryFilter(I_C_OLCand.COLUMNNAME_C_OLCand_ID, I_C_OLCand.COLUMNNAME_Record_ID, existingICsQuery);
		}
		
		//
		// Order by
		queryBuilder.orderBy()
				.addColumn(I_C_OLCand.COLUMN_C_OLCand_ID);
		
		return queryBuilder;
	}

}
