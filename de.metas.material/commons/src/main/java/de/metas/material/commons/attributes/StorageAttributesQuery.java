package de.metas.material.commons.attributes;

import de.metas.material.event.commons.AttributesKey;

/*
 * #%L
 * metasfresh-material-dispo-commons
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

public class StorageAttributesQuery
{
	public static final StorageAttributesQuery ALL = null;
	public static final StorageAttributesQuery NULL = null;

	public static StorageAttributesQuery parseString(final String storageAttributesKeys)
	{
		throw new UnsupportedOperationException();
//		return Splitter.on(",")
//				.trimResults()
//				.omitEmptyStrings()
//				.splitToList(storageAttributesKeys)
//				.stream()
//				.map(attributesKeyStr -> toAttributesKeyQuery(attributesKeyStr))
//				.collect(ImmutableSet.toImmutableSet());

	}

//	private static StorageAttributeMatcher toAttributesKeyQuery(final String storageAttributesKey)
//	{
//		if ("<ALL_STORAGE_ATTRIBUTES_KEYS>".equals(storageAttributesKey))
//		{
//			return StorageAttributeMatcher.ALL;
//		}
//		else if ("<OTHER_STORAGE_ATTRIBUTES_KEYS>".equals(storageAttributesKey))
//		{
//			return StorageAttributeMatcher.OTHER;
//		}
//		else
//		{
//			return StorageAttributeMatcher.ofString(storageAttributesKey);
//		}
//	}

	public static StorageAttributesQuery of(AttributesKey storageAttributesKey)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
