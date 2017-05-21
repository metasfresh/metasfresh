package de.metas.handlingunits.model;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public interface I_M_InOutLine_HU_Alloc extends de.metas.inout.model.I_M_InOutLine_HU_Alloc
{

	// @formatter:off
	public void setM_HU_ID (int M_HU_ID);
	public int getM_HU_ID();
	public de.metas.handlingunits.model.I_M_HU getM_HU();
	public void setM_HU(de.metas.handlingunits.model.I_M_HU M_HU);
    public static final String COLUMNNAME_M_HU_ID = "M_HU_ID";
 	// @formatter:on

	// @formatter:off
 	String COLUMNNAME_M_TU_HU_ID = "M_TU_HU_ID";
 	// void setM_TU_HU_ID(int M_TU_HU_ID); // commented out because it's discouraged
 	int getM_TU_HU_ID();
 	de.metas.handlingunits.model.I_M_HU getM_TU_HU() throws RuntimeException;
 	void setM_TU_HU(de.metas.handlingunits.model.I_M_HU M_HU);
 	// @formatter:on
}
