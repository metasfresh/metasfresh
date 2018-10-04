package de.metas.adempiere.gui.search;

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


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;

import org.adempiere.images.Images;
import org.adempiere.plaf.VEditorUI;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.search.IInfoQueryCriteria;
import org.compiere.apps.search.IInfoSimple;
import org.compiere.apps.search.InfoPAttribute;
import org.compiere.model.I_AD_InfoColumn;
import org.compiere.swing.CButton;
import org.compiere.util.SwingUtils;

import de.metas.util.Check;

/**
 * @author cg
 * 
 */
public class InfoQueryCriteriaASI implements IInfoQueryCriteria
{
	private IInfoSimple parent;
	private I_AD_InfoColumn infoColumn;
	private String m_WhereClause = "";
	private CButton m_Button = null;
	//private String label;

	@Override
	public void init(final IInfoSimple parent, I_AD_InfoColumn infoColumn, String searchText)
	{
		this.parent = parent;
		this.infoColumn = infoColumn;
		//this.label = Services.get(IMsgBL.class).translate(Env.getCtx(), infoColumn.getAD_Element().getColumnName());
		initUI();
	}
	
	private void initUI()
	{
		m_Button = new CButton(Images.getImageIcon2(ConfirmPanel.A_PATTRIBUTE + "16"));
		m_Button.setFocusable(false);
		m_Button.setEnabled(true);
		
		final int height = VEditorUI.getVEditorHeight();
		m_Button.setPreferredSize(new Dimension(height, height));
		m_Button.setMaximumSize(new Dimension(height, height));
		
		m_Button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
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
		final JFrame parentWindow = SwingUtils.getFrame(m_Button);
		final InfoPAttribute ia = new InfoPAttribute(parentWindow, "M_Product");
		String whereClause = ia.getWhereClause();
		if (Check.isEmpty(whereClause, true))
			return;

		m_WhereClause = whereClause.trim();
		// Fix returned query because it always starts with an AND
		if (m_WhereClause.startsWith("AND "))
			m_WhereClause = m_WhereClause.substring(4);
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
//		if(index == 0)
//			return label;
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
