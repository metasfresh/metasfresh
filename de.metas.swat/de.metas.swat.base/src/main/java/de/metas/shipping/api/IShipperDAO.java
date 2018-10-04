package de.metas.shipping.api;

import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Shipper;

import de.metas.util.ISingletonService;

/**
 * 
 * @author tsa
 * 
 */
public interface IShipperDAO extends ISingletonService
{

	/**
	 * Retrieves the first active {@link I_M_Shipper} which has the given <code>bpartner</code> and same client with the given bpartner. If there is any default {@link I_M_Shipper} it will take that one.
	 * 
	 * @param bpartner
	 * @return shipper; if no shippers found it will return an error message
	 */
	I_M_Shipper retrieveForShipperBPartner(I_C_BPartner bpartner);

}
