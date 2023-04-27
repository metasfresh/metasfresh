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

package de.metas.camel.externalsystems.sap.bpartner;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.sap.common.ExternalIdentifierFormat;
import de.metas.camel.externalsystems.sap.model.bpartner.BPartnerRow;
import de.metas.camel.externalsystems.sap.model.bpartner.PartnerCategory;
import de.metas.camel.externalsystems.sap.model.bpartner.PartnerCode;
import de.metas.common.bpartner.v2.common.JsonDeliveryRule;
import de.metas.common.bpartner.v2.common.JsonDeliveryViaRule;
import de.metas.common.bpartner.v2.common.JsonPaymentRule;
import de.metas.common.bpartner.v2.request.JsonRequestBPartner;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestComposite;
import de.metas.common.bpartner.v2.request.JsonRequestLocation;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsertItem;
import de.metas.common.externalsystem.JsonExternalSAPBPartnerImportSettings;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.util.Check;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.metas.camel.externalsystems.sap.SAPConstants.BPARTNER_DEFAULT_LANGUAGE;

@Value
@Builder
public class UpsertBPartnerRequestBuilder
{
	private static final String VAL_EXTERNAL_IDENTIFIER_PREFIX = "val-";

	@NonNull
	PartnerCode parentPartnerCode;

	@Nullable
	JsonRequestBPartnerUpsertItem sectionGroupBPartnerItem;

	@NonNull
	String orgCode;

	@NonNull
	JsonMetasfreshId externalSystemConfigId;

	@NonNull
	@Getter(AccessLevel.NONE)
	ArrayList<BPartnerRow> bPartnerRows;

	@NonNull
	@Getter(AccessLevel.NONE)
	ImmutableList<JsonExternalSAPBPartnerImportSettings> sapbPartnerImportSettingsList;

	@NonNull
	public static UpsertBPartnerRequestBuilder of(
			@NonNull final BPartnerRow row,
			@NonNull final String orgCode,
			@NonNull final JsonMetasfreshId externalSystemConfigId,
			@NonNull final ImmutableList<JsonExternalSAPBPartnerImportSettings> sapbPartnerImportSettings) throws Exception
	{
		final JsonExternalSAPBPartnerImportSettings partnerImportSettings = getBPartnerImportSettingsForRow(sapbPartnerImportSettings, row)
				.orElse(null);

		final JsonRequestBPartnerUpsertItem sectionGroupJsonRequestBPartnerUpsertItem = partnerImportSettings != null && partnerImportSettings.isSingleBPartner()
				? null
				: buildSectionGroupJsonRequestBPartnerUpsertItem(row, orgCode, externalSystemConfigId, partnerImportSettings);

		final UpsertBPartnerRequestBuilder syncBPartnerRequestBuilder = new UpsertBPartnerRequestBuilder(
				row.getPartnerCode(),
				sectionGroupJsonRequestBPartnerUpsertItem,
				orgCode,
				externalSystemConfigId,
				new ArrayList<>(),
				sapbPartnerImportSettings);

		syncBPartnerRequestBuilder.add(row);

		return syncBPartnerRequestBuilder;
	}

	@NonNull
	public static String buildExternalIdentifier(@NonNull final String partnerCode, @NonNull final String sectionCode)
	{
		return ExternalIdentifierFormat.formatExternalId(partnerCode + "_" + sectionCode);
	}

	@NonNull
	public static String getBPartnerExternalIdentifierTruncatedPartnerCode(@NonNull final BPartnerRow bPartnerRow)
	{
		return buildExternalIdentifier(bPartnerRow.getPartnerCode().getPartnerCode(), bPartnerRow.getSection());
	}

	@NonNull
	public static String getBPartnerExternalIdentifierRawPartnerCode(@NonNull final BPartnerRow bPartnerRow)
	{
		return buildExternalIdentifier(bPartnerRow.getPartnerCode().getRawPartnerCode(), bPartnerRow.getSection());
	}

	public boolean add(@NonNull final BPartnerRow row)
	{
		if (!row.getPartnerCode().matchesGroup(parentPartnerCode))
		{
			return false;
		}

		if (shouldImportByItself(row)
				&& !parentPartnerCode.getRawPartnerCode().equals(row.getPartnerCode().getRawPartnerCode()))
		{
			return false;
		}

		bPartnerRows.add(row);

		return true;
	}

	@NonNull
	public BPUpsertCamelRequest build() throws Exception
	{
		final ImmutableList.Builder<JsonRequestBPartnerUpsertItem> upsertBPartnerItemsCollector = ImmutableList.builder();
		getSectionBPartnerUpsertItem().ifPresent(upsertBPartnerItemsCollector::add);

		final Map<String, List<BPartnerRow>> groupedBPartnerRows = bPartnerRows.stream()
				.collect(Collectors.groupingBy(BPartnerRow::getSection));

		groupedBPartnerRows.forEach((sectionCode, partnerRows) -> {
			final JsonRequestBPartnerUpsertItem upsertItem = getJsonRequestBPartnerUpsertItem(sectionCode, partnerRows);
			upsertBPartnerItemsCollector.add(upsertItem);
		});

		final JsonRequestBPartnerUpsert jsonRequestBPartnerUpsert = JsonRequestBPartnerUpsert.builder()
				.requestItems(upsertBPartnerItemsCollector.build())
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.build();

		return BPUpsertCamelRequest.builder()
				.jsonRequestBPartnerUpsert(jsonRequestBPartnerUpsert)
				.orgCode(orgCode)
				.build();
	}

	@NonNull
	private JsonRequestBPartnerUpsertItem getJsonRequestBPartnerUpsertItem(@NonNull final String sectionCode, @NonNull final List<BPartnerRow> bPartnerRows)
	{
		Check.assumeNotEmpty(bPartnerRows, "At least one partner row must be present for section code {} when calling this method.", sectionCode);

		final ImmutableList<JsonRequestLocationUpsertItem> locationUpsertItems = bPartnerRows.stream()
				.map(this::buildJsonRequestLocationUpsertItem)
				.collect(ImmutableList.toImmutableList());

		final JsonRequestLocationUpsert jsonRequestLocationUpsert = JsonRequestLocationUpsert.builder()
				.requestItems(locationUpsertItems)
				.build();

		final BPartnerRow lastRowOfTheGroup = bPartnerRows.get(bPartnerRows.size() - 1);

		final JsonExternalSAPBPartnerImportSettings matchingBPartnerImportSettings = getBPartnerImportSettingsForRow(sapbPartnerImportSettingsList, lastRowOfTheGroup)
				.orElse(null);

		final JsonRequestBPartner jsonRequestBPartner = buildJsonRequestBPartner(lastRowOfTheGroup, matchingBPartnerImportSettings);

		final JsonRequestComposite jsonRequestComposite = JsonRequestComposite.builder()
				.bpartner(jsonRequestBPartner)
				.locations(jsonRequestLocationUpsert)
				.orgCode(orgCode)
				.build();

		final String externalIdentifier = Optional.ofNullable(matchingBPartnerImportSettings)
				.map(JsonExternalSAPBPartnerImportSettings::isSingleBPartner)
				.orElse(false)
				? getBPartnerExternalIdentifierRawPartnerCode(lastRowOfTheGroup)
				: getBPartnerExternalIdentifierTruncatedPartnerCode(lastRowOfTheGroup);

		return JsonRequestBPartnerUpsertItem.builder()
				.bpartnerComposite(jsonRequestComposite)
				.bpartnerIdentifier(externalIdentifier)
				.externalSystemConfigId(externalSystemConfigId)
				.build();
	}

	@NonNull
	private JsonRequestBPartner buildJsonRequestBPartner(
			@NonNull final BPartnerRow bPartnerRow,
			@Nullable final JsonExternalSAPBPartnerImportSettings partnerImportSettings)
	{
		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();

		Optional.ofNullable(bPartnerRow.getSalesPaymentTerms())
				.filter(Check::isNotBlank)
				.map(salesPaymentTerm -> VAL_EXTERNAL_IDENTIFIER_PREFIX + salesPaymentTerm)
				.ifPresent(jsonRequestBPartner::setCustomerPaymentTermIdentifier);

		Optional.ofNullable(bPartnerRow.getPurchasePaymentTerms())
				.filter(Check::isNotBlank)
				.map(purchasePaymentTerm -> VAL_EXTERNAL_IDENTIFIER_PREFIX + purchasePaymentTerm)
				.ifPresent(jsonRequestBPartner::setVendorPaymentTermIdentifier);

		final String bpartnerCode;
		if (partnerImportSettings != null)
		{
			Optional.ofNullable(partnerImportSettings.getBpGroupName())
					.ifPresent(jsonRequestBPartner::setGroup);

			if (partnerImportSettings.isSingleBPartner())
			{
				bpartnerCode = bPartnerRow.getPartnerCode().getRawPartnerCode();
			}
			else
			{
				bpartnerCode = bPartnerRow.getPartnerCode().getPartnerCode();
				jsonRequestBPartner.setSectionGroupPartnerIdentifier(getParentExternalIdentifier());
			}
		}
		else
		{
			bpartnerCode = bPartnerRow.getPartnerCode().getPartnerCode();
			jsonRequestBPartner.setSectionGroupPartnerIdentifier(getParentExternalIdentifier());
		}
		final String bpartnerValue = bpartnerCode + " (" + bPartnerRow.getSection() + ")";

		jsonRequestBPartner.setCode(bpartnerValue);
		jsonRequestBPartner.setCompanyName(bPartnerRow.getName1());
		jsonRequestBPartner.setName(bPartnerRow.getName1());
		jsonRequestBPartner.setName2(bPartnerRow.getName2());

		jsonRequestBPartner.setSectionCodeValue(bPartnerRow.getSection());
		jsonRequestBPartner.setDeliveryRule(JsonDeliveryRule.Availability);
		jsonRequestBPartner.setDeliveryViaRule(JsonDeliveryViaRule.Shipper);
		jsonRequestBPartner.setPaymentRule(JsonPaymentRule.OnCredit);
		jsonRequestBPartner.setPaymentRulePO(JsonPaymentRule.OnCredit);

		if (PartnerCategory.STORAGE_LOCATION == PartnerCategory.ofCodeOrNull(bPartnerRow.getPartnerCategory()))
		{
			jsonRequestBPartner.setVendor(true);
			jsonRequestBPartner.setCustomer(false);
			jsonRequestBPartner.setStorageWarehouse(true);
		}
		else
		{
			jsonRequestBPartner.setVendor(true);
			jsonRequestBPartner.setCustomer(true);
			jsonRequestBPartner.setStorageWarehouse(false);
		}

		jsonRequestBPartner.setLanguage(BPARTNER_DEFAULT_LANGUAGE);
		jsonRequestBPartner.setProspect(false);
		jsonRequestBPartner.setSapBPartnerCode(bpartnerCode);
		jsonRequestBPartner.setSectionPartner(true);

		return jsonRequestBPartner;
	}

	@NonNull
	private JsonRequestLocationUpsertItem buildJsonRequestLocationUpsertItem(@NonNull final BPartnerRow bPartnerRow)
	{
		final JsonRequestLocation jsonRequestLocation = new JsonRequestLocation();
		jsonRequestLocation.setCountryCode(bPartnerRow.getCountryKey());
		jsonRequestLocation.setCity(bPartnerRow.getCity());
		jsonRequestLocation.setAddress1(bPartnerRow.getStreet());
		jsonRequestLocation.setAddress2(bPartnerRow.getStreet2());
		jsonRequestLocation.setAddress3(bPartnerRow.getStreet3());

		final String address4 = Stream.of(bPartnerRow.getStreet4(), bPartnerRow.getStreet5())
				.filter(Check::isNotBlank)
				.collect(Collectors.joining(","));
		jsonRequestLocation.setAddress4(address4);

		jsonRequestLocation.setPostal(bPartnerRow.getPostalCode());
		jsonRequestLocation.setVisitorsAddress(false);
		jsonRequestLocation.setShipTo(true);
		jsonRequestLocation.setShipToDefault(false);
		jsonRequestLocation.setBillTo(true);
		jsonRequestLocation.setBillToDefault(false);
		jsonRequestLocation.setHandoverLocation(true);
		jsonRequestLocation.setRemitTo(false);
		jsonRequestLocation.setReplicationLookupDefault(false);

		jsonRequestLocation.setVatId(bPartnerRow.getVatRegNo());
		jsonRequestLocation.setSapPaymentMethod(bPartnerRow.getPaymentMethod());
		jsonRequestLocation.setSapBPartnerCode(bPartnerRow.getPartnerCode().getRawPartnerCode());

		return JsonRequestLocationUpsertItem.builder()
				.location(jsonRequestLocation)
				.locationIdentifier(getLocationExternalIdentifier(bPartnerRow))
				.externalSystemConfigId(externalSystemConfigId)
				.build();
	}

	@NonNull
	private String getParentExternalIdentifier()
	{
		return ExternalIdentifierFormat.formatExternalId(parentPartnerCode.getPartnerCode());
	}

	@NonNull
	private String getLocationExternalIdentifier(@NonNull final BPartnerRow bPartnerRow)
	{
		return buildExternalIdentifier(bPartnerRow.getPartnerCode().getRawPartnerCode(), bPartnerRow.getSection());
	}

	@NonNull
	private static JsonRequestBPartnerUpsertItem buildSectionGroupJsonRequestBPartnerUpsertItem(
			@NonNull final BPartnerRow bPartnerRow,
			@NonNull final String orgCode,
			@NonNull final JsonMetasfreshId externalSystemConfigId,
			@Nullable final JsonExternalSAPBPartnerImportSettings importSettings)
	{
		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();

		final String bpartnerCode = bPartnerRow.getPartnerCode().getPartnerCode();

		jsonRequestBPartner.setCode(bpartnerCode);
		jsonRequestBPartner.setCompanyName(bPartnerRow.getName1());
		jsonRequestBPartner.setName(bPartnerRow.getName1());
		jsonRequestBPartner.setName2(bPartnerRow.getName2());
		jsonRequestBPartner.setLanguage(BPARTNER_DEFAULT_LANGUAGE);
		jsonRequestBPartner.setProspect(false);
		jsonRequestBPartner.setSapBPartnerCode(bpartnerCode);
		jsonRequestBPartner.setSectionGroupPartner(true);
		Optional.ofNullable(importSettings)
				.map(JsonExternalSAPBPartnerImportSettings::getBpGroupName)
				.ifPresent(jsonRequestBPartner::setGroup);

		final JsonRequestComposite.JsonRequestCompositeBuilder jsonRequestCompositeBuilder = JsonRequestComposite.builder()
				.bpartner(jsonRequestBPartner)
				.orgCode(orgCode);

		return JsonRequestBPartnerUpsertItem.builder()
				.bpartnerComposite(jsonRequestCompositeBuilder.build())
				.bpartnerIdentifier(ExternalIdentifierFormat.formatExternalId(bPartnerRow.getPartnerCode().getPartnerCode()))
				.externalSystemConfigId(externalSystemConfigId)
				.build();
	}

	@NonNull
	private Optional<JsonRequestBPartnerUpsertItem> getSectionBPartnerUpsertItem()
	{
		return Optional.ofNullable(sectionGroupBPartnerItem);
	}

	private boolean shouldImportByItself(@NonNull final BPartnerRow bPartnerRow)
	{
		return getBPartnerImportSettingsForRow(sapbPartnerImportSettingsList, bPartnerRow)
				.map(JsonExternalSAPBPartnerImportSettings::isSingleBPartner)
				.orElse(false);
	}

	private static boolean matchesPartnerCode(
			@NonNull final JsonExternalSAPBPartnerImportSettings settings,
			@NonNull final PartnerCode partnerCode)
	{
		return Pattern.compile(settings.getPartnerCodePattern())
				.matcher(partnerCode.getRawPartnerCode())
				.find();
	}

	@NonNull
	private static Optional<JsonExternalSAPBPartnerImportSettings> getBPartnerImportSettingsForRow(
			@NonNull final ImmutableList<JsonExternalSAPBPartnerImportSettings> partnerImportSettingsList,
			@NonNull final BPartnerRow row)
	{
		return partnerImportSettingsList.stream()
				.sorted(Comparator.comparingInt(JsonExternalSAPBPartnerImportSettings::getSeqNo))
				.filter(settings -> matchesPartnerCode(settings, row.getPartnerCode()))
				.findFirst();
	}
}