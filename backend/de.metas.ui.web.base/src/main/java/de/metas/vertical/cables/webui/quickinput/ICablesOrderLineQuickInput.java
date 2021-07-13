package de.metas.vertical.cables.webui.quickinput;

import java.math.BigDecimal;

/*
 * #%L
 * metasfresh-webui-api
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

public interface ICablesOrderLineQuickInput
{
	//@formatter:off
	String COLUMNNAME_Plug1_Product_ID = "Plug1_Product_ID";
	int getPlug1_Product_ID();
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_Plug2_Product_ID = "Plug2_Product_ID";
	int getPlug2_Product_ID();
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_Cable_Product_ID = "Cable_Product_ID";
	int getCable_Product_ID();
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_CableLength = "CableLength";
	BigDecimal getCableLength();
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_Qty = "Qty";
	BigDecimal getQty();
	//@formatter:on
}
