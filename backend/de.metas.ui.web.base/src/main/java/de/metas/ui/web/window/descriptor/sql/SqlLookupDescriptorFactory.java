package de.metas.ui.web.window.descriptor.sql;

import de.metas.ad_reference.ADReferenceService;
import de.metas.ad_reference.ReferenceId;
import de.metas.adempiere.service.impl.TooltipType;
import de.metas.common.util.CoalesceUtil;
import de.metas.security.permissions.Access;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider;
import de.metas.ui.web.window.descriptor.factory.standard.DescriptorsFactoryHelper;
import de.metas.ui.web.window.model.lookup.LookupDataSourceContext;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.ConstantStringExpression;
import org.adempiere.ad.table.api.ColumnNameFQ;
import org.adempiere.ad.table.api.TableName;
import org.adempiere.ad.validationRule.AdValRuleId;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MLookupInfo;
import org.compiere.util.DisplayType;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;

final class SqlLookupDescriptorFactory
{
	static SqlLookupDescriptorFactory newInstance() {return new SqlLookupDescriptorFactory();}

	private static final int WINDOWNO_Dummy = 99999;

	private ADReferenceService adReferenceService;
	@Nullable private String ctxColumnName;
	@Nullable private String ctxTableName;

	private ReferenceId displayType;
	private ReferenceId AD_Reference_Value_ID = null;

	private final CompositeSqlLookupFilterBuilder filtersBuilder = new CompositeSqlLookupFilterBuilder();
	private Access requiredAccess = null;

	//
	// Built/prepared values (i.e. computed on build)
	private TableName lookupTableName;
	private SqlForFetchingLookups sqlForFetchingExpression;
	private SqlForFetchingLookupById sqlForFetchingLookupByIdExpression;

	@Nullable private AdWindowId zoomIntoAdWindowId = null;
	@NonNull private TooltipType tooltipType = TooltipType.DEFAULT;

	@Nullable private Integer pageLength;

	private SqlLookupDescriptorFactory()
	{
	}

	SqlLookupDescriptor build()
	{
		final int displayType = this.displayType.getRepoId();
		if (displayType == DisplayType.PAttribute && AD_Reference_Value_ID == null)
		{
			setSqlExpressions_PAttribute();
		}
		else
		{
			final MLookupInfo lookupInfo = newLookupFactory()
					.getLookupInfo(
							WINDOWNO_Dummy,
							displayType,
							ctxTableName,
							ctxColumnName,
							AD_Reference_Value_ID,
							false, // IsParent - not relevant
							(AdValRuleId)null // AD_Val_Rule_ID
					);
			if (lookupInfo == null)
			{
				// shall not happen
				throw new AdempiereException("No lookup info for " + ctxTableName + "." + ctxColumnName);
			}

			final MLookupInfo.SqlQuery lookupInfoSqlQuery = lookupInfo.getSqlQuery();
			zoomIntoAdWindowId = lookupInfo.getZoomAD_Window_ID_Override();
			tooltipType = CoalesceUtil.coalesceNotNull(lookupInfo.getTooltipType(), TooltipType.DEFAULT);

			filtersBuilder.addFilter(lookupInfo.getValidationRule(), LookupDescriptorProvider.LookupScope.DocumentField); // i.e. AD_Ref_Table.WhereClause (if the where clause contains context var)
			setSqlExpressions(lookupInfoSqlQuery);

		}

		return SqlLookupDescriptor.builder()
				.filters(getFilters().withOnlyScope(LookupDescriptorProvider.LookupScope.DocumentField))
				.highVolume(computeIsHighVolume(this.displayType))
				.lookupSourceType(DescriptorsFactoryHelper.extractLookupSource(this.displayType, AD_Reference_Value_ID))
				.lookupTableName(this.lookupTableName)
				.sqlForFetchingLookupByIdExpression(sqlForFetchingLookupByIdExpression)
				.sqlForFetchingExpression(sqlForFetchingExpression)
				.tooltipType(this.tooltipType)
				.zoomIntoWindowId(WindowId.ofNullable(zoomIntoAdWindowId))
				.pageLength(pageLength)
				.build();
	}

	private void setSqlExpressions(final MLookupInfo.SqlQuery lookupInfoSqlQuery)
	{
		this.lookupTableName = lookupInfoSqlQuery.getTableName();
		this.filtersBuilder.setLookupTableName(this.lookupTableName);

		final Access requiredAccess = !lookupInfoSqlQuery.isSecurityDisabled() ? getRequiredAccess(this.lookupTableName) : null;

		this.sqlForFetchingExpression = SqlForFetchingLookups.ofLookupInfoSqlFrom(
				lookupInfoSqlQuery,
				requiredAccess,
				isOrderByLevenshteinDistance()
		);
		this.sqlForFetchingLookupByIdExpression = SqlForFetchingLookupById.ofLookupInfoSqlFrom(lookupInfoSqlQuery);
	}

	private void setSqlExpressions_PAttribute()
	{
		final TableName lookupTableName = TableName.ofString(I_M_AttributeSetInstance.Table_Name);
		final ColumnNameFQ keyColumnNameFQ = ColumnNameFQ.ofTableAndColumnName(lookupTableName, I_M_AttributeSetInstance.COLUMNNAME_M_AttributeSetInstance_ID);
		final ColumnNameFQ displayColumnSql = ColumnNameFQ.ofTableAndColumnName(lookupTableName, I_M_AttributeSetInstance.COLUMNNAME_Description);

		this.lookupTableName = lookupTableName;
		this.filtersBuilder.setLookupTableName(this.lookupTableName);

		//
		// Set the SQLs
		this.sqlForFetchingExpression = SqlForFetchingLookups.builder()
				.tableName(lookupTableName)
				.sqlSelectFromPart(IStringExpression.composer()
						.append("SELECT ")
						// IMPORTANT: respect the column indices from MLookupInfo.SqlSelectFrom.COLUMNINDEX_* !!!
						.append(" ").append(keyColumnNameFQ.getAsString()) // 1 - Key
						.append(", NULL") // 2 - Value
						.append(",").append(displayColumnSql.getAsString()) // 3 - DisplayName
						.append(", M_AttributeSetInstance.IsActive") // 4 - IsActive
						.append(", NULL") // 5 - Description
						.append(" FROM ").append(lookupTableName.getAsString())
						.build()
				)
				.sqlWhere_Main(IStringExpression.composer()
						.append(" /* filter */ ").append("(").append(displayColumnSql.getAsString()).append(") ILIKE ").append(LookupDataSourceContext.PARAM_FilterSql)
						.build())
				.sqlOrderBy(IStringExpression.composer()
						.append(I_M_AttributeSetInstance.COLUMNNAME_Description)
						.build())
				.requiredAccess(getRequiredAccess(lookupTableName))
				.entityTypeIndex(null)
				.build();
		sqlForFetchingLookupByIdExpression = SqlForFetchingLookupById.builder()
				.keyColumnNameFQ(keyColumnNameFQ)
				.numericKey(true)
				.displayColumn(ConstantStringExpression.of(displayColumnSql.getAsString()))
				.sqlFrom(ConstantStringExpression.of(lookupTableName.getAsString()))
				.build();
	}

	private boolean isOrderByLevenshteinDistance()
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		return sysConfigBL.getBooleanValue("webui.lookup.orderBy.levenshtein", false);
	}

	public SqlLookupDescriptorFactory setADReferenceService(@NonNull final ADReferenceService adReferenceService)
	{
		this.adReferenceService = adReferenceService;
		return this;
	}

	public ADReferenceService getADReferenceService()
	{
		return adReferenceService;
	}

	private MLookupFactory newLookupFactory()
	{
		return MLookupFactory.builder()
				.adReferenceService(adReferenceService)
				.build();
	}

	public SqlLookupDescriptorFactory setCtxColumnName(@Nullable final String ctxColumnName)
	{
		this.ctxColumnName = ctxColumnName;
		this.filtersBuilder.setCtxColumnName(this.ctxColumnName);
		return this;
	}

	public SqlLookupDescriptorFactory setCtxTableName(@Nullable final String ctxTableName)
	{
		this.ctxTableName = ctxTableName;
		this.filtersBuilder.setCtxTableName(ctxTableName);
		return this;
	}

	public SqlLookupDescriptorFactory setDisplayType(final ReferenceId displayType)
	{
		this.displayType = displayType;
		this.filtersBuilder.setDisplayType(ReferenceId.toRepoId(displayType));
		return this;
	}

	public SqlLookupDescriptorFactory setAD_Reference_Value_ID(final ReferenceId AD_Reference_Value_ID)
	{
		this.AD_Reference_Value_ID = AD_Reference_Value_ID;
		return this;
	}

	public SqlLookupDescriptorFactory setAdValRuleIds(@NonNull final Map<LookupDescriptorProvider.LookupScope, AdValRuleId> adValRuleIds)
	{
		this.filtersBuilder.setAdValRuleIds(adValRuleIds);
		return this;
	}

	private CompositeSqlLookupFilter getFilters()
	{
		return filtersBuilder.getOrBuild();
	}

	private static boolean computeIsHighVolume(@NonNull final ReferenceId diplayType)
	{
		final int displayTypeInt = diplayType.getRepoId();
		return DisplayType.TableDir != displayTypeInt && DisplayType.Table != displayTypeInt && DisplayType.List != displayTypeInt && DisplayType.Button != displayTypeInt;
	}

	/**
	 * Advice the lookup to show all records on which current user has at least read only access
	 */
	public SqlLookupDescriptorFactory setReadOnlyAccess()
	{
		this.requiredAccess = Access.READ;
		return this;
	}

	private Access getRequiredAccess(@NonNull final TableName tableName)
	{
		if (requiredAccess != null)
		{
			return requiredAccess;
		}

		// AD_Client_ID/AD_Org_ID (security fields): shall display only those entries on which current user has read-write access
		if (I_AD_Client.Table_Name.equals(tableName.getAsString())
				|| I_AD_Org.Table_Name.equals(tableName.getAsString()))
		{
			return Access.WRITE;
		}

		// Default: all entries on which current user has at least readonly access
		return Access.READ;
	}

	SqlLookupDescriptorFactory addValidationRules(final Collection<IValidationRule> validationRules)
	{
		this.filtersBuilder.addFilter(validationRules, null);
		return this;
	}

	SqlLookupDescriptorFactory setPageLength(final Integer pageLength)
	{
		this.pageLength = pageLength;
		return this;
	}
}
