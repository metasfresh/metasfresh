package org.compiere.model.copy;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_AD_Table;

@AllArgsConstructor
public enum TableWhenChildCloningStrategy implements ReferenceListAwareEnum
{
	AllowCloning(X_AD_Table.WHENCHILDCLONINGSTRATEGY_AllowCloning),
	AlwaysInclude(X_AD_Table.WHENCHILDCLONINGSTRATEGY_AlwaysInclude),
	Skip(X_AD_Table.WHENCHILDCLONINGSTRATEGY_Skip),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<TableWhenChildCloningStrategy> index = ReferenceListAwareEnums.index(values());

	@Getter private final String code;

	public static TableWhenChildCloningStrategy ofCode(@NonNull final String code) {return index.ofCode(code);}

	public boolean isSkip() {return Skip.equals(this);}

	public boolean isIncluded() {return AlwaysInclude.equals(this);}
}
