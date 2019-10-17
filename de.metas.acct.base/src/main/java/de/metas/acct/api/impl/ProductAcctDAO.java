package de.metas.acct.api.impl;

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

import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_M_Product_Acct;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.I_M_Product_Category_Acct;
import org.compiere.util.Env;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.api.IProductAcctDAO;
import de.metas.cache.annotation.CacheCtx;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.util.Services;
import lombok.NonNull;

public class ProductAcctDAO implements IProductAcctDAO
{
	@Override
	public ActivityId retrieveActivityForAcct(
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@NonNull final ProductId productId)
	{
		final Properties ctx = Env.getCtx();

		final AcctSchemaId acctSchemaId = Services.get(IAcctSchemaDAO.class).getAcctSchemaIdByClientAndOrg(clientId, orgId);
		if (acctSchemaId == null)
		{
			return null;
		}

		final I_M_Product_Acct acctInfo = retrieveProductAcctOrNull(ctx, acctSchemaId, productId);
		if (acctInfo == null)
		{
			return null;
		}

		return ActivityId.ofRepoIdOrNull(acctInfo.getC_Activity_ID());
	}

	@Cached(cacheName = I_M_Product_Acct.Table_Name)
	public I_M_Product_Acct retrieveProductAcctOrNull(@CacheCtx final Properties ctx, final AcctSchemaId acctSchemaId, final ProductId productId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Product_Acct.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_M_Product_Acct.COLUMNNAME_C_AcctSchema_ID, acctSchemaId)
				.addEqualsFilter(I_M_Product_Acct.COLUMNNAME_M_Product_ID, productId)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_M_Product_Acct.class);
	}

	@Override
	public I_M_Product_Acct retrieveProductAcctOrNull(final AcctSchema acctSchema, final ProductId productId)
	{
		final Properties ctx = Env.getCtx();
		return retrieveProductAcctOrNull(ctx, acctSchema.getId(), productId);
	}

	@Override
	public ActivityId getProductActivityId(@NonNull final ProductId productId)
	{
		final Properties ctx = Env.getCtx();
		final AcctSchema schema = Services.get(IAcctSchemaDAO.class).getByCliendAndOrg(ctx);
		final I_M_Product_Acct productAcct = retrieveProductAcctOrNull(ctx, schema.getId(), productId);
		if (productAcct == null)
		{
			return null;
		}

		return ActivityId.ofRepoIdOrNull(productAcct.getC_Activity_ID());
	}

	@Override
	@Cached(cacheName = I_M_Product_Category_Acct.Table_Name + "#Default")
	public I_M_Product_Category_Acct retrieveDefaultProductCategoryAcct(@CacheCtx final Properties ctx, final AcctSchemaId acctSchemaId)
	{
		final I_M_Product_Category pc = Services.get(IProductDAO.class).retrieveDefaultProductCategory(ctx);

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Product_Category_Acct.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_M_Product_Category_Acct.COLUMNNAME_C_AcctSchema_ID, acctSchemaId)
				.addEqualsFilter(I_M_Product_Category_Acct.COLUMNNAME_M_Product_Category_ID, pc.getM_Product_Category_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnlyNotNull(I_M_Product_Category_Acct.class);
	}

	@Override
	public I_M_Product_Category_Acct retrieveDefaultProductCategoryAcct(final AcctSchema acctSchema)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(acctSchema);
		return retrieveDefaultProductCategoryAcct(ctx, acctSchema.getId());
	}

}
