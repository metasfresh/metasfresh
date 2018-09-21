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


import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_WF_Node;
import org.eevolution.api.IPPWFNodeAssetDAO;
import org.eevolution.model.I_PP_WF_Node_Asset;

import de.metas.adempiere.util.CacheCtx;
import de.metas.util.Services;

public class PPWFNodeAssetDAO implements IPPWFNodeAssetDAO
{
	@Override
	@Cached(cacheName = I_PP_WF_Node_Asset.Table_Name + "#by#" + I_PP_WF_Node_Asset.COLUMNNAME_AD_WF_Node_ID)
	public List<I_PP_WF_Node_Asset> retrieveForWFNode(@CacheCtx Properties ctx, int adWFNodeId)
	{
		final IQueryBuilder<I_PP_WF_Node_Asset> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_PP_WF_Node_Asset.class, ctx, ITrx.TRXNAME_None);

		final ICompositeQueryFilter<I_PP_WF_Node_Asset> filters = queryBuilder.getCompositeFilter();
		filters.addEqualsFilter(I_PP_WF_Node_Asset.COLUMNNAME_AD_WF_Node_ID, adWFNodeId);
		filters.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_PP_WF_Node_Asset.COLUMNNAME_SeqNo);

		return queryBuilder
				.create()
				.list();
	}

	@Override
	public List<I_PP_WF_Node_Asset> retrieveForWFNode(final I_AD_WF_Node wfNode)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(wfNode);
		final int adWFNodeId = wfNode.getAD_WF_Node_ID();
		return retrieveForWFNode(ctx, adWFNodeId);
	}

}
