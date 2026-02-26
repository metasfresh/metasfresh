package de.metas.inventory.mobileui.launchers;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.inventory.InventoryReference;
import de.metas.inventory.mobileui.deps.warehouse.WarehouseService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class InventoryJobReferenceMapperService
{
	@NonNull private final WarehouseService warehouseService;

	InventoryJobReferenceMapper newMapper()
	{
		return InventoryJobReferenceMapper.builder()
				.warehouses(warehouseService.newLoadingCache())
				.build();
	}

	public Stream<InventoryJobReference> toInventoryJobReferencesStream(final Stream<InventoryReference> stream)
	{
		final ImmutableList<InventoryReference> list = stream.collect(ImmutableList.toImmutableList());
		if (list.isEmpty()) {return Stream.empty();}

		return newMapper().toInventoryJobReferencesStream(list);
	}

}
