package de.metas.acct.impl;

import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import com.google.common.base.Joiner;

import de.metas.acct.IVATCodeDAO;
import de.metas.acct.VATCode;
import de.metas.acct.VATCodeMatchingRequest;
import de.metas.acct.model.I_C_VAT_Code;
import de.metas.adempiere.util.CacheCtx;

/*
 * #%L
 * de.metas.acct.base
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

public class VATCodeDAO implements IVATCodeDAO
{
	private static final transient Logger logger = LogManager.getLogger(VATCodeDAO.class);

	@Override
	public VATCode findVATCode(final VATCodeMatchingRequest request)
	{
		Check.assumeNotNull(request, "request not null");
		final Properties ctx = Env.getCtx();

		final List<I_C_VAT_Code> matchings = retriveVATCodeMatchingsForSchema(ctx, request.getC_AcctSchema_ID());
		
		if (logger.isDebugEnabled())
		{
			logger.debug("Request: {}" + request);
			logger.debug("Rules:\n" + Joiner.on("\n").join(matchings));
		}

		for (final I_C_VAT_Code matching : matchings)
		{
			if (isMatching(matching, request))
			{
				return VATCode.of(matching.getVATCode());
			}
		}

		logger.debug("Nothing matched. Returning NULL");
		return VATCode.NULL;
	}

	/**
	 *
	 * @param matching
	 * @param request
	 * @return true if the given {@link I_C_VAT_Code} is matching our request.
	 */
	private final boolean isMatching(final I_C_VAT_Code matching, final VATCodeMatchingRequest request)
	{
		logger.debug("Matching: {}", matching);
		logger.debug("Request: {}", request);
		// Match accounting schema
		if (matching.getC_AcctSchema_ID() != request.getC_AcctSchema_ID())
		{
			logger.debug("=> not matching (C_AcctSchema_ID)");
			return false;
		}

		// Match tax
		if (matching.getC_Tax_ID() != request.getC_Tax_ID())
		{
			logger.debug("=> not matching (C_Tax_ID)");
			return false;
		}

		// Match IsSOTrx
		final String matchingIsSOTrxStr = matching.getIsSOTrx();
		final Boolean matchingIsSOTrx = matchingIsSOTrxStr == null ? null : DisplayType.toBoolean(matchingIsSOTrxStr);
		if (matchingIsSOTrx != null && matchingIsSOTrx != request.isSOTrx())
		{
			logger.debug("=> not matching (IsSOTrx)");
			return false;
		}

		// Match Date
		if (!TimeUtil.isBetween(request.getDate(), matching.getValidFrom(), matching.getValidTo()))
		{
			logger.debug("=> not matching (Date)");
			return false;
		}

		logger.debug("=> matching");
		return true;
	}

	/**
	 * Retries all active {@link I_C_VAT_Code}s for given C_AcctSchema_ID.
	 *
	 * @param ctx
	 * @param acctSchemaId C_AcctSchema_ID
	 */
	@Cached(cacheName = I_C_VAT_Code.Table_Name + "#by#" + I_C_VAT_Code.COLUMNNAME_C_AcctSchema_ID)
	public List<I_C_VAT_Code> retriveVATCodeMatchingsForSchema(@CacheCtx final Properties ctx, final int acctSchemaId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_VAT_Code.class, ctx, ITrx.TRXNAME_ThreadInherited)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_VAT_Code.COLUMN_C_AcctSchema_ID, acctSchemaId)
				//
				.orderBy()
				.addColumn(I_C_VAT_Code.COLUMN_C_Tax_ID)
				.addColumn(I_C_VAT_Code.COLUMN_ValidFrom, Direction.Descending, Nulls.Last)
				.addColumn(I_C_VAT_Code.COLUMN_ValidTo, Direction.Descending, Nulls.Last)
				.addColumn(I_C_VAT_Code.COLUMN_IsSOTrx, Direction.Ascending, Nulls.Last)
				.addColumn(I_C_VAT_Code.COLUMN_C_VAT_Code_ID)
				.endOrderBy()
				//
				.create()
				.list();
	}
}
