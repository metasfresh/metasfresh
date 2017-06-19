package org.compiere.apps.search;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.adempiere.images.Images;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.plaf.FindPanelUI;
import org.adempiere.util.Services;
import org.compiere.apps.AEnv;
import org.compiere.model.MQuery;
import org.compiere.swing.CDialog;
import org.slf4j.Logger;

import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
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

	/** Logger */
	private static final transient Logger log = LogManager.getLogger(Find.class);

	private final FindPanel findPanel;
	private final FindPanelActionListener findPanelActionListener = new FindPanelActionListener()
	{
		@Override
		public void onSearch(boolean triggeredFromSearchField)
		{
			if (triggeredFromSearchField && findPanel.getTotalRecords() <= 0)
			{
				return;
			}
			dispose();
		};

		@Override
		public void onCancel()
		{
			dispose();
		};

		@Override
		public void onOpenAsNewRecord()
		{
			dispose();
		};
	};

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
		
		findPanel.setActionListener(findPanelActionListener);

		// Set panel size
		// NOTE: we are setting such a big width because the table from advanced panel shall be displayed nicely.
		final int findPanelHeight = AdempierePLAF.getInt(FindPanelUI.KEY_Dialog_Height, FindPanelUI.DEFAULT_Dialog_Height);
		findPanel.setPreferredSize(new Dimension(950, findPanelHeight));

		setIconImage(Images.getImage2("Find24"));
		this.setContentPane(findPanel);

		// teo_sarca, [ 1670847 ] Find dialog: closing and canceling need same
		// functionality
		this.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowOpened(WindowEvent e)
			{
				findPanel.requestFocus();
			}
			
			@Override
			public void windowClosing(WindowEvent e)
			{
				findPanel.doCancel();
			}
		});

		AEnv.showCenterWindow(builder.getParentFrame(), this);
	} // Find


	@Override
	public void dispose()
	{
		log.info("");
		findPanel.dispose();
		removeAll();
		super.dispose();
	} // dispose

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
} // Find
