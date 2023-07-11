package de.metas.acct.gljournal_sap.select_open_items;

import de.metas.acct.api.FactAcctId;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
class OIRowUserInputPart
{
	@NonNull DocumentId rowId;
	@NonNull FactAcctId factAcctId;
	boolean selected;
	@Nullable BigDecimal amountOverride;
}
