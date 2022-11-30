package de.metas.product;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_M_Product;

import javax.annotation.Nullable;

@AllArgsConstructor
public enum IssuingToleranceValueType implements ReferenceListAwareEnum
{
	PERCENTAGE(X_M_Product.ISSUINGTOLERANCE_VALUETYPE_Percentage),
	QUANTITY(X_M_Product.ISSUINGTOLERANCE_VALUETYPE_Quantity),
	;

	@Getter
	private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<IssuingToleranceValueType> index = ReferenceListAwareEnums.index(values());

	public static IssuingToleranceValueType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static IssuingToleranceValueType ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}

	public static String toCode(@Nullable final IssuingToleranceValueType type) {return type != null ? type.getCode() : null;}
}
