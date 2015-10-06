/**
 * 
 */
package org.adempiere.webui.panel;

/*
 * #%L
 * ADempiere ERP - ZkWebUI Lib
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


import java.util.List;

import org.adempiere.webui.component.Window;
import org.compiere.apps.search.IInfoSimple;
import org.compiere.apps.search.IInfoQueryCriteria;
import org.compiere.model.I_AD_InfoColumn;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;


/**
 * @author cg
 *
 */
public class InfoQueryCriteriaASIPanel extends Window implements IInfoQueryCriteria
{
	private static final long serialVersionUID = 8336368589932924531L;

	private IInfoSimple parent;

	private I_AD_InfoColumn infoColumn;

	private String m_WhereClause = "";

	private Button m_Button = new Button();

	private String label;

	@Override
	public void init(final IInfoSimple parent, I_AD_InfoColumn infoColumn, String searchText)
	{
		this.parent = parent;
		this.infoColumn = infoColumn;
		this.label = Msg.translate(Env.getCtx(), infoColumn.getAD_Element().getColumnName());
		initUI();
	}
	
	private void initUI()
	{
		m_Button = new Button();
		m_Button.setImage("/images/PAttribute16.png");
		m_Button.setTooltiptext(Msg.getMsg(Env.getCtx(), "PAttribute"));
		m_Button.setDisabled(false);
		m_Button.addEventListener(Events.ON_CLICK, new EventListener()
		{
			@Override
			public void onEvent(Event event) throws Exception
			{
				createWindow();
				parent.executeQuery();
			}
		});
	}

	@Override
	public String[] getWhereClauses(List<Object> params)
	{
		return new String[]{m_WhereClause};
	}

	private void createWindow()
	{
		InfoPAttributePanel ia = new InfoPAttributePanel((Window)parent);
		String whereClause = ia.getWhereClause();
		if (whereClause != null)
			m_WhereClause = whereClause.replaceAll("p.", "M_Product.");
	}

	@Override
	public I_AD_InfoColumn getAD_InfoColumn()
	{
		return infoColumn;
	}

	@Override
	public int getParameterCount()
	{
		return 1;
	}

	@Override
	public Object getParameterComponent(int index)
	{
		if (index == 0)
			return m_Button;
		return null;
	}

	@Override
	public Object getParameterToComponent(int index)
	{
		return null;
	}

	@Override
	public String getLabel(int index)
	{
		if(index == 0)
			return label;
		return null;
	}

	@Override
	public Object getParameterValue(int index, boolean returnValueTo)
	{
		return null;
	}

	@Override
	public String getText()
	{
		return null;
	}
}
