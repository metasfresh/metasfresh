package de.metas.handlingunits.receiptschedule;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;

import de.metas.handlingunits.model.I_M_HU;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

/**
 * Annotate implementations of this interface with {@link Component} and they will be automatically discovered.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IHUToReceiveValidator
{
	/**
	 * Called before HUs are about to be received.
	 */
	public void assertValidForReceiving(I_M_HU hu) throws AdempiereException;
}
