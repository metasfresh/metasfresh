/*
 * #%L
 * de-metas-camel-edi
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

package de.metas.edi.esb.desadvexport.helper;

import de.metas.common.util.Check;
import de.metas.edi.esb.jaxb.metasfresh.EDIExpDesadvLineType;
import de.metas.edi.esb.jaxb.metasfresh.EDIExpDesadvPackItemType;
import de.metas.edi.esb.jaxb.metasfresh.EDIExpDesadvPackType;
import lombok.NonNull;
import lombok.Value;

@Value
public class SinglePack
{
	@NonNull
	public static SinglePack of(@NonNull final EDIExpDesadvPackType pack)
	{
		final EDIExpDesadvPackItemType packItemType = Check.singleElement(pack.getEDIExpDesadvPackItem());

		return new SinglePack(pack, packItemType);
	}

	@NonNull
	EDIExpDesadvPackType pack;

	@NonNull
	EDIExpDesadvPackItemType packItem;

	public int getDesadvLineID()
	{
		return packItem.getEDIDesadvLineID().getEDIDesadvLineID().intValue();
	}

	@NonNull
	public EDIExpDesadvLineType getDesadvLine()
	{
		return packItem.getEDIDesadvLineID();
	}
}
