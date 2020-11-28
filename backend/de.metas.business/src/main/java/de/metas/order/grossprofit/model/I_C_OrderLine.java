package de.metas.order.grossprofit.model;

import java.math.BigDecimal;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

public interface I_C_OrderLine extends org.compiere.model.I_C_OrderLine
{
	org.adempiere.model.ModelColumn<I_C_OrderLine, Object> COLUMN_ProfitPercent = new org.adempiere.model.ModelColumn<>(I_C_OrderLine.class, "ProfitPercent", null);
	String COLUMNNAME_ProfitPercent = "ProfitPercent";

	void setProfitPercent(BigDecimal ProfitPercent);
	BigDecimal getProfitPercent();
}
