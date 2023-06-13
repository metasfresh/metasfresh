package org.compiere.model.copy;

import org.compiere.model.PO;

@FunctionalInterface
public interface POValuesCopyStrategy
{
	ValueToCopy getValueToCopy(final PO to, final PO from, final String columnName);
}
