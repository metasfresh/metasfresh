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

package de.metas.rest_api.v2.shipping;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import com.google.common.collect.UnmodifiableIterator;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.composite.BPartner;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonAttributeSetInstance;
import de.metas.common.rest_api.v2.JsonError;
import de.metas.common.rest_api.v2.JsonErrorItem;
import de.metas.common.rest_api.v2.JsonQuantity;
import de.metas.common.shipping.v2.JsonProduct;
import de.metas.common.shipping.v2.JsonRequestCandidateResult;
import de.metas.common.shipping.v2.JsonRequestCandidateResults;
import de.metas.common.shipping.v2.shipmentcandidate.JsonCustomer;
import de.metas.common.shipping.v2.shipmentcandidate.JsonCustomer.JsonCustomerBuilder;
import de.metas.common.shipping.v2.shipmentcandidate.JsonResponseShipmentCandidate;
import de.metas.common.shipping.v2.shipmentcandidate.JsonResponseShipmentCandidate.JsonResponseShipmentCandidateBuilder;
import de.metas.common.shipping.v2.shipmentcandidate.JsonResponseShipmentCandidates;
import de.metas.common.shipping.v2.shipmentcandidate.JsonResponseShipmentCandidates.JsonResponseShipmentCandidatesBuilder;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.EmptyUtil;
import de.metas.common.util.StringUtils;
import de.metas.common.util.pair.IPair;
import de.metas.common.util.time.SystemTime;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.error.IssueCreateRequest;
import de.metas.impex.InputDataSourceId;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.ShipmentSchedule;
import de.metas.inoutcandidate.ShipmentScheduleRepository;
import de.metas.inoutcandidate.ShipmentScheduleRepository.ShipmentScheduleQuery;
import de.metas.inoutcandidate.exportaudit.APIExportAudit;
import de.metas.inoutcandidate.exportaudit.APIExportAudit.APIExportAuditBuilder;
import de.metas.inoutcandidate.exportaudit.APIExportStatus;
import de.metas.inoutcandidate.exportaudit.ShipmentScheduleAuditRepository;
import de.metas.inoutcandidate.exportaudit.ShipmentScheduleExportAuditItem;
import de.metas.inoutcandidate.exportaudit.ShipmentScheduleExportAuditItem.ShipmentScheduleExportAuditItemBuilder;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.ordercandidate.api.IOLCandDAO;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.Product;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import de.metas.quantity.Quantity;
import de.metas.rest_api.utils.RestApiUtilsV2;
import de.metas.rest_api.v2.shipping.custom.OxidAdaptor;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
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
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import static de.metas.inoutcandidate.exportaudit.APIExportStatus.ExportedAndError;
import static de.metas.inoutcandidate.exportaudit.APIExportStatus.ExportedAndForwarded;

@Service
class ShipmentCandidateAPIService
{
	private final static Logger logger = LogManager.getLogger(ShipmentCandidateAPIService.class);

	private final ShipmentScheduleAuditRepository shipmentScheduleAuditRepository;
	private final ShipmentScheduleRepository shipmentScheduleRepository;
	private final BPartnerCompositeRepository bPartnerCompositeRepository;
	private final ProductRepository productRepository;
	private final ShipmentCandidateExportSequenceNumberProvider exportSequenceNumberProvider;
	private final OxidAdaptor oxidAdaptor;

	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IErrorManager errorManager = Services.get(IErrorManager.class);
	private final IOLCandDAO olCandDAO = Services.get(IOLCandDAO.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);
	private final IInputDataSourceDAO inputDataSourceDAO = Services.get(IInputDataSourceDAO.class);

	public ShipmentCandidateAPIService(
			@NonNull final ShipmentScheduleAuditRepository shipmentScheduleAuditRepository,
			@NonNull final ShipmentScheduleRepository shipmentScheduleRepository,
			@NonNull final BPartnerCompositeRepository bPartnerCompositeRepository,
			@NonNull final ProductRepository productRepository,
			@NonNull final ShipmentCandidateExportSequenceNumberProvider exportSequenceNumberProvider,
			@NonNull final OxidAdaptor oxidAdaptor)
	{
		this.shipmentScheduleAuditRepository = shipmentScheduleAuditRepository;
		this.shipmentScheduleRepository = shipmentScheduleRepository;
		this.bPartnerCompositeRepository = bPartnerCompositeRepository;
		this.productRepository = productRepository;
		this.exportSequenceNumberProvider = exportSequenceNumberProvider;
		this.oxidAdaptor = oxidAdaptor;
	}

	/**
	 * Exports them; Flags them as "exported - don't touch"; creates an export audit table with one line per shipment schedule.
	 */
	public JsonResponseShipmentCandidates exportShipmentCandidates(@NonNull final QueryLimit limit)
	{
		final String transactionKey = UUID.randomUUID().toString();
		try (final MDC.MDCCloseable ignore = MDC.putCloseable("TransactionIdAPI", transactionKey))
		{
			final List<ShipmentSchedule> shipmentSchedules = loadShipmentSchedulesToExport(limit);

			if (shipmentSchedules.isEmpty())
			{ // return empty result and call it a day
				return JsonResponseShipmentCandidates.empty(transactionKey);
			}

			final IdsRegistry idsRegistry = buildIdsRegistry(shipmentSchedules);

			final Map<AttributeSetInstanceId, ImmutableAttributeSet> attributesForASIs = loadAttributesForASIs(idsRegistry);

			final ImmutableMap<OrderId, I_C_Order> orderIdToOrderRecord = loadOrdersByOrderId(idsRegistry);

			final ImmutableMap<OrderAndLineId, I_C_OrderLine> idToOrderLineRecord = loadOrdersLinesById(idsRegistry);

			final ImmutableMap<ProductId, Product> productId2Product = Maps.uniqueIndex(productRepository.getByIds(idsRegistry.getProductIds()), Product::getId);

			final ImmutableMap<ShipperId, String> shipperId2InternalName = loadShipperInternalNameByIds(idsRegistry);

			final int exportSequenceNumber = exportSequenceNumberProvider.provideNextShipmentCandidateSeqNo();

			final APIExportAuditBuilder<ShipmentScheduleExportAuditItem> auditBuilder =
					APIExportAudit
							.<ShipmentScheduleExportAuditItem>builder()
							.transactionId(transactionKey)
							.exportSequenceNumber(exportSequenceNumber)
							.orgId(OrgId.ANY) // currently, this audit *might* export shipment scheds from different orgs
							.exportStatus(APIExportStatus.Exported); // status might be changed to "error" down the line

			final JsonResponseShipmentCandidatesBuilder result = JsonResponseShipmentCandidates.builder()
					.hasMoreItems(limit.isLimitHitOrExceeded(shipmentSchedules))
					.transactionKey(transactionKey)
					.exportSequenceNumber(exportSequenceNumber);

			final ImmutableMap<BPartnerId, BPartnerComposite> bpartnerIdToBPartner = Maps.uniqueIndex(
					bPartnerCompositeRepository.getByIds(idsRegistry.getBPartnerIds()),
					bp -> bp.getBpartner().getId());

			// keep track about which shipment schedule was successfully exported
			final HashMap<APIExportStatus, HashSet<ShipmentScheduleId>> status2ShipmentScheduleIds = new HashMap<>();

			for (final ShipmentSchedule shipmentSchedule : shipmentSchedules)
			{
				try (final MDC.MDCCloseable ignore1 = TableRecordMDC.putTableRecordReference(I_M_ShipmentSchedule.Table_Name, shipmentSchedule.getId()))
				{
					final JsonAttributeSetInstance jsonAttributeSetInstance = createJsonASI(shipmentSchedule, attributesForASIs);

					final Optional<JsonCustomer> shipBPartnerOpt = createJsonCustomer(shipmentSchedule, bpartnerIdToBPartner, true/*isShippingBPartner*/);

					Check.assume(shipBPartnerOpt.isPresent(), "Failed to create shipBPartner to export! ShipmentScheduleId: {}", shipmentSchedule.getId());

					final JsonCustomer shipBPartner = shipBPartnerOpt.get();

					final JsonProduct product = createJsonProduct(shipmentSchedule, shipBPartner.getLanguage(), productId2Product);

					final List<JsonQuantity> quantityToDeliver = createJsonQuantities(shipmentSchedule.getQuantityToDeliver());

					final List<JsonQuantity> orderedQty = createJsonQuantities(shipmentSchedule.getOrderedQuantity());

					final JsonResponseShipmentCandidateBuilder itemBuilder = JsonResponseShipmentCandidate.builder()
							.id(JsonMetasfreshId.of(shipmentSchedule.getId().getRepoId()))
							.orgCode(orgDAO.retrieveOrgValue(shipmentSchedule.getOrgId()))
							.shipBPartner(shipBPartner)
							.product(product)
							.attributeSetInstance(jsonAttributeSetInstance)
							.quantities(quantityToDeliver)
							.orderedQty(orderedQty)
							.dateOrdered(shipmentSchedule.getDateOrdered())
							.numberOfItemsForSameShipment(shipmentSchedule.getNumberOfItemsForSameShipment());

					final Optional<JsonCustomer> billBPartner = createJsonCustomer(shipmentSchedule, bpartnerIdToBPartner, false/*isShippingBPartner*/);

					billBPartner.ifPresent(itemBuilder::billBPartner);

					setOrderReferences(itemBuilder, shipmentSchedule, orderIdToOrderRecord);

					setNetPrices(itemBuilder, shipmentSchedule, idToOrderLineRecord);

					setShipperInternalName(itemBuilder, shipmentSchedule, shipperId2InternalName);

					setOrderDataSource(itemBuilder, shipmentSchedule);

					result.item(itemBuilder.build());
					createExportedAuditItem(shipmentSchedule, auditBuilder);
					status2ShipmentScheduleIds
							.computeIfAbsent(APIExportStatus.Exported, k -> new HashSet<>())
							.add(shipmentSchedule.getId());
				}
				catch (final RuntimeException e) // catch all RTEs; if we don't catch them here, then the whole export won't proceed, for no shipment candidate
				{
					createExportErrorAuditItem(shipmentSchedule, e, auditBuilder);
					status2ShipmentScheduleIds
							.computeIfAbsent(APIExportStatus.ExportError, k -> new HashSet<>())
							.add(shipmentSchedule.getId());
				}
			}

			shipmentScheduleAuditRepository.save(auditBuilder.build());
			for (final APIExportStatus status : status2ShipmentScheduleIds.keySet())
			{
				shipmentScheduleRepository.exportStatusMassUpdate(status2ShipmentScheduleIds.get(status), status);
			}

			return result.build();
		}
	}

	/**
	 * Also see the API-doc for {@code orderDataSource} in {@link JsonResponseShipmentCandidate}.
	 */
	private void setOrderDataSource(
			@NonNull final JsonResponseShipmentCandidateBuilder itemBuilder,
			@NonNull final ShipmentSchedule shipmentSchedule)
	{
		final OrderAndLineId orderAndLineId = shipmentSchedule.getOrderAndLineId();
		if (orderAndLineId == null)
		{
			return; // avoid NPE
		}

		final List<I_C_OLCand> olCands = olCandDAO.retrieveOLCands(orderAndLineId.getOrderLineId(), I_C_OLCand.class);
		for (final I_C_OLCand olCand : olCands)
		{
			final InputDataSourceId id = InputDataSourceId.ofRepoId(olCand.getAD_InputDataSource_ID()); // C_OLCand.AD_InputDataSource_ID is mandatory
			final I_AD_InputDataSource byId = inputDataSourceDAO.getById(id);

			if (Check.isNotBlank(byId.getInternalName()))
			{
				itemBuilder.orderDataSourceInternalName(byId.getInternalName());
				return;
			}
		}

		itemBuilder.orderDataSourceInternalName(null); // no ad_inputdatasource found
	}

	@NonNull
	private ImmutableList<JsonQuantity> createJsonQuantities(@NonNull final Quantity quantity)
	{
		return ImmutableList.of(JsonQuantity.builder()
										.qty(quantity.toBigDecimal())
										.uomCode(quantity.getX12DE355().getCode())
										.build());
	}

	@NonNull
	private Optional<JsonCustomer> createJsonCustomer(
			@NonNull final ShipmentSchedule shipmentSchedule,
			@NonNull final ImmutableMap<BPartnerId, BPartnerComposite> bpartnerIdToBPartner,
			final boolean isShippingBPartner)
	{
		if (!isShippingBPartner
				&& (shipmentSchedule.getBillBPartnerId() == null || shipmentSchedule.getBillLocationId() == null))
		{
			return Optional.empty();
		}

		final BPartnerId bpartnerId;
		final BPartnerLocationId bPartnerLocationId;
		final BPartnerContactId bPartnerContactId;

		if (isShippingBPartner)
		{
			bpartnerId = shipmentSchedule.getShipBPartnerId();
			bPartnerLocationId = shipmentSchedule.getShipLocationId();
			bPartnerContactId = shipmentSchedule.getShipContactId();
		}
		else
		{
			bpartnerId = shipmentSchedule.getBillBPartnerId();
			bPartnerLocationId = shipmentSchedule.getBillLocationId();
			bPartnerContactId = shipmentSchedule.getBillContactId();
		}

		final OrderAndLineId orderAndLineId = shipmentSchedule.getOrderAndLineId();

		final BPartnerComposite composite = bpartnerIdToBPartner.get(bpartnerId);
		final BPartnerLocation location = composite
				.extractLocation(bPartnerLocationId)
				.orElseThrow(() -> new ShipmentCandidateExportException("Unable to get the shipment schedule's location from the shipment schedule's bPartner")
						.appendParametersToMessage()
						.setParameter("C_BPartner_ID", bpartnerId.getRepoId())
						.setParameter("C_BPartner_Location_ID", bPartnerLocationId.getRepoId()));

		final IPair<String, String> splitStreetAndHouseNumber = StringUtils.splitStreetAndHouseNumberOrNull(location.getAddress1());
		if (splitStreetAndHouseNumber == null || splitStreetAndHouseNumber.getLeft() == null || splitStreetAndHouseNumber.getRight() == null)
		{
			throw new ShipmentCandidateExportException("Unable to extract street and street-number from Address1")
					.appendParametersToMessage()
					.setParameter("Address1", location.getAddress1())
					.setParameter("C_BPartner_ID", composite.getBpartner().getId().getRepoId())
					.setParameter("C_BPartner_Location_ID", location.getIdNotNull().getRepoId());
		}
		final BPartner bpartner = composite.getBpartner();

		final String adLanguage = bpartner.getLanguage() != null ? bpartner.getLanguage().getAD_Language() : Env.getAD_Language();

		final String postal = location.getPostal();
		if (EmptyUtil.isBlank(postal))
		{
			throw new ShipmentCandidateExportException("BPartner's location needs to have a postal set")
					.appendParametersToMessage()
					.setParameter("C_BPartner_ID", composite.getBpartner().getId().getRepoId())
					.setParameter("C_BPartner_Location_ID", location.getIdNotNull().getRepoId());
		}
		final String city = location.getCity();
		if (EmptyUtil.isBlank(city))
		{
			throw new ShipmentCandidateExportException("BPartner's location needs to have a city set")
					.appendParametersToMessage()
					.setParameter("C_BPartner_ID", composite.getBpartner().getId().getRepoId())
					.setParameter("C_BPartner_Location_ID", location.getIdNotNull().getRepoId());
		}
		final JsonCustomerBuilder customerBuilder = JsonCustomer.builder()
				.company(bpartner.isCompany())
				.companyName(CoalesceUtil.firstNotEmptyTrimmed(location.getBpartnerName(), bpartner.getCompanyName(), bpartner.getName()))
				.shipmentAllocationBestBeforePolicy(bpartner.getShipmentAllocationBestBeforePolicy())
				.street(splitStreetAndHouseNumber.getLeft())
				.streetNo(splitStreetAndHouseNumber.getRight())
				.addressSuffix1(location.getAddress2())
				.addressSuffix2(location.getAddress3())
				.addressSuffix3(location.getAddress4())
				.postal(postal)
				.city(city)
				.countryCode(location.getCountryCode())
				.language(adLanguage);

		if (orderAndLineId != null && oxidAdaptor.isOxidOrder(orderAndLineId))
		{
			setAdditionalContactFieldsForOxidOrder(shipmentSchedule, composite, customerBuilder, bPartnerContactId);
		}
		else if (isShippingBPartner && orderAndLineId != null)
		{
			setAdditionalContactFields(customerBuilder, orderAndLineId);
		}
		else
		{
			setAdditionalContactFields(customerBuilder, location);
		}

		return Optional.of(customerBuilder.build());
	}

	private JsonProduct createJsonProduct(
			@NonNull final ShipmentSchedule shipmentSchedule,
			@NonNull final String adLanguage,
			@NonNull final ImmutableMap<ProductId, Product> productId2Product)
	{
		final Product product = productId2Product.get(shipmentSchedule.getProductId());

		return JsonProduct.builder()
				.productNo(product.getProductNo())
				.name(product.getName().translate(adLanguage))
				.documentNote(product.getDocumentNote().translate(adLanguage))
				.packageSize(product.getPackageSize())
				.weight(product.getWeight())
				.stocked(product.isStocked())
				.description(product.getDescription().translate(adLanguage))
				.build();
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
		return RestApiUtilsV2.extractJsonAttributeSetInstance(attributeSet, shipmentSchedule.getOrgId());
	}

	private void createExportedAuditItem(
			@NonNull final ShipmentSchedule shipmentSchedule,
			@NonNull final APIExportAuditBuilder<ShipmentScheduleExportAuditItem> auditBuilder)
	{
		auditBuilder.item(
				shipmentSchedule.getId(),
				ShipmentScheduleExportAuditItem.builder()
						.exportStatus(APIExportStatus.Exported)
						.repoIdAware(shipmentSchedule.getId())
						.orgId(shipmentSchedule.getOrgId())
						.build());
	}

	private void createExportErrorAuditItem(
			@NonNull final ShipmentSchedule shipmentSchedule,
			@NonNull final RuntimeException e,
			@NonNull final APIExportAuditBuilder<ShipmentScheduleExportAuditItem> auditBuilder)
	{
		final OrgId orgId = shipmentSchedule.getOrgId();

		final AdIssueId adIssueId = Services.get(IErrorManager.class).createIssue(IssueCreateRequest.builder()
																						  .throwable(e)
																						  .loggerName(logger.getName())
																						  .sourceClassname(ShipmentCandidateAPIService.class.getName())
																						  .summary(e.getMessage())
																						  .build());

		auditBuilder
				.exportStatus(APIExportStatus.ExportError)
				.item(
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
		final APIExportAudit<ShipmentScheduleExportAuditItem> exportAudit = shipmentScheduleAuditRepository.getByTransactionId(results.getTransactionKey());
		if (exportAudit == null)
		{
			logger.debug("Given results.transactionKey={} does not match any audit records; -> return", results.getTransactionKey());
			return;
		}

		exportAudit.setForwardedData(results.getForwardedData());
		exportAudit.setIssueId(generalAdIssueId);

		final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds = CollectionUtils.extractDistinctElementsIntoSet(
				results.getItems(),
				item -> ShipmentScheduleId.ofRepoId(item.getScheduleId().getValue()));
		final ImmutableMap<ShipmentScheduleId, ShipmentSchedule> shipmentSchedules = shipmentScheduleRepository.getByIds(shipmentScheduleIds);

		APIExportStatus overallStatus = ExportedAndForwarded;
		for (final JsonRequestCandidateResult jsonResultItem : results.getItems())
		{
			final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(jsonResultItem.getScheduleId().getValue());
			final ShipmentSchedule shipmentSchedule = shipmentSchedules.get(shipmentScheduleId);
			if (shipmentSchedule == null)
			{
				continue; // also shouldn't happen, unless we do some API-testing with static JSON stuff
			}

			final ShipmentScheduleExportAuditItemBuilder builder = createOrGetAuditItemBuilder(exportAudit, shipmentScheduleId)
					.orgId(shipmentSchedule.getOrgId());

			final APIExportStatus itemStatus;
			switch (jsonResultItem.getOutcome())
			{
				case OK:
					itemStatus = ExportedAndForwarded;
					break;
				case ERROR:
					itemStatus = ExportedAndError;
					overallStatus = ExportedAndError;

					final AdIssueId specificAdIssueId = createADIssue(jsonResultItem.getError());
					builder.issueId(specificAdIssueId);
					break;
				default:
					throw new AdempiereException("jsonResultItem has unexpected outcome=" + jsonResultItem.getOutcome())
							.setParameter("TransactionIdAPI", results.getTransactionKey())
							.setParameter("jsonResultItem", jsonResultItem);
			}
			builder.exportStatus(itemStatus);
			exportAudit.addItem(builder.build());

			shipmentSchedule.setExportStatus(itemStatus);
		}

		exportAudit.setExportStatus(overallStatus);

		shipmentScheduleRepository.saveAll(shipmentSchedules.values());
		shipmentScheduleAuditRepository.save(exportAudit);
	}

	private ShipmentScheduleExportAuditItemBuilder createOrGetAuditItemBuilder(
			@NonNull final APIExportAudit<ShipmentScheduleExportAuditItem> exportAudit,
			@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		final ShipmentScheduleExportAuditItem existingExportAuditItem = exportAudit.getItemById(shipmentScheduleId);
		if (existingExportAuditItem == null) // should not happen, but we don't want to make a fuzz in case it does
		{
			return ShipmentScheduleExportAuditItem.builder()
					.repoIdAware(shipmentScheduleId);
		}
		else
		{
			return existingExportAuditItem.toBuilder();
		}
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
			@NonNull final Map<OrderAndLineId, I_C_OrderLine> ids2OrderLines)
	{
		if (shipmentSchedule.getOrderAndLineId() == null)
		{
			return;// nothing to do
		}

		final I_C_OrderLine orderLine = ids2OrderLines.get(shipmentSchedule.getOrderAndLineId());

		if (orderLine == null)
		{
			logger.warn("***WARNING: No orderLine found in ids2OrderLines : {} for orderAndLineId: {}", ids2OrderLines, shipmentSchedule.getOrderAndLineId());
			return;
		}

		final BigDecimal orderedQtyNetPrice = orderLineBL.computeQtyNetPriceFromOrderLine(orderLine, shipmentSchedule.getOrderedQuantity());
		final BigDecimal qtyToDeliverNetPrice = orderLineBL.computeQtyNetPriceFromOrderLine(orderLine, shipmentSchedule.getQuantityToDeliver());
		final BigDecimal deliveredQtyNetPrice = orderLineBL.computeQtyNetPriceFromOrderLine(orderLine, shipmentSchedule.getDeliveredQuantity());

		candidateBuilder.orderedQtyNetPrice(orderedQtyNetPrice);
		candidateBuilder.qtyToDeliverNetPrice(qtyToDeliverNetPrice);
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

		return queryBL.createQueryBuilder(I_C_Order.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Order.COLUMNNAME_C_Order_ID, orderIds)
				.create()
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						orderRecord -> OrderId.ofRepoId(orderRecord.getC_Order_ID()),
						Function.identity()));
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
						Function.identity()));
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
					.bPartnerId(shipmentSchedule.getShipBPartnerId())
					.productId(shipmentSchedule.getProductId());

			if (shipmentSchedule.getBillBPartnerId() != null)
			{
				idsRegistryBuilder.bPartnerId(shipmentSchedule.getBillBPartnerId());
			}

			if (shipmentSchedule.getAttributeSetInstanceId() != null)
			{
				idsRegistryBuilder.asiId(shipmentSchedule.getAttributeSetInstanceId());
			}

			if (shipmentSchedule.getOrderAndLineId() != null)
			{
				idsRegistryBuilder.orderAndLineId(shipmentSchedule.getOrderAndLineId());
			}

			if (shipmentSchedule.getShipperId() != null)
			{
				idsRegistryBuilder.shipperId(shipmentSchedule.getShipperId());
			}
		}

		return idsRegistryBuilder.build();
	}

	@NonNull
	private List<ShipmentSchedule> loadShipmentSchedulesToExport(@NonNull final QueryLimit limit)
	{
		final ShipmentScheduleQuery shipmentScheduleQuery = ShipmentScheduleQuery.builder()
				.canBeExportedFrom(SystemTime.asInstant())
				.onlyIfAllFromOrderExportable(true)
				.exportStatus(APIExportStatus.Pending)
				.includeWithQtyToDeliverZero(true)
				.fromCompleteOrderOrNullOrder(true)
				.orderByOrderId(true)
				.build();

		final List<ShipmentSchedule> shipmentSchedulesOrderedByOrderId = shipmentScheduleRepository.getBy(shipmentScheduleQuery);
		final List<ShipmentSchedule> schedulesToBeExported = new ArrayList<>();

		// add possible shipmentSchedule which don't reference an order
		int counter = 0;
		for (final ShipmentSchedule schedule : shipmentSchedulesOrderedByOrderId)
		{
			if (schedule.getOrderAndLineId() == null && counter < limit.toInt() - 1)
			{
				schedulesToBeExported.add(schedule);
				counter++;
			}
			else
			{
				break;
			}
		}
		logger.info("loadShipmentSchedulesToExport - Added {} shipmentSchedules that have no C_Order_ID", schedulesToBeExported.size());
		if (counter >= limit.toInt())
		{
			return schedulesToBeExported;
		}

		// add shipmentSchedules which reference an oder
		final List<ShipmentSchedule> nonNullOrderShipmentSchedules = shipmentSchedulesOrderedByOrderId.stream()
				.filter(sched -> sched.getOrderAndLineId() != null)
				.collect(ImmutableList.toImmutableList());

		final ImmutableListMultimap<OrderId, ShipmentSchedule> schedulesForOrderIds = Multimaps.index(nonNullOrderShipmentSchedules, sched -> sched.getOrderAndLineId().getOrderId());
		int ordersLeftToExport = limit.toInt() - counter;

		final ImmutableSet<OrderId> orderIds = schedulesForOrderIds.keySet();
		logger.info("loadShipmentSchedulesToExport - the exportable shipmentSchedules have {} different C_Order_IDs; ordersLeftToExport={}", orderIds.size(), ordersLeftToExport);

		final UnmodifiableIterator<OrderId> orderIdIterator = orderIds.iterator();
		while (orderIdIterator.hasNext() && ordersLeftToExport > 0)
		{
			final OrderId currentOrderId = orderIdIterator.next();
			final ImmutableList<ShipmentSchedule> shipmentSchedules = schedulesForOrderIds.get(currentOrderId);
			schedulesToBeExported.addAll(shipmentSchedules);
			logger.info("loadShipmentSchedulesToExport - Added {} shipmentSchedules for C_Order_ID={} to the schedulesToBeExported", shipmentSchedules.size(), currentOrderId.getRepoId());
			ordersLeftToExport--;
		}

		return schedulesToBeExported;

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
			@NonNull final Map<ShipperId, String> shipperId2InternalName)
	{
		final ShipperId shipperId = shipmentSchedule.getShipperId();

		if (shipperId == null)
		{
			return;// nothing to do
		}

		candidateBuilder.shipperInternalSearchKey(shipperId2InternalName.get(shipperId));
	}

	private void setOrderReferences(
			@NonNull final JsonResponseShipmentCandidateBuilder candidateBuilder,
			@NonNull final ShipmentSchedule shipmentSchedule,
			@NonNull final Map<OrderId, I_C_Order> id2Order)
	{
		final OrderId orderId = shipmentSchedule.getOrderAndLineId() != null
				? shipmentSchedule.getOrderAndLineId().getOrderId()
				: null;

		if (orderId == null)
		{
			return; // nothing to do
		}

		final I_C_Order orderRecord = id2Order.get(orderId);

		if (orderRecord == null)
		{
			Loggables.withLogger(logger, Level.WARN).addLog("*** WARNING: No I_C_Order was found in id2Order: {} for orderId: {}!", id2Order, orderId);
			return;
		}

		candidateBuilder.orderDocumentNo(orderRecord.getDocumentNo());
		candidateBuilder.poReference(orderRecord.getPOReference());
		candidateBuilder.deliveryInfo(orderRecord.getDeliveryInfo());
	}

	private static class ShipmentCandidateExportException extends AdempiereException
	{
		public ShipmentCandidateExportException(final String message)
		{
			super(message);
		}
	}

	private void setAdditionalContactFields(
			@NonNull final JsonCustomerBuilder customerBuilder,
			@NonNull final OrderAndLineId orderAndLineId)
	{

		final I_C_Order orderRecord = orderDAO.getById(orderAndLineId.getOrderId());

		customerBuilder
				.contactEmail(orderRecord.getEMail())
				.contactName(orderRecord.getBPartnerName())
				.contactPhone(orderRecord.getPhone());

		logger.debug("Exporting effective contactEmail={}, contactName={}, contactPhone={} from the orderId={}",
					 orderRecord.getEMail(),
					 orderRecord.getBPartnerName(),
					 orderRecord.getPhone(),
					 orderAndLineId.getOrderId());

	}

	private void setAdditionalContactFields(
			@NonNull final JsonCustomerBuilder customerBuilder,
			@NonNull final BPartnerLocation bPartnerLocation)
	{
		customerBuilder
				.contactEmail(bPartnerLocation.getEmail())
				.contactName(bPartnerLocation.getBpartnerName())
				.contactPhone(bPartnerLocation.getPhone());

		logger.debug("Exporting effective contactEmail={}, contactName={}, contactPhone={} from the bPartnerLocationId={}",
					 bPartnerLocation.getEmail(),
					 bPartnerLocation.getBpartnerName(),
					 bPartnerLocation.getPhone(),
					 bPartnerLocation.getId());
	}

	private void setAdditionalContactFieldsForOxidOrder(
			@NonNull final ShipmentSchedule shipmentSchedule,
			@NonNull final BPartnerComposite composite,
			@NonNull final JsonCustomerBuilder customerBuilder,
			@Nullable final BPartnerContactId contactId)
	{
		if (contactId == null)
		{
			return;
		}

		final BPartnerContact contact = composite.extractContact(contactId)
				.orElseThrow(() -> new ShipmentCandidateExportException("Unable to get the contact info from bPartnerComposite!")
						.appendParametersToMessage()
						.setParameter("composite.C_BPartner_ID", composite.getBpartner().getId())
						.setParameter("AD_User_ID", contactId));

		customerBuilder
				.contactEmail(oxidAdaptor.getContactEmail(shipmentSchedule, composite))
				.contactName(contact.getName())
				.contactPhone(contact.getPhone());
	}
}