package de.metas.order.rest.controller;

import java.io.IOException;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
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

import com.google.common.collect.ImmutableList;

import de.metas.Profiles;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryId;
import de.metas.attachments.IAttachmentBL;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.order.OrderFactory;
import de.metas.order.rest.model.JsonSalesOrder;
import de.metas.order.rest.model.JsonSalesOrderAttachment;
import de.metas.order.rest.model.JsonSalesOrderCreateRequest;
import de.metas.order.rest.model.JsonSalesOrderLine;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.web.MetasfreshRestAPIConstants;
import lombok.NonNull;

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
@RequestMapping(SalesOrderRestController.ENDPOINT)
@Profile(Profiles.PROFILE_App)
public class SalesOrderRestController
{
	public static final String ENDPOINT = MetasfreshRestAPIConstants.ENDPOINT_API + "/sales/order";

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
			final int docTypeId = Services.get(IDocTypeDAO.class).getDocTypeId(DocTypeQuery.builder()
					.docBaseType(X_C_DocType.DOCBASETYPE_SalesOrder)
					.adClientId(Env.getAD_Client_ID())
					.name(request.getDocTypeName())
					.build());
			salesOrderFactory.docType(docTypeId);
		}

		final BPartnerId shipBPartnerId = bpartnersRepo.getBPartnerIdByValue(request.getShipBPartnerCode());
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

		final I_C_UOM uom = productBL.getStockingUOM(productId);
		final Quantity qty = Quantity.of(salesOrderLine.getQty(), uom);

		salesOrderFactory.newOrderLine()
				.productId(productId)
				.addQty(qty)
				.manualPrice(salesOrderLine.getPrice());
	}

	private JsonSalesOrder toSalesOrder(I_C_Order salesOrderRecord)
	{
		return JsonSalesOrder.builder()
				.salesOrderId(String.valueOf(salesOrderRecord.getC_Order_ID()))
				.build();
	}

	@GetMapping("/{salesOrderId}/attachments")
	public List<JsonSalesOrderAttachment> getAttachments(@PathVariable("salesOrderId") final String salesOrderIdStr)
	{
		final IAttachmentBL attachmentsBL = Services.get(IAttachmentBL.class);

		final int salesOrderId = Integer.parseInt(salesOrderIdStr);
		final TableRecordReference salesOrderRef = TableRecordReference.of(I_C_Order.Table_Name, salesOrderId);

		return attachmentsBL.getEntries(salesOrderRef)
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
		final IAttachmentBL attachmentsBL = Services.get(IAttachmentBL.class);

		final int salesOrderId = Integer.parseInt(salesOrderIdStr);
		final TableRecordReference salesOrderRef = TableRecordReference.of(I_C_Order.Table_Name, salesOrderId);

		final String name = file.getOriginalFilename();
		final byte[] data = file.getBytes();

		final AttachmentEntry entry = attachmentsBL.addEntry(salesOrderRef, name, data);
		return toSalesOrderAttachment(salesOrderId, entry);
	}

	private JsonSalesOrderAttachment toSalesOrderAttachment(final int salesOrderId, final AttachmentEntry entry)
	{
		return JsonSalesOrderAttachment.builder()
				.salesOrderId(String.valueOf(salesOrderId))
				.id(AttachmentEntryId.getRepoId(entry.getId()))
				.type(entry.getType())
				.filename(entry.getFilename())
				.contentType(entry.getContentType())
				.url(entry.getUrl() != null ? entry.getUrl().toString() : null)
				.build();
	}
}
