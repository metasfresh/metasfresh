package de.metas.vertical.pharma;

import com.google.common.annotations.VisibleForTesting;
import de.metas.bpartner.BPartnerId;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.order.IOrderLineInputValidator;
import de.metas.order.OrderLineInputValidatorResults;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.springframework.stereotype.Component;

/*
 * #%L
 * metasfresh-pharma
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

@Component
public class PharmaPurchaseOrderLineInputValidator implements IOrderLineInputValidator
{
	private final PharmaBPartnerRepository pharmaBPartnerRepo;
	private final PharmaProductRepository pharmaProductRepo;

	@VisibleForTesting final static String MSG_NoNarcoticPermission = "de.metas.vertical.pharma.PharmaPurchaseOrderLineInputValidator.NoNarcoticPermission";
	@VisibleForTesting final static String MSG_NoPrescriptionPermission = "de.metas.vertical.pharma.PharmaPurchaseOrderLineInputValidator.NoPrescriptionPermission";

	public PharmaPurchaseOrderLineInputValidator(final PharmaBPartnerRepository pharmaBPartnerRepo, final PharmaProductRepository pharmaProductRepo)
	{
		this.pharmaBPartnerRepo = pharmaBPartnerRepo;
		this.pharmaProductRepo = pharmaProductRepo;
	}

	@Override public OrderLineInputValidatorResults validate(final BPartnerId bpartnerId, final ProductId productId)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final OrderLineInputValidatorResults.OrderLineInputValidatorResultsBuilder resultBuilder = OrderLineInputValidatorResults.builder();

		final PharmaBPartner bPartner = pharmaBPartnerRepo.getById(bpartnerId);
		final PharmaProduct product = pharmaProductRepo.getById(productId);

		if (bPartner.getReceiptPermission().equals(PharmaReceiptPermission.TYPE_C))
		{
			return resultBuilder.isValid(true).build();
		}

		if (product.isNarcotic())
		{
			final ITranslatableString noPermissionMessage = msgBL.getTranslatableMsgText(
					MSG_NoNarcoticPermission,
					product.getValue(),
					bPartner.getName());
			return resultBuilder.isValid(false).errorMessage(noPermissionMessage).build();
		}

		if (bPartner.isHasAtLeastOneVendorPermission())
		{
			return resultBuilder.isValid(true).build();
		}

		if (!product.isPrescriptionRequired())
		{
			return resultBuilder.isValid(true).build();
		}

		final ITranslatableString noPermissionMessage = msgBL.getTranslatableMsgText(
				MSG_NoPrescriptionPermission,
				product.getValue(),
				bPartner.getName());

		return resultBuilder.isValid(false).errorMessage(noPermissionMessage).build();
	}
}
