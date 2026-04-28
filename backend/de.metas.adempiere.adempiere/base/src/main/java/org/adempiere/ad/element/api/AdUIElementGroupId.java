package org.adempiere.ad.element.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_AD_UI_ElementGroup;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class AdUIElementGroupId implements RepoIdAware
{
	int repoId;

	private AdUIElementGroupId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_AD_UI_ElementGroup.COLUMNNAME_AD_UI_ElementGroup_ID);
	}

	public static AdUIElementGroupId ofRepoId(final int repoId) {return new AdUIElementGroupId(repoId);}

	@Nullable
	public static AdUIElementGroupId ofRepoIdOrNull(@Nullable final Integer repoId) {return repoId != null && repoId > 0 ? new AdUIElementGroupId(repoId) : null;}

	public static Optional<AdUIElementGroupId> optionalOfRepoId(final int repoId) {return Optional.ofNullable(ofRepoIdOrNull(repoId));}

	@JsonCreator
	public static AdUIElementGroupId ofObject(@NonNull final Object object) {return RepoIdAwares.ofObject(object, AdUIElementGroupId.class, AdUIElementGroupId::ofRepoId);}

	public static int toRepoId(@Nullable final AdUIElementGroupId id) {return id != null ? id.getRepoId() : -1;}

	@JsonValue
	public int toJson() {return getRepoId();}

	public static boolean equals(@Nullable final AdUIElementGroupId o1, @Nullable final AdUIElementGroupId o2) {return Objects.equals(o1, o2);}
}
