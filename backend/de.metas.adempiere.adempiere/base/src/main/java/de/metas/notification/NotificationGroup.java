package de.metas.notification;

import com.google.common.collect.ImmutableSet;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class NotificationGroup
{
	@NonNull NotificationGroupId id;
	@NonNull NotificationGroupName name;
	@NonNull NotificationGroupCCs ccs;
}
