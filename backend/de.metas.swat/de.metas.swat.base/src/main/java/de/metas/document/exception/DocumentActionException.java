package de.metas.document.exception;

import org.adempiere.exceptions.AdempiereException;

import de.metas.i18n.AdMessageKey;
import lombok.NonNull;

/**
 * Handles document action related exceptions
 * 
 * @author RC
 * 
 */
@SuppressWarnings("serial")
public class DocumentActionException extends AdempiereException
{
	public DocumentActionException(@NonNull final AdMessageKey message)
	{
		super(message);
	}
}
