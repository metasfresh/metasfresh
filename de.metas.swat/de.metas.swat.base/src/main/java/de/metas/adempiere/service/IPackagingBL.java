package de.metas.adempiere.service;

import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.Lookup;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Transportverpackung_%282009_0022_G61%29'>(2009_0022_G61)</a>"
 */
public interface IPackagingBL extends ISingletonService
{
	/**
	 * 
	 * @return true if we shall display non items
	 * @see de.metas.shipping.util.Constants#SYSCONFIG_SHIPMENT_PACKAGE_NON_ITEMS
	 */
	boolean isDisplayNonItemsEnabled(Properties ctx);

	Lookup createShipperLookup();
}
