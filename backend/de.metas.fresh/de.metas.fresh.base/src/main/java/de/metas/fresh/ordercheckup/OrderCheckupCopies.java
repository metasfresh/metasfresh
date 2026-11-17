package de.metas.fresh.ordercheckup;

import de.metas.report.PrintCopies;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Copies of a Plant order checkup (PL) from the order's shipper (Lieferweg) {@code M_Shipper.DocumentCopies}:
 * N &gt; 0 = N copies on every print; 0 = archive, but no automatic print. Without a shipper count the sysconfig applies, as before.
 */
@Value
@Builder
public class OrderCheckupCopies
{
	/** incl. {@link PrintCopies#ZERO} */
	@NonNull PrintCopies copies;

	/** the shipper says 0 copies: archive, but do not print automatically */
	public boolean isShipperSaysNoPrint()
	{
		return copies.isZero();
	}
}
