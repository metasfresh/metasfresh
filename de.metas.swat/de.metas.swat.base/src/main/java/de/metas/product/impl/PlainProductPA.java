package de.metas.product.impl;

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.compiere.model.I_C_UOM;

import de.metas.adempiere.model.I_M_Product;

public class PlainProductPA extends ProductPA
{

	private final POJOLookupMap db = POJOLookupMap.get();

	public POJOLookupMap getDB()
	{
		return db;
	}


	@Override
	public I_C_UOM retrieveProductUOM(Properties ctx, int productId)
	{
		final I_M_Product product = retrieveProduct(ctx, productId, true, ITrx.TRXNAME_None);
		return product.getC_UOM();
	}

}
