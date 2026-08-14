/*
 * #%L
 * de.metas.shipper.client.nshift
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.shipper.client.nshift;

import de.metas.common.delivery.v1.json.DeliveryMappingConstants;
import de.metas.common.delivery.v1.json.JsonContact;
import de.metas.common.delivery.v1.json.request.JsonCarrierService;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequest;
import de.metas.common.util.Check;
import de.metas.shipper.client.nshift.json.JsonAddress;
import de.metas.shipper.client.nshift.json.JsonAddressKind;
import de.metas.shipper.client.nshift.json.JsonDetail;
import de.metas.shipper.client.nshift.json.JsonDetailGroup;
import de.metas.shipper.client.nshift.json.JsonDetailRow;
import de.metas.shipper.client.nshift.json.JsonLine;
import de.metas.shipper.client.nshift.json.response.JsonShipmentResponse;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@UtilityClass
public class NShiftUtil
{
	/**
	 * Extracts the resolved carrier services from an nShift shipment / order-advice response. nShift returns them
	 * as bare numeric ids under {@code Services}; the id is used as the name as well. Shared by the shipment
	 * (booking) and order-advise paths so both map the response services identically.
	 */
	public static Set<JsonCarrierService> extractResolvedServices(@NonNull final JsonShipmentResponse response)
	{
		if (response.getServices() == null)
		{
			return Collections.emptySet();
		}
		return response.getServices().stream()
				.map(svcId -> {
					final String id = String.valueOf(svcId);
					return JsonCarrierService.builder().id(id).name(id).build();
				})
				.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	/**
	 * Display name for a carrier product resolved from an nShift response:
	 * {@code "<CarrierFullName> - <ProdName>"} (e.g. "UPS Rest API - UPS Standard®").
	 * Falls back to the product part alone when {@code carrierFullName} is blank, and to {@code fallback} when
	 * {@code prodName} is null. This enriches only the display NAME — the carrier-product IDENTITY stays the
	 * ProdConceptID (the {@code code}).
	 */
	public static String buildCarrierProductName(
			@Nullable final String carrierFullName,
			@Nullable final String prodName,
			@NonNull final String fallback)
	{
		final String product = prodName != null ? prodName : fallback;
		return carrierFullName != null && !carrierFullName.trim().isEmpty()
				? carrierFullName + " - " + product
				: product;
	}

	public static JsonAddress.JsonAddressBuilder buildNShiftAddressBuilder(@NonNull final de.metas.common.delivery.v1.json.JsonAddress commonAddress,
																		   @Nullable final JsonContact contact,
																		   @NonNull final JsonAddressKind kind)
	{
		String street1 = commonAddress.getStreet();
		if (Check.isNotBlank(commonAddress.getHouseNo()))
		{
			street1 = street1 + " " + commonAddress.getHouseNo();
		}

		final JsonAddress.JsonAddressBuilder addressBuilder = JsonAddress.builder()
				.kind(kind)
				.name1(commonAddress.getCompanyName1())
				.name2(commonAddress.getCompanyName2())
				.street1(street1)
				.street2(commonAddress.getAdditionalAddressInfo())
				.postCode(commonAddress.getZipCode())
				.city(commonAddress.getCity())
				.countryCode(commonAddress.getCountry());

		if (contact != null)
		{
			if (Check.isNotBlank(contact.getPhone()))
			{
				addressBuilder.phone(contact.getPhone());
			}
			if (Check.isNotBlank(contact.getEmailAddress()))
			{
				addressBuilder.email(contact.getEmailAddress());
			}
		}

		return addressBuilder;
	}

	public static JsonAddress buildAddressWithAttentionFromMappings(
			@NonNull final de.metas.common.delivery.v1.json.JsonAddress commonAddress,
			@Nullable final JsonContact contact,
			@NonNull final JsonAddressKind kind,
			@NonNull final NShiftMappingConfigs mappingConfigs,
			@NonNull final Function<String, String> valueProvider)
	{
		final String role = kind == JsonAddressKind.SENDER ? "Sender" : "Receiver";

		final String attentionAttributeType = kind == JsonAddressKind.SENDER
				? DeliveryMappingConstants.ATTRIBUTE_TYPE_SENDER_ATTENTION
				: DeliveryMappingConstants.ATTRIBUTE_TYPE_RECEIVER_ATTENTION;
		final String attention = mappingConfigs.getSingleValue(attentionAttributeType, valueProvider);
		Check.assumeNotEmpty(attention, IllegalStateException.class,
				role + " Attention is mandatory but was not resolved from mapping configs.");
		Check.assumeNotEmpty(contact != null ? contact.getPhone() : null, IllegalStateException.class,
				role + " Phone is mandatory but is missing or blank.");
		Check.assumeNotEmpty(contact != null ? contact.getEmailAddress() : null, IllegalStateException.class,
				role + " Email is mandatory but is missing or blank.");

		// Optional CustNo, resolved from mapping rules (e.g. a CustomValueString1 shipper-config value routed via a
		// SenderCustNo / ReceiverCustNo rule). Unset -> getSingleValue returns "" -> omitted (JsonAddress is NON_NULL).
		final String custNoAttributeType = kind == JsonAddressKind.SENDER
				? DeliveryMappingConstants.ATTRIBUTE_TYPE_SENDER_CUSTNO
				: DeliveryMappingConstants.ATTRIBUTE_TYPE_RECEIVER_CUSTNO;
		final String custNo = mappingConfigs.getSingleValue(custNoAttributeType, valueProvider);

		final JsonAddress.JsonAddressBuilder addressBuilder = buildNShiftAddressBuilder(commonAddress, contact, kind)
				.attention(attention);
		if (Check.isNotBlank(custNo))
		{
			addressBuilder.custNo(custNo);
		}
		return addressBuilder.build();
	}

	/**
	 * Builds the nShift {@link JsonLine} for an advise request, mirroring the ship path's
	 * {@code NShiftShipmentService#buildNShiftLine} two-level structure:
	 * <ul>
	 *   <li>PARCEL level — weight (kg→g) and dimensions (cm→mm) come from the request's parcel fields
	 *       ({@link JsonDeliveryAdvisorRequest#getGrossWeightKg()} / {@code getPackageDimensions()}).</li>
	 *   <li>LINE references — resolved ONCE via {@link JsonDeliveryAdvisorRequest#getValue(String)}, exactly as the
	 *       ship path resolves line references via {@code JsonDeliveryOrderParcel.getValue}. The request's
	 *       {@code getValue} already aggregates the per-product attributes over its items (product name/value,
	 *       customs tariff, total value, …), so parcel-level attributes resolve once (no per-item duplication) and
	 *       per-product attributes are aggregated consistently with the ship path.</li>
	 * </ul>
	 * Shared by {@code NShiftShipAdvisorService} and {@code NShiftOrderAdvisorService} — the two advise
	 * endpoints must build the line identically; keep this the single source for advise-line building.
	 * <p>
	 * For a single-element {@code items} list the emitted line is byte-identical to the previous single-item
	 * advise wire format.
	 */
	public static JsonLine buildAdvisorLine(
			@NonNull final JsonDeliveryAdvisorRequest request,
			@NonNull final NShiftMappingConfigs mappingConfigs)
	{
		if (request.getPackageDimensions() == null)
		{
			throw new IllegalStateException("Package dimensions are mandatory but were not specified (dimensions is null).");
		}

		final int weightGrams = request.getGrossWeightKg().multiply(BigDecimal.valueOf(1000)).intValue();
		final Function<String, String> lineValueProvider = request::getValue;
		final int lengthMM = request.getPackageDimensions().getLengthInCM() * 10;
		final int widthMM = request.getPackageDimensions().getWidthInCM() * 10;
		final int heightMM = request.getPackageDimensions().getHeightInCM() * 10;
		return JsonLine.builder()
				.lineWeight(weightGrams)
				.references(mappingConfigs.getReferences(DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_REFERENCE, lineValueProvider))
				.number(1) // always 1: the advise carries a single physical HU / parcel
				.length(lengthMM)
				.width(widthMM)
				.height(heightMM)
				.build();
	}

	/**
	 * Assembles the nShift detail groups for an advise request — the advise-side counterpart of the ship path's
	 * detail-group assembly in {@code NShiftShipmentService.buildShipmentRequest}. So the mobile advise is a real
	 * preview of what ship sends to nShift, it emits the same shipment-level + line-level detail groups:
	 * <ul>
	 *   <li>shipment-level groups via {@link #buildShipmentDetailGroups(NShiftMappingConfigs, Function)} resolved by
	 *       {@code request::getValue};</li>
	 *   <li>line-level groups via {@link #buildLineLevelDetailGroups(List, int, NShiftMappingConfigs)} with one
	 *       per-item value-provider chain {@code withFallback(item::getValue, request::getValue)} per advise item.
	 *       The advise carries exactly ONE physical parcel (the packed HU), so all rows use {@code lineNo = 1}
	 *       (mirroring {@link #buildAdvisorLine}).</li>
	 * </ul>
	 * Shared by {@code NShiftShipAdvisorService} and {@code NShiftOrderAdvisorService} — the two advise endpoints
	 * must build detail groups identically (same contract as {@link #buildAdvisorLine}).
	 */
	public static List<JsonDetailGroup> buildAdvisorDetailGroups(
			@NonNull final JsonDeliveryAdvisorRequest request,
			@NonNull final NShiftMappingConfigs mappingConfigs)
	{
		final List<JsonDetailGroup> allDetailGroups = new ArrayList<>(
				buildShipmentDetailGroups(mappingConfigs, request::getValue));

		final List<Function<String, String>> perItemValueProviders = request.getItems().stream()
				.map(item -> {
					final Function<String, Optional<String>> chain =
							withFallback(item::getValue, attributeValue -> Optional.ofNullable(request.getValue(attributeValue)));
					return (Function<String, String>)(attributeValue -> chain.apply(attributeValue).orElse(null));
				})
				.collect(Collectors.toList());

		allDetailGroups.addAll(buildLineLevelDetailGroups(perItemValueProviders, 1, mappingConfigs));

		return allDetailGroups;
	}

	/**
	 * Builds the shipment-level nShift detail groups ({@link DeliveryMappingConstants#ATTRIBUTE_TYPE_DETAIL_GROUP}),
	 * processed once per shipment. Shared by the ship path ({@code NShiftShipmentService}) and the advise paths
	 * ({@code NShiftShipAdvisorService} / {@code NShiftOrderAdvisorService}) so both emit identical groups —
	 * keep this the single source for shipment-level detail-group building.
	 *
	 * @param shipmentValueProvider resolves shipment-level attribute values (e.g. {@code request::getValue}).
	 */
	public static List<JsonDetailGroup> buildShipmentDetailGroups(
			@NonNull final NShiftMappingConfigs mappingConfigs,
			@NonNull final Function<String, String> shipmentValueProvider)
	{
		final List<String> shipmentLevelGroupKeys = mappingConfigs.getDetailGroupKeysForType(DeliveryMappingConstants.ATTRIBUTE_TYPE_DETAIL_GROUP, shipmentValueProvider);

		if (shipmentLevelGroupKeys.isEmpty())
		{
			return Collections.emptyList();
		}

		final List<JsonDetailGroup> resultGroups = new ArrayList<>();
		for (final String groupKey : shipmentLevelGroupKeys)
		{
			final List<JsonDetail> details = mappingConfigs.getDetailsForGroupAndType(groupKey, DeliveryMappingConstants.ATTRIBUTE_TYPE_DETAIL_GROUP, shipmentValueProvider);
			if (details.isEmpty())
			{
				continue;
			}

			// For shipment-level groups, we create one row without a line number.
			final JsonDetailRow detailRow = JsonDetailRow.builder()
					.details(details)
					.build();

			resultGroups.add(JsonDetailGroup.builder()
					.groupID(groupKey)
					.row(detailRow)
					.build());
		}
		return resultGroups;
	}

	/**
	 * Builds the line-level nShift detail groups ({@link DeliveryMappingConstants#ATTRIBUTE_TYPE_LINE_DETAIL_GROUP})
	 * for ONE physical line/parcel (all its content items share the given {@code lineNo}). Shared by the ship path
	 * ({@code NShiftShipmentService}) and the advise paths ({@code NShiftShipAdvisorService} /
	 * {@code NShiftOrderAdvisorService}); keep this the single source for line-level detail-group building.
	 * <p>
	 * Each per-item value provider is already the full fallback chain the caller wants for that content item
	 * (ship: {@code withFallback(content::getValue, withFallback(parcel::getValue, request::getValue))};
	 * advise: {@code withFallback(item::getValue, request::getValue)}). This method just iterates the provided
	 * chains, so the two paths share one implementation while keeping their own context plumbing.
	 *
	 * @param perItemValueProviders one value-provider chain per content item of the line.
	 * @param lineNo                the nShift line number all emitted rows carry.
	 */
	public static List<JsonDetailGroup> buildLineLevelDetailGroups(
			@NonNull final List<Function<String, String>> perItemValueProviders,
			final int lineNo,
			@NonNull final NShiftMappingConfigs mappingConfigs)
	{
		final Map<String, JsonDetailGroup.JsonDetailGroupBuilder> groupBuilders = new LinkedHashMap<>();

		for (final Function<String, String> finalValueProvider : perItemValueProviders)
		{
			final List<String> groupKeys = mappingConfigs.getDetailGroupKeysForType(DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_DETAIL_GROUP, finalValueProvider);
			if (groupKeys.isEmpty())
			{
				continue;
			}

			for (final String groupKey : groupKeys)
			{
				final List<JsonDetail> details = mappingConfigs.getDetailsForGroupAndType(groupKey, DeliveryMappingConstants.ATTRIBUTE_TYPE_LINE_DETAIL_GROUP, finalValueProvider);

				if (!details.isEmpty())
				{
					// eDekGoodsLineNo
					final JsonDetailRow.JsonDetailRowBuilder builder = JsonDetailRow.builder()
							.lineNo(lineNo)
							.details(details);
					if (groupKey.equals("1"))
					{
						builder.detail(JsonDetail.builder().kindId(193).value(String.valueOf(lineNo)).build()); // lineNo
					}
					groupBuilders.computeIfAbsent(groupKey, k -> JsonDetailGroup.builder().groupID(k))
							.row(builder.build());
				}
			}
		}

		return groupBuilders.values().stream()
				.map(JsonDetailGroup.JsonDetailGroupBuilder::build)
				.collect(Collectors.toList());
	}

	public static <T, R> Function<T, Optional<R>> withFallback(
			@NonNull final Function<T, Optional<R>> primary,
			@NonNull final Function<T, Optional<R>> fallback)
	{
		return t -> {
			final Optional<R> value = primary.apply(t);
			return value.isPresent() ? value : fallback.apply(t);
		};
	}
}
