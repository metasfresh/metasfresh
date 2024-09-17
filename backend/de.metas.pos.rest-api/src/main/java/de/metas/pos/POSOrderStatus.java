package de.metas.pos;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum POSOrderStatus implements ReferenceListAwareEnum
{
	Drafted("DR"),
	WaitingPayment("WP"),
	Completed("CO"),
	Voided("VO");

	private static final ValuesIndex<POSOrderStatus> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	public static POSOrderStatus ofCode(@NonNull String code) {return index.ofCode(code);}

	public boolean isDrafted() {return this == Drafted;}

	public static boolean equals(@Nullable final POSOrderStatus status1, @Nullable final POSOrderStatus status2) {return Objects.equals(status1, status2);}
}
