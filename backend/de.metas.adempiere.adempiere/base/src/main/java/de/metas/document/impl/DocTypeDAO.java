package de.metas.document.impl;

import com.google.common.collect.ImmutableMap;
<<<<<<< HEAD
import de.metas.cache.CCache;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeBL;
import de.metas.document.IDocTypeDAO;
=======
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.invoicingpool.DocTypeInvoicingPoolId;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
<<<<<<< HEAD
import org.adempiere.exceptions.DocTypeNotFoundException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_Sequence;
import org.compiere.model.I_C_DocBaseType_Counter;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_GL_Category;
import org.compiere.model.MSequence;
import org.compiere.util.Env;

=======
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DocTypeNotFoundException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Sequence;
import org.compiere.model.I_C_DocBaseType_Counter;
import org.compiere.model.I_C_DocType;
import org.compiere.model.MSequence;
import org.compiere.util.Env;

import javax.annotation.Nullable;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

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

public class DocTypeDAO implements IDocTypeDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

<<<<<<< HEAD
	private CCache<DocTypeQuery, Optional<DocTypeId>> docTypeIdsByQuery = CCache.<DocTypeQuery, Optional<DocTypeId>>builder()
			.tableName(I_C_DocType.Table_Name)
			.build();

	private CCache<Integer, DocBaseTypeCountersMap> docBaseTypeCountersMapCache = CCache.<Integer, DocBaseTypeCountersMap>builder()
=======
	private final CCache<DocTypeQuery, Optional<DocTypeId>> docTypeIdsByQuery = CCache.<DocTypeQuery, Optional<DocTypeId>>builder()
			.tableName(I_C_DocType.Table_Name)
			.build();

	private final CCache<DocTypeInvoicingPoolId, ImmutableSet<DocTypeId>> docTypeIdsByInvoicingPoolId = CCache.<DocTypeInvoicingPoolId, ImmutableSet<DocTypeId>>builder()
			.tableName(I_C_DocType.Table_Name)
			.build();

	private final CCache<Integer, DocBaseTypeCountersMap> docBaseTypeCountersMapCache = CCache.<Integer, DocBaseTypeCountersMap>builder()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			.tableName(I_C_DocBaseType_Counter.Table_Name)
			.build();

	@Override
<<<<<<< HEAD
=======
	@NonNull
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	public I_C_DocType getById(final int docTypeId)
	{
		return getById(DocTypeId.ofRepoId(docTypeId));
	}

	@Override
<<<<<<< HEAD
	public I_C_DocType getById(@NonNull final DocTypeId docTypeId)
	{
		// NOTE: we assume the C_DocType is cached on table level (i.e. see org.adempiere.model.validator.AdempiereBaseValidator.setupCaching(IModelCacheService))
		return InterfaceWrapperHelper.loadOutOfTrx(docTypeId, I_C_DocType.class);
	}

	@Override
=======
	@NonNull
	public I_C_DocType getById(@NonNull final DocTypeId docTypeId)
	{
		// NOTE: we assume the C_DocType is cached on table level (i.e. see org.adempiere.model.validator.AdempiereBaseValidator.setupCaching(IModelCacheService))
		final I_C_DocType docTypeRecord = InterfaceWrapperHelper.loadOutOfTrx(docTypeId, I_C_DocType.class);
		
		if (docTypeRecord == null)
		{
			throw new AdempiereException("No C_DocType record found for ID!")
					.appendParametersToMessage()
					.setParameter("DocTypeId", docTypeId);
		}
		
		return docTypeRecord;
	}

	@Override
	@NonNull
	public I_C_DocType getByIdInTrx(@NonNull final DocTypeId docTypeId)
	{
		final I_C_DocType docTypeRecord = InterfaceWrapperHelper.load(docTypeId, I_C_DocType.class);

		if (docTypeRecord == null)
		{
			throw new AdempiereException("No C_DocType record found for ID!")
					.appendParametersToMessage()
					.setParameter("DocTypeId", docTypeId);
		}

		return docTypeRecord;
	}

	@Override
	@Nullable
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	public DocTypeId getDocTypeIdOrNull(@NonNull final DocTypeQuery query)
	{
		return docTypeIdsByQuery.getOrLoad(query, this::retrieveDocTypeIdByQuery)
				.orElse(null);
	}

<<<<<<< HEAD
=======
	@Override
	@NonNull
	public ImmutableSet<DocTypeId> getDocTypeIdsByInvoicingPoolId(@NonNull final DocTypeInvoicingPoolId docTypeInvoicingPoolId)
	{
		return docTypeIdsByInvoicingPoolId.getOrLoad(docTypeInvoicingPoolId, this::retrieveDocTypeIdsByInvoicingPoolId);
	}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	private Optional<DocTypeId> retrieveDocTypeIdByQuery(@NonNull final DocTypeQuery query)
	{
		return Optional.ofNullable(
				createDocTypeByBaseTypeQuery(query)
						.create()
						.firstId(DocTypeId::ofRepoIdOrNull));
	}

	@Override
	@NonNull
	public DocTypeId getDocTypeId(@NonNull final DocTypeQuery query)
	{
		final DocTypeId docTypeId = getDocTypeIdOrNull(query);
		if (docTypeId == null)
		{
			throw new DocTypeNotFoundException(query);
		}
		return docTypeId;
	}

	@Override
	public Optional<I_C_DocType> retrieveDocType(@NonNull final DocTypeQuery docTypeQuery)
	{
		return Optional.ofNullable(
				createDocTypeByBaseTypeQuery(docTypeQuery)
						.create()
						.first(I_C_DocType.class));

	}

	@Override
	public boolean queryMatchesDocTypeId(
			@NonNull final DocTypeQuery docTypeQuery,
			final int docTypeId)
	{
<<<<<<< HEAD
		final boolean queryMatchesDocTypeId = createDocTypeByBaseTypeQuery(docTypeQuery)
				.addEqualsFilter(I_C_DocType.COLUMN_C_DocType_ID, docTypeId)
				.create()
				.anyMatch();
		return queryMatchesDocTypeId;
=======
		return createDocTypeByBaseTypeQuery(docTypeQuery)
				.addEqualsFilter(I_C_DocType.COLUMN_C_DocType_ID, docTypeId)
				.create()
				.anyMatch();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	private IQueryBuilder<I_C_DocType> createDocTypeByBaseTypeQuery(@NonNull final DocTypeQuery query)
	{
		final IQueryBuilder<I_C_DocType> queryBuilder = queryBL.createQueryBuilderOutOfTrx(I_C_DocType.class);

		final ICompositeQueryFilter<I_C_DocType> filters = queryBuilder.getCompositeFilter();
		filters.addOnlyActiveRecordsFilter();
		filters.addEqualsFilter(I_C_DocType.COLUMNNAME_AD_Client_ID, query.getAdClientId());
		filters.addInArrayOrAllFilter(I_C_DocType.COLUMNNAME_AD_Org_ID, 0, query.getAdOrgId());
<<<<<<< HEAD
		filters.addEqualsFilter(I_C_DocType.COLUMN_DocBaseType, query.getDocBaseType());

		final String docSubType = query.getDocSubType();
		if (docSubType == DocTypeQuery.DOCSUBTYPE_NONE)
		{
			filters.addEqualsFilter(I_C_DocType.COLUMN_DocSubType, null);
		}
		else if (docSubType != DocTypeQuery.DOCSUBTYPE_Any)
		{
			filters.addEqualsFilter(I_C_DocType.COLUMN_DocSubType, docSubType);
=======
		filters.addEqualsFilter(I_C_DocType.COLUMNNAME_DocBaseType, query.getDocBaseType());

		final DocSubType docSubType = query.getDocSubType();
		if (docSubType.isNone())
		{
			filters.addEqualsFilter(I_C_DocType.COLUMNNAME_DocSubType, null);
		}
		else if (!docSubType.isAny())
		{
			filters.addEqualsFilter(I_C_DocType.COLUMNNAME_DocSubType, docSubType);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}

		if (query.getIsSOTrx() != null)
		{
<<<<<<< HEAD
			filters.addEqualsFilter(I_C_DocType.COLUMN_IsSOTrx, query.getIsSOTrx());
=======
			filters.addEqualsFilter(I_C_DocType.COLUMNNAME_IsSOTrx, query.getIsSOTrx());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}

		if (query.getDefaultDocType() != null)
		{
<<<<<<< HEAD
			filters.addEqualsFilter(I_C_DocType.COLUMN_IsDefault, query.getDefaultDocType());
=======
			filters.addEqualsFilter(I_C_DocType.COLUMNNAME_IsDefault, query.getDefaultDocType());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}

		if (!Check.isEmpty(query.getName(), true))
		{
<<<<<<< HEAD
			filters.addEqualsFilter(I_C_DocType.COLUMN_Name, query.getName());
=======
			filters.addEqualsFilter(I_C_DocType.COLUMNNAME_Name, query.getName());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
		return createDocTypeByBaseTypeQuery(query)
				.create()
				.list(I_C_DocType.class);
	}

	@Override
<<<<<<< HEAD
	public Optional<String> getDocBaseTypeCounter(final String docBaseType)
=======
	public Optional<DocBaseType> getDocBaseTypeCounter(final DocBaseType docBaseType)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		final DocBaseTypeCountersMap map = this.docBaseTypeCountersMapCache.getOrLoad(0, this::retrieveDocBaseTypeCountersMap);
		return map.getCounterDocBaseTypeByDocBaseType(docBaseType);
	}

	// @Cached(cacheName = I_C_DocBaseType_Counter.Table_Name)
	private DocBaseTypeCountersMap retrieveDocBaseTypeCountersMap()
	{
		// load the existing info from the table C_DocBaseType_Counter in an immutable map
<<<<<<< HEAD
		ImmutableMap.Builder<String, String> docBaseTypeCounters = ImmutableMap.builder();
=======
		final ImmutableMap.Builder<DocBaseType, DocBaseType> docBaseTypeCounters = ImmutableMap.builder();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		final IQueryBuilder<I_C_DocBaseType_Counter> queryBuilder = queryBL.createQueryBuilderOutOfTrx(I_C_DocBaseType_Counter.class);

		queryBuilder.addOnlyActiveRecordsFilter()
				.addOnlyContextClient();

		final List<I_C_DocBaseType_Counter> docBaseTypeCountersList = queryBuilder.create().list();

		for (final I_C_DocBaseType_Counter docBaseTypeCounter : docBaseTypeCountersList)
		{
<<<<<<< HEAD
			docBaseTypeCounters.put(docBaseTypeCounter.getDocBaseType(), docBaseTypeCounter.getCounter_DocBaseType());
=======
			docBaseTypeCounters.put(DocBaseType.ofCode(docBaseTypeCounter.getDocBaseType()), DocBaseType.ofCode(docBaseTypeCounter.getCounter_DocBaseType()));
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}

		return DocBaseTypeCountersMap.ofMap(docBaseTypeCounters.build());
	}

	@Override
	public DocTypeId createDocType(final DocTypeCreateRequest request)
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

		final I_C_DocType dt = newInstance(I_C_DocType.class);
		dt.setAD_Org_ID(0);
<<<<<<< HEAD
		dt.setDocBaseType(request.getDocBaseType());
		dt.setName(name);
		dt.setPrintName(name);
		dt.setGL_Category_ID(retrieveDefaultGL_Category_ID());
=======
		dt.setDocBaseType(request.getDocBaseType().getCode());
		dt.setName(name);
		dt.setPrintName(name);
		dt.setGL_Category_ID(request.getGlCategoryId().getRepoId());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		//		final MDocType dt = new MDocType(ctx, request.getDocBaseType(), name, trxName);
		dt.setEntityType(request.getEntityType());
		if (request.getAdOrgId() > 0)
		{
			dt.setAD_Org_ID(request.getAdOrgId());
		}
<<<<<<< HEAD
		if (request.getPrintName() != null && request.getPrintName().length() > 0)
		{
			dt.setPrintName(request.getPrintName()); // Defaults to Name
		}
		if (request.getDocSubType() != null)
		{
			dt.setDocSubType(request.getDocSubType());
=======
		if (request.getPrintName() != null && !request.getPrintName().isEmpty())
		{
			dt.setPrintName(request.getPrintName()); // Defaults to Name
		}
		if (!request.getDocSubType().isAnyOrNone())
		{
			dt.setDocSubType(request.getDocSubType().getCode());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
		if (request.getDocTypeShipmentId() > 0)
		{
			dt.setC_DocTypeShipment_ID(request.getDocTypeShipmentId());
		}
		if (request.getDocTypeInvoiceId() > 0)
		{
			dt.setC_DocTypeInvoice_ID(request.getDocTypeInvoiceId());
		}
<<<<<<< HEAD
		if (request.getGlCategoryId() > 0)
		{
			dt.setGL_Category_ID(request.getGlCategoryId());
		}
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
			final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
			final boolean isSOTrx = docTypeBL.isSOTrx(request.getDocBaseType());
			dt.setIsSOTrx(isSOTrx);
=======
			dt.setIsSOTrx(request.getDocBaseType().isSOTrx());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}

		InterfaceWrapperHelper.save(dt);
		return DocTypeId.ofRepoId(dt.getC_DocType_ID());
	}
<<<<<<< HEAD

	/**
	 * Set Default GL Category
	 */
	private int retrieveDefaultGL_Category_ID()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_GL_Category.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_GL_Category.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH.getRepoId())
				.orderByDescending(I_GL_Category.COLUMNNAME_IsDefault)
				.orderBy(I_GL_Category.COLUMNNAME_GL_Category_ID)
				.create()
				.firstId();
=======
	
	@Override
	public void save(@NonNull final I_C_DocType docTypeRecord)
	{
		InterfaceWrapperHelper.saveRecord(docTypeRecord);
	}

	@Override
	public DocBaseType getDocBaseTypeById(@NonNull final DocTypeId docTypeId)
	{
		final I_C_DocType docTypeRecord = getById(docTypeId);
		return DocBaseType.ofCode(docTypeRecord.getDocBaseType());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public DocBaseAndSubType getDocBaseAndSubTypeById(@NonNull final DocTypeId docTypeId)
	{
		final I_C_DocType docTypeRecord = getById(docTypeId);
		return DocBaseAndSubType.of(docTypeRecord.getDocBaseType(), docTypeRecord.getDocSubType());
	}

	@EqualsAndHashCode
	@ToString
	private static class DocBaseTypeCountersMap
	{
<<<<<<< HEAD
		public static DocBaseTypeCountersMap ofMap(@NonNull final ImmutableMap<String, String> map)
=======
		public static DocBaseTypeCountersMap ofMap(@NonNull final ImmutableMap<DocBaseType, DocBaseType> map)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			return !map.isEmpty()
					? new DocBaseTypeCountersMap(map)
					: EMPTY;
		}

		private static final DocBaseTypeCountersMap EMPTY = new DocBaseTypeCountersMap(ImmutableMap.of());

<<<<<<< HEAD
		private ImmutableMap<String, String> counterDocBaseTypeByDocBaseType;

		private DocBaseTypeCountersMap(@NonNull final ImmutableMap<String, String> map)
=======
		private final ImmutableMap<DocBaseType, DocBaseType> counterDocBaseTypeByDocBaseType;

		private DocBaseTypeCountersMap(@NonNull final ImmutableMap<DocBaseType, DocBaseType> map)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			this.counterDocBaseTypeByDocBaseType = map;
		}

<<<<<<< HEAD
		public Optional<String> getCounterDocBaseTypeByDocBaseType(@NonNull final String docBaseType)
=======
		public Optional<DocBaseType> getCounterDocBaseTypeByDocBaseType(@NonNull final DocBaseType docBaseType)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			return Optional.ofNullable(counterDocBaseTypeByDocBaseType.get(docBaseType));
		}
	}
<<<<<<< HEAD
=======

	@NonNull
	private ImmutableSet<DocTypeId> retrieveDocTypeIdsByInvoicingPoolId(@NonNull final DocTypeInvoicingPoolId docTypeInvoicingPoolId)
	{
		return queryBL.createQueryBuilder(I_C_DocType.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_DocType.COLUMNNAME_C_DocType_Invoicing_Pool_ID, docTypeInvoicingPoolId)
				.create()
				.listIds(DocTypeId::ofRepoId);
	}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
