package org.eevolution.api;

import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_M_Product;

/**
 * Helper class to update {@link I_M_Product#COLUMNNAME_LowLevel}.
 * 
 * By default, it's updating ALL products for context client.
 * 
 * @author tsa
 *
 */
public interface IProductLowLevelUpdater
{
	/**
	 * Execute Product's LowLevel update
	 * 
	 * @return
	 */
	IProductLowLevelUpdater update();

	/**
	 * @return how many products where updated
	 */
	int getUpdatedCount();

	/**
	 * @return how many errors we got while updating the products
	 */
	int getErrorsCount();

	IProductLowLevelUpdater setContext(final IContextAware context);

	/**
	 * 
	 * @param failOnFirstError true if we shall fail on first error (Default: false, i.e. don't fail)
	 * @return this
	 */
	IProductLowLevelUpdater setFailOnFirstError(boolean failOnFirstError);

}
