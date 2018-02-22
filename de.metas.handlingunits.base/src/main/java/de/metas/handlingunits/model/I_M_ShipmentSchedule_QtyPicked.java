package de.metas.handlingunits.model;

import java.math.BigDecimal;

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


public interface I_M_ShipmentSchedule_QtyPicked extends de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked
{
	// @formatter:off
	String COLUMNNAME_M_TU_HU_ID = "M_TU_HU_ID";
	void setM_TU_HU_ID(int M_TU_HU_ID);
	int getM_TU_HU_ID();
	de.metas.handlingunits.model.I_M_HU getM_TU_HU() throws RuntimeException;
	void setM_TU_HU(de.metas.handlingunits.model.I_M_HU M_HU);

	String COLUMNNAME_M_LU_HU_ID = "M_LU_HU_ID";
	void setM_LU_HU_ID(int M_LU_HU_ID);
	int getM_LU_HU_ID();
	de.metas.handlingunits.model.I_M_HU getM_LU_HU() throws RuntimeException;
	void setM_LU_HU(de.metas.handlingunits.model.I_M_HU M_LU_HU);

	String COLUMNNAME_VHU_ID = "VHU_ID";
	void setVHU_ID(int VHU_ID);
	int getVHU_ID();
	de.metas.handlingunits.model.I_M_HU getVHU() throws RuntimeException;
	void setVHU(de.metas.handlingunits.model.I_M_HU VHU);

	String COLUMNNAME_QtyTU = "QtyTU";
	void setQtyTU(BigDecimal QtyTU);
	BigDecimal getQtyTU();

	String COLUMNNAME_QtyLU = "QtyLU";
	void setQtyLU(BigDecimal QtyLU);
	BigDecimal getQtyLU();
	// @formatter:on
}
