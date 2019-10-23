package org.compiere.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JFrame;

import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@UtilityClass
public class SwingUtils
{
	/**
	 * @return JFrame of component or null
	 */
	public static JFrame getFrame(final Component component)
	{
		Component element = component;
		while (element != null)
		{
			if (element instanceof JFrame)
			{
				return (JFrame)element;
			}
			element = element.getParent();
		}
		return null;
	}	// getFrame

	public static Window getParentWindow(final Component component)
	{
		Component element = component;
		while (element != null)
		{
			if (element instanceof JDialog || element instanceof JFrame)
			{
				return (Window)element;
			}
			if (element instanceof Window)
			{
				return (Window)element;
			}
			element = element.getParent();
		}
		return null;
	}   // getParent

	/**
	 * Get Graphics of container or its parent. The element may not have a Graphic if not displayed yet, but the parent might have.
	 *
	 * @param container Container
	 * @return Graphics of container or null
	 */
	public static Graphics getGraphics(final Container container)
	{
		Container element = container;
		while (element != null)
		{
			final Graphics g = element.getGraphics();
			if (g != null)
			{
				return g;
			}
			element = element.getParent();
		}
		return null;
	}
}
