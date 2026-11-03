package de.metas.distribution.mobileui.launchers;

import de.metas.distribution.ddorder.DDOrderId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.api.Params;

@Value
@Builder
public class DistributionWFProcessStartParams
{
	@NonNull DDOrderId ddOrderId;
	boolean isInTransit;

	public Params toParams()
	{
		return Params.builder()
				.value("ddOrderId", ddOrderId.getRepoId())
				.value("isInTransit", isInTransit)
				.build();
	}

	public static DistributionWFProcessStartParams ofParams(final Params params)
	{
		try
		{
			//noinspection ConstantConditions
			return builder()
					.ddOrderId(params.getParameterAsId("ddOrderId", DDOrderId.class))
					.build();
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Invalid params: " + params, ex);
		}
	}

}
