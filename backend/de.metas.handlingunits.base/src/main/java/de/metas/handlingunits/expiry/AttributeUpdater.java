<<<<<<<< HEAD:backend/de.metas.handlingunits.base/src/main/java/de/metas/handlingunits/expiry/AttributeUpdater.java
package de.metas.handlingunits.expiry;

import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import lombok.NonNull;

import java.util.List;

========
>>>>>>>> new_dawn_uat:backend/de.metas.adempiere.adempiere/base/src/main/java/de/metas/cache/CCacheConfig.java
/*
 * #%L
 * metasfresh-webui-api
 * %%
<<<<<<<< HEAD:backend/de.metas.handlingunits.base/src/main/java/de/metas/handlingunits/expiry/AttributeUpdater.java
 * Copyright (C) 2017 metas GmbH
========
 * Copyright (C) 2024 metas GmbH
>>>>>>>> new_dawn_uat:backend/de.metas.adempiere.adempiere/base/src/main/java/de/metas/cache/CCacheConfig.java
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

<<<<<<<< HEAD:backend/de.metas.handlingunits.base/src/main/java/de/metas/handlingunits/expiry/AttributeUpdater.java
@FunctionalInterface
public interface AttributeUpdater
{
	boolean update(@NonNull final IAttributeStorage huAttributes);
========
package de.metas.cache;

import de.metas.cache.CCache.CacheMapType;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CCacheConfig
{
	@NonNull CacheMapType cacheMapType;
	int initialCapacity;
	int maximumSize;
	int expireMinutes;
>>>>>>>> new_dawn_uat:backend/de.metas.adempiere.adempiere/base/src/main/java/de/metas/cache/CCacheConfig.java
}
