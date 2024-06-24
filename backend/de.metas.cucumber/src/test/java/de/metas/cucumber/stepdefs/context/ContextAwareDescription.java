package de.metas.cucumber.stepdefs.context;

import de.metas.util.StringUtils;
import lombok.NonNull;
import org.assertj.core.description.Description;
import org.slf4j.MDC;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ContextAwareDescription extends Description
{

	private final String message;
	private HashMap<String, Object> localContext;

	public static ContextAwareDescription newInstance() {return new ContextAwareDescription(null);}

	public static ContextAwareDescription ofString(String message) {return new ContextAwareDescription(message);}

	private ContextAwareDescription(final String message) {this(message, null);}

	private ContextAwareDescription(@Nullable final String message, @Nullable final Map<String, Object> localContext)
	{
		this.message = StringUtils.trimBlankToNull(message);
		this.localContext = localContext != null && !localContext.isEmpty() ? new HashMap<>(localContext) : null;
	}

	public void put(@NonNull String key, @Nullable Object value)
	{
		HashMap<String, Object> localContext = this.localContext;
		if (localContext == null)
		{
			localContext = this.localContext = new HashMap<>();
		}
		localContext.put(key, value);
	}

	@Override
	public String value()
	{
		final StringBuilder result = new StringBuilder();
		if (message != null)
		{
			result.append(message);
		}

		appendMap(result, localContext);
		appendMap(result, SharedTestContext.getCopyOfContextMap());
		appendMap(result, MDC.getCopyOfContextMap());

		return result.toString();
	}

	static void appendMap(@NonNull final StringBuilder result, @Nullable final Map<String, ?> context)
	{
		if (context == null || context.isEmpty()) {return;}

		context.forEach((key, value) -> {
			if (result.length() > 0)
			{
				result.append("\n\t");
			}
			result.append(key).append(": ").append(value);
		});
	}

	public ContextAwareDescription newWithMessage(@NonNull final String message)
	{
		if (!Objects.equals(this.message, message))
		{
			return new ContextAwareDescription(message, this.localContext);
		}
		else
		{
			return this;
		}
	}
}
