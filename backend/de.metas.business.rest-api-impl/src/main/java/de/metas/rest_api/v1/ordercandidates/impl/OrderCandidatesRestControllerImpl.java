package de.metas.rest_api.v1.ordercandidates.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.Profiles;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryCreateRequest;
import de.metas.attachments.AttachmentEntryId;
import de.metas.attachments.AttachmentTags;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.common.ordercandidates.v1.request.JsonOLCandCreateBulkRequest;
import de.metas.common.ordercandidates.v1.request.JsonOLCandCreateRequest;
import de.metas.common.ordercandidates.v1.response.JsonAttachment;
import de.metas.common.ordercandidates.v1.response.JsonOLCandCreateBulkResponse;
import de.metas.common.rest_api.v1.attachment.JsonAttachmentType;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.i18n.ExplainedOptional;
import de.metas.impex.InputDataSourceId;
import de.metas.logging.LogManager;
import de.metas.monitoring.adapter.PerformanceMonitoringService;
import de.metas.monitoring.adapter.PerformanceMonitoringService.SpanMetadata;
import de.metas.monitoring.adapter.PerformanceMonitoringService.Type;
import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.api.OLCand;
import de.metas.ordercandidate.api.OLCandCreateRequest;
import de.metas.ordercandidate.api.OLCandQuery;
import de.metas.ordercandidate.api.OLCandRepository;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.PricingSystemId;
import de.metas.rest_api.utils.ApiAPMHelper;
import de.metas.rest_api.utils.JsonErrors;
import de.metas.rest_api.v1.bpartner.BpartnerRestController;
import de.metas.rest_api.v1.ordercandidates.OrderCandidatesRestEndpoint;
import de.metas.rest_api.v1.ordercandidates.impl.ProductMasterDataProvider.ProductInfo;
import de.metas.security.permissions2.PermissionServiceFactories;
import de.metas.security.permissions2.PermissionServiceFactory;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.web.MetasfreshRestAPIConstants;
import de.metas.util.web.exception.MissingResourceException;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static de.metas.common.util.CoalesceUtil.coalesce;

/*
 * #%L
 * de.metas.ordercandidate.rest-api
 * %%
 * Copyright (C) 2018 metas GmbH
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
@RestController
@RequestMapping(value = {
		MetasfreshRestAPIConstants.ENDPOINT_API_DEPRECATED + "/sales/order/candidates",
		MetasfreshRestAPIConstants.ENDPOINT_API_V1 + "/sales/order/candidates"})
@Profile(Profiles.PROFILE_App)
public class OrderCandidatesRestControllerImpl implements OrderCandidatesRestEndpoint
{
	private final String PATH_BULK = "/bulk";
	
	public static final String DATA_SOURCE_INTERNAL_NAME = "SOURCE." + OrderCandidatesRestControllerImpl.class.getName();

	private static final Logger logger = LogManager.getLogger(OrderCandidatesRestControllerImpl.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final JsonConverters jsonConverters;
	private final OLCandRepository olCandRepo;
	private final BpartnerRestController bpartnerRestController;
	private final PerformanceMonitoringService perfMonService;

	private PermissionServiceFactory permissionServiceFactory;

	public OrderCandidatesRestControllerImpl(
			@NonNull final JsonConverters jsonConverters,
			@NonNull final OLCandRepository olCandRepo,
			@NonNull final BpartnerRestController bpartnerRestController,
			@NonNull final PerformanceMonitoringService perfMonService)
	{
		this.jsonConverters = jsonConverters;
		this.olCandRepo = olCandRepo;
		this.bpartnerRestController = bpartnerRestController;
		this.perfMonService = perfMonService;
		this.permissionServiceFactory = PermissionServiceFactories.currentContext();
	}

	@VisibleForTesting
	public void setPermissionServiceFactory(@NonNull final PermissionServiceFactory permissionServiceFactory)
	{
		this.permissionServiceFactory = permissionServiceFactory;
	}

	@PostMapping
	@Override
	public ResponseEntity<JsonOLCandCreateBulkResponse> createOrderLineCandidate(@RequestBody @NonNull final JsonOLCandCreateRequest request)
	{
		return createOrderLineCandidates(JsonOLCandCreateBulkRequest.of(request));
	}

	@PostMapping(PATH_BULK)
	@Override
	public ResponseEntity<JsonOLCandCreateBulkResponse> createOrderLineCandidates(@RequestBody @NonNull final JsonOLCandCreateBulkRequest bulkRequest)
	{
		try
		{
			bulkRequest.validate();

			final MasterdataProvider masterdataProvider = MasterdataProvider.builder()
					.permissionService(permissionServiceFactory.createPermissionService())
					.bpartnerRestController(bpartnerRestController)
					.build();

			final ITrxManager trxManager = Services.get(ITrxManager.class);

			// load/create/update the master data (according to SyncAdvice) in a dedicated trx.
			// because when creating the actual order line candidates, there is e.g. code invoked by model interceptors that gets AD_OrgInfo out of transaction.
			trxManager.runInNewTrx(() -> createOrUpdateMasterdataBulk(bulkRequest, masterdataProvider));
			// the required masterdata should be there now

			// invoke creatOrderLineCandidates with the unchanged bulkRequest, because the request's bpartner and product instances are
			// (at least currently) part of the respective caching keys.
			final JsonOLCandCreateBulkResponse //
					response = trxManager.callInNewTrx(() -> creatOrderLineCandidatesBulk(bulkRequest, masterdataProvider));

			//
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		}
		catch (final Exception ex)
		{
			logger.warn("Got exception while processing {}", bulkRequest, ex);

			final String adLanguage = Env.getADLanguageOrBaseLanguage();
			return ResponseEntity.badRequest()
					.body(JsonOLCandCreateBulkResponse.error(JsonErrors.ofThrowable(ex, adLanguage)));
		}
	}

	private void assertCanCreate(
			@NonNull final JsonOLCandCreateRequest request,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		final OrgId orgId = masterdataProvider.getCreateOrgIdInTrx(request.getOrg());
		masterdataProvider.assertCanCreateNewOLCand(orgId);
	}

	private void createOrUpdateMasterdataBulk(
			@NonNull final JsonOLCandCreateBulkRequest bulkRequest,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		perfMonService.monitorSpan(
				() -> bulkRequest.getRequests()
						.stream()
						.forEach(request -> createOrUpdateMasterdata(request, masterdataProvider)),
				ApiAPMHelper.createMetadataFor("CreateOrUpdateMasterDataBulk"));
	}

	private void createOrUpdateMasterdata(
			@NonNull final JsonOLCandCreateRequest json,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		try (final MDCCloseable extHeaderMDC = MDC.putCloseable("externalHeaderId", json.getExternalHeaderId());
				final MDCCloseable extLineMDC = MDC.putCloseable("externalLineId", json.getExternalLineId()))
		{
			final SpanMetadata spanMetadata = SpanMetadata.builder()
					.name("CreateOrUpdateMasterDataSingle")
					.type(Type.REST_API_PROCESSING.getCode())
					.label("externalHeaderId", json.getExternalHeaderId())
					.label("externalLineId", json.getExternalLineId())
					.build();

			perfMonService.monitorSpan(
					() -> createOrUpdateMasterdata0(json, masterdataProvider),
					spanMetadata);
		}
	}

	private void createOrUpdateMasterdata0(
			@NonNull final JsonOLCandCreateRequest json,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		final OrgId orgId = masterdataProvider.getCreateOrgIdInTrx(json.getOrg());

		final BPartnerInfo bpartnerInfo = masterdataProvider.getCreateBPartnerInfoInTrx(json.getBpartner(), true/* billTo */, orgId);
		final BPartnerInfo billBPartnerInfo = masterdataProvider.getCreateBPartnerInfoInTrx(json.getBillBPartner(), true/* billTo */, orgId);
		masterdataProvider.getCreateBPartnerInfoInTrx(json.getDropShipBPartner(), false/* billTo */, orgId);
		masterdataProvider.getCreateBPartnerInfoInTrx(json.getHandOverBPartner(), false/* billTo */, orgId);

		final ProductInfo productInfo = masterdataProvider.getCreateProductInfo(json.getProduct(), orgId);

		// Create product prices if needed
		{
			final BPartnerInfo billBPartnerInfoEffective = CoalesceUtil.coalesce(billBPartnerInfo, bpartnerInfo);

			final ExplainedOptional<ProductPriceCreateRequest> optionalRequest = createProductPriceCreateRequest(
					masterdataProvider,
					json,
					billBPartnerInfoEffective,
					productInfo);
			if (optionalRequest.isPresent())
			{
				masterdataProvider.createProductPrice(optionalRequest.get());
			}
			else
			{
				logger.debug("Skip creating product price for {} because {}", productInfo, optionalRequest.getExplanation());
			}
		}
	}

	private ExplainedOptional<ProductPriceCreateRequest> createProductPriceCreateRequest(
			@NonNull final MasterdataProvider masterdataProvider,
			@NonNull final JsonOLCandCreateRequest json,
			@NonNull final BPartnerInfo bpartnerInfo,
			@NonNull final ProductInfo productInfo)
	{
		if (!productInfo.isJustCreated())
		{
			return ExplainedOptional.emptyBecause("product was already created");
		}

		final BigDecimal priceStd = json.getProduct().getPriceStd();
		if (priceStd == null)
		{
			return ExplainedOptional.emptyBecause("priceStd was not specified");
		}

		final BPartnerLocationId bpartnerAndLocationId = bpartnerInfo.getBpartnerLocationId();
		if (bpartnerAndLocationId == null)
		{
			throw new AdempiereException("@NotFound@ @C_BPartner_Location_ID@");
		}
		final OrgId orgId = masterdataProvider.getCreateOrgIdInTrx(json.getOrg());
		final ZoneId timeZone = orgDAO.getTimeZone(orgId);

		final ZonedDateTime dateEffective = CoalesceUtil.coalesceSuppliers(
				() -> TimeUtil.asZonedDateTime(json.getDateRequired(), timeZone),
				() -> TimeUtil.asZonedDateTime(json.getDateOrdered(), timeZone),
				() -> SystemTime.asZonedDateTime());

		final PricingSystemId pricingSystemId = masterdataProvider.getPricingSystemIdByValue(json.getPricingSystemCode());

		final ProductPriceCreateRequest request = ProductPriceCreateRequest.builder()
				.bpartnerAndLocationId(bpartnerAndLocationId)
				.pricingSystemId(pricingSystemId)
				.date(dateEffective)
				.productId(productInfo.getProductId())
				.uomId(productInfo.getUomId())
				.priceStd(priceStd)
				.build();

		return ExplainedOptional.of(request);
	}

	private JsonOLCandCreateBulkResponse creatOrderLineCandidatesBulk(
			@NonNull final JsonOLCandCreateBulkRequest bulkRequest,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		final SpanMetadata spanMetadata = SpanMetadata.builder()
				.name("CreatOrderLineCandidatesBulk")
				.type(Type.REST_API_PROCESSING.getCode())
				.build();

		return perfMonService.monitorSpan(
				() -> creatOrderLineCandidates0(bulkRequest, masterdataProvider),
				spanMetadata);
	}

	private JsonOLCandCreateBulkResponse creatOrderLineCandidates0(
			@NonNull final JsonOLCandCreateBulkRequest bulkRequest,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		final List<OLCandCreateRequest> requests = bulkRequest
				.getRequests()
				.stream()
				.peek(request -> assertCanCreate(request, masterdataProvider))
				.map(request -> fromJson(request, masterdataProvider))
				.collect(ImmutableList.toImmutableList());

		final List<OLCand> olCands = olCandRepo.create(requests);
		return jsonConverters.toJson(olCands, masterdataProvider);
	}

	private OLCandCreateRequest fromJson(
			@NonNull final JsonOLCandCreateRequest request,
			@NonNull final MasterdataProvider masterdataProvider)
	{
		final String dataSourceInternalNameToUse = coalesce(
				request.getDataSource(),
				"int-" + DATA_SOURCE_INTERNAL_NAME);

		final InputDataSourceId dataSourceId = masterdataProvider.getDataSourceId(
				dataSourceInternalNameToUse,
				masterdataProvider.getCreateOrgIdInTrx(request.getOrg()));
		if (dataSourceId == null)
		{
			throw MissingResourceException.builder()
					.resourceName("dataSource")
					.resourceIdentifier(dataSourceInternalNameToUse)
					.parentResource(request).build();
		}

		return jsonConverters
				.fromJson(request, masterdataProvider)
				.dataSourceId(dataSourceId)
				.build();
	}

	@PostMapping("/{dataSourceName}/{externalHeaderId}/attachments")
	@Override
	public ResponseEntity<JsonAttachment> attachFile(
			@PathVariable("dataSourceName") final String dataSourceName,

			@ApiParam(required = true, value = "`externalheaderId` of the order line candidates to which the given file shall be attached") //
			@PathVariable("externalHeaderId") final String externalHeaderId,

			@ApiParam(value = "List with an even number of items;\n"
					+ "transformed to a map of key-value pairs and added to the new attachment as tags.\n"
					+ "If the number of items is odd, the last item is discarded.", allowEmptyValue = true) //
			@RequestParam("tags") //
			@Nullable final List<String> tagKeyValuePairs,

			@ApiParam(required = true, value = "The file to attach; the attachment's MIME type will be determined from the file extenstion", allowEmptyValue = false) //
			@RequestBody @NonNull final MultipartFile file) throws IOException
	{
		final IOLCandBL olCandsService = Services.get(IOLCandBL.class);

		final OLCandQuery query = OLCandQuery
				.builder()
				.inputDataSourceName(dataSourceName)
				.externalHeaderId(externalHeaderId)
				.build();

		final String fileName = file.getOriginalFilename();
		final byte[] data = file.getBytes();
		final AttachmentTags attachmentTags = AttachmentTags.builder().tags(extractTags(tagKeyValuePairs)).build();

		final AttachmentEntryCreateRequest request = AttachmentEntryCreateRequest
				.builderFromByteArray(fileName, data)
				.tags(attachmentTags)
				.build();

		final AttachmentEntry attachmentEntry = olCandsService.addAttachment(query, request);

		final JsonAttachment jsonAttachment = toJsonAttachment(
				externalHeaderId,
				dataSourceName,
				attachmentEntry);
		return new ResponseEntity<>(jsonAttachment, HttpStatus.CREATED);
	}

	@VisibleForTesting
	static ImmutableMap<String, String> extractTags(@Nullable final List<String> tagKeyValuePairs)
	{
		if (tagKeyValuePairs == null)
		{
			return ImmutableMap.of();
		}
		final ImmutableMap.Builder<String, String> tags = ImmutableMap.builder();

		final int listSize = tagKeyValuePairs.size();
		final int maxIndex = listSize % 2 == 0 ? listSize : listSize - 1;

		for (int i = 0; i < maxIndex; i += 2)
		{
			tags.put(tagKeyValuePairs.get(i), tagKeyValuePairs.get(i + 1));
		}
		return tags.build();
	}

	@VisibleForTesting
	static JsonAttachment toJsonAttachment(
			@NonNull final String externalReference,
			@NonNull final String dataSourceName,
			@NonNull final AttachmentEntry entry)
	{
		final AttachmentEntryId entryId = Check.assumeNotNull(entry.getId(), "Param 'entry' needs to have a non-null id; entry={}", entry);
		final String attachmentId = Integer.toString(entryId.getRepoId());

		final JsonAttachmentType attachmentType = JsonAttachmentType
				.valueOf(entry.getType().toString());

		return JsonAttachment.builder()
				.externalReference(externalReference)
				.dataSourceName(dataSourceName)
				.attachmentId(attachmentId)
				.type(attachmentType)
				.filename(entry.getFilename())
				.mimeType(entry.getMimeType())
				.url(entry.getUrl() != null ? entry.getUrl().toString() : null)
				.build();
	}
}
