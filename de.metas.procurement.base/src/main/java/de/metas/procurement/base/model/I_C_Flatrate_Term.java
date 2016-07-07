package de.metas.procurement.base.model;

import de.metas.procurement.base.rfq.model.I_C_RfQResponseLine;

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

public interface I_C_Flatrate_Term extends de.metas.flatrate.model.I_C_Flatrate_Term
{
	//@formatter:off
	public static final String COLUMNNAME_PMM_Product_ID = "PMM_Product_ID";
	int getPMM_Product_ID();
	I_PMM_Product getPMM_Product();
	void setPMM_Product(I_PMM_Product PMM_Product);
	//@formatter:on
	
	//@formatter:off
	public static final String COLUMNNAME_C_RfQResponseLine_ID = "C_RfQResponseLine_ID";
	int getC_RfQResponseLine_ID();
	I_C_RfQResponseLine getC_RfQResponseLine();
	void setC_RfQResponseLine(de.metas.rfq.model.I_C_RfQResponseLine C_RfQResponseLine);
	//@formatter:on
	
}
