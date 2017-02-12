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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractListModel;
import javax.swing.MutableComboBoxModel;

import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.util.Check;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;
import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;

/**
 *	Base Class for MLookup, MLocator.
 *  as well as for MLocation, MAccount (only single value)
 *  Maintains selectable data as NamePairs in ArrayList
 *  The objects itself may be shared by the lookup implementation (ususally HashMap)
 *
 *  @author 	Jorg Janke
 *  @version 	$Id: Lookup.java,v 1.3 2006/07/30 00:58:18 jjanke Exp $
 */
public abstract class Lookup extends AbstractListModel
	implements MutableComboBoxModel, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2811763289904455349L;

	/**
	 *  Lookup
	 * 	@param displayType display type
	 * 	@param windowNo window no
	 */
	public Lookup (int displayType, int windowNo)
	{
		m_displayType = displayType;
		m_WindowNo = windowNo;
	}   //  Lookup

	/** The Data List           */
	private volatile List<Object>   p_data = new ArrayList<Object>();

	/** The Selected Item       */
	private volatile Object         m_selectedObject;

	/** Temporary Data          */
	private Object[]                m_tempData = null;

	/**	Logger					*/
	protected final transient Logger log = LogManager.getLogger(getClass());

	/**	Display Type			*/
	private final int m_displayType;
	/**	Window No				*/
	private final int m_WindowNo;
	
	private boolean 				m_mandatory;
	
	private boolean					m_loaded;

	/**
	 * 	Get Display Type
	 *	@return display type
	 */
	public int getDisplayType()
	{
		return m_displayType;
	}	//	getDisplayType

	/**
	 * 	Get Window No
	 *	@return Window No
	 */
	public int getWindowNo()
	{
		return m_WindowNo;
	}	//	getWindowNo
	
	/**************************************************************************
	 * Set the value of the selected item. The selected item may be null.
	 * <p>
	 * @param anObject The combo box value or null for no selection.
	 */
	@Override
	public void setSelectedItem(Object anObject)
	{
		// Do nothing if disposed
		if (p_data == null)
		{
			return;
		}
		
		if ((m_selectedObject != null && !m_selectedObject.equals( anObject ))
			|| m_selectedObject == null && anObject != null)
		{
			if (p_data.contains(anObject) || anObject == null)
			{
				m_selectedObject = anObject;
			//	Log.trace(s_ll, "Lookup.setSelectedItem", anObject);
			}
			else
			{
				m_selectedObject = null;
				log.debug(getColumnName() + ": setSelectedItem - Set to NULL");
			}
			fireContentsChanged(this, -1, -1);
		}
	}   //  setSelectedItem

	/**
	 *  Return previously selected Item
	 *  @return value
	 */
	@Override
	public Object getSelectedItem()
	{
		return m_selectedObject;
	}   //  getSelectedItem

	/**
	 *  Get Size of Model
	 *  @return size
	 */
	@Override
	public int getSize()
	{
		return p_data.size();
	}   //  getSize

	/**
	 *  Get Element at Index
	 *  @param index index
	 *  @return value
	 */
	@Override
	public Object getElementAt (int index)
	{
		return p_data.get(index);
	}   //  getElementAt

	/**
	 * Returns the index-position of the specified object in the list.
	 * 
	 * @param anObject object
	 * @return an int representing the index position, where 0 is the first position, or -1 if this list does not contain the element
	 */
	public int getIndexOf(Object anObject)
	{
		return p_data.indexOf(anObject);
	}   // getIndexOf

	/**
	 *  Add Element at the end
	 *  @param anObject object
	 */
	@Override
	public void addElement (Object anObject)
	{
		p_data.add(anObject);
		fireIntervalAdded (this, p_data.size()-1, p_data.size()-1);
		if (p_data.size() == 1 && m_selectedObject == null && anObject != null)
			setSelectedItem (anObject);
	}   //  addElement

	/**
	 *  Insert Element At
	 *  @param anObject object
	 *  @param index index
	 */
	@Override
	public void insertElementAt (Object anObject, int index)
	{
		p_data.add (index, anObject);
		fireIntervalAdded (this, index, index);
	}   //  insertElementAt

	/**
	 *  Remove Item at index
	 *  @param index index
	 */
	@Override
	public void removeElementAt (int index)
	{
		if (getElementAt(index) == m_selectedObject)
		{
			if (index == 0)
				setSelectedItem (getSize() == 1 ? null : getElementAt( index + 1 ));
			else
				setSelectedItem (getElementAt (index - 1));
		}
		p_data.remove(index);
		fireIntervalRemoved (this, index, index);
	}   //  removeElementAt

	/**
	 *  Remove Item
	 *  @param anObject object
	 */
	@Override
	public void removeElement (Object anObject)
	{
		int index = p_data.indexOf (anObject);
		if (index != -1)
			removeElementAt(index);
	}   //  removeItem

	/**
	 *  Empties the list.
	 */
	public void removeAllElements()
	{
		if (p_data.size() > 0)
		{
			int firstIndex = 0;
			int lastIndex = p_data.size() - 1;
			p_data.clear();
			m_selectedObject = null;
			fireIntervalRemoved (this, firstIndex, lastIndex);
		}
		m_loaded = false;
	}   //  removeAllElements

	
	/**************************************************************************
	 *	Put Value
	 *  @param key key
	 *  @param value value
	 */
	public void put (String key, String value)
	{
		NamePair pp = new ValueNamePair (key, value);
		addElement(pp);
	}	//	put

	/**
	 *	Put Value
	 *  @param key key
	 *  @param value value
	 */
	public void put (int key, String value)
	{
		NamePair pp = new KeyNamePair (key, value);
		addElement(pp);
	}	//	put

	/**
	 *  Fill ComboBox with lookup data (async using Worker).
	 *  - try to maintain selected item
	 *  @param mandatory  has mandatory data only (i.e. no "null" selection)
	 *  @param onlyValidated only validated
	 *  @param onlyActive onlt active
	 *  @param temporary  save current values - restore via fillComboBox (true)
	 */
	public void fillComboBox (boolean mandatory, boolean onlyValidated, boolean onlyActive, boolean temporary)
	{
		long startTime = System.currentTimeMillis();
		m_loaded = false;
		//  Save current data
		if (temporary)
		{
			int size = p_data.size();
			m_tempData = new Object[size];
			//  We need to do a deep copy, so store it in Array
			p_data.toArray(m_tempData);
		//	for (int i = 0; i < size; i++)
		//		m_tempData[i] = p_data.get(i);
		}


		final Object selectedObjectOld = m_selectedObject;
		p_data.clear();

		//  may cause delay *** The Actual Work ***
		p_data = getData (mandatory, onlyValidated, onlyActive, temporary);
		
		//  Selected Object changed
		if (selectedObjectOld != m_selectedObject)
		{
			log.trace(getColumnName() + ": SelectedValue Changed=" + selectedObjectOld + "->" + m_selectedObject);
		}

		// comment next code because of bug [ 2053140 ] Mandatory lookup fields autofilled (badly)
		//  if nothing selected & mandatory, select first
		// if (obj == null && mandatory  && p_data.size() > 0)
		// {
		// 	obj = p_data.get(0);
		// 	m_selectedObject = obj;
		// 	log.trace(getColumnName() + ": SelectedValue SetToFirst=" + obj);
		// //	fireContentsChanged(this, -1, -1);
		// }
		
		m_loaded = true; 
		if (p_data.isEmpty())
		{
			fireContentsChanged(this, -1, -1);
			log.debug(getColumnName() + ": #0 - ms=" + String.valueOf(System.currentTimeMillis()-startTime));
		}
		else
		{
			fireContentsChanged(this, 0, p_data.size());
			log.debug(getColumnName() + ": #" + p_data.size() + " - ms=" + String.valueOf(System.currentTimeMillis()-startTime));
		}
	}   //  fillComboBox

	/**
	 *  Fill ComboBox with old saved data (if exists) or all data available
	 *  @param restore if true, use saved data - else fill it with all data
	 */
	public void fillComboBox (boolean restore)
	{
		if (restore && m_tempData != null)
		{
			Object obj = m_selectedObject;
			p_data.clear();
			//  restore old data
			p_data = new ArrayList<Object>(m_tempData.length);
			for (int i = 0; i < m_tempData.length; i++)
				p_data.add(m_tempData[i]);
			m_tempData = null;

			//  if nothing selected, select first
			if (obj == null && p_data.size() > 0)
				obj = p_data.get(0);
			setSelectedItem(obj);
			
			fireContentsChanged(this, 0, p_data.size());
			return;
		}
		if (p_data != null)
			fillComboBox(isMandatory(), true, true, false);
	}   //  fillComboBox

	
	/**
	 * Get Display of Key Value
	 * 
	 * NOTE: this method is checking if given key is valid in given context
	 * 
	 * @param evalCtx
	 * @param key key
	 * @return String
	 */
	public abstract String getDisplay(IValidationContext evalCtx, Object key);

	/**
	 * Get Display of Key Value.
	 * 
	 * NOTE: this method is not validating the record
	 * 
	 * @param key
	 * @return
	 */
	public final String getDisplay(Object key)
	{
		return getDisplay(IValidationContext.DISABLED, key);
	}

	/**
	 * Get Object of Key Value
	 * 
	 * @param evalCtx evaluation context to be used 
	 * @param key key
	 * @return Object or null
	 */
	public abstract NamePair get(final IValidationContext validationCtx, Object key);

	/**
	 * Get Object of Key Value.
	 * 
	 * NOTE: no validation will be performed
	 * 
	 * @param key key
	 * @return Object or null
	 */
	public final NamePair get(Object key)
	{
		final IValidationContext evalCtx = IValidationContext.DISABLED;
		return get(evalCtx, key);
	}

	/**
	 *  Fill ComboBox with Data (Value/KeyNamePair)
	 *  @param mandatory  has mandatory data only (i.e. no "null" selection)
	 *  @param onlyValidated only validated
	 *  @param onlyActive only active
	 * 	@param temporary force load for temporary display
	 *  @return ArrayList
	 */
	public abstract List<Object> getData (boolean mandatory, boolean onlyValidated, boolean onlyActive, boolean temporary);

	/**
	 * Gets Lookup TableName or null if the lookup is not based on a particular table
	 * 
	 * @return TableName or null
	 */
	public abstract String getTableName();
	
	/** @return true if this lookup shall enable autocomplete feature in UI */
	public boolean isAutoComplete()
	{
		return false;
	}
	
	/**
	 *	Get underlying fully qualified Table.Column Name.
	 *	Used for VLookup.actionButton (Zoom)
	 *  @return column name
	 */
	public abstract String getColumnName();
	
	/**
	 * Get underlying NOT fully qualified Column Name.
	 * 
	 * @return not fully qualified column name
	 */
	public abstract String getColumnNameNotFQ();

	/**
	 *  The Lookup contains the key
	 *  @param evalCtx
	 *  @param key key
	 *  @return true if contains key
	 */
	public abstract boolean containsKey (final IValidationContext evalCtx, final Object key);
	
	public final boolean containsKey (Object key)
	{
		return containsKey(IValidationContext.DISABLED, key);
	}

	
	/**************************************************************************
	 *	Refresh Values - default implementation
	 *  @return size
	 */
	public int refresh()
	{
		return 0;
	}	//	refresh

	/**
	 *	Is Validated - default implementation
	 *  @return true if validated
	 */
	public boolean isValidated()
	{
		return true;
	}	//	isValidated

	/**
	 *  Get dynamic Validation SQL (none)
	 *  @return validation
	 */
	@Deprecated
	public String getValidation()
	{
		return "";
	}   //  getValidation
	
	public boolean hasValidation()
	{
		return !Check.isEmpty(getValidation(), true);
	}

	/**
	 *  Has Inactive records - default implementation
	 *  @return true if inactive
	 */
	public boolean hasInactive()
	{
		return false;
	}

	/**
	 *	Get Zoom - default implementation
	 *  @return Zoom AD_Window_ID
	 */
	public int getZoom()
	{
		return 0;
	}	//	getZoom

	/**
	 *	Get Zoom - default implementation
	 * 	@param query query
	 *  @return Zoom Window - here 0
	 */
	public int getZoom(MQuery query)
	{
		return 0;
	}	//	getZoom

	/**
	 *	Get Zoom Query String - default implementation
	 *  @return Zoom Query
	 */
	public MQuery getZoomQuery()
	{
		return null;
	}	//	getZoomQuery

	/**
	 * Get Data Direct from Table. Default implementation - does not requery
	 *
	 * @param evalCtx evaluation context to be used
	 * @param key key
	 * @param saveInCache save in cache for r/w
	 * @param cacheLocal cache locally for r/o
	 * @return value
	 */
	public NamePair getDirect(final IValidationContext evalCtx, Object key, boolean saveInCache, boolean cacheLocal)
	{
		return get(evalCtx, key);
	}	// getDirect

	/**
	 *  Dispose - clear items w/o firing events
	 */
	public void dispose()
	{
		if (p_data != null)
			p_data.clear();
		p_data = null;
		m_selectedObject = null;
		m_tempData = null;
		m_loaded = false;
	}   //  dispose

	/**
	 *  Wait until async Load Complete
	 */
	public void loadComplete()
	{
	}   //  loadComplete
	
	/**
	 * Set lookup model as mandatory, use in loading data
	 * @param flag
	 */
	public void setMandatory(boolean flag)
	{
		m_mandatory = flag;
	}
	
	/**
	 * Is lookup model mandatory
	 * @return boolean
	 */
	public boolean isMandatory()
	{
		return m_mandatory;
	}
	
	/**
	 * Is this lookup model populated
	 * @return boolean
	 */
	public boolean isLoaded() 
	{
		return m_loaded;
	}
	
	/**
	 * Get custom info factory class
	 * @return info factory class name
	 */
	public String getInfoFactoryClass() 
	{
		return "";
	}

	/**
	 * Returns a list of parameters on which this lookup depends.
	 * 
	 * Those parameters will be fetched from context on validation time.
	 * 
	 * @return list of parameter names
	 */
	public Set<String> getParameters()
	{
		return ImmutableSet.of();
	}

	/**
	 * 
	 * @return evaluation context
	 */
	public IValidationContext getValidationContext()
	{
		return IValidationContext.NULL;
	}

	/**
	 * Suggests a valid value for given value
	 * 
	 * @param value
	 * @return equivalent valid value or same this value is valid; if there are no suggestions, null will be returned
	 */
	public NamePair suggestValidValue(final NamePair value)
	{
		return null;
	}

	/**
	 * Returns true if given <code>display</code> value was rendered for a not found item.
	 * To be used together with {@link #getDisplay} methods.
	 * 
	 * @param display
	 * @return true if <code>display</code> contains not found markers
	 */
	public boolean isNotFoundDisplayValue(String display)
	{
		return false;
	}
}	//	Lookup
