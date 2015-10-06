package de.metas.commission.interfaces;

/*
 * #%L
 * de.metas.commission.base
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

public interface I_M_DiscountSchemaLine extends org.compiere.model.I_M_DiscountSchemaLine
{
	public static String COLUMNNAME_CommissionPoints_Base = "CommissionPoints_Base";

	String getCommissionPoints_Base();

	void setCommissionPoints_Base(String CommissionPoints_Base);

	public static String COLUMNNAME_CommissionPoints_Discount = "CommissionPoints_Discount";

	BigDecimal getCommissionPoints_Discount();

	void setCommissionPoints_Discount(BigDecimal CommissionPoints_Discount);

	public static String COLUMNNAME_CommissionPoints_SubtractVAT = "CommissionPoints_SubtractVAT";

	boolean isCommissionPoints_SubtractVAT();

	void setCommissionPoints_SubtractVAT(boolean CommissionPoints_SubtractVAT);

	public static String COLUMNNAME_CommissionPoints_AddAmt = "CommissionPoints_AddAmt";

	BigDecimal getCommissionPoints_AddAmt();

	void setCommissionPoints_AddAmt(BigDecimal CommissionPoints_AddAmt);

	public static String COLUMNNAME_CommissionPoints_Rounding = "CommissionPoints_Rounding";

	void setCommissionPoints_Rounding(String CommissionPoints_Rounding);

	String getCommissionPoints_Rounding();
}
