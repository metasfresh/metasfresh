package de.metas.invoicecandidate.callout;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.invoicecandidate.api.IAggregationBL;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerDAO;
import de.metas.invoicecandidate.compensationGroup.InvoiceCandidateGroupRepository;
import de.metas.invoicecandidate.compensationGroup.InvoiceCandidatesStorage;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.impl.ManualCandidateHandler;
import de.metas.lang.SOTrx;
import de.metas.order.compensationGroup.Group;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.PricingSystemId;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Properties;

@Callout(I_C_Invoice_Candidate.class)
public class C_Invoice_Candidate
{
	@CalloutMethod(columnNames = {
			I_C_Invoice_Candidate.COLUMNNAME_Discount_Override //
			, I_C_Invoice_Candidate.COLUMNNAME_IsTaxIncluded_Override //
			, I_C_Invoice_Candidate.COLUMNNAME_PriceActual //
			, I_C_Invoice_Candidate.COLUMNNAME_PriceActual_Override //
			, I_C_Invoice_Candidate.COLUMNNAME_PriceEntered_Override //
			, I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice //
			, I_C_Invoice_Candidate.COLUMNNAME_C_VAT_Code_ID //
			, I_C_Invoice_Candidate.COLUMNNAME_C_VAT_Code_Override_ID //
	})
	public void updatePriceAndAmt(final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandBL invoiceScheduleBL = Services.get(IInvoiceCandBL.class);

		if (ic.getC_ILCandHandler_ID() <= 0)
		{
			// Case: this is a new record and C_ILCandHandler_ID was not already set. Skip it for now
			return;
		}

		// first chek price actual override
		invoiceScheduleBL.setPriceActual_Override(ic);

		invoiceScheduleBL.setNetAmtToInvoice(ic);

		invoiceScheduleBL.setPriceActualNet(ic);
	}

	@CalloutMethod(columnNames = I_C_Invoice_Candidate.COLUMNNAME_IsManual)
	public void onIsManual(final I_C_Invoice_Candidate ic)
	{
		final IInvoiceCandidateHandlerDAO handlerDAO = Services.get(IInvoiceCandidateHandlerDAO.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(ic);
		if (ic.isManual())
		{
			final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
			final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

			final I_C_ILCandHandler handler = handlerDAO.retrieveForTable(ctx, ManualCandidateHandler.MANUAL).get(0);

			final LocalDate today = invoiceCandBL.getToday();
			final Timestamp todayAsTimestamp = TimeUtil.asTimestamp(today, orgDAO.getTimeZone(OrgId.ofRepoId(ic.getAD_Org_ID())));

			ic.setC_ILCandHandler(handler);
			ic.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_EFFECTIVE_Immediate);
			// ic.setQtyToInvoice(BigDecimal.ONE); setting this qty is don my the update process
			ic.setQtyDelivered(BigDecimal.ONE);
			ic.setQtyOrdered(BigDecimal.ONE);
			ic.setDateToInvoice(todayAsTimestamp);
			// we have no source
			ic.setAD_Table_ID(0);
			ic.setRecord_ID(0);

			ic.setC_Invoice_Candidate_Agg(null);
			Services.get(IAggregationBL.class).resetHeaderAggregationKey(ic);
			ic.setLineAggregationKey(null);
			//
			invoiceCandBL.set_QtyInvoiced_NetAmtInvoiced_Aggregation(ic);
			//
			if (ic.getBill_BPartner_ID() > 0)
			{
				setPricingSystem(ctx, ic);
			}
		}
		else
		{
			ic.setC_ILCandHandler(null);
			ic.setInvoiceRule(null);
			ic.setQtyToInvoice_Override(BigDecimal.ZERO);
			// we have no source
			ic.setAD_Table_ID(0);
			ic.setRecord_ID(0);
			ic.setC_Invoice_Candidate_Agg(null);
			ic.setLineAggregationKey(null);
		}
	}

	@CalloutMethod(columnNames = I_C_Invoice_Candidate.COLUMNNAME_Bill_BPartner_ID)
	public void onBill_BPartner_ID(final I_C_Invoice_Candidate ic)
	{
		if (ic.isManual() && ic.getBill_BPartner_ID() > 0)
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(ic);
			setPricingSystem(ctx, ic);
		}
	}

	private void setPricingSystem(final Properties ctx, final I_C_Invoice_Candidate ic)
	{
		final IBPartnerDAO bPartnerPA = Services.get(IBPartnerDAO.class);
		final PricingSystemId pricingSysId = bPartnerPA.retrievePricingSystemIdOrNull(
				BPartnerId.ofRepoId(ic.getBill_BPartner_ID()),
				SOTrx.ofBoolean(ic.isSOTrx()));
		ic.setM_PricingSystem_ID(PricingSystemId.toRepoId(pricingSysId));
	}

	@CalloutMethod(columnNames = I_C_Invoice_Candidate.COLUMNNAME_QualityDiscountPercent_Override)
	public void onQualityDiscountPercentOverride(final I_C_Invoice_Candidate icRecord, final ICalloutField field)
	{
		icRecord.setIsInDispute(false);
	}

	@CalloutMethod(columnNames = I_C_Invoice_Candidate.COLUMNNAME_GroupCompensationPercentage)
	public void onGroupCompensationPercentageChanged(final I_C_Invoice_Candidate ic)
	{
		final InvoiceCandidateGroupRepository groupsRepo = SpringContextHolder.instance.getBean(InvoiceCandidateGroupRepository.class);

		final Group group = groupsRepo.createPartialGroupFromCompensationLine(ic);
		group.updateAllCompensationLines();

		final InvoiceCandidatesStorage orderLinesStorage = groupsRepo.createNotSaveableSingleOrderLineStorage(ic);
		groupsRepo.saveGroup(group, orderLinesStorage);
	}

	@CalloutMethod(columnNames = I_C_Invoice_Candidate.COLUMNNAME_DateInvoiced )
	public void updateFromDateInvoiced(final I_C_Invoice_Candidate invoice, final ICalloutField field)
	{
		invoice.setDateAcct(invoice.getDateInvoiced());
	}
}
