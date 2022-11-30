package org.adempiere.ad.service;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.i18n.ITranslatableString;
import de.metas.reflist.RefListId;
import de.metas.reflist.ReferenceId;
import de.metas.util.ISingletonService;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.compiere.model.I_AD_Reference;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

public interface IADReferenceDAO extends ISingletonService
{
	@EqualsAndHashCode
	@ToString
	class ADRefList
	{
		@Getter
		private final ReferenceId referenceId;
		private final ImmutableMap<String, ADRefListItem> itemsByValue;

		@Builder
		private ADRefList(
				final @NonNull ReferenceId referenceId,
				final @NonNull List<ADRefListItem> items)
		{

			this.referenceId = referenceId;
			this.itemsByValue = Maps.uniqueIndex(items, ADRefListItem::getValue);
		}

		public Set<String> getValues() {return itemsByValue.keySet();}

		public Collection<ADRefListItem> getItems() {return itemsByValue.values();}

		public Optional<ADRefListItem> getItemByValue(final String value) {return Optional.ofNullable(itemsByValue.get(value));}

		public int getFieldLength()
		{
			return getValues().stream().mapToInt(String::length).max().orElse(0);
		}
	}

	@Value
	@Builder
	class ADRefListItem
	{
		@NonNull
		ReferenceId referenceId;

		@NonNull
		RefListId refListId;

		@NonNull
		String value;

		@Nullable
		String valueName;

		@NonNull
		ITranslatableString name;

		@Nullable
		ITranslatableString description;
	}

	@Value
	@Builder
	class ADRefListItemCreateRequest
	{
		@NonNull
		ReferenceId referenceId;

		@NonNull
		String value;

		@NonNull
		ITranslatableString name;
	}

	ADRefList getRefListById(ReferenceId adReferenceId);

	/**
	 * @return a collection of all active {@link ADRefListItem} items of given <code>adReferenceId</code>
	 */
	Collection<ADRefListItem> retrieveListItems(int adReferenceId);

	/**
	 * @return Set of active {@link ADRefListItem#getValue()}s.
	 */
	Set<String> retrieveListValues(int adReferenceId);

	/**
	 * Gets List Item's Name for given <code>value</code> translated to context language.
	 *
	 * @return list name translated or value if not list item found.
	 * @deprecated use {@link #retrieveListNameTranslatableString(int, String)}
	 */
	@Deprecated
	String retrieveListNameTrl(final Properties ctx, int adReferenceId, String value);

	/**
	 * @deprecated use {@link #retrieveListNameTranslatableString(int, String)}
	 */
	@Deprecated
	default String retrieveListNameTrl(final int adReferenceId, final String value)
	{
		return retrieveListNameTrl(Env.getCtx(), adReferenceId, value);
	}

	/**
	 * @return true if an active {@link ADRefListItem} for given <code>adReferenceId</code> and <code>value</code> exists
	 */
	boolean existListValue(int adReferenceId, String value);

	@Nullable
	ADRefListItem retrieveListItemOrNull(int adReferenceId, String value);

	ITranslatableString retrieveListNameTranslatableString(int adReferenceId, String value);

	void saveRefList(IADReferenceDAO.ADRefListItemCreateRequest refListItemCreateRequest);

	I_AD_Reference getReferenceByID(ReferenceId referenceId);
}
