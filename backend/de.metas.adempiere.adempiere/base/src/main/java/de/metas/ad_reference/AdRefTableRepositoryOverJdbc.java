package de.metas.ad_reference;

import com.google.common.base.MoreObjects;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;
import de.metas.adempiere.service.impl.TooltipType;
import de.metas.cache.CCache;
import de.metas.i18n.ExplainedOptional;
import de.metas.logging.LogManager;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.compiere.model.I_AD_Ref_Table;
import org.compiere.model.I_AD_Reference;
import org.compiere.model.I_AD_Table;
import org.compiere.util.DB;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@Repository
public class AdRefTableRepositoryOverJdbc implements AdRefTableRepository
{
	private static final Logger logger = LogManager.getLogger(AdRefTableRepositoryOverJdbc.class);

	private final CCache<Integer, TableRefInfoMap> tableRefInfoMapCache = CCache.<Integer, TableRefInfoMap>builder()
			.additionalTableNameToResetFor(I_AD_Reference.Table_Name)
			.additionalTableNameToResetFor(I_AD_Ref_Table.Table_Name)
			.build();

	@Override
	public ExplainedOptional<ADRefTable> getById(final ReferenceId referenceId)
	{
		return getMap().getById(referenceId);
	}

	@Override
	public boolean hasTableReference(@Nullable final ReferenceId referenceId)
	{
		return referenceId != null && getMap().hasTableReference(referenceId);
	}

	private TableRefInfoMap getMap()
	{
		return tableRefInfoMapCache.getOrLoad(0, this::retrieveMap);
	}

	private TableRefInfoMap retrieveMap()
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		// NOTE: this method is called when we are loading POInfoColumn,
		// so it's very important to not use POs here but just plain SQL!

		final String sql = "SELECT t.TableName,ck.ColumnName AS KeyColumn,"                // 1..2
				+ "cd.ColumnName AS DisplayColumn,rt.IsValueDisplayed,cd.IsTranslated,"    // 3..5
				+ "rt.WhereClause," // 6
				+ "rt.OrderByClause," // 7
				+ "t.AD_Window_ID," // 8
				+ "t.PO_Window_ID, " // 9
				+ "t.AD_Table_ID, cd.ColumnSQL as DisplayColumnSQL, "                    // 10..11
				+ "rt.AD_Window_ID as RT_AD_Window_ID, " // 12
				+ "t." + I_AD_Table.COLUMNNAME_IsAutocomplete // 13
				+ ", rt." + I_AD_Ref_Table.COLUMNNAME_ShowInactiveValues // 14
				+ ", r.Name as ReferenceName"
				+ ", t.TooltipType as TooltipType "
				+ ", rt.AD_Reference_ID "
				+ ", r.IsActive as Ref_IsActive"
				+ ", rt.IsActive as RefTable_IsActive"
				+ ", t.IsActive as Table_IsActive"
				// #2340 Also collect information about the ref table being a reference target
				+ " FROM AD_Ref_Table rt"
				+ " INNER JOIN AD_Reference r on (r.AD_Reference_ID=rt.AD_Reference_ID)"
				+ " INNER JOIN AD_Table t ON (rt.AD_Table_ID=t.AD_Table_ID)"
				+ " INNER JOIN AD_Column ck ON (rt.AD_Key=ck.AD_Column_ID)" // key-column
				+ " LEFT OUTER JOIN AD_Column cd ON (rt.AD_Display=cd.AD_Column_ID) " // display-column
				+ " ORDER BY rt.AD_Reference_ID";

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			rs = pstmt.executeQuery();

			final ImmutableMap.Builder<ReferenceId, ExplainedOptional<ADRefTable>> map = ImmutableMap.builder();
			while (rs.next())
			{
				final ReferenceId referenceId = ReferenceId.ofRepoId(rs.getInt("AD_Reference_ID"));
				final ExplainedOptional<ADRefTable> tableRefInfo = retrieveTableRefInfo(rs);
				map.put(referenceId, tableRefInfo);
			}

			final TableRefInfoMap result = new TableRefInfoMap(map.build());
			logger.info("Loaded {} in {}", result, stopwatch.stop());
			return result;
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	private static ExplainedOptional<ADRefTable> retrieveTableRefInfo(final ResultSet rs) throws SQLException
	{
		final ReferenceId referenceId = ReferenceId.ofRepoId(rs.getInt("AD_Reference_ID"));
		final String TableName = rs.getString(1);

		if (!StringUtils.toBoolean(rs.getString("Ref_IsActive")))
		{
			return ExplainedOptional.emptyBecause("AD_Reference with AD_Reference_ID=" + referenceId.getRepoId() + " is not active");
		}
		if (!StringUtils.toBoolean(rs.getString("RefTable_IsActive")))
		{
			return ExplainedOptional.emptyBecause("AD_Ref_Table with AD_Reference_ID=" + referenceId.getRepoId() + " is not active");
		}
		if (!StringUtils.toBoolean(rs.getString("Table_IsActive")))
		{
			return ExplainedOptional.emptyBecause("Table " + TableName + " is not active");
		}

		final String KeyColumn = rs.getString(2);
		final String DisplayColumn = rs.getString(3);
		final boolean isValueDisplayed = StringUtils.toBoolean(rs.getString(4));
		final boolean IsTranslated = StringUtils.toBoolean(rs.getString(5));
		final String WhereClause = rs.getString(6);
		final String OrderByClause = rs.getString(7);
		final AdWindowId zoomSO_Window_ID = AdWindowId.ofRepoIdOrNull(rs.getInt(8));
		final AdWindowId zoomPO_Window_ID = AdWindowId.ofRepoIdOrNull(rs.getInt(9));
		// AD_Table_ID = rs.getInt(10);
		final String displayColumnSQL = rs.getString(11);
		final AdWindowId zoomAD_Window_ID_Override = AdWindowId.ofRepoIdOrNull(rs.getInt(12));
		final boolean autoComplete = StringUtils.toBoolean(rs.getString(13));
		final boolean showInactiveValues = StringUtils.toBoolean(rs.getString(14));
		final String referenceName = rs.getString("ReferenceName");
		final TooltipType tooltipType = TooltipType.ofCode(rs.getString("TooltipType"));

		return ExplainedOptional.of(ADRefTable.builder()
				.identifier("AD_Reference[ID=" + referenceId.getRepoId() + ",Name=" + referenceName + "]")
				.tableName(TableName)
				.keyColumn(KeyColumn)
				.displayColumn(DisplayColumn)
				.valueDisplayed(isValueDisplayed)
				.displayColumnSQL(displayColumnSQL)
				.translated(IsTranslated)
				.whereClause(WhereClause)
				.orderByClause(OrderByClause)
				.zoomSO_Window_ID(zoomSO_Window_ID)
				.zoomPO_Window_ID(zoomPO_Window_ID)
				.zoomAD_Window_ID_Override(zoomAD_Window_ID_Override)
				.autoComplete(autoComplete)
				.showInactiveValues(showInactiveValues)
				.tooltipType(tooltipType)
				.build());
	}

	//
	//
	//

	private static class TableRefInfoMap
	{
		private final ImmutableMap<ReferenceId, ExplainedOptional<ADRefTable>> map;

		public TableRefInfoMap(final Map<ReferenceId, ExplainedOptional<ADRefTable>> map)
		{
			this.map = ImmutableMap.copyOf(map);
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("size", map.size())
					.toString();
		}

		public boolean hasTableReference(@NonNull final ReferenceId referenceId)
		{
			final ExplainedOptional<ADRefTable> result = map.get(referenceId);
			return result != null && result.isPresent();
		}

		public ExplainedOptional<ADRefTable> getById(@NonNull final ReferenceId referenceId)
		{
			final ExplainedOptional<ADRefTable> result = map.get(referenceId);
			return result != null
					? result
					: ExplainedOptional.emptyBecause("Unknown AD_Reference_ID=" + referenceId.getRepoId());
		}
	}
}
