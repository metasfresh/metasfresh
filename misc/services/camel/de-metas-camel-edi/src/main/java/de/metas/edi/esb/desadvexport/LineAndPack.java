/*
 * #%L
 * de-metas-edi-esb-camel
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.edi.esb.desadvexport;

import org.springframework.lang.Nullable;

import de.metas.edi.esb.jaxb.metasfresh.EDIExpDesadvLinePackType;
import de.metas.edi.esb.jaxb.metasfresh.EDIExpDesadvLineType;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

@Value
@ToString
public class LineAndPack
{
	@NonNull
	EDIExpDesadvLineType line;

	@Nullable // could be null if nothing is delivered for this line
	EDIExpDesadvLinePackType pack;

	public boolean hasPack()
	{
		return pack != null;
	}
}
