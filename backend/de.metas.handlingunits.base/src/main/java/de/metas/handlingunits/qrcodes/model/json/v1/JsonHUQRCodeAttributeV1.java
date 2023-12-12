package de.metas.handlingunits.qrcodes.model.json.v1;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.mm.attributes.AttributeCode;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonHUQRCodeAttributeV1
{
	@NonNull AttributeCode code;
	@NonNull String displayName;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable String value;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Nullable String valueRendered;
}
