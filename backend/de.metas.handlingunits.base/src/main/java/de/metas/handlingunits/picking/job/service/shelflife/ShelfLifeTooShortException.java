package de.metas.handlingunits.picking.job.service.shelflife;

import de.metas.i18n.AdMessageKey;
import org.adempiere.exceptions.AdempiereException;

/**
 * Thrown when the remaining shelf life of a picked HU would be undercut at the time of delivery,
 * and the picker has not yet confirmed (acknowledged) the shelf-life warning.
 *
 * <p>Identified by the stable error code {@code "RLZ_TooShort"} (the {@link AdMessageKey}); the
 * {@code AdempiereException(AdMessageKey)} constructor sets both the error code and the
 * user-validation-error flag.
 */
public class ShelfLifeTooShortException extends AdempiereException
{
	private static final AdMessageKey MSG_RLZ_TooShort = AdMessageKey.of("RLZ_TooShort");

	public ShelfLifeTooShortException()
	{
		super(MSG_RLZ_TooShort);
	}
}
