package de.metas.frontend_testing.expectations.assertions;

import org.adempiere.exceptions.AdempiereException;

interface ExceptionCollector
{
	void collect(final AdempiereException error);
}
