package de.metas.vertical.pharma;

import java.util.Collections;

import javax.annotation.Nonnull;

import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import com.google.common.annotations.VisibleForTesting;

import de.metas.bpartner.BPartnerId;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderLineInputValidator;
import de.metas.order.OrderLineInputValidatorResults;
import de.metas.order.OrderLineInputValidatorResults.OrderLineInputValidatorResultsBuilder;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.common.util.CoalesceUtil;
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
public class PharmaBPartnerProductPermissionValidator implements IOrderLineInputValidator
{
	private final static AdMessageKey MSG_NoPharmaShipmentPermission_Sales = AdMessageKey.of("de.metas.vertical.pharma.PharmaOrderLineQuickInputValidator.NoPharmaShipmentPermissions");
	@VisibleForTesting
	final static AdMessageKey MSG_NoPrescriptionPermission_Sales = AdMessageKey.of("de.metas.vertical.pharma.PharmaOrderLineQuickInputValidator.NoPrescriptionPermission");
	@VisibleForTesting
	final static AdMessageKey MSG_NoNarcoticPermission_Sales = AdMessageKey.of("de.metas.vertical.pharma.PharmaOrderLineQuickInputValidator.NoNarcoticPermissions");

	@VisibleForTesting
	final static AdMessageKey MSG_NoNarcoticPermission_Purchase = AdMessageKey.of("de.metas.vertical.pharma.PharmaPurchaseOrderLineInputValidator.NoNarcoticPermission");
	@VisibleForTesting
	final static AdMessageKey MSG_NoPrescriptionPermission_Purchase = AdMessageKey.of("de.metas.vertical.pharma.PharmaPurchaseOrderLineInputValidator.NoPrescriptionPermission");

	private final PharmaBPartnerRepository pharmaBPartnerRepo;
	private final PharmaProductRepository pharmaProductRepo;

	public PharmaBPartnerProductPermissionValidator(
			@NonNull final PharmaBPartnerRepository pharmaBPartnerRepo,
			@NonNull final PharmaProductRepository pharmaProductRepo)
	{
		this.pharmaBPartnerRepo = pharmaBPartnerRepo;
		this.pharmaProductRepo = pharmaProductRepo;
	}

	/**
	 * TypeB can only ship non-prescription products.
	 * TypeA can ship typeB + prescription.
	 * TypeC can ship typeA + narcotics.
	 */
	@Override
	public OrderLineInputValidatorResults validate(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final ProductId productId,
			@NonNull final SOTrx soTrx)
	{
		if (soTrx.isSales())
		{
			return evaluateSalesPrescriptionPermission(bpartnerId, productId);
		}
		else
		{
			return evaluatePurchasePrescriptionPermission(bpartnerId, productId);
		}
	}

	private @Nonnull OrderLineInputValidatorResults evaluatePurchasePrescriptionPermission(final BPartnerId bpartnerId, final ProductId productId)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final OrderLineInputValidatorResults.OrderLineInputValidatorResultsBuilder resultBuilder = OrderLineInputValidatorResults.builder();

		final PharmaBPartner bPartner = pharmaBPartnerRepo.getById(bpartnerId);
		final PharmaProduct product = pharmaProductRepo.getById(productId);

		if (PharmaReceiptPermission.TYPE_C.equals(bPartner.getReceiptPermission()))
		{
			return resultBuilder.isValid(true).build();
		}

		if (product.isNarcotic())
		{
			final ITranslatableString noPermissionMessage = msgBL.getTranslatableMsgText(
					MSG_NoNarcoticPermission_Purchase,
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
				MSG_NoPrescriptionPermission_Purchase,
				product.getValue(),
				bPartner.getName());

		return resultBuilder.isValid(false).errorMessage(noPermissionMessage).build();

	}

	private @Nonnull OrderLineInputValidatorResults evaluateSalesPrescriptionPermission(@NonNull final BPartnerId bpartnerId, @NonNull final ProductId productId)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final OrderLineInputValidatorResultsBuilder resultBuilder = OrderLineInputValidatorResults.builder();

		final PharmaBPartner bPartner = pharmaBPartnerRepo.getById(bpartnerId);
		final PharmaProduct product = pharmaProductRepo.getById(productId);

		if (PharmaShipmentPermission.TYPE_C.equals(bPartner.getShipmentPermission()))
		{
			return resultBuilder.isValid(true).build();
		}

		if (product.isNarcotic())
		{
			final ITranslatableString noPermissionMessage = msgBL.getTranslatableMsgText(
					MSG_NoNarcoticPermission_Sales,
					product.getValue(),
					bPartner.getName());
			return resultBuilder.isValid(false).errorMessage(noPermissionMessage).build();
		}

		if (bPartner.isHasAtLeastOneCustomerPermission())
		{
			return resultBuilder.isValid(true).build();
		}

		if (!product.isPrescriptionRequired())
		{
			return resultBuilder.isValid(true).build();
		}

		final ITranslatableString noPermissionReason = msgBL.getTranslatableMsgText(MSG_NoPharmaShipmentPermission_Sales, Collections.emptyList());

		final ITranslatableString noPermissionMessage = msgBL.getTranslatableMsgText(
				MSG_NoPrescriptionPermission_Sales,
				product.getValue(),
				bPartner.getName(),
				CoalesceUtil.coalesce(bPartner.getShipmentPermission(), noPermissionReason.translate(Env.getAD_Language())));

		return resultBuilder.isValid(false).errorMessage(noPermissionMessage).build();

	}

}
