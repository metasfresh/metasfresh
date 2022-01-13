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

package de.metas.document.references.related_documents.generic;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.Check;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.experimental.Delegate;

@Value
public class GenericRelatedDocumentDescriptor
{
	@Delegate
	@NonNull GenericTargetWindowInfo genericTargetWindowInfo;

	@Getter(AccessLevel.NONE)
	ImmutableMap<String, GenericTargetColumnInfo> targetColumnsByColumnName;

	@Builder
	private GenericRelatedDocumentDescriptor(
			@NonNull final GenericTargetWindowInfo targetWindow,
			@NonNull @Singular final ImmutableList<GenericTargetColumnInfo> targetColumns)
	{
		Check.assumeNotEmpty(targetColumns, "targetColumns is not empty");

		this.genericTargetWindowInfo = targetWindow;
		this.targetColumnsByColumnName = Maps.uniqueIndex(targetColumns, GenericTargetColumnInfo::getColumnName);
	}

	public ImmutableCollection<GenericTargetColumnInfo> getTargetColumns()
	{
		return targetColumnsByColumnName.values();
	}
}
