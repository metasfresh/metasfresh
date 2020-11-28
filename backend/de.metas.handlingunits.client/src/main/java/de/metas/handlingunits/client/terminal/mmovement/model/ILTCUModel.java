package de.metas.handlingunits.client.terminal.mmovement.model;

/*
 * #%L
 * de.metas.handlingunits.client
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


import java.math.BigDecimal;

import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalKey;

/**
 * Loading / Trading / Customer Unit Model. Used to easily define CU-TU-LU data models.
 *
 * @author al
 */
public interface ILTCUModel extends IMaterialMovementModel
{
	/**
	 * If this property is <code>true</code> then do not allow infinite capacity in POS/HU splitting, except the case when the TU we are splitting is unlimited.
	 *
	 * @task http://dewiki908/mediawiki/index.php/07990_LU_unbestimmte_Kapazit%C3%A4t_zulassen_%28107264303343%29
	 */
	String PROPERTY_AllowSameTUInfiniteCapacity = "AllowSameTUInfiniteCapacity";

	IKeyLayout getCUKeyLayout();

	IKeyLayout getTUKeyLayout();

	IKeyLayout getLUKeyLayout();

	void setQtyCU(BigDecimal qtyCU);

	void setQtyTU(BigDecimal qtyTU);

	void setQtyTU(int qtyTU);

	void setQtyLU(BigDecimal qtyLU);

	void setQtyLU(int qtyLU);

	BigDecimal getQtyCU();

	BigDecimal getQtyTU();

	BigDecimal getQtyLU();

	/**
	 * @return true if CU Qty component is readonly
	 */
	boolean isQtyCUReadonly();

	/**
	 * Configures component to be readonly or not. Note that this shall be overridden by {@link #setQtyCUReadonlyAlways()}
	 *
	 * @param qtyCUReadonly
	 */
	void setQtyCUReadonly(boolean qtyCUReadonly);

	/**
	 * @return true if TU Qty component is readonly
	 */
	boolean isQtyTUReadonly();

	/**
	 * Configures component to be readonly or not. Note that this shall be overridden by {@link #setQtyTUReadonlyAlways()}
	 *
	 * @param qtyTUReadonly
	 */
	void setQtyTUReadonly(boolean qtyTUReadonly);

	/**
	 * @return true if LU Qty component is readonly
	 */
	boolean isQtyLUReadonly();

	/**
	 * Configures component to be readonly or not. Note that this shall be overridden by {@link #setQtyLUReadonlyAlways()}
	 *
	 * @param qtyLUReadonly
	 */
	void setQtyLUReadonly(boolean qtyLUReadonly);

	/**
	 * Sets given CU-Key
	 *
	 * @param key
	 */
	<T extends ITerminalKey> void setCUKey(T key);

	/**
	 * Sets given TU-Key
	 *
	 * @param key
	 */
	<T extends ITerminalKey> void setTUKey(T key);

	/**
	 * Sets given LU-Key
	 *
	 * @param key
	 */
	<T extends ITerminalKey> void setLUKey(T key);
}
