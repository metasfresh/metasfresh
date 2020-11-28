package org.adempiere.ad.validationRule;

public final class NullValidationContext implements IValidationContext
{
	public static final NullValidationContext instance = new NullValidationContext();

	/* package */NullValidationContext()
	{
		super();
	}

	@Override
	public String getTableName()
	{
		return null;
	}

	@Override
	public String get_ValueAsString(String variableName)
	{
		return null;
	}
}
