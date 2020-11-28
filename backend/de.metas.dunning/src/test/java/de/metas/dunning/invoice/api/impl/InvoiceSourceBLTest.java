package de.metas.dunning.invoice.api.impl;

import org.adempiere.ad.trx.api.ITrx;

/*
 * #%L
 * de.metas.dunning
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


import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.MTable;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Test;

import de.metas.dunning.DunningTestBase;
import de.metas.dunning.interfaces.I_C_Dunning;
import de.metas.dunning.model.I_C_Dunning_Candidate;

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
			Assert.assertEquals("Invalid dunning - BP level", dunningBP, invoiceSourceBL.getDunningForInvoiceOrNull(invoice));
		}

		// Dunning at BP Group level
		{
			bpGroup.setC_Dunning_ID(dunningBPG.getC_Dunning_ID());
			InterfaceWrapperHelper.save(bpGroup);
			bpartner.setC_Dunning_ID(-1);
			InterfaceWrapperHelper.save(bpartner);
			Assert.assertEquals("Invalid dunning - BP Group level", dunningBPG, invoiceSourceBL.getDunningForInvoiceOrNull(invoice));
		}

		// Dunning at Org level
		{
			bpGroup.setC_Dunning_ID(-1);
			InterfaceWrapperHelper.save(bpGroup);
			bpartner.setC_Dunning_ID(-1);
			InterfaceWrapperHelper.save(bpartner);
			Assert.assertEquals("Invalid dunning - Org level", dunningOrgDefault, invoiceSourceBL.getDunningForInvoiceOrNull(invoice));
		}
	}

	@Test
	public void writeOffDunningCandidate_NotAnInvoice()
	{
		final I_C_Dunning_Candidate candidate = db.newInstance(I_C_Dunning_Candidate.class);
		candidate.setAD_Table_ID(MTable.getTable_ID(I_C_Order.Table_Name));
		InterfaceWrapperHelper.save(candidate);

		final boolean executed = invoiceSourceBL.writeOffDunningCandidate(candidate, null);
		Assert.assertFalse("Only invoices are supported for write-off", executed);
	}

	@Test(expected = Exception.class)
	public void writeOffDunningCandidate_InvoiceNotFound()
	{
		final I_C_Dunning_Candidate candidate = db.newInstance(I_C_Dunning_Candidate.class);
		candidate.setAD_Table_ID(MTable.getTable_ID(I_C_Invoice.Table_Name));
		candidate.setRecord_ID(12345);
		InterfaceWrapperHelper.save(candidate);

		invoiceSourceBL.writeOffDunningCandidate(candidate, null);
	}

	public void writeOffDunningCandidate()
	{
		final I_C_Invoice invoice1 = InterfaceWrapperHelper.create(Env.getCtx(), I_C_Invoice.class, ITrx.TRXNAME_None);
		invoice1.setIsSOTrx(true);
		invoice1.setProcessed(true);
		InterfaceWrapperHelper.save(invoice1);
		Assert.assertFalse("Invoice not wrote off " + invoice1, invoiceBL.isInvoiceWroteOff(invoice1));

		final I_C_Dunning_Candidate candidate = db.newInstance(I_C_Dunning_Candidate.class);
		candidate.setAD_Table_ID(MTable.getTable_ID(I_C_Invoice.Table_Name));
		candidate.setRecord_ID(invoice1.getC_Invoice_ID());
		InterfaceWrapperHelper.save(candidate);

		final boolean executed = invoiceSourceBL.writeOffDunningCandidate(candidate, null);
		Assert.assertFalse("Only invoices are supported for write-off", executed);

		Assert.assertTrue("Invoice shall be wrote off " + invoice1, invoiceBL.isInvoiceWroteOff(invoice1));
	}
}
