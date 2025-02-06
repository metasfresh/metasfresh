package de.metas.ui.web.window.events;

import de.metas.common.util.time.SystemTime;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.WindowId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JSONDocumentChangedWebSocketEventCollectorTest
{
	private static final WindowId windowId1 = WindowId.of(1);
	// private static final WindowId windowId2 = WindowId.of(2);
	private static final DocumentId documentId1 = DocumentId.of(1);
	// private static final DocumentId documentId2 = DocumentId.of(2);

	@BeforeEach
	void beforeEach()
	{
		SystemTime.setFixedTimeSource("2024-03-01T12:13:14Z");
	}

	@Nested
	class mergeFrom_markActiveTabStaled
	{
		@Test
		void mergeToEmptyCollector()
		{
			final JSONDocumentChangedWebSocketEventCollector collector1 = JSONDocumentChangedWebSocketEventCollector.newInstance();
			assertThat(collector1.getEvents()).isEmpty();

			final JSONDocumentChangedWebSocketEventCollector collector2 = JSONDocumentChangedWebSocketEventCollector.newInstance();
			collector2.staleRootDocument(windowId1, documentId1, true);
			assertThat(collector2.getEvents()).containsExactly(JSONDocumentChangedWebSocketEvent.rootDocument(windowId1, documentId1).markRootDocumentAsStaled().markActiveTabStaled());

			collector1.mergeFrom(collector2);
			assertThat(collector1.getEvents()).containsExactly(JSONDocumentChangedWebSocketEvent.rootDocument(windowId1, documentId1).markRootDocumentAsStaled().markActiveTabStaled());
		}

		@Test
		void mergeToCollectorWithExistingEvent()
		{
			final JSONDocumentChangedWebSocketEventCollector collector1 = JSONDocumentChangedWebSocketEventCollector.newInstance();
			collector1.staleRootDocument(windowId1, documentId1, false);
			assertThat(collector1.getEvents()).containsExactly(JSONDocumentChangedWebSocketEvent.rootDocument(windowId1, documentId1).markRootDocumentAsStaled());

			final JSONDocumentChangedWebSocketEventCollector collector2 = JSONDocumentChangedWebSocketEventCollector.newInstance();
			collector2.staleRootDocument(windowId1, documentId1, true);
			assertThat(collector2.getEvents()).containsExactly(JSONDocumentChangedWebSocketEvent.rootDocument(windowId1, documentId1).markRootDocumentAsStaled().markActiveTabStaled());

			collector1.mergeFrom(collector2);
			assertThat(collector1.getEvents()).containsExactly(JSONDocumentChangedWebSocketEvent.rootDocument(windowId1, documentId1).markRootDocumentAsStaled().markActiveTabStaled());
		}
	}
}