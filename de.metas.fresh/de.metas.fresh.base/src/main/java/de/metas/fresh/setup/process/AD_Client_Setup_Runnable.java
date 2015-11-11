package de.metas.fresh.setup.process;

/*
 * #%L
 * de.metas.fresh.base
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


import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.bpartner.service.IBPartnerBL;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IClientDAO;
import org.adempiere.service.IOrgDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_ClientInfo;
import org.compiere.model.I_AD_Image;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Location;
import org.compiere.model.MPriceList;
import org.compiere.model.X_C_BP_BankAccount;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable;

import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.adempiere.model.I_M_PriceList;
import de.metas.adempiere.service.IBPartnerOrgBL;
import de.metas.banking.service.IBankingBPBankAccountDAO;
import de.metas.interfaces.I_C_BP_BankAccount;
import de.metas.payment.esr.ESRConstants;

/**
 * Runnable used to quick setup the metas Fresh installation.
 * 
 * @author tsa
 * @task 09250
 */
class AD_Client_Setup_Runnable
{
	// services
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IClientDAO clientDAO = Services.get(IClientDAO.class);
	private final transient IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final transient IBPartnerOrgBL partnerOrgBL = Services.get(IBPartnerOrgBL.class);
	private final transient IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
	private final transient IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final transient IBankingBPBankAccountDAO bankAccountDAO = Services.get(IBankingBPBankAccountDAO.class);

	// Parameters
	private boolean _executed = false;
	private Properties _ctx;
	private static final int AD_Org_ID_Main = 1000000;
	private String _companyName;
	private String _companyTaxID;
	private I_C_Location _companyAddress;
	private I_AD_Image _companyLogo;
	private int _currencyId;
	private String _adLanguage;
	//
	private String _contactFirstName;
	private String _contactLastName;
	//
	private String _iban;
	private String _accountNo;
	private int _bankId;

	public static AD_Client_Setup_Runnable newInstance()
	{
		return new AD_Client_Setup_Runnable();
	}

	private AD_Client_Setup_Runnable()
	{
		super();
	}

	public void run()
	{
		markExecuted();

		final String trxName = trxManager.getThreadInheritedTrxName(OnTrxMissingPolicy.ReturnTrxNone);
		trxManager.run(trxName, new TrxRunnable()
		{

			@Override
			public void run(String localTrxName) throws Exception
			{
				runInTrx();
			}
		});

	}

	private final void runInTrx()
	{
		final I_AD_Client adClient = getAD_Client();
		final I_AD_Image companyLogo = getCompanyLogo();
		final int currencyId = getC_Currency_ID();
		final String adLanguage = getAD_Language();

		//
		// AD_Client
		final String companyName = getCompanyName();
		adClient.setValue(companyName);
		adClient.setName(companyName);
		adClient.setAD_Language(adLanguage);
		InterfaceWrapperHelper.save(adClient, ITrx.TRXNAME_ThreadInherited);

		// AD_ClientInfo
		final I_AD_ClientInfo adClientInfo = clientDAO.retrieveClientInfo(getCtx(), adClient.getAD_Client_ID());
		InterfaceWrapperHelper.setTrxName(adClientInfo, ITrx.TRXNAME_ThreadInherited);
		adClientInfo.setLogo_ID(companyLogo.getAD_Image_ID());
		adClientInfo.setLogoReport_ID(companyLogo.getAD_Image_ID());
		adClientInfo.setLogoWeb_ID(companyLogo.getAD_Image_ID());
		InterfaceWrapperHelper.save(adClientInfo, ITrx.TRXNAME_ThreadInherited);

		//
		// AD_Org
		final I_AD_Org adOrg = getAD_Org();
		adOrg.setValue(companyName);
		adOrg.setName(companyName);
		adOrg.setIsSummary(false);
		InterfaceWrapperHelper.save(adOrg, ITrx.TRXNAME_ThreadInherited);

		//
		// AD_OrgInfo
		final I_AD_OrgInfo adOrgInfo = orgDAO.retrieveOrgInfo(getCtx(), adOrg.getAD_Org_ID(), ITrx.TRXNAME_ThreadInherited);
		adOrgInfo.setLogo_ID(companyLogo.getAD_Image_ID());
		InterfaceWrapperHelper.save(adOrgInfo, ITrx.TRXNAME_ThreadInherited);

		//
		// AD_Org Linked BPartner:
		final I_C_BPartner orgBPartner = partnerOrgBL.retrieveLinkedBPartner(adOrg);
		{
			orgBPartner.setIsCompany(true);
			orgBPartner.setValue(companyName);
			orgBPartner.setName(companyName);
			orgBPartner.setName2(null);
			orgBPartner.setCompanyName(companyName);
			orgBPartner.setVATaxID(getCompanyTaxID());
			orgBPartner.setLogo_ID(companyLogo.getAD_Image_ID());
			orgBPartner.setAD_Language(adLanguage); // i.e. Org Language
			orgBPartner.setIsCustomer(false);
			orgBPartner.setIsVendor(false);
			orgBPartner.setIsEmployee(false);
			InterfaceWrapperHelper.save(orgBPartner);
		}

		//
		// AD_Org Linked BPartner Location:
		{
			final I_C_BPartner_Location orgBPartnerLocation = InterfaceWrapperHelper.create(partnerOrgBL.retrieveOrgBPLocation(getCtx(), adOrg.getAD_Org_ID(), ITrx.TRXNAME_ThreadInherited),
					I_C_BPartner_Location.class);

			// C_Location
			final I_C_Location orgLocation = orgBPartnerLocation.getC_Location();
			InterfaceWrapperHelper.copyValues(getCompanyAddress(), orgLocation);
			InterfaceWrapperHelper.save(orgLocation);

			// C_BPartner_Location
			orgBPartnerLocation.setName(orgLocation.getCity()); // To be updated on save...
			bpartnerBL.setAddress(orgBPartnerLocation); // update Address string

			// C_BPartner_Location - EDI
			{
				final de.metas.edi.model.I_C_BPartner_Location ediBPartnerLocation = InterfaceWrapperHelper.create(orgBPartnerLocation, de.metas.edi.model.I_C_BPartner_Location.class);
				ediBPartnerLocation.setGLN(null); // TODO: how to set the GLN?!?

			}

			InterfaceWrapperHelper.save(orgBPartnerLocation);
		}

		//
		// AD_Org Linked BPartner Contact:
		{
			final I_AD_User orgContact = bpartnerDAO.retrieveDefaultContactOrNull(orgBPartner, I_AD_User.class);
			Check.assumeNotNull(orgContact, "orgContact not null"); // TODO: create if does not exist
			orgContact.setFirstName(getContactFirstName());
			orgContact.setLastName(getContactLastName());
			InterfaceWrapperHelper.save(orgContact, ITrx.TRXNAME_ThreadInherited);
		}

		//
		// AD_Org Linked Bank Account:
		{
			final I_C_BP_BankAccount orgBankAccount = InterfaceWrapperHelper.create(bankAccountDAO.retrieveDefaultBankAccount(orgBPartner), I_C_BP_BankAccount.class);
			Check.assumeNotNull(orgBankAccount, "orgBankAccount not null"); // TODO create one if does not exists
			orgBankAccount.setAccountNo(getAccountNo());
			orgBankAccount.setIBAN(getIBAN());
			orgBankAccount.setC_Bank_ID(getC_Bank_ID());
			orgBankAccount.setC_Currency_ID(currencyId);
			orgBankAccount.setA_Name("-"); // FIXME: shall we add here the contact name?!
			orgBankAccount.setIsDefault(true);
			orgBankAccount.setBPBankAcctUse(X_C_BP_BankAccount.BPBANKACCTUSE_Both);
			orgBankAccount.setBankAccountType(X_C_BP_BankAccount.BANKACCOUNTTYPE_Girokonto);
			InterfaceWrapperHelper.save(orgBankAccount, ITrx.TRXNAME_ThreadInherited);
		}

		//
		// C_AcctSchema
		final I_C_AcctSchema acctSchema = adClientInfo.getC_AcctSchema1();
		acctSchema.setC_Currency_ID(currencyId);
		acctSchema.setName(acctSchema.getGAAP() + " / " + acctSchema.getC_Currency().getISO_Code());
		InterfaceWrapperHelper.save(acctSchema);

		//
		// ESR
		ESRConstants.setEnabled(getCtx(), false);
		
		//
		// Price Lists
		{
			final I_M_PriceList priceList_None = InterfaceWrapperHelper.create(getCtx(), MPriceList.M_PriceList_ID_None, I_M_PriceList.class, ITrx.TRXNAME_ThreadInherited);
			priceList_None.setC_Currency_ID(getC_Currency_ID());
			InterfaceWrapperHelper.save(priceList_None);
		}
	}

	private final void assertNotExecuted()
	{
		Check.assume(!_executed, "not already executed");
	}

	private final void markExecuted()
	{
		assertNotExecuted();
		_executed = true;
	}

	public AD_Client_Setup_Runnable setContext(final Properties ctx)
	{
		assertNotExecuted();
		this._ctx = ctx;
		return this;
	}

	private Properties getCtx()
	{
		Check.assumeNotNull(_ctx, "_ctx not null");
		return _ctx;
	}

	public AD_Client_Setup_Runnable setCompanyName(final String companyName)
	{
		assertNotExecuted();
		this._companyName = companyName;
		return this;
	}

	private String getCompanyName()
	{
		Check.assumeNotEmpty(_companyName, "companyName not empty");
		return _companyName;
	}

	private final int getAD_Client_ID()
	{
		return Env.getAD_Client_ID(getCtx());
	}

	private final I_AD_Client getAD_Client()
	{
		final I_AD_Client adClient = clientDAO.retriveClient(getCtx(), getAD_Client_ID());
		InterfaceWrapperHelper.setTrxName(adClient, ITrx.TRXNAME_ThreadInherited);
		return adClient;
	}

	private final I_AD_Org getAD_Org()
	{
		I_AD_Org adOrg = orgDAO.retrieveOrg(getCtx(), AD_Org_ID_Main);
		InterfaceWrapperHelper.setTrxName(adOrg, ITrx.TRXNAME_ThreadInherited);
		return adOrg;
	}

	public AD_Client_Setup_Runnable setC_Currency_ID(final int currencyId)
	{
		assertNotExecuted();
		this._currencyId = currencyId;
		return this;
	}

	private int getC_Currency_ID()
	{
		Check.assume(_currencyId > 0, "currencyId > 0");
		return _currencyId;
	}

	public AD_Client_Setup_Runnable setAD_Language(final String adLanguage)
	{
		assertNotExecuted();
		this._adLanguage = adLanguage;
		return this;
	}

	private String getAD_Language()
	{
		Check.assumeNotEmpty(_adLanguage, "_adLanguage not empty");
		return _adLanguage;
	}

	private final I_C_Location getCompanyAddress()
	{
		Check.assumeNotNull(_companyAddress, "_companyAddress not null");
		return _companyAddress;
	}

	public AD_Client_Setup_Runnable setCompanyAddress(I_C_Location companyAddress)
	{
		assertNotExecuted();
		this._companyAddress = companyAddress;
		return this;
	}

	public AD_Client_Setup_Runnable setCompanyLogo(final I_AD_Image companyLogo)
	{
		assertNotExecuted();
		this._companyLogo = companyLogo;
		return this;
	}

	private final I_AD_Image getCompanyLogo()
	{
		Check.assumeNotNull(_companyLogo, "companyLogo not null");
		return _companyLogo;
	}

	public AD_Client_Setup_Runnable setCompanyTaxID(String companyTaxID)
	{
		assertNotExecuted();
		this._companyTaxID = companyTaxID;
		return this;
	}

	private final String getCompanyTaxID()
	{
		Check.assumeNotEmpty(_companyTaxID, "_companyTaxID not empty");
		return _companyTaxID.trim();
	}

	private String getContactFirstName()
	{
		Check.assumeNotEmpty(_contactFirstName, "ContactFirstName not empty");
		return _contactFirstName;
	}

	public AD_Client_Setup_Runnable setContactFirstName(String contactFirstName)
	{
		assertNotExecuted();
		this._contactFirstName = contactFirstName;
		return this;
	}

	private String getContactLastName()
	{
		Check.assumeNotEmpty(_contactLastName, "ContactLastName not empty");
		return _contactLastName;
	}

	public AD_Client_Setup_Runnable setContactLastName(String contactLastName)
	{
		assertNotExecuted();
		this._contactLastName = contactLastName;
		return this;
	}

	public AD_Client_Setup_Runnable setAccountNo(final String accountNo)
	{
		assertNotExecuted();
		this._accountNo = accountNo;
		return this;
	}

	private final String getAccountNo()
	{
		return _accountNo == null ? null : _accountNo.trim();
	}

	private String getIBAN()
	{
		return _iban == null ? null : _iban.trim();
	}

	public AD_Client_Setup_Runnable setIBAN(String iBAN)
	{
		assertNotExecuted();
		this._iban = iBAN;
		return this;
	}

	public AD_Client_Setup_Runnable setC_Bank_ID(final int bankId)
	{
		assertNotExecuted();
		this._bankId = bankId;
		return this;
	}

	private final int getC_Bank_ID()
	{
		Check.assume(_bankId > 0, "Bank set");
		return _bankId;
	}

}
