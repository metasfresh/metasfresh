package de.metas.ui.web.window.datatypes.json;

import de.metas.acct.gljournal_sap.PostingSign;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class JSONDocumentChangedEventTest
{
	@Nested
	class getValueAsEnum
	{
		@Test
		void nullValue()
		{
			final JSONDocumentChangedEvent event = JSONDocumentChangedEvent.replace("field", null);
			assertThat(event.getValueAsEnum(PostingSign.class)).isNull();
		}

		@Test
		void string()
		{
			final JSONDocumentChangedEvent event = JSONDocumentChangedEvent.replace("field", PostingSign.DEBIT.getCode());
			assertThat(event.getValueAsEnum(PostingSign.class)).isEqualTo(PostingSign.DEBIT);
		}

		@Test
		void enumType()
		{
			final JSONDocumentChangedEvent event = JSONDocumentChangedEvent.replace("field", PostingSign.DEBIT);
			assertThat(event.getValueAsEnum(PostingSign.class)).isEqualTo(PostingSign.DEBIT);
		}

		@Test
		void mapValue()
		{
			final HashMap<String, Object> value = new HashMap<>();
			value.put("key", PostingSign.DEBIT.getCode());
			final JSONDocumentChangedEvent event = JSONDocumentChangedEvent.replace("field", value);
			assertThat(event.getValueAsEnum(PostingSign.class)).isEqualTo(PostingSign.DEBIT);
		}

		@Test
		void jsonLookupValue()
		{
			final JSONDocumentChangedEvent event = JSONDocumentChangedEvent.replace("field", JSONLookupValue.of(PostingSign.DEBIT.getCode(), "bla bla"));
			assertThat(event.getValueAsEnum(PostingSign.class)).isEqualTo(PostingSign.DEBIT);
		}

	}
}