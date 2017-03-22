package org.compiere.process;

import org.compiere.model.I_I_Product;

import de.metas.adempiere.model.I_M_Product;

/**
 * Import {@link I_I_Product} to {@link I_M_Product}.
 *
 * @author tsa
 */
public class ImportProduct extends AbstractImportJavaProcess<I_I_Product>
{
	public ImportProduct()
	{
		super(I_I_Product.class);
	}
}
