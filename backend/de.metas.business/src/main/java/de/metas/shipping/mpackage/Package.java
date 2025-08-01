/*
 * #%L
 * de.metas.swat.base
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

package de.metas.shipping.mpackage;

import de.metas.handlingunits.HuId;
import de.metas.inout.InOutId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class Package
{
	@NonNull PackageId id;
	@Nullable BigDecimal weightInKg;
	@Nullable InOutId inOutId;
	@Nullable String sscc; // because there's no easy way to parse a String into an SSCC without knowing details about the producer
	@Nullable HuId huId;
	@NonNull @Singular List<PackageItem> packageContents;
	@NonNull OrgId orgId;

}
