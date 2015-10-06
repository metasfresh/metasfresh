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
import java.util.List;

/**
 * @author tsa
 *
 */
public interface ITerminalKeyPanel extends IComponent
{
	String KEYFIXEDSIZE_Auto = null;

	/**
	 * @return current active key layout
	 */
	IKeyLayout getKeyLayout();

	/**
	 * Remove the {@link IKeyLayout} identified by <code>keyLayoutId</code> form this panel.
	 *
	 * @param keyLayoutId
	 */
	void removeLayout(String keyLayoutId);

	/**
	 * Sets current active {@link IKeyLayout} and displays it
	 *
	 * @param keyLayout
	 */
	void showPOSKeyKayout(IKeyLayout keyLayout);

	/**
	 * Add a {@link IKeyLayout} to this panel.
	 *
	 * To show it, please use {@link #showPOSKeyKayout(IKeyLayout)}.
	 *
	 * @param keyLayout
	 */
	void addPOSKeyLayout(IKeyLayout keyLayout);

	/**
	 * Gets {@link ITerminalKey}s from current key layout
	 *
	 * @return {@link ITerminalKey}s from current key layout; or null if there is no current key layout
	 */
	List<ITerminalKey> getKeys();

	void setAllowKeySelection(boolean allowKeySelection);

	boolean isAllowKeySelection();

	void setKeySelectionColor(Color color);

	Color getKeySelectionColor();

	void onKeySelected(ITerminalKey key);

	@Override
	void dispose();

	ITerminalKey getSelectedKey();

	/**
	 * Emulates {@link ITerminalKey} pressed action and it will do exactly the same like when the user is pressing on that key button.
	 *
	 * @param keyId {@link ITerminalKey} identifier (see {@link ITerminalKey#getId()})
	 */
	void fireKeyPressed(String keyId);

	void setRenderer(IKeyPanelRenderer renderer);

	IKeyPanelRenderer getRenderer();

	void setKeyFixedHeight(String keyFixedHeight);

	String getKeyFixedHeight();

	void setKeyFixedWidth(String keyFixedWidth);

	String getKeyFixedWidth();
}
