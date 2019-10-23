/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2007 Adempiere, Inc. All Rights Reserved.                    *
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
package org.compiere.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import org.adempiere.plaf.AdempierePLAF;
import org.jdesktop.swingx.JXCollapsiblePane;
import org.jdesktop.swingx.JXHyperlink;
import org.jdesktop.swingx.VerticalLayout;

/**
 * Collapsible panel with a title and contents.<br>
 *
 * Based on the StackedBox component develop by <a href="mailto:fred@L2FProd.com">Frederic Lavigne</a>
 *
 * @author hengsin
 */
public class CollapsiblePanel extends JPanel
{
	/**
	 *
	 */
	private static final long serialVersionUID = -7372966868790589720L;

	private final JXHyperlink link;
	private final JXCollapsiblePane collapsible;
	private final Action toggleAction;

	private Color titleBackgroundColor;
	private Color titleForegroundColor;
	private Color separatorColor;


	public CollapsiblePanel()
	{
		this("");
	}

	/**
	 *
	 * @param title
	 */
	public CollapsiblePanel(final String title)
	{
		final LayoutManager mgr = new VerticalLayout();
		setLayout(mgr);

		setOpaque(true);
		setBackground(Color.WHITE);

		final SeparatorBorder separatorBorder = new SeparatorBorder();
		setTitleForegroundColor(AdempierePLAF.getColor("black", Color.BLACK));
		setTitleBackgroundColor(new Color(248, 248, 248));
		setSeparatorColor(new Color(214, 223, 247));

		collapsible = new JXCollapsiblePane();
		// collapsible.getContentPane().setBackground(AdempierePLAF.getFormBackground());
		collapsible.setBorder(new CompoundBorder(separatorBorder, collapsible.getBorder()));
		collapsible.setAnimated(UIManager.getBoolean("TaskPane.animate"));

		this.toggleAction = collapsible.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION);
		// use the collapse/expand icons from the JTree UI
		toggleAction.putValue(JXCollapsiblePane.COLLAPSE_ICON, UIManager.getIcon("Tree.expandedIcon"));
		toggleAction.putValue(JXCollapsiblePane.EXPAND_ICON, UIManager.getIcon("Tree.collapsedIcon"));

		link = new JXHyperlink();
		link.setAction(toggleAction);
		link.setText(title);
		link.setOpaque(true);
		link.setBackground(getTitleBackgroundColor());
		link.setFocusPainted(false);
		link.setFocusable(false); // there is no point to have the link focusable, user will always click on it

		link.setUnclickedColor(getTitleForegroundColor());
		link.setClickedColor(getTitleForegroundColor());

		link.setBorder(new CompoundBorder(separatorBorder, BorderFactory.createEmptyBorder(2, 4, 2, 4)));
		link.setBorderPainted(true);

		super.add(link);
		super.add(collapsible);

	}

	/**
	 *
	 * @return color
	 */
	public Color getSeparatorColor()
	{
		return separatorColor;
	}

	/**
	 *
	 * @param separatorColor
	 */
	public void setSeparatorColor(final Color separatorColor)
	{
		this.separatorColor = separatorColor;
	}

	/**
	 * get title foreground color
	 *
	 * @return color
	 */
	public Color getTitleForegroundColor()
	{
		return titleForegroundColor;
	}

	/**
	 * Set title foreground color
	 *
	 * @param titleForegroundColor
	 */
	public void setTitleForegroundColor(final Color titleForegroundColor)
	{
		this.titleForegroundColor = titleForegroundColor;
	}

	/**
	 *
	 * @return title background color
	 */
	public Color getTitleBackgroundColor()
	{
		return titleBackgroundColor;
	}

	/**
	 * Set background color of title
	 *
	 * @param titleBackgroundColor
	 */
	public void setTitleBackgroundColor(final Color titleBackgroundColor)
	{
		this.titleBackgroundColor = titleBackgroundColor;
	}

	/**
	 * Set title of the section
	 *
	 * @param title
	 */
	public void setTitle(final String title)
	{
		if (link != null)
		{
			link.setText(title);
		}
	}

	/**
	 *
	 * @return collapsible pane
	 */
	public JXCollapsiblePane getCollapsiblePane()
	{
		return collapsible;
	}

	public JComponent getContentPane()
	{
		return (JComponent)getCollapsiblePane().getContentPane();
	}
	
	public void setContentPane(JComponent contentPanel)
	{
		getCollapsiblePane().setContentPane(contentPanel);
	}

	/**
	 * The border between the stack components. It separates each component with a fine line border.
	 */
	class SeparatorBorder implements Border
	{

		boolean isFirst(final Component c)
		{
			return c.getParent() == null || c.getParent().getComponent(0) == c;
		}

		@Override
		public Insets getBorderInsets(final Component c)
		{
			// if the collapsible is collapsed, we do not want its border to be
			// painted.
			if (c instanceof JXCollapsiblePane)
			{
				if (((JXCollapsiblePane)c).isCollapsed())
				{
					return new Insets(0, 0, 0, 0);
				}
			}
			return new Insets(4, 0, 1, 0);
		}

		@Override
		public boolean isBorderOpaque()
		{
			return true;
		}

		@Override
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width, final int height)
		{
			// if the collapsible is collapsed, we do not want its border to be painted.
			if (c instanceof JXCollapsiblePane)
			{
				if (((JXCollapsiblePane)c).isCollapsed())
				{
					return;
				}
			}
			g.setColor(getSeparatorColor());
			if (isFirst(c))
			{
				g.drawLine(x, y + 2, x + width, y + 2);
			}
			g.drawLine(x, y + height - 1, x + width, y + height - 1);
		}
	}

	@Override
	public final Component add(final Component comp)
	{
		return collapsible.add(comp);
	}
	
	public final void setCollapsed(final boolean collapsed)
	{
		collapsible.setCollapsed(collapsed);
	}
	
	public final void setCollapsible(final boolean collapsible)
	{
		this.toggleAction.setEnabled(collapsible);
		if (!collapsible)
		{
			this.collapsible.setCollapsed(false);
		}
	}

}
