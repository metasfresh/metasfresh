package de.metas.order.voidorderandrelateddocs;

import de.metas.ad_reference.ADReferenceService;
import de.metas.adempiere.model.I_C_Order;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.util.RelatedRecordsProvider;
import de.metas.util.RelatedRecordsProvider.SourceRecordsKey;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.compiere.util.Env;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Implementors are invoked when an order shall be voided.
 * Implementors can assume that the "most downstream" implementations are invoked first.
 * E.g. the implementation that deals with the order's invoice is invoked prior to the implementation that deals with the order's subscriptions.
 */
public interface VoidOrderAndRelatedDocsHandler
{
	AdMessageKey Msg_OrderDocumentCancelNotAllowed_4P = AdMessageKey.of("de.metas.order.Msg_OrderDocumentCancelNotAllowed");

	/** The implementors' returned keys determine which implementor is invoked for which sorts of records. */
	RecordsToHandleKey getRecordsToHandleKey();

	void handleOrderVoided(VoidOrderAndRelatedDocsRequest request);

	@Value
	class RecordsToHandleKey
	{
		public static RecordsToHandleKey of(@NonNull final String tableName)
		{
			return new RecordsToHandleKey(tableName);
		}

		String tableName;

		private RecordsToHandleKey(@NonNull final String tableName)
		{
			this.tableName = tableName;
		}

		/** Needed to identify the proper {@link RelatedRecordsProvider}s for a given {@link VoidOrderAndRelatedDocsRequest}. */
		public SourceRecordsKey toSourceRecordsKey()
		{
			return SourceRecordsKey.of(tableName);
		}

		/** Needed to identify the proper implementor(s) of this interface for a given {@link RelatedRecordsProvider}'s result. */
		public static RecordsToHandleKey ofSourceRecordsKey(@NonNull final SourceRecordsKey key)
		{
			return RecordsToHandleKey.of(key.getTableName());
		}
	}

	static ITranslatableString createInvalidDocStatusErrorMessage(
			@NonNull final OrderId orderId,
			@NonNull final String documentTrlValue,
			@NonNull final String documentNo,
			@NonNull final DocStatus docStatus)
	{
		final I_C_Order orderRecord = Services.get(IOrderDAO.class).getById(orderId, I_C_Order.class);

		final ADReferenceService adReferenceService = ADReferenceService.get();
		final String docStatusTrl = adReferenceService.retrieveListNameTrl(DocStatus.AD_REFERENCE_ID, docStatus.getCode());

		final IMsgBL msgBL = Services.get(IMsgBL.class);

		return msgBL.getTranslatableMsgText(
				Msg_OrderDocumentCancelNotAllowed_4P,
				orderRecord.getDocumentNo(),
				msgBL.translate(Env.getCtx(), documentTrlValue),
				documentNo,
				docStatusTrl);
	}
}
