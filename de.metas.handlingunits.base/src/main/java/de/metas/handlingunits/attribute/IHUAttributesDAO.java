package de.metas.handlingunits.attribute;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;

import org.adempiere.util.ISingletonService;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.NullAutoCloseable;
import org.compiere.model.I_M_Attribute;

import de.metas.handlingunits.attribute.impl.HUAttributesBySeqNoComparator;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;

/**
 * Note: there are multiple implementations of this API. One (the "default" one) of them is returned by {@link org.adempiere.util.Services#get(Class)}, the others are only instantiated and returned by
 * factories.
 *
 *
 */
public interface IHUAttributesDAO extends ISingletonService
{
	/**
	 * @param contextProvider
	 * @return new {@link I_M_HU_Attribute} (not saved)
	 */
	I_M_HU_Attribute newHUAttribute(Object contextProvider);

	/** Save given {@link I_M_HU_Attribute} */
	void save(I_M_HU_Attribute huAttribute);

	/** Delete gven {@link I_M_HU_Attribute} */
	void delete(I_M_HU_Attribute huAttribute);

	/**
	 * Called by API when a new HU is created.
	 * Depends on implemenentation, here the internal caches could be initialized.
	 * 
	 * @param hu
	 */
	void initHUAttributes(I_M_HU hu);

	/**
	 * Load the given <code>hu</code>'s attributes, ordered by their <code>M_HU_PI_Attribute</code>'s <code>SeqNo</code> (see {@link HUAttributesBySeqNoComparator}).
	 *
	 * @param hu
	 * @return sorted HU attributes
	 */
	List<I_M_HU_Attribute> retrieveAttributesOrdered(I_M_HU hu);

	/**
	 *
	 * @param hu
	 * @param attribute
	 * @return the attribute or <code>null</code>
	 */
	I_M_HU_Attribute retrieveAttribute(I_M_HU hu, I_M_Attribute attribute);

	/**
	 * If the DAO implementation supports "autoflush" this method will disable it
	 * and it will return an {@link IAutoCloseable} which when closed it will enable back the "autoflush".
	 * 
	 * If DAO implementation does not support "autoflush", this method will return a {@link NullAutoCloseable}.
	 * 
	 * If the autoflush is already disabled, this method will return {@link NullAutoCloseable}.
	 * 
	 * @return {@link IAutoCloseable} to enable back the autoflush or {@link NullAutoCloseable}
	 */
	IAutoCloseable temporaryDisableAutoflush();

	/**
	 * Clears internal cache if the implementation has an internal cache.
	 * If there is no internal cache, this method will do nothing.
	 */
	void flushAndClearCache();
}
