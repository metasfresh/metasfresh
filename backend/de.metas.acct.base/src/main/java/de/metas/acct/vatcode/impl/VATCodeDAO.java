package de.metas.acct.vatcode.impl;

<<<<<<< HEAD
import java.util.List;
import java.util.Properties;

import lombok.NonNull;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.proxy.Cached;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import com.google.common.base.Joiner;

=======
import com.google.common.base.Joiner;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.acct.model.I_C_VAT_Code;
import de.metas.acct.vatcode.IVATCodeDAO;
import de.metas.acct.vatcode.VATCode;
import de.metas.acct.vatcode.VATCodeMatchingRequest;
import de.metas.cache.annotation.CacheCtx;
<<<<<<< HEAD
=======
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.tax.api.VatCodeId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.proxy.Cached;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
	private static final transient Logger logger = LogManager.getLogger(VATCodeDAO.class);

	@Override
	public VATCode findVATCode(@NonNull final VATCodeMatchingRequest request)
=======
	private static final Logger logger = LogManager.getLogger(VATCodeDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	@NonNull
	public Optional<VATCode> findVATCode(@NonNull final VATCodeMatchingRequest request)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		final Properties ctx = Env.getCtx();

		final List<I_C_VAT_Code> matchings = retriveVATCodeMatchingsForSchema(ctx, request.getC_AcctSchema_ID());
<<<<<<< HEAD
		
=======

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		if (logger.isDebugEnabled())
		{
			logger.debug("Request={}", request);
			logger.debug("Rules:\n{}", Joiner.on("\n").join(matchings));
		}

		for (final I_C_VAT_Code matching : matchings)
		{
			if (isMatching(matching, request))
			{
<<<<<<< HEAD
				return VATCode.of(matching.getVATCode());
=======
				return Optional.of(VATCode.of(matching.getVATCode(), matching.getC_VAT_Code_ID()));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			}
		}

		logger.debug("Nothing matched. Returning NULL");
<<<<<<< HEAD
		return VATCode.NULL;
=======
		return Optional.empty();
	}

	@Override
	@NonNull
	public VatCodeId getIdByCodeAndOrgId(
			@NonNull final String code,
			@NonNull final OrgId orgId)
	{
		return queryBL.createQueryBuilder(I_C_VAT_Code.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_VAT_Code.COLUMNNAME_VATCode, code)
				.addInArrayFilter(I_C_VAT_Code.COLUMNNAME_AD_Org_ID, orgId, OrgId.ANY)
				.create()
				.firstIdOnlyOptional(VatCodeId::ofRepoId)
				.orElseThrow(() -> new AdempiereException("No C_VAT_Code found for code & org")
						.appendParametersToMessage()
						.setParameter("OrgId", orgId.getRepoId())
						.setParameter("code", code));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	/**
	 * @return true if the given {@link I_C_VAT_Code} is matching our request.
	 */
	private boolean isMatching(final I_C_VAT_Code matching, final VATCodeMatchingRequest request)
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
	 * @param acctSchemaId C_AcctSchema_ID
	 */
	@Cached(cacheName = I_C_VAT_Code.Table_Name + "#by#" + I_C_VAT_Code.COLUMNNAME_C_AcctSchema_ID)
	public List<I_C_VAT_Code> retriveVATCodeMatchingsForSchema(@CacheCtx final Properties ctx, final int acctSchemaId)
	{
<<<<<<< HEAD
		return Services.get(IQueryBL.class)
=======
		return queryBL
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				.createQueryBuilder(I_C_VAT_Code.class, ctx, ITrx.TRXNAME_ThreadInherited)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_VAT_Code.COLUMN_C_AcctSchema_ID, acctSchemaId)
				//
				.orderBy()
<<<<<<< HEAD
				.addColumn(I_C_VAT_Code.COLUMN_C_Tax_ID)
=======
				.addColumn(I_C_VAT_Code.COLUMNNAME_C_Tax_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
