package de.metas.shipping;

import de.metas.report.PrintCopies;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Shipper;

import javax.annotation.Nullable;
import java.math.RoundingMode;

/**
 * Reads the number of document copies configured on a shipper ({@code M_Shipper.DocumentCopies}, "Lieferweg").
 * Shared by every document whose printed copies follow the shipper (delivery note, order checkup), so the
 * "not set" / zero / rounding rules exist only once.
 */
@UtilityClass
public class ShipperDocumentCopies
{
	/**
	 * @return the shipper's copies, incl. {@link PrintCopies#ZERO}; {@code null} if the column is NULL (= not set)
	 */
	@Nullable
	public static PrintCopies ofShipperOrNull(@NonNull final I_M_Shipper shipper)
	{
		// the generated getter returns 0 for NULL, so NULL ("not set") must be checked on the column itself
		if (InterfaceWrapperHelper.isNull(shipper, I_M_Shipper.COLUMNNAME_DocumentCopies))
		{
			return null;
		}

		// the column is a Quantity (so that clearing it in the UI stores NULL, not 0); a fraction can only come from a non-UI write
		// and is rounded up, so a positive count never becomes 0 (= print nothing)
		final int copies = shipper.getDocumentCopies().setScale(0, RoundingMode.CEILING).intValueExact();
		return PrintCopies.ofInt(copies);
	}
}
