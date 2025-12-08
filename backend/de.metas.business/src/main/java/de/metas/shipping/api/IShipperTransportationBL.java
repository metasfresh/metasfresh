package de.metas.shipping.api;

import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.I_M_ShippingPackage;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.ISingletonService;
import org.compiere.model.I_M_Package;

public interface IShipperTransportationBL extends ISingletonService
{
	/**
	 * Links given {@link I_M_Package} to shipper transportation.
	 *
	 */
	I_M_ShippingPackage createShippingPackage(ShipperTransportationId shipperTransportationId, I_M_Package mpackage);

	/**
	 * Finds and set suitable document type to given shipper transportation.
	 */
	void setC_DocType(I_M_ShipperTransportation shipperTransportation);

}
