package de.metas.ui.web.document.filter.sql;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.document.references.zoom_into.CustomizedWindowInfo;
import de.metas.document.references.zoom_into.CustomizedWindowInfoMap;
import de.metas.document.references.zoom_into.SqlCustomizedWindowInfoMapRepository;
import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class SqlDocumentFilterConverterDecoratorsProvider
{
	@NonNull private static final Logger logger = LogManager.getLogger(SqlDocumentFilterConverterDecoratorsProvider.class);
	@NonNull private final SqlCustomizedWindowInfoMapRepository sqlCustomizedWindowInfoMapRepository;
	@NonNull private final ImmutableMap<WindowId, SqlDocumentFilterConverterDecorator> filterConvertorDecorators;

	public SqlDocumentFilterConverterDecoratorsProvider(
			@NonNull final Optional<List<SqlDocumentFilterConverterDecorator>> filterConverterDecorators,
			@NonNull final SqlCustomizedWindowInfoMapRepository sqlCustomizedWindowInfoMapRepository)
	{
		this.sqlCustomizedWindowInfoMapRepository = sqlCustomizedWindowInfoMapRepository;

		this.filterConvertorDecorators = makeDecoratorsMapAndHandleDuplicates(filterConverterDecorators.orElseGet(ImmutableList::of));
		logger.info("Filter converter decorators: {}", filterConvertorDecorators);

	}

	private static ImmutableMap<WindowId, SqlDocumentFilterConverterDecorator> makeDecoratorsMapAndHandleDuplicates(
			@NonNull final Collection<SqlDocumentFilterConverterDecorator> providers)
	{
		try
		{
			return Maps.uniqueIndex(providers, SqlDocumentFilterConverterDecorator::getWindowId);
		}
		catch (final IllegalArgumentException e)
		{
			final String message = "The given collection of SqlDocumentFilterConverterDecoratorProvider implementors contains more than one element with the same window-id";
			throw new AdempiereException(message, e)
					.setParameter("sqlDocumentFilterConverterDecoratorProviders", providers)
					.appendParametersToMessage();
		}
	}

	public Optional<SqlDocumentFilterConverterDecorator> getByWindowId(@NonNull final WindowId windowId)
	{
		if (filterConvertorDecorators.containsKey(windowId))
		{
			return Optional.of(filterConvertorDecorators.get(windowId));
		}

		final AdWindowId adWindowId = windowId.toAdWindowIdOrNull();
		if (adWindowId == null) {return Optional.empty();}

		final CustomizedWindowInfoMap customizedWindowInfoMap = sqlCustomizedWindowInfoMapRepository.get();
		final CustomizedWindowInfo customizedWindowInfo = customizedWindowInfoMap.getCustomizedWindowInfo(adWindowId).orElse(null);
		if (customizedWindowInfo == null) {return Optional.empty();}

		final AdWindowId baseAdWindowId = customizedWindowInfo.getBaseWindowId();
		return Optional.ofNullable(filterConvertorDecorators.get(WindowId.of(baseAdWindowId)));
	}

}
