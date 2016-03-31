package org.compiere.dbPort;

import java.util.Collections;
import java.util.List;

/**
 * Native PostgreSQL (pass-through) implementation of {@link Convert}
 * 
 * @author tsa
 * 
 */
public final class Convert_PostgreSQL_Native extends Convert
{

	@Override
	protected final List<String> convertStatement(final String sqlStatement)
	{
		return Collections.singletonList(sqlStatement);
	}

}
