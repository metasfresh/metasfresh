/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.rest_api.v2.order.sales;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryId;
import de.metas.attachments.AttachmentEntryService;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.rest_api.v1.JsonError;
import de.metas.common.rest_api.v2.order.JsonOrderPaymentCreateRequest;
import de.metas.common.rest_api.v2.order.JsonOrderRevertRequest;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.logging.LogManager;
import de.metas.order.OrderFactory;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.rest_api.utils.JsonErrors;
import de.metas.rest_api.v2.bpartner.BpartnerRestController;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonRetrieverService;
import de.metas.rest_api.v2.bpartner.bpartnercomposite.JsonServiceFactory;
import de.metas.rest_api.v2.order.JsonSalesOrder;
import de.metas.rest_api.v2.order.JsonSalesOrderAttachment;
import de.metas.rest_api.v2.order.JsonSalesOrderCreateRequest;
import de.metas.rest_api.v2.order.JsonSalesOrderLine;
import de.metas.rest_api.v2.ordercandidates.impl.MasterdataProvider;
import de.metas.rest_api.v2.util.JsonConverters;
import de.metas.security.permissions2.PermissionServiceFactories;
import de.metas.security.permissions2.PermissionServiceFactory;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/orders/sales")
@Profile(Profiles.PROFILE_App)
public class SalesOrderRestController
{
	private static final Logger logger = LogManager.getLogger(SalesOrderRestController.class);
	private final OrderService orderService;
	private final AttachmentEntryService attachmentEntryService;
	private final BpartnerRestController bpartnerRestController;
	private final ExternalReferenceRestControllerService externalReferenceRestControllerService;
	private final JsonRetrieverService jsonRetrieverService;
	private final PermissionServiceFactory permissionServiceFactory = PermissionServiceFactories.currentContext();

	public SalesOrderRestController(
			@NonNull final OrderService orderService,
			@NonNull final AttachmentEntryService attachmentEntryService,
			@NonNull final JsonServiceFactory jsonServiceFactory,
			@NonNull final BpartnerRestController bpartnerRestController,
			@NonNull final ExternalReferenceRestControllerService externalReferenceRestControllerService)
	{
		this.orderService = orderService;
		this.attachmentEntryService = attachmentEntryService;
		this.jsonRetrieverService = jsonServiceFactory.createRetriever();
		this.bpartnerRestController = bpartnerRestController;
		this.externalReferenceRestControllerService = externalReferenceRestControllerService;
	}

	@ApiOperation("Create new order payment")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully created new order payment"),
			@ApiResponse(code = 401, message = "You are not authorized to create a new order payment"),
			@ApiResponse(code = 403, message = "Accessing a related resource is forbidden"),
			@ApiResponse(code = 422, message = "The request body could not be processed")
	})
	@PostMapping("/payment")
	public ResponseEntity<?> createOrderPayment(@NonNull @RequestBody final JsonOrderPaymentCreateRequest request)
	{
		try
		{
			orderService.createOrderPayment(request);

			return ResponseEntity.ok().build();
		}
		catch (final Exception ex)
		{
			logger.error(ex.getMessage(), ex);

			final String adLanguage = Env.getADLanguageOrBaseLanguage();

			return ResponseEntity.unprocessableEntity()
					.body(JsonErrors.ofThrowable(ex, adLanguage));
		}
	}

	@PostMapping
	public JsonSalesOrder createOrder(@RequestBody final JsonSalesOrderCreateRequest request)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		return trxManager.call(ITrx.TRXNAME_ThreadInherited, () -> createOrder0(request));
	}

	@GetMapping("/{salesOrderId}/attachments")
	public List<JsonSalesOrderAttachment> getAttachments(@PathVariable("salesOrderId") final String salesOrderIdStr)
	{
		final int salesOrderId = Integer.parseInt(salesOrderIdStr);
		final TableRecordReference salesOrderRef = TableRecordReference.of(I_C_Order.Table_Name, salesOrderId);

		return attachmentEntryService.getByReferencedRecord(salesOrderRef)
				.stream()
				.map(entry -> toSalesOrderAttachment(salesOrderId, entry))
				.collect(ImmutableList.toImmutableList());
	}

	@PostMapping("/{salesOrderId}/attachments")
	public JsonSalesOrderAttachment attachFile(
			@PathVariable("salesOrderId") final String salesOrderIdStr,
			@RequestParam("file") @NonNull final MultipartFile file)
			throws IOException
	{
		final int salesOrderId = Integer.parseInt(salesOrderIdStr);
		final TableRecordReference salesOrderRef = TableRecordReference.of(I_C_Order.Table_Name, salesOrderId);

		final String name = file.getOriginalFilename();
		final byte[] data = file.getBytes();

		final AttachmentEntry entry = attachmentEntryService.createNewAttachment(salesOrderRef, name, data);
		return toSalesOrderAttachment(salesOrderId, entry);
	}

	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "PDF retrieved for order"),
			@ApiResponse(code = 401, message = "You are not authorized to see the order PDF"),
			@ApiResponse(code = 404, message = "No archive found for the order")
	})
	@GetMapping(path = "/{orderId}/pdf")
	public ResponseEntity<byte[]> getOrderPDF(
			@ApiParam(required = true, value = "metasfreshId of the order to get the PDF of") //
			@PathVariable("orderId") final int orderRecordId)
	{
		return Optional.ofNullable(OrderId.ofRepoIdOrNull(orderRecordId))
				.flatMap(orderService::getOrderPDF)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping(path = "/{orderId}/revert")
	public ResponseEntity<?> revertOrder(
			@PathVariable("orderId") @ApiParam(required = true, value = "metasfreshId of the order to revert") final int orderRecordId)
	{
		try
		{
			return Optional.ofNullable(OrderId.ofRepoIdOrNull(orderRecordId))
					.map(orderService::reverseOrder)
					.map(SalesOrderRestController::toSalesOrder)
					.map(ResponseEntity::ok)
					.orElseGet(() -> ResponseEntity.notFound().build());
		}
		catch (final Exception ex)
		{
			final JsonError error = JsonError.ofSingleItem(JsonErrors.ofThrowable(ex, Env.getADLanguageOrBaseLanguage()));

			return ResponseEntity.unprocessableEntity().body(error);
		}
	}

	@PutMapping(path = "/revert")
	public ResponseEntity<?> revertOrder(@NonNull @RequestBody final JsonOrderRevertRequest request)
	{
		try
		{
			final MasterdataProvider masterdataProvider = MasterdataProvider.builder()
					.permissionService(permissionServiceFactory.createPermissionService())
					.bpartnerRestController(bpartnerRestController)
					.externalReferenceRestControllerService(externalReferenceRestControllerService)
					.jsonRetrieverService(jsonRetrieverService)
					.build();

			return orderService.getOrderId(request, masterdataProvider)
					.map(orderService::reverseOrder)
					.map(SalesOrderRestController::toSalesOrder)
					.map(ResponseEntity::ok)
					.orElseGet(() -> ResponseEntity.notFound().build());
		}
		catch (final Exception ex)
		{
			final JsonError error = JsonError.ofSingleItem(JsonErrors.ofThrowable(ex, Env.getADLanguageOrBaseLanguage()));

			return ResponseEntity.unprocessableEntity().body(error);
		}
	}

	private JsonSalesOrder createOrder0(@RequestBody final JsonSalesOrderCreateRequest request)
	{
		final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);

		final OrderFactory salesOrderFactory = OrderFactory.newSalesOrder();

		if (!Check.isEmpty(request.getDocTypeName(), true))
		{
			final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
			final DocTypeQuery query = DocTypeQuery.builder()
					.docBaseType(X_C_DocType.DOCBASETYPE_SalesOrder)
					.adClientId(Env.getAD_Client_ID())
					.name(request.getDocTypeName())
					.build();
			final DocTypeId docTypeId = docTypeDAO.getDocTypeId(query);
			salesOrderFactory.docType(docTypeId);
		}

		final BPartnerQuery query = BPartnerQuery.builder()
				.bpartnerValue(request.getShipBPartnerCode())
				.onlyOrgId(OrgId.ANY)
				.onlyOrgId(Env.getOrgId())
				.failIfNotExists(true)
				.build();

		final BPartnerId shipBPartnerId = bpartnersRepo
				.retrieveBPartnerIdBy(query)
				.get()/* bc failIfNotExists(true) */;

		salesOrderFactory.shipBPartner(shipBPartnerId);

		salesOrderFactory.datePromised(request.getDatePromised());

		request.getLines().forEach(line -> createOrderLine(salesOrderFactory, line));

		final I_C_Order salesOrderRecord = salesOrderFactory.createAndComplete();

		return toSalesOrder(salesOrderRecord);
	}

	private void createOrderLine(final OrderFactory salesOrderFactory, final JsonSalesOrderLine salesOrderLine)
	{
		final IProductDAO productsRepo = Services.get(IProductDAO.class);
		final IProductBL productBL = Services.get(IProductBL.class);

		final ProductId productId = productsRepo.retrieveProductIdByValue(salesOrderLine.getProductCode());
		if (productId == null)
		{
			throw new AdempiereException("@NotFound@ M_Product_ID@ (@Value=" + salesOrderLine.getProductCode() + ")");
		}

		final I_C_UOM uom = productBL.getStockUOM(productId);
		final Quantity qty = Quantity.of(salesOrderLine.getQty(), uom);

		salesOrderFactory.newOrderLine()
				.productId(productId)
				.addQty(qty)
				.manualPrice(salesOrderLine.getPrice());
	}

	@NonNull
	private static JsonSalesOrder toSalesOrder(@NonNull final I_C_Order salesOrderRecord)
	{
		return JsonSalesOrder.builder()
				.salesOrderId(String.valueOf(salesOrderRecord.getC_Order_ID()))
				.documentNo(salesOrderRecord.getDocumentNo())
				.build();
	}

	private JsonSalesOrderAttachment toSalesOrderAttachment(final int salesOrderId, final AttachmentEntry entry)
	{
		return JsonSalesOrderAttachment.builder()
				.salesOrderId(String.valueOf(salesOrderId))
				.id(AttachmentEntryId.getRepoId(entry.getId()))
				.type(JsonConverters.toJsonAttachmentSourceType(entry.getType()))
				.filename(entry.getFilename())
				.mimeType(entry.getMimeType())
				.url(entry.getUrl() != null ? entry.getUrl().toString() : null)
				.build();
	}

}
