package de.metas.invoicecandidate.spi.impl;

import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.getCtx;
import static org.adempiere.model.InterfaceWrapperHelper.getTrxName;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import lombok.NonNull;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.ClientId;
import org.adempiere.service.OrgId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.Env;
import org.compiere.util.Util;

import de.metas.bpartner.BPartnerId;
import de.metas.cache.model.impl.TableRecordCacheLocal;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.InvoiceCandidate_Constants;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;
import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PricingSystemId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.product.acct.api.IProductAcctDAO;
import de.metas.tax.api.ITaxBL;
import de.metas.util.Check;
import de.metas.util.Services;

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
	public boolean isCreateMissingCandidatesAutomatically()
	{
		return true;
	}

	@Override
	public boolean isCreateMissingCandidatesAutomatically(final Object model)
	{
		final I_C_OLCand olCand = create(model, I_C_OLCand.class);
		if (!isEligibleForInvoiceCandidateCreate(olCand))
		{
			return false;
		}

		return true;
	}

	@Override
	public String getSourceTable()
	{
		return I_C_OLCand.Table_Name;
	}

	@Override
	public Iterator<I_C_OLCand> retrieveAllModelsWithMissingCandidates(final int limit)
	{
		return dao.retrieveMissingCandidatesQuery(Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.setLimit(limit)
				.create()
				.iterate(I_C_OLCand.class);
	}

	@Override
	public InvoiceCandidateGenerateResult createCandidatesFor(@NonNull final InvoiceCandidateGenerateRequest request)
	{
		final I_C_OLCand olCand = request.getModel(I_C_OLCand.class);

		//
		// Make sure the OL_Cand is eligible for creating invoice candidates
		if (!isEligibleForInvoiceCandidateCreate(olCand))
		{
			return InvoiceCandidateGenerateResult.of(this);
		}

		final I_C_Invoice_Candidate ic = createInvoiceCandidateForOLCand(olCand);
		return InvoiceCandidateGenerateResult.of(this, ic);
	}

	private boolean isEligibleForInvoiceCandidateCreate(final I_C_OLCand olCand)
	{
		final Properties ctx = getCtx(olCand);
		final String trxName = getTrxName(olCand);
		return dao.retrieveMissingCandidatesQuery(ctx, trxName)
				.addEqualsFilter(I_C_OLCand.COLUMN_C_OLCand_ID, olCand.getC_OLCand_ID())
				.create()
				.match();
	}

	private I_C_Invoice_Candidate createInvoiceCandidateForOLCand(@NonNull final I_C_OLCand olc)
	{
		// services
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
		final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);

		final Properties ctx = getCtx(olc);
		Check.assume(Env.getAD_Client_ID(ctx) == olc.getAD_Client_ID(), "AD_Client_ID of {} and of its Ctx are the same", olc);

		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class, olc);

		// org id
		final OrgId orgId = OrgId.ofRepoId(olc.getAD_Org_ID());
		ic.setAD_Org_ID(orgId.getRepoId());

		ic.setC_ILCandHandler(getHandlerRecord());

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

		ic.setQtyToInvoice(ZERO); // to be computed
		ic.setC_UOM_ID(olCandEffectiveValuesBL.getC_UOM_Effective_ID(olc));

		ic.setM_PricingSystem_ID(olc.getM_PricingSystem_ID());
		ic.setPriceActual(olc.getPriceActual());
		ic.setPrice_UOM_ID(olCandEffectiveValuesBL.getC_UOM_Effective_ID(olc)); // 07090 when we set PriceActual, we shall also set PriceUOM.

		ic.setPriceEntered(olc.getPriceEntered()); // cg : task 04917
		ic.setDiscount(olc.getDiscount());
		ic.setC_Currency_ID(olc.getC_Currency_ID());
		// ic.setC_ConversionType_ID(C_ConversionType_ID); // N/A

		ic.setBill_BPartner_ID(BPartnerId.toRepoId(olCandEffectiveValuesBL.getBill_BPartner_Effective_ID(olc)));

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

		ic.setDateInvoiced(olc.getDateInvoiced());
		ic.setC_DocTypeInvoice_ID(olc.getC_DocTypeInvoice_ID());

		// 07442 activity and tax
		final ActivityId activityId = Services.get(IProductAcctDAO.class).retrieveActivityForAcct(
				ClientId.ofRepoId(olc.getAD_Client_ID()),
				OrgId.ofRepoId(olc.getAD_Org_ID()),
				ProductId.ofRepoId(olCandEffectiveValuesBL.getM_Product_Effective_ID(olc)));
		ic.setC_Activity_ID(ActivityId.toRepoId(activityId));

		final ITaxBL taxBL = Services.get(ITaxBL.class);
		final int taxId = taxBL.getTax(
				ctx,
				ic, // model
				olc.getC_TaxCategory_ID(),
				productId,
				Util.coalesce(olc.getDatePromised_Override(), olc.getDatePromised(), olc.getDateInvoiced()),
				orgId,
				(WarehouseId)null,
				olCandEffectiveValuesBL.getDropShip_Location_Effective_ID(olc),
				true /*isSOTrx*/);
		ic.setC_Tax_ID(taxId);

		ic.setExternalId(olc.getExternalId());

		olc.setProcessed(true);
		saveRecord(olc);

		return ic;
	}

	@Override
	public void invalidateCandidatesFor(final Object model)
	{
		final I_C_OLCand olc = create(model, I_C_OLCand.class);
		invalidateCandidatesForOLCand(olc);
	}

	private void invalidateCandidatesForOLCand(@NonNull final I_C_OLCand olc)
	{
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		final Properties ctx = getCtx(olc);
		final String trxName = getTrxName(olc);

		final List<I_C_Invoice_Candidate> ics = invoiceCandDAO.fetchInvoiceCandidates(ctx, I_C_OLCand.Table_Name, olc.getC_OLCand_ID(), trxName);
		invoiceCandDAO.invalidateCands(ics);
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
	public void setOrderedData(@NonNull final I_C_Invoice_Candidate ic)
	{
		final I_C_OLCand olc = getOLCand(ic);

		ic.setQtyOrdered(olc.getQty());
		ic.setDateOrdered(olc.getDateCandidate());
	}

	private I_C_OLCand getOLCand(@NonNull final I_C_Invoice_Candidate ic)
	{
		final I_C_OLCand olc = TableRecordCacheLocal.getReferencedValue(ic, I_C_OLCand.class);
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
	public void setDeliveredData(@NonNull final I_C_Invoice_Candidate ic)
	{
		ic.setQtyDelivered(ic.getQtyOrdered()); // when changing this, make sure to threat ProductType.Service specially
		ic.setDeliveryDate(ic.getDateOrdered());
	}

	@Override
	public PriceAndTax calculatePriceAndTax(@NonNull final I_C_Invoice_Candidate ic)
	{
		final I_C_OLCand olc = getOLCand(ic);
		final IPricingResult pricingResult = Services.get(IOLCandBL.class).computePriceActual(olc, null, PricingSystemId.NULL, olc.getDateCandidate());

		return PriceAndTax.builder()
				.priceUOMId(pricingResult.getPrice_UOM_ID())
				.priceActual(pricingResult.getPriceStd())
				.taxIncluded(pricingResult.isTaxIncluded())
				.build();
	}

	@Override
	public void setBPartnerData(@NonNull final I_C_Invoice_Candidate ic)
	{
		final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);

		final I_C_OLCand olc = getOLCand(ic);

		ic.setBill_BPartner_ID(BPartnerId.toRepoId(olCandEffectiveValuesBL.getBill_BPartner_Effective_ID(olc)));
		ic.setBill_Location_ID(olCandEffectiveValuesBL.getBill_Location_Effective_ID(olc));
		ic.setBill_User_ID(olCandEffectiveValuesBL.getBill_User_Effective_ID(olc));
	}

	@Override
	public void setC_UOM_ID(@NonNull final I_C_Invoice_Candidate ic)
	{
		final IOLCandEffectiveValuesBL olCandEffectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);

		final I_C_OLCand olc = getOLCand(ic);

		ic.setC_UOM_ID(olCandEffectiveValuesBL.getC_UOM_Effective_ID(olc));
	}
}
