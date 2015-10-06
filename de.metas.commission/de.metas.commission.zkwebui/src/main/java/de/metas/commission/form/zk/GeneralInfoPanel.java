/**
 * 
 */
package de.metas.commission.form.zk;

/*
 * #%L
 * de.metas.commission.zkwebui
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
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.exceptions.DBException;
import org.adempiere.util.Check;
import org.adempiere.webui.component.Column;
import org.adempiere.webui.component.Columns;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.ToolBarButton;
import org.compiere.model.MBPartner;
import org.compiere.model.MUser;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zhtml.Div;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.impl.XulElement;

import de.metas.commission.model.I_C_AdvComSponsorPoints_v1;

/**
 * @author teo_sarca
 *
 */
public class GeneralInfoPanel extends Grid
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6892484096399279457L;
	
	private final CLogger log = CLogger.getCLogger(getClass());
	
	private final Rows rows;
	private final org.zkoss.zul.Label lblSponsorNo = new org.zkoss.zul.Label();
	private final org.zkoss.zul.Label lblName = new org.zkoss.zul.Label();
//	private final org.zkoss.zul.Label lblAddress = new org.zkoss.zul.Label();
	private final org.zkoss.zul.Label lblPhone = new org.zkoss.zul.Label();
	private final ToolBarButton lblEMail = new ToolBarButton();
	private final org.zkoss.zul.Label lblPoints_Predicted = new org.zkoss.zul.Label();
	private final org.zkoss.zul.Label lblPoints_Calculated = new org.zkoss.zul.Label();
	private final org.zkoss.zul.Label lblPV = new org.zkoss.zul.Label();
	private final org.zkoss.zul.Label lblAPV = new org.zkoss.zul.Label();
	private final org.zkoss.zul.Label lbl6BDL = new org.zkoss.zul.Label();
	
	private final List<XulElement> fields = new ArrayList<XulElement>();

	private SponsorTreeNode node;
	
	public GeneralInfoPanel()
	{
		super();
		makeNoStrip();
		{
			rows = newRows();
	    	Columns columns = new Columns();
	    	this.appendChild(columns);
	    	Column col = new Column();
	    	col.setStyle("width: 40%");
	    	columns.appendChild(col);
	    	col = new Column();
	    	col.setStyle("width: 60%");
	    	columns.appendChild(col);
		}
		
		addLine("SponsorNo", lblSponsorNo);
		addLine("Name", lblName);
//		addLine("Address", lblAddress);
		addLine("Phone", lblPhone);
		addLine("EMail", lblEMail);
//		addLine("Points_Predicted", lblPoints_Predicted);
		addLine("Points_Calculated", lblPoints_Calculated);
		addLine(I_C_AdvComSponsorPoints_v1.COLUMNNAME_PersonalVolume, lblPV);
		addLine(I_C_AdvComSponsorPoints_v1.COLUMNNAME_AbsolutePersonalVolume, lblAPV);
		addLine(I_C_AdvComSponsorPoints_v1.COLUMNNAME_AbsoluteDownlineVolume, lbl6BDL);
	}
	
	private void addLine(String label, XulElement valueLabel)
	{
		Label l = new Label(Msg.translate(Env.getCtx(), label));
		
		Row row = rows.newRow();
		
		Div div = new Div();
//		div.setStyle("align: right; border: 1px solid red;");
		div.appendChild(l);
		row.appendChild(div);
		
		div = new Div();
//		div.setStyle("align: left; border: 1px solid red;");
		div.appendChild(valueLabel);
		row.appendChild(div);
		
		fields.add(valueLabel);
	}
	
	private void resetFieldValues()
	{
		for (XulElement e: fields)
		{
			setValue(e, "", null);
		}
	}
	
	private static final void setValue(XulElement e, String value, String url)
	{
		if (e instanceof org.zkoss.zul.Label)
			((org.zkoss.zul.Label)e).setValue(value);
		else if (e instanceof Toolbarbutton)
		{
			((Toolbarbutton)e).setLabel(value);
			if (!Check.isEmpty(url, true))
				((Toolbarbutton)e).setHref(url);
		}
	}
	
	public void setSponsorTreeNode(SponsorTreeNode node)
	{
		this.node = node;
//		final long ts = System.currentTimeMillis();
		
		resetFieldValues();
		if (node.getBPartner_ID() <= 0)
		{
			log.warning("No C_BPartner_ID found - "+node);
			return;
		}
		MBPartner bp = MBPartner.get(Env.getCtx(), node.getBPartner_ID());
		if (bp == null)
		{
			log.warning("No C_BPartner record found - "+node);
			return;
		}
		//
		MUser contact = null;
		String phone = "-";
		String email = "-";
		int AD_User_ID = bp.getPrimaryAD_User_ID();
		if (AD_User_ID > 0)
			contact = MUser.get(Env.getCtx(), AD_User_ID);
		if (contact != null)
		{
			phone = contact.getPhone();
			email = contact.getEMail();
		}
		//
		setValue(lblSponsorNo, node.getSponsorNo()+" - "+node.getSponsorTypeName(), null);
		setValue(lblName, node.getBPName(), null);
//		lblAddress.setValue(locationStr);
		setValue(lblPhone, phone, null);
		setValue(lblEMail, email, Check.isEmpty(email, true) ? null : "mailto:"+email);
		//
		loadCommisionPoints(node);
		//
		final I_C_AdvComSponsorPoints_v1 points = node.getPoints();
		setLabel(lblPV, points != null ? points.getPersonalVolume() : null, DisplayType.Integer);
		setLabel(lblAPV, points != null ? points.getAbsolutePersonalVolume() : null, DisplayType.Integer);
		setLabel(lbl6BDL, points != null ? points.getAbsoluteDownlineVolume() : null, DisplayType.Integer);
		//
//		System.out.println("***setSponsorTreeNode: "+node+" - "+(System.currentTimeMillis() - ts)+"ms");
		
	}
	
	public SponsorTreeNode getSponsorTreeNode()
	{
		return this.node;
	}
	
	private void loadCommisionPoints(SponsorTreeNode node)
	{
//		final long ts = System.currentTimeMillis();
		
		int month = node.getTreeModel().getMonth();
		int year = node.getTreeModel().getYear();
		
		final String sql = "SELECT * FROM P_AdvComSponsor_Points"
			+" WHERE C_Sponsor_ID=? AND Month=? AND Year=?"
		;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			DB.setParameters(pstmt, new Object[]{node.getSponsor_ID(), month, year});
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				String status = rs.getString("Status");
				BigDecimal points = rs.getBigDecimal("Points");
				if (points == null)
					points = Env.ZERO;
				points = points.setScale(0, RoundingMode.HALF_UP);
				//
				if ("PR".equals(status))
				{
					lblPoints_Predicted.setValue(points.toString());
				}
				else if ("CA".equals(status))
				{
					lblPoints_Calculated.setValue(points.toString());
				}
				else
				{
					log.warning("Unknown status="+status+", C_Sponsor_ID="+node.getSponsor_ID()+", month="+month+", year="+year);
				}
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
			//
//			System.out.println("***loadCommisionPoints: "+node+" - "+(System.currentTimeMillis() - ts)+"ms");
		}
	}
	
	private static final void setLabel(org.zkoss.zul.Label label, Object value, int displayType)
	{
		if (value == null)
		{
			label.setValue("");
		}
		else if (DisplayType.isNumeric(displayType))
		{
			Format f = DisplayType.getNumberFormat(displayType);
			label.setValue(f.format(value));
		}
		else if (DisplayType.isDate(displayType))
		{
			Format f = DisplayType.getDateFormat(displayType);
			label.setValue(f.format(value));
		}
		else
		{
			label.setValue(value.toString());
		}
	}
}
