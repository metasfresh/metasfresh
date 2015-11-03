/**
 * 
 */
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
import java.beans.PropertyChangeListener;

/**
 * @author tsa
 * 
 */
public interface ITerminalButton extends IFocusableComponent, IExecuteBeforePainingSupport
{
	String PROPERTY_Action = "action";

	void addListener(PropertyChangeListener listener);

	void setAD_Image_ID(int AD_Image_ID);

	void setVisible(boolean visible);

	void setEnabled(boolean enabled);

	void setFocusable(boolean focusable);

	@Override
	void requestFocus();

	void setBackground(Color color);

	void setFont(Font font);

	void setAction(String action);

	String getAction();

	/** @return text which is displayed on this button. */
	String getText();

	/**
	 * Sets text, but does NOT translate it. See {@link #setTextAndTranslate(String)} if you want to translate.
	 * 
	 * @param text
	 */
	void setText(String text);

	/**
	 * Sets text, and translates it.
	 * 
	 * @param text
	 */
	void setTextAndTranslate(String text);

	void setPressed(boolean pressed);

	boolean isPressed();

	void setSelected(boolean selected);

	boolean isSelected();

	void setToolTipText(String tooltipText);

	int getWidth();

	int getHeight();
}
