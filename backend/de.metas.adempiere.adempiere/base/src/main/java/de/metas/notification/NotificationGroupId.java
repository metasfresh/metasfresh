package de.metas.notification;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_AD_NotificationGroup;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class NotificationGroupId implements RepoIdAware
{
	int repoId;

	private NotificationGroupId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_AD_NotificationGroup.COLUMNNAME_AD_NotificationGroup_ID);
	}

	public static NotificationGroupId ofRepoId(final int repoId) {return new NotificationGroupId(repoId);}

	@Nullable
	public static NotificationGroupId ofRepoIdOrNull(@Nullable final Integer repoId) {return repoId != null && repoId > 0 ? new NotificationGroupId(repoId) : null;}

	public static Optional<NotificationGroupId> optionalOfRepoId(final int repoId) {return Optional.ofNullable(ofRepoIdOrNull(repoId));}

	@JsonCreator
	public static NotificationGroupId ofObject(@NonNull final Object object) {return RepoIdAwares.ofObject(object, NotificationGroupId.class, NotificationGroupId::ofRepoId);}

	public static int toRepoId(@Nullable final NotificationGroupId id) {return id != null ? id.getRepoId() : -1;}

	@JsonValue
	public int toJson() {return getRepoId();}

	public static boolean equals(@Nullable final NotificationGroupId o1, @Nullable final NotificationGroupId o2) {return Objects.equals(o1, o2);}
}
