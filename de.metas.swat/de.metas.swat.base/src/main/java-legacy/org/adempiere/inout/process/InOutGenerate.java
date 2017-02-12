/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                        *
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
package org.adempiere.inout.process;

/*
 * #%L
 * de.metas.swat.base
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


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.adempiere.exceptions.DBException;
import org.adempiere.inout.shipment.IShipmentBL;
import org.adempiere.inout.shipment.ShipmentParams;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.process.DocAction;
import org.compiere.util.AdempiereUserError;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.inout.model.I_M_InOut;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 * Generate Shipments. Manual or Automatic
 *
 * New features by metas
 * <ul>
 * <li>Option to create shipments in order of ordering, even if a bpartner has
 * been selected explicitly (prevents a bPartner from "starving")</li>
 * <li>Option to evaluate a bPartner's postage-free amount. If used, shipments
 * are created only for orders above a certain value</li>
 * </ul>
 *
 * NOTE: this is metas's modified version of ADempiere's org.compiere.process.InOutGenerate
 *
 * @author Jorg Janke
 * @author t.schoeneberg@metas.de
 */
public final class InOutGenerate extends JavaProcess
{
	public static final String PARAM_Selection = "Selection";
	public static final String PARAM_M_Warehouse_ID = "M_Warehouse_ID";
	public static final String PARAM_DocAction = "DocAction";
	public static final String PARAM_IsUnconfirmedInOut = "IsUnconfirmedInOut";
	public static final String PARAM_IgnorePostageFreeAmount = "IgnorePostageFreeAmount";
	private static final String PARAM_PREFER_BPARTNER = "PreferBPartner";

	private static String SQL_SELECT_SELECTED_ORDERLINE_IDS = //
	" SELECT ol.C_OrderLine_ID " //
			+ " FROM T_Selection s " //
			// + "    LEFT JOIN C_Order o ON s.T_Selection_ID=o.C_Order_ID " //
			// + "    LEFT JOIN C_OrderLine ol ON o.C_Order_ID=ol.C_Order_ID "
			// //
			+ "    LEFT JOIN C_OrderLine ol ON s.T_Selection_ID=ol.C_OrderLine_ID " //
			+ " WHERE s.AD_PInstance_ID=?";

	/** Manual Selection */
	private boolean p_Selection = false;
	/** Warehouse */
	private int p_M_Warehouse_ID = 0;
	/** BPartner */
	private int p_C_BPartner_ID = 0;
	/** Promise Date */
	private Timestamp p_DatePromised = null;

	/** Include Orders w. unconfirmed Shipments */
	// metas: consider unconfirmed shipments by default
	private boolean p_IsIncludeWhenIncompleteInOutExists = false;

	/** DocAction */
	private String p_docAction = DocAction.ACTION_Complete;
	/** Consolidate */
	private boolean p_ConsolidateDocument = true;
	/** Shipment Date */
	private Timestamp p_DateShipped = null;

	// metas: additional parameters and members
	/**
	 * Create a shipment, even if the total price is below the bPartner's
	 * postage free amount.
	 */
	private boolean p_ignorePostageFreeAmount = false;

	/**
	 * If if a bPartner has been selected, create a shipment for that bPArtner,
	 * even if there are other bPartners that are actually further up in the
	 * queue. That is the old behavior of this process.
	 */
	private boolean p_preferBPartner = false;

	// metas end

	/** Number of Shipments */
	private int m_created = 0;

	/** Movement Date */
	private Timestamp m_movementDate = null;

	/**
	 * Prepare - e.g., get Parameters.
	 */
	@Override
	protected void prepare() {

		ProcessInfoParameter[] para = getParametersAsArray();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals(PARAM_M_Warehouse_ID))
				p_M_Warehouse_ID = para[i].getParameterAsInt();
			else if (name.equals("C_BPartner_ID"))
				p_C_BPartner_ID = para[i].getParameterAsInt();
			else if (name.equals("DatePromised"))
				p_DatePromised = (Timestamp) para[i].getParameter();
			else if (name.equals(PARAM_Selection))
				p_Selection = "Y".equals(para[i].getParameter());
			else if (name.equals(PARAM_IsUnconfirmedInOut))
				p_IsIncludeWhenIncompleteInOutExists = "Y".equals(para[i].getParameter());
			else if (name.equals("ConsolidateDocument"))
				p_ConsolidateDocument = "Y".equals(para[i].getParameter());
			else if (name.equals(PARAM_DocAction))
				p_docAction = (String) para[i].getParameter();
			else if (name.equals("MovementDate"))
				p_DateShipped = (Timestamp) para[i].getParameter();
			// metas
			else if (name.equals(PARAM_PREFER_BPARTNER))
				p_preferBPartner = "Y".equals(para[i].getParameter());
			else if (name.equals(PARAM_IgnorePostageFreeAmount))
				p_ignorePostageFreeAmount = "Y".equals(para[i].getParameter());
			// metas end
			else
				log.error("Unknown Parameter: " + name);

			// juddm - added ability to specify a shipment date from Generate
			// Shipments
			if (p_DateShipped == null) {
				m_movementDate = Env.getContextAsDate(getCtx(), "#Date");
				if (m_movementDate == null)
					m_movementDate = SystemTime.asTimestamp();
			} else
				m_movementDate = p_DateShipped;
			// DocAction check
			if (!DocAction.ACTION_Complete.equals(p_docAction))
				p_docAction = DocAction.ACTION_Prepare;
		}
	} // prepare

	/**
	 * Generate Shipments
	 *
	 * @return info
	 * @throws Exception
	 */
	@Override
	protected String doIt() throws Exception
	{
		log.info("Selection=" + p_Selection + ", M_Warehouse_ID="
				+ p_M_Warehouse_ID + ", C_BPartner_ID=" + p_C_BPartner_ID
				+ ", Consolidate=" + p_ConsolidateDocument + ", IsUnconfirmed="
				+ p_IsIncludeWhenIncompleteInOutExists + ", Movement=" + m_movementDate
				+ ", IgnorePostageFreeAmt=" + p_ignorePostageFreeAmount);

		if (p_M_Warehouse_ID == 0)
			throw new AdempiereUserError("@NotFound@ @M_Warehouse_ID@");

		final ShipmentParams params = new ShipmentParams();

		if (p_Selection) // VInOutGen
		{
			// instead of selecting only the "selected" orders as was the old
			// behavior, we create a list of the selected order ids

			params.setSelectedOrderLineIds(retrieveSelectedOrderIds());
		}

		params.setAdPInstanceId(getAD_PInstance_ID());
		params.setAdUserId(getAD_User_ID());

		params.setBPartnerId(p_C_BPartner_ID);
		params.setConsolidateDocument(p_ConsolidateDocument);
		params.setDatePromised(p_DatePromised);
		params.setMovementDate(m_movementDate);
		params.setPreferBPartner(p_preferBPartner);
		params.setIncludeWhenIncompleteInOutExists(p_IsIncludeWhenIncompleteInOutExists);
		params.setWarehouseId(p_M_Warehouse_ID);
		params.setIgnorePostageFreeamount(p_ignorePostageFreeAmount);


		final IShipmentBL shipmentBL = Services.get(IShipmentBL.class);
		final Iterator<I_M_InOut> shipments = shipmentBL.generateShipments(getCtx(), params, p_docAction, get_TrxName());
		while(shipments.hasNext())
		{
			final I_M_InOut currentShipment = shipments.next();
			addLog(currentShipment.getM_InOut_ID(),
					currentShipment.getMovementDate(),
					null, // number
					currentShipment.getDocumentNo());
			m_created++;
		}

		return "@Created@ = " + m_created;

	} // doIt

	private Set<Integer> retrieveSelectedOrderIds()
	{
		final Set<Integer> orderLineIds = new HashSet<>();

		final String trxName = get_TrxName();
		final String sql = SQL_SELECT_SELECTED_ORDERLINE_IDS;
		final List<Object> sqlParams = Arrays.asList((Object)getAD_PInstance_ID());
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			DB.setParameters(pstmt, sqlParams);

			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final int orderLineId = rs.getInt(1);
				orderLineIds.add(orderLineId);
			}
			return orderLineIds;
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}
} // InOutGenerate

