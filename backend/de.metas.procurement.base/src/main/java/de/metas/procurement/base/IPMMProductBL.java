package de.metas.procurement.base;

import java.util.Date;

import de.metas.product.ProductId;
import lombok.NonNull;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import org.compiere.model.I_C_BPartner_Product;
import de.metas.procurement.base.model.I_PMM_Product;
import de.metas.util.ISingletonService;

import javax.annotation.Nullable;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface IPMMProductBL extends ISingletonService
{
	void updateByProduct(I_M_Product product);

	void updateByBPartner(BPartnerId bpartnerId);

	void updateByHUPIItemProduct(I_M_HU_PI_Item_Product huPIItemProduct);

	/**
	 * Update given {@link I_PMM_Product} from underlying {@link I_M_Product}, {@link I_C_BPartner_Product}, {@link I_M_HU_PI_Item_Product}.
	 */
	void update(I_PMM_Product pmmProduct);

	/**
	 * Get the PMM_Product for the date, product, partner and M_HU_PI_Item_Product that fits the given ASI the most.
	 * This means the attribute instances from the ASI of the PMM Product must be a subset of the given ASI's attribute instances
	 *
	 * @return PMM_Product if found, null otherwise
	 */
	I_PMM_Product getPMMProductForDateProductAndASI(@Nullable Date date, @NonNull ProductId productId, @Nullable BPartnerId partnerId, int huPIPId, I_M_AttributeSetInstance asi);
}