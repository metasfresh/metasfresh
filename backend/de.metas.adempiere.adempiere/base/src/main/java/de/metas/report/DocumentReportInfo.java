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

import de.metas.bpartner.BPartnerId;
import de.metas.document.DocTypeId;
import de.metas.i18n.Language;
import de.metas.process.AdProcessId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

import java.util.Objects;

@Value
@Builder(toBuilder = true)
public class DocumentReportInfo
{
	@NonNull TableRecordReference recordRef;

	@NonNull PrintFormatId printFormatId;
	AdProcessId reportProcessId;

	@NonNull
	@Builder.Default
	PrintCopies copies = PrintCopies.ONE;

	String documentNo;
	BPartnerId bpartnerId;
	DocTypeId docTypeId;
	Language language;

	@NonNull
	@Builder.Default
	DocumentPrintOptions printOptions = DocumentPrintOptions.NONE;

	public DocumentReportInfo withPrintOptionsFallback(@NonNull final DocumentPrintOptions printOptionsFallback)
	{
		final DocumentPrintOptions newPrintOptions = this.printOptions.mergeWithFallback(printOptionsFallback);
		return !Objects.equals(this.printOptions, newPrintOptions)
				? toBuilder().printOptions(newPrintOptions).build()
				: this;
	}
}
