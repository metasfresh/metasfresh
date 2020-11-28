package de.metas.impexp.process;

import org.compiere.process.AbstractImportJavaProcess;

import de.metas.vertical.pharma.model.I_I_Pharma_Product;


public class ImportIFAProduct extends AbstractImportJavaProcess<I_I_Pharma_Product>
{
	public ImportIFAProduct()
	{
		super(I_I_Pharma_Product.class);
	}
}
