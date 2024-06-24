package org.compiere.model.copy;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class POValuesCopyStrategies
{
	public static POValuesCopyStrategy standard(boolean skipCalculatedColumns)
	{
		return new Standard(skipCalculatedColumns);
	}

	public static boolean isSkipStandardColumn(final String columnName, final boolean isCalculated)
	{
		return columnName.equals("Created")
				|| columnName.equals("CreatedBy")
				|| columnName.equals("Updated")
				|| columnName.equals("UpdatedBy")
				|| columnName.equals("IsActive")
				// fresh 07896: skip copying org and client ONLY if it's calculated
				|| (isCalculated && columnName.equals("AD_Client_ID"))
				|| (isCalculated && columnName.equals("AD_Org_ID"))
				|| columnName.equals("DocumentNo")
				|| columnName.equals("Processing")
				|| columnName.equals("Processed");
	}

	private static class Standard implements POValuesCopyStrategy
	{
		private final boolean skipCalculatedColumns;

		private Standard(final boolean skipCalculatedColumns)
		{
			this.skipCalculatedColumns = skipCalculatedColumns;
		}

		@Override
		public ValueToCopyResolved getValueToCopy(@NonNull final ValueToCopyResolveContext context)
		{
			final boolean isCalculated = context.isToColumnCalculated();
			if (skipCalculatedColumns && isCalculated)
			{
				return ValueToCopyResolved.SKIP;
			}
			else if (isSkipStandardColumn(context.getColumnName(), isCalculated))
			{
				return ValueToCopyResolved.SKIP;
			}
			else
			{
				return ValueToCopy.DIRECT_COPY.resolve(context);
			}
		}
	}
}
