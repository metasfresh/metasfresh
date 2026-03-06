package de.metas.dunning.invoice.api.impl;

import de.metas.dunning.DunningTestBase;
import de.metas.dunning.interfaces.I_C_Dunning;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.MTable;
import org.compiere.util.Env;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InvoiceSourceBLTest extends DunningTestBase
{
	private final InvoiceSourceBL invoiceSourceBL = new InvoiceSourceBL();

	@Test
	public void getDunningForInvoiceOrNull()
	{
		final I_C_Dunning dunningBPG = createDunning("bp group");
		dunningBPG.setAD_Org_ID(2);
		dunningBPG.setIsDefault(false);
		InterfaceWrapperHelper.save(dunningBPG);

		final I_C_Dunning dunningBP = createDunning("bp");
		dunningBP.setAD_Org_ID(2);
		dunningBP.setIsDefault(false);
		InterfaceWrapperHelper.save(dunningBP);

		final I_C_Dunning dunningOrgDefault = createDunning("org default");
		dunningOrgDefault.setAD_Org_ID(2);
		dunningOrgDefault.setIsDefault(true);
		InterfaceWrapperHelper.save(dunningOrgDefault);

		final I_C_BP_Group bpGroup = db.newInstance(I_C_BP_Group.class);
		InterfaceWrapperHelper.save(bpGroup);

		final I_C_BPartner bpartner = db.newInstance(I_C_BPartner.class);
		bpartner.setC_BP_Group_ID(bpGroup.getC_BP_Group_ID());
		InterfaceWrapperHelper.save(bpartner);

		final I_C_Invoice invoice = db.newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(2);
		invoice.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		InterfaceWrapperHelper.save(invoice);

		// Dunning at BP level
		{
			bpGroup.setC_Dunning_ID(dunningBPG.getC_Dunning_ID());
			InterfaceWrapperHelper.save(bpGroup);
			bpartner.setC_Dunning_ID(dunningBP.getC_Dunning_ID());
			InterfaceWrapperHelper.save(bpartner);
			Assertions.assertEquals(dunningBP, invoiceSourceBL.getDunningForInvoiceOrNull(invoice), "Invalid dunning - BP level");
		}

		// Dunning at BP Group level
		{
			bpGroup.setC_Dunning_ID(dunningBPG.getC_Dunning_ID());
			InterfaceWrapperHelper.save(bpGroup);
			bpartner.setC_Dunning_ID(-1);
			InterfaceWrapperHelper.save(bpartner);
			Assertions.assertEquals(dunningBPG, invoiceSourceBL.getDunningForInvoiceOrNull(invoice), "Invalid dunning - BP Group level");
		}

		// Dunning at Org level
		{
			bpGroup.setC_Dunning_ID(-1);
			InterfaceWrapperHelper.save(bpGroup);
			bpartner.setC_Dunning_ID(-1);
			InterfaceWrapperHelper.save(bpartner);
			Assertions.assertEquals(dunningOrgDefault, invoiceSourceBL.getDunningForInvoiceOrNull(invoice), "Invalid dunning - Org level");
		}
	}

	@Test
	public void writeOffDunningCandidate_NotAnInvoice()
	{
		final I_C_Dunning_Candidate candidate = db.newInstance(I_C_Dunning_Candidate.class);
		candidate.setAD_Table_ID(MTable.getTable_ID(I_C_Order.Table_Name));
		InterfaceWrapperHelper.save(candidate);

		final boolean executed = invoiceSourceBL.writeOffDunningCandidate(candidate, null);
		Assertions.assertFalse(executed, "Only invoices are supported for write-off");
	}

	@Test
	public void writeOffDunningCandidate_InvoiceNotFound()
	{
		final I_C_Dunning_Candidate candidate = db.newInstance(I_C_Dunning_Candidate.class);
		candidate.setAD_Table_ID(MTable.getTable_ID(I_C_Invoice.Table_Name));
		candidate.setRecord_ID(12345);
		InterfaceWrapperHelper.save(candidate);

		Assertions.assertThrows(Exception.class, () -> invoiceSourceBL.writeOffDunningCandidate(candidate, null));
	}

	public void writeOffDunningCandidate()
	{
		final I_C_Invoice invoice1 = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Invoice.class, ITrx.TRXNAME_None);
		invoice1.setIsSOTrx(true);
		invoice1.setProcessed(true);
		InterfaceWrapperHelper.save(invoice1);
		Assertions.assertFalse(invoiceBL.isInvoiceWroteOff(invoice1), "Invoice not wrote off " + invoice1);

		final I_C_Dunning_Candidate candidate = db.newInstance(I_C_Dunning_Candidate.class);
		candidate.setAD_Table_ID(MTable.getTable_ID(I_C_Invoice.Table_Name));
		candidate.setRecord_ID(invoice1.getC_Invoice_ID());
		InterfaceWrapperHelper.save(candidate);

		final boolean executed = invoiceSourceBL.writeOffDunningCandidate(candidate, null);
		Assertions.assertFalse(executed, "Only invoices are supported for write-off");

		Assertions.assertTrue(invoiceBL.isInvoiceWroteOff(invoice1), "Invoice shall be wrote off " + invoice1);
	}
}
