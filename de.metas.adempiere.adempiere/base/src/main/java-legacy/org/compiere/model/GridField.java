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
 * Contributor(s): Victor Perez e-Evolution victor.perez@e-evolution.com      *
 *****************************************************************************/
package org.compiere.model;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.swing.SwingUtilities;

import org.adempiere.ad.callout.api.ICalloutExecutor;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.TableAccessLevel;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.beans.DelayedPropertyChangeSupport;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Env.Scope;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;

import com.google.common.base.Objects;
import com.google.common.base.Supplier;

import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.service.IColumnBL;
import de.metas.logging.LogManager;

/**
 * Grid Field Model.
 * <p>
 * Fields are a combination of AD_Field (the display attributes) and AD_Column (the storage attributes).
 * <p>
 * The Field maintains the current edited value. If the value is changed, it fire PropertyChange "FieldValue". If the background is changed the PropertyChange "FieldAttribute" is fired. <br>
 * Usually editors listen to their fields.
 *
 * @author Jorg Janke
 * @author Victor Perez , e-Evolution.SC FR [ 1757088 ], [1877902] Implement JSR 223 Scripting APIs to Callout http://sourceforge.net/tracker/?func=detail&atid=879335&aid=1877902&group_id=176962 to FR
 *         [1877902]
 * @author Carlos Ruiz, qss FR [1877902]
 * @author Juan David Arboleda (arboleda), GlobalQSS, [ 1795398 ] Process Parameter: add display and readonly logic
 * @author Teo Sarca, teo.sarca@gmail.com <li>BF [ 2874646 ] GridField issue when a lookup is key https://sourceforge.net/tracker/?func=detail&aid=2874646&group_id=176962&atid=879332
 * @author victor.perez@e-evolution.com,www.e-evolution.com <li>BF [ 2910358 ] Error in context when a field is found in different tabs.
 *         https://sourceforge.net/tracker/?func=detail&aid=2910358&group_id=176962&atid=879332 <li>BF [ 2910368 ] Error in context when IsActive field is found in different
 *         https://sourceforge.net/tracker/?func=detail&aid=2910368&group_id=176962&atid=879332
 * @version $Id: GridField.java,v 1.5 2006/07/30 00:51:02 jjanke Exp $
 */
public class GridField
		implements Serializable, Evaluatee
		, ICalloutField
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1124123543602986028L;

	/**
	 * Field Constructor. requires initField for complete instantiation
	 *
	 * @param vo ValueObjecy
	 */
	public GridField(final GridFieldVO vo)
	{
		super();
		m_vo = vo;

		// Process Parameters have always Inserting=true because there are on a "new record"
		if (vo.isProcessParameter())
		{
			m_inserting = true;
		}

		// Set Attributes
		setError(false);
	}   // MField

	/** Value Object */
	private GridFieldVO m_vo;
	/** The Mnemonic */
	private char m_mnemonic = 0;

	private GridTab m_gridTab;

	/**
	 * Dispose
	 */
	protected void dispose()
	{
		m_propertyChangeListeners = null;

		//
		// Lookup
		final ExtendedMemorizingSupplier<Lookup> lookupSupplier = this.lookupSupplier;
		this.lookupSupplier = null;
		final Lookup lookup = lookupSupplier == null ? null : lookupSupplier.forget();
		if (lookup != null)
		{
			lookup.dispose();
		}
		// m_vo.lookupInfo = null;

		m_vo = null;
	}   // dispose

	/** Lookup supplier for this field */
	private ExtendedMemorizingSupplier<Lookup> lookupSupplier = ExtendedMemorizingSupplier.of(new Supplier<Lookup>()
	{
		@Override
		public Lookup get()
		{
			return createLookup();
		}
	});
	/** New Row / inserting */
	private boolean m_inserting = false;

	/** The current value */
	private Object m_value = null;
	/** The old to force Property Change */
	private static Object s_oldValue = new Object();
	/** The old/previous value */
	private Object m_oldValue = s_oldValue;
	/** Only fire Property Change if old value really changed */
	private boolean m_valueNoFire = true;
	/** Error Status */
	private boolean m_error = false;
	/** Parent Check */
	private Boolean m_parentValue = null;

	/** Property Change */
	private DelayedPropertyChangeSupport m_propertyChangeListeners = new DelayedPropertyChangeSupport(this);
	/** PropertyChange Name */
	public static final String PROPERTY = "FieldValue";
	/** Indicator for new Value */
	public static final String INSERTING = "FieldValueInserting";

	/** Error Value for HTML interface */
	private String m_errorValue = null;
	/** Error Value indicator for HTML interface */
	private boolean m_errorValueFlag = false;

	/** Logger */
	private static final Logger log = LogManager.getLogger(GridField.class);

	public Lookup createLookup()
	{
		if (!isLookup())
		{
			return null;
		}

		log.info("Loading lookup for {}", m_vo.getColumnName());

		// If the field it's not displayed, there is no point to create the lookup
		if (!isDisplayable())
		{
			return null;
		}

		final Properties ctx = getGridFieldContext();
		final int displayType = getDisplayType();
		final String columnName = getColumnName();
		final GridFieldVO vo = getVO();

		if (DisplayType.isLookup(displayType))
		{
			final MLookupInfo lookupInfo = vo.getLookupInfo();
			if (lookupInfo == null)
			{
				log.error("(" + vo.getColumnName() + ") - No LookupInfo");
				return null;
			}
			// Prevent loading of CreatedBy/UpdatedBy
			if (displayType == DisplayType.Table
					&& (columnName.equals("CreatedBy") || columnName.equals("UpdatedBy")))
			{
				lookupInfo.setIsCreadedUpdatedBy(true);
				lookupInfo.setDisplayType(DisplayType.Search);
			}
			//
			lookupInfo.setIsKey(isKey());
			return new MLookup(getCtx(), vo.getAD_Column_ID(), vo.getLookupInfo(), vo.TabNo);
		}
		else if (displayType == DisplayType.Location)   // not cached
		{
			return new MLocationLookup(ctx, vo.WindowNo);
		}
		else if (displayType == DisplayType.Locator)
		{
			return new MLocatorLookup(ctx, vo.WindowNo);
		}
		else if (displayType == DisplayType.Account)    // not cached
		{
			return new MAccountLookup(ctx, vo.WindowNo);
		}
		else if (displayType == DisplayType.PAttribute)    // not cached
		{
			return new MPAttributeLookup(ctx, vo.WindowNo);
		}
		else
		{
			return null;
		}
	}

	// metas: end

	/**
	 * Get Lookup, may return null
	 *
	 * @return lookup
	 */
	public Lookup getLookup()
	{
		final Supplier<Lookup> lookupSupplier = this.lookupSupplier; // thread safe
		return lookupSupplier == null ? null : lookupSupplier.get();
	}   // getLookup

	/**
	 * Is this field a Lookup?.
	 *
	 * @return true if lookup field
	 */
	public boolean isLookup()
	{
		if (m_vo.IsKey)
		{
			// Keys are not lookups
			return false;
		}

		return DisplayType.isAnyLookup(m_vo.getDisplayType());
	}   // isLookup

	/**
	 * Refresh Lookup if the lookup is unstable
	 *
	 * @return true if lookup is validated
	 */
	public boolean refreshLookup()
	{
		final Lookup lookup = getLookup();

		if (lookup == null)
		{
			// no lookup, nothing to refresh
			return true;
		}

		// if there is a validation string, the lookup is unstable
		if(!lookup.hasValidation())
		{
			return true;
		}
		
		//
		lookup.refresh();
		return lookup.isValidated();
	}   // refreshLookup

	/**
	 * Get a list of variables, this field is dependent on. - for display purposes or - for lookup purposes
	 *
	 * @return ArrayList
	 */
	public List<String> getDependentOn()
	{
		final List<String> list = new ArrayList<String>();
		// Display
		list.addAll(m_vo.getDisplayLogic().getParameters());
		list.addAll(m_vo.getDisplayLogic().getParameters()); // metas: 03093
		list.addAll(m_vo.getReadOnlyLogic().getParameters()); // metas: 03093
		list.addAll(m_vo.getMandatoryLogic().getParameters()); // metas: 03093
		list.addAll(m_vo.getColorLogic().getParameters()); // metas-2009_0021_AP1_CR045
		// Lookup
		final Lookup lookup = getLookup();
		if (lookup != null)
		{
			list.addAll(lookup.getParameters());
		}
		//
		if (!list.isEmpty() && LogManager.isLevelFiner())
		{
			log.trace("(" + m_vo.getColumnName() + ") " + list);
		}
		return list;
	}   // getDependentOn

	/**************************************************************************
	 * Set Error. Used by editors to set the color
	 *
	 * @param error true if error
	 */
	public void setError(boolean error)
	{
		m_error = error;
	}	// setBackground

	/**
	 * Get Background Error.
	 *
	 * @return error
	 */
	public boolean isError()
	{
		return m_error;
	}	// isError

	/**
	 * Is it Mandatory to enter for user? Mandatory checking is dome in MTable.getMandatory
	 *
	 * @param checkContext - check environment (requires correct row position)
	 * @return true if mandatory
	 */
	public boolean isMandatory(boolean checkContext)
	{
		// Do we have a mandatory rule
		// metas: 03093
		if (checkContext)
		{
			// boolean retValue = Evaluator.evaluateLogic(this, m_vo.MandatoryLogic);
			final Evaluatee evaluationCtx = createEvaluationContext(null);
			final ILogicExpression mandatoryLogic = m_vo.getMandatoryLogic();
			final boolean mandatory = mandatoryLogic.evaluate(evaluationCtx, true); // ignoreUparsable=true //failOnMissingParam=false
			log.trace(m_vo.getColumnName() + " Mandatory(" + mandatoryLogic + ") => Mandatory-" + mandatory);
			if (mandatory)
				return true;
		}

		// Not mandatory
		if (!m_vo.isMandatory() || isVirtualColumn())
			return false;

		// Numeric Keys and Created/Updated as well as
		// DocumentNo/Value/ASI ars not mandatory (persistency layer manages them)
		if (m_gridTab != null &&  // if gridtab doesn't exist then it's not a window field (probably a process parameter field)
				((m_vo.IsKey && m_vo.getColumnName().endsWith("_ID"))
						|| m_vo.getColumnName().startsWith("Created") || m_vo.getColumnName().startsWith("Updated")
						|| m_vo.getColumnName().equals("Value")
						|| m_vo.getColumnName().equals("DocumentNo")
						|| m_vo.getColumnName().equals("M_AttributeSetInstance_ID") 	// 0 is valid
				))
			return false;

		// Mandatory if displayed
		return isDisplayed(checkContext);
	}	// isMandatory

	/**
	 * @see #isEditablePara(Properties)
	 */
	@Deprecated
	public boolean isEditablePara(boolean checkContext)
	{
		final Properties ctx = checkContext ? getGridFieldContext() : null;
		return isEditablePara(ctx);

	}

	/**
	 * Is parameter Editable - checks if parameter is Read Only and if {@link #isDisplayed(Properties)}
	 *
	 * @param ctx context to be used for evaluation
	 * @return true, if editable
	 */
	public boolean isEditablePara(final Properties ctx)
	{
		if (ctx != null)
		{
			final Evaluatee evaluationCtx = createEvaluationContext(ctx);
			final ILogicExpression readOnlyLogic = m_vo.getReadOnlyLogic();
			final boolean readWrite = !readOnlyLogic.evaluate(evaluationCtx, true); // ignoreUnparsable=true // metas: 03093
			log.trace(m_vo.getColumnName() + " R/O(" + readOnlyLogic + ") => R/W-" + readWrite);
			if (!readWrite)
			{
				return false;
			}
		}

		// ultimately visibility decides
		return isDisplayed(ctx);
	}

	/**
	 * Is it Editable - checks IsActive, IsUpdateable, and isDisplayed
	 *
	 * @param checkContext if true checks Context for Active, IsProcessed, LinkColumn
	 * @return true, if editable
	 */
	public boolean isEditable(boolean checkContext)
	{
		// metas: begin
		return isEditable(checkContext ? getGridFieldContext() : null);
	}

	public boolean isEditable(Properties rowCtx)
	{
		return isEditable(rowCtx, GridTabLayoutMode.SingleRowLayout);
	}

	public boolean isEditable(final Properties rowCtx, final GridTabLayoutMode tabLayoutMode)
	{
		final IColumnBL columnBL = Services.get(IColumnBL.class);
		final boolean checkContext = rowCtx != null;

		//
		// Virtual columns are always readonly
		if (isVirtualColumn())
		{
			return false;
		}

		//
		// Fields always enabled (are usually not updateable and are usually buttons),
		// even if the parent tab is processed/not active
		if (m_vo.getColumnName().equals("Posted")
				|| (columnBL.isRecordColumnName(m_vo.getColumnName()) && getDisplayType() == DisplayType.Button))	// Zoom
		{
			return true;
		}

		//
		// Tab or field is R/O (staticly configured in application dictionary)
		if (m_vo.tabReadOnly || m_vo.IsReadOnly)
		{
			log.trace(m_vo.getColumnName() + " NO - TabRO=" + m_vo.tabReadOnly + ", FieldRO=" + m_vo.IsReadOnly);
			return false;
		}

		//
		// Not Updateable - only editable if new updateable row
		if (!m_vo.IsUpdateable && !m_inserting)
		{
			log.trace(m_vo.getColumnName() + " NO - FieldUpdateable=" + m_vo.IsUpdateable);
			return false;
		}

		//
		// Field is the Link Column of the tab
		if (m_vo.getColumnName().equals(Env.getContext(getGridFieldContext(), m_vo.WindowNo, m_vo.TabNo, GridTab.CTX_LinkColumnName)))
		{
			log.trace(m_vo.getColumnName() + " NO - LinkColumn");
			return false;
		}

		// Role Access & Column Access
		if (checkContext)
		{
			int AD_Client_ID = Env.getContextAsInt(rowCtx, m_vo.WindowNo, m_vo.TabNo, "AD_Client_ID");
			int AD_Org_ID = Env.getContextAsInt(rowCtx, m_vo.WindowNo, m_vo.TabNo, "AD_Org_ID");
			String keyColumn = Env.getContext(rowCtx, m_vo.WindowNo, m_vo.TabNo, GridTab.CTX_KeyColumnName);
			if ("EntityType".equals(keyColumn))
				keyColumn = "AD_EntityType_ID";
			if (!keyColumn.endsWith("_ID"))
				keyColumn += "_ID";			// AD_Language_ID
			int Record_ID = Env.getContextAsInt(rowCtx, m_vo.WindowNo, m_vo.TabNo, keyColumn);
			int AD_Table_ID = m_vo.getAD_Table_ID();

			final IUserRolePermissions role = Env.getUserRolePermissions(getGridFieldContext());
			if (!role.canUpdate(AD_Client_ID, AD_Org_ID, AD_Table_ID, Record_ID, false))
			{
				return false;
			}
			if (!role.isColumnAccess(AD_Table_ID, m_vo.getAD_Column_ID(), false))
			{
				return false;
			}
		}

		// Do we have a readonly rule
		if (checkContext)
		{
			final Evaluatee evaluationCtx = createEvaluationContext(rowCtx);
			final ILogicExpression readOnlyLogic = m_vo.getReadOnlyLogic();
			final boolean readWrite = !readOnlyLogic.evaluate(evaluationCtx, true); // ignoreUnparsable=true // metas: 03093
			log.trace(m_vo.getColumnName() + " R/O(" + readOnlyLogic + ") => R/W-" + readWrite);
			if (!readWrite)
			{
				return false;
			}
		}

		// BF [ 2910368 ]
		// Always editable if Activedefault
		// FIXME: i think we shall drop this hardcoded checking and update it in application dictionary if really needed
		if (checkContext
				&& isActive(rowCtx)
				&& (m_vo.getColumnName().equals("Processing")
						|| m_vo.getColumnName().equals("PaymentRule")
						|| m_vo.getColumnName().equals("DocAction")
						|| m_vo.getColumnName().equals("GenerateTo")))
		{
			return true;
		}

		//
		// If parent tab (if any) is processed or Not Active
		// => consider this field as readonly
		if (checkContext
				&& !m_vo.IsAlwaysUpdateable // allow editing AlwaysUpdateable fields, even if parent is processed or not active
				&& isParentTabProcessedOrNotActive(rowCtx))
		{
			return false; // not editable
		}

		//
		// Check if THIS tab is Processed or Not Active
		if (checkContext
				&& !m_vo.IsAlwaysUpdateable // 03375 don't return false here, if IsAlwaysUpdateable
				&& isProcessed(rowCtx))
		{
			return false;
		}


		//
		// IsActive field is editable, if record not processed
		if ("IsActive".equals(m_vo.getColumnName()))
		{
			return true;
		}

		// BF [ 2910368 ]
		// Record is not Active
		if (checkContext && !isActive(rowCtx))
		{
			return false;
		}

		//
		// ultimately visibility decides
		return isDisplayed(rowCtx, tabLayoutMode);
	}	// isEditable

	/**
	 * Set Inserting (allows to enter not updateable fields). Reset when setting the Field Value
	 *
	 * @param inserting true if inserting
	 */
	public void setInserting(boolean inserting)
	{
		m_inserting = inserting;
	}   // setInserting

	/**************************************************************************
	 * Create default value. <br>
	 * Note that this value is also validated. e.g. if a system preference is not correct give the field's validation rule, then <code>null</code> will be returned instead of that value.
	 *
	 * <pre>
	 * 	(a) Key/Parent/IsActive/SystemAccess
	 *      (b) SQL Default
	 * 	(c) Column Default		//	system integrity
	 *      (d) User Preference
	 * 	(e) System Preference
	 * 	(f) DataType Defaults
	 *
	 *  Don't default from Context => use explicit defaultValue
	 *  (would otherwise copy previous record)
	 * </pre>
	 *
	 * @return default value or null
	 */
	public Object getDefault()
	{
		final Object result = getDefaultNoCheck();
		if (validateValue(result))
		{
			return result;
		}
		return null;
	}

	private Object getDefaultNoCheck()
	{
		// TODO: until refactoring, please keep in sync with org.adempiere.model.GeneralCopyRecordSupport.getDefault(PO, String)

		final Properties ctx = getGridFieldContext();
		/**
		 * (a) Key/Parent/IsActive/SystemAccess
		 */

		// No defaults for these fields
		final int displayType = m_vo.getDisplayType();
		if (m_vo.IsKey || displayType == DisplayType.RowID
				|| DisplayType.isLOB(displayType))
			return null;
		// Set Parent to context if not explitly set
		if (isParentValue()
				&& (m_vo.DefaultValue == null || m_vo.DefaultValue.length() == 0))
		{
			String parent = Env.getContext(ctx, m_vo.WindowNo, m_vo.getColumnName());
			log.debug("[Parent] " + m_vo.getColumnName() + "=" + parent);
			return createDefault(parent);
		}
		// Always Active
		if (m_vo.getColumnName().equals("IsActive"))
		{
			log.debug("[IsActive] " + m_vo.getColumnName() + "=Y");
			return "Y";
		}

		// Set Client & Org to System, if System access
		final TableAccessLevel accessLevel = getTableAccessLevel(ctx);
		if (accessLevel.isSystemOnly()
				&& (m_vo.getColumnName().equals("AD_Client_ID") || m_vo.getColumnName().equals("AD_Org_ID")))
		{
			log.debug("[SystemAccess] {}=0", m_vo.getColumnName());
			return 0;
		}
		// Set Org to System, if Client access
		else if (accessLevel == TableAccessLevel.SystemPlusClient
				&& m_vo.getColumnName().equals("AD_Org_ID"))
		{
			log.debug("[ClientAccess] {}=0", m_vo.getColumnName());
			return 0;
		}

		/**
		 * (b) SQL Statement (for data integity & consistency)
		 */
		String defStr = "";
		if (m_vo.DefaultValue.startsWith("@SQL="))
		{
			String sql = m_vo.DefaultValue.substring(5);			// w/o tag
			// sql = Env.parseContext(ctx, m_vo.WindowNo, sql, false, true); // replace variables
			// hengsin, capture unparseable error to avoid subsequent sql exception
			sql = Env.parseContext(ctx, m_vo.WindowNo, sql, false, false);	// replace variables
			if (sql.equals(""))
			{
				log.warn("(" + m_vo.getColumnName() + ") - Default SQL variable parse failed: "
						+ m_vo.DefaultValue);
			}
			else
			{
				PreparedStatement stmt = null;
				ResultSet rs = null;
				try
				{
					stmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
					rs = stmt.executeQuery();
					if (rs.next())
						defStr = rs.getString(1);
					else
						log.warn("(" + m_vo.getColumnName() + ") - no Result: " + sql);
				}
				catch (SQLException e)
				{
					log.warn("(" + m_vo.getColumnName() + ") " + sql, e);
				}
				finally
				{
					DB.close(rs, stmt);
				}
			}
			if (defStr != null && defStr.length() > 0)
			{
				log.debug("[SQL] " + m_vo.getColumnName() + "=" + defStr);
				return createDefault(defStr);
			}
		}	// SQL Statement

		/**
		 * (c) Field DefaultValue === similar code in AStartRPDialog.getDefault ===
		 */
		if (!m_vo.DefaultValue.equals("") && !m_vo.DefaultValue.startsWith("@SQL="))
		{
			defStr = "";		// problem is with texts like 'sss;sss'
			// It is one or more variables/constants
			StringTokenizer st = new StringTokenizer(m_vo.DefaultValue, ",;", false);
			while (st.hasMoreTokens())
			{
				defStr = st.nextToken().trim();
				if (defStr.equals("@SysDate@"))				// System Time
				{
					return SystemTime.asTimestamp();
				}
				else if ("@NULL@".equalsIgnoreCase(defStr))
				{
					return null;
				}
				else if (defStr.indexOf('@') != -1)			// it is a variable
				{
					defStr = Env.getContext(ctx, m_vo.WindowNo, defStr.replace('@', ' ').trim());
				}
				else if (defStr.indexOf("'") != -1)			// it is a 'String'
				{
					defStr = defStr.replace('\'', ' ').trim();
				}

				if (!defStr.equals(""))
				{
					log.debug("[DefaultValue] " + m_vo.getColumnName() + "=" + defStr);
					return createDefault(defStr);
				}
			}	// while more Tokens
		}	// Default value

		/**
		 * (d) Preference (user) - P|
		 */
		defStr = Env.getPreference(ctx, m_vo.AD_Window_ID, m_vo.getColumnName(), false);
		if (!defStr.equals(""))
		{
			log.debug("[UserPreference] " + m_vo.getColumnName() + "=" + defStr);
			return createDefault(defStr);
		}

		/**
		 * (e) Preference (System) - # $
		 */
		defStr = Env.getPreference(ctx, m_vo.AD_Window_ID, m_vo.getColumnName(), true);
		if (!defStr.equals(""))
		{
			log.debug("[SystemPreference] " + m_vo.getColumnName() + "=" + defStr);
			return createDefault(defStr);
		}

		/**
		 * (f) DataType defaults
		 */

		// Button to N
		if (displayType == DisplayType.Button && !m_vo.getColumnName().endsWith("_ID"))
		{
			log.debug("[Button=N] " + m_vo.getColumnName());
			return "N";
		}
		// CheckBoxes default to No
		if (displayType == DisplayType.YesNo)
		{
			log.debug("[YesNo=N] " + m_vo.getColumnName());
			return "N";
		}
		// lookups with one value
		// if (DisplayType.isLookup(m_vo.displayType) && m_lookup.getSize() == 1)
		// {
		// /** @todo default if only one lookup value */
		// }
		// IDs remain null
		if (m_vo.getColumnName().endsWith("_ID"))
		{
			log.debug("[ID=null] " + m_vo.getColumnName());
			return null;
		}
		// actual Numbers default to zero
		if (DisplayType.isNumeric(displayType))
		{
			log.debug("[Number=0] " + m_vo.getColumnName());
			return createDefault("0");
		}

		/**
		 * No resolution
		 */
		log.debug("[NONE] " + m_vo.getColumnName());
		return null;
	}	// getDefault

	private final TableAccessLevel getTableAccessLevel(final Properties ctx)
	{
		final String accessLevelStr = Env.getContext(ctx, m_vo.WindowNo, m_vo.TabNo, GridTab.CTX_AccessLevel);
		if (Check.isEmpty(accessLevelStr))
		{
			return TableAccessLevel.None;
		}
		return TableAccessLevel.forAccessLevel(accessLevelStr);
	}

	/**
	 * Create Default Object type.
	 *
	 * <pre>
	 * 	Integer 	(IDs, Integer)
	 * 	BigDecimal 	(Numbers)
	 * 	Timestamp	(Dates)
	 * 	Boolean		(YesNo)
	 * 	default: String
	 * </pre>
	 *
	 * @param value string
	 * @return type dependent converted object
	 */
	private Object createDefault(final String value)
	{
		return DisplayType.convertToDisplayType(value, getColumnName(), getDisplayType());
	}	// createDefault

	/**
	 * Validate initial Field Value. Called from MTab.dataNew and MTab.setCurrentRow when inserting
	 *
	 * @return true if valid
	 */
	// this method contained basically the code of validateValue(Object)
	public boolean validateValue()
	{
		if (!validateValue(m_value))
		{
			log.trace(m_vo.getColumnName() + " - value not valid - set to null");
			if (m_value == null || Check.isEmpty(m_value.toString()))
			{
				// only calling setValue if m_value is not empty, because the old method also only called it in that case
				setValue(null, m_inserting);
			}
			m_error = true;
			return false;
		}
		return true;
	}

	/**
	 * Method can be used to validate a given value without actually settings it.
	 *
	 * @param value
	 * @return
	 */
	private boolean validateValue(final Object value)
	{
		// null
		if (value == null || value.toString().length() == 0)
		{
			if (isMandatory(true))
			{
				return false;
			}
			else
				return true;
		}

		// Search not cached
		final Lookup lookup = getLookup();
		if (getDisplayType() == DisplayType.Search && lookup != null)
		{
			// need to re-set invalid values - OK BPartner in PO Line - not OK SalesRep in Invoice
			if (lookup.getDirect(IValidationContext.NULL, value, false, true) == null)
			{
				return false;
			}
			return true;
		}

		// cannot be validated
		if (!isLookup()
				|| lookup == null
				|| lookup.containsKey(value))
		{
			return true;
		}

		// it's not null, a lookup and has the key
		if (isKey() || isParentValue())		// parents/ket are not validated
		{
			return true;
		}

		return false;
	}   // validateValue

	/**************************************************************************
	 * Is the Column Visible ?
	 *
	 * @param checkContext - check environment (requires correct row position)
	 * @return true, if visible
	 */
	public boolean isDisplayed(boolean checkContext)
	{
		// metas: begin
		return isDisplayed(checkContext ? getGridFieldContext() : null);
	}

	public boolean isDisplayed(final Properties rowCtx)
	{
		return isDisplayed(rowCtx, GridTabLayoutMode.SingleRowLayout);
	}

	public boolean isDisplayed(final Properties rowCtx, final GridTabLayoutMode tabLayoutMode)
	{
		final boolean checkContext = rowCtx != null;
		// metas: end
		// ** static content **
		// not displayed
		if (!m_vo.isDisplayed(tabLayoutMode))
			return false;
		// no restrictions
		// metas: 03093: not needed
		// if (m_vo.DisplayLogic.equals(""))
		// return true;

		// ** dynamic content **
		if (checkContext)
		{
			final Evaluatee evaluationCtx = createEvaluationContext(rowCtx);
			final ILogicExpression displayLogic = m_vo.getDisplayLogic();
			final boolean displayed = displayLogic.evaluate(evaluationCtx, true); // ignoreUnparsable=true // metas: 03093
			log.trace(m_vo.getColumnName() + " (" + displayLogic + ") => " + displayed);
			return displayed;
		}
		return true;
	}	// isDisplayed

	/**
	 * Get Variable Value (Evaluatee)
	 *
	 * @param variableName name
	 * @return value
	 */
	@Override
	public String get_ValueAsString(String variableName)
	{
		final Properties ctx = getGridFieldContext();
		final boolean onlyWindow = true;
		return Env.getContext(ctx, m_vo.WindowNo, variableName, onlyWindow);
	}	// get_ValueAsString

	/**************************************************************************
	 * Get Column Name
	 *
	 * @return column name
	 */
	@Override
	public String getColumnName()
	{
		if(m_vo == null)
		{
			log.warn("{} was already disposed", this);
			return null;
		}
		return m_vo.getColumnName();
	}	// getColumnName

	/**
	 * Get Column Name or SQL .. with/without AS
	 *
	 * @param withAS include AS ColumnName for virtual columns in select statements
	 * @return column name
	 */
	public String getColumnSQL(boolean withAS)
	{
		return m_vo.getColumnSQL(withAS);
	}	// getColumnSQL

	/**
	 * Is Virtual Column
	 *
	 * @return column is virtual
	 */
	public boolean isVirtualColumn()
	{
		return m_vo.isVirtualColumn();
	}	// isColumnVirtual

	/**
	 * Get Header
	 *
	 * @return header
	 */
	public String getHeader()
	{
		return m_vo.getHeader();
	}

	/**
	 * Get Display Type
	 *
	 * @return dt
	 */
	public int getDisplayType()
	{
		return m_vo.getDisplayType();
	}

	/**
	 * Get AD_Reference_Value_ID
	 *
	 * @return reference value
	 */
	public int getAD_Reference_Value_ID()
	{
		return m_vo.getAD_Reference_Value_ID();
	}

	/**
	 * Get AD_Window_ID
	 *
	 * @return window
	 */
	public int getAD_Window_ID()
	{
		return m_vo.AD_Window_ID;
	}

	/**
	 * Get Window No
	 *
	 * @return window no
	 */
	@Override
	public int getWindowNo()
	{
		return m_vo.WindowNo;
	}

	/**
	 * Get AD_Column_ID
	 *
	 * @return column
	 */
	public int getAD_Column_ID()
	{
		return m_vo.getAD_Column_ID();
	}

	public GridFieldLayoutConstraints getLayoutConstraints()
	{
		return m_vo.getLayoutConstraints();
	}

	/**
	 * Get Display Length
	 *
	 * @return display
	 */
	public int getDisplayLength()
	{
		return getLayoutConstraints().getDisplayLength();
	}

	/**
	 * Is Displayed
	 *
	 * @return true if displayed
	 */
	public boolean isDisplayed()
	{
		return m_vo.isDisplayed();
	}

	public boolean isDisplayed(final GridTabLayoutMode tabLayoutMode)
	{
		return m_vo.isDisplayed(tabLayoutMode);
	}

	/**
	 * Checks if this field CAN be displayed.
	 *
	 * It's is possible to not be displayed at the moment, but user can display it anytime
	 *
	 * @return true if this column CAN be displayed
	 */
	public boolean isDisplayable()
	{
		return m_vo.isDisplayed(GridTabLayoutMode.SingleRowLayout);
	}

	/**
	 * Get Default Value
	 *
	 * @return default
	 */
	public String getDefaultValue()
	{
		return m_vo.DefaultValue;
	}

	/**
	 * Is ReadOnly
	 *
	 * @return true if read only
	 */
	public boolean isReadOnly()
	{
		if (isVirtualColumn())
			return true;
		return m_vo.IsReadOnly;
	}

	/**
	 * Is Updateable
	 *
	 * @return true if updateable
	 */
	public boolean isUpdateable()
	{
		if (isVirtualColumn())
			return false;
		return m_vo.IsUpdateable;
	}

	/**
	 * Is Autocomplete
	 *
	 * @return true if autocomplete
	 */
	public boolean isAutocomplete()
	{
		return m_vo.isAutocomplete();
	}

	/**
	 * Set auto-complete on {@link #m_vo}
	 *
	 * @param autoComplete
	 */
	public void setAutocomplete(final boolean autoComplete)
	{
		m_vo.setAutocomplete(autoComplete);
	}

	/**
	 * Is Always Updateable
	 *
	 * @return true if always updateable
	 */
	public boolean isAlwaysUpdateable()
	{
		if (isVirtualColumn() || !m_vo.IsUpdateable)
			return false;
		return m_vo.IsAlwaysUpdateable;
	}

	/**
	 * Is Heading
	 *
	 * @return heading
	 */
	public boolean isHeading()
	{
		return m_vo.isHeadingOnly();
	}

	/**
	 * Is Field Only
	 *
	 * @return field only
	 */
	public boolean isFieldOnly()
	{
		return m_vo.isFieldOnly();
	}

	/**
	 * Is Encrypted Field (display)
	 *
	 * @return encrypted field
	 */
	public boolean isEncryptedField()
	{
		return m_vo.isEncryptedField();
	}

	/**
	 * Is Encrypted Field (display) or obscured
	 *
	 * @return encrypted field
	 */
	public boolean isEncrypted()
	{
		if (m_vo.isEncryptedField())
			return true;
		String ob = getObscureType();
		if (ob != null && ob.length() > 0)
			return true;
		return m_vo.getColumnName().equals("Password");
	}

	/**
	 * Is Encrypted Column (data)
	 *
	 * @return encrypted column
	 */
	public boolean isEncryptedColumn()
	{
		return m_vo.IsEncryptedColumn;
	}

	/**
	 * Is Selection Column
	 *
	 * @return selection
	 */
	public boolean isSelectionColumn()
	{
		return m_vo.isSelectionColumn();
	}

	/**
	 * Get Obscure Type
	 *
	 * @return obscure
	 */
	public String getObscureType()
	{
		return m_vo.ObscureType;
	}

	/**
	 * Get Sort No
	 *
	 * @return sort
	 */
	public int getSortNo()
	{
		return m_vo.SortNo;
	}

	/**
	 * Get Field Length
	 *
	 * @return field length
	 */
	public int getFieldLength()
	{
		return m_vo.FieldLength;
	}

	/**
	 * Get VFormat
	 *
	 * @return format
	 */
	public String getVFormat()
	{
		return m_vo.VFormat;
	}

	/**
	 * Get Value Min
	 *
	 * @return min
	 */
	public String getValueMin()
	{
		return m_vo.ValueMin;
	}

	/**
	 * Get Value Max
	 *
	 * @return max
	 */
	public String getValueMax()
	{
		return m_vo.ValueMax;
	}

	/** @return field group definition */
	public FieldGroupVO getFieldGroup()
	{
		return m_vo.getFieldGroup();
	}

	/**
	 * Key
	 *
	 * @return key
	 */
	public boolean isKey()
	{
		return m_vo.IsKey;
	}

	/**
	 * Parent Column
	 *
	 * @return parent column
	 */
	public boolean isParentColumn()
	{
		return m_vo.IsParent;
	}

	/**
	 * Parent Link Value
	 *
	 * @return parent value
	 */
	public boolean isParentValue()
	{
		if (m_parentValue != null)
			return m_parentValue.booleanValue();
		if (!DisplayType.isID(m_vo.getDisplayType()) || m_vo.TabNo == 0)
			m_parentValue = Boolean.FALSE;
		else
		{
			String LinkColumnName = Env.getContext(getGridFieldContext(), m_vo.WindowNo, m_vo.TabNo, GridTab.CTX_LinkColumnName);
			if (LinkColumnName == null || LinkColumnName.length() == 0)
				m_parentValue = Boolean.FALSE; // teo_sarca, [ 1673886 ]
			else
				m_parentValue = Boolean.valueOf(m_vo.getColumnName().equals(LinkColumnName));
			if (m_parentValue)
				log.info(m_parentValue
						+ " - Link(" + LinkColumnName + ", W=" + m_vo.WindowNo + ",T=" + m_vo.TabNo
						+ ") = " + m_vo.getColumnName());
			else
				m_parentValue = Boolean.valueOf(isIndirectParentValue());
		}
		return m_parentValue.booleanValue();
	}	// isParentValue

	/**
	 * Get AD_Process_ID
	 *
	 * @return process
	 */
	public int getAD_Process_ID()
	{
		return m_vo.AD_Process_ID;
	}

	/**
	 * Get Description
	 *
	 * @return description
	 */
	public String getDescription()
	{
		return m_vo.getDescription();
	}

	/**
	 * Get Help
	 *
	 * @return help
	 */
	public String getHelp()
	{
		return m_vo.getHelp();
	}

	/**
	 * Get AD_Tab_ID
	 *
	 * @return tab
	 */
	public int getAD_Tab_ID()
	{
		return m_vo.AD_Tab_ID;
	}

	/**
	 * Get VO
	 *
	 * @return value object
	 */
	public GridFieldVO getVO()
	{
		return m_vo;
	}

	/**
	 * Set Value to null.
	 *
	 * @param updateContext if true, field value (i.e. null) will be exported to context
	 */
	public void setValueToNull(final boolean updateContext)
	{
		// log.debug(ColumnName + "=" + newValue);
		if (m_valueNoFire)      // set the old value
			m_oldValue = m_value;
		m_value = null;
		m_inserting = false;
		m_error = false;        // reset error

		if (updateContext)
		{
			// [ 1881480 ] Navigation problem between tabs
			//Env.setContext(getGridFieldContext(), m_vo.WindowNo, m_vo.getColumnName(), (String)m_value);
			updateContext();
		}

		// Does not fire, if same value
		m_propertyChangeListeners.firePropertyChange(PROPERTY, m_oldValue, m_value);
		// m_propertyChangeListeners.firePropertyChange(PROPERTY, s_oldValue, null);
	}   // setValue

	/**
	 * Set Value.
	 * <p>
	 * Update context, if not text or RowID; Send Bean PropertyChange if there is a change
	 *
	 * @param newValue new value
	 * @param inserting true if inserting
	 */
	public void setValue(Object newValue, boolean inserting)
	{
		// log.debug(ColumnName + "=" + newValue);
		if (m_valueNoFire)      // set the old value
			m_oldValue = m_value;
		m_value = newValue;
		m_inserting = inserting;
		m_error = false;        // reset error

		updateContext();

		// Does not fire, if same value
		Object oldValue = m_oldValue;
		if (inserting)
			oldValue = INSERTING;
		m_propertyChangeListeners.firePropertyChange(PROPERTY, oldValue, m_value);
	}   // setValue

	/**
	 * Update env. context with current value
	 */
	public void updateContext()
	{
		final Properties ctx = getGridFieldContextRW();
		final int displayType = m_vo.getDisplayType();
		// Set Context
		if (displayType == DisplayType.Text
				|| displayType == DisplayType.Memo
				|| displayType == DisplayType.TextLong
				|| displayType == DisplayType.Binary
				|| displayType == DisplayType.RowID
				|| isEncrypted())
		{
			;	// ignore
		}
		else if (m_value instanceof Boolean)
		{
			backupValue(); // teo_sarca [ 1699826 ]
			Env.setContext(ctx, m_vo.WindowNo, m_vo.getColumnName(), ((Boolean)m_value).booleanValue());
			Env.setContext(ctx, m_vo.WindowNo, m_vo.TabNo, m_vo.getColumnName(), m_value == null ? null : (((Boolean)m_value) ? "Y" : "N"));
		}
		else if (m_value instanceof Timestamp)
		{
			backupValue(); // teo_sarca [ 1699826 ]
			Env.setContext(ctx, m_vo.WindowNo, m_vo.getColumnName(), (Timestamp)m_value);
			Env.setContext(ctx, m_vo.WindowNo, m_vo.TabNo, m_vo.getColumnName(), m_value == null ? null : m_value.toString().substring(0, m_value.toString().indexOf(".")));
		}
		else
		{
			backupValue(); // teo_sarca [ 1699826 ]
			Env.setContext(ctx, m_vo.WindowNo, m_vo.getColumnName(), m_value == null ? null : m_value.toString());
			Env.setContext(ctx, m_vo.WindowNo, m_vo.TabNo, m_vo.getColumnName(), m_value == null ? null : m_value.toString());
		}
	}

	/**
	 * Get Value
	 *
	 * @return current value
	 */
	@Override
	public Object getValue()
	{
		return m_value;
	}   // getValue

	/**
	 * Set old/previous Value. (i.e. don't fire Property change) Used by VColor.setField
	 *
	 * @param value if false property change will always be fires
	 */
	public void setValueNoFire(boolean value)
	{
		m_valueNoFire = value;
	}   // setOldValue

	/**
	 * Get old/previous Value. Called from MTab.processCallout
	 *
	 * @return old value
	 */
	@Override
	public Object getOldValue()
	{
		return m_oldValue;
	}   // getOldValue
	
	public boolean isValueChanged()
	{
		final Object valueOld = getOldValue();
		final Object value = getValue();
		return Objects.equal(value, valueOld);
	}

	/**
	 * Set Error Value (the value, which cuased some Error)
	 *
	 * @param errorValue error message
	 */
	public void setErrorValue(String errorValue)
	{
		m_errorValue = errorValue;
		m_errorValueFlag = true;
	}   // setErrorValue

	/**
	 * Get Error Value (the value, which cuased some Error) <b>AND</b> reset it to null
	 *
	 * @return error value
	 */
	public String getErrorValue()
	{
		String s = m_errorValue;
		m_errorValue = null;
		m_errorValueFlag = false;
		return s;
	}   // getErrorValue

	/**
	 * Return true, if value has Error (for HTML interface) <b>AND</b> reset it to false
	 *
	 * @return has error
	 */
	public boolean isErrorValue()
	{
		boolean b = m_errorValueFlag;
		m_errorValueFlag = false;
		return b;
	}   // isErrorValue

	/**
	 * Overwrite default DisplayLength
	 *
	 * @param length new length
	 */
	public void setDisplayLength(int length)
	{
		getLayoutConstraints().setDisplayLength(length);
	}   // setDisplayLength

	/**
	 * Overwrite Displayed
	 *
	 * @param displayed trie if displayed
	 */
	public void setDisplayed(boolean displayed)
	{
		m_vo.setIsDisplayed(displayed);
	}   // setDisplayed

	/**
	 * Create Mnemonic for field
	 *
	 * @return no for r/o, client, org, document no
	 */
	public boolean isCreateMnemonic()
	{
		if (isReadOnly()
				|| m_vo.getColumnName().equals("AD_Client_ID")
				|| m_vo.getColumnName().equals("AD_Org_ID")
				|| m_vo.getColumnName().equals("DocumentNo"))
			return false;
		return true;
	}

	/**
	 * Get Label Mnemonic
	 *
	 * @return Mnemonic
	 */
	public char getMnemonic()
	{
		return m_mnemonic;
	}	// getMnemonic

	/**
	 * Set Label Mnemonic
	 *
	 * @param mnemonic Mnemonic
	 */
	public void setMnemonic(char mnemonic)
	{
		m_mnemonic = mnemonic;
	}	// setMnemonic

	/**
	 * String representation
	 *
	 * @return string representation
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("GridField[");

		if (m_vo != null)
		{
			sb.append(m_vo.getColumnName()).append("=").append(m_value);
			if (isKey())
				sb.append("(Key)");
			if (isParentColumn())
				sb.append("(Parent)");
		}
		else
		{
			sb.append("NoVO/Disposed");
		}
		sb.append("]");
		return sb.toString();
	}   // toString

	/**
	 * Extended String representation
	 *
	 * @return string representation
	 */
	public String toStringX()
	{
		StringBuffer sb = new StringBuffer("MField[");
		sb.append(m_vo.getColumnName()).append("=").append(m_value)
				.append(",DisplayType=").append(getDisplayType())
				.append("]");
		return sb.toString();
	}   // toStringX

	/*************************************************************************
	 * Remove Property Change Listener
	 *
	 * @param l listener
	 */
	public synchronized void removePropertyChangeListener(PropertyChangeListener l)
	{
		m_propertyChangeListeners.removePropertyChangeListener(l);
	}

	/**
	 * Add Property Change Listener
	 *
	 * @param l listener
	 */
	public synchronized void addPropertyChangeListener(PropertyChangeListener l)
	{
		m_propertyChangeListeners.addPropertyChangeListener(l);
	}

	/**************************************************************************
	 * Create Search Fields.
	 *
	 * Used by APanel.cmd_find and Viewer.cmd_find.
	 *
	 * @param ctx context
	 * @param WindowNo window
	 * @param TabNo tab no
	 * @param AD_Tab_ID tab
	 * @return array of all fields in display order
	 */
	public static GridField[] createSearchFields(Properties ctx, int WindowNo, int TabNo, int AD_Tab_ID)
	{
		final List<GridFieldVO> listVO = GridFieldVOsLoader.newInstance()
				.setCtx(ctx)
				.setWindowNo(WindowNo)
				.setTabNo(TabNo)
				.setAD_Window_ID(0)
				.setAD_Tab_ID(AD_Tab_ID)
				.setTabReadOnly(false)
				.load();

		//
		final GridField[] retValue = new GridField[listVO.size()];
		for (int i = 0; i < listVO.size(); i++)
		{
			retValue[i] = new GridField(listVO.get(i));
		}
		return retValue;
	}	// createFields

	/**
	 * bug[1637757] Check whether is indirect parent.
	 *
	 * @return boolean
	 */
	private boolean isIndirectParentValue()
	{
		final Properties ctx = getGridFieldContext();
		boolean result = false;
		int tabNo = m_vo.TabNo;
		int currentLevel = Env.getContextAsInt(ctx, m_vo.WindowNo, tabNo, GridTab.CTX_TabLevel);
		if (tabNo > 1 && currentLevel > 1)
		{
			while (tabNo >= 1 && !result)
			{
				tabNo--;
				int level = Env.getContextAsInt(ctx, m_vo.WindowNo, tabNo, GridTab.CTX_TabLevel);
				if (level > 0 && level < currentLevel)
				{
					String linkColumn = Env.getContext(ctx, m_vo.WindowNo, tabNo, GridTab.CTX_LinkColumnName);
					if (m_vo.getColumnName().equals(linkColumn))
					{
						result = true;
						log.info(result
								+ " - Link(" + linkColumn + ", W=" + m_vo.WindowNo + ",T=" + m_vo.TabNo
								+ ") = " + m_vo.getColumnName());
					}
					currentLevel = level;
				}
			}
		}
		return result;
	}

	/** Initial context value for this field - teo_sarca [ 1699826 ] */
	private String m_backupValue = null;

	/** Is the initial context value for this field backup ? - teo_sarca [ 1699826 ] */
	private boolean m_isBackupValue = false;

	/**
	 * Backup the context value
	 *
	 * @author teo_sarca [ 1699826 ]
	 */
	private final void backupValue()
	{
		if (!m_isBackupValue)
		{
			final Properties ctx = getGridFieldContext();
			m_backupValue = Env.getContext(ctx, m_vo.WindowNo, m_vo.getColumnName());
			if (LogManager.isLevelFinest())
				log.trace("Backup " + m_vo.WindowNo + "|" + m_vo.getColumnName() + "=" + m_backupValue);
			m_isBackupValue = true;
		}
	}

	/**
	 * Restore the backup value if any
	 *
	 * @author teo_sarca [ 1699826 ]
	 */
	public void restoreValue()
	{
		if (m_isBackupValue)
		{
			if (LogManager.isLevelFinest())
				log.trace("Restore " + m_vo.WindowNo + "|" + m_vo.getColumnName() + "=" + m_backupValue);
			final Properties ctx = getGridFieldContextRW();
			Env.setContext(ctx, m_vo.WindowNo, m_vo.getColumnName(), m_backupValue);
		}
	}

	/**
	 * Feature Request [1707462] Enable user to change VFormat on runtime
	 *
	 * @param strNewFormat VFormat mask
	 * @author fer_luck
	 */
	public void setVFormat(String strNewFormat)
	{
		m_vo.VFormat = strNewFormat;
	} // setVFormat

	/**
	 * Feature Request FR [ 1757088 ] Get the id tab include
	 *
	 * @return id Tab
	 */
	public int getIncluded_Tab_ID()
	{
		return m_vo.Included_Tab_ID;
	}

	/**
	 * Returns a list containing all existing entries of this field with the actual AD_Org_ID and AD_Client_ID.
	 *
	 * @return List of existing entries for this field
	 */
	public List<String> getEntries()
	{
		ArrayList<String> list = new ArrayList<String>();
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs2 = null;
		String sql = "";

		try
		{
			String tableName = null;
			String columnName = null;
			int AD_Org_ID = Env.getAD_Org_ID(Env.getCtx());
			int AD_Client_ID = Env.getAD_Client_ID(Env.getCtx());
			sql = "SELECT t.TableName, c.ColumnName " +
					" FROM AD_COLUMN c INNER JOIN AD_Table t ON (c.AD_Table_ID=t.AD_Table_ID)" +
					" WHERE AD_Column_ID=?";
			pstmt1 = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			pstmt1.setInt(1, getAD_Column_ID());
			rs1 = pstmt1.executeQuery();
			if (rs1.next())
			{
				tableName = rs1.getString(1);
				columnName = rs1.getString(2);
			}
			DB.close(rs1, pstmt1);
			rs1 = null; pstmt1 = null;

			if (tableName != null && columnName != null)
			{
				sql = "SELECT DISTINCT " + columnName + " FROM " + tableName + " WHERE AD_Client_ID=? "
						+ " AND AD_Org_ID=?";
				pstmt2 = DB.prepareStatement(sql, ITrx.TRXNAME_None);
				pstmt2.setInt(1, AD_Client_ID);
				pstmt2.setInt(2, AD_Org_ID);

				rs2 = pstmt2.executeQuery();
				while (rs2.next())
				{
					list.add(rs2.getString(1));
				}
				DB.close(rs2, pstmt2);
				rs2 = null; pstmt2 = null;
			}
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs1, pstmt1);
			DB.close(rs2, pstmt2);
		}

		return list;
	}

	/**
	 * @param gridTab
	 */
	public void setGridTab(GridTab gridTab)
	{
		m_gridTab = gridTab;
	}

	/**
	 * @return GridTab
	 */
	public GridTab getGridTab()
	{
		return m_gridTab;
	}

	// metas: begin: ------------------------------------------------------------------
	// metas: allow a GridField to make its component (VEditor) request the
	// focus

	/**
	 * GUI component should request focus
	 *
	 * @see "<a href='http://dewiki908/mediawiki/index.php/Geschwindigkeit_Erfassung_%282009_0027_G131%29'>(2009 0027 G131)</a>"
	 */
	public static final String REQUEST_FOCUS = "RequestFocus";

	/**
	 * Make our VEditor request the focus. This is done using {@link SwingUtilities#invokeLater(Runnable)}, unless the method is already called from within the EDT.
	 *
	 * @see "<a href='http://dewiki908/mediawiki/index.php/Geschwindigkeit_Erfassung_%282009_0027_G131%29'>(2009 0027 G131)</a>"
	 */
	public void requestFocus()
	{
		// NOTE: because we want to not collide with current Focus Systems we are ALWAYS requesting the focus on "invokeLater"
		// In this way we make sure our request is processed last.
		//
		// NOTE2: when considering to modify this approach consider following case too:
		// this method is called from some FocusListener.focusLost implementation. You are requesting the focus, but after that,
		// Swing's focus system will set the focus according to current focus traveral policy. So your request will be lost.
		// To avoid that case, we invoke later our request.

		if (log.isDebugEnabled())
		{
			// task 07068: logging when the request focus property change is scheduled
			final PropertyChangeListener[] propertyChangeListeners = m_propertyChangeListeners.getPropertyChangeListeners();
			log.debug(this.getColumnName() + ": calling invokeLater to fire property change '" + REQUEST_FOCUS + " with " + propertyChangeListeners.length + " listener(s): "
					+ Arrays.asList(propertyChangeListeners));
		}

		Services.get(IClientUI.class).invokeLater(getWindowNo(), new Runnable()
		{

			@Override
			public void run()
			{
				requestFocusInCurrentThread();
			}
		});
	}

	/**
	 * Fires a property chagne event to make our field's <code>VEditor</code> request the focus. This is done in the current thread.
	 */
	private void requestFocusInCurrentThread()
	{
		// task 07068: logging when the request focus property change is actually triggered
		if (log.isDebugEnabled())
		{
			final PropertyChangeListener[] propertyChangeListeners = m_propertyChangeListeners.getPropertyChangeListeners();
			log.debug(GridField.this.getColumnName() + ": firing property change '" + REQUEST_FOCUS + "' with " + propertyChangeListeners.length + " listener(s): "
					+ Arrays.asList(propertyChangeListeners));
		}

		m_propertyChangeListeners.firePropertyChange(REQUEST_FOCUS, false, true);
	}

	/**
	 * Check if current record is active. If the "IsActive" flag is not defined in current tab, parent tab (if any) is checked. If "IsActive" flag was not found, IsActive on window level is checked.
	 * If "IsActive" flag was not found, we consider it true.
	 *
	 * @param ctx
	 * @return true if the current record is active
	 * @task http://dewiki908/mediawiki/index.php/03297:_Cockpit_Tab_%22Weitere_Daten%22_readonly_first_time_opening_%282012091810000052%29
	 */
	private boolean isActive(Properties ctx)
	{
		//
		// Check Tab level IsActive
		String isActiveStr = Env.getContext(ctx, m_vo.WindowNo, m_vo.TabNo, "IsActive", Scope.Tab);
		if (!Check.isEmpty(isActiveStr, true))
		{
			return "Y".equals(isActiveStr);
		}

		//
		// Check Parent Tab
		final GridTab gridTab = getGridTab();
		final int parentTabNo = gridTab != null ? gridTab.getParentTabNo() : -1;
		if (parentTabNo >= 0)
		{
			isActiveStr = Env.getContext(ctx, m_vo.WindowNo, parentTabNo, "IsActive", Scope.Tab);
			if (!Check.isEmpty(isActiveStr, true))
			{
				return "Y".equals(isActiveStr);
			}
		}

		//
		// Check Window level IsActive
		isActiveStr = Env.getContext(ctx, m_vo.WindowNo, Env.TAB_None, "IsActive", Scope.Window);
		if (!Check.isEmpty(isActiveStr, true))
		{
			return "Y".equals(isActiveStr);
		}

		//
		// No IsActive flag found => assume true, but log a message, so we can fix it
		log.warn("No IsActive flag found on WindowNo=" + m_vo.WindowNo + ", TabNo=" + m_vo.TabNo
				+ ", WindowName=" + Services.get(IADWindowDAO.class).retrieveWindowName(getAD_Window_ID())
				+ ", TableName=" + Services.get(IADTableDAO.class).retrieveTableName(getAD_Table_ID())
				+ ". Considering IsActive=Y");
		return true;
	}

	/** @return true if this record is Processed */
	private boolean isProcessed(final Properties ctx)
	{
		return GridTab.isProcessed(ctx, getWindowNo(), getTabNo());
	}

	private final boolean isParentTabProcessedOrNotActive(final Properties ctx)
	{
		final GridTab gridTab = getGridTab();
		if (gridTab == null)
		{
			return false;
		}

		final GridTab parentGridTab = gridTab.getParentGridTab();
		if (parentGridTab == null)
		{
			// no parent
			return false;
		}

		return parentGridTab.isProcessedOrNotActive(ctx);
	}

	/**
	 * @task metas-2009_0021_AP1_CR051
	 */
	public int getIncludedTabHeight()
	{
		return m_vo.IncludedTabHeight;
	}

	/**
	 * Get ColorLogic (metas-2009_0021_AP1_CR045).
	 *
	 * @return color logic
	 */
	public IStringExpression getColorLogic()
	{
		return m_vo.getColorLogic();
	}

	public void setValueFromString(final String valueStr, final boolean inserting)
	{
		final Object value = createDefault(valueStr);
		setValue(value, inserting);
	}

	/**
	 * Gets AttributeName to be used for storing/loading default values (i.e. from AD_Preference)
	 *
	 * @return attribute name
	 */
	public String getPreferenceAttributeName()
	{
		final String attributeName;

		if (isStandardTabNo())
		{
			attributeName = m_vo.getColumnName();
		}
		else
		{
			// If it's not a standard tab then we need to also concatenate the TabNo because we don't want to disturb the standard functionality
			attributeName = m_vo.TabNo + "#" + m_vo.getColumnName();
		}

		return attributeName;
	}

	/**
	 *
	 * @return true if this field is on a standard TabNo (i.e. TabNo <= 50).
	 */
	public boolean isStandardTabNo()
	{
		// TabNo > 50 ... means not a regular tab (e.g. FindWindow)
		if (m_vo.TabNo > 50 || m_vo.TabNo < 0)
		{
			return false;
		}

		return true;
	}

	/**
	 * Create evaluation context based on given rowCtx (if not null) or current GridField
	 *
	 * @param rowCtx
	 * @return evaluation context to be used in expression evaluation
	 */
	public Evaluatee createEvaluationContext(final Properties rowCtx)
	{
		// Create evaluation context based on given rowCtx or current GridField
		final Evaluatee evaluationCtx;
		if (rowCtx == null)
		{
			evaluationCtx = this;
		}
		else if (rowCtx instanceof Evaluatee)
		{
			evaluationCtx = (Evaluatee)rowCtx;
		}
		else
		{
			final boolean onlyWindow = true;
			evaluationCtx = Evaluatees.ofCtx(rowCtx, getWindowNo(), onlyWindow);
		}

		return evaluationCtx;
	}

	private Properties getGridFieldContext()
	{
		return m_vo.getCtx();
	}

	private Properties getGridFieldContextRW()
	{
		return m_vo.getCtx();
	}

	@Override
	public boolean isTriggerCalloutAllowed()
	{
		final GridTab gridTab = getGridTab();
		if (gridTab == null)
		{
			return false;
		}

		if (gridTab.isProcessed() && !isAlwaysUpdateable())		// only active records
		{
			return false;
		}

		return true;

	}

	// metas: end

	@Override
	public Properties getCtx()
	{
		return m_vo.getCtx();
	}

	public int getAD_Table_ID()
	{
		return m_vo.getAD_Table_ID();
	}

	@Override
	public String getTableName()
	{
		final int adTableId = getAD_Table_ID();
		return adTableId <= 0 ? null : Services.get(IADTableDAO.class).retrieveTableName(adTableId);
	}
	
	@Override
	public ICalloutExecutor getCurrentCalloutExecutor()
	{
		final GridTab gridTab = getGridTab();
		if (gridTab == null)
		{
			return null;
		}
		
		return gridTab.getCalloutExecutor();
	}

	@Override
	public <T> T getModel(final Class<T> modelClass)
	{
		final ICalloutRecord calloutRecord = getCalloutRecord();
		final T model = calloutRecord.getModel(modelClass);
		Check.assumeNotNull(model, "model not null");
		return model;
	}

	@Override
	public int getTabNo()
	{
		return m_vo.TabNo;
	}

	/**
	 * Enable events delaying.
	 * So, from now on, all events will be enqueued instead of directly fired.
	 * Later, when {@link #releaseDelayedEvents()} is called, all enqueued events will be fired.
	 */
	public void delayEvents()
	{
		m_propertyChangeListeners.blockEvents();
	}

	/**
	 * Fire all enqueued events (if any) and disable events delaying.
	 *
	 * @see #delayEvents().
	 */
	public void releaseDelayedEvents()
	{
		m_propertyChangeListeners.releaseEvents();
	}

	@Override
	public boolean isRecordCopyingMode()
	{
		final GridTab gridTab = getGridTab();

		// If there was no GridTab set for this field, consider as we are not copying the record
		if (gridTab == null)
		{
			return false;
		}

		return gridTab.isDataNewCopy();
	}
	
	@Override
	public boolean isRecordCopyingModeIncludingDetails()
	{
		final GridTab gridTab = getGridTab();

		// If there was no GridTab set for this field, consider as we are not copying the record
		if (gridTab == null)
		{
			return false;
		}

		return gridTab.getTableModel().isCopyWithDetails();
	}

	@Override
	public void fireDataStatusEEvent(final String AD_Message, final String info, final boolean isError)
	{
		final GridTab gridTab = getGridTab();
		if(gridTab == null)
		{
			log.warn("Could not fire EEvent on {} because gridTab is not set. The event was: AD_Message={}, info={}, isError={}", this, AD_Message, info, isError);
			return;
		}

		gridTab.fireDataStatusEEvent(AD_Message, info, isError);
	}
	
	@Override
	public void fireDataStatusEEvent(final ValueNamePair errorLog)
	{
		final GridTab gridTab = getGridTab();
		if(gridTab == null)
		{
			log.warn("Could not fire EEvent on {} because gridTab is not set. The event was: errorLog={}, info={}, isError={}", this, errorLog);
			return;
		}
		
		gridTab.fireDataStatusEEvent(errorLog);
	}

	@Override
	public ICalloutRecord getCalloutRecord()
	{
		final GridTab gridTab = getGridTab();
		Check.assumeNotNull(gridTab, "gridTab not null");
		return gridTab;
	}
}
