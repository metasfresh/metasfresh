package org.compiere.model.copy;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.annotation.Nullable;

@EqualsAndHashCode
@ToString
public class ValueToCopyResolved
{
	public static final ValueToCopyResolved SKIP = new ValueToCopyResolved(true, null);

	public static ValueToCopyResolved value(@Nullable final Object value) {return new ValueToCopyResolved(false, value);}

	@Getter private final boolean skip;
	@Getter @Nullable private final Object value;

	private ValueToCopyResolved(final boolean skip, @Nullable final Object value)
	{
		this.value = value;
		this.skip = skip;
	}
}
