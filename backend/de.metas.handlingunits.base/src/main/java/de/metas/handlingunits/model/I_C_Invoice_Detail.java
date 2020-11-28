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


public interface I_C_Invoice_Detail extends de.metas.materialtracking.model.I_C_Invoice_Detail
{
	//@formatter:off
	String COLUMNNAME_M_TU_HU_PI_ID = "M_TU_HU_PI_ID";
	void setM_TU_HU_PI_ID(int M_TU_HU_PI_ID);
	I_M_HU_PI getM_TU_HU_PI();
	int getM_TU_HU_PI_ID();
	void setM_TU_HU_PI(final I_M_HU_PI M_TU_HU_PI);
	//@formatter:on

	//@formatter:off
	public static final String COLUMNNAME_QtyTU = "QtyTU";
	public void setQtyTU (java.math.BigDecimal QtyTU);
	public java.math.BigDecimal getQtyTU();
	//@formatter:on
}
