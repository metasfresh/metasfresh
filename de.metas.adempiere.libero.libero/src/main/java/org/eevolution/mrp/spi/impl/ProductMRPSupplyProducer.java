package org.eevolution.mrp.spi.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.compiere.model.I_M_Product;
import org.eevolution.mrp.api.IMRPCreateSupplyRequest;
import org.eevolution.mrp.api.IMRPExecutor;
import org.eevolution.mrp.api.IMRPSourceEvent;

import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.pporder.LiberoException;

/**
 * Actually this is a validation rule to not allow user to change product's UOM when there are MRP records
 * 
 * @author tsa
 * 
 */
public class ProductMRPSupplyProducer extends AbstractMRPSupplyProducer
{
	private static final String MSG_ProductMRPSupplyProducerNotApplies = "The Product MRP Supply Producer never applies";

	public ProductMRPSupplyProducer()
	{
		super();
		addSourceColumnNames(I_M_Product.Table_Name, new String[] {
				I_M_Product.COLUMNNAME_C_UOM_ID
		});
	}

	@Override
	public Class<?> getDocumentClass()
	{
		return null; // N/A
	}

	@Override
	public boolean applies(final IMaterialPlanningContext mrpContext, IMutable<String> notAppliesReason)
	{
		// never apply
		notAppliesReason.setValue(MSG_ProductMRPSupplyProducerNotApplies);
		return false;
	}

	@Override
	public void onDocumentChange(final Object model, final DocTimingType timing)
	{
		throw new IllegalStateException("Not supported");
	}

	@Override
	public void createSupply(final IMRPCreateSupplyRequest request)
	{
		// shall never been called
		throw new IllegalStateException("Not supported");
	}

	@Override
	protected void onRecordChange(final IMRPSourceEvent event)
	{
		if (ModelChangeType.BEFORE_CHANGE != event.getChangeType())
		{
			return;
		}

		final I_M_Product product = event.getModel(I_M_Product.class);
		if (InterfaceWrapperHelper.isValueChanged(product, I_M_Product.COLUMNNAME_C_UOM_ID))
		{
			assertNoProductMRPRecords(product);
		}
	}

	private void assertNoProductMRPRecords(final I_M_Product product)
	{
		if (mrpDAO.hasProductRecords(product))
		{
			throw new LiberoException("@SaveUomError@");
		}
	}

	@Override
	public void cleanup(final IMaterialPlanningContext mrpContext, final IMRPExecutor executor)
	{
		// nothing
	}
}
