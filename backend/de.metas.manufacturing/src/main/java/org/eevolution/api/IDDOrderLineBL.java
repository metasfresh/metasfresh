package org.eevolution.api;

import org.eevolution.model.I_DD_OrderLine;

import de.metas.util.ISingletonService;

public interface IDDOrderLineBL extends ISingletonService
{

	/**
	 * Set the UOM from the product in the DDOrderLine
	 * 
	 * @param ddOrderLine
	 * @task http://dewiki908/mediawiki/index.php/08583_Erfassung_Packvorschrift_in_DD_Order_ist_crap_%28108882381939%29
	 *       ("UOM In manual DD_OrderLine shall always be the uom of the product ( as talked with Mark) ")
	 */
	void setUOMInDDOrderLine(I_DD_OrderLine ddOrderLine);

}
