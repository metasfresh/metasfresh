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
import de.metas.common.bpartner.v2.request.JsonRequestBPartner;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsertItem;
import de.metas.common.bpartner.v2.request.JsonRequestComposite;
import de.metas.common.bpartner.v2.request.JsonRequestLocation;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsert;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsertItem;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.common.util.Check;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Value
public class SyncBPartnerRequestBuilder
{

	/*
	*
	*  row: PCODE 00 SCODE
	*  row: PCODE 04 SCODE
	*
	*  row: PCODE 01 SCODE1
	*  row: PCODE 02 SCODE1
	*
	*  row: PCODE 03 SCODE2
	*
	*  row: PCODE 07 SCODE3
	*
	* GroupBPartner -> ext-SAP-PCODE00
	*   BPartner1 -> ext-SAP-SCODE_PCODE00 (locatiille: -SAP-SCODE_PCODE00 + -SAP-SCODE_PCODE04)
	*   BPartner2 -> ext-SAP-SCODE1_PCODE00 (locatiille: -SAP-SCODE1_PCODE01 + -SAP-SCODE1_PCODE02)
	*   BPartner3 -> ext-SAP-SCODE2_PCODE00 (locatiille: -SAP-SCODE2_PCODE03 )
	*   BPartner1 -> ext-SAP-SCODE3_PCODE00 (locatiille: -SAP-SCODE3_PCODE07 )
	*
	* */







	@NonNull
	String partnerCode;

	// todo mi: (ext-SAP-${partnerCode})
	@NonNull
	String parentIdentifier;

	@NonNull
	String orgCode;

	@NonNull
	JsonMetasfreshId externalSystemConfigId;

	@NonNull
	JsonRequestBPartnerUpsertItem sectionGroupBPartnerItem;

	@Getter(AccessLevel.NONE)
	List<BPartnerRow> bPartnerRows;

	// todo mi:

	public static SyncBPartnerRequestBuilder of(
			@NonNull final BPartnerRow row,
			@NonNull final String orgCode,
			@NonNull final JsonMetasfreshId externalSystemConfigId) throws Exception
	{
		final JsonRequestBPartnerUpsertItem sectionGroupJsonRequestBPartnerUpsertItem = buildSectionGroupJsonRequestBPartnerUpsertItem(row, orgCode, externalSystemConfigId);

		final SyncBPartnerRequestBuilder syncBPartnerRequestBuilder = new SyncBPartnerRequestBuilder(
				row.getPartnerCode(),
				sectionGroupJsonRequestBPartnerUpsertItem.getBpartnerIdentifier(),
				orgCode,
				externalSystemConfigId,
				sectionGroupJsonRequestBPartnerUpsertItem,
				new ArrayList<>());

		//todo mi: here we are starting a new group, so in fact, we need to add 2 x JsonRequestBPartnerUpsertItem; 1 for Section group BP and 1 x the bpartner (with the parent identifier)
		syncBPartnerRequestBuilder.add(row);

		return syncBPartnerRequestBuilder;
	}

	public boolean add(@NonNull final BPartnerRow row)
	{
		if (!isPartnerCodeMatching(row))
		{
			return false;
		}

		this.bPartnerRows.add(row);

		return true;
	}

	@NonNull
	public BPUpsertCamelRequest build() throws Exception
	{
		this.bPartnerRows.sort(Comparator.comparing(BPartnerRow::getSection));

		final Map<String, List<BPartnerRow>> groupedBPartnerRows = this.bPartnerRows.stream()
				.collect(Collectors.groupingBy(bPartnerRow -> bPartnerRow.getPartnerCode().substring(0, bPartnerRow.getPartnerCode().length() - 2)));

		final ImmutableList.Builder<JsonRequestBPartnerUpsertItem> mergedItemBuilder = ImmutableList.builder();

		mergedItemBuilder.add(this.sectionGroupBPartnerItem);

		for (final Map.Entry<String, List<BPartnerRow>> sectionCodeEntry : groupedBPartnerRows.entrySet())
		{
			mergedItemBuilder.add(mergeBPartnerRowsIntoJsonUpsertItem(sectionCodeEntry.getKey(), sectionCodeEntry.getValue()));
		}

		final List<JsonRequestBPartnerUpsertItem> mergedBPartnerUpsertItems = mergedItemBuilder.build();

		final JsonRequestBPartnerUpsert jsonRequestBPartnerUpsert = JsonRequestBPartnerUpsert.builder()
				.requestItems(mergedBPartnerUpsertItems)
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.build();

		return BPUpsertCamelRequest.builder()
				.jsonRequestBPartnerUpsert(jsonRequestBPartnerUpsert)
				.orgCode(orgCode)
				.build();
	}

	@NonNull
	private JsonRequestBPartner buildJsonRequestBPartner(@NonNull final BPartnerRow bPartner) throws Exception
	{
		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();

		jsonRequestBPartner.setCode(bPartner.getPartnerCode());
		jsonRequestBPartner.setCompanyName(bPartner.getName1());
		jsonRequestBPartner.setName(bPartner.getName2());
		jsonRequestBPartner.setDescription(bPartner.getSearchTerm());

		jsonRequestBPartner.setSectionCodeValue(bPartner.getSection());
		jsonRequestBPartner.setDeliveryRule(JsonRequestBPartner.DeliveryRule.AVAILABILITY);
		jsonRequestBPartner.setDeliveryViaRule(JsonRequestBPartner.DeliveryViaRule.SHIPPER);
		jsonRequestBPartner.setPaymentRule(JsonRequestBPartner.PaymentRule.ON_CREDIT);
		jsonRequestBPartner.setPaymentRulePO(JsonRequestBPartner.PaymentRule.ON_CREDIT);

		jsonRequestBPartner.setIncotermsCustomerValue(bPartner.getSalesIncoterms());
		jsonRequestBPartner.setIncotermsVendorValue(bPartner.getPurchaseIncoterms());
		jsonRequestBPartner.setCustomerPaymentTermIdentifier(bPartner.getSalesPaymentTerms());
		jsonRequestBPartner.setVendorPaymentTermIdentifier(bPartner.getPurchasePaymentTerms());

		if (Check.isNotBlank(bPartner.getPartnerCategory()))
		{
			if (BPartnerRow.PartnerCategory.ofCode(bPartner.getPartnerCategory()).equals(BPartnerRow.PartnerCategory.STORAGE_LOCATION)) //todo mi: use enum for the 3 values PartnerCategory
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
		}

		//todo mi: set also all the default flags or configs (and don;t forget about camel unit test)

		jsonRequestBPartner.setParentIdentifier(parentIdentifier);

		return jsonRequestBPartner;
	}

	@NonNull
	private JsonRequestLocationUpsertItem buildJsonRequestLocationUpsertItem(@NonNull final BPartnerRow bPartner)
	{
		final JsonRequestLocation jsonRequestLocation = new JsonRequestLocation();
		jsonRequestLocation.setCountryCode(bPartner.getCountryKey());
		jsonRequestLocation.setCity(bPartner.getCity());
		jsonRequestLocation.setAddress1(bPartner.getStreet());
		jsonRequestLocation.setAddress2(bPartner.getStreet2());
		jsonRequestLocation.setAddress3(bPartner.getStreet3());

		final String address4 = Stream.of(bPartner.getStreet4(), bPartner.getStreet5())
				.filter(Check::isNotBlank)
				.collect(Collectors.joining(","));
		jsonRequestLocation.setAddress4(address4);

		jsonRequestLocation.setPostal(bPartner.getPostalCode());
		jsonRequestLocation.setVisitorsAddress(false);
		jsonRequestLocation.setShipTo(true);
		jsonRequestLocation.setShipToDefault(false);
		jsonRequestLocation.setBillTo(true);
		jsonRequestLocation.setBillToDefault(false);
		jsonRequestLocation.setHandoverLocation(true);
		jsonRequestLocation.setRemitTo(false);
		jsonRequestLocation.setReplicationLookupDefault(false);

		//todo mi: set all flags even if default

		final String externalIdentifier = bPartner.getSection() +
				"_" +
				bPartner.getPartnerCode();

		return JsonRequestLocationUpsertItem.builder()
				.location(jsonRequestLocation)
				.locationIdentifier(ExternalIdentifierFormat.formatExternalId(externalIdentifier))
				.build();
	}

	//todo mi: add method for creating section group bpartner

	@NonNull
	private static JsonRequestBPartnerUpsertItem buildSectionGroupJsonRequestBPartnerUpsertItem(
			@NonNull final BPartnerRow bPartnerRow,
			@NonNull final String orgCode,
			@NonNull final JsonMetasfreshId externalSystemConfigId
	)
	{
		final JsonRequestBPartner jsonRequestBPartner = new JsonRequestBPartner();

		jsonRequestBPartner.setCode(bPartnerRow.getPartnerCode());
		jsonRequestBPartner.setCompanyName(bPartnerRow.getName1());
		jsonRequestBPartner.setName(bPartnerRow.getName2());
		jsonRequestBPartner.setDescription(bPartnerRow.getSearchTerm());

		final JsonRequestComposite.JsonRequestCompositeBuilder jsonRequestCompositeBuilder = JsonRequestComposite.builder()
				.bpartner(jsonRequestBPartner)
				.orgCode(orgCode);

		final String externalIdentifier = extractGroupingPartnerCode(bPartnerRow.getPartnerCode()) + "00";

		return JsonRequestBPartnerUpsertItem.builder()
				.bpartnerComposite(jsonRequestCompositeBuilder.build())
				.bpartnerIdentifier(ExternalIdentifierFormat.formatExternalId(externalIdentifier))
				.externalSystemConfigId(externalSystemConfigId)
				.isReadOnlyInMetasfresh(true)
				.build();
	}

	@NonNull
	private JsonRequestBPartnerUpsertItem mergeBPartnerRowsIntoJsonUpsertItem(@NonNull final String sectionCode, @NonNull final List<BPartnerRow> bPartnerRows) throws Exception
	{
		bPartnerRows.sort(Comparator.comparing(BPartnerRow::getPartnerCode));

		final ImmutableList.Builder<JsonRequestLocationUpsertItem> locationUpsertItemBuilder = ImmutableList.builder();

		for(final BPartnerRow bPartnerRow : bPartnerRows)
		{
			locationUpsertItemBuilder.add(buildJsonRequestLocationUpsertItem(bPartnerRow));
		}

		final JsonRequestLocationUpsert jsonRequestLocationUpsert = JsonRequestLocationUpsert.builder()
				.requestItems(locationUpsertItemBuilder.build())
				.build();

		final JsonRequestBPartner jsonRequestBPartnerToPersist = buildJsonRequestBPartner(bPartnerRows.get(bPartnerRows.size() - 1));

		final JsonRequestComposite jsonRequestComposite = JsonRequestComposite.builder()
				.bpartner(jsonRequestBPartnerToPersist)
				.locations(jsonRequestLocationUpsert)
				.orgCode(orgCode)
				.build();

		final String externalIdentifier = sectionCode +
				"_" +
				extractGroupingPartnerCode(this.partnerCode) +
				"00";

		return JsonRequestBPartnerUpsertItem.builder()
				.bpartnerComposite(jsonRequestComposite)
				.bpartnerIdentifier(ExternalIdentifierFormat.formatExternalId(externalIdentifier))
				.externalSystemConfigId(externalSystemConfigId)
				.isReadOnlyInMetasfresh(true)
				.build();
	}

	private boolean isPartnerCodeMatching(@NonNull final BPartnerRow row)
	{
		return extractGroupingPartnerCode(row.getPartnerCode()).equals(extractGroupingPartnerCode(partnerCode));//todo mi: partnerCode.subString(0, length-3) ==  row.getPartnerCOode9)
	}

	/**
	 * Extracts the chars used when aggregating multiple rows into one BPartner.
	 */
	private static String extractGroupingPartnerCode(@NonNull final String partnerCode)
	{
		return partnerCode.substring(0, partnerCode.length() - 2);//todo mi: partnerCode.subString(0, length-3)
	}
}
