package de.metas.handlingunits.mobileui;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum HUManagerAction
{
	Dispose("dispose"),
	Move("move"),
	SetClearanceStatus("setClearanceStatus"),
	SetCurrentLocator("setCurrentLocator"),
	BulkActions("bulkActions"),
	ChangeQty("changeQty"),
	PrintLabels("printLabels"),
	;

	@NonNull private final String internalName;
}
