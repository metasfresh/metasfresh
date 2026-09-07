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

import com.google.common.collect.ImmutableList;
import de.metas.common.delivery.v1.json.DeliveryMappingConstants;
import de.metas.common.delivery.v1.json.JsonAddress;
import de.metas.common.delivery.v1.json.JsonContact;
import de.metas.common.delivery.v1.json.JsonMoney;
import de.metas.common.delivery.v1.json.JsonPackageDimensions;
import de.metas.common.delivery.v1.json.JsonQuantity;
import de.metas.common.delivery.v1.json.request.JsonCarrierService;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequest;
import de.metas.common.delivery.v1.json.request.JsonDeliveryAdvisorRequestItem;
import de.metas.common.delivery.v1.json.request.JsonDeliveryOrderLineContents;
import de.metas.common.delivery.v1.json.request.JsonDeliveryOrderParcel;
import de.metas.common.delivery.v1.json.request.JsonDeliveryRequest;
import de.metas.common.delivery.v1.json.request.JsonGoodsType;
import de.metas.common.delivery.v1.json.request.JsonMappingConfigList;
import de.metas.common.delivery.v1.json.request.JsonShipperConfig;
import de.metas.common.delivery.v1.json.request.JsonShipperProduct;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Mandatory-field validation for all 4 nShift build paths.
 * <p>
 * All four {@code buildRequest} / {@code buildShipmentRequest} / {@code buildOrderAdviceShipmentRequest}
 * methods are {@code public static} and pure (no Spring / DB), so we exercise them directly.
 * <p>
 * For EACH path we test:
 * <ul>
 *   <li>missing receiver Attention → throws naming "Attention"</li>
 *   <li>missing sender Phone → throws naming "Phone"</li>
 *   <li>missing receiver Email → throws naming "Email" (representative contact-field check)</li>
 *   <li>UNSPECIFIED / null dimensions → throws naming "dimensions"</li>
 *   <li>all mandatory fields present → builds OK (no exception)</li>
 * </ul>
 */
public class NShiftMandatoryFieldValidationTest
{
	// -----------------------------------------------------------------------
	// Shared address / contact builders — VALID baseline
	// -----------------------------------------------------------------------

	private static final JsonAddress VALID_PICKUP_ADDRESS = JsonAddress.builder()
			.bpartnerId(1)
			.companyName1("Sender GmbH")
			.city("Bonn")
			.country("DE")
			.zipCode("53179")
			.street("Sender Str")
			.houseNo("1")
			.attention("Sender Attention")
			.build();

	private static final JsonAddress VALID_DELIVERY_ADDRESS = JsonAddress.builder()
			.bpartnerId(2)
			.companyName1("Receiver GmbH")
			.city("Timisoara")
			.country("RO")
			.zipCode("300078")
			.street("Receiver Str")
			.houseNo("2")
			.attention("Receiver Attention")
			.build();

	private static final JsonContact VALID_SENDER_CONTACT = JsonContact.builder()
			.name("Sender Name")
			.language("de")
			.phone("0228-1234")
			.emailAddress("sender@example.com")
			.build();

	private static final JsonContact VALID_RECEIVER_CONTACT = JsonContact.builder()
			.name("Receiver Name")
			.language("de")
			.phone("0040-1234")
			.emailAddress("receiver@example.com")
			.build();

	private static final JsonPackageDimensions VALID_DIMS = JsonPackageDimensions.builder()
			.lengthInCM(100)
			.widthInCM(20)
			.heightInCM(15)
			.build();

	private static final JsonShipperConfig ADVISOR_SHIPPER_CONFIG = JsonShipperConfig.builder()
			.url("https://demo.shipmentserver.com:8080")
			.username("user")
			.password("pass")
			.additionalProperty(NShiftConstants.ACTOR_ID, "123")
			.additionalProperty(NShiftConstants.SERVICE_LEVEL, "TestLevel")
			.additionalProperty(NShiftConstants.SELECTION_RULES, "N")
			.build();

	private static final JsonShipperConfig SHIP_SHIPPER_CONFIG = JsonShipperConfig.builder()
			.url("https://demo.shipmentserver.com:8080")
			.username("user")
			.password("pass")
			.additionalProperty(NShiftConstants.ACTOR_ID, "123")
			.additionalProperty(NShiftConstants.IS_CREATE_DRAFT_SHIPMENT_ONLY, "N")
			.build();

	// -----------------------------------------------------------------------
	// Mapping config with BOTH sender AND receiver attention mapped
	// -----------------------------------------------------------------------
	private static final JsonMappingConfigList MAPPING_WITH_ATTENTION =
			NShiftTestMappingConfigs.SHARED_TEST;

	// Mapping config with NO sender/receiver attention entries at all
	private static final JsonMappingConfigList MAPPING_WITHOUT_ATTENTION =
			buildMappingWithoutAttention();

	private static JsonMappingConfigList buildMappingWithoutAttention()
	{
		// Keep references but remove all attention entries
		return JsonMappingConfigList.ofList(
				NShiftTestMappingConfigs.SHARED_TEST.getConfigs().stream()
						.filter(mappingConfig -> !DeliveryMappingConstants.ATTRIBUTE_TYPE_SENDER_ATTENTION.equals(mappingConfig.getAttributeType())
								&& !DeliveryMappingConstants.ATTRIBUTE_TYPE_RECEIVER_ATTENTION.equals(mappingConfig.getAttributeType()))
						.collect(ImmutableList.toImmutableList()));
	}

	// -----------------------------------------------------------------------
	// Advisor request builder helpers
	// -----------------------------------------------------------------------

	private static JsonDeliveryAdvisorRequest validAdvisorRequest()
	{
		return JsonDeliveryAdvisorRequest.builder()
				.id("test-id")
				.pickupAddress(VALID_PICKUP_ADDRESS)
				.pickupContact(VALID_SENDER_CONTACT)
				.pickupDate("2025-10-02")
				.pickupTimeFrom("08:00:00")
				.deliveryAddress(VALID_DELIVERY_ADDRESS)
				.deliveryContact(VALID_RECEIVER_CONTACT)
				.grossWeightKg(BigDecimal.TEN)
				.packageDimensions(VALID_DIMS)
				.items(ImmutableList.of(JsonDeliveryAdvisorRequestItem.builder()
						.numberOfItems(1)
						.productName("Test Product")
						.productValue("Test Value")
						.build()))
				.shipperConfig(ADVISOR_SHIPPER_CONFIG)
				.mappingConfigs(MAPPING_WITH_ATTENTION)
				.build();
	}

	// -----------------------------------------------------------------------
	// Ship request builder helpers
	// -----------------------------------------------------------------------

	private static JsonDeliveryRequest validShipRequest()
	{
		return JsonDeliveryRequest.builder()
				.deliveryOrderId(1)
				.shipperProduct(JsonShipperProduct.builder().code("10305").name("DHL Freight").build())
				.service(JsonCarrierService.builder().id("972053").name("Ex Works").build())
				.goodsType(JsonGoodsType.builder().id("5").name("Packet").build())
				.pickupAddress(VALID_PICKUP_ADDRESS)
				.pickupContact(VALID_SENDER_CONTACT)
				.pickupDate("2025-10-02")
				.timeFrom("10:00:00")
				.timeTo("13:00:00")
				.deliveryAddress(VALID_DELIVERY_ADDRESS)
				.deliveryContact(VALID_RECEIVER_CONTACT)
				.deliveryDate("2025-10-02")
				.deliveryOrderParcel(JsonDeliveryOrderParcel.builder()
						.id("1")
						.grossWeightKg(BigDecimal.TEN)
						.packageDimensions(VALID_DIMS)
						.packageId("1")
						.contents(ImmutableList.of(JsonDeliveryOrderLineContents.builder()
								.shipmentOrderItemId("1")
								.unitPrice(JsonMoney.builder().amount(BigDecimal.TEN).currencyCode("EUR").build())
								.totalValue(JsonMoney.builder().amount(BigDecimal.TEN).currencyCode("EUR").build())
								.productName("Product")
								.productValue("Value")
								.totalWeightInKg(BigDecimal.TEN)
								.shippedQuantity(JsonQuantity.builder().value(BigDecimal.TEN).uomCode("PCE").build())
								.build()))
						.build())
				.shipperConfig(SHIP_SHIPPER_CONFIG)
				.mappingConfigs(MAPPING_WITH_ATTENTION)
				.build();
	}

	// =======================================================================
	// PATH 1 — NShiftOrderAdvisorService.buildRequest
	// =======================================================================

	@Nested
	class OrderAdvisorBuildRequest
	{
		@Test
		void missingAttentionMapping_throwsNamingAttention()
		{
			final JsonDeliveryAdvisorRequest request = validAdvisorRequest().toBuilder()
					.mappingConfigs(MAPPING_WITHOUT_ATTENTION)
					.build();

			assertThatThrownBy(() -> NShiftOrderAdvisorService.buildRequest(request))
					.isInstanceOf(IllegalStateException.class)
					.hasMessageContaining("Attention");
		}

		@Test
		void missingSenderPhone_throwsNamingPhone()
		{
			final JsonContact noPhone = JsonContact.builder()
					.name(VALID_SENDER_CONTACT.getName())
					.language(VALID_SENDER_CONTACT.getLanguage())
					.emailAddress(VALID_SENDER_CONTACT.getEmailAddress())
					// phone omitted
					.build();
			final JsonDeliveryAdvisorRequest request = validAdvisorRequest().toBuilder()
					.pickupContact(noPhone)
					.build();

			assertThatThrownBy(() -> NShiftOrderAdvisorService.buildRequest(request))
					.isInstanceOf(IllegalStateException.class)
					.hasMessageContaining("Phone");
		}

		@Test
		void missingReceiverEmail_throwsNamingEmail()
		{
			final JsonContact noEmail = JsonContact.builder()
					.name(VALID_RECEIVER_CONTACT.getName())
					.language(VALID_RECEIVER_CONTACT.getLanguage())
					.phone(VALID_RECEIVER_CONTACT.getPhone())
					// emailAddress omitted
					.build();
			final JsonDeliveryAdvisorRequest request = validAdvisorRequest().toBuilder()
					.deliveryContact(noEmail)
					.build();

			assertThatThrownBy(() -> NShiftOrderAdvisorService.buildRequest(request))
					.isInstanceOf(IllegalStateException.class)
					.hasMessageContaining("Email");
		}

		@Test
		void nullDimensions_throwsNamingDimensions()
		{
			final JsonDeliveryAdvisorRequest request = validAdvisorRequest().toBuilder()
					.packageDimensions(null)
					.build();

			assertThatThrownBy(() -> NShiftOrderAdvisorService.buildRequest(request))
					.isInstanceOf(IllegalStateException.class)
					.hasMessageContaining("dimensions");
		}

		@Test
		void allPresent_buildsOk()
		{
			assertThatCode(() -> NShiftOrderAdvisorService.buildRequest(validAdvisorRequest()))
					.doesNotThrowAnyException();
		}
	}

	// =======================================================================
	// PATH 2 — NShiftShipAdvisorService.buildRequest
	// =======================================================================

	@Nested
	class ShipAdvisorBuildRequest
	{
		@Test
		void missingAttentionMapping_throwsNamingAttention()
		{
			final JsonDeliveryAdvisorRequest request = validAdvisorRequest().toBuilder()
					.mappingConfigs(MAPPING_WITHOUT_ATTENTION)
					.build();

			assertThatThrownBy(() -> NShiftShipAdvisorService.buildRequest(request))
					.isInstanceOf(IllegalStateException.class)
					.hasMessageContaining("Attention");
		}

		@Test
		void missingSenderPhone_throwsNamingPhone()
		{
			final JsonContact noPhone = JsonContact.builder()
					.name(VALID_SENDER_CONTACT.getName())
					.language(VALID_SENDER_CONTACT.getLanguage())
					.emailAddress(VALID_SENDER_CONTACT.getEmailAddress())
					// phone omitted
					.build();
			final JsonDeliveryAdvisorRequest request = validAdvisorRequest().toBuilder()
					.pickupContact(noPhone)
					.build();

			assertThatThrownBy(() -> NShiftShipAdvisorService.buildRequest(request))
					.isInstanceOf(IllegalStateException.class)
					.hasMessageContaining("Phone");
		}

		@Test
		void missingReceiverEmail_throwsNamingEmail()
		{
			final JsonContact noEmail = JsonContact.builder()
					.name(VALID_RECEIVER_CONTACT.getName())
					.language(VALID_RECEIVER_CONTACT.getLanguage())
					.phone(VALID_RECEIVER_CONTACT.getPhone())
					// emailAddress omitted
					.build();
			final JsonDeliveryAdvisorRequest request = validAdvisorRequest().toBuilder()
					.deliveryContact(noEmail)
					.build();

			assertThatThrownBy(() -> NShiftShipAdvisorService.buildRequest(request))
					.isInstanceOf(IllegalStateException.class)
					.hasMessageContaining("Email");
		}

		@Test
		void nullDimensions_throwsNamingDimensions()
		{
			final JsonDeliveryAdvisorRequest request = validAdvisorRequest().toBuilder()
					.packageDimensions(null)
					.build();

			assertThatThrownBy(() -> NShiftShipAdvisorService.buildRequest(request))
					.isInstanceOf(IllegalStateException.class)
					.hasMessageContaining("dimensions");
		}

		@Test
		void allPresent_buildsOk()
		{
			assertThatCode(() -> NShiftShipAdvisorService.buildRequest(validAdvisorRequest()))
					.doesNotThrowAnyException();
		}
	}

	// =======================================================================
	// PATH 3 — NShiftShipmentService.buildShipmentRequest
	// =======================================================================

	@Nested
	class ShipmentRequest
	{
		@Test
		void missingAttentionMapping_throwsNamingAttention()
		{
			final JsonDeliveryRequest request = validShipRequest().toBuilder()
					.mappingConfigs(MAPPING_WITHOUT_ATTENTION)
					.build();

			assertThatThrownBy(() -> NShiftShipmentService.buildShipmentRequest(request))
					.isInstanceOf(IllegalStateException.class)
					.hasMessageContaining("Attention");
		}

		@Test
		void missingSenderPhone_throwsNamingPhone()
		{
			final JsonContact noPhone = JsonContact.builder()
					.name(VALID_SENDER_CONTACT.getName())
					.language(VALID_SENDER_CONTACT.getLanguage())
					.emailAddress(VALID_SENDER_CONTACT.getEmailAddress())
					// phone omitted
					.build();
			final JsonDeliveryRequest request = validShipRequest().toBuilder()
					.pickupContact(noPhone)
					.build();

			assertThatThrownBy(() -> NShiftShipmentService.buildShipmentRequest(request))
					.isInstanceOf(IllegalStateException.class)
					.hasMessageContaining("Phone");
		}

		@Test
		void missingReceiverEmail_throwsNamingEmail()
		{
			final JsonContact noEmail = JsonContact.builder()
					.name(VALID_RECEIVER_CONTACT.getName())
					.language(VALID_RECEIVER_CONTACT.getLanguage())
					.phone(VALID_RECEIVER_CONTACT.getPhone())
					// emailAddress omitted
					.build();
			final JsonDeliveryRequest request = validShipRequest().toBuilder()
					.deliveryContact(noEmail)
					.build();

			assertThatThrownBy(() -> NShiftShipmentService.buildShipmentRequest(request))
					.isInstanceOf(IllegalStateException.class)
					.hasMessageContaining("Email");
		}

		@Test
		void zeroDimensions_throwsNamingDimensions()
		{
			// Zero dims (0,0,0) = unspecified on the ship path (JsonDeliveryOrderParcel.packageDimensions is @NonNull)
			final JsonDeliveryOrderParcel parcelWithZeroDims = validShipRequest().getDeliveryOrderParcels().get(0).toBuilder()
					.packageDimensions(JsonPackageDimensions.builder().lengthInCM(0).widthInCM(0).heightInCM(0).build())
					.build();
			final JsonDeliveryRequest request = validShipRequest().toBuilder()
					.clearDeliveryOrderParcels()
					.deliveryOrderParcel(parcelWithZeroDims)
					.build();

			assertThatThrownBy(() -> NShiftShipmentService.buildShipmentRequest(request))
					.isInstanceOf(IllegalStateException.class)
					.hasMessageContaining("dimensions");
		}

		@Test
		void allPresent_buildsOk()
		{
			assertThatCode(() -> NShiftShipmentService.buildShipmentRequest(validShipRequest()))
					.doesNotThrowAnyException();
		}
	}

	// =======================================================================
	// PATH 4 — NShiftShipmentService.buildOrderAdviceShipmentRequest
	// =======================================================================

	@Nested
	class OrderAdviceShipmentRequest
	{
		@Test
		void missingAttentionMapping_throwsNamingAttention()
		{
			final JsonDeliveryRequest request = validShipRequest().toBuilder()
					.mappingConfigs(MAPPING_WITHOUT_ATTENTION)
					.build();

			assertThatThrownBy(() -> NShiftShipmentService.buildOrderAdviceShipmentRequest(request))
					.isInstanceOf(IllegalStateException.class)
					.hasMessageContaining("Attention");
		}

		@Test
		void missingSenderPhone_throwsNamingPhone()
		{
			final JsonContact noPhone = JsonContact.builder()
					.name(VALID_SENDER_CONTACT.getName())
					.language(VALID_SENDER_CONTACT.getLanguage())
					.emailAddress(VALID_SENDER_CONTACT.getEmailAddress())
					// phone omitted
					.build();
			final JsonDeliveryRequest request = validShipRequest().toBuilder()
					.pickupContact(noPhone)
					.build();

			assertThatThrownBy(() -> NShiftShipmentService.buildOrderAdviceShipmentRequest(request))
					.isInstanceOf(IllegalStateException.class)
					.hasMessageContaining("Phone");
		}

		@Test
		void missingReceiverEmail_throwsNamingEmail()
		{
			final JsonContact noEmail = JsonContact.builder()
					.name(VALID_RECEIVER_CONTACT.getName())
					.language(VALID_RECEIVER_CONTACT.getLanguage())
					.phone(VALID_RECEIVER_CONTACT.getPhone())
					// emailAddress omitted
					.build();
			final JsonDeliveryRequest request = validShipRequest().toBuilder()
					.deliveryContact(noEmail)
					.build();

			assertThatThrownBy(() -> NShiftShipmentService.buildOrderAdviceShipmentRequest(request))
					.isInstanceOf(IllegalStateException.class)
					.hasMessageContaining("Email");
		}

		@Test
		void zeroDimensions_throwsNamingDimensions()
		{
			final JsonDeliveryOrderParcel parcelWithZeroDims = validShipRequest().getDeliveryOrderParcels().get(0).toBuilder()
					.packageDimensions(JsonPackageDimensions.builder().lengthInCM(0).widthInCM(0).heightInCM(0).build())
					.build();
			final JsonDeliveryRequest request = validShipRequest().toBuilder()
					.clearDeliveryOrderParcels()
					.deliveryOrderParcel(parcelWithZeroDims)
					.build();

			assertThatThrownBy(() -> NShiftShipmentService.buildOrderAdviceShipmentRequest(request))
					.isInstanceOf(IllegalStateException.class)
					.hasMessageContaining("dimensions");
		}

		@Test
		void allPresent_buildsOk()
		{
			assertThatCode(() -> NShiftShipmentService.buildOrderAdviceShipmentRequest(validShipRequest()))
					.doesNotThrowAnyException();
		}
	}
}
