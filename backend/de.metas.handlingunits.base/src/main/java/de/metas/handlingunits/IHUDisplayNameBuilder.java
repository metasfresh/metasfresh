package de.metas.handlingunits;

/*
 * #%L
 * de.metas.handlingunits.base
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

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;

/**
 * Builds the display name of a {@link I_M_HU}.
 */
public interface IHUDisplayNameBuilder
{
	/**
	 * @return display name of underlying HU
	 */
	public String build();

	/**
	 * @return
	 *         <ul>
	 *         <li>the packing instructions name of current HU
	 *         <li>or if it's an aggregate HU - the name of the PI that is represented.
	 *         </ul>
	 */
	String getPIName();

	/**
	 * @return true if included HU count shall be displayed
	 */
	public boolean isShowIncludedHUCount();

	/**
	 * @param showIncludedHUCount true if included HU count shall be displayed
	 */
	public IHUDisplayNameBuilder setShowIncludedHUCount(boolean showIncludedHUCount);

	/**
	 * @return included HU suffix (i.e TU, VHU etc)
	 */
	public String getIncludedHUCountSuffix();

	/**
	 * @param includedHUCountSuffix included HU suffix (i.e TU, VHU etc)
	 */
	public IHUDisplayNameBuilder setIncludedHUCountSuffix(String includedHUCountSuffix);

	/**
	 * Gets included HUs count for current HU.
	 * 
	 * NOTE this method does not care about {@link #isShowIncludedHUCount()}.
	 * 
	 * @return included HUs count for current HU
	 */
	int getIncludedHUsCount();

	/**
	 * @return true if {@link I_M_HU_PI#getName()} shall be displayed below value
	 */
	public boolean isShowHUPINameNextLine();

	/**
	 * @param showHUPINameNextLine if {@link I_M_HU_PI#getName()} shall be displayed below value
	 */
	public IHUDisplayNameBuilder setShowHUPINameNextLine(boolean showHUPINameNextLine);

	/**
	 * Sets if we shall display a "Destroyed" marker in case the HU is destroyed.
	 * 
	 * NOTE: usually this "tag" is not displayed because we are not carrying/ignoring about destroyed HUs in our business logic.
	 * But in case this happens, it's important to be displayed because in most of the cases, it's an issue.
	 */
	public IHUDisplayNameBuilder setShowIfDestroyed(boolean showIfDestroyed);
}
