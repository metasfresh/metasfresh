package org.eevolution.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_AD_Workflow;
import org.eevolution.api.IPPWorkflowDAO;

import de.metas.adempiere.util.CacheCtx;
import de.metas.util.Services;

public class PPWorkflowDAO implements IPPWorkflowDAO
{

	@Override
	public int retrieveWorkflowIdForProduct(final I_M_Product product)
	{
		final I_AD_Workflow workflow = retrieveWorkflowForProduct(product);
		if (workflow == null)
		{
			return -1;
		}

		return workflow.getAD_Workflow_ID();
	}

	@Override
	public I_AD_Workflow retrieveWorkflowForProduct(final I_M_Product product)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(product);
		final String productValue = product.getValue();
		return retrieveWorkflowForProduct(ctx, productValue);
	}

	@Cached(cacheName = I_AD_Workflow.Table_Name + "#by#" + I_AD_Workflow.COLUMNNAME_Value)
	/* package */ I_AD_Workflow retrieveWorkflowForProduct(@CacheCtx final Properties ctx, final String productValue)
	{

		final IQueryBuilder<I_AD_Workflow> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Workflow.class, ctx, ITrx.TRXNAME_None);

		final ICompositeQueryFilter<I_AD_Workflow> filters = queryBuilder.getCompositeFilter();
		filters.addEqualsFilter(I_AD_Workflow.COLUMNNAME_Value, productValue);
		filters.addEqualsFilter(I_AD_Workflow.COLUMNNAME_WorkflowType, X_AD_Workflow.WORKFLOWTYPE_Manufacturing);
		filters.addOnlyContextClient(ctx);
		filters.addOnlyActiveRecordsFilter();

		return queryBuilder.create()
				.firstOnly(I_AD_Workflow.class);
	}

}
