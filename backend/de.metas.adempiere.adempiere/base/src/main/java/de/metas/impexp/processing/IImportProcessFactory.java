package de.metas.impexp.processing;

import de.metas.util.ISingletonService;

/**
 * {@link IImportProcess} factory.
 * 
 * @author tsa
 *
 */
public interface IImportProcessFactory extends ISingletonService
{
	/**
	 * Registers which {@link IImportProcess} shall be used for a given import table model.
	 * 
	 * @param modelImportClass import table model (e.g. I_I_BPartner).
	 * @param importProcessClass import process class to be used
	 */
	<ImportRecordType> void registerImportProcess(Class<ImportRecordType> modelImportClass, Class<? extends IImportProcess<ImportRecordType>> importProcessClass);

	void setDeleteImportDataProcessClass(Class<?> deleteImportDataProcessClass);

	<ImportRecordType> IImportProcess<ImportRecordType> newImportProcess(Class<ImportRecordType> modelImportClass);

	<ImportRecordType> IImportProcess<ImportRecordType> newImportProcessForTableName(String importTableName);
}
