package net.sf.memoranda.ui.htmleditor;

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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JEditorPane;

public class HTMLEditorPane extends JEditorPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3894172494918474160L;
	
	boolean antiAlias = true;

	public HTMLEditorPane(String text) {
		super("text/html", text);
	}

	public boolean isAntialiasOn() {
		return antiAlias;
	}

	public void setAntiAlias(boolean on) {
		antiAlias = on;
	}

	@Override
	public void paint(Graphics g) {
		if (antiAlias) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			/*g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
					RenderingHints.VALUE_FRACTIONALMETRICS_ON);*/
			super.paint(g2);
		} else {
			super.paint(g);
		}
	}
}
