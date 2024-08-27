/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.document.archive.api.impl;

import de.metas.acct.api.AccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.DocTypeId;
import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.i18n.Language;
import de.metas.process.AdProcessId;
import de.metas.report.DocumentReportAdvisor;
import de.metas.report.DocumentReportAdvisorUtil;
import de.metas.report.DocumentReportInfo;
import de.metas.report.PrintFormatId;
import de.metas.report.StandardDocumentReportType;
import de.metas.shippingnotification.ShippingNotification;
import de.metas.shippingnotification.ShippingNotificationId;
import de.metas.shippingnotification.ShippingNotificationService;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_InOut;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
public class ShippingNotificationDocumentReportAdvisor implements DocumentReportAdvisor
{
	private final DocumentReportAdvisorUtil util;
	private final ShippingNotificationService shippingNotificationService;
	private final IDocOutboundDAO docOutboundDAO = Services.get(IDocOutboundDAO.class);
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

	public ShippingNotificationDocumentReportAdvisor(@NonNull final DocumentReportAdvisorUtil util, final ShippingNotificationService shippingNotificationService)
	{
		this.util = util;
		this.shippingNotificationService = shippingNotificationService;
	}

	@Override
	public @NonNull String getHandledTableName()
	{
		return I_M_Shipping_Notification.Table_Name;
	}

	@Override
	public StandardDocumentReportType getStandardDocumentReportType()
	{
		return StandardDocumentReportType.SHIPPINGNOTIFICATION;
	}

	@Override
	public @NonNull DocumentReportInfo getDocumentReportInfo(
			@NonNull final TableRecordReference recordRef,
			@Nullable final PrintFormatId adPrintFormatToUseId, final AdProcessId reportProcessIdToUse)
	{
		final ShippingNotificationId shippingNotificationId = recordRef.getIdAssumingTableName(I_M_Shipping_Notification.Table_Name, ShippingNotificationId::ofRepoId);
		final ShippingNotification shippingNotification = shippingNotificationService.getById(shippingNotificationId);

		final AdColumnId columnId = docOutboundDAO.retrievePartnerColumnCorelatedWithPrintFormatId(recordRef, adPrintFormatToUseId).orElse(null);

		BPartnerId bpartnerId;
		if (columnId == null)
		{
			bpartnerId = shippingNotification.getBPartnerId();
		}
		else
		{
			final I_M_Shipping_Notification shippingNotificationModel = shippingNotificationService.getRecordById(shippingNotificationId);
			final String columnName = adTableDAO.retrieveColumnName(columnId.getRepoId());
			final Object partnerIdObj = InterfaceWrapperHelper.getValueOrNull(shippingNotificationModel, columnName);
			bpartnerId = BPartnerId.ofRepoIdOrNull(NumberUtils.asInt(partnerIdObj, -1));
		}

		final DocTypeId docTypeId = shippingNotification.getDocTypeId();
		final I_C_DocType docType = util.getDocTypeById(docTypeId);

		final ClientId clientId = shippingNotification.getClientId();

		final PrintFormatId printFormatId = CoalesceUtil.coalesceSuppliers(
				() -> adPrintFormatToUseId,
				() -> util.getBPartnerPrintFormats(bpartnerId).getPrintFormatIdByDocTypeId(docTypeId).orElse(null),
				() -> PrintFormatId.ofRepoIdOrNull(docType.getAD_PrintFormat_ID()));
		if (printFormatId == null)
		{
			throw new AdempiereException("@NotFound@ @AD_PrintFormat_ID@");
		}

		final I_C_BPartner bpartner = util.getBPartnerById(bpartnerId);
		final Language language = util.getBPartnerLanguage(bpartner).orElse(null);

		return DocumentReportInfo.builder()
				.recordRef(TableRecordReference.of(I_M_InOut.Table_Name, shippingNotificationId))
				.reportProcessId(util.getReportProcessIdByPrintFormatId(printFormatId))
				.copies(util.getDocumentCopies(bpartner, docType))
				.documentNo(shippingNotification.getDocumentNo())
				.bpartnerId(bpartnerId)
				.docTypeId(docTypeId)
				.language(language)
				.build();
	}
}
