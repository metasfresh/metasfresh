package org.adempiere.archive.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableSet;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.AdArchive;
import org.adempiere.archive.ArchiveId;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.archive.api.IArchiveEventManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.Query;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

public class ArchiveDAO implements IArchiveDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IArchiveBL archiveBL = Services.get(IArchiveBL.class);
	private final IArchiveEventManager archiveEventManager = Services.get(IArchiveEventManager.class);
	// private static final transient Logger logger = CLogMgt.getLogger(ArchiveDAO.class);

	@Override
	public List<I_AD_Archive> retrieveArchives(final Properties ctx, final String whereClause)
	{
		final StringBuilder wc = new StringBuilder();
		final List<Object> sqlParams = new ArrayList<>();

		wc.append(I_AD_Archive.COLUMNNAME_AD_Client_ID).append("=?");
		sqlParams.add(Env.getAD_Client_ID(ctx));

		if (!Check.isEmpty(whereClause, true))
		{
			wc.append(" ").append(whereClause);
		}

		return new Query(ctx, I_AD_Archive.Table_Name, wc.toString(), ITrx.TRXNAME_None)
				.setParameters(sqlParams)
				.setOrderBy(I_AD_Archive.COLUMNNAME_Created)
				.list(I_AD_Archive.class);
	}

	@Override
	public List<I_AD_Archive> retrieveLastArchives(
			@NonNull final Properties ctx,
			@NonNull final TableRecordReference recordRef,
			@NonNull final QueryLimit limit)
	{
		return retrieveArchivesQuery(ctx, recordRef)
				.orderByDescending(I_AD_Archive.COLUMNNAME_Created)
				.setLimit(limit)
				.create()
				.list(I_AD_Archive.class);
	}

	@Override
	@Nullable
	public I_AD_Archive retrieveArchiveOrNull(final TableRecordReference recordRef, final ArchiveId archiveId)
	{
		return retrieveArchivesQuery(Env.getCtx(), recordRef)
				.addEqualsFilter(I_AD_Archive.COLUMNNAME_AD_Archive_ID, archiveId)
				.create()
				.firstOnly(I_AD_Archive.class);
	}

	private IQueryBuilder<I_AD_Archive> retrieveArchivesQuery(final Properties ctx, final TableRecordReference recordRef)
	{
		return queryBL
				.createQueryBuilder(I_AD_Archive.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Archive.COLUMNNAME_AD_Table_ID, recordRef.getAD_Table_ID())
				.addEqualsFilter(I_AD_Archive.COLUMNNAME_Record_ID, recordRef.getRecord_ID());
	}

	@Override
	@Nullable
	public <T> T retrieveReferencedModel(final I_AD_Archive archive, final Class<T> modelClass)
	{
		// TODO: use org.adempiere.ad.dao.cache.impl.TableRecordCacheLocal<ParentModelType>

		if (archive == null)
		{
			return null;
		}

		final int tableId = archive.getAD_Table_ID();
		if (tableId <= 0)
		{
			return null;
		}

		final int recordId = archive.getRecord_ID();
		if (recordId < 0)
		{
			return null;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(archive);
		final String trxName = InterfaceWrapperHelper.getTrxName(archive);
		final String tableName = Services.get(IADTableDAO.class).retrieveTableName(tableId);
		return InterfaceWrapperHelper.create(ctx, tableName, recordId, modelClass, trxName);
	}

	@Override
	public I_AD_Archive retrieveArchive(@NonNull final ArchiveId archiveId)
	{
		return InterfaceWrapperHelper.load(archiveId, I_AD_Archive.class);
	}

	@Override
	public <T> Stream<AdArchive> streamArchivesForFilter(@NonNull final IQueryFilter<T> outboundLogFilter, final Class<T> objectClass)
	{
		final IQueryBuilder<T> queryBuilder = queryBL.createQueryBuilder(objectClass)
				.addOnlyActiveRecordsFilter().filter(outboundLogFilter);

		return queryBuilder.create()
				.iterateAndStream()
				.map(log -> retrieveLastArchives(Env.getCtx(), TableRecordReference.ofReferenced(log), QueryLimit.ONE).stream().findFirst())
				.filter(Optional::isPresent)
				.map(Optional::get)
				.map(arch -> AdArchive.builder().id(ArchiveId.ofRepoId(arch.getAD_Archive_ID())).archiveData(archiveBL.getBinaryData(arch)).build());
	}

	@Override
	public void updatePrintedRecords(final ImmutableSet<ArchiveId> ids, final UserId userId)
	{
		queryBL.createQueryBuilder(I_AD_Archive.class, Env.getCtx(), ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_AD_Archive.COLUMN_AD_Archive_ID, ids).create().iterateAndStream().filter(Objects::nonNull).forEach(archive -> archiveEventManager.firePdfUpdate(archive, userId));
	}

	@Override
	public <T extends I_AD_Archive> T retrieveArchive(@NonNull final ArchiveId archiveId, @NonNull final Class<T> modelClass)
	{
		final T archive = loadOutOfTrx(archiveId, modelClass);

		if (archive == null)
		{
			throw new AdempiereException("@NotFound@ @AD_Archive_ID@: " + archiveId);
		}
		return archive;
	}

}
