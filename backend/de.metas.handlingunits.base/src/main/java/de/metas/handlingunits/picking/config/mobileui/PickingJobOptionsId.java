package de.metas.handlingunits.picking.config.mobileui;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;
import org.compiere.model.I_MobileUI_UserProfile_Picking_Job;

import javax.annotation.Nullable;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
@Value
public class PickingJobOptionsId implements RepoIdAware
{
	int repoId;

	private PickingJobOptionsId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_MobileUI_UserProfile_Picking_Job.COLUMNNAME_MobileUI_UserProfile_Picking_Job_ID);
	}

	@JsonCreator
	public static PickingJobOptionsId ofRepoId(final int repoId)
	{
		return new PickingJobOptionsId(repoId);
	}

	public static PickingJobOptionsId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final PickingJobOptionsId id) {return id != null ? id.getRepoId() : -1;}
}
