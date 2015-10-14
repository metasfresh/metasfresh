/******************************************************************************
 * Copyright (C) 2008 Low Heng Sin  All Rights Reserved.                      *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/

package org.adempiere.webui.editor;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeExcludeBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.product.service.IProductBL;
import org.adempiere.util.Services;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.PAttributebox;
import org.adempiere.webui.event.ContextMenuEvent;
import org.adempiere.webui.event.ContextMenuListener;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.window.WFieldRecordInfo;
import org.adempiere.webui.window.WPAttributeDialog;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeSetExclude;
import org.compiere.model.Lookup;
import org.compiere.model.MProduct;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;

/**
 *
 * @author Low Heng Sin
 *
 */
public class WPAttributeEditor extends WEditor implements ContextMenuListener
{
	private static final String[] LISTENER_EVENTS = {Events.ON_CLICK, Events.ON_CHANGE, Events.ON_OK};

	private static final CLogger log = CLogger.getCLogger(WPAttributeEditor.class);

	private int m_WindowNo;

	private Lookup m_mPAttribute;

	private int m_C_BPartner_ID;

	private WEditorPopupMenu	popupMenu;

	private Object m_value;

	private GridTab m_GridTab;

	/**	No Instance Key					*/
	private static Integer		NO_INSTANCE = new Integer(0);

	public WPAttributeEditor(GridTab gridTab, GridField gridField)
	{
		super(new PAttributebox(), gridField);
		m_GridTab = gridTab;
		initComponents();
	}

	private void initComponents() {
		getComponent().setButtonImage("images/PAttribute10.png");
		getComponent().addEventListener(Events.ON_CLICK, this);

		m_WindowNo = gridField.getWindowNo();
		m_mPAttribute = gridField.getLookup();
		m_C_BPartner_ID = Env.getContextAsInt(Env.getCtx(), m_WindowNo, "C_BPartner_ID");

		//	Popup
		popupMenu = new WEditorPopupMenu(true, false, false);
		getComponent().getTextbox().setContext(popupMenu.getId());
		if (gridField != null && gridField.getGridTab() != null)
		{
			WFieldRecordInfo.addMenu(popupMenu);
		}
		
		getComponent().getTextbox().setReadonly(true);
	}

	@Override
	public WEditorPopupMenu getPopupMenu() {
		return popupMenu;
	}

	@Override
	public PAttributebox getComponent()
	{
		return (PAttributebox) component;
	}

	@Override
	public void setValue(Object value)
	{
		if (value == null || NO_INSTANCE.equals(value))
		{
			getComponent().setText("");
			m_value = value;
			return;
		}

		//	The same
		if (value.equals(m_value))
			return;
		//	new value
		log.fine("Value=" + value);
		m_value = value;
		getComponent().setText(m_mPAttribute.getDisplay(value));	//	loads value
	}

	@Override
	public Object getValue()
	{
		return m_value;
	}

	@Override
	public String getDisplay()
	{
		return getComponent().getText();
	}

	public void onEvent(Event event)
	{
		if (Events.ON_CHANGE.equals(event.getName()) || Events.ON_OK.equals(event.getName()))
		{
			String newText = getComponent().getText();
			String oldText = null;
			if (m_value != null)
			{
				oldText = m_mPAttribute.getDisplay(m_value);
			}
			if (oldText != null && newText != null && oldText.equals(newText))
			{
	    	    return;
	    	}
	        if (oldText == null && newText == null)
	        {
	        	return;
	        }
			ValueChangeEvent changeEvent = new ValueChangeEvent(this, this.getColumnName(), oldText, newText);
			fireValueChange(changeEvent);
		}
		else if (Events.ON_CLICK.equals(event.getName()))
		{
			cmd_dialog();
		}
	}

	/**
	 *  Start dialog
	 */
	private void cmd_dialog()
	{
		//
		Integer oldValue = (Integer)getValue ();
		int oldValueInt = oldValue == null ? 0 : oldValue.intValue ();
		int M_AttributeSetInstance_ID = oldValueInt;
		int M_Product_ID = Env.getContextAsInt (Env.getCtx (), m_WindowNo, "M_Product_ID");
		int M_ProductBOM_ID = Env.getContextAsInt (Env.getCtx (), m_WindowNo, "M_ProductBOM_ID");

		log.config("M_Product_ID=" + M_Product_ID + "/" + M_ProductBOM_ID
			+ ",M_AttributeSetInstance_ID=" + M_AttributeSetInstance_ID
			+ ", AD_Column_ID=" + gridField.getAD_Column_ID());

		//	M_Product.M_AttributeSetInstance_ID = 8418
		boolean productWindow = (gridField.getAD_Column_ID() == 8418);		//	HARDCODED

		//	Exclude ability to enter ASI
		boolean exclude = true;

		if (M_Product_ID != 0)
		{
			MProduct product = MProduct.get(Env.getCtx(), M_Product_ID);
			int M_AttributeSet_ID = Services.get(IProductBL.class).getM_AttributeSet_ID(product);
			if (M_AttributeSet_ID != 0)
			{
				final IAttributeExcludeBL excludeBL = Services.get(IAttributeExcludeBL.class);
				final I_M_AttributeSet attributeSet = InterfaceWrapperHelper.create(Env.getCtx(), M_AttributeSet_ID, I_M_AttributeSet.class, ITrx.TRXNAME_None);
				final I_M_AttributeSetExclude asExclude = excludeBL.getAttributeSetExclude(attributeSet, gridField.getAD_Column_ID(), Env.isSOTrx(Env.getCtx(), m_WindowNo));
				if ((null == asExclude) || (!excludeBL.isFullExclude(asExclude)))
				{
					exclude = false;
				}
			}
		}

		boolean changed = false;
		if (M_ProductBOM_ID != 0)	//	Use BOM Component
			M_Product_ID = M_ProductBOM_ID;
		//
		if (!productWindow && (M_Product_ID == 0 || exclude))
		{
			changed = true;
			getComponent().setText(null);
			M_AttributeSetInstance_ID = 0;
		}
		else
		{
			WPAttributeDialog vad = new WPAttributeDialog (
				M_AttributeSetInstance_ID, M_Product_ID, m_C_BPartner_ID,
				productWindow, gridField.getAD_Column_ID(), m_WindowNo);
			if (vad.isChanged())
			{
				getComponent().setText(vad.getM_AttributeSetInstanceName());
				M_AttributeSetInstance_ID = vad.getM_AttributeSetInstance_ID();
				if (m_GridTab != null && !productWindow && vad.getM_Locator_ID() > 0)
					m_GridTab.setValue("M_Locator_ID", vad.getM_Locator_ID());
				changed = true;
			}
		}
		/** Selection
		{
			//	Get Model
			MAttributeSetInstance masi = MAttributeSetInstance.get(Env.getCtx(), M_AttributeSetInstance_ID, M_Product_ID);
			if (masi == null)
			{
				log.log(Level.SEVERE, "No Model for M_AttributeSetInstance_ID=" + M_AttributeSetInstance_ID + ", M_Product_ID=" + M_Product_ID);
			}
			else
			{
				Env.setContext(Env.getCtx(), m_WindowNo, "M_AttributeSet_ID", masi.getM_AttributeSet_ID());
				//	Get Attribute Set
				MAttributeSet as = masi.getMAttributeSet();
				//	Product has no Attribute Set
				if (as == null)
					ADialog.error(m_WindowNo, this, "PAttributeNoAttributeSet");
				//	Product has no Instance Attributes
				else if (!as.isInstanceAttribute())
					ADialog.error(m_WindowNo, this, "PAttributeNoInstanceAttribute");
				else
				{
					int M_Warehouse_ID = Env.getContextAsInt (Env.getCtx (), m_WindowNo, "M_Warehouse_ID");
					int M_Locator_ID = Env.getContextAsInt (Env.getCtx (), m_WindowNo, "M_Locator_ID");
					String title = "";
					PAttributeInstance pai = new PAttributeInstance (
						Env.getFrame(this), title,
						M_Warehouse_ID, M_Locator_ID, M_Product_ID, m_C_BPartner_ID);
					if (pai.getM_AttributeSetInstance_ID() != -1)
					{
						m_text.setText(pai.getM_AttributeSetInstanceName());
						M_AttributeSetInstance_ID = pai.getM_AttributeSetInstance_ID();
						changed = true;
					}
				}
			}
		}
		**/

		//	Set Value
		if (changed)
		{
			log.finest("Changed M_AttributeSetInstance_ID=" + M_AttributeSetInstance_ID);
			m_value = new Object();				//	force re-query display
			if (M_AttributeSetInstance_ID == 0)
				setValue(null);
			else
				setValue(new Integer(M_AttributeSetInstance_ID));

			ValueChangeEvent vce = new ValueChangeEvent(this, gridField.getColumnName(), new Object(), getValue());
			fireValueChange(vce);
			if (M_AttributeSetInstance_ID == oldValueInt && m_GridTab != null && gridField != null)
			{
				//  force Change - user does not realize that embedded object is already saved.
				m_GridTab.processFieldChange(gridField);
			}
		}	//	change
	}   //  cmd_file

	public String[] getEvents()
    {
        return LISTENER_EVENTS;
    }

	public void onMenu(ContextMenuEvent evt)
	{
		if (WEditorPopupMenu.ZOOM_EVENT.equals(evt.getContextEvent()))
		{
			actionZoom();
		}
		else if (WEditorPopupMenu.CHANGE_LOG_EVENT.equals(evt.getContextEvent()))
		{
			WFieldRecordInfo.start(gridField);
		}
	}

	public void actionZoom()
	{
	   	AEnv.actionZoom(m_mPAttribute, getValue());
	}

	@Override
	public boolean isReadWrite() {
		return getComponent().getButton().isEnabled();
	}

	@Override
	public void setReadWrite(boolean readWrite) {
		getComponent().setEnabled(readWrite);
		
		getComponent().getTextbox().setReadonly(true);
	}


}
