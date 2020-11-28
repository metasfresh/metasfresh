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


/**
 * Terminal Checkbox
 *
 * @author al
 */
public interface ITerminalCheckboxField extends ITerminalField<Boolean>
{
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

	void setFontSize(float fontSize);

	void setVisible(boolean visible);

	/**
	 * @return true if this component is visible
	 */
	boolean isVisible();
}
