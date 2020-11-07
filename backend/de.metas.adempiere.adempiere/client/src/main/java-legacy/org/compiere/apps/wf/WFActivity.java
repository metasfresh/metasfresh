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
package org.compiere.apps.wf;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.reflist.ReferenceId;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.apps.wf.WFActivityModel;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
import org.compiere.apps.AWindow;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.StatusBar;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.grid.ed.VLookup;
import org.compiere.minigrid.MiniTable;
import org.compiere.model.I_AD_Form;
import org.compiere.model.MQuery;
import org.compiere.model.MRefList;
import org.compiere.model.PO;
import org.compiere.swing.CButton;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CScrollPane;
import org.compiere.swing.CTextArea;
import org.compiere.swing.CTextField;
import org.compiere.swing.CTextPane;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.compiere.util.ValueNamePair;
import org.compiere.wf.MWFActivity;
import org.compiere.wf.MWFEventAudit;
import de.metas.workflow.WFNode;
import de.metas.workflow.WFNodeAction;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * WorkFlow Activities Panel
 *
 * @author Jorg Janke
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL - BF [ 1748449 ]
 * @author Compiere - CarlosRuiz integrate code for table selection on workflow present at GPL version of Compiere 3.2.0
 * @version $Id: WFActivity.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class WFActivity extends CPanel
		implements FormPanel, ActionListener, ListSelectionListener
{
	/**
	 *
	 */
	private static final long serialVersionUID = 6917300855914216420L;

	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	/**
	 * WF Activity
	 */
	public WFActivity()
	{
		super();
		log.info("");
		m_WindowNo = Env.createWindowNo(this);
		try
		{
			dynInit(m_WindowNo);
			jbInit();
		}
		catch (final Exception e)
		{
			log.error("", e);
		}
	}    // WFActivity

	private WFActivityModel model;

	/**
	 * Window No
	 */
	private int m_WindowNo = 0;
	/**
	 * FormFrame
	 */
	private FormFrame m_frame = null;
	/**
	 * Open Activities
	 */
	private MWFActivity[] m_activities = null;
	/**
	 * Current Activity
	 */
	private MWFActivity m_activity = null;
	/** Set Column */
	// private MColumn m_column = null;
	/**
	 * Logger
	 */
	private static final Logger log = LogManager.getLogger(WFActivity.class);

	DefaultTableModel selTableModel = new DefaultTableModel(
			new String[] { msgBL.translate(Env.getCtx(), "Priority"),
					msgBL.translate(Env.getCtx(), "AD_WF_Node_ID"),
					msgBL.translate(Env.getCtx(), "Summary") }, 0);
	private final MiniTable selTable = new MiniTable();
	private final CScrollPane selPane = new CScrollPane(selTable);
	//
	private final CPanel centerPanel = new CPanel();
	private final GridBagLayout centerLayout = new GridBagLayout();
	private final CLabel lNode = new CLabel(msgBL.translate(Env.getCtx(), "AD_WF_Node_ID"));
	private final CTextField fNode = new CTextField();
	private final CLabel lDesctiption = new CLabel(msgBL.translate(Env.getCtx(), "Description"));
	private final CTextArea fDescription = new CTextArea();
	private final CLabel lHelp = new CLabel(msgBL.translate(Env.getCtx(), "Help"));
	private final CTextArea fHelp = new CTextArea();
	private final CLabel lHistory = new CLabel(msgBL.translate(Env.getCtx(), "History"));
	private final CTextPane fHistory = new CTextPane();
	private final CLabel lAnswer = new CLabel(msgBL.getMsg(Env.getCtx(), "Answer"));
	private final CPanel answers = new CPanel(new FlowLayout(FlowLayout.LEADING));
	private final CTextField fAnswerText = new CTextField();
	private final CComboBox fAnswerList = new CComboBox();
	private final CButton fAnswerButton = new CButton();
	// private CButton bPrevious = AEnv.getButton("Previous");
	// private CButton bNext = AEnv.getButton("Next");
	private final CButton bZoom = AEnv.getButton("Zoom");
	private final CLabel lTextMsg = new CLabel(msgBL.getMsg(Env.getCtx(), "Messages"));
	private final CTextArea fTextMsg = new CTextArea();
	private final CButton bOK = ConfirmPanel.createOKButton(true);
	private VLookup fForward = null;    // dynInit
	private final CLabel lForward = new CLabel(msgBL.getMsg(Env.getCtx(), "Forward"));
	private final CLabel lOptional = new CLabel("(" + msgBL.translate(Env.getCtx(), "Optional") + ")");
	private final StatusBar statusBar = new StatusBar();

	/**
	 * Dynamic Init. Called before Static Init
	 *
	 * @param WindowNo window
	 */
	private void dynInit(final int WindowNo)
	{
		model = new WFActivityModel(Env.getCtx());
		loadActivities();
		// Forward
		fForward = VLookup.createUser(WindowNo);
	}    // dynInit

	/**
	 * Static Init. Called after Dynamic Init
	 *
	 * @throws Exception
	 */
	private void jbInit() throws Exception
	{
		final int width = 150;
		centerPanel.setLayout(centerLayout);
		fNode.setReadWrite(false);
		fDescription.setReadWrite(false);
		fDescription.setPreferredSize(new Dimension(width, 40));
		fHelp.setReadWrite(false);
		fHelp.setPreferredSize(new Dimension(width, 40));
		fHistory.setReadWrite(false);
		fHistory.setPreferredSize(new Dimension(width, 80));
		fTextMsg.setPreferredSize(new Dimension(width, 40));
		//
		// bPrevious.addActionListener(this);
		// bNext.addActionListener(this);
		selTable.setModel(selTableModel);
		selTable.setColumnClass(0, Integer.class, true);      // 0-Priority
		selTable.setColumnClass(1, String.class, true);        // 1-AD_WF_Node_ID
		selTable.setColumnClass(2, String.class, true);        // 2-Summary
		selTable.getSelectionModel().addListSelectionListener(this);
		bZoom.addActionListener(this);
		bOK.addActionListener(this);
		//
		this.setLayout(new BorderLayout());
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(statusBar, BorderLayout.SOUTH);
		//
		// answers.setOpaque(false);
		answers.add(fAnswerText);
		answers.add(fAnswerList);
		answers.add(fAnswerButton);
		fAnswerButton.addActionListener(this);
		//
		int row = 0;
		selPane.setPreferredSize(new Dimension(width, 60));
		selPane.setMinimumSize(new Dimension(100, 60));
		centerPanel.add(selPane, new GridBagConstraints(0, row, 4, 1, 0.3, 0.3,
				GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
				new Insets(5, 10, 5, 10), 0, 0));

		centerPanel.add(lNode, new GridBagConstraints(0, ++row, 1, 1, 0.0, 0.0,
				GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
				new Insets(5, 10, 5, 5), 0, 0));
		centerPanel.add(fNode, new GridBagConstraints(1, row, 3, 2, 0.5, 0.0,
				GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 0, 5, 10), 0, 0));

		centerPanel.add(lDesctiption, new GridBagConstraints(0, ++row, 1, 1, 0.0, 0.0,
				GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
				new Insets(5, 10, 5, 5), 0, 0));
		centerPanel.add(fDescription, new GridBagConstraints(1, row, 3, 1, 0.0, 0.0,
				GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 0, 5, 10), 0, 0));

		centerPanel.add(lHelp, new GridBagConstraints(0, ++row, 1, 1, 0.0, 0.0,
				GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
				new Insets(2, 10, 5, 5), 0, 0));
		centerPanel.add(fHelp, new GridBagConstraints(1, row, 3, 1, 0.0, 0.0,
				GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
				new Insets(2, 0, 5, 10), 0, 0));

		centerPanel.add(lHistory, new GridBagConstraints(0, ++row, 1, 1, 0.0, 0.0,
				GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
				new Insets(5, 10, 5, 5), 0, 0));
		centerPanel.add(fHistory, new GridBagConstraints(1, row, 3, 1, 0.5, 0.5,
				GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH,
				new Insets(5, 0, 5, 10), 0, 0));

		centerPanel.add(lAnswer, new GridBagConstraints(0, ++row, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(10, 10, 5, 5), 0, 0));
		centerPanel.add(answers, new GridBagConstraints(1, row, 2, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
				new Insets(10, 0, 5, 5), 0, 0));
		centerPanel.add(bZoom, new GridBagConstraints(3, row, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(10, 0, 10, 10), 0, 0));

		centerPanel.add(lTextMsg, new GridBagConstraints(0, ++row, 1, 1, 0.0, 0.0,
				GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
				new Insets(5, 10, 5, 5), 0, 0));
		centerPanel.add(fTextMsg, new GridBagConstraints(1, row, 3, 1, 0.5, 0.0,
				GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL,
				new Insets(5, 0, 5, 10), 0, 0));

		centerPanel.add(lForward, new GridBagConstraints(0, ++row, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(10, 10, 5, 5), 0, 0));
		centerPanel.add(fForward, new GridBagConstraints(1, row, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(10, 0, 5, 0), 0, 0));
		centerPanel.add(lOptional, new GridBagConstraints(2, row, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE,
				new Insets(10, 5, 5, 5), 0, 0));
		centerPanel.add(bOK, new GridBagConstraints(3, row, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(10, 5, 5, 10), 0, 0));
	}    // jbInit

	/**
	 * Initialize Panel for FormPanel
	 *
	 * @param WindowNo window
	 * @param frame    frame
	 * @see org.compiere.apps.form.FormPanel#init(int, FormFrame)
	 */
	@Override
	public void init(final int WindowNo, final FormFrame frame)
	{
		m_WindowNo = WindowNo;
		m_frame = frame;
		//
		log.info("");
		try
		{
			dynInit(WindowNo);
			jbInit();
			//
			// this.setPreferredSize(new Dimension (400,400));
			frame.getContentPane().add(this, BorderLayout.CENTER);
			display(-1);
		}
		catch (final Exception e)
		{
			log.error("", e);
		}
	}    // init

	/**
	 * Dispose
	 *
	 * @see org.compiere.apps.form.FormPanel#dispose()
	 */
	@Override
	public void dispose()
	{
		if (m_frame != null)
		{
			m_frame.dispose();
		}
		m_frame = null;
	}    // dispose

	/**
	 * Get active activities count
	 *
	 * @return int
	 */
	public int getActivitiesCount()
	{
		final int count = model.getActivitiesCount();
		return count;
	}

	/**
	 * Load Activities
	 *
	 * @return int
	 */
	public int loadActivities()
	{
		while (selTableModel.getRowCount() > 0)
		{
			selTableModel.removeRow(0);
		}

		final long start = System.currentTimeMillis();
		final List<MWFActivity> activities = model.retrieveActivities();
		final String adLanguage = Env.getADLanguageOrBaseLanguage();
		for (final MWFActivity activity : activities)
		{
			final Object[] rowData = new Object[3];
			rowData[0] = activity.getPriority();
			rowData[1] = activity.getNode().getName().translate(adLanguage);
			rowData[2] = getSummary(activity);
			selTableModel.addRow(rowData);
		}

		selTable.autoSize(false);
		m_activities = activities.toArray(new MWFActivity[activities.size()]);
		//
		log.debug("#" + m_activities.length + "(" + (System.currentTimeMillis() - start) + "ms)");
		return m_activities.length;
	}    // loadActivities

	/**
	 * @return PO Summary
	 */
	@Nullable
	private static String getSummary(final MWFActivity activity)
	{
		final PO po = activity.getPO();
		if (po == null)
		{
			return null;
		}
		final StringBuilder sb = new StringBuilder();
		final String[] keyColumns = po.get_KeyColumns();
		if ((keyColumns != null) && (keyColumns.length > 0))
		{
			sb.append(Services.get(IMsgBL.class).translate(Env.getCtx(), keyColumns[0])).append(" ");
		}
		int index = po.get_ColumnIndex("DocumentNo");
		if (index != -1)
		{
			sb.append(po.get_Value(index)).append(": ");
		}
		index = po.get_ColumnIndex("SalesRep_ID");
		Integer sr = null;
		if (index != -1)
		{
			sr = (Integer)po.get_Value(index);
		}
		else
		{
			index = po.get_ColumnIndex("AD_User_ID");
			if (index != -1)
			{
				sr = (Integer)po.get_Value(index);
			}
		}
		if (sr != null)
		{
			final String userFullname = Services.get(IUserDAO.class).retrieveUserFullname(sr);
			if (!Check.isEmpty(userFullname))
			{
				sb.append(userFullname).append(" ");
			}
		}
		//
		index = po.get_ColumnIndex("C_BPartner_ID");
		if (index != -1)
		{
			final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(po.get_ValueAsInt(index));
			if (bpartnerId != null)
			{
				final String bpartnerName = Services.get(IBPartnerBL.class).getBPartnerName(bpartnerId);
				sb.append(bpartnerName).append(" ");
			}
		}
		return sb.toString();
	}

	/**
	 * Display.
	 *
	 * @param index index of table Fill Editors
	 */
	public void display(final int index)
	{
		log.debug("Index=" + index);
		m_activity = resetDisplay(index);
		//
		if (m_activity == null)
		{
			return;
		}

		// Display Activity
		final String adLanguage = Env.getADLanguageOrBaseLanguage();
		fNode.setText(m_activity.getNode().getName().translate(adLanguage));
		fDescription.setText(m_activity.getNode().getDescription().translate(adLanguage));
		fHelp.setText(m_activity.getNode().getHelp().translate(adLanguage));
		//
		fHistory.setText(getHistoryHTML(m_activity));

		// User Actions
		final WFNode node = m_activity.getNode();
		if (WFNodeAction.UserChoice.equals(node.getAction()))
		{
			final ReferenceId dt = node.getDocumentColumnValueType();
			if (dt.getRepoId() == DisplayType.YesNo)
			{
				final ValueNamePair[] values = MRefList.getList(Env.getCtx(), 319, false);        // _YesNo
				fAnswerList.setModel(new DefaultComboBoxModel(values));
				fAnswerList.setVisible(true);
			}
			else if (dt.getRepoId() == DisplayType.List)
			{
				final ValueNamePair[] values = MRefList.getList(Env.getCtx(), node.getDocumentColumnValueReferenceId().getRepoId(), false);
				fAnswerList.setModel(new DefaultComboBoxModel(values));
				fAnswerList.setVisible(true);
			}
			else
			// other display types come here
			{
				fAnswerText.setText("");
				fAnswerText.setVisible(true);
			}
		}
		// --
		else if (WFNodeAction.UserWindow.equals(node.getAction())
				|| WFNodeAction.UserForm.equals(node.getAction()))
		{
			fAnswerButton.setText(node.getName().getDefaultValue());
			fAnswerButton.setToolTipText(node.getDescription().getDefaultValue());
			fAnswerButton.setVisible(true);
		}
		/*
		 * else if (WFNodeAction.UserWorkbench.equals(node.getAction())) log.error("Workflow Action not implemented yet");
		 */
		else
		{
			log.error("Unknown Node Action: " + node.getAction());
		}

		statusBar.setStatusDB((index + 1) + "/" + m_activities.length);
		statusBar.setStatusLine(msgBL.getMsg(Env.getCtx(), "WFActivities"));
	}    // display

	/**************************************************************************
	 * Get Process Activity (Event) History
	 *
	 * @return history
	 */
	private static String getHistoryHTML(final MWFActivity activity)
	{
		final SimpleDateFormat format = DisplayType.getDateFormat(DisplayType.DateTime);
		final StringBuilder sb = new StringBuilder();
		final MWFEventAudit[] events = MWFEventAudit.get(activity.getCtx(), activity.getAD_WF_Process_ID(), ITrx.TRXNAME_None);
		for (final MWFEventAudit audit : events)
		{
			// sb.append("<p style=\"width:400\">");
			sb.append("<p>");
			sb.append(format.format(audit.getCreated()))
					.append(" ")
					.append(getHTMLpart("b", audit.getAD_WF_Node().getName()))
					.append(": ")
					.append(getHTMLpart(null, audit.getDescription()))
					.append(getHTMLpart("i", audit.getTextMsg()));
			sb.append("</p>");
		}
		return sb.toString();
	}    // getHistory

	/**
	 * Get HTML part
	 *
	 * @param tag     HTML tag
	 * @param content content
	 * @return <tag>content</tag>
	 */
	private static StringBuilder getHTMLpart(@Nullable final String tag, final String content)
	{
		final StringBuilder sb = new StringBuilder();
		if (content == null || content.length() == 0)
		{
			return sb;
		}
		if (tag != null && tag.length() > 0)
		{
			sb.append("<").append(tag).append(">");
		}
		sb.append(content);
		if (tag != null && tag.length() > 0)
		{
			sb.append("</").append(tag).append(">");
		}
		return sb;
	}    // getHTMLpart

	/**
	 * Reset Display
	 *
	 * @param selIndex select index
	 * @return selected activity
	 */
	private MWFActivity resetDisplay(final int selIndex)
	{
		fAnswerText.setVisible(false);
		fAnswerList.setVisible(false);
		fAnswerButton.setVisible(false);
		fTextMsg.setReadWrite(selIndex >= 0);
		bZoom.setEnabled(selIndex >= 0);
		bOK.setEnabled(selIndex >= 0);
		fForward.setValue(null);
		fForward.setEnabled(selIndex >= 0);
		//
		statusBar.setStatusDB((selIndex + 1) + "/" + m_activities.length);
		m_activity = null;
		if (m_activities.length > 0)
		{
			if (selIndex >= 0 && selIndex < m_activities.length)
			{
				m_activity = m_activities[selIndex];
			}
		}
		// Nothing to show
		if (m_activity == null)
		{
			fNode.setText("");
			fDescription.setText("");
			fHelp.setText("");
			fHistory.setText("");
			statusBar.setStatusDB("0/0");
			statusBar.setStatusLine(msgBL.getMsg(Env.getCtx(), "WFNoActivities"));
		}
		return m_activity;
	}    // resetDisplay

	/**
	 * Selection Listener
	 *
	 * @param e event
	 */
	@Override
	public void valueChanged(final ListSelectionEvent e)
	{
		final int index = selTable.getSelectedRow();
		if (index >= 0)
		{
			display(index);
		}
	}    // valueChanged

	/**
	 * Action Listener
	 *
	 * @param e event
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e)
	{
		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		//
		if (e.getSource() == bZoom)
		{
			cmd_zoom();
		}
		else if (e.getSource() == bOK)
		{
			cmd_OK();
		}
		else if (e.getSource() == fAnswerButton)
		{
			cmd_button();
		}
		//
		this.setCursor(Cursor.getDefaultCursor());
	}    // actionPerformed

	/**
	 * Zoom
	 */
	private void cmd_zoom()
	{
		log.info("Activity=" + m_activity);
		if (m_activity == null)
		{
			return;
		}
		AEnv.zoom(m_activity.getAD_Table_ID(), m_activity.getRecord_ID());
	}    // cmd_zoom

	/**
	 * Answer Button
	 */
	private void cmd_button()
	{
		log.info("Activity=" + m_activity);
		if (m_activity == null)
		{
			return;
		}
		//
		final WFNode node = m_activity.getNode();
		if (WFNodeAction.UserWindow.equals(node.getAction()))
		{
			final AdWindowId AD_Window_ID = node.getAdWindowId();        // Explicit Window
			final String ColumnName = m_activity.getPO().get_TableName() + "_ID";
			final int Record_ID = m_activity.getRecord_ID();
			final MQuery query = MQuery.getEqualQuery(ColumnName, Record_ID);
			final boolean IsSOTrx = m_activity.isSOTrx();
			//
			log.info("Zoom to AD_Window_ID=" + AD_Window_ID
					+ " - " + query + " (IsSOTrx=" + IsSOTrx + ")");
			AWindow frame = new AWindow();
			if (!frame.initWindow(AD_Window_ID, query))
			{
				return;
			}
			AEnv.addToWindowManager(frame);
			AEnv.showCenterScreen(frame);
			frame = null;
		}
		else if (WFNodeAction.UserForm.equals(node.getAction()))
		{
			final int adFormId = node.getAdFormId();
			final I_AD_Form form = InterfaceWrapperHelper.loadOutOfTrx(adFormId, I_AD_Form.class);
			final FormFrame ff = new FormFrame();
			if (ff.openForm(form))
			{
				ff.pack();
				AEnv.addToWindowManager(ff);
				AEnv.showCenterScreen(ff);
			}
			else
			{
				ff.dispose();
				return;
			}
		}
		/*
		 * else if (WFNodeAction.UserWorkbench.equals(node.getAction())) {
		 *
		 * }
		 */
		else
		{
			log.error("No User Action:" + node.getAction());
		}
	}    // cmd_button

	/**
	 * Save
	 */
	private void cmd_OK()
	{
		log.info("Activity=" + m_activity);
		if (m_activity == null)
		{
			return;
		}
		final UserId userId = Env.getLoggedUserId();
		final String textMsg = fTextMsg.getText();
		//
		final WFNode node = m_activity.getNode();

		final Object forward = fForward.getValue();

		// ensure activity is ran within a transaction - [ 1953628 ]
		final Trx trx = Trx.get(Trx.createTrxName("FWFA"), true);
		m_activity.set_TrxName(trx.getTrxName());

		if (forward != null)
		{
			log.info("Forward to {}", forward);
			final UserId forwardToId = UserId.ofRepoIdOrNullIfSystem((Integer)forward);
			if (forwardToId == null || UserId.equals(userId, forwardToId))
			{
				log.error("Forward User={}", forwardToId);
				trx.rollback();
				trx.close();
				return;
			}
			else
			{
				m_activity.forwardTo(forwardToId, textMsg);
			}
		}
		// User Choice - Answer
		else if (WFNodeAction.UserChoice.equals(node.getAction()))
		{
			// Do we have an answer?
			final ReferenceId dt = node.getDocumentColumnValueType();
			String value = fAnswerText.getText();
			if (dt.getRepoId() == DisplayType.YesNo || dt.getRepoId() == DisplayType.List)
			{
				final ValueNamePair pp = (ValueNamePair)fAnswerList.getSelectedItem();
				value = pp.getValue();
			}
			if (value == null || value.length() == 0)
			{
				ADialog.error(m_WindowNo, this, "FillMandatory", msgBL.getMsg(Env.getCtx(), "Answer"));
				trx.rollback();
				trx.close();
				return;
			}
			//
			log.info("Answer={}" + value + " - " + textMsg);
			try
			{
				m_activity.setUserChoice(userId, value, dt.getRepoId(), textMsg);
			}
			catch (final Exception e)
			{
				log.error(node.getName().getDefaultValue(), e);
				ADialog.error(m_WindowNo, this, "Error", e.toString());
				trx.rollback();
				trx.close();
				return;
			}
		}
		// User Action
		else
		{
			log.info("Action=" + node.getAction() + " - " + textMsg);
			try
			{
				// ensure activity is ran within a transaction
				m_activity.setUserConfirmation(userId, textMsg);
			}
			catch (final Exception e)
			{
				log.error(node.getName().getDefaultValue(), e);
				ADialog.error(m_WindowNo, this, "Error", e.toString());
				trx.rollback();
				trx.close();
				return;
			}

		}

		trx.commit();
		trx.close();

		// Next
		loadActivities();
		display(-1);
	}    // cmd_OK

}    // WFActivity
