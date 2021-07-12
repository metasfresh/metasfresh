/*
 * #%L
 * de.metas.servicerepair.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.servicerepair.repository.model;

import java.math.BigDecimal;

// TODO: generate the actual model !!!
public interface I_PP_Order_RepairService
{
	String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

	int getPP_Order_ID();
	void setPP_Order_ID(int id);

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	int getM_Product_ID();
	void setM_Product_ID(int id);

	int getC_UOM_ID();
	void setC_UOM_ID(int id);

	BigDecimal getQty();
	void setQty(BigDecimal qty);
}
