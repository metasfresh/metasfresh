package de.metas.purchasecandidate.material.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.purchase.PurchaseCandidateCreatedEvent;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import de.metas.quantity.Quantity;
import lombok.NonNull;

/*
 * #%L
 * de.metas.purchasecandidate.base
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

@Interceptor(I_C_PurchaseCandidate.class)
@Component
public class C_PurchaseCandidate
{
	private final PostMaterialEventService postMaterialEventService;
	private final ModelProductDescriptorExtractor productDescriptorFactory;

	public C_PurchaseCandidate(@NonNull final PostMaterialEventService postMaterialEventService,
			@NonNull final ModelProductDescriptorExtractor productDescriptorFactory)
	{
		this.postMaterialEventService = postMaterialEventService;
		this.productDescriptorFactory = productDescriptorFactory;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void postEvent(@NonNull final I_C_PurchaseCandidate purchaseCandidateRecord)
	{
		ProductDescriptor productDescriptor = productDescriptorFactory.createProductDescriptor(purchaseCandidateRecord);

		final Quantity purchaseQty = Services.get(IUOMConversionBL.class)
				.convertToProductUOM(Quantity.of(purchaseCandidateRecord.getQtyToPurchase(), purchaseCandidateRecord.getC_UOM()),
						purchaseCandidateRecord.getM_Product_ID());

		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.bPartnerId(purchaseCandidateRecord.getVendor_ID())
				.date(purchaseCandidateRecord.getDateRequired())
				.productDescriptor(productDescriptor)
				.quantity(purchaseQty.getQty())
				.build();

		final PurchaseCandidateCreatedEvent purchaseCandidateCreatedEvent = PurchaseCandidateCreatedEvent.builder()
				.purchaseCandidateRepoId(purchaseCandidateRecord.getC_PurchaseCandidate_ID())
				.purchaseMaterialDescriptor(materialDescriptor)
				//.
				.build();

		postMaterialEventService.postEventAfterNextCommit(purchaseCandidateCreatedEvent);
	}
}
