package org.adempiere.acct.api.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.acct.api.IAccountDimension;
import org.adempiere.acct.api.IGLDistributionDAO;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery;
import org.compiere.model.I_GL_Distribution;
import org.compiere.model.I_GL_DistributionLine;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;

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

public class GLDistributionDAO implements IGLDistributionDAO
{
	@Cached(cacheName = I_GL_Distribution.Table_Name + "#by#" + I_GL_Distribution.COLUMNNAME_Account_ID)
	public List<I_GL_Distribution> retrieveForElementValue(@CacheCtx final Properties ctx, final int elementValueId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_GL_Distribution.class, ctx, ITrx.TRXNAME_None)
				.addOnlyContextClientOrSystem()
				.addInArrayOrAllFilter(I_GL_Distribution.COLUMN_Account_ID, null, elementValueId) // for given element value or without an element value
				//
				.orderBy()
				.addColumn(I_GL_Distribution.COLUMN_AD_Client_ID, Direction.Descending, Nulls.Last) // first, return those for our AD_Client_ID
				.addColumn(I_GL_Distribution.COLUMN_GL_Distribution_ID) // just to have a predictable order
				.endOrderBy()
				//
				.create()
				.list(I_GL_Distribution.class);
	}

	@Override
	public List<I_GL_Distribution> retrieve(final Properties ctx, final IAccountDimension dimension, final String PostingType, final int C_DocType_ID)
	{
		Check.assumeNotNull(dimension, "dimension not null");

		final List<I_GL_Distribution> result = new ArrayList<>();
		for (final I_GL_Distribution glDistribution : retrieveForElementValue(ctx, dimension.getC_ElementValue_ID()))
		{
			if (!glDistribution.isActive() || !glDistribution.isValid())
			{
				continue;
			}

			// Mandatory Acct Schema
			if (glDistribution.getC_AcctSchema_ID() != dimension.getC_AcctSchema_ID())
			{
				continue;
			}
			// Only Posting Type
			if (glDistribution.getPostingType() != null && !Check.equals(glDistribution.getPostingType(), PostingType))
			{
				continue;
			}
			// Only DocType
			if (glDistribution.getC_DocType_ID() > 0 && glDistribution.getC_DocType_ID() != C_DocType_ID)
			{
				continue;
			}
			//
			// Optional Elements - "non-Any"
			if (!glDistribution.isAnyOrg() && glDistribution.getAD_Org_ID() != dimension.getAD_Org_ID())
			{
				continue;
			}
			if (!glDistribution.isAnyAcct() && glDistribution.getAccount_ID() != dimension.getC_ElementValue_ID())
			{
				continue;
			}
			if (!glDistribution.isAnyProduct() && glDistribution.getM_Product_ID() != dimension.getM_Product_ID())
			{
				continue;
			}
			if (!glDistribution.isAnyBPartner() && glDistribution.getC_BPartner_ID() != dimension.getC_BPartner_ID())
			{
				continue;
			}
			if (!glDistribution.isAnyProject() && glDistribution.getC_Project_ID() != dimension.getC_Project_ID())
			{
				continue;
			}
			if (!glDistribution.isAnyCampaign() && glDistribution.getC_Campaign_ID() != dimension.getC_Campaign_ID())
			{
				continue;
			}
			if (!glDistribution.isAnyActivity() && glDistribution.getC_Activity_ID() != dimension.getC_Activity_ID())
			{
				continue;
			}
			if (!glDistribution.isAnyOrgTrx() && glDistribution.getAD_OrgTrx_ID() != dimension.getAD_OrgTrx_ID())
			{
				continue;
			}
			if (!glDistribution.isAnySalesRegion() && glDistribution.getC_SalesRegion_ID() != dimension.getC_SalesRegion_ID())
			{
				continue;
			}
			if (!glDistribution.isAnyLocTo() && glDistribution.getC_LocTo_ID() != dimension.getC_LocTo_ID())
			{
				continue;
			}
			if (!glDistribution.isAnyLocFrom() && glDistribution.getC_LocFrom_ID() != dimension.getC_LocFrom_ID())
			{
				continue;
			}
			if (!glDistribution.isAnyUser1() && glDistribution.getUser1_ID() != dimension.getUser1_ID())
			{
				continue;
			}
			if (!glDistribution.isAnyUser2() && glDistribution.getUser2_ID() != dimension.getUser2_ID())
			{
				continue;
			}

			//
			// Add it to our result list
			result.add(glDistribution);
		}

		return result;
	}

	@Override
	public List<I_GL_DistributionLine> retrieveLines(final I_GL_Distribution glDistribution)
	{
		Check.assumeNotNull(glDistribution, "glDistribution not null");
		final Properties ctx = InterfaceWrapperHelper.getCtx(glDistribution);
		final String trxName = InterfaceWrapperHelper.getTrxName(glDistribution);
		final int glDistributionId = glDistribution.getGL_Distribution_ID();
		final List<I_GL_DistributionLine> lines = retrieveLines(ctx, glDistributionId, trxName);

		// optimization
		for (final I_GL_DistributionLine line : lines)
		{
			line.setGL_Distribution(glDistribution);
		}

		return lines;
	}

	@Cached(cacheName = I_GL_DistributionLine.Table_Name + "#by#" + I_GL_DistributionLine.COLUMNNAME_GL_Distribution_ID)
	List<I_GL_DistributionLine> retrieveLines(@CacheCtx final Properties ctx, final int glDistributionId, @CacheTrx final String trxName)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_GL_DistributionLine.class, ctx, trxName)
				.addEqualsFilter(I_GL_DistributionLine.COLUMN_GL_Distribution_ID, glDistributionId)
				.addOnlyActiveRecordsFilter()
				//
				.orderBy()
				.addColumn(I_GL_DistributionLine.COLUMN_Line)
				.endOrderBy()
				//
				.create()
				.list(I_GL_DistributionLine.class);
	}

	@Override
	public final int retrieveLastLineNo(final I_GL_Distribution glDistribution)
	{
		Check.assumeNotNull(glDistribution, "glDistribution not null");
		final Integer lastLineNo = Services.get(IQueryBL.class)
				.createQueryBuilder(I_GL_DistributionLine.class, glDistribution)
				.addEqualsFilter(I_GL_DistributionLine.COLUMN_GL_Distribution_ID, glDistribution.getGL_Distribution_ID())
				//
				.create()
				.aggregate(I_GL_DistributionLine.COLUMNNAME_Line, IQuery.AGGREGATE_MAX, Integer.class);

		return lastLineNo == null ? 0 : lastLineNo;
	}
}
