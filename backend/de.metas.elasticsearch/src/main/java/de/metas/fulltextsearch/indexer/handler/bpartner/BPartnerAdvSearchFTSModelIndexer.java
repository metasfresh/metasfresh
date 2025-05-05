/*
 * #%L
 * de.metas.elasticsearch
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.fulltextsearch.indexer.handler.bpartner;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.fulltextsearch.config.ESDocumentToIndex;
import de.metas.fulltextsearch.config.ESDocumentToIndexChunk;
import de.metas.fulltextsearch.config.ESDocumentToIndexTemplate;
import de.metas.fulltextsearch.config.FTSConfig;
import de.metas.fulltextsearch.indexer.handler.FTSModelIndexer;
import de.metas.fulltextsearch.indexer.queue.ModelToIndex;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.TableName;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Adv_Search;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.util.DB;
import org.compiere.util.Evaluatee;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BPartnerAdvSearchFTSModelIndexer implements FTSModelIndexer
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);

	@Override
	public Set<TableName> getHandledSourceTableNames()
	{
		return ImmutableSet.of(
				TableName.ofString(I_C_BPartner.Table_Name),
				TableName.ofString(I_C_BPartner_Location.Table_Name),
				TableName.ofString(I_AD_User.Table_Name));
	}

	@Override
	public List<ESDocumentToIndexChunk> createDocumentsToIndex(
			@NonNull final List<ModelToIndex> requests,
			@NonNull final FTSConfig config)
	{
		final Set<BPartnerId> bpartnerIds = extractAffectedBPartnerIds(requests);
		final ChangedDocumentIds changedDocumentIds = updateSearchTable(bpartnerIds);

		final ESDocumentToIndexChunk.ESDocumentToIndexChunkBuilder result = ESDocumentToIndexChunk.builder();

		result.documentIdsToDelete(changedDocumentIds.getRemovedDocumentIds());

		if (!changedDocumentIds.getChangedDocumentIds().isEmpty())
		{
			final ESDocumentToIndexTemplate documentToIndexTemplate = config.getDocumentToIndexTemplate();

			final List<I_C_BPartner_Adv_Search> records = queryBL
					.createQueryBuilder(I_C_BPartner_Adv_Search.class)
					.addOnlyActiveRecordsFilter()
					.addInArrayFilter(I_C_BPartner_Adv_Search.COLUMN_ES_DocumentId, changedDocumentIds.getChangedDocumentIds())
					.create()
					.list();
			for (final I_C_BPartner_Adv_Search record : records)
			{
				final String esDocumentId = record.getES_DocumentId();
				final Evaluatee evalCtx = InterfaceWrapperHelper.getEvaluatee(record);
				final ESDocumentToIndex documentToIndex = documentToIndexTemplate.resolve(evalCtx, esDocumentId);
				result.documentToIndex(documentToIndex);
			}
		}

		return ImmutableList.of(result.build());
	}

	private ChangedDocumentIds updateSearchTable(@NonNull final Set<BPartnerId> bpartnerIds)
	{
		if (bpartnerIds.isEmpty())
		{
			return ChangedDocumentIds.EMPTY;
		}

		final String sql = "SELECT * from C_BPartner_Adv_Search_Update(p_C_BPartner_IDs := ARRAY["
				+ bpartnerIds.stream().map(bpartnerId -> "?").collect(Collectors.joining(","))
				+ "])";
		final ArrayList<Object> sqlParams = new ArrayList<>(bpartnerIds);

		final ChangedDocumentIds.ChangedDocumentIdsBuilder changes = ChangedDocumentIds.builder();

		DB.forEachRow(sql, sqlParams, rs -> {
			final String esDocumentId = rs.getString("es_documentid");
			final String operation = rs.getString("operation");
			if ("D".equals(operation))
			{
				changes.removedDocumentId(esDocumentId);
			}
			else
			{
				changes.changedDocumentId(esDocumentId);
			}
		});

		return changes.build();
	}

	private DocumentChange retrieveDocumentChange(final ResultSet rs) throws SQLException
	{
		final String operation = rs.getString("operation");
		final boolean isRemove = "D".equals(operation);
		final String esDocumentId = rs.getString("es_documentid");

		return DocumentChange.of(isRemove, esDocumentId);
	}

	private ImmutableSet<BPartnerId> extractAffectedBPartnerIds(@NonNull final List<ModelToIndex> requests)
	{
		final ImmutableSet.Builder<BPartnerId> bpartnerIds = ImmutableSet.builder();
		final HashSet<Integer> bpartnerLocationRepoIds = new HashSet<>();
		final HashSet<Integer> bpartnerContactRepoIds = new HashSet<>();

		for (final ModelToIndex request : requests)
		{
			final TableRecordReference sourceModelRef = request.getSourceModelRef();
			if(sourceModelRef == null)
			{
				continue;
			}

			final String sourceTableName = sourceModelRef.getTableName();
			if (I_C_BPartner.Table_Name.equals(sourceTableName))
			{
				bpartnerIds.add(BPartnerId.ofRepoId(sourceModelRef.getRecord_ID()));
			}
			else if (I_C_BPartner_Location.Table_Name.equals(sourceTableName))
			{
				bpartnerLocationRepoIds.add(sourceModelRef.getRecord_ID());
			}
			else if (I_AD_User.Table_Name.equals(sourceTableName))
			{
				bpartnerContactRepoIds.add(sourceModelRef.getRecord_ID());
			}
		}

		partnerDAO.getBPartnerLocationIdsByRepoIds(bpartnerLocationRepoIds)
				.stream()
				.map(BPartnerLocationId::getBpartnerId)
				.forEach(bpartnerIds::add);

		partnerDAO.getContactIdsByRepoIds(bpartnerContactRepoIds)
				.stream()
				.map(BPartnerContactId::getBpartnerId)
				.forEach(bpartnerIds::add);

		return bpartnerIds.build();
	}

	@Value(staticConstructor = "of")
	private static class DocumentChange
	{
		boolean isRemove;
		@NonNull String esDocumentId;
	}

	@Value
	@Builder
	private static class ChangedDocumentIds
	{
		public static final ChangedDocumentIds EMPTY = builder().build();

		@NonNull
		@Singular
		ImmutableSet<String> removedDocumentIds;

		@NonNull
		@Singular
		ImmutableSet<String> changedDocumentIds;
	}
}
