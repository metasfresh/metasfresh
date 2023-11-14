package de.metas.ui.web.quickinput;

import de.metas.lang.SOTrx;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.descriptor.DetailId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.adempiere.ad.element.api.AdWindowId;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Set;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Quick input descriptor factory.
 * Implementations shall be annotated with {@link Component} and they will be automatically discovered and registered.
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public interface IQuickInputDescriptorFactory
{
	/**
	 * Gets matching keys on which this factory shall be registered.
	 * NOTE to implementors: This method will be called once, when the factory is discovered and registered,
	 * so it's safe to compute the result just in time (instead of precomputing and storing it).
	 */
	Set<MatchingKey> getMatchingKeys();

	QuickInputDescriptor createQuickInputDescriptor(
			final DocumentType documentType,
			final DocumentId documentTypeId,
			final DetailId detailId,
			final Optional<SOTrx> soTrx);

	//
	//
	// -------------------------------------------------------------------
	//
	//

	/** Key used to identify the {@link IQuickInputDescriptorFactory} to be used */
	@Value
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	class MatchingKey
	{
		@Nullable DocumentType documentType;
		@Nullable DocumentId documentTypeId;
		@Nullable String tableName;

		public static MatchingKey includedTab(final AdWindowId windowId, final String includedTabTableName)
		{
			return includedDocument(DocumentType.Window, DocumentId.of(windowId), includedTabTableName);
		}

		public static MatchingKey includedDocument(final DocumentType rootDocumentType, final DocumentId rootDocumentTypeId, final String includedTableName)
		{
			return new MatchingKey(rootDocumentType, rootDocumentTypeId, includedTableName);
		}

		public static MatchingKey ofTableName(final String tableName)
		{
			return new MatchingKey(null, null, tableName);
		}
	}
}
