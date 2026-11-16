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
	ScanGRAI("scanGRAI"),
	/** create an HU from a scanned product label that is no existing HU (propose + create) */
	CreateHU("createHU"),
	;

	@NonNull private final String internalName;
}
