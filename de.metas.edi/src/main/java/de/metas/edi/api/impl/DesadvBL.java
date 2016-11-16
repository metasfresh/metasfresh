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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.DB;

import de.metas.adempiere.report.jasper.JasperConstants;
import de.metas.adempiere.service.IOrderBL;
import de.metas.adempiere.service.IOrderDAO;
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
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.attributes.sscc18.ISSCC18CodeDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.inout.IInOutDAO;
import de.metas.interfaces.I_C_BPartner_Product;
import de.metas.process.ProcessInfo;
import de.metas.purchasing.api.IBPartnerProductDAO;

public class DesadvBL implements IDesadvBL
{
	/** Process used to print the {@link I_EDI_DesadvLine_SSCC}s labels */
	private static final String AD_PROCESS_VALUE_EDI_DesadvLine_SSCC_Print = "EDI_DesadvLine_SSCC_Print";

	@Override
	public I_EDI_Desadv addToDesadvCreateForOrderIfNotExist(final I_C_Order order)
	{
		Check.assumeNotEmpty(order.getPOReference(), "C_Order {} has a not-empty POReference", order);

		final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

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
		final IDesadvDAO desadvDAO = Services.get(IDesadvDAO.class);

		final I_EDI_DesadvLine existingDesadvLine = desadvDAO.retrieveMatchingDesadvLinevOrNull(desadv, orderLine.getLine());
		if (existingDesadvLine != null)
		{
			return existingDesadvLine; // done
		}

		final IBPartnerProductDAO bPartnerProductDAO = Services.get(IBPartnerProductDAO.class);

		final I_EDI_DesadvLine newDesadvLine = InterfaceWrapperHelper.newInstance(I_EDI_DesadvLine.class, order);
		newDesadvLine.setEDI_Desadv(desadv);
		newDesadvLine.setLine(orderLine.getLine());

		newDesadvLine.setQtyEntered(orderLine.getQtyEntered());
		newDesadvLine.setQtyDeliveredInUOM(BigDecimal.ZERO);
		newDesadvLine.setC_UOM_ID(orderLine.getC_UOM_ID());

		final BigDecimal orderLineItemCapacity = orderLine.getQtyItemCapacity();
		final BigDecimal lineItemCapacity;
		if (orderLineItemCapacity.signum() <= 0)
		{
			// task 09776
			final I_C_BPartner bpartner = InterfaceWrapperHelper.create(desadv.getC_BPartner(), I_C_BPartner.class);
			lineItemCapacity = bpartner.getEdiDESADVDefaultItemCapacity();
		}
		else
		{
			lineItemCapacity = orderLineItemCapacity;
		}
		newDesadvLine.setQtyItemCapacity(lineItemCapacity);

		newDesadvLine.setMovementQty(BigDecimal.ZERO);
		newDesadvLine.setM_Product_ID(orderLine.getM_Product_ID());

		newDesadvLine.setProductDescription(orderLine.getProductDescription());

		final I_C_BPartner_Product bPartnerProduct = InterfaceWrapperHelper.create(
				bPartnerProductDAO.retrieveBPartnerProductAssociation(order.getC_BPartner(), orderLine.getM_Product(), orderLine.getM_Product().getAD_Org_ID()),
				I_C_BPartner_Product.class);

		// don't throw an error for missing bPartnerProduct; it might prevent users from creating shipments
		// instead, just don't set the values and let the user fix it in the DESADV window later on
		// Check.assumeNotNull(bPartnerProduct, "there is a C_BPartner_Product for C_BPArtner {} and M_Product {}", inOut.getC_BPartner(), inOutLine.getM_Product());
		if (bPartnerProduct != null)
		{
			newDesadvLine.setProductNo(bPartnerProduct.getProductNo());
			newDesadvLine.setUPC(bPartnerProduct.getUPC());

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
			newDesadvLine.setProductDescription(orderLine.getM_Product().getName());
		}

		newDesadvLine.setIsSubsequentDeliveryPlanned(false); // the default

		InterfaceWrapperHelper.save(newDesadvLine);
		return newDesadvLine;
	}

	/**
	 * Sets the given line's <code>MovementQty</code> and <code>QtyDeliveredInUOM</code>.
	 *
	 * @param desadvLine
	 * @param newMovementQty
	 */
	private void setQty(final I_EDI_DesadvLine desadvLine, final BigDecimal newMovementQty)
	{
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(desadvLine);

		desadvLine.setMovementQty(newMovementQty);
		desadvLine.setQtyDeliveredInUOM(uomConversionBL.convertFromProductUOM(ctx, desadvLine.getM_Product(), desadvLine.getC_UOM(), newMovementQty));
	}

	private I_EDI_Desadv retrieveOrCreateDesadv(final I_C_Order order)
	{
		final IDesadvDAO desadvDAO = Services.get(IDesadvDAO.class);
		final IOrderBL orderBL = Services.get(IOrderBL.class);

		I_EDI_Desadv desadv = desadvDAO.retrieveMatchingDesadvOrNull(order.getPOReference(), InterfaceWrapperHelper.getContextAware(order));
		if (desadv == null)
		{
			desadv = InterfaceWrapperHelper.newInstance(I_EDI_Desadv.class, order);

			desadv.setPOReference(order.getPOReference());
			desadv.setC_BPartner(orderBL.getShipToPartner(order));
			desadv.setC_BPartner_Location(orderBL.getShipToLocation(order));

			desadv.setDateOrdered(order.getDateOrdered());
			desadv.setMovementDate(order.getDatePromised());
			desadv.setC_Currency_ID(order.getC_Currency_ID());
			desadv.setHandOver_Location_ID(order.getHandOver_Location_ID());
			desadv.setBill_Location(orderBL.getBillToLocation(order));
			InterfaceWrapperHelper.save(desadv);
		}
		return desadv;
	}

	@Override
	public I_EDI_Desadv addToDesadvCreateForInOutIfNotExist(final I_M_InOut inOut)
	{
		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
		final ISSCC18CodeDAO sscc18CodeDAO = Services.get(ISSCC18CodeDAO.class);
		final IHUAttributesDAO huAttributesDAO = Services.get(IHUAttributesDAO.class);
		final IDesadvDAO desadvDAO = Services.get(IDesadvDAO.class);

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

		final Properties ctx = InterfaceWrapperHelper.getCtx(inOut);

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
				final I_M_HU hu = topLevelHUs.get(0);
				desadvLine.setM_HU(hu);

				final I_M_Attribute sscc18Attribute = sscc18CodeDAO.retrieveSSCC18Attribute(ctx);

				final I_M_HU_Attribute sscc18HUAttribute = huAttributesDAO.retrieveAttribute(hu, sscc18Attribute);
				// don't throw an error; it might prevent users from creating shipments
				// Check.errorIf(sscc18HUAttribute == null, "M_HU {} has no SSCC18 attrbute (tried to retrieve with M_Attribute = {})", hu, sscc18Attribute);
				if (sscc18HUAttribute != null)
				{
					desadvLine.setIPA_SSCC18(sscc18HUAttribute.getValue());
					desadvLine.setIsManual_IPA_SSCC18(false);
				}
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

	@Override
	public void removeInOutFromDesadv(final I_M_InOut inOut)
	{
		if (inOut.getEDI_Desadv_ID() <= 0)
		{
			return;
		}

		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
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

	@Override
	public void removeOrderFromDesadv(I_C_Order order)
	{
		if (order.getEDI_Desadv_ID() <= 0)
		{
			return;
		}

		final IDesadvDAO desadvDAO = Services.get(IDesadvDAO.class);

		final I_EDI_Desadv desadv = order.getEDI_Desadv();

		final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
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
		final IDesadvDAO desadvDAO = Services.get(IDesadvDAO.class);

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
	public void printSSCC18_Labels(final Properties ctx, final Collection<Integer> desadvLineSSCC_IDs_ToPrint)
	{
		Check.assumeNotNull(ctx, "ctx not null");
		Check.assumeNotEmpty(desadvLineSSCC_IDs_ToPrint, "desadvLineSSCC_IDs_ToPrint not empty");

		//
		// Create the process info based on AD_Process and AD_PInstance
		ProcessInfo.builder()
				.setCtx(ctx)
				.setAD_ProcessByValue(AD_PROCESS_VALUE_EDI_DesadvLine_SSCC_Print)
				//
				// Parameter: REPORT_SQL_QUERY: provide a different report query which will select from our datasource instead of using the standard query (which is M_HU_ID based).
				.addParameter(JasperConstants.REPORT_PARAM_SQL_QUERY, "select * from report.fresh_EDI_DesadvLine_SSCC_Label_Report"
						+ " where AD_PInstance_ID=" + JasperConstants.REPORT_PARAM_SQL_QUERY_AD_PInstance_ID_Placeholder + " "
						+ " order by EDI_DesadvLine_SSCC_ID")
				//
				// Execute the actual printing process
				.buildAndPrepareExecution()
				.onErrorThrowException()
				// Create a selection with the EDI_DesadvLine_SSCC_IDs that we need to print.
				// The report will fetch it from selection.
				.callBefore(pi -> DB.createT_Selection(pi.getAD_PInstance_ID(), desadvLineSSCC_IDs_ToPrint, ITrx.TRXNAME_None))
				.executeSync();
	}

	@Override
	public void setMinimumPercentage(final I_EDI_Desadv desadv)
	{
		final BigDecimal minimumPercentageAccepted = Services.get(IDesadvDAO.class).retrieveMinimumSumPercentage();
		desadv.setEDI_DESADV_MinimumSumPercentage(minimumPercentageAccepted);
	}

}
