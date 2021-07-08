package de.metas.ui.web.quickinput.inout;

import de.metas.handlingunits.model.I_M_HU_PackingMaterial;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public interface IEmptiesQuickInput
{
	//@formatter:off
	String COLUMNNAME_M_HU_PackingMaterial_ID = I_M_HU_PackingMaterial.COLUMNNAME_M_HU_PackingMaterial_ID;
	//int getM_HU_PackingMaterial_ID();
	I_M_HU_PackingMaterial getM_HU_PackingMaterial();
	//void setM_HU_PackingMaterial_ID(final int M_HU_PackingMaterial_ID);
	//void setM_HU_PackingMaterial(final I_M_HU_PackingMaterial huPIItemProduct);
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_Qty = "Qty";
	int getQty();
	//@formatter:on

}
