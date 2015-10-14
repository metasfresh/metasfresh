package de.metas.invoicecandidate.spi.impl;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.POWrapper;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.MTable;
import org.compiere.util.Env;

import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.InvoiceCandidate_Constants;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.product.acct.api.IProductAcctDAO;
import de.metas.tax.api.ITaxBL;

/**
 * Creates {@link I_C_Invoice_Candidate} from {@link I_C_OLCand}.
 *
 * Please note:
 * <ul>
 * <li>only those {@link I_C_OLCand}s are handled which have {@link InvoiceCandidate_Constants#DATA_DESTINATION_INTERNAL_NAME} as their destination datasource
 * </ul>
 *
 * @author tsa
 *
 */
public class C_OLCand_Handler extends AbstractInvoiceCandidateHandler
{
	private final C_OLCand_HandlerDAO dao = new C_OLCand_HandlerDAO();

	@Override
	public String getSourceTable()
	{
		return I_C_OLCand.Table_Name;
	}

	@Override
	public List<I_C_Invoice_Candidate> createMissingCandidates(final Properties ctx, final int limit, final String trxName)
	{
		final List<I_C_Invoice_Candidate> result = new ArrayList<I_C_Invoice_Candidate>();

		final Iterator<I_C_OLCand> olCandidates = dao.retrieveMissingCandidates(ctx, limit, trxName);
		while (olCandidates.hasNext())
		{
			final I_C_OLCand olc = olCandidates.next();
			final I_C_Invoice_Candidate ic = createInvoiceCandidateForOLCand(olc);
			result.add(ic);
		}

		return result;
	}

	@Override
	public List<I_C_Invoice_Candidate> createCandidatesFor(final Object model)
	{
		final I_C_OLCand olc = InterfaceWrapperHelper.create(model, I_C_OLCand.class);

		return Collections.singletonList(createInvoiceCandidateForOLCand(olc));
	}

	private I_C_Invoice_Candidate createInvoiceCandidateForOLCand(final I_C_OLCand olc)
	{
		//
		// services
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
		final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(olc);
		final String trxName = InterfaceWrapperHelper.getTrxName(olc);

		Check.assume(Env.getAD_Client_ID(ctx) == olc.getAD_Client_ID(), "AD_Client_ID of {0} and of its Ctx are the same", olc);

		final I_C_Invoice_Candidate ic = POWrapper.create(ctx, I_C_Invoice_Candidate.class, trxName);

		// org id
		final int orgId = olc.getAD_Org_ID();
		ic.setAD_Org_ID(orgId);

		ic.setC_ILCandHandler_ID(getHandlerRecord().getC_ILCandHandler_ID());

		ic.setAD_Table_ID(adTableDAO.retrieveTableId(I_C_OLCand.Table_Name));
		ic.setRecord_ID(olc.getC_OLCand_ID());

		// product
		final int productId = olCandEffectiveValuesBL.getM_Product_Effective_ID(olc);
		ic.setM_Product_ID(productId);

		// charge
		final int chargeId = olc.getC_Charge_ID();
		ic.setC_Charge_ID(chargeId);

		ic.setQtyOrdered(olc.getQty());
		ic.setDateOrdered(olc.getDateCandidate());

		ic.setQtyToInvoice(Env.ZERO); // to be computed
		ic.setC_UOM_ID(olCandEffectiveValuesBL.getC_UOM_Effective_ID(olc));

		ic.setM_PricingSystem_ID(olc.getM_PricingSystem_ID());
		ic.setPriceActual(olc.getPriceActual());
		ic.setPrice_UOM_ID(olCandEffectiveValuesBL.getC_UOM_Effective_ID(olc)); // 07090 when we set PriceActual, we shall also set PriceUOM.

		ic.setPriceEntered(olc.getPriceEntered()); // cg : task 04917
		ic.setDiscount(olc.getDiscount());
		ic.setC_Currency_ID(olc.getC_Currency_ID());
		// ic.setC_ConversionType_ID(C_ConversionType_ID); // N/A

		ic.setBill_BPartner_ID(olCandEffectiveValuesBL.getBill_BPartner_Effective_ID(olc));

		// bill location
		final int billLocationId = olCandEffectiveValuesBL.getBill_Location_Effective_ID(olc);
		ic.setBill_Location_ID(billLocationId);

		ic.setBill_User_ID(olCandEffectiveValuesBL.getBill_User_Effective_ID(olc));

		ic.setDescription(olc.getDescription());

		ic.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_Sofort); // Immediate

		// 04285: set header and footer

		ic.setDescriptionBottom(olc.getDescriptionBottom());
		ic.setDescriptionHeader(olc.getDescriptionHeader());

		// 05265
		ic.setIsSOTrx(true);

		// 07442 activity and tax
		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(olc);
		final I_C_Activity activity = Services.get(IProductAcctDAO.class).retrieveActivityForAcct(contextProvider, olc.getAD_Org(), olCandEffectiveValuesBL.getM_Product_Effective(olc));
		ic.setC_Activity(activity);

		final int taxCategoryId = -1; // FIXME for accuracy, we will need the tax category
		final I_M_Warehouse warehouse = null;
		final boolean isSOTrx = true;

		final int taxId = Services.get(ITaxBL.class).getTax(ctx
				, ic
				, taxCategoryId
				, productId
				, chargeId
				, olc.getDatePromised()
				, olc.getDatePromised()
				, orgId
				, warehouse
				, billLocationId
				, olc.getC_BPartner_Location_ID()
				, isSOTrx
				, trxName
				);
		ic.setC_Tax_ID(taxId);

		return ic;
	}

	@Override
	public void invalidateCandidatesFor(final Object model)
	{
		final I_C_OLCand olc = InterfaceWrapperHelper.create(model, I_C_OLCand.class);
		invalidateCandidatesForOLCand(olc);
	}

	private void invalidateCandidatesForOLCand(final I_C_OLCand olc)
	{
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(olc);
		final String trxName = InterfaceWrapperHelper.getTrxName(olc);

		final List<I_C_Invoice_Candidate> ics = invoiceCandDAO.fetchInvoiceCandidates(ctx, I_C_OLCand.Table_Name, olc.getC_OLCand_ID(), trxName);
		for (final I_C_Invoice_Candidate ic : ics)
		{
			invoiceCandDAO.invalidateCand(ic);
		}

	}

	@Override
	public boolean isUserInChargeUserEditable()
	{
		return false;
	}

	/**
	 * <ul>
	 * <li>QtyOrdered := C_OLCand.Qty
	 * <li>DateOrdered := C_OLCand.DateCandidate
	 * <li>C_Order_ID: untouched
	 * </ul>
	 *
	 * @see IInvoiceCandidateHandler#setDeliveredData(I_C_Invoice_Candidate)
	 */
	@Override
	public void setOrderedData(final I_C_Invoice_Candidate ic)
	{
		final I_C_OLCand olc = getOLCand(ic);

		ic.setQtyOrdered(olc.getQty());
		ic.setDateOrdered(olc.getDateCandidate());
	}

	private I_C_OLCand getOLCand(final I_C_Invoice_Candidate ic)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(ic);
		final String trxName = InterfaceWrapperHelper.getTrxName(ic);

		final I_C_OLCand olc =
				InterfaceWrapperHelper.create(
						MTable.get(ctx, ic.getAD_Table_ID()).getPO(ic.getRecord_ID(), trxName),
						I_C_OLCand.class);
		return olc;
	}

	/**
	 * <ul>
	 * <li>QtyDelivered := QtyOrdered
	 * <li>DeliveryDate := DateOrdered
	 * <li>M_InOut_ID: untouched
	 * </ul>
	 *
	 * @see IInvoiceCandidateHandler#setDeliveredData(I_C_Invoice_Candidate)
	 */
	@Override
	public void setDeliveredData(final I_C_Invoice_Candidate ic)
	{
		ic.setQtyDelivered(ic.getQtyOrdered());
		ic.setDeliveryDate(ic.getDateOrdered());
	}

	@Override
	public void setPriceActual(final I_C_Invoice_Candidate ic)
	{
		final I_C_OLCand olc = getOLCand(ic);
		final IPricingResult pricingResult = Services.get(IOLCandBL.class).computePriceActual(olc, null, 0, olc.getDateCandidate());

		ic.setPrice_UOM_ID(pricingResult.getPrice_UOM_ID());
		ic.setPriceActual(pricingResult.getPriceStd());
		ic.setIsTaxIncluded(pricingResult.isTaxIncluded());
	}

	@Override
	public void setPriceEntered(final I_C_Invoice_Candidate ic)
	{
		// nothing to do
	}

	@Override
	public void setBPartnerData(final I_C_Invoice_Candidate ic)
	{
		final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);

		final I_C_OLCand olc = getOLCand(ic);

		ic.setBill_BPartner_ID(olCandEffectiveValuesBL.getBill_BPartner_Effective_ID(olc));
		ic.setBill_Location_ID(olCandEffectiveValuesBL.getBill_Location_Effective_ID(olc));
		ic.setBill_User_ID(olCandEffectiveValuesBL.getBill_User_Effective_ID(olc));
	}

	@Override
	public void setC_UOM_ID(final I_C_Invoice_Candidate ic)
	{
		final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);

		final I_C_OLCand olc = getOLCand(ic);

		ic.setC_UOM_ID(olCandEffectiveValuesBL.getC_UOM_Effective_ID(olc));
	}
}
