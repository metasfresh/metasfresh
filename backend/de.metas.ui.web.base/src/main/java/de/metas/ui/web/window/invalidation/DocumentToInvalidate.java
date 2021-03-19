package de.metas.ui.web.window.invalidation;

import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.util.Check;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.util.lang.impl.TableRecordReference;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

@ToString
public final class DocumentToInvalidate
{
	private final TableRecordReference recordRef;
	@Getter
	private boolean invalidateDocument;

	private final HashMap<String, IncludedDocumentToInvalidate> includedDocumentsByTableName = new HashMap<>();

	public DocumentToInvalidate(@NonNull final TableRecordReference rootRecordRef)
	{
		recordRef = rootRecordRef;
	}

	public void invalidateDocument()
	{
		invalidateDocument = true;
	}

	public void invalidateAllIncludedDocuments(@NonNull final String includedTableName)
	{
		getIncludedDocument(includedTableName).invalidateAll();
	}

	public void addIncludedDocument(@NonNull final String includedTableName, final int includedRecordId)
	{
		getIncludedDocument(includedTableName).addRecordId(includedRecordId);
	}

	private IncludedDocumentToInvalidate getIncludedDocument(@NonNull final String includedTableName)
	{
		return includedDocumentsByTableName.computeIfAbsent(includedTableName, IncludedDocumentToInvalidate::new);
	}

	public String getTableName()
	{
		return recordRef.getTableName();
	}

	public DocumentId getDocumentId()
	{
		return DocumentId.of(recordRef.getRecord_ID());
	}

	public Collection<IncludedDocumentToInvalidate> getIncludedDocuments()
	{
		return includedDocumentsByTableName.values();
	}

	DocumentToInvalidate combine(@NonNull final DocumentToInvalidate other)
	{
		Check.assumeEquals(this.recordRef, other.recordRef, "recordRef");
		this.invalidateDocument = this.invalidateDocument || other.invalidateDocument;

		for (final Map.Entry<String, IncludedDocumentToInvalidate> e : other.includedDocumentsByTableName.entrySet())
		{
			this.includedDocumentsByTableName.merge(
					e.getKey(),
					e.getValue(),
					(item1, item2) -> item1.combine(item2));
		}

		return this;
	}
}
