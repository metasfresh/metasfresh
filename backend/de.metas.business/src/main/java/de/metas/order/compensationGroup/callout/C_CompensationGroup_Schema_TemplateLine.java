/*
 * #%L
 * de.metas.business
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

package de.metas.order.compensationGroup.callout;

import de.metas.order.model.I_C_CompensationGroup_Schema_TemplateLine;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Callout(I_C_CompensationGroup_Schema_TemplateLine.class)
@Component
public class C_CompensationGroup_Schema_TemplateLine
{
	private final IProductBL productBL = Services.get(IProductBL.class);

	@PostConstruct
	void postConstruct()
	{
		final IProgramaticCalloutProvider programaticCalloutProvider = Services.get(IProgramaticCalloutProvider.class);
		programaticCalloutProvider.registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_C_CompensationGroup_Schema_TemplateLine.COLUMNNAME_M_Product_ID)
	public void onProductChanged(final I_C_CompensationGroup_Schema_TemplateLine record)
	{
		final ProductId productId = ProductId.ofRepoIdOrNull(record.getM_Product_ID());
		if (productId == null)
		{
			return;
		}

		final UomId uomId = productBL.getStockUOMId(productId);
		record.setC_UOM_ID(uomId.getRepoId());

		if (record.getQty().signum() <= 0)
		{
			record.setQty(BigDecimal.ONE);
		}
	}
}
