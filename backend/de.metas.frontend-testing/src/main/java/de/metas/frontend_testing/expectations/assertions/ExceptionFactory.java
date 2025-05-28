package de.metas.frontend_testing.expectations.assertions;

import org.adempiere.exceptions.AdempiereException;

interface ExceptionFactory
{
	AdempiereException createException(final String message);
}
