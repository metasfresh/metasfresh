/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.adempiere.ad.dao.cache.impl.TableRecordCacheLocal;
import org.adempiere.ad.migration.logger.IMigrationLogger;
import org.adempiere.ad.migration.model.X_AD_MigrationStep;
import org.adempiere.ad.persistence.po.INoDataFoundHandler;
import org.adempiere.ad.persistence.po.NoDataFoundHandlers;
import org.adempiere.ad.security.TableAccessLevel;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.session.ChangeLogRecord;
import org.adempiere.ad.session.ISessionBL;
import org.adempiere.ad.session.ISessionDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.ad.trx.spi.TrxListenerAdapter;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ad.validationRule.IValidationRuleFactory;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.CopyRecordSupport;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.util.CacheMgt;
import org.compiere.util.DB;
import org.compiere.util.DB.OnFail;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatee2;
import org.compiere.util.ISqlUpdateReturnProcessor;
import org.compiere.util.Ini;
import org.compiere.util.SecureEngine;
import org.compiere.util.Trace;
import org.compiere.util.TrxRunnable2;
import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.metas.document.documentNo.IDocumentNoBL;
import de.metas.document.documentNo.IDocumentNoBuilder;
import de.metas.document.documentNo.IDocumentNoBuilderFactory;
import de.metas.document.documentNo.impl.IPreliminaryDocumentNoBuilder;
import de.metas.i18n.IModelTranslation;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.impl.NullModelTranslationMap;
import de.metas.i18n.impl.POInfoModelTranslationMap;
import de.metas.logging.LogManager;
import de.metas.logging.MetasfreshLastError;

/**
 * Persistent Object.
 * Superclass for actual implementations
 *
 * @author Jorg Janke
 * @version $Id: PO.java,v 1.12 2006/08/09 16:38:47 jjanke Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *         <li>FR [ 1675490 ] ModelValidator on modelChange after events
 *         <li>BF [ 1704828 ] PO.is_Changed() and PO.is_ValueChanged are not consistent
 *         <li>FR [ 1720995 ] Add PO.saveEx() and PO.deleteEx() methods
 *         <li>BF [ 1990856 ] PO.set_Value* : truncate string more than needed
 *         <li>FR [ 2042844 ] PO.get_Translation improvements
 *         <li>FR [ 2818369 ] Implement PO.get_ValueAs*(columnName)
 *         https://sourceforge.net/tracker/?func=detail&aid=2818369&group_id=176962&atid=879335
 *         <li>BF [ 2849122 ] PO.AfterSave is not rollback on error
 *         https://sourceforge.net/tracker/?func=detail&aid=2849122&group_id=176962&atid=879332
 *         <li>BF [ 2859125 ] Can't set AD_OrgBP_ID
 *         https://sourceforge.net/tracker/index.php?func=detail&aid=2859125&group_id=176962&atid=879332
 *         <li>BF [ 2866493 ] VTreePanel is not saving who did the node move
 *         https://sourceforge.net/tracker/?func=detail&atid=879332&aid=2866493&group_id=176962
 * @author Teo Sarca, teo.sarca@gmail.com
 *         <li>BF [ 2876259 ] PO.insertTranslation query is not correct
 *         https://sourceforge.net/tracker/?func=detail&aid=2876259&group_id=176962&atid=879332
 * @author Victor Perez, e-Evolution SC
 *         <li>[ 2195894 ] Improve performance in PO engine
 *         <li>http://sourceforge.net/tracker/index.php?func=detail&aid=2195894&group_id=176962&atid=879335
 */
public abstract class PO
		implements Serializable, Comparator<Object>, Evaluatee, Evaluatee2 // metas: 01622
		, IClientOrgAware // metas
{
	/**
	 *
	 */
	private static final long serialVersionUID = 8413360272600437423L;

	private static final String USE_TIMEOUT_FOR_UPDATE = "org.adempiere.po.useTimeoutForUpdate";

	private static final int QUERY_TIME_OUT = 10;

	final IDocumentNoBuilderFactory documentNoFactory = Services.get(IDocumentNoBuilderFactory.class);

	/**
	 * Set Document Value Workflow Manager
	 *
	 * @param docWFMgr mgr
	 */
	public static void setDocWorkflowMgr(DocWorkflowMgr docWFMgr)
	{
		s_docWFMgr = docWFMgr;
		s_log.info("Document workflow manager set to {}", s_docWFMgr);
	}	// setDocWorkflowMgr

	/** Document Value Workflow Manager */
	private static DocWorkflowMgr s_docWFMgr = null;

	/** User Maintained Entity Type */
	static protected final String ENTITYTYPE_UserMaintained = "U";
	/** Dictionary Maintained Entity Type */
	static public final String ENTITYTYPE_Dictionary = "D";

	/**************************************************************************
	 * Create New Persistent Object
	 *
	 * @param ctx context
	 */
	public PO(Properties ctx)
	{
		this(ctx, 0, null, null);
	}   // PO

	/**
	 * Create & Load existing Persistent Object
	 *
	 * @param ID The unique ID of the object
	 * @param ctx context
	 * @param trxName transaction name
	 */
	public PO(Properties ctx, int ID, String trxName)
	{
		this(ctx, ID, trxName, null);
	}   // PO

	/**
	 * Create & Load existing Persistent Object.
	 *
	 * @param ctx context
	 * @param rs optional - load from current result set position (no navigation, not closed)
	 *            if null, a new record is created.
	 * @param trxName transaction name
	 */
	public PO(Properties ctx, ResultSet rs, String trxName)
	{
		this(ctx, 0, trxName, rs);
	}	// PO

	/**
	 * Create & Load existing Persistent Object.
	 *
	 * <pre>
	 *  You load
	 * 		- an existing single key record with 	new PO (ctx, Record_ID)
	 * 			or									new PO (ctx, Record_ID, trxName)
	 * 			or									new PO (ctx, rs, get_TrxName())
	 * 		- a new single key record with			new PO (ctx, 0)
	 * 		- an existing multi key record with		new PO (ctx, rs, get_TrxName())
	 * 		- a new multi key record with			new PO (ctx, null)
	 *  The ID for new single key records is created automatically,
	 *  you need to set the IDs for multi-key records explicitly.
	 * </pre>
	 *
	 * @param ctx context
	 * @param ID the ID if 0, the record defaults are applied - ignored if re exists
	 * @param trxName transaction name
	 * @param rs optional - load from current result set position (no navigation, not closed)
	 */
	public PO(Properties ctx, int ID, String trxName, ResultSet rs)
	{
		super();
		if (ctx == null)
			throw new IllegalArgumentException("No Context");
		p_ctx = ctx;
		m_trxName = trxName;

		if (ID == ID_NewInstanceNoInit)
		{
			return;
		}

		p_info = initPO(ctx);
		if (p_info == null || p_info.getTableName() == null)
			throw new IllegalArgumentException("Invalid PO Info - " + p_info);
		m_KeyColumns = p_info.getKeyColumnNamesAsArray();
		//
		int size = p_info.getColumnCount();
		m_oldValues = new Object[size];
		m_newValues = new Object[size];
		m_valueLoaded = new boolean[size]; // metas

		if (rs != null)
			load(rs);		// will not have virtual columns
		else
			load(ID, trxName);
	}   // PO

	/**
	 * Create New PO by Copying existing (key not copied).
	 *
	 * @param ctx context
	 * @param source source object
	 * @param AD_Client_ID client
	 * @param AD_Org_ID org
	 */
	public PO(Properties ctx, PO source, int AD_Client_ID, int AD_Org_ID)
	{
		this(ctx, 0, null, null);	// create new
		//
		if (source != null)
			copyValues(source, this);
		setAD_Client_ID(AD_Client_ID);
		setAD_Org_ID(AD_Org_ID);
	}	// PO

	/** Logger */
	protected transient final Logger log = LogManager.getLogger(getClass());
	/** Static Logger */
	private static final Logger s_log = LogManager.getLogger(PO.class);

	private static final String COLUMNNAME_IsApproved = "IsApproved";

	/** Context */
	private Properties p_ctx;
	/** Model Info */
	private volatile POInfo p_info = null;

	/** Original Values */
	private Object[] m_oldValues = null;
	/** New Values */
	private Object[] m_newValues = null;
	/** Flag to mark that a value was loaded */
	private boolean[] m_valueLoaded = null;
	/** Counts how many times this object was loaded/reloaded */
	private int m_loadCount = 0;

	/**
	 * Lock to indicate that we have a loading in progress
	 *
	 * @see #load(String)
	 */
	private final ReentrantLock m_loadingLock = new ReentrantLock();
	/**
	 * Flag to indicate that we have a loading in progress
	 */
	private boolean m_loading = false;

	/**
	 * Flag to indicate if this object is stale and needs to be reloaded before any get/set operation
	 *
	 * @see http://dewiki908/mediawiki/index.php/01537:_Speed_optimizations_for_new_record_saving_%282011052610000028%29
	 */
	private boolean m_stale = false;

	/** Record_IDs */
	private Object[] m_IDs = new Object[] { I_ZERO };
	/** Key Columns */
	// NOTE: not "final" because we want to use it in copy
	private String[] m_KeyColumns;
	/**
	 * Flag used to mark those POs which are new and not yet saved.
	 * After they were already saved, this flag will be set to <code>false</code>.
	 */
	private boolean m_createNew = false;
	/**
	 * Flag used to mark those POs which are new, saved or not.
	 * Compared to {@link #m_createNew} this flag will be never ever reset so can always know if this PO was created now.
	 */
	private boolean m_wasJustCreated = false;
	/** Attachment with entries */
	private MAttachment m_attachment = null;
	/** Deleted ID */
	private int m_idOld = 0;
	/** Custom Columns */
	private HashMap<String, String> m_custom = null;

	/** Zero Integer */
	protected static final Integer I_ZERO = new Integer(0);
	private static final Integer I_ZERO_NATIVESEQUENCE = new Integer(0);
	/** Accounting Columns */
	private List<String> s_acctColumns = null;

	/** Trifon - Indicates that this record is created by replication functionality. */
	private boolean m_isReplication = false;

	/** Do not overwrite assigned ID with generated one */
	private boolean isAssignedID = false;

	public final boolean isAssignedID()
	{
		return isAssignedID;
	}

	public final void setIsAssignedID(boolean assignedID)
	{
		isAssignedID = assignedID;
	}

	/**
	 * Initialize and return PO_Info
	 *
	 * @param ctx context
	 * @return POInfo
	 */
	abstract protected POInfo initPO(Properties ctx);

	/**
	 * Get Table Access Level as integer.
	 *
	 * @return Access Level as integer.
	 * @deprecated Please use {@link #get_TableAccessLevel()}
	 */
	// NOTE: not making it final because old generated models are overriding this
	@Deprecated
	protected int get_AccessLevel()
	{
		return p_info.getAccessLevelInt(); // metas
	}

	/**
	 * Get Table Access Level
	 *
	 * @return table access level
	 */
	protected final TableAccessLevel get_TableAccessLevel()
	{
		return p_info.getAccessLevel();
	}

	/**
	 * String representation
	 *
	 * @return String representation
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder(getClass().getSimpleName())
				.append("[")
				.append(get_WhereClause(true)); // .append("]");
		// metas: begin
		int idx = get_ColumnIndex("Value");
		if (idx >= 0)
			sb.append(", Value=").append(get_Value(idx));
		idx = get_ColumnIndex("Name");
		if (idx >= 0)
			sb.append(", Name=").append(get_Value(idx));

		// display the trxName (easy for debugging)
		sb.append(", trxName=").append(get_TrxName());

		sb.append("]");
		// metas: end
		return sb.toString();
	}	// toString

	/**
	 * Equals based on ID
	 *
	 * @param cmp comparator
	 * @return true if ID the same
	 */
	@Override
	public boolean equals(Object cmp)
	{
		if (this == cmp)
			return true;
		if (cmp == null)
			return false;
		if (!(cmp instanceof PO))
			return false;
		if (cmp.getClass().equals(this.getClass()))
		{
			// if both ID's are zero they can't be compared by ID
			if (((PO)cmp).get_ID() == 0 && get_ID() == 0)
			{
				return super.equals(cmp);
			}
			else
			{
				// metas: compare all IDs instead of only one because this will fail on multi-primary key POs
				return Arrays.equals(((PO)cmp).m_IDs, m_IDs);
				// return ((PO)cmp).get_ID() == get_ID();
			}
		}
		return super.equals(cmp);
	}	// equals

	/**
	 * Compare based on DocumentNo, Value, Name, Description
	 *
	 * @param o1 Object 1
	 * @param o2 Object 2
	 * @return -1 if o1 < o2
	 */
	@Override
	public int compare(Object o1, Object o2)
	{
		if (o1 == null)
			return -1;
		else if (o2 == null)
			return 1;
		if (!(o1 instanceof PO))
			throw new ClassCastException("Not PO -1- " + o1);
		if (!(o2 instanceof PO))
			throw new ClassCastException("Not PO -2- " + o2);
		// same class
		if (o1.getClass().equals(o2.getClass()))
		{
			int index = get_ColumnIndex("DocumentNo");
			if (index == -1)
				index = get_ColumnIndex("Value");
			if (index == -1)
				index = get_ColumnIndex("Name");
			if (index == -1)
				index = get_ColumnIndex("Description");
			if (index != -1)
			{
				PO po1 = (PO)o1;
				Object comp1 = po1.get_Value(index);
				PO po2 = (PO)o2;
				Object comp2 = po2.get_Value(index);
				if (comp1 == null)
					return -1;
				else if (comp2 == null)
					return 1;
				return comp1.toString().compareTo(comp2.toString());
			}
		}
		return o1.toString().compareTo(o2.toString());
	}	// compare

	/**
	 * Get TableName.
	 *
	 * @return table name
	 */
	public final String get_TableName()
	{
		return p_info.getTableName();
	}   // get_TableName

	/**
	 * Get Key Columns.
	 *
	 * @return table name
	 */
	public final String[] get_KeyColumns()
	{
		return m_KeyColumns;
	}   // get_KeyColumns

	/**
	 * Get Table ID.
	 *
	 * @return table id
	 */
	public final int get_Table_ID()
	{
		return p_info.getAD_Table_ID();
	}   // get_TableID

	/**
	 * Return Single Key Record ID
	 *
	 * @return ID or 0
	 */
	public final int get_ID()
	{
		// Guard against the case when m_IDs array is null or empty
		// This can happen when you deal with a table which does not have Keys or Parent Keys.
		if (m_IDs == null || m_IDs.length == 0)
		{
			return -1;
		}

		Object oo = m_IDs[0];
		if (oo != null && oo instanceof Integer)
			return ((Integer)oo).intValue();
		return 0;
	}   // getID

	/**
	 * Return Deleted Single Key Record ID
	 *
	 * @return ID or 0
	 */
	public final int get_IDOld()
	{
		return m_idOld;
	}   // getID

	/**
	 * Get Context
	 *
	 * @return context
	 */
	public final Properties getCtx()
	{
		return p_ctx;
	}	// getCtx

	/**
	 * Sets PO's context.
	 *
	 * WARNING: use it only if u really know what are you doing.
	 *
	 * @param ctx
	 */
	protected final void setCtx(final Properties ctx)
	{
		Check.assumeNotNull(ctx, "ctx not null");
		this.p_ctx = ctx;
	}

	/**
	 * Get Logger
	 *
	 * @return logger
	 */
	public final Logger get_Logger()
	{
		return log;
	}	// getLogger

	/**************************************************************************
	 * Get Value
	 *
	 * @param index index
	 * @return value
	 */
	public final Object get_Value(int index)
	{
		if (index < 0 || index >= get_ColumnCount())
		{
			log.warn("Index invalid - {}", index, new Exception()); // metas: tsa: added exeption to trace it
			return null;
		}
		if (m_newValues[index] != null)
		{
			if (m_newValues[index].equals(Null.NULL))
				return null;
			return m_newValues[index];
		}

		loadIfStalled(index); // metas: 01537
		if (!m_valueLoaded[index])
		{
			loadColumn(index);
		}

		return m_oldValues[index];
	}   // get_Value

	/**
	 * Get Value as int
	 *
	 * @param index index
	 * @return int value or 0
	 */
	public final int get_ValueAsInt(int index)
	{
		Object value = get_Value(index);
		if (value == null)
			return 0;
		if (value instanceof Integer)
			return ((Integer)value).intValue();
		try
		{
			return Integer.parseInt(value.toString());
		}
		catch (NumberFormatException ex)
		{
			log.warn(p_info.getColumnName(index) + " - " + ex.getMessage());
			return 0;
		}
	}   // get_ValueAsInt

	/**
	 * Get Value
	 *
	 * @param columnName column name
	 * @return value or null
	 */
	public final Object get_Value(String columnName)
	{
		int index = get_ColumnIndex(columnName);
		if (index < 0)
		{
			log.warn("Column not found - " + columnName);
			Trace.printStack();
			return null;
		}
		return get_Value(index);
	}   // get_Value

	/**
	 * Get Encrypted Value
	 *
	 * @param columnName column name
	 * @return value or null
	 */
	protected final Object get_ValueE(String columnName)
	{
		return get_Value(columnName);
	}   // get_ValueE
	
	@Override
	public <T> T get_ValueAsObject(String variableName)
	{
		@SuppressWarnings("unchecked")
		final T value = (T)get_Value(variableName);
		return value;
	}

	/**
	 * Get Column Value
	 *
	 * @param variableName name
	 * @return
	 * 		<ul>
	 *         <li>string value
	 *         <li>empty string in case the underlying value is null
	 *         <li>"Y"/"N" in case the underlying value is {@link Boolean}
	 *         </ul>
	 */
	@Override
	public final String get_ValueAsString(final String variableName)
	{
		final Object value = get_Value(variableName);
		if (value == null)
		{
			return "";
		}
		//
		// In case we deal with a boolean column we need to return "Y"/"N" instead of "true"/"false",
		// because this method is actually implementing Evaluatee interface which is used for evaluating logic expressions,
		// and on all our logic expressions we are comparing with Y or N.
		else if (value instanceof Boolean)
		{
			final boolean valueBoolean = (boolean)value;
			return Env.toString(valueBoolean);
		}

		return value.toString();
	}	// get_ValueAsString

	/**
	 * Get Value of Column
	 *
	 * @param AD_Column_ID column
	 * @return value or null
	 */
	public final Object get_ValueOfColumn(int AD_Column_ID)
	{
		int index = p_info.getColumnIndex(AD_Column_ID);
		if (index < 0)
		{
			log.warn("Not found - AD_Column_ID=" + AD_Column_ID);
			return null;
		}
		return get_Value(index);
	}   // get_ValueOfColumn

	/**
	 * Get Old Value
	 *
	 * @param index index
	 * @return value
	 */
	public final Object get_ValueOld(int index)
	{
		if (index < 0 || index >= get_ColumnCount())
		{
			log.warn("Index invalid - " + index);
			return null;
		}
		loadIfStalled(index); // metas: 01537
		return m_oldValues[index];
	}   // get_ValueOld

	/**
	 * Get Old Value
	 *
	 * @param columnName column name
	 * @return value or null
	 */
	public final Object get_ValueOld(String columnName)
	{
		int index = get_ColumnIndex(columnName);
		if (index < 0)
		{
			log.warn("Column not found - " + columnName);
			return null;
		}
		return get_ValueOld(index);
	}   // get_ValueOld

	/**
	 * Get Old Value as int
	 *
	 * @param columnName column name
	 * @return int value or 0
	 */
	public final int get_ValueOldAsInt(String columnName)
	{
		int index = get_ColumnIndex(columnName);
		if (index < 0)
		{
			log.warn("Column not found - " + columnName);
			return 0;
		}
		return get_ValueOldAsInt(index);
	}   // get_ValueOldAsInt

	public final int get_ValueOldAsInt(int index)
	{
		Object value = get_ValueOld(index);
		if (value == null)
			return 0;
		if (value instanceof Integer)
			return ((Integer)value).intValue();
		try
		{
			return Integer.parseInt(value.toString());
		}
		catch (NumberFormatException ex)
		{
			log.warn(get_ColumnName(index) + " - " + ex.getMessage());
			return 0;
		}
	}

	/**
	 * Is Value Changed
	 *
	 * @param index index
	 * @return true if changed
	 */
	public final boolean is_ValueChanged(int index)
	{
		if (index < 0 || index >= get_ColumnCount())
		{
			log.warn("Index invalid - " + index);
			return false;
		}

		//
		// Check if object is stale (01537)
		if (m_stale)
		{
			// We return false because object is stalled and it was not changed before.
			// If it would be changed, first it would be reloaded and it won't be stale at this moment
			return false;
		}

		// metas: begin: If column was explicitly marked as changed we consider it changed
		if (markedChangedColumns != null && markedChangedColumns.contains(index))
		{
			return true;
		}
		// metas: end

		if (m_newValues[index] == null)
			return false;

		// metas: normalize null values before comparing them (04219)
		Object newValue = m_newValues[index];
		if (newValue == null)
		{
			newValue = Null.NULL;
		}
		Object oldValue = m_oldValues[index];
		if (oldValue == null)
		{
			oldValue = Null.NULL;
		}

		return !newValue.equals(oldValue);
	}   // is_ValueChanged

	/**
	 * Is Value Changed
	 *
	 * @param columnName column name
	 * @return true if changed
	 */
	public final boolean is_ValueChanged(String columnName)
	{
		int index = get_ColumnIndex(columnName);
		if (index < 0)
		{
			log.warn("Column not found - " + columnName);
			return false;
		}
		return is_ValueChanged(index);
	}   // is_ValueChanged

	/**
	 * Return new - old.
	 * - New Value if Old Value is null
	 * - New Value - Old Value if Number
	 * - otherwise null
	 *
	 * @param index index
	 * @return new - old or null if not appropriate or not changed
	 */
	public final Object get_ValueDifference(int index)
	{
		if (index < 0 || index >= get_ColumnCount())
		{
			log.warn("Index invalid - " + index);
			return null;
		}

		//
		// Check if object is stalled
		// In case is stalled then there is no change so we return null
		// because it's the same as having m_newValues[index] = null
		if (m_stale)  // metas: 01537
		{
			return null;
		}

		final Object nValue = m_newValues[index];
		// No new Value or NULL
		if (nValue == null || nValue == Null.NULL)
			return null;
		//
		final Object oValue = m_oldValues[index];
		if (oValue == null || oValue == Null.NULL)
			return nValue;
		if (nValue instanceof BigDecimal)
		{
			BigDecimal obd = (BigDecimal)oValue;
			return ((BigDecimal)nValue).subtract(obd);
		}
		else if (nValue instanceof Integer)
		{
			int result = ((Integer)nValue).intValue();
			result -= ((Integer)oValue).intValue();
			return new Integer(result);
		}
		//
		log.warn("Invalid type - New=" + nValue);
		return null;
	}   // get_ValueDifference

	/**
	 * Return new - old.
	 * - New Value if Old Value is null
	 * - New Value - Old Value if Number
	 * - otherwise null
	 *
	 * @param columnName column name
	 * @return new - old or null if not appropriate or not changed
	 */
	public final Object get_ValueDifference(String columnName)
	{
		int index = get_ColumnIndex(columnName);
		if (index < 0)
		{
			log.warn("Column not found - " + columnName);
			return null;
		}
		return get_ValueDifference(index);
	}   // get_ValueDifference

	/**************************************************************************
	 * Set Value
	 *
	 * @param ColumnName column name
	 * @param value value
	 * @return true if value set
	 */
	protected final boolean set_Value(String ColumnName, Object value)
	{
		if (value instanceof String && ColumnName.equals("WhereClause")
				&& value.toString().toUpperCase().indexOf("=NULL") != -1)
			log.warn("Invalid Null Value - " + ColumnName + "=" + value);

		int index = get_ColumnIndex(ColumnName);
		if (index < 0)
		{
			log.error("Column not found - " + ColumnName);
			return false;
		}
		if (ColumnName.endsWith("_ID") && value instanceof String)
		{
			// Convert to Integer only if info class is Integer - teo_sarca [ 2859125 ]
			Class<?> clazz = p_info.getColumnClass(p_info.getColumnIndex(ColumnName));
			if (Integer.class == clazz)
			{
				log.error("Invalid Data Type for " + ColumnName + "=" + value);
				value = Integer.parseInt((String)value);
			}
		}

		return set_Value(index, value);
	}   // setValue

	/**
	 * Set Encrypted Value
	 *
	 * @param ColumnName column name
	 * @param value value
	 * @return true if value set
	 */
	protected final boolean set_ValueE(String ColumnName, Object value)
	{
		return set_Value(ColumnName, value);
	}   // setValueE

	/**
	 * Set Value if updatable and correct class.
	 * (and to NULL if not mandatory)
	 *
	 * @param index index
	 * @param value value
	 * @return true if value set
	 */
	protected final boolean set_Value(int index, Object value)
	{
		if (index < 0 || index >= get_ColumnCount())
		{
			log.warn("Index invalid - " + index);
			return false;
		}
		String ColumnName = p_info.getColumnName(index);
		//
		if (p_info.isVirtualColumn(index))
		{
			final AdempiereException ex = new AdempiereException("Setting a Virtual Column is not allowed: " + ColumnName);
			log.warn(ex.getLocalizedMessage(), ex);
			return false;
		}

		//
		// globalqss -- Bug 1618469 - is throwing not updateable even on new records
		// if (!p_info.isColumnUpdateable(index))
		if ((!p_info.isColumnUpdateable(index)) && (!is_new()))
		{
			// metas-ts 02973 only show this message (and clutter the log) if old and new value differ
			// NOTE: when comparing we need to take the old value (the one which is in database) and NOT the previously set (via set_ValueNoCheck for example).
			final Object oldValue = get_ValueOld(index);
			if (Objects.equals(oldValue, value))
			{
				// Value did not changed.

				// Don't return here, but allow actually setting the value
				// because it could be that someone changed the "m_newValues" by using set_ValueNoCheck()
				// ...but we can do a quick look-ahead and see if that's the case
				if (Objects.equals(m_newValues[index], value))
				{
					return true;
				}
			}
			else if (oldValue != null && InterfaceWrapperHelper.ATTR_ReadOnlyColumnCheckDisabled.is(this, Boolean.TRUE))
			{
				// ReadOnly column checking was disabled for this model => continue the execution.
			}
			else if (oldValue != null)
			{
				// task 09266: be strict but allow an admin to declare exceptions from the stric rule via AD_SysConfig
				final IDeveloperModeBL developerModeBL = Services.get(IDeveloperModeBL.class);
				final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

				// Value was changed and oldValue was already set
				// That's an error because user shall not be allowed to change the value of this column
				final String sysConfigName = "org.compiere.PO." + get_TableName() + "_" + ColumnName + ".ThrowExIfNotUpdateable";
				final boolean throwException = developerModeBL.isEnabled()
						|| sysConfigBL.getBooleanValue(sysConfigName, true, getAD_Client_ID(), getAD_Org_ID());

				return new AdempiereException("Column not updateable: " + ColumnName + " - NewValue=" + value + " - OldValue=" + oldValue + "; "
						+ "Note to developer: to bypass this checking you can"
						+ "1. Set AD_SysConfig '" + sysConfigName + "' = 'N' to disable this exception (will still be logged with Level=SERVERE)"
						+ "2. Set dynamic attribute " + InterfaceWrapperHelper.ATTR_ReadOnlyColumnCheckDisabled + " = true (no errors will be logged in this case)")
								.throwOrLogSevere(throwException, log);
			}
			else
			{
				// Old Value was not already set
				// We allow user to set it now, so let the execution continue
				Check.assumeNull(oldValue, "internal error: at this point oldValue shall be null");
			}
		}

		//
		// Load record if stale (01537)
		// NOTE: we are calling it with columnIndex=-1 because we want to make sure everything is loaded and m_stale is set to false
		// Else when load(ResultSet) is called, m_newValues gets reset
		loadIfStalled(-1);

		if (value == null)
		{
			if (p_info.isColumnMandatory(index))
			{
				// metas: throw exception only if old value is not null
				// else, we are setting the value from NULL to NULL which can be tolerated
				// NOTE: when comparing we need to take the old value (the one which is in database) and NOT the previously set (via set_ValueNoCheck for example).
				final Object oldValue = get_ValueOld(index);
				if (oldValue != null)
				{
					throw new IllegalArgumentException(ColumnName + " is mandatory.");
				}
			}
			m_newValues[index] = Null.NULL;          // correct
			log.trace("{} = null", ColumnName);
		}
		else
		{
			// matching class or generic object
			if (value.getClass().equals(p_info.getColumnClass(index))
					|| p_info.getColumnClass(index) == Object.class)
				m_newValues[index] = value;     // correct
			// Integer can be set as BigDecimal
			else if (value.getClass() == BigDecimal.class
					&& p_info.getColumnClass(index) == Integer.class)
				m_newValues[index] = new Integer(((BigDecimal)value).intValue());
			// Set Boolean
			else if (p_info.getColumnClass(index) == Boolean.class
					&& ("Y".equals(value) || "N".equals(value)))
				m_newValues[index] = Boolean.valueOf("Y".equals(value));
			// added by vpj-cd
			// To solve BUG [ 1618423 ] Set Project Type button in Project window throws warning
			// generated because C_Project.C_Project_Type_ID is defined as button in dictionary
			// although is ID (integer) in database
			else if (value.getClass() == Integer.class
					&& p_info.getColumnClass(index) == String.class)
				m_newValues[index] = value;
			else if (value.getClass() == String.class
					&& p_info.getColumnClass(index) == Integer.class)
				try
				{
					m_newValues[index] = new Integer((String)value);
				}
				catch (NumberFormatException e)
				{
					log.error(ColumnName
							+ " - Class invalid(1): " + value.getClass().toString()
							+ ", Should be " + p_info.getColumnClass(index).toString() + ": " + value, new Exception("stacktrace"));
					return false;
				}
			else
			{
				log.error(ColumnName
						+ " - Class invalid(2): " + value.getClass().toString()
						+ ", Should be " + p_info.getColumnClass(index).toString() + ": " + value, new Exception("stacktrace"));
				return false;
			}
			// Validate (Min/Max)
			String error = p_info.validate(index, value);
			if (error != null)
			{
				log.warn(ColumnName + "=" + value + " - " + error);
				return false;
			}
			// Length for String
			if (p_info.getColumnClass(index) == String.class)
			{
				String stringValue = value.toString();
				int length = p_info.getFieldLength(index);
				if (stringValue.length() > length && length > 0)
				{
					log.warn(ColumnName + " - Value too long - truncated to length=" + length
							+ ": " + (p_info.isEncrypted(index) ? "(encrypted)" : stringValue));
					m_newValues[index] = stringValue.substring(0, length);
				}
			}
			// Validate reference list [1762461]
			if (p_info.getColumn(index).DisplayType == DisplayType.List &&
					p_info.getColumn(index).AD_Reference_Value_ID > 0 &&
					value instanceof String)
			{
				final boolean hasListValue = Services.get(IADReferenceDAO.class).existListValue(getCtx(), p_info.getColumn(index).AD_Reference_Value_ID, (String)value);
				if (!hasListValue)
				{
					final StringBuilder validValues = new StringBuilder();
					for (ValueNamePair vp : MRefList.getList(getCtx(), p_info.getColumn(index).AD_Reference_Value_ID, false))
						validValues.append(" - ").append(vp.getValue());
					throw new IllegalArgumentException(ColumnName + " Invalid value - "
							+ value + " - Reference_ID=" + p_info.getColumn(index).AD_Reference_Value_ID + validValues.toString());
				}
			}
			if (log.isTraceEnabled())
				log.trace(ColumnName + " = " + m_newValues[index] + " (OldValue=" + m_oldValues[index] + ")");
		}
		set_Keys(ColumnName, m_newValues[index]);
		return true;
	}   // setValue

	/**
	 * Set Value w/o check (update, r/o, ..).
	 * Used when Column is R/O
	 * Required for key and parent values
	 *
	 * @param ColumnName column name
	 * @param value value
	 * @return true if value set
	 */
	// metas: changed from protected to public
	public final boolean set_ValueNoCheck(String ColumnName, Object value)
	{
		int index = get_ColumnIndex(ColumnName);
		if (index < 0)
		{
			log.error("Column not found - " + ColumnName);
			return false;
		}
		return set_ValueNoCheck(index, value);
	}

	// metas: changed from private to public
	public final boolean set_ValueNoCheck(final int index, final Object value)
	{
		//
		// Load record if stale (01537)
		// NOTE: we are calling it with columnIndex=-1 because we want to make sure everything is loaded and m_stale is set to false
		// Else when load(ResultSet) is called, m_newValues gets reset
		loadIfStalled(-1);
		if (value == null || value == Null.NULL)
		{
			m_newValues[index] = Null.NULL;		// write direct
		}
		else
		{
			// matching class or generic object
			if (value.getClass().equals(p_info.getColumnClass(index))
					|| p_info.getColumnClass(index) == Object.class)
				m_newValues[index] = value;     // correct
			// Integer can be set as BigDecimal
			else if (value.getClass() == BigDecimal.class
					&& p_info.getColumnClass(index) == Integer.class)
				m_newValues[index] = new Integer(((BigDecimal)value).intValue());
			// Set Boolean
			else if (p_info.getColumnClass(index) == Boolean.class
					&& ("Y".equals(value) || "N".equals(value)))
				m_newValues[index] = Boolean.valueOf("Y".equals(value));
			else if (p_info.getColumnClass(index) == Boolean.class && (value instanceof Boolean))
				m_newValues[index] = value;
			else if (p_info.getColumnClass(index) == Integer.class
					&& value.getClass() == String.class)
			{
				try
				{
					int intValue = Integer.parseInt((String)value);
					m_newValues[index] = Integer.valueOf(intValue);
				}
				catch (Exception e)
				{
					log.warn(get_ColumnName(index)
							+ " - Class invalid(3): " + value.getClass().toString()
							+ ", Should be " + p_info.getColumnClass(index).toString() + ": " + value, new Exception("stacktrace"));
					m_newValues[index] = null;
				}
			}
			else
			{
				log.warn(get_ColumnName(index)
						+ " - Class invalid(4): " + value.getClass().toString()
						+ ", Should be " + p_info.getColumnClass(index).toString() + ": " + value, new Exception("stacktrace"));
				m_newValues[index] = value;     // correct
			}

			//
			// Validate (Min/Max)
			String error = p_info.validate(index, value);
			if (error != null)
				log.warn(get_ColumnName(index) + "=" + value + " - " + error);
			// length for String
			if (p_info.getColumnClass(index) == String.class)
			{
				String stringValue = value.toString();
				int length = p_info.getFieldLength(index);
				if (stringValue.length() > length && length > 0)
				{
					log.warn(get_ColumnName(index) + " - Value too long - truncated to length=" + length);
					m_newValues[index] = stringValue.substring(0, length);
				}
			}
		}

		if (log.isTraceEnabled())
		{
			log.trace(get_ColumnName(index) + " = " + m_newValues[index] + " (" + (m_newValues[index] == null ? "-" : m_newValues[index].getClass().getName()) + ")");
		}

		set_Keys(get_ColumnName(index), m_newValues[index]);
		return true;
	}   // set_ValueNoCheck

	/**
	 * Set Encrypted Value w/o check (update, r/o, ..).
	 * Used when Column is R/O
	 * Required for key and parent values
	 *
	 * @param ColumnName column name
	 * @param value value
	 * @return true if value set
	 */
	protected final boolean set_ValueNoCheckE(String ColumnName, Object value)
	{
		return set_ValueNoCheck(ColumnName, value);
	}	// set_ValueNoCheckE

	/**
	 * Set value of Column
	 *
	 * @param columnName
	 * @param value
	 */
	public final void set_ValueOfColumn(String columnName, Object value)
	{
		set_ValueOfColumnReturningBoolean(columnName, value);
	}

	/**
	 * Set value of Column returning boolean
	 *
	 * @param columnName
	 * @param value
	 * @returns boolean indicating success or failure
	 */
	public final boolean set_ValueOfColumnReturningBoolean(final String columnName, final Object value)
	{
		final int columnIndex = p_info.getColumnIndex(columnName);
		if (columnIndex < 0)
		{
			log.error("Not found - ColumnName={}", columnName);
			return false;
		}

		return set_ValueReturningBoolean(columnIndex, value);
	}

	/**
	 * Set Value of AD_Column_ID
	 *
	 * @param AD_Column_ID column
	 * @param value value
	 * @returns boolean indicating success or failure
	 */
	public final boolean set_ValueOfAD_Column_ID(final int AD_Column_ID, final Object value)
	{
		final int columnIndex = p_info.getColumnIndex(AD_Column_ID);
		if (columnIndex < 0)
		{
			log.error("Not found - AD_Column_ID={}", AD_Column_ID);
			return false;
		}

		return set_ValueReturningBoolean(columnIndex, value);
	}

	public final boolean set_ValueReturningBoolean(final int columnIndex, final Object value)
	{
		final String columnName = p_info.getColumnName(columnIndex);
		if (COLUMNNAME_IsApproved.equals(columnName))
		{
			return set_ValueNoCheck(columnIndex, value);
		}
		else
		{
			return set_Value(columnIndex, value);
		}
	}

	/**
	 * Set Custom Column
	 *
	 * @param columnName column
	 * @param value value
	 */
	public final void set_CustomColumn(String columnName, Object value)
	{
		set_CustomColumnReturningBoolean(columnName, value);
	}	// set_CustomColumn

	/**
	 * Set Custom Column returning boolean
	 *
	 * @param columnName column
	 * @param value value
	 * @returns boolean indicating success or failure
	 */
	public final boolean set_CustomColumnReturningBoolean(String columnName, Object value)
	{
		// [ 1845793 ] PO.set_CustomColumn not updating correctly m_newValues
		// this is for columns not in PO - verify and call proper method if exists
		int poIndex = get_ColumnIndex(columnName);
		// metas: tsa: correct, it should be greather OR EQUAL because else first column is skiped and we get duplicate column error on insert
		if (poIndex >= 0)
		{
			// is not custom column - it exists in the PO
			return set_Value(columnName, value);
		}
		if (m_custom == null)
			m_custom = new HashMap<String, String>();
		String valueString = "NULL";
		if (value == null)
			;
		else if (value instanceof Number)
			valueString = value.toString();
		else if (value instanceof Boolean)
			valueString = ((Boolean)value).booleanValue() ? "'Y'" : "'N'";
		else if (value instanceof Timestamp)
			valueString = DB.TO_DATE((Timestamp)value, false);
		else // if (value instanceof String)
			valueString = DB.TO_STRING(value.toString());
		// Save it
		log.debug("Set custom column: {}={}", columnName, valueString);
		m_custom.put(columnName, valueString);
		return true;
	}	// set_CustomColumn

	/**
	 * Set (numeric) Key Value
	 *
	 * @param ColumnName column name
	 * @param value value
	 */
	private void set_Keys(String ColumnName, Object value)
	{
		// Update if KeyColumn
		for (int i = 0; i < m_IDs.length; i++)
		{
			if (ColumnName.equals(m_KeyColumns[i]))
			{
				m_IDs[i] = value;
			}
		} 	// for all key columns
	}	// setKeys

	/**************************************************************************
	 * Get Column Count
	 *
	 * @return column count
	 */
	public final int get_ColumnCount()
	{
		return p_info.getColumnCount();
	}   // getColumnCount

	/**
	 * Get Column Name
	 *
	 * @param index index
	 * @return ColumnName
	 */
	public final String get_ColumnName(int index)
	{
		return p_info.getColumnName(index);
	}   // getColumnName

	/**
	 * Is Column Mandatory
	 *
	 * @param index index
	 * @return true if column mandatory
	 */
	protected final boolean isColumnMandatory(int index)
	{
		return p_info.isColumnMandatory(index);
	}   // isColumnNandatory

	/**
	 * Is Column Updateable
	 *
	 * @param index index
	 * @return true if column updateable
	 */
	protected final boolean isColumnUpdateable(int index)
	{
		return p_info.isColumnUpdateable(index);
	}	// isColumnUpdateable

	/**
	 * Get Column DisplayType
	 *
	 * @param index index
	 * @return display type
	 */
	protected final int get_ColumnDisplayType(int index)
	{
		return p_info.getColumnDisplayType(index);
	}	// getColumnDisplayType

	/**
	 * Get Lookup
	 *
	 * @param index index
	 * @return Lookup or null
	 */
	protected final Lookup get_ColumnLookup(int index)
	{
		// NOTE: in case the PO was saved/deleted from a UI window then WindowNo is available
		final int windowNo = get_WindowNo();

		return p_info.getColumnLookup(getCtx(), windowNo, index);
	}   // getColumnLookup

	/**
	 * Get Column Index
	 *
	 * @param columnName column name
	 * @return index of column with ColumnName or -1 if not found
	 */
	public final int get_ColumnIndex(String columnName)
	{
		return p_info.getColumnIndex(columnName);
	}   // getColumnIndex

	/**
	 * Get Display Value of value
	 *
	 * @param columnName columnName
	 * @param currentValue current value
	 * @return String value with "./." as null
	 */
	public final String get_DisplayValue(String columnName, boolean currentValue)
	{
		Object value = currentValue ? get_Value(columnName) : get_ValueOld(columnName);
		if (value == null)
			return "./.";
		String retValue = value.toString();
		int index = get_ColumnIndex(columnName);
		if (index < 0)
			return retValue;
		int dt = get_ColumnDisplayType(index);
		if (DisplayType.isText(dt) || DisplayType.YesNo == dt)
			return retValue;
		// Lookup
		final Lookup lookup = get_ColumnLookup(index);
		if (lookup != null)
		{
			final IValidationContext evalCtx = Services.get(IValidationRuleFactory.class).createValidationContext(this);
			return lookup.getDisplay(evalCtx, value);
		}
		// Other
		return retValue;
	}	// get_DisplayValue

	/**
	 * Copy old values of From to new values of To.
	 * Does not copy Keys
	 *
	 * @param from old, existing & unchanged PO
	 * @param to new, not saved PO
	 * @param AD_Client_ID client
	 * @param AD_Org_ID org
	 */
	protected static void copyValues(PO from, PO to, int AD_Client_ID, int AD_Org_ID)
	{
		copyValues(from, to);
		to.setAD_Client_ID(AD_Client_ID);
		to.setAD_Org_ID(AD_Org_ID);
	}	// copyValues

	/**
	 * Copy old values of From to new values of To.
	 * Does not copy Keys and AD_Client_ID/AD_Org_ID.
	 * <p>
	 * <b>IMPORTANT:</b> Copies all columns, even if they are marked with <code>IsCalculated=Y</code>.
	 *
	 * @param from old, existing & unchanged PO
	 * @param to new, not saved PO
	 */
	public static void copyValues(PO from, PO to)
	{
		// metas: begin
		copyValues(from, to, false);
	}

	public static void copyValues(PO from, PO to, final boolean honorIsCalculated)
	{
		// metas: end
		s_log.debug("Copy values: From ID={}, To ID={}", from.get_ID(), to.get_ID());

		//
		// Make sure "from" and "to" objects are not stale (01537)
		from.loadIfStalled(-1);
		to.loadIfStalled(-1);

		//
		// Different Classes
		if (from.getClass() != to.getClass())
		{
			for (int fromColumnIndex = 0; fromColumnIndex < from.m_oldValues.length; fromColumnIndex++)
			{
				final String fromColumnName = from.p_info.getColumnName(fromColumnIndex); // metas: us215
				if (from.p_info.isVirtualColumn(fromColumnIndex)
						|| from.p_info.isKey(fromColumnIndex)) 		// KeyColumn
				{
					continue;
				}
				// metas: begin: us215
				else if (honorIsCalculated && from.p_info.isCalculated(fromColumnIndex))
				{
					for (int toColumnIndex = 0; toColumnIndex < to.m_oldValues.length; toColumnIndex++)
					{
						final String toColumnName = to.p_info.getColumnName(toColumnIndex);
						if (toColumnName.equals(fromColumnName))
						{
							if (to.getDynAttribute(PO.DYNATTR_CopyRecordSupport) != null)
							{
								CopyRecordSupport cps = (CopyRecordSupport)to.getDynAttribute(PO.DYNATTR_CopyRecordSupport);
								to.m_newValues[toColumnIndex] = cps.getValueToCopy(to, from, fromColumnName);
							}
							break;
						}
					}
				}
				// metas: end: us215
				// Ignore Standard Values
				else // metas: us215: use else
					if (fromColumnName.startsWith("Created")
							|| fromColumnName.startsWith("Updated")
							|| fromColumnName.equals("IsActive")
							// fresh 07896: skip copying org and client ONLY if it's calculated
							|| (to.p_info.isCalculated(fromColumnIndex)
									&& (fromColumnName.equals("AD_Client_ID") || fromColumnName.equals("AD_Org_ID")))
							|| fromColumnName.equals("DocumentNo")
							|| fromColumnName.equals("Processing")
							|| fromColumnName.equals("Processed") // metas: tsa: us215
				)
				{
					;	// ignore / skip this column
				}
				else
				{
					for (int toColumnIndex = 0; toColumnIndex < to.m_oldValues.length; toColumnIndex++)
					{
						final String toColumnName = to.p_info.getColumnName(toColumnIndex);
						if (toColumnName.equals(fromColumnName))
						{
							to.m_newValues[toColumnIndex] = from.m_oldValues[fromColumnIndex];
							break;
						}
					}
				}
			} 	// from loop
		}
		//
		// Same class
		else
		{
			for (int i = 0; i < from.m_oldValues.length; i++)
			{
				final String colName = from.p_info.getColumnName(i); // metas
				if (from.p_info.isVirtualColumn(i)
						|| from.p_info.isKey(i)) 		// KeyColumn
					continue;
				// metas: begin: us215
				else if (honorIsCalculated && from.p_info.isCalculated(i))
				{
					if (to.getDynAttribute(PO.DYNATTR_CopyRecordSupport) != null)
					{
						final CopyRecordSupport cps = (CopyRecordSupport)to.getDynAttribute(PO.DYNATTR_CopyRecordSupport);
						to.m_newValues[i] = cps.getValueToCopy(to, from, colName);
					}
				}
				// metas: end: us215
				// Ignore Standard Values
				else // metas: us215: use else
					if (colName.startsWith("Created")
							|| colName.startsWith("Updated")
							|| colName.equals("IsActive")
							// fresh 07896: skip copying org and client ONLY if it's calculated
							|| (to.p_info.isCalculated(i)
									&& (colName.equals("AD_Client_ID")
											|| colName.equals("AD_Org_ID")))
							|| colName.equals("DocumentNo")
							|| colName.equals("Processing")
							|| colName.equals("Processed") // metas: tsa: us215
				)
				{
					;	// ignore / skip this column
				}
				else
				{
					to.m_newValues[i] = from.m_oldValues[i];
					// metas: tsa: begin: when dealing with new POs copy their new values because old values are all null
					if (from.is_new())
					{
						to.m_newValues[i] = from.m_newValues[i];
					}
					// metas: tsa: Copy cached objects
					// NOTE: is is important because sometimes we have set a new object which is present in PO cache but it's ID is still zero.
					// Without doing this copy, the object will be lost when copying
					if (from.m_poCacheLocals != null)
					{
						final POCacheLocal poCacheLocal = from.m_poCacheLocals.get(colName);
						if (poCacheLocal != null)
						{
							if (to.m_poCacheLocals == null)
							{
								to.m_poCacheLocals = new HashMap<String, POCacheLocal>();
							}
							final POCacheLocal poCacheLocalCopy = poCacheLocal.copy(to);
							to.m_poCacheLocals.put(colName, poCacheLocalCopy);
						}
					}
					// metas: tsa: end
				}
			}
		} 	// same class

		// NOTE: don't copy the DynAttributes because this is how it is designed and some BLs are rellying on this (e.g. caching)
	}	// copy

	/**************************************************************************
	 * Load record with ID
	 *
	 * @param ID ID
	 * @param trxName transaction name
	 */
	protected final void load(final int ID, final String trxName)
	{
		log.trace("Loading ID={}", ID);
		if (ID > 0)
		{
			Check.assume(m_KeyColumns != null && m_KeyColumns.length == 1, "PO {} shall have one single primary key but it has: {}", this, m_KeyColumns);
			m_IDs = new Object[] { ID };
			load(trxName);
		}
		else	// new
		{
			loadDefaults();
			m_createNew = true;
			m_wasJustCreated = true;
			setKeyInfo();	// sets m_IDs
			loadComplete(true);
		}
	}	// load

	/**
	 * (re)Load record with m_ID[*]
	 *
	 * @param trxName transaction
	 * @return true if loaded
	 */
	public final boolean load(final String trxName)
	{
		m_loadingLock.lock();
		try
		{
			m_loading = true;
			return load0(trxName, false); // gh #986 isRetry=false because this is our first attempt to load the record
		}
		finally
		{
			m_loading = false;
			m_loadingLock.unlock();
		}
	}
	
	/**
	 * Do the actual loading.
	 * 
	 * @param trxName
	 * @param isRetry if there is a loading problem, we invoke the registered {@link INoDataFoundHandler}s and retry <b>one time</b>. This flag being {@code true} means that this invocation is that retry. 
	 */
	private final boolean load0(final String trxName, final boolean isRetry)
	{
		m_trxName = trxName;
		boolean success = true;
		final String sql = p_info.getSqlSelectByKeys();
		final int columnsCount = get_ColumnCount();

		//
		if (log.isTraceEnabled())
		{
			log.trace("Loading: {}", get_WhereClause(true));
		}

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, m_trxName);	// local trx only
			DB.setParameters(pstmt, m_IDs);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				success = load(rs);
			}
			else
			{
				if (!isRetry)
				{
					// gh #986 see if any noDataFoundHandler can do something and, if so, retry *once*
					if (NoDataFoundHandlers.get()
							.invokeHandlers(get_TableName(),
									m_IDs,
									InterfaceWrapperHelper.getContextAware(this)))
					{
						return load0(trxName, true); // this is the retry, so isRetry=true this time
					}
				}
				log.error("NO Data found for " + get_WhereClause(true) + ", trxName=" + m_trxName, new Exception());
				m_IDs = new Object[] { I_ZERO };
				success = false;
				// throw new DBException("NO Data found for " + get_WhereClause(true));
			}

			//
			// Make sure there is only one result for our query
			// else it's a programatic or application dictionary configuration error (i.e. Key IDs are not unique)
			if (rs.next())
			{
				final AdempiereException ex = new AdempiereException("More then one records were found while loading " + this + "."
						+ " Please make sure key columns are unique."
						+ "\n WhereClause: " + get_WhereClause(true));
				throw ex;
			}

			m_createNew = false;
			// reset new values
			m_newValues = new Object[columnsCount];

			//
			// Set Staled flag to false because we just loaded the object
			// metas: 01537
			m_stale = false;
		}
		catch (Exception e)
		{
			String msg = "";
			if (m_trxName != null)
				msg = "[" + m_trxName + "] - ";
			msg += get_WhereClause(true)
					// + ", Index=" + index
					// + ", Column=" + get_ColumnName(index)
					// + ", " + p_info.toString(index)
					+ ", SQL=" + sql.toString();
			success = false;
			m_IDs = new Object[] { I_ZERO };
			log.error(msg, e);
			// throw new DBException(e);
		}
		// Finish
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}

		loadComplete(success);

		return success;
	}   // load

	/**
	 * Load from the current position of a ResultSet
	 *
	 * @param rs result set
	 * @return true if loaded
	 */
	protected final boolean load(ResultSet rs)
	{
		int size = get_ColumnCount();
		boolean success = true;
		int index = 0;
		log.trace("Loading from ResultSet");
		// load column values
		for (index = 0; index < size; index++)
		{
			if (p_info.isLazyLoading(index))
				continue;
			if (!loadColumn(index, rs))
				success = false;
		}
		m_createNew = false;
		setKeyInfo();
		loadComplete(success);
		return success;
	}	// load

	private final boolean loadColumn(int index, ResultSet rs)
	{
		boolean success = true;
		String columnName = p_info.getColumnName(index);
		Class<?> clazz = p_info.getColumnClass(index);
		int dt = p_info.getColumnDisplayType(index);
		try
		{
			if (clazz == Integer.class)
				m_oldValues[index] = decrypt(index, rs.getInt(columnName));
			else if (clazz == BigDecimal.class)
				m_oldValues[index] = decrypt(index, rs.getBigDecimal(columnName));
			else if (clazz == Boolean.class)
				m_oldValues[index] = Boolean.valueOf("Y".equals(decrypt(index, rs.getString(columnName))));
			else if (clazz == Timestamp.class)
				m_oldValues[index] = decrypt(index, rs.getTimestamp(columnName));
			else if (DisplayType.isLOB(dt))
				m_oldValues[index] = get_LOB(rs.getObject(columnName));
			else if (clazz == String.class)
				m_oldValues[index] = decrypt(index, rs.getString(columnName));
			else
				m_oldValues[index] = loadSpecial(rs, index);

			//
			// If the column's value was NULL, set null to our old values array.
			// NOTE: business logic like org.adempiere.ad.dao.impl.GuaranteedPOBufferedIterator.isValidModel(ET) is rellying on this.
			if (rs.wasNull() && m_oldValues[index] != null)
			{
				m_oldValues[index] = null;
			}

			m_newValues[index] = null; // reset new value
			m_valueLoaded[index] = true; // mark the column as loaded
			//
			if (log.isTraceEnabled())
				log.trace(String.valueOf(index) + ": " + p_info.getColumnName(index) + "(" + p_info.getColumnClass(index) + ") = " + m_oldValues[index]);
		}
		catch (SQLException e)
		{
			if (p_info.isVirtualColumn(index)) 	// if rs constructor used
			{
				log.trace("Virtual Column not loaded: {}", columnName);
			}
			else
			{
				log.error("(rs) - " + String.valueOf(index)
						+ ": " + p_info.getTableName() + "." + p_info.getColumnName(index)
						+ " (" + p_info.getColumnClass(index) + ") - " + e);
				success = false;
			}
		}
		return success;
	}

	private boolean loadColumn(int index)
	{
		if (is_new())
		{
			return false;
		}
		boolean success = true;

		final String sql = "SELECT " + p_info.getColumnSqlForSelect(index)
				+ " FROM " + p_info.getTableName()
				+ " WHERE " + get_WhereClause(false);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, get_TrxName());
			DB.setParameters(pstmt, m_IDs);
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				success = loadColumn(index, rs);
			}
		}
		catch (SQLException e)
		{
			throw new DBException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		return success;
	}

	/**
	 * Load from HashMap
	 *
	 * @param hmIn hash map
	 * @return true if loaded
	 */
	protected final boolean load(HashMap<String, String> hmIn)
	{
		int size = get_ColumnCount();
		boolean success = true;
		int index = 0;
		log.trace("Loading from HashMap");
		// load column values
		for (index = 0; index < size; index++)
		{
			String columnName = p_info.getColumnName(index);
			String value = hmIn.get(columnName);
			if (value == null)
				continue;
			Class<?> clazz = p_info.getColumnClass(index);
			int dt = p_info.getColumnDisplayType(index);
			try
			{
				if (clazz == Integer.class)
					m_oldValues[index] = new Integer(value);
				else if (clazz == BigDecimal.class)
					m_oldValues[index] = new BigDecimal(value);
				else if (clazz == Boolean.class)
					m_oldValues[index] = Boolean.valueOf("Y".equals(value));
				else if (clazz == Timestamp.class)
					m_oldValues[index] = Timestamp.valueOf(value);
				else if (DisplayType.isLOB(dt))
					m_oldValues[index] = null;	// get_LOB (rs.getObject(columnName));
				else if (clazz == String.class)
					m_oldValues[index] = value;
				else
					m_oldValues[index] = null;	// loadSpecial(rs, index);
				//
				if (log.isTraceEnabled())
					log.trace(String.valueOf(index) + ": " + p_info.getColumnName(index) + "(" + p_info.getColumnClass(index) + ") = " + m_oldValues[index]);
			}
			catch (Exception e)
			{
				if (p_info.isVirtualColumn(index)) 	// if rs constructor used
				{
					log.trace("Virtual Column not loaded: {}", columnName);
				}
				else
				{
					log.error("(ht) - " + String.valueOf(index)
							+ ": " + p_info.getTableName() + "." + p_info.getColumnName(index)
							+ " (" + p_info.getColumnClass(index) + ") - " + e);
					success = false;
				}
			}
		}
		m_createNew = false;
		m_stale = false; // metas: 01537
		// Overwrite
		setStandardDefaults();
		setKeyInfo();
		loadComplete(success);
		return success;
	}	// load

	/**
	 * Load column if object is staled.
	 *
	 * @param requestedColumnIndex column index to load; if requestedColumnIndex is less then ZERO then object will be loaded anyway
	 * @task 01537
	 */
	private final void loadIfStalled(int requestedColumnIndex)
	{
		// Object is not staled, nothing to do
		if (!m_stale)
		{
			return;
		}

		// Loading is in progress, nothing to do
		if (m_loading)
		{
			return;
		}

		//
		// Check if our column can get stale
		if (requestedColumnIndex >= 0)
		{
			// If there are no stale columns, set stale status to false and return right now
			if (!p_info.hasStaleableColumns())
			{
				m_stale = false;
				return;
			}

			// Ask POInfo if there is a chance that our column to get stale
			if (!p_info.isColumnStaleable(requestedColumnIndex))
			{
				return;
			}
		}

		if (log.isDebugEnabled())
		{
			log.debug("Loading stalled object: {}", this);
		}

		if (p_info.hasStaleableColumns())
		{
			if (!load(get_TrxName()))
			{
				throw new AdempiereException();
			}
		}
		else
		{
			// No staleable columns => there is no need to reload
			m_stale = false;
		}

		Check.assume(!m_stale, "Internal Error: object is stalled even after loading");
	}

	/**
	 * Create Hashmap with data as Strings
	 *
	 * @return HashMap
	 */
	protected final HashMap<String, String> get_HashMap()
	{
		HashMap<String, String> hmOut = new HashMap<String, String>();
		int size = get_ColumnCount();
		for (int i = 0; i < size; i++)
		{
			Object value = get_Value(i);
			// Don't insert NULL values (allows Database defaults)
			if (value == null
					|| p_info.isVirtualColumn(i))
				continue;
			// Display Type
			int dt = p_info.getColumnDisplayType(i);
			// Based on class of definition, not class of value
			Class<?> c = p_info.getColumnClass(i);
			String stringValue = null;
			if (c == Object.class)
				;	// saveNewSpecial (value, i));
			else if (value == null || value.equals(Null.NULL))
				;
			else if (value instanceof Integer || value instanceof BigDecimal)
				stringValue = value.toString();
			else if (c == Boolean.class)
			{
				boolean bValue = false;
				if (value instanceof Boolean)
					bValue = ((Boolean)value).booleanValue();
				else
					bValue = "Y".equals(value);
				stringValue = bValue ? "Y" : "N";
			}
			else if (value instanceof Timestamp)
				stringValue = value.toString();
			else if (c == String.class)
				stringValue = (String)value;
			else if (DisplayType.isLOB(dt))
				;
			else
				;	// saveNewSpecial (value, i));
			//
			if (stringValue != null)
				hmOut.put(p_info.getColumnName(i), stringValue);
		}
		// Custom Columns
		if (m_custom != null)
		{
			Iterator<String> it = m_custom.keySet().iterator();
			while (it.hasNext())
			{
				String column = it.next();
				// int index = p_info.getColumnIndex(column);
				String value = m_custom.get(column);
				if (value != null)
					hmOut.put(column, value);
			}
			m_custom = null;
		}
		return hmOut;
	}   // get_HashMap

	/**
	 * Load Special data (images, ..).
	 * To be extended by sub-classes
	 *
	 * @param rs result set
	 * @param index zero based index
	 * @return value value
	 * @throws SQLException
	 */
	protected Object loadSpecial(ResultSet rs, int index) throws SQLException
	{
		if (log.isTraceEnabled())
		{
			log.trace("Loading special column {}: (NOP)", p_info.getColumnName(index));
		}

		return null;
	}   // loadSpecial

	/**
	 * Method called when load is complete.
	 *
	 * @param success success
	 */
	private final void loadComplete(final boolean success)
	{
		this.m_loadCount++;

		onLoadComplete(success);
	}

	/**
	 * Called when load is complete. To be extended by sub-classes.
	 *
	 * @param success success
	 */
	protected void onLoadComplete(boolean success)
	{
		// nothing at this level
	}

	/**
	 * Load Defaults
	 */
	protected final void loadDefaults()
	{
		setStandardDefaults();
		//
		/**
		 * @todo defaults from Field
		 */
		// MField.getDefault(p_info.getDefaultLogic(i));
	}	// loadDefaults

	/**
	 * Set Default values.
	 * Client, Org, Created/Updated, *By, IsActive
	 */
	protected final void setStandardDefaults()
	{
		// TODO: metas: 01537 - should we check here if object is stalled?

		final Properties ctx = getCtx();

		final int size = get_ColumnCount();
		for (int i = 0; i < size; i++)
		{
			if (p_info.isVirtualColumn(i))
				continue;
			String colName = p_info.getColumnName(i);
			// Set Standard Values
			if (colName.endsWith("tedBy"))
				m_newValues[i] = new Integer(Env.getAD_User_ID(ctx));
			else if (colName.equals("Created") || colName.equals("Updated"))
				m_newValues[i] = new Timestamp(System.currentTimeMillis());
			else if (colName.equals(p_info.getTableName() + "_ID"))     // KeyColumn
				m_newValues[i] = I_ZERO;
			else if (colName.equals("IsActive"))
				m_newValues[i] = Boolean.TRUE;
			else if (colName.equals("AD_Client_ID"))
				m_newValues[i] = new Integer(Env.getAD_Client_ID(ctx));
			else if (colName.equals("AD_Org_ID"))
				m_newValues[i] = new Integer(Env.getAD_Org_ID(ctx));
			else if (colName.equals("Processed"))
				m_newValues[i] = Boolean.FALSE;
			else if (colName.equals("Processing"))
				m_newValues[i] = Boolean.FALSE;
			else if (colName.equals("Posted"))
				m_newValues[i] = Boolean.FALSE;
		}
	}   // setDefaults

	/**
	 * Set Key Info (IDs and KeyColumns).
	 */
	private void setKeyInfo()
	{
		final int size = m_KeyColumns.length;
		m_IDs = new Object[size];
		for (int i = 0; i < size; i++)
		{
			String keyColumnName = m_KeyColumns[i];
			final Object valueObj = get_Value(keyColumnName);
			if (keyColumnName.endsWith("_ID"))
			{
				Integer valueInt;
				try
				{
					valueInt = (Integer)valueObj;
					if (valueInt == null)
					{
						valueInt = I_ZERO;
					}
				}
				catch (Exception e)
				{
					log.error("Failed to cast ID column to Integer: " + keyColumnName + ", value=" + valueObj, e);
					valueInt = null;
				}

				m_IDs[i] = valueInt;
			}
			else
			{
				m_IDs[i] = valueObj;
			}
		}
	}	// setKeyInfo

	/**************************************************************************
	 * Are all mandatory Fields filled (i.e. can we save)?.
	 * Stops at first null mandatory field
	 *
	 * @return true if all mandatory fields are ok
	 */
	protected final boolean isMandatoryOK()
	{
		int size = get_ColumnCount();
		for (int i = 0; i < size; i++)
		{
			if (p_info.isColumnMandatory(i))
			{
				if (p_info.isVirtualColumn(i))
					continue;
				if (get_Value(i) == null || get_Value(i).equals(Null.NULL))
				{
					// log.info(p_info.getColumnName(i));
					return false;
				}
			}
		}
		return true;
	}   // isMandatoryOK

	/**************************************************************************
	 * Set AD_Client
	 *
	 * @param AD_Client_ID client
	 */
	final protected void setAD_Client_ID(int AD_Client_ID)
	{
		set_ValueNoCheck("AD_Client_ID", new Integer(AD_Client_ID));
	}	// setAD_Client_ID

	/**
	 * Get AD_Client
	 *
	 * @return AD_Client_ID
	 */
	@Override
	public final int getAD_Client_ID()
	{
		Integer ii = (Integer)get_Value("AD_Client_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}	// getAD_Client_ID

	/**
	 * Set AD_Org
	 *
	 * @param AD_Org_ID org
	 */
	final public void setAD_Org_ID(int AD_Org_ID)
	{
		set_ValueNoCheck("AD_Org_ID", new Integer(AD_Org_ID));
	}	// setAD_Org_ID

	/**
	 * Get AD_Org
	 *
	 * @return AD_Org_ID
	 */
	@Override
	public int getAD_Org_ID()
	{
		Integer ii = (Integer)get_Value("AD_Org_ID");
		if (ii == null)
			return 0;
		return ii.intValue();
	}	// getAD_Org_ID

	/**
	 * Overwrite Client Org if different
	 *
	 * @param AD_Client_ID client
	 * @param AD_Org_ID org
	 */
	@OverridingMethodsMustInvokeSuper
	protected void setClientOrg(int AD_Client_ID, int AD_Org_ID)
	{
		if (AD_Client_ID != getAD_Client_ID())
			setAD_Client_ID(AD_Client_ID);
		if (AD_Org_ID != getAD_Org_ID())
			setAD_Org_ID(AD_Org_ID);
	}	// setClientOrg

	/**
	 * Overwrite Client Org if different
	 *
	 * @param po persistent object
	 */
	protected final void setClientOrg(PO po)
	{
		setClientOrg(po.getAD_Client_ID(), po.getAD_Org_ID());
	}	// setClientOrg

	protected final void setClientOrgFromModel(final Object model)
	{
		final PO po = InterfaceWrapperHelper.getStrictPO(model);
		Check.assumeNotNull(po, "po not null for {}", model);
		setClientOrg(po);
	}

	/**
	 * Set Active
	 *
	 * @param active active
	 */
	public final void setIsActive(boolean active)
	{
		set_Value("IsActive", active);
	}	// setActive

	/**
	 * Is Active
	 *
	 * @return is active
	 */
	public final boolean isActive()
	{
		Boolean bb = (Boolean)get_Value("IsActive");
		if (bb != null)
			return bb.booleanValue();
		return false;
	}	// isActive

	/**
	 * Get Created
	 *
	 * @return created
	 */
	final public Timestamp getCreated()
	{
		return (Timestamp)get_Value("Created");
	}	// getCreated

	/**
	 * Get Updated
	 *
	 * @return updated
	 */
	final public Timestamp getUpdated()
	{
		return (Timestamp)get_Value("Updated");
	}	// getUpdated

	/**
	 * Get CreatedBy
	 *
	 * @return AD_User_ID
	 */
	final public int getCreatedBy()
	{
		Integer ii = (Integer)get_Value("CreatedBy");
		if (ii == null)
			return 0;
		return ii.intValue();
	}	// getCreateddBy

	/**
	 * Get UpdatedBy
	 *
	 * @return AD_User_ID
	 */
	final public int getUpdatedBy()
	{
		Integer ii = (Integer)get_Value("UpdatedBy");
		if (ii == null)
			return 0;
		return ii.intValue();
	}	// getUpdatedBy

	/**
	 * Set UpdatedBy
	 *
	 * @param AD_User_ID user
	 */
	final protected void setUpdatedBy(int AD_User_ID)
	{
		set_ValueNoCheck("UpdatedBy", new Integer(AD_User_ID));
	}	// setAD_User_ID

	/**
	 * Get Translation of column (if needed).
	 * It checks if the base language is used or the column is not translated.
	 * If there is no translation then it fallback to original value.
	 *
	 * @param columnName
	 * @param AD_Language
	 * @return translated string
	 * @throws IllegalArgumentException if columnName or AD_Language is null or model has multiple PK
	 */
	public final String get_Translation(final String columnName, final String AD_Language)
	{
		//
		// Check if columnName, AD_Language is valid or table support translation (has 1 PK) => error
		if (columnName == null || AD_Language == null
				|| m_IDs.length > 1 || m_IDs[0].equals(I_ZERO)
				|| !(m_IDs[0] instanceof Integer))
		{
			throw new IllegalArgumentException("ColumnName=" + columnName
					+ ", AD_Language=" + AD_Language
					+ ", ID.length=" + m_IDs.length
					+ ", ID=" + m_IDs[0]);
		}

		String retValue = null;
		//
		// Check if NOT base language and column is translated => load trl from db
		if (!Env.isBaseLanguage(AD_Language, get_TableName())
				&& p_info.isColumnTranslated(p_info.getColumnIndex(columnName)))
		{
			// Load translation from database
			// metas: begin
			final IModelTranslation trlMap = get_ModelTranslation(AD_Language);
			if (trlMap.isTranslated(columnName))
			{
				retValue = trlMap.getTranslation(columnName);
				// Case: is possible that the translation in one language to be empty
				// and we don't want to fallback to original value (where is possible to not be empty)
				if (retValue == null)
					retValue = "";
			}
		}

		//
		// If no translation found or not translated, fallback to original:
		if (retValue == null)
		{
			Object val = get_Value(columnName);
			retValue = (val != null ? val.toString() : null);
		}
		//
		return retValue;
	}	// get_Translation

	/**
	 * Get Translation of column
	 *
	 * @param ctx context
	 * @param columnName
	 * @return translation
	 */
	public final String get_Translation(String columnName)
	{
		return get_Translation(columnName, Env.getAD_Language(getCtx()));
	}

	/**
	 * Is new record
	 *
	 * @return true if new
	 */
	public final boolean is_new()
	{
		return m_createNew;

		// NOTE: we are not checking for IDs anymore because:
		// * it's not always OK (consider the case when we have records with ID=0)
		// * i think long time ago, we did that because "m_createNew" was set during a save and if the save failed, the "m_createNew" was not set back
		// * below is the old logic (entirely)
		// if (m_createNew)
		// {
		// return true;
		// }
		//
		// for (int i = 0; i < m_IDs.length; i++)
		// {
		// if (m_IDs[i].equals(I_ZERO) || m_IDs[i] == Null.NULL)
		// continue;
		// return false; // one value is non-zero
		// }
		// return true;
	}	// is_new

	/**
	 *
	 * @return true if this object was just created (saved or not).
	 */
	public final boolean is_JustCreated()
	{
		return m_wasJustCreated;
	}

	private final void createChangeLog(final String changeLogType)
	{
		//
		// Don't create change logs if is not activated on table level
		if (!p_info.isChangeLog())
		{
			return;
		}

		//
		// Don't create change logs if they are not activated in ChangeLog system/BL
		if (!Services.get(ISessionBL.class).isChangeLogEnabled())
		{
			return;
		}

		//
		// Don't create change logs if there is no one single primary key, because AD_ChangeLog cannot link to composed primary key (or no primary key at all)
		if (!p_info.hasKeyColumn())
		{
			return;
		}

		//
		// FRESH-314: create a change log also if there is no AD_Session_ID; also store the AD_PInstance_ID
		final I_AD_Session session = get_Session();
		final int adSessionId = session != null ? session.getAD_Session_ID() : 0;
		final int adPInstanceId = Env.getContextAsInt(getCtx(), Env.CTXNAME_AD_PInstance_ID);

		final int adClientId = getAD_Client_ID();
		final boolean isInsertChangeLogEvent = X_AD_ChangeLog.EVENTCHANGELOG_Insert.equals(changeLogType);
		boolean logIfKeyOnly = false;
		if (isInsertChangeLogEvent)
		{
			final String insertChangeLogType = Services.get(ISysConfigBL.class).getValue("SYSTEM_INSERT_CHANGELOG", "Y", adClientId);
			if ("Y".equals(insertChangeLogType))
			{
				// log everything allowed
			}
			// log only keys
			else if ("K".equals(insertChangeLogType))
			{
				logIfKeyOnly = true;
			}
			else
			{
				// log nothing
				return;
			}
		}

		final int recordId = get_ID();
		final int adTableId = p_info.getAD_Table_ID();
		final int adOrgId = getAD_Org_ID();
		final int adUserId = Env.getAD_User_ID(getCtx());

		//
		// Iterate all columns
		List<ChangeLogRecord> changeLogRecords = null;
		final int columnCount = get_ColumnCount();
		for (int i = 0; i < columnCount; i++)
		{
			if (logIfKeyOnly && !p_info.getColumn(i).IsKey)
			{
				continue;
			}

			if (!p_info.isAllowLogging(i))
			{
				continue;
			}
			if (p_info.isVirtualColumn(i))
			{
				continue;
			}
			if (p_info.isEncrypted(i))
			{
				continue;
			}

			final String columnName = p_info.getColumnName(i);
			if ("Password".equals(columnName))
			{
				continue;
			}

			//
			// Get Old and New values to be logged for current column
			Object valueOld = null;
			Object valueNew = null;
			if (isInsertChangeLogEvent)
			{
				// FIXME: this code is old and not working anyways
				// Object copyRecordSupportOldValue = getDynAttribute(DYNATTR_CopyRecordSupport_OldValue);
				// CopyRecordSupport copyRecordSupport = (CopyRecordSupport)getDynAttribute(DYNATTR_CopyRecordSupport);
				// if (copyRecordSupportOldValue != null || copyRecordSupport != null)
				// valueOld = copyRecordSupport == null ? copyRecordSupportOldValue : copyRecordSupport.getFromPO_ID();

				valueOld = null;
				valueNew = m_newValues[i];
			}
			else if (X_AD_ChangeLog.EVENTCHANGELOG_Update.equals(changeLogType))
			{
				valueOld = m_oldValues[i];
				valueNew = m_newValues[i];
				if (valueNew == null)
				{
					valueNew = valueOld;
				}
			}
			else if (X_AD_ChangeLog.EVENTCHANGELOG_Delete.equals(changeLogType))
			{
				valueOld = get_ValueOld(i);
				valueNew = null;
			}
			else
			{
				throw new IllegalArgumentException("Unknown ChangeLogType: " + changeLogType);
			}

			//
			// Convert Null.NULL to "null"
			if (valueOld != null && valueOld == Null.NULL)
			{
				valueOld = null;
			}
			if (valueNew != null && valueNew == Null.NULL)
			{
				valueNew = null;
			}

			//
			// Create Change Log record
			final ChangeLogRecord changeLogRecord = ChangeLogRecord.builder()
					.setAD_Session_ID(adSessionId)
					.setAD_PInstance_ID(adPInstanceId) // FRESH-314
					.setTrxName(m_trxName)
					.setAD_Table_ID(adTableId)
					.setAD_Column_ID(p_info.getColumn(i).getAD_Column_ID())
					.setRecord_ID(recordId)
					.setAD_Client_ID(adClientId)
					.setAD_Org_ID(adOrgId)
					.setOldValue(valueOld)
					.setNewValue(valueNew)
					.setEventType(changeLogType)
					.setAD_User_ID(adUserId)
					.build();

			if (changeLogRecords == null)
			{
				changeLogRecords = new ArrayList<>();
			}
			changeLogRecords.add(changeLogRecord);
		}

		//
		// Save change log records
		if (changeLogRecords != null)
		{
			Services.get(ISessionDAO.class).saveChangeLogs(changeLogRecords);
		}
	}

	private final void logMigration(final String actionType)
	{
		if (!Ini.isPropertyBool(Ini.P_LOGMIGRATIONSCRIPT))
		{
			return;
		}
		final I_AD_Session session = get_Session();
		if (session == null)
		{
			return;
		}

		Services.get(IMigrationLogger.class).logMigration(session, this, p_info, actionType);
	}

	/*
	 * Classes which override save() method:
	 * org.compiere.process.DocActionTemplate
	 * org.compiere.model.MClient
	 * org.compiere.model.MClientInfo
	 * org.compiere.model.MSystem
	 */
	/**************************************************************************
	 * Update Value or create new record. To reload call load() - not updated
	 * The operation is performed, unless
	 * <ul>
	 * <li>it was disabled via {@link InterfaceWrapperHelper#setSaveDeleteDisabled(Object, boolean)}. In this case it will throw an exception.
	 * <li>the accesslevel (see {@link #get_AccessLevel()}) isn't compatible with values of <code>AD_Client_ID</code>/<code>AD_Org_ID</code></code>.
	 * <li>{@link #beforeSave(boolean)()} or {@link #afterSave(boolean, boolean)} returns <code>false</code> or throws an {@link Exception}
	 * <li>a model validator returns an error message or throws an exception.
	 * </ul>
	 *
	 * @return true if saved
	 */
	public final boolean save()
	{
		boolean success = false;
		try
		{
			saveEx();
			success = true;
		}
		catch (Exception e)
		{
			success = false;
			log.error("Error while saving {}", this, e);

			// task 08596: w need to save the error in case po.save() was called from the gridtab.
			// It will want to show the result of CLogger.retrieveError() to the user.
			MetasfreshLastError.saveError(log, "", e);
		}

		return success;
	}	// save

	/**
	 * Update Value or create new record.
	 *
	 * @throws AdempiereException
	 * @see #save()
	 */
	public final void saveEx() throws AdempiereException
	{
		//
		// Check and prepare the saving
		// (this shall happen before running the part which is handled in transaction)
		final boolean saveNeeded = savePrepare();
		if (!saveNeeded)
		{
			return;
		}

		final ITrxManager trxManager = get_TrxManager();
		final String trxNameInitial = m_trxName;
		final boolean newRecordInitial = m_createNew;
		trxManager.run(trxNameInitial, new TrxRunnable2()
		{

			@Override
			public void run(final String localTrxName) throws Exception
			{
				m_trxName = localTrxName;
				save0();
			}

			@Override
			public boolean doCatch(Throwable e) throws Throwable
			{
				// restoring settings and flags before failing
				m_createNew = newRecordInitial;

				throw e;
			}

			@Override
			public void doFinally()
			{
				// restore the transaction name
				m_trxName = trxNameInitial;
			}
		});
	}

	/**
	 * Check & prepare the persistent object for saving.
	 *
	 * @return <code>true</code> if save is needed; <code>false</code> if no save is needed
	 */
	private final boolean savePrepare()
	{
		MetasfreshLastError.resetLast();
		final boolean newRecord = is_new();	// save locally as load resets
		if (!newRecord && !is_Changed())
		{
			log.debug("Save prepare: nothing changed - {}", this);
			return false; // no save is needed
		}

		// Organization Check
		final TableAccessLevel accessLevel = get_TableAccessLevel();
		if (getAD_Org_ID() <= 0
				&& (accessLevel == TableAccessLevel.Organization
						|| (accessLevel == TableAccessLevel.ClientPlusOrganization
								&& MClientShare.isOrgLevelOnly(getAD_Client_ID(), get_Table_ID()))))
		{
			throw new FillMandatoryException("AD_Org_ID");
		}
		// Should be Org 0
		if (getAD_Org_ID() != 0)
		{
			boolean reset = accessLevel.isSystemOnly();
			if (!reset && MClientShare.isClientLevelOnly(getAD_Client_ID(), get_Table_ID()))
			{
				reset = accessLevel.isClient();
			}
			if (reset)
			{
				log.warn("Save prepare: Set AD_Org_ID to 0 for {}", this);
				setAD_Org_ID(0);
			}
		}

		//
		// Check if saving is allowed
		if (InterfaceWrapperHelper.isSaveDeleteDisabled(this))
		{
			throw new AdempiereException("Save/Delete is disabled for " + this);
		}

		//
		// In case we create a local transaction, fire first TYPE_BEFORE_SAVE_TRX event
		// we trigger the event only if was not started in other place, like in a process for example
		// metas: tsa: 02380
		if (m_trxName == null)
		{
			fireModelChange(ModelValidator.TYPE_BEFORE_SAVE_TRX);
		}

		return true; // save is needed
	}

	private final void save0() throws Exception
	{
		final boolean newRecord = is_new();	// save locally as load resets

		// Before Save
		if (!isAssignedID)
		{
			if (!beforeSave(newRecord))
			{
				throw new AdempiereException("beforeSave failed - " + toString());
			}
		}

		// Call ModelValidators TYPE_NEW/TYPE_CHANGE
		fireModelChange(newRecord ? ModelValidator.TYPE_BEFORE_NEW : ModelValidator.TYPE_BEFORE_CHANGE);

		// Save
		if (newRecord)
		{
			final boolean b = saveNew();
			if (!b)
			{
				throw new AdempiereException("saveNew failed");
			}
		}
		else
		{
			final boolean b = saveUpdate();
			if (!b)
			{
				throw new AdempiereException("saveUpdate failed");
			}
		}
	}

	/**
	 * Finish Save Process. Called after {@link #saveNew()} or {@link #saveUpdate()}.
	 *
	 * @param newRecord new
	 * @param success success
	 * @return true if saved
	 */
	private final boolean saveFinish(final boolean newRecord, boolean success) throws Exception
	{
		// Translations
		if (success)
		{
			if (newRecord)
				insertTranslations();
			else
				updateTranslations();
		}

		if (!isAssignedID)
		{
			//
			try
			{
				success = afterSave(newRecord, success);
				if (success)
				{
					if (newRecord)
						MTree.insertTreeNode(this);
					else
						MTree.updateTreeNode(this);
				}

				// start: c.ghita@metas.ro
				CopyRecordSupport copyRecordSupport = (CopyRecordSupport)getDynAttribute(DYNATTR_CopyRecordSupport);
				if (copyRecordSupport != null && success)
				{
					copyRecordSupport.setParentID(get_ID());
					copyRecordSupport.setParentPO(this);
					copyRecordSupport.copyRecord(this, get_TrxName());
					copyRecordSupport = null;
					setDynAttribute(DYNATTR_CopyRecordSupport, null);
				}
				// end: c.ghita@metas.ro
			}
			catch (final Exception e)
			{
				// In case there was an error encountered, propagate it.
				// The caller(s) will take care to leave this PO in a consistent state.
				throw AdempiereException.wrapIfNeeded(e);
			}
		}

		//
		// Call ModelValidators TYPE_AFTER_NEW/TYPE_AFTER_CHANGE - teo_sarca [ 1675490 ]
		if (success)
		{
			final boolean replication = isReplication();
			fireModelChange(newRecord ? (replication ? ModelValidator.TYPE_AFTER_NEW_REPLICATION : ModelValidator.TYPE_AFTER_NEW)
					: (replication ? ModelValidator.TYPE_AFTER_CHANGE_REPLICATION : ModelValidator.TYPE_AFTER_CHANGE));
		}

		final int columnsCount = p_info.getColumnCount();

		// OK
		if (success)
		{
			//
			// Fire Workflow Manager on document value changed
			fireDocWorkflowManager();

			//
			// Copy New to Old values
			for (int i = 0; i < columnsCount; i++)
			{
				if (m_newValues[i] != null)
				{
					if (m_newValues[i] == Null.NULL)
						m_oldValues[i] = null;
					else
						m_oldValues[i] = m_newValues[i];
				}
			}
			m_newValues = new Object[columnsCount];
		}

		//
		// Mark columns as loaded
		if (success && newRecord)
		{
			for (int i = 0; i < columnsCount; i++)
			{
				// Skip virtual columns, those need to be loaded for sure
				if (p_info.isVirtualColumn(i))
				{
					m_valueLoaded[i] = false;
					continue;
				}

				m_valueLoaded[i] = true;
			}
		}

		//
		// Un-set "createNew" flag because it's not a new record anymore
		m_createNew = false;

		//
		// Reset cache
		if (!newRecord)
		{
			final int id = get_ID();
			CacheMgt.get().resetOnTrxCommit(get_TrxName(), p_info.getTableName(), id);
		}

		//
		// Deferred processing of this po (metas-ts 1076)
		if (success)
		{
			fireModelChange(ModelValidator.TYPE_SUBSEQUENT);
		}

		//
		// Return "success"
		return success;
	}	// saveFinish

	private final void fireDocWorkflowManager()
	{
		if (s_docWFMgr == null)
		{
			try
			{
				Class.forName("org.compiere.wf.DocWorkflowManager");
			}
			catch (Exception e)
			{
			}
		}
		if (s_docWFMgr != null)
		{
			s_docWFMgr.process(this);
		}
	}

	/**
	 * Sets given trxName and save it.
	 *
	 * @param trxName transaction
	 * @return true if saved
	 */
	public final boolean save(final String trxName)
	{
		set_TrxName(trxName);
		return save();
	}	// save

	/**
	 * Sets given trxName and save it.
	 *
	 * @param trxName transaction
	 * @throws AdempiereException
	 * @see #saveEx(String)
	 */
	public final void saveEx(final String trxName) throws AdempiereException
	{
		set_TrxName(trxName);
		saveEx();
	}

	/**
	 * Set <code>isReplica=true</code>. Calls {@link #saveEx()} afterwards.
	 *
	 * @param isFromReplication
	 * @throws AdempiereException
	 */
	public final void saveExReplica(boolean isFromReplication) throws AdempiereException
	{
		setReplication(isFromReplication);
		saveEx();
	}

	/**
	 * Is there a Change to be saved?
	 *
	 * @return true if record changed
	 */
	public final boolean is_Changed()
	{
		int size = get_ColumnCount();
		for (int i = 0; i < size; i++)
		{
			// Test if the column has changed - teo_sarca [ 1704828 ]
			if (is_ValueChanged(i))
				return true;
		}
		if (m_custom != null && m_custom.size() > 0)
			return true; // there are custom columns modified
		return false;
	}	// is_Change

	/**
	 * Called before Save for Pre-Save Operation
	 *
	 * @param newRecord new record
	 * @return true if record can be saved
	 */
	protected boolean beforeSave(boolean newRecord)
	{
		/**
		 * Prevents saving
		 * log.error("Error", Msg.parseTranslation(getCtx(), "@C_Currency_ID@ = @C_Currency_ID@"));
		 * log.error("FillMandatory", Msg.getElement(getCtx(), "PriceEntered"));
		 * /** Issues message
		 * log.saveWarning(AD_Message, message);
		 * log.saveInfo (AD_Message, message);
		 **/
		return true;
	}	// beforeSave

	/**
	 * Called after Save for Post-Save Operation
	 *
	 * @param newRecord new record
	 * @param success true if save operation was success
	 * @return if save was a success
	 */
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		return success;
	}	// afterSave

	/**
	 * Update Record directly
	 *
	 * @return true if updated
	 */
	private final boolean saveUpdate() throws Exception
	{
		String where = get_WhereClause(true);
		//
		boolean changes = false;
		StringBuilder sql = new StringBuilder("UPDATE ");
		sql.append(p_info.getTableName()).append(" SET ");
		boolean updated = false;
		boolean updatedBy = false;
		lobReset();

		int size = get_ColumnCount();
		for (int i = 0; i < size; i++)
		{
			Object value = m_newValues[i];
			if (value == null
					|| p_info.isVirtualColumn(i))
				continue;
			// we have a change
			final Class<?> c = p_info.getColumnClass(i);
			final int dt = p_info.getColumnDisplayType(i);
			final String columnName = p_info.getColumnName(i);
			//
			// updated/by
			if (columnName.equals("UpdatedBy"))
			{
				if (updatedBy) 	// explicit
					continue;
				updatedBy = true;
			}
			else if (columnName.equals("Updated"))
			{
				if (updated)
					continue;
				updated = true;
			}
			if (DisplayType.isLOB(dt))
			{
				lobAdd(value, i, dt);
				// If no changes set UpdatedBy explicitly to ensure commit of lob
				if (!changes && !updatedBy)
				{
					final int AD_User_ID = Env.getAD_User_ID(getCtx());
					set_ValueNoCheck("UpdatedBy", new Integer(AD_User_ID));
					sql.append("UpdatedBy=").append(AD_User_ID);
					changes = true;
					updatedBy = true;
				}
				continue;
			}
			// Update Document No
			if (columnName.equals("DocumentNo"))
			{
				String documentNo = (String)value;
				if (IPreliminaryDocumentNoBuilder.hasPreliminaryMarkers(documentNo))
				{
					value = null;
					int docTypeIndex = p_info.getColumnIndex("C_DocTypeTarget_ID");
					if (docTypeIndex == -1)
					{
						docTypeIndex = p_info.getColumnIndex("C_DocType_ID");
					}
					if (docTypeIndex != -1) 		// get based on Doc Type (might return null)
					{
						final int docTypeId = get_ValueAsInt(docTypeIndex);
						value = documentNoFactory.forDocType(docTypeId, false) // useDefiniteSequence=false
								.setTrxName(m_trxName)
								.setDocumentModel(this)
								.setFailOnError(false)
								.build();
					}
					if (value == null) 	// not overwritten by DocType and not manually entered
					{
						value = documentNoFactory.forTableName(p_info.getTableName(), getAD_Client_ID(), getAD_Org_ID())
								.setTrxName(m_trxName)
								.setDocumentModel(this)
								.setFailOnError(false)
								.build();
						value = value == IDocumentNoBuilder.NO_DOCUMENTNO ? null : value; // just to make sure we get null in case no DocumentNo
					}
					set_ValueNoCheck(i, value);
				}
				else
				{
					log.warn("DocumentNo updated: " + m_oldValues[i] + " -> " + value);
				}
			}

			if (changes)
				sql.append(", ");
			changes = true;
			sql.append(columnName).append("=");

			// values
			if (value == Null.NULL)
				sql.append("NULL");
			else if (value instanceof Integer || value instanceof BigDecimal)
				sql.append(encrypt(i, value));
			else if (c == Boolean.class)
			{
				boolean bValue = false;
				if (value instanceof Boolean)
					bValue = ((Boolean)value).booleanValue();
				else
					bValue = "Y".equals(value);
				sql.append(encrypt(i, bValue ? "'Y'" : "'N'"));
			}
			else if (value instanceof Timestamp)
				sql.append(DB.TO_DATE((Timestamp)encrypt(i, value), p_info.getColumnDisplayType(i) == DisplayType.Date));
			else
			{
				if (value.toString().length() == 0)
				{
					// [ 1722057 ] Encrypted columns throw error if saved as null
					// don't encrypt NULL
					sql.append(DB.TO_STRING(value.toString()));
				}
				else
				{
					sql.append(encrypt(i, DB.TO_STRING(value.toString())));
				}
			}
		} 	// for all fields

		// Custom Columns (cannot be logged as no column)
		if (m_custom != null)
		{
			Iterator<String> it = m_custom.keySet().iterator();
			while (it.hasNext())
			{
				if (changes)
					sql.append(", ");
				changes = true;
				//
				String column = it.next();
				String value = m_custom.get(column);
				int index = p_info.getColumnIndex(column);
				sql.append(column).append("=").append(encrypt(index, value));
			}
			m_custom = null;
		}

		// Something changed
		if (changes)
		{
			if (log.isDebugEnabled())
				log.debug("[" + m_trxName + "] - " + p_info.getTableName() + "." + where);

			if (!updated) 	// Updated not explicitly set
			{
				Timestamp now = new Timestamp(System.currentTimeMillis());
				set_ValueNoCheck("Updated", now);
				sql.append(",Updated=").append(DB.TO_DATE(now, false));
			}
			if (!updatedBy) 	// UpdatedBy not explicitly set
			{
				final int AD_User_ID = Env.getAD_User_ID(getCtx());
				set_ValueNoCheck("UpdatedBy", AD_User_ID);
				sql.append(",UpdatedBy=").append(AD_User_ID);
			}
			sql.append(" WHERE ").append(where);
			/**
			 * @todo status locking goes here
			 */

			//
			// Execute UPDATE SQL
			log.trace("Save update: SQL={}", sql);
			final int no;
			if (isUseTimeoutForUpdate())
				no = DB.executeUpdateEx(sql.toString(), m_trxName, QUERY_TIME_OUT);
			else
				no = DB.executeUpdateEx(sql.toString(), m_trxName);
			boolean ok = no == 1;

			//
			// Create change logs
			if (ok)
			{
				createChangeLog(X_AD_ChangeLog.EVENTCHANGELOG_Update);
				logMigration(X_AD_MigrationStep.ACTION_Update);
			}

			if (ok)
			{
				ok = lobSave();
			}
			else
			{
				log.warn("#" + no + " - [" + m_trxName + "] - " + p_info.getTableName() + "." + where);
			}

			return saveFinish(false, ok); // newRecord=false, success=ok
		}

		// nothing changed, so OK=true
		return saveFinish(false, true);  // newRecord=false, success=true
	}   // saveUpdate

	private final boolean isUseTimeoutForUpdate()
	{
		return "true".equalsIgnoreCase(System.getProperty(USE_TIMEOUT_FOR_UPDATE, "false"))
				&& DB.getDatabase().isQueryTimeoutSupported();
	}

	/**
	 * Sets ID (first one)
	 *
	 * @param id new id
	 * @return old id
	 */
	private final Object set_ID(final Object id)
	{
		final Object idOld = m_IDs[0];
		m_IDs[0] = id;
		set_ValueNoCheck(m_KeyColumns[0], m_IDs[0]);
		return idOld;
	}

	/**
	 * Set's the IDs on SAVE NEW
	 *
	 * @return true if succeed (even if it does nothing); false ONLY if failed
	 */
	private final boolean retrieveAndSetIds()
	{
		// ID was assigned externally. Nothing to do
		if (isAssignedID)
		{
			return true;
		}

		// Set ID for single key - Multi-Key values need explicitly be set previously
		if (m_IDs.length != 1)
		{
			return true;
		}
		if (!p_info.hasKeyColumn())
		{
			return true;
		}

		// Check if ID column is Integer (i.e. shall end with "_ID")
		if (!m_KeyColumns[0].endsWith("_ID"))
		{
			return true;
		}

		Integer idNew = saveNew_getID();

		if (idNew <= 0
				&& DB.isUseNativeSequences(getAD_Client_ID(), get_TableName()))
		{
			idNew = I_ZERO_NATIVESEQUENCE;
		}
		else if (idNew <= 0)
		{
			idNew = DB.getNextID(getAD_Client_ID(), p_info.getTableName(), m_trxName);
			if (idNew <= 0)
			{
				final AdempiereException ex = new AdempiereException("No NextID (" + idNew + ") for " + p_info.getTableName());
				// log.error(ex.getLocalizedMessage(), ex);
				// return false;
				throw ex;
			}
		}

		final Object idOld = set_ID(idNew);
		final boolean createNewOld = m_createNew;

		//
		// We are registering a fallback trx listener: in case the transaction fails we need to revert IDs to their old values
		final ITrx trx = get_TrxManager().get(m_trxName, OnTrxMissingPolicy.Fail);
		trx.getTrxListenerManager().registerListener(
				true,  // weak because in case the object is not referenced anymore there is no point to update it's status
				new TrxListenerAdapter()
				{

					@Override
					public void afterRollback(final ITrx trx)
					{
						// revert ID
						set_ID(idOld);
						m_createNew = createNewOld;
					}
				});

		return true;
	}

	/**
	 * Create New Record
	 *
	 * @return true if new record inserted
	 */
	private boolean saveNew() throws Exception
	{
		//
		// Generate and set new IDs
		if (!retrieveAndSetIds())
		{
			// Setting new IDs failed.
			// Execute saveFinish directly (last line in this method)
			return saveFinish(true, false); // newRecord=true, success=false
		}

		final String tableName = p_info.getTableName();
		POReturningAfterInsertLoader loadAfterInsertProcessor = new POReturningAfterInsertLoader();

		if (log.isDebugEnabled())
		{
			log.debug("[" + m_trxName + "] - " + tableName + " - " + get_WhereClause(true));
		}

		// Set new DocumentNo
		{
			final String columnName = "DocumentNo";
			final int index = p_info.getColumnIndex(columnName);
			if (index != -1 && p_info.isUseDocSequence(index))
			{
				String value = (String)get_Value(index);
				if (value != null && IPreliminaryDocumentNoBuilder.hasPreliminaryMarkers(value))
					value = null;
				if (value == null || value.isEmpty())
				{
					value = null; // metas: tsa: seq is not automatically fetched on tables with no docType if value is ""
					int docTypeIndex = p_info.getColumnIndex("C_DocTypeTarget_ID");
					if (docTypeIndex == -1)
					{
						docTypeIndex = p_info.getColumnIndex("C_DocType_ID");
					}
					if (docTypeIndex != -1) 		// get based on Doc Type (might return null)
					{
						value = documentNoFactory.forDocType(get_ValueAsInt(docTypeIndex), false) // useDefiniteSequence=false
								.setTrxName(m_trxName)
								.setDocumentModel(this)
								.setFailOnError(false)
								.build();
					}
					if (value == null || value == IDocumentNoBuilder.NO_DOCUMENTNO) 	// not overwritten by DocType and not manually entered
					{
						value = documentNoFactory.forTableName(tableName, getAD_Client_ID(), getAD_Org_ID())
								.setTrxName(m_trxName)
								.setDocumentModel(this)
								.setFailOnError(false)
								.build();
						value = value == IDocumentNoBuilder.NO_DOCUMENTNO ? null : value; // just to make sure we get null in case no DocumentNo
					}
					set_ValueNoCheck(columnName, value);

					Services.get(IDocumentNoBL.class).fireDocumentNoChange(this, value); // task 09776
				}
			}
		}

		// handle an empty Value column
		{
			final String columnName = "Value";
			final int index = p_info.getColumnIndex(columnName);
			if (index < 0)
			{
				// no "Value" column exist => do nothing
			}
			else if (p_info.isUseDocSequence(index))
			{
				String value = (String)get_Value(index);
				if(IPreliminaryDocumentNoBuilder.hasPreliminaryMarkers(value))
				{
					value = null;
				}
				
				if (value == null || value.isEmpty())
				{
					// metas: using AD_Org_ID as additional parameter
					value = documentNoFactory.forTableName(tableName, getAD_Client_ID(), getAD_Org_ID())
							.setTrxName(m_trxName)
							.setDocumentModel(this)
							.setFailOnError(true) // backward compatiblity: initially here an DBException was thrown
							.build();
					set_ValueNoCheck(index, value);
				}
			}
			else if (p_info.isColumnMandatory(index))
			{
				final String value = (String)get_Value(index);
				if (value == null || value.length() == 0)
				{
					// gh #213 as of yesterday we make a distinction between NULL and "", so we need to set NULL here. Otherwise, the DB won't return anything for us.
					set_ValueNoCheck(index, Null.NULL);
					loadAfterInsertProcessor.addColumnName(columnName);
				}
			}
		}

		lobReset();

		//
		// Build INSERT SQL
		final StringBuilder sqlInsert = new StringBuilder("INSERT INTO ");
		sqlInsert.append(tableName).append(" (");
		final StringBuilder sqlValues = new StringBuilder(") VALUES (");
		final int size = get_ColumnCount();
		boolean doComma = false;
		for (int i = 0; i < size; i++)
		{
			// Skip virtual columns
			if (p_info.isVirtualColumn(i))
			{
				continue;
			}

			final String columnName = p_info.getColumnName(i);
			final Object value = get_Value(i);
			final int dt = p_info.getColumnDisplayType(i);

			// Don't insert NULL values (allows Database defaults)
			if (value == null)
			{
				//
				// Case: We deal with an ID column which it's mandatory but Value is null
				// => Do nothing, but retrieve it from database after insert
				// NOTE: atm the single case we know if AD_ChangeLog.AD_ChangeLog_ID which it's ID but is not primary key
				if (dt == DisplayType.ID
						&& p_info.isColumnMandatory(i))
				{
					loadAfterInsertProcessor.addColumnName(columnName);
				}
				//
				// Case: We deal with a staleable column which has no value specified on inserting
				// => Do nothing, but retrieve it from database after insert
				else if (p_info.isColumnStaleable(i))
				{
					loadAfterInsertProcessor.addColumnName(columnName);
				}

				continue;
			}

			// Display Type
			if (DisplayType.isLOB(dt))
			{
				lobAdd(value, i, dt);
				continue;
			}

			// ** add column **
			if (doComma)
			{
				sqlInsert.append(",");
				sqlValues.append(",");
			}
			else
			{
				doComma = true;
			}

			sqlInsert.append(columnName);

			//
			// Based on class of definition, not class of value
			final Class<?> c = p_info.getColumnClass(i);
			try
			{
				//
				// Case: ColumnName is the primary key and we were asked to use native sequences
				if (value == I_ZERO_NATIVESEQUENCE && columnName.equals(p_info.getKeyColumnName()))
				{
					final String sqlValue = DB.TO_TABLESEQUENCE_NEXTVAL(tableName);
					sqlValues.append(sqlValue);

					// Add it to columns to retrieve after database insert
					loadAfterInsertProcessor.addColumnName(columnName);
				}
				else if (c == Object.class)  // may have need to deal with null values differently
				{
					sqlValues.append(saveNewSpecial(value, i));
				}
				else if (value == null || value.equals(Null.NULL))
				{
					sqlValues.append("NULL");
				}
				else if (value instanceof Integer || value instanceof BigDecimal)
					sqlValues.append(encrypt(i, value));
				else if (c == Boolean.class)
				{
					boolean bValue = false;
					if (value instanceof Boolean)
						bValue = ((Boolean)value).booleanValue();
					else
						bValue = "Y".equals(value);
					sqlValues.append(encrypt(i, bValue ? "'Y'" : "'N'"));
				}
				else if (value instanceof Timestamp)
				{
					sqlValues.append(DB.TO_DATE((Timestamp)encrypt(i, value), p_info.getColumnDisplayType(i) == DisplayType.Date));
				}
				else if (c == String.class)
				{
					sqlValues.append(encrypt(i, DB.TO_STRING((String)value)));
				}
				else if (DisplayType.isLOB(dt))
				{
					sqlValues.append("null");		// no db dependent stuff here
				}
				else
				{
					sqlValues.append(saveNewSpecial(value, i));
				}
			}
			catch (Exception e)
			{
				String msg = "";
				if (m_trxName != null)
					msg = "[" + m_trxName + "] - ";
				msg += p_info.toString(i)
						+ " - Value=" + value
						+ "(" + (value == null ? "null" : value.getClass().getName()) + ")";
				log.error(msg, e);
				throw new DBException(e);	// fini
			}
		}

		//
		// Custom Columns
		if (m_custom != null)
		{
			final Iterator<String> it = m_custom.keySet().iterator();
			while (it.hasNext())
			{
				final String column = it.next();
				final int index = p_info.getColumnIndex(column);
				String value = m_custom.get(column);
				if (doComma)
				{
					sqlInsert.append(",");
					sqlValues.append(",");
				}
				else
					doComma = true;
				sqlInsert.append(column);
				// jz for ad_issue, some value may include ' in a string???
				sqlValues.append(encrypt(index, value));
			}
			m_custom = null;
		}

		//
		// Build the final INSERT sql
		sqlInsert.append(sqlValues)
				.append(")");

		//
		// Append the "RETURNING" clause if needed
		if (loadAfterInsertProcessor.hasColumnNames())
		{
			sqlInsert.append(" RETURNING ").append(loadAfterInsertProcessor.getSqlReturning());
		}
		else
		{
			// If there are no columns to load, get rid of this load after insert processor
			loadAfterInsertProcessor = null;
		}

		//
		// Execute actual database INSERT
		final int no = DB.executeUpdate(sqlInsert.toString(),
				(Object[])null,  // params,
				OnFail.ThrowException,  // onFail
				m_trxName,
				0,  // timeOut,
				loadAfterInsertProcessor);
		boolean ok = no == 1;

		//
		// Save LOBs
		if (ok)
		{
			ok = lobSave();
		}

		//
		// Record Change Logs
		if (ok)
		{
			createChangeLog(X_AD_ChangeLog.EVENTCHANGELOG_Insert);
			logMigration(X_AD_MigrationStep.ACTION_Insert);
		}

		if (ok)
		{
			//
			// If we don't need to load the object right-away after save, we just mark it as staled (01537)
			if (!p_info.isLoadAfterSave())
			{
				m_stale = true;
			}
			//
			// Re-load this object right now
			else if (!load(m_trxName))
			{
				log.error("[" + m_trxName + "] - reloading");
				ok = false;
			}
		}
		else
		{
			String msg = "Not inserted - ";
			if (LogManager.isLevelFiner())
				msg += sqlInsert.toString();
			else
				msg += get_TableName();
			if (m_trxName == null)
				log.warn(msg);
			else
				log.warn("[" + m_trxName + "]" + msg);
		}

		return saveFinish(true, ok);
	}   // saveNew

	/**
	 * Get ID for new record during save.
	 * You can overwrite this to explicitly set the ID
	 *
	 * @return ID to be used or 0 for default logic
	 */
	protected int saveNew_getID()
	{
		// NOTE: we shall not enforce only IDs which are less than 999999, we shall take what we get.
		// NOTE2: rest-webui is relying on this!
		// if (get_ID() < 999999) // 2Pack assigns official ID's when importing
		// return get_ID();
		// return 0;

		final int id = get_ID();
		return id;
	}	// saveNew_getID

	/**
	 * Create Single/Multi Key Where Clause
	 *
	 * @param withValues if true uses actual values otherwise ?
	 * @return where clause
	 */
	public final String get_WhereClause(boolean withValues)
	{
		if (!withValues)
		{
			return p_info.getSqlWhereClauseByKeys();
		}

		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < m_IDs.length; i++)
		{
			if (i != 0)
				sb.append(" AND ");
			sb.append(m_KeyColumns[i]).append("=");
			if (m_KeyColumns[i].endsWith("_ID"))
				sb.append(m_IDs[i]);
			else
				sb.append("'").append(m_IDs[i]).append("'");
		}
		return sb.toString();
	}	// getWhereClause

	/**
	 * Save Special Data.
	 * To be extended by sub-classes
	 *
	 * @param value value
	 * @param index index
	 * @return SQL code for INSERT VALUES clause
	 */
	protected String saveNewSpecial(Object value, int index)
	{
		String colName = p_info.getColumnName(index);
		String colClass = p_info.getColumnClass(index).toString();
		String colValue = value == null ? "null" : value.getClass().toString();
		// int dt = p_info.getColumnDisplayType(index);

		log.error("Unknown class for column " + colName
				+ " (" + colClass + ") - Value=" + colValue);

		if (value == null)
			return "NULL";
		return value.toString();
	}   // saveNewSpecial

	/**
	 * Encrypt data.
	 * Not: LOB, special values/Objects
	 *
	 * @param index index
	 * @param xx data
	 * @return xx
	 */
	private Object encrypt(int index, Object xx)
	{
		if (xx == null)
			return null;
		if (index != -1 && p_info.isEncrypted(index))
			return SecureEngine.encrypt(xx);
		return xx;
	}	// encrypt

	/**
	 * Decrypt data
	 *
	 * @param index index
	 * @param yy data
	 * @return yy
	 */
	private Object decrypt(int index, Object yy)
	{
		if (yy == null)
			return null;
		if (index != -1 && p_info.isEncrypted(index))
			return SecureEngine.decrypt(yy);
		return yy;
	}	// decrypt

	/**************************************************************************
	 * Delete Current Record, unless
	 * <ul>
	 * <li>it was disabled via {@link InterfaceWrapperHelper#setSaveDeleteDisabled(Object, boolean)}. In this case it will throw an exception.
	 * <li>the record was processed and <code>force</code> is <code>false</code>.
	 * <li>{@link #beforeDelete()} or {@link #afterDelete(boolean)} returns <code>false</code> or throws an {@link Exception}
	 * <li>a model validator returns an error message or throws an exception.
	 * </ul>
	 *
	 * @param force delete also processed records
	 * @return true if deleted
	 */
	public final boolean delete(final boolean force)
	{
		boolean success = false;
		try
		{
			deleteEx(force);
			success = true;
		}
		catch (Exception e)
		{
			success = false;
			log.error("Error while deleting " + this, e);
		}

		return success;
	}	// delete

	/**
	 * Delete Current Record
	 *
	 * @param force delete also processed records
	 * @throws AdempiereException
	 * @see #delete(boolean)
	 */
	public final void deleteEx(final boolean force) throws AdempiereException
	{
		//
		// Check and prepare the deleting
		// (this shall happen before running the part which is handled in transaction)
		final boolean deleteNeeded = deletePrepare(force);
		if (!deleteNeeded)
		{
			return;
		}

		final ITrxManager trxManager = get_TrxManager();
		final String trxNameInitial = m_trxName;
		trxManager.run(trxNameInitial, new TrxRunnable2()
		{

			@Override
			public void run(final String localTrxName) throws Exception
			{
				m_trxName = localTrxName;
				delete0();
			}

			@Override
			public boolean doCatch(Throwable e) throws Throwable
			{
				throw e;
			}

			@Override
			public void doFinally()
			{
				// restore the transaction name
				m_trxName = trxNameInitial;
			}
		});
	}

	/**
	 * Check & prepare the persistent object for deleting.
	 *
	 * @param force force delete (i.e. delete even if the record is processed)
	 * @return <code>true</code> if delete is needed; <code>false</code> if delete is not needed
	 */
	private final boolean deletePrepare(final boolean force)
	{
		MetasfreshLastError.resetLast();
		if (is_new())
		{
			return false; // delete not needed because the record is new and never saved
		}

		//
		// Make sure record is not already processed (or we do "force" delete)
		if (!force)
		{
			int iProcessed = get_ColumnIndex("Processed");
			if (iProcessed != -1)
			{
				final Boolean processed = (Boolean)get_Value(iProcessed);
				if (processed != null && processed.booleanValue())
				{
					throw new AdempiereException("@CannotDelete@ (@Processed@): " + this);
				}
			} 	// processed
		} 	// force

		//
		// Check if deleting is allowed
		if (InterfaceWrapperHelper.isSaveDeleteDisabled(this))
		{
			throw new AdempiereException("Save/Delete is disabled for: " + this);
		}

		return true; // delete is needed
	}

	private final void delete0()
	{
		final int AD_Table_ID = p_info.getAD_Table_ID();
		final int Record_ID = get_ID();
		final String trxName = m_trxName;

		//
		// Before delete
		if (!beforeDelete())
		{
			throw new AdempiereException("beforeDelete failed: " + this);
		}

		//
		// Delete Restrict AD_Table_ID/Record_ID (Requests, ..)
		{
			final String errorMsg = PO_Record.exists(AD_Table_ID, Record_ID, m_trxName);
			if (errorMsg != null)
			{
				throw new AdempiereException("@CannotDelete@ (" + errorMsg + "): " + this);
			}
		}

		// Call ModelValidators TYPE_DELETE
		{
			fireModelChange(isReplication() ? ModelValidator.TYPE_BEFORE_DELETE_REPLICATION : ModelValidator.TYPE_BEFORE_DELETE);
		}

		// Delete translations, if any
		deleteTranslations(trxName);

		// Delete Cascade AD_Table_ID/Record_ID (Attachments, ..)
		PO_Record.deleteCascade(AD_Table_ID, Record_ID, trxName);

		//
		// The Delete Statement
		final StringBuilder sql = new StringBuilder("DELETE FROM ") // jz why no FROM??
				.append(p_info.getTableName())
				.append(" WHERE ")
				.append(get_WhereClause(true));
		final int no;
		if (isUseTimeoutForUpdate())
			no = DB.executeUpdateEx(sql.toString(), trxName, QUERY_TIME_OUT);
		else
			no = DB.executeUpdateEx(sql.toString(), trxName);
		boolean success = no == 1;

		// Save ID
		m_idOld = get_ID();
		//
		if (!success)
		{
			throw new AdempiereException("@CannotDelete@: " + this + "+; SQL update returned no=" + no + "; SQL=(" + sql + ")");
		}
		else
		{
			createChangeLog(X_AD_ChangeLog.EVENTCHANGELOG_Delete);
			logMigration(X_AD_MigrationStep.ACTION_Delete);

			// Housekeeping
			m_IDs[0] = I_ZERO;
			m_attachment = null;

			if (log.isDebugEnabled())
				log.debug("[" + m_trxName + "] - complete");
		}

		success = afterDelete(success);
		if (success)
			MTree.deleteTreeNode(this);

		// Call ModelValidators TYPE_AFTER_DELETE - teo_sarca [ 1675490 ]
		if (success)
		{
			fireModelChange(ModelValidator.TYPE_AFTER_DELETE); // metas: use fireModelChange method - 01512
		}

		// Reset
		if (success)
		{
			int size = p_info.getColumnCount();
			m_oldValues = new Object[size];
			m_newValues = new Object[size];
			m_valueLoaded = new boolean[size]; // metas
			m_stale = false; // metas: 01537

			CacheMgt.get().resetOnTrxCommit(trxName, p_info.getTableName(), m_idOld);
			m_idOld = 0;
		}
	}

	/**
	 * Delete Current Record
	 *
	 * @param force delete also processed records
	 * @param trxName transaction
	 * @return true if deleted
	 */
	public final boolean delete(boolean force, String trxName)
	{
		set_TrxName(trxName);
		return delete(force);
	}	// delete

	/**
	 * Delete Current Record
	 *
	 * @param force delete also processed records
	 * @param trxName transaction
	 * @throws AdempiereException
	 * @see {@link #deleteEx(boolean)}
	 */
	public final void deleteEx(boolean force, String trxName) throws AdempiereException
	{
		set_TrxName(trxName);
		deleteEx(force);
	}

	/**
	 * Executed before Delete operation.
	 *
	 * @return true if record can be deleted
	 */
	protected boolean beforeDelete()
	{
		// log.error("Error", Msg.getMsg(getCtx(), "CannotDelete"));
		return true;
	} 	// beforeDelete

	/**
	 * Executed after Delete operation.
	 *
	 * @param success true if record deleted
	 * @return true if delete is a success
	 */
	protected boolean afterDelete(boolean success)
	{
		return success;
	} 	// afterDelete

	/**
	 * Insert (missing) Translation Records
	 *
	 * @return false if error (true if no translation or success)
	 */
	public final boolean insertTranslations()
	{
		// Not a translation table
		if (m_IDs.length > 1 || m_IDs.length == 0
				|| m_IDs[0].equals(I_ZERO)
				|| !p_info.isTranslated()
				|| !(m_IDs[0] instanceof Integer))
			return true;
		//
		StringBuilder iColumns = new StringBuilder();
		StringBuilder sColumns = new StringBuilder();
		for (int i = 0; i < p_info.getColumnCount(); i++)
		{
			if (p_info.isColumnTranslated(i))
			{
				iColumns.append(p_info.getColumnName(i))
						.append(",");
				sColumns.append("t.")
						.append(p_info.getColumnName(i))
						.append(",");
			}
		}
		if (iColumns.length() == 0)
			return true;

		String tableName = p_info.getTableName();
		String keyColumn = m_KeyColumns[0];
		StringBuilder sql = new StringBuilder("INSERT INTO ")
				.append(tableName).append("_Trl (AD_Language,")
				.append(keyColumn).append(", ")
				.append(iColumns)
				.append(" IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) ")
				.append("SELECT l.AD_Language,t.")
				.append(keyColumn).append(", ")
				.append(sColumns)
				.append(" 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy ")
				.append("FROM AD_Language l, ").append(tableName).append(" t ")
				.append("WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.")
				.append(keyColumn).append("=").append(get_ID())
				.append(" AND NOT EXISTS (SELECT * FROM ").append(tableName)
				.append("_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.")
				.append(keyColumn).append("=t.").append(keyColumn).append(")");
		int no = DB.executeUpdateEx(sql.toString(), m_trxName);
		log.debug("Inserted {} translation records for {}", no, this);
		m_translations = null; // metas
		return no > 0;
	}	// insertTranslations

	/**
	 * Update Translations.
	 *
	 * @return false if error (true if no translation or success)
	 */
	private boolean updateTranslations()
	{
		// Not a translation table
		if (m_IDs.length > 1
				|| m_IDs[0].equals(I_ZERO)
				|| !p_info.isTranslated()
				|| !(m_IDs[0] instanceof Integer))
			return true;
		//
		boolean trlColumnChanged = false;
		for (int i = 0; i < p_info.getColumnCount(); i++)
		{
			if (p_info.isColumnTranslated(i)
					&& is_ValueChanged(p_info.getColumnName(i)))
			{
				trlColumnChanged = true;
				break;
			}
		}
		if (!trlColumnChanged)
			return true;
		//
		MClient client = MClient.get(getCtx());
		//
		String tableName = p_info.getTableName();
		String keyColumn = m_KeyColumns[0];
		StringBuilder sql = new StringBuilder("UPDATE ")
				.append(tableName).append("_Trl SET ");
		//
		if (client.isAutoUpdateTrl(tableName))
		{
			for (int i = 0; i < p_info.getColumnCount(); i++)
			{
				if (p_info.isColumnTranslated(i))
				{
					String columnName = p_info.getColumnName(i);
					sql.append(columnName).append("=");
					Object value = get_Value(columnName);
					if (value == null)
						sql.append("NULL");
					else if (value instanceof String)
						sql.append(DB.TO_STRING((String)value));
					else if (value instanceof Boolean)
						sql.append(((Boolean)value).booleanValue() ? "'Y'" : "'N'");
					else if (value instanceof Timestamp)
						sql.append(DB.TO_DATE((Timestamp)value));
					else
						sql.append(value.toString());
					sql.append(",");
				}
			}
			sql.append("IsTranslated='Y'");
		}
		else
			sql.append("IsTranslated='N'");
		//
		sql.append(" WHERE ")
				.append(keyColumn).append("=").append(get_ID());
		int no = DB.executeUpdate(sql.toString(), m_trxName);
		log.debug("Updated {} translation records for {}", no, this);
		m_translations = null; // metas
		return no >= 0;
	}	// updateTranslations

	/**
	 * Delete Translation Records
	 *
	 * @param trxName transaction
	 * @return false if error (true if no translation or success)
	 */
	private boolean deleteTranslations(String trxName)
	{
		// Not a translation table
		if (m_IDs.length > 1
				|| m_IDs[0].equals(I_ZERO)
				|| !p_info.isTranslated()
				|| !(m_IDs[0] instanceof Integer))
			return true;
		//
		String tableName = p_info.getTableName();
		String keyColumn = m_KeyColumns[0];
		StringBuilder sql = new StringBuilder("DELETE FROM  ")
				.append(tableName).append("_Trl WHERE ")
				.append(keyColumn).append("=").append(get_ID());
		int no = DB.executeUpdate(sql.toString(), trxName);
		log.debug("Deleted {} translation records for {}", no, this);
		m_translations = null; // metas
		return no >= 0;
	}	// deleteTranslations

	/**
	 * Insert Accounting Records
	 *
	 * @param acctTable accounting sub table
	 * @param acctBaseTable acct table to get data from
	 * @param whereClause optional where clause with alias "p" for acctBaseTable
	 * @return true if records inserted
	 */
	// task 05372: make method public so we can insert accountings from not-M-classes
	public final boolean insert_Accounting(
			final String acctTable,
			final String acctBaseTable,
			final String whereClause)
	{
		if (s_acctColumns == null	// cannot cache C_BP_*_Acct as there are 3
				|| acctTable.startsWith("C_BP_"))
		{
			s_acctColumns = new ArrayList<String>();
			final String sql = "SELECT c.ColumnName "
					+ "FROM AD_Column c INNER JOIN AD_Table t ON (c.AD_Table_ID=t.AD_Table_ID) "
					+ "WHERE t.TableName=? AND c.IsActive='Y' AND c.AD_Reference_ID=25 ORDER BY c.ColumnName";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setString(1, acctTable);
				rs = pstmt.executeQuery();
				while (rs.next())
				{
					s_acctColumns.add(rs.getString(1));
				}
			}
			catch (Exception e)
			{
				log.error(acctTable, e);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
			if (s_acctColumns.size() == 0)
			{
				log.error("No Columns for " + acctTable);
				return false;
			}
		}

		// Create SQL Statement - INSERT
		final StringBuilder sb = new StringBuilder("INSERT INTO ")
				.append(acctTable)
				.append(" (").append(get_TableName())
				.append("_ID, C_AcctSchema_ID, AD_Client_ID,AD_Org_ID,IsActive, Created,CreatedBy,Updated,UpdatedBy ");
		for (int i = 0; i < s_acctColumns.size(); i++)
		{
			sb.append(",").append(s_acctColumns.get(i));
		}
		// .. SELECT
		sb.append(") SELECT ")
				.append(get_ID())
				.append(", p.C_AcctSchema_ID, p.AD_Client_ID,0,'Y', now(),")
				.append(getUpdatedBy())
				.append(",now(),")
				.append(getUpdatedBy());

		for (int i = 0; i < s_acctColumns.size(); i++)
		{
			sb.append(",p.").append(s_acctColumns.get(i));
		}
		// .. FROM
		sb.append(" FROM ").append(acctBaseTable)
				.append(" p WHERE p.AD_Client_ID=").append(getAD_Client_ID());

		if (whereClause != null && whereClause.length() > 0)
		{
			sb.append(" AND ").append(whereClause);
		}
		sb.append(" AND NOT EXISTS (SELECT * FROM ").append(acctTable)
				.append(" e WHERE e.C_AcctSchema_ID=p.C_AcctSchema_ID AND e.")
				.append(get_TableName()).append("_ID=").append(get_ID()).append(")");
		//
		final int no = DB.executeUpdate(sb.toString(), get_TrxName());
		if (no > 0)
		{
			log.debug("Inserted {} accounting records for {}", no, this);
		}
		else
		{
			final AdempiereException ex = new AdempiereException("No accouting records were inserted."
					+ "\n Acct Table: " + acctTable
					+ "\n Base Table: " + acctBaseTable
					+ "\n SQL: " + sb
					+ "\n PO: " + this
					+ "\n TrxName: " + get_TrxName()
					+ "\n Ctx: " + getCtx());
			log.warn(ex.getLocalizedMessage(), ex);
		}
		return no > 0;
	}	// insert_Accounting

	/**
	 * Delete Accounting records.
	 * NOP - done by database constraints
	 *
	 * @param acctTable accounting sub table
	 * @return true
	 */
	public final boolean delete_Accounting(final String acctTable)
	{
		return true;
	}	// delete_Accounting

	/**
	 * Insert id data into Tree
	 *
	 * @param treeType MTree TREETYPE_*
	 * @return true if inserted
	 * @deprecated Do not call this method anymore. Tree maintenance is automatically made.
	 */
	@Deprecated
	protected final boolean insert_Tree(String treeType)
	{
		return true;
	}	// insert_Tree

	/**
	 * Delete ID Tree Nodes
	 *
	 * @param treeType MTree TREETYPE_*
	 * @return true if deleted
	 * @deprecated Do not call this method anymore. Tree maintenance is automatically made.
	 */
	@Deprecated
	protected final boolean delete_Tree(String treeType)
	{
		return true;
	}	// delete_Tree

	/**************************************************************************
	 * Lock it.
	 *
	 * @return true if locked
	 */
	public final boolean lock()
	{
		int index = get_ProcessingIndex();
		if (index != -1)
		{
			set_ValueNoCheck(index, true); // metas: 01537: replaced: m_newValues[index] = Boolean.TRUE; // direct
			String sql = "UPDATE " + p_info.getTableName()
					+ " SET Processing='Y' WHERE (Processing='N' OR Processing IS NULL) AND "
					+ get_WhereClause(true);
			boolean success = false;
			if (isUseTimeoutForUpdate())
				success = DB.executeUpdateEx(sql, null, QUERY_TIME_OUT) == 1;	// outside trx
			else
				success = DB.executeUpdate(sql, null) == 1;	// outside trx
			if (success)
				log.debug("success");
			else
				log.warn("failed");
			return success;
		}
		return false;
	}	// lock

	/**
	 * Get the Column Processing index
	 *
	 * @return index or -1
	 */
	private int get_ProcessingIndex()
	{
		return p_info.getColumnIndex("Processing");
	}	// getProcessingIndex

	/**
	 * UnLock it
	 *
	 * @param trxName transaction
	 * @return true if unlocked (false only if unlock fails)
	 */
	public final boolean unlock(String trxName)
	{
		// log.warn(trxName);
		int index = get_ProcessingIndex();
		if (index != -1)
		{
			set_ValueNoCheck(index, false); // metas: 01537: replaced: m_newValues[index] = Boolean.FALSE; // direct
			String sql = "UPDATE " + p_info.getTableName()
					+ " SET Processing='N' WHERE " + get_WhereClause(true);
			boolean success = false;
			if (isUseTimeoutForUpdate())
				success = DB.executeUpdateEx(sql, trxName, QUERY_TIME_OUT) == 1;
			else
				success = DB.executeUpdate(sql, trxName) == 1;
			if (success)
				log.debug("Locked {} (trxName={})", this, trxName);
			else
				log.warn("Failed locking {} (trxName={})", this, trxName);
			return success;
		}
		return true;
	}	// unlock

	/** Optional Transaction */
	private String m_trxName = null;

	/**
	 * Set Trx
	 *
	 * @param trxName transaction
	 */
	public final void set_TrxName(String trxName)
	{
		m_trxName = trxName;
	}	// setTrx

	/**
	 * Get Trx
	 *
	 * @return transaction
	 */
	public final String get_TrxName()
	{
		return m_trxName;
	}	// getTrx

	/**************************************************************************
	 * Get Attachments.
	 * An attachment may have multiple entries
	 *
	 * @return Attachment or null
	 */
	public final MAttachment getAttachment()
	{
		return getAttachment(false);
	}	// getAttachment

	/**
	 * Get Attachments
	 *
	 * @param requery requery
	 * @return Attachment or null
	 */
	public final MAttachment getAttachment(boolean requery)
	{
		// Make sure the attachment is for current PO ID
		if (m_attachment != null && m_attachment.getRecord_ID() != get_ID())
		{
			m_attachment = null;
		}

		if (m_attachment == null || requery)
		{
			m_attachment = MAttachment.get(getCtx(), p_info.getAD_Table_ID(), get_ID());
		}
		return m_attachment;
	}	// getAttachment

	/**
	 * Create/return Attachment for PO.
	 * If not exist, create new
	 *
	 * @return attachment
	 */
	public final MAttachment createAttachment()
	{
		getAttachment(false);
		if (m_attachment == null)
			m_attachment = new MAttachment(getCtx(), p_info.getAD_Table_ID(), get_ID(), ITrx.TRXNAME_None);
		return m_attachment;
	}	// createAttachment

	/**
	 * Do we have a Attachment of type
	 *
	 * @param extension extension e.g. .pdf
	 * @return true if there is a attachment of type
	 */
	public final boolean isAttachment(String extension)
	{
		getAttachment(false);
		if (m_attachment == null)
			return false;
		for (int i = 0; i < m_attachment.getEntryCount(); i++)
		{
			if (m_attachment.getEntryName(i).endsWith(extension))
			{
				if (log.isDebugEnabled())
					log.debug("#" + i + ": " + m_attachment.getEntryName(i));
				return true;
			}
		}
		return false;
	}	// isAttachment

	/**
	 * Get Attachment Data of type
	 *
	 * @param extension extension e.g. .pdf
	 * @return data or null
	 */
	public final byte[] getAttachmentData(String extension)
	{
		getAttachment(false);
		if (m_attachment == null)
			return null;
		for (int i = 0; i < m_attachment.getEntryCount(); i++)
		{
			if (m_attachment.getEntryName(i).endsWith(extension))
			{
				if (log.isDebugEnabled())
					log.debug("#" + i + ": " + m_attachment.getEntryName(i));
				return m_attachment.getEntryData(i);
			}
		}
		return null;
	}	// getAttachmentData

	/**
	 * Do we have a PDF Attachment
	 *
	 * @return true if there is a PDF attachment
	 */
	public boolean isPdfAttachment()
	{
		return isAttachment(".pdf");
	}	// isPdfAttachment

	/**
	 * Get PDF Attachment Data
	 *
	 * @return data or null
	 */
	public byte[] getPdfAttachment()
	{
		return getAttachmentData(".pdf");
	}	// getPDFAttachment

	/**************************************************************************
	 * Dump Record
	 */
	public void dump()
	{
		if (LogManager.isLevelFinest())
		{
			log.trace(get_WhereClause(true));
			for (int i = 0; i < get_ColumnCount(); i++)
				dump(i);
		}
	}   // dump

	/**
	 * Dump column
	 *
	 * @param index index
	 */
	public void dump(int index)
	{
		StringBuilder sb = new StringBuilder(" ").append(index);
		if (index < 0 || index >= get_ColumnCount())
		{
			log.trace(sb.append(": invalid").toString());
			return;
		}
		sb.append(": ").append(get_ColumnName(index))
				.append(" = ").append(m_oldValues[index])
				.append(" (").append(m_newValues[index]).append(")");
		log.trace(sb.toString());
	}   // dump

	/*************************************************************************
	 * Get All IDs of Table.
	 * Used for listing all Entities
	 * <code>
	 	int[] IDs = PO.getAllIDs ("AD_PrintFont", null);
		for (int i = 0; i < IDs.length; i++)
		{
			pf = new MPrintFont(Env.getCtx(), IDs[i]);
			System.out.println(IDs[i] + " = " + pf.getFont());
		}
	 *	</code>
	 *
	 * @param TableName table name (key column with _ID)
	 * @param WhereClause optional where clause
	 * @return array of IDs or null
	 * @param trxName transaction
	 */
	public static int[] getAllIDs(String TableName, String WhereClause, String trxName)
	{
		ArrayList<Integer> list = new ArrayList<Integer>();
		StringBuilder sql = new StringBuilder("SELECT ");
		sql.append(TableName).append("_ID FROM ").append(TableName);
		if (WhereClause != null && WhereClause.length() > 0)
			sql.append(" WHERE ").append(WhereClause);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), trxName);
			rs = pstmt.executeQuery();
			while (rs.next())
				list.add(new Integer(rs.getInt(1)));
		}
		catch (SQLException e)
		{
			s_log.error(sql.toString(), e);
			return null;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		// Convert to array
		int[] retValue = new int[list.size()];
		for (int i = 0; i < retValue.length; i++)
			retValue[i] = list.get(i).intValue();
		return retValue;
	}	// getAllIDs

	/**
	 * Get Find parameter.
	 * Convert to upper case and add % at the end
	 *
	 * @param query in string
	 * @return out string
	 */
	protected static String getFindParameter(String query)
	{
		if (query == null)
			return null;
		if (query.length() == 0 || query.equals("%"))
			return null;
		if (!query.endsWith("%"))
			query += "%";
		return query.toUpperCase();
	}	// getFindParameter

	/**************************************************************************
	 * Load LOB
	 *
	 * @param value LOB
	 * @return object
	 */
	private Object get_LOB(Object value)
	{
		log.debug("Getting LOB for Value={}", value);
		if (value == null)
			return null;
		//
		Object retValue = null;

		long length = -99;
		try
		{
			// [ 1643996 ] Chat not working in postgres port
			if (value instanceof String ||
					value instanceof byte[])
				retValue = value;
			else if (value instanceof Clob) 		// returns String
			{
				Clob clob = (Clob)value;
				length = clob.length();
				retValue = clob.getSubString(1, (int)length);
			}
			else if (value instanceof Blob) 	// returns byte[]
			{
				Blob blob = (Blob)value;
				length = blob.length();
				int index = 1;	// correct
				if (blob.getClass().getName().equals("oracle.jdbc.rowset.OracleSerialBlob"))
					index = 0;	// Oracle Bug Invalid Arguments
	// at oracle.jdbc.rowset.OracleSerialBlob.getBytes(OracleSerialBlob.java:130)
				retValue = blob.getBytes(index, (int)length);
			}
			else
				log.error("Unknown: " + value);
		}
		catch (Exception e)
		{
			log.error("Length=" + length, e);
		}
		return retValue;
	}	// getLOB

	/** LOB Info */
	private ArrayList<PO_LOB> m_lobInfo = null;

	/**
	 * Reset LOB info
	 */
	private void lobReset()
	{
		m_lobInfo = null;
	}	// resetLOB

	/**
	 * Prepare LOB save
	 *
	 * @param value value
	 * @param index index
	 * @param displayType display type
	 */
	private void lobAdd(Object value, int index, int displayType)
	{
		log.trace("Adding LOB: Value={}", value);
		PO_LOB lob = new PO_LOB(p_info.getTableName(), get_ColumnName(index),
				get_WhereClause(true), displayType, value);
		if (m_lobInfo == null)
			m_lobInfo = new ArrayList<PO_LOB>();
		m_lobInfo.add(lob);
	}	// lobAdd

	/**
	 * Save LOB
	 *
	 * @return true if saved or ok
	 */
	private boolean lobSave()
	{
		if (m_lobInfo == null || m_lobInfo.isEmpty())
		{
			return true;
		}

		final String whereClause = get_WhereClause(true); // withValues=true
		final String trxName = get_TrxName();
		boolean retValue = true;
		for (final PO_LOB lob : m_lobInfo)
		{
			if (!lob.save(whereClause, trxName))
			{
				retValue = false;
				break;
			}
		} 	// for all LOBs
		lobReset();
		return retValue;
	}	// saveLOB

	/**
	 * Get Object xml representation as string
	 *
	 * @param xml optional string buffer
	 * @return updated/new string buffer header is only added once
	 */
	public final StringBuilder get_xmlString(StringBuilder xml)
	{
		if (xml == null)
			xml = new StringBuilder();
		else
			xml.append(Env.NL);
		//
		try
		{
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			DOMSource source = new DOMSource(get_xmlDocument(xml.length() != 0));
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
			StringBuffer newXML = writer.getBuffer();
			//
			if (xml.length() != 0)
			{	// // <?xml version="1.0" encoding="UTF-8"?>
				int tagIndex = newXML.indexOf("?>");
				if (tagIndex != -1)
					xml.append(newXML.substring(tagIndex + 2));
				else
					xml.append(newXML);
			}
			else
				xml.append(newXML);
		}
		catch (Exception e)
		{
			log.error("", e);
		}
		return xml;
	}	// get_xmlString

	/** Table ID Attribute */
	protected final static String XML_ATTRIBUTE_AD_Table_ID = "AD_Table_ID";
	/** Record ID Attribute */
	protected final static String XML_ATTRIBUTE_Record_ID = "Record_ID";

	/**
	 * Get XML Document representation
	 *
	 * @param noComment do not add comment
	 * @return XML document
	 */
	public final Document get_xmlDocument(boolean noComment)
	{
		Document document = null;
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
			if (!noComment)
				document.appendChild(document.createComment(Adempiere.getSummaryAscii()));
		}
		catch (Exception e)
		{
			log.error("", e);
		}
		// Root
		Element root = document.createElement(get_TableName());
		root.setAttribute(XML_ATTRIBUTE_AD_Table_ID, String.valueOf(get_Table_ID()));
		root.setAttribute(XML_ATTRIBUTE_Record_ID, String.valueOf(get_ID()));
		document.appendChild(root);
		// Columns
		int size = get_ColumnCount();
		for (int i = 0; i < size; i++)
		{
			if (p_info.isVirtualColumn(i))
				continue;

			Element col = document.createElement(p_info.getColumnName(i));
			//
			Object value = get_Value(i);
			// Display Type
			int dt = p_info.getColumnDisplayType(i);
			// Based on class of definition, not class of value
			Class<?> c = p_info.getColumnClass(i);
			if (value == null || value.equals(Null.NULL))
				;
			else if (c == Object.class)
				col.appendChild(document.createCDATASection(value.toString()));
			else if (value instanceof Integer || value instanceof BigDecimal)
				col.appendChild(document.createTextNode(value.toString()));
			else if (c == Boolean.class)
			{
				boolean bValue = false;
				if (value instanceof Boolean)
					bValue = ((Boolean)value).booleanValue();
				else
					bValue = "Y".equals(value);
				col.appendChild(document.createTextNode(bValue ? "Y" : "N"));
			}
			else if (value instanceof Timestamp)
				col.appendChild(document.createTextNode(value.toString()));
			else if (c == String.class)
				col.appendChild(document.createCDATASection((String)value));
			else if (DisplayType.isLOB(dt))
				col.appendChild(document.createCDATASection(value.toString()));
			else
				col.appendChild(document.createCDATASection(value.toString()));
			//
			root.appendChild(col);
		}
		// Custom Columns
		if (m_custom != null)
		{
			Iterator<String> it = m_custom.keySet().iterator();
			while (it.hasNext())
			{
				String columnName = it.next();
				// int index = p_info.getColumnIndex(columnName);
				String value = m_custom.get(columnName);
				//
				Element col = document.createElement(columnName);
				if (value != null)
					col.appendChild(document.createTextNode(value));
				root.appendChild(col);
			}
			m_custom = null;
		}
		return document;
	}	// getDocument

	public final void setReplication(boolean isFromReplication)
	{
		m_isReplication = isFromReplication;
	}

	public final boolean isReplication()
	{
		return m_isReplication;
	}

	/**
	 * PO.setTrxName - set given trxName to an array of POs
	 * As suggested by teo in [ 1854603 ]
	 */
	public static void set_TrxName(PO[] lines, String trxName)
	{
		for (PO line : lines)
			line.set_TrxName(trxName);
	}

	/**
	 * Get Integer Value
	 *
	 * @param columnName
	 * @return int value
	 */
	public final int get_ValueAsInt(String columnName)
	{
		int idx = get_ColumnIndex(columnName);
		if (idx < 0)
		{
			return 0;
		}
		return get_ValueAsInt(idx);
	}

	/**
	 * Get value as Boolean
	 *
	 * @param columnName
	 * @return boolean value
	 */
	// metas: tsa: changed and introduced get_ValueAsBoolean(int)
	public final boolean get_ValueAsBoolean(String columnName)
	{
		int idx = get_ColumnIndex(columnName);
		if (idx < 0)
		{
			return false;
		}
		return get_ValueAsBoolean(idx);
	}

	public final boolean get_ValueAsBoolean(int index)
	{
		Object oo = get_Value(index);
		if (oo != null)
		{
			if (oo instanceof Boolean)
				return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;

	}

	// metas: begin
	private Map<String, Object> m_dynAttrs = null;

	/**
	 * Set Dynamic Attribute.
	 * A dynamic attribute is an attribute that is not stored in database and is kept as long as this
	 * PO instance is not destroyed.
	 *
	 * @param name
	 * @param value
	 */
	public final Object setDynAttribute(String name, Object value)
	{
		if (m_dynAttrs == null)
			m_dynAttrs = new HashMap<String, Object>();
		return m_dynAttrs.put(name, value);
	}

	/**
	 * Get Dynamic Attribute
	 *
	 * @param name
	 * @return attribute value or null if not found
	 */
	public final Object getDynAttribute(String name)
	{
		if (m_dynAttrs == null)
			return null;
		return m_dynAttrs.get(name);
	}

	/**
	 * Fire Model Change Event.
	 *
	 * After event is fired, if the event was about replication, the replication flag will also be set to <code>false</code>.
	 *
	 * @param type see ModelValidator.TYPE_* events
	 * @return error or null
	 * @task 01512
	 */
	private final void fireModelChange(final int type)
	{
		if (type == -1)
		{
			throw new IllegalArgumentException("Invalid type " + type + " (" + this + ")");
		}
		if (m_currentChangeType != -1)
		{
			throw new AdempiereException("Object is already involved in a model change event"
					+ "(" + this
					+ ", currentChangeType=" + m_currentChangeType
					+ ", ChangeType=" + type);
		}
		try
		{
			m_currentChangeType = type;
			ModelValidationEngine.get().fireModelChange(this, type);
		}
		finally
		{
			// Make sure replication flag is reset
			if (type == ModelValidator.TYPE_AFTER_NEW_REPLICATION
					|| type == ModelValidator.TYPE_AFTER_CHANGE_REPLICATION
					|| type == ModelValidator.TYPE_BEFORE_DELETE_REPLICATION)
			{
				setReplication(false);
			}
			m_currentChangeType = -1;
		}
	}

	private int m_currentChangeType = -1;

	/**
	 * DynAttr which holds the <code>CopyRecordSupport</code> class which handles this PO
	 */
	public static final String DYNATTR_CopyRecordSupport = "CopyRecordSupport";
	/**
	 * DynAttr which holds the source PO from which this PO was copied
	 */
	public static final String DYNATTR_CopyRecordSupport_OldValue = "CopyRecordSupportOldValue";

	@Override
	public final boolean has_Variable(String variableName)
	{
		return get_ColumnIndex(variableName) >= 0;
	}

	@Override
	public final String get_ValueOldAsString(String columnName)
	{
		int index = get_ColumnIndex(columnName);
		if (index < 0)
		{
			log.warn("Column not found: {}", columnName);
			return "";
		}
		return get_ValueOldAsString(index);
	}

	public final String get_ValueOldAsString(int index)
	{
		Object value = get_ValueOld(index);
		if (value == null)
			return "";
		return value.toString();
	}

	/**
	 * Get/Load translation map for a given language
	 *
	 * @param AD_Language
	 * @return map of columnName->translatedValue pairs
	 */
	private IModelTranslation get_ModelTranslation(final String AD_Language)
	{
		final IModelTranslationMap modelTranslationMap = get_ModelTranslationMap();
		if (modelTranslationMap instanceof POInfoModelTranslationMap)
		{
			return ((POInfoModelTranslationMap)modelTranslationMap).getTranslation(get_ID(), AD_Language);
		}
		else
		{
			return modelTranslationMap.getTranslation(AD_Language);
		}
	}

	public IModelTranslationMap get_ModelTranslationMap()
	{
		final int id = get_ID();
		if (id <= 0)
			return NullModelTranslationMap.instance;

		if (m_translations == null)
		{
			m_translations = POInfoModelTranslationMap.of(p_info, id);
		}
		return m_translations;
	}

	private IModelTranslationMap m_translations = null;

	public final POInfo getPOInfo()
	{
		return p_info;
	}

	private transient Map<String, POCacheLocal> m_poCacheLocals;

	private POCacheLocal get_POCacheLocal(String columnName, String refTableName)
	{
		if (m_poCacheLocals == null)
		{
			m_poCacheLocals = new HashMap<>();
		}

		POCacheLocal poCache = m_poCacheLocals.get(columnName);
		if (poCache != null && !refTableName.equals(poCache.getTableName()))
		{
			log.warn("POCache does not have tableName=" + refTableName + " -- " + poCache, new Exception());
			poCache = null;
		}
		if (poCache == null)
		{
			poCache = POCacheLocal.newInstance(this, columnName, refTableName);
			m_poCacheLocals.put(columnName, poCache);
		}

		return poCache;
	}

	public final <T> T get_ValueAsPO(final String columnName, final Class<T> refClass)
	{
		final String refTableName = InterfaceWrapperHelper.getTableName(refClass);
		final POCacheLocal poCache = get_POCacheLocal(columnName, refTableName);
		return poCache == null ? null : poCache.get(refClass);
	}

	public final Object get_ValueAsPO(final String columnName, final String refTableName)
	{
		final POCacheLocal poCache = get_POCacheLocal(columnName, refTableName);
		return poCache == null ? null : poCache.get();
	}

	public final <T> void set_ValueFromPO(final String columnName, final Class<T> refClass, final Object obj)
	{
		final String refTableName = InterfaceWrapperHelper.getTableName(refClass);
		final POCacheLocal poCache = get_POCacheLocal(columnName, refTableName);
		poCache.set(obj);
	}

	protected final <RecordType> RecordType getReferencedRecord(final Class<RecordType> recordType)
	{
		return TableRecordCacheLocal.getReferencedValue(this, recordType);
	}

	protected final <RecordType> void setReferencedRecord(final RecordType record)
	{
		TableRecordCacheLocal.setReferencedValue(this, record);
	}

	private boolean m_isManualUserAction = false;
	private int m_windowNo = 0;

	/**
	 *
	 * @return true if this PO is created, updated or deleted by an manual user action (from window)
	 */
	public final boolean is_ManualUserAction()
	{
		return m_isManualUserAction;
	}

	/**
	 *
	 * @return WindowNo in case is an user manual action, or {@link Env#WINDOW_None} otherwise
	 */
	public final int get_WindowNo()
	{
		return m_isManualUserAction ? m_windowNo : Env.WINDOW_None;
	}

	/**
	 * Mark this PO as beeing created, updated or deleted by an manual user action (from window).
	 */
	public final void set_ManualUserAction(final int windowNo)
	{
		// NOTE: we don't need to enforce setting this flag only out-of-trx because it's not valid
		// // Make sure the PO is not in transaction
		// final ITrxManager trxManager = get_TrxManager();
		// if(!trxManager.isNull(get_TrxName()))
		// {
		// throw new AdempiereException("Marking a PO to be manual user action when that PO is in transaction is not allowed - " + this);
		// }

		this.m_isManualUserAction = true;
		this.m_windowNo = windowNo;
	}

	@Override
	public org.compiere.model.I_AD_Client getAD_Client()
	{
		return get_ValueAsPO("AD_Client_ID", org.compiere.model.I_AD_Client.class);
	}

	@Override
	public org.compiere.model.I_AD_Org getAD_Org()
	{
		return get_ValueAsPO("AD_Org_ID", org.compiere.model.I_AD_Org.class);
	}

	public final void setAD_Org(org.compiere.model.I_AD_Org org)
	{
		set_ValueFromPO("AD_Org_ID", org.compiere.model.I_AD_Org.class, org);
	}

	/**
	 * Gets context session. Please note, this method will never return a session if current table is AD_Session.
	 *
	 * @return session or null
	 */
	// metas
	private final I_AD_Session get_Session()
	{
		if (I_AD_Session.Table_Name.equals(get_TableName()))
		{
			// to avoid recursions and data coruption problems due to stalled caches, never return session when doing CRUD operations with a session
			return null;
		}

		final MSession session = MSession.get(getCtx(), false);
		if (session == null)
		{
			log.debug("No Session found");
		}
		return session;
	}

	public final Timestamp get_ValueAsTimestamp(final String columnName)
	{
		int idx = get_ColumnIndex(columnName);
		if (idx < 0)
		{
			return null;
		}
		return get_ValueAsTimestamp(idx);
	}

	public final Timestamp get_ValueAsTimestamp(int index)
	{
		final Timestamp ts = (Timestamp)get_Value(index);
		return ts;
	}

	/**
	 * Explicitly mark a column that was changed.
	 *
	 * It is helpful to do this when:
	 * <ul>
	 * <li>you set a value for a column but the new value can be the same as the old value
	 * <li>and you really really what to trigger the database UPDATE or you really really want to trigger the model validators
	 * </ul>
	 *
	 * NOTE: if you are marking the column as changed but you are not explicitly set a value (i.e. a new value), this command will have no effect
	 *
	 * @param columnName column name to be marked as changed
	 */
	public final void markColumnChanged(final String columnName)
	{
		Check.assumeNotNull(columnName, "columnName not null");
		final int columnIndex = get_ColumnIndex(columnName);
		if (columnIndex < 0)
		{
			log.warn("No columnIndex found for column name '" + columnName + "'");
			return;
		}
		if (markedChangedColumns == null)
		{
			markedChangedColumns = new HashSet<Integer>();
		}
		markedChangedColumns.add(columnIndex);
	}

	/**
	 * A set of column indexes that were explicitly marked as changed
	 *
	 * @see #markColumnChanged(String)
	 */
	private Set<Integer> markedChangedColumns = null;

	/**
	 * Gets current transaction manager
	 *
	 * @return transaction manager; never return null
	 */
	private ITrxManager get_TrxManager()
	{
		return Services.get(ITrxManager.class);
	}

	/**
	 * Marker ID passed to constructor to let them know we will create a plain instance (used for cloning).
	 *
	 * @see #newInstance()
	 */
	protected final static int ID_NewInstanceNoInit = -10000;

	/**
	 * Creates a new empty instance of this object
	 *
	 * @return new instance (same class); never return null
	 */
	protected PO newInstance()
	{
		final PO po;
		try
		{
			final Constructor<? extends PO> constructor = getClass().getDeclaredConstructor(Properties.class, int.class, String.class);
			if (!constructor.isAccessible())
			{
				constructor.setAccessible(true);
			}
			po = constructor.newInstance(getCtx(), ID_NewInstanceNoInit, m_trxName);
		}
		catch (Exception e)
		{
			throw new AdempiereException("Cannot create a new instance of " + this, e);
		}

		return po;
	}

	/**
	 * Clones this object.
	 *
	 * @return cloned object; never return null
	 */
	public PO copy()
	{
		final PO poCopy = newInstance();

		// log : CLogger
		poCopy.p_info = this.p_info;
		poCopy.p_ctx = this.p_ctx;
		poCopy.m_trxName = this.m_trxName;
		poCopy.isAssignedID = this.isAssignedID;
		poCopy.m_attachment = null;
		poCopy.m_createNew = this.m_createNew;
		poCopy.m_wasJustCreated = this.m_wasJustCreated;
		poCopy.m_idOld = this.m_idOld;
		poCopy.m_IDs = this.m_IDs == null ? null : Arrays.copyOf(this.m_IDs, this.m_IDs.length);
		poCopy.m_oldValues = this.m_oldValues == null ? null : Arrays.copyOf(this.m_oldValues, this.m_oldValues.length);
		poCopy.m_newValues = this.m_newValues == null ? null : Arrays.copyOf(this.m_newValues, this.m_newValues.length);
		poCopy.m_KeyColumns = this.m_KeyColumns == null ? null : Arrays.copyOf(this.m_KeyColumns, this.m_KeyColumns.length);
		// m_currentChangeType : int
		poCopy.m_custom = this.m_custom == null ? null : new HashMap<String, String>(this.m_custom);
		// poCopy.m_doc = this.m_doc // NOTE: don't need to copy; it will be set by org.compiere.acct.Doc
		poCopy.m_dynAttrs = null; // don't copy DynAttributes
		poCopy.m_isReplication = false; // don't copy IsReplication
		// m_loading : boolean // TODO: this shall be false
		// m_loadingLock : ReentrantLock
		poCopy.m_lobInfo = null; // don't copy the LOB Info for now
		poCopy.m_poCacheLocals = null; // don't copy the cache locals for now
		poCopy.m_stale = this.m_stale;
		// m_translations : Map<String, Map<String, String>> // TODO: copy the translations
		poCopy.m_valueLoaded = this.m_valueLoaded == null ? null : Arrays.copyOf(this.m_valueLoaded, this.m_valueLoaded.length);
		poCopy.markedChangedColumns = this.markedChangedColumns == null ? null : new HashSet<Integer>(this.markedChangedColumns);
		poCopy.s_acctColumns = this.s_acctColumns == null ? null : new ArrayList<String>(this.s_acctColumns);

		//
		// Manual User Action (i.e. if user loaded this PO from a window)
		// NOTE: don't copy that information because in caching that info is not relevant
		// poCopy.m_isManualUserAction = this.m_isManualUserAction;
		// poCopy.m_windowNo = this.m_windowNo;

		return poCopy;
	}

	/**
	 *
	 * @return how many times this object was loaded/reloaded
	 */
	public final int getLoadCount()
	{
		return m_loadCount;
	}

	// metas: end

	private class POReturningAfterInsertLoader implements ISqlUpdateReturnProcessor
	{
		private final List<String> columnNames;
		private final StringBuilder sqlReturning;

		public POReturningAfterInsertLoader()
		{
			super();
			this.columnNames = new ArrayList<>();
			this.sqlReturning = new StringBuilder();
		}

		public void addColumnName(final String columnName)
		{
			// Make sure column was not already added
			if (columnNames.contains(columnName))
			{
				return;
			}

			columnNames.add(columnName);

			if (sqlReturning.length() > 0)
			{
				sqlReturning.append(", ");
			}
			sqlReturning.append(columnName);
		}

		public String getSqlReturning()
		{
			return sqlReturning.toString();
		}

		public boolean hasColumnNames()
		{
			return !columnNames.isEmpty();
		}

		@Override
		public void process(final ResultSet rs) throws SQLException
		{
			for (final String columnName : columnNames)
			{
				final Object value = rs.getObject(columnName);
				// NOTE: it is also setting the ID if applies
				set_ValueNoCheck(columnName, value);
			}
		}

		@Override
		public String toString()
		{
			return "POReturningAfterInsertLoader [columnNames=" + columnNames + "]";
		}
	}
}   // PO
