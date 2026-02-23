package de.metas.allocation.api;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

public enum WriteOffType implements ReferenceListAwareEnum
{
	WriteOff("WO"),
	BankFee("BF");

	@Getter
	private final String code;

	WriteOffType(@NonNull final String code) {this.code = code;}

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
