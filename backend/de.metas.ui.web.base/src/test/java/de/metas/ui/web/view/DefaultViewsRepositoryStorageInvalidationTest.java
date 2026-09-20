package de.metas.ui.web.view;

import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.websocket.sender.WebsocketSender;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Duration;

/**
 * Pins the deliberate split between refreshing a view's row VALUES and recomputing WHICH rows it holds.
 * <p>
 * An ambient invalidation - one the cache machinery raises because somebody, anybody, wrote to a trigger
 * table - must only refresh values. Growing an open view's selection from such an event would silently add
 * rows the user never asked for, and a process the user then runs over "all rows" of that view would act on
 * them. Membership is recomputed only where the user's own action changed it: every
 * {@link IView#invalidateSelection()} caller in the codebase is the {@code postProcess} of a process the user
 * just ran on that very view. Re-materializing a selection is also far more expensive than dropping row
 * caches.
 * <p>
 * These tests exist because this was "fixed" the wrong way once: a window whose grid did not show a planning
 * created elsewhere looked like a cache bug, and making the ambient path forget the selection made the row
 * appear - by changing shared behaviour for every grid view in the product. Not showing it is the intended
 * behaviour; the requirement is only that a CHANGE to a row already in the view shows without a reload.
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
	@DisplayName("an ambient invalidation refreshes the rows' values and leaves the view's membership alone")
	void invalidateView_refreshesValues_butKeepsTheSelection()
	{
		final ViewId viewId = ViewId.random(WindowId.of(542190));
		final IView view = mock(IView.class);
		when(view.getViewId()).thenReturn(viewId);
		storage.put(view);

		storage.invalidateView(viewId);

		verify(view).invalidateAll();
		verify(view, never()).invalidateSelection();
	}

	@Test
	@DisplayName("asking a view type that has no selection to recompute its membership fails loudly")
	void invalidateSelection_default_throwsRatherThanSilentlyDoingLess()
	{
		// Only DefaultView overrides invalidateSelection(), because only it has a selection to forget. Any other
		// implementation must reject the request instead of quietly downgrading it to invalidateAll(), which
		// would leave the caller believing membership had been recomputed when it had not.
		final IView view = mock(IView.class);
		doCallRealMethod().when(view).invalidateSelection();

		assertThatThrownBy(view::invalidateSelection).isInstanceOf(UnsupportedOperationException.class);

		verify(view, never()).invalidateAll();
	}
}
