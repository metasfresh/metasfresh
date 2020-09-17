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

package de.metas.rest_api.shipping;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.composite.BPartner;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.common.rest_api.JsonAttributeSetInstance;
import de.metas.common.rest_api.JsonError;
import de.metas.common.rest_api.JsonErrorItem;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.common.rest_api.JsonQuantity;
import de.metas.common.shipping.JsonProduct;
import de.metas.common.shipping.JsonProduct.JsonProductBuilder;
import de.metas.common.shipping.JsonRequestCandidateResult;
import de.metas.common.shipping.JsonRequestCandidateResults;
import de.metas.common.shipping.shipmentcandidate.JsonCustomer;
import de.metas.common.shipping.shipmentcandidate.JsonCustomer.JsonCustomerBuilder;
import de.metas.common.shipping.shipmentcandidate.JsonResponseShipmentCandidate;
import de.metas.common.shipping.shipmentcandidate.JsonResponseShipmentCandidate.JsonResponseShipmentCandidateBuilder;
import de.metas.common.shipping.shipmentcandidate.JsonResponseShipmentCandidates;
import de.metas.common.shipping.shipmentcandidate.JsonResponseShipmentCandidates.JsonResponseShipmentCandidatesBuilder;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.EmptyUtil;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.error.IssueCreateRequest;
import de.metas.inoutcandidate.ShipmentSchedule;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.ShipmentScheduleRepository;
import de.metas.inoutcandidate.ShipmentScheduleRepository.ShipmentScheduleQuery;
import de.metas.inoutcandidate.exportaudit.APIExportAudit;
import de.metas.inoutcandidate.exportaudit.APIExportAudit.APIExportAuditBuilder;
import de.metas.inoutcandidate.exportaudit.APIExportStatus;
import de.metas.inoutcandidate.exportaudit.ShipmentScheduleAuditRepository;
import de.metas.inoutcandidate.exportaudit.ShipmentScheduleExportAuditItem;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.Product;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import de.metas.quantity.Quantity;
import de.metas.rest_api.utils.RestApiUtils;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.util.lang.IPair;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import static de.metas.inoutcandidate.exportaudit.APIExportStatus.ExportedAndError;
import static de.metas.inoutcandidate.exportaudit.APIExportStatus.ExportedAndForwarded;
import static de.metas.inoutcandidate.exportaudit.APIExportStatus.Pending;

@Service
class ShipmentCandidateAPIService
{
	private final static transient Logger logger = LogManager.getLogger(ShipmentCandidateAPIService.class);

	private final ShipmentScheduleAuditRepository shipmentScheduleAuditRepository;
	private final ShipmentScheduleRepository shipmentScheduleRepository;
	private final BPartnerCompositeRepository bPartnerCompositeRepository;
	private final ProductRepository productRepository;

	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IErrorManager errorManager = Services.get(IErrorManager.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);

	public ShipmentCandidateAPIService(
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
	public JsonResponseShipmentCandidates exportShipmentCandidates(@NonNull final QueryLimit limit)
	{
		final String transactionId = UUID.randomUUID().toString();
		try (final MDC.MDCCloseable ignore = MDC.putCloseable("TransactionIdAPI", transactionId))
		{
			final APIExportAuditBuilder<ShipmentScheduleExportAuditItem> auditBuilder =
					APIExportAudit
							.<ShipmentScheduleExportAuditItem>builder()
							.transactionId(transactionId);

			final List<ShipmentSchedule> shipmentSchedules = loadShipmentSchedulesToExport(limit);

			if (shipmentSchedules.isEmpty())
			{ // return empty result and call it a day
				return JsonResponseShipmentCandidates.builder().hasMoreItems(false).transactionKey(transactionId).build();
			}

			final IdsRegistry idsRegistry = buildIdsRegistry(shipmentSchedules);

			final Map<AttributeSetInstanceId, ImmutableAttributeSet> attributesForASIs = loadAttributesForASIs(idsRegistry);

			final ImmutableMap<OrderId, I_C_Order> orderIdToOrderRecord = loadOrdersByOrderId(idsRegistry);

			final ImmutableMap<OrderAndLineId, I_C_OrderLine> idToOrderLineRecord = loadOrdersLinesById(idsRegistry);

			final ImmutableMap<ProductId, Product> productId2Product = Maps.uniqueIndex(productRepository.getByIds(idsRegistry.getProductIds()), Product::getId);

			final ImmutableMap<ShipperId, String> shipperId2InternalName = loadShipperInternalNameByIds(idsRegistry);

			final JsonResponseShipmentCandidatesBuilder result = JsonResponseShipmentCandidates.builder()
					.hasMoreItems(limit.isLimitHitOrExceeded(shipmentSchedules))
					.transactionKey(transactionId);

			final ImmutableMap<BPartnerId, BPartnerComposite> bpartnerIdToBPartner = Maps.uniqueIndex(
					bPartnerCompositeRepository.getByIds(idsRegistry.getBPartnerIds()),
					bp -> bp.getBpartner().getId());

			for (final ShipmentSchedule shipmentSchedule : shipmentSchedules)
			{
				try (final MDC.MDCCloseable ignore1 = TableRecordMDC.putTableRecordReference(I_M_ShipmentSchedule.Table_Name, shipmentSchedule.getId()))
				{
					final JsonAttributeSetInstance jsonAttributeSetInstance = createJsonASI(shipmentSchedule, attributesForASIs);
					final JsonCustomer customer = createJsonCustomer(shipmentSchedule, bpartnerIdToBPartner);
					final JsonProduct product = createJsonProduct(shipmentSchedule, customer.getLanguage(), productId2Product);

					final List<JsonQuantity> quantityToDeliver = createJsonQuantities(shipmentSchedule.getQuantityToDeliver());

					final List<JsonQuantity> orderedQty = createJsonQuantities(shipmentSchedule.getOrderedQuantity());

					final JsonResponseShipmentCandidateBuilder itemBuilder = JsonResponseShipmentCandidate.builder()
							.id(JsonMetasfreshId.of(shipmentSchedule.getId().getRepoId()))
							.orgCode(orgDAO.retrieveOrgValue(shipmentSchedule.getOrgId()))
							.customer(customer)
							.product(product)
							.attributeSetInstance(jsonAttributeSetInstance)
							.quantities(quantityToDeliver)
							.orderedQty(orderedQty)
							.dateOrdered(shipmentSchedule.getDateOrdered());

					setOrderReferences(itemBuilder, shipmentSchedule, orderIdToOrderRecord);

					setNetPrices(itemBuilder, shipmentSchedule, idToOrderLineRecord);

					setShipperInternalName(itemBuilder, shipmentSchedule, shipperId2InternalName);

					result.item(itemBuilder.build());
					createExportedAuditItem(shipmentSchedule, auditBuilder);
				}
				catch (final ShipmentCandidateExportException e) // don't catch just any exception; just the "functional" ones
				{
					createExportErrorAuditItem(shipmentSchedule, e, auditBuilder);
				}
			}

			shipmentScheduleAuditRepository.save(auditBuilder.build());
			shipmentScheduleRepository.exportStatusMassUpdate(idsRegistry.getShipmentScheduleIds(), APIExportStatus.Exported);

			return result.build();
		}
	}

	@NonNull
	private ImmutableList<JsonQuantity> createJsonQuantities(@NonNull final Quantity quantity)
	{
		return ImmutableList.of(JsonQuantity.builder()
				.qty(quantity.toBigDecimal())
				.uomCode(quantity.getX12DE355().getCode())
				.build());
	}

	private JsonCustomer createJsonCustomer(
			@NonNull final ShipmentSchedule shipmentSchedule,
			@NonNull final ImmutableMap<BPartnerId, BPartnerComposite> bpartnerIdToBPartner)
	{
		final BPartnerComposite composite = bpartnerIdToBPartner.get(shipmentSchedule.getCustomerId());
		final BPartnerLocation location = composite
				.extractLocation(shipmentSchedule.getLocationId())
				.orElseThrow(() -> new ShipmentCandidateExportException("Unable to get the shipment schedule's location from the shipment schedule's bPartner")
						.appendParametersToMessage()
						.setParameter("C_BPartner_ID", shipmentSchedule.getCustomerId().getRepoId())
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
				.shipmentAllocationBestBeforePolicy(bpartner.getShipmentAllocationBestBeforePolicy())
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
							.setParameter("C_BPartner_ID", shipmentSchedule.getCustomerId().getRepoId())
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
				.documentNote(product.getDocumentNote().translate(adLanguage))
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
		return RestApiUtils.extractJsonAttributeSetInstance(attributeSet, shipmentSchedule.getOrgId());
	}

	private void createExportedAuditItem(
			@NonNull final ShipmentSchedule shipmentSchedule,
			@NonNull final APIExportAuditBuilder<ShipmentScheduleExportAuditItem> auditBuilder)
	{
		final OrgId orgId = shipmentSchedule.getOrgId();

		auditBuilder.item(
				shipmentSchedule.getId(),
				ShipmentScheduleExportAuditItem.builder()
						.exportStatus(APIExportStatus.Exported)
						.repoIdAware(shipmentSchedule.getId())
						.orgId(orgId)
						.build());
	}

	private void createExportErrorAuditItem(
			@NonNull final ShipmentSchedule shipmentSchedule,
			@NonNull final ShipmentCandidateExportException e,
			@NonNull final APIExportAuditBuilder<ShipmentScheduleExportAuditItem> auditBuilder)
	{
		final OrgId orgId = shipmentSchedule.getOrgId();

		final AdIssueId adIssueId = Services.get(IErrorManager.class).createIssue(IssueCreateRequest.builder()
				.throwable(e)
				.loggerName(logger.getName())
				.sourceClassname(ShipmentCandidateAPIService.class.getName())
				.summary(e.getMessage())
				.build());

		auditBuilder.item(
				shipmentSchedule.getId(),
				ShipmentScheduleExportAuditItem.builder()
						.exportStatus(APIExportStatus.ExportError)
						.repoIdAware(shipmentSchedule.getId())
						.issueId(adIssueId)
						.orgId(orgId)
						.build());
	}

	@Value
	@Builder
	private static class IdsRegistry
	{
		@Singular
		Set<ShipmentScheduleId> shipmentScheduleIds;

		@Singular
		Set<AttributeSetInstanceId> asiIds;

		@Singular
		Set<OrderAndLineId> orderAndLineIds;

		@Singular
		Set<BPartnerId> bPartnerIds;

		@Singular
		Set<ProductId> productIds;

		@Singular
		Set<ShipperId> shipperIds;
	}

	/**
	 * Use the given pojo's transactionKey to load the respective export audit table and update its lines
	 */
	public void updateStatus(@NonNull final JsonRequestCandidateResults results)
	{
		final AdIssueId generalAdIssueId = createADIssue(results.getError());
		if (generalAdIssueId != null)
		{
			logger.debug("Created AD_Issue_ID={} that applies to all exported shipment schedules", generalAdIssueId.getRepoId());
		}

		if (results.getItems().isEmpty())
		{
			logger.debug("given results is empty; -> return");
			return;
		}
		final APIExportAudit<ShipmentScheduleExportAuditItem> auditRecords = shipmentScheduleAuditRepository.getByTransactionId(results.getTransactionKey());
		if (auditRecords == null)
		{
			logger.debug("Given results.transactionKey={} does not match any audit records; -> return", results.getTransactionKey());
			return;
		}
		final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds = CollectionUtils.extractDistinctElementsIntoSet(
				results.getItems(),
				item -> ShipmentScheduleId.ofRepoId(item.getScheduleId().getValue()));
		final ImmutableMap<ShipmentScheduleId, ShipmentSchedule> shipmentSchedules = shipmentScheduleRepository.getByIds(shipmentScheduleIds);

		for (final JsonRequestCandidateResult resultItem : results.getItems())
		{
			final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(resultItem.getScheduleId().getValue());
			final ShipmentSchedule shipmentSchedule = shipmentSchedules.get(shipmentScheduleId);
			if (shipmentSchedule == null)
			{
				continue; // also shouldn't happen, unless we do some API-testing with static JSON stuff
			}

			ShipmentScheduleExportAuditItem auditRecord = auditRecords.getItemById(shipmentScheduleId);
			if (auditRecord == null) // should not happen, but we don't want to make a fuzz in case it does
			{
				auditRecord = ShipmentScheduleExportAuditItem.builder()
						.orgId(shipmentSchedule.getOrgId())
						.repoIdAware(shipmentScheduleId)
						.exportStatus(Pending)
						.build();
				auditRecords.addItem(auditRecord);
			}

			final APIExportStatus status;
			switch (resultItem.getOutcome())
			{
				case OK:
					status = ExportedAndForwarded;
					break;
				case ERROR:
					status = ExportedAndError;

					final AdIssueId specificAdIssueId = createADIssue(resultItem.getError());
					auditRecord.setIssueId(CoalesceUtil.coalesce(specificAdIssueId, generalAdIssueId));
					break;
				default:
					throw new AdempiereException("resultItem has unexpected outcome=" + resultItem.getOutcome())
							.setParameter("TransactionIdAPI", results.getTransactionKey())
							.setParameter("resultItem", resultItem);
			}

			auditRecord.setExportStatus(status);
			shipmentSchedule.setExportStatus(status);
		}
		shipmentScheduleRepository.saveAll(shipmentSchedules.values());
		shipmentScheduleAuditRepository.save(auditRecords);
	}

	@Nullable
	private AdIssueId createADIssue(@Nullable final JsonError error)
	{
		if (error == null || error.getErrors().isEmpty())
		{
			return null;
		}

		final JsonErrorItem errorItem = error.getErrors().get(0);
		return errorManager.createIssue(IssueCreateRequest.builder()
				.summary(errorItem.getMessage() + "; " + errorItem.getDetail())
				.stackTrace(errorItem.getStackTrace())
				.loggerName(logger.getName())
				.build());
	}

	private void setNetPrices(
			@NonNull final JsonResponseShipmentCandidateBuilder candidateBuilder,
			@NonNull final ShipmentSchedule shipmentSchedule,
			@NonNull final Map<OrderAndLineId,I_C_OrderLine> ids2OrderLines)
	{
		if (shipmentSchedule.getOrderAndLineId() == null)
		{
			return;//nothing to do
		}

		final I_C_OrderLine orderLine = ids2OrderLines.get(shipmentSchedule.getOrderAndLineId());

		if (orderLine == null)
		{
			logger.warn("***WARNING: No orderLine found in ids2OrderLines : {} for orderAndLineId: {}", ids2OrderLines, shipmentSchedule.getOrderAndLineId());
			return;
		}

		final BigDecimal orderedQtyNetPrice = orderLineBL.computeQtyNetPriceFromOrderLine(orderLine, shipmentSchedule.getOrderedQuantity());
		final BigDecimal deliveredQtyNetPrice = orderLineBL.computeQtyNetPriceFromOrderLine(orderLine, shipmentSchedule.getDeliveredQty());

		candidateBuilder.orderedQtyNetPrice(orderedQtyNetPrice);
		candidateBuilder.deliveredQtyNetPrice(deliveredQtyNetPrice);
	}

	@NonNull
	private ImmutableMap<OrderId, I_C_Order> loadOrdersByOrderId(@NonNull final IdsRegistry idsRegistry)
	{
		if (Check.isEmpty(idsRegistry.getOrderAndLineIds()))
		{
			return ImmutableMap.of();
		}

		final ImmutableSet<OrderId> orderIds = idsRegistry.getOrderAndLineIds()
				.stream()
				.map(OrderAndLineId::getOrderId)
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableMap<OrderId, I_C_Order> orderIdToOrderRecord = queryBL.createQueryBuilder(I_C_Order.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Order.COLUMNNAME_C_Order_ID, orderIds)
				.create()
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						orderRecord -> OrderId.ofRepoId(orderRecord.getC_Order_ID()),
						Function.identity()
				));

		return orderIdToOrderRecord;
	}


	@NonNull
	private ImmutableMap<OrderAndLineId, I_C_OrderLine> loadOrdersLinesById(@NonNull final IdsRegistry idsRegistry)
	{
		if (Check.isEmpty(idsRegistry.getOrderAndLineIds()))
		{
			return ImmutableMap.of();
		}

		return orderDAO.getOrderLinesByIds(idsRegistry.getOrderAndLineIds(), I_C_OrderLine.class)
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						(orderLine) -> OrderAndLineId.ofRepoIds(orderLine.getC_Order_ID(), orderLine.getC_OrderLine_ID()),
						Function.identity()
				));
	}

	@NonNull
	private Map<AttributeSetInstanceId, ImmutableAttributeSet> loadAttributesForASIs(@NonNull final IdsRegistry idsRegistry)
	{
		if (Check.isEmpty(idsRegistry.getAsiIds()))
		{
			return ImmutableMap.of();
		}

		return attributeDAO.getAttributesForASIs(idsRegistry.getAsiIds());
	}

	private IdsRegistry buildIdsRegistry(@NonNull final List<ShipmentSchedule> schedules)
	{
		final IdsRegistry.IdsRegistryBuilder idsRegistryBuilder = IdsRegistry.builder();

		for (final ShipmentSchedule shipmentSchedule : schedules)
		{
			idsRegistryBuilder
					.shipmentScheduleId(shipmentSchedule.getId())
					.asiId(shipmentSchedule.getAttributeSetInstanceId())
					.orderAndLineId(shipmentSchedule.getOrderAndLineId())
					.bPartnerId(shipmentSchedule.getCustomerId())
					.shipperId(shipmentSchedule.getShipperId())
					.productId(shipmentSchedule.getProductId());
		}

		return idsRegistryBuilder.build();
	}

	@NonNull
	private List<ShipmentSchedule> loadShipmentSchedulesToExport(@NonNull final QueryLimit limit)
	{
		final ShipmentScheduleQuery shipmentScheduleQuery = ShipmentScheduleQuery.builder()
				.limit(limit)
				.canBeExportedFrom(Instant.now())
				.exportStatus(APIExportStatus.Pending)
				.build();

		return shipmentScheduleRepository.getBy(shipmentScheduleQuery);
	}

	private ImmutableMap<ShipperId, String> loadShipperInternalNameByIds(@NonNull final IdsRegistry idsRegistry)
	{
		if (Check.isEmpty(idsRegistry.getShipperIds()))
		{
			return ImmutableMap.of();
		}

		return shipperDAO.getByIds(idsRegistry.getShipperIds()).entrySet()
				.stream()
				.filter(entry -> Check.isNotBlank(entry.getValue().getInternalName()))
				.map(entry -> new HashMap.SimpleImmutableEntry<>(entry.getKey(), entry.getValue().getInternalName()))
				.collect(ImmutableMap.toImmutableMap(HashMap.SimpleImmutableEntry::getKey, HashMap.SimpleImmutableEntry::getValue));
	}

	private void setShipperInternalName(
			@NonNull final JsonResponseShipmentCandidateBuilder candidateBuilder,
			@NonNull final ShipmentSchedule shipmentSchedule,
			@NonNull final Map<ShipperId,String> shipperId2InternalName)
	{
		final ShipperId shipperId = shipmentSchedule.getShipperId();

		if (shipperId == null)
		{
			return;//nothing to do
		}

		candidateBuilder.shipperInternalSearchKey(shipperId2InternalName.get(shipperId));
	}

	private void setOrderReferences(
			@NonNull final JsonResponseShipmentCandidateBuilder candidateBuilder,
			@NonNull final ShipmentSchedule shipmentSchedule,
			@NonNull final Map<OrderId,I_C_Order> id2Order)
	{
		final OrderId orderId = shipmentSchedule.getOrderAndLineId() != null
				? shipmentSchedule.getOrderAndLineId().getOrderId()
				: null;

		if (orderId == null)
		{
			return; //nothing to do
		}

		final I_C_Order orderRecord = id2Order.get(orderId);

		if (orderRecord == null)
		{
			logger.warn("*** WARNING: No I_C_Order was found in id2Order: {} for orderId: {}!", id2Order, orderId);
			return;
		}

		candidateBuilder.orderDocumentNo(orderRecord.getDocumentNo());
		candidateBuilder.poReference(orderRecord.getPOReference());
	}


	private static class ShipmentCandidateExportException extends AdempiereException
	{
		public ShipmentCandidateExportException(final String message)
		{
			super(message);
		}
	}
}
