package de.metas.inventory.mobileui.rest_api.json;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableMap;
import de.metas.handlingunits.HuId;
import de.metas.inventory.InventoryLineId;
import de.metas.scannable_code.ScannedCode;
import de.metas.workflow.rest_api.model.WFProcessId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.mm.attributes.AttributeCode;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonCountRequest
{
	@NonNull WFProcessId wfProcessId;
	@NonNull InventoryLineId lineId;

	@Nullable ScannedCode scannedCode;
	@Nullable HuId huId;

	@NonNull BigDecimal qtyCount;
	boolean lineCountingDone;

	@Nullable List<Attribute> attributes;

	@JsonIgnore
	@NonNull
	public Map<AttributeCode, String> getAttributesAsMap()
	{
		if (attributes == null || attributes.isEmpty())
		{
			return ImmutableMap.of();
		}

		final HashMap<AttributeCode, String> result = new HashMap<>();
		for (final Attribute attribute : attributes)
		{
			result.put(attribute.getCode(), attribute.getValue());
		}

		return result;
	}

	//
	//
	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	public static class Attribute
	{
		@NonNull AttributeCode code;
		@Nullable String value;
	}
}
