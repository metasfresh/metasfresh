package de.metas.edi.api.impl;

import static de.metas.util.Check.isEmpty;
import static java.math.BigDecimal.ONE;
import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.delete;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_M_Product;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

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
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.inout.IInOutDAO;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.organization.OrgId;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.report.ReportResultData;
import de.metas.report.server.ReportConstants;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;

@Service
public class DesadvBL implements IDesadvBL
{
	/** Process used to print the {@link I_EDI_DesadvLine_Pack}s labels */
	private static final String AD_PROCESS_VALUE_EDI_DesadvLine_SSCC_Print = "EDI_DesadvLine_SSCC_Print";

	private final transient IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
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

	// @VisibleForTesting
	public DesadvBL(@NonNull final HURepository huRepository)
	{
		this.huRepository = huRepository;
	}

	@Override
	public I_EDI_Desadv addToDesadvCreateForOrderIfNotExist(final I_C_Order order)
	{
		Check.assumeNotEmpty(order.getPOReference(), "C_Order {} has a not-empty POReference", order);

		final I_EDI_Desadv desadv = retrieveOrCreateDesadv(order);
		order.setEDI_Desadv(desadv);

		final List<I_C_OrderLine> orderLines = orderDAO.retrieveOrderLines(order, I_C_OrderLine.class);
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

			final I_EDI_DesadvLine desadvLine = retrieveOrCreateDesadvLine(order, desadv, orderLine);
			Check.errorIf(
					desadvLine.getM_Product_ID() != orderLine.getM_Product_ID(),
					"EDI_DesadvLine {} of EDI_Desadv {} has M_Product_ID {} and C_OrderLine {} of C_Order {} has M_Product_ID {}, but both have POReference {} and Line {} ",
					desadvLine, desadv, desadvLine.getM_Product_ID(),
					orderLine, order, orderLine.getM_Product_ID(),
					order.getPOReference(), orderLine.getLine());

			orderLine.setEDI_DesadvLine(desadvLine);
			InterfaceWrapperHelper.save(orderLine);
		}
		return desadv;
	}

	private I_EDI_DesadvLine retrieveOrCreateDesadvLine(
			final I_C_Order order,
			final I_EDI_Desadv desadv,
			final I_C_OrderLine orderLine)
	{
		final I_EDI_DesadvLine existingDesadvLine = desadvDAO.retrieveMatchingDesadvLinevOrNull(desadv, orderLine.getLine());
		if (existingDesadvLine != null)
		{
			return existingDesadvLine; // done
		}

		final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());
		final BPartnerId buyerBPartnerId = BPartnerId.ofRepoId(order.getC_BPartner_ID());
		final org.compiere.model.I_C_BPartner buyerBPartner = bpartnerDAO.getById(buyerBPartnerId);

		final I_EDI_DesadvLine newDesadvLine = InterfaceWrapperHelper.newInstance(I_EDI_DesadvLine.class, order);
		newDesadvLine.setEDI_Desadv(desadv);
		newDesadvLine.setLine(orderLine.getLine());

		newDesadvLine.setQtyEntered(orderLine.getQtyEntered());
		newDesadvLine.setC_UOM_ID(orderLine.getC_UOM_ID());

		newDesadvLine.setPriceActual(orderLine.getPriceActual());

		newDesadvLine.setQtyOrdered(orderLine.getQtyOrdered());
		newDesadvLine.setMovementQty(BigDecimal.ZERO);
		newDesadvLine.setM_Product_ID(productId.getRepoId());

		newDesadvLine.setProductDescription(orderLine.getProductDescription());

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
		final I_M_HU_PI_Item_Product materialItemProduct = extractHUPIItemProduct(order, orderLine);
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
		I_EDI_Desadv desadv = desadvDAO.retrieveMatchingDesadvOrNull(order.getPOReference(), InterfaceWrapperHelper.getContextAware(order));
		if (desadv == null)
		{
			desadv = InterfaceWrapperHelper.newInstance(I_EDI_Desadv.class, order);

			desadv.setPOReference(order.getPOReference());

			final BPartnerLocationId shipToBPLocationId = orderBL.getShipToLocationId(order);
			desadv.setC_BPartner_ID(shipToBPLocationId.getBpartnerId().getRepoId());
			desadv.setC_BPartner_Location_ID(shipToBPLocationId.getRepoId());

			desadv.setDateOrdered(order.getDateOrdered());
			desadv.setMovementDate(order.getDatePromised());
			desadv.setC_Currency_ID(order.getC_Currency_ID());
			desadv.setHandOver_Location_ID(order.getHandOver_Location_ID());
			desadv.setBill_Location_ID(BPartnerLocationId.toRepoId(orderBL.getBillToLocationIdOrNull(order)));
			InterfaceWrapperHelper.save(desadv);
		}
		return desadv;
	}

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

		final List<I_M_InOutLine> inOutLines = inOutDAO.retrieveLines(inOut, I_M_InOutLine.class);
		for (final I_M_InOutLine inOutLine : inOutLines)
		{
			if (inOutLine.getC_OrderLine_ID() <= 0)
			{
				continue;
			}
			addInOutLine(inOutLine);
		}
		return desadv;
	}

	@VisibleForTesting
	void addInOutLine(@NonNull final I_M_InOutLine inOutLineRecord)
	{
		final I_C_OrderLine orderLineRecord = InterfaceWrapperHelper.create(inOutLineRecord.getC_OrderLine(), I_C_OrderLine.class);
		final I_EDI_DesadvLine desadvLineRecord = orderLineRecord.getEDI_DesadvLine();

		final List<I_M_HU> topLevelHUs = huAssignmentDAO.retrieveTopLevelHUsForModel(inOutLineRecord);
		final ProductId productId = ProductId.ofRepoId(inOutLineRecord.getM_Product_ID());

		Quantity remainingQtyToAdd = Quantitys.create(inOutLineRecord.getMovementQty(), productId);
		for (final I_M_HU topLevelHU : topLevelHUs)
		{
			final Quantity packQty = addPackInfoToLineUsingHU(desadvLineRecord, inOutLineRecord, topLevelHU);
			remainingQtyToAdd = remainingQtyToAdd.subtract(packQty);
		}

		if (remainingQtyToAdd.signum() > 0)
		{
			addPackToLineUsingJustInOutLine(inOutLineRecord, orderLineRecord, desadvLineRecord, remainingQtyToAdd);
		}

		final BigDecimal newMovementQty = desadvLineRecord.getMovementQty().add(inOutLineRecord.getMovementQty());
		desadvLineRecord.setMovementQty(newMovementQty);
		InterfaceWrapperHelper.save(desadvLineRecord);

		inOutLineRecord.setEDI_DesadvLine_ID(desadvLineRecord.getEDI_DesadvLine_ID());
		InterfaceWrapperHelper.save(inOutLineRecord);
	}

	private void addPackToLineUsingJustInOutLine(
			@NonNull final I_M_InOutLine inOutLineRecord,
			@NonNull final I_C_OrderLine orderLineRecord,
			@NonNull final I_EDI_DesadvLine desadvLineRecord,
			@NonNull final Quantity qtyToAdd)
	{
		final ProductId productId = ProductId.ofRepoId(inOutLineRecord.getM_Product_ID());
		final I_C_Order orderRecord = create(orderLineRecord.getC_Order(), I_C_Order.class);
		final I_M_HU_PI_Item_Product tuPIItemProduct = extractHUPIItemProduct(orderRecord, orderLineRecord);

		final I_M_HU_LUTU_Configuration lutuConfiguration = lutuConfigurationFactory.createLUTUConfiguration(
				tuPIItemProduct,
				productId,
				qtyToAdd.getUOM(),
				BPartnerId.ofRepoId(orderRecord.getC_BPartner_ID()),
				false/* noLUForVirtualTU */);
		final int requiredLUQty = Math.max(
				lutuConfigurationFactory.calculateQtyLUForTotalQtyCUs(
						lutuConfiguration,
						qtyToAdd.toBigDecimal(),
						qtyToAdd.getUOM()),
				1 // if e.g. the tuPIItemProduct does not have an LU packing instruction
		);

		final Quantity maxQtyCUsPerLU = Quantity.of(
				lutuConfiguration.getQtyCU().multiply(lutuConfiguration.getQtyTU()),
				qtyToAdd.getUOM());

		Quantity remainingQty = qtyToAdd;

		for (int i = 0; i < requiredLUQty; i++)
		{
			final I_EDI_DesadvLine_Pack packRecord = createNewPackRecord(desadvLineRecord);
			packRecord.setQtyItemCapacity(lutuConfiguration.getQtyCU());
			packRecord.setM_InOut_ID(inOutLineRecord.getM_InOut_ID());
			packRecord.setM_InOutLine_ID(inOutLineRecord.getM_InOutLine_ID());

			// BestBefore
			final Optional<Timestamp> bestBeforeDate = extractBestBeforeDate(inOutLineRecord);
			bestBeforeDate.ifPresent(packRecord::setBestBeforeDate);

			// SSCC18
			final String sscc18 = computeSSCC18(OrgId.ofRepoId(inOutLineRecord.getAD_Org_ID()));
			packRecord.setIPA_SSCC18(sscc18);
			packRecord.setIsManual_IPA_SSCC18(true); // because the SSCC string is not coming from any M_HU

			// PackagingCodes
			final int packagingCodeLU_ID = tuPIItemProduct.getM_HU_PackagingCode_LU_Fallback_ID();
			packRecord.setM_HU_PackagingCode_LU_ID(packagingCodeLU_ID);

			final int packagingCodeTU_ID = tuPIItemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PackagingCode_ID();
			packRecord.setM_HU_PackagingCode_TU_ID(packagingCodeTU_ID);

			final Quantity qtyCUsPerCurrentLU = remainingQty.min(maxQtyCUsPerLU);

			final Quantity currentQtyTU = qtyCUsPerCurrentLU.divide(lutuConfiguration.getQtyCU(), 0, RoundingMode.UP);
			packRecord.setQtyTU(currentQtyTU.toBigDecimal().intValue());

			setQty(productId,
					packRecord,
					Quantitys.create(lutuConfiguration.getQtyCU(), productId),
					qtyCUsPerCurrentLU);

			saveRecord(packRecord);

			// prepare next iteration within this for-look
			remainingQty = remainingQty.subtract(qtyCUsPerCurrentLU);
		}
	}

	private String computeSSCC18(@NonNull final OrgId orgId)
	{
		final SSCC18 sscc18 = sscc18CodeService.generate(orgId);
		final String ipaSSCC18 = sscc18CodeService.toString(sscc18, false); // humanReadable=false
		return ipaSSCC18;
	}

	private Quantity addPackInfoToLineUsingHU(
			@NonNull final I_EDI_DesadvLine desadvLineRecord,
			@NonNull final I_M_InOutLine inOutLineRecord,
			@NonNull final I_M_HU huRecord)
	{
		final ProductId productId = ProductId.ofRepoId(desadvLineRecord.getM_Product_ID());

		final HU rootHU = huRepository
				.getById(HuId.ofRepoId(huRecord.getM_HU_ID()))
				.retainProduct(productId) // no need to blindly hope that the HU is homogenous
				.orElse(null);

		if (rootHU == null || !rootHU.getType().isLU())
		{
			return Quantitys.createZero(productId); // we don't do HU-related SSCC's if the HU is not a LU.
		}

		final I_EDI_DesadvLine_Pack packRecord = createNewPackRecord(desadvLineRecord);
		packRecord.setM_InOut_ID(inOutLineRecord.getM_InOut_ID());
		packRecord.setM_InOutLine_ID(inOutLineRecord.getM_InOutLine_ID());
		packRecord.setM_HU_ID(huRecord.getM_HU_ID());

		final Quantity qtyCUInStockUOM = rootHU.extractMedianCUQtyPerChildHU(productId);
		packRecord.setQtyItemCapacity(qtyCUInStockUOM.toBigDecimal());

		// get minimum best before
		final Date bestBefore = rootHU.extractSingleAttributeValue(
				attrSet -> attrSet.hasAttribute(AttributeConstants.ATTR_BestBeforeDate) ? attrSet.getValueAsDate(AttributeConstants.ATTR_BestBeforeDate) : null,
				(date1, date2) -> TimeUtil.min(date1, date2));
		packRecord.setBestBeforeDate(TimeUtil.asTimestamp(bestBefore));

		// SSCC18
		final String sscc18 = rootHU.getAttributes().getValueAsString(HUAttributeConstants.ATTR_SSCC18_Value);
		if (isEmpty(sscc18))
		{
			packRecord.setIsManual_IPA_SSCC18(true);
			final String onTheFlySSCC18 = computeSSCC18ForHUId(rootHU.getId());
			packRecord.setIPA_SSCC18(onTheFlySSCC18);
		}
		else
		{
			packRecord.setIPA_SSCC18(sscc18);
			packRecord.setIsManual_IPA_SSCC18(false);
		}

		final Optional<PackagingCode> packagingCode = rootHU.getPackagingCode();
		if (packagingCode.isPresent())
		{
			packRecord.setM_HU_PackagingCode_LU_ID(packagingCode.get().getId().getRepoId());

			final PackagingCode tuPackagingCode = CollectionUtils.extractSingleElementOrDefault(
					rootHU.getChildHUs(), // don't iterate all HUs; we just care for the level below our LU (aka TU level).
					hu -> hu.getPackagingCode().orElse(null),
					null);
			if (packagingCode != null)
			{
				packRecord.setM_HU_PackagingCode_TU_ID(tuPackagingCode.getId().getRepoId());
			}
		}
		packRecord.setQtyTU(rootHU.getChildHUs().size());

		final Quantity quantity = rootHU.getProductQuantities().get(productId);
		setQty(productId, packRecord, qtyCUInStockUOM, quantity);
		saveRecord(packRecord);

		return quantity;
	}

	private String computeSSCC18ForHUId(@NonNull final HuId huId)
	{
		final SSCC18 sscc18 = sscc18CodeService.generate(huId.getRepoId());
		final String ipaSSCC18 = sscc18CodeService.toString(sscc18, false); // humanReadable=false
		return ipaSSCC18;
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

		final I_EDI_DesadvLine desadvLine = inOutLineRecord.getEDI_DesadvLine();
		final BigDecimal newDesavLineQty = desadvLine.getMovementQty().subtract(inOutLineRecord.getMovementQty());
		desadvLine.setMovementQty(newDesavLineQty);
		InterfaceWrapperHelper.save(desadvLine);

		inOutLineRecord.setEDI_DesadvLine_ID(0);
		InterfaceWrapperHelper.save(inOutLineRecord);
	}

	/**
	 * Sets the given line's <code>MovementQty</code> and <code>QtyDeliveredInUOM</code>.
	 */
	@VisibleForTesting
	void setQty(
			@NonNull final ProductId productId,
			@NonNull final I_EDI_DesadvLine_Pack packrecord,
			@NonNull final Quantity qtyCUInStockUom,
			@NonNull final Quantity qtyCUsPerLUInStockUom)
	{
		final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
		final UomId uomId = UomId.ofRepoId(packrecord.getC_UOM_ID());

		final BigDecimal qtyCUinPackUOM;

		final BigDecimal qtyCUsPerLUinPackUOM;
		if (uomDAO.isUOMForTUs(uomId))
		{
			qtyCUinPackUOM = ONE;

			qtyCUsPerLUinPackUOM = qtyCUsPerLUInStockUom
					.toBigDecimal()
					.divide(packrecord.getQtyItemCapacity(), 0/* result's scale */, RoundingMode.UP);
		}
		else
		{
			qtyCUinPackUOM = uomConversionBL
					.convertQuantityTo(
							qtyCUInStockUom,
							UOMConversionContext.of(productId),
							uomId)
					.toBigDecimal();

			qtyCUsPerLUinPackUOM = uomConversionBL
					.convertQuantityTo(
							qtyCUsPerLUInStockUom,
							UOMConversionContext.of(productId),
							uomId)
					.toBigDecimal();
		}

		packrecord.setMovementQty(qtyCUsPerLUInStockUom.toBigDecimal());
		packrecord.setQtyCU(qtyCUinPackUOM);
		packrecord.setQtyCUsPerLU(qtyCUsPerLUinPackUOM);
	}

	@Override
	public void removeOrderFromDesadv(I_C_Order order)
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
	public void removeOrderLineFromDesadv(final I_C_OrderLine orderLine)
	{
		if (orderLine.getEDI_DesadvLine_ID() <= 0)
		{
			return;
		}

		final I_EDI_DesadvLine desadvLine = orderLine.getEDI_DesadvLine();

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

		return ReportResultData.builder()
				.reportData(result.getReportData())
				.reportFilename(result.getReportFilename())
				.reportContentType(result.getReportContentType())
				.build();
	}

	@Override
	public void setMinimumPercentage(final I_EDI_Desadv desadv)
	{
		final BigDecimal minimumPercentageAccepted = desadvDAO.retrieveMinimumSumPercentage();
		desadv.setEDI_DESADV_MinimumSumPercentage(minimumPercentageAccepted);
	}
}
