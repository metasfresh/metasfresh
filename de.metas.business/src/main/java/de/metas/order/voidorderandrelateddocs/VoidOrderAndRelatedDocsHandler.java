package de.metas.order.voidorderandrelateddocs;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.util.Services;
import org.compiere.model.X_C_Invoice;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_C_Order;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.order.OrderId;
import de.metas.util.RelatedRecordsProvider.SourceRecordsKey;
import lombok.NonNull;
import lombok.Value;

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
 * Implementors are invoked when an order is voided.
 * The advantage of this interface in comparison with a model interceptor is
 * that its implementors need to provide a seqNo such that they can be invoked in a correct order.
 *
 */
public interface VoidOrderAndRelatedDocsHandler
{
	String Msg_OrderDocumentCancelNotAllowed_4P = "de.metas.order.Msg_OrderDocumentCancelNotAllowed";

	RecordsToHandleKey getRecordsToHandleKey();

	void handleOrderVoided(VoidOrderAndRelatedDocsRequest request);

	@Value
	public class RecordsToHandleKey
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

		public SourceRecordsKey toSourceRecordsKey()
		{
			return SourceRecordsKey.of(tableName);
		}

		public static RecordsToHandleKey ofSourceRecordsKey(@NonNull final SourceRecordsKey key)
		{
			return RecordsToHandleKey.of(key.getTableName());
		}
	}

	public static ITranslatableString createErrorMessage(
			@NonNull final OrderId orderId,
			@NonNull final String documentTrlValue,
			@NonNull final String documentNo,
			@NonNull final String docStatus)
	{
		final I_C_Order orderRecord = load(orderId, I_C_Order.class);

		final IADReferenceDAO referenceDAO = Services.get(IADReferenceDAO.class);
		final String docStatusTrl = referenceDAO.retrieveListNameTrl(X_C_Invoice.DOCSTATUS_AD_Reference_ID, docStatus);

		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final ITranslatableString errorMsg = msgBL.getTranslatableMsgText(
				Msg_OrderDocumentCancelNotAllowed_4P,
				orderRecord.getDocumentNo(),
				msgBL.translate(Env.getCtx(), documentTrlValue),
				documentNo,
				docStatusTrl);
		return errorMsg;
	}
}
