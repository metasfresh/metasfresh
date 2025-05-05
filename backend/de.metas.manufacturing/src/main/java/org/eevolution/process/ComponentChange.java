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

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import de.metas.ad_reference.ADReferenceService;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.Query;
import org.eevolution.model.I_PP_Product_BOMLine;

import de.metas.material.planning.pporder.LiberoException;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;

/**
 *	Component Change into BOM
 *	
 *  @author victor.perez@e-evolution.com
 *  @version $Id: ComponentChange.java
 *  
 *  @author Teo Sarca, www.arhipac.ro
 */
public class ComponentChange extends JavaProcess
{
	private static final int	ACTION_AD_Reference_ID	= 53227;
	private static final String ACTION_Add				= "A";
	private static final String ACTION_Deactivate		= "D";
	private static final String ACTION_Expire			= "E";
	private static final String ACTION_Replace			= "R";
	private static final String ACTION_ReplaceAndExpire = "RE";
	
	private int			 	p_M_Product_ID = 0;     
	private Timestamp       p_ValidTo = null;
	private Timestamp       p_ValidFrom = null;
	private String          p_Action;
	private int             p_New_M_Product_ID =0;
	private BigDecimal      p_Qty = null;
	private int 			p_M_ChangeNotice_ID=0;

	@Override
	protected void prepare()
	{
		int morepara = 0;

		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			final String name = para.getParameterName();

			if (para.getParameter() == null)
				return;
			else if (name.equals("M_Product_ID") && morepara == 0)
			{    
				p_M_Product_ID = para.getParameterAsInt();
				morepara = 1;
			}
			else if (name.equals("ValidTo"))
				p_ValidTo = ((Timestamp)para.getParameter());
			else if (name.equals("ValidFrom")) 
				p_ValidFrom = ((Timestamp)para.getParameter());
			else if (name.equals("Action"))
				p_Action = ((String)para.getParameter());
			else if (name.equals("M_Product_ID"))
				p_New_M_Product_ID = para.getParameterAsInt();
			else if (name.equals("Qty")) 
				p_Qty = ((BigDecimal)para.getParameter());
			else if (name.equals("M_ChangeNotice_ID")) 
				p_M_ChangeNotice_ID = para.getParameterAsInt();
			else
				log.error("prepare - Unknown Parameter: " + name);
		}
	}	//	prepare

	@Override
	protected String doIt()
	{
		if (p_Action == null)
		{
			throw new FillMandatoryException("Action");
		}

		final List<Object> params = new ArrayList<>();
		final StringBuffer whereClause = new StringBuffer();
		
		whereClause.append(I_PP_Product_BOMLine.COLUMNNAME_M_Product_ID+"=?");
		params.add(p_M_Product_ID);
		
		if (p_ValidTo != null)
		{
			whereClause.append(" AND TRUNC("+I_PP_Product_BOMLine.COLUMNNAME_ValidTo+") <= ?");
			params.add(p_ValidTo);
		}
		if (p_ValidFrom != null)
		{
			whereClause.append(" AND TRUNC("+I_PP_Product_BOMLine.COLUMNNAME_ValidFrom+") >= ?");
			params.add(p_ValidFrom);
		}

		final List<I_PP_Product_BOMLine> components = new Query(getCtx(), I_PP_Product_BOMLine.Table_Name, whereClause.toString(), get_TrxName())
													.setParameters(params)
													.list(I_PP_Product_BOMLine.class);
		for (final I_PP_Product_BOMLine bomline : components)
		{		
			if (p_Action.equals(ACTION_Add))
			{
				actionAdd(bomline, 0);
			}
			else if (p_Action.equals(ACTION_Deactivate))
			{
				actionDeactivate(bomline);
			}
			else if (p_Action.equals(ACTION_Expire))
			{
				actionExpire(bomline);
			}
			else if (p_Action.equals(ACTION_Replace))
			{
				actionAdd(bomline, bomline.getLine() + 1);
				actionDeactivate(bomline);
			}
			else if (p_Action.equals(ACTION_ReplaceAndExpire))
			{
				actionAdd(bomline, bomline.getLine() + 1);
				actionExpire(bomline);
			}
			else
			{
				throw new LiberoException("Action not supported - "+p_Action);
			}
			addLog(ADReferenceService.get().retrieveListNameTrl(getCtx(), ACTION_AD_Reference_ID, p_Action));
		}                    
		return "@OK@";
	}	//	doIt

	protected void actionAdd(final I_PP_Product_BOMLine bomline, final int line)
	{
		final I_PP_Product_BOMLine newbomline = InterfaceWrapperHelper.copy()
				.setFrom(bomline)
				.copyToNew(I_PP_Product_BOMLine.class);
		newbomline.setIsActive(true);
		newbomline.setLine(line);
		newbomline.setM_ChangeNotice_ID(p_M_ChangeNotice_ID);
		//
		newbomline.setM_Product_ID(p_New_M_Product_ID);
		if (p_Qty.signum() != 0)
		{
			newbomline.setQtyBOM(p_Qty);
		}
		newbomline.setValidFrom(newbomline.getUpdated());
		saveRecord(newbomline);
	}

	protected void actionDeactivate(final I_PP_Product_BOMLine bomline)
	{
		bomline.setIsActive(false);
		bomline.setM_ChangeNotice_ID(p_M_ChangeNotice_ID);
		saveRecord(bomline);
	}

	protected void actionExpire(final I_PP_Product_BOMLine bomline)
	{
		bomline.setIsActive(true);
		bomline.setValidTo(bomline.getUpdated());
		bomline.setM_ChangeNotice_ID(p_M_ChangeNotice_ID);
		saveRecord(bomline);
	}
}	//	Component Change
