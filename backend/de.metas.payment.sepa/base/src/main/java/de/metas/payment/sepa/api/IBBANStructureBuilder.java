/**
 * 
 */
package de.metas.payment.sepa.api;

import de.metas.payment.sepa.wrapper.BBANStructure;

/**
 * @author cg
 *
 */
public interface IBBANStructureBuilder
{
	/**
	 * Creates the {@link BBANStructure}.
	 * 
	 * @return
	 */
	BBANStructure create();
	
	/**
	 * Creates a new BBANStructure Entry builder to add another entry to the BBAN structure that is currently build
	 */
	IBBANStructureEntryBuilder addBBANStructureEntry();
}
