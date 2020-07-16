/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.rest_api.shipment;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.composite.BPartner;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.common.rest_api.JsonAttributeInstance;
import de.metas.common.rest_api.JsonAttributeSetInstance;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.common.rest_api.JsonQuantity;
import de.metas.common.shipmentschedule.JsonCustomer;
import de.metas.common.shipmentschedule.JsonCustomer.JsonCustomerBuilder;
import de.metas.common.shipmentschedule.JsonProduct;
import de.metas.common.shipmentschedule.JsonProduct.JsonProductBuilder;
import de.metas.common.shipmentschedule.JsonRequestShipmentCandidateResults;
import de.metas.common.shipmentschedule.JsonResponseShipmentCandidate;
import de.metas.common.shipmentschedule.JsonResponseShipmentCandidate.JsonResponseShipmentCandidateBuilder;
import de.metas.common.shipmentschedule.JsonResponseShipmentCandidates;
import de.metas.common.shipmentschedule.JsonResponseShipmentCandidates.JsonResponseShipmentCandidatesBuilder;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.EmptyUtil;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.error.IssueCreateRequest;
import de.metas.inoutcandidate.ShipmentSchedule;
import de.metas.inoutcandidate.ShipmentScheduleRepository;
import de.metas.inoutcandidate.ShipmentScheduleRepository.ShipmentScheduleQuery;
import de.metas.inoutcandidate.exportaudit.ShipmentScheduleAuditRepository;
import de.metas.inoutcandidate.exportaudit.ShipmentScheduleExportAudit;
import de.metas.inoutcandidate.exportaudit.ShipmentScheduleExportAudit.ShipmentScheduleExportAuditBuilder;
import de.metas.inoutcandidate.exportaudit.ShipmentScheduleExportAuditItem;
import de.metas.inoutcandidate.exportaudit.ShipmentScheduleExportStatus;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.order.OrderId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.Product;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.util.lang.IPair;
import org.compiere.model.I_C_Order;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

@Service
public class ShipmentCandidateExporter
{
	private final static transient Logger logger = LogManager.getLogger(ShipmentCandidateExporter.class);

	private final ShipmentScheduleAuditRepository shipmentScheduleAuditRepository;
	private final ShipmentScheduleRepository shipmentScheduleRepository;
	private final BPartnerCompositeRepository bPartnerCompositeRepository;
	private final ProductRepository productRepository;

	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public ShipmentCandidateExporter(
			@NonNull final ShipmentScheduleAuditRepository shipmentScheduleAuditRepository,
			@NonNull final ShipmentScheduleRepository shipmentScheduleRepository,
			@NonNull final BPartnerCompositeRepository bPartnerCompositeRepository,
			@NonNull final ProductRepository productRepository)
	{
		this.shipmentScheduleAuditRepository = shipmentScheduleAuditRepository;
		this.shipmentScheduleRepository = shipmentScheduleRepository;
		this.bPartnerCompositeRepository = bPartnerCompositeRepository;
		this.productRepository = productRepository;
	}

	/**
	 * Exports them; Flags them as "exported - don't touch"; creates an export audit table with one line per shipment schedule.
	 */
	public JsonResponseShipmentCandidates exportShipmentCandidates()
	{
		//final JsonResponseShipmentCandidates result = createMockResult();

		final String transactionId = UUID.randomUUID().toString();

		final Map<OrgId, ShipmentScheduleExportAuditBuilder> auditBuilders = new HashMap<>();

		final ShipmentScheduleQuery shipmentScheduleQuery = ShipmentScheduleQuery.builder()
				.limit(500)
				.canBeExportedFrom(Instant.now())
				.exportStatus(ShipmentScheduleExportStatus.Pending)
				.build();
		final List<ShipmentSchedule> shipmentSchedules = shipmentScheduleRepository.getBy(shipmentScheduleQuery);

		final IdsToLoad.IdsToLoadBuilder idsToLoadBuilder = IdsToLoad.builder();
		for (final ShipmentSchedule shipmentSchedule : shipmentSchedules)
		{
			idsToLoadBuilder
					.asiId(shipmentSchedule.getAttributeSetInstanceId())
					.orderId(shipmentSchedule.getOrderId())
					.bPartnerId(shipmentSchedule.getBPartnerId())
					.productId(shipmentSchedule.getProductId());
		}

		final IdsToLoad idsToLoad = idsToLoadBuilder.build();

		final Map<AttributeSetInstanceId, ImmutableAttributeSet> attributesForASIs;
		if (idsToLoad.getAsiIds().isEmpty())
		{
			attributesForASIs = ImmutableMap.of();
		}
		else
		{
			attributesForASIs = attributeDAO.getAttributesForASIs(idsToLoad.getAsiIds());
		}
		final JsonResponseShipmentCandidatesBuilder result = JsonResponseShipmentCandidates.builder()
				.hasMoreItems(shipmentSchedules.size() == 500)
				.transactionKey(transactionId);

		final ImmutableMap<OrderId, I_C_Order> orderIdToOrderRecord = queryBL.createQueryBuilder(I_C_Order.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Order.COLUMNNAME_C_Order_ID, idsToLoad.getOrderIds())
				.create()
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						orderRecord -> OrderId.ofRepoId(orderRecord.getC_Order_ID()),
						Function.identity()
				));

		final ImmutableMap<BPartnerId, BPartnerComposite> bpartnerIdToBPartner = Maps.uniqueIndex(
				bPartnerCompositeRepository.getByIds(idsToLoad.getBPartnerIds()),
				bp -> bp.getBpartner().getId());

		final ImmutableMap<ProductId, Product> productId2Product = Maps.uniqueIndex(productRepository.getByIds(idsToLoad.getProductIds()), Product::getId);

		for (final ShipmentSchedule shipmentSchedule : shipmentSchedules)
		{
			try (final MDC.MDCCloseable ignore = TableRecordMDC.putTableRecordReference(I_M_ShipmentSchedule.Table_Name, shipmentSchedule.getId()))
			{
				final JsonAttributeSetInstance jsonAttributeSetInstance = createJsonASI(shipmentSchedule, attributesForASIs);
				final JsonCustomer customer = createJsonCustomer(shipmentSchedule, bpartnerIdToBPartner);
				final JsonProduct product = createJsonProduct(shipmentSchedule, customer.getLanguage(), productId2Product);
				final I_C_Order orderRecord = orderIdToOrderRecord.get(shipmentSchedule.getOrderId());
				final Quantity quantity = shipmentSchedule.getQuantityToDeliver();
				final List<JsonQuantity> quantities = createJsonQuantities(quantity);

				final JsonResponseShipmentCandidateBuilder itemBuilder = JsonResponseShipmentCandidate.builder()
						.id(JsonMetasfreshId.of(shipmentSchedule.getId().getRepoId()))
						.orgCode(orgDAO.retrieveOrgValue(shipmentSchedule.getOrgId()))
						.customer(customer)
						.product(product)
						.attributeSetInstance(jsonAttributeSetInstance)
						.quantities(quantities)
						.dateOrdered(shipmentSchedule.getDateOrdered());
				if (orderRecord != null)
				{
					itemBuilder
							.orderDocumentNo(orderRecord.getDocumentNo())
							.poReference(orderRecord.getPOReference());
				}

				result.item(itemBuilder.build());
				createExportedAuditItem(shipmentSchedule, transactionId, auditBuilders);
			}
			catch (final ShipmentCandidateExportException e) // don't catch just any exception; just the "functional" ones
			{
				createExportErrorAuditItem(shipmentSchedule, transactionId, e, auditBuilders);
			}
		}

		for (final ShipmentScheduleExportAuditBuilder value : auditBuilders.values())
		{
			shipmentScheduleAuditRepository.save(value.build());
		}
		return result.build();
	}

	@NonNull
	private ImmutableList<JsonQuantity> createJsonQuantities(@NonNull final Quantity quantity)
	{
		return ImmutableList.of(JsonQuantity.builder().qty(quantity.toBigDecimal()).uomCode(quantity.getUOMSymbol()).build());
	}

	private JsonCustomer createJsonCustomer(
			@NonNull final ShipmentSchedule shipmentSchedule,
			@NonNull final ImmutableMap<BPartnerId, BPartnerComposite> bpartnerIdToBPartner)
	{
		final BPartnerComposite composite = bpartnerIdToBPartner.get(shipmentSchedule.getBPartnerId());
		final BPartnerLocation location = composite
				.extractLocation(shipmentSchedule.getLocationId())
				.orElseThrow(() -> new ShipmentCandidateExportException("Unable to get the shipment schedule's location from the shipment schedule's bPartner")
						.appendParametersToMessage()
						.setParameter("C_BPartner_ID", shipmentSchedule.getBPartnerId().getRepoId())
						.setParameter("C_BPartner_Location_ID", shipmentSchedule.getLocationId().getRepoId()));

		final IPair<String, String> splitStreetAndHouseNumber = StringUtils.splitStreetAndHouseNumberOrNull(location.getAddress1());
		if (splitStreetAndHouseNumber == null)
		{
			throw new ShipmentCandidateExportException("BPartner's location needs to have an Address1 with a discernible street and street number")
					.appendParametersToMessage()
					.setParameter("Address1", location.getAddress1())
					.setParameter("C_BPartner_ID", composite.getBpartner().getId().getRepoId())
					.setParameter("C_BPartner_Location_ID", location.getId().getRepoId());
		}
		final BPartner bpartner = composite.getBpartner();

		final String adLanguage = bpartner.getLanguage() != null ? bpartner.getLanguage().getAD_Language() : Env.getAD_Language();

		final String postal = location.getPostal();
		if (EmptyUtil.isBlank(postal))
		{
			throw new ShipmentCandidateExportException("BPartner's location needs to have a postal set")
					.appendParametersToMessage()
					.setParameter("C_BPartner_ID", composite.getBpartner().getId().getRepoId())
					.setParameter("C_BPartner_Location_ID", location.getId().getRepoId());
		}
		final String city = location.getCity();
		if (EmptyUtil.isBlank(city))
		{
			throw new ShipmentCandidateExportException("BPartner's location needs to have a city set")
					.appendParametersToMessage()
					.setParameter("C_BPartner_ID", composite.getBpartner().getId().getRepoId())
					.setParameter("C_BPartner_Location_ID", location.getId().getRepoId());
		}
		final JsonCustomerBuilder customerBuilder = JsonCustomer.builder()
				.companyName(CoalesceUtil.coalesce(bpartner.getCompanyName(), bpartner.getName()))
				.street(splitStreetAndHouseNumber.getLeft())
				.streetNo(splitStreetAndHouseNumber.getRight())
				.postal(postal)
				.city(city)
				.countryCode(location.getCountryCode())
				.language(adLanguage);
		if (shipmentSchedule.getContactId() != null)
		{
			final BPartnerContact contact = composite.extractContact(shipmentSchedule.getContactId())
					.orElseThrow(() -> new ShipmentCandidateExportException("Unable to get the shipment schedule's contact from the shipment schedule's bPartner")
							.appendParametersToMessage()
							.setParameter("C_BPartner_ID", shipmentSchedule.getBPartnerId().getRepoId())
							.setParameter("AD_User_ID", shipmentSchedule.getContactId().getRepoId()));

			customerBuilder
					.contactEmail(contact.getEmail())
					.contactName(contact.getName())
					.contactPhone(CoalesceUtil.coalesce(contact.getMobilePhone(), contact.getPhone()));
		}
		return customerBuilder.build();
	}

	private JsonProduct createJsonProduct(
			@NonNull final ShipmentSchedule shipmentSchedule,
			@NonNull final String adLanguage,
			@NonNull final ImmutableMap<ProductId, Product> productId2Product)
	{
		final Product product = productId2Product.get(shipmentSchedule.getProductId());

		final JsonProductBuilder productBuilder = JsonProduct.builder()
				.productNo(product.getProductNo())
				.name(product.getName().translate(adLanguage))
				.packageSize(product.getPackageSize())
				.weight(product.getWeight());
		if (product.getDescription() != null)
		{
			productBuilder.description(product.getDescription().translate(adLanguage));
		}
		return productBuilder.build();
	}

	@Nullable
	private JsonAttributeSetInstance createJsonASI(
			@NonNull final ShipmentSchedule shipmentSchedule,
			@NonNull final Map<AttributeSetInstanceId, ImmutableAttributeSet> attributesForASIs)
	{
		final AttributeSetInstanceId scheduleASIId = shipmentSchedule.getAttributeSetInstanceId();
		if (!AttributeSetInstanceId.isRegular(scheduleASIId))
		{
			return null;
		}
		final ImmutableAttributeSet attributeSet = attributesForASIs.get(scheduleASIId);
		return extractJsonAttributeSetInstance(attributeSet, shipmentSchedule.getOrgId());
	}

	private void createExportedAuditItem(
			@NonNull final ShipmentSchedule shipmentSchedule,
			@NonNull final String transactionId,
			@NonNull final Map<OrgId, ShipmentScheduleExportAuditBuilder> auditBuilders)
	{
		final OrgId orgId = shipmentSchedule.getOrgId();

		final ShipmentScheduleExportAuditBuilder auditBuilder = auditBuilders.computeIfAbsent(
				orgId,
				k -> ShipmentScheduleExportAudit.builder().orgId(k).transactionId(transactionId));
		auditBuilder.item(
				shipmentSchedule.getId(),
				ShipmentScheduleExportAuditItem.builder()
						.exportStatus(ShipmentScheduleExportStatus.Exported)
						.shipmentScheduleId(shipmentSchedule.getId())
						.orgId(orgId)
						.build());
	}

	private void createExportErrorAuditItem(
			@NonNull final ShipmentSchedule shipmentSchedule,
			@NonNull final String transactionId,
			@NonNull final ShipmentCandidateExportException e,
			@NonNull final Map<OrgId, ShipmentScheduleExportAuditBuilder> auditBuilders)
	{
		final OrgId orgId = shipmentSchedule.getOrgId();

		final AdIssueId adIssueId = Services.get(IErrorManager.class).createIssue(IssueCreateRequest.builder()
				.throwable(e)
				.loggerName(logger.getName())
				.sourceClassname(ShipmentCandidateExporter.class.getName())
				.summary(e.getMessage())
				.build());

		final ShipmentScheduleExportAuditBuilder auditBuilder = auditBuilders.computeIfAbsent(
				orgId,
				k -> ShipmentScheduleExportAudit.builder().orgId(k).transactionId(transactionId));
		auditBuilder.item(
				shipmentSchedule.getId(),
				ShipmentScheduleExportAuditItem.builder()
						.exportStatus(ShipmentScheduleExportStatus.ExportError)
						.shipmentScheduleId(shipmentSchedule.getId())
						.issueId(adIssueId)
						.orgId(orgId)
						.build());
	}

	private JsonAttributeSetInstance extractJsonAttributeSetInstance(
			@NonNull final ImmutableAttributeSet attributeSet,
			@NonNull final OrgId orgId)
	{
		final JsonAttributeSetInstance.JsonAttributeSetInstanceBuilder jsonAttributeSetInstance = JsonAttributeSetInstance.builder();
		for (final AttributeId attributeId : attributeSet.getAttributeIds())
		{
			final AttributeCode attributeCode = attributeSet.getAttributeCode(attributeId);

			final JsonAttributeInstance.JsonAttributeInstanceBuilder instanceBuilder = JsonAttributeInstance.builder()
					.attributeName(attributeSet.getAttributeName(attributeId))
					.attributeCode(attributeCode.getCode());
			final String attributeValueType = attributeSet.getAttributeValueType(attributeId);
			if (X_M_Attribute.ATTRIBUTEVALUETYPE_Date.equals(attributeValueType))
			{
				final Date valueAsDate = attributeSet.getValueAsDate(attributeCode);
				if (valueAsDate != null)
				{
					final ZoneId timeZone = orgDAO.getTimeZone(orgId);
					final LocalDate localDate = valueAsDate.toInstant().atZone(timeZone).toLocalDate();
					instanceBuilder.valueDate(localDate);
				}
			}
			else if (X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40.equals(attributeValueType))
			{
				instanceBuilder.valueStr(attributeSet.getValueAsString(attributeCode));
			}
			else if (X_M_Attribute.ATTRIBUTEVALUETYPE_List.equals(attributeValueType))
			{
				instanceBuilder.valueStr(attributeSet.getValueAsString(attributeCode));
			}
			else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Number.equals(attributeValueType))
			{
				instanceBuilder.valueNumber(attributeSet.getValueAsBigDecimal(attributeCode));
			}

			jsonAttributeSetInstance.attributeInstance(instanceBuilder.build());
		}
		return jsonAttributeSetInstance.build();
	}

	@Value
	@Builder
	private static class IdsToLoad
	{
		@Singular
		Set<AttributeSetInstanceId> asiIds;

		@Singular
		Set<OrderId> orderIds;

		@Singular
		Set<BPartnerId> bPartnerIds;

		@Singular
		Set<ProductId> productIds;
	}

	private JsonResponseShipmentCandidates createMockResult()
	{
		return JsonResponseShipmentCandidates.builder()
				.transactionKey("transactionKey")
				.item(JsonResponseShipmentCandidate.builder()
						.id(JsonMetasfreshId.of(10))
						.orgCode("orgCode")
						.dateOrdered(LocalDateTime.of(2020, Month.JULY, 14, 5, 48))
						.poReference("poReference")
						.orderDocumentNo("orderDocumentNo")
						.product(JsonProduct.builder().name("name").productNo("productNo").description("description").weight(BigDecimal.TEN).packageSize("100pce").build())
						.customer(JsonCustomer.builder().street("street").streetNo("streetNo").postal("postal").city("city").build())
						.quantity(JsonQuantity.builder().qty(BigDecimal.ONE).uomCode("PCE").build())
						.quantity(JsonQuantity.builder().qty(BigDecimal.TEN).uomCode("KG").build())
						.attributeSetInstance(JsonAttributeSetInstance.builder().id(JsonMetasfreshId.of(20))
								.attributeInstance(JsonAttributeInstance.builder().attributeName("attributeName_1").attributeCode("attributeCode_1").valueNumber(BigDecimal.TEN).build())
								.attributeInstance(JsonAttributeInstance.builder().attributeName("attributeName_2").attributeCode("attributeCode_2").valueStr("valueStr").build())
								.build())
						.build())
				.build();
	}

	/**
	 * Use the given pojo's transactionKey to load the respective export audit table and update its lines
	 */
	public void updateStatus(final JsonRequestShipmentCandidateResults status)
	{

	}

	private static class ShipmentCandidateExportException extends AdempiereException
	{
		public ShipmentCandidateExportException(final String message)
		{
			super(message);
		}
	}
}
