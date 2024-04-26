package de.metas.edi.api.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner_product.IBPartnerProductDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.edi.api.EDIDesadvLineId;
import de.metas.edi.api.IDesadvBL;
import de.metas.edi.api.IDesadvDAO;
import de.metas.edi.api.impl.pack.EDIDesadvPackId;
import de.metas.edi.api.impl.pack.EDIDesadvPackService;
import de.metas.edi.model.I_C_Order;
import de.metas.edi.model.I_C_OrderLine;
import de.metas.edi.model.I_M_InOut;
import de.metas.edi.model.I_M_InOutLine;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.logging.LogManager;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.report.ReportResultData;
import de.metas.report.server.ReportConstants;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.DB;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Service
public class DesadvBL implements IDesadvBL
{
	private final static Logger logger = LogManager.getLogger(EDIDesadvPackService.class);

	private static final AdMessageKey MSG_EDI_DESADV_RefuseSending = AdMessageKey.of("EDI_DESADV_RefuseSending");

	/**
	 * Process used to print the {@link de.metas.esb.edi.model.I_EDI_Desadv_Pack}s labels
	 */
	private static final String AD_PROCESS_VALUE_EDI_DesadvLine_SSCC_Print = "EDI_DesadvLine_SSCC_Print";

	private final transient IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final transient IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final transient IDesadvDAO desadvDAO = Services.get(IDesadvDAO.class);
	private final transient IProductDAO productDAO = Services.get(IProductDAO.class);
	private final transient IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final transient IBPartnerProductDAO bPartnerProductDAO = Services.get(IBPartnerProductDAO.class);
	private final transient IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final transient IOrderBL orderBL = Services.get(IOrderBL.class);
	private final transient IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final transient IProductBL productBL = Services.get(IProductBL.class);

	private final transient EDIDesadvPackService ediDesadvPackService;

	// @VisibleForTesting
	public DesadvBL(@NonNull final EDIDesadvPackService ediDesadvPackService)
	{
		this.ediDesadvPackService = ediDesadvPackService;
	}

	@Override
	public List<I_EDI_DesadvLine> retrieveLinesByIds(final Collection<Integer> desadvLineIds)
	{
		return desadvDAO.retrieveLinesByIds(desadvLineIds);
	}

	@Override
	public I_EDI_Desadv addToDesadvCreateForOrderIfNotExist(@NonNull final I_C_Order orderRecord)
	{
		Check.assumeNotEmpty(orderRecord.getPOReference(), "C_Order {} has a not-empty POReference", orderRecord);

		final I_EDI_Desadv desadvRecord = retrieveOrCreateDesadv(orderRecord);
		orderRecord.setEDI_Desadv_ID(desadvRecord.getEDI_Desadv_ID());

		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(orderRecord, I_C_OrderLine.class);
		for (final I_C_OrderLine orderLine : orderLines)
		{
			if (orderLine.getEDI_DesadvLine_ID() > 0)
			{
				continue; // is already assigned to a desadv line
			}
			if (orderLine.isPackagingMaterial())
			{
				continue; // packing materials from the OL don't belong into the desadv
			}

			final I_EDI_DesadvLine desadvLine = retrieveOrCreateDesadvLine(orderRecord, desadvRecord, orderLine);
			Check.errorIf(
					desadvLine.getM_Product_ID() != orderLine.getM_Product_ID(),
					"EDI_DesadvLine {} of EDI_Desadv {} has M_Product_ID {} and C_OrderLine {} of C_Order {} has M_Product_ID {}, but both have POReference {} and Line {} ",
					desadvLine, desadvRecord, desadvLine.getM_Product_ID(),
					orderLine, orderRecord, orderLine.getM_Product_ID(),
					orderRecord.getPOReference(), orderLine.getLine());

			orderLine.setEDI_DesadvLine(desadvLine);
			InterfaceWrapperHelper.save(orderLine);
		}

		updateFullfilmentPercent(desadvRecord);
		saveRecord(desadvRecord);

		return desadvRecord;
	}

	private I_EDI_DesadvLine retrieveOrCreateDesadvLine(
			@NonNull final I_C_Order orderRecord,
			@NonNull final I_EDI_Desadv desadvRecord,
			@NonNull final I_C_OrderLine orderLineRecord)
	{
		final I_EDI_DesadvLine existingDesadvLine = desadvDAO.retrieveMatchingDesadvLinevOrNull(
				desadvRecord,
				orderLineRecord.getLine(),
				BPartnerId.ofRepoId(orderLineRecord.getC_BPartner_ID()));
		if (existingDesadvLine != null)
		{
			return existingDesadvLine; // done
		}

		final ProductId productId = ProductId.ofRepoId(orderLineRecord.getM_Product_ID());
		final BPartnerId buyerBPartnerId = BPartnerId.ofRepoId(orderRecord.getC_BPartner_ID());
		final org.compiere.model.I_C_BPartner buyerBPartner = bpartnerDAO.getById(buyerBPartnerId);

		final I_EDI_DesadvLine newDesadvLine = InterfaceWrapperHelper.newInstance(I_EDI_DesadvLine.class, orderRecord);
		newDesadvLine.setEDI_Desadv(desadvRecord);
		newDesadvLine.setLine(orderLineRecord.getLine());

		newDesadvLine.setOrderLine(orderLineRecord.getLine());
		newDesadvLine.setOrderPOReference(orderRecord.getPOReference());

		final BigDecimal sumOrderedInStockingUOM = desadvRecord.getSumOrderedInStockingUOM().add(orderLineRecord.getQtyOrdered());
		desadvRecord.setSumOrderedInStockingUOM(sumOrderedInStockingUOM);

		newDesadvLine.setC_UOM_Invoice_ID(orderLineRecord.getPrice_UOM_ID());
		newDesadvLine.setQtyDeliveredInInvoiceUOM(ZERO);

		// we'll need this when inoutLines are added, because then we need to add either the nominal quantity or the catch-quantity
		newDesadvLine.setInvoicableQtyBasedOn(orderLineRecord.getInvoicableQtyBasedOn());
		// this we'll need as well when inoutLines are added, in case there is a TU-UOM involved
		newDesadvLine.setQtyItemCapacity(orderLineRecord.getQtyItemCapacity());

		newDesadvLine.setQtyEntered(orderLineRecord.getQtyEntered());
		newDesadvLine.setQtyDeliveredInUOM(ZERO);
		newDesadvLine.setC_UOM_ID(orderLineRecord.getC_UOM_ID());

		newDesadvLine.setPriceActual(orderLineRecord.getPriceActual());

		newDesadvLine.setQtyOrdered(orderLineRecord.getQtyOrdered());
		newDesadvLine.setQtyDeliveredInStockingUOM(ZERO);
		newDesadvLine.setM_Product_ID(productId.getRepoId());

		newDesadvLine.setProductDescription(orderLineRecord.getProductDescription());

		final I_M_Product product = productDAO.getById(productId);
		final OrgId orgId = OrgId.ofRepoId(product.getAD_Org_ID());

		//
		// set infos from C_BPartner_Product
		final I_C_BPartner_Product bPartnerProduct = bPartnerProductDAO.retrieveBPartnerProductAssociation(buyerBPartner, product, orgId);
		// don't throw an error for missing bPartnerProduct; it might prevent users from creating shipments
		// instead, just don't set the values and let the user fix it in the DESADV window later on
		// Check.assumeNotNull(bPartnerProduct, "there is a C_BPartner_Product for C_BPArtner {} and M_Product {}", inOut.getC_BPartner(), inOutLine.getM_Product());
		if (bPartnerProduct != null)
		{
			newDesadvLine.setProductNo(bPartnerProduct.getProductNo());
			newDesadvLine.setUPC_CU(bPartnerProduct.getUPC());
			newDesadvLine.setEAN_CU(bPartnerProduct.getEAN_CU());

			if (Check.isEmpty(newDesadvLine.getProductDescription(), true))
			{
				// fallback for product description
				newDesadvLine.setProductDescription(bPartnerProduct.getProductDescription());
			}
			if (Check.isEmpty(newDesadvLine.getProductDescription(), true))
			{
				// fallback for product description
				newDesadvLine.setProductDescription(bPartnerProduct.getProductName());
			}
		}

		if (Check.isEmpty(newDesadvLine.getProductDescription(), true))
		{
			// fallback for product description
			newDesadvLine.setProductDescription(product.getName());
		}

		//
		// set infos from M_HU_PI_Item_Product
		final I_M_HU_PI_Item_Product materialItemProduct = ediDesadvPackService.extractHUPIItemProduct(orderRecord, orderLineRecord);
		if (materialItemProduct != null)
		{
			newDesadvLine.setGTIN(materialItemProduct.getGTIN());
			newDesadvLine.setUPC_TU(materialItemProduct.getUPC());
			newDesadvLine.setEAN_TU(materialItemProduct.getEAN_TU());
		}
		newDesadvLine.setIsSubsequentDeliveryPlanned(false); // the default

		setExternalBPartnerInfo(newDesadvLine, orderLineRecord);

		InterfaceWrapperHelper.save(newDesadvLine);
		return newDesadvLine;
	}

	private I_EDI_Desadv retrieveOrCreateDesadv(@NonNull final I_C_Order order)
	{
		I_EDI_Desadv desadv = desadvDAO.retrieveMatchingDesadvOrNull(
				order.getPOReference(),
				BPartnerId.ofRepoId(order.getC_BPartner_ID()),
				InterfaceWrapperHelper.getContextAware(order));
		if (desadv == null)
		{
			desadv = InterfaceWrapperHelper.newInstance(I_EDI_Desadv.class, order);

			desadv.setPOReference(order.getPOReference());
			desadv.setDeliveryViaRule(order.getDeliveryViaRule());

			desadv.setC_BPartner_ID(order.getC_BPartner_ID());
			desadv.setC_BPartner_Location_ID(order.getC_BPartner_Location_ID());

			desadv.setDateOrdered(order.getDateOrdered());
			desadv.setMovementDate(order.getDatePromised());
			desadv.setC_Currency_ID(order.getC_Currency_ID());

			// the DESADV recipient might need an explicitly set dropship/handover partner and location; even if it is the same as the buyer's one
			desadv.setHandOver_Partner_ID(CoalesceUtil.firstGreaterThanZero(order.getHandOver_Partner_ID(), order.getC_BPartner_ID()));
			desadv.setHandOver_Location_ID(CoalesceUtil.firstGreaterThanZero(order.getHandOver_Location_ID(), order.getC_BPartner_Location_ID()));

			desadv.setDropShip_BPartner_ID(CoalesceUtil.firstGreaterThanZero(order.getDropShip_BPartner_ID(), order.getC_BPartner_ID()));
			desadv.setDropShip_Location_ID(CoalesceUtil.firstGreaterThanZero(order.getDropShip_Location_ID(), order.getC_BPartner_Location_ID()));

			desadv.setBill_Location_ID(BPartnerLocationAndCaptureId.toBPartnerLocationRepoId(orderBL.getBillToLocationId(order)));
			// note: the minimal acceptable fulfillment is currently set by a model interceptor
			InterfaceWrapperHelper.save(desadv);
		}
		return desadv;
	}

	@Nullable
	@Override
	public I_EDI_Desadv addToDesadvCreateForInOutIfNotExist(@NonNull final I_M_InOut inOut)
	{
		final I_EDI_Desadv desadv;

		if (inOut.getC_Order_ID() > 0)
		{
			final I_C_Order order = InterfaceWrapperHelper.create(inOut.getC_Order(), I_C_Order.class);
			if (order.getEDI_Desadv_ID() > 0)
			{
				desadv = order.getEDI_Desadv();
			}
			else
			{
				desadv = addToDesadvCreateForOrderIfNotExist(order);
				InterfaceWrapperHelper.save(order);
			}
		}
		else if (!Check.isEmpty(inOut.getPOReference(), true))
		{
			desadv = desadvDAO.retrieveMatchingDesadvOrNull(inOut.getPOReference(), BPartnerId.ofRepoId(inOut.getC_BPartner_ID()), InterfaceWrapperHelper.getContextAware(inOut));
		}
		else
		{
			desadv = null;
		}

		if (desadv == null)
		{
			return null;
		}

		inOut.setEDI_Desadv(desadv);

		final BPartnerId recipientBPartnerId = BPartnerId.ofRepoId(inOut.getC_BPartner_ID());

		final List<I_M_InOutLine> inOutLines = inOutDAO.retrieveLines(inOut, I_M_InOutLine.class);
		for (final I_M_InOutLine inOutLine : inOutLines)
		{
			if (inOutLine.getC_OrderLine_ID() <= 0)
			{
				continue;
			}
			addInOutLine(inOutLine, recipientBPartnerId);
		}
		return desadv;
	}

	private void addInOutLine(@NonNull final I_M_InOutLine inOutLineRecord, @NonNull final BPartnerId recipientBPartnerId)
	{
		final I_C_OrderLine orderLineRecord = InterfaceWrapperHelper.create(inOutLineRecord.getC_OrderLine(), I_C_OrderLine.class);

		final EDIDesadvLineId desadvLineId = EDIDesadvLineId.ofRepoIdOrNull(orderLineRecord.getEDI_DesadvLine_ID());

		if (desadvLineId == null)
		{
			logger.debug("No EDI_DesadvLine_ID set on C_OrderLine with ID={};",
						 orderLineRecord.getC_OrderLine_ID());

			return;
		}

		final I_EDI_DesadvLine desadvLineRecord = desadvDAO.retrieveLineById(desadvLineId);

		final InvoicableQtyBasedOn invoicableQtyBasedOn = InvoicableQtyBasedOn.ofNullableCodeOrNominal(desadvLineRecord.getInvoicableQtyBasedOn());
		final StockQtyAndUOMQty inOutLineQty = inOutBL.extractInOutLineQty(inOutLineRecord, invoicableQtyBasedOn);

		// update the desadvLineRecord first, so it's always <= the packs' sum and so our validating MI doesn't fail
		addOrSubtractInOutLineQty(desadvLineRecord, inOutLineQty, orderLineRecord, true/* add */);
		InterfaceWrapperHelper.save(desadvLineRecord);

		inOutLineRecord.setEDI_DesadvLine_ID(desadvLineRecord.getEDI_DesadvLine_ID());
		InterfaceWrapperHelper.save(inOutLineRecord);

		ediDesadvPackService.createPacks(inOutLineRecord, recipientBPartnerId);
	}

	@Override
	public void removeInOutFromDesadv(final I_M_InOut inOut)
	{
		if (inOut.getEDI_Desadv_ID() <= 0)
		{
			return;
		}

		final List<I_M_InOutLine> inOutLines = inOutDAO.retrieveLines(inOut, I_M_InOutLine.class);
		for (final I_M_InOutLine inOutLine : inOutLines)
		{
			removeInOutLineFromDesadv(inOutLine);
		}

		inOut.setEDI_Desadv_ID(0);
		InterfaceWrapperHelper.save(inOut);
	}

	@Override
	public void removeInOutLineFromDesadv(@NonNull final I_M_InOutLine inOutLineRecord)
	{
		if (inOutLineRecord.getEDI_DesadvLine_ID() <= 0)
		{
			return;
		}

		ediDesadvPackService.removePackAndItemRecords(inOutLineRecord);

		final I_EDI_DesadvLine desadvLineRecord = inOutLineRecord.getEDI_DesadvLine();

		final StockQtyAndUOMQty inOutLineQty = inOutBL.extractInOutLineQty(
				inOutLineRecord,
				InvoicableQtyBasedOn.ofNullableCodeOrNominal(desadvLineRecord.getInvoicableQtyBasedOn()));

		addOrSubtractInOutLineQty(desadvLineRecord, inOutLineQty, null/*orderLine*/, false/* add=false, i.e. subtract */);
		InterfaceWrapperHelper.save(desadvLineRecord);

		inOutLineRecord.setEDI_DesadvLine_ID(0);
		InterfaceWrapperHelper.save(inOutLineRecord);
	}

	@VisibleForTesting
	void addOrSubtractInOutLineQty(
			@NonNull final I_EDI_DesadvLine desadvLineRecord,
			@NonNull final StockQtyAndUOMQty inOutLineQty,
			@Nullable final I_C_OrderLine orderLine,
			final boolean add)
	{
		final StockQtyAndUOMQty inOutLineQtyEff = inOutLineQty.negateIfNot(add);

		final Quantity inOutLineStockQty = inOutLineQtyEff.getStockQty();
		final BigDecimal oldMovementQtyInStockUOM = desadvLineRecord.getQtyDeliveredInStockingUOM();
		final BigDecimal newMovementQty = desadvLineRecord.getQtyDeliveredInStockingUOM().add(inOutLineStockQty.toBigDecimal());
		desadvLineRecord.setQtyDeliveredInStockingUOM(newMovementQty);

		final Quantity desadvLineQtyDelivered = Quantitys.of(desadvLineRecord.getQtyDeliveredInUOM(), UomId.ofRepoId(desadvLineRecord.getC_UOM_ID()));
		final Quantity newQtyDeliveredInUOM = addInOutLineQtyToDesadvLineQty(inOutLineQtyEff, desadvLineQtyDelivered, desadvLineRecord);
		desadvLineRecord.setQtyDeliveredInUOM(newQtyDeliveredInUOM.toBigDecimal());

		final Optional<BigDecimal> newQtyEnteredInBPartnerUOM = orderLine != null
				? computeDeliveredQtyInBPartnerUOM(orderLine, desadvLineRecord)
				: computeDeliveredQtyInBPartnerUOM(desadvLineRecord, oldMovementQtyInStockUOM);

		newQtyEnteredInBPartnerUOM.ifPresent(desadvLineRecord::setQtyEnteredInBPartnerUOM);

		// convert the delivered qty (which *might* also be in catch-weight!) to the invoicing-UOM
		final UomId invoiceUomId = UomId.ofRepoIdOrNull(desadvLineRecord.getC_UOM_Invoice_ID());
		if (invoiceUomId != null)
		{
			final Quantity desadvLineQtyInInvoiceUOM = Quantitys.of(desadvLineRecord.getQtyDeliveredInInvoiceUOM(), UomId.ofRepoId(desadvLineRecord.getC_UOM_Invoice_ID()));
			final Quantity newQtyDeliveredInInvoiceUOM = addInOutLineQtyToDesadvLineQty(inOutLineQtyEff, desadvLineQtyInInvoiceUOM, desadvLineRecord);
			desadvLineRecord.setQtyDeliveredInInvoiceUOM(newQtyDeliveredInInvoiceUOM.toBigDecimal());
		}

		// update header record
		final I_EDI_Desadv desadvRecord = desadvLineRecord.getEDI_Desadv();
		final BigDecimal newSumDeliveredInStockingUOM = desadvRecord
				.getSumDeliveredInStockingUOM()
				.add(inOutLineStockQty.toBigDecimal());
		desadvRecord.setSumDeliveredInStockingUOM(newSumDeliveredInStockingUOM);
		updateFullfilmentPercent(desadvRecord);

		saveRecord(desadvRecord);
	}

	private Quantity addInOutLineQtyToDesadvLineQty(
			@NonNull final StockQtyAndUOMQty inOutLineQty,
			@NonNull final Quantity desadvLineQtyToAugment,
			@NonNull final I_EDI_DesadvLine desadvLineRecord)
	{
		final Quantity inOutLineStockQty = inOutLineQty.getStockQty();
		final Quantity inOutLineLineQtyDelivered = inOutLineQty.getUOMQtyNotNull();

		final Quantity augentQtyDeliveredInUOM;

		final boolean desadvLineQtyIsUOMForTUS = uomDAO.isUOMForTUs(desadvLineQtyToAugment.getUomId());
		final boolean inOutLineQtyIsUOMForTUS = uomDAO.isUOMForTUs(inOutLineLineQtyDelivered.getUomId());
		if (desadvLineQtyIsUOMForTUS && !inOutLineQtyIsUOMForTUS)
		{
			// we need to take inOutLineStockQty and convert it to "TU-UOM" by using the itemCapacity
			final BigDecimal cusPerTU = desadvLineRecord.getQtyItemCapacity();
			if (cusPerTU.signum() <= 0)
			{
				throw new AdempiereException("desadvLineRecord with TU-UOM C_UOM_ID=" + desadvLineQtyToAugment.getUomId().getRepoId() + " needs to have a QtyItemCapacity in order to convert the quantity")
						.appendParametersToMessage().setParameter("desadvLineRecord", desadvLineRecord);
			}
			augentQtyDeliveredInUOM = Quantitys.of(
					inOutLineStockQty.toBigDecimal().divide(cusPerTU, RoundingMode.CEILING),
					desadvLineQtyToAugment.getUomId());
		}
		else if (!desadvLineQtyIsUOMForTUS && inOutLineQtyIsUOMForTUS)
		{
			// we again need to take inOutLineStockQty and this time convert is to desadvLineQtyDelivered's UOM via uom-conversion
			augentQtyDeliveredInUOM = inOutLineStockQty;
		}
		else
		{
			// if bot are TU or both are not, then uom-conversion will work fine
			augentQtyDeliveredInUOM = inOutLineLineQtyDelivered;
		}

		final UOMConversionContext conversionCtx = UOMConversionContext.of(desadvLineRecord.getM_Product_ID());

		final Quantity newQtyDeliveredInUOM = Quantitys
				.add(conversionCtx,
					 desadvLineQtyToAugment,
					 augentQtyDeliveredInUOM);
		return newQtyDeliveredInUOM;
	}

	@Override
	public void removeOrderFromDesadv(@NonNull final I_C_Order order)
	{
		if (order.getEDI_Desadv_ID() <= 0)
		{
			return;
		}

		final I_EDI_Desadv desadv = order.getEDI_Desadv();

		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(order, I_C_OrderLine.class);
		for (final I_C_OrderLine orderLine : orderLines)
		{
			removeOrderLineFromDesadv(orderLine);
		}

		order.setEDI_Desadv_ID(0);
		InterfaceWrapperHelper.save(order);

		if (!desadvDAO.hasDesadvLines(desadv)
				&& !desadvDAO.hasOrders(desadv)
			/* && !desadvDAO.hasInOuts(desadv) delete, even if there are by some constellation inouts left */
		)
		{
			InterfaceWrapperHelper.delete(desadv);
		}
	}

	@Override
	public void removeOrderLineFromDesadv(@NonNull final I_C_OrderLine orderLine)
	{
		if (orderLine.getEDI_DesadvLine_ID() <= 0)
		{
			return;
		}

		final I_EDI_DesadvLine desadvLine = orderLine.getEDI_DesadvLine();

		final I_EDI_Desadv desadvRecord = desadvLine.getEDI_Desadv();
		final BigDecimal sumOrderedInStockingUOM = desadvRecord.getSumOrderedInStockingUOM().subtract(orderLine.getQtyOrdered());
		desadvRecord.setSumOrderedInStockingUOM(sumOrderedInStockingUOM);
		updateFullfilmentPercent(desadvRecord);
		saveRecord(desadvRecord);

		if (desadvDAO.hasInOutLines(desadvLine))
		{
			// not supposed to happen because when we get here, there should be no iol at all, or it's inout should have been reversed and in that case, the iol was already detached by
			// removeInOutLineFromDesadv.
		}

		orderLine.setEDI_DesadvLine_ID(0);
		InterfaceWrapperHelper.save(orderLine);

		if (!desadvDAO.hasOrderLines(desadvLine) && !desadvDAO.hasInOutLines(desadvLine))
		{
			InterfaceWrapperHelper.delete(desadvLine);
		}
	}

	private void updateFullfilmentPercent(@NonNull final I_EDI_Desadv desadvRecord)
	{
		// If we have 99.9, then the result which the user sees shall be 99, not 100. That way it's much more clear to the user.
		final RoundingMode roundTofloor = RoundingMode.FLOOR;

		final Percent fullfilment = Percent.of(
				desadvRecord.getSumDeliveredInStockingUOM(),
				desadvRecord.getSumOrderedInStockingUOM(),
				0/* precision */,
				roundTofloor);
		desadvRecord.setFulfillmentPercent(fullfilment.toBigDecimal());
	}

	@Override
	public ReportResultData printSSCC18_Labels(
			@NonNull final Properties ctx,
			@NonNull final Collection<EDIDesadvPackId> desadvPack_IDs_ToPrint)
	{
		Check.assumeNotEmpty(desadvPack_IDs_ToPrint, "desadvPack_IDs_ToPrint not empty");

		//
		// Create the process info based on AD_Process and AD_PInstance
		final ProcessExecutionResult result = ProcessInfo.builder()
				.setCtx(ctx)
				.setAD_ProcessByValue(AD_PROCESS_VALUE_EDI_DesadvLine_SSCC_Print)
				//
				// Parameter: REPORT_SQL_QUERY: provide a different report query which will select from our datasource instead of using the standard query (which is M_HU_ID based).
				.addParameter(ReportConstants.REPORT_PARAM_SQL_QUERY, "select * from report.fresh_EDI_DesadvLine_SSCC_Label_Report"
						+ " where AD_PInstance_ID=" + ReportConstants.REPORT_PARAM_SQL_QUERY_AD_PInstance_ID_Placeholder + " "
						+ " order by EDI_DesadvLine_SSCC_ID")
				//
				// Execute the actual printing process
				.buildAndPrepareExecution()
				.onErrorThrowException()
				// Create a selection with the EDI_DesadvLine_Pack_IDs that we need to print.
				// The report will fetch it from selection.
				.callBefore(pi -> DB.createT_Selection(pi.getPinstanceId(), desadvPack_IDs_ToPrint, ITrx.TRXNAME_ThreadInherited))
				.executeSync()
				.getResult();

		return result.getReportData();
	}

	@Override
	public void setMinimumPercentage(@NonNull final I_EDI_Desadv desadv)
	{
		final BigDecimal minimumPercentageAccepted = desadvDAO.retrieveMinimumSumPercentage();
		desadv.setFulfillmentPercentMin(minimumPercentageAccepted);
	}

	@Override
	public ImmutableList<ITranslatableString> createMsgsForDesadvsBelowMinimumFulfilment(@NonNull final ImmutableList<I_EDI_Desadv> desadvsRecords)
	{
		final ImmutableList.Builder<ITranslatableString> result = ImmutableList.builder();
		final ImmutableListMultimap<BigDecimal, I_EDI_Desadv> minimum2DesadvRecords = Multimaps.index(desadvsRecords, I_EDI_Desadv::getFulfillmentPercentMin);
		for (final BigDecimal minimumSumPercentage : minimum2DesadvRecords.keySet())
		{
			createSingleMsg(desadvsRecords, minimumSumPercentage).ifPresent(result::add);
		}
		return result.build();
	}

	@Override
	public List<I_M_InOutLine> retrieveAllInOutLines(final I_EDI_DesadvLine desadvLine)
	{
		return desadvDAO.retrieveAllInOutLines(desadvLine);
	}

	private Optional<ITranslatableString> createSingleMsg(
			@NonNull final List<I_EDI_Desadv> desadvsToSkip,
			@NonNull final BigDecimal minimumSumPercentage)
	{
		final StringBuilder skippedDesadvsString = new StringBuilder();

		for (final I_EDI_Desadv desadvRecord : desadvsToSkip)
		{
			if (desadvRecord.getFulfillmentPercent().compareTo(desadvRecord.getFulfillmentPercentMin()) <= 0)
			{
				skippedDesadvsString.append("#")
						.append(desadvRecord.getDocumentNo())
						.append(" - ")
						.append(desadvRecord.getFulfillmentPercent())
						.append("\n");
			}
		}

		if (skippedDesadvsString.length() <= 0)
		{
			return Optional.empty(); // nothing to log
		}

		// log a message that includes all the skipped lines'documentNo and percentage
		final ITranslatableString msg = Services.get(IMsgBL.class).getTranslatableMsgText(
				MSG_EDI_DESADV_RefuseSending,
				minimumSumPercentage, skippedDesadvsString.toString());
		return Optional.of(msg);
	}

	@NonNull
	private Optional<BigDecimal> computeDeliveredQtyInBPartnerUOM(
			@NonNull final I_C_OrderLine orderLine,
			@NonNull final I_EDI_DesadvLine desadvLine)
	{
		if (desadvLine.getC_UOM_BPartner_ID() <= 0)
		{
			return Optional.empty();
		}

		final UomId stockUOMId = productBL.getStockUOMId(orderLine.getM_Product_ID());
		final I_C_UOM stockUOM = uomDAO.getById(stockUOMId);

		//dev-note: calculating deliveredQtyInBPartnerUOM using proportion to avoid missing UOM conversion between
		//BPartner_UOM_ID - which might not be considered at all in metas internal processing - and actual stock UOM
		final BigDecimal deliveredQtyInBPartnerUOM = desadvLine.getQtyDeliveredInStockingUOM()
				.multiply(orderLine.getQtyEnteredInBPartnerUOM())
				.divide(orderLine.getQtyOrdered(), stockUOM.getStdPrecision(), RoundingMode.HALF_UP);

		if (deliveredQtyInBPartnerUOM.signum() < 0)
		{
			return Optional.of(ZERO);
		}

		return Optional.of(deliveredQtyInBPartnerUOM);
	}

	private Optional<BigDecimal> computeDeliveredQtyInBPartnerUOM(
			@NonNull final I_EDI_DesadvLine desadvLine,
			@NonNull final BigDecimal oldMovementQtyInStockUOM)
	{
		if (desadvLine.getC_UOM_BPartner_ID() <= 0)
		{
			return Optional.empty();
		}

		if(desadvLine.getQtyEnteredInBPartnerUOM().signum() <= 0)
		{
			return Optional.of(ZERO); // return zero and don't run the risk of an ArithmeticException if both the new and old value are zero.
		}
		
		final UomId stockUOMId = productBL.getStockUOMId(desadvLine.getM_Product_ID());
		final I_C_UOM stockUOM = uomDAO.getById(stockUOMId);

		//dev-note: calculating deliveredQtyInBPartnerUOM using proportion to avoid missing UOM conversion between
		//BPartner_UOM_ID - which might not be considered at all in metas internal processing - and actual stock UOM
		final BigDecimal deliveredQtyInBPartnerUOM = desadvLine.getQtyEnteredInBPartnerUOM()
				.multiply(desadvLine.getQtyDeliveredInStockingUOM())
				.divide(oldMovementQtyInStockUOM, stockUOM.getStdPrecision(), RoundingMode.HALF_UP);

		if (deliveredQtyInBPartnerUOM.signum() < 0)
		{
			return Optional.of(ZERO);
		}

		return Optional.of(deliveredQtyInBPartnerUOM);
	}

	private static void setExternalBPartnerInfo(@NonNull final I_EDI_DesadvLine newDesadvLine, @NonNull final I_C_OrderLine orderLineRecord)
	{
		newDesadvLine.setExternalSeqNo(orderLineRecord.getExternalSeqNo());
		newDesadvLine.setC_UOM_BPartner_ID(orderLineRecord.getC_UOM_BPartner_ID());
		newDesadvLine.setQtyEnteredInBPartnerUOM(ZERO);
		newDesadvLine.setBPartner_QtyItemCapacity(orderLineRecord.getBPartner_QtyItemCapacity());
	}
}
