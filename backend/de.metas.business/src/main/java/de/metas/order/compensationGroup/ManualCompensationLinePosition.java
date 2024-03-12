package de.metas.order.compensationGroup;

import de.metas.common.util.CoalesceUtil;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_C_OrderLine;

import javax.annotation.Nullable;

@RequiredArgsConstructor
@Getter
public enum ManualCompensationLinePosition implements ReferenceListAwareEnum
{
	BEFORE_GENERATED_COMPENSATION_LINES(X_C_OrderLine.MANUALCOMPENSATIONLINEPOSITION_BeforeGeneratedCompensationLines),
	LAST(X_C_OrderLine.MANUALCOMPENSATIONLINEPOSITION_Last),
	;
	public static final ManualCompensationLinePosition DEFAULT = LAST;

	private static final ReferenceListAwareEnums.ValuesIndex<ManualCompensationLinePosition> index = ReferenceListAwareEnums.index(values());

	private final String code;

	public static ManualCompensationLinePosition ofCode(@NonNull final String code) {return index.ofCode(code);}

	public static ManualCompensationLinePosition ofNullableCode(@Nullable final String code) {return index.ofNullableCode(code);}

	public static ManualCompensationLinePosition ofNullableCodeOrDefault(@Nullable final String code) {return CoalesceUtil.coalesceNotNull(ofNullableCode(code), DEFAULT);}

	public static String toCode(@Nullable final ManualCompensationLinePosition type) {return type != null ? type.getCode() : null;}
}
