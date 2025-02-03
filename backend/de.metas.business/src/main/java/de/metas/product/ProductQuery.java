/*
 * #%L
<<<<<<<< HEAD:backend/de.metas.business/src/main/java/de/metas/product/ProductQuery.java
 * de.metas.business
========
 * de.metas.device.scales
>>>>>>>> origin/new_dawn_uat:backend/de.metas.device.scales/src/main/java/de/metas/device/scales/request/NoDeviceResponse.java
 * %%
 * Copyright (C) 2022 metas GmbH
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

<<<<<<<< HEAD:backend/de.metas.business/src/main/java/de/metas/product/ProductQuery.java
package de.metas.product;

import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class ProductQuery
{
	@Nullable
	Boolean isStocked;

	@Nullable
	Boolean isSold;
========
package de.metas.device.scales.request;

import de.metas.device.api.IDeviceResponse;

public class NoDeviceResponse implements IDeviceResponse
{
>>>>>>>> origin/new_dawn_uat:backend/de.metas.device.scales/src/main/java/de/metas/device/scales/request/NoDeviceResponse.java
}
