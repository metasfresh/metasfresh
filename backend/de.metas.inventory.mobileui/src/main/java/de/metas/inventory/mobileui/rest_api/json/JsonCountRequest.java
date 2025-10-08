package de.metas.inventory.mobileui.rest_api.json;

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
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonCountRequest
{
	@NonNull WFProcessId wfProcessId;
	@NonNull InventoryLineId lineId;

	@NonNull ScannedCode scannedCode;
	@Nullable HuId huId;

	@NonNull BigDecimal qtyCount;
	boolean lineCountingDone;

	@Nullable List<Attribute> attributes;

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
