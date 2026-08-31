package org.adempiere.ad.table.api;

import de.metas.util.ISingletonService;
import org.adempiere.util.lang.impl.TableRecordReference;

public interface IReferencedRecordDAO extends ISingletonService
{
	boolean exists(TableRecordReference recordRef);
}
