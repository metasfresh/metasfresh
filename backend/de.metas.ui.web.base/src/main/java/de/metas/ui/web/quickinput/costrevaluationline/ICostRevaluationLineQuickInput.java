/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.ui.web.quickinput.costrevaluationline;

import org.compiere.model.I_M_Product;

import java.math.BigDecimal;

public interface ICostRevaluationLineQuickInput
{
	//@formatter:off
	String COLUMNNAME_M_Product_ID = "M_Product_ID";
	int getM_Product_ID();
	I_M_Product getM_Product();
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_NewCostPrice = "NewCostPrice";
	BigDecimal getNewCostPrice();
	//@formatter:on
}
