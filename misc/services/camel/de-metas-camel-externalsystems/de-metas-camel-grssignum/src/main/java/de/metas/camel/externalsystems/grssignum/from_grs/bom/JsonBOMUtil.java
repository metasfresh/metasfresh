/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.grssignum.from_grs.bom;

import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonBOM;
import de.metas.common.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class JsonBOMUtil
{
	@NonNull
	public String getName(@NonNull final JsonBOM grsBOM)
	{
		final String name = Stream.of(grsBOM.getName1(), grsBOM.getName2())
				.filter(Check::isNotBlank)
				.collect(Collectors.joining(" "));

		if (Check.isBlank(name))
		{
			throw new RuntimeException("Missing name for bomProduct: " + grsBOM.getProductId());
		}

		return name;
	}
}
