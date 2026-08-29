package de.metas.acct.api;

import de.metas.util.ISingletonService;
import org.adempiere.util.lang.impl.TableRecordReference;

public interface IPostingDocumentDAO extends ISingletonService
{
	/**
	 * @return true if the record which shall be posted still exists.
	 * A posting can be scheduled for a document which is deleted before the posting is actually executed.
	 */
	boolean exists(TableRecordReference documentRef);
}
