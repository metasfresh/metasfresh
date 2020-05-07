package de.metas.handlingunits;

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


import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Package;
import org.compiere.model.I_M_Shipper;

import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Package_HU;
import de.metas.shipping.model.I_M_ShippingPackage;

public interface IHUPackageBL extends ISingletonService
{
	/**
	 * Unassign all HUs from given package
	 *
	 * @param mpackage
	 */
	void destroyHUPackage(I_M_Package mpackage);

	/**
	 * Create a {@link de.metas.shipping.interfaces.I_M_Package} and an {@link I_M_Package_HU} for the given <code>hu</code> and <code>shipper</code>. Take the package's BPartner and BPartnerLocation
	 * from the hu.
	 *
	 * @param hu
	 * @param shipper
	 * @return
	 * @throws HUException if <code>hu</code> or <code>shipper</code> is <code>null</code> or if <code>hu</code> has not both a <code>C_BPartner_ID</code> and a <code>C_BPartner_Location_ID</code>.
	 */
	de.metas.shipping.interfaces.I_M_Package createM_Package(I_M_HU hu, I_M_Shipper shipper);

	/**
	 * Update all {@link I_M_Package}s and {@link I_M_ShippingPackage}s which are linked to given <code>hu</code>.
	 *
	 * @param hu
	 * @param inout
	 * @param trxName
	 */
	void assignShipmentToPackages(final I_M_HU hu, final I_M_InOut inout, String trxName);

	/**
	 * Unassign given shipment from {@link I_M_Package}s and {@link I_M_ShippingPackage}s.
	 *
	 * This is the counterpart method of {@link #assignShipmentToPackages(I_M_HU, I_M_InOut, String)}.
	 *
	 * @param shipment
	 */
	void unassignShipmentFromPackages(I_M_InOut shipment);
}
