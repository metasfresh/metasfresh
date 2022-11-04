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
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.sap.api.model.creditlimit.CreditLimitRow;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestComposite;
import de.metas.common.bpartner.v2.request.creditLimit.JsonRequestCreditLimitUpsert;
import de.metas.common.bpartner.v2.request.creditLimit.JsonRequestCreditLimitUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.util.Check;
import de.metas.common.util.NumberUtils;
import de.metas.common.util.time.SystemTime;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.camel.RuntimeCamelException;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static de.metas.camel.externalsystems.sap.common.ExternalIdentifierFormat.formatExternalId;

@EqualsAndHashCode
@NoArgsConstructor
public class SyncCreditLimitRequestBuilder
{
	private final static String SUPPORTED_CREDIT_LIMIT_TYPE = "Insurance";
	private final static String PATTERN = "yyyyMMdd";

	@Nullable
	private String groupIdentifier;

	@NonNull
	private final ImmutableList.Builder<JsonRequestCreditLimitUpsertItem> creditLimitUpsertGroupBuilder = new ImmutableList.Builder<>();

	@NonNull
	private final AtomicBoolean built = new AtomicBoolean(false);

	private SyncCreditLimitRequestBuilder(@NonNull final String groupIdentifier)

	{
		this.groupIdentifier = groupIdentifier;
	}

	@NonNull
	public static SyncCreditLimitRequestBuilder of(@NonNull final InitCreditLimitGroup initCreditLimitGroup)
	{
		final CreditLimitRow creditLimitRow = initCreditLimitGroup.getCreditLimitRow();

		final SyncCreditLimitRequestBuilder builder = new SyncCreditLimitRequestBuilder(buildGroupIdentifier(creditLimitRow));

		builder.addCreditLimitRow(creditLimitRow, initCreditLimitGroup.getOrgCode());

		return builder;
	}

	public boolean addCreditLimitRow(@NonNull final CreditLimitRow creditLimitRow, @NonNull final String orgCode)
	{
		if (built.get())
		{
			throw new RuntimeException("SyncCreditLimitRequestBuilder built!");
		}

		final String currentGroupIdentifier = buildGroupIdentifier(creditLimitRow);
		if (Check.isNotBlank(groupIdentifier) && !sameGroup(currentGroupIdentifier, groupIdentifier))
		{
			return false;
		}

		groupIdentifier = currentGroupIdentifier;

		creditLimitUpsertGroupBuilder.add(mapCreditLimitRowToJsonRequestCreditLimitUpsertItem(creditLimitRow, orgCode));

		return true;
	}

	@NonNull
	public BPUpsertCamelRequest build()
	{
		final JsonRequestCreditLimitUpsert creditLimitUpsert = wrapCreditLimitUpsertItems(creditLimitUpsertGroupBuilder.build());

		built.set(true);

		return createBPUpsertCamelRequest(creditLimitUpsert);
	}

	@NonNull
	public String getBPartnerIdentifierNotNull()
	{
		if (Check.isBlank(groupIdentifier))
		{
			throw new RuntimeCamelException("GroupIdentifier shouldn't be missing at this stage!");
		}

		return groupIdentifier.concat("00");
	}

	@NonNull
	private JsonRequestCreditLimitUpsertItem mapCreditLimitRowToJsonRequestCreditLimitUpsertItem(@NonNull final CreditLimitRow creditLimitRow, @NonNull final String orgCode)
	{
		final JsonRequestCreditLimitUpsertItem jsonRequestCreditLimitUpsertItem = new JsonRequestCreditLimitUpsertItem();

		jsonRequestCreditLimitUpsertItem.setType(SUPPORTED_CREDIT_LIMIT_TYPE);
		jsonRequestCreditLimitUpsertItem.setAmount(NumberUtils.asBigDecimal(creditLimitRow.getCreditLine()));
		jsonRequestCreditLimitUpsertItem.setCurrencyCode(creditLimitRow.getCurrencyCode());
		jsonRequestCreditLimitUpsertItem.setOrgCode(orgCode);
		jsonRequestCreditLimitUpsertItem.setDateFrom(LocalDate.parse(creditLimitRow.getEffectiveDateFrom(), DateTimeFormatter.ofPattern(PATTERN)));
		jsonRequestCreditLimitUpsertItem.setProcessed(true);
		jsonRequestCreditLimitUpsertItem.setActive(computeIsActiveCreditLimit(creditLimitRow));

		return jsonRequestCreditLimitUpsertItem;
	}

	@NonNull
	private JsonRequestCreditLimitUpsert wrapCreditLimitUpsertItems(@NonNull final List<JsonRequestCreditLimitUpsertItem> upsertItems)
	{
		return JsonRequestCreditLimitUpsert.builder()
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.requestItems(upsertItems)
				.build();
	}

	@NonNull
	private BPUpsertCamelRequest createBPUpsertCamelRequest(@NonNull final JsonRequestCreditLimitUpsert jsonRequestCreditLimitUpsert)
	{
		final JsonRequestBPartnerUpsertItem jsonRequestBPartnerUpsertItem = createJsonRequestBPartnerUpsertItem(jsonRequestCreditLimitUpsert);

		final JsonRequestBPartnerUpsert jsonRequestBPartnerUpsert = JsonRequestBPartnerUpsert.builder()
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.requestItems(ImmutableList.of(jsonRequestBPartnerUpsertItem))
				.build();

		return BPUpsertCamelRequest.builder()
				.jsonRequestBPartnerUpsert(jsonRequestBPartnerUpsert)
				.orgCode(jsonRequestCreditLimitUpsert.getRequestItems().get(0).getOrgCode())
				.build();
	}

	@NonNull
	private JsonRequestBPartnerUpsertItem createJsonRequestBPartnerUpsertItem(@NonNull final JsonRequestCreditLimitUpsert jsonRequestCreditLimitUpsert)
	{
		return JsonRequestBPartnerUpsertItem.builder()
				.bpartnerIdentifier(formatExternalId(getBPartnerIdentifierNotNull()))
				.bpartnerComposite(JsonRequestComposite.builder()
										   .orgCode(jsonRequestCreditLimitUpsert.getRequestItems().get(0).getOrgCode())
										   .creditLimits(jsonRequestCreditLimitUpsert)
										   .syncAdvise(SyncAdvise.CREATE_OR_MERGE)
										   .build())
				.build();
	}

	private boolean computeIsActiveCreditLimit(@NonNull final CreditLimitRow creditLimitRow)
	{
		if ("X".equals(creditLimitRow.getDeleteFlag()))
		{
			return false;
		}

		final LocalDate dateFrom = LocalDate.parse(creditLimitRow.getEffectiveDateFrom(), DateTimeFormatter.ofPattern(PATTERN));
		final LocalDate dateTo = LocalDate.parse(creditLimitRow.getEffectiveDateTo(), DateTimeFormatter.ofPattern(PATTERN));

		final LocalDate currentDate = SystemTime.asLocalDate();

		return currentDate.isAfter(dateFrom) && currentDate.isBefore(dateTo);
	}

	@NonNull
	private static String buildGroupIdentifier(@NonNull final CreditLimitRow creditLimitRow)
	{
		return creditLimitRow.getCreditControlArea() + "_" + creditLimitRow.getCreditAccount().substring(0, creditLimitRow.getCreditAccount().length() - 2);
	}

	private static boolean sameGroup(@NonNull final String currentGroupIdentifier, @NonNull final String groupIdentifier)
	{
		return groupIdentifier.equals(currentGroupIdentifier);
	}
}
