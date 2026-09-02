package de.metas.document.archive.notification.delay;

import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import lombok.NonNull;

/**
 * Per-source-document-type decision whether a document notification email
 * must be held back (e.g. until shipment carrier tracking URLs are available).
 * Implementations are always registered; the actual on/off is each handler's own concern (e.g. a SysConfig).
 */
public interface DocOutboundNotificationDelayHandler
{
	/** TableName of {@code C_Doc_Outbound_Log.AD_Table_ID} this handler is responsible for. */
	String getTableName();

	/** @return true if sending the notification for this log must be delayed right now. */
	boolean shouldDelaySending(@NonNull I_C_Doc_Outbound_Log docOutboundLog);
}
