package de.metas.commission.model;

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

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Provisionsberechnung_%282009_0023_G106%29'>(2009 0023 G106)</a>"
 */

public interface IInstanceTrigger
{
	public static final String COLUMNNAME_CommissionPoints = "CommissionPoints";

	public void setCommissionPoints(BigDecimal CommissionPoints);

	public BigDecimal getCommissionPoints();

	public static final String COLUMNNAME_CommissionPointsSum = "CommissionPointsSum";

	public void setCommissionPointsSum(BigDecimal CommissionPointsSum);

	public BigDecimal getCommissionPointsSum();

	public static final String COLUMNNAME_CommissionPointsNet = "CommissionPointsNet";

	public void setCommissionPointsNet(BigDecimal CommissionPointsNet);

	public BigDecimal getCommissionPointsNet();

	public static final String COLUMNNAME_Discount = "Discount";

	public void setDiscount(BigDecimal Discount);

	public BigDecimal getDiscount();

	public static final String COLUMNNAME_IsManualCommissionPoints = "IsManualCommissionPoints";

	public void setIsManualCommissionPoints(boolean IsManualCommissionPoints);

	public boolean isManualCommissionPoints();
}
