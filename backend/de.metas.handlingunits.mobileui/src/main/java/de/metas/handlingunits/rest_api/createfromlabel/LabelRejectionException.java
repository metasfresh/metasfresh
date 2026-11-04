package de.metas.handlingunits.rest_api.createfromlabel;

import de.metas.i18n.AdMessageKey;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

/** A scanned label that cannot be turned into an HU: carries the AD_Message that explains why (one plain-language message for the user). */
class LabelRejectionException extends AdempiereException
{
	@Getter private final AdMessageKey messageKey;
	@Getter private final Object[] messageParams;

	LabelRejectionException(@NonNull final AdMessageKey messageKey, final Object... messageParams)
	{
		super(messageKey, messageParams);
		this.messageKey = messageKey;
		this.messageParams = messageParams;
	}
}
