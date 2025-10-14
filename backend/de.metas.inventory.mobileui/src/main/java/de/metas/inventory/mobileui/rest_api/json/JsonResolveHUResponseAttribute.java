package de.metas.inventory.mobileui.rest_api.json;

import de.metas.inventory.mobileui.job.qrcode.ResolveHUResponse;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeValueType;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonResolveHUResponseAttribute
{
	@NonNull AttributeCode code;
	@NonNull String caption;
	@NonNull JsonAttributeValueType valueType;
	@Nullable Object value;
}
