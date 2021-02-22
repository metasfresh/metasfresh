package de.metas.adempiere.modelvalidator;

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.i18n.Language;
import de.metas.title.Title;
import de.metas.title.TitleId;
import de.metas.title.TitleRepository;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.CopyRecordFactory;
import org.compiere.SpringContextHolder;
import org.compiere.model.ModelValidator;

import de.metas.adempiere.model.I_AD_User;
import de.metas.user.UserPOCopyRecordSupport;
import de.metas.user.api.IUserBL;
import de.metas.util.Check;
import de.metas.util.Services;

import java.util.Optional;

/**
 * This model validator
 * <ul>
 * <li>sets AD_User.Name from AD_User.FirstName and AD_User.LastName</li>
 * <li>Checks if the password contains no spaces and has at least a length of <code>org.compiere.util.Login.MinPasswordLength</code> (AD_AsyConfig) characters</li>
 * </ul>
 *
 */
@Interceptor(I_AD_User.class)
@Callout(I_AD_User.class)
public class AD_User
{
	private final IBPartnerBL bpPartnerService = Services.get(IBPartnerBL.class);
	private final TitleRepository titleRepository = SpringContextHolder.instance.getBean(TitleRepository.class);

	@Init
	public void init()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);

		CopyRecordFactory.enableForTableName(I_AD_User.Table_Name);
		CopyRecordFactory.registerCopyRecordSupport(I_AD_User.Table_Name, UserPOCopyRecordSupport.class);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = { I_AD_User.COLUMNNAME_Firstname, I_AD_User.COLUMNNAME_Lastname })
	@CalloutMethod(columnNames = { I_AD_User.COLUMNNAME_Firstname, I_AD_User.COLUMNNAME_Lastname })
	public void setName(final I_AD_User user)
	{
		final IUserBL userService = Services.get(IUserBL.class);

		final String contactName = userService.buildContactName(user.getFirstname(), user.getLastname());
		if (Check.isEmpty(contactName))
		{
			return; // make sure not to overwrite an existing name with an empty string!
		}
		user.setName(contactName);
	}

	@ModelChange(timings = {ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE},
			ifColumnsChanged = {I_AD_User.COLUMNNAME_C_Title_ID})
	public void setTitle(final I_AD_User user)
	{
		if (user.getC_Title_ID() > 0)
		{
			String title = extractTitle(user);
			user.setTitle(title);
		}
		else
		{
			user.setTitle("");
		}
	}

	private String extractTitle(I_AD_User user)
	{
		String userTitle = "";
		final Optional<Language> languageForModel = bpPartnerService.getLanguageForModel(user);
		final Title title = titleRepository.getByIdAndLang(TitleId.ofRepoId(user.getC_Title_ID()), languageForModel.orElse(null));
		if (title != null)
		{
			userTitle = title.getTitle();
		}

		return userTitle;
	}
}
