package de.metas.rest_api.v2.workstation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.metas.product.ResourceId;
import de.metas.resource.qrcode.ResourceQRCode;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonAssignWorkstationRequest
{
	@Nullable ResourceId workstationId;
	@Nullable String workstationQRCode;

	@JsonIgnore
	public ResourceId getWorkstationIdEffective()
	{
		if (workstationId != null)
		{
			return workstationId;
		}
		else if (workstationQRCode != null && !Check.isBlank(workstationQRCode))
		{
			final ResourceQRCode qrCode = ResourceQRCode.ofGlobalQRCodeJsonString(workstationQRCode);
			return qrCode.getResourceId();
		}
		else
		{
			throw new AdempiereException("Cannot determine workstationId from " + this);
		}
	}
}
