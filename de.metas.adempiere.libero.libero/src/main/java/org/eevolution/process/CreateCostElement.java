/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
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
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *                 Teo Sarca, www.arhipac.ro                                  *
 *****************************************************************************/

package org.eevolution.process;

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

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.engines.CostDimension;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_M_Cost;
import org.compiere.model.I_M_CostElement;
import org.compiere.model.I_M_Product;
import org.compiere.model.MCost;
import org.compiere.model.X_C_AcctSchema;

import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 * Create Cost Element
 * 
 * @author victor.perez@e-evolution.com, e-Evolution, S.C.
 * @version $Id: CreateCostElement.java,v 1.1 2004/06/22 05:24:03 vpj-cd Exp $
 * 
 * @author Teo Sarca, www.arhipac.ro
 */
public class CreateCostElement extends JavaProcess
{
	private Integer p_AD_Org_ID = null;
	private int p_C_AcctSchema_ID = 0;
	private int p_M_CostType_ID = 0;
	private int p_M_CostElement_ID = 0;
	private int p_M_Product_Category_ID = 0;
	private int p_M_Product_ID = 0;
	@SuppressWarnings("unused")
	private int p_M_AttributeSetInstance_ID = 0;

	/**
	 * Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
			{
				;
			}
			else if (name.equals("AD_Org_ID"))
			{
				p_AD_Org_ID = para[i].getParameterAsInt();
			}
			else if (name.equals(I_M_Cost.COLUMNNAME_C_AcctSchema_ID))
			{
				p_C_AcctSchema_ID = para[i].getParameterAsInt();
			}
			else if (name.equals(I_M_Cost.COLUMNNAME_M_CostType_ID))
			{
				p_M_CostType_ID = para[i].getParameterAsInt();
			}
			else if (name.equals(I_M_Cost.COLUMNNAME_M_CostElement_ID))
			{
				p_M_CostElement_ID = para[i].getParameterAsInt();
			}
			else if (name.equals(I_M_Product.COLUMNNAME_M_Product_Category_ID))
			{
				p_M_Product_Category_ID = para[i].getParameterAsInt();
			}
			else if (name.equals(I_M_Cost.COLUMNNAME_M_Product_ID))
			{
				p_M_Product_ID = para[i].getParameterAsInt();
			}
			else if (name.equals(I_M_Cost.COLUMNNAME_M_AttributeSetInstance_ID))
			{
				p_M_AttributeSetInstance_ID = para[i].getParameterAsInt();
			}
			else
				log.error("prepare - Unknown Parameter: " + name);
		}
	}	// prepare

	@Override
	protected String doIt() throws Exception
	{
		final I_C_AcctSchema as = InterfaceWrapperHelper.create(getCtx(), p_C_AcctSchema_ID, I_C_AcctSchema.class, ITrx.TRXNAME_None);

		int count_costs = 0, count_all = 0;
		for (final int org_id : getOrgs(as))
		{
			for (final I_M_Product product : getProducts())
			{
				final int product_id = product.getM_Product_ID();
				
				for (final I_M_CostElement element : getElements())
				{
					CostDimension d = new CostDimension(getAD_Client_ID(), org_id,
							product_id,
							0, // ASI=0
							p_M_CostType_ID,
							as.getC_AcctSchema_ID(),
							element.getM_CostElement_ID()
							);
					I_M_Cost cost = d.toQuery(I_M_Cost.class, get_TrxName()).firstOnly(I_M_Cost.class);
					if (cost == null)
					{
						cost = new MCost(product, d.getM_AttributeSetInstance_ID(), as,
								d.getAD_Org_ID(), d.getM_CostElement_ID());
						cost.setM_CostType_ID(d.getM_CostType_ID());
						InterfaceWrapperHelper.save(cost, get_TrxName());
						count_costs++;
					}
					count_all++;
				}
			}
		}

		return "@Created@ #" + count_costs + " / " + count_all;
	}

	/**
	 * get the IDs for Organization and Valid the Cost Level
	 * 
	 * @param as Account Schema
	 * @return array of IDs
	 */
	private List<Integer> getOrgs(final I_C_AcctSchema as)
	{
		// Set the Costing Level
		final String CostingLevel = as.getCostingLevel();
		if (X_C_AcctSchema.COSTINGLEVEL_Client.equals(CostingLevel))
		{
			p_AD_Org_ID = 0;
			p_M_AttributeSetInstance_ID = 0;
			return Collections.singletonList(0);
		}

		final IQueryBuilder<I_AD_Org> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_AD_Org.class, this)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient();

		if (p_AD_Org_ID != null)
		{
			queryBuilder.addEqualsFilter(I_AD_Org.COLUMNNAME_AD_Org_ID, p_AD_Org_ID);
		}

		return queryBuilder
				.create()
				.listIds();
	}

	private Collection<I_M_CostElement> getElements()
	{
		if (m_costElements != null)
		{
			return m_costElements;
		}

		final IQueryBuilder<I_M_CostElement> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_CostElement.class, this)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient();

		if (p_M_CostElement_ID > 0)
		{
			queryBuilder.addEqualsFilter(I_M_CostElement.COLUMNNAME_M_CostElement_ID, p_M_CostElement_ID);
		}

		m_costElements = queryBuilder
				.create()
				.list();

		return m_costElements;
	}

	private Collection<I_M_CostElement> m_costElements = null;

	private List<I_M_Product> getProducts()
	{
		if (m_products != null)
		{
			return m_products;
		}

		final IQueryBuilder<I_M_Product> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Product.class, this)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient();

		if (p_M_Product_Category_ID > 0)
		{
			queryBuilder.addEqualsFilter(I_M_Product.COLUMNNAME_M_Product_Category_ID, p_M_Product_Category_ID);

			p_M_Product_ID = 0;
		}

		if (p_M_Product_ID > 0)
		{
			queryBuilder.addEqualsFilter(I_M_Product.COLUMNNAME_M_Product_ID, p_M_Product_ID);
		}
		else
		{
			p_M_AttributeSetInstance_ID = 0;
		}

		m_products = queryBuilder
				.create()
				.list();
		
		return m_products;
	}

	private List<I_M_Product> m_products = null;
}	// Create Cost Element
