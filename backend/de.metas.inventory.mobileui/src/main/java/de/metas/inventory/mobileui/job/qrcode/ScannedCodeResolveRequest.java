package de.metas.inventory.mobileui.job.qrcode;

import com.google.common.collect.ImmutableList;
import de.metas.inventory.InventoryLineId;
import de.metas.inventory.mobileui.job.InventoryJob;
import de.metas.inventory.mobileui.job.InventoryJobLine;
import de.metas.scannable_code.ScannedCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;

@Value
@Builder
public class ScannedCodeResolveRequest
{
	@NonNull ScannedCode scannedCode;

	//
	// Context:
	@NonNull InventoryJob job;
	@Nullable InventoryLineId lineId;

	@NonNull
	public List<InventoryJobLine> getContextJobLines()
	{
		return lineId != null
				? ImmutableList.of(job.getLineById(lineId))
				: job.getLines();
	}
}
