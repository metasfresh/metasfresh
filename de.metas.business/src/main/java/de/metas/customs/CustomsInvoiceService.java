package de.metas.customs;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Customs_Invoice;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.currency.ICurrencyBL;
import de.metas.customs.event.CustomsInvoiceUserNotificationsProducer;
import de.metas.document.DocTypeId;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderLine;
import de.metas.order.OrderLineId;
import de.metas.order.OrderLineRepository;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Service
public class CustomsInvoiceService
{

	private final CustomsInvoiceRepository customsInvoiceRepo;
	private final OrderLineRepository orderLineRepo;

	public CustomsInvoiceService(
			@NonNull final CustomsInvoiceRepository customsInvoiceRepo,
			@NonNull final OrderLineRepository orderLineRepo)
	{
		this.customsInvoiceRepo = customsInvoiceRepo;
		this.orderLineRepo = orderLineRepo;

	}

	public CustomsInvoice generateCustomsInvoice(final CustomsInvoiceRequest customsInvoiceRequest)
	{
		final IDocumentBL documentBL = Services.get(IDocumentBL.class);

		final CustomsInvoice customsInvoice = createCustomsInvoice(customsInvoiceRequest);

		final I_C_Customs_Invoice customsInvoiceRecord = customsInvoiceRepo.save(customsInvoice);

		if (customsInvoiceRequest.isComplete())
		{
			documentBL.processEx(customsInvoiceRecord, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		}

		CustomsInvoiceUserNotificationsProducer.newInstance()
				.notifyGenerated(customsInvoiceRecord);

		return customsInvoice;
	}

	private CustomsInvoice createCustomsInvoice(final CustomsInvoiceRequest customsInvoiceRequest)
	{

		final Map<ProductId, List<InOutAndLineId>> linesToExportMap = customsInvoiceRequest.getLinesToExportMap();

		final CurrencyId currencyId = customsInvoiceRequest.getCurrencyId();

		final BPartnerLocationId bpartnerAndLocationId = customsInvoiceRequest.getBpartnerAndLocationId();

		final UserId userId = customsInvoiceRequest.getUserId();

		final LocalDate invoiceDate = customsInvoiceRequest.getInvoiceDate();

		final DocTypeId docTypeId = customsInvoiceRequest.getDocTypeId();

		final String documentNo = customsInvoiceRequest.getDocumentNo();

		final ImmutableList<CustomsInvoiceLine> customsInvoiceLines = linesToExportMap.keySet()
				.stream()
				.map(productId -> createCustomsInvoiceLine(productId, linesToExportMap.get(productId), currencyId))
				.collect(ImmutableList.toImmutableList());

		final CustomsInvoice customsInvoice = CustomsInvoice.builder()
				.bpartnerAndLocationId(bpartnerAndLocationId)
				.userId(userId)
				.invoiceDate(invoiceDate)
				.orgId(Env.getOrgId())
				.currencyId(currencyId)
				.docStatus(IDocument.STATUS_Drafted)
				.docAction(IDocument.ACTION_Complete)
				.docTypeId(docTypeId)
				.documentNo(documentNo)
				.lines(customsInvoiceLines)
				.build();

		customsInvoice.updateLineNos();

		return customsInvoice;
	}

	private CustomsInvoiceLine createCustomsInvoiceLine(final ProductId productId,
			final List<InOutAndLineId> shipmentLinesForProducts,
			final CurrencyId currencyId)
	{
		final IProductDAO productDAO = Services.get(IProductDAO.class);

		final I_M_Product product = productDAO.getById(productId);

		Quantity qty = Quantity.of(BigDecimal.ZERO, product.getC_UOM());
		Money lineNetAmt = Money.of(BigDecimal.ZERO, currencyId);

		final UomId uomId = UomId.ofRepoId(product.getC_UOM_ID());

		for (InOutAndLineId inoutAndLineId : shipmentLinesForProducts)
		{
			final Quantity inoutLineQtyCoverted = getInOutLineQtyConverted(inoutAndLineId, uomId);

			qty = qty.add(inoutLineQtyCoverted.getAsBigDecimal());

			final Money inoutLinePriceConverted = getInOutLinePriceConverted(inoutAndLineId, currencyId);

			final Money shipmentLineNetAmt = inoutLinePriceConverted.multiply(inoutLineQtyCoverted.getAsBigDecimal());

			lineNetAmt = lineNetAmt.add(shipmentLineNetAmt);

		}

		final CustomsInvoiceLine customsInvoiceLine = CustomsInvoiceLine.builder()
				.productId(productId)
				.lineNetAmt(lineNetAmt)
				.quantity(qty)
				.orgId(Env.getOrgId())
				.uomId(uomId)
				.build();

		return customsInvoiceLine;
	}

	private Money getInOutLinePriceConverted(final InOutAndLineId inoutAndLineId, final CurrencyId currencyId)
	{
		final IInOutDAO inoutDAO = Services.get(IInOutDAO.class);
		final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

		final InOutLineId inOutLineId = inoutAndLineId.getInOutLineId();

		final I_M_InOutLine inoutLineRecord = inoutDAO.getLineById(inOutLineId);

		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(inoutLineRecord.getC_OrderLine_ID());

		if (Check.isEmpty(orderLineId))
		{
			// we don't have a place where to take the price from.
			throw new AdempiereException("Can't find price for the shipment line " + inoutAndLineId);
		}

		final OrderLine orderLine = orderLineRepo.getById(orderLineId);

		final Money priceActual = orderLine.getPriceActual();

		final BigDecimal shipmentLinePriceConverted = currencyBL.convert(
				Env.getCtx(),
				priceActual.getValue(),
				priceActual.getCurrencyId().getRepoId(),
				currencyId.getRepoId(),
				Env.getAD_Client_ID(),
				Env.getOrgId().getRepoId());

		return Money.of(shipmentLinePriceConverted, currencyId);

	}

	private Quantity getInOutLineQtyConverted(final InOutAndLineId inoutAndLineId, final UomId uomId)
	{
		final IInOutDAO inoutDAO = Services.get(IInOutDAO.class);
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

		final InOutLineId inOutLineId = inoutAndLineId.getInOutLineId();

		final I_M_InOutLine inoutLineRecord = inoutDAO.getLineById(inOutLineId);

		final BigDecimal movementQty = inoutLineRecord.getMovementQty();

		final Quantity lineQty = Quantity.of(movementQty, inoutLineRecord.getC_UOM());

		final ProductId productId = ProductId.ofRepoId(inoutLineRecord.getM_Product_ID());

		final Quantity quantityConverted = uomConversionBL.convertQuantityTo(
				lineQty,
				UOMConversionContext.of(productId),
				uomId);

		return quantityConverted;
	}

	public String reserveDocumentNo(@NonNull final DocTypeId docTypeId)
	{
		final IDocumentNoBuilderFactory documentNoFactory = Services.get(IDocumentNoBuilderFactory.class);

		final String documentNo = documentNoFactory.forDocType(docTypeId.getRepoId(), /* useDefiniteSequence */false)
				.setClientId(Env.getClientId())
				.setFailOnError(true)
				.build();

		if (documentNo == null || documentNo == IDocumentNoBuilder.NO_DOCUMENTNO)
		{
			throw new AdempiereException("Cannot fetch documentNo for " + docTypeId);
		}

		return documentNo;
	}

	public void setCustomsInvoiceToShipments(final ImmutableSet<InOutId> exportedShippmentIds, final CustomsInvoice customsInvoice)
	{
		exportedShippmentIds
				.stream()
				.forEach(exportedShipmentId -> customsInvoiceRepo.setCustomsInvoiceToShipment(exportedShipmentId, customsInvoice));
	}

	public DocTypeId retrieveCustomsInvoiceDocTypeId()
	{
		return customsInvoiceRepo.retrieveCustomsInvoiceDocTypeId();
	}

}
