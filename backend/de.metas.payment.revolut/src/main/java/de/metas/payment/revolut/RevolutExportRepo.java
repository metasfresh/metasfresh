/*
 * #%L
 * de.metas.payment.revolut
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

package de.metas.payment.revolut;

import com.google.common.collect.ImmutableList;
import de.metas.currency.Amount;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.payment.revolut.model.I_Revolut_Payment_Export;
import de.metas.payment.revolut.model.RecipientType;
import de.metas.payment.revolut.model.RevolutPaymentExport;
import de.metas.payment.revolut.model.RevolutPaymentExportId;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.loadOrNew;
import static org.adempiere.model.InterfaceWrapperHelper.save;

@Repository
public class RevolutExportRepo
{
	private final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);

	@NonNull
	public List<RevolutPaymentExport> saveAll(@NonNull final List<RevolutPaymentExport> revolutPaymentExportList)
	{
		return revolutPaymentExportList.stream()
				.map(this::saveRevolutPaymentExport)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public RevolutPaymentExport saveRevolutPaymentExport(@NonNull final RevolutPaymentExport request)
	{
		final I_Revolut_Payment_Export record = prepareRevolutPaymentExport(request);
		save(record);

		return toRevolutExport(record);
	}

	@NonNull
	private I_Revolut_Payment_Export prepareRevolutPaymentExport(@NonNull final RevolutPaymentExport request)
	{
		final I_Revolut_Payment_Export record = loadOrNew(request.getRevolutPaymentExportId(), I_Revolut_Payment_Export.class);

		final Currency currency = currencyDAO.getByCurrencyCode(request.getAmount().getCurrencyCode());

		record.setAD_Org_ID(request.getOrgId().getRepoId());
		record.setAD_Table_ID(request.getRecordReference().getAdTableId().getRepoId());
		record.setRecord_ID(request.getRecordReference().getRecord_ID());

		record.setName(request.getName());
		record.setC_Currency_ID(currency.getId().getRepoId());
		record.setAmount(request.getAmount().getAsBigDecimal());
		record.setRecipientType(request.getRecipientType().getCode());
		record.setRecipientBankCountryId(NumberUtils.asInt(request.getRecipientBankCountryId(), -1));

		record.setAccountNo(request.getAccountNo());
		record.setRoutingNo(request.getRoutingNo());
		record.setIBAN(request.getIBAN());
		record.setSwiftCode(request.getSwiftCode());
		record.setPaymentReference(request.getPaymentReference());

		record.setRecipientCountryId(NumberUtils.asInt(request.getRecipientCountryId(), -1));
		record.setAddressLine1(request.getAddressLine1());
		record.setAddressLine2(request.getAddressLine2());
		record.setPostalCode(request.getPostalCode());
		record.setCity(request.getCity());
		record.setRegionName(request.getRegionName());

		return record;
	}

	@NonNull
	private RevolutPaymentExport toRevolutExport(@NonNull final I_Revolut_Payment_Export record)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());
		final CurrencyCode currencyCode = currencyDAO.getCurrencyCodeById(currencyId);
		final Amount amount = Amount.of(record.getAmount(), currencyCode);

		final TableRecordReference recordRef = TableRecordReference.of(record.getAD_Table_ID(), record.getRecord_ID());

		return RevolutPaymentExport.builder()
				.revolutPaymentExportId(RevolutPaymentExportId.ofRepoId(record.getRevolut_Payment_Export_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.recordReference(recordRef)
				.name(record.getName())
				.recipientType(RecipientType.ofCode(record.getRecipientType()))
				.accountNo(record.getAccountNo())
				.routingNo(record.getRoutingNo())
				.IBAN(record.getIBAN())
				.SwiftCode(record.getSwiftCode())
				.amount(amount)
				.recipientBankCountryId(CountryId.ofRepoIdOrNull(record.getRecipientBankCountryId()))
				.paymentReference(record.getPaymentReference())
				.recipientCountryId(CountryId.ofRepoIdOrNull(record.getRecipientCountryId()))
				.regionName(record.getRegionName())
				.addressLine1(record.getAddressLine1())
				.addressLine2(record.getAddressLine2())
				.city(record.getCity())
				.postalCode(record.getPostalCode())
				.build();
	}
}
