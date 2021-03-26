package de.metas.rest_api.order.impl;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryId;
import de.metas.attachments.AttachmentEntryService;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.rest_api.v1.attachment.JsonAttachmentType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.order.OrderFactory;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.rest_api.order.JsonSalesOrder;
import de.metas.rest_api.order.JsonSalesOrderAttachment;
import de.metas.rest_api.order.JsonSalesOrderCreateRequest;
import de.metas.rest_api.order.JsonSalesOrderLine;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/*
 * #%L
 * de.metas.business
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
		MetasfreshRestAPIConstants.ENDPOINT_API_DEPRECATED + "/sales/order",
		MetasfreshRestAPIConstants.ENDPOINT_API_V1 + "/sales/order",
		MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/orders/sales" })
@Profile(Profiles.PROFILE_App)
public class SalesOrderRestController
{
	private final AttachmentEntryService attachmentEntryService;

	public SalesOrderRestController(@NonNull final AttachmentEntryService attachmentEntryService)
	{
		this.attachmentEntryService = attachmentEntryService;
	}

	@PostMapping
	public JsonSalesOrder createOrder(@RequestBody final JsonSalesOrderCreateRequest request)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		return trxManager.call(ITrx.TRXNAME_ThreadInherited, () -> createOrder0(request));
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

	private JsonSalesOrder toSalesOrder(final I_C_Order salesOrderRecord)
	{
		return JsonSalesOrder.builder()
				.salesOrderId(String.valueOf(salesOrderRecord.getC_Order_ID()))
				.build();
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

	private JsonSalesOrderAttachment toSalesOrderAttachment(final int salesOrderId, final AttachmentEntry entry)
	{
		return JsonSalesOrderAttachment.builder()
				.salesOrderId(String.valueOf(salesOrderId))
				.id(AttachmentEntryId.getRepoId(entry.getId()))
				.type(JsonAttachmentType.valueOf(entry.getType().toString()))
				.filename(entry.getFilename())
				.mimeType(entry.getMimeType())
				.url(entry.getUrl() != null ? entry.getUrl().toString() : null)
				.build();
	}
}
