/******************************************************************************
 * Product: Posterita Ajax UI 												  *
 * Copyright (C) 2007 Posterita Ltd.  All Rights Reserved.                    *
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
 * Posterita Ltd., 3, Draper Avenue, Quatre Bornes, Mauritius                 *
 * or via info@posterita.org or http://www.posterita.org/                     *
 *****************************************************************************/

package org.adempiere.webui.component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.adempiere.webui.LayoutUtils;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Messagebox;
/**
 * Application Confirm Panel
 * Web UI port of the rich client's ConfirmPanel by Jorg Janke
 * @author Sendy Yagambrum
 * @date July 25, 2007
 **/
public final class ConfirmPanel extends Hbox
{
    /**
	 *
	 */
	private static final long serialVersionUID = -6050634074454659578L;
	/** Action String OK.        */
    public static final String A_OK = "Ok";
    /** Action String Cancel.    */
    public static final String A_CANCEL = "Cancel";
    /** Action String Refresh.   */
    public static final String A_REFRESH = "Refresh";
    /** Action String Reset.     */
    public static final String A_RESET = "Reset";
    /** Action String Customize. */
    public static final String A_CUSTOMIZE = "Customize";
    /** Action String History.   */
    public static final String A_HISTORY = "History";
    /** Action String Zoom.      */
    public static final String A_ZOOM = "Zoom";

    /** Action String Process.   */
    public static final String A_PROCESS = "Process";
    /** Action String Print.     */
    public static final String A_PRINT = "Print";
    /** Action String Export.    */
    public static final String A_EXPORT = "Export";
    /** Action String Help.      */
    public static final String A_HELP = "Help";
    /** Action String Delete.    */
    public static final String A_DELETE = "Delete";
    /** Action String PAttribute.    */
    public static final String A_PATTRIBUTE = "PAttribute";
    /** Action String New.   */
    public static final String A_NEW = "New";

    private boolean  m_withText = false;

    private Map<String, Button> buttonMap = new HashMap<String, Button>();

    /**
     * Creates a button of the specified id
     *
     * @param id button id
     * @return  button
     *
     * <p>The string can be any of the following and the corresponding button will be created: </p>
     * <dl>
     * <dt>Ok</dt>          <dd>Ok button</dd>
     * <dt>Cancel</dt>      <dd>Cancel button</dd>
     * <dt>Refresh</dt>     <dd>Refresh button</dd>
     * <dt>Reset</dt>       <dd>Reset button</dd>
     * <dt>History</dt>     <dd>History button</dd>
     * <dt>Process</dt>     <dd>Process button</dd>
     * <dt>New</dt>         <dd>New button</dd>
     * <dt>Customize</dt>   <dd>Customize button</dd>
     * <dt>Delete</dt>      <dd>Delete button</dd>
     * <dt>Save</dt>        <dd>Save button</dd>
     * <dt>Zoom</dt>        <dd>Zoom button</dd>
     * <dt>Help</dt>        <dd>Help button</dd>
     * </dl>
     *
     */
    public Button createButton(String name)
    {
        Button button = new Button();
        button.setName("btn"+name);
        button.setId(name);
        String text = Msg.translate(Env.getCtx(), name);
        if (!name.equals(text))
        	text = text.replaceAll("[&]", "");
        else
        	text = null;

        if (m_withText && text != null)
        {
        	button.setImage("images/"+name+"16.png");
        	button.setLabel(text);
        	LayoutUtils.addSclass("action-text-button", button);
        }
        else
        {
        	button.setImage("images/"+name+"24.png");
        	if (text != null)
        		button.setTooltiptext(text);
        	LayoutUtils.addSclass("action-button", button);
        }

        buttonMap.put(name, button);

        return button;
    }

    /**
     * create confirm panel with multiple options
     * @param withCancelButton       with cancel
     * @param withRefreshButton      with refresh
     * @param withResetButton        with reset
     * @param withCustomizeButton    with customize
     * @param withHistoryButton      with history
     * @param withZoomButton         with zoom
     */
     public ConfirmPanel(boolean withCancelButton,
             boolean withRefreshButton,
             boolean withResetButton,
             boolean withCustomizeButton,
             boolean withHistoryButton,
             boolean withZoomButton)
     {
    	 this(withCancelButton, withRefreshButton, withResetButton, withCustomizeButton, withHistoryButton, withZoomButton, false);
     }

   /**
    * create confirm panel with multiple options
    * @param withCancelButton       with cancel
    * @param withRefreshButton      with refresh
    * @param withResetButton        with reset
    * @param withCustomizeButton    with customize
    * @param withHistoryButton      with history
    * @param withZoomButton         with zoom
    */
    public ConfirmPanel(boolean withCancelButton,
            boolean withRefreshButton,
            boolean withResetButton,
            boolean withCustomizeButton,
            boolean withHistoryButton,
            boolean withZoomButton,
            boolean withText)
    {
    	m_withText = withText;

        init();

        setVisible(A_CANCEL, withCancelButton);
        addComponentsRight(createButton(A_OK));

        if (withCancelButton)
        	addComponentsRight(createButton(A_CANCEL));

        if (withRefreshButton)
        {
             addComponentsLeft(createButton(A_REFRESH));
        }
        if (withResetButton)
        {
            addComponentsLeft(createButton(A_RESET));
        }
        if (withCustomizeButton)
        {
            addComponentsLeft(createButton(A_CUSTOMIZE));
        }
        if (withHistoryButton)
        {
            addComponentsLeft(createButton(A_HISTORY));
        }
        if (withZoomButton)
        {
            addComponentsLeft(createButton(A_ZOOM));
        }
    }

    /**
     * Create confirm panel with Ok button only
     */
    public ConfirmPanel()
    {
        this(false,false,false,false,false,false);
    }

    /**
     * Create confirm panel with Ok and Cancel button
     * @param withCancel with cancel
     *
     */
    public ConfirmPanel(boolean withCancel)
    {
        this(withCancel,false,false,false,false,false);
    }
    //
    private Hbox hboxBtnLeft;
    private Hbox hboxBtnRight;
    //
    private Panel pnlBtnRight;
    private Panel pnlBtnLeft;

    /**
     * initialise components
     */
    private void init()
    {
        pnlBtnLeft = new Panel();
        pnlBtnLeft.setAlign("left");

        pnlBtnRight = new Panel();
        pnlBtnRight.setAlign("right");

        hboxBtnRight = new Hbox();
        hboxBtnRight.appendChild(pnlBtnRight);
        hboxBtnRight.setWidth("100%");
        hboxBtnRight.setStyle("text-align:right");

        hboxBtnLeft = new Hbox();
        hboxBtnLeft.appendChild(pnlBtnLeft);
        hboxBtnLeft.setWidth("100%");
        hboxBtnLeft.setStyle("text-align:left");

        this.appendChild(hboxBtnLeft);
        this.appendChild(hboxBtnRight);
        this.setWidth("100%");
    }

    /**
     * add button to the left side of the confirm panel
     * @param button button
     */
    public void addComponentsLeft(Button button)
    {
    	if (!buttonMap.containsKey(button.getId()))
    		buttonMap.put(button.getId(), button);
        pnlBtnLeft.appendChild(button);
    }

    /**
     * add button to the right side of the confirm panel
     * @param button button
     */
    public void addComponentsRight(Button button)
    {
    	if (!buttonMap.containsKey(button.getId()))
    		buttonMap.put(button.getId(), button);
        pnlBtnRight.appendChild(button);
    }

    /**
     * return button of the specified id
     * @param id button id
     * @return button or null if no button is found
     * <p> The button id can be any of the following
     * <dl>
     * <dt>Ok</dt>          <dd>Ok button</dd>
     * <dt>Cancel</dt>      <dd>Cancel button</dd>
     * <dt>Refresh</dt>     <dd>Refresh button</dd>
     * <dt>Reset</dt>       <dd>Reset button</dd>
     * <dt>History</dt>     <dd>History button</dd>
     * <dt>Process</dt>     <dd>Process button</dd>
     * <dt>New</dt>         <dd>New button</dd>
     * <dt>Customize</dt>   <dd>Customize button</dd>
     * <dt>Delete</dt>      <dd>Delete button</dd>
     * <dt>Save</dt>        <dd>Save button</dd>
     * <dt>Zoom</dt>        <dd>Zoom button</dd>
     * <dt>Help</dt>        <dd>Help button</dd>
     * </dl>
     */
    public Button getButton(String id)
    {
        return buttonMap.get(id);
    }

    /**
     * sets the visibility of the specified button
     * @param btnName   button name
     * @param visible   visibility
     * <p> The button name can be any of the following
     * <dl>
     * <dt>Ok</dt>          <dd>Ok button</dd>
     * <dt>Cancel</dt>      <dd>Cancel button</dd>
     * <dt>Refresh</dt>     <dd>Refresh button</dd>
     * <dt>Reset</dt>       <dd>Reset button</dd>
     * <dt>History</dt>     <dd>History button</dd>
     * <dt>Process</dt>     <dd>Process button</dd>
     * <dt>New</dt>         <dd>New button</dd>
     * <dt>Customize</dt>   <dd>Customize button</dd>
     * <dt>Delete</dt>      <dd>Delete button</dd>
     * <dt>Save</dt>        <dd>Save button</dd>
     * <dt>Zoom</dt>        <dd>Zoom button</dd>
     * <dt>Help</dt>        <dd>Help button</dd>
     * </dl>
     */
    public void setVisible(String id, boolean visible)
    {
        Button btn = getButton(id);
        if (btn != null)
        {
            btn.setVisible(visible);
        }
    }
    /**
     * returns whether the specified button is visible or not
     * @param btnName
     * @return visibility of the button
     * <p> The button name can be any of the following
     * <dl>
     * <dt>Ok</dt>          <dd>Ok button</dd>
     * <dt>Cancel</dt>      <dd>Cancel button</dd>
     * <dt>Refresh</dt>     <dd>Refresh button</dd>
     * <dt>Reset</dt>       <dd>Reset button</dd>
     * <dt>History</dt>     <dd>History button</dd>
     * <dt>Process</dt>     <dd>Process button</dd>
     * <dt>New</dt>         <dd>New button</dd>
     * <dt>Customize</dt>   <dd>Customize button</dd>
     * <dt>Delete</dt>      <dd>Delete button</dd>
     * <dt>Save</dt>        <dd>Save button</dd>
     * <dt>Zoom</dt>        <dd>Zoom button</dd>
     * <dt>Help</dt>        <dd>Help button</dd>
     * </dl>
     */
    public boolean isVisible(String btnName)
    {
        Button btn = getButton(btnName);
        if (btn != null)
        {
            return btn.isVisible();
        }
        else
        {
            try
            {
                Messagebox.show("No "+btnName+" button available");
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return false;
        }
    }
    /**
     * enable specific button
     * @param id   button id
     * @param enabled   enabled
     *
     * <p> The button id can be any of the following
     * <dl>
     * <dt>Ok</dt>          <dd>Ok button</dd>
     * <dt>Cancel</dt>      <dd>Cancel button</dd>
     * <dt>Refresh</dt>     <dd>Refresh button</dd>
     * <dt>Reset</dt>       <dd>Reset button</dd>
     * <dt>History</dt>     <dd>History button</dd>
     * <dt>Process</dt>     <dd>Process button</dd>
     * <dt>New</dt>         <dd>New button</dd>
     * <dt>Customize</dt>   <dd>Customize button</dd>
     * <dt>Delete</dt>      <dd>Delete button</dd>
     * <dt>Save</dt>        <dd>Save button</dd>
     * <dt>Zoom</dt>        <dd>Zoom button</dd>
     * <dt>Help</dt>        <dd>Help button</dd>
     * </dl>
     */
    public void setEnabled(String id, boolean enabled)
    {
        Button button = getButton(id);
        if (button != null)
        {
            button.setEnabled(enabled);
        }
    }

    /**
     * enable all components
     * @param enabled enabled
     */
    public void setEnabledAll(boolean enabled)
    {
        List list1 = pnlBtnLeft.getChildren();
        List list2 = pnlBtnRight.getChildren();
        Iterator iter1 = list1.iterator();
        Iterator iter2 = list2.iterator();

        while (iter1.hasNext())
        {
            Button button = (Button)iter1.next();
            button.setEnabled(enabled);
        }
        while (iter2.hasNext())
        {
            Button button = (Button)iter2.next();
            button.setEnabled(enabled);
        }
    }
    /**
     * add action listener on the existing buttons
     * @param event event
     * @param listener listener
     */
    public void addActionListener(String event, EventListener listener)
    {
        List list1 = pnlBtnLeft.getChildren();
        List list2 = pnlBtnRight.getChildren();
        Iterator iter1 = list1.iterator();
        Iterator iter2 = list2.iterator();

        while (iter1.hasNext())
        {
            Button button = (Button)iter1.next();
            button.addEventListener(event, listener);
        }
        while (iter2.hasNext())
        {
            Button button = (Button)iter2.next();
            button.addEventListener(event, listener);
        }
    }

    /**
     * added to ease porting of swing form
     * @param listener
     */
	public void addActionListener(EventListener listener) {
		addActionListener(Events.ON_CLICK, listener);
	}

	/**
	 * alias for addComponentsLeft for ease of porting swing form
	 * @param selectAllButton
	 */
	public void addButton(Button button) {
		addComponentsLeft(button);
	}

	/**
	 * alias for getButton("Ok"), to ease porting of swing form
	 * @return Button
	 */
	public Button getOKButton() {
		return getButton(A_OK);
	}

}   //  ConfirmPanel
