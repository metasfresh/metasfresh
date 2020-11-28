/**
 * 
 */
package de.metas.payment.sepa.api;

import de.metas.payment.sepa.wrapper.BBANStructure;
import de.metas.util.ISingletonService;

/**
 * @author cg
 *
 */
public interface IBBANStructureBL extends ISingletonService
{

	/**
	 * @param countryCode the country code.
	 * @return BbanCode for specified country or null if country is not supported.
	 */
	BBANStructure getBBANStructureForCountry(String countryCode);
	
	/**
	 * BBANStructure builder
	 * @return
	 */
	IBBANStructureBuilder newBuilder();

}
