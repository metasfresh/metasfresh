package de.metas.hu_consolidation.mobile.job;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

/**
 * The label of a target could not be printed, although the target itself was closed and shipped.
 * The message tells the operator so, with a short translated reason; the technical error is the cause.
 */
public class HUConsolidationLabelNotPrintedException extends AdempiereException
{
	private static final AdMessageKey MSG_PALLET_SHIPPED_LABEL_NOT_PRINTED = AdMessageKey.of("de.metas.hu_consolidation.mobile.job.PALLET_SHIPPED_LABEL_NOT_PRINTED");
	private static final AdMessageKey MSG_REASON_PRINT_FAILED = AdMessageKey.of("de.metas.hu_consolidation.mobile.job.LABEL_NOT_PRINTED_REASON_PRINT_FAILED");

	/**
	 * @param cause the error of the label print itself
	 */
	public static HUConsolidationLabelNotPrintedException printFailed(@NonNull final Throwable cause)
	{
		return new HUConsolidationLabelNotPrintedException(MSG_REASON_PRINT_FAILED, cause);
	}

	private HUConsolidationLabelNotPrintedException(@NonNull final AdMessageKey reason, @NonNull final Throwable cause)
	{
		super(MSG_PALLET_SHIPPED_LABEL_NOT_PRINTED, TranslatableStrings.adMessage(reason));
		initCause(cause);
	}
}
