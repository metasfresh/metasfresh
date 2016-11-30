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
 * @contributor Victor Perez , e-Evolution.SC FR [ 1757088 ]                  *
 *****************************************************************************/
package org.compiere.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.security.asp.IASPFiltersFactory;
import org.adempiere.ad.security.permissions.UIDisplayedEntityTypes;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.FieldGroupVO.FieldGroupType;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Language;
import org.compiere.util.Util;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.logging.LogManager;


/**
 *  Field Model Value Object
 *
 *  @author Jorg Janke
 *  @author Victor Perez , e-Evolution.SC FR [ 1757088 ] , [1877902] Implement JSR 223 Scripting APIs to Callout
 *  @author Carlos Ruiz, qss FR [1877902]
 *  @author Juan David Arboleda (arboleda), GlobalQSS, [ 1795398 ] Process Parameter: add display and readonly logic
 *  @see  http://sourceforge.net/tracker/?func=detail&atid=879335&aid=1877902&group_id=176962 to FR [1877902]
 *  @version  $Id: GridFieldVO.java,v 1.3 2006/07/30 00:58:04 jjanke Exp $
 */
public class GridFieldVO implements Serializable
{
	public static final Comparator<GridFieldVO> COMPARATOR_BySeqNo = new Comparator<GridFieldVO>()
	{
		@Override
		public int compare(final GridFieldVO o1, final GridFieldVO o2)
		{
			return getSeqNo(o1) - getSeqNo(o2);
		}
		
		private final int getSeqNo(final GridFieldVO field)
		{
			return field == null ? 0 : field.getSeqNo();
		}
	};
	
	public static final Comparator<GridFieldVO> COMPARATOR_BySeqNoGrid = new Comparator<GridFieldVO>()
	{
		@Override
		public int compare(final GridFieldVO o1, final GridFieldVO o2)
		{
			return getSeqNo(o1) - getSeqNo(o2);
		}
		
		private final int getSeqNo(final GridFieldVO field)
		{
			return field == null ? 0 : field.getSeqNoGrid();
		}
	};
	
	private static final transient Logger logger = LogManager.getLogger(GridFieldVO.class);
	
	/**
	 * Return the SQL statement used for the {@link #create(Properties, int, int, int, int, boolean, boolean, ResultSet)}.
	 * 
	 * @param ctx context
	 * @return SQL
	 */
	static String getSQL (final Properties ctx, final int adTabId, final boolean loadAllLanguages, final List<Object> sqlParams)
	{
		final String viewName;
		final boolean filterByLanguage;
		if(loadAllLanguages)
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
	}   //  getSQL

	/**
	 *  Create Field Value Object
	 *  @param ctx context
	 *  @param WindowNo window
	 *  @param TabNo tab
	 *  @param AD_Window_ID window
	 *  @param AD_Tab_ID tab
	 *  @param readOnly r/o
	 *  @param rs resultset AD_Field_v
	 *  @return MFieldVO
	 */
	static GridFieldVO create (final Properties ctx,
			final int WindowNo, final int TabNo,
			final int AD_Window_ID, final int AD_Tab_ID,
			final boolean readOnly,
			final boolean loadAllLanguages,
			final ResultSet rs)
	{
		final GridFieldVO vo = new GridFieldVO (ctx, WindowNo, TabNo, AD_Window_ID, AD_Tab_ID, readOnly);
		
		String columnName = "ColumnName";
		try
		{
			vo.ColumnName = rs.getString("ColumnName");
			Check.assumeNotEmpty(vo.ColumnName, "ColumnName is not empty");
			logger.debug("Creating grid field for {}", vo.ColumnName);

			String fieldGroupName = null;
			FieldGroupType fieldGroupType = null;
			boolean fieldGroupCollapsedByDefault = false;

			final GridFieldLayoutConstraints.Builder layoutConstraints = GridFieldLayoutConstraints.builder();

			vo.header = Util.coalesce(rs.getString ("Name"), vo.ColumnName);
			vo.description = rs.getString ("Description");
			vo.help = rs.getString ("Help");
			if(loadAllLanguages)
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
				columnName = rsmd.getColumnName (i);
				
				if (columnName.equalsIgnoreCase("AD_Reference_ID"))
					vo.displayType = rs.getInt (i);
				else if (columnName.equalsIgnoreCase("AD_Column_ID"))
					vo.AD_Column_ID = rs.getInt (i);
				else if (columnName.equalsIgnoreCase("AD_Table_ID"))
					vo.AD_Table_ID = rs.getInt (i);
				// metas: begin
				else if (columnName.equalsIgnoreCase("AD_Field_ID"))
				{
					vo.AD_Field_ID = rs.getInt(i);
					if (rs.wasNull())
					{
						vo.AD_Field_ID = -1;
					}
				}
				// metas: end
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
					vo.setSeqNo(rs.getInt(i));
				else if (columnName.equalsIgnoreCase(I_AD_Field.COLUMNNAME_SeqNoGrid))
					vo.setSeqNoGrid(rs.getInt(i));
				else if (columnName.equalsIgnoreCase("IsDisplayed"))
					vo.IsDisplayed = "Y".equals(rs.getString (i));
				else if (columnName.equalsIgnoreCase(I_AD_Field.COLUMNNAME_IsDisplayedGrid))
					vo.setIsDisplayedGrid("Y".equals(rs.getString(i)));
				else if (columnName.equalsIgnoreCase("DisplayLogic"))
					vo.DisplayLogic = rs.getString (i);
				// metas-2009_0021_AP1_CR045: begin
				else if (columnName.equalsIgnoreCase("ColorLogic"))
					vo.ColorLogic = rs.getString(i);
				// metas-2009_0021_AP1_CR045: end
				else if (columnName.equalsIgnoreCase("DefaultValue"))
					vo.DefaultValue = rs.getString (i);
				else if (columnName.equalsIgnoreCase("IsMandatory"))
					vo.IsMandatory = "Y".equals(rs.getString (i));
				else if (columnName.equalsIgnoreCase("IsMandatoryDB"))
					vo.IsMandatoryDB = "Y".equals(rs.getString (i));
				else if (columnName.equalsIgnoreCase("IsReadOnly"))
					vo.IsReadOnly = "Y".equals(rs.getString (i));
				else if (columnName.equalsIgnoreCase("IsUpdateable"))
					vo.IsUpdateable = "Y".equals(rs.getString (i));
				else if (columnName.equalsIgnoreCase("IsAlwaysUpdateable"))
					vo.IsAlwaysUpdateable = "Y".equals(rs.getString (i));
				else if (columnName.equalsIgnoreCase("IsHeading"))
					vo.IsHeading = "Y".equals(rs.getString (i));
				else if (columnName.equalsIgnoreCase("IsFieldOnly"))
					vo.IsFieldOnly = "Y".equals(rs.getString (i));
				else if (columnName.equalsIgnoreCase("IsEncryptedField"))
					vo.IsEncryptedField = "Y".equals(rs.getString (i));
				else if (columnName.equalsIgnoreCase("IsEncryptedColumn"))
					vo.IsEncryptedColumn = "Y".equals(rs.getString (i));
				else if (columnName.equalsIgnoreCase("IsSelectionColumn"))
					vo.IsSelectionColumn = "Y".equals(rs.getString (i));
				else if (columnName.equalsIgnoreCase("SortNo"))
					vo.SortNo = rs.getInt (i);
				else if (columnName.equalsIgnoreCase("FieldLength"))
					vo.FieldLength = rs.getInt (i);
				else if (columnName.equalsIgnoreCase("VFormat"))
					vo.VFormat = rs.getString (i);
				else if (columnName.equalsIgnoreCase("FormatPattern"))
					vo.formatPattern = rs.getString (i);
				else if (columnName.equalsIgnoreCase("ValueMin"))
					vo.ValueMin = rs.getString (i);
				else if (columnName.equalsIgnoreCase("ValueMax"))
					vo.ValueMax = rs.getString (i);
				//
				else if (columnName.equalsIgnoreCase("FieldGroup"))
					fieldGroupName = rs.getString (i);
				else if (columnName.equalsIgnoreCase("FieldGroupType"))
					fieldGroupType = FieldGroupType.forCodeOrDefault(rs.getString(i), FieldGroupType.Label);
				else if (columnName.equalsIgnoreCase("IsCollapsedByDefault"))
					fieldGroupCollapsedByDefault = "Y".equals(rs.getString(i));
				//
				else if (columnName.equalsIgnoreCase("IsKey"))
					vo.IsKey = "Y".equals(rs.getString (i));
				else if (columnName.equalsIgnoreCase("IsParent"))
					vo.IsParent = "Y".equals(rs.getString (i));
				// metas: tsa: commented out because we load callouts above, from AD_ColumnCallout
				// else if (columnName.equalsIgnoreCase("Callout"))
				// vo.Callout = rs.getString (i);
				else if (columnName.equalsIgnoreCase("AD_Process_ID"))
					vo.AD_Process_ID = rs.getInt (i);
				else if (columnName.equalsIgnoreCase("ReadOnlyLogic"))
					vo.ReadOnlyLogic = rs.getString (i);
				else if (columnName.equalsIgnoreCase("MandatoryLogic"))
					vo.MandatoryLogic = rs.getString (i);
				else if (columnName.equalsIgnoreCase("ObscureType"))
					vo.ObscureType = rs.getString (i);
				//
				else if (columnName.equalsIgnoreCase("AD_Reference_Value_ID"))
					vo.AD_Reference_Value_ID = rs.getInt(i);
				else if (columnName.equalsIgnoreCase("AD_Val_Rule_ID"))	// metas: 03271
					vo.AD_Val_Rule_ID = rs.getInt(i);					// metas: 03271
				else if (columnName.equalsIgnoreCase("ColumnSQL"))
					vo.ColumnSQL = rs.getString(i);
				// metas: adding cloumnclass
				else if (columnName.equalsIgnoreCase("ColumnClass"))
					vo.ColumnClass = rs.getString(i);
				// metas end
				//Feature Request FR [ 1757088 ]
				else if (columnName.equalsIgnoreCase("Included_Tab_ID"))
					vo.Included_Tab_ID = rs.getInt(i);
				//Info Factory class
				else if (columnName.equalsIgnoreCase("InfoFactoryClass"))
					vo.InfoFactoryClass  = rs.getString(i);
//				Feature Request FR [ 2003044 ]
				else if (columnName.equalsIgnoreCase("IsAutocomplete"))
					vo.autocomplete  = "Y".equals(rs.getString(i));
				// metas-2009_0021_AP1_CR051: begin
				else if (columnName.equalsIgnoreCase("IncludedTabHeight"))
					vo.IncludedTabHeight = rs.getInt(i);
				// metas-2009_0021_AP1_CR051: end
				// metas: begin: us215
				else if (columnName.equalsIgnoreCase("IsCalculated"))
					vo.IsCalculated = "Y".equals(rs.getString (i));
				// metas: end: us215
				else if (columnName.equalsIgnoreCase("FieldEntityType"))
				{
					vo.fieldEntityType = rs.getString(i);
				}
			}

			//
			vo.fieldGroup = FieldGroupVO.build(fieldGroupName, fieldGroupType, fieldGroupCollapsedByDefault);
			vo.layoutConstraints = layoutConstraints.build();
		}
		catch (SQLException e)
		{
			logger.error("ColumnName=" + columnName, e);
			return null;
		}
		
		//
		// ASP
		if (vo.IsDisplayed)
		{
			// ASP for fields has a different approach - it must be defined as a field but hidden
			//   in order to have the proper context variable filled with defaults
			// Validate field and put IsDisplayed=N if must be hidden
			if (!Services.get(IASPFiltersFactory.class).getASPFiltersForClient(ctx).isDisplayField(vo.AD_Field_ID))
			{
				vo.IsDisplayed = false;
				vo.isDisplayedGrid = false;
			}
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
	}   //  create
	
	void loadAdditionalLanguage(final ResultSet rs) throws SQLException
	{
		final String adLanguage = rs.getString("AD_Language");
		setHeader(adLanguage, rs.getString("Name"));
		setDescription(adLanguage, rs.getString("Description"));
		setHelp(adLanguage, rs.getString("Help"));
	}

	/**
	 *  Init Field for Process Parameter
	 *  @param ctx context
	 *  @param WindowNo window No
	 *  @param tabNo Tab No or {@link Env#TAB_None}
	 *  @param rs result set AD_Process_Para
	 *  @return MFieldVO
	 */
	public static GridFieldVO createParameter (final Properties ctx, final int WindowNo, final int tabNo, final ResultSet rs)
	{
		final GridFieldVO vo = new GridFieldVO (ctx, WindowNo, tabNo, 0, 0, false);
		vo.isProcess = true;
		vo.isProcessParameterTo = false;
		vo.IsDisplayed = true;
		vo.isDisplayedGrid = false;
		vo.IsReadOnly = false;
		vo.IsUpdateable = true;

		try
		{
			vo.AD_Table_ID = 0;
			vo.AD_Field_ID = 0; // metas
			vo.AD_Column_ID = 0; // metas-tsa: we cannot use the AD_Column_ID to store the AD_Process_Para_ID because we get inconsistencies elsewhere // rs.getInt("AD_Process_Para_ID");
			vo.ColumnName = rs.getString("ColumnName");
			vo.header = rs.getString("Name");
			vo.description = rs.getString("Description");
			vo.help = rs.getString("Help");
			vo.displayType = rs.getInt("AD_Reference_ID");
			vo.IsMandatory = "Y".equals(rs.getString("IsMandatory"));
			vo.IsMandatoryDB = vo.IsMandatory;
			vo.FieldLength = rs.getInt("FieldLength");
			vo.layoutConstraints = GridFieldLayoutConstraints.builder()
					.setDisplayLength(vo.FieldLength)
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
			vo.AD_Reference_Value_ID = rs.getInt("AD_Reference_Value_ID");
			vo.autocomplete = "Y".equals(rs.getString("IsAutoComplete"));
			vo.AD_Val_Rule_ID = rs.getInt("AD_Val_Rule_ID"); // metas: 03271
			vo.ReadOnlyLogic = rs.getString("ReadOnlyLogic");
			vo.DisplayLogic= rs.getString("DisplayLogic");

			vo.fieldEntityType = rs.getString("FieldEntityType");
		}
		catch (SQLException e)
		{
			logger.error("createParameter", e);
		}
		//
		vo.initFinish();
		if (vo.DefaultValue2 == null)
			vo.DefaultValue2 = "";
		return vo;
	}   //  createParameter

	/**
	 *  Create range "to" Parameter Field from "from" Parameter Field
	 *  @param vo parameter from VO
	 */
	public static GridFieldVO createParameterTo (final GridFieldVO vo)
	{
		final GridFieldVO voTo = vo.clone(vo.ctx, vo.WindowNo, vo.TabNo, vo.AD_Window_ID, vo.AD_Tab_ID, vo.tabReadOnly);
		voTo.isProcess = true;
		voTo.isProcessParameterTo = true;
		voTo.IsDisplayed = true;
		voTo.isDisplayedGrid = false;
		voTo.IsReadOnly = false;
		voTo.IsUpdateable = true;
		//
		voTo.AD_Field_ID = vo.AD_Field_ID; // metas
		voTo.AD_Table_ID = vo.AD_Table_ID;
		voTo.AD_Column_ID = vo.AD_Column_ID;    //  AD_Process_Para_ID
		voTo.ColumnName = vo.ColumnName;
		voTo.header = vo.header;
		voTo.description = vo.description;
		voTo.help = vo.help;
		voTo.displayType = vo.displayType;
		voTo.IsMandatory = vo.IsMandatory;
		voTo.IsMandatoryDB = vo.IsMandatoryDB;
		voTo.FieldLength = vo.FieldLength;
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
		voTo.isHiddenFromUI = vo.isHiddenFromUI;

		voTo.initFinish();
		return voTo;
	}   //  createParameter


	/**
	 *  Make a standard field (Created/Updated/By)
	 *  @param ctx context
	 *  @param WindowNo window
	 *  @param TabNo tab
	 *  @param AD_Window_ID window
	 *  @param AD_Tab_ID tab
	 *  @param tabReadOnly rab is r/o
	 *  @param isCreated is Created field
	 *  @param isTimestamp is the timestamp (not by)
	 *  @return MFieldVO
	 */
	public static GridFieldVO createStdField (Properties ctx, int WindowNo, int TabNo,
		int AD_Window_ID, int AD_Tab_ID, boolean tabReadOnly,
		boolean isCreated, boolean isTimestamp)
	{
		GridFieldVO vo = new GridFieldVO (ctx, WindowNo, TabNo, AD_Window_ID, AD_Tab_ID, tabReadOnly);
		vo.ColumnName = isCreated ? "Created" : "Updated";
		if (!isTimestamp)
			vo.ColumnName += "By";
		vo.displayType = isTimestamp ? DisplayType.DateTime : DisplayType.Table;
		if (!isTimestamp)
			vo.AD_Reference_Value_ID = 110;		//	AD_User Table Reference
		vo.IsDisplayed = false;
		vo.isDisplayedGrid = false;
		vo.IsMandatory = false;
		vo.IsMandatoryDB = false;
		vo.IsReadOnly = false;
		vo.IsUpdateable = true;
		vo.initFinish();
		return vo;
	}   //  initStdField

	/**************************************************************************
	 *  Private constructor.
	 *  @param ctx context
	 *  @param windowNo window
	 *  @param tabNo tab
	 *  @param ad_Window_ID window
	 *  @param ad_Tab_ID tab
	 *  @param TabReadOnly tab read only
	 */
	private GridFieldVO (Properties ctx, int windowNo, int tabNo, int ad_Window_ID, int ad_Tab_ID, boolean TabReadOnly)
	{
		super();
		this.ctx = ctx;
		WindowNo = windowNo;
		TabNo = tabNo;
		AD_Window_ID = ad_Window_ID;
		AD_Tab_ID = ad_Tab_ID;
		tabReadOnly = TabReadOnly;
	}   //  MFieldVO

	static final long serialVersionUID = 4385061125114436797L;

	/** Context                     */
	private Properties ctx;
	/** Window No                   */
	public final int WindowNo;
	/** Tab No                      */
	public final int TabNo;
	/** AD_Winmdow_ID               */
	public final int AD_Window_ID;
	/** AD_Tab_ID					*/
	public final int AD_Tab_ID;
	/** Is the Tab Read Only        */
	public final boolean tabReadOnly;

	/** Is Process Parameter        */
	private boolean isProcess = false;
	private boolean isFormField = false;

	/**
	 * Is Process Parameter To.
	 *
	 * NOTE: This one is set to true only if {@link #isProcess} is set.
	 */
	private boolean isProcessParameterTo = false;

	/**	Column name		*/
	private String       ColumnName = "";
	/**	Column sql		*/
	public String       ColumnSQL;

	// metas: adding column class
	/** Column class */
	public String ColumnClass;
	// metas end

	/**	Label			*/
	private String header = "";
	private Map<String, String> headerTrls = null; // lazy
	/**	DisplayType		*/
	private int          displayType = 0;
	/**	Table ID		*/
	private int          AD_Table_ID = 0;
	/**	Column ID */
	private int AD_Column_ID = 0;
	/** Field ID */
	public int AD_Field_ID = 0; // metas
	private GridFieldLayoutConstraints layoutConstraints = GridFieldLayoutConstraints.builder().build();
	private int			seqNo = 0;
	private int			seqNoGrid = 0;
	/**	Displayed		*/
	private boolean      IsDisplayed = false;
	/**	Displayed (grid mode) */
	private boolean      isDisplayedGrid = false;
	/**
	 * Indicates that the field is hidden from UI
	 * @task 09504
	 */
	private boolean isHiddenFromUI = false;
	/**	Dislay Logic	*/
	private String       DisplayLogic = "";
	private ILogicExpression DisplayLogicExpr; // metas: 03093
	/** Color Logic */
	private String ColorLogic = "";
	private IStringExpression ColorLogicExpr = IStringExpression.NULL; // metas-2009_0021_AP1_CR045
	/**	Default Value	*/
	public String       DefaultValue = "";
	/**	Mandatory (in UI) */
	private boolean IsMandatory = false;
	/**	Mandatory (in database/model) */
	private boolean IsMandatoryDB = false;
	/**	Read Only		*/
	public boolean      IsReadOnly = false;
	/**	Updateable		*/
	public boolean      IsUpdateable = false;
	/**	Always Updateable	*/
	public boolean      IsAlwaysUpdateable = false;
	/**	Heading Only	*/
	private boolean      IsHeading = false;
	/**	Field Only		*/
	private boolean      IsFieldOnly = false;
	/**	Display Encryption	*/
	private boolean IsEncryptedField = false;
	/**	Storage Encryption	*/
	public boolean      IsEncryptedColumn = false;
	/**	Find Selection		*/
	private boolean IsSelectionColumn = false;
	/**	Order By		*/
	public int          SortNo = 0;
	/**	Field Length		*/
	public int          FieldLength = 0;
	/**	Format enforcement		*/
	public String       VFormat = "";
	private String formatPattern = "";
	/**	Min. Value		*/
	public String       ValueMin = "";
	/**	Max. Value		*/
	public String       ValueMax = "";
	/**	Field Group		*/
	private FieldGroupVO fieldGroup = FieldGroupVO.NULL;
	/**	PK				*/
	public boolean      IsKey = false;
	/**	FK				*/
	private boolean      IsParent = false;
	/**	Process			*/
	public int          AD_Process_ID = 0;
	/**	Description		*/
	private String description = "";
	private Map<String, String> descriptionTrls = null; // lazy
	/**	Help			*/
	private String help = "";
	private Map<String, String> helpTrls = null; // lazy
	/**	Mandatory Logic	*/
	public String 		MandatoryLogic = "";
	private ILogicExpression MandatoryLogicExpr; // metas: 03093
	/**	Read Only Logic	*/
	public String       ReadOnlyLogic = "";
	private ILogicExpression ReadOnlyLogicExpr; // metas: 03093
	/**	Display Obscure	*/
	public String		ObscureType = null;
	/** Included Tab Height - metas-2009_0021_AP1_CR051 */
	public int IncludedTabHeight = 0; // metas

	/**	Validation rule */
	private int AD_Val_Rule_ID = -1; // metas: 03271
	/**	Reference Value			*/
	private int AD_Reference_Value_ID = 0;

	/**	Process Parameter Range		*/
	public boolean      isRange = false;
	/**	Process Parameter Value2	*/
	public String       DefaultValue2 = "";

	/** Lookup Value Object     */
	private MLookupInfo  lookupInfo = null;


	//*  Feature Request FR [ 1757088 ]
	public int          Included_Tab_ID = 0;

	/**  Autocompletion for textfields - Feature Request FR [ 1757088 ] */
	private boolean autocomplete = false;

	public boolean IsCalculated = false; // metas

	public String InfoFactoryClass = null;

	private String fieldEntityType = null;

	/**
	 *  Set Context including contained elements
	 *  @param newCtx new context
	 */
	public void setCtx (Properties newCtx)
	{
		ctx = newCtx;
	}   //  setCtx

	/**
	 *  Validate Fields and create LookupInfo if required
	 */
	private final void initFinish()
	{
		final IExpressionFactory expressionFactory = Services.get(IExpressionFactory.class);

		//  Not null fields
		if (DisplayLogic == null)
			DisplayLogic = "";
		DisplayLogicExpr = expressionFactory.compileOrDefault(DisplayLogic, ILogicExpression.TRUE, ILogicExpression.class); // metas: 03093

		// metas-2009_0021_AP1_CR045: begin
		if (ColorLogic == null)
			ColorLogic = "";
		ColorLogicExpr = expressionFactory.compileOrDefault(ColorLogic, IStringExpression.NULL, IStringExpression.class);
		// metas-2009_0021_AP1_CR045: end

		if (DefaultValue == null)
			DefaultValue = "";

		if (description == null)
			description = "";

		if (help == null)
			help = "";

		if (ReadOnlyLogic == null)
			ReadOnlyLogic = "";
		ReadOnlyLogicExpr = expressionFactory.compileOrDefault(ReadOnlyLogic, ILogicExpression.FALSE, ILogicExpression.class); // metas: 03093

		if (MandatoryLogic == null)
			MandatoryLogic = "";
		MandatoryLogicExpr = expressionFactory.compileOrDefault(MandatoryLogic, ILogicExpression.FALSE, ILogicExpression.class); // metas: 03093

		//
		// If EntityType is not displayed, hide this field
		if (!Check.isEmpty(fieldEntityType, true) && !UIDisplayedEntityTypes.isEntityTypeDisplayedInUIOrTrueIfNull(fieldEntityType))
		{
			this.isHiddenFromUI = true;
			this.IsDisplayed = false;
			this.isDisplayedGrid = false;
		}

		createLookupInfo(true); // metas : cg: task 02354 // tsa: always create the lookupInfo
	}   //  initFinish

	/**
	 * Create lookup info if the type is lookup and control the creation trough displayed param
	 *
	 * @param alwaysCreate
	 *            always create the lookup info, even if the field is not displayed
	 */
	// metas : cg: task 02354
	private void createLookupInfo(boolean alwaysCreate)
	{
		// Shall we create the MLookupInfo?
		if (lookupInfo != null)
		{
			return;
		}

		//  Create Lookup, if not ID
		final boolean displayed = this.IsDisplayed || this.isDisplayedGrid;
		if (DisplayType.isLookup(displayType) && (displayed || alwaysCreate))
		{
			try
			{
				if (this.lookupLoadFromColumn)
				{
					lookupInfo = MLookupFactory.getLookupInfo(WindowNo, AD_Column_ID, displayType);
				}
				else
				{
					lookupInfo = MLookupFactory.getLookupInfo(WindowNo, displayType,
							ColumnName, AD_Reference_Value_ID,
							IsParent, AD_Val_Rule_ID); // metas: 03271
				}
				lookupInfo.InfoFactoryClass = this.InfoFactoryClass;
			}
			catch (Exception e)     //  Cannot create Lookup
			{
				logger.error("No LookupInfo for {}", ColumnName, e);
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
	 * <li>{@link #isProcessParameterTo} is set to false</li>
	 * </ul>
	 *
	 * @param Ctx context
	 * @param windowNo window no
	 * @param tabNo tab no
	 * @param ad_Window_ID window id
	 * @param ad_Tab_ID tab id
	 * @param TabReadOnly r/o
	 */
	public GridFieldVO clone(Properties Ctx, int windowNo, int tabNo,
		int ad_Window_ID, int ad_Tab_ID,
		boolean TabReadOnly)
	{
		final GridFieldVO clone = new GridFieldVO(Ctx, windowNo, tabNo,  ad_Window_ID, ad_Tab_ID, TabReadOnly);
		//
		clone.isProcess = false;
		clone.isProcessParameterTo = false;
		clone.isFormField = false;
		//  Database Fields
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
		clone.IsSelectionColumn = IsSelectionColumn;
		clone.autocomplete = autocomplete;
		clone.SortNo = SortNo;
		clone.FieldLength = FieldLength;
		clone.VFormat = VFormat;
		clone.ValueMin = ValueMin;
		clone.ValueMax = ValueMax;
		clone.fieldGroup = fieldGroup;
		clone.IsKey = IsKey;
		clone.IsParent = IsParent;
//		clone.Callout = Callout;
		clone.AD_Process_ID = AD_Process_ID;
		clone.ReadOnlyLogic = ReadOnlyLogic;
		clone.ReadOnlyLogicExpr = ReadOnlyLogicExpr; // metas: 03093
		clone.MandatoryLogic = MandatoryLogic;
		clone.MandatoryLogicExpr = MandatoryLogicExpr; // metas: 03093
		clone.ObscureType = ObscureType;
		clone.Included_Tab_ID = Included_Tab_ID;
		clone.IncludedTabHeight = IncludedTabHeight; // metas-2009_0021_AP1_CR051
		//	Lookup
		clone.AD_Val_Rule_ID = AD_Val_Rule_ID; // metas: 03271
		clone.AD_Reference_Value_ID = AD_Reference_Value_ID;
		clone.lookupInfo = lookupInfo == null ? null : lookupInfo.cloneIt(windowNo);

		//  Process Parameter
		clone.isRange = isRange;
		clone.DefaultValue2 = DefaultValue2;

		clone.IsCalculated = IsCalculated; // metas: us215

		clone.fieldEntityType = fieldEntityType;
		clone.isHiddenFromUI = isHiddenFromUI;

		return clone;
	}	//	clone

	public GridFieldVO copy()
	{
		return clone(ctx, WindowNo, TabNo, AD_Window_ID, AD_Tab_ID, tabReadOnly);
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
	
	public int getTabNo()
	{
		return TabNo;
	}

	public int getDisplayType()
	{
		return this.displayType;
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

		//  Not null fields
		if (DisplayLogic == null)
			DisplayLogic = "";
		DisplayLogicExpr = expressionFactory.compileOrDefault(DisplayLogic, ILogicExpression.TRUE, ILogicExpression.class); // metas: 03093
	}

	public ILogicExpression getReadOnlyLogic()
	{
		return ReadOnlyLogicExpr;
	}

	public ILogicExpression getMandatoryLogic()
	{
		return MandatoryLogicExpr;
	}

	public IStringExpression getColorLogic()
	{
		return ColorLogicExpr;
	}

	public MLookupInfo getLookupInfo()
	{
		if (lookupInfo == null)
		{
			createLookupInfo(true);
		}
		return this.lookupInfo;
	}

	public void setIsDisplayed(boolean displayed)
	{
		if (this.IsDisplayed == displayed)
		{
			return;
		}
		this.IsDisplayed = displayed;
		//this.lookupInfo = null; // no need to reset lookup info
	}

	public boolean isDisplayed()
	{
		return this.IsDisplayed;
	}

	public boolean isAutocomplete()
	{
		return autocomplete;
	}

	public void setAutocomplete(boolean autocomplete)
	{
		this.autocomplete = autocomplete;
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

	public void setAD_Reference_Value_ID(final int AD_Reference_Value_ID)
	{
		if (this.AD_Reference_Value_ID == AD_Reference_Value_ID)
		{
			return;
		}

		this.AD_Reference_Value_ID = AD_Reference_Value_ID;
		this.lookupInfo = null; // reset lookup info
	}

	public void setColumnName(String columnName)
	{
		this.ColumnName = columnName;
	}

	public String getColumnName()
	{
		return this.ColumnName;
	}

	private boolean lookupLoadFromColumn = false;
	public void setLookupLoadFromColumn(boolean lookupLoadFromColumn)
	{
		if (this.lookupLoadFromColumn == lookupLoadFromColumn)
		{
			return;
		}
		this.lookupLoadFromColumn = lookupLoadFromColumn;
		this.lookupInfo = null; // reset
	}

	public int getAD_Table_ID()
	{
		return this.AD_Table_ID;
	}

	public Properties getCtx()
	{
		return ctx;
	}

	public int getAD_Column_ID()
	{
		return AD_Column_ID;
	}
	
	public void setAD_Column_ID(final int AD_Column_ID)
	{
		this.AD_Column_ID = AD_Column_ID;
	}

	public int getSeqNo()
	{
		return seqNo;
	}

	private void setSeqNo(int seqNo)
	{
		this.seqNo = seqNo;
	}

	public int getSeqNoGrid()
	{
		return seqNoGrid;
	}

	public void setSeqNoGrid(int seqNoGrid)
	{
		this.seqNoGrid = seqNoGrid;
	}

	public boolean isDisplayedGrid()
	{
		return isDisplayedGrid;
	}

	public void setIsDisplayedGrid(boolean isDisplayedGrid)
	{
		this.isDisplayedGrid = isDisplayedGrid;
	}

	public GridFieldLayoutConstraints getLayoutConstraints()
	{
		return layoutConstraints;
	}

	public int getAD_Field_ID()
	{
		return AD_Field_ID;
	}

	public boolean isProcessParameter()
	{
		return isProcess;
	}
	
	/** @return true if this is a custom form field */
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

	public FieldGroupVO getFieldGroup()
	{
		return fieldGroup;
	}

	public void setIsFieldOnly(boolean isFieldOnly)
	{
		this.IsFieldOnly = isFieldOnly;
	}

	public boolean isFieldOnly()
	{
		return IsFieldOnly;
	}

	public void setIsHeadingOnly(boolean isHeading)
	{
		this.IsHeading = isHeading;
	}

	public boolean isHeadingOnly()
	{
		return IsHeading;
	}

	public String getFieldEntityType()
	{
		return fieldEntityType;
	}

	/**
	 * @return true if this field shall be hidden from UI; in this case {@link #isDisplayed()} and {@link #isDisplayedGrid()} will also return false.
	 * @task 09504
	 */
	public boolean isHiddenFromUI()
	{
		return this.isHiddenFromUI;
	}

	public String getFormatPattern()
	{
		return formatPattern;
	}

	/** @return true if it's mandatory in UI */
	public boolean isMandatory()
	{
		return IsMandatory;
	}

	public void setMandatory(final boolean mandatory)
	{
		this.IsMandatory = mandatory;
	}

	/** @return true if it's mandatory in database */
	public boolean isMandatoryDB()
	{
		return IsMandatoryDB;
	}

	public String getHeader()
	{
		return header;
	}
	
	public Map<String, String> getHeaderTrls()
	{
		return headerTrls;
	}
	
	public void setHeader(String header)
	{
		this.header = header;
	}
	
	private void setHeader(final String adLanguage, final String headerTrl)
	{
		Check.assumeNotEmpty(adLanguage, "adLanguage is not empty");
		
		if(headerTrl == null)
		{
			return;
		}
		
		if(headerTrls == null)
		{
			headerTrls = new HashMap<>();
		}
		headerTrls.put(adLanguage, headerTrl);
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public Map<String, String> getDescriptionTrls()
	{
		return descriptionTrls;
	}
	
	public void setDescription(String description)
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

	public String getHelp()
	{
		return help;
	}
	
	public Map<String, String> getHelpTrls()
	{
		return helpTrls;
	}
	
	public void setHelp(String help)
	{
		this.help = help;
	}

	private void setHelp(final String adLanguage, final String helpTrl)
	{
		Check.assumeNotEmpty(adLanguage, "adLanguage is not empty");
		if(helpTrls == null)
		{
			helpTrls = new HashMap<>();
		}
		helpTrls.put(adLanguage, helpTrl);
	}

	public int getAD_Reference_Value_ID()
	{
		return AD_Reference_Value_ID;
	}
	
	public int getAD_Val_Rule_ID()
	{
		return AD_Val_Rule_ID;
	}

	/**
	 * Get Column Name or SQL .. with/without AS
	 *
	 * @param withAS include AS ColumnName for virtual columns in select statements
	 * @return column name
	 */
	public String getColumnSQL(boolean withAS)
	{
		// metas
		if (ColumnClass != null)
		{
			return "NULL";
		}
		// metas end
		if (ColumnSQL != null && ColumnSQL.length() > 0)
		{
			if (withAS)
				return ColumnSQL + " AS " + ColumnName;
			else
				return ColumnSQL;
		}
		return ColumnName;
	}	// getColumnSQL

	/**
	 * Is Virtual Column
	 *
	 * @return column is virtual
	 */
	public boolean isVirtualColumn()
	{
		if ((ColumnSQL != null && ColumnSQL.length() > 0)
				|| (ColumnClass != null && !"".equals(ColumnClass))) // metas: columns with a columnClass are also virtual
			return true;
		return false;
	}	// isColumnVirtual
	
	public int getIncluded_Tab_ID()
	{
		return Included_Tab_ID;
	}
	
	public boolean isReadOnly()
	{
		return IsReadOnly;
	}
	
	public void setIsReadOnly(final boolean readonly)
	{
		this.IsReadOnly = readonly;
	}
	
	public boolean isUpdateable()
	{
		return IsUpdateable;
	}
	
	public boolean isAlwaysUpdateable()
	{
		return IsAlwaysUpdateable;
	}
	
	public String getDefaultValue()
	{
		return DefaultValue;
	}
	
	public boolean isKey()
	{
		return IsKey;
	}
	
	public boolean isEncryptedField()
	{
		return IsEncryptedField;
	}
	
	public void setIsEncryptedField(final boolean isEncryptedField)
	{
		this.IsEncryptedField = isEncryptedField;
	}
	
	public boolean isEncryptedColumn()
	{
		return IsEncryptedColumn;
	}
	
	public int getSortNo()
	{
		return SortNo;
	}
	
	public boolean isSelectionColumn()
	{
		return IsSelectionColumn;
	}
}
