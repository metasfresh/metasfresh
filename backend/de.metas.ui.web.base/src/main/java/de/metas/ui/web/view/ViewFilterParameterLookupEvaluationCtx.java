package de.metas.ui.web.view;

import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;
import java.util.function.UnaryOperator;

@Value
@Builder(toBuilder = true)
public class ViewFilterParameterLookupEvaluationCtx
{
	@NonNull ViewId viewId;
	@Builder.Default long viewSize = -1;
	@NonNull Evaluatee userSessionCtx;
	@Nullable Map<String, Object> parameterValues;

	public Evaluatee toEvaluatee()
	{
		Evaluatee ctx = Evaluatees.mapBuilder()
				.put(LookupDataSourceContext.PARAM_ViewId, viewId)
				.put(LookupDataSourceContext.PARAM_ViewSize, viewSize)
				.build()
				.andComposeWith(userSessionCtx);

		if (parameterValues != null && !parameterValues.isEmpty())
		{
			ctx = ctx.andComposeWith(Evaluatees.ofMap(parameterValues));
		}

		return ctx;
	}

	public ViewFilterParameterLookupEvaluationCtx mapParameterValues(@NonNull final UnaryOperator<Map<String, Object>> mapper)
	{
		final Map<String, Object> parameterValuesNew = mapper.apply(parameterValues);
		return !Objects.equals(parameterValues, parameterValuesNew)
				? toBuilder().parameterValues(parameterValuesNew).build()
				: this;
	}
}
