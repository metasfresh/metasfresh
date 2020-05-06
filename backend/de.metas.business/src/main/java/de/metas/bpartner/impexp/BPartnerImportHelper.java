package de.metas.bpartner.impexp;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_I_BPartner;
import org.compiere.model.ModelValidationEngine;

import de.metas.bpartner.impexp.BPartnersCache.BPartner;
import de.metas.impexp.processing.IImportInterceptor;
import de.metas.util.Check;
import lombok.NonNull;

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

	public void importRecord(final BPartnerImportContext context)
	{
		final BPartnersCache cache = context.getBpartnersCache();

		final BPartner bpartner;
		final boolean insertMode;
		if (!context.isCurrentBPartnerIdSet())	// Insert new BPartner
		{
			insertMode = true;
			final I_C_BPartner bpartnerRecord = createNewBPartnerNoSave(context.getCurrentImportRecord());
			bpartner = cache.newBPartner(bpartnerRecord);
		}
		else
		// Update existing BPartner
		{
			insertMode = false;
			bpartner = context.getCurrentBPartner();
			updateExistingBPartnerNoSave(bpartner.getRecord(), context.getCurrentImportRecord());
		}

		//
		updateBPartnerOnInsertOrUpdate(bpartner.getRecord(), context.getCurrentImportRecord(), insertMode);

		//
		// Update after INSERT/UPDATE

		ModelValidationEngine.get().fireImportValidate(process, context.getCurrentImportRecord(), bpartner.getRecord(), IImportInterceptor.TIMING_AFTER_IMPORT);

		bpartner.save();

		context.setCurrentBPartnerId(bpartner.getIdOrNull());
	}

	// TODO: figure it out why this code is not part of the updateExistingBPartner
	private static void updateBPartnerOnInsertOrUpdate(final I_C_BPartner bpartnerRecord, final I_I_BPartner importRecord, final boolean insertMode)
	{
		//
		// CompanyName
		final String companyName = importRecord.getCompanyname();
		if (!Check.isEmpty(companyName, true))
		{
			bpartnerRecord.setIsCompany(true);
			bpartnerRecord.setCompanyName(companyName.trim());
			bpartnerRecord.setName(companyName.trim());
		}
		else if (insertMode)
		{
			bpartnerRecord.setIsCompany(false);
			bpartnerRecord.setCompanyName(null);
		}

		//
		// Type (Vendor, Customer, Employee)
		setTypeOfBPartner(importRecord, bpartnerRecord);

		final String url = importRecord.getURL();

		if (!Check.isEmpty(url))
		{
			bpartnerRecord.setURL(url);
		}

		final String url3 = importRecord.getURL3();

		if (!Check.isEmpty(url3))
		{
			bpartnerRecord.setURL3(url3);
		}
	}

	private I_C_BPartner createNewBPartnerNoSave(final I_I_BPartner from)
	{
		final I_C_BPartner bpartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);

		bpartner.setExternalId(from.getC_BPartner_ExternalId());
		bpartner.setAD_Org_ID(from.getAD_Org_ID());
		//
		bpartner.setValue(extractBPValue(from));
		bpartner.setName(extractBPName(from));
		bpartner.setName2(from.getName2());
		bpartner.setName3(from.getName3());
		bpartner.setDescription(from.getDescription());
		bpartner.setShortDescription(from.getShortDescription());
		bpartner.setDUNS(from.getDUNS());
		bpartner.setVATaxID(from.getTaxID());
		bpartner.setC_InvoiceSchedule_ID(from.getC_InvoiceSchedule_ID());
		bpartner.setPaymentRule(from.getPaymentRule());
		bpartner.setPaymentRulePO(from.getPaymentRulePO());
		bpartner.setPO_PaymentTerm_ID(from.getPO_PaymentTerm_ID());
		bpartner.setC_PaymentTerm_ID(from.getC_PaymentTerm_ID());
		bpartner.setNAICS(from.getNAICS());
		bpartner.setC_BP_Group_ID(from.getC_BP_Group_ID());
		bpartner.setAD_Language(from.getAD_Language());
		bpartner.setIsSEPASigned(from.isSEPASigned());
		bpartner.setIsActive(from.isActiveStatus());

		if (from.getDebtorId() > 0)
		{
			bpartner.setDebtorId(from.getDebtorId());
		}
		if (from.getCreditorId() > 0)
		{
			bpartner.setCreditorId(from.getCreditorId());
		}

		bpartner.setMemo(from.getC_BPartner_Memo());
		bpartner.setDeliveryViaRule(from.getDeliveryViaRule());
		bpartner.setM_Shipper_ID(from.getM_Shipper_ID());
		bpartner.setVendorCategory(from.getVendorCategory());
		bpartner.setCustomerNoAtVendor(from.getCustomerNoAtVendor());
		bpartner.setQualification(from.getQualification());
		bpartner.setM_PricingSystem_ID(from.getM_PricingSystem_ID());
		bpartner.setPO_PricingSystem_ID(from.getPO_PricingSystem_ID());
		bpartner.setMemo_Delivery(from.getMemo_Delivery());
		bpartner.setMemo_Invoicing(from.getMemo_Invoicing());
		bpartner.setGlobalId(from.getGlobalId());

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
		String bpValue = importRecord.getBPValue();
		return Check.isEmpty(bpValue, true) ? importRecord.getEMail() : bpValue;
	}

	private static void updateExistingBPartnerNoSave(
			@NonNull final I_C_BPartner bpartner,
			@NonNull final I_I_BPartner from)
	{
		final String partnerExternalId = from.getC_BPartner_ExternalId();
		if (partnerExternalId != null)
		{
			bpartner.setExternalId(partnerExternalId);
		}
		if (from.getName() != null)
		{
			bpartner.setName(from.getName());
		}
		if (from.getName2() != null)
		{
			bpartner.setName2(from.getName2());
		}
		if (from.getName3() != null)
		{
			bpartner.setName3(from.getName3());
		}
		if (from.getDUNS() != null)
		{
			bpartner.setDUNS(from.getDUNS());
		}
		if (from.getTaxID() != null)
		{
			bpartner.setVATaxID(from.getTaxID());
		}
		if (from.getNAICS() != null)
		{
			bpartner.setNAICS(from.getNAICS());
		}
		if (from.getDescription() != null)
		{
			bpartner.setDescription(from.getDescription());
		}
		if (from.getShortDescription() != null)
		{
			bpartner.setShortDescription(from.getShortDescription());
		}
		if (from.getC_BP_Group_ID() != 0)
		{
			bpartner.setC_BP_Group_ID(from.getC_BP_Group_ID());
		}
		if (from.getAD_Language() != null)
		{
			bpartner.setAD_Language(from.getAD_Language());
		}
		if (from.getC_InvoiceSchedule_ID() > 0)
		{
			bpartner.setC_InvoiceSchedule_ID(from.getC_InvoiceSchedule_ID());
		}
		if (from.getPaymentRule() != null)
		{
			bpartner.setPaymentRule(from.getPaymentRule());
		}
		if (from.getPO_PaymentTerm_ID() > 0)
		{
			bpartner.setPO_PaymentTerm_ID(from.getPO_PaymentTerm_ID());
		}

		if (from.getC_PaymentTerm_ID() > 0)
		{
			bpartner.setC_PaymentTerm_ID(from.getC_PaymentTerm_ID());
		}

		if (from.getPaymentRulePO() != null)
		{
			bpartner.setPaymentRulePO(from.getPaymentRulePO());
		}
		if (from.getVendorCategory() != null)
		{
			bpartner.setVendorCategory(from.getVendorCategory());
		}
		if (from.getCustomerNoAtVendor() != null)
		{
			bpartner.setCustomerNoAtVendor(from.getCustomerNoAtVendor());
		}
		if (from.getQualification() != null)
		{
			bpartner.setQualification(from.getQualification());
		}

		bpartner.setIsSEPASigned(from.isSEPASigned());
		bpartner.setIsActive(from.isActiveStatus());
		bpartner.setDebtorId(from.getDebtorId());
		bpartner.setCreditorId(from.getCreditorId());
		if (from.getC_BPartner_Memo() != null)
		{
			bpartner.setMemo(from.getC_BPartner_Memo());
		}

		if (!Check.isEmpty(from.getDeliveryViaRule(), true))
		{
			bpartner.setDeliveryViaRule(from.getDeliveryViaRule());
		}
		if (from.getM_Shipper_ID() > 0)
		{
			bpartner.setM_Shipper_ID(from.getM_Shipper_ID());
		}
		if (from.getM_PricingSystem_ID() > 0)
		{
			bpartner.setM_PricingSystem_ID(from.getM_PricingSystem_ID());
		}
		if (from.getPO_PricingSystem_ID() > 0)
		{
			bpartner.setPO_PricingSystem_ID(from.getPO_PricingSystem_ID());
		}

		final String memoInvoicing = from.getMemo_Invoicing();
		if (!Check.isEmpty(memoInvoicing))
		{
			bpartner.setMemo_Invoicing(memoInvoicing);
		}

		final String memoDelivery = from.getMemo_Delivery();
		if (!Check.isEmpty(memoDelivery))
		{
			bpartner.setMemo_Delivery(memoDelivery);
		}

		final String globalId = from.getGlobalId();
		if (!Check.isEmpty(globalId))
		{
			bpartner.setGlobalId(globalId);
		}
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
