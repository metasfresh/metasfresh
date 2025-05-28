package de.metas.frontend_testing.expectations.assertions;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Trace;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("UnusedReturnValue")
@Data
public class Failure
{
	@NonNull private final String message;
	@NonNull private final String stacktrace;
	@NonNull private final HashMap<String, Object> context;

	@NonNull private final ArrayList<Failure> causes;

	private static final String EXCEPTION_PARAM_FAILURE = "failure";

	@Builder
	@Jacksonized
	private Failure(
			@NonNull final String message,
			@Nullable final String stacktrace,
			@Nullable final Map<String, Object> context,
			@Nullable final List<Failure> causes)
	{
		this.message = message;
		this.stacktrace = stacktrace != null ? stacktrace : Trace.toOneLineStackTraceString();
		this.context = context != null && !context.isEmpty() ? new HashMap<>(context) : new HashMap<>();
		this.causes = causes != null && !causes.isEmpty() ? new ArrayList<>(causes) : new ArrayList<>();
	}

	public static Failure ofException(@NonNull final Exception ex)
	{
		final Map<String, Object> exceptionParams = AdempiereException.extractParameters(ex);
		final Object failure = exceptionParams.get(EXCEPTION_PARAM_FAILURE);
		if (failure instanceof Failure)
		{
			return (Failure)failure;
		}
		else
		{
			return builder()
					.message(ex.getLocalizedMessage())
					.stacktrace(Trace.toOneLineStackTraceString(ex))
					.context(exceptionParams)
					.build();
		}
	}

	public Failure putContext(@NonNull final String key, @Nullable final Object value)
	{
		this.context.put(key, value);
		return this;
	}

	public AdempiereException toException()
	{
		return new AdempiereException(message)
				.appendParametersToMessage()
				.setParameter("stacktrace", stacktrace)
				.setParameters(context)
				.setParameter(EXCEPTION_PARAM_FAILURE, this);
	}
}
