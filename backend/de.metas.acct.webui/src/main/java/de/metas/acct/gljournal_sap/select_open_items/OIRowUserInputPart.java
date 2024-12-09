package de.metas.acct.gljournal_sap.select_open_items;

import de.metas.acct.api.FactAcctId;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
class OIRowUserInputPart
{
	@NonNull DocumentId rowId;
	@NonNull FactAcctId factAcctId;
	boolean selected;
}
