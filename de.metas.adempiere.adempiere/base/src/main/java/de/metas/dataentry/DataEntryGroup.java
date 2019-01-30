package de.metas.dataentry;

import static de.metas.util.Check.assumeNotEmpty;

import java.util.List;

import de.metas.dataentry.model.I_DataEntry_Record_Assignment;
import de.metas.i18n.ITranslatableString;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

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

@Value
public class DataEntryGroup
{
	DataEntryGroupId id;

	ITranslatableString caption;
	ITranslatableString description;

	String internalName;

	List<DataEntrySubGroup> dataEntrySubGroups;

	/** if true, then there shall be now tab shown for this group and the sub-groups shall be shown on the same level as the normal AD_Tab based groups. */
	boolean anonymous;

	/** The column name of the record/document (e.g. C_BPartner_ID) against {@link I_DataEntry_Record_Assignment}s are linked. */
	DocumentLinkColumnName documentLinkColumnName;

	@Builder
	private DataEntryGroup(
			@NonNull final DataEntryGroupId id,
			@NonNull final ITranslatableString caption,
			@NonNull final ITranslatableString description,
			@NonNull final String internalName,
			@NonNull final DocumentLinkColumnName documentLinkColumnName,
			final boolean anonymous,
			@Singular final List<DataEntrySubGroup> dataEntrySubGroups)
	{
		this.id = id;
		this.caption = caption;
		this.description = description;
		this.internalName = internalName;
		this.documentLinkColumnName = documentLinkColumnName;
		this.anonymous = anonymous;
		this.dataEntrySubGroups = dataEntrySubGroups;
	}

	@Value
	public static class DocumentLinkColumnName
	{
		public static DocumentLinkColumnName of(String columnName)
		{
			return new DocumentLinkColumnName(columnName);
		}

		String asString;

		private DocumentLinkColumnName(String columnName)
		{
			this.asString = assumeNotEmpty(columnName, "Given columnName may not be empty");
		}
	}
}
