/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.grssignum.to_grs.bpartner;

import de.metas.common.bpartner.v2.response.JsonResponseLocation;
import de.metas.common.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@UtilityClass
public class ExportLocationHelper
{
	@NonNull
	public static Optional<JsonResponseLocation> getBPartnerMainLocation(@NonNull final List<JsonResponseLocation> jsonResponseLocations)
	{
		if (Check.isEmpty(jsonResponseLocations))
		{
			return Optional.empty();
		}

		return jsonResponseLocations.stream()
				.min(Comparator.comparing(JsonResponseLocation::isVisitorsAddress, Comparator.reverseOrder())
							 .thenComparing(JsonResponseLocation::isBillToDefault, Comparator.reverseOrder())
							 .thenComparing(JsonResponseLocation::isShipToDefault, Comparator.reverseOrder())
							 .thenComparing(JsonResponseLocation::isBillTo, Comparator.reverseOrder())
							 .thenComparing(JsonResponseLocation::isShipTo, Comparator.reverseOrder()));
	}
}
