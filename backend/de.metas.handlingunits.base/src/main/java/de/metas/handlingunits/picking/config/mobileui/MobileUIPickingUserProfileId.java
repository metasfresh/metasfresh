package de.metas.handlingunits.picking.config.mobileui;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;
import org.compiere.model.I_MobileUI_UserProfile_Picking;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Value
public class MobileUIPickingUserProfileId implements RepoIdAware
{
	int repoId;

	private MobileUIPickingUserProfileId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_MobileUI_UserProfile_Picking.COLUMNNAME_MobileUI_UserProfile_Picking_ID);
	}

	@JsonCreator
	public static MobileUIPickingUserProfileId ofRepoId(final int repoId)
	{
		return new MobileUIPickingUserProfileId(repoId);
	}

	public static MobileUIPickingUserProfileId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
