package org.compiere.model;

import de.metas.process.PInstanceId;
import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class CreateSelectionResponse
{
	@NonNull PInstanceId selectionId;
	
	/**
	 * How many items were added to that selection (not how many items that selection contains)
	 */
	int count;
}
