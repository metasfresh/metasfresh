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

import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.security.IUserRolePermissions;
import de.metas.security.permissions.Access;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.workflow.WFNodeId;
import de.metas.workflow.WorkflowId;
import de.metas.workflow.service.IADWorkflowDAO;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.images.Images;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.service.ClientId;
import org.compiere.apps.AEnv;
import org.compiere.apps.AMenu;
import org.compiere.apps.AMenuStartItem;
import org.compiere.apps.AWindow;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.MQuery;
import org.compiere.model.X_AD_Workflow;
import org.compiere.swing.CButton;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CPanel;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.slf4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Properties;

/**
 * WorkFlow Panel
 *
 * @author Jorg Janke
 * @author Teo Sarca, www.arhipac.ro <li>FR [ 2048081 ] Mf. Workflow editor should display only mf. workflows <li>BF [ 2844102 ] Workfow Editor is displaying manufacturing routings too
 * https://sourceforge.net/tracker/?func=detail&aid=2844102&group_id=176962&atid=879332
 * @version $Id: WFPanel.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 */
public class WFPanel extends CPanel
		implements PropertyChangeListener, ActionListener, FormPanel
{

	private static final long serialVersionUID = 4478193785606693055L;

	private final IADWorkflowDAO workflowDAO = Services.get(IADWorkflowDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	/**
	 * variable used to check when we want to display only the workflow, without the navigation bar
	 */
	private boolean isSimpleWorkflowWindow = false;

	public boolean isSimpleWorkflowWindow()
	{
		return isSimpleWorkflowWindow;
	}

	public void setSimpleWorkflowWindow(boolean isSimpleWorkflowWindow)
	{
		this.isSimpleWorkflowWindow = isSimpleWorkflowWindow;
	}

	/**
	 * Workflow WhereClause : General, Document Process, Document Value
	 */
	private static final String WORKFLOW_WhereClause = "WorkflowType IN ("
			+ DB.TO_STRING(X_AD_Workflow.WORKFLOWTYPE_General)
			+ "," + DB.TO_STRING(X_AD_Workflow.WORKFLOWTYPE_DocumentProcess)
			+ "," + DB.TO_STRING(X_AD_Workflow.WORKFLOWTYPE_DocumentValue)
			+ ")";

	/**
	 * Create Workflow Panel.
	 * FormPanel
	 */
	public WFPanel()
	{
		this(null, WORKFLOW_WhereClause, -1);
	}    // WFPanel

	/**
	 * Create Workflow Panel
	 *
	 * @param menu menu
	 */
	public WFPanel(AMenu menu)
	{
		this(menu, WORKFLOW_WhereClause, -1);
	}

	/**
	 * Create Workflow Panel
	 *
	 * @param menu menu
	 */
	public WFPanel(AMenu menu, String wfWhereClause, int wfWindow_ID)
	{
		if (m_frame != null)
		{
			m_frame.setTitle(Services.get(IMsgBL.class).getMsg(Env.getCtx(), "WorkflowPanel"));
			m_frame.setIconImage(Images.getImage2("mWorkFlow"));
		}
		m_menu = menu;
		m_readWrite = (menu == null);
		m_WF_whereClause = wfWhereClause;
		m_WF_Window_ID = AdWindowId.ofRepoIdOrNull(wfWindow_ID);
		log.info("RW=" + m_readWrite);
		try
		{
			jbInit();
		}
		catch (Exception e)
		{
			log.error("WFPanel", e);
		}
	}    // WFPanel

	/**
	 * Menu.
	 * <p>
	 * NOTE: atm is needed only for {@link AMenuStartItem} which needs it to update the progress bar.
	 */
	private AMenu m_menu = null;

	public AMenu getAMenu()
	{
		return m_menu;
	}

	// /** Window No */
	// private int m_WindowNo = 0;
	/**
	 * FormFrame
	 */
	private FormFrame m_frame;

	/**
	 * Workflow Model
	 */
	private WorkflowModel workflowModel = null;
	/**
	 * Context
	 */
	private Properties m_ctx = Env.getCtx();
	/**
	 * Active Node
	 */
	private WFNodeComponent m_activeNode = null;
	/**
	 * ReadWrite Mode (see WFNode)
	 */
	private boolean m_readWrite = false;

	/**
	 * Workflows List Where Clause
	 */
	private String m_WF_whereClause = null;
	/**
	 * Workflow Window ID
	 */
	private AdWindowId m_WF_Window_ID;

	/**
	 * Logger
	 */
	private static Logger log = LogManager.getLogger(WFPanel.class);

	// UI
	private BorderLayout mainLayout = new BorderLayout();
	private CPanel southPanel = new CPanel();
	private WFContentPanel centerPanel = new WFContentPanel(this);
	private BorderLayout southLayout = new BorderLayout();
	private JScrollPane infoScrollPane = new JScrollPane();
	private JTextPane infoTextPane = new JTextPane();
	private CButton wfStartNode = new CButton();
	//
	private CPanel loadPanel = new CPanel(new FlowLayout(FlowLayout.LEADING));
	private CComboBox<KeyNamePair> workflow = new CComboBox<>();
	private CButton bResetLayout = AEnv.getButton("Reset");
	private CButton bSaveLayout = AEnv.getButton("Save");
	private CButton bZoom = AEnv.getButton("Zoom");
	private CButton bIgnore = AEnv.getButton("Ignore");

	/**
	 * Static Init
	 *
	 * <pre>
	 * 		centerScrollPane
	 * 			centerPanel
	 * 		south Panel
	 * 			infoScrollPane
	 * 			buttonPanel
	 * </pre>
	 *
	 * @throws Exception
	 */
	private void jbInit() throws Exception
	{
		this.setLayout(mainLayout);
		AdempierePLAF.setDefaultBackground(this);
		southPanel.setLayout(southLayout);
		// Center
		this.add(new JScrollPane(centerPanel), BorderLayout.CENTER);
		// Info
		infoScrollPane.getViewport().add(infoTextPane, null);
		infoScrollPane.setPreferredSize(new Dimension(200, 140));
		infoTextPane.setBackground(AdempierePLAF.getFieldBackground_Inactive());
		infoTextPane.setEditable(false);
		infoTextPane.setRequestFocusEnabled(false);
		infoTextPane.setContentType("text/html");

		wfStartNode.setIcon(Images.getImageIcon2("Zoom24"));
		wfStartNode.setEnabled(false);
		wfStartNode.setMargin(new Insets(0, 0, 0, 0));
		wfStartNode.setRequestFocusEnabled(false);
		wfStartNode.addActionListener(this);
		wfStartNode.setToolTipText(msgBL.getMsg(m_ctx, "WFStart"));
		wfStartNode.setBorder(null);
		wfStartNode.setOpaque(false);
		//
		final CPanel wfPanel = new CPanel();
		wfPanel.add(wfStartNode, null);
		// South
		this.add(southPanel, BorderLayout.SOUTH);
		southPanel.add(infoScrollPane, BorderLayout.CENTER);
		southPanel.add(wfPanel, BorderLayout.SOUTH);

	}    // jbInit

	/**
	 * Initialize Panel for FormPanel
	 *
	 * @param WindowNo window
	 * @param frame    frame
	 * @see org.compiere.apps.form.FormPanel#init(int, FormFrame)
	 */
	@Override
	public void init(int WindowNo, FormFrame frame)
	{
		// m_WindowNo = WindowNo;
		m_frame = frame;
		//
		log.debug("WindowNo=" + WindowNo);
		try
		{
			if (!isSimpleWorkflowWindow())
			{
				loadPanel();
				frame.getContentPane().add(loadPanel, BorderLayout.NORTH);
			}

			this.setPreferredSize(new Dimension(800, 700));
			frame.getContentPane().add(this, BorderLayout.CENTER);
		}
		catch (Exception e)
		{
			log.error("init", e);
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
	 * Workflow Add & Load Panel
	 */
	private void loadPanel()
	{
		String sql = Env.getUserRolePermissions().addAccessSQL(
				"SELECT AD_Workflow_ID, Name FROM AD_Workflow "
						+ (!Check.isEmpty(m_WF_whereClause, true) ? " WHERE " + m_WF_whereClause : "")
						+ " ORDER BY 2",
				"AD_Workflow", IUserRolePermissions.SQL_NOTQUALIFIED, Access.READ);    // all
		KeyNamePair[] pp = DB.getKeyNamePairs(sql, true);
		//
		workflow = new CComboBox<>(pp);
		workflow.enableAutoCompletion();
		loadPanel.add(workflow);
		workflow.addActionListener(this);
		//
		loadPanel.add(bIgnore);
		bIgnore.addActionListener(this);
		loadPanel.add(bResetLayout);
		bResetLayout.addActionListener(this);
		loadPanel.add(bSaveLayout);
		bSaveLayout.addActionListener(this);
		loadPanel.add(bZoom);
		bZoom.addActionListener(this);
	}    // loadPanel

	/**************************************************************************
	 * Load Workflow & Nodes
	 *
	 * @param readWrite if true, you can move nodes
	 */
	private void load(boolean readWrite)
	{
		KeyNamePair pp = workflow.getSelectedItem();
		if (pp == null)
		{
			return;
		}
		load(WorkflowId.ofRepoIdOrNull(pp.getKey()), readWrite);
	}    // load

	/**
	 * Load Workflow & Nodes
	 *
	 * @param workflowId ID
	 * @param readWrite  if true nodes can be moved
	 */
	public void load(WorkflowId workflowId, boolean readWrite)
	{
		log.debug("RW=" + readWrite + " - AD_Workflow_ID=" + workflowId);
		if (workflowId == null)
		{
			return;
		}

		final ClientId clientId = Env.getClientId();
		final String adLanguage = Env.getADLanguageOrBaseLanguage();

		// Get Workflow
		workflowModel = new WorkflowModel(workflowDAO.getById(workflowId));
		centerPanel.removeAll();
		centerPanel.setReadWrite(readWrite);
		if (readWrite)
		{
			centerPanel.setWorkflow(workflowModel);
		}
		// Add Nodes for Paint
		List<WorkflowNodeModel> nodes = workflowModel.getNodesInOrder(clientId);
		for (final WorkflowNodeModel node : nodes)
		{
			final WFNodeComponent wfn = new WFNodeComponent(node);
			wfn.addPropertyChangeListener(WFNodeComponent.PROPERTY_SELECTED, this);
			boolean rw = readWrite        // in editor mode & owned
					&& ClientId.equals(clientId, node.getClientId());
			centerPanel.add(wfn, rw);
			// Add Lines
			for (WorkflowNodeTransitionModel transition : node.getTransitions(clientId))
			{
				centerPanel.add(new WFLine(transition), false);
			}
		}
		// Info Text
		StringBuffer msg = new StringBuffer("<HTML>");
		msg.append("<H2>").append(workflowModel.getName().translate(adLanguage)).append("</H2>");
		String s = workflowModel.getDescription().translate(adLanguage);
		if (s != null && s.length() > 0)
		{
			msg.append("<B>").append(s).append("</B>");
		}
		s = workflowModel.getHelp().translate(adLanguage);
		if (s != null && s.length() > 0)
		{
			msg.append("<BR>").append(s);
		}
		msg.append("</HTML>");
		infoTextPane.setText(msg.toString());
		infoTextPane.setCaretPosition(0);

		wfStartNode.setEnabled(false);
		// Layout
		centerPanel.validate();
		centerPanel.repaint();
		validate();
	}    // load

	/**
	 * Property Change Listener
	 *
	 * @param e event
	 * @see java.beans.PropertyChangeListener#propertyChange(PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent e)
	{
		if (e.getNewValue() == Boolean.TRUE)
		{
			start((WFNodeComponent)e.getSource());
		}
	}    // propertyChange

	/**
	 * Action Listener
	 *
	 * @param e event
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (workflowModel == null && e.getSource() != workflow)
		{
			return;
		}

		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		try
		{
			final ClientId clientId = Env.getClientId();
			// Editing
			if (e.getSource() == bZoom)
			{
				zoom();
			}
			else if (e.getSource() == bIgnore)
			{
				load(workflowModel.getId(), true);
			}
			else if (e.getSource() == workflow)
			{
				load(true);
			}
			else if (e.getSource() == bSaveLayout)
			{
				for (WorkflowNodeModel node : workflowModel.getNodesInOrder(clientId))
				{
					if (ClientId.equals(node.getClientId(), clientId))
					{
						node.saveEx();
					}
				}
			}
			else if (e.getSource() == bResetLayout)
			{
				resetLayout();
			}
			else if (e.getSource() == wfStartNode)
			{
				if (isSimpleWorkflowWindow && m_activeNode != null)
				{
					final WorkflowNodeModel model = m_activeNode.getModel();
					AMenuStartItem.startWFNode(
							model.getWorkflowId(),
							model.unbox(),
							m_menu); // async load
				}
			}
		}
		finally
		{
			setCursor(Cursor.getDefaultCursor());
		}
	}    // actionPerformed

	/**************************************************************************
	 * Start Node
	 *
	 * @param node node
	 */
	private void start(WFNodeComponent node)
	{
		log.debug("Node=" + node);
		final String adLanguage = Env.getADLanguageOrBaseLanguage();
		WorkflowNodeModel model = node.getModel();
		// Info Text
		StringBuffer msg = new StringBuffer("<HTML>");
		msg.append("<H2>").append(model.getName().translate(adLanguage)).append("</H2>");
		String s = model.getDescription().translate(adLanguage);
		if (s != null && s.length() > 0)
		{
			msg.append("<B>").append(s).append("</B>");
		}
		s = model.getHelp().translate(adLanguage);
		if (s != null && s.length() > 0)
		{
			msg.append("<BR>").append(s);
		}
		msg.append("</HTML>");
		infoTextPane.setText(msg.toString());
		infoTextPane.setCaretPosition(0);

		wfStartNode.setEnabled(true);
		m_activeNode = node;
	}    // start


	private void resetLayout()
	{
		Point p0 = new Point(0, 0);
		for (int i = 0; i < centerPanel.getComponentCount(); i++)
		{
			Component comp = centerPanel.getComponent(i);
			comp.setLocation(p0);
		}
		centerPanel.validate();
	}    // resetLayout

	/**
	 * Zoom to WorkFlow
	 */
	private void zoom()
	{
		if (m_WF_Window_ID == null)
		{
			m_WF_Window_ID = Services.get(IADTableDAO.class).retrieveWindowId(I_AD_Workflow.Table_Name).orElse(null);
		}
		if (m_WF_Window_ID == null)
		{
			throw new AdempiereException("@NotFound@ @AD_Window_ID@");
		}

		MQuery query = null;
		if (workflowModel != null)
		{
			query = MQuery.getEqualQuery("AD_Workflow_ID", workflowModel.getId().getRepoId());
		}
		AWindow frame = new AWindow();
		if (!frame.initWindow(m_WF_Window_ID, query))
		{
			return;
		}
		AEnv.addToWindowManager(frame);
		AEnv.showCenterScreen(frame);
		frame = null;
	}    // zoom

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("WFPanel[");
		if (workflowModel != null)
		{
			sb.append(workflowModel.getId().getRepoId());
		}
		sb.append("]");
		return sb.toString();
	}    // toString
}    // WFPanel
