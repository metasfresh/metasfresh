<<<<<<<< HEAD:backend/de.metas.adempiere.adempiere/base/src/main/java/de/metas/cache/CCacheConfig.java
========
package de.metas.handlingunits.inventory.draftlinescreator.aggregator;

>>>>>>>> origin/new_dawn_uat:backend/de.metas.handlingunits.base/src/main/java/de/metas/handlingunits/inventory/draftlinescreator/aggregator/InventoryLineAggregationKey.java
/*
 * #%L
 * de.metas.handlingunits.base
 * %%
<<<<<<<< HEAD:backend/de.metas.adempiere.adempiere/base/src/main/java/de/metas/cache/CCacheConfig.java
 * Copyright (C) 2024 metas GmbH
========
 * Copyright (C) 2019 metas GmbH
>>>>>>>> origin/new_dawn_uat:backend/de.metas.handlingunits.base/src/main/java/de/metas/handlingunits/inventory/draftlinescreator/aggregator/InventoryLineAggregationKey.java
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

<<<<<<<< HEAD:backend/de.metas.adempiere.adempiere/base/src/main/java/de/metas/cache/CCacheConfig.java
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
========
public interface InventoryLineAggregationKey
{
>>>>>>>> origin/new_dawn_uat:backend/de.metas.handlingunits.base/src/main/java/de/metas/handlingunits/inventory/draftlinescreator/aggregator/InventoryLineAggregationKey.java
}
