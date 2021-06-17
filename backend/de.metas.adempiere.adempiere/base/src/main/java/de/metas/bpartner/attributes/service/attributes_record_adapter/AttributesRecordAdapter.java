/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.bpartner.attributes.service.attributes_record_adapter;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.attributes.BPartnerAttribute;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;

import java.util.Collection;
import java.util.HashMap;

public abstract class AttributesRecordAdapter<T, PARENT_ID>
{
	protected final IQueryBL queryBL = Services.get(IQueryBL.class);

	protected abstract IQuery<T> queryRecords(final @NonNull PARENT_ID parentId);

	protected abstract BPartnerAttribute extractAttribute(final @NonNull T record);

	protected abstract T createNewRecord(final @NonNull PARENT_ID parentId, final BPartnerAttribute attribute);

	private void deleteRecords(final Collection<T> records)
	{
		InterfaceWrapperHelper.deleteAll(records);
	}

	public ImmutableSet<BPartnerAttribute> getAttributes(final @NonNull PARENT_ID parentId)
	{
		return queryRecords(parentId)
				.stream()
				.map(this::extractAttribute)
				.distinct()
				.collect(ImmutableSet.toImmutableSet());
	}

	// see also: de.metas.ui.web.window.model.sql.SqlDocumentsRepository.saveLabels
	public void saveAttributes(
			@NonNull final ImmutableSet<BPartnerAttribute> attributesSet,
			@NonNull final PARENT_ID parentId)
	{
		final HashMap<BPartnerAttribute, T> existingRecords = queryRecords(parentId)
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(this::extractAttribute));

		for (final BPartnerAttribute attribute : attributesSet)
		{
			final T existingRecord = existingRecords.remove(attribute);
			if (existingRecord == null)
			{
				createNewRecord(parentId, attribute);
			}
		}

		deleteRecords(existingRecords.values());
	}

}
