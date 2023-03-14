/*
 * #%L
 * de-metas-camel-sap-file-import
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

package de.metas.camel.externalsystems.sap.creditlimit;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.sap.bpartner.UpsertBPartnerRequestBuilder;
import de.metas.camel.externalsystems.sap.creditlimit.info.UpsertCreditLimitRequest;
import de.metas.camel.externalsystems.sap.model.bpartner.PartnerCode;
import de.metas.camel.externalsystems.sap.model.creditlimit.CreditLimitRow;
import de.metas.common.bpartner.v2.request.creditLimit.JsonMoney;
import de.metas.common.bpartner.v2.request.creditLimit.JsonRequestCreditLimitUpsert;
import de.metas.common.bpartner.v2.request.creditLimit.JsonRequestCreditLimitUpsertItem;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.util.Check;
import de.metas.common.util.NumberUtils;
import de.metas.common.util.time.SystemTime;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static de.metas.camel.externalsystems.sap.SAPConstants.DEFAULT_DATE_FORMAT;

@Value
public class UpsertCreditLimitRequestBuilder
{
	private final static String DEFAULT_CREDIT_LIMIT_TYPE = "Insurance";
	private final static String DELETED_FLAG = "X";

	@NonNull
	String orgCode;

	@NonNull
	PartnerCode partnerCode;

	@NonNull
	String sectionCode;

	@NonNull
	ImmutableList.Builder<JsonRequestCreditLimitUpsertItem> creditLimitUpsertGroupBuilder = new ImmutableList.Builder<>();

	@Nullable
	JsonMetasfreshId creditLimitResponsibleUser;

	@NonNull
	public static UpsertCreditLimitRequestBuilder of(
			@NonNull final CreditLimitRow creditLimitRow,
			@NonNull final String orgCode,
			@Nullable final JsonMetasfreshId creditLimitResponsibleUser)
	{
		final UpsertCreditLimitRequestBuilder requestBuilder = new UpsertCreditLimitRequestBuilder(
				creditLimitRow.getCreditAccount(),
				creditLimitRow.getCreditControlArea(),
				orgCode,
				creditLimitResponsibleUser);

		requestBuilder.addCreditLimitRow(creditLimitRow);

		return requestBuilder;
	}

	private UpsertCreditLimitRequestBuilder(
			@NonNull final PartnerCode partnerCode,
			@NonNull final String sectionCode,
			@NonNull final String orgCode,
			@Nullable final JsonMetasfreshId creditLimitResponsibleUser)
	{
		this.partnerCode = partnerCode;
		this.orgCode = orgCode;
		this.sectionCode = sectionCode;
		this.creditLimitResponsibleUser = creditLimitResponsibleUser;
	}

	public boolean addCreditLimitRow(@NonNull final CreditLimitRow creditLimitRow)
	{
		if (!isForSamePartner(creditLimitRow))
		{
			return false;
		}

		creditLimitUpsertGroupBuilder.add(getRequestItem(creditLimitRow));

		return true;
	}

	@NonNull
	public UpsertCreditLimitRequest build()
	{
		final JsonRequestCreditLimitUpsert creditLimitUpsert = wrapCreditLimitUpsertItems(creditLimitUpsertGroupBuilder.build());

		return UpsertCreditLimitRequest.builder()
				.orgCode(orgCode)
				.bpartnerIdentifier(UpsertBPartnerRequestBuilder.buildExternalIdentifier(partnerCode.getPartnerCode(), sectionCode))
				.jsonRequestCreditLimitUpsert(creditLimitUpsert)
				.build();
	}

	@NonNull
	private JsonRequestCreditLimitUpsertItem getRequestItem(@NonNull final CreditLimitRow creditLimitRow)
	{
		final JsonRequestCreditLimitUpsertItem jsonRequestCreditLimitUpsertItem = new JsonRequestCreditLimitUpsertItem();

		jsonRequestCreditLimitUpsertItem.setType(DEFAULT_CREDIT_LIMIT_TYPE);
		jsonRequestCreditLimitUpsertItem.setAmount(getCreditLimitAmount(creditLimitRow));
		jsonRequestCreditLimitUpsertItem.setDateFrom(creditLimitRow.getEffectiveDateFrom(DEFAULT_DATE_FORMAT).orElse(null));
		jsonRequestCreditLimitUpsertItem.setProcessed(true);
		jsonRequestCreditLimitUpsertItem.setActive(computeIsActiveCreditLimit(creditLimitRow));

		if (creditLimitResponsibleUser != null)
		{
			jsonRequestCreditLimitUpsertItem.setApprovedBy(creditLimitResponsibleUser);
		}

		return jsonRequestCreditLimitUpsertItem;
	}

	private boolean computeIsActiveCreditLimit(@NonNull final CreditLimitRow creditLimitRow)
	{
		if (DELETED_FLAG.equalsIgnoreCase(creditLimitRow.getDeleteFlag()))
		{
			return false;
		}

		final LocalDate dateTo = creditLimitRow.getEffectiveDateTo(DEFAULT_DATE_FORMAT).orElse(null);

		final LocalDate currentDate = SystemTime.asLocalDate();

		return dateTo == null || currentDate.isBefore(dateTo);
	}

	@NonNull
	private JsonMoney getCreditLimitAmount(@NonNull final CreditLimitRow creditLimitRow)
	{
		final BigDecimal amount = NumberUtils.asBigDecimal(creditLimitRow.getCreditLine());

		if (amount == null)
		{
			throw new RuntimeException("Credit limit line is missing amount! RawPartnerCode: " + creditLimitRow.getCreditAccount().getRawPartnerCode());
		}

		if (Check.isBlank(creditLimitRow.getCurrencyCode()))
		{
			throw new RuntimeException("Credit limit line is missing currency! Skipping... RawPartnerCode: " + creditLimitRow.getCreditAccount().getRawPartnerCode());
		}

		return JsonMoney.builder()
				.amount(amount)
				.currencyCode(creditLimitRow.getCurrencyCode())
				.build();
	}

	private boolean isForSamePartner(@NonNull final CreditLimitRow creditLimitRow)
	{
		return creditLimitRow.getCreditAccount().getPartnerCode().equals(partnerCode.getPartnerCode())
				&& creditLimitRow.getCreditControlArea().equals(sectionCode);
	}

	@NonNull
	private static JsonRequestCreditLimitUpsert wrapCreditLimitUpsertItems(@NonNull final List<JsonRequestCreditLimitUpsertItem> upsertItems)
	{
		return JsonRequestCreditLimitUpsert.builder()
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.requestItems(upsertItems)
				.build();
	}
}
