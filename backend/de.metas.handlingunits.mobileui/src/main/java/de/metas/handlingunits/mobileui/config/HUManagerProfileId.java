package de.metas.handlingunits.mobileui.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;
import org.compiere.model.I_MobileUI_HUManager;

import javax.annotation.Nullable;

@Value
public class HUManagerProfileId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static HUManagerProfileId ofRepoId(final int repoId)
	{
		return new HUManagerProfileId(repoId);
	}

	@Nullable
	public static HUManagerProfileId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new HUManagerProfileId(repoId) : null;
	}

	private HUManagerProfileId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_MobileUI_HUManager.COLUMNNAME_MobileUI_HUManager_ID);
	}

	@JsonValue
	public int toJson()
	{
		return getRepoId();
	}
}
