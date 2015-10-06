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
import java.math.BigDecimal;

/**
 * @author tsa
 * 
 */
public interface ITerminalNumericField extends ITerminalField<BigDecimal>
{
	String ACTION_Minus = "Minus";
	String ACTION_Plus = "Plus";

	/**
	 * Set read-only
	 * 
	 * @param ro
	 * @deprecated Please use {@link #setEditable(boolean)}
	 */
	@Deprecated
	void setReadOnly(boolean ro);
	
	@Override
	void setEditable(boolean editable);


	/**
	 * @return background color
	 */
	Color getBackgroundColor();

	/**
	 * Set field background color.
	 * 
	 * @param color
	 */
	void setBackgroundColor(Color color);

	/**
	 * 
	 * @param ro true if "Minus" button shall be readonly
	 */
	void setMinusRO(boolean ro);

	/**
	 * 
	 * @param ro true if "Plus" button shall be readonly
	 */
	void setPlusRO(boolean ro);

	/**
	 * 
	 * @param ro true if number field (just the number field, excluding Plus/Minus buttons) shall be readonly
	 */
	void setNumberRO(boolean ro);
}
