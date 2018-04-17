package de.metas.document.impl;

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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DocTypeNotFoundException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Sequence;
import org.compiere.model.I_C_DocBaseType_Counter;
import org.compiere.model.I_C_DocType;
import org.compiere.model.MDocType;
import org.compiere.model.MSequence;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableMap;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.adempiere.util.cache.annotations.CacheAllowMutable;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocumentBL;
import lombok.NonNull;

public class DocTypeDAO implements IDocTypeDAO
{
	@Override
	public I_C_DocType getById(final int docTypeId)
	{
		Check.assume(docTypeId > 0, "docTypeId > 0");

		// NOTE: we assume the C_DocType is cached on table level (i.e. see org.adempiere.model.validator.AdempiereBaseValidator.setupCaching(IModelCacheService))
		return InterfaceWrapperHelper.loadOutOfTrx(docTypeId, I_C_DocType.class);
	}

	@Override
	public int getDocTypeIdOrNull(final DocTypeQuery query)
	{
		return getDocTypeIdOrNull(Env.getCtx(), ITrx.TRXNAME_None, query);
	}

	@Cached(cacheName = I_C_DocType.Table_Name + "#by#query")
	public int getDocTypeIdOrNull(
			@CacheCtx final Properties ctx,
			@CacheTrx final String trxName,
			@CacheAllowMutable final DocTypeQuery query)
	{
		final int docTypeId = createDocTypeByBaseTypeQuery(ctx, trxName, query)
				.create()
				.firstId();

		if (docTypeId <= 0)
		{
			return -1;
		}

		return docTypeId;
	}

	@Override
	public int getDocTypeId(final Properties ctx, final String docBaseType, final int adClientId, final int adOrgId, final String trxName)
	{
		final DocTypeQuery query = DocTypeQuery.builder()
				.docBaseType(docBaseType)
				.docSubType(DocTypeQuery.DOCSUBTYPE_Any)
				.adClientId(adClientId)
				.adOrgId(adOrgId)
				.build();
		return getDocTypeIdOrNull(ctx, trxName, query);
	}

	@Override
	public int getDocTypeId(final DocTypeQuery query)
	{
		final int docTypeId = getDocTypeIdOrNull(Env.getCtx(), ITrx.TRXNAME_None, query);
		if (docTypeId <= 0)
		{
			throw new DocTypeNotFoundException(query);
		}
		return docTypeId;
	}

	@Override
	public int getDocTypeId(final Properties ctx, final String docBaseType, final String docSubType, final int adClientId, final int adOrgId, final String trxName)
	{
		final DocTypeQuery query = DocTypeQuery.builder()
				.docBaseType(docBaseType)
				.docSubType(docSubType)
				.adClientId(adClientId)
				.adOrgId(adOrgId)
				.build();
		final int docTypeId = getDocTypeIdOrNull(ctx, trxName, query);
		if (docTypeId <= 0)
		{
			final String additionalInfo = "@DocSubType@: " + docSubType
					+ ", @AD_Client_ID@: " + adClientId
					+ ", @AD_Org_ID@: " + adOrgId;
			throw new DocTypeNotFoundException(docBaseType, additionalInfo);
		}

		return docTypeId;
	}

	@Override
	public I_C_DocType getDocType(
			final String docBaseType,
			final String docSubType,
			final int adClientId,
			final int adOrgId)
	{
		final DocTypeQuery query = DocTypeQuery.builder()
				.docBaseType(docBaseType)
				.docSubType(docSubType)
				.adClientId(adClientId)
				.adOrgId(adOrgId)
				.build();

		final Optional<I_C_DocType> docType = retrieveDocType(query);
		return docType.orElseThrow(() -> new DocTypeNotFoundException(query));
	}

	@Override
	public Optional<I_C_DocType> retrieveDocType(@NonNull final DocTypeQuery docTypeQuery)
	{
		final I_C_DocType docTypeOrNull = //
				createDocTypeByBaseTypeQuery(Env.getCtx(), ITrx.TRXNAME_None, docTypeQuery)
						.create()
						.first(I_C_DocType.class);

		return Optional.ofNullable(docTypeOrNull);
	}

	@Override
	public boolean queryMatchesDocTypeId(
			@NonNull final DocTypeQuery docTypeQuery,
			@NonNull final Object documentModel)
	{
		final Integer docTypeId = InterfaceWrapperHelper.getValueOrNull(docTypeQuery, IDocumentBL.COLUMNNAME_C_DocType_ID);
		if (docTypeId == null)
		{
			return false;
		}

		final boolean queryMatchesDocTypeId = createDocTypeByBaseTypeQuery(Env.getCtx(), ITrx.TRXNAME_None, docTypeQuery)
				.addEqualsFilter(I_C_DocType.COLUMN_C_DocType_ID, docTypeId)
				.create()
				.match();
		return queryMatchesDocTypeId;
	}

	private IQueryBuilder<I_C_DocType> createDocTypeByBaseTypeQuery(
			final Properties ctx,
			final String trxName,
			@NonNull final DocTypeQuery query)
	{
		final IQueryBuilder<I_C_DocType> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_DocType.class, ctx, trxName);

		final ICompositeQueryFilter<I_C_DocType> filters = queryBuilder.getCompositeFilter();
		filters.addOnlyActiveRecordsFilter();
		filters.addEqualsFilter(I_C_DocType.COLUMN_AD_Client_ID, query.getAdClientId());
		filters.addInArrayOrAllFilter(I_C_DocType.COLUMN_AD_Org_ID, 0, query.getAdOrgId());
		filters.addEqualsFilter(I_C_DocType.COLUMN_DocBaseType, query.getDocBaseType());

		final String docSubType = query.getDocSubType();
		if (docSubType == DocTypeQuery.DOCSUBTYPE_NONE)
		{
			filters.addEqualsFilter(I_C_DocType.COLUMN_DocSubType, null);
		}
		else if (docSubType != DocTypeQuery.DOCSUBTYPE_Any)
		{
			filters.addEqualsFilter(I_C_DocType.COLUMN_DocSubType, docSubType);
		}

		if (query.getIsSOTrx() != null)
		{
			filters.addEqualsFilter(I_C_DocType.COLUMN_IsSOTrx, query.getIsSOTrx());
		}

		queryBuilder.orderBy()
				.addColumn(I_C_DocType.COLUMNNAME_IsDefault, Direction.Descending, Nulls.Last)
				.addColumn(I_C_DocType.COLUMNNAME_AD_Org_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_C_DocType.COLUMNNAME_DocSubType, Direction.Ascending, Nulls.First);

		return queryBuilder;
	}

	@Override
	public List<I_C_DocType> retrieveDocTypesByBaseType(final DocTypeQuery query)
	{
		return createDocTypeByBaseTypeQuery(Env.getCtx(), ITrx.TRXNAME_None, query)
				.create()
				.list(I_C_DocType.class);
	}

	@Override
	public String retrieveDocBaseTypeCounter(final Properties ctx, final String docBaseType)
	{
		final Map<String, String> docBaseTypePairs = retrieveDocBaseTypeCountersMap(ctx);

		return docBaseTypePairs.get(docBaseType);
	}

	@Cached(cacheName = I_C_DocBaseType_Counter.Table_Name)
	public Map<String, String> retrieveDocBaseTypeCountersMap(@CacheCtx final Properties ctx)
	{
		// load the existing info from the table C_DocBaseType_Counter in an immutable map
		ImmutableMap.Builder<String, String> docBaseTypeCounters = ImmutableMap.builder();

		final IQueryBuilder<I_C_DocBaseType_Counter> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_C_DocBaseType_Counter.class, ctx, ITrx.TRXNAME_None);

		queryBuilder.addOnlyActiveRecordsFilter()
				.addOnlyContextClient();

		final List<I_C_DocBaseType_Counter> docBaseTypeCountersList = queryBuilder.create().list();

		for (final I_C_DocBaseType_Counter docBaseTypeCounter : docBaseTypeCountersList)
		{
			docBaseTypeCounters.put(docBaseTypeCounter.getDocBaseType(), docBaseTypeCounter.getCounter_DocBaseType());
		}

		return docBaseTypeCounters.build();
	}

	@Override
	public I_C_DocType createDocType(final DocTypeCreateRequest request)
	{
		final Properties ctx = request.getCtx();
		final String trxName = ITrx.TRXNAME_ThreadInherited;
		final String name = request.getName();

		final int docNoSequenceId;
		if (request.getDocNoSequenceId() > 0)
		{
			docNoSequenceId = request.getDocNoSequenceId();
		}
		else if (request.getNewDocNoSequenceStartNo() > 0)
		{
			final I_AD_Sequence sequence = new MSequence(ctx, Env.getAD_Client_ID(ctx), name, request.getNewDocNoSequenceStartNo(), trxName);
			InterfaceWrapperHelper.save(sequence);
			docNoSequenceId = sequence.getAD_Sequence_ID();
		}
		else
		{
			docNoSequenceId = -1;
		}

		final MDocType dt = new MDocType(ctx, request.getDocBaseType(), name, trxName);
		dt.setEntityType(request.getEntityType());
		if (request.getAdOrgId() > 0)
		{
			dt.setAD_Org_ID(request.getAdOrgId());
		}
		if (request.getPrintName() != null && request.getPrintName().length() > 0)
		{
			dt.setPrintName(request.getPrintName()); // Defaults to Name
		}
		if (request.getDocSubType() != null)
		{
			dt.setDocSubType(request.getDocSubType());
		}
		if (request.getDocTypeShipmentId() > 0)
		{
			dt.setC_DocTypeShipment_ID(request.getDocTypeShipmentId());
		}
		if (request.getDocTypeInvoiceId() > 0)
		{
			dt.setC_DocTypeInvoice_ID(request.getDocTypeInvoiceId());
		}
		if (request.getGlCategoryId() > 0)
		{
			dt.setGL_Category_ID(request.getGlCategoryId());
		}

		if (docNoSequenceId <= 0)
		{
			dt.setIsDocNoControlled(false);
		}
		else
		{
			dt.setIsDocNoControlled(true);
			dt.setDocNoSequence_ID(docNoSequenceId);
		}

		if (request.getDocumentCopies() > 0)
		{
			dt.setDocumentCopies(request.getDocumentCopies());
		}

		if (request.getIsSOTrx() != null)
		{
			dt.setIsSOTrx(request.getIsSOTrx());
		}
		else
		{
			dt.setIsSOTrx();
		}

		InterfaceWrapperHelper.save(dt);
		return dt;
	}
}
