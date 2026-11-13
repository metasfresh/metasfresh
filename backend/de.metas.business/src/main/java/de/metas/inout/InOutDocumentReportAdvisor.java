/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.inout;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPPrintFormatQuery;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.i18n.Language;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.process.AdProcessId;
import de.metas.report.DocumentReportAdvisor;
import de.metas.report.DocumentReportAdvisorUtil;
import de.metas.report.DocumentReportInfo;
import de.metas.report.PrintCopies;
import de.metas.report.PrintFormatId;
import de.metas.report.StandardDocumentReportType;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Shipper;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.math.RoundingMode;

@Component
public class InOutDocumentReportAdvisor implements DocumentReportAdvisor
{
	private final IInOutBL inoutBL = Services.get(IInOutBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);
	private final DocumentReportAdvisorUtil util;

	public InOutDocumentReportAdvisor(@NonNull final DocumentReportAdvisorUtil util)
	{
		this.util = util;
	}

	@Override
	public @NonNull String getHandledTableName()
	{
		return I_M_InOut.Table_Name;
	}

	@Override
	public StandardDocumentReportType getStandardDocumentReportType()
	{
		return StandardDocumentReportType.SHIPMENT;
	}

	@Override
	public @NonNull DocumentReportInfo getDocumentReportInfo(
			@NonNull final TableRecordReference recordRef,
			@Nullable final PrintFormatId adPrintFormatToUseId, final AdProcessId reportProcessIdToUse)
	{
		final InOutId inoutId = recordRef.getIdAssumingTableName(I_M_InOut.Table_Name, InOutId::ofRepoId);
		final I_M_InOut inout = inoutBL.getById(inoutId);
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(inout.getC_BPartner_ID());
		final I_C_BPartner bpartner = util.getBPartnerById(bpartnerId);

		final DocTypeId docTypeId = extractDocTypeId(inout);
		final I_C_DocType docType = util.getDocTypeById(docTypeId);

		final ClientId clientId = ClientId.ofRepoId(inout.getAD_Client_ID());

		final PrintFormatId printFormatId = CoalesceUtil.coalesceSuppliers(
				() -> adPrintFormatToUseId,
				() -> util.getBPartnerPrintFormats(bpartnerId).getPrintFormatIdByDocTypeId(docTypeId).orElse(null),
				() -> PrintFormatId.ofRepoIdOrNull(docType.getAD_PrintFormat_ID()),
				() -> util.getDefaultPrintFormats(clientId).getShipmentPrintFormatId());
		if (printFormatId == null)
		{
			throw new AdempiereException("@NotFound@ @AD_PrintFormat_ID@");
		}

		final Language language = util.getBPartnerLanguage(bpartner).orElse(null);

		final I_C_Order order = getOrderOrNull(inout);
		// a manual shipment has no order to inspect -> treat as NOT a drop-ship (do not suppress auto-print)
		final boolean docIsDropShip = order != null && util.isDropShip(order);

		final BPPrintFormatQuery bpPrintFormatQuery = BPPrintFormatQuery.builder()
				.adTableId(recordRef.getAdTableId())
				.bpartnerId(bpartnerId)
				.bPartnerLocationId(BPartnerLocationId.ofRepoId(bpartnerId, inout.getC_BPartner_Location_ID()))
				.docTypeId(docTypeId)
				.onlyCopiesGreaterZero(true)
				.isDropShip(docIsDropShip)
				.build();

		// the shipment's Lieferweg count (incl. 0) wins over the per-address/doc-type resolution, which stays the fallback
		final PrintCopies shipperCopies = getShipperCopiesOrNull(inout, docType);
		final PrintCopies copies = shipperCopies != null ? shipperCopies : util.getDocumentCopies(docType, bpPrintFormatQuery);
		// 0 copies are coerced to 1 when the print data is created, so a 0 must suppress the auto-print queue item instead
		final boolean suppressAutoPrint = (shipperCopies != null && shipperCopies.isZero()) || util.resolveSuppressAutoPrint(bpPrintFormatQuery);

		return DocumentReportInfo.builder()
				.recordRef(TableRecordReference.of(I_M_InOut.Table_Name, inoutId))
				.reportProcessId(util.getReportProcessIdByPrintFormatId(printFormatId))
				.copies(copies)
				.documentNo(inout.getDocumentNo())
				.bpartnerId(bpartnerId)
				.docTypeId(docTypeId)
				.language(language)
				.poReference(inout.getPOReference())
				.suppressAutoPrint(suppressAutoPrint)
				.build();
	}

	/**
	 * @return the copies configured on the shipment's Lieferweg ({@code M_Shipper.DocumentCopies}); {@code null} if the record is not a customer shipment,
	 * has no shipper or the column is NULL
	 */
	@Nullable
	private PrintCopies getShipperCopiesOrNull(@NonNull final I_M_InOut inout, @NonNull final I_C_DocType docType)
	{
		// the Lieferweg count is a delivery-note setting; receipts and returns keep their per-address/doc-type copies
		if (!inout.isSOTrx() || !DocBaseType.Shipment.getCode().equals(docType.getDocBaseType()))
		{
			return null;
		}

		final ShipperId shipperId = ShipperId.ofRepoIdOrNull(inout.getM_Shipper_ID());
		if (shipperId == null)
		{
			return null;
		}

		// the generated getter returns 0 for NULL, so NULL ("not set") must be checked on the column itself
		final I_M_Shipper shipper = shipperDAO.getById(shipperId);
		if (InterfaceWrapperHelper.isNull(shipper, I_M_Shipper.COLUMNNAME_DocumentCopies))
		{
			return null;
		}

		// the column is a Quantity (so that clearing it in the UI stores NULL, not 0); a fraction can only come from a non-UI write
		// and is rounded up, so a positive count never becomes 0 (= print nothing)
		final int copies = shipper.getDocumentCopies().setScale(0, RoundingMode.CEILING).intValueExact();
		return PrintCopies.ofInt(copies);
	}

	@Nullable
	private I_C_Order getOrderOrNull(@NonNull final I_M_InOut inout)
	{
		final OrderId orderId = OrderId.ofRepoIdOrNull(inout.getC_Order_ID());
		return orderId != null ? orderDAO.getById(orderId) : null;
	}

	private DocTypeId extractDocTypeId(@NonNull final I_M_InOut inout)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(inout.getC_DocType_ID());
		if (docTypeId != null)
		{
			return docTypeId;
		}
		else
		{
			throw new AdempiereException("No document type set");
		}
	}
}
