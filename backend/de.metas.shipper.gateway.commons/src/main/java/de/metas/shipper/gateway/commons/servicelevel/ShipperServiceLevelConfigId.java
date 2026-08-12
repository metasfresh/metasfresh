package de.metas.shipper.gateway.commons.servicelevel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class ShipperServiceLevelConfigId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static ShipperServiceLevelConfigId ofRepoId(final int repoId)
	{
		return new ShipperServiceLevelConfigId(repoId);
	}

	@Nullable
	public static ShipperServiceLevelConfigId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new ShipperServiceLevelConfigId(repoId) : null;
	}

	private ShipperServiceLevelConfigId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_Shipper_ServiceLevel_Config_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
