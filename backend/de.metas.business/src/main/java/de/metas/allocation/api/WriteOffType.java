package de.metas.allocation.api;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_C_AllocationLine;

import javax.annotation.Nullable;

@RequiredArgsConstructor
@Getter
public enum WriteOffType implements ReferenceListAwareEnum
{
	WriteOff(X_C_AllocationLine.WRITEOFFTYPE_StandardWriteOff),
	BankFee(X_C_AllocationLine.WRITEOFFTYPE_BankFee);

	@NonNull private final String code;

	public static WriteOffType ofCode(@NonNull final String code) {return index.ofCode(code);}

	@Nullable
	public static WriteOffType ofNullableCode(@Nullable final String code) {return index.ofNullableCode(code);}

	@NonNull
	public static WriteOffType ofNullableCodeOrWriteOff(@Nullable final String code)
	{
		final WriteOffType result = ofNullableCode(code);
		return result != null ? result : WriteOff;
	}

	public boolean isBankFee() {return this == BankFee;}

	public boolean isWriteOff() {return this == WriteOff;}

	private static final ValuesIndex<WriteOffType> index = ReferenceListAwareEnums.index(values());
}
