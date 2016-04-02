package de.metas.procurement.base.order.model;

import org.compiere.model.I_M_AttributeSetInstance;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public interface I_C_OrderLine extends de.metas.handlingunits.model.I_C_OrderLine
{
	//@formatter:off
	int getC_Flatrate_DataEntry_ID();
	void setC_Flatrate_DataEntry_ID(final int C_Flatrate_DataEntry_ID);
	//@formatter:on

	//@formatter:off
	boolean isMFProcurement();
	void setIsMFProcurement(boolean IsMFProcurement);
	//@formatter:on
	
	//@formatter:off
	int getPMM_Contract_ASI_ID();
	I_M_AttributeSetInstance getPMM_Contract_ASI();
	void setPMM_Contract_ASI_ID(int PMM_Contract_ASI_ID);
	void setPMM_Contract_ASI(I_M_AttributeSetInstance PMM_Contract_ASI);
	//@formatter:on
}
