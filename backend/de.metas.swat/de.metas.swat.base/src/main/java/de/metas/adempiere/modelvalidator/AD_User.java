package de.metas.adempiere.modelvalidator;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.copy_with_details.CopyRecordFactory;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.Language;
import de.metas.title.Title;
import de.metas.title.TitleId;
import de.metas.title.TitleRepository;
import de.metas.user.UserId;
import de.metas.user.api.IUserBL;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;

import java.util.Optional;

/**
 * This model validator
 * <ul>
 * <li>sets AD_User.Name from AD_User.FirstName and AD_User.LastName</li>
 * <li>Checks if the password contains no spaces and has at least a length of <code>org.compiere.util.Login.MinPasswordLength</code> (AD_AsyConfig) characters</li>
 * </ul>
 */
@Interceptor(I_AD_User.class)
@Callout(I_AD_User.class)
public class AD_User
{
	private static final AdMessageKey MSG_UserDelete = AdMessageKey.of("LoggedInUserCannotBeDeleted");

	private final IBPartnerBL bpPartnerService = Services.get(IBPartnerBL.class);
	private final TitleRepository titleRepository = SpringContextHolder.instance.getBean(TitleRepository.class);
	private final IUserBL userBL = Services.get(IUserBL.class);

	@Init
	public void init()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);

		CopyRecordFactory.enableForTableName(org.compiere.model.I_AD_User.Table_Name);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = { org.compiere.model.I_AD_User.COLUMNNAME_Firstname, org.compiere.model.I_AD_User.COLUMNNAME_Lastname })
	@CalloutMethod(columnNames = { org.compiere.model.I_AD_User.COLUMNNAME_Firstname, org.compiere.model.I_AD_User.COLUMNNAME_Lastname })
	public void setName(final org.compiere.model.I_AD_User user)
	{
		final String contactName = IUserBL.buildContactName(user.getFirstname(), user.getLastname());
		if (Check.isEmpty(contactName))
		{
			return; // make sure not to overwrite an existing name with an empty string!
		}
		user.setName(contactName);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { org.compiere.model.I_AD_User.COLUMNNAME_C_Title_ID })
	public void setTitle(final org.compiere.model.I_AD_User user)
	{
		if (user.getC_Title_ID() > 0)
		{
			final String title = extractTitle(user);
			user.setTitle(title);
		}
		else
		{
			user.setTitle("");
		}
	}

	private String extractTitle(org.compiere.model.I_AD_User user)
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

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifUIAction = true)
	public void afterSave(@NonNull final I_AD_User userRecord)
	{
		final BPartnerId bPartnerId = BPartnerId.ofRepoIdOrNull(userRecord.getC_BPartner_ID());

		if (bPartnerId == null)
		{
			//nothing to do
			return;
		}
		bpPartnerService.updateNameAndGreetingFromContacts(bPartnerId);
	}

	@ModelChange(timings = {ModelValidator.TYPE_BEFORE_DELETE},
			ifUIAction = true)
	public void beforeDelete_UIAction(@NonNull final I_AD_User userRecord)
	{
		final UserId loggedInUserId = Env.getLoggedUserIdIfExists().orElse(null);
		if (loggedInUserId != null && loggedInUserId.getRepoId() == userRecord.getAD_User_ID())
		{
			throw new AdempiereException(MSG_UserDelete)
					.setParameter("AD_User_ID", userRecord.getAD_User_ID())
					.setParameter("Name", userRecord.getName());
		}
		userBL.deleteUserDependency(userRecord);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_DELETE },
			ifUIAction = true)
	public void afterDelete(@NonNull final I_AD_User userRecord)
	{
		final BPartnerId bPartnerId = BPartnerId.ofRepoIdOrNull(userRecord.getC_BPartner_ID());

		if (bPartnerId == null)
		{
			//nothing to do
			return;
		}
		bpPartnerService.updateNameAndGreetingFromContacts(bPartnerId);
	}
}
