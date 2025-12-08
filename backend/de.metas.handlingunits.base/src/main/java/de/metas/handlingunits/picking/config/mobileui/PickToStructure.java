package de.metas.handlingunits.picking.config.mobileui;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PickToStructure
{
	// NOTE to dev: pls keep in sync with metasfresh/misc/services/mobile-webui/mobile-webui-frontend/src/reducers/wfProcesses/picking/PickToStructure.js

	LU_TU("LU_TU"),
	TU("TU"),
	LU_CU("LU_CU"),
	CU("CU"),
	;

	private final String json;

	@JsonValue
	public String toJson() {return json;}
}
