package de.metas.handlingunits.picking.job.service.shelflife;

import de.metas.i18n.AdMessageKey;
import org.adempiere.exceptions.AdempiereException;

/**
 * Thrown when the remaining shelf life of a picked HU would be undercut at the time of delivery,
 * and the picker has not yet confirmed (acknowledged) the shelf-life warning.
 *
 * <p>The frontend matches this exception by the stable {@code errorCode} value {@code "RLZ_TooShort"}
 * (surfaced in {@code responseData.errors[0].errorCode}).  That value is derived automatically from
 * the {@link AdMessageKey} via {@code key.toAD_Message()} — see
 * {@code AdempiereException(AdMessageKey)} constructor.
 */
public class ShelfLifeTooShortException extends AdempiereException
{
	private static final AdMessageKey MSG_RLZ_TooShort = AdMessageKey.of("RLZ_TooShort");

	public ShelfLifeTooShortException()
	{
		super(MSG_RLZ_TooShort);
		// The parent constructor sets:
		//   errorCode = coalesce(msgBL.getErrorCode("RLZ_TooShort"), "RLZ_TooShort")
		// Guarantee the stable "RLZ_TooShort" code even when the DB message has no errorCode column:
		setErrorCode("RLZ_TooShort");
		markAsUserValidationError();
	}
}
