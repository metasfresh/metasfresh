package de.metas.edi.api.impl;

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
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_M_Product;
import org.compiere.util.DB;
import org.compiere.util.TimeUtil;

import com.google.common.annotations.VisibleForTesting;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner_product.IBPartnerProductDAO;
import de.metas.edi.api.IDesadvBL;
import de.metas.edi.api.IDesadvDAO;
import de.metas.edi.model.I_C_BPartner;
import de.metas.edi.model.I_C_Order;
import de.metas.edi.model.I_C_OrderLine;
import de.metas.edi.model.I_M_InOut;
import de.metas.edi.model.I_M_InOutLine;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.esb.edi.model.I_EDI_DesadvLine_SSCC;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.attributes.sscc18.ISSCC18CodeDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.inout.IInOutDAO;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.organization.OrgId;
import de.metas.process.ProcessInfo;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.report.server.ReportConstants;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;

public class DesadvBL implements IDesadvBL
{
	/** Process used to print the {@link I_EDI_DesadvLine_SSCC}s labels */
	private static final String AD_PROCESS_VALUE_EDI_DesadvLine_SSCC_Print = "EDI_DesadvLine_SSCC_Print";

	private final transient IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final transient IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
	private final transient ISSCC18CodeDAO sscc18CodeDAO = Services.get(ISSCC18CodeDAO.class);
	private final transient IHUAttributesDAO huAttributesDAO = Services.get(IHUAttributesDAO.class);
	private final transient IDesadvDAO desadvDAO = Services.get(IDesadvDAO.class);
	private final transient IProductDAO productDAO = Services.get(IProductDAO.class);
	private final transient IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final transient IBPartnerProductDAO bPartnerProductDAO = Services.get(IBPartnerProductDAO.class);
	private final transient IHUPIItemProductDAO hupiItemProductDAO = Services.get(IHUPIItemProductDAO.class);
	private final transient IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final transient IOrderBL orderBL = Services.get(IOrderBL.class);
	private final transient IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

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
		BPartnerId buyerBPartnerId = BPartnerId.ofRepoId(order.getC_BPartner_ID());
		final org.compiere.model.I_C_BPartner buyerBPartner = bpartnerDAO.getById(buyerBPartnerId);

		final I_EDI_DesadvLine newDesadvLine = InterfaceWrapperHelper.newInstance(I_EDI_DesadvLine.class, order);
		newDesadvLine.setEDI_Desadv(desadv);
		newDesadvLine.setLine(orderLine.getLine());

		newDesadvLine.setQtyEntered(orderLine.getQtyEntered());
		newDesadvLine.setQtyDeliveredInUOM(BigDecimal.ZERO);
		newDesadvLine.setC_UOM_ID(orderLine.getC_UOM_ID());

		newDesadvLine.setPriceActual(orderLine.getPriceActual());

		final BigDecimal orderLineItemCapacity = orderLine.getQtyItemCapacity();
		final BigDecimal lineItemCapacity;
		if (orderLineItemCapacity.signum() <= 0)
		{
			// task 09776
			final I_C_BPartner bpartner = InterfaceWrapperHelper.create(desadv.getC_BPartner_ID(), I_C_BPartner.class);
			lineItemCapacity = bpartner.getEdiDESADVDefaultItemCapacity();
		}
		else
		{
			lineItemCapacity = orderLineItemCapacity;
		}
		newDesadvLine.setQtyItemCapacity(lineItemCapacity);

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
		final I_M_HU_PI_Item_Product materialItemProduct = hupiItemProductDAO.retrieveMaterialItemProduct(
				productId,
				buyerBPartnerId,
				TimeUtil.asZonedDateTime(order.getDateOrdered()),
				X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit, true/* allowInfiniteCapacity */);
		if (materialItemProduct != null)
		{
			newDesadvLine.setGTIN(materialItemProduct.getGTIN());
			newDesadvLine.setUPC_TU(materialItemProduct.getUPC());
			newDesadvLine.setEAN_TU(materialItemProduct.getEAN_TU());

			newDesadvLine.setM_HU_PackagingCode_TU_ID(materialItemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PackagingCode_ID());
			newDesadvLine.setM_HU_PackagingCode_LU_ID(materialItemProduct.getM_HU_PackagingCode_LU_Fallback_ID());
		}
		newDesadvLine.setIsSubsequentDeliveryPlanned(false); // the default

		InterfaceWrapperHelper.save(newDesadvLine);
		return newDesadvLine;
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
	public I_EDI_Desadv addToDesadvCreateForInOutIfNotExist(final I_M_InOut inOut)
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

			final I_EDI_DesadvLine desadvLine = InterfaceWrapperHelper.create(inOutLine.getC_OrderLine(), I_C_OrderLine.class).getEDI_DesadvLine();

			final List<I_M_HU> topLevelHUs = huAssignmentDAO.retrieveTopLevelHUsForModel(inOutLine);

			// don't throw an error; it might prevent users from creating shipments
			// Check.errorIf(topLevelHUs.size() != 1, "Expecting one top level HU for M_InOutLine {}, but got this: {}", inOutLine, topLevelHUs);
			if (topLevelHUs.size() == 1)
			{
				final I_M_HU hu = CollectionUtils.singleElement(topLevelHUs);
				desadvLine.setM_HU_ID(hu.getM_HU_ID());

				final AttributeId sscc18AttributeId = sscc18CodeDAO.retrieveSSCC18AttributeId();

				final I_M_HU_Attribute sscc18HUAttribute = huAttributesDAO.retrieveAttribute(hu, sscc18AttributeId);
				// don't throw an error; it might prevent users from creating shipments
				// Check.errorIf(sscc18HUAttribute == null, "M_HU {} has no SSCC18 attrbute (tried to retrieve with M_Attribute = {})", hu, sscc18Attribute);
				if (sscc18HUAttribute != null)
				{
					desadvLine.setIPA_SSCC18(sscc18HUAttribute.getValue());
					desadvLine.setIsManual_IPA_SSCC18(false);
				}

				final int packagingCodeLU_ID = extractPackagingCodeId(hu);
				desadvLine.setM_HU_PackagingCode_LU_ID(packagingCodeLU_ID);

				final int packagingCodeTU_ID = CollectionUtils.extractSingleElementOrDefault(
						huAssignmentDAO.retrieveTUHUsForModel(inOutLine),
						this::extractPackagingCodeId,
						-1);
				desadvLine.setM_HU_PackagingCode_TU_ID(packagingCodeTU_ID);
			}

			// check if we got the value
			if (Check.isEmpty(desadvLine.getIPA_SSCC18(), true))
			{
				desadvLine.setIsManual_IPA_SSCC18(true); // someone will need to enter a manual SSCC18
			}

			final BigDecimal newMovementQty = desadvLine.getMovementQty().add(inOutLine.getMovementQty());
			setQty(desadvLine, newMovementQty);
			InterfaceWrapperHelper.save(desadvLine);

			inOutLine.setEDI_DesadvLine_ID(desadvLine.getEDI_DesadvLine_ID());
			InterfaceWrapperHelper.save(inOutLine);
		}

		return desadv;
	}

	private int extractPackagingCodeId(@NonNull final I_M_HU hu)
	{
		return hu.getM_HU_PI_Version().getM_HU_PackagingCode_ID();
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
	public void removeInOutLineFromDesadv(I_M_InOutLine inOutLine)
	{
		if (inOutLine.getEDI_DesadvLine_ID() <= 0)
		{
			return;
		}

		final I_EDI_DesadvLine desadvLine = inOutLine.getEDI_DesadvLine();

		inOutLine.setEDI_DesadvLine_ID(0);
		InterfaceWrapperHelper.save(inOutLine);

		final BigDecimal newDesavLineQty = desadvLine.getMovementQty().subtract(inOutLine.getMovementQty());
		setQty(desadvLine, newDesavLineQty);
		InterfaceWrapperHelper.save(desadvLine);

	}

	/**
	 * Sets the given line's <code>MovementQty</code> and <code>QtyDeliveredInUOM</code>.
	 */
	@VisibleForTesting
	void setQty(final I_EDI_DesadvLine desadvLine, final BigDecimal newMovementQty)
	{
		desadvLine.setMovementQty(newMovementQty);

		final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

		final UomId uomId = UomId.ofRepoId(desadvLine.getC_UOM_ID());

		final BigDecimal qtyDeliveredInUOM;
		if (uomDAO.isUOMForTUs(uomId))
		{
			qtyDeliveredInUOM = newMovementQty
					.divide(desadvLine.getQtyItemCapacity(), RoundingMode.UP)
					.setScale(0, RoundingMode.UP);
		}
		else
		{
			final ProductId productId = ProductId.ofRepoId(desadvLine.getM_Product_ID());
			qtyDeliveredInUOM = uomConversionBL.convertFromProductUOM(
					productId,
					uomId,
					newMovementQty);

		}
		desadvLine.setQtyDeliveredInUOM(qtyDeliveredInUOM);
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
	public void printSSCC18_Labels(@NonNull final Properties ctx, final Collection<Integer> desadvLineSSCC_IDs_ToPrint)
	{
		Check.assumeNotEmpty(desadvLineSSCC_IDs_ToPrint, "desadvLineSSCC_IDs_ToPrint not empty");

		//
		// Create the process info based on AD_Process and AD_PInstance
		ProcessInfo.builder()
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
				// Create a selection with the EDI_DesadvLine_SSCC_IDs that we need to print.
				// The report will fetch it from selection.
				.callBefore(pi -> DB.createT_Selection(pi.getPinstanceId(), desadvLineSSCC_IDs_ToPrint, ITrx.TRXNAME_ThreadInherited))
				.executeSync();
	}

	@Override
	public void setMinimumPercentage(final I_EDI_Desadv desadv)
	{
		final BigDecimal minimumPercentageAccepted = desadvDAO.retrieveMinimumSumPercentage();
		desadv.setEDI_DESADV_MinimumSumPercentage(minimumPercentageAccepted);
	}
}
