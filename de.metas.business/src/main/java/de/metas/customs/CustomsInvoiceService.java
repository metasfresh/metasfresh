package de.metas.customs;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Customs_Invoice;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.currency.ICurrencyBL;
import de.metas.currency.impl.CurrencyBL;
import de.metas.document.DocTypeId;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutLineId;
import de.metas.money.CurrencyId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.user.UserId;
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

	public CustomsInvoiceService(
			@NonNull final CustomsInvoiceRepository customsInvoiceRepo)
	{
		this.customsInvoiceRepo = customsInvoiceRepo;

	}

	public void generateCustomsInvoice(final BPartnerLocationId bpartnerAndLocationId,
			final UserId userId,
			final CurrencyId currencyId,
			final Map<ProductId, List<InOutAndLineId>> linesToExportMap)
	{
		final IDocumentBL documentBL = Services.get(IDocumentBL.class);

		final DocTypeId docTypeId = customsInvoiceRepo.retrieveCustomsInvoiceDocTypeId();

		final String documentNo = reserveDocumentNo(docTypeId);

		final CustomsInvoice customsInvoice = createCustomsInvoice(bpartnerAndLocationId, userId, docTypeId, documentNo, currencyId, linesToExportMap);

		final I_C_Customs_Invoice customsInvoiceRecord = customsInvoiceRepo.save(customsInvoice);

		documentBL.processEx(customsInvoiceRecord, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

	}

	private CustomsInvoice createCustomsInvoice(final BPartnerLocationId bpartnerAndLocationId,
			final UserId userId,
			final DocTypeId docTypeId,
			final String documentNo,
			final CurrencyId currencyId,
			final Map<ProductId, List<InOutAndLineId>> linesToExportMap)
	{

		for (final ProductId productId : linesToExportMap.keySet())
		{
			createCustomsInvoiceLine(productId, linesToExportMap.get(productId), currencyId);
		}

		return CustomsInvoice.builder()
				.bpartnerAndLocationId(bpartnerAndLocationId)
				.userId(userId)
				.docAction(IDocument.ACTION_Complete)
				.docTypeId(docTypeId)
				.documentNo(documentNo)
				.build();
	}

	private void createCustomsInvoiceLine(final ProductId productId, final List<InOutAndLineId> shipmentLinesForProducts, final CurrencyId currencyId)
	{
		final IProductDAO productDAO = Services.get(IProductDAO.class);
		final IInOutDAO inoutDAO = Services.get(IInOutDAO.class);
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final ICurrencyBL currencyBL = Services.get(CurrencyBL.class);

		final I_M_Product product = productDAO.getById(productId);;

		Quantity qty = Quantity.of(BigDecimal.ZERO, product.getC_UOM());
		BigDecimal price = BigDecimal.ZERO;

		final UomId uomId = UomId.ofRepoId(product.getC_UOM_ID());

		for (InOutAndLineId inoutAndLineId : shipmentLinesForProducts)
		{
			final InOutLineId inOutLineId = inoutAndLineId.getInOutLineId();

			final I_M_InOutLine inoutLineRecord = inoutDAO.getLineById(inOutLineId);

			final BigDecimal movementQty = inoutLineRecord.getMovementQty();

			final Quantity lineQty = Quantity.of(movementQty, inoutLineRecord.getC_UOM());

			final Quantity quantityConverted = uomConversionBL.convertQuantityTo(
					lineQty,
					UOMConversionContext.of(productId),
					uomId);

			qty.add(quantityConverted.getAsBigDecimal());

			final BigDecimal priceActual = inoutLineRecord.getC_OrderLine().getPriceActual();

			// final CurrencyId lineCurrencyId = CurrencyId.ofRepoId(inoutLineRecord.getC_OrderLine().getC_Currency_ID());

			final BigDecimal shipmentLinePriceConverted = currencyBL.convert(
					Env.getCtx(),
					inoutLineRecord.getC_OrderLine().getPriceActual(),
					inoutLineRecord.getC_OrderLine().getC_Currency_ID(),
					currencyId.getRepoId(),
					Env.getAD_Client_ID(),
					Env.getOrgId().getRepoId());

			final BigDecimal linePrice = shipmentLinePriceConverted.multiply(quantityConverted.getAsBigDecimal());

			price = price.add(linePrice);

		}

		// return ShipmentDeclarationLine.builder()
		// .orgId(OrgId.ofRepoId(shipmentLineRecord.getAD_Org_ID()))
		// .packageSize(product.getPackageSize())
		// .productId(productId)
		// .quantity(Quantity.of(shipmentLineRecord.getMovementQty(), uom))
		// .shipmentLineId(shipmentLineId)
		// .build();

	}

	private String reserveDocumentNo(@NonNull final DocTypeId docTypeId)
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

}
