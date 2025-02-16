package de.metas.frontend_testing;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.StringUtils;
import de.metas.util.lang.RepoIdAware;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

/**
 * <code>data-testid</code> property to be set on HTML elements in order to make the frontend application testable
 */
@EqualsAndHashCode(doNotUseGetters = true)
public final class JsonTestId
{
	private final String value;

	private JsonTestId(@NonNull final String value)
	{
		this.value = StringUtils.trimBlankToNull(value);
		if (this.value == null)
		{
			throw new AdempiereException("value is null");
		}
	}

	@JsonCreator
	public static JsonTestId ofString(@NonNull final String value) {return new JsonTestId(value);}

	@JsonCreator
	public static JsonTestId ofRepoId(@NonNull final RepoIdAware id) {return ofString(String.valueOf(id.getRepoId()));}

	@Override
	@Deprecated
	public String toString() {return getAsString();}

	@JsonValue
	public String getAsString() {return value;}
}
