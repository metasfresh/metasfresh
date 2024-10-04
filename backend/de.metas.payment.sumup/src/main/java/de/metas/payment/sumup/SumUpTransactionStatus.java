package de.metas.payment.sumup;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@EqualsAndHashCode
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class SumUpTransactionStatus
{
	@NonNull private static final ConcurrentHashMap<String, SumUpTransactionStatus> intern = new ConcurrentHashMap<>();

	@NonNull private final String code;

	private SumUpTransactionStatus(@NonNull final String code)
	{
		final String codeNorm = StringUtils.trimBlankToNull(code);
		if (codeNorm == null)
		{
			throw new AdempiereException("Invalid status: " + code);
		}
		this.code = codeNorm;
	}

	@JsonCreator
	@NonNull
	public static SumUpTransactionStatus ofString(@NonNull final String code)
	{
		final String codeNorm = StringUtils.trimBlankToNull(code);
		if (codeNorm == null)
		{
			throw new AdempiereException("Invalid status: " + code);
		}

		return intern.computeIfAbsent(codeNorm, SumUpTransactionStatus::new);
	}

	@Override
	@Deprecated
	public String toString() {return getAsString();}

	@JsonValue
	public String getAsString() {return code;}

	public static boolean equals(@Nullable final SumUpTransactionStatus status1, @Nullable final SumUpTransactionStatus status2) {return Objects.equals(status1, status2);}
}
