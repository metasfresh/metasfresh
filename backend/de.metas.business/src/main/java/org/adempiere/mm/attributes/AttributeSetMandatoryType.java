package org.adempiere.mm.attributes;

import de.metas.lang.SOTrx;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_M_AttributeSet;

@Getter
@RequiredArgsConstructor
public enum AttributeSetMandatoryType implements ReferenceListAwareEnum
{
	NotMandatory(X_M_AttributeSet.MANDATORYTYPE_NotMandatary),
	AlwaysMandatory(X_M_AttributeSet.MANDATORYTYPE_AlwaysMandatory),
	WhenShipping(X_M_AttributeSet.MANDATORYTYPE_WhenShipping),
	;

	@NonNull private static final ValuesIndex<AttributeSetMandatoryType> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	public static AttributeSetMandatoryType ofCode(@NonNull final String code) {return index.ofCode(code);}

	public boolean isASIMandatory(@NonNull final SOTrx soTrx)
	{
		// Outgoing transaction
		if (soTrx.isSales())
		{
			return !NotMandatory.equals(this);
		}
		// Incoming transaction
		else
		{
			// isSOTrx == false
			return AlwaysMandatory.equals(this);
		}

	}

}
