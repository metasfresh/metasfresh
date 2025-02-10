/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
package org.compiere.model;

import com.google.common.base.MoreObjects;
import de.metas.ad_reference.ReferenceId;
import de.metas.common.util.CoalesceUtil;
import de.metas.i18n.Language;
import de.metas.logging.LogManager;
import de.metas.security.permissions.UIDisplayedEntityTypes;
import de.metas.util.Check;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.adempiere.ad.column.ColumnSql;
import org.adempiere.ad.element.api.AdFieldId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.ad.validationRule.AdValRuleId;
import org.adempiere.exceptions.DBException;
import org.compiere.model.FieldGroupVO.FieldGroupType;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Field Model Value Object
 *
 * @author Jorg Janke
 * @author Victor Perez , e-Evolution.SC FR [ 1757088 ] , [1877902] Implement JSR 223 Scripting APIs to Callout
 * @author Carlos Ruiz, qss FR [1877902]
 * @author Juan David Arboleda (arboleda), GlobalQSS, [ 1795398 ] Process Parameter: add display and readonly logic
 */
public class GridFieldVO implements Serializable
{
	private static final Logger logger = LogManager.getLogger(GridFieldVO.class);

	static String getSQL(final Properties ctx, final int adTabId, final boolean loadAllLanguages, final List<Object> sqlParams)
	{
		final String viewName;
		final boolean filterByLanguage;
		if (loadAllLanguages)
		{
			viewName = "AD_Field_vt";
			filterByLanguage = false;
		}
		else if (!Env.isBaseLanguage(ctx, I_AD_Field.Table_Name))
		{
			viewName = "AD_Field_vt";
			filterByLanguage = true;
		}
		else
		{
			viewName = "AD_Field_v";
			filterByLanguage = false;
		}

		final StringBuilder sql = new StringBuilder("SELECT * FROM ")
				.append(viewName)
				.append(" WHERE AD_Tab_ID=?");
		sqlParams.add(adTabId);

		// NOTE: IsActive is part of View

		// Only those fields which entity type allows to be displayed in UI
		// NOTE: instead of filtering we will, later, set IsDisplayed and IsDisplayedGrid flags.
		// sql.append(" AND (").append(EntityTypesCache.instance.getDisplayedInUIEntityTypeSQLWhereClause("FieldEntityType")).append(")");

		if (filterByLanguage)
		{
			sql.append(" AND AD_Language=?");
			sqlParams.add(Env.getAD_Language(ctx));
		}

		sql.append(" ORDER BY IsDisplayed DESC, SeqNo, AD_Field_ID");

		return sql.toString();
	}   // getSQL

	/**
	 * Create Field Value Object
	 *
	 * @param ctx          context
	 * @param WindowNo     window
	 * @param TabNo        tab
	 * @param AD_Window_ID window
	 * @param AD_Tab_ID    tab
	 * @param readOnly     r/o
	 * @param rs           resultset AD_Field_v
	 * @return MFieldVO
	 */
	@NonNull
	static GridFieldVO create(
			final Properties ctx,
			final int WindowNo,
			final int TabNo,
			final AdWindowId AD_Window_ID,
			final int AD_Tab_ID,
			final boolean readOnly,
			@NonNull final TabIncludeFiltersStrategy tabIncludeFiltersStrategy,
			final boolean loadAllLanguages,
			final boolean applyRolePermissions,
			final ResultSet rs)
	{
		final IExpressionFactory expressionFactory = Services.get(IExpressionFactory.class);

		final GridFieldVO vo = new GridFieldVO(ctx, WindowNo, TabNo, AD_Window_ID, AD_Tab_ID, readOnly, applyRolePermissions);

		String columnName = null;
		try
		{
			vo.ColumnName = rs.getString("ColumnName");
			Check.assumeNotEmpty(vo.ColumnName, "ColumnName is not empty");
			logger.debug("Creating grid field for {}", vo.ColumnName);

			String fieldGroupName = null;
			FieldGroupType fieldGroupType = null;
			boolean fieldGroupCollapsedByDefault = false;

			final GridFieldLayoutConstraints.Builder layoutConstraints = GridFieldLayoutConstraints.builder();

			vo.header = CoalesceUtil.coalesce(rs.getString("Name"), vo.ColumnName);
			vo.description = rs.getString("Description");
			vo.help = rs.getString("Help");
			if (loadAllLanguages)
			{
				final String adLanguage = rs.getString("AD_Language");
				vo.setHeader(adLanguage, vo.header);
				vo.setDescription(adLanguage, vo.description);
				vo.setHelp(adLanguage, vo.help);

				final String baseAD_Language = Language.getBaseAD_Language();
				vo.setHeader(baseAD_Language, rs.getString("Name_BaseLang"));
				vo.setDescription(baseAD_Language, rs.getString("Description_BaseLang"));
				vo.setHelp(baseAD_Language, rs.getString("Help_BaseLang"));
			}

			final ResultSetMetaData rsmd = rs.getMetaData();
			for (int i = 1, columnsCount = rsmd.getColumnCount(); i <= columnsCount; i++)
			{
				columnName = rsmd.getColumnName(i);

				if (columnName.equalsIgnoreCase("AD_Reference_ID"))
				{
					vo.displayType = rs.getInt(i);
				}
				else if (columnName.equalsIgnoreCase("AD_Column_ID"))
				{
					vo.AD_Column_ID = rs.getInt(i);
				}
				else if (columnName.equalsIgnoreCase("AD_Table_ID"))
				{
					vo.AD_Table_ID = rs.getInt(i);
				}
				else if (columnName.equalsIgnoreCase("AD_Field_ID"))
				{
					vo.AD_Field_ID = AdFieldId.ofRepoIdOrNull(rs.getInt(i));
				}
				//
				// Layout constraints
				else if (columnName.equalsIgnoreCase(I_AD_Field.COLUMNNAME_DisplayLength))
				{
					layoutConstraints.setDisplayLength(rs.getInt(i));
				}
				else if (columnName.equalsIgnoreCase(I_AD_Field.COLUMNNAME_ColumnDisplayLength))
				{
					layoutConstraints.setColumnDisplayLength(rs.getInt(i));
				}
				else if (columnName.equalsIgnoreCase(I_AD_Field.COLUMNNAME_IsSameLine))
				{
					layoutConstraints.setSameLine(DisplayType.toBoolean(rs.getString(i)));
				}
				else if (columnName.equalsIgnoreCase(I_AD_Field.COLUMNNAME_SpanX))
				{
					layoutConstraints.setSpanX(rs.getInt(i));
				}
				else if (columnName.equalsIgnoreCase(I_AD_Field.COLUMNNAME_SpanY))
				{
					layoutConstraints.setSpanY(rs.getInt(i));
				}
				//
				else if (columnName.equalsIgnoreCase(I_AD_Field.COLUMNNAME_SeqNo))
				{
					vo.setSeqNo(rs.getInt(i));
				}
				else if (columnName.equalsIgnoreCase(I_AD_Field.COLUMNNAME_SeqNoGrid))
				{
					vo.setSeqNoGrid(rs.getInt(i));
				}
				else if (columnName.equalsIgnoreCase("IsDisplayed"))
				{
					vo.IsDisplayed = "Y".equals(rs.getString(i));
				}
				else if (columnName.equalsIgnoreCase(I_AD_Field.COLUMNNAME_IsDisplayedGrid))
				{
					vo.setDisplayedGrid("Y".equals(rs.getString(i)));
				}
				else if (columnName.equalsIgnoreCase("DisplayLogic"))
				{
					vo.DisplayLogic = rs.getString(i);
				}
				else if (columnName.equalsIgnoreCase("ColorLogic"))
				{
					vo.ColorLogic = rs.getString(i);
				}
				else if (columnName.equalsIgnoreCase("DefaultValue"))
				{
					vo.DefaultValue = rs.getString(i);
				}
				else if (columnName.equalsIgnoreCase("IsMandatory"))
				{
					vo.IsMandatory = "Y".equals(rs.getString(i));
				}
				else if (columnName.equalsIgnoreCase("IsMandatoryDB"))
				{
					vo.IsMandatoryDB = "Y".equals(rs.getString(i));
				}
				else if (columnName.equalsIgnoreCase("IsReadOnly"))
				{
					vo.IsReadOnly = "Y".equals(rs.getString(i));
				}
				else if (columnName.equalsIgnoreCase("IsUpdateable"))
				{
					vo.IsUpdateable = "Y".equals(rs.getString(i));
				}
				else if (columnName.equalsIgnoreCase("IsAlwaysUpdateable"))
				{
					vo.IsAlwaysUpdateable = "Y".equals(rs.getString(i));
				}
				else if (columnName.equalsIgnoreCase("IsHeading"))
				{
					vo.IsHeading = "Y".equals(rs.getString(i));
				}
				else if (columnName.equalsIgnoreCase("IsFieldOnly"))
				{
					vo.IsFieldOnly = "Y".equals(rs.getString(i));
				}
				else if (columnName.equalsIgnoreCase("IsEncryptedField"))
				{
					vo.IsEncryptedField = "Y".equals(rs.getString(i));
				}
				else if (columnName.equalsIgnoreCase("IsEncryptedColumn"))
				{
					vo.IsEncryptedColumn = "Y".equals(rs.getString(i));
				}
				else if (columnName.equalsIgnoreCase("SortNo"))
				{
					vo.SortNo = rs.getInt(i);
				}
				else if (columnName.equalsIgnoreCase("FieldLength"))
				{
					vo.fieldLength = rs.getInt(i);
				}
				else if (columnName.equalsIgnoreCase("VFormat"))
				{
					vo.VFormat = rs.getString(i);
				}
				else if (columnName.equalsIgnoreCase("FormatPattern"))
				{
					vo.formatPattern = rs.getString(i);
				}
				else if (columnName.equalsIgnoreCase("ValueMin"))
				{
					vo.ValueMin = rs.getString(i);
				}
				else if (columnName.equalsIgnoreCase("ValueMax"))
				{
					vo.ValueMax = rs.getString(i);
				}
				else if (columnName.equalsIgnoreCase("FieldGroup"))
				{
					fieldGroupName = rs.getString(i);
				}
				else if (columnName.equalsIgnoreCase("FieldGroupType"))
				{
					fieldGroupType = FieldGroupType.forCodeOrDefault(rs.getString(i), FieldGroupType.Label);
				}
				else if (columnName.equalsIgnoreCase("IsCollapsedByDefault"))
				{
					fieldGroupCollapsedByDefault = "Y".equals(rs.getString(i));
				}
				else if (columnName.equalsIgnoreCase("IsKey"))
				{
					vo.IsKey = "Y".equals(rs.getString(i));
				}
				else if (columnName.equalsIgnoreCase("IsParent"))
				{
					vo.IsParent = "Y".equals(rs.getString(i));
				}
				else if (columnName.equalsIgnoreCase("AD_Process_ID"))
				{
					vo.AD_Process_ID = rs.getInt(i);
				}
				else if (columnName.equalsIgnoreCase("ReadOnlyLogic"))
				{
					vo.ReadOnlyLogic = rs.getString(i);
				}
				else if (columnName.equalsIgnoreCase("MandatoryLogic"))
				{
					final String mandatoryLogic = rs.getString(i);
					vo.mandatoryLogicExpr = expressionFactory.compileOrDefault(mandatoryLogic, null, ILogicExpression.class); // metas: 03093
				}
				else if (columnName.equalsIgnoreCase("ObscureType"))
				{
					vo.ObscureType = rs.getString(i);
				}
				else if (columnName.equalsIgnoreCase("AD_Reference_Value_ID"))
				{
					vo.AD_Reference_Value_ID = ReferenceId.ofRepoIdOrNull(rs.getInt(i));
				}
				else if (columnName.equalsIgnoreCase("AD_Val_Rule_ID"))
				{
					vo.AD_Val_Rule_ID = AdValRuleId.ofRepoIdOrNull(rs.getInt(i));                    // metas: 03271
				}
				else if (columnName.equalsIgnoreCase("ColumnSQL"))
				{
					vo.ColumnSQL = rs.getString(i);
				}
				else if (columnName.equalsIgnoreCase("ColumnClass"))
				{
					vo.ColumnClass = rs.getString(i);
				}
				else if (columnName.equalsIgnoreCase("Included_Tab_ID"))
				{
					vo.Included_Tab_ID = rs.getInt(i);
				}
				else if (columnName.equalsIgnoreCase("IsAutocomplete"))
				{
					vo.autocomplete = "Y".equals(rs.getString(i));
				}
				else if (columnName.equalsIgnoreCase("IncludedTabHeight"))
				{
					vo.IncludedTabHeight = rs.getInt(i);
				}
				else if (columnName.equalsIgnoreCase("IsCalculated"))
				{
					vo.IsCalculated = "Y".equals(rs.getString(i));
				}
				else if (columnName.equalsIgnoreCase("FieldEntityType"))
				{
					vo.fieldEntityType = rs.getString(i);
				}
				else if (columnName.equalsIgnoreCase(I_AD_Column.COLUMNNAME_IsUseDocSequence))
				{
					vo.useDocSequence = DisplayType.toBoolean(rs.getString(i));
				}
				else if(columnName.equalsIgnoreCase(I_AD_Field.COLUMNNAME_IsHideGridColumnIfEmpty))
				{
					vo.hideGridColumnIfEmpty = DisplayType.toBoolean(rs.getString(i));
				}
			}

			//
			vo.defaultFilterDescriptor = retrieveDefaultFilterDescriptor(rs, tabIncludeFiltersStrategy);
			vo.fieldGroup = FieldGroupVO.build(fieldGroupName, fieldGroupType, fieldGroupCollapsedByDefault);
			vo.layoutConstraints = layoutConstraints.build();
		}
		catch (final SQLException e)
		{
			throw new DBException("Exception loading GridFieldVO", e).appendParametersToMessage()
					.setParameter("AD_Window_ID", AdWindowId.toRepoId(AD_Window_ID))
					.setParameter("AD_Tab_ID", AD_Tab_ID)
					.setParameter("ColumnName", columnName);
		}

		//
		// Apply UserDef settings
		MUserDefWin.apply(vo);

		//
		// metas: tsa: if debugging display ColumnNames instead of regular name
		if (Services.get(IDeveloperModeBL.class).isEnabled())
		{
			vo.description = vo.header + " - " + vo.description;
			vo.header = vo.ColumnName;
		}

		//
		//
		vo.initFinish();
		return vo;
	}   // create

	@Nullable
	private static GridFieldDefaultFilterDescriptor retrieveDefaultFilterDescriptor(
			@NonNull final ResultSet rs,
			@NonNull final TabIncludeFiltersStrategy tabIncludeFiltersStrategy) throws SQLException
	{
		if (tabIncludeFiltersStrategy == TabIncludeFiltersStrategy.None)
		{
			return null;
		}

		final OptionalBoolean isFilterField = OptionalBoolean.ofNullableString(rs.getString(I_AD_Field.COLUMNNAME_IsFilterField));
		if (isFilterField.isFalse())
		{
			return null;
		}

		final boolean isDefaultFilterColumn;
		final boolean isFacetFilter;
		if (tabIncludeFiltersStrategy == TabIncludeFiltersStrategy.Explicit)
		{
			isDefaultFilterColumn = isFilterField.isTrue();
			isFacetFilter = isFilterField.isTrue() && StringUtils.toBoolean(rs.getString(I_AD_Column.COLUMNNAME_IsFacetFilter));
		}
		else if (tabIncludeFiltersStrategy == TabIncludeFiltersStrategy.Auto)
		{
			isDefaultFilterColumn = StringUtils.toBoolean(rs.getString(I_AD_Column.COLUMNNAME_IsSelectionColumn));
			isFacetFilter = StringUtils.toBoolean(rs.getString(I_AD_Column.COLUMNNAME_IsFacetFilter));
		}
		else
		{
			throw new AdempiereException("Unknown TabIncludeFiltersStrategy: " + tabIncludeFiltersStrategy);
		}

		if (!isDefaultFilterColumn && !isFacetFilter)
		{
			return null;
		}

		return GridFieldDefaultFilterDescriptor.builder()
				//
				.defaultFilter(isDefaultFilterColumn)
				.defaultFilterSeqNo(rs.getInt(I_AD_Column.COLUMNNAME_SelectionColumnSeqNo))
				.operator(rs.getString(I_AD_Column.COLUMNNAME_FilterOperator))
				.showFilterIncrementButtons(StringUtils.toBoolean(rs.getString(I_AD_Column.COLUMNNAME_IsShowFilterIncrementButtons)))
				.showFilterInline(StringUtils.toBoolean(rs.getString(I_AD_Column.COLUMNNAME_IsShowFilterInline)))
				.defaultValue(rs.getString(I_AD_Column.COLUMNNAME_FilterDefaultValue))
				//.adValRuleId(AdValRuleId.ofRepoIdOrNull(rs.getInt(I_AD_Column.COLUMNNAME_Filter_Val_Rule_ID)))
				//
				.facetFilter(isFacetFilter)
				.facetFilterSeqNo(rs.getInt(I_AD_Column.COLUMNNAME_FacetFilterSeqNo))
				.maxFacetsToFetch(rs.getInt(I_AD_Column.COLUMNNAME_MaxFacetsToFetch))
				//
				.build();
	}

	void loadAdditionalLanguage(final ResultSet rs) throws SQLException
	{
		final String adLanguage = rs.getString("AD_Language");
		setHeader(adLanguage, rs.getString("Name"));
		setDescription(adLanguage, rs.getString("Description"));
		setHelp(adLanguage, rs.getString("Help"));
	}

	/**
	 * Init Field for Process Parameter
	 *
	 * @param ctx      context
	 * @param WindowNo window No
	 * @param tabNo    Tab No or {@link Env#TAB_None}
	 * @param rs       result set AD_Process_Para
	 * @return MFieldVO
	 */
	public static GridFieldVO createParameter(final Properties ctx, final int WindowNo, final int tabNo, final ResultSet rs)
	{
		final AdWindowId adWindowId = null;
		final int adTabId = 0;
		final boolean tabReadOnly = false;
		final boolean applyRolePermissions = true; // because it's used only in Swing UI
		final GridFieldVO vo = new GridFieldVO(ctx, WindowNo, tabNo, adWindowId, adTabId, tabReadOnly, applyRolePermissions);
		vo.isProcess = true;
		vo.IsDisplayed = true;
		vo.isDisplayedGrid = false;
		vo.IsReadOnly = false;
		vo.IsUpdateable = true;

		try
		{
			vo.AD_Table_ID = 0;
			vo.AD_Field_ID = null; // metas
			vo.AD_Column_ID = 0; // metas-tsa: we cannot use the AD_Column_ID to store the AD_Process_Para_ID because we get inconsistencies elsewhere // rs.getInt("AD_Process_Para_ID");
			vo.ColumnName = rs.getString("ColumnName");
			vo.header = rs.getString("Name");
			vo.description = rs.getString("Description");
			vo.help = rs.getString("Help");
			vo.displayType = rs.getInt("AD_Reference_ID");
			vo.IsMandatory = "Y".equals(rs.getString("IsMandatory"));
			vo.IsMandatoryDB = vo.IsMandatory;
			vo.fieldLength = rs.getInt("FieldLength");
			vo.layoutConstraints = GridFieldLayoutConstraints.builder()
					.setDisplayLength(vo.fieldLength)
					.build();
			vo.DefaultValue = rs.getString("DefaultValue");
			vo.DefaultValue2 = rs.getString("DefaultValue2");
			vo.VFormat = rs.getString("VFormat");
			vo.formatPattern = "";
			vo.ValueMin = rs.getString("ValueMin");
			vo.ValueMax = rs.getString("ValueMax");
			vo.isRange = rs.getString("IsRange").equals("Y");
			vo.IsEncryptedField = "Y".equals(rs.getString("IsEncrypted")); // metas: tsa: US745
			//
			vo.AD_Reference_Value_ID = ReferenceId.ofRepoIdOrNull(rs.getInt("AD_Reference_Value_ID"));
			vo.autocomplete = "Y".equals(rs.getString("IsAutoComplete"));
			vo.AD_Val_Rule_ID = AdValRuleId.ofRepoIdOrNull(rs.getInt("AD_Val_Rule_ID")); // metas: 03271
			vo.ReadOnlyLogic = rs.getString("ReadOnlyLogic");
			vo.DisplayLogic = rs.getString("DisplayLogic");

			vo.fieldEntityType = rs.getString("FieldEntityType");
		}
		catch (final SQLException e)
		{
			logger.error("createParameter", e);
		}
		//
		vo.initFinish();
		if (vo.DefaultValue2 == null)
		{
			vo.DefaultValue2 = "";
		}
		return vo;
	}   // createParameter

	/**
	 * Create range "to" Parameter Field from "from" Parameter Field
	 *
	 * @param vo parameter from VO
	 */
	public static GridFieldVO createParameterTo(final GridFieldVO vo)
	{
		final GridFieldVO voTo = vo.clone(vo.ctx, vo.WindowNo, vo.TabNo, vo.adWindowId, vo.AD_Tab_ID, vo.tabReadOnly);
		voTo.isProcess = true;
		voTo.IsDisplayed = true;
		voTo.isDisplayedGrid = false;
		voTo.IsReadOnly = false;
		voTo.IsUpdateable = true;
		//
		voTo.AD_Field_ID = vo.AD_Field_ID; // metas
		voTo.AD_Table_ID = vo.AD_Table_ID;
		voTo.AD_Column_ID = vo.AD_Column_ID;    // AD_Process_Para_ID
		voTo.ColumnName = vo.ColumnName;
		voTo.header = vo.header;
		voTo.description = vo.description;
		voTo.help = vo.help;
		voTo.displayType = vo.displayType;
		voTo.IsMandatory = vo.IsMandatory;
		voTo.IsMandatoryDB = vo.IsMandatoryDB;
		voTo.fieldLength = vo.fieldLength;
		voTo.layoutConstraints = vo.layoutConstraints.copy();
		voTo.DefaultValue = vo.DefaultValue2;
		voTo.VFormat = vo.VFormat;
		voTo.formatPattern = vo.formatPattern;
		voTo.ValueMin = vo.ValueMin;
		voTo.ValueMax = vo.ValueMax;
		voTo.isRange = vo.isRange;
		//
		// Genied: For a range parameter the second field
		// lookup behaviour should match the first one.
		voTo.AD_Reference_Value_ID = vo.AD_Reference_Value_ID;
		voTo.autocomplete = vo.autocomplete;
		voTo.fieldEntityType = vo.fieldEntityType;
		voTo.useDocSequence = vo.useDocSequence;
		voTo.isHiddenFromUI = vo.isHiddenFromUI;

		voTo.initFinish();
		return voTo;
	}   // createParameter

	/**
	 * Make a standard field (Created/Updated/By)
	 *
	 * @param ctx          context
	 * @param WindowNo     window
	 * @param TabNo        tab
	 * @param AD_Window_ID window
	 * @param AD_Tab_ID    tab
	 * @param tabReadOnly  rab is r/o
	 * @param isCreated    is Created field
	 * @param isTimestamp  is the timestamp (not by)
	 * @return MFieldVO
	 */
	public static GridFieldVO createStdField(
			final Properties ctx,
			final int WindowNo,
			final int TabNo,
			final AdWindowId AD_Window_ID,
			final int AD_Tab_ID,
			final boolean tabReadOnly,
			final boolean applyRolePermissions,
			final boolean isCreated,
			final boolean isTimestamp)
	{
		GridFieldVO vo = new GridFieldVO(ctx, WindowNo, TabNo, AD_Window_ID, AD_Tab_ID, tabReadOnly, applyRolePermissions);
		vo.ColumnName = isCreated ? "Created" : "Updated";
		if (!isTimestamp)
		{
			vo.ColumnName += "By";
		}
		vo.displayType = isTimestamp ? DisplayType.DateTime : DisplayType.Table;
		if (!isTimestamp)
		{
			vo.AD_Reference_Value_ID = ReferenceId.ofRepoIdOrNull(110);        // AD_User Table Reference
		}
		vo.IsDisplayed = false;
		vo.isDisplayedGrid = false;
		vo.IsMandatory = false;
		vo.IsMandatoryDB = false;
		vo.IsReadOnly = false;
		vo.IsUpdateable = true;
		vo.initFinish();
		return vo;
	}   // initStdField

	private GridFieldVO(
			final Properties ctx,
			final int windowNo,
			final int tabNo,
			final AdWindowId adWindowId,
			final int adTabId,
			final boolean tabReadOnly,
			final boolean applyRolePermissions)
	{
		this.ctx = ctx;
		this.WindowNo = windowNo;
		this.TabNo = tabNo;
		this.adWindowId = adWindowId;
		this.AD_Tab_ID = adTabId;
		this.tabReadOnly = tabReadOnly;
		this.applyRolePermissions = applyRolePermissions;
	}   // MFieldVO

	private static final long serialVersionUID = 4385061125114436797L;

	// setCtx
	/**
	 * Context
	 * -- SETTER --
	 *  Set Context including contained elements
	 *

	 */
	@Setter @Getter private Properties ctx;
	/**
	 * Window No
	 */
	public final int WindowNo;
	/**
	 * Tab No
	 */
	@Getter public final int TabNo;
	@Getter
	private final AdWindowId adWindowId;
	/**
	 * AD_Tab_ID
	 */
	public final int AD_Tab_ID;
	/**
	 * Is the Tab Read Only
	 */
	public final boolean tabReadOnly;

	/**
	 * Is Process Parameter
	 */
	private boolean isProcess = false;
	private boolean isFormField = false;

	/**
	 * Column name
	 */
	@Setter @Getter private String ColumnName = "";
	/**
	 * Column sql
	 */
	public String ColumnSQL;

	// metas: adding column class
	/**
	 * Column class
	 */
	public String ColumnClass;
	// metas end

	/**
	 * Label
	 */
	@Getter private String header = "";
	@Getter private Map<String, String> headerTrls = null; // lazy
	/**
	 * DisplayType
	 */
	@Getter private int displayType = 0;
	/**
	 * Table ID
	 */
	@Getter private int AD_Table_ID = 0;
	/**
	 * Column ID
	 */
	@Setter @Getter private int AD_Column_ID = 0;
	/**
	 * Field ID
	 */
	@Getter private AdFieldId AD_Field_ID = null; // metas
	@Getter private GridFieldLayoutConstraints layoutConstraints = GridFieldLayoutConstraints.builder().build();
	@Getter private int seqNo = 0;
	@Setter @Getter private int seqNoGrid = 0;
	/**
	 * Displayed
	 */
	private boolean IsDisplayed = false;
	/**
	 * Displayed (grid mode)
	 */
	@Setter private boolean isDisplayedGrid = false;
	/**
	 * Indicates that the field is hidden from UI
	 *
	 * @implNote task 09504
	 */
	private boolean isHiddenFromUI = false;
	/**
	 * Dislay Logic
	 */
	private String DisplayLogic = "";
	private ILogicExpression DisplayLogicExpr; // metas: 03093
	/**
	 * Color Logic
	 */
	private String ColorLogic = "";
	private IStringExpression ColorLogicExpr = IStringExpression.NULL; // metas-2009_0021_AP1_CR045
	/**
	 * Default Value
	 */
	@Getter public String DefaultValue = "";
	/**
	 * Mandatory (in UI)
	 */
	private boolean IsMandatory = false;
	/**
	 * Mandatory (in database/model)
	 */
	private boolean IsMandatoryDB = false;
	/**
	 * Read Only
	 */
	@Setter private boolean IsReadOnly = false;
	/**
	 * Updateable
	 */
	@Setter private boolean IsUpdateable = false;
	/**
	 * Always Updateable
	 */
	private boolean IsAlwaysUpdateable = false;
	/**
	 * Heading Only
	 */
	private boolean IsHeading = false;
	/**
	 * Field Only
	 */
	@Setter private boolean IsFieldOnly = false;
	/**
	 * Display Encryption
	 */
	@Setter private boolean IsEncryptedField = false;
	/**
	 * Storage Encryption
	 */
	public boolean IsEncryptedColumn = false;
	/**
	 * Filtering info
	 */
	@Getter private GridFieldDefaultFilterDescriptor defaultFilterDescriptor;
	/**
	 * Order By
	 */
	@Getter public int SortNo = 0;
	/**
	 * Field Length
	 */
	@Getter private int fieldLength = 0;
	/**
	 * Format enforcement
	 */
	public String VFormat = "";
	@Getter private String formatPattern = "";
	/**
	 * Min. Value
	 */
	public String ValueMin = "";
	/**
	 * Max. Value
	 */
	public String ValueMax = "";
	/**
	 * Field Group
	 */
	@Getter private FieldGroupVO fieldGroup = FieldGroupVO.NULL;
	/**
	 * PK
	 */
	public boolean IsKey = false;
	/**
	 * FK
	 */
	private boolean IsParent = false;
	/**
	 * Process
	 */
	public int AD_Process_ID = 0;
	/**
	 * Description
	 */
	@Getter private String description = "";
	@Getter private Map<String, String> descriptionTrls = null; // lazy
	/**
	 * Help
	 */
	@Getter private String help = "";
	private Map<String, String> helpTrls = null; // lazy
	/**
	 * Mandatory Logic
	 */
	@Nullable
	private ILogicExpression mandatoryLogicExpr; // metas: 03093
	/**
	 * Read Only Logic
	 */
	public String ReadOnlyLogic = "";
	private ILogicExpression ReadOnlyLogicExpr; // metas: 03093
	/**
	 * Display Obscure
	 */
	public String ObscureType = null;
	/**
	 * Included Tab Height - metas-2009_0021_AP1_CR051
	 */
	public int IncludedTabHeight = 0; // metas

	private ReferenceId AD_Reference_Value_ID = null;
	@Getter private AdValRuleId AD_Val_Rule_ID = null;

	/**
	 * Process Parameter Range
	 */
	public boolean isRange = false;
	/**
	 * Process Parameter Value2
	 */
	public String DefaultValue2 = "";

	/**
	 * Lookup Value Object
	 */
	private MLookupInfo lookupInfo = null;

	// * Feature Request FR [ 1757088 ]
	@Getter public int Included_Tab_ID = 0;

	/**
	 * Autocompletion for textfields - Feature Request FR [ 1757088 ]
	 */
	@Setter @Getter private boolean autocomplete = false;

	public boolean IsCalculated = false; // metas

	private String fieldEntityType = null;

	/**
	 * -- GETTER --
	 *
	 *
	 */
	@Getter private boolean useDocSequence = false;

	private final boolean applyRolePermissions;

	@Getter private int AD_Sequence_ID = 0;

	@Getter private boolean forbidNewRecordCreation = false;
	
	@Getter private boolean hideGridColumnIfEmpty = false;

	/**
	 * Validate Fields and create LookupInfo if required
	 */
	private void initFinish()
	{
		final IExpressionFactory expressionFactory = Services.get(IExpressionFactory.class);

		// Not null fields
		if (DisplayLogic == null)
		{
			DisplayLogic = "";
		}
		DisplayLogicExpr = expressionFactory.compileOrDefault(DisplayLogic, ConstantLogicExpression.TRUE, ILogicExpression.class); // metas: 03093

		// metas-2009_0021_AP1_CR045: begin
		if (ColorLogic == null)
		{
			ColorLogic = "";
		}
		ColorLogicExpr = expressionFactory.compileOrDefault(ColorLogic, IStringExpression.NULL, IStringExpression.class);
		// metas-2009_0021_AP1_CR045: end

		if (DefaultValue == null)
		{
			DefaultValue = "";
		}

		if (description == null)
		{
			description = "";
		}

		if (help == null)
		{
			help = "";
		}

		if (ReadOnlyLogic == null)
		{
			ReadOnlyLogic = "";
		}
		ReadOnlyLogicExpr = expressionFactory.compileOrDefault(ReadOnlyLogic, ConstantLogicExpression.FALSE, ILogicExpression.class); // metas: 03093

		//
		// If EntityType is not displayed, hide this field
		if (applyRolePermissions
				&& !Check.isEmpty(fieldEntityType, true)
				&& !UIDisplayedEntityTypes.isEntityTypeDisplayedInUIOrTrueIfNull(Env.getCtx(), fieldEntityType))
		{
			this.isHiddenFromUI = true;
			this.IsDisplayed = false;
			this.isDisplayedGrid = false;
		}

		createLookupInfo(); // metas : cg: task 02354 // tsa: always create the lookupInfo
	}   // initFinish

	/**
	 * Create lookup info if the type is lookup and control the creation trough displayed param
	 */
	// metas : cg: task 02354
	private void createLookupInfo()
	{
		// Shall we create the MLookupInfo?
		if (lookupInfo != null)
		{
			return;
		}

		// Create Lookup, if not ID
		if (DisplayType.isLookup(displayType))
		{
			try
			{
				if (this.lookupLoadFromColumn)
				{
					lookupInfo = MLookupFactory.newInstance().getLookupInfo(WindowNo, AD_Column_ID, displayType);
				}
				else
				{
					final String tablename = Services.get(IADTableDAO.class).retrieveTableName(getAD_Table_ID());
					lookupInfo = MLookupFactory.newInstance().getLookupInfo(
							WindowNo,
							displayType,
							tablename,
							ColumnName,
							AD_Reference_Value_ID,
							IsParent,
							AD_Val_Rule_ID); // metas: 03271
				}
			}
			catch (Exception e)     // Cannot create Lookup
			{
				logger.warn("No LookupInfo for {}. Considering displayType=ID", ColumnName, e);
				displayType = DisplayType.ID;
				lookupInfo = null;
			}
		}
	}

	/**
	 * Clone Field.
	 * <br/>
	 * Please note that following fields are not copied:
	 * <ul>
	 * <li>{@link #isProcess} is set to false</li>
	 * </ul>
	 *
	 * @param Ctx          context
	 * @param windowNo     window no
	 * @param tabNo        tab no
	 * @param ad_Window_ID window id
	 * @param ad_Tab_ID    tab id
	 * @param TabReadOnly  r/o
	 */
	public GridFieldVO clone(
			final Properties Ctx,
			final int windowNo,
			final int tabNo,
			final AdWindowId ad_Window_ID,
			final int ad_Tab_ID,
			final boolean TabReadOnly)
	{
		final GridFieldVO clone = new GridFieldVO(Ctx, windowNo, tabNo, ad_Window_ID, ad_Tab_ID, TabReadOnly, applyRolePermissions);
		//
		clone.isProcess = false;
		clone.isFormField = false;
		// Database Fields
		clone.ColumnName = ColumnName;
		clone.ColumnSQL = ColumnSQL;
		clone.header = header;
		clone.headerTrls = headerTrls == null ? null : new HashMap<>(headerTrls);
		clone.description = description;
		clone.descriptionTrls = descriptionTrls == null ? null : new HashMap<>(descriptionTrls);
		clone.help = help;
		clone.helpTrls = helpTrls == null ? null : new HashMap<>(helpTrls);
		clone.displayType = displayType;
		clone.seqNo = seqNo;
		clone.seqNoGrid = seqNoGrid;
		clone.AD_Field_ID = AD_Field_ID; // metas
		clone.AD_Table_ID = AD_Table_ID;
		clone.AD_Column_ID = AD_Column_ID;
		clone.layoutConstraints = layoutConstraints.copy();
		clone.IsDisplayed = IsDisplayed;
		clone.isDisplayedGrid = isDisplayedGrid;
		clone.DisplayLogic = DisplayLogic;
		clone.DisplayLogicExpr = DisplayLogicExpr; // metas: 03093
		clone.ColorLogic = ColorLogic; // metas-2009_0021_AP1_CR045
		clone.ColorLogicExpr = ColorLogicExpr;
		clone.DefaultValue = DefaultValue;
		clone.IsMandatory = IsMandatory;
		clone.IsMandatoryDB = IsMandatoryDB;
		clone.IsReadOnly = IsReadOnly;
		clone.IsUpdateable = IsUpdateable;
		clone.IsAlwaysUpdateable = IsAlwaysUpdateable;
		clone.IsHeading = IsHeading;
		clone.IsFieldOnly = IsFieldOnly;
		clone.IsEncryptedField = IsEncryptedField;
		clone.IsEncryptedColumn = IsEncryptedColumn;
		clone.defaultFilterDescriptor = defaultFilterDescriptor;
		clone.autocomplete = autocomplete;
		clone.SortNo = SortNo;
		clone.fieldLength = fieldLength;
		clone.VFormat = VFormat;
		clone.ValueMin = ValueMin;
		clone.ValueMax = ValueMax;
		clone.fieldGroup = fieldGroup;
		clone.IsKey = IsKey;
		clone.IsParent = IsParent;
		// clone.Callout = Callout;
		clone.AD_Process_ID = AD_Process_ID;
		clone.ReadOnlyLogic = ReadOnlyLogic;
		clone.ReadOnlyLogicExpr = ReadOnlyLogicExpr; // metas: 03093
		clone.mandatoryLogicExpr = mandatoryLogicExpr; // metas: 03093
		clone.ObscureType = ObscureType;
		clone.Included_Tab_ID = Included_Tab_ID;
		clone.IncludedTabHeight = IncludedTabHeight; // metas-2009_0021_AP1_CR051
		// Lookup
		clone.AD_Val_Rule_ID = AD_Val_Rule_ID; // metas: 03271
		clone.AD_Reference_Value_ID = AD_Reference_Value_ID;
		clone.lookupInfo = lookupInfo == null ? null : lookupInfo.cloneIt(windowNo);

		// Process Parameter
		clone.isRange = isRange;
		clone.DefaultValue2 = DefaultValue2;

		clone.IsCalculated = IsCalculated; // metas: us215

		clone.fieldEntityType = fieldEntityType;
		clone.useDocSequence = useDocSequence;
		clone.isHiddenFromUI = isHiddenFromUI;

		clone.AD_Sequence_ID = AD_Sequence_ID;
		clone.forbidNewRecordCreation = forbidNewRecordCreation;
		clone.hideGridColumnIfEmpty = hideGridColumnIfEmpty;

		return clone;
	}    // clone

	public GridFieldVO copy()
	{
		return clone(ctx, WindowNo, TabNo, adWindowId, AD_Tab_ID, tabReadOnly);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("ColumnName", ColumnName)
				.add("AD_Column_ID", AD_Column_ID)
				.toString();
	}

	public boolean isParentLink()
	{
		return IsParent;
	}

	// NOTE: not setting to package level because we call it from zkwebui
	public void setDisplayType(final int displayType)
	{
		if (this.displayType == displayType)
		{
			return;
		}
		this.displayType = displayType;
		this.lookupInfo = null; // reset lookup info
	}

	public ILogicExpression getDisplayLogic()
	{
		return DisplayLogicExpr;
	}

	// NOTE: temporary here to let org.compiere.model.MUserDefField.apply(GridFieldVO) work
	protected void setDisplayLogic(final String displayLogicStr)
	{
		final IExpressionFactory expressionFactory = Services.get(IExpressionFactory.class);
		this.DisplayLogic = displayLogicStr;

		// Not null fields
		if (DisplayLogic == null)
		{
			DisplayLogic = "";
		}
		DisplayLogicExpr = expressionFactory.compileOrDefault(DisplayLogic, ConstantLogicExpression.TRUE, ILogicExpression.class); // metas: 03093
	}

	public ILogicExpression getReadOnlyLogic()
	{
		return ReadOnlyLogicExpr;
	}

	public ILogicExpression getMandatoryLogic()
	{
		return mandatoryLogicExpr != null ? mandatoryLogicExpr : ConstantLogicExpression.FALSE;
	}

	public boolean isMandatoryLogicExpression()
	{
		return mandatoryLogicExpr != null;
	}

	public IStringExpression getColorLogic()
	{
		return ColorLogicExpr;
	}

	MLookupInfo getLookupInfo()
	{
		if (lookupInfo == null)
		{
			createLookupInfo();
		}
		return this.lookupInfo;
	}

	public void setIsDisplayed(final boolean displayed)
	{
		if (this.IsDisplayed == displayed)
		{
			return;
		}
		this.IsDisplayed = displayed;
		// this.lookupInfo = null; // no need to reset lookup info
	}

	public boolean isDisplayed()
	{
		return this.IsDisplayed;
	}

	public boolean isDisplayed(final GridTabLayoutMode tabLayoutMode)
	{
		if (tabLayoutMode == GridTabLayoutMode.SingleRowLayout)
		{
			return this.IsDisplayed;
		}
		else if (tabLayoutMode == GridTabLayoutMode.Grid)
		{
			return this.isDisplayedGrid;
		}
		else
		{
			throw new IllegalArgumentException("Unknown GridTabLayoutMode: " + tabLayoutMode);
		}
	}

	public void setAD_Reference_Value_ID(@Nullable final ReferenceId AD_Reference_Value_ID)
	{
		if (ReferenceId.equals(this.AD_Reference_Value_ID, AD_Reference_Value_ID))
		{
			return;
		}

		this.AD_Reference_Value_ID = AD_Reference_Value_ID;
		this.lookupInfo = null; // reset lookup info
	}

	private boolean lookupLoadFromColumn = false;

	public void setLookupLoadFromColumn(final boolean lookupLoadFromColumn)
	{
		if (this.lookupLoadFromColumn == lookupLoadFromColumn)
		{
			return;
		}
		this.lookupLoadFromColumn = lookupLoadFromColumn;
		this.lookupInfo = null; // reset
	}

	private void setSeqNo(final int seqNo)
	{
		this.seqNo = seqNo;
	}

	public boolean isDisplayedGrid()
	{
		return isDisplayedGrid;
	}

	public boolean isProcessParameter()
	{
		return isProcess;
	}

	/**
	 * @return true if this is a custom form field
	 */
	public boolean isFormField()
	{
		return isFormField;
	}

	public void setFormField(final boolean isFormField)
	{
		this.isFormField = isFormField;
	}

	public boolean isCalculated()
	{
		return IsCalculated;
	}

	public boolean isFieldOnly()
	{
		return IsFieldOnly;
	}

	public void setIsHeadingOnly(final boolean isHeading)
	{
		this.IsHeading = isHeading;
	}

	public boolean isHeadingOnly()
	{
		return IsHeading;
	}

	/**
	 * @return true if this field shall be hidden from UI; in this case {@link #isDisplayed()} and {@link #isDisplayedGrid()} will also return false.
	 * @implSpec Task 09504
	 */
	public boolean isHiddenFromUI()
	{
		return this.isHiddenFromUI;
	}

	/**
	 * @return true if it's mandatory in UI
	 */
	public boolean isMandatory()
	{
		return IsMandatory;
	}

	public void setMandatory(final boolean mandatory)
	{
		this.IsMandatory = mandatory;
	}

	/**
	 * @return true if it's mandatory in database
	 */
	public boolean isMandatoryDB()
	{
		return IsMandatoryDB;
	}

	public void setHeader(final String header)
	{
		this.header = header;
	}

	private void setHeader(final String adLanguage, final String headerTrl)
	{
		Check.assumeNotEmpty(adLanguage, "adLanguage is not empty");

		if (headerTrl == null)
		{
			return;
		}

		if (headerTrls == null)
		{
			headerTrls = new HashMap<>();
		}
		headerTrls.put(adLanguage, headerTrl);
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}

	private void setDescription(final String adLanguage, final String descriptionTrl)
	{
		Check.assumeNotEmpty(adLanguage, "adLanguage is not empty");

		if (descriptionTrl == null)
		{
			return;
		}

		if (descriptionTrls == null)
		{
			descriptionTrls = new HashMap<>();
		}
		descriptionTrls.put(adLanguage, descriptionTrl);
	}

	public void setHelp(final String help)
	{
		this.help = help;
	}

	private void setHelp(final String adLanguage, final String helpTrl)
	{
		Check.assumeNotEmpty(adLanguage, "adLanguage is not empty");
		if (helpTrls == null)
		{
			helpTrls = new HashMap<>();
		}
		helpTrls.put(adLanguage, helpTrl);
	}

	@Nullable
	public ReferenceId getAD_Reference_Value_ID()
	{
		return AD_Reference_Value_ID;
	}

	/**
	 * Get Column Name or SQL .. with/without AS
	 *
	 * @param withAS include AS ColumnName for virtual columns in select statements
	 * @return column name
	 */
	public String getColumnSQL(final boolean withAS)
	{
		// metas
		if (ColumnClass != null)
		{
			return "NULL";
		}
		// metas end
		if (ColumnSQL != null && !ColumnSQL.isEmpty())
		{
			if (withAS)
			{
				return ColumnSQL + " AS " + ColumnName;
			}
			else
			{
				return ColumnSQL;
			}
		}
		return ColumnName;
	}    // getColumnSQL

	public ColumnSql getColumnSql(@NonNull final String ctxTableName)
	{
		return ColumnSql.ofSql(getColumnSQL(false), ctxTableName);
	}

	/**
	 * Is Virtual Column
	 *
	 * @return column is virtual
	 */
	public boolean isVirtualColumn()
	{
		return (ColumnSQL != null && !ColumnSQL.isEmpty())
				|| (ColumnClass != null && !ColumnClass.isEmpty());
	}    // isColumnVirtual

	public boolean isReadOnly()
	{
		return IsReadOnly;
	}

	public boolean isUpdateable()
	{
		return IsUpdateable;
	}

	public boolean isAlwaysUpdateable()
	{
		return IsAlwaysUpdateable;
	}

	public boolean isKey()
	{
		return IsKey;
	}

	public boolean isEncryptedField()
	{
		return IsEncryptedField;
	}

	public boolean isEncryptedColumn()
	{
		return IsEncryptedColumn;
	}

	public boolean isSelectionColumn()
	{
		return defaultFilterDescriptor != null;
	}

}
