package de.metas.process.ui;

import java.awt.Container;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.WindowListener;

import javax.swing.JRootPane;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Swing process window which will host the {@link ProcessDialog} implementation.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface ProcessPanelWindow
{
	void dispose();

	void showCenterScreen();

	void enableWindowEvents(long eventsToEnable);

	void setIconImage(Image image);

	/**
	 * Add window listener
	 * 
	 * @param windowListener
	 */
	void addWindowListener(WindowListener windowListener);

	Container getContentPane();

	public JRootPane getRootPane();

	void setTitle(String title);

	void setEnabled(boolean enabled);

	Window asAWTWindow();
}
