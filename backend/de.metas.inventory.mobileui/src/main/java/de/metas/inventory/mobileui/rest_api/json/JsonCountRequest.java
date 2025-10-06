package de.metas.inventory.mobileui.rest_api.json;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.metas.handlingunits.HuId;
import de.metas.inventory.InventoryLineId;
import de.metas.scannable_code.ScannedCode;
import de.metas.workflow.rest_api.model.WFProcessId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;

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

	@Nullable @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate bestBeforeDate;
	@Nullable String lotNo;

	boolean lineCountingDone;
}
