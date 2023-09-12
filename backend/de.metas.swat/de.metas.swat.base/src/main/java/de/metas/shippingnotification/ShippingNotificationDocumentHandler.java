/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.shippingnotification;

import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentTableFields;
import de.metas.document.engine.IDocument;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.MPeriod;
import org.compiere.util.Env;

@RequiredArgsConstructor
public class ShippingNotificationDocumentHandler implements DocumentHandler
{
	private final ShippingNotificationService shippingNotificationService;

	private static I_M_Shipping_Notification extractShippingNotification(@NonNull final DocumentTableFields docFields)
	{
		return InterfaceWrapperHelper.create(docFields, I_M_Shipping_Notification.class);
	}

	@Override
	public String getSummary(@NonNull final DocumentTableFields docFields)
	{
		return extractShippingNotification(docFields).getDocumentNo();
	}

	@Override
	public String getDocumentInfo(@NonNull final DocumentTableFields docFields)
	{
		return getSummary(docFields);
	}

	@Override
	public int getDoc_User_ID(@NonNull final DocumentTableFields docFields)
	{
		return extractShippingNotification(docFields).getCreatedBy();
	}

	@Override
	public InstantAndOrgId getDocumentDate(@NonNull final DocumentTableFields docFields)
	{
		final I_M_Shipping_Notification record = extractShippingNotification(docFields);
		return InstantAndOrgId.ofTimestamp(record.getPhysicalClearanceDate(), OrgId.ofRepoId(record.getAD_Org_ID()));
	}

	@Override
	public String completeIt(@NonNull final DocumentTableFields docFields)
	{
		final I_M_Shipping_Notification shippingNotificationRecord = extractShippingNotification(docFields);
		assertPeriodOpen(shippingNotificationRecord);

		shippingNotificationService.updateWhileSaving(
				shippingNotificationRecord,
				ShippingNotification::completeIt
		);

		shippingNotificationRecord.setDocAction(IDocument.ACTION_Reverse_Correct);
		return IDocument.STATUS_Completed;
	}

	@Override
	public void reactivateIt(@NonNull final DocumentTableFields docFields)
	{
		final I_M_Shipping_Notification shippingNotificationRecord = extractShippingNotification(docFields);
		assertPeriodOpen(shippingNotificationRecord);
		shippingNotificationRecord.setProcessed(false);
		shippingNotificationRecord.setDocAction(IDocument.ACTION_Complete);
	}

	private static void assertPeriodOpen(final I_M_Shipping_Notification record)
	{
		MPeriod.testPeriodOpen(Env.getCtx(), record.getDateAcct(), record.getC_DocType_ID(), record.getAD_Org_ID());
	}

}
