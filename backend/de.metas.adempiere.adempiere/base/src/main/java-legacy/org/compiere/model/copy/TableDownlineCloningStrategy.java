package org.compiere.model.copy;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_AD_Table;

@AllArgsConstructor
public enum TableDownlineCloningStrategy implements ReferenceListAwareEnum
{
	Auto(X_AD_Table.DOWNLINECLONINGSTRATEGY_Auto),
	OnlyIncluded(X_AD_Table.DOWNLINECLONINGSTRATEGY_OnlyIncluded),
	Skip(X_AD_Table.DOWNLINECLONINGSTRATEGY_Skip),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<TableDownlineCloningStrategy> index = ReferenceListAwareEnums.index(values());

	@Getter private final String code;

	public static TableDownlineCloningStrategy ofCode(@NonNull final String code) {return index.ofCode(code);}

	public boolean isSkip() {return Skip.equals(this);}
}
