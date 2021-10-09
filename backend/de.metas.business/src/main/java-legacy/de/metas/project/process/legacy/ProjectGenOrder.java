/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
package de.metas.project.process.legacy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import org.compiere.model.I_C_ProjectLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProject;
import org.compiere.model.MProjectLine;
import org.compiere.util.Env;

import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;

/**
 *  Generate Sales Order from Project.
 *
 *	@author Jorg Janke
 *	@version $Id: ProjectGenOrder.java,v 1.3 2006/07/30 00:51:01 jjanke Exp $
 *
 * @deprecated To be deleted.
 */
@Deprecated
public class ProjectGenOrder extends JavaProcess
{
	/**	Project ID from project directly		*/
	private int		m_C_Project_ID = 0;

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else
				log.error("Unknown Parameter: " + name);
		}
		m_C_Project_ID = getRecord_ID();
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	@Override
	protected String doIt() throws Exception
	{
		log.info("C_Project_ID=" + m_C_Project_ID);
		if (m_C_Project_ID == 0)
			throw new IllegalArgumentException("C_Project_ID == 0");
		MProject fromProject = getProject (getCtx(), m_C_Project_ID, get_TrxName());
		Env.setSOTrx(getCtx(), true);	//	Set SO context

		/** @todo duplicate invoice prevention */

		MOrder order = new MOrder (fromProject, true, MOrder.DocSubType_OnCredit);
		if (!order.save())
			throw new Exception("Could not create Order");

		//	***	Lines ***
		int count = 0;
		
		//	Service Project	
		if (MProject.PROJECTCATEGORY_ServiceChargeProject.equals(fromProject.getProjectCategory()))
		{
			/** @todo service project invoicing */
			throw new Exception("Service Charge Projects are on the TODO List");
		}	//	Service Lines

		else	//	Order Lines
		{
			final List<MProjectLine> lines = fromProject.getLines();
			for (I_C_ProjectLine line : lines)
			{
				MOrderLine ol = new MOrderLine(order);
				ol.setLine(line.getLine());
				ol.setDescription(line.getDescription());
				//
				ol.setM_Product_ID(line.getM_Product_ID(), true);
				ol.setQty(line.getPlannedQty().subtract(line.getInvoicedQty()));
				ol.setPrice();
				if (line.getPlannedPrice() != null && line.getPlannedPrice().compareTo(Env.ZERO) != 0)
					ol.setPrice(line.getPlannedPrice());
				updateDiscount(ol);
				ol.setTax();
				if (ol.save())
					count++;
			}	//	for all lines
			if (lines.size() != count)
				log.error("Lines difference - ProjectLines=" + lines.size() + " <> Saved=" + count);
		}	//	Order Lines

		return "@C_Order_ID@ " + order.getDocumentNo() + " (" + count + ")";
	}	//	doIt

	public void updateDiscount(final MOrderLine ol)
	{
		BigDecimal list = ol.getPriceList();
		// No List Price
		if (BigDecimal.ZERO.compareTo(list) == 0)
			return;
		// final int precision = getPrecision();
		final int precision = 1; // metas
		// TODO: metas: why we are using precision=1 instead of getPrecision()?
		BigDecimal discount = list.subtract(ol.getPriceActual())
				.multiply(new BigDecimal(100))
				.divide(list, precision, BigDecimal.ROUND_HALF_UP);
		ol.setDiscount(discount);
	}	// setDiscount

	/**
	 * 	Get and validate Project
	 * 	@param ctx context
	 * 	@param C_Project_ID id
	 * 	@return valid project
	 * 	@param trxName transaction
	 */
	static protected MProject getProject (Properties ctx, int C_Project_ID, String trxName)
	{
		MProject fromProject = new MProject (ctx, C_Project_ID, trxName);
		if (fromProject.getC_Project_ID() == 0)
			throw new IllegalArgumentException("Project not found C_Project_ID=" + C_Project_ID);
		if (fromProject.getM_PriceList_Version_ID() == 0)
			throw new IllegalArgumentException("Project has no Price List");
		if (fromProject.getM_Warehouse_ID() == 0)
			throw new IllegalArgumentException("Project has no Warehouse");
		if (fromProject.getC_BPartner_ID() == 0 || fromProject.getC_BPartner_Location_ID() == 0)
			throw new IllegalArgumentException("Project has no Business Partner/Location");
		return fromProject;
	}	//	getProject

}	//	ProjectGenOrder
