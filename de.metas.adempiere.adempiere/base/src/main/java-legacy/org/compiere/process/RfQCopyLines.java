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

import java.math.BigDecimal;
import java.util.logging.Level;

import org.compiere.model.MRfQ;
import org.compiere.model.MRfQLine;
import org.compiere.model.MRfQLineQty;


/**
 *	Copy Lines	
 *	
 *  @author Jorg Janke
 *  @version $Id: RfQCopyLines.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class RfQCopyLines extends SvrProcess
{
	/**	From RfQ 			*/
	private int		p_From_RfQ_ID = 0;
	/**	From RfQ 			*/
	private int		p_To_RfQ_ID = 0;

	/**
	 * 	Prepare
	 */
	protected void prepare ()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("C_RfQ_ID"))
				p_From_RfQ_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else
				log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
		}
		p_To_RfQ_ID = getRecord_ID();
	}	//	prepare

	/**
	 * 	Process
	 *	@see org.compiere.process.SvrProcess#doIt()
	 *	@return message
	 */
	protected String doIt () throws Exception
	{
		log.info("doIt - From_RfQ_ID=" + p_From_RfQ_ID + ", To_RfQ_ID=" + p_To_RfQ_ID);
		//
		MRfQ to = new MRfQ (getCtx(), p_To_RfQ_ID, get_TrxName());
		if (to.get_ID() == 0)
			throw new IllegalArgumentException("No To RfQ found");
		MRfQ from = new MRfQ (getCtx(), p_From_RfQ_ID, get_TrxName());
		if (from.get_ID() == 0)
			throw new IllegalArgumentException("No From RfQ found");
		
		//	Copy Lines
		int counter = 0;
		MRfQLine[] lines = from.getLines();
		for (int i = 0; i < lines.length; i++)
		{
			MRfQLine newLine = new MRfQLine (to);
			newLine.setLine(lines[i].getLine());
			newLine.setDescription(lines[i].getDescription());
			newLine.setHelp(lines[i].getHelp());
			newLine.setM_Product_ID(lines[i].getM_Product_ID());
			newLine.setM_AttributeSetInstance_ID(lines[i].getM_AttributeSetInstance_ID());
		//	newLine.setDateWorkStart();
		//	newLine.setDateWorkComplete();
			newLine.setDeliveryDays(lines[i].getDeliveryDays());
			newLine.save();
			//	Copy Qtys
			MRfQLineQty[] qtys = lines[i].getQtys();
			for (int j = 0; j < qtys.length; j++)
			{
				MRfQLineQty newQty = new MRfQLineQty (newLine);
				newQty.setC_UOM_ID(qtys[j].getC_UOM_ID());
				newQty.setQty(qtys[j].getQty());
				newQty.setIsOfferQty(qtys[j].isOfferQty());
				newQty.setIsPurchaseQty(qtys[j].isPurchaseQty());
				newQty.setMargin(qtys[j].getMargin());
				newQty.save();
			}
			counter++;
		}	//	copy all lines	
		
		//
		return "# " + counter;
	}	//	doIt
}
