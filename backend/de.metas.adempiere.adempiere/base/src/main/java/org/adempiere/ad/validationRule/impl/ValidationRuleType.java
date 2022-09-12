package org.adempiere.ad.validationRule.impl;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_AD_Val_Rule;

@AllArgsConstructor
public enum ValidationRuleType implements ReferenceListAwareEnum
{
	SQL(X_AD_Val_Rule.TYPE_SQL),
	JAVA(X_AD_Val_Rule.TYPE_Java),
	COMPOSITE(X_AD_Val_Rule.TYPE_Composite),
	;

	private static final ReferenceListAwareEnums.ValuesIndex<ValidationRuleType> index = ReferenceListAwareEnums.index(values());

	@Getter private final String code;

	public static ValidationRuleType ofCode(@NonNull final String code) {return index.ofCode(code);}
}
