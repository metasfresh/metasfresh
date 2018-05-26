package de.metas.purchasecandidate;

import java.util.OptionalInt;

import javax.annotation.Nullable;

import org.adempiere.bpartner.BPartnerId;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.util.Util;

import de.metas.payment.api.IPaymentTermRepository;
import de.metas.payment.api.PaymentTermId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

@Value
public class VendorProductInfo
{
	OptionalInt bpartnerProductId;

	BPartnerId vendorBPartnerId;

	/** can be null if the resp. vendor can no payment term and none is flagged as default. */
	PaymentTermId paymentTermId;

	ProductId productId;

	String productNo;
	String productName;

	boolean aggregatePOs;

	public static VendorProductInfo fromDataRecord(@NonNull final I_C_BPartner_Product bpartnerProductRecord)
	{
		final BPartnerId bpartnerVendorIdOverride = null;
		final Boolean aggregatePOsOverride = null; // N/A
		return fromDataRecord(bpartnerProductRecord, bpartnerVendorIdOverride, aggregatePOsOverride);
	}

	public static VendorProductInfo fromDataRecord(
			@NonNull final I_C_BPartner_Product bpartnerProductRecord,
			final BPartnerId bpartnerVendorIdOverride,
			final Boolean aggregatePOsOverride)
	{
		final String productNo = Util.coalesceSuppliers(
				() -> bpartnerProductRecord.getVendorProductNo(),
				() -> bpartnerProductRecord.getProductNo(),
				() -> bpartnerProductRecord.getM_Product().getValue());

		final String productName = Util.coalesceSuppliers(
				() -> bpartnerProductRecord.getProductName(),
				() -> bpartnerProductRecord.getM_Product().getName());

		final BPartnerId bpartnerVendorId = Util.coalesceSuppliers(
				() -> bpartnerVendorIdOverride,
				() -> BPartnerId.ofRepoIdOrNull(bpartnerProductRecord.getC_BPartner_ID()));

		final PaymentTermId paymentTermId = retrievePaymentTermIdOrNull(bpartnerProductRecord.getC_BPartner());

		final boolean aggregatePOs;
		if (aggregatePOsOverride != null)
		{
			aggregatePOs = aggregatePOsOverride;
		}
		else
		{
			final I_C_BPartner bpartner = Services.get(IBPartnerDAO.class).getById(bpartnerVendorId);
			aggregatePOs = bpartner.isAggregatePO();
		}

		return builder()
				.bpartnerProductId(bpartnerProductRecord.getC_BPartner_Product_ID())
				.vendorBPartnerId(bpartnerVendorId)
				.paymentTermId(paymentTermId)
				.productId(ProductId.ofRepoId(bpartnerProductRecord.getM_Product_ID()))
				.productNo(productNo)
				.productName(productName)
				.aggregatePOs(aggregatePOs)
				.build();
	}

	private static PaymentTermId retrievePaymentTermIdOrNull(@NonNull final I_C_BPartner bpartnerRecord)
	{
		if (bpartnerRecord.getPO_PaymentTerm_ID() > 0)
		{
			return PaymentTermId.ofRepoId(bpartnerRecord.getPO_PaymentTerm_ID());
		}

		return Services
				.get(IPaymentTermRepository.class)
				.getDefaultPaymentTermIdOrNull();
	}

	@Builder
	private VendorProductInfo(
			final int bpartnerProductId,
			@NonNull final BPartnerId vendorBPartnerId,
			@Nullable final PaymentTermId paymentTermId,
			@NonNull final ProductId productId,
			@NonNull final String productNo,
			@NonNull final String productName,
			final boolean aggregatePOs)
	{
		this.bpartnerProductId = bpartnerProductId > 0 ? OptionalInt.of(bpartnerProductId) : OptionalInt.empty();

		this.vendorBPartnerId = vendorBPartnerId;
		this.productId = productId;

		this.productNo = productNo;
		this.productName = productName;
		this.aggregatePOs = aggregatePOs;
		this.paymentTermId = paymentTermId;
	}

}
