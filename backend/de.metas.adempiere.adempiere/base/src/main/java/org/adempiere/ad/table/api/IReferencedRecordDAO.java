package org.adempiere.ad.table.api;

import de.metas.util.ISingletonService;
import org.adempiere.util.lang.impl.TableRecordReference;

/**
 * Generic access to the record a {@link TableRecordReference} points to, without loading it.
 */
public interface IReferencedRecordDAO extends ISingletonService
{
	/**
	 * @return true if the referenced record still exists.
	 * A {@link TableRecordReference} may outlive the record it points to, e.g. when a posting is scheduled
	 * for a document which is deleted before the posting is actually executed.
	 */
	boolean exists(TableRecordReference recordRef);
}
