package de.metas.impexp.processing;

import java.util.ArrayList;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
@VisibleForTesting
public final class ImportGroup<ImportGroupKey, ImportRecordType>
{
	public static <ImportGroupKey, ImportRecordType> ImportGroup<ImportGroupKey, ImportRecordType> newInstance(@NonNull final ImportGroupKey groupKey)
	{
		return new ImportGroup<>(groupKey);
	}

	@Getter
	private final ImportGroupKey groupKey;
	private final ArrayList<ImportRecordType> importRecords = new ArrayList<>();

	private ImportGroup(@NonNull final ImportGroupKey groupKey)
	{
		this.groupKey = groupKey;
	}

	public void addImportRecord(@NonNull final ImportRecordType importRecord)
	{
		importRecords.add(importRecord);
	}

	public ImmutableList<ImportRecordType> getImportRecords()
	{
		return ImmutableList.copyOf(importRecords);
	}

	public boolean isEmpty()
	{
		return importRecords.isEmpty();
	}

	public Set<Integer> getImportRecordIds()
	{
		return importRecords.stream()
				.map(InterfaceWrapperHelper::getId)
				.collect(ImmutableSet.toImmutableSet());
	}
}
