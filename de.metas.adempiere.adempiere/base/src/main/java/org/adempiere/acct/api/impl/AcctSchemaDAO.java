package org.adempiere.acct.api.impl;

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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.acct.api.IAcctSchemaBL;
import org.adempiere.acct.api.IAcctSchemaDAO;
import org.adempiere.acct.api.exception.AccountingException;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IClientDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.StringUtils;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_ClientInfo;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_AcctSchema_Default;
import org.compiere.model.I_C_AcctSchema_Element;
import org.compiere.model.I_C_AcctSchema_GL;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.logging.LogManager;

public class AcctSchemaDAO implements IAcctSchemaDAO
{
	private static final Logger logger = LogManager.getLogger(AcctSchemaDAO.class);

	@Override
	public I_C_AcctSchema retrieveAcctSchema(final Properties ctx)
	{
		final int ad_Client_ID = Env.getAD_Client_ID(ctx);
		final int ad_Org_ID = Env.getAD_Org_ID(ctx);

		return retrieveAcctSchema(ctx, ad_Client_ID, ad_Org_ID);
	}

	@Override
	public I_C_AcctSchema retrieveAcctSchemaById(final int acctSchemaId)
	{
		Check.assume(acctSchemaId > 0, "acctSchemaId > 0");

		// NOTE: we assume the C_AcctSchema is cached (see org.adempiere.acct.model.validator.AcctModuleInterceptor.setupCaching(IModelCacheService) )
		final I_C_AcctSchema acctSchema = InterfaceWrapperHelper.loadOutOfTrx(acctSchemaId, I_C_AcctSchema.class);
		Check.assumeNotNull(acctSchema, "Accounting schema shall exists for {}", acctSchemaId);

		return acctSchema;
	}

	@Override
	@Cached(cacheName = I_C_AcctSchema.Table_Name + "#By#" + "getC_AcctSchema_ID(?,?)", expireMinutes = Cached.EXPIREMINUTES_Never)
	public I_C_AcctSchema retrieveAcctSchema(final @CacheCtx Properties ctx, final int ad_Client_ID, final int ad_Org_ID)
	{
		final int acctSchemaId = DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT getC_AcctSchema_ID(?,?)", ad_Client_ID, ad_Org_ID);
		if (acctSchemaId <= -1)
		{
			throw new AccountingException(StringUtils.formatMessage("Found no C_AcctSchema_ID for AD_Client_ID={0} and AD_Org_ID={1}", ad_Client_ID, ad_Org_ID));
		}
		return InterfaceWrapperHelper.create(ctx, acctSchemaId, I_C_AcctSchema.class, ITrx.TRXNAME_None);
	}

	@Override
	@Cached(cacheName = I_C_AcctSchema.Table_Name + "#by#" + I_C_AcctSchema.COLUMNNAME_AD_Client_ID)
	public List<I_C_AcctSchema> retrieveClientAcctSchemas(@CacheCtx final Properties ctx, final int AD_Client_ID)
	{
		// services
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IClientDAO clientDAO = Services.get(IClientDAO.class);

		// Accounting schemas: C_AcctSchema_ID to C_AcctSchema
		final Map<Integer, I_C_AcctSchema> acctSchemas = new LinkedHashMap<>();

		//
		// Retrieve the primary accounting schema for our client
		final I_AD_ClientInfo info = clientDAO.retrieveClientInfo(ctx, AD_Client_ID);
		final I_C_AcctSchema acctSchemaPrimary = info.getC_AcctSchema1();
		if (acctSchemaPrimary != null && acctSchemaPrimary.getC_AcctSchema_ID() > 0)
		{
			acctSchemas.put(acctSchemaPrimary.getC_AcctSchema_ID(), acctSchemaPrimary);
		}

		//
		// Prepare query to retrieve all other accounting schemas that we have
		final IQuery<I_C_AcctSchema_GL> acctSchemaGLsQuery = queryBL.createQueryBuilder(I_C_AcctSchema_GL.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.create();
		final IQuery<I_C_AcctSchema_Default> acctSchemaDefaultsQuery = queryBL.createQueryBuilder(I_C_AcctSchema_Default.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.create();
		final IQueryBuilder<I_C_AcctSchema> acctSchemaQueryBuilder = queryBL.createQueryBuilder(I_C_AcctSchema.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(I_C_AcctSchema.COLUMN_C_AcctSchema_ID, I_C_AcctSchema_GL.COLUMN_C_AcctSchema_ID, acctSchemaGLsQuery)
				.addInSubQueryFilter(I_C_AcctSchema.COLUMN_C_AcctSchema_ID, I_C_AcctSchema_Default.COLUMN_C_AcctSchema_ID, acctSchemaDefaultsQuery);
		// Only for given AD_Client_ID, if specified.
		// If not specified, we will retrieve accounting schemas for ALL clients
		if (AD_Client_ID > 0)
		{
			acctSchemaQueryBuilder.addEqualsFilter(I_C_AcctSchema.COLUMN_AD_Client_ID, AD_Client_ID);
		}

		//
		// Retrieve all other accouting schemas that we have and add them to our map (to make sure they are uniquely fetched)
		for (final I_C_AcctSchema acctSchema : acctSchemaQueryBuilder.create().list())
		{
			acctSchemas.put(acctSchema.getC_AcctSchema_ID(), acctSchema);
		}

		return ImmutableList.copyOf(acctSchemas.values());
	}

	@Override
	public List<I_C_AcctSchema_Element> retrieveSchemaElements(final I_C_AcctSchema as)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(as);
		final String trxName = InterfaceWrapperHelper.getTrxName(as);
		final int acctSchemaId = as.getC_AcctSchema_ID();
		return retrieveSchemaElements(ctx, acctSchemaId, trxName);
	}

	@Cached(cacheName = I_C_AcctSchema_Element.Table_Name + "#By#" + I_C_AcctSchema_Element.COLUMNNAME_C_AcctSchema_ID, expireMinutes = Cached.EXPIREMINUTES_Never)
	List<I_C_AcctSchema_Element> retrieveSchemaElements(
			@CacheCtx final Properties ctx,
			final int acctSchemaId,
			@CacheTrx final String trxName)
	{
		final IQueryBuilder<I_C_AcctSchema_Element> queryBuilder = mkDefaultAcctSchemaElementQueryBuilder(ctx, acctSchemaId, trxName);

		final List<I_C_AcctSchema_Element> result = queryBuilder.create().list();

		// porting the check + the warning from the old code in MAcctSchemaElement.getAcctSchemaElements()
		final IAcctSchemaBL acctSchemaBL = Services.get(IAcctSchemaBL.class);
		for (final I_C_AcctSchema_Element ase : result)
		{
			if (ase.isMandatory() && acctSchemaBL.getDefaultValue(ase) == 0)
			{
				logger.error("No default value for " + ase.getName());
			}
		}

		return result;
	}

	@Override
	public List<I_C_AcctSchema_Element> retrieveSchemaElementsDisplayedInEditor(final I_C_AcctSchema as)
	{
		final List<I_C_AcctSchema_Element> result = new ArrayList<>();
		for (final I_C_AcctSchema_Element element : retrieveSchemaElements(as))
		{
			if (!element.isActive())
			{
				continue;
			}
			if (!element.isDisplayInEditor())
			{
				continue;
			}

			result.add(element);
		}

		return result;
	}

	@Override
	public I_C_AcctSchema_Element retrieveFirstAcctSchemaElementOrNull(final I_C_AcctSchema as, final String elementTypeToReturn)
	{
		Check.assumeNotNull(elementTypeToReturn, "elementTypeToReturn not null");

		for (final I_C_AcctSchema_Element element : retrieveSchemaElements(as))
		{
			final String elementType = element.getElementType();
			if (elementTypeToReturn.equals(elementType))
			{
				return element;
			}
		}

		// not found
		return null;
	}

	private final IQueryBuilder<I_C_AcctSchema_Element> mkDefaultAcctSchemaElementQueryBuilder(final Properties ctx, int acctSchemaId, final String trxName)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_C_AcctSchema_Element> queryBuilder = queryBL.createQueryBuilder(I_C_AcctSchema_Element.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_AcctSchema_Element.COLUMNNAME_C_AcctSchema_ID, acctSchemaId);

		queryBuilder.orderBy()
				.addColumn(I_C_AcctSchema_Element.COLUMNNAME_SeqNo) // NOTE: ordering by SeqNo first it's uber important! (07539)
				.addColumn(I_C_AcctSchema_Element.COLUMNNAME_C_AcctSchema_Element_ID);

		return queryBuilder;
	}

	@Override
	@Cached(cacheName = I_C_AcctSchema_GL.Table_Name)
	public I_C_AcctSchema_GL retrieveAcctSchemaGL(@CacheCtx final Properties ctx, final int acctSchemaId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_C_AcctSchema_GL.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_AcctSchema_GL.COLUMN_C_AcctSchema_ID, acctSchemaId)
				.create()
				.firstOnlyNotNull(I_C_AcctSchema_GL.class);
	}
}
