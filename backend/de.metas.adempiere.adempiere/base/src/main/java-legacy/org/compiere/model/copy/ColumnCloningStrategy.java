package org.compiere.model.copy;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_AD_Column;

@AllArgsConstructor
public enum ColumnCloningStrategy implements ReferenceListAwareEnum
{
	Auto(X_AD_Column.CLONINGSTRATEGY_Auto),
	DirectCopy(X_AD_Column.CLONINGSTRATEGY_DirectCopy),
	UseDefaultValue(X_AD_Column.CLONINGSTRATEGY_UseDefaultValue),
	MakeUnique(X_AD_Column.CLONINGSTRATEGY_MakeUnique),
	Skip(X_AD_Column.CLONINGSTRATEGY_Skip),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<ColumnCloningStrategy> index = ReferenceListAwareEnums.index(values());

	@Getter private final String code;

	public static ColumnCloningStrategy ofCode(@NonNull final String code) {return index.ofCode(code);}
}
