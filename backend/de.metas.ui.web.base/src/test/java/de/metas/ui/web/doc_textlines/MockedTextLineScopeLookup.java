package de.metas.ui.web.doc_textlines;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CCacheStats;
import de.metas.doctextline.TextLineScope;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.zoom_into.DocumentZoomIntoInfo;
import lombok.NonNull;
import lombok.ToString;
import org.compiere.util.Evaluatee;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Stands in for the {@code TextLineScope} reference-list lookup that {@link DocTextLinesViewFactory} builds
 * from {@code LookupDataSourceFactory} in production. A plain unit test has no database and therefore no
 * {@code AD_Ref_List} rows to read, so the values are served from the {@link TextLineScope} enum instead --
 * which is the same set the reference list holds, keyed by the same codes.
 * <p>
 * Modelled on {@code de.metas.ui.web.shipment_candidates_editor.MockedLookupDataSource}: only the methods the
 * code under test actually calls are implemented; the rest refuse rather than return something made up.
 */
@ToString
final class MockedTextLineScopeLookup implements LookupDataSource
{
	public static MockedTextLineScopeLookup instance()
	{
		return new MockedTextLineScopeLookup();
	}

	private MockedTextLineScopeLookup()
	{
	}

	private static StringLookupValue toLookupValue(@NonNull final TextLineScope scope)
	{
		return StringLookupValue.of(scope.getCode(), scope.name());
	}

	@Override
	public LookupValuesPage findEntities(final Evaluatee ctx, final int pageLength)
	{
		return LookupValuesPage.allValues(Stream.of(TextLineScope.values())
				.limit(pageLength)
				.map(MockedTextLineScopeLookup::toLookupValue)
				.collect(LookupValuesList.collect()));
	}

	@Override
	public LookupValuesPage findEntities(final Evaluatee ctx, final String filter, final int firstRow, final int pageLength)
	{
		throw new UnsupportedOperationException();
	}

	@Nullable
	@Override
	public LookupValue findById(@Nullable final Object id)
	{
		if (id == null)
		{
			return null;
		}

		return Stream.of(TextLineScope.values())
				.filter(scope -> scope.getCode().equals(id.toString()))
				.findFirst()
				.map(MockedTextLineScopeLookup::toLookupValue)
				.orElse(null);
	}

	@NonNull
	@Override
	public LookupValuesList findByIdsOrdered(@NonNull final Collection<?> ids)
	{
		return ids.stream()
				.map(this::findById)
				.filter(Objects::nonNull)
				.collect(LookupValuesList.collect());
	}

	@Override
	public List<CCacheStats> getCacheStats()
	{
		return ImmutableList.of();
	}

	@Override
	public DocumentZoomIntoInfo getDocumentZoomInto(final int id)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Optional<WindowId> getZoomIntoWindowId()
	{
		return Optional.empty();
	}

	@Override
	public void cacheInvalidate()
	{
		// nothing
	}
}
