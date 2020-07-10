package de.metas.payment.paypal.config;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Repository;

import de.metas.cache.CCache;
import de.metas.email.templates.MailTemplateId;
import de.metas.payment.paypal.model.I_PayPal_Config;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.payment.paypalplus
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

@Repository
public class PayPalConfigRepository implements PayPalConfigProvider
{
	private final CCache<Integer, PayPalConfig> cache = CCache.<Integer, PayPalConfig> builder()
			.tableName(I_PayPal_Config.Table_Name)
			.build();

	@Override
	public PayPalConfig getConfig()
	{
		return cache.getOrLoad(0, this::retrievePayPalConfig);
	}

	private PayPalConfig retrievePayPalConfig()
	{
		final I_PayPal_Config record = Services.get(IQueryBL.class).createQueryBuilderOutOfTrx(I_PayPal_Config.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_PayPal_Config.class);
		if (record == null)
		{
			throw new AdempiereException("@NotFound@ @PayPal_Config_ID@");
		}

		return toPayPalConfig(record);
	}

	private static PayPalConfig toPayPalConfig(@NonNull final I_PayPal_Config record)
	{
		return PayPalConfig.builder()
				.clientId(record.getPayPal_ClientId())
				.clientSecret(record.getPayPal_ClientSecret())
				.sandbox(record.isPayPal_Sandbox())
				.baseUrl(record.getPayPal_BaseUrl())
				.webUrl(record.getPayPal_WebUrl())
				//
				.orderApproveMailTemplateId(MailTemplateId.ofRepoId(record.getPayPal_PayerApprovalRequest_MailTemplate_ID()))
				.orderApproveCallbackUrl(record.getPayPal_PaymentApprovedCallbackUrl())
				//
				.build();
	}

}
