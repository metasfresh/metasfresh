/******************************************************************************
 * Copyright (C) 2008 Low Heng Sin                                            *
 * Copyright (C) 2008 Idalica Corporation                                     *
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
package org.adempiere.webui.apps.wf;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.adempiere.apps.wf.WFActivityModel;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Button;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListHeader;
import org.adempiere.webui.component.ListItem;
import org.adempiere.webui.component.ListModelTable;
import org.adempiere.webui.component.Listbox;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.component.Textbox;
import org.adempiere.webui.component.WListItemRenderer;
import org.adempiere.webui.component.WListbox;
import org.adempiere.webui.component.Window;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.panel.StatusBarPanel;
import org.adempiere.webui.window.FDialog;
import org.compiere.model.MColumn;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MQuery;
import org.compiere.model.MRefList;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Trx;
import org.compiere.util.ValueNamePair;
import org.compiere.wf.MWFActivity;
import org.compiere.wf.MWFNode;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.North;
import org.zkoss.zkex.zul.South;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Html;

/**
 * Direct port from WFActivity
 * @author hengsin
 *
 */
public class WWFActivity extends ADForm implements EventListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8405802852868437716L;
	/**	Window No					*/
	private int         		m_WindowNo = 0;
	/**	Open Activities				*/
	private MWFActivity[] 		m_activities = null;
	/**	Current Activity			*/
	private MWFActivity 		m_activity = null;
	/**	Current Activity			*/
	private int	 				m_index = 0;
	/**	Set Column					*/
	private	MColumn 			m_column = null;
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(WWFActivity.class);

	//
	private Label lNode = new Label(Msg.translate(Env.getCtx(), "AD_WF_Node_ID"));
	private Textbox fNode = new Textbox();
	private Label lDesctiption = new Label(Msg.translate(Env.getCtx(), "Description"));
	private Textbox fDescription = new Textbox();
	private Label lHelp = new Label(Msg.translate(Env.getCtx(), "Help"));
	private Textbox fHelp = new Textbox();
	private Label lHistory = new Label(Msg.translate(Env.getCtx(), "History"));
	private Html fHistory = new Html();
	private Label lAnswer = new Label(Msg.getMsg(Env.getCtx(), "Answer"));
	private Textbox fAnswerText = new Textbox();
	private Listbox fAnswerList = new Listbox();
	private Button fAnswerButton = new Button();
	private Button bZoom = new Button();
	private Label lTextMsg = new Label(Msg.getMsg(Env.getCtx(), "Messages"));
	private Textbox fTextMsg = new Textbox();
	private Button bOK = new Button();
	private WSearchEditor fForward = null;	//	dynInit
	private Label lForward = new Label(Msg.getMsg(Env.getCtx(), "Forward"));
	private Label lOptional = new Label("(" + Msg.translate(Env.getCtx(), "Optional") + ")");
	private StatusBarPanel statusBar = new StatusBarPanel();

	private ListModelTable model = null;
	private WListbox listbox = new WListbox();
	
	private final WFActivityModel activityModel = new WFActivityModel(Env.getCtx());

	private final static String HISTORY_DIV_START_TAG = "<div style='width: 100%; height: 100px; border: 1px solid #7F9DB9;'>";
	public WWFActivity()
	{
		super();
	}

    protected void initForm()
    {
        loadActivities();

        fAnswerList.setMold("select");

    	bZoom.setImage("/images/Zoom16.png");
    	bOK.setImage("/images/Ok24.png");

        MLookup lookup = MLookupFactory.get(Env.getCtx(), m_WindowNo,
                0, 10443, DisplayType.Search);
        fForward = new WSearchEditor(lookup, Msg.translate(
                Env.getCtx(), "AD_User_ID"), "", true, false, true);

        init();
        display(-1);
    }

	private void init()
	{
		Grid grid = new Grid();
		grid.setWidth("100%");
        grid.setHeight("100%");
        grid.setStyle("margin:0; padding:0; position: absolute; align: center; valign: center;");
        grid.makeNoStrip();
        grid.setOddRowSclass("even");

		Rows rows = new Rows();
		grid.appendChild(rows);

		Row row = new Row();
		rows.appendChild(row);
		Div div = new Div();
		div.setAlign("right");
		div.appendChild(lNode);
		row.appendChild(div);
		row.appendChild(fNode);
		fNode.setWidth("100%");
		fNode.setReadonly(true);

		row = new Row();
		rows.appendChild(row);
		row.setValign("top");
		div = new Div();
		div.setAlign("right");
		div.appendChild(lDesctiption);
		row.appendChild(div);
		row.appendChild(fDescription);
		fDescription.setMultiline(true);
		fDescription.setWidth("100%");
		fDescription.setReadonly(true);

		row = new Row();
		rows.appendChild(row);
		div = new Div();
		div.setAlign("right");
		div.appendChild(lHelp);
		row.appendChild(div);
		row.appendChild(fHelp);
		fHelp.setMultiline(true);
		fHelp.setWidth("100%");
		fHelp.setReadonly(true);
		fHelp.setRows(3);
		row.appendChild(new Label());

		row = new Row();
		rows.appendChild(row);
		div = new Div();
		div.setAlign("right");
		div.appendChild(lHistory);
		row.appendChild(div);
		row.appendChild(fHistory);
		row.appendChild(new Label());

		row = new Row();
		rows.appendChild(row);
		div = new Div();
		div.setAlign("right");
		div.appendChild(lAnswer);
		row.appendChild(div);
		Hbox hbox = new Hbox();
		hbox.appendChild(fAnswerText);
		hbox.appendChild(fAnswerList);
		hbox.appendChild(fAnswerButton);
		fAnswerButton.addEventListener(Events.ON_CLICK, this);
		row.appendChild(hbox);
		row.appendChild(bZoom);
		bZoom.addEventListener(Events.ON_CLICK, this);

		row = new Row();
		rows.appendChild(row);
		div = new Div();
		div.setAlign("right");
		div.appendChild(lTextMsg);
		row.appendChild(div);
		row.appendChild(fTextMsg);
		fTextMsg.setMultiline(true);
		fTextMsg.setWidth("100%");
		row.appendChild(new Label());

		row = new Row();
		rows.appendChild(row);
		div = new Div();
		div.setAlign("right");
		div.appendChild(lForward);
		row.appendChild(div);
		hbox = new Hbox();
		hbox.appendChild(fForward.getComponent());
		hbox.appendChild(lOptional);
		row.appendChild(hbox);
		row.appendChild(bOK);
		bOK.addEventListener(Events.ON_CLICK, this);

		Borderlayout layout = new Borderlayout();
		layout.setWidth("100%");
		layout.setHeight("100%");
		layout.setStyle("background-color: transparent; position: absolute;");

		North north = new North();
		north.appendChild(listbox);
		north.setSplittable(true);
		north.setFlex(true);
		north.setHeight("50%");
		layout.appendChild(north);
		north.setStyle("background-color: transparent");
		listbox.addEventListener(Events.ON_SELECT, this);

		Center center = new Center();
		center.appendChild(grid);
		layout.appendChild(center);
		center.setStyle("background-color: transparent");
		center.setFlex(true);

		South south = new South();
		south.appendChild(statusBar);
		layout.appendChild(south);
		south.setStyle("background-color: transparent");

		this.appendChild(layout);
		this.setStyle("height: 100%; width: 100%; position: absolute;");
	}

	public void onEvent(Event event) throws Exception
	{
		Component comp = event.getTarget();
        String eventName = event.getName();

        if(eventName.equals(Events.ON_CLICK))
        {
    		if (comp == bZoom)
    			cmd_zoom();
    		else if (comp == bOK)
    		{
    			Clients.showBusy(Msg.getMsg(Env.getCtx(), "Processing"), true);
    			Events.echoEvent("onOK", this, null);
    		}
    		else if (comp == fAnswerButton)
    			cmd_button();
        } else if (Events.ON_SELECT.equals(eventName) && comp == listbox)
        {
        	m_index = listbox.getSelectedIndex();
        	if (m_index >= 0)
    			display(m_index);
        }
	}

	/**
	 * Get active activities count
	 * @return int
	 */
	public int getActivitiesCount()
	{
		return activityModel.getActivitiesCount();
	}

	/**
	 * 	Load Activities
	 * 	@return int
	 */
	public int loadActivities()
	{
		model = new ListModelTable();

		final List<MWFActivity> list = activityModel.retrieveActivities();
		m_activities = new MWFActivity[list.size ()];
		list.toArray (m_activities);
		//
		m_index = 0;

		String[] columns = new String[]{Msg.translate(Env.getCtx(), "Priority"),
				Msg.translate(Env.getCtx(), "AD_WF_Node_ID"),
				Msg.translate(Env.getCtx(), "Summary")};

		WListItemRenderer renderer = new WListItemRenderer(Arrays.asList(columns));
		ListHeader header = new ListHeader();
		header.setWidth("30px");
		renderer.setListHeader(0, header);
		renderer.addTableValueChangeListener(listbox);
		model.setNoColumns(columns.length);
		listbox.setModel(model);
		listbox.setItemRenderer(renderer);
		listbox.repaint();
		listbox.setFixedLayout(true);

		return m_activities.length;
	}	//	loadActivities

	/**
	 * 	Reset Display
	 *	@param selIndex select index
	 *	@return selected activity
	 */
	private MWFActivity resetDisplay(int selIndex)
	{
		fAnswerText.setVisible(false);
		fAnswerList.setVisible(false);
		fAnswerButton.setVisible(false);
		fAnswerButton.setImage("/images/mWindow.png");
		fTextMsg.setReadonly(!(selIndex >= 0));
		bZoom.setEnabled(selIndex >= 0);
		bOK.setEnabled(selIndex >= 0);
		fForward.setValue(null);
		fForward.setReadWrite(selIndex >= 0);
		//
		statusBar.setStatusDB(String.valueOf(selIndex+1) + "/" + m_activities.length);
		m_activity = null;
		m_column = null;
		if (m_activities.length > 0)
		{
			if (selIndex >= 0 && selIndex < m_activities.length)
				m_activity = m_activities[selIndex];
		}
		//	Nothing to show
		if (m_activity == null)
		{
			fNode.setText ("");
			fDescription.setText ("");
			fHelp.setText ("");
			fHistory.setContent(HISTORY_DIV_START_TAG + "&nbsp;</div>");
			statusBar.setStatusDB("0/0");
			statusBar.setStatusLine(Msg.getMsg(Env.getCtx(), "WFNoActivities"));
		}
		return m_activity;
	}	//	resetDisplay

	/**
	 * 	Display.
	 * 	Fill Editors
	 */
	public void display (int index)
	{
		log.fine("Index=" + index);
		//
		m_activity = resetDisplay(index);
		//	Nothing to show
		if (m_activity == null)
		{
			return;
		}
		//	Display Activity
		fNode.setText (m_activity.getNodeName());
		fDescription.setValue (m_activity.getNodeDescription());
		fHelp.setValue (m_activity.getNodeHelp());
		//
		fHistory.setContent (HISTORY_DIV_START_TAG+m_activity.getHistoryHTML()+"</div>");

		//	User Actions
		MWFNode node = m_activity.getNode();
		if (MWFNode.ACTION_UserChoice.equals(node.getAction()))
		{
			if (m_column == null)
				m_column = node.getColumn();
			if (m_column != null && m_column.get_ID() != 0)
			{
				fAnswerList.removeAllItems();
				int dt = m_column.getAD_Reference_ID();
				if (dt == DisplayType.YesNo)
				{
					ValueNamePair[] values = MRefList.getList(Env.getCtx(), 319, false);		//	_YesNo
					for(int i = 0; i < values.length; i++)
					{
						fAnswerList.appendItem(values[i].getName(), values[i].getValue());
					}
					fAnswerList.setVisible(true);
				}
				else if (dt == DisplayType.List)
				{
					ValueNamePair[] values = MRefList.getList(Env.getCtx(), m_column.getAD_Reference_Value_ID(), false);
					for(int i = 0; i < values.length; i++)
					{
						fAnswerList.appendItem(values[i].getName(), values[i].getValue());
					}
					fAnswerList.setVisible(true);
				}
				else	//	other display types come here
				{
					fAnswerText.setText ("");
					fAnswerText.setVisible(true);
				}
			}
		}
		//	--
		else if (MWFNode.ACTION_UserWindow.equals(node.getAction())
			|| MWFNode.ACTION_UserForm.equals(node.getAction()))
		{
			fAnswerButton.setLabel(node.getName());
			fAnswerButton.setTooltiptext(node.getDescription());
			fAnswerButton.setVisible(true);
		}
		else
			log.log(Level.SEVERE, "Unknown Node Action: " + node.getAction());

		statusBar.setStatusDB((m_index+1) + "/" + m_activities.length);
		statusBar.setStatusLine(Msg.getMsg(Env.getCtx(), "WFActivities"));
	}	//	display


	/**
	 * 	Zoom
	 */
	private void cmd_zoom()
	{
		log.config("Activity=" + m_activity);
		if (m_activity == null)
			return;
		AEnv.zoom(m_activity.getAD_Table_ID(), m_activity.getRecord_ID());
	}	//	cmd_zoom

	/**
	 * 	Answer Button
	 */
	private void cmd_button()
	{
		log.config("Activity=" + m_activity);
		if (m_activity == null)
			return;
		//
		MWFNode node = m_activity.getNode();
		if (MWFNode.ACTION_UserWindow.equals(node.getAction()))
		{
			int AD_Window_ID = node.getAD_Window_ID();		// Explicit Window
			String ColumnName = m_activity.getPO().get_TableName() + "_ID";
			int Record_ID = m_activity.getRecord_ID();
			MQuery query = MQuery.getEqualQuery(ColumnName, Record_ID);
			boolean IsSOTrx = m_activity.isSOTrx();
			//
			log.info("Zoom to AD_Window_ID=" + AD_Window_ID
				+ " - " + query + " (IsSOTrx=" + IsSOTrx + ")");

			AEnv.zoom(AD_Window_ID, query);
		}
		else if (MWFNode.ACTION_UserForm.equals(node.getAction()))
		{
			int AD_Form_ID = node.getAD_Form_ID();

			Window form = ADForm.openForm(AD_Form_ID);
			AEnv.showWindow(form);
		}
		else
			log.log(Level.SEVERE, "No User Action:" + node.getAction());
	}	//	cmd_button


	/**
	 * 	Save
	 */
	public void onOK()
	{
		log.config("Activity=" + m_activity);
		if (m_activity == null)
		{
			Clients.showBusy(null, false);
			return;
		}
		int AD_User_ID = Env.getAD_User_ID(Env.getCtx());
		String textMsg = fTextMsg.getValue();
		//
		MWFNode node = m_activity.getNode();

		Object forward = null;//fForward.getValue();

		// ensure activity is ran within a transaction - [ 1953628 ]
		Trx trx = null;
		try {
			trx = Trx.get(Trx.createTrxName("FWFA"), true);
			m_activity.set_TrxName(trx.getTrxName());

			if (forward != null)
			{
				log.config("Forward to " + forward);
				int fw = ((Integer)forward).intValue();
				if (fw == AD_User_ID || fw == 0)
				{
					log.log(Level.SEVERE, "Forward User=" + fw);
					trx.rollback();
					trx.close();
					return;
				}
				if (!m_activity.forwardTo(fw, textMsg))
				{
					FDialog.error(m_WindowNo, this, "CannotForward");
					trx.rollback();
					trx.close();
					return;
				}
			}
			//	User Choice - Answer
			else if (MWFNode.ACTION_UserChoice.equals(node.getAction()))
			{
				if (m_column == null)
					m_column = node.getColumn();
				//	Do we have an answer?
				int dt = m_column.getAD_Reference_ID();
				String value = fAnswerText.getText();
				if (dt == DisplayType.YesNo || dt == DisplayType.List)
				{
					ListItem li = fAnswerList.getSelectedItem();
					if(li != null) value = li.getValue().toString();
				}
				if (value == null || value.length() == 0)
				{
					FDialog.error(m_WindowNo, this, "FillMandatory", Msg.getMsg(Env.getCtx(), "Answer"));
					trx.rollback();
					trx.close();
					return;
				}
				//
				log.config("Answer=" + value + " - " + textMsg);
				try
				{
					m_activity.setUserChoice(AD_User_ID, value, dt, textMsg);
				}
				catch (Exception e)
				{
					log.log(Level.SEVERE, node.getName(), e);
					FDialog.error(m_WindowNo, this, "Error", e.toString());
					trx.rollback();
					trx.close();
					return;
				}
			}
			//	User Action
			else
			{
				log.config("Action=" + node.getAction() + " - " + textMsg);
				try
				{
					// ensure activity is ran within a transaction
					m_activity.setUserConfirmation(AD_User_ID, textMsg);
				}
				catch (Exception e)
				{
					log.log(Level.SEVERE, node.getName(), e);
					FDialog.error(m_WindowNo, this, "Error", e.toString());
					trx.rollback();
					trx.close();
					return;
				}

			}

			trx.commit();
		}
		finally
		{
			Clients.showBusy(null, false);
			if (trx != null)
				trx.close();
		}

		//	Next
		loadActivities();
		display(-1);
	}	//	onOK
}
