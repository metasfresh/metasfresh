package de.metas.inout;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPPrintFormatQuery;
import de.metas.document.DocTypeId;
import de.metas.report.DocumentReportAdvisorUtil;
import de.metas.report.PrintCopies;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperDocumentCopies;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_InOut;

import javax.annotation.Nullable;

/**
 * The number of delivery-note (Lieferschein) copies of a customer shipment, in this order:
 * <ol>
 * <li>the shipper's (Lieferweg) {@code M_Shipper.DocumentCopies}, if set (incl. 0 = no automatic print)</li>
 * <li>else the partner/ship-to address print format ({@code C_BP_PrintFormat.DocumentCopies_Override > 0}) for the shipment document type</li>
 * <li>else the shipment document type's {@code DocumentCopies}, else 1</li>
 * </ol>
 * Used for the shipment itself and for documents that must print the same number of copies as the shipment of their order
 * (the order checkup "Bestellkontrolle Büro").
 */
public class ShipmentDocumentCopiesResolver
{
	private final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private final DocumentReportAdvisorUtil util;

	public ShipmentDocumentCopiesResolver(@NonNull final DocumentReportAdvisorUtil util)
	{
		this.util = util;
	}

	@NonNull
	public ShipmentDocumentCopies resolve(@NonNull final ShipmentDocumentCopiesRequest request)
	{
		final BPPrintFormatQuery bpPrintFormatQuery = BPPrintFormatQuery.builder()
				.adTableId(AdTableId.ofRepoId(tableDAO.retrieveTableId(I_M_InOut.Table_Name)))
				.bpartnerId(request.getBpartnerId())
				.bPartnerLocationId(request.getBpartnerLocationId())
				.docTypeId(request.getShipmentDocType() != null ? DocTypeId.ofRepoId(request.getShipmentDocType().getC_DocType_ID()) : null)
				.onlyCopiesGreaterZero(true)
				.isDropShip(request.isDropShip())
				.build();

		final PrintCopies shipperCopies = request.getShipperId() != null
				? ShipperDocumentCopies.ofShipperOrNull(shipperDAO.getById(request.getShipperId()))
				: null;

		return ShipmentDocumentCopies.builder()
				.copies(shipperCopies != null ? shipperCopies : util.getDocumentCopies(request.getShipmentDocType(), bpPrintFormatQuery))
				.fromShipper(shipperCopies != null)
				.bpPrintFormatQuery(bpPrintFormatQuery)
				.build();
	}

	@Value
	@Builder
	public static class ShipmentDocumentCopiesRequest
	{
		@NonNull BPartnerId bpartnerId;
		@Nullable BPartnerLocationId bpartnerLocationId;
		@Nullable I_C_DocType shipmentDocType;
		/** {@code null} if the shipper count shall not be considered (e.g. not a customer shipment) */
		@Nullable ShipperId shipperId;
		boolean dropShip;
	}

	@Value
	@Builder
	public static class ShipmentDocumentCopies
	{
		/** incl. {@link PrintCopies#ZERO} if the shipper says so */
		@NonNull PrintCopies copies;
		/** true if the copies are the shipper's (Lieferweg) count */
		boolean fromShipper;
		/** the partner print-format query the copies were looked up with (e.g. to also resolve its auto-print flag) */
		@NonNull BPPrintFormatQuery bpPrintFormatQuery;

		/** the shipper says 0 copies: archive, but do not print automatically */
		public boolean isShipperSaysNoPrint()
		{
			return fromShipper && copies.isZero();
		}
	}
}
