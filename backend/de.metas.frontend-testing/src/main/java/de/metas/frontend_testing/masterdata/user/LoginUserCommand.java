package de.metas.frontend_testing.masterdata.user;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.printing.model.I_AD_Printer_Config;
import de.metas.printing.model.I_AD_Printer_Matching;
import de.metas.security.IRoleDAO;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import de.metas.user.api.IUserBL;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_User;

import java.util.UUID;

@Builder
public class LoginUserCommand
{
	@NonNull private final IUserBL userBL = Services.get(IUserBL.class);
	@NonNull private final IRoleDAO roleDAO = Services.get(IRoleDAO.class);

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonLoginUserRequest request;
	@NonNull private final Identifier identifier;

	public JsonLoginUserResponse execute()
	{
		final String login = identifier.toUniqueString();

		final I_AD_User user = InterfaceWrapperHelper.newInstance(I_AD_User.class);
		user.setFirstname(login);
		user.setLastname(login);
		user.setEMail(login + "@metasfresh.com");
		user.setAD_Language(request.getLanguage());
		user.setIsSystemUser(true);
		user.setLogin(login);
		InterfaceWrapperHelper.save(user);
		userBL.save(user);
		final UserId userId = UserId.ofRepoId(user.getAD_User_ID());

		//noinspection UnnecessaryLocalVariable
		final String password = login; // for convenience, we use the same password as user // UUID.randomUUID().toString().replace("-", "");
		userBL.changePasswordAndSave(user, password);

		roleDAO.deleteUserRolesByUserId(userId);
		roleDAO.createUserRoleAssignmentIfMissing(userId, RoleId.WEBUI);

		createPrinterConfig(userId);

		return JsonLoginUserResponse.builder()
				.username(login)
				.password(password)
				.build();
	}

	private static void createPrinterConfig(final UserId userId)
	{
		final I_AD_Printer_Config printerConfig = InterfaceWrapperHelper.newInstance(I_AD_Printer_Config.class);
		printerConfig.setAD_User_PrinterMatchingConfig_ID(userId.getRepoId());
		InterfaceWrapperHelper.save(printerConfig);

		final I_AD_Printer_Matching printerMatching = InterfaceWrapperHelper.newInstance(I_AD_Printer_Matching.class);
		printerMatching.setAD_Printer_Config_ID(printerConfig.getAD_Printer_Config_ID());
		printerMatching.setAD_Printer_ID(MasterdataContext.STANDARD_AD_PRINTER_ID);
		printerMatching.setAD_PrinterHW_ID(MasterdataContext.PRINT_TO_DISK_AD_PRINTERHW_ID);
		InterfaceWrapperHelper.save(printerMatching);
	}
}
