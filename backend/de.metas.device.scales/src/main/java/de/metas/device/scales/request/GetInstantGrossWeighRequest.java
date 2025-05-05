package de.metas.device.scales.request;

import com.google.common.base.MoreObjects;
import de.metas.device.api.IDeviceRequest;

/**
 * Requests the "instant" weight from the scale.
 * This result might be "stable" or "dynamic".
 *
 */
public class GetInstantGrossWeighRequest implements IDeviceRequest<GetWeightResponse>
{
	@Override
	public Class<GetWeightResponse> getResponseClass()
	{
		return GetWeightResponse.class;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this).toString();
	}
}
