/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.ui.web.quickinput.invoiceline;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.product.ProductId;
import de.metas.ui.web.quickinput.QuickInput;
import de.metas.ui.web.quickinput.field.DefaultPackingItemCriteria;
import de.metas.ui.web.quickinput.field.PackingItemProductFieldHelper;
import lombok.NonNull;
import org.adempiere.ad.callout.api.ICalloutField;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InvoiceLineQuickInputCallout
{
	private final PackingItemProductFieldHelper packingItemProductFieldHelper;

	public InvoiceLineQuickInputCallout(final PackingItemProductFieldHelper packingItemProductFieldHelper)
	{
		this.packingItemProductFieldHelper = packingItemProductFieldHelper;
	}

	public void onProductChange(@NonNull final ICalloutField calloutField)
	{
		final QuickInput quickInput = QuickInput.getQuickInputOrNull(calloutField);
		if (quickInput == null)
		{
			return;
		}

		if (!quickInput.hasField(IInvoiceLineQuickInput.COLUMNNAME_M_HU_PI_Item_Product_ID))
		{
			return;
		}

		final IInvoiceLineQuickInput invoiceLineQuickInput = quickInput.getQuickInputDocumentAs(IInvoiceLineQuickInput.class);

		final ProductId productId = ProductId.ofRepoIdOrNull( invoiceLineQuickInput.getM_Product_ID() );

		if ( productId == null)
		{
			return;
		}
		final I_C_Invoice invoice = quickInput.getRootDocumentAs(I_C_Invoice.class);

		final Optional<DefaultPackingItemCriteria> defaultPackingItemCriteria = DefaultPackingItemCriteria.of(invoice, productId);

		final I_M_HU_PI_Item_Product huPIItemProduct = defaultPackingItemCriteria.flatMap(packingItemProductFieldHelper::getDefaultPackingMaterial).orElse(null);

		invoiceLineQuickInput.setM_HU_PI_Item_Product(huPIItemProduct);
	}
}
