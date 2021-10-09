/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.i18n.AdMessageId;
import de.metas.i18n.Language;
import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.security.IUserRolePermissions;
import de.metas.security.TableAccessLevel;
import de.metas.security.permissions.Access;
import de.metas.security.permissions.UIDisplayedEntityTypes;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Getter;

/**
 *  Model Tab Value Object
 *
 *  @author Jorg Janke
 *  @version  $Id: GridTabVO.java,v 1.4 2006/07/30 00:58:38 jjanke Exp $
 */
public class GridTabVO implements Evaluatee, Serializable
{
	private static final long serialVersionUID = -1425513230093430761L;

	public static final int MAIN_TabNo = 0;

	/**************************************************************************
	 *	Create MTab VO, ordered by <code>SeqNo</code>.
	 *
	 *  @param wVO value object
	 *  @param TabNo tab no
	 *	@param rs ResultSet from AD_Tab_v
	 *	@param isRO true if window is r/o
	 *  @param onlyCurrentRows if true query is limited to not processed records
	 *  @return TabVO
	 */
	static GridTabVO create (final GridWindowVO wVO, final int TabNo, final ResultSet rs, final boolean isRO, final boolean onlyCurrentRows)
	{
		logger.debug("TabNo={}", TabNo);

		GridTabVO vo = new GridTabVO (wVO.getCtx(), wVO.getWindowNo(), TabNo, wVO.isLoadAllLanguages(), wVO.isApplyRolePermissions());
		vo.adWindowId = wVO.getAdWindowId();
		//
		if (!loadTabDetails(vo, rs))
		{
			wVO.addLoadErrorMessage(vo.getLoadErrorMessage(), false); // metas: 01934
			return null;
		}

		if (isRO)
		{
			logger.debug("Tab is ReadOnly");
			vo.IsReadOnly = true;
		}
		vo.onlyCurrentRows = onlyCurrentRows;

		//  Create Fields
		if (vo.IsSortTab)
		{
			vo._fields = ImmutableList.of();
		}
		wVO.addLoadErrorMessage(vo.getLoadErrorMessage(), false); // metas: 01934
		return vo;
	}	//	create

	/**
	 * 	Load Tab Details from rs into vo
	 * 	@param vo Tab value object
	 *	@param rs ResultSet from AD_Tab_v/t
	 * 	@return true if read ok
	 */
	private static boolean loadTabDetails (final GridTabVO vo, final ResultSet rs)
	{
		boolean showTrl = "Y".equals(Env.getContext(vo.ctx, "#ShowTrl"));
		final boolean showAcct = true; // "Y".equals(Env.getContext(vo.ctx, Env.CTXNAME_ShowAcct));
		final boolean showAdvanced = "Y".equals(Env.getContext(vo.ctx, "#ShowAdvanced"));
		final boolean loadAllLanguages = vo.loadAllLanguages;

		try
		{
			vo.adTabId = AdTabId.ofRepoId(rs.getInt("AD_Tab_ID"));
			Env.setContext(vo.ctx, vo.WindowNo, vo.TabNo, GridTab.CTX_AD_Tab_ID, String.valueOf(vo.adTabId.getRepoId()));

			vo.templateTabId = AdTabId.ofRepoIdOrNull(rs.getInt(I_AD_Tab.COLUMNNAME_Template_Tab_ID));

			vo.internalName = rs.getString("InternalName");

			vo.name = rs.getString("Name");
			Env.setContext(vo.ctx, vo.WindowNo, vo.TabNo, GridTab.CTX_Name, vo.name);
			//
			vo.description = rs.getString("Description");
			if (vo.description == null)
			{
				vo.description = "";
			}
			vo.help = rs.getString("Help");
			if (vo.help == null)
			{
				vo.help = "";
			}
			vo.CommitWarning = rs.getString("CommitWarning");
			if (vo.CommitWarning == null)
			{
				vo.CommitWarning = "";
			}

			if(loadAllLanguages)
			{
				final String adLanguage = rs.getString("AD_Language");
				vo.setName(adLanguage, vo.name);
				vo.setDescription(adLanguage, vo.description);
				vo.setHelp(adLanguage, vo.help);
				vo.setCommitWarning(adLanguage, vo.CommitWarning);

				final String baseAD_Language = Language.getBaseAD_Language();
				vo.setName(baseAD_Language, rs.getString("Name_BaseLang"));
				vo.setDescription(baseAD_Language, rs.getString("Description_BaseLang"));
				vo.setHelp(baseAD_Language, rs.getString("Help_BaseLang"));
				vo.setCommitWarning(baseAD_Language, rs.getString("CommitWarning_BaseLang"));
			}

			vo.entityType = rs.getString("EntityType");

			vo.AD_Table_ID = rs.getInt("AD_Table_ID");
			vo.TableName = rs.getString("TableName");

			//	Translation Tab	**
			if ("Y".equals(rs.getString("IsTranslationTab")))
			{
				//	Document Translation
				//vo.TableName = rs.getString("TableName"); // metas: not necessary; is loaded above
				if (!Env.isBaseTranslation(vo.TableName)	//	C_UOM, ...
					&& !Env.isMultiLingualDocument(vo.ctx))
				{
					showTrl = false;
				}
				if (!showTrl)
				{
					vo.addLoadErrorMessage("TrlTab Not displayed (BaseTrl=" + Env.isBaseTranslation(vo.TableName) + ", MultiLingual=" + Env.isMultiLingualDocument(vo.ctx)+")"); // metas: 01934
					logger.info("TrlTab Not displayed - AD_Tab_ID="
							+ vo.adTabId + "=" + vo.name + ", Table=" + vo.TableName
							+ ", BaseTrl=" + Env.isBaseTranslation(vo.TableName)
							+ ", MultiLingual=" + Env.isMultiLingualDocument(vo.ctx));
					return false;
				}
			}

			//	Advanced Tab	**
			if (!showAdvanced && "Y".equals(rs.getString("IsAdvancedTab")))
			{
				vo.addLoadErrorMessage("AdvancedTab Not displayed"); // metas: 1934
				logger.info("AdvancedTab Not displayed - AD_Tab_ID=" + vo.adTabId + " " + vo.name);
				return false;
			}

			//	Accounting Info Tab	**
			if (!showAcct && "Y".equals(rs.getString("IsInfoTab")))
			{
				vo.addLoadErrorMessage("AcctTab Not displayed"); // metas: 1934
				logger.debug("AcctTab Not displayed - AD_Tab_ID=" + vo.adTabId + " " + vo.name);
				return false;
			}


			//	DisplayLogic
			final String DisplayLogic = rs.getString("DisplayLogic");
			vo.DisplayLogicExpr = Services.get(IExpressionFactory.class)
					.compileOrDefault(DisplayLogic, DEFAULT_DisplayLogic, ILogicExpression.class); // metas: 03093

			//	Access Level
			vo.AccessLevel = TableAccessLevel.forAccessLevel(rs.getString("AccessLevel"));
			Env.setContext(vo.ctx, vo.WindowNo, vo.TabNo, GridTab.CTX_AccessLevel, vo.AccessLevel.getAccessLevelString());

			//	Table Access
			vo.AD_Table_ID = rs.getInt("AD_Table_ID");
			Env.setContext(vo.ctx, vo.WindowNo, vo.TabNo, GridTab.CTX_AD_Table_ID, String.valueOf(vo.AD_Table_ID));

			//
			// Apply role permissions
			if(vo.applyRolePermissions)
			{
				final IUserRolePermissions role = Env.getUserRolePermissions(vo.ctx);
				
				// If EntityType is not displayed, hide this tab; note that this decision is role-specific
				vo.entityType = rs.getString("EntityType");
				if (!Check.isEmpty(vo.entityType, true)
						&& !UIDisplayedEntityTypes.isEntityTypeDisplayedInUIOrTrueIfNull(role, vo.entityType))
				{
					vo.addLoadErrorMessage("EntityType not displayed");
					return false;
				}


				if (!role.canView(vo.AccessLevel))	// No Access
				{
					vo.addLoadErrorMessage("No Role Access - AccessLevel=" + vo.AccessLevel + ", UserLevel=" + role.getUserLevel()); // 01934
					logger.debug("No Role Access - AD_Tab_ID={} {}", vo.adTabId, vo.name);
					return false;
				}	//	Used by MField.getDefault

				if (!role.isTableAccess(vo.AD_Table_ID, Access.READ))
				{
					vo.addLoadErrorMessage("No Table Access (AD_Table_ID="+vo.AD_Table_ID+")"); // 01934
					logger.debug("No Table Access - AD_Tab_ID={} {}", vo.adTabId, vo.name);
					return false;
				}
			}

			if ("Y".equals(rs.getString("IsReadOnly")))
			{
				vo.IsReadOnly = true;
			}
			final String ReadOnlyLogic = rs.getString("ReadOnlyLogic");
			vo.ReadOnlyLogicExpr = Services.get(IExpressionFactory.class)
					.compileOrDefault(ReadOnlyLogic, DEFAULT_ReadOnlyLogicExpr, ILogicExpression.class); // metas: 03093
			if (rs.getString("IsInsertRecord").equals("N"))
			{
				vo.IsInsertRecord = false;
			}

			if ("Y".equals(rs.getString("IsSingleRow")))
			{
				vo.IsSingleRow = true;
			}
			if ("Y".equals(rs.getString("HasTree")))
			{
				vo.HasTree = true;
			}

			if ("Y".equals(rs.getString("IsView")))
			{
				vo.IsView = true;
			}
			vo.AD_Column_ID = rs.getInt("AD_Column_ID");   //  Primary Link Column
			vo.Parent_Column_ID = rs.getInt("Parent_Column_ID");   // Parent tab link column

			if ("Y".equals(rs.getString("IsSecurityEnabled")))
			{
				vo.IsSecurityEnabled = true;
			}
			if ("Y".equals(rs.getString("IsDeleteable")))
			{
				vo.IsDeleteable = true;
			}
			if ("Y".equals(rs.getString("IsHighVolume")))
			{
				vo.IsHighVolume = true;
			}

			//
			// Where clause
			{
				vo.WhereClause = rs.getString("WhereClause");
				if (vo.WhereClause == null)
				{
					vo.WhereClause = "";
				}
				//jz col=null not good for Derby
				if (vo.WhereClause.indexOf("=null") > 0)
				{
					logger.warn("Replaced '=null' with 'IS NULL' for " + vo);
					vo.WhereClause = vo.WhereClause.replaceAll("=null", " IS NULL ");
				}
				// Where Clauses should be surrounded by parenthesis - teo_sarca, BF [ 1982327 ]
				if (vo.WhereClause.trim().length() > 0) {
					vo.WhereClause = "("+vo.WhereClause+")";
				}
			}

			vo.OrderByClause = rs.getString("OrderByClause");
			if (vo.OrderByClause == null)
			{
				vo.OrderByClause = "";
			}

			vo.printProcessId = AdProcessId.ofRepoIdOrNull(rs.getInt("AD_Process_ID"));
			if (rs.wasNull())
			{
				vo.printProcessId = null;
			}
			vo.AD_Image_ID = rs.getInt("AD_Image_ID");
			if (rs.wasNull())
			{
				vo.AD_Image_ID = 0;
			}
			vo.Included_Tab_ID = rs.getInt("Included_Tab_ID");
			if (rs.wasNull())
			{
				vo.Included_Tab_ID = 0;
			}
			//
			vo.TabLevel = rs.getInt("TabLevel");
			if (rs.wasNull())
			{
				vo.TabLevel = 0;
			}
			Env.setContext(vo.ctx, vo.WindowNo, vo.TabNo, GridTab.CTX_TabLevel, String.valueOf(vo.TabLevel)); // metas: tsa: set this value here because here is the right place

			//
			vo.IsSortTab = rs.getString("IsSortTab").equals("Y");
			if (vo.IsSortTab)
			{
				vo.AD_ColumnSortOrder_ID = rs.getInt("AD_ColumnSortOrder_ID");
				vo.AD_ColumnSortYesNo_ID = rs.getInt("AD_ColumnSortYesNo_ID");
			}
			//
			//	Replication Type - set R/O if Reference
			try
			{
				final int replicationTypeIdx = rs.findColumn ("ReplicationType");
				vo.ReplicationType = rs.getString (replicationTypeIdx);
				if ("R".equals(vo.ReplicationType))
				{
					vo.IsReadOnly = true;
				}
			}
			catch (Exception e)
			{
			}

			vo.allowQuickInput = StringUtils.toBoolean(rs.getString(I_AD_Tab.COLUMNNAME_AllowQuickInput));
			vo.refreshViewOnChangeEvents = StringUtils.toBoolean(rs.getString(I_AD_Tab.COLUMNNAME_IsRefreshViewOnChangeEvents));

			loadTabDetails_metas(vo, rs); // metas
		}
		catch (SQLException ex)
		{
			logger.error("", ex);
			return false;
		}
		// Apply UserDef settings - teo_sarca [ 2726889 ] Finish User Window (AD_UserDef*) functionality
		if (!MUserDefWin.apply(vo))
		{
			vo.addLoadErrorMessage("Hidden by UserDef"); // 01934
			logger.debug("Hidden by UserDef - AD_Tab_ID=" + vo.adTabId + " " + vo.name);
			return false;
		}

		return true;
	}	//	loadTabDetails

	void loadAdditionalLanguage(final ResultSet rs) throws SQLException
	{
		final String adLanguage = rs.getString("AD_Language");
		setName(adLanguage, rs.getString("Name"));
		setDescription(adLanguage, rs.getString("Description"));
		setHelp(adLanguage, rs.getString("Help"));
		setCommitWarning(adLanguage, rs.getString("CommitWarning"));
	}


	/**
	 * Return the SQL statement used for {@link GridTabVO#create(GridWindowVO, int, ResultSet, boolean, boolean)}.
	 *
	 * @param ctx context
	 * @return SQL SELECT String
	 */
	static String getSQL(final Properties ctx, final AdWindowId adWindowId, final boolean loadAllLanguages, final List<Object> sqlParams)
	{
		final String viewName;
		final boolean filterByLanguage;
		if (loadAllLanguages)
		{
			viewName = "AD_Tab_vt";
			filterByLanguage = false;
		}
		else if (!Env.isBaseLanguage(ctx, I_AD_Tab.Table_Name))
		{
			viewName = "AD_Tab_vt";
			filterByLanguage = true;
		}
		else
		{
			viewName = "AD_Tab_v";
			filterByLanguage = false;
		}

		final StringBuilder sql = new StringBuilder()
				.append("SELECT * FROM ").append(viewName)
				.append(" WHERE AD_Window_ID=? ");
		sqlParams.add(adWindowId);

		if (filterByLanguage)
		{
			sql.append(" AND AD_Language=?");
			sqlParams.add(Env.getAD_Language(ctx));
		}

		//
		// ORDER BY
		sql.append(" ORDER BY SeqNo, AD_Tab_ID");

		return sql.toString();
	}   // getSQL

	private GridTabVO (final Properties ctx, final int windowNo, final int tabNo, final boolean loadAllLanguages, final boolean applyRolePermissions)
	{
		this.ctx = ctx;
		this.WindowNo = windowNo;
		this.TabNo = tabNo;
		this.loadAllLanguages = loadAllLanguages;
		this.applyRolePermissions = applyRolePermissions;
	}

	private static final transient Logger logger = LogManager.getLogger(GridTabVO.class);

	/** Context - replicated    */
	private Properties ctx;
	/** Window No - replicated  */
	public final int WindowNo;
	/** AD Window - replicated  */
	private AdWindowId adWindowId;
	/** Tab No (not AD_Tab_ID) 0.. */
	private final int TabNo;
	private final boolean loadAllLanguages;
	private final boolean applyRolePermissions;

	/**	Tab	ID			*/
	private AdTabId adTabId;
	private AdTabId templateTabId;

	@Getter
	private String internalName;

	/** Name			*/
	private String name = "";
	private Map<String, String> nameTrls = null;

	/** Description		*/
	private String description = "";
	private Map<String, String> descriptionTrls = null;
	/** Help			*/
	private String help = "";
	private Map<String, String> helpTrls = null;

	private String entityType = null;
	/** Single Row		*/
	public	boolean	    IsSingleRow = false;
	/** Read Only		*/
	private boolean IsReadOnly = false;
	/** Insert Record	*/
	private boolean IsInsertRecord = true;
	/** Tree			*/
	public  boolean	    HasTree = false;
	/** Table			*/
	public  int		    AD_Table_ID;
	/** Primary Link Column  (from this tab)  */
	private int		    AD_Column_ID = 0;
	/** Parent Tab's Link Column (i.e. the AD_Column_ID from parent tab) */
	private	int			Parent_Column_ID = 0;
	/** Table Name		*/
	public  String	    TableName;
	/** Table is View	*/
	private boolean IsView = false;
	/** Table Access Level	*/
	public TableAccessLevel AccessLevel;
	/** Security		*/
	public  boolean	    IsSecurityEnabled = false;
	/** Table Deleteable	*/
	private boolean IsDeleteable = false;
	/** Table High Volume	*/
	public  boolean     IsHighVolume = false;
	/** Process			*/
	private AdProcessId printProcessId;
	/** Commit Warning	*/
	private String CommitWarning;
	private Map<String, String> commitWarningTrls = null;

	/** Where			*/
	private String WhereClause;
//	private static final IStringExpression DEFAULT_WhereClauseExpression = IStringExpression.NULL;
	/** Order by		*/
	public  String      OrderByClause;
	/** Tab Read Only	*/
	private static final ILogicExpression DEFAULT_ReadOnlyLogicExpr = ConstantLogicExpression.FALSE;
	private ILogicExpression ReadOnlyLogicExpr = DEFAULT_ReadOnlyLogicExpr;
	/** Tab Display		*/
	private static final ILogicExpression DEFAULT_DisplayLogic = ConstantLogicExpression.TRUE;
	private ILogicExpression DisplayLogicExpr = DEFAULT_DisplayLogic;
	/** Tab Level */
	private int TabLevel = 0;
	/** Image			*/
	public int          AD_Image_ID = 0;
	/** Included Tab	*/
	public int          Included_Tab_ID = 0;
	/** Replication Type	*/
	public String		ReplicationType = "L";

	/** Sort Tab			*/
	public boolean		IsSortTab = false;
	/** Column Sort			*/
	public int			AD_ColumnSortOrder_ID = 0;
	/** Column Displayed	*/
	public int			AD_ColumnSortYesNo_ID = 0;

	/** Only Current Rows - derived	*/
	public  boolean     onlyCurrentRows = true;
	/**	Only Current Days - derived	*/
	public int			onlyCurrentDays = 0;

	private List<GridFieldVO> _fields = null; // lazy
	private Optional<GridFieldVO> _keyField = null; // lazy
	private Set<String> _linkColumnNames = null; // lazy

	@Getter
	private boolean allowQuickInput;
	@Getter
	private boolean refreshViewOnChangeEvents = false;

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("Name", name)
				.add("TableName", TableName)
				.add("TabNo", TabNo)
				.add("TabLevel", TabLevel)
				.add("AD_Tab_ID", adTabId)
				.toString();
	}

	public List<GridFieldVO> getFields()
	{
		if(_fields == null)
		{
			synchronized (this)
			{
				if(_fields == null)
				{
					_fields = GridFieldVOsLoader.newInstance()
							.setCtx(getCtx())
							.setWindowNo(getWindowNo())
							.setTabNo(getTabNo())
							.setAdWindowId(getAdWindowId())
							.setAD_Tab_ID(getAD_Tab_ID())
							.setTemplateTabId(AdTabId.toRepoId(getTemplateTabId()))
							.setTabReadOnly(isReadOnly())
							.setLoadAllLanguages(loadAllLanguages)
							.setApplyRolePermissions(applyRolePermissions)
							.load();
				}
			}
		}
		return _fields;
	}

	/** @return {@link GridFieldVO} or <code>null</code> */
	public GridFieldVO getFieldByAD_Field_ID(final int adFieldId)
	{
		if(adFieldId <= 0)
		{
			return null;
		}

		for (final GridFieldVO gridFieldVO : getFields())
		{
			if (gridFieldVO.getAD_Field_ID() == adFieldId)
			{
				return gridFieldVO;
			}
		}

		return null;
	}

	public GridFieldVO getFieldByColumnName(final String columnName)
	{
		Check.assumeNotEmpty(columnName, "columnName is not empty");
		for (final GridFieldVO gridFieldVO : getFields())
		{
			if(Objects.equals(columnName, gridFieldVO.getColumnName()))
			{
				return gridFieldVO;
			}
		}

		return null;
	}

	/** @return field or null if not found */
	public GridFieldVO getFieldByAD_Column_ID(final int adColumnId)
	{
		if(adColumnId <= 0)
		{
			return null;
		}

		for (final GridFieldVO gridFieldVO : getFields())
		{
			if(adColumnId == gridFieldVO.getAD_Column_ID())
			{
				return gridFieldVO;
			}
		}

		return null;
	}

	/** @return column name or null if not found */
	public String getColumnNameByAD_Column_ID(final int adColumnId)
	{
		final GridFieldVO field = getFieldByAD_Column_ID(adColumnId);
		return field == null ? null : field.getColumnName();
	}


	public boolean hasField(final String columnName)
	{
		return getFieldByColumnName(columnName) != null;
	}

	/**
	 * @return Key(ID) field or null
	 */
	public GridFieldVO getKeyField()
	{
		if (_keyField == null)
		{
			GridFieldVO keyField = null;

			for (final GridFieldVO gridFieldVO : getFields())
			{
				if (gridFieldVO.isKey())
				{
					keyField = gridFieldVO;
					break;
				}
			}

			_keyField = Optional.fromNullable(keyField);
		}

		return _keyField.orNull();
	}

	public String getKeyColumnName()
	{
		final GridFieldVO keyField = getKeyField();
		return keyField == null ? null : keyField.getColumnName();
	}

	/**
	 *  Set Context including contained elements
	 *  @param newCtx new context
	 */
	public void setCtx (Properties newCtx)
	{
		ctx = newCtx;
		final List<GridFieldVO> fields = this._fields;
		if (fields != null)
		{
			for (GridFieldVO field : fields)
			{
				field.setCtx(newCtx);
			}
		}
	}   //  setCtx

	public Properties getCtx()
	{
		return ctx;
	}


	/**
	 * 	Get Variable Value (Evaluatee)
	 *	@param variableName name
	 *	@return value
	 */
	@Override
	public String get_ValueAsString (String variableName)
	{
		return Env.getContext (ctx, WindowNo, variableName, false);	// not just window
	}	//	get_ValueAsString

	protected GridTabVO clone(final Properties ctx, final int windowNo)
	{
		final GridTabVO clone = new GridTabVO(ctx, windowNo, this.TabNo, this.loadAllLanguages, this.applyRolePermissions);
		clone.adWindowId = adWindowId;
		Env.setContext(ctx, windowNo, clone.TabNo, GridTab.CTX_AD_Tab_ID, clone.adTabId != null ? String.valueOf(clone.adTabId.getRepoId()) : null);
		//
		clone.adTabId = adTabId;
		clone.name = name;
		clone.nameTrls = nameTrls == null ? null : new HashMap<>(nameTrls);
		Env.setContext(ctx, windowNo, clone.TabNo, GridTab.CTX_Name, clone.name);
		clone.description = description;
		clone.descriptionTrls = descriptionTrls == null ? null : new HashMap<>(descriptionTrls);
		clone.help = help;
		clone.helpTrls = helpTrls == null ? null : new HashMap<>(helpTrls);
		clone.entityType = entityType;
		clone.IsSingleRow = IsSingleRow;
		clone.IsReadOnly = IsReadOnly;
		clone.IsInsertRecord = IsInsertRecord;
		clone.HasTree = HasTree;
		clone.AD_Table_ID = AD_Table_ID;
		clone.AD_Column_ID = AD_Column_ID;
		clone.Parent_Column_ID = Parent_Column_ID;
		clone.TableName = TableName;
		clone.IsView = IsView;
		clone.AccessLevel = AccessLevel;
		clone.IsSecurityEnabled = IsSecurityEnabled;
		clone.IsDeleteable = IsDeleteable;
		clone.IsHighVolume = IsHighVolume;
		clone.printProcessId = printProcessId;
		clone.CommitWarning = CommitWarning;
		clone.commitWarningTrls = commitWarningTrls == null ? null : new HashMap<>(commitWarningTrls);
		clone.WhereClause = WhereClause;
		clone.OrderByClause = OrderByClause;
		clone.ReadOnlyLogicExpr = ReadOnlyLogicExpr;
		clone.DisplayLogicExpr = DisplayLogicExpr;
		clone.TabLevel = TabLevel;
		clone.AD_Image_ID = AD_Image_ID;
		clone.Included_Tab_ID = Included_Tab_ID;
		clone.ReplicationType = ReplicationType;
		Env.setContext(ctx, windowNo, clone.TabNo, GridTab.CTX_AccessLevel, clone.AccessLevel.getAccessLevelString());
		Env.setContext(ctx, windowNo, clone.TabNo, GridTab.CTX_AD_Table_ID, String.valueOf(clone.AD_Table_ID));

		//
		clone.IsSortTab = IsSortTab;
		clone.AD_ColumnSortOrder_ID = AD_ColumnSortOrder_ID;
		clone.AD_ColumnSortYesNo_ID = AD_ColumnSortYesNo_ID;
		//  Derived
		clone.onlyCurrentRows = true;
		clone.onlyCurrentDays = 0;

		clone.allowQuickInput = allowQuickInput;
		clone.refreshViewOnChangeEvents = refreshViewOnChangeEvents;

		clone_metas(ctx, windowNo, clone); // metas

		final List<GridFieldVO> fields = _fields;
		if(fields != null)
		{
			final ImmutableList.Builder<GridFieldVO> cloneFields = ImmutableList.builder();
			for (final GridFieldVO field : fields)
			{
				final GridFieldVO cloneField = field.clone(ctx, windowNo, TabNo, adWindowId, AdTabId.toRepoId(adTabId), IsReadOnly);
				if (cloneField == null)
				{
					continue;
				}
				cloneFields.add(cloneField);
			}

			clone._fields = cloneFields.build();
		}

		return clone;
	}	//	clone

	public void clearFields()
	{
		_fields = null;
	}

// metas: begin
	/** Grid Mode Only */
	// metas-2009_0021_AP1_CR059
	public boolean IsGridModeOnly = false; // metas-2009_0021_AP1_CR059
	/** Tab has search active */
	// metas-2009_0021_AP1_CR057
	public boolean IsSearchActive = true; // metas-2009_0021_AP1_CR057
	/** Search panel is collapsed */
	// metas-2009_0021_AP1_CR064
	public boolean IsSearchCollapsed = true; // metas-2009_0021_AP1_CR064
	/** Query tab data after open */
	// metas-2009_0021_AP1_CR064
	public boolean IsQueryOnLoad = true; // metas-2009_0021_AP1_CR064
	/** Deafault Where */
	private String DefaultWhereClause;
	public boolean IsRefreshAllOnActivate = false; // metas-2009_0021_AP1_CR050
	public AdMessageId AD_Message_ID = null; //metas-us092
	/** Check if the parents of this tab have changed */// 01962
	public boolean IsCheckParentsChanged = true;
	/** Max records to be queried (overrides the settings from AD_Role) */
	private int MaxQueryRecords = 0;

	private static void loadTabDetails_metas (final GridTabVO vo, final ResultSet rs) throws SQLException
	{
		if ("Y".equals(rs.getString("IsGridModeOnly")))
		 {
			vo.IsGridModeOnly = true; // metas-2009_0021_AP1_CR059
		}
		vo.IsSearchActive = "Y".equals(rs.getString("IsSearchActive")); // metas-2009_0021_AP1_CR057
		vo.IsSearchCollapsed = "Y".equals(rs.getString("IsSearchCollapsed")); // metas-2009_0021_AP1_CR064
		vo.IsQueryOnLoad = "Y".equals(rs.getString("IsQueryOnLoad")); // metas-2009_0021_AP1_CR064

		// metas: default where clause
		vo.DefaultWhereClause = rs.getString("DefaultWhereClause");
		if (vo.DefaultWhereClause == null)
		{
			vo.DefaultWhereClause = "";
		}
		if (vo.DefaultWhereClause.indexOf("=null") > 0)
		{
			vo.DefaultWhereClause = vo.DefaultWhereClause.replaceAll("=null", " IS NULL ");
		}
		if (vo.DefaultWhereClause.trim().length() > 0)
		{
			vo.DefaultWhereClause = "(" + vo.DefaultWhereClause + ")";
		}

		vo.IsRefreshAllOnActivate = "Y".equals(rs.getString("IsRefreshAllOnActivate")); // metas-2009_0021_AP1_CR050
		vo.AD_Message_ID = AdMessageId.ofRepoIdOrNull(rs.getInt("AD_Message_ID")); // metas-us092
		vo.IsCheckParentsChanged = "Y".equals(rs.getString("IsCheckParentsChanged")); // 01962

		vo.MaxQueryRecords = rs.getInt("MaxQueryRecords");
		if(vo.MaxQueryRecords <= 0)
		{
			vo.MaxQueryRecords = 0;
		}
	}

	private void clone_metas(final Properties ctx, final int windowNo, final GridTabVO clone)
	{
		clone.IsSearchActive = IsSearchActive; // metas-2009_0021_AP1_CR057
		clone.IsSearchCollapsed = IsSearchCollapsed; // metas-2009_0021_AP1_CR064
		clone.IsQueryOnLoad = IsQueryOnLoad; // metas-2009_0021_AP1_CR064
		clone.DefaultWhereClause = DefaultWhereClause;
		clone.IsRefreshAllOnActivate = IsRefreshAllOnActivate; // metas-2009_0021_AP1_CR050
		clone.AD_Message_ID = AD_Message_ID; // metas-US092
		clone.IsCheckParentsChanged = IsCheckParentsChanged; // 01962
		clone.MaxQueryRecords = this.MaxQueryRecords;
	}


	private StringBuffer loadErrorMessages = null;
	protected void addLoadErrorMessage(String message)
	{
		if (Check.isEmpty(message, true))
		{
			return;
		}
		if (loadErrorMessages == null)
		{
			loadErrorMessages = new StringBuffer();
		}
		if (loadErrorMessages.length() > 0)
		{
			loadErrorMessages.append("\n");
		}
		loadErrorMessages.append(message);
	}
	public String getLoadErrorMessage()
	{
		if (loadErrorMessages == null || loadErrorMessages.length() == 0)
		{
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("Tab ").append(this.name).append("(").append(this.TableName).append("): ").append(loadErrorMessages);
		return sb.toString();
	}

	public ILogicExpression getReadOnlyLogic()
	{
		return ReadOnlyLogicExpr;
	}

	public ILogicExpression getDisplayLogic()
	{
		return DisplayLogicExpr;
	}
// metas: end

	public String getTableName()
	{
		return TableName;
	}

	public AdWindowId getAdWindowId()
	{
		return adWindowId;
	}

	public AdTabId getAdTabId()
	{
		return adTabId;
	}

	/**
	 * @deprecated Please use {@link #getAD_Tab_ID()}
	 */
	@Deprecated
	public int getAD_Tab_ID()
	{
		return AdTabId.toRepoId(getAdTabId());
	}

	public AdTabId getTemplateTabId()
	{
		return templateTabId;
	}

	public int getWindowNo()
	{
		return WindowNo;
	}

	public int getAD_Table_ID()
	{
		return AD_Table_ID;
	}

	/**
	 * @return Primary Link Column (from this tab)
	 */
	public int getAD_Column_ID()
	{
		return AD_Column_ID;
	}

	/**
	 * @return Parent Tab's Link Column (i.e. the AD_Column_ID from parent tab)
	 */
	public int getParent_Column_ID()
	{
		return Parent_Column_ID;
	}

	/**
	 * @return  max records to be queried (overrides the settings from AD_Role).
	 */
	public int getMaxQueryRecords()
	{
		return MaxQueryRecords;
	}

	public String getEntityType()
	{
		return entityType;
	}

	public int getTabNo()
	{
		return TabNo;
	}

	public int getTabLevel()
	{
		return TabLevel;
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public Map<String, String> getNameTrls()
	{
		return nameTrls;
	}

	private void setName(final String adLanguage, final String nameTrl)
	{
		Check.assumeNotEmpty(adLanguage, "adLanguage is not empty");

		if(nameTrl == null)
		{
			return;
		}

		if(nameTrls == null)
		{
			nameTrls = new HashMap<>();
		}
		nameTrls.put(adLanguage, nameTrl);
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}

	public Map<String, String> getDescriptionTrls()
	{
		return descriptionTrls;
	}

	private void setDescription(final String adLanguage, final String descriptionTrl)
	{
		Check.assumeNotEmpty(adLanguage, "adLanguage is not empty");

		if(descriptionTrl == null)
		{
			return;
		}

		if(descriptionTrls == null)
		{
			descriptionTrls = new HashMap<>();
		}
		descriptionTrls.put(adLanguage, descriptionTrl);
	}

	public String getHelp()
	{
		return help;
	}

	public void setHelp(final String help)
	{
		this.help = help;
	}

	public Map<String, String> getHelpTrls()
	{
		return helpTrls;
	}

	private void setHelp(final String adLanguage, final String helpTrl)
	{
		Check.assumeNotEmpty(adLanguage, "adLanguage is not empty");

		if(helpTrl == null)
		{
			return;
		}

		if(helpTrls == null)
		{
			helpTrls = new HashMap<>();
		}
		helpTrls.put(adLanguage, helpTrl);
	}

	public String getCommitWarning()
	{
		return CommitWarning;
	}

	public Map<String, String> getCommitWarningTrls()
	{
		return commitWarningTrls;
	}

	private void setCommitWarning(final String adLanguage, final String commitWarningTrl)
	{
		Check.assumeNotEmpty(adLanguage, "adLanguage is not empty");

		if(commitWarningTrl == null)
		{
			return;
		}

		if(commitWarningTrls == null)
		{
			commitWarningTrls = new HashMap<>();
		}
		commitWarningTrls.put(adLanguage, commitWarningTrl);
	}

	public AdProcessId getPrintProcessId()
	{
		return printProcessId;
	}

	public boolean isReadOnly()
	{
		return IsReadOnly;
	}

	void setReadOnly(boolean isReadOnly)
	{
		IsReadOnly = isReadOnly;
	}

	public boolean isView()
	{
		return IsView;
	}

	public String getOrderByClause()
	{
		return OrderByClause;
	}

	public String getWhereClause()
	{
		return WhereClause;
	}

	public String getDefaultWhereClause()
	{
		return DefaultWhereClause;
	}

	public boolean isInsertRecord()
	{
		return IsInsertRecord;
	}

	public boolean isDeleteable()
	{
		return IsDeleteable;
	}

	/**
	 * Get Order column for sort tab
	 *
	 * @return AD_Column_ID
	 */
	public int getAD_ColumnSortOrder_ID()
	{
		return AD_ColumnSortOrder_ID;
	}

	/**
	 * Get Yes/No column for sort tab
	 *
	 * @return AD_Column_ID
	 */
	public int getAD_ColumnSortYesNo_ID()
	{
		return AD_ColumnSortYesNo_ID;
	}

	public Set<String> getLinkColumnNames()
	{
		if(_linkColumnNames == null)
		{
			_linkColumnNames = buildLinkColumnNames();
		}
		return _linkColumnNames;
	}

	private Set<String> buildLinkColumnNames()
	{
		//
		// If the link column name was specified, then use it.
		final int linkColumnId = getAD_Column_ID();
		if(linkColumnId > 0)
		{
			final GridFieldVO linkField = getFieldByAD_Column_ID(linkColumnId);
			if(linkField != null)
			{
				return ImmutableSet.of(linkField.getColumnName());
			}
			else
			{
				return ImmutableSet.of();
			}
		}

		//
		// Fallback: collect all fields which were marked as possible link column candidates.
		return getFields()
				.stream()
				.filter(field -> field.isParentLink())
				.map(field -> field.getColumnName())
				.collect(GuavaCollectors.toImmutableSet());
	}
	
	boolean isApplyRolePermissions()
	{
		return applyRolePermissions;
	}
}
