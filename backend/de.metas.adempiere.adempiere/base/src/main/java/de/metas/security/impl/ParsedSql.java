package de.metas.security.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;

import java.util.List;
import java.util.StringTokenizer;

@ToString
@EqualsAndHashCode
public final class ParsedSql
{
	public static ParsedSql parse(@NonNull final String sql)
	{
		final ImmutableList<SqlSelect> sqlSelects = parseSqlSelectsList(sql);
		final int mainSqlIndex = findMainSqlSelectIndex(sqlSelects);

		return new ParsedSql(sqlSelects, mainSqlIndex);
	}

	private static final Logger logger = LogManager.getLogger(ParsedSql.class);

	private static final String FROM = " FROM ";
	private static final String FROM_LOWERCASE = " from ";
	private static final int FROM_LENGTH = FROM.length();
	private static final String WHERE = " WHERE ";
	private static final String WHERE_LOWERCASE = " where ";
	private static final String ON = " ON ";
	private static final String ON_LOWERCASE = " on ";

	@VisibleForTesting
	@Getter(AccessLevel.PACKAGE)
	private final ImmutableList<SqlSelect> sqlSelects;
	@VisibleForTesting
	@Getter(AccessLevel.PACKAGE)
	private final int mainSqlIndex;

	@Builder
	private ParsedSql(
			@NonNull @Singular final ImmutableList<SqlSelect> sqlSelects,
			final int mainSqlIndex)
	{
		this.sqlSelects = sqlSelects;
		this.mainSqlIndex = mainSqlIndex;
	}

	private static ImmutableList<SqlSelect> parseSqlSelectsList(final String sql)
	{
		final String sqlOriginal = normalizeOriginalSql(sql);
		if (sqlOriginal == null || sqlOriginal.isEmpty())
		{
			throw new AdempiereException("No SQL");
		}

		return extractAllSqlSelectStatements(sqlOriginal)
				.stream()
				.map(sqlSelectQuery -> parseSqlSelect(sqlSelectQuery))
				.collect(ImmutableList.toImmutableList());
	}

	@VisibleForTesting
	static final String normalizeOriginalSql(final String sql)
	{
		String sqlNormalized = sql;

		final int idxFrom = sqlNormalized.indexOf("\nFROM ");
		if (idxFrom >= 0)
		{
			sqlNormalized = sqlNormalized.replace("\nFROM ", FROM);
		}

		final int idxWhere = sqlNormalized.indexOf("\nWHERE ");
		if (idxWhere >= 0)
		{
			sqlNormalized = sqlNormalized.replace("\nWHERE ", WHERE);
		}

		return sqlNormalized;
	}

	private static ImmutableList<String> extractAllSqlSelectStatements(final String sqlOriginal)
	{
		ImmutableList<String> sqlIn = ImmutableList.of(sqlOriginal);
		ImmutableList<String> sqlOut;
		try
		{
			sqlOut = extractSubSqlStatements(sqlIn);
		}
		catch (final Exception e)
		{
			logger.error(sqlOriginal, e);
			throw new IllegalArgumentException(sqlOriginal);
		}

		// a sub-query was found
		while (sqlIn.size() != sqlOut.size())
		{
			sqlIn = sqlOut;
			try
			{
				sqlOut = extractSubSqlStatements(sqlIn);
			}
			catch (final Exception e)
			{
				logger.error(sqlOriginal, e);
				throw new IllegalArgumentException(sqlOut.size() + ": " + sqlOriginal);
			}
		}

		return sqlOut;
	}

	/**
	 * Get Sub SQL of sql statements
	 */
	private static ImmutableList<String> extractSubSqlStatements(final List<String> sqlIn)
	{
		final ImmutableList.Builder<String> result = ImmutableList.builder();
		for (String sql : sqlIn)
		{
			int index = sql.indexOf("(SELECT ", 7);
			while (index != -1)
			{
				int endIndex = index + 1;
				int parenthesisLevel = 0;
				// search for the end of the sql
				while (endIndex++ < sql.length())
				{
					final char c = sql.charAt(endIndex);
					if (c == ')')
					{
						if (parenthesisLevel == 0)
						{
							break;
						}
						else
						{
							parenthesisLevel--;
						}
					}
					else if (c == '(')
					{
						parenthesisLevel++;
					}
				}
				final String subSQL = sql.substring(index, endIndex + 1);
				result.add(subSQL);
				// remove inner SQL (##)
				sql = sql.substring(0, index + 1) + "##" + sql.substring(endIndex);
				index = sql.indexOf("(SELECT ", 7);
			}
			result.add(sql);    // last SQL
		}

		return result.build();
	}

	private static SqlSelect parseSqlSelect(final String sql)
	{
		return new SqlSelect(sql, parseTableInfoList(sql));
	}

	private static ImmutableList<TableNameAndAlias> parseTableInfoList(String sql)
	{
		final ImmutableList.Builder<TableNameAndAlias> result = ImmutableList.builder();

		sql = sql.trim();

		// remove ()
		if (sql.startsWith("(") && sql.endsWith(")"))
		{
			sql = sql.substring(1, sql.length() - 1);
		}

		// Lower case: IS DISTINCT FROM, IS NOT DISTINCT FROM
		sql = sql.replace(" IS DISTINCT FROM ", " is distinct from ");
		sql = sql.replace(" IS NOT DISTINCT FROM ", " is not distinct from ");

		int fromIndex = sql.indexOf(FROM);
		if (fromIndex != sql.lastIndexOf(FROM))
		{
			logger.warn("More than one FROM clause: {}", sql);
		}

		while (fromIndex != -1)
		{
			String from = sql.substring(fromIndex + FROM_LENGTH);
			int index = from.lastIndexOf(WHERE);    // end at where
			if (index != -1)
			{
				from = from.substring(0, index);
			}
			from = from.replaceAll("[\r\n\t ]+AS[\r\n\t ]+", " ");
			from = from.replaceAll("[\r\n\t ]+as[\r\n\t ]+", " ");
			from = from.replaceAll("[\r\n\t ]+INNER[\r\n\t ]+JOIN[\r\n\t ]+", ", ");
			from = from.replaceAll("[\r\n\t ]+LEFT[\r\n\t ]+OUTER[\r\n\t ]+JOIN[\r\n\t ]+", ", ");
			from = from.replaceAll("[\r\n\t ]+RIGHT[\r\n\t ]+OUTER[\r\n\t ]+JOIN[\r\n\t ]+", ", ");
			from = from.replaceAll("[\r\n\t ]+FULL[\r\n\t ]+JOIN[\r\n\t ]+", ", ");
			from = from.replaceAll("[\r\n\t ]+[Oo][Nn][\r\n\t ]+", ON); // teo_sarca, BF [ 2840157 ]
			// Remove ON clause - assumes that there is no IN () in the clause
			index = from.indexOf(ON);
			while (index != -1)
			{
				// metas kh: us135: search closing bracket only behind " ON ".
				// Note: if we don't start the search for ")" *after* the " ON ", we might find the wrong bracket,
				// e.g. with "... INNER JOIN (select ... from ... ) ON (...) ", we would find the first ")" instead of
				// the second one (without the index paramter)
				int indexClose = from.indexOf(')', index + 4); // does not catch "IN (1,2)" in ON
				// metas kh: us135 end.
				final int indexNextOn = from.indexOf(ON, index + 4);
				if (indexNextOn != -1)
				{
					indexClose = from.lastIndexOf(')', indexNextOn);
				}
				if (indexClose != -1)
				{
					if (index > indexClose)
					{
						throw new IllegalStateException("Could not remove (index=" + index + " > indexClose=" + indexClose + ") - " + from);
					}
					from = from.substring(0, index) + from.substring(indexClose + 1);
				}
				else
				{
					logger.error("Could not remove ON " + from);
					break;
				}
				index = from.indexOf(ON);
			}

			// log.debug("getTableInfo - " + from);
			final StringTokenizer tableST = new StringTokenizer(from, ",");
			while (tableST.hasMoreTokens())
			{
				final String tableAndSynonym = tableST.nextToken().trim();
				final StringTokenizer tableAndSynonymST = new StringTokenizer(tableAndSynonym, " \r\n\t"); // teo_sarca [ 1652623 ] AccessSqlParser.getTableInfo(String) - tablename parsing bug

				final TableNameAndAlias tableInfo;
				if (tableAndSynonymST.countTokens() > 1)
				{
					tableInfo = TableNameAndAlias.ofTableNameAndAlias(tableAndSynonymST.nextToken(), tableAndSynonymST.nextToken());
				}
				else
				{
					tableInfo = TableNameAndAlias.ofTableName(tableAndSynonym);
				}

				result.add(tableInfo);
			}
			//
			sql = sql.substring(0, fromIndex);
			fromIndex = sql.lastIndexOf(FROM);
		}

		return result.build();
	}

	private static int findMainSqlSelectIndex(final List<SqlSelect> sqlSelects)
	{
		final int sqlSelectsCount = sqlSelects.size();
		if (sqlSelectsCount == 0)
		{
			// TODO: shall we throw ex?
			return -1;
		}
		else if (sqlSelectsCount == 1)
		{
			return 0;
		}
		else
		{
			for (int i = sqlSelectsCount - 1; i >= 0; i--)
			{
				final SqlSelect sqlSelect = sqlSelects.get(i);
				if (sqlSelect.getSql().charAt(0) != '(')
				{
					return i;
				}
			}

			return -1;
		}

	}

	public SqlSelect getMainSqlSelect()
	{
		return sqlSelects.get(mainSqlIndex);
	}

	/**
	 * Transform the key words WHERE, FROM and ON in lowercase.
	 * TODO: Delete this logic when we get rid of the workaround in org.compiere.model.AccessSqlParser.getTableInfo(String)
	 *
	 * @param whereClause
	 * @return
	 */
	public static String rewriteWhereClauseWithLowercaseKeyWords(final String whereClause)
	{
		return whereClause.replaceAll("\\s", " ")
				.replaceAll(WHERE, WHERE_LOWERCASE)
				.replaceAll(FROM, FROM_LOWERCASE)
				.replaceAll(ON, ON_LOWERCASE);
	}

	@Value
	public static class TableNameAndAlias
	{
		public static TableNameAndAlias ofTableNameAndAlias(final String tableName, final String alias)
		{
			return new TableNameAndAlias(tableName, alias);
		}

		public static TableNameAndAlias ofTableName(final String tableName)
		{
			final String synonym = "";
			return new TableNameAndAlias(tableName, synonym);
		}

		private final String tableName;
		private final String alias;

		private TableNameAndAlias(@NonNull final String tableName, final String alias)
		{
			this.tableName = tableName;
			this.alias = alias != null ? alias : "";
		}

		public String getAliasOrTableName()
		{
			return !alias.isEmpty() ? alias : tableName;
		}

		public boolean isTrlTable()
		{
			return tableName.toUpperCase().endsWith("_TRL");
		}
	}

	@Value
	public static final class SqlSelect
	{
		private final String sql;
		private final ImmutableList<TableNameAndAlias> tableNameAndAliases;

		@Builder
		private SqlSelect(
				@NonNull final String sql,
				@NonNull @Singular final ImmutableList<TableNameAndAlias> tableNameAndAliases)
		{
			this.sql = sql;
			this.tableNameAndAliases = tableNameAndAliases;
		}

		public boolean hasWhereClause()
		{
			return sql.indexOf(" WHERE ") >= 0;
		}

		public String getFirstTableAliasOrTableName()
		{
			if (tableNameAndAliases.isEmpty()) // TODO check if we still need this check!
			{
				return "";
			}

			return tableNameAndAliases.get(0).getAliasOrTableName();
		}

		public String getFirstTableNameOrEmpty()
		{
			if (tableNameAndAliases.isEmpty())
			{
				return "";
			}

			return tableNameAndAliases.get(0).getTableName();
		}
	}
}
