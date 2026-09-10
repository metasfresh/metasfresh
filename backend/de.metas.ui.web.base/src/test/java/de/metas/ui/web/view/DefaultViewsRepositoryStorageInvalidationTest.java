package de.metas.ui.web.view;

import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.websocket.sender.WebsocketSender;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * A configured view invalidation has to be able to surface a row that did not exist when the view was opened.
 * <p>
 * A grid view serves its rows out of a MATERIALIZED selection ({@link DefaultView} reads
 * {@code selectionsRef.getOrderedSelection(...)}), and {@link DefaultView#invalidateAll()} drops only the
 * per-row cache and the header - it leaves that selection in place. So invalidating "everything" still replays
 * the same row ids: a newly inserted row is not among them and can never appear, no matter how many times the
 * frontend re-fetches. Only {@link DefaultView#invalidateSelection()} calls {@code forgetCurrentSelections()},
 * which is what lets the selection be recomputed.
 */
class DefaultViewsRepositoryStorageInvalidationTest
{
	private DefaultViewsRepositoryStorage storage;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init(); // activates JUnit mode, without which registerJUnitBean refuses

		// the invalidation ends by collecting a "fully changed" event, which flushes to the websocket sender bean
		SpringContextHolder.registerJUnitBean(WebsocketSender.class, mock(WebsocketSender.class));

		storage = new DefaultViewsRepositoryStorage(Duration.ofMinutes(10));
	}

	@AfterEach
	void tearDown()
	{
		SpringContextHolder.instance.clearJUnitRegisteredBeans();
	}

	@Test
	@DisplayName("invalidating a view forgets its selection, so a row created after the view was opened can enter")
	void invalidateView_forgetsTheSelection()
	{
		final ViewId viewId = ViewId.random(WindowId.of(542190));
		final IView view = mock(IView.class);
		when(view.getViewId()).thenReturn(viewId);
		storage.put(view);

		storage.invalidateView(viewId);

		// the point of the test: dropping the row caches alone (invalidateAll) cannot surface a new row
		verify(view).invalidateSelection();
	}

	@Test
	@DisplayName("the IView.invalidateSelection() default degrades to invalidateAll() instead of throwing")
	void invalidateSelection_default_doesNotThrow()
	{
		// DefaultView overrides invalidateSelection(); the other IView implementations inherit the default, and
		// they are reached by the same invalidation path - so the default must be a usable fallback, not a throw.
		final IView view = mock(IView.class);
		doCallRealMethod().when(view).invalidateSelection();

		assertThatCode(view::invalidateSelection).doesNotThrowAnyException();

		verify(view).invalidateAll();
	}
}
