/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2009 SC ARHIPAC SERVICE SRL. All Rights Reserved.            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.compiere.grid.ed;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.text.JTextComponent;

import org.adempiere.exceptions.DBException;
import org.compiere.model.MSysConfig;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * 
 * @author Cristina Ghita , www.arhipac.ro
 *
 */
public class CityAutoCompleter extends AutoCompleter 
{
	public static final CityVO ITEM_More = new CityVO(-1, "...", -1, "");
	private final int m_maxRows = MSysConfig.getIntValue("LOCATION_MAX_CITY_ROWS", 7);
	private CityVO m_city = null;
	private final int m_windowNo;
	private ArrayList<CityVO> list = new ArrayList<CityVO>();
	private ArrayList<CityVO> listShow = new ArrayList<CityVO>();

	public CityAutoCompleter(JTextComponent comp, int windowNo)
	{ 
		super(comp);
		this.m_windowNo = windowNo;
		listBox.setVisibleRowCount(m_maxRows);
		setCity(null);
	}
	
	
	@Override
	protected void acceptedListItem(Object selected)
	{
		if (selected == null || selected == ITEM_More)
		{
			setCity(null);
			return;
		}
		CityVO item = (CityVO) selected;
		setCity(item);
		Env.setContext(Env.getCtx(), m_windowNo, Env.TAB_INFO, "C_Region_ID", String.valueOf(item.C_Region_ID));
		textBox.setText(item.CityName);
	}

	@Override
	protected boolean updateListData()
	{
		String search = textBox.getText();
		if (m_city != null && m_city.CityName.compareTo(search) != 0)
		{
			setCity(null);
		}
		listShow.clear();
		boolean truncated = false;
		search = search.toUpperCase();
		int i = 0;
		for (CityVO vo : list) {
			if (vo.CityName.toUpperCase().startsWith(search)) {
				if (i > 0 && i == m_maxRows+1)
				{
					listShow.add(ITEM_More);
					truncated = true;
					break;
				}
				listShow.add(vo);
				i++;
			}
		}
		this.listBox.setListData(listShow.toArray());
		//if there is no city on the list return false, to not show the popup
		if (listShow.isEmpty())
		{
			return false;
		}
		else
		{
			CityVO city = (CityVO) listShow.get(0);
			if (city.CityName.equalsIgnoreCase(search))
			{
				m_city = city;
				return true;
			}	
		}
		//if the list has only one item, but that item is not equals with m_city
		//return false to not show any popup
		if (!truncated && listShow.size() == 1
				&& m_city != null && listShow.get(0).equals(this.m_city))
		{
			log.finest("nothing to do 1");
			return false;
		}
		return true;
	}

	public void fillList()
	{
		// Carlos Ruiz - globalqss - improve to avoid going to the database on every keystroke
		list.clear();
		listShow.clear();
		ArrayList<Object> params = new ArrayList<Object>();
		final StringBuffer sql = new StringBuffer(
				"SELECT cy.C_City_ID, cy.Name, cy.C_Region_ID, r.Name"
				+" FROM C_City cy"
				+" LEFT OUTER JOIN C_Region r ON (r.C_Region_ID=cy.C_Region_ID)"
				+" WHERE cy.AD_Client_ID IN (0,?)");
		params.add(getAD_Client_ID());
		if (getC_Region_ID() > 0)
		{
			sql.append(" AND cy.C_Region_ID=?");
			params.add(getC_Region_ID());
		}		
		if (getC_Country_ID() > 0)
		{
			sql.append(" AND cy.C_Country_ID=?");
			params.add(getC_Country_ID());
		}
		sql.append(" ORDER BY cy.Name, r.Name");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			DB.setParameters(pstmt, params);
			rs = pstmt.executeQuery();
			int i = 0;
			while(rs.next())
			{
				CityVO vo = new CityVO(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4));
				list.add(vo);
				if (i <= m_maxRows) {
					listShow.add(vo);
				} else if (i == m_maxRows + 1 && i > 0) {
					listShow.add(ITEM_More);
				}
				i++;
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e, sql.toString());
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		//
		this.listBox.setListData(listShow.toArray());
	}

	private void setCity(CityVO vo)
	{
		m_city = vo;
		log.finest("C_City_ID="+m_city);
		if (m_city == null) {
			textBox.setBackground(new Color(230, 230, 255));
		} else {
			textBox.setBackground(Color.WHITE);
		}
	}
	public int getC_City_ID()
	{
		return m_city != null ? m_city.C_City_ID : -1;
	}
	public int getAD_Client_ID()
	{
		return Env.getAD_Client_ID(Env.getCtx());
	}
	public int getC_Country_ID()
	{
		return Env.getContextAsInt(Env.getCtx(), m_windowNo, Env.TAB_INFO, "C_Country_ID");
	}
	public int getC_Region_ID()
	{
		return Env.getContextAsInt(Env.getCtx(), m_windowNo, Env.TAB_INFO, "C_Region_ID");
	}
	
	public CityVO getCity()
	{
		return m_city;
	}

	public void mouseEntered(MouseEvent e) 
	{
		// nothing to do

	}

	public void mouseExited(MouseEvent e) 
	{
		// nothing to do

	}

	public void mousePressed(MouseEvent e) 
	{
		// nothing to do

	}

	public void mouseReleased(MouseEvent e) 
	{
		// nothing to do

	}

	public void mouseClicked(MouseEvent e) 
	{
		if(e == null || listBox.getSelectedValue().equals(ITEM_More))
		{
			setCity(null);
			return;
		}

		CityVO item = (CityVO)listBox.getSelectedValue();
		setCity(item);
		Env.setContext(Env.getCtx(), m_windowNo, Env.TAB_INFO, "C_Region_ID", String.valueOf(item.C_Region_ID));
		textBox.setText(item.CityName);
	}
}
