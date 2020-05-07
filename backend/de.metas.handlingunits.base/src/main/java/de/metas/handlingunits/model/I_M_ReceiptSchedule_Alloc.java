package de.metas.handlingunits.model;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


public interface I_M_ReceiptSchedule_Alloc extends de.metas.inoutcandidate.model.I_M_ReceiptSchedule_Alloc
{
	// @formatter:off
	String COLUMNNAME_M_TU_HU_ID = "M_TU_HU_ID";
	// void setM_TU_HU_ID(int M_TU_HU_ID); // commented out because it's descouraged
	int getM_TU_HU_ID();
	de.metas.handlingunits.model.I_M_HU getM_TU_HU() throws RuntimeException;
	void setM_TU_HU(de.metas.handlingunits.model.I_M_HU M_HU);
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_M_LU_HU_ID = "M_LU_HU_ID";
	// void setM_LU_HU_ID(int M_LU_HU_ID); // commented out because it's descouraged
	int getM_LU_HU_ID();
	de.metas.handlingunits.model.I_M_HU getM_LU_HU() throws RuntimeException;
	void setM_LU_HU(de.metas.handlingunits.model.I_M_HU M_LU_HU);
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_VHU_ID = "VHU_ID";
	// void setVHU_ID(int VHU_ID); // commented out because it's descouraged
	int getVHU_ID();
	de.metas.handlingunits.model.I_M_HU getVHU() throws RuntimeException;
	void setVHU(de.metas.handlingunits.model.I_M_HU VHU);
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_HU_QtyAllocated = "HU_QtyAllocated";
	void setHU_QtyAllocated(java.math.BigDecimal HU_QtyAllocated);
	java.math.BigDecimal getHU_QtyAllocated();
	// @formatter:on
}
