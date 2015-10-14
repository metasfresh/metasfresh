package org.compiere.pos;

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

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;

import org.compiere.minigrid.MiniTable;

public class PosTable extends MiniTable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7884238751207398699L;

	public PosTable() {
		
		super();
		setRowSelectionAllowed(true);
		setColumnSelectionAllowed(false);
		setMultiSelection(false);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setRowHeight(30);
		setAutoResize(false);
	}
	
	public void growScrollbars() {
		// fatter scroll bars
		Container p = getParent();
		if (p instanceof JViewport) {
			Container gp = p.getParent();
			if (gp instanceof JScrollPane) {
				JScrollPane scrollPane = (JScrollPane) gp;             
				scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(30,0));
				scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0,30));
			}
		}
	}

}
