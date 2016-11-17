package de.metas.fresh.setup.process;

import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.CacheMgt;

import de.metas.adempiere.util.cache.CacheInterceptor;
import de.metas.interfaces.I_C_BPartner;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 * Process used to quick setup the metas Fresh installation.
 *
 * @author tsa
 * @task 09250
 */
public class AD_Client_Setup extends JavaProcess implements IProcessDefaultParametersProvider
{
	private static final String PARAM_CompanyName = I_C_BPartner.COLUMNNAME_CompanyName;
	private static final String PARAM_VATaxID = I_C_BPartner.COLUMNNAME_VATaxID;
	private static final String PARAM_C_Location_ID = "C_Location_ID";
	private static final String PARAM_Logo_ID = "Logo_ID";
	private static final String PARAM_AD_Language = "AD_Language";
	private static final String PARAM_C_Currency_ID = "C_Currency_ID";
	//
	private static final String PARAM_FirstName = "Firstname";
	private static final String PARAM_LastName = "Lastname";
	//
	private static final String PARAM_IBAN = "IBAN";
	private static final String PARAM_AccountNo = "AccountNo";
	private static final String PARAM_C_Bank_ID = "C_Bank_ID";
	//
	private static final String PARAM_Phone = "Phone";
	private static final String PARAM_Fax = "Fax";
	private static final String PARAM_EMail = "EMail";
	//
	public static final String PARAM_Description = "Description";

	private ClientSetup _clientSetup;

	@Override
	public Object getParameterDefaultValue(final String name)
	{
		final ClientSetup clientSetup = getClientSetup();
		if (PARAM_CompanyName.equalsIgnoreCase(name))
		{
			return clientSetup.getCompanyName();
		}
		else if (PARAM_VATaxID.equalsIgnoreCase(name))
		{
			return clientSetup.getCompanyTaxID();
		}
		else if (PARAM_C_Location_ID.equalsIgnoreCase(name))
		{
			return clientSetup.getCompanyAddressLocationId();
		}
		else if (PARAM_Logo_ID.equalsIgnoreCase(name))
		{
			return clientSetup.getCompanyLogoImageId();
		}
		else if (PARAM_AD_Language.equalsIgnoreCase(name))
		{
			return clientSetup.getAD_Language();
		}
		else if (PARAM_C_Currency_ID.equalsIgnoreCase(name))
		{
			return clientSetup.getC_Currency_ID();
		}
		//
		//
		else if (PARAM_FirstName.equalsIgnoreCase(name))
		{
			return clientSetup.getContactFirstName();
		}
		else if (PARAM_LastName.equalsIgnoreCase(name))
		{
			return clientSetup.getContactLastName();
		}
		//
		//
		else if (PARAM_AccountNo.equalsIgnoreCase(name))
		{
			return clientSetup.getAccountNo();
		}
		else if (PARAM_IBAN.equalsIgnoreCase(name))
		{
			return clientSetup.getIBAN();
		}
		else if (PARAM_C_Bank_ID.equalsIgnoreCase(name))
		{
			return clientSetup.getC_Bank_ID();
		}
		//
		//
		else if (PARAM_Phone.equalsIgnoreCase(name))
		{
			return clientSetup.getPhone();
		}
		else if (PARAM_Fax.equalsIgnoreCase(name))
		{
			return clientSetup.getFax();
		}
		else if (PARAM_EMail.equalsIgnoreCase(name))
		{
			return clientSetup.getEMail();
		}
		//
		//
		else if (PARAM_Description.equalsIgnoreCase(name))
		{
			return clientSetup.getBPartnerDescription();
		}
		//
		//
		else
		{
			return DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	private final ClientSetup getClientSetup()
	{
		if (_clientSetup == null)
		{
			_clientSetup = ClientSetup.newInstance(getCtx());
		}
		return _clientSetup;
	}

	@Override
	protected void prepare()
	{
		try (final IAutoCloseable cacheFlagRestorer = CacheInterceptor.temporaryDisableCaching())
		{
			final ClientSetup clientSetup = getClientSetup();

			for (final ProcessInfoParameter para : getParametersAsArray())
			{
				if (para.getParameter() == null)
				{
					continue;
				}

				final String name = para.getParameterName();
				if (PARAM_CompanyName.equalsIgnoreCase(name))
				{
					clientSetup.setCompanyName(para.getParameterAsString());
				}
				else if (PARAM_VATaxID.equalsIgnoreCase(name))
				{
					clientSetup.setCompanyTaxID(para.getParameterAsString());
				}
				else if (PARAM_C_Location_ID.equalsIgnoreCase(name))
				{
					clientSetup.setCompanyAddressByLocationId(para.getParameterAsInt());
				}
				else if (PARAM_Logo_ID.equalsIgnoreCase(name))
				{
					clientSetup.setCompanyLogoByImageId(para.getParameterAsInt());
				}
				else if (PARAM_AD_Language.equalsIgnoreCase(name))
				{
					clientSetup.setAD_Language(para.getParameterAsString());
				}
				else if (PARAM_C_Currency_ID.equalsIgnoreCase(name))
				{
					clientSetup.setC_Currency_ID(para.getParameterAsInt());
				}
				//
				//
				else if (PARAM_FirstName.equalsIgnoreCase(name))
				{
					clientSetup.setContactFirstName(para.getParameterAsString());
				}
				else if (PARAM_LastName.equalsIgnoreCase(name))
				{
					clientSetup.setContactLastName(para.getParameterAsString());
				}
				//
				//
				else if (PARAM_AccountNo.equalsIgnoreCase(name))
				{
					clientSetup.setAccountNo(para.getParameterAsString());
				}
				else if (PARAM_IBAN.equalsIgnoreCase(name))
				{
					clientSetup.setIBAN(para.getParameterAsString());
				}
				else if (PARAM_C_Bank_ID.equalsIgnoreCase(name))
				{
					clientSetup.setC_Bank_ID(para.getParameterAsInt());
				}
				//
				//
				else if (PARAM_Phone.equalsIgnoreCase(name))
				{
					clientSetup.setPhone(para.getParameterAsString());
				}
				else if (PARAM_Fax.equalsIgnoreCase(name))
				{
					clientSetup.setFax(para.getParameterAsString());
				}
				else if (PARAM_EMail.equalsIgnoreCase(name))
				{
					clientSetup.setEMail(para.getParameterAsString());
				}
				//
				//
				else if (PARAM_Description.equalsIgnoreCase(name))
				{
					clientSetup.setBPartnerDescription(para.getParameterAsString());
				}
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		try (final IAutoCloseable cacheFlagRestorer = CacheInterceptor.temporaryDisableCaching())
		{
			final ClientSetup clientSetup = getClientSetup();
			clientSetup.save();
			return MSG_OK;
		}
	}
	
	@Override
	protected void postProcess(final boolean success)
	{
		if (!success)
		{
			return;
		}
		
		// Fully reset the cache
		CacheMgt.get().reset();
	}
}
