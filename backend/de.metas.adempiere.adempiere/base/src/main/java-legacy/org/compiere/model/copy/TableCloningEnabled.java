package org.compiere.model.copy;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_AD_Table;

@AllArgsConstructor
public enum TableCloningEnabled implements ReferenceListAwareEnum
{
	Auto(X_AD_Table.CLONINGENABLED_Auto),
	Enabled(X_AD_Table.CLONINGENABLED_Enabled),
	Disabled(X_AD_Table.CLONINGENABLED_Disabled),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<TableCloningEnabled> index = ReferenceListAwareEnums.index(values());

	@Getter private final String code;

	public static TableCloningEnabled ofCode(@NonNull final String code) {return index.ofCode(code);}

	public boolean isDisabled() {return Disabled.equals(this);}
}
