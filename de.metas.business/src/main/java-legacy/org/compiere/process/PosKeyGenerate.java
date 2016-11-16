/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.process;

import java.util.List;

import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_POSKey;
import org.compiere.model.MProduct;
import org.compiere.model.Query;
import org.compiere.util.Env;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;


public class PosKeyGenerate extends SvrProcess {

	private int posKeyLayoutId = 0;
	private int productCategoryId = 0;
	
	@Override
	protected void prepare() {

		for ( ProcessInfoParameter para : getParametersAsArray())
		{
			
			if ( para.getParameterName().equals("C_POSKeyLayout_ID") )
				posKeyLayoutId = para.getParameterAsInt();
			if ( para.getParameterName().equals("M_Product_Category_ID") )
				productCategoryId = para.getParameterAsInt();
			else 
				log.info("Parameter not found " + para.getParameterName());
		}

		if ( posKeyLayoutId == 0 )
		{
			posKeyLayoutId = getRecord_ID();
		}
	}
	
	/**
	 * Generate keys for each product
	 */
	@Override
	protected String doIt() throws Exception {

		if ( posKeyLayoutId == 0 )
			throw new FillMandatoryException("C_POSKeyLayout_ID");
		
		int count = 0;
		String where = "";
		Object [] params = new Object[] {};
		if ( productCategoryId > 0 )
		{
			where = "M_Product_Category_ID = ? ";
			params = new Object[] {productCategoryId};
		}
		
		
		Query query = new Query(getCtx(), MProduct.Table_Name, where, get_TrxName())
			.setParameters(params)
			.setOnlyActiveRecords(true)
			.setOrderBy("Value");
			
		List<MProduct> products = query.list();
		
		for (MProduct product : products )
		{
			final I_C_POSKey key = InterfaceWrapperHelper.newInstance(I_C_POSKey.class, this);
			key.setName(product.getName());
			key.setM_Product_ID(product.getM_Product_ID());
			key.setC_POSKeyLayout_ID(posKeyLayoutId);
			key.setSeqNo(count*10);
			key.setQty(Env.ONE);
			InterfaceWrapperHelper.save(key);
			count++;
		}
		
		return "@Created@ " + count;
	}

}
