package org.compiere.model.copy;

import lombok.experimental.UtilityClass;
import org.compiere.model.PO;

@UtilityClass
public class POValuesCopyStrategies
{
	public static final POValuesCopyStrategy SKIP_CALCULATED_COLUMNS = new SkipCalculatedColumns();
	public static final POValuesCopyStrategy SKIP_STANDARD_COLUMNS = new SkipStandardColumns();
	public static final POValuesCopyStrategy STANDARD = new Standard();

	private static class SkipCalculatedColumns implements POValuesCopyStrategy
	{
		@Override
		public ValueToCopy getValueToCopy(final PO to, final PO from, final String columnName)
		{
			final boolean isCalculated = to.getPOInfo().isCalculated(columnName);
			return isCalculated ? ValueToCopy.SKIP : ValueToCopy.NOT_SPECIFIED;
		}
	}

	private static class SkipStandardColumns implements POValuesCopyStrategy
	{
		@Override
		public ValueToCopy getValueToCopy(final PO to, final PO from, final String columnName)
		{
			final boolean isCalculated = to.getPOInfo().isCalculated(columnName);

			// Ignore Standard Values
			if (columnName.equals("Created")
					|| columnName.equals("CreatedBy")
					|| columnName.equals("Updated")
					|| columnName.equals("UpdatedBy")
					|| columnName.equals("IsActive")
					// fresh 07896: skip copying org and client ONLY if it's calculated
					|| (isCalculated && columnName.equals("AD_Client_ID"))
					|| (isCalculated && columnName.equals("AD_Org_ID"))
					|| columnName.equals("DocumentNo")
					|| columnName.equals("Processing")
					|| columnName.equals("Processed")
			)
			{
				return ValueToCopy.SKIP;
			}
			else
			{
				return ValueToCopy.NOT_SPECIFIED;
			}
		}
	}

	private static class Standard implements POValuesCopyStrategy
	{

		@Override
		public ValueToCopy getValueToCopy(final PO to, final PO from, final String columnName)
		{
			return ValueToCopy.NOT_SPECIFIED;
		}
	}
}
