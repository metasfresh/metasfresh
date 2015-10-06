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


import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.I_AD_Image;
import org.compiere.model.I_C_Location;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import de.metas.interfaces.I_C_BPartner;

/**
 * Process used to quick setup the metas Fresh installation.
 * 
 * @author tsa
 * @task 09250
 */
public class AD_Client_Setup extends SvrProcess
{
	private static final String PARAM_CompanyName = I_C_BPartner.COLUMNNAME_CompanyName;
	private String p_CompanyName;
	private static final String PARAM_VATaxID = I_C_BPartner.COLUMNNAME_VATaxID;
	private String p_VATaxID;
	private static final String PARAM_C_Location_ID = "C_Location_ID";
	private int p_C_Location_ID;
	private static final String PARAM_Logo_ID = "Logo_ID";
	private int p_Logo_ID;
	private static final String PARAM_AD_Language = "AD_Language";
	private String p_AD_Language;
	private static final String PARAM_C_Currency_ID = "C_Currency_ID";
	private int p_C_Currency_ID;
	//
	private static final String PARAM_FirstName = "Firstname";
	private String p_ContactFirstName;
	private static final String PARAM_LastName = "Lastname";
	private String p_ContactLastName;
	//
	private static final String PARAM_IBAN = "IBAN";
	private String p_IBAN;
	private static final String PARAM_AccountNo = "AccountNo";
	private String p_AccountNo;
	private static final String PARAM_C_Bank_ID = "C_Bank_ID";
	private int p_C_Bank_ID;

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParameter())
		{
			final String name = para.getParameterName();
			if (PARAM_CompanyName.equalsIgnoreCase(name))
			{
				this.p_CompanyName = para.getParameterAsString();
			}
			else if (PARAM_VATaxID.equalsIgnoreCase(name))
			{
				this.p_VATaxID = para.getParameterAsString();
			}
			else if (PARAM_C_Location_ID.equalsIgnoreCase(name))
			{
				this.p_C_Location_ID = para.getParameterAsInt();
			}
			else if (PARAM_Logo_ID.equalsIgnoreCase(name))
			{
				this.p_Logo_ID = para.getParameterAsInt();
			}
			else if (PARAM_AD_Language.equalsIgnoreCase(name))
			{
				this.p_AD_Language = para.getParameterAsString();
			}
			else if (PARAM_C_Currency_ID.equalsIgnoreCase(name))
			{
				this.p_C_Currency_ID = para.getParameterAsInt();
			}
			//
			//
			else if (PARAM_FirstName.equalsIgnoreCase(name))
			{
				this.p_ContactFirstName = para.getParameterAsString();
			}
			else if (PARAM_LastName.equalsIgnoreCase(name))
			{
				this.p_ContactLastName = para.getParameterAsString();
			}
			//
			//
			else if (PARAM_AccountNo.equalsIgnoreCase(name))
			{
				this.p_AccountNo = para.getParameterAsString();
			}
			else if (PARAM_IBAN.equalsIgnoreCase(name))
			{
				this.p_IBAN = para.getParameterAsString();
			}
			else if (PARAM_C_Bank_ID.equalsIgnoreCase(name))
			{
				this.p_C_Bank_ID = para.getParameterAsInt();
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_C_Location companyAddress = getCompanyAddress();
		final I_AD_Image companyLogo = getCompanyLogo();

		AD_Client_Setup_Runnable.newInstance()
				.setContext(getCtx())
				.setCompanyName(p_CompanyName)
				.setCompanyTaxID(p_VATaxID)
				.setCompanyAddress(companyAddress)
				.setCompanyLogo(companyLogo)
				.setAD_Language(p_AD_Language)
				.setC_Currency_ID(p_C_Currency_ID)
				//
				.setContactFirstName(p_ContactFirstName)
				.setContactLastName(p_ContactLastName)
				//
				.setAccountNo(p_AccountNo)
				.setIBAN(p_IBAN)
				.setC_Bank_ID(p_C_Bank_ID)
				//
				.run();
		return MSG_OK;
	}

	private final I_C_Location getCompanyAddress()
	{
		if (p_C_Location_ID <= 0)
		{
			throw new FillMandatoryException(PARAM_C_Location_ID);
		}
		I_C_Location companyAddress = InterfaceWrapperHelper.create(getCtx(), p_C_Location_ID, I_C_Location.class, ITrx.TRXNAME_ThreadInherited);
		Check.assumeNotNull(companyAddress, "companyAddress not null");
		return companyAddress;
	}

	private final I_AD_Image getCompanyLogo()
	{
		if (p_Logo_ID <= 0)
		{
			throw new FillMandatoryException(PARAM_Logo_ID);
		}
		I_AD_Image companyLogo = InterfaceWrapperHelper.create(getCtx(), p_Logo_ID, I_AD_Image.class, ITrx.TRXNAME_ThreadInherited);
		Check.assumeNotNull(companyLogo, "companyLogo not null");
		return companyLogo;
	}

}
