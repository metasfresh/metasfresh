package org.adempiere.archive.api;

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
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.archive.ArchiveId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Archive;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Properties;

/**
 * Archive related DAO
 *
 * @author tsa
 *
 */
public interface IArchiveDAO extends ISingletonService
{
	I_AD_Archive getArchiveRecordById(@NonNull ArchiveId id);

	/**
	 * Retrieves all archive records for context's AD_Client_ID by using given whereClause. Records will be ordered by Created.
	 *
	 * @param ctx context
	 * @param whereClause optional where clause (starting with AND)
	 * @return list of {@link I_AD_Archive}s
	 */
	@Deprecated
	List<I_AD_Archive> retrieveArchives(Properties ctx, String whereClause);

	/**
	 * Retrieve the latest {@code limit} archives, ordered by their {@code Created} timestamp
	 */
	List<I_AD_Archive> retrieveLastArchives(Properties ctx, TableRecordReference recordRef, QueryLimit limit);

	/**
	 * Retrieves the archive with given ID and which is linked to given recordRef.
	 *
	 * NOTE: might look a bit redundant that we require the ID and the recordRef but we do that to make sure that given ID is for that recordRef (validation).
	 * @return archive or null
	 */
	@Nullable
	I_AD_Archive retrieveArchiveOrNull(TableRecordReference recordRef, ArchiveId archiveId);


	/**
	 * Retrieves underlying model, referenced by AD_Table_ID and Record_ID
	 * @return underlying model or null
	 */
	@Nullable
	<T> T retrieveReferencedModel(I_AD_Archive archive, Class<T> modelClass);

	I_AD_Archive retrieveArchive(ArchiveId archiveId);

	void updatePrintedRecords(ImmutableSet<ArchiveId> ids, UserId userId);

	<T extends I_AD_Archive> T retrieveArchive(@NonNull ArchiveId archiveId, @NonNull Class<T> modelClass);
}
