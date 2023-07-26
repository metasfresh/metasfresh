/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.process;

import de.metas.document.references.zoom_into.CustomizedWindowInfoMap;
import de.metas.document.references.zoom_into.CustomizedWindowInfoMapRepository;
import lombok.ToString;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;

@ToString
class MockedCustomizedWindowInfoMapRepository implements CustomizedWindowInfoMapRepository
{
	private CustomizedWindowInfoMap map;

	@Override
	public CustomizedWindowInfoMap get()
	{
		final CustomizedWindowInfoMap map = this.map;
		if (map == null)
		{
			throw new AdempiereException("map was not set");
		}
		return map;
	}

	public void set(CustomizedWindowInfoMap map)
	{
		this.map = map;
	}

	@Override
	public void assertNoCycles(final AdWindowId adWindowId)
	{
		throw new UnsupportedOperationException("not implemented");
	}
}
