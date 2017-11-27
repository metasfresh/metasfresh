package org.adempiere.impexp.bpartner;

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.impexp.IImportInterceptor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_I_BPartner;
import org.compiere.model.ModelValidationEngine;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/* package */ class BPartnerImportHelper
{
	public static BPartnerImportHelper newInstance()
	{
		return new BPartnerImportHelper();
	}

	private BPartnerImportProcess process;

	private BPartnerImportHelper()
	{
	}

	public BPartnerImportHelper setProcess(final BPartnerImportProcess process)
	{
		this.process = process;
		return this;
	}

	private Properties getCtx()
	{
		return process.getCtx();
	}

	public I_C_BPartner importRecord(final I_I_BPartner importRecord)
	{
		final I_C_BPartner bpartner;
		final boolean insertMode;
		if (importRecord.getC_BPartner_ID() <= 0)	// Insert new BPartner
		{
			insertMode = true;
			bpartner = createNewBPartner(importRecord);
		}
		else
		// Update existing BPartner
		{
			insertMode = false;
			bpartner = updateExistingBPartner(importRecord);
		}

		//
		// CompanyName
		final String companyName = importRecord.getCompanyname();
		if (!Check.isEmpty(companyName, true))
		{
			bpartner.setIsCompany(true);
			bpartner.setCompanyName(companyName.trim());
		}
		else if (insertMode)
		{
			bpartner.setIsCompany(false);
			bpartner.setCompanyName(null);
		}

		//
		// Type (Vendor, Customer, Employee)
		setTypeOfBPartner(importRecord, bpartner);

		ModelValidationEngine.get().fireImportValidate(process, importRecord, bpartner, IImportInterceptor.TIMING_AFTER_IMPORT);
		InterfaceWrapperHelper.save(bpartner);
		importRecord.setC_BPartner(bpartner);

		return bpartner;
	}

	private I_C_BPartner createNewBPartner(final I_I_BPartner importRecord)
	{
		final I_C_BPartner bpartner;
		bpartner = InterfaceWrapperHelper.create(getCtx(), I_C_BPartner.class, ITrx.TRXNAME_ThreadInherited);
		bpartner.setAD_Org_ID(importRecord.getAD_Org_ID());
		//
		bpartner.setValue(extractBPValue(importRecord));
		bpartner.setName(extractBPName(importRecord));
		bpartner.setName2(importRecord.getName2());
		bpartner.setName3(importRecord.getName3());
		bpartner.setDescription(importRecord.getDescription());
		bpartner.setShortDescription(importRecord.getShortDescription());
		bpartner.setDUNS(importRecord.getDUNS());
		bpartner.setVATaxID(importRecord.getTaxID());
		bpartner.setC_InvoiceSchedule_ID(importRecord.getC_InvoiceSchedule_ID());
		bpartner.setPaymentRule(importRecord.getPaymentRule());
		bpartner.setPaymentRulePO(importRecord.getPaymentRulePO());
		bpartner.setPO_PaymentTerm_ID(importRecord.getPO_PaymentTerm_ID());
		bpartner.setNAICS(importRecord.getNAICS());
		bpartner.setC_BP_Group_ID(importRecord.getC_BP_Group_ID());
		bpartner.setAD_Language(importRecord.getAD_Language());
		bpartner.setIsSEPASigned(importRecord.isSEPASigned());
		return bpartner;
	}

	private static final String extractBPName(final I_I_BPartner importRecord)
	{
		final String name = importRecord.getName();
		if (!Check.isEmpty(name, true))
		{
			return name;
		}
		final String email = importRecord.getEMail();
		if (!Check.isEmpty(email, true))
		{
			return email;
		}

		return extractBPValue(importRecord);
	}

	private static final String extractBPValue(final I_I_BPartner importRecord)
	{
		String value = importRecord.getValue();
		return Check.isEmpty(value, true) ? importRecord.getEMail() : value;
	}

	private I_C_BPartner updateExistingBPartner(final I_I_BPartner importRecord)
	{
		final I_C_BPartner bpartner;
		bpartner = importRecord.getC_BPartner();
		if (importRecord.getName() != null)
		{
			bpartner.setName(importRecord.getName());
		}
		if (importRecord.getName2() != null)
		{
			bpartner.setName2(importRecord.getName2());
		}
		if (importRecord.getName3() != null)
		{
			bpartner.setName3(importRecord.getName3());
		}
		if (importRecord.getDUNS() != null)
		{
			bpartner.setDUNS(importRecord.getDUNS());
		}
		if (importRecord.getTaxID() != null)
		{
			bpartner.setVATaxID(importRecord.getTaxID());
		}
		if (importRecord.getNAICS() != null)
		{
			bpartner.setNAICS(importRecord.getNAICS());
		}
		if (importRecord.getDescription() != null)
		{
			bpartner.setDescription(importRecord.getDescription());
		}
		if (importRecord.getShortDescription() != null)
		{
			bpartner.setShortDescription(importRecord.getShortDescription());
		}
		if (importRecord.getC_BP_Group_ID() != 0)
		{
			bpartner.setC_BP_Group_ID(importRecord.getC_BP_Group_ID());
		}
		if (importRecord.getAD_Language() != null)
		{
			bpartner.setAD_Language(importRecord.getAD_Language());
		}
		if (importRecord.getC_InvoiceSchedule_ID() > 0)
		{
			bpartner.setC_InvoiceSchedule_ID(importRecord.getC_InvoiceSchedule_ID());
		}
		if (importRecord.getPaymentRule() != null)
		{
			bpartner.setPaymentRule(importRecord.getPaymentRule());
		}
		if (importRecord.getPO_PaymentTerm_ID() > 0)
		{
			bpartner.setPO_PaymentTerm_ID(importRecord.getPO_PaymentTerm_ID());
		}
		if (importRecord.getPaymentRulePO() != null)
		{
			bpartner.setPaymentRulePO(importRecord.getPaymentRulePO());
		}
		bpartner.setIsSEPASigned(importRecord.isSEPASigned());
		return bpartner;
	}

	/**
	 * Set type of Business Partner
	 *
	 * @param X_I_BPartner impBP
	 * @param MBPartner bp
	 */
	private static void setTypeOfBPartner(final I_I_BPartner importRecord, final I_C_BPartner bpartner)
	{
		if (importRecord.isVendor())
		{
			bpartner.setIsVendor(true);
			bpartner.setIsCustomer(false); // It is put to false since by default in C_BPartner is true
		}
		if (importRecord.isEmployee())
		{
			bpartner.setIsEmployee(true);
			bpartner.setIsCustomer(false); // It is put to false since by default in C_BPartner is true
		}
		// it has to be the last if, to subscribe the bp.setIsCustomer (false) of the other two
		if (importRecord.isCustomer())
		{
			bpartner.setIsCustomer(true);
		}
	}	// setTypeOfBPartner
}
