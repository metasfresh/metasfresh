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
package org.adempiere.webui.window;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.adempiere.exceptions.DBException;
import org.adempiere.webui.component.AutoComplete;
import org.compiere.grid.ed.CityVO;
import org.compiere.model.MSysConfig;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Timer;



/**
 * @author Cristina Ghita - www.arhipac.ro
 *
 */
public class WAutoCompleterCity extends AutoComplete implements EventListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 318310285903469314L;

	private static final int PopupDelayMillis = 500;

	private final Timer timer = new Timer(PopupDelayMillis);

	private CityVO m_city = null;

	private ArrayList<CityVO> m_cities = new ArrayList<CityVO>();

	private ArrayList<CityVO> m_citiesShow = new ArrayList<CityVO>();

	private final int m_maxRows = MSysConfig.getIntValue("LOCATION_MAX_CITY_ROWS", 7);

	public static final CityVO ITEM_More = new CityVO(-1, "...", -1, "");

	private final int m_windowNo;

	public WAutoCompleterCity(int m_windowNo)
	{ 
		super();
		this.m_windowNo = m_windowNo;
		this.addEventListener(Events.ON_SELECT, this);
	}

	private void showPopupDelayed()
	{
		timer.setRepeats(false);
		timer.start();
	}

	@Override
	public void onChanging(InputEvent evt) 
	{
		showPopupDelayed();
		refreshData(evt.getValue());
		super.onChanging(evt);
	}

	public void refreshData(String val) 
	{
		String search = val;
		if (m_city != null && m_city.CityName.compareTo(search) != 0)
		{
			setCity(null);
		}
		m_citiesShow.clear();
		this.removeAllItems();
		this.setDict(null);
		this.setDescription(null);
		boolean truncated = false;
		search = search.toUpperCase();
		int i = 0;
		for (CityVO vo : m_cities) {
			if (vo.CityName.toUpperCase().startsWith(search)) {
				if (i > 0 && i == m_maxRows+1)
				{
					m_citiesShow.add(ITEM_More);
					truncated = true;
					break;
				}
				m_citiesShow.add(vo);
				i++;
			}
		}
		//if there is no city on the list return false, to not show the popup
		if (m_citiesShow.isEmpty())
		{
			return;
		}
		else
		{
			CityVO city = (CityVO) m_citiesShow.get(0);
			if (city.CityName.equalsIgnoreCase(search))
			{
				m_city = city;
				return;
			}	
		}
		//if the list has only one item, but that item is not equals with m_city
		//return false to not show any popup
		if (!truncated && m_citiesShow.size() == 1
				&& m_city != null && m_citiesShow.get(0).equals(this.m_city))
		{
			return;
		}
		
		String[] cityValues = new String[m_citiesShow.size()];
		String[] cityDesc = new String[m_citiesShow.size()];
		i = 0;
		for (CityVO vo : m_citiesShow) {
			cityValues[i] = vo.CityName;
			cityDesc[i] = vo.RegionName;
			i++;
		}
		//
		this.removeAllItems();
		this.setDict(cityValues);
		this.setDescription(cityDesc);
	}

	public void fillList()
	{
		// Carlos Ruiz - globalqss - improve to avoid going to the database on every keystroke
		m_cities.clear();
		m_citiesShow.clear();
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
				m_cities.add(vo);
				if (i <= m_maxRows) {
					m_citiesShow.add(vo);
				} else if (i == m_maxRows + 1 && i > 0) {
					m_citiesShow.add(ITEM_More);
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
		refreshData("");
	}

	private void setCity(CityVO vo)
	{
		m_city = vo;
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


	public void onEvent(Event event) throws Exception 
	{
		System.out.println("Event: " + event.getName());
		event.toString();
		int index = this.getSelectedIndex();
		System.out.println("Index = " +index	);
		if (index>=0)
		{
			CityVO city = (CityVO) m_citiesShow.get(index);
	
			if(event == null || city.equals(ITEM_More))
			{
				setCity(null);
				return;
			}
	
			setCity(city);
			Env.setContext(Env.getCtx(), m_windowNo, Env.TAB_INFO, "C_Region_ID", String.valueOf(city.C_Region_ID));
			this.setText(city.CityName);
		}
	}

}
