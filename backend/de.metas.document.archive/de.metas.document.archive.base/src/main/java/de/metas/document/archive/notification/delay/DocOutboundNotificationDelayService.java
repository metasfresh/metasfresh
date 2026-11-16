package de.metas.document.archive.notification.delay;

import com.google.common.collect.ImmutableMap;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocOutboundNotificationDelayService
{
	private final ImmutableMap<String, DocOutboundNotificationDelayHandler> tableName2handler;

	public DocOutboundNotificationDelayService(@NonNull final Optional<List<DocOutboundNotificationDelayHandler>> handlers)
	{
		final ImmutableMap.Builder<String, DocOutboundNotificationDelayHandler> builder = ImmutableMap.builder();
		handlers.ifPresent(list -> list.forEach(h -> builder.put(h.getTableName(), h)));
		this.tableName2handler = builder.build();
	}

	public boolean shouldDelaySending(@NonNull final I_C_Doc_Outbound_Log docOutboundLog)
	{
		if (docOutboundLog.getRecord_ID() <= 0)
		{
			return false;
		}
		final TableRecordReference recordRef = TableRecordReference.ofOrNull(
				docOutboundLog.getAD_Table_ID(), docOutboundLog.getRecord_ID());
		if (recordRef == null)
		{
			return false;
		}
		final DocOutboundNotificationDelayHandler handler = tableName2handler.get(recordRef.getTableName());
		return handler != null && handler.shouldDelaySending(docOutboundLog);
	}
}
