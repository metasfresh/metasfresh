package org.compiere.model.copy;

import lombok.NonNull;

@FunctionalInterface
public interface POValuesCopyStrategy
{
	ValueToCopyResolved getValueToCopy(@NonNull final ValueToCopyResolveContext context);
}
