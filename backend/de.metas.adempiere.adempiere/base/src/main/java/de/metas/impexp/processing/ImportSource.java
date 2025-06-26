package de.metas.impexp.processing;

import de.metas.impexp.ActualImportRecordsResult;
import de.metas.process.PInstanceId;
import lombok.NonNull;

import java.util.Iterator;
import java.util.Set;

public interface ImportSource<ImportRecordType>
{
	String getTableName();

	String getKeyColumnName();

	boolean isEmpty();

	ImportRecordsSelection getSelection();

	int deleteImportRecords(@NonNull ImportDataDeleteRequest request);

	/**
	 * Reset standard columns (Client, Org, IsActive, Created/Updated).
	 * <p>
	 * Called before starting to validate.
	 */
	void resetStandardColumns();

	Iterator<ImportRecordType> retrieveRecordsToImport();

	void runSQLAfterRowImport(ImportRecordType importRecord);

	void runSQLAfterAllImport();

	PInstanceId getMainSelectionId();

	ActualImportRecordsResult.Error markAsError(@NonNull Set<Integer> importRecordIds, @NonNull Throwable exception);

}
