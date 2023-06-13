package org.compiere.model.copy;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.function.Supplier;

@EqualsAndHashCode
@ToString
public class ValueToCopy
{
	public static ValueToCopy DIRECT_COPY = new ValueToCopy(ValueToCopyType.DIRECT_COPY, null);
	public static ValueToCopy SKIP = new ValueToCopy(ValueToCopyType.SKIP, null);
	public static ValueToCopy NOT_SPECIFIED = new ValueToCopy(ValueToCopyType.NOT_SPECIFIED, null);

	public static ValueToCopy explicitValueToSet(final Object value) {return new ValueToCopy(ValueToCopyType.SET_EXPLICIT_VALUE, value);}

	@Getter @NonNull ValueToCopyType type;
	@Getter @Nullable Object explicitValueToSet;

	private ValueToCopy(@NonNull final ValueToCopyType type, @Nullable final Object explicitValueToSet)
	{
		this.type = type;
		this.explicitValueToSet = explicitValueToSet;
	}

	public ValueToCopy ifNotSpecifiedThen(@NonNull final Supplier<ValueToCopy> then) {return isNotSpecified() ? then.get() : this;}

	public ValueToCopy ifNotSpecifiedThenDirectCopy() {return isNotSpecified() ? DIRECT_COPY : this;}

	public boolean isSpecified() {return !isNotSpecified();}

	public boolean isNotSpecified() {return ValueToCopyType.NOT_SPECIFIED.equals(type);}

	public ValueToCopy ifDirectCopyThenSetExplicitValue(@NonNull final Supplier<Object> explicitValueSupplier)
	{
		return ValueToCopyType.DIRECT_COPY.equals(type)
				? explicitValueToSet(explicitValueSupplier.get())
				: this;
	}
}
