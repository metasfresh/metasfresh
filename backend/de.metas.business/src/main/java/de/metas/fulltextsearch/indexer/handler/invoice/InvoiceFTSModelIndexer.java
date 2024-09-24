/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.fulltextsearch.indexer.handler.invoice;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.bpartner.BPartnerId;
import de.metas.calendar.standard.CalendarId;
import de.metas.calendar.standard.YearId;
import de.metas.document.DocTypeId;
import de.metas.fulltextsearch.config.ESDocumentToIndex;
import de.metas.fulltextsearch.config.ESDocumentToIndexChunk;
import de.metas.fulltextsearch.config.ESDocumentToIndexTemplate;
import de.metas.fulltextsearch.config.FTSConfig;
import de.metas.fulltextsearch.indexer.handler.FTSModelIndexer;
import de.metas.fulltextsearch.indexer.queue.ModelToIndex;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceMultiQuery;
import de.metas.invoice.InvoiceQuery;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.TableName;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice_Adv_Search;
import org.compiere.model.I_C_Year;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.DB;
import org.compiere.util.Evaluatee;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class InvoiceFTSModelIndexer implements FTSModelIndexer
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

	@Override
	public Set<TableName> getHandledSourceTableNames()
	{
		return ImmutableSet.of(
				TableName.ofString(I_C_Invoice.Table_Name),
				TableName.ofString(I_C_BPartner.Table_Name),
				TableName.ofString(I_C_BPartner_Location.Table_Name),
				TableName.ofString(I_AD_User.Table_Name),
				TableName.ofString(I_C_DocType.Table_Name),
				TableName.ofString(I_M_Warehouse.Table_Name),
				TableName.ofString(I_C_Calendar.Table_Name),
				TableName.ofString(I_C_Year.Table_Name));
	}

	@Override
	public List<ESDocumentToIndexChunk> createDocumentsToIndex(final List<ModelToIndex> requests, final FTSConfig config)
	{
		final Set<InvoiceId> invoiceIds = extractAffectedInvoiceIds(requests);
		final ChangedDocumentIds changedDocumentIds = updateSearchTable(invoiceIds);

		final ESDocumentToIndexChunk.ESDocumentToIndexChunkBuilder result = ESDocumentToIndexChunk.builder();

		result.documentIdsToDelete(changedDocumentIds.getRemovedDocumentIds());

		if (!changedDocumentIds.getChangedDocumentIds().isEmpty())
		{
			final ESDocumentToIndexTemplate documentToIndexTemplate = config.getDocumentToIndexTemplate();

			final List<I_C_Invoice_Adv_Search> records = queryBL
					.createQueryBuilder(I_C_Invoice_Adv_Search.class)
					.addOnlyActiveRecordsFilter()
					.addInArrayFilter(I_C_Invoice_Adv_Search.COLUMN_ES_DocumentId, changedDocumentIds.getChangedDocumentIds())
					.create()
					.list();
			for (final I_C_Invoice_Adv_Search record : records)
			{
				final String esDocumentId = record.getES_DocumentId();
				final Evaluatee evalCtx = InterfaceWrapperHelper.getEvaluatee(record);
				final ESDocumentToIndex documentToIndex = documentToIndexTemplate.resolve(evalCtx, esDocumentId);
				result.documentToIndex(documentToIndex);
			}
		}

		return ImmutableList.of(result.build());
	}

	private ChangedDocumentIds updateSearchTable(@NonNull final Set<InvoiceId> invoiceIds)
	{
		if (invoiceIds.isEmpty())
		{
			return ChangedDocumentIds.EMPTY;
		}

		final String sql = "SELECT * from C_Invoice_Adv_Search_Update(p_C_Invoice_IDs := ARRAY["
				+ invoiceIds.stream().map(invoiceId -> "?").collect(Collectors.joining(","))
				+ "])";
		final ArrayList<Object> sqlParams = new ArrayList<>(invoiceIds);

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

	private ImmutableSet<InvoiceId> extractAffectedInvoiceIds(@NonNull final List<ModelToIndex> requests)
	{
		final HashSet<InvoiceId> invoiceIds = new HashSet<>();
		final HashSet<BPartnerId> bPartnerIds = new HashSet<>();
		final HashSet<Integer> bpartnerLocationRepoIds = new HashSet<>();
		final HashSet<Integer> bpartnerContactRepoIds = new HashSet<>();
		final HashSet<DocTypeId> docTypeIds = new HashSet<>();
		final HashSet<WarehouseId> warehouseIds = new HashSet<>();
		final HashSet<CalendarId> calendarIds = new HashSet<>();
		final HashSet<YearId> yearIds = new HashSet<>();

		for (final ModelToIndex request : requests)
		{
			final TableRecordReference sourceModelRef = request.getSourceModelRef();
			if (sourceModelRef == null)
			{
				continue;
			}

			final String sourceTableName = sourceModelRef.getTableName();
			if (I_C_Invoice.Table_Name.equals(sourceTableName))
			{
				invoiceIds.add(InvoiceId.ofRepoId(sourceModelRef.getRecord_ID()));
			}
			else if (I_C_BPartner.Table_Name.equals(sourceTableName))
			{
				bPartnerIds.add(BPartnerId.ofRepoId(sourceModelRef.getRecord_ID()));
			}
			else if (I_C_BPartner_Location.Table_Name.equals(sourceTableName))
			{
				bpartnerLocationRepoIds.add(sourceModelRef.getRecord_ID());
			}
			else if (I_AD_User.Table_Name.equals(sourceTableName))
			{
				bpartnerContactRepoIds.add(sourceModelRef.getRecord_ID());
			}
			else if (I_C_DocType.Table_Name.equals(sourceTableName))
			{
				docTypeIds.add(DocTypeId.ofRepoId(sourceModelRef.getRecord_ID()));
			}
			else if (I_M_Warehouse.Table_Name.equals(sourceTableName))
			{
				warehouseIds.add(WarehouseId.ofRepoId(sourceModelRef.getRecord_ID()));
			}
			else if (I_C_Calendar.Table_Name.equals(sourceTableName))
			{
				calendarIds.add(CalendarId.ofRepoId(sourceModelRef.getRecord_ID()));
			}
			else if (I_C_Year.Table_Name.equals(sourceTableName))
			{
				yearIds.add(YearId.ofRepoId(sourceModelRef.getRecord_ID()));
			}
			else
			{
				throw new AdempiereException("Source table not supported: " + sourceTableName );
			}
		}

		final Set<InvoiceQuery> invoiceQueries = new HashSet<>();

		if (!invoiceIds.isEmpty())
		{
			invoiceQueries.add(InvoiceQuery.builder().invoiceIds(invoiceIds).build());
		}
		if (!bPartnerIds.isEmpty())
		{
			invoiceQueries.add(InvoiceQuery.builder().bpartnerIds(bPartnerIds).build());
		}
		if (!bpartnerLocationRepoIds.isEmpty())
		{
			invoiceQueries.add(InvoiceQuery.builder().bpartnerLocationRepoIds(bpartnerLocationRepoIds).build());
		}
		if (!bpartnerContactRepoIds.isEmpty())
		{
			invoiceQueries.add(InvoiceQuery.builder().bpartnerContactRepoIds(bpartnerContactRepoIds).build());
		}
		if (!warehouseIds.isEmpty())
		{
			invoiceQueries.add(InvoiceQuery.builder().warehouseIds(warehouseIds).build());
		}
		if (!docTypeIds.isEmpty())
		{
			invoiceQueries.add(InvoiceQuery.builder().docTypeIds(docTypeIds).build());
		}
		if (!calendarIds.isEmpty())
		{
			invoiceQueries.add(InvoiceQuery.builder().calendarIds(calendarIds).build());
		}
		if (!yearIds.isEmpty())
		{
			invoiceQueries.add(InvoiceQuery.builder().yearIds(yearIds).build());
		}

		return invoiceDAO.retrieveInvoiceIds(InvoiceMultiQuery.builder().queries(invoiceQueries).build());

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
