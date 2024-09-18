package de.metas.security.impl;

import de.metas.copy_with_details.template.CopyTemplateCustomizer;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import org.compiere.model.I_AD_Role;
import org.compiere.model.POInfo;
import org.compiere.model.copy.ValueToCopy;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

@Component
public class AD_Role_CopyTemplateCustomizer implements CopyTemplateCustomizer
{
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IUserDAO userDAO = Services.get(IUserDAO.class);

	private static final AdMessageKey MSG_AD_Role_Name_Unique = AdMessageKey.of("AD_Role_Unique_Name");
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd:HH:mm:ss");

	@Override
	public String getTableName() {return I_AD_Role.Table_Name;}

	@Override
	public ValueToCopy extractValueToCopy(final POInfo poInfo, final String columnName)
	{
		return I_AD_Role.COLUMNNAME_Name.equals(columnName) ? ValueToCopy.explicitValueToSet(makeUniqueName()) : ValueToCopy.NOT_SPECIFIED;
	}

	private String makeUniqueName()
	{
		final Properties ctx = Env.getCtx();
		final int adUserId = Env.getAD_User_ID(ctx);
		final String adLanguage = Env.getAD_Language(ctx);

		final String timestampStr = DATE_FORMATTER.format(LocalDateTime.now());
		final String userName = userDAO.retrieveUserFullName(adUserId);

		// Create the name using the text from the specific AD_Message.
		return msgBL.getMsg(adLanguage, MSG_AD_Role_Name_Unique, new String[] { userName, timestampStr });
	}
}
