package de.metas.ui.web.quickinput;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Component;

import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.descriptor.DetailId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

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
 *
 * Implementations shall be annotated with {@link Component} and they will be automatically discovered and registered.
 */
public interface IQuickInputDescriptorFactory
{
	/**
	 * Gets matching keys on which this factory shall be registered.
	 *
	 * NOTE to implementors: This method will be called once, when the factory is discovered and registered,
	 * so it's safe to compute the result just in time (instead of precomputing and storing it).
	 */
	Set<MatchingKey> getMatchingKeys();

	QuickInputDescriptor createQuickInputEntityDescriptor(
			final DocumentType documentType,
			final DocumentId documentTypeId,
			final DetailId detailId,
			final Optional<Boolean> soTrx);

	//
	//
	// -------------------------------------------------------------------
	//
	//

	/** Key used to identify the {@link IQuickInputDescriptorFactory} to be used */
	@Value
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class MatchingKey
	{
		public static final MatchingKey includedDocument(final DocumentType documentType, final int documentTypeIdInt, final String tableName)
		{
			final DocumentId documentTypeId = DocumentId.of(documentTypeIdInt);
			return new MatchingKey(documentType, documentTypeId, tableName);
		}

		public static final MatchingKey includedDocument(final DocumentType documentType, final DocumentId documentTypeId, final String tableName)
		{
			return new MatchingKey(documentType, documentTypeId, tableName);
		}

		public static final MatchingKey ofTableName(final String tableName)
		{
			final DocumentType documentType = null;
			final DocumentId documentTypeId = null;
			return new MatchingKey(documentType, documentTypeId, tableName);
		}

		private final DocumentType documentType;
		private final DocumentId documentTypeId;
		private final String tableName;
	}
}
