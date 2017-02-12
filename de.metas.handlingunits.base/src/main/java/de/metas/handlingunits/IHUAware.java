package de.metas.handlingunits;

import java.util.Collection;

import com.google.common.collect.Collections2;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.handlingunits.model.I_M_HU;

/**
 * Interface implemented by all classes which are aware of {@link I_M_HU}
 *
 * @author tsa
 *
 */
public interface IHUAware
{
	/**
	 * @return given HU, wrapped as {@link IHUAware}
	 */
	static IHUAware fromHU(final I_M_HU hu)
	{
		return () -> hu;
	}

	/**
	 * @return live collection, where each HU is wrapped as {@link IHUAware}
	 */
	static Collection<IHUAware> transformHUCollection(final Collection<I_M_HU> hus)
	{
		return Collections2.transform(hus, IHUAware::fromHU);
	}

	/**
	 * @param model
	 * @return {@link I_M_HU} or <code>null</code>
	 */
	static I_M_HU getM_HUOrNull(final Object model)
	{
		if(model instanceof IHUAware)
		{
			return ((IHUAware)model).getM_HU();
		}
		else
		{
			return null;
		}
	}

	I_M_HU getM_HU();
}
