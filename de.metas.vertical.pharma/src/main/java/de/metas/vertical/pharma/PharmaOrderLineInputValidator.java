package de.metas.vertical.pharma;

import java.util.Collections;

import org.compiere.util.Env;
import org.compiere.util.Util;
import org.springframework.stereotype.Component;

import de.metas.bpartner.BPartnerId;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.order.IOrderLineInputValidator;
import de.metas.order.OrderLineInputValidatorResults;
import de.metas.order.OrderLineInputValidatorResults.OrderLineInputValidatorResultsBuilder;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;

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
public class PharmaOrderLineInputValidator implements IOrderLineInputValidator
{
	private final static String MSG_NoPrescriptionPermission = "de.metas.vertical.pharma.PharmaOrderLineQuickInputValidator.NoPrescriptionPermission";
	private final static String MSG_NoPharmaShipmentPermission = "de.metas.vertical.pharma.PharmaOrderLineQuickInputValidator.NoPharmaShipmentPermissions";

	private final PharmaBPartnerRepository pharmaBPartnerRepo;
	private final PharmaProductRepository pharmaProductRepo;

	public PharmaOrderLineInputValidator(
			@NonNull PharmaBPartnerRepository pharmaBPartnerRepo,
			@NonNull PharmaProductRepository pharmaProductRepo)
	{
		this.pharmaBPartnerRepo = pharmaBPartnerRepo;
		this.pharmaProductRepo = pharmaProductRepo;
	}

	@Override
	public OrderLineInputValidatorResults validate(final @NonNull BPartnerId bpartnerId, final @NonNull ProductId productId)
	{
		return evaluatePrescriptionPermission(bpartnerId, productId);
	}

	private OrderLineInputValidatorResults evaluatePrescriptionPermission(@NonNull final BPartnerId bpartnerId, @NonNull final ProductId productId)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final OrderLineInputValidatorResultsBuilder resultBuilder = OrderLineInputValidatorResults.builder();

		final PharmaBPartner bpartner = pharmaBPartnerRepo.getById(bpartnerId);
		if (bpartner.isHasAtLeastOnePermission())
		{
			return resultBuilder.isValid(true).build();
		}

		final PharmaProduct product = pharmaProductRepo.getById(productId);
		if (!product.isPrescriptionRequired())
		{
			return resultBuilder.isValid(true).build();
		}

		final ITranslatableString noPermissionReason = msgBL.getTranslatableMsgText(MSG_NoPharmaShipmentPermission, Collections.emptyList());

		final ITranslatableString noPermissionMessage = msgBL.getTranslatableMsgText(MSG_NoPrescriptionPermission,
				product.getValue(),
				bpartner.getName(),
				Util.coalesce(bpartner.getShipmentPermission(), noPermissionReason.translate(Env.getAD_Language())));

		return resultBuilder.isValid(false)
				.errorMessage(noPermissionMessage).build();

	}

}
