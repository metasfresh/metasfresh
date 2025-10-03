package de.metas.inventory.mobileui.job.qrcode;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.inventory.InventoryLineId;
import de.metas.inventory.mobileui.job.InventoryJob;
import de.metas.inventory.mobileui.job.InventoryJobLine;
import de.metas.product.ProductId;
import de.metas.scannable_code.ScannedCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

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
	public Set<LocatorId> getContextLocatorIds()
	{
		return getContextJobLines()
				.stream()
				.map(InventoryJobLine::getLocatorId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	public Set<ProductId> getContextProductIds()
	{
		return getContextJobLines()
				.stream()
				.map(InventoryJobLine::getProductId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	public List<InventoryJobLine> getContextJobLines()
	{
		return lineId != null
				? ImmutableList.of(job.getLineById(lineId))
				: job.getLines();
	}
}
