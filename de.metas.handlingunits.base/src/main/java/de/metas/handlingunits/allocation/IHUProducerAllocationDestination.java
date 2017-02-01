package de.metas.handlingunits.allocation;

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


import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Locator;

import de.metas.handlingunits.model.I_M_HU;

/**
 * Implementations of this interface are {@link IAllocationDestination}s which also produce HUs.
 *
 * @author tsa
 *
 */
public interface IHUProducerAllocationDestination extends IAllocationDestination
{
	/**
	 * Get the created top-level HUs.
	 *
	 * NOTE: please keep in mind that the <code>trxName</code> of returned HUs is not guaranteed to be {@link ITrx#TRXNAME_None} so if you want to reuse them you will need to change their transaction name first.
	 *
	 * @return created HUs so far; never return null
	 */
	List<I_M_HU> getCreatedHUs();

	/**
	 *
	 * @return how many HUs were created so far
	 */
	int getCreatedHUsCount();

	/**
	 * Sets HUStatus to be used for newly created HUs.
	 *
	 * If Parent HU Item is set then HUStatus will be used from there and this one will be ignored.
	 *
	 * @param huStatus HUStatus to be used; if null, it will be ignored
	 */
	void setHUStatus(String huStatus);

	String getHUStatus();

	/**
	 * Sets locator to be used for newly created HUs.
	 *
	 * If Parent HU Item is set then locator will be used from there and this one will be ignored.
	 *
	 * @param locator
	 */
	void setM_Locator(I_M_Locator locator);

	/**
	 * Sets BPartner to be used for newly created HUs.
	 *
	 * If BPartner is not set, the one from load {@link IAllocationRequest} will be used.
	 *
	 * @param bpartner
	 */
	void setC_BPartner(I_C_BPartner bpartner);

	I_C_BPartner getC_BPartner();

	/**
	 * Sets BPartner Location to be used for newly created HUs.
	 *
	 * If BPartner Location is not set, the one from load {@link IAllocationRequest} will be used.
	 *
	 * @param bpartnerLocationId
	 */
	void setC_BPartner_Location_ID(int bpartnerLocationId);

	int getC_BPartner_Location_ID();

	I_M_Locator getM_Locator();
}
