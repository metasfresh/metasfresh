package de.metas.material.planning.pporder;

import de.metas.util.Check;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_AD_WF_Node;

import javax.annotation.Nullable;
import java.util.Objects;

@AllArgsConstructor
public enum PPAlwaysAvailableToUser implements ReferenceListAwareEnum
{
	YES(X_AD_WF_Node.PP_ALWAYSAVAILABLETOUSER_Yes),
	NO(X_AD_WF_Node.PP_ALWAYSAVAILABLETOUSER_No),
	DEFAULT(null);

	@Getter @Nullable private final String code;

	@NonNull
	public static PPAlwaysAvailableToUser ofCode(@Nullable final String code)
	{
		return ofNullableCode(code);
	}

	@NonNull
	public static PPAlwaysAvailableToUser ofNullableCode(@Nullable final String code)
	{
		if (Check.isBlank(code))
		{
			return DEFAULT;
		}
		else if (Objects.equals(YES.code, code))
		{
			return YES;
		}
		else if (Objects.equals(NO.code, code))
		{
			return NO;
		}
		else
		{
			throw new AdempiereException("Unknown code: " + code);
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
