package de.metas.handlingunits;

import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Package;

import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Package_HU;
import de.metas.shipping.ShipperId;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.util.ISingletonService;

public interface IHUPackageBL extends ISingletonService
{
	/**
	 * Unassign all HUs from given package
	 */
	void destroyHUPackage(I_M_Package mpackage);

	/**
	 * Create a {@link de.metas.shipping.interfaces.I_M_Package} and an {@link I_M_Package_HU} for the given <code>hu</code> and <code>shipper</code>. Take the package's BPartner and BPartnerLocation
	 * from the hu.
	 *
	 * @throws HUException if <code>hu</code> or <code>shipper</code> is <code>null</code> or if <code>hu</code> has not both a <code>C_BPartner_ID</code> and a <code>C_BPartner_Location_ID</code>.
	 */
	I_M_Package createM_Package(I_M_HU hu, ShipperId shipperId);

	/**
	 * Update all {@link I_M_Package}s and {@link I_M_ShippingPackage}s which are linked to given <code>hu</code>.
	 */
	void assignShipmentToPackages(final I_M_HU hu, final I_M_InOut inout, String trxName);

	/**
	 * Unassign given shipment from {@link I_M_Package}s and {@link I_M_ShippingPackage}s.
	 *
	 * This is the counterpart method of {@link #assignShipmentToPackages(I_M_HU, I_M_InOut, String)}.
	 */
	void unassignShipmentFromPackages(I_M_InOut shipment);
}
