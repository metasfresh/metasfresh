package de.metas.handlingunits.inventory.draftlinescreator;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DraftInventoryLinesCreateResponse
{
	long countInventoryLines;
}
