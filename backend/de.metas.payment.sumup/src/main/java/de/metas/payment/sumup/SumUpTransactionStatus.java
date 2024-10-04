package de.metas.payment.sumup;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@EqualsAndHashCode
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class SumUpTransactionStatus
{
	public static final SumUpTransactionStatus SUCCESSFUL = new SumUpTransactionStatus("SUCCESSFUL");
	public static final SumUpTransactionStatus CANCELLED = new SumUpTransactionStatus("CANCELLED");
	public static final SumUpTransactionStatus FAILED = new SumUpTransactionStatus("FAILED");
	public static final SumUpTransactionStatus PENDING = new SumUpTransactionStatus("PENDING");

	@NonNull private static final ConcurrentHashMap<String, SumUpTransactionStatus> intern = new ConcurrentHashMap<>();

	static
	{
		Arrays.asList(SUCCESSFUL, CANCELLED, FAILED, PENDING)
				.forEach(status -> intern.put(status.getCode(), status));
	}

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
	public String toString() {return getCode();}

	@JsonValue
	@NonNull
	public String getCode() {return code;}

	public static boolean equals(@Nullable final SumUpTransactionStatus status1, @Nullable final SumUpTransactionStatus status2) {return Objects.equals(status1, status2);}
}
