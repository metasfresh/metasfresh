package de.metas.notification;

import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class NotificationGroup
{
	@NonNull NotificationGroupId id;
	@NonNull NotificationGroupName name;
	@NonNull NotificationGroupCCs ccs;
	@Nullable UserId deadLetterRecipientUserId;
	boolean isNotifyOrgBPUsersOnly;
}
