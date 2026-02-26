package de.metas.impexp.processing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

@FunctionalInterface
public interface ImportRecordLoader<ImportRecordType>
{
	ImportRecordType retrieveImportRecord(final Properties ctx, final ResultSet rs) throws SQLException;
}
