package de.metas.document.engine.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.document.engine.DocActionOptionsContext;
import de.metas.document.engine.IDocActionOptionsCustomizer;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;

public class CompositeDocActionOptionsCustomizer implements IDocActionOptionsCustomizer
{
	public static IDocActionOptionsCustomizer ofList(@NonNull final List<IDocActionOptionsCustomizer> list)
	{
		if (list.isEmpty())
		{
			throw new AdempiereException("At least one customizer is required");
		}
		else if (list.size() == 1)
		{
			return list.get(0);
		}
		else
		{
			return new CompositeDocActionOptionsCustomizer(list);
		}
	}

	private final ImmutableList<IDocActionOptionsCustomizer> list;
	private final ImmutableSet<String> parameters;

	private CompositeDocActionOptionsCustomizer(@NonNull final List<IDocActionOptionsCustomizer> list)
	{
		Check.assumeNotEmpty(list, "list shall not be empty");

		this.list = ImmutableList.copyOf(list);

		this.parameters = list.stream()
				.flatMap(customizer -> customizer.getParameters().stream())
				.collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public String getAppliesToTableName() {
		return null; // ANY
	}

	@Override
	public void customizeValidActions(final DocActionOptionsContext optionsCtx)
	{
		list.forEach(customizer -> customizer.customizeValidActions(optionsCtx));
	}

	@Override
	public ImmutableSet<String> getParameters() {return parameters;}
}
