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

import de.metas.cache.CCache;
import de.metas.document.DocTypeId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_DocType_PrintOptions;
import org.springframework.stereotype.Repository;

@Repository
public class DocTypePrintOptionsRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<DocTypeId, DocumentPrintOptions> cache = CCache.<DocTypeId, DocumentPrintOptions>builder()
			.tableName(I_C_DocType_PrintOptions.Table_Name)
			.build();

	@NonNull
	public DocumentPrintOptions getByDocTypeId(@NonNull final DocTypeId docTypeId)
	{
		return cache.getOrLoad(docTypeId, this::retrieveByDocTypeId);
	}

	@NonNull
	private DocumentPrintOptions retrieveByDocTypeId(@NonNull final DocTypeId docTypeId)
	{
		final I_C_DocType_PrintOptions record = queryBL.createQueryBuilderOutOfTrx(I_C_DocType_PrintOptions.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_DocType_PrintOptions.COLUMNNAME_C_DocType_ID, docTypeId)
				.create()
				.firstOnly(I_C_DocType_PrintOptions.class);

		return record != null
				? toDocumentPrintOptions(record)
				: DocumentPrintOptions.NONE;
	}

	@NonNull
	private static DocumentPrintOptions toDocumentPrintOptions(@NonNull final I_C_DocType_PrintOptions record)
	{
		return DocumentPrintOptions.builder()
				.option(DocumentPrintOptions.OPTION_IsPrintLogo, record.isPRINTER_OPTS_IsPrintLogo())
				.option(DocumentPrintOptions.OPTION_IsPrintTotals, record.isPRINTER_OPTS_IsPrintTotals())
				.build();
	}
}
