package de.metas.ui.web.window.events;

import de.metas.common.util.time.SystemTime;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.util.Services;
import de.metas.websocket.WebsocketEndpointAware;
import de.metas.websocket.sender.WebsocketSender;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DocumentWebsocketPublisherTest
{
	private static final WindowId windowId1 = WindowId.of(1);
	private static final DocumentId documentId1 = DocumentId.of(1);

	private ITrxManager trxManager;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		trxManager = Services.get(ITrxManager.class);

		SystemTime.setFixedTimeSource("2024-03-01T12:13:14Z");
	}

	private static WebsocketSender mockWebSocketSender(@NonNull final ArrayList<JSONDocumentChangedWebSocketEvent> sentEvents)
	{
		final @NonNull WebsocketSender websocketSender = Mockito.mock(WebsocketSender.class);
		Mockito.doAnswer((args) -> {
					final Collection<? extends WebsocketEndpointAware> events = args.getArgument(0);
					events.forEach(event -> {
						System.out.println("Got event: " + event);
						sentEvents.add((JSONDocumentChangedWebSocketEvent)event);
					});

					return null;
				})
				.when(websocketSender)
				.convertAndSend(Mockito.anyCollection());

		return websocketSender;
	}

	private static DocumentWebsocketPublisher newDocumentWebsocketPublisher(@NonNull final ArrayList<JSONDocumentChangedWebSocketEvent> sentEvents)
	{
		final @NonNull WebsocketSender websocketSender = mockWebSocketSender(sentEvents);
		return new DocumentWebsocketPublisher(websocketSender);
	}

	@Test
	void noThreadLocals_noTrx()
	{
		final ArrayList<JSONDocumentChangedWebSocketEvent> sentEvents = new ArrayList<>();
		final DocumentWebsocketPublisher documentWebsocketPublisher = newDocumentWebsocketPublisher(sentEvents);

		documentWebsocketPublisher.staleRootDocument(windowId1, documentId1);
		assertThat(sentEvents).containsExactly(JSONDocumentChangedWebSocketEvent.rootDocument(windowId1, documentId1).markRootDocumentAsStaled());
	}

	@Test
	void withThreadLocal()
	{
		final ArrayList<JSONDocumentChangedWebSocketEvent> sentEvents = new ArrayList<>();
		final DocumentWebsocketPublisher documentWebsocketPublisher = newDocumentWebsocketPublisher(sentEvents);

		try (IAutoCloseable ignored = documentWebsocketPublisher.temporaryCollectOnThisThread())
		{
			documentWebsocketPublisher.staleRootDocument(windowId1, documentId1);
			assertThat(sentEvents).isEmpty();
		}
		assertThat(sentEvents).containsExactly(JSONDocumentChangedWebSocketEvent.rootDocument(windowId1, documentId1).markRootDocumentAsStaled());
	}

	@Test
	void withTwoThreadLocals()
	{
		final ArrayList<JSONDocumentChangedWebSocketEvent> sentEvents = new ArrayList<>();
		final DocumentWebsocketPublisher documentWebsocketPublisher = newDocumentWebsocketPublisher(sentEvents);

		try (DocumentWebsocketPublisher.CloseableCollector ignored = documentWebsocketPublisher.temporaryCollectOnThisThread())
		{
			assertThatThrownBy(documentWebsocketPublisher::temporaryCollectOnThisThread)
					.hasMessageStartingWith("A thread level collector was already set");
		}
	}

	@Test
	void beResilientOnDoubleClose()
	{
		final ArrayList<JSONDocumentChangedWebSocketEvent> sentEvents = new ArrayList<>();
		final DocumentWebsocketPublisher documentWebsocketPublisher = newDocumentWebsocketPublisher(sentEvents);

		try (IAutoCloseable closeable = documentWebsocketPublisher.temporaryCollectOnThisThread())
		{
			documentWebsocketPublisher.staleRootDocument(windowId1, documentId1);
			assertThat(sentEvents).isEmpty();

			closeable.close();
			assertThat(sentEvents).containsExactly(JSONDocumentChangedWebSocketEvent.rootDocument(windowId1, documentId1).markRootDocumentAsStaled());
		}

		assertThat(sentEvents).containsExactly(JSONDocumentChangedWebSocketEvent.rootDocument(windowId1, documentId1).markRootDocumentAsStaled());
	}

	@Test
	void withTrx()
	{
		final ArrayList<JSONDocumentChangedWebSocketEvent> sentEvents = new ArrayList<>();
		final DocumentWebsocketPublisher documentWebsocketPublisher = newDocumentWebsocketPublisher(sentEvents);

		final JSONDocumentChangedWebSocketEvent expectedEvent = JSONDocumentChangedWebSocketEvent.rootDocument(windowId1, documentId1).markRootDocumentAsStaled();

		trxManager.runInThreadInheritedTrx(() -> {
			documentWebsocketPublisher.staleRootDocument(windowId1, documentId1);

			assertThat(sentEvents).isEmpty();
		});

		assertThat(sentEvents).containsExactly(expectedEvent);
	}

	@Test
	void withThreadLocal_withTrx()
	{
		final ArrayList<JSONDocumentChangedWebSocketEvent> sentEvents = new ArrayList<>();
		final DocumentWebsocketPublisher documentWebsocketPublisher = newDocumentWebsocketPublisher(sentEvents);

		final JSONDocumentChangedWebSocketEvent expectedEvent = JSONDocumentChangedWebSocketEvent.rootDocument(windowId1, documentId1).markRootDocumentAsStaled();

		try (DocumentWebsocketPublisher.CloseableCollector collector1 = documentWebsocketPublisher.temporaryCollectOnThisThread())
		{
			assertThat(collector1.getEvents()).isEmpty();

			trxManager.runInThreadInheritedTrx(() -> {
				documentWebsocketPublisher.staleRootDocument(windowId1, documentId1);

				assertThat(sentEvents).isEmpty();
				// NOTE: atm if there is a thread local collector that is always used first
				// In future we might improve this logic but atm does not make sense to invest time in something not used
				assertThat(collector1.getEvents()).containsExactly(expectedEvent);
			});

			assertThat(sentEvents).isEmpty();
			assertThat(collector1.getEvents()).containsExactly(expectedEvent);

		}

		assertThat(sentEvents).containsExactly(expectedEvent);
	}

}