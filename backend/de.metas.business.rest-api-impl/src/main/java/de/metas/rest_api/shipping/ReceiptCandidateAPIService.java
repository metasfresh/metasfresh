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
import de.metas.common.shipping.receiptcandidate.JsonResponseReceiptCandidate;
import de.metas.common.shipping.receiptcandidate.JsonResponseReceiptCandidate.JsonResponseReceiptCandidateBuilder;
import de.metas.common.shipping.receiptcandidate.JsonResponseReceiptCandidates;
import de.metas.common.shipping.receiptcandidate.JsonResponseReceiptCandidates.JsonResponseReceiptCandidatesBuilder;
import de.metas.common.shipping.receiptcandidate.JsonVendor;
import de.metas.common.shipping.receiptcandidate.JsonVendor.JsonVendorBuilder;
import de.metas.common.util.CoalesceUtil;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.error.IssueCreateRequest;
import de.metas.inoutcandidate.ReceiptSchedule;
import de.metas.inoutcandidate.ReceiptScheduleId;
import de.metas.inoutcandidate.ReceiptScheduleRepository;
import de.metas.inoutcandidate.ReceiptScheduleRepository.ReceiptScheduleQuery;
import de.metas.inoutcandidate.exportaudit.APIExportAudit;
import de.metas.inoutcandidate.exportaudit.APIExportAudit.APIExportAuditBuilder;
import de.metas.inoutcandidate.exportaudit.APIExportStatus;
import de.metas.inoutcandidate.exportaudit.ReceiptScheduleAuditRepository;
import de.metas.inoutcandidate.exportaudit.ReceiptScheduleExportAuditItem;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.order.OrderId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.Product;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import de.metas.quantity.Quantity;
import de.metas.rest_api.utils.RestApiUtils;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.compiere.model.I_C_Order;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import static de.metas.inoutcandidate.exportaudit.APIExportStatus.ExportedAndError;
import static de.metas.inoutcandidate.exportaudit.APIExportStatus.ExportedAndForwarded;
import static de.metas.inoutcandidate.exportaudit.APIExportStatus.Pending;

@Service
class ReceiptCandidateAPIService
{
	private final static transient Logger logger = LogManager.getLogger(ReceiptCandidateAPIService.class);

	private final ReceiptScheduleAuditRepository receiptScheduleAuditRepository;
	private final ReceiptScheduleRepository receiptScheduleRepository;
	private final BPartnerCompositeRepository bPartnerCompositeRepository;
	private final ProductRepository productRepository;

	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IErrorManager errorManager = Services.get(IErrorManager.class);

	public ReceiptCandidateAPIService(
			@NonNull final ReceiptScheduleAuditRepository receiptScheduleAuditRepository,
			@NonNull final ReceiptScheduleRepository receiptScheduleRepository,
			@NonNull final BPartnerCompositeRepository bPartnerCompositeRepository,
			@NonNull final ProductRepository productRepository)
	{
		this.receiptScheduleAuditRepository = receiptScheduleAuditRepository;
		this.receiptScheduleRepository = receiptScheduleRepository;
		this.bPartnerCompositeRepository = bPartnerCompositeRepository;
		this.productRepository = productRepository;
	}

	/**
	 * Exports them; Flags them as "exported - don't touch"; creates an export audit table with one line per shipment schedule.
	 */
	public JsonResponseReceiptCandidates exportReceiptCandidates(final int limit)
	{
		final String transactionId = UUID.randomUUID().toString();
		try (final MDC.MDCCloseable ignore = MDC.putCloseable("TransactionIdAPI", transactionId))
		{
			final APIExportAuditBuilder<ReceiptScheduleExportAuditItem> auditBuilder = APIExportAudit
					.<ReceiptScheduleExportAuditItem>builder()
					.transactionId(transactionId);

			final ReceiptScheduleQuery receiptScheduleQuery = ReceiptScheduleQuery.builder()
					.limit(limit)
					.canBeExportedFrom(Instant.now())
					.exportStatus(APIExportStatus.Pending)
					.build();
			final List<ReceiptSchedule> receiptSchedules = receiptScheduleRepository.getBy(receiptScheduleQuery);
			if (receiptSchedules.isEmpty())
			{ // return empty result and call it a day
				return JsonResponseReceiptCandidates.builder().hasMoreItems(false).transactionKey(transactionId).build();
			}

			final IdsRegistry.IdsRegistryBuilder idsRegistryBuilder = IdsRegistry.builder();
			for (final ReceiptSchedule receiptSchedule : receiptSchedules)
			{
				idsRegistryBuilder
						.receiptScheduleId(receiptSchedule.getId())
						.asiId(receiptSchedule.getAttributeSetInstanceId())
						.orderId(receiptSchedule.getOrderId())
						.productId(receiptSchedule.getProductId())
						.bPartnerId(receiptSchedule.getVendorId());
			}
			final IdsRegistry idsRegistry = idsRegistryBuilder.build();

			final Map<AttributeSetInstanceId, ImmutableAttributeSet> attributesForASIs;
			if (idsRegistry.getAsiIds().isEmpty())
			{
				attributesForASIs = ImmutableMap.of();
			}
			else
			{
				attributesForASIs = attributeDAO.getAttributesForASIs(idsRegistry.getAsiIds());
			}

			final JsonResponseReceiptCandidatesBuilder result = JsonResponseReceiptCandidates.builder()
					.hasMoreItems(receiptSchedules.size() == limit)
					.transactionKey(transactionId);

			final ImmutableMap<OrderId, I_C_Order> orderIdToOrderRecord = queryBL.createQueryBuilder(I_C_Order.class)
					.addOnlyActiveRecordsFilter()
					.addInArrayFilter(I_C_Order.COLUMNNAME_C_Order_ID, idsRegistry.getOrderIds())
					.create()
					.stream()
					.collect(ImmutableMap.toImmutableMap(
							orderRecord -> OrderId.ofRepoId(orderRecord.getC_Order_ID()),
							Function.identity()
					));

			final ImmutableMap<BPartnerId, BPartnerComposite> bpartnerIdToBPartner = Maps.uniqueIndex(
					bPartnerCompositeRepository.getByIds(idsRegistry.getBPartnerIds()),
					bp -> bp.getBpartner().getId());

			final ImmutableMap<ProductId, Product> productId2Product = Maps.uniqueIndex(productRepository.getByIds(idsRegistry.getProductIds()), Product::getId);

			for (final ReceiptSchedule receiptSchedule : receiptSchedules)
			{
				try (final MDC.MDCCloseable ignore1 = TableRecordMDC.putTableRecordReference(I_M_ReceiptSchedule.Table_Name, receiptSchedule.getId()))
				{
					final JsonAttributeSetInstance jsonAttributeSetInstance = createJsonASI(receiptSchedule, attributesForASIs);
					final JsonVendor vendor = createJsonVendor(receiptSchedule, bpartnerIdToBPartner);
					final JsonProduct product = createJsonProduct(receiptSchedule, vendor.getLanguage(), productId2Product);
					final I_C_Order orderRecord = orderIdToOrderRecord.get(receiptSchedule.getOrderId());
					final Quantity quantity = receiptSchedule.getQuantityToDeliver();
					final List<JsonQuantity> quantities = createJsonQuantities(quantity);

					final JsonResponseReceiptCandidateBuilder itemBuilder = JsonResponseReceiptCandidate.builder()
							.id(JsonMetasfreshId.of(receiptSchedule.getId().getRepoId()))
							.orgCode(orgDAO.retrieveOrgValue(receiptSchedule.getOrgId()))
							.product(product)
							.attributeSetInstance(jsonAttributeSetInstance)
							.quantities(quantities)
							.dateOrdered(receiptSchedule.getDateOrdered());
					if (orderRecord != null)
					{
						itemBuilder
								.orderDocumentNo(orderRecord.getDocumentNo())
								.poReference(orderRecord.getPOReference());
					}

					result.item(itemBuilder.build());
					createExportedAuditItem(receiptSchedule, auditBuilder);
				}
				catch (final ReceiptCandidateExportException e) // don't catch just any exception; just the "functional" ones
				{
					createExportErrorAuditItem(receiptSchedule, e, auditBuilder);
				}
			}

			receiptScheduleAuditRepository.save(auditBuilder.build());
			receiptScheduleRepository.exportStatusMassUpdate(idsRegistry.getReceiptScheduleIds(), APIExportStatus.Exported);

			return result.build();
		}
	}

	@NonNull
	private ImmutableList<JsonQuantity> createJsonQuantities(@NonNull final Quantity quantity)
	{
		return ImmutableList.of(JsonQuantity.builder().qty(quantity.toBigDecimal()).uomCode(quantity.getUOMSymbol()).build());
	}

	private JsonVendor createJsonVendor(
			@NonNull final ReceiptSchedule receiptSchedule,
			@NonNull final ImmutableMap<BPartnerId, BPartnerComposite> bpartnerIdToBPartner)
	{
		final BPartnerComposite composite = bpartnerIdToBPartner.get(receiptSchedule.getVendorId());

		final BPartner bpartner = composite.getBpartner();

		final String adLanguage = bpartner.getLanguage() != null ? bpartner.getLanguage().getAD_Language() : Env.getAD_Language();

		final JsonVendorBuilder vendorBuilder = JsonVendor.builder()
				.companyName(CoalesceUtil.coalesce(bpartner.getCompanyName(), bpartner.getName()))
				.language(adLanguage);

		return vendorBuilder.build();
	}

	private JsonProduct createJsonProduct(
			@NonNull final ReceiptSchedule receiptSchedule,
			@NonNull final String adLanguage,
			@NonNull final ImmutableMap<ProductId, Product> productId2Product)
	{
		final Product product = productId2Product.get(receiptSchedule.getProductId());

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
			@NonNull final ReceiptSchedule receiptSchedule,
			@NonNull final Map<AttributeSetInstanceId, ImmutableAttributeSet> attributesForASIs)
	{
		final AttributeSetInstanceId scheduleASIId = receiptSchedule.getAttributeSetInstanceId();
		if (!AttributeSetInstanceId.isRegular(scheduleASIId))
		{
			return null;
		}
		final ImmutableAttributeSet attributeSet = attributesForASIs.get(scheduleASIId);
		return RestApiUtils.extractJsonAttributeSetInstance(attributeSet, receiptSchedule.getOrgId());
	}

	private void createExportedAuditItem(
			@NonNull final ReceiptSchedule receiptSchedule,
			@NonNull final APIExportAuditBuilder<ReceiptScheduleExportAuditItem> auditBuilder)
	{
		final OrgId orgId = receiptSchedule.getOrgId();

		auditBuilder.item(
				receiptSchedule.getId(),
				ReceiptScheduleExportAuditItem.builder()
						.exportStatus(APIExportStatus.Exported)
						.repoIdAware(receiptSchedule.getId())
						.orgId(orgId)
						.build());
	}

	private void createExportErrorAuditItem(
			@NonNull final ReceiptSchedule receiptSchedule,
			@NonNull final ReceiptCandidateExportException e,
			@NonNull final APIExportAuditBuilder<ReceiptScheduleExportAuditItem> auditBuilder)
	{
		final OrgId orgId = receiptSchedule.getOrgId();

		final AdIssueId adIssueId = Services.get(IErrorManager.class).createIssue(IssueCreateRequest.builder()
				.throwable(e)
				.loggerName(logger.getName())
				.sourceClassname(ReceiptCandidateAPIService.class.getName())
				.summary(e.getMessage())
				.build());

		auditBuilder.item(
				receiptSchedule.getId(),
				ReceiptScheduleExportAuditItem.builder()
						.exportStatus(APIExportStatus.ExportError)
						.repoIdAware(receiptSchedule.getId())
						.issueId(adIssueId)
						.orgId(orgId)
						.build());
	}

	@Value
	@Builder
	private static class IdsRegistry
	{
		@Singular
		Set<ReceiptScheduleId> receiptScheduleIds;

		@Singular
		Set<AttributeSetInstanceId> asiIds;

		@Singular
		Set<OrderId> orderIds;

		@Singular
		Set<BPartnerId> bPartnerIds;

		@Singular
		Set<ProductId> productIds;
	}

	/**
	 * Use the given pojo's transactionKey to load the respective export audit table and update its lines
	 */
	public void updateStatus(@NonNull final JsonRequestCandidateResults results)
	{
		final AdIssueId generalAdIssueId = createADIssue(results.getError());
		if (generalAdIssueId != null)
		{
			logger.debug("Created AD_Issue_ID={} that applies to all exported receipt schedules", generalAdIssueId.getRepoId());
		}

		if (results.getItems().isEmpty())
		{
			logger.debug("given results is empty; -> return");
			return;
		}
		final APIExportAudit<ReceiptScheduleExportAuditItem> audit = receiptScheduleAuditRepository.getByTransactionId(results.getTransactionKey());
		if (audit == null)
		{
			logger.debug("Given results.transactionKey={} does not match any audit records; -> return", results.getTransactionKey());
			return;
		}
		final ImmutableSet<ReceiptScheduleId> receiptScheduleIds = CollectionUtils.extractDistinctElementsIntoSet(
				results.getItems(),
				item -> ReceiptScheduleId.ofRepoId(item.getScheduleId().getValue()));
		final ImmutableMap<ReceiptScheduleId, ReceiptSchedule> receiptSchedules = receiptScheduleRepository.getByIds(receiptScheduleIds);

		for (final JsonRequestCandidateResult resultItem : results.getItems())
		{
			final ReceiptScheduleId receiptScheduleId = ReceiptScheduleId.ofRepoId(resultItem.getScheduleId().getValue());
			final ReceiptSchedule receiptSchedule = receiptSchedules.get(receiptScheduleId);
			if (receiptSchedule == null)
			{
				continue; // also shouldn't happen, unless we do some API-testing with static JSON stuff
			}

			ReceiptScheduleExportAuditItem auditItem = audit.getItemById(receiptScheduleId);
			if (auditItem == null) // should not happen, but we don't want to make a fuzz in case it does
			{
				auditItem = ReceiptScheduleExportAuditItem.builder()
						.orgId(receiptSchedule.getOrgId())
						.repoIdAware(receiptScheduleId)
						.exportStatus(Pending)
						.build();
				audit.addItem(auditItem);
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
					auditItem.setIssueId(CoalesceUtil.coalesce(specificAdIssueId, generalAdIssueId));
					break;
				default:
					throw new AdempiereException("resultItem has unexpected outcome=" + resultItem.getOutcome())
							.setParameter("TransactionIdAPI", results.getTransactionKey())
							.setParameter("resultItem", resultItem);
			}

			auditItem.setExportStatus(status);
			receiptSchedule.setExportStatus(status);
		}
		receiptScheduleRepository.saveAll(receiptSchedules.values());
		receiptScheduleAuditRepository.save(audit);
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

	private static class ReceiptCandidateExportException extends AdempiereException
	{
		public ReceiptCandidateExportException(final String message)
		{
			super(message);
		}
	}
}
