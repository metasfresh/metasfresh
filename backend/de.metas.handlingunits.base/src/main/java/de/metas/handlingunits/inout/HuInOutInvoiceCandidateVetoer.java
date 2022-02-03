/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.handlingunits.inout;

import de.metas.handlingunits.HuPackingMaterial;
import de.metas.inoutcandidate.spi.ModelWithoutInvoiceCandidateVetoer;
import de.metas.invoicecandidate.model.I_M_InOutLine;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;

/**
 * Prevent creating new invoice candidates for packing materials that are not to be invoiced.
 */
public class HuInOutInvoiceCandidateVetoer implements ModelWithoutInvoiceCandidateVetoer
{
	IHUPackingMaterialDAO packingMaterialDAO = Services.get(IHUPackingMaterialDAO.class);

	/**
	 * @see #HuInOutInvoiceCandidateVetoer()
	 */
	@Override
	public OnMissingCandidate foundModelWithoutInvoiceCandidate(final Object model)
	{
		final I_M_InOutLine inOutLine = InterfaceWrapperHelper.create(model, I_M_InOutLine.class);

		final ProductId productId = ProductId.ofRepoId(inOutLine.getM_Product_ID());
		final HuPackingMaterial packingMaterial = packingMaterialDAO.retrieveBy(HuPackingMaterialQuery.builder()
						.productId(productId)
						.build())
				.stream()
				.findFirst()
				.orElse(null);
		if (packingMaterial != null && !packingMaterial.isInvoiceable())
		{
			return OnMissingCandidate.I_VETO;
		}
		return OnMissingCandidate.I_DONT_CARE;
	}
}
