/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved. *
 * Contributor(s): Victor Perez www.e-evolution.com *
 *****************************************************************************/

package org.eevolution.form.crp;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.awt.Component;
import java.awt.Graphics;
import java.util.Date;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.adempiere.images.Images;
import org.eevolution.form.tree.MapTreeCellRenderer;

/**
 * @author Gunther Hoppe, tranSIT GmbH Ilmenau/Germany
 * @version 1.0, October 14th 2005
 */
public class DiagramTreeCellRenderer extends MapTreeCellRenderer
{
	private static final long serialVersionUID = 1L;

	public DiagramTreeCellRenderer(HashMap<?, ?> map)
	{
		super(map);
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
	{
		Component c = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

		String name = (String)getMapping(value);
		ImageIcon icon = getIcon(value);
		if (isNotAvailable(name))
		{
			final int x1 = getFontMetrics(getFont()).stringWidth(name) + icon.getIconWidth();
			JLabel l = new JLabel(name.substring(1, name.length() - 1), icon, JLabel.LEFT)
			{
				private static final long serialVersionUID = 1L;

				@Override
				public void paint(Graphics g)
				{
					super.paint(g);
					int y = getFont().getSize() / 2;
					g.drawLine(0, y, x1, y);
				}
			};
			l.setFont(getFont());
			return l;
		}
		return c;
	}

	private boolean isNotAvailable(String value)
	{
		return value.startsWith("{") && value.endsWith("}");
	}

	@Override
	protected ImageIcon getIcon(Object value)
	{
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
		if (node.getUserObject() instanceof Date)
		{
			return Images.getImageIcon2("Calendar10");
		}
		else
		{
			return null;
		}
	}
}
