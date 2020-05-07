package de.metas.impexp.process;

import org.compiere.process.AbstractImportJavaProcess;

import de.metas.vertical.pharma.model.I_I_Pharma_Product;


public class ImportPharmaProduct extends AbstractImportJavaProcess<I_I_Pharma_Product>
{
	public ImportPharmaProduct()
	{
		super(I_I_Pharma_Product.class);
	}
}
