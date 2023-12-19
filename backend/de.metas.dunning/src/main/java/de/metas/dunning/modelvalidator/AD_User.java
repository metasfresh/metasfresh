package de.metas.dunning.modelvalidator;

import de.metas.notification.INotificationGroupNameRepository;
import de.metas.notification.IUserNotificationsConfigRepository;
import de.metas.notification.NotificationType;
import de.metas.notification.impl.UserNotificationGroupCreateRequest;
import de.metas.notification.impl.UserNotificationGroupDeleteRequest;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_User;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import static de.metas.dunning.api.IDunningBL.MASS_DUNNING_NOTIFICATION_GROUP_NAME;

/**
 * sync flags
 *
 * @author adi
 */
@Interceptor(I_AD_User.class)
@Component
class AD_User
{
	private final IUserNotificationsConfigRepository userNotificationsConfigRepository = Services.get(IUserNotificationsConfigRepository.class);
	private final INotificationGroupNameRepository notificationGroupNamesRepo = Services.get(INotificationGroupNameRepository.class);

	@ModelChange(
			timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE },
			ifColumnsChanged = I_AD_User.COLUMNNAME_IsReceiveMassDunningReports)
	public void updateDunning(final I_AD_User user)
	{
		final UserId userId = UserId.ofRepoId(user.getAD_User_ID());
		if (user.isReceiveMassDunningReports())
		{
			userNotificationsConfigRepository.createOrUpdate(UserNotificationGroupCreateRequest.builder()
					.userId(userId)
					.orgId(OrgId.ofRepoId(user.getAD_Org_ID()))
					.notificationGroupId(notificationGroupNamesRepo.getNotificationGroupId(MASS_DUNNING_NOTIFICATION_GROUP_NAME))
					.notificationType(NotificationType.EMail)
					.build());
		}
		else
		{
			userNotificationsConfigRepository.delete(UserNotificationGroupDeleteRequest.builder()
					.userId(userId)
					.notificationGroupId(notificationGroupNamesRepo.getNotificationGroupId(MASS_DUNNING_NOTIFICATION_GROUP_NAME))
					.build());
		}
	}
}
