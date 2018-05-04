package de.metas.contracts.invoicecandidate;

import static org.adempiere.model.InterfaceWrapperHelper.getContextAware;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;

/*
 * #%L
 * de.metas.contracts
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
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.contracts.IContractsDAO;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Transition;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;
import de.metas.product.acct.api.IProductAcctDAO;
import de.metas.tax.api.ITaxBL;
import lombok.NonNull;

/**
 * Creates {@link I_C_Invoice_Candidate} from {@link I_C_Flatrate_Term}.
 *
 * @author tsa
 *
 */
public class FlatrateTermInvoiceCandidateHandler extends AbstractInvoiceCandidateHandler
{
	/**
	 * One invocation returns a maximum of <code>limit</code> {@link I_C_Flatrate_Term}s that are completed subscriptions and don't have a <code>C_OrderLine_Term_ID</code>.
	 */
	@Override
	public Iterator<I_C_Flatrate_Term> retrieveAllModelsWithMissingCandidates(final int limit)
	{
		return Services.get(IContractsDAO.class).retrieveSubscriptionTermsWithMissingCandidates(limit).iterator();
	}

	@Override
	public InvoiceCandidateGenerateResult createCandidatesFor(final InvoiceCandidateGenerateRequest request)
	{
		final I_C_Flatrate_Term term = request.getModel(I_C_Flatrate_Term.class);

		final I_C_Invoice_Candidate ic = createCandidateForTerm(term);
		return InvoiceCandidateGenerateResult.of(this, ic);
	}

	@Override
	public void invalidateCandidatesFor(final Object model)
	{
		final I_C_Flatrate_Term term = InterfaceWrapperHelper.create(model, I_C_Flatrate_Term.class);

		final IInvoiceCandDAO invoiceCandDB = Services.get(IInvoiceCandDAO.class);

		for (final I_C_Invoice_Candidate cand : invoiceCandDB.retrieveReferencing(term))
		{
			invoiceCandDB.invalidateCand(cand);
		}
	}

	@Override
	public String getSourceTable()
	{
		return I_C_Flatrate_Term.Table_Name;
	}

	@Override
	public int getAD_User_InCharge_ID(@NonNull final I_C_Invoice_Candidate ic)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(ic);
		final String trxName = InterfaceWrapperHelper.getTrxName(ic);

		final String tableName = Services.get(IADTableDAO.class).retrieveTableName(ic.getAD_Table_ID());
		Check.assume(I_C_Flatrate_Term.Table_Name.equals(tableName), "AD_Table_ID for {} shall be {} but it was {}", ic, I_C_Flatrate_Term.Table_Name, tableName);

		Check.assume(ic.getRecord_ID() > 0, "Record_ID shall be filled for {}", ic);

		final I_C_Flatrate_Term term = InterfaceWrapperHelper.create(ctx, ic.getRecord_ID(), I_C_Flatrate_Term.class, trxName);
		Check.assume(term != null, "Term found for {}", ic);

		return term.getAD_User_InCharge_ID();
	}

	/**
	 * Returns false, because the user in charge is taken from the C_Flatrate_Term.
	 */
	@Override
	public boolean isUserInChargeUserEditable()
	{
		return false;
	}

	private boolean isCancelledContract(@NonNull final I_C_Flatrate_Term term)
	{
		return X_C_Flatrate_Term.CONTRACTSTATUS_Quit.equals(term.getContractStatus())
				|| X_C_Flatrate_Term.CONTRACTSTATUS_Voided.equals(term.getContractStatus());
	}

	private void setOrderAndOrderLineIfNeeded(@NonNull final I_C_Flatrate_Term term, @NonNull final I_C_Invoice_Candidate ic)
	{
		final I_C_OrderLine orderLine = term.getC_OrderLine_Term();
		if (orderLine == null)
		{
			return;
		}

		ic.setC_OrderLine(orderLine);
		ic.setC_Order(orderLine.getC_Order());
	}

	private I_C_Invoice_Candidate createCandidateForTerm(final I_C_Flatrate_Term term)
	{
		if (isCancelledContract(term))
		{
			return null;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(term);
		final String trxName = InterfaceWrapperHelper.getTrxName(term);

		Check.assume(Env.getAD_Client_ID(ctx) == term.getAD_Client_ID(), "AD_Client_ID of " + term + " and of its Ctx are the same");

		final I_C_Invoice_Candidate ic = InterfaceWrapperHelper.create(ctx, I_C_Invoice_Candidate.class, trxName);
		ic.setAD_Org_ID(term.getAD_Org_ID());
		ic.setC_ILCandHandler(getHandlerRecord());

		ic.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(I_C_Flatrate_Term.Table_Name));
		ic.setRecord_ID(term.getC_Flatrate_Term_ID());

		ic.setM_Product_ID(term.getM_Product_ID());

		final BigDecimal qty = Services.get(IContractsDAO.class).retrieveSubscriptionProgressQtyForTerm(term);

		ic.setQtyOrdered(qty);

		setOrderAndOrderLineIfNeeded(term, ic);

		// 03799
		// If the term is the first one, then the candidate should be created
		// with DateOrdered=Term.getStartDate
		final Timestamp dateOrdered = getDateOrdered(term);

		ic.setDateOrdered(dateOrdered);
		ic.setPriceActual(term.getPriceActual()); // TODO document and make sure there is the correct value
		ic.setPriceEntered(term.getPriceActual()); // cg : task 04917 -- same as price actual
		ic.setPrice_UOM_ID(term.getC_UOM_ID()); // 07090 when we set PiceActual, we shall also set PriceUOM.
		ic.setC_Currency_ID(term.getC_Currency_ID());

		ic.setQtyToInvoice(BigDecimal.ZERO); // to be computed

		ic.setBill_BPartner_ID(term.getBill_BPartner_ID());
		ic.setBill_Location_ID(term.getBill_Location_ID());
		ic.setBill_User_ID(term.getBill_User_ID());

		// note that C_Invoice_Cand.QtyDelivered by InvoiceCandBL, so
		// whatever InvoiceRule set in the conditions should be OK
		ic.setInvoiceRule(term.getC_Flatrate_Conditions().getInvoiceRule());

		// 05265
		ic.setIsSOTrx(true);

		ic.setM_PricingSystem_ID(term.getM_PricingSystem_ID());

		// 07442 activity and tax
		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(term);

		final I_C_Activity activity = Services.get(IProductAcctDAO.class).retrieveActivityForAcct(contextProvider, term.getAD_Org(), term.getM_Product());

		ic.setC_Activity(activity);
		ic.setIsTaxIncluded(term.isTaxIncluded());

		final int taxCategoryId = term.getC_TaxCategory_ID();
		final I_M_Warehouse warehouse = null;
		final boolean isSOTrx = true;

		final int taxId = Services.get(ITaxBL.class).getTax(
				ctx, term, taxCategoryId, term.getM_Product_ID(), -1 // chargeId
				, dateOrdered // billDate
				, dateOrdered // shipDate
				, term.getAD_Org_ID(), warehouse, term.getBill_Location_ID(), -1 // ship location id
				, isSOTrx, trxName);

		ic.setC_Tax_ID(taxId);
		return ic;
	}

	private Timestamp getDateOrdered(final I_C_Flatrate_Term term)
	{
		final Timestamp dateOrdered;
		final boolean termHasAPredecessor = Services.get(IContractsDAO.class).termHasAPredecessor(term);
		if (!termHasAPredecessor)
		{
			dateOrdered = term.getStartDate();
		}
		// If the term was extended (meaning that there is a predecessor term),
		// C_Invoice_Candidate.DateOrdered should rather be the StartDate minus TermOfNoticeDuration/TermOfNoticeUnit
		else
		{
			final Timestamp firstDayOfNewTerm = getGetExtentionDateOfNewTerm(term);
			dateOrdered = firstDayOfNewTerm;
		}
		return dateOrdered;
	}

	/**
	 * Check if the current date is equal or after the term's startdate minus the period specified by "TermOfNoticeDuration" and "TermOfNoticeUnit" of the C_Flatrate_Transition record that is
	 * referenced by the term's C_Flatrate_Conditions. This is important, because the current date will be the dateOrdered of the invoice candidate
	 *
	 * @param term
	 * @return
	 */
	protected boolean isCorrectDateForTerm(final I_C_Flatrate_Term term)
	{
		final Timestamp today = SystemTime.asTimestamp();

		final Timestamp minimumDateToStart = getGetExtentionDateOfNewTerm(term);

		if (today.before(minimumDateToStart))
		{
			return false;
		}

		return true;
	}

	/**
	 * For the given <code>term</code> and its <code>C_Flatrate_Transition</code> record, this method returns the term's start date minus the period specified by <code>TermOfNoticeDuration</code> and
	 * <code>TermOfNoticeUnit</code>.
	 *
	 *
	 * @param term
	 * @return
	 */
	private Timestamp getGetExtentionDateOfNewTerm(final I_C_Flatrate_Term term)
	{
		final Timestamp startDateOfTerm = term.getStartDate();

		final I_C_Flatrate_Conditions conditions = term.getC_Flatrate_Conditions();
		final I_C_Flatrate_Transition transition = conditions.getC_Flatrate_Transition();
		final String termOfNoticeUnit = transition.getTermOfNoticeUnit();
		final int termOfNotice = transition.getTermOfNotice();

		final Timestamp minimumDateToStart;
		if (X_C_Flatrate_Transition.TERMOFNOTICEUNIT_MonatE.equals(termOfNoticeUnit))
		{
			minimumDateToStart = TimeUtil.addMonths(startDateOfTerm, termOfNotice * -1);
		}
		else if (X_C_Flatrate_Transition.TERMOFNOTICEUNIT_WocheN.equals(termOfNoticeUnit))
		{
			minimumDateToStart = TimeUtil.addWeeks(startDateOfTerm, termOfNotice * -1);
		}
		else if (X_C_Flatrate_Transition.TERMOFNOTICEUNIT_TagE.equals(termOfNoticeUnit))
		{
			minimumDateToStart = TimeUtil.addDays(startDateOfTerm, termOfNotice * -1);
		}
		else
		{
			Check.assume(false, "TermOfNoticeDuration " + transition.getTermOfNoticeUnit() + " doesn't exist");
			minimumDateToStart = null; // code won't be reached
		}

		return minimumDateToStart;
	}

	/**
	 * <ul>
	 * <li>DateOrdered: if the term has a predecessor, then the the predecessor's notice/extend date. Else the term's <code>StartDate</code>.
	 * <li>QtyOrdered: the term's <code>PlannedQtyPerUnit</code>.
	 * <li>C_Order_ID: untouched
	 * </ul>
	 *
	 * @see IInvoiceCandidateHandler#setOrderedData(I_C_Invoice_Candidate)
	 */
	@Override
	public void setOrderedData(final I_C_Invoice_Candidate ic)
	{
		final I_C_Flatrate_Term term = retrieveTerm(ic);

		ic.setDateOrdered(getDateOrdered(term));
		ic.setQtyOrdered(term.getPlannedQtyPerUnit()); // Set the quantity from the term.
	}

	private I_C_Flatrate_Term retrieveTerm(@NonNull final I_C_Invoice_Candidate ic)
	{
		final int flatrateTermTableId = getTableId(I_C_Flatrate_Term.class);
		Check.assume(ic.getAD_Table_ID() == flatrateTermTableId, "{} has AD_Table_ID={}", ic, flatrateTermTableId);

		return TableRecordReference.ofReferenced(ic).getModel(getContextAware(ic), I_C_Flatrate_Term.class);
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
		ic.setDeliveryDate(ic.getDateOrdered());
		ic.setQtyDelivered(ic.getQtyOrdered());
	}

	@Override
	public PriceAndTax calculatePriceAndTax(final I_C_Invoice_Candidate ic)
	{
		final I_C_Flatrate_Term term = retrieveTerm(ic);
		return PriceAndTax.builder()
				.priceActual(term.getPriceActual())
				.priceUOMId(term.getC_UOM_ID()) // 07090: when setting a priceActual, we also need to specify a PriceUOM
				.build();
	}

	@Override
	public void setC_UOM_ID(final I_C_Invoice_Candidate ic)
	{
		final I_C_Flatrate_Term term = retrieveTerm(ic);
		ic.setC_UOM_ID(term.getC_UOM_ID());
	}

	@Override
	public void setBPartnerData(final I_C_Invoice_Candidate ic)
	{
		final I_C_Flatrate_Term term = retrieveTerm(ic);

		ic.setBill_BPartner_ID(term.getBill_BPartner_ID());
		ic.setBill_Location_ID(term.getBill_Location_ID());
		ic.setBill_User_ID(term.getBill_User_ID());
	}
}
