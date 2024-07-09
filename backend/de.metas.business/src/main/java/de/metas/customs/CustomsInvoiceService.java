package de.metas.customs;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.currency.ICurrencyBL;
import de.metas.customs.event.CustomsInvoiceUserNotificationsProducer;
import de.metas.customs.process.ShipmentLinesForCustomsInvoiceRepo;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.document.location.DocumentLocation;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.document.location.RenderedAddressAndCapturedLocation;
import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderLine;
import de.metas.order.OrderLineId;
import de.metas.order.OrderLineRepository;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.product.event.ProductWithNoCustomsTariffUserNotificationsProducer;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.NoUOMConversionException;
import org.compiere.model.I_C_Customs_Invoice;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

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
public class CustomsInvoiceService {
    public static final AdMessageKey ERR_NoValidLines = AdMessageKey.of("M_InOut_Create_CustomsInvoice_NoValidLines");

	private static final Logger logger = LogManager.getLogger(CustomsInvoiceService.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IInOutBL inoutBL = Services.get(IInOutBL.class);
	private final CustomsInvoiceRepository customsInvoiceRepo;
	private final OrderLineRepository orderLineRepo;
	private final ShipmentLinesForCustomsInvoiceRepo shipmentLinesForCustomsInvoiceRepo;
	private final IDocumentLocationBL documentLocationBL;

	public CustomsInvoiceService(
			@NonNull final CustomsInvoiceRepository customsInvoiceRepo,
			@NonNull final OrderLineRepository orderLineRepo,
			@NonNull final ShipmentLinesForCustomsInvoiceRepo shipmentLinesForCustomsInvoiceRepo,
			@NonNull final IDocumentLocationBL documentLocationBL)
	{
		this.customsInvoiceRepo = customsInvoiceRepo;
		this.orderLineRepo = orderLineRepo;
		this.shipmentLinesForCustomsInvoiceRepo = shipmentLinesForCustomsInvoiceRepo;
		this.documentLocationBL = documentLocationBL;
	}

	public CustomsInvoice generateCustomsInvoice(@NonNull final CustomsInvoiceRequest customsInvoiceRequest)
	{
		final CustomsInvoice customsInvoice = createCustomsInvoice(customsInvoiceRequest);

		customsInvoiceRepo.save(customsInvoice);

		return customsInvoice;
	}

	public void completeCustomsInvoice(final CustomsInvoice customsInvoice)
	{

		final IDocumentBL documentBL = Services.get(IDocumentBL.class);

		final CustomsInvoiceId id = customsInvoice.getId();

		final Set<ProductId> productIdsWithNoCustomsTariff = customsInvoiceRepo.retrieveProductIdsWithNoCustomsTariff(id);

		if (!productIdsWithNoCustomsTariff.isEmpty())
		{
			ProductWithNoCustomsTariffUserNotificationsProducer.newInstance()
					.notify(productIdsWithNoCustomsTariff);

		}
		else
		{

			documentBL.processEx(customsInvoiceRepo.getByIdInTrx(id), IDocument.ACTION_Complete, IDocument.STATUS_Completed);

			customsInvoiceRepo.updateDocActionAndStatus(customsInvoice);
		}

	}

	private CustomsInvoice createCustomsInvoice(@NonNull final CustomsInvoiceRequest customsInvoiceRequest)
	{
		final SetMultimap<ProductId, InOutAndLineId> linesToExportMap = customsInvoiceRequest.getLinesToExportMap();

		final CurrencyId currencyId = customsInvoiceRequest.getCurrencyId();

		final BPartnerLocationId bpartnerAndLocationId = customsInvoiceRequest.getBpartnerAndLocationId();

		final String bpartnerAddress = customsInvoiceRequest.getBpartnerAddress();

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
				.bpartnerAddress(bpartnerAddress)
				.userId(userId)
				.invoiceDate(invoiceDate)
				.orgId(Env.getOrgId())
				.currencyId(currencyId)
				.docStatus(DocStatus.Drafted)
				.docAction(IDocument.ACTION_Complete)
				.docTypeId(docTypeId)
				.documentNo(documentNo)
				.lines(customsInvoiceLines)
				.build();

		customsInvoice.updateLineNos();

		return customsInvoice;
	}

	private CustomsInvoiceLine createCustomsInvoiceLine(
			@NonNull final ProductId productId,
			@NonNull final Collection<InOutAndLineId> shipmentLinesForProducts,
			@NonNull final CurrencyId currencyId)
	{
		Check.assumeNotEmpty(shipmentLinesForProducts, "shipmentLinesForProducts is not empty");

		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

		Quantity qty = null;
		Money lineNetAmt = Money.of(BigDecimal.ZERO, currencyId);
		ArrayList<CustomsInvoiceLineAlloc> allocations = new ArrayList<>();

		for (final InOutAndLineId inoutAndLineId : shipmentLinesForProducts)
		{
			final Quantity inoutLineQty = getInOutLineQty(inoutAndLineId);
			if (qty == null)
			{
				qty = inoutLineQty;
			}
			else
			{
				qty = Quantitys.add(UOMConversionContext.of(productId), qty, inoutLineQty);
			}

			final ProductPrice inoutLinePrice = getInOutLinePriceConverted(inoutAndLineId, currencyId);
			final Quantity inoutLineQtyInPriceUOM = uomConversionBL.convertQuantityTo(inoutLineQty, UOMConversionContext.of(productId), inoutLinePrice.getUomId());

			final CustomsInvoiceLineAlloc alloc = CustomsInvoiceLineAlloc.builder()
					.inoutAndLineId(inoutAndLineId)
					.price(inoutLinePrice.toMoney())
					.quantityInPriceUOM(inoutLineQtyInPriceUOM)
					.build();
			allocations.add(alloc);

			lineNetAmt = lineNetAmt.add(alloc.getNetAmt());
		}

		return CustomsInvoiceLine.builder()
				.orgId(Env.getOrgId()) // FIXME: use header's Org
				.productId(productId)
				.lineNetAmt(lineNetAmt)
				.quantity(qty)
				.allocations(allocations)
				.build();
	}

	private ProductPrice getInOutLinePriceConverted(@NonNull final InOutAndLineId inoutAndLineId, @NonNull final CurrencyId currencyId)
	{
		final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

		final ProductPrice priceActual = getPriceActual(inoutAndLineId);

		final BigDecimal shipmentLinePriceConverted = currencyBL.convert(
				priceActual.toBigDecimal(),
				priceActual.getCurrencyId(),
				currencyId,
				Env.getClientId(),
				Env.getOrgId());

		return priceActual.toBuilder().money(Money.of(shipmentLinePriceConverted, currencyId)).build();
	}

	private ProductPrice getPriceActual(@NonNull final InOutAndLineId inoutAndLineId)
	{
		final IInOutDAO inoutDAO = Services.get(IInOutDAO.class);

		final InOutLineId inOutLineId = inoutAndLineId.getInOutLineId();

		final I_M_InOutLine inoutLineRecord = inoutDAO.getLineByIdInTrx(inOutLineId);

		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(inoutLineRecord.getC_OrderLine_ID());

		if (Check.isEmpty(orderLineId))
		{
			// we don't have a place where to take the price from.
			throw new AdempiereException("Can't find price for the shipment line " + inoutAndLineId);
		}

		final OrderLine orderLine = orderLineRepo.getById(orderLineId);

		final ProductPrice priceActual = orderLine.getPriceActual();
		return priceActual;
	}

	private Quantity getInOutLineQty(@NonNull final InOutAndLineId inoutAndLineId)
	{
		final IInOutDAO inoutDAO = Services.get(IInOutDAO.class);

		final I_M_InOutLine inoutLineRecord = inoutDAO.getLineByIdInTrx(inoutAndLineId.getInOutLineId());

		final ProductId productId = ProductId.ofRepoId(inoutLineRecord.getM_Product_ID());

		final Quantity lineQty;
		if (inoutLineRecord.getCatch_UOM_ID() > 0 && inoutLineRecord.getQtyDeliveredCatch().signum() != 0)
		{
			lineQty = Quantitys.of(inoutLineRecord.getQtyDeliveredCatch(), UomId.ofRepoId(inoutLineRecord.getCatch_UOM_ID()));
		}
		else if (inoutLineRecord.getC_UOM_ID() > 0)
		{
			lineQty = inoutBL.getQtyEntered(inoutLineRecord);
		}
		else
		{
			lineQty = inoutBL.getMovementQty(inoutLineRecord);
		}

		return convertToKillogram(lineQty, productId)
				.orElse(lineQty);
	}

	private Optional<Quantity> convertToKillogram(final Quantity qty, final ProductId productId)
	{
		final UomId kilogram = Services.get(IUOMDAO.class).getUomIdByX12DE355(X12DE355.KILOGRAM);
		try
		{
			final Quantity quantityInKilograms = uomConversionBL.convertQuantityTo(
					qty,
					UOMConversionContext.of(productId),
					kilogram);

			return Optional.of(quantityInKilograms);
		}
		catch (final NoUOMConversionException ex)
		{
			logger.debug("No UOM conversion. Returning empty", ex);
			return Optional.empty();
		}
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

	public DocTypeId retrieveCustomsInvoiceDocTypeId()
	{
		return customsInvoiceRepo.retrieveCustomsInvoiceDocTypeId();
	}

	public CustomsInvoice generateNewCustomsInvoice(
			final BPartnerLocationId bpartnerLocationId,
			@Nullable final BPartnerContactId contactId,
			final IQueryFilter<I_M_InOut> queryFilter)
	{
		final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

		final List<InOutAndLineId> linesToExport = retrieveLinesToExport(queryFilter);

		if (Check.isEmpty(linesToExport))
		{
			final ITranslatableString errorMessage = Services.get(IMsgBL.class).getTranslatableMsgText(ERR_NoValidLines);

			throw new AdempiereException(errorMessage);
		}

		final ImmutableSetMultimap<ProductId, InOutAndLineId> linesToExportMap = linesToExport
				.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(
						this::getProductId, // keyFunction,
						Function.identity()));// valueFunction

		final CurrencyId currencyId = currencyBL.getBaseCurrencyId(Env.getClientId(), Env.getOrgId());

		final LocalDate invoiceDate = Env.getLocalDate();

		final DocTypeId docTypeId = retrieveCustomsInvoiceDocTypeId();

		final String documentNo = reserveDocumentNo(docTypeId);

		final RenderedAddressAndCapturedLocation bpartnerAddress = documentLocationBL.computeRenderedAddress(DocumentLocation.builder()
				.bpartnerId(bpartnerLocationId.getBpartnerId())
				.bpartnerLocationId(bpartnerLocationId)
				.contactId(contactId)
				.build());

		final CustomsInvoiceRequest customsInvoiceRequest = CustomsInvoiceRequest.builder()
				.bpartnerAndLocationId(bpartnerLocationId)
				.bpartnerAddress(bpartnerAddress.getRenderedAddress())
				.userId(BPartnerContactId.toUserIdOrNull(contactId))
				.currencyId(currencyId)
				.linesToExportMap(linesToExportMap)
				.invoiceDate(invoiceDate)
				.documentNo(documentNo)
				.docTypeId(docTypeId)
				.build();

		final CustomsInvoice customsInvoice = generateCustomsInvoice(customsInvoiceRequest);

		setIsExportedShipmentToCustomsInvoice(customsInvoice);

		CustomsInvoiceUserNotificationsProducer.newInstance()
				.notifyGenerated(customsInvoice);

		return customsInvoice;
	}

	private ProductId getProductId(final InOutAndLineId inoutAndLineId)
	{
		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

		final InOutLineId shipmentLineId = inoutAndLineId.getInOutLineId();
		final I_M_InOutLine shipmentLineRecord = inOutDAO.getLineByIdOutOfTrx(shipmentLineId, I_M_InOutLine.class);

		return ProductId.ofRepoId(shipmentLineRecord.getM_Product_ID());
	}

	public List<InOutAndLineId> retrieveLinesToExport(IQueryFilter<I_M_InOut> queryFilter)
	{
		final ImmutableList<InOutId> selectedShipments = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_InOut.class)
				.filter(queryFilter)
				.create()
				.listIds(InOutId::ofRepoId)
				.stream()
				.collect(ImmutableList.toImmutableList());

		List<InOutAndLineId> shipmentLinesToExport = shipmentLinesForCustomsInvoiceRepo.retrieveValidLinesToExport(selectedShipments);

		return shipmentLinesToExport;

	}

	public void addShipmentsToCustomsInvoice(@NonNull final CustomsInvoiceId customsInvoiceId, @NonNull final IQueryFilter<I_M_InOut> queryFilter)
	{
		final List<InOutAndLineId> linesToExport = retrieveLinesToExport(queryFilter);

		if (Check.isEmpty(linesToExport))
		{
			final ITranslatableString errorMessage = Services.get(IMsgBL.class).getTranslatableMsgText(ERR_NoValidLines);

			throw new AdempiereException(errorMessage);
		}

		final ImmutableSetMultimap<ProductId, InOutAndLineId> linesToExportMap = linesToExport
				.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(
						this::getProductId, // keyFunction,
						Function.identity()));// valueFunction

		linesToExportMap.keySet()
				.stream()
				.forEach(productId -> addShipmentLinesToCustomsInvoice(productId, linesToExportMap.get(productId), customsInvoiceId));

		final CustomsInvoice customsInvoice = customsInvoiceRepo.retrieveById(customsInvoiceId);

		setIsExportedShipmentToCustomsInvoice(customsInvoice);

	}

	@VisibleForTesting
	void addShipmentLinesToCustomsInvoice(@NonNull ProductId productId, @NonNull final ImmutableSet<InOutAndLineId> shipmentLinesForProduct, @NonNull final CustomsInvoiceId customsInvoiceId)
	{
		CustomsInvoice customsInvoice = customsInvoiceRepo.retrieveById(customsInvoiceId);

		final ImmutableList<CustomsInvoiceLine> existingLines = customsInvoice.getLines();

		final CustomsInvoiceLine customsInvoiceLineForProductFound = findCustomsInvoiceLineForProductId(productId, existingLines).orElse(null);
		CustomsInvoiceLine customsInvoiceLineForProduct;
		if (customsInvoiceLineForProductFound == null)
		{
			final I_C_Customs_Invoice customsInvoiceRecord = customsInvoiceRepo.getByIdInTrx(customsInvoiceId);
			final CustomsInvoiceLine newCustomsInvoiceLineForProduct = createCustomsInvoiceLine(productId, shipmentLinesForProduct, CurrencyId.ofRepoId(customsInvoiceRecord.getC_Currency_ID()));

			customsInvoiceLineForProduct = newCustomsInvoiceLineForProduct;
		}
		else
		{
			customsInvoiceLineForProduct = customsInvoiceLineForProductFound;
			for (final InOutAndLineId shipmentLineId : shipmentLinesForProduct)
			{
				customsInvoiceLineForProduct = allocateShipmentLine(customsInvoiceLineForProduct, shipmentLineId, customsInvoice.getCurrencyId());
			}
		}

		//
		// newLines: existingLines with adding/replacing customsInvoiceLineForProductFound with our created/changed version
		final List<CustomsInvoiceLine> newLines = new ArrayList<>();
		{
			boolean added = false;
			for (CustomsInvoiceLine existingLine : existingLines)
			{
				if (Util.same(customsInvoiceLineForProductFound, existingLine))
				{
					newLines.add(customsInvoiceLineForProduct);
					added = true;
				}
				else
				{
					newLines.add(existingLine);
				}
			}

			if (!added)
			{
				newLines.add(customsInvoiceLineForProduct);
				added = true;
			}
		}

		customsInvoice = customsInvoice.toBuilder()
				.lines(ImmutableList.copyOf(newLines))
				.build();
		customsInvoice.updateLineNos();

		customsInvoiceRepo.save(customsInvoice);
	}

	private void setIsExportedShipmentToCustomsInvoice(@NonNull final CustomsInvoice customsInvoice)
	{
		customsInvoice
				.getLines()
				.stream()
				.forEach(line -> setIsExportedShipmentToCustomsInvoiceLine(line));

	}

	private void setIsExportedShipmentToCustomsInvoiceLine(@NonNull final CustomsInvoiceLine customsInvoiceLine)
	{
		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

		customsInvoiceLine.getAllocations()
				.stream()
				.map(alloc -> alloc.getInoutAndLineId().getInOutId())
				.forEach(shipment -> inOutDAO.setExportedInCustomsInvoice(shipment));
	}

	private CustomsInvoiceLine allocateShipmentLine(
			@NonNull final CustomsInvoiceLine customsInvoiceLineForProduct,
			@NonNull final InOutAndLineId inOutAndLineId,
			@NonNull final CurrencyId currencyId)
	{
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

		final CustomsInvoiceLineAlloc existingAlloc = customsInvoiceLineForProduct.getAllocationByInOutLineId(inOutAndLineId).orElse(null);

		Quantity qty = customsInvoiceLineForProduct.getQuantity();
		Money lineNetAmt = customsInvoiceLineForProduct.getLineNetAmt();

		if (existingAlloc != null)
		{
			final Quantity existingAllocQty = existingAlloc.getQuantityInPriceUOM();
			final Money existingAllocNetAmt = existingAlloc.getNetAmt();

			qty = Quantitys.subtract(UOMConversionContext.of(customsInvoiceLineForProduct.getProductId()), qty, existingAllocQty);
			lineNetAmt = lineNetAmt.subtract(existingAllocNetAmt);

			customsInvoiceLineForProduct.removeAllocation(existingAlloc);
		}

		final CustomsInvoiceLineAlloc newAlloc;
		{
			final Quantity inoutLineQty = getInOutLineQty(inOutAndLineId);
			final ProductPrice inoutLinePrice = getInOutLinePriceConverted(inOutAndLineId, currencyId);
			final Quantity inoutLineQtyInPriceUOM = uomConversionBL.convertQuantityTo(inoutLineQty, UOMConversionContext.of(customsInvoiceLineForProduct.getProductId()), inoutLinePrice.getUomId());

			newAlloc = CustomsInvoiceLineAlloc.builder()
					.inoutAndLineId(inOutAndLineId)
					.price(inoutLinePrice.toMoney())
					.quantityInPriceUOM(inoutLineQtyInPriceUOM)
					.build();

			customsInvoiceLineForProduct.addAllocation(newAlloc);
		}

		qty = Quantitys.add(UOMConversionContext.of(customsInvoiceLineForProduct.getProductId()), qty, newAlloc.getQuantityInPriceUOM());
		lineNetAmt = lineNetAmt.add(newAlloc.getNetAmt());

		return customsInvoiceLineForProduct.toBuilder()
				.lineNetAmt(lineNetAmt)
				.quantity(qty)
				.build();
	}

	private Optional<CustomsInvoiceLine> findCustomsInvoiceLineForProductId(@NonNull final ProductId productId, @NonNull final Collection<CustomsInvoiceLine> existingLines)
	{
		return existingLines.stream()
				.filter(line -> line.getProductId().equals(productId))
				.findFirst();
	}

}
