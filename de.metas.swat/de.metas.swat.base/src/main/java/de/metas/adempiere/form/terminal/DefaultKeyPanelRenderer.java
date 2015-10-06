package de.metas.adempiere.form.terminal;

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


import java.awt.Color;
import java.awt.Font;

import org.compiere.print.MPrintColor;
import org.compiere.print.MPrintFont;
import org.compiere.util.Env;

public class DefaultKeyPanelRenderer implements IKeyPanelRenderer
{
	public static final DefaultKeyPanelRenderer instance = new DefaultKeyPanelRenderer();

	protected DefaultKeyPanelRenderer()
	{
		super();
	}

	@Override
	public void renderKey(final ITerminalButton button, final ITerminalKey terminalKey, final IKeyLayout keyLayout)
	{
		//
		// Color
		{
			final Color keyColor = getBackgroundColor(terminalKey, keyLayout);
			button.setBackground(keyColor);
		}

		//
		// Font
		{
			final Font keyFont = getFont(terminalKey, keyLayout);
			button.setFont(keyFont);
		}

		//
		// Icon
		final int iconImageId = getIconImageId(terminalKey, keyLayout);
		if (iconImageId > 0)
		{
			button.setAD_Image_ID(iconImageId);
		}
	}

	protected Color getBackgroundColor(final ITerminalKey terminalKey, final IKeyLayout keyLayout)
	{
		final ITerminalKeyStatus terminalKeyStatus = terminalKey.getStatus();

		Color keyColor = keyLayout.getDefaultColor(terminalKey);
		if (terminalKeyStatus == null)
		{
			// no status found; we use defaults
		}
		else if (terminalKeyStatus.getAD_PrintColor_ID() > 0)
		{
			final MPrintColor color = MPrintColor.get(Env.getCtx(), terminalKeyStatus.getAD_PrintColor_ID());
			keyColor = color.getColor();
		}
		else if (terminalKeyStatus.getColor() != null)
		{
			keyColor = terminalKeyStatus.getColor();
		}

		return keyColor;
	}

	protected Font getFont(final ITerminalKey terminalKey, final IKeyLayout keyLayout)
	{
		final ITerminalKeyStatus terminalKeyStatus = terminalKey.getStatus();

		Font keyFont = keyLayout.getDefaultFont();
		if (terminalKeyStatus == null)
		{
			// no status found; we use defaults
		}
		else if (terminalKeyStatus.getAD_PrintFont_ID() > 0)
		{
			final MPrintFont font = MPrintFont.get(terminalKeyStatus.getAD_PrintFont_ID());
			keyFont = font.getFont();
		}

		return keyFont;
	}

	/**
	 * 
	 * @return icon's AD_Image_ID
	 */
	protected int getIconImageId(final ITerminalKey terminalKey, final IKeyLayout keyLayout)
	{
		final ITerminalKeyStatus terminalKeyStatus = terminalKey.getStatus();

		if (terminalKeyStatus == null)
		{
			// no status found; we use defaults
		}
		else if (terminalKeyStatus.getAD_Image_ID() > 0)
		{
			return terminalKeyStatus.getAD_Image_ID();
		}

		return -1;
	}
}
