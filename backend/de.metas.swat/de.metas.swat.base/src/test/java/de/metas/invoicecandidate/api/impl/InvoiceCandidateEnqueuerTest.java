package de.metas.invoicecandidate.api.impl;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyRepository;
import de.metas.invoicecandidate.AbstractICTestSupport;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueueResult;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.MoneyService;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class InvoiceCandidateEnqueuerTest extends AbstractICTestSupport
{
	@Before
	public void beforeEach()
	{
		SpringContextHolder.registerJUnitBean(new InvoiceCandidateRecordService());
		SpringContextHolder.registerJUnitBean(new MoneyService(new CurrencyRepository()));
	}

	/**
	 * Test case:
	 * <ul>
	 * <li>system date is 01.01.2015
	 * <li>we have 2 invoice candidates, approximately the same, but one has DateInvoiced set to 01.01.2015 (system date) and the second one has no DateInvoiced set
	 * <li>when enqueuing for invoicing both shall end in the same workpackage because basically they are on the same date.
	 * </ul>
	 *
	 * @task http://dewiki908/mediawiki/index.php/08492_rechnungsdatum_and_buchungsdatum_in_invoice_candidates_%28102690023594%29
	 */
	@Test
	public void test_2InvoiceCandidates_OneWithDateInvoicedSet_OneWithoutDateInvoicedButSameDay()
	{
		//
		// Setup
		// registerModelInterceptors(); // got a lot of errors if i am registering it, so i keep it simple
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
		SystemTime.setFixedTimeSource("2015-01-01T00:00:00+01:00");

		//
		// Create invoice candidates
		final int priceEntered = 1;
		final int qty = 1;
		final boolean isManual = false;
		final boolean isSOTrx = true;

		final BPartnerLocationId billBPartnerAndLocationId = BPartnerLocationId.ofRepoId(1, 2);

		final I_C_Invoice_Candidate ic1 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(priceEntered)
				.setQtyOrdered(qty)
				.setSOTrx(isSOTrx)
				.setManual(isManual)
				.build();

		ic1.setQtyToInvoice_Override(BigDecimal.valueOf(qty)); // to make sure it's invoiced
		ic1.setDateInvoiced(TimeUtil.getDay(2015, 1, 1));
		ic1.setApprovalForInvoicing(false);
		InterfaceWrapperHelper.save(ic1);
		invoiceCandDAO.invalidateCand(ic1);

		final I_C_Invoice_Candidate ic2 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(priceEntered)
				.setQtyOrdered(qty)
				.setSOTrx(isSOTrx)
				.setManual(isManual)
				.build();
		ic2.setQtyToInvoice_Override(BigDecimal.valueOf(qty)); // to make sure it's invoiced
		// ic2.setDateInvoiced(TimeUtil.getDay(2015, 1, 1)); // DON'T set it
		ic2.setApprovalForInvoicing(false);
		InterfaceWrapperHelper.save(ic2);
		invoiceCandDAO.invalidateCand(ic2);

		//
		// Enqueue them to be invoiced
		final PInstanceId selectionId = POJOLookupMap.get().createSelectionFromModels(ic1, ic2);
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final IMutable<IInvoiceCandidateEnqueueResult> enqueueResultRef = new Mutable<>();
		trxManager.runInNewTrx(localTrxName -> {
			final IInvoiceCandidateEnqueueResult result = new InvoiceCandidateEnqueuer()
					.setContext(Env.getCtx())
					.setInvoicingParams(createDefaultInvoicingParams())
					.setFailOnChanges(false) // ... because we have some invalid candidates which we know that it will be updated here
					.enqueueSelection(selectionId);
			enqueueResultRef.setValue(result);
		});

		//
		// Validate the invoice enqueuing result
		final IInvoiceCandidateEnqueueResult enqueueResult = enqueueResultRef.getValue();
		assertThat(enqueueResult.getWorkpackageEnqueuedCount()).as("EnqueuedWorkpackageCount").isEqualTo(1);
		assertThat(enqueueResult.getInvoiceCandidateEnqueuedCount()).as("InvoiceCandidateSelectionCount").isEqualTo(2);

		InterfaceWrapperHelper.refresh(ic1);
		assertThat(ic1.isApprovalForInvoicing()).isTrue();

		InterfaceWrapperHelper.refresh(ic2);
		assertThat(ic2.isApprovalForInvoicing()).isTrue();
	}

	@Test
	public void checkApprovalForInvoicingIsSet()
	{
		//
		// Create invoice candidates
		final I_C_Invoice_Candidate ic1 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(BPartnerLocationId.ofRepoId(1, 2))
				.setPriceEntered(1)
				.setQtyOrdered(1)
				.setSOTrx(true)
				.setManual(false)
				.setDateInvoiced(LocalDate.parse("2020-06-01"))
				.build();
		//
		// Enqueue them to be invoiced
		final PInstanceId selectionId = POJOLookupMap.get().createSelectionFromModels(ic1);
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.callInNewTrx(() -> new InvoiceCandidateEnqueuer()
				.setContext(Env.getCtx())
				.setInvoicingParams(createDefaultInvoicingParams())
				.setFailOnChanges(false) // ... because we have some invalid candidates which we know that it will be updated here
				.enqueueSelection(selectionId));

		InterfaceWrapperHelper.refresh(ic1);
		assertThat(ic1.isApprovalForInvoicing()).isTrue();
	}
}
