/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.report;

import com.google.common.collect.ImmutableMap;
import de.metas.cache.CCache;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.DocTypeId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_DocType_PrintOptions;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Map;

@Repository
public class DocTypePrintOptionsRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, DocTypePrintOptionsMap> cache = CCache.<Integer, DocTypePrintOptionsMap>builder()
			.tableName(I_C_DocType_PrintOptions.Table_Name)
			.build();

	@NonNull
	public DocumentPrintOptions getByDocTypeAndFlavor(
			@NonNull final DocTypeId docTypeId,
			@NonNull final DocumentReportFlavor flavor)
	{
		return getDocTypePrintOptionsMap().getByDocTypeAndFlavor(docTypeId, flavor);
	}

	private DocTypePrintOptionsMap getDocTypePrintOptionsMap()
	{
		return cache.getOrLoad(0, this::retrieveDocTypePrintOptionsMap);
	}

	private DocTypePrintOptionsMap retrieveDocTypePrintOptionsMap()
	{
		final ImmutableMap<DocTypePrintOptionsKey, DocumentPrintOptions> map = queryBL
				.createQueryBuilderOutOfTrx(I_C_DocType_PrintOptions.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						DocTypePrintOptionsRepository::toDocTypePrintOptionsKey,
						DocTypePrintOptionsRepository::toDocumentPrintOptions));

		return !map.isEmpty()
				? new DocTypePrintOptionsMap(map)
				: DocTypePrintOptionsMap.EMPTY;
	}

	@NonNull
	private static DocTypePrintOptionsKey toDocTypePrintOptionsKey(@NonNull final I_C_DocType_PrintOptions record)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoId(record.getC_DocType_ID());
		final DocumentReportFlavor flavor = DocumentReportFlavor.ofNullableCode(record.getDocumentFlavor());
		return DocTypePrintOptionsKey.of(docTypeId, flavor);
	}

	@NonNull
	private static DocumentPrintOptions toDocumentPrintOptions(@NonNull final I_C_DocType_PrintOptions record)
	{
		return DocumentPrintOptions.builder()
				.sourceName("DocType options: C_DocType_ID=" + record.getC_DocType_ID() + ", flavor=" + DocumentReportFlavor.ofNullableCode(record.getDocumentFlavor()))
				.option(DocumentPrintOptions.OPTION_IsPrintLogo, record.isPRINTER_OPTS_IsPrintLogo())
				.option(DocumentPrintOptions.OPTION_IsPrintTotals, record.isPRINTER_OPTS_IsPrintTotals())
				.build();
	}

	@Value(staticConstructor = "of")
	private static class DocTypePrintOptionsKey
	{
		@NonNull
		DocTypeId docTypeId;
		@Nullable
		DocumentReportFlavor flavor;
	}

	private static class DocTypePrintOptionsMap
	{
		public static final DocTypePrintOptionsMap EMPTY = new DocTypePrintOptionsMap(ImmutableMap.of());

		private final ImmutableMap<DocTypePrintOptionsKey, DocumentPrintOptions> map;

		DocTypePrintOptionsMap(final Map<DocTypePrintOptionsKey, DocumentPrintOptions> map)
		{
			this.map = ImmutableMap.copyOf(map);
		}

		public DocumentPrintOptions getByDocTypeAndFlavor(
				@NonNull final DocTypeId docTypeId,
				@NonNull final DocumentReportFlavor flavor)
		{
			return CoalesceUtil.coalesceSuppliers(
					() -> map.get(DocTypePrintOptionsKey.of(docTypeId, flavor)),
					() -> map.get(DocTypePrintOptionsKey.of(docTypeId, null)),
					() -> DocumentPrintOptions.NONE);
		}
	}
}
