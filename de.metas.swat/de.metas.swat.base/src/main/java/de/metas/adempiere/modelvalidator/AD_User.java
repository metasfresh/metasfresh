package de.metas.adempiere.modelvalidator;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.user.api.IUserBL;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.adempiere.model.I_AD_User;

/**
 * This model validator
 * <ul>
 * <li>sets AD_User.Name from AD_User.FirstName and AD_User.LastName</li>
 * <li>Checks if the password contains no spaces and has at least a length of <code>org.compiere.util.Login.MinPasswordLength</code> (AD_AsyConfig) characters</li>
 * </ul>
 * 
 */
@Validator(I_AD_User.class)
@Callout(value = I_AD_User.class)
public class AD_User
{
	@Init
	public void init()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	// 04270
	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_AD_User.COLUMNNAME_Password, I_AD_User.COLUMNNAME_IsSystemUser })
	public void checkPassword(final I_AD_User user)
	{
		if (!user.isSystemUser())
		{
			return;
		}
		
		final String password = user.getPassword();
		
		// NOTE: Allow empty passwords because in webui we are not showing the password field so, initially it will be empty  
		if(password == null || password.isEmpty())
		{
			return;
		}
		
		Services.get(IUserBL.class).assertValidPassword(password);
	}

	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_AD_User.COLUMNNAME_Firstname, I_AD_User.COLUMNNAME_Lastname })
	@CalloutMethod(columnNames = {I_AD_User.COLUMNNAME_Firstname, I_AD_User.COLUMNNAME_Lastname})
	public void setName(final I_AD_User user)
	{
		final String contactName = Services.get(IUserBL.class).buildContactName(user.getFirstname(), user.getLastname());
		user.setName(contactName);
	}
}
