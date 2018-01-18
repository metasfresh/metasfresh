package de.metas.ui.web.handlingunits.process;

import java.math.BigDecimal;
import java.util.List;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.ui.web.handlingunits.process.WebuiHUTransformCommand.ActionType;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
 * {@link WebuiHUTransformCommand} parameters.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Value
@Builder
public class WebuiHUTransformParameters
{
	@NonNull
	private final ActionType actionType;
	private final I_M_HU_PI_Item_Product huPIItemProduct;
	private final I_M_HU_PI_Item huPIItem;
	private final I_M_HU tuHU;
	private final I_M_HU luHU;
	private final BigDecimal qtyCU;
	private final BigDecimal qtyTU;
	private final boolean huPlanningReceiptOwnerPM_LU;
	private final boolean huPlanningReceiptOwnerPM_TU;
	
	private final List<String> serialNumbers;

}
