package org.adempiere.ad.migration.validator.sql_migration_context_info.names;

import de.metas.ad_reference.ADRefListId;
import de.metas.i18n.AdMessageId;
import de.metas.process.AdProcessId;
import de.metas.reflist.ReferenceId;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.element.api.AdElementId;
import org.adempiere.ad.element.api.AdFieldId;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdUIColumnId;
import org.adempiere.ad.element.api.AdUISectionId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.window.api.UIElementGroupId;
import org.compiere.util.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

public final class Names
{
	private static String sqlSelectColumn(final String tableAlias, final String columnName, final String asPrefix)
	{
		return tableAlias + "." + columnName + " AS " + asPrefix + columnName;
	}

	public static final class ADReferenceName_Loader
	{
		public static String retrieve(final ReferenceId adReferenceId)
		{
			final String name = DB.getSQLValueStringEx(
					ITrx.TRXNAME_ThreadInherited,
					"SELECT Name FROM AD_Reference WHERE AD_Reference_ID=?",
					adReferenceId);

			return StringUtils.trimBlankToOptional(name)
					.orElseGet(() -> "<" + adReferenceId.getRepoId() + ">");
		}
	}

	public static final class ADRefListName_Loader
	{
		public static String retrieve(final ADRefListId refListId)
		{
			final String name = DB.getSQLValueStringEx(
					ITrx.TRXNAME_ThreadInherited,
					"SELECT r.Name||' -> '||rl.Value||'_'||rl.ValueName"
							+ " FROM AD_Ref_List rl"
							+ " INNER JOIN AD_Reference r ON r.AD_Reference_ID=rl.AD_Reference_ID"
							+ " WHERE rl.AD_Ref_List_ID=?",
					refListId);

			return StringUtils.trimBlankToOptional(name)
					.orElseGet(() -> "<" + refListId.getRepoId() + ">");
		}
	}

	public static final class ADTableName_Loader
	{
		private static final String PREFIX = "t_";

		public static ADTableName retrieve(@NonNull final AdTableId adTableId)
		{
			final ADTableName name = DB.retrieveFirstRowOrNull(
					"SELECT " + ADTableName_Loader.sqlSelect("t")
							+ " FROM AD_Table t"
							+ " WHERE t.AD_Table_ID=?",
					Collections.singletonList(adTableId),
					ADTableName_Loader::retrieve
			);

			return name != null
					? name
					: ADTableName.missing(adTableId);
		}

		@SuppressWarnings("SameParameterValue")
		private static String sqlSelect(final String tableAlias)
		{
			return sqlSelectColumn(tableAlias, "AD_Table_ID", PREFIX)
					+ ", " + sqlSelectColumn(tableAlias, "TableName", PREFIX)
					+ ", " + sqlSelectColumn(tableAlias, "EntityType", PREFIX);
		}

		private static ADTableName retrieve(final ResultSet rs) throws SQLException
		{
			return ADTableName.builder()
					.adTableId(AdTableId.ofRepoId(rs.getInt(PREFIX + "AD_Table_ID")))
					.tableName(rs.getString(PREFIX + "TableName"))
					.entityType(rs.getString(PREFIX + "EntityType"))
					.build();
		}
	}

	public static final class ADColumnNameFQ_Loader
	{
		private static final String PREFIX = "c_";

		public static ADColumnNameFQ retrieve(@NonNull final AdColumnId adColumnId)
		{
			final ADColumnNameFQ columnNameFQ = DB.retrieveFirstRowOrNull(
					"SELECT " + ADTableName_Loader.sqlSelect("t")
							+ ", " + ADColumnNameFQ_Loader.sqlSelect("c")
							+ " FROM AD_Column c"
							+ " LEFT OUTER JOIN AD_Table t on t.AD_Table_ID=c.AD_Table_ID "
							+ " WHERE c.AD_Column_ID=?",
					Collections.singletonList(adColumnId),
					ADColumnNameFQ_Loader::retrieve
			);

			return columnNameFQ != null
					? columnNameFQ
					: ADColumnNameFQ.missing("<AD_Column_ID=" + adColumnId.getRepoId() + ">");
		}

		public static ADColumnNameFQ retrieve(final AdFieldId adFieldId)
		{
			final ADColumnNameFQ columnNameFQ = DB.retrieveFirstRowOrNull(
					"SELECT " + ADTableName_Loader.sqlSelect("t")
							+ "," + ADColumnNameFQ_Loader.sqlSelect("c")
							+ " FROM AD_Field f"
							+ " LEFT OUTER JOIN AD_Column c on c.AD_Column_ID=f.AD_Column_ID"
							+ " LEFT OUTER JOIN AD_Table t on t.AD_Table_ID=c.AD_Table_ID "
							+ " WHERE f.AD_Field_ID=?",
					Collections.singletonList(adFieldId),
					ADColumnNameFQ_Loader::retrieve
			);

			return columnNameFQ != null
					? columnNameFQ
					: ADColumnNameFQ.missing("<AD_Field_ID=" + adFieldId.getRepoId() + ">");
		}

		@SuppressWarnings("SameParameterValue")
		private static String sqlSelect(final String tableAlias)
		{
			return sqlSelectColumn(tableAlias, "AD_Column_ID", PREFIX)
					+ ", " + sqlSelectColumn(tableAlias, "ColumnName", PREFIX)
					+ ", " + sqlSelectColumn(tableAlias, "EntityType", PREFIX);
		}

		private static ADColumnNameFQ retrieve(final ResultSet rs) throws SQLException
		{
			return ADColumnNameFQ.builder()
					.adColumnId(AdColumnId.ofRepoId(rs.getInt(PREFIX + "AD_Column_ID")))
					.columnName(rs.getString(PREFIX + "ColumnName"))
					.entityType(rs.getString(PREFIX + "EntityType"))
					.tableName(ADTableName_Loader.retrieve(rs))
					.build();
		}
	}

	public static final class ADWindowName_Loader
	{
		private static final String PREFIX = "w_";

		public static ADWindowName retrieve(@NonNull final AdWindowId adWindowId)
		{
			final ADWindowName windowName = DB.retrieveFirstRowOrNull(
					"SELECT " + ADWindowName_Loader.sqlSelect("w")
							+ " FROM AD_Window w"
							+ " WHERE w.AD_Window_ID=?",
					Collections.singletonList(adWindowId),
					ADWindowName_Loader::retrieve
			);

			return windowName != null
					? windowName
					: ADWindowName.missing(adWindowId);
		}

		@SuppressWarnings("SameParameterValue")
		private static String sqlSelect(final String tableAlias)
		{
			return sqlSelectColumn(tableAlias, "AD_Window_ID", PREFIX)
					+ ", " + sqlSelectColumn(tableAlias, "Name", PREFIX)
					+ ", " + sqlSelectColumn(tableAlias, "EntityType", PREFIX);
		}

		private static ADWindowName retrieve(final ResultSet rs) throws SQLException
		{
			return ADWindowName.builder()
					.adWindowId(AdWindowId.ofRepoId(rs.getInt(PREFIX + "AD_Window_ID")))
					.name(rs.getString(PREFIX + "Name"))
					.entityType(rs.getString(PREFIX + "EntityType"))
					.build();
		}
	}

	public static final class ADTabNameFQ_Loader
	{
		private static final String PREFIX = "tab_";

		public static ADTabNameFQ retrieve(@NonNull final AdTabId adTabId)
		{
			final ADTabNameFQ tabName = DB.retrieveFirstRowOrNull(
					"SELECT " + ADWindowName_Loader.sqlSelect("w")
							+ ", " + ADTabNameFQ_Loader.sqlSelect("tt")
							+ " FROM AD_Tab tt "
							+ " LEFT OUTER JOIN AD_Window w on w.AD_Window_ID=tt.AD_Window_ID "
							+ " WHERE tt.AD_Tab_ID=?",
					Collections.singletonList(adTabId),
					ADTabNameFQ_Loader::retrieve
			);

			return tabName != null
					? tabName
					: ADTabNameFQ.missing(adTabId);
		}

		@SuppressWarnings("SameParameterValue")
		private static String sqlSelect(final String tableAlias)
		{
			return sqlSelectColumn(tableAlias, "AD_Tab_ID", PREFIX)
					+ ", " + sqlSelectColumn(tableAlias, "Name", PREFIX)
					+ ", " + sqlSelectColumn(tableAlias, "EntityType", PREFIX);

		}

		private static ADTabNameFQ retrieve(final ResultSet rs) throws SQLException
		{
			return ADTabNameFQ.builder()
					.adTabId(AdTabId.ofRepoId(rs.getInt(PREFIX + "AD_Tab_ID")))
					.name(rs.getString(PREFIX + "Name"))
					.entityType(rs.getString(PREFIX + "EntityType"))
					.windowName(ADWindowName_Loader.retrieve(rs))
					.build();
		}
	}

	public static final class ADUISectionNameFQ_Loader
	{
		private static final String PREFIX = "uis_";

		public static ADUISectionNameFQ retrieve(@NonNull final AdUISectionId uiSectionId)
		{
			final ADUISectionNameFQ name = DB.retrieveFirstRowOrNull(
					"SELECT " + ADUISectionNameFQ_Loader.sqlSelect("s")
							+ ", " + ADTabNameFQ_Loader.sqlSelect("tt")
							+ ", " + ADWindowName_Loader.sqlSelect("w")
							+ " FROM AD_UI_Section s "
							+ " INNER JOIN AD_Tab tt on tt.AD_Tab_ID=s.AD_Tab_ID"
							+ " INNER JOIN AD_Window w on w.AD_Window_ID=tt.AD_Window_ID"
							+ " WHERE s.AD_UI_Section_ID=?",
					Collections.singletonList(uiSectionId),
					ADUISectionNameFQ_Loader::retrieve
			);

			return name != null
					? name
					: ADUISectionNameFQ.missing(uiSectionId);
		}

		@SuppressWarnings("SameParameterValue")
		private static String sqlSelect(final String tableAlias)
		{
			return sqlSelectColumn(tableAlias, "AD_UI_Section_ID", PREFIX)
					+ ", " + sqlSelectColumn(tableAlias, "Value", PREFIX);
		}

		private static ADUISectionNameFQ retrieve(final ResultSet rs) throws SQLException
		{
			return ADUISectionNameFQ.builder()
					.adUISectionId(AdUISectionId.ofRepoId(rs.getInt(PREFIX + "AD_UI_Section_ID")))
					.value(rs.getString(PREFIX + "Value"))
					.tabName(ADTabNameFQ_Loader.retrieve(rs))
					.build();
		}
	}

	public static final class ADUIColumnNameFQ_Loader
	{
		private static final String PREFIX = "uic_";

		public static ADUIColumnNameFQ retrieve(@NonNull final AdUIColumnId uiColumnId)
		{
			final ADUIColumnNameFQ name = DB.retrieveFirstRowOrNull(
					"SELECT " + ADUIColumnNameFQ_Loader.sqlSelect("c")
							+ ", " + ADUISectionNameFQ_Loader.sqlSelect("s")
							+ ", " + ADTabNameFQ_Loader.sqlSelect("tt")
							+ ", " + ADWindowName_Loader.sqlSelect("w")
							+ " FROM AD_UI_Column c"
							+ " INNER JOIN AD_UI_Section s on s.AD_UI_Section_ID=c.AD_UI_Section_ID"
							+ " INNER JOIN AD_Tab tt on tt.AD_Tab_ID=s.AD_Tab_ID"
							+ " INNER JOIN AD_Window w on w.AD_Window_ID=tt.AD_Window_ID"
							+ " WHERE c.AD_UI_Column_ID=?",
					Collections.singletonList(uiColumnId),
					ADUIColumnNameFQ_Loader::retrieve
			);

			return name != null
					? name
					: ADUIColumnNameFQ.missing(uiColumnId);
		}

		@SuppressWarnings("SameParameterValue")
		private static String sqlSelect(final String tableAlias)
		{
			return sqlSelectColumn(tableAlias, "AD_UI_Column_ID", PREFIX)
					+ ", " + sqlSelectColumn(tableAlias, "SeqNo", PREFIX);
		}

		private static ADUIColumnNameFQ retrieve(final ResultSet rs) throws SQLException
		{
			return ADUIColumnNameFQ.builder()
					.seqNo(rs.getInt(PREFIX + "SeqNo"))
					.uiSectionName(ADUISectionNameFQ_Loader.retrieve(rs))
					.build();
		}
	}

	public static final class ADUIElementGroupNameFQ_Loader
	{
		private static final String PREFIX = "uieg_";

		public static ADUIElementGroupNameFQ retrieve(@NonNull final UIElementGroupId uiElementGroupId)
		{
			final ADUIElementGroupNameFQ uiElementGroupNameFQ = DB.retrieveFirstRowOrNull(
					"SELECT " + ADUIElementGroupNameFQ_Loader.sqlSelect("eg")
							+ ", " + ADUIColumnNameFQ_Loader.sqlSelect("c")
							+ ", " + ADUISectionNameFQ_Loader.sqlSelect("s")
							+ ", " + ADTabNameFQ_Loader.sqlSelect("tt")
							+ ", " + ADWindowName_Loader.sqlSelect("w")
							+ " FROM AD_UI_ElementGroup eg"
							+ " INNER JOIN AD_UI_Column c on c.AD_UI_Column_ID=eg.AD_UI_Column_ID"
							+ " INNER JOIN AD_UI_Section s on s.AD_UI_Section_ID=c.AD_UI_Section_ID"
							+ " INNER JOIN AD_Tab tt on tt.AD_Tab_ID=s.AD_Tab_ID"
							+ " INNER JOIN AD_Window w on w.AD_Window_ID=tt.AD_Window_ID "
							+ " WHERE eg.AD_UI_ElementGroup_ID=?",
					Collections.singletonList(uiElementGroupId),
					ADUIElementGroupNameFQ_Loader::retrieve
			);

			return uiElementGroupNameFQ != null
					? uiElementGroupNameFQ
					: ADUIElementGroupNameFQ.missing(uiElementGroupId);
		}

		@SuppressWarnings("SameParameterValue")
		private static String sqlSelect(final String tableAlias)
		{
			return sqlSelectColumn(tableAlias, "AD_UI_ElementGroup_ID", PREFIX)
					+ ", " + sqlSelectColumn(tableAlias, "Name", PREFIX);
		}

		private static ADUIElementGroupNameFQ retrieve(final ResultSet rs) throws SQLException
		{
			return ADUIElementGroupNameFQ.builder()
					.uiElementGroupId(UIElementGroupId.ofRepoId(rs.getInt(PREFIX + "AD_UI_ElementGroup_ID")))
					.name(rs.getString(PREFIX + "Name"))
					.uiColumnName(ADUIColumnNameFQ_Loader.retrieve(rs))
					.build();
		}
	}

	public static final class ADProcessName_Loader
	{
		private static final String PREFIX = "process_";

		public static ADProcessName retrieve(final AdProcessId adProcessId)
		{
			final ADProcessName name = DB.retrieveFirstRowOrNull(
					"SELECT " + ADProcessName_Loader.sqlSelect("p")
							+ " FROM AD_Process p"
							+ " WHERE p.AD_Process_ID=?",
					Collections.singletonList(adProcessId),
					ADProcessName_Loader::retrieve
			);

			return name != null
					? name
					: ADProcessName.missing(adProcessId);
		}

		@SuppressWarnings("SameParameterValue")
		private static String sqlSelect(final String tableAlias)
		{
			return sqlSelectColumn(tableAlias, "Value", PREFIX)
					+ ", " + sqlSelectColumn(tableAlias, "Classname", PREFIX);
		}

		private static ADProcessName retrieve(final ResultSet rs) throws SQLException
		{
			return ADProcessName.builder()
					.value(rs.getString(PREFIX + "Value"))
					.classname(rs.getString(PREFIX + "Classname"))
					.build();
		}
	}

	public static final class ADElementName_Loader
	{
		public static String retrieveColumnName(@NonNull final AdElementId adElementId)
		{
			return DB.getSQLValueStringEx(ITrx.TRXNAME_ThreadInherited,
					"SELECT ColumnName FROM AD_Element WHERE AD_Element_ID=?",
					adElementId);
		}
	}

	public static final class ADMessageName_Loader
	{
		public static String retrieveValue(@NonNull final AdMessageId adMessageId)
		{
			return DB.getSQLValueStringEx(ITrx.TRXNAME_ThreadInherited,
					"SELECT Value FROM AD_Message WHERE AD_Message_ID=?",
					adMessageId);
		}
	}

}
