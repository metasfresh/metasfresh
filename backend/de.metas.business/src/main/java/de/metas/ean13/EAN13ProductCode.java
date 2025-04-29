package de.metas.ean13;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;

@EqualsAndHashCode(doNotUseGetters = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class EAN13ProductCode
{
	private final String code;

	private EAN13ProductCode(@NonNull final String code)
	{
		this.code = code;
	}

	@NonNull
	public static EAN13ProductCode ofString(@NonNull final String stringCode)
	{
		final EAN13ProductCode code = ofNullableString(stringCode);
		if (code == null)
		{
			throw new AdempiereException("Invalid product code: " + stringCode);
		}
		return code;
	}

	@JsonCreator
	@Nullable
	public static EAN13ProductCode ofNullableString(@Nullable final String stringCode)
	{
		final String codeNorm = StringUtils.trimBlankToNull(stringCode);
		return codeNorm == null ? null : new EAN13ProductCode(codeNorm);
	}

	@Override
	@Deprecated
	public String toString() {return getAsString();}

	@JsonValue
	public String getAsString() {return code;}

	public boolean isPrefixOf(@NonNull final String string) {return string.startsWith(code);}

	public static boolean equals(@Nullable EAN13ProductCode code1, @Nullable EAN13ProductCode code2) {return Objects.equals(code1, code2);}
}
