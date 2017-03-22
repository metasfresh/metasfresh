package org.adempiere.ui.notifications;

import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.text.MapFormat;
import org.compiere.util.Util;

import de.metas.adempiere.util.ADHyperlinkBuilder;
import de.metas.event.EventMessageFormatTemplate;

/**
 * Extension of {@link MapFormat} which produces HTML text.
 *
 * Mainly,
 * <ul>
 * <li>handles {@link ITableRecordReference} parameters and converts them to URLs
 * <li>escape all plain text chunks
 * </ul>
 *
 * @author tsa
 *
 */
@SuppressWarnings("serial")
final class EventHtmlMessageFormat extends EventMessageFormatTemplate
{
	public static final EventHtmlMessageFormat newInstance()
	{
		return new EventHtmlMessageFormat();
	}

	private EventHtmlMessageFormat()
	{
		super();
	}

	@Override
	protected String formatTableRecordReference(final ITableRecordReference recordRef)
	{
		return new ADHyperlinkBuilder().createShowWindowHTML(recordRef);
	}

	@Override
	protected String formatText(final String text)
	{
		if (text == null || text.isEmpty())
		{
			return "";
		}

		return Util.maskHTML(text);
	}
}
