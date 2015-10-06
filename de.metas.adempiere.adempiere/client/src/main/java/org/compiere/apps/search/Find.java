package org.compiere.apps.search;

/*
 * #%L
 * ADempiere ERP - Desktop Client
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


import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTextField;

import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.apps.AEnv;
import org.compiere.apps.ConfirmPanel;
import org.compiere.model.GridField;
import org.compiere.model.MQuery;
import org.compiere.swing.CDialog;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

/**
 * Find/Search Records.
 * 
 * @author Teo Sarca
 */
public final class Find extends CDialog
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4199738941808772082L;

	public static final FindPanelBuilder builder()
	{
		return new FindPanelBuilder();
	}

	private final FindPanel findPanel;

	Find(final FindPanelBuilder builder)
	{
		super(builder.getParentFrame(),
				Services.get(IMsgBL.class).getMsg(Env.getCtx(), "Find") + ": " + builder.getTitle(),
				true // modal=true
		);

		findPanel = builder.buildFindPanel();

		// metas: tsa: begin
		if (findPanel.isDisposed())
		{
			this.dispose();
			return;
		}
		// metas: tsa: begin
		findPanel.addActionListener(this);
		this.add(findPanel);
		// teo_sarca, [ 1670847 ] Find dialog: closing and canceling need same
		// functionality
		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				findPanel.doCancel();
			}
		});

		AEnv.showCenterWindow(builder.getParentFrame(), this);
	} // Find

	/** Logger */
	private CLogger log = CLogger.getCLogger(getClass());

	@Override
	public void dispose()
	{
		log.config("");
		findPanel.dispose();
		removeAll();
		super.dispose();
	} // dispose

	@Override
	public void actionPerformed(ActionEvent e)
	{
		log.info(e.getActionCommand());
		//
		if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			dispose();
		}
		else if (e.getActionCommand().equals(ConfirmPanel.A_NEW))
		{
			dispose();
		}
		else if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			dispose();
		}
		// Enter pressed on FindPanel search fields:
		else if (e.getSource() instanceof JTextField
				&& findPanel.getTotalRecords() > 0)
		{
			dispose();
		}
	} // actionPerformed

	/**************************************************************************
	 * Get Query - Retrieve result
	 * 
	 * @return String representation of query
	 */
	public MQuery getQuery()
	{
		return findPanel.getQuery();
	} // getQuery

	/**
	 * @return true if cancel button pressed
	 */
	public boolean isCancel()
	{
		return findPanel.isCancel();
	}

	/**
	 * Get Target MField
	 * 
	 * @param columnName
	 *            column name
	 * @return MField
	 */
	public GridField getTargetMField(String columnName)
	{
		return findPanel.getTargetMField(columnName);
	} // getTargetMField
} // Find
