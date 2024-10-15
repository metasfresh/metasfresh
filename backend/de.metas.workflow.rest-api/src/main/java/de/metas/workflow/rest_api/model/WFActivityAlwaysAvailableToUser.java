package de.metas.workflow.rest_api.model;

import lombok.NonNull;

import javax.annotation.Nullable;

public enum WFActivityAlwaysAvailableToUser
{
	YES, NO, DEFAULT;

	@NonNull
	public static WFActivityAlwaysAvailableToUser ofBoolean(@Nullable final Boolean value)
	{
		if (value == null)
		{
			return DEFAULT;
		}
		else
		{
			return value ? YES : NO;
		}
	}

	@Nullable
	public Boolean toBooleanObject()
	{
		if (this == YES)
		{
			return Boolean.TRUE;
		}
		else if (this == NO)
		{
			return Boolean.FALSE;
		}
		else // DEFAULT
		{
			return null;
		}
	}
}
