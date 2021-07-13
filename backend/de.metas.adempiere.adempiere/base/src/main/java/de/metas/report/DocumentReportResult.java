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

import javax.annotation.Nullable;

import org.adempiere.archive.api.ArchiveResult;
import org.adempiere.util.lang.impl.TableRecordReference;

import de.metas.bpartner.BPartnerId;
import de.metas.i18n.Language;
import de.metas.process.AdProcessId;
import de.metas.process.PInstanceId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

@Value
@Builder
public class DocumentReportResult
{
	@NonNull
	@Builder.Default
	DocumentReportFlavor flavor = DocumentReportFlavor.PRINT;

	@Nullable
	ReportResultData data;

	@Nullable
	TableRecordReference documentRef;

	@Nullable
	AdProcessId reportProcessId;
	@Nullable
	PInstanceId reportPInstanceId;

	@Nullable
	@With
	BPartnerId bpartnerId;

	@Nullable
	Language language;

	@NonNull
	@Builder.Default
	PrintCopies copies = PrintCopies.ONE;
	
	@Nullable
	ArchiveResult lastArchive;

	@Nullable
	public String getFilename()
	{
		return data != null ? data.getReportFilename() : null;
	}

	public boolean isNoData()
	{
		return data == null || data.isEmpty();
	}

	@Nullable
	public byte[] getDataAsByteArray()
	{
		return data != null ? data.getReportData() : null;
	}
}
