package de.metas.edi.api.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner_product.IBPartnerProductDAO;
import de.metas.edi.api.IDesadvBL;
import de.metas.edi.api.IDesadvDAO;
import de.metas.edi.model.I_C_Order;
import de.metas.edi.model.I_C_OrderLine;
import de.metas.edi.model.I_M_InOut;
import de.metas.edi.model.I_M_InOutLine;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.esb.edi.model.I_EDI_DesadvLine_Pack;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.attributes.sscc18.ISSCC18CodeBL;
import de.metas.handlingunits.attributes.sscc18.SSCC18;
import de.metas.handlingunits.generichumodel.HU;
import de.metas.handlingunits.generichumodel.HURepository;
import de.metas.handlingunits.generichumodel.PackagingCode;
import de.metas.handlingunits.inout.IHUPackingMaterialDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
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
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.report.ReportResultData;
import de.metas.report.server.ReportConstants;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_M_Product;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static de.metas.common.util.CoalesceUtil.coalesce;
import static de.metas.util.Check.isEmpty;
import static de.metas.util.Check.isNotBlank;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.delete;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Service
public class DesadvBL implements IDesadvBL
{
	private final static transient Logger logger = LogManager.getLogger(DesadvBL.class);

	private static final AdMessageKey MSG_EDI_DESADV_RefuseSending = AdMessageKey.of("EDI_DESADV_RefuseSending");

	/**
	 * Process used to print the {@link I_EDI_DesadvLine_Pack}s labels
	 */
	private static final String AD_PROCESS_VALUE_EDI_DesadvLine_SSCC_Print = "EDI_DesadvLine_SSCC_Print";

	private final transient IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final transient IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final transient IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
	private final transient IDesadvDAO desadvDAO = Services.get(IDesadvDAO.class);
	private final transient IProductDAO productDAO = Services.get(IProductDAO.class);
	private final transient IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final transient IBPartnerProductDAO bPartnerProductDAO = Services.get(IBPartnerProductDAO.class);
	private final transient IHUPIItemProductDAO hupiItemProductDAO = Services.get(IHUPIItemProductDAO.class);
	private final transient IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final transient IOrderBL orderBL = Services.get(IOrderBL.class);
	private final transient IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final transient IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final transient ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
	private final transient ISSCC18CodeBL sscc18CodeService = Services.get(ISSCC18CodeBL.class);
	private final transient HURepository huRepository;
	private final transient IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final transient IBPartnerProductDAO partnerProductDAO = Services.get(IBPartnerProductDAO.class);
	private final IHUPackingMaterialDAO packingMaterialDAO = Services.get(IHUPackingMaterialDAO.class);

	// @VisibleForTesting
	public DesadvBL(@NonNull final HURepository huRepository)
	{
		this.huRepository = huRepository;
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
			final I_C_Order orderRecord,
			final I_EDI_Desadv desadvRecord,
			final I_C_OrderLine orderLineRecord)
	{
		final I_EDI_DesadvLine existingDesadvLine = desadvDAO.retrieveMatchingDesadvLinevOrNull(desadvRecord, orderLineRecord.getLine());
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
		final I_M_HU_PI_Item_Product materialItemProduct = extractHUPIItemProduct(orderRecord, orderLineRecord);
		if (materialItemProduct != null)
		{
			newDesadvLine.setGTIN(materialItemProduct.getGTIN());
			newDesadvLine.setUPC_TU(materialItemProduct.getUPC());
			newDesadvLine.setEAN_TU(materialItemProduct.getEAN_TU());
		}
		newDesadvLine.setIsSubsequentDeliveryPlanned(false); // the default

		InterfaceWrapperHelper.save(newDesadvLine);
		return newDesadvLine;
	}

	private I_M_HU_PI_Item_Product extractHUPIItemProduct(final I_C_Order order, final I_C_OrderLine orderLine)
	{
		final I_M_HU_PI_Item_Product materialItemProduct;
		if (orderLine.getM_HU_PI_Item_Product_ID() > 0)
		{
			materialItemProduct = orderLine.getM_HU_PI_Item_Product();
		}
		else
		{
			final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());
			final BPartnerId buyerBPartnerId = BPartnerId.ofRepoId(order.getC_BPartner_ID());

			materialItemProduct = hupiItemProductDAO.retrieveMaterialItemProduct(
					productId,
					buyerBPartnerId,
					TimeUtil.asZonedDateTime(order.getDateOrdered()),
					X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit, true/* allowInfiniteCapacity */);
		}
		return materialItemProduct;
	}

	private I_EDI_Desadv retrieveOrCreateDesadv(final I_C_Order order)
	{
		I_EDI_Desadv desadv = desadvDAO.retrieveMatchingDesadvOrNull(
				order.getPOReference(),
				InterfaceWrapperHelper.getContextAware(order));
		if (desadv == null)
		{
			desadv = InterfaceWrapperHelper.newInstance(I_EDI_Desadv.class, order);

			desadv.setPOReference(order.getPOReference());

			desadv.setC_BPartner_ID(order.getC_BPartner_ID());
			desadv.setC_BPartner_Location_ID(order.getC_BPartner_Location_ID());

			desadv.setDateOrdered(order.getDateOrdered());
			desadv.setMovementDate(order.getDatePromised());
			desadv.setC_Currency_ID(order.getC_Currency_ID());

			desadv.setHandOver_Partner_ID(order.getHandOver_Partner_ID());
			desadv.setHandOver_Location_ID(order.getHandOver_Location_ID());
			desadv.setDropShip_BPartner_ID(order.getDropShip_BPartner_ID());
			desadv.setDropShip_Location_ID(order.getDropShip_Location_ID());

			desadv.setBill_Location_ID(BPartnerLocationId.toRepoId(orderBL.getBillToLocationId(order)));
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
			desadv = desadvDAO.retrieveMatchingDesadvOrNull(inOut.getPOReference(), InterfaceWrapperHelper.getContextAware(inOut));
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

	@VisibleForTesting
	void addInOutLine(
			@NonNull final I_M_InOutLine inOutLineRecord,
			@NonNull final BPartnerId recipientBPartnerId)
	{
		final I_C_OrderLine orderLineRecord = InterfaceWrapperHelper.create(inOutLineRecord.getC_OrderLine(), I_C_OrderLine.class);
		final I_EDI_DesadvLine desadvLineRecord = orderLineRecord.getEDI_DesadvLine();

		final InvoicableQtyBasedOn invoicableQtyBasedOn = InvoicableQtyBasedOn.fromRecordString(desadvLineRecord.getInvoicableQtyBasedOn());
		final StockQtyAndUOMQty inOutLineQty = extractInOutLineQty(inOutLineRecord, invoicableQtyBasedOn);

		StockQtyAndUOMQty remainingQtyToAdd = inOutLineQty;
		// note that if inOutLineRecord has catch-weight, then logically we can't have HUs
		final List<I_M_HU> topLevelHUs = huAssignmentDAO.retrieveTopLevelHUsForModel(inOutLineRecord);
		for (final I_M_HU topLevelHU : topLevelHUs)
		{
			final StockQtyAndUOMQty addedPackQty = addPackRecordToLineUsingHU(desadvLineRecord, inOutLineRecord, topLevelHU, recipientBPartnerId);
			remainingQtyToAdd = StockQtyAndUOMQtys.subtract(remainingQtyToAdd, addedPackQty);
		}

		if (remainingQtyToAdd.getStockQty().signum() > 0)
		{
			addPackRecordsToLineUsingJustInOutLine(inOutLineRecord, orderLineRecord, desadvLineRecord, remainingQtyToAdd);
		}

		addOrSubtractInOutLineQty(desadvLineRecord, inOutLineQty, true/* add */);
		InterfaceWrapperHelper.save(desadvLineRecord);

		inOutLineRecord.setEDI_DesadvLine_ID(desadvLineRecord.getEDI_DesadvLine_ID());
		InterfaceWrapperHelper.save(inOutLineRecord);
	}

	private StockQtyAndUOMQty extractInOutLineQty(
			@NonNull final I_M_InOutLine inOutLineRecord,
			@NonNull final InvoicableQtyBasedOn invoicableQtyBasedOn)
	{
		switch (invoicableQtyBasedOn)
		{
			case CatchWeight:
				final StockQtyAndUOMQty stockQtyAndCatchQty = inOutBL.getStockQtyAndCatchQty(inOutLineRecord);
				if (stockQtyAndCatchQty.getUOMQtyOpt().isPresent())
				{
					return stockQtyAndCatchQty;
				}

				// fallback if the given iol simply doesn't have a catch weight (which is a common case)
				return inOutBL.getStockQtyAndQtyInUOM(inOutLineRecord);
			case NominalWeight:
				return inOutBL.getStockQtyAndQtyInUOM(inOutLineRecord);
			default:
				throw new AdempiereException("Unsupported invoicableQtyBasedOn=" + invoicableQtyBasedOn);
		}
	}

	private void addPackRecordsToLineUsingJustInOutLine(
			@NonNull final I_M_InOutLine inOutLineRecord,
			@NonNull final I_C_OrderLine orderLineRecord,
			@NonNull final I_EDI_DesadvLine desadvLineRecord,
			@NonNull final StockQtyAndUOMQty qtyToAdd)
	{
		Check.assume(qtyToAdd.getStockQty().signum() > 0, "Parameter 'qtyToAdd' needs to be >0 for all this to make sense");

		final ProductId productId = ProductId.ofRepoId(inOutLineRecord.getM_Product_ID());

		final I_C_Order orderRecord = create(orderLineRecord.getC_Order(), I_C_Order.class);
		final I_M_HU_PI_Item_Product tuPIItemProduct = extractHUPIItemProduct(orderRecord, orderLineRecord);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(orderRecord.getC_BPartner_ID());

		final I_M_HU_LUTU_Configuration lutuConfigurationInStockUOM = lutuConfigurationFactory.createLUTUConfiguration(
				tuPIItemProduct,
				productId,
				qtyToAdd.getStockQty().getUomId(),
				bpartnerId,
				false/* noLUForVirtualTU */);

		final StockQtyAndUOMQty maxQtyCUsPerLU;
		final int requiredLUCount;
		final boolean configCapacityUnspecified = lutuConfigurationInStockUOM.isInfiniteQtyTU() || lutuConfigurationInStockUOM.isInfiniteQtyCU();
		if (configCapacityUnspecified)
		{
			maxQtyCUsPerLU = StockQtyAndUOMQtys.createConvert(
					qtyToAdd.getStockQty(),
					productId,
					qtyToAdd.getUOMQtyNotNull().getUomId());

			requiredLUCount = 1;
		}
		else
		{
			maxQtyCUsPerLU = StockQtyAndUOMQtys.createConvert(
					lutuConfigurationInStockUOM.getQtyCU().multiply(lutuConfigurationInStockUOM.getQtyTU()),
					productId,
					qtyToAdd.getUOMQtyNotNull().getUomId());

			requiredLUCount = lutuConfigurationFactory.calculateQtyLUForTotalQtyCUs(
					lutuConfigurationInStockUOM,
					qtyToAdd.getUOMQtyNotNull());
		}

		final Quantity qtyCUsPerTUInStockUOM;
		if (lutuConfigurationInStockUOM.isInfiniteQtyCU())
		{
			qtyCUsPerTUInStockUOM = qtyToAdd.getStockQty();
		}
		else
		{
			qtyCUsPerTUInStockUOM = Quantitys.create(lutuConfigurationInStockUOM.getQtyCU(), qtyToAdd.getStockQty().getUomId());
		}

		StockQtyAndUOMQty remainingQty = qtyToAdd;

		for (int i = 0; i < requiredLUCount; i++)
		{
			final I_EDI_DesadvLine_Pack packRecord = createNewPackRecord(desadvLineRecord);
			packRecord.setQtyItemCapacity(lutuConfigurationInStockUOM.getQtyCU());
			packRecord.setM_InOut_ID(inOutLineRecord.getM_InOut_ID());
			packRecord.setM_InOutLine_ID(inOutLineRecord.getM_InOutLine_ID());

			// BestBefore
			final Optional<Timestamp> bestBeforeDate = extractBestBeforeDate(inOutLineRecord);
			bestBeforeDate.ifPresent(packRecord::setBestBeforeDate);

			// Lot
			final Optional<String> lotNumber = extractLotNumber(inOutLineRecord);
			lotNumber.ifPresent(packRecord::setLotNumber);

			// SSCC18
			final String sscc18 = computeSSCC18(OrgId.ofRepoId(inOutLineRecord.getAD_Org_ID()));
			packRecord.setIPA_SSCC18(sscc18);
			packRecord.setIsManual_IPA_SSCC18(true); // because the SSCC string is not coming from any M_HU

			// PackagingCodes and PackagingGTINs
			final int packagingCodeLU_ID = tuPIItemProduct.getM_HU_PackagingCode_LU_Fallback_ID();
			packRecord.setM_HU_PackagingCode_LU_ID(packagingCodeLU_ID);
			packRecord.setGTIN_LU_PackingMaterial(tuPIItemProduct.getGTIN_LU_PackingMaterial_Fallback());

			final int packagingCodeTU_ID = tuPIItemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PackagingCode_ID();
			packRecord.setM_HU_PackagingCode_TU_ID(packagingCodeTU_ID);

			final List<I_M_HU_PackingMaterial> huPackingMaterials = packingMaterialDAO.retrievePackingMaterials(tuPIItemProduct);
			if (huPackingMaterials.size() == 1)
			{
				final I_C_BPartner_Product bPartnerProductRecord = partnerProductDAO
						.retrieveBPartnerProductAssociation(Env.getCtx(),
								bpartnerId,
								ProductId.ofRepoId(huPackingMaterials.get(0).getM_Product_ID()),
								OrgId.ofRepoId(desadvLineRecord.getAD_Org_ID()));
				if (bPartnerProductRecord != null && isNotBlank(bPartnerProductRecord.getGTIN()))
				{
					packRecord.setGTIN_TU_PackingMaterial(bPartnerProductRecord.getGTIN());
				}
			}
			else
			{
				logger.debug("M_HU_PI_Item_Product_ID={} has {} M_HU_PackingMaterials; -> skip setting GTIN_TU_PackingMaterial to EDI_DesadvLine_Pack_ID={}",
						tuPIItemProduct.getM_HU_PI_Item_Product_ID(), huPackingMaterials.size(), packRecord.getEDI_DesadvLine_Pack_ID());
			}

			final StockQtyAndUOMQty qtyCUsPerCurrentLU = remainingQty.min(maxQtyCUsPerLU);

			final Quantity currentQtyTU = qtyCUsPerCurrentLU.getStockQty().divide(qtyCUsPerTUInStockUOM.toBigDecimal(), 0, RoundingMode.UP);
			packRecord.setQtyTU(currentQtyTU.toBigDecimal().intValue());

			setQty(packRecord, productId, qtyCUsPerTUInStockUOM, qtyCUsPerCurrentLU);

			saveRecord(packRecord);

			// prepare next iteration within this for-look
			remainingQty = StockQtyAndUOMQtys.subtract(remainingQty, qtyCUsPerCurrentLU);
		}
	}

	private String computeSSCC18(@NonNull final OrgId orgId)
	{
		final SSCC18 sscc18 = sscc18CodeService.generate(orgId);
		return sscc18CodeService.toString(sscc18, false);
	}

	private StockQtyAndUOMQty addPackRecordToLineUsingHU(
			@NonNull final I_EDI_DesadvLine desadvLineRecord,
			@NonNull final I_M_InOutLine inOutLineRecord,
			@NonNull final I_M_HU huRecord,
			@NonNull final BPartnerId bPartnerId)
	{
		final ProductId productId = ProductId.ofRepoId(desadvLineRecord.getM_Product_ID());

		final HU rootHU = huRepository
				.getById(HuId.ofRepoId(huRecord.getM_HU_ID()))
				.retainProduct(productId) // no need to blindly hope that the HU is homogenous
				.orElse(null);

		if (rootHU == null || !rootHU.getType().isLU())
		{
			final UomId desadvUomId = UomId.ofRepoId(desadvLineRecord.getC_UOM_ID());
			return StockQtyAndUOMQtys.createZero(productId, desadvUomId); // we don't do HU-related stuffs if the HU is not a LU.
		}

		final I_EDI_DesadvLine_Pack packRecord = createNewPackRecord(desadvLineRecord);
		packRecord.setM_InOut_ID(inOutLineRecord.getM_InOut_ID());
		packRecord.setM_InOutLine_ID(inOutLineRecord.getM_InOutLine_ID());
		packRecord.setM_HU_ID(huRecord.getM_HU_ID());

		final Quantity qtyCUInStockUOM = rootHU.extractMedianCUQtyPerChildHU(productId);

		packRecord.setQtyItemCapacity(qtyCUInStockUOM.toBigDecimal());

		// get minimum best before of all HUs and sub-HUs
		final Date bestBefore = rootHU.extractSingleAttributeValue(
				attrSet -> attrSet.hasAttribute(AttributeConstants.ATTR_BestBeforeDate) ? attrSet.getValueAsDate(AttributeConstants.ATTR_BestBeforeDate) : null,
				TimeUtil::min);
		packRecord.setBestBeforeDate(TimeUtil.asTimestamp(bestBefore));

		// Lot
		final String lotNumber = rootHU.getAttributes().getValueAsString(AttributeConstants.ATTR_LotNumber);
		if (!isEmpty(lotNumber, true))
		{
			packRecord.setLotNumber(lotNumber);
		}

		// SSCC18
		final String sscc18 = rootHU.getAttributes().getValueAsString(HUAttributeConstants.ATTR_SSCC18_Value);
		if (isEmpty(sscc18, true))
		{
			packRecord.setIsManual_IPA_SSCC18(true);
			final String onTheFlySSCC18 = computeSSCC18ForHUId(rootHU.getOrgId(), rootHU.getId());
			packRecord.setIPA_SSCC18(onTheFlySSCC18);
		}
		else
		{
			packRecord.setIPA_SSCC18(sscc18);
			packRecord.setIsManual_IPA_SSCC18(false);
		}

		extractAndSetPackagingCodes(rootHU, packRecord);
		extractAndSetPackagingGTINs(rootHU, bPartnerId, packRecord);

		packRecord.setQtyTU(rootHU.getChildHUs().size());

		// note that rootHU only contains children, quantities and weights for productId
		final Quantity qtyInStockUOM = rootHU.getProductQtysInStockUOM().get(productId);
		final Optional<Quantity> weight = rootHU.getWeightNet();
		final StockQtyAndUOMQty quantity;
		if (weight.isPresent())
		{
			quantity = StockQtyAndUOMQty.builder()
					.productId(productId)
					.stockQty(qtyInStockUOM)
					.uomQty(weight.get())
					.build();
		}
		else
		{
			quantity = StockQtyAndUOMQtys.createConvert(
					qtyInStockUOM,
					productId,
					qtyInStockUOM.getUomId()); // don't try to convert to the pack's UOM! it might be a TU-uom
		}
		setQty(packRecord, productId, qtyCUInStockUOM, quantity);
		saveRecord(packRecord);

		return quantity;
	}

	private void extractAndSetPackagingCodes(
			@NonNull final HU rootHU,
			@NonNull final I_EDI_DesadvLine_Pack packRecord)
	{
		final Optional<PackagingCode> packagingCode = rootHU.getPackagingCode();
		if (packagingCode.isPresent())
		{
			packRecord.setM_HU_PackagingCode_LU_ID(packagingCode.get().getId().getRepoId());
		}

		final PackagingCode tuPackagingCode = CollectionUtils.extractSingleElementOrDefault(
				rootHU.getChildHUs(), // don't iterate all HUs; we just care for the level below our LU (aka TU level).
				hu -> hu.getPackagingCode().orElse(PackagingCode.NONE), // don't use null because CollectionUtils runs with ImmutableList
				PackagingCode.NONE);
		if (!tuPackagingCode.isNone())
		{
			packRecord.setM_HU_PackagingCode_TU_ID(tuPackagingCode.getId().getRepoId());
		}
	}

	private void extractAndSetPackagingGTINs(
			@NonNull final HU rootHU,
			@NonNull BPartnerId bPartnerId,
			@NonNull final I_EDI_DesadvLine_Pack packRecord)
	{
		final String packagingGTIN = rootHU.getPackagingGTINs().get(bPartnerId);
		if (isNotBlank(packagingGTIN))
		{
			packRecord.setGTIN_LU_PackingMaterial(packagingGTIN);
		}

		final String tuPackagingGTIN = CollectionUtils.extractSingleElementOrDefault(
				rootHU.getChildHUs(), // don't iterate all HUs; we just care for the level below our LU (aka TU level).
				hu -> coalesce(hu.getPackagingGTINs().get(bPartnerId), ""),
				"");
		if (isNotBlank(tuPackagingGTIN))
		{
			packRecord.setGTIN_TU_PackingMaterial(tuPackagingGTIN);
		}

	}

	private String computeSSCC18ForHUId(@NonNull final OrgId orgId, @NonNull final HuId huId)
	{
		final SSCC18 sscc18 = sscc18CodeService.generate(orgId, huId.getRepoId());
		return sscc18CodeService.toString(sscc18, false);
	}

	private I_EDI_DesadvLine_Pack createNewPackRecord(@NonNull final I_EDI_DesadvLine desadvLineRecord)
	{
		final I_EDI_DesadvLine_Pack ssccRecord = newInstance(I_EDI_DesadvLine_Pack.class);
		ssccRecord.setEDI_DesadvLine_ID(desadvLineRecord.getEDI_DesadvLine_ID());
		ssccRecord.setEDI_Desadv_ID(desadvLineRecord.getEDI_Desadv_ID());
		ssccRecord.setC_UOM_ID(desadvLineRecord.getC_UOM_ID());

		return ssccRecord;
	}

	private Optional<Timestamp> extractBestBeforeDate(@NonNull final I_M_InOutLine inOutLineRecord)
	{
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(inOutLineRecord.getM_AttributeSetInstance_ID());
		final ImmutableAttributeSet attributeSet = attributeDAO.getImmutableAttributeSetById(asiId);
		if (attributeSet.hasAttribute(AttributeConstants.ATTR_BestBeforeDate))
		{
			final Date bestBeforeDate = attributeSet.getValueAsDate(AttributeConstants.ATTR_BestBeforeDate);
			return Optional.of(TimeUtil.asTimestamp(bestBeforeDate));
		}
		return Optional.empty();
	}

	private Optional<String> extractLotNumber(@NonNull final I_M_InOutLine inOutLineRecord)
	{
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(inOutLineRecord.getM_AttributeSetInstance_ID());
		final ImmutableAttributeSet attributeSet = attributeDAO.getImmutableAttributeSetById(asiId);
		if (attributeSet.hasAttribute(AttributeConstants.ATTR_LotNumber))
		{
			final String lotNumber = attributeSet.getValueAsString(AttributeConstants.ATTR_LotNumber);
			return Optional.of(lotNumber);
		}
		return Optional.empty();
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

		final List<I_EDI_DesadvLine_Pack> packRecords = desadvDAO.retrieveDesadvLinePackRecords(inOutLineRecord);
		for (final I_EDI_DesadvLine_Pack packRecord : packRecords)
		{
			delete(packRecord);
		}

		final I_EDI_DesadvLine desadvLineRecord = inOutLineRecord.getEDI_DesadvLine();

		final StockQtyAndUOMQty inOutLineQty = extractInOutLineQty(
				inOutLineRecord,
				InvoicableQtyBasedOn.fromRecordString(desadvLineRecord.getInvoicableQtyBasedOn()));

		addOrSubtractInOutLineQty(desadvLineRecord, inOutLineQty, false/* add=false, i.e. subtract */);
		InterfaceWrapperHelper.save(desadvLineRecord);

		inOutLineRecord.setEDI_DesadvLine_ID(0);
		InterfaceWrapperHelper.save(inOutLineRecord);
	}

	@VisibleForTesting
	void addOrSubtractInOutLineQty(
			@NonNull final I_EDI_DesadvLine desadvLineRecord,
			@NonNull final StockQtyAndUOMQty inOutLineQty,
			final boolean add)
	{
		final StockQtyAndUOMQty inOutLineQtyEff = inOutLineQty.negateIfNot(add);

		final Quantity inOutLineStockQty = inOutLineQtyEff.getStockQty();
		final BigDecimal newMovementQty = desadvLineRecord.getQtyDeliveredInStockingUOM().add(inOutLineStockQty.toBigDecimal());
		desadvLineRecord.setQtyDeliveredInStockingUOM(newMovementQty);

		final Quantity desadvLineQtyDelivered = Quantitys.create(desadvLineRecord.getQtyDeliveredInUOM(), UomId.ofRepoId(desadvLineRecord.getC_UOM_ID()));
		final Quantity newQtyDeliveredInUOM = addInOutLineQtyToDesadvLineQty(inOutLineQtyEff, desadvLineQtyDelivered, desadvLineRecord);
		desadvLineRecord.setQtyDeliveredInUOM(newQtyDeliveredInUOM.toBigDecimal());

		// convert the delivered qty (which *might* also be in catch-weight!) to the invoicing-UOM
		final UomId invoiceUomId = UomId.ofRepoIdOrNull(desadvLineRecord.getC_UOM_Invoice_ID());
		if (invoiceUomId != null)
		{
			final Quantity desadvLineQtyInInvoiceUOM = Quantitys.create(desadvLineRecord.getQtyDeliveredInInvoiceUOM(), UomId.ofRepoId(desadvLineRecord.getC_UOM_Invoice_ID()));
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
			augentQtyDeliveredInUOM = Quantitys.create(
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

	/**
	 * Sets the given packRecord's <code>MovementQty</code> and <code>QtyDeliveredInUOM</code>.
	 */
	@VisibleForTesting
	void setQty(
			@NonNull final I_EDI_DesadvLine_Pack packRecord,
			@NonNull final ProductId productId,
			@NonNull final Quantity qtyCUInStockUOM,
			@NonNull final StockQtyAndUOMQty qtyCUsPerLU)
	{
		final UomId packUomId = UomId.ofRepoId(packRecord.getC_UOM_ID());

		final BigDecimal qtyCUPerTUinPackUOM;
		final BigDecimal qtyCUsPerLUinPackUOM;
		if (uomDAO.isUOMForTUs(packUomId))
		{
			qtyCUPerTUinPackUOM = ONE; // we count in TUs, also on the CU-level

			qtyCUsPerLUinPackUOM = qtyCUsPerLU.getStockQty()
					.toBigDecimal()
					.divide(packRecord.getQtyItemCapacity(), 0/* result's scale */, RoundingMode.UP);
		}
		else
		{
			qtyCUPerTUinPackUOM = uomConversionBL
					.convertQuantityTo(
							qtyCUInStockUOM,
							UOMConversionContext.of(productId),
							packUomId)
					.toBigDecimal();

			qtyCUsPerLUinPackUOM = uomConversionBL
					.convertQuantityTo(
							qtyCUsPerLU.getUOMQtyNotNull(),
							UOMConversionContext.of(productId),
							packUomId)
					.toBigDecimal();
		}

		packRecord.setMovementQty(qtyCUsPerLU.getStockQty().toBigDecimal());
		packRecord.setQtyCU(qtyCUPerTUinPackUOM);
		packRecord.setQtyCUsPerLU(qtyCUsPerLUinPackUOM);
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
			@NonNull final Collection<Integer> desadvLinePack_IDs_ToPrint)
	{
		Check.assumeNotEmpty(desadvLinePack_IDs_ToPrint, "desadvLineSSCC_IDs_ToPrint not empty");

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
				.callBefore(pi -> DB.createT_Selection(pi.getPinstanceId(), desadvLinePack_IDs_ToPrint, ITrx.TRXNAME_ThreadInherited))
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
}
