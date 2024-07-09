package de.metas.dunning.api.impl;

/*
 * #%L
 * de.metas.dunning
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

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.cache.CCache;
import de.metas.cache.CCache.CacheMapType;
import de.metas.cache.annotation.CacheCtx;
import de.metas.cache.annotation.CacheTrx;
import de.metas.dunning.DunningDocId;
import de.metas.dunning.DunningLevel;
import de.metas.dunning.DunningLevelId;
import de.metas.dunning.api.IDunningCandidateQuery;
import de.metas.dunning.api.IDunningCandidateQuery.ApplyAccessFilter;
import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.api.IDunningDAO;
import de.metas.dunning.interfaces.I_C_Dunning;
import de.metas.dunning.interfaces.I_C_DunningLevel;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.dunning.model.I_C_DunningDoc_Line;
import de.metas.dunning.model.I_C_DunningDoc_Line_Source;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_BPartner;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.load;

public abstract class AbstractDunningDAO implements IDunningDAO
{

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public final I_C_Dunning retrieveDunningForBPartner(@NonNull final BPartnerId bpartnerId)
	{
		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
		final I_C_BPartner bpartner = bpartnerDAO.getById(bpartnerId);

		I_C_Dunning dunning = InterfaceWrapperHelper.create(bpartner.getC_Dunning(), I_C_Dunning.class);
		if (dunning != null)
		{
			return dunning;
		}

		dunning = InterfaceWrapperHelper.create(bpartner.getC_BP_Group().getC_Dunning(), I_C_Dunning.class);
		return dunning;
	}

	private final transient CCache<OrgId, I_C_Dunning> orgId2dunning = CCache.<OrgId, I_C_Dunning>builder().tableName(I_C_Dunning.Table_Name).cacheMapType(CacheMapType.LRU).initialCapacity(100).build();
	private final transient CCache<DunningLevelId, DunningLevel> dunningIdToDunningLevel = CCache.<DunningLevelId, DunningLevel>builder().tableName(I_C_DunningLevel.Table_Name).build();

	@Override
	public final I_C_Dunning retrieveDunningByOrg(@NonNull final OrgId orgId)
	{
		return orgId2dunning.getOrLoad(orgId, () -> retrieveDunningByOrg0(orgId));
	}

	private I_C_Dunning retrieveDunningByOrg0(@NonNull final OrgId orgId)
	{
		Check.assume(orgId.isRegular(), "Param 'orgId' needs to be > 0");

		return queryBL.createQueryBuilder(I_C_Dunning.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Dunning.COLUMNNAME_AD_Org_ID, orgId)
				.addEqualsFilter(I_C_Dunning.COLUMNNAME_IsDefault, true)
				.create()
				.firstOnly(I_C_Dunning.class); // we have a UC on AD_Org_ID and IsDefault, so this should be fine
	}

	@Override
	public final I_C_Dunning_Candidate retrieveDunningCandidate(IDunningContext context, int tableId, int recordId, I_C_DunningLevel dunningLevel)
	{
		final DunningCandidateQuery query = new DunningCandidateQuery();
		query.setAD_Table_ID(tableId);
		query.setRecord_ID(recordId);
		query.setC_DunningLevels(Collections.singletonList(dunningLevel));

		// 04766 this method is also called from the server side, so for now the check for AD_Client_ID must suffice
		query.setApplyClientSecurity(true);
		query.setApplyAccessFilter(ApplyAccessFilter.ACCESS_FILTER_NONE);

		return retrieveDunningCandidate(context, query);
	}

	@Override
	public final I_C_Dunning_Candidate retrieveDunningCandidate(IDunningContext context, Object model, I_C_DunningLevel dunningLevel)
	{
		final TableRecordReference reference = TableRecordReference.of(model);

		return retrieveDunningCandidate(context, reference.getAD_Table_ID(), reference.getRecord_ID(), dunningLevel);
	}

	@Override
	public final List<I_C_Dunning_Candidate> retrieveDunningCandidates(IDunningContext context, int tableId, int recordId)
	{
		final List<I_C_DunningLevel> dunningLevels = null; // don't filter by dunning levels
		return retrieveDunningCandidates(context, tableId, recordId, dunningLevels);
	}

	@Override
	public final List<I_C_Dunning_Candidate> retrieveDunningCandidates(IDunningContext context, int tableId, int recordId, List<I_C_DunningLevel> dunningLevels)
	{
		final DunningCandidateQuery query = new DunningCandidateQuery();
		query.setAD_Table_ID(tableId);
		query.setRecord_ID(recordId);
		query.setC_DunningLevels(dunningLevels);
		query.setApplyClientSecurity(false); // we need to return all candidates for given table/record

		return retrieveDunningCandidates(context, query);
	}

	@Override
	public final Iterator<I_C_Dunning_Candidate> retrieveNotProcessedCandidatesIterator(final IDunningContext dunningContext)
	{
		final DunningCandidateQuery query = new DunningCandidateQuery();
		query.setProcessed(false);
		query.setApplyClientSecurity(true);
		query.setActive(true);

		return retrieveDunningCandidatesIterator(dunningContext, query);
	}

	@Override
	public final Iterator<I_C_Dunning_Candidate> retrieveNotProcessedCandidatesIteratorRW(final IDunningContext dunningContext, final String additionalWhere)
	{
		final DunningCandidateQuery query = new DunningCandidateQuery();
		query.setProcessed(false);
		query.setApplyClientSecurity(true);
		query.setActive(true);
		query.setAdditionalWhere(additionalWhere);
		query.setApplyAccessFilter(ApplyAccessFilter.ACCESS_FILTER_RW);

		return retrieveDunningCandidatesIterator(dunningContext, query);
	}

	@Override
	public final Iterator<I_C_Dunning_Candidate> retrieveNotProcessedCandidatesIteratorByLevel(IDunningContext dunningContext, final I_C_DunningLevel dunningLevel)
	{
		Check.assumeNotNull(dunningLevel, "dunningLevel not null");

		final DunningCandidateQuery query = new DunningCandidateQuery();
		query.setProcessed(false);
		query.setActive(true);
		query.setApplyClientSecurity(true);
		query.setC_DunningLevels(Collections.singletonList(dunningLevel));

		return retrieveDunningCandidatesIterator(dunningContext, query);
	}

	@Override
	public final List<I_C_DunningLevel> retrieveDunningLevels(final I_C_Dunning dunning)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(dunning);
		final String trxName = InterfaceWrapperHelper.getTrxName(dunning);
		final int dunningId = dunning.getC_Dunning_ID();
		return retrieveDunningLevels(ctx, dunningId, trxName);
	}

	@Override
	public final List<I_C_DunningDoc_Line> retrieveDunningDocLines(@NonNull final I_C_DunningDoc dunningDoc)
	{
		return queryBL.createQueryBuilder(I_C_DunningDoc_Line.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_DunningDoc_Line.COLUMN_C_DunningDoc_ID, dunningDoc.getC_DunningDoc_ID())
				.orderBy()
				.addColumn(I_C_DunningDoc_Line.COLUMN_C_DunningDoc_Line_ID).endOrderBy()
				.create()
				.list();
	}

	@Override
	public final List<I_C_DunningDoc_Line_Source> retrieveDunningDocLineSources(@NonNull final I_C_DunningDoc_Line line)
	{
		return queryBL
				.createQueryBuilder(I_C_DunningDoc_Line_Source.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_DunningDoc_Line_Source.COLUMN_C_DunningDoc_Line_ID, line.getC_DunningDoc_Line_ID())
				.orderBy()
				.addColumn(I_C_DunningDoc_Line_Source.COLUMN_C_DunningDoc_Line_Source_ID).endOrderBy()
				.create()
				.list();
	}

	@NonNull
	public ImmutableList<I_C_Dunning> retrieveDunningsByOrg(@NonNull final OrgId orgId)
	{
		return queryBL.createQueryBuilderOutOfTrx(I_C_Dunning.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Dunning.COLUMNNAME_AD_Org_ID, orgId, OrgId.ANY)
				.create()
				.listImmutable();
	}
	
	@Cached(cacheName = I_C_DunningLevel.Table_Name + "_for_C_Dunning_ID")
		/* package */ List<I_C_DunningLevel> retrieveDunningLevels(@CacheCtx Properties ctx, int dunningId, @CacheTrx String trxName)
	{
		return queryBL.createQueryBuilder(I_C_DunningLevel.class, ctx, trxName)
				.addEqualsFilter(I_C_DunningLevel.COLUMNNAME_C_Dunning_ID, dunningId)
				.addOnlyActiveRecordsFilter()
				.orderBy()
				.addColumn(I_C_DunningLevel.COLUMNNAME_DaysAfterDue)
				.addColumn(I_C_DunningLevel.COLUMNNAME_DaysBetweenDunning)
				.addColumn(I_C_DunningLevel.COLUMNNAME_C_DunningLevel_ID)
				.endOrderBy()
				.create()
				.list();
	}

	protected abstract List<I_C_Dunning_Candidate> retrieveDunningCandidates(IDunningContext context, IDunningCandidateQuery query);

	protected abstract I_C_Dunning_Candidate retrieveDunningCandidate(IDunningContext context, IDunningCandidateQuery query);

	protected abstract Iterator<I_C_Dunning_Candidate> retrieveDunningCandidatesIterator(IDunningContext context, IDunningCandidateQuery query);

	@Override
	public I_C_DunningDoc getByIdInTrx(@NonNull final DunningDocId dunningDocId)
	{
		return load(dunningDocId, I_C_DunningDoc.class);
	}

	@Override
	public Collection<I_C_DunningDoc> getByIdsInTrx(@NonNull final Collection<DunningDocId> dunningDocIds)
	{
		if (dunningDocIds.isEmpty())
		{
			return Collections.emptyList();
		}
		return queryBL.createQueryBuilder(I_C_DunningDoc.class)
				.addInArrayFilter(I_C_DunningDoc.COLUMNNAME_C_DunningDoc_ID, dunningDocIds)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();
	}

	@Override
	public DunningLevel getById(@NonNull final DunningLevelId id)
	{
		return dunningIdToDunningLevel.getOrLoad(id, dunningLevelId -> fromRecord(InterfaceWrapperHelper.load(id, I_C_DunningLevel.class)));
	}

	private DunningLevel fromRecord(@NonNull final I_C_DunningLevel level)
	{
		return DunningLevel.builder()
				.id(DunningLevelId.ofRepoId(level.getC_DunningLevel_ID()))
				.name(level.getName())
				.printName(level.getPrintName())
				.build();
	}
}
