package de.metas.acct.vatcode.impl;

import com.google.common.base.Joiner;
import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.model.I_C_VAT_Code;
import de.metas.acct.model.X_C_VAT_Code;
import de.metas.acct.vatcode.CreateVATCodeRequest;
import de.metas.acct.vatcode.IVATCodeDAO;
import de.metas.acct.vatcode.VATCode;
import de.metas.acct.vatcode.VATCodeMatchingRequest;
import de.metas.cache.annotation.CacheCtx;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.VatCodeId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

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
	private static final Logger logger = LogManager.getLogger(VATCodeDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	@NonNull
	public Optional<VATCode> findVATCode(@NonNull final VATCodeMatchingRequest request)
	{
		final Properties ctx = Env.getCtx();

		final List<I_C_VAT_Code> matchings = retriveVATCodeMatchingsForSchema(ctx, request.getC_AcctSchema_ID());

		if (logger.isDebugEnabled())
		{
			logger.debug("Request={}", request);
			logger.debug("Rules:\n{}", Joiner.on("\n").join(matchings));
		}

		for (final I_C_VAT_Code matching : matchings)
		{
			if (isMatching(matching, request))
			{
				return Optional.of(VATCode.of(matching.getVATCode(), matching.getC_VAT_Code_ID()));
			}
		}

		logger.debug("Nothing matched. Returning NULL");
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
				.firstIdOnlyOptional(VatCodeId::ofRepoIdOrNull)
				.orElseThrow(() -> new AdempiereException("No C_VAT_Code found for code & org")
						.appendParametersToMessage()
						.setParameter("OrgId", orgId.getRepoId())
						.setParameter("code", code));
	}

	@Override
	public boolean existsForAcctSchemaAndTax(@NonNull final AcctSchemaId acctSchemaId, @NonNull final TaxId taxId)
	{
		return queryBL.createQueryBuilder(I_C_VAT_Code.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_VAT_Code.COLUMNNAME_C_AcctSchema_ID, acctSchemaId)
				.addEqualsFilter(I_C_VAT_Code.COLUMNNAME_C_Tax_ID, taxId)
				.create()
				.anyMatch();
	}

	@Override
	@NonNull
	public VATCode createVATCode(@NonNull final CreateVATCodeRequest request)
	{
		final I_C_VAT_Code record = InterfaceWrapperHelper.newInstance(I_C_VAT_Code.class);
		record.setC_AcctSchema_ID(request.getAcctSchemaId().getRepoId());
		record.setC_Tax_ID(request.getTaxId().getRepoId());
		record.setVATCode(request.getVatCode());
		if (request.getOrgId() != null)
		{
			record.setAD_Org_ID(request.getOrgId().getRepoId());
		}
		if (request.getIsSOTrx() != null)
		{
			record.setIsSOTrx(request.getIsSOTrx() ? X_C_VAT_Code.ISSOTRX_Yes : X_C_VAT_Code.ISSOTRX_No);
		}
		if (request.getValidFrom() != null)
		{
			record.setValidFrom(TimeUtil.asTimestamp(request.getValidFrom()));
		}
		if (request.getValidTo() != null)
		{
			record.setValidTo(TimeUtil.asTimestamp(request.getValidTo()));
		}
		if (request.getDescription() != null)
		{
			record.setDescription(request.getDescription());
		}
		InterfaceWrapperHelper.saveRecord(record);
		return VATCode.of(record.getVATCode(), record.getC_VAT_Code_ID());
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

		// Match AmountType (when specified in request)
		if (request.getAmountType() != null)
		{
			final String matchingAmountType = matching.getAmountType();
			// null matchingAmountType: record has no AmountType set (only in legacy/test data;
			// production column is NOT NULL DEFAULT 'T'). A null record cannot satisfy a typed request.
			if (!request.getAmountType().getCode().equals(matchingAmountType))
			{
				logger.debug("=> not matching (AmountType)");
				return false;
			}
		}

		logger.debug("=> matching");
		return true;
	}

	/**
	 * Retrieves all active {@link I_C_VAT_Code}s for given C_AcctSchema_ID.
	 *
	 * @param acctSchemaId C_AcctSchema_ID
	 */
	@Cached(cacheName = I_C_VAT_Code.Table_Name + "#by#" + I_C_VAT_Code.COLUMNNAME_C_AcctSchema_ID)
	public List<I_C_VAT_Code> retriveVATCodeMatchingsForSchema(@CacheCtx final Properties ctx, final int acctSchemaId)
	{
		return queryBL
				.createQueryBuilder(I_C_VAT_Code.class, ctx, ITrx.TRXNAME_ThreadInherited)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_VAT_Code.COLUMN_C_AcctSchema_ID, acctSchemaId)
				//
				.orderBy()
				.addColumn(I_C_VAT_Code.COLUMNNAME_C_Tax_ID)
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
