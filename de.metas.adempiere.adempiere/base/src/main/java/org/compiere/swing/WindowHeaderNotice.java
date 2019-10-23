package org.compiere.swing;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import java.awt.Color;
import java.awt.Dimension;
import java.util.Properties;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.util.Colors;
import org.compiere.util.Env;

import de.metas.util.Check;

/**
 * See http://dewiki908/mediawiki/index.php/05730_Use_different_Theme_colour_on_UAT_system
 *
 * @author tsa
 *
 */
public class WindowHeaderNotice extends JPanel
{

	/**
	 *
	 */
	private static final long serialVersionUID = -914277060790906131L;

	private final JLabel label = new JLabel();

	public WindowHeaderNotice()
	{
		super();

		//
		// Init components & layout
		{
			this.setBackground(AdempierePLAF.getColor("WindowHeaderNotice.background", "red", Color.RED));
			this.setMinimumSize(new Dimension(100, 50));

			label.setForeground(AdempierePLAF.getColor("WindowHeaderNotice.foreground", "white", Color.WHITE));
			this.add(label);
		}

		//
		// Load from context
		load();
	}

	public void setNotice(final String notice)
	{
		label.setText(notice);
	}

	public String getNotice()
	{
		return label.getText();
	}

	/**
	 * Load status from context
	 */
	public void load()
	{
		final Properties ctx = Env.getCtx();

		// FRESH-352: check if we shall override the default background color which we set in the constructor
		final String windowHeaderNoticeBGColorStr = Env.getContext(ctx, Env.CTXNAME_UI_WindowHeader_Notice_BG_COLOR);
		if (!Check.isEmpty(windowHeaderNoticeBGColorStr, true))
		{
			final Color backgroundColor = Colors.toColor(windowHeaderNoticeBGColorStr);
			if (backgroundColor != null)
			{
				this.setBackground(backgroundColor);
			}
		}
		final String windowHeaderNoticeFGColorStr = Env.getContext(ctx, Env.CTXNAME_UI_WindowHeader_Notice_FG_COLOR);
		if (!Check.isEmpty(windowHeaderNoticeBGColorStr, true))
		{
			final Color foregroundColor = Colors.toColor(windowHeaderNoticeFGColorStr);
			if (foregroundColor != null)
			{
				label.setForeground(foregroundColor);
			}
		}


		final String windowHeaderNotice = Env.getContext(ctx, Env.CTXNAME_UI_WindowHeader_Notice_Text);

		if (!Check.isEmpty(windowHeaderNotice, true) && !"-".equals(windowHeaderNotice))
		{
			setNotice(windowHeaderNotice);
			setVisible(true);
		}
		else
		{
			setNotice("");
			setVisible(false);
		}

	}
}
