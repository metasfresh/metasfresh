package de.metas.fresh.ordercheckup;

import de.metas.report.PrintCopies;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Copies of a Plant order checkup (PL). It prints the same number of copies as the delivery note of its order,
 * determined the same way the delivery note's are:
 * <ol>
 * <li>the order's shipper (Lieferweg) {@code M_Shipper.DocumentCopies}, if set (incl. 0 = archive, but no automatic print)</li>
 * <li>else the partner/ship-to address print format ({@code C_BP_PrintFormat.DocumentCopies_Override > 0}) for the delivery note</li>
 * <li>else the delivery note document type's {@code DocumentCopies}, else 1</li>
 * </ol>
 */
@Value
@Builder
public class OrderCheckupCopies
{
	/** incl. {@link PrintCopies#ZERO} if the shipper says so */
	@NonNull PrintCopies copies;

	/** true if the copies are the shipper's (Lieferweg) count */
	boolean fromShipper;

	/** the shipper says 0 copies: archive, but do not print automatically */
	public boolean isShipperSaysNoPrint()
	{
		return fromShipper && copies.isZero();
	}

	/** what a print of the document gets: a manual print of a "no print" document still prints one copy */
	public int getCopiesToPrint()
	{
		return copies.isZero() ? 1 : copies.toInt();
	}
}
