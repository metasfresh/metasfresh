package de.metas.ui.web.handlingunits.util;

import java.math.BigDecimal;

import org.compiere.model.I_C_UOM;

import de.metas.handlingunits.model.I_M_HU_PI;

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

/**
 * Packing informations used by {@link HUPackingInfoFormatter}.
 * 
 * Check {@link HUPackingInfos} for creating various instances.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IHUPackingInfo
{
	//@formatter:off
	I_M_HU_PI getM_LU_HU_PI();
	//@formatter:on

	//@formatter:off
	I_M_HU_PI getM_TU_HU_PI();
	boolean isInfiniteQtyTUsPerLU();
	BigDecimal getQtyTUsPerLU();
	//@formatter:on

	//@formatter:off
	boolean isInfiniteQtyCUsPerTU();
	BigDecimal getQtyCUsPerTU();
	I_C_UOM getQtyCUsPerTU_UOM();
	//@formatter:on
}
