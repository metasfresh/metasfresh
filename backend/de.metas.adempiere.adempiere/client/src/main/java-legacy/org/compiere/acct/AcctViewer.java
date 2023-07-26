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
package org.compiere.acct;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableColumnModel;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.images.Images;
import org.adempiere.plaf.AdempierePLAF;
import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
import org.compiere.apps.search.Info;
import org.compiere.apps.search.InfoBuilder;
import org.compiere.grid.ed.VDate;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_Fact_Acct;
import org.compiere.model.MQuery;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.report.core.RModel;
import org.compiere.report.core.RModelExcelExporter;
import org.compiere.report.core.ResultTable;
import org.compiere.swing.CButton;
import org.compiere.swing.CCheckBox;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CFrame;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.CScrollPane;
import org.compiere.swing.CTabbedPane;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.KeyNamePair;
import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;

import de.metas.acct.api.AcctSchemaElement;
import de.metas.acct.api.AcctSchemaElementType;
import de.metas.acct.api.AcctSchemaElementsMap;
import de.metas.acct.api.ChartOfAccountsId;
import de.metas.acct.api.IPostingService;
import de.metas.adempiere.form.IClientUI;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 *  Account Viewer
 *
 *  @author Jorg Janke
 *  @version  $Id: AcctViewer.java,v 1.3 2006/08/10 01:00:27 jjanke Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 			BF [ 1778534 ] Info Account: can't find product
 * @author Colin Rooney (croo)
 * 			BF [ 2006668 ] Selection of Product in the Accounting Viewer
 */
public class AcctViewer extends CFrame
	implements ActionListener, ChangeListener
{
	/**
	 *
	 */
	private static final long serialVersionUID = -6160970582569467185L;

	private static final transient Insets INSETS_5_5_0_5 = new Insets(5, 5, 0, 5);
	private static final transient Insets INSETS_5_5_5_5 = new Insets(5, 5, 5, 5);
	private static final transient Insets INSETS_10_5_10_5 = new Insets(10, 5, 10, 5);
	private static final transient Insets INSETS_5_0_0_5 = new Insets(5, 0, 0, 5);

	// Services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	private RModel tableModel = null;

	/**
	 *  Default constructor
	 */
	public AcctViewer()
	{
		this(0, 0, 0);
	}   //  AcctViewer

	/**
	 *  Detail Constructor
	 *
	 *  @param AD_Client_ID Client
	 *  @param AD_Table_ID Table
	 *  @param Record_ID Record
	 */
	public AcctViewer(int AD_Client_ID, final int AD_Table_ID, final int Record_ID)
	{
		super(Services.get(IMsgBL.class).getMsg(Env.getCtx(), "AcctViewer"));

		final Properties ctx = Env.getCtx();

		log.info("AD_Table_ID=" + AD_Table_ID + ", Record_ID=" + Record_ID);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		// task 07393: find out the AD_Org_ID. we'll use that info to initially set the current document's accounting schema.
		final int AD_Org_ID;
		if (AD_Table_ID > 0 && Record_ID > 0)
		{
			final PO po = MTable.get(ctx, AD_Table_ID).getPO(Record_ID, ITrx.TRXNAME_None);
			AD_Org_ID = po.getAD_Org_ID(); // the the AD_Org_ID for out table and record
			AD_Client_ID =  po.getAD_Client_ID(); // also get the AD_Client_ID to make sure it is consistent with the org
		}
		else
		{
			AD_Org_ID = 0; // assume AD_Org_ID=0
		}

		m_data = new AcctViewerData(ctx, Env.createWindowNo(this), AD_Client_ID, AD_Org_ID, AD_Table_ID);
		AEnv.addToWindowManager(this);
		//
		try
		{
			// Enable "Display" checkboxes by default (see 07609)
			// (ony if this window is not opened from a document)
			if (AD_Table_ID <= 0 || Record_ID <= 0)
			{
				displayDocumentInfo.setSelected(true);
				displayQty.setSelected(true);
				displayEndingBalance.setSelected(true);
			}

			//
			jbInit();
			dynInit(AD_Table_ID, Record_ID);
			AEnv.showCenterScreen(this);
		}
		catch (Exception e)
		{
			log.error("", e);
			dispose();
		}
	}   //  AcctViewer

	/** State Info          */
	private AcctViewerData m_data = null;
	/** Image Icon			*/
	private final ImageIcon m_iFind = Images.getImageIcon2("Find16");

	/**	Logger			*/
	private static final Logger log = LogManager.getLogger(AcctViewer.class);

	/** @todo Display Record Info & Zoom */

	//
	private CPanel mainPanel = new CPanel();
	private CTabbedPane tabbedPane = new CTabbedPane();
	private CPanel query = new CPanel();
	private BorderLayout mainLayout = new BorderLayout();
	private CScrollPane result = new CScrollPane();
	private ResultTable table = new ResultTable();
	private CPanel southPanel = new CPanel();
	private CButton bQuery = new CButton();
	private CButton bExport = new CButton();
	private CLabel statusLine = new CLabel();
	private BorderLayout southLayout = new BorderLayout();
	private BorderLayout queryLayout = new BorderLayout();
	private CPanel selectionPanel = new CPanel();
	private CPanel displayPanel = new CPanel();
	private TitledBorder displayBorder;
	private TitledBorder selectionBorder;
	//
	private final GridBagLayout displayLayout = new GridBagLayout();
	private CCheckBox displayQty = new CCheckBox();
	private CCheckBox displaySourceAmt = new CCheckBox();
	private CCheckBox displayDocumentInfo = new CCheckBox();
	private CCheckBox displayEndingBalance = new CCheckBox();
	//
	private CLabel lSort = new CLabel();
	private CComboBox<ValueNamePair> sortBy1 = new CComboBox<>();
	private CComboBox<ValueNamePair> sortBy2 = new CComboBox<>();
	private CComboBox<ValueNamePair> sortBy3 = new CComboBox<>();
	private CComboBox<ValueNamePair> sortBy4 = new CComboBox<>();
	private CCheckBox group1 = new CCheckBox();
	private CCheckBox group2 = new CCheckBox();
	private CCheckBox group3 = new CCheckBox();
	private CCheckBox group4 = new CCheckBox();
	private CLabel lGroup = new CLabel();
	private GridBagLayout selectionLayout = new GridBagLayout();
	private CComboBox<KeyNamePair> selAcctSchema = new CComboBox<>();
	private CComboBox<ValueNamePair> selPostingType = new CComboBox<>();
	private CCheckBox selDocument = new CCheckBox();
	private CComboBox<ValueNamePair> selTable = new CComboBox<>();
	private CButton selRecord = new CButton();
	private CLabel lOrg = new CLabel();
	private CComboBox<KeyNamePair> selOrg = new CComboBox<>();
	private CLabel lAcct = new CLabel();
	private CButton selAcct = new CButton();
	private CButton selAcctTo = new CButton();
	private CLabel lDate = new CLabel();
	private CLabel lacctSchema = new CLabel();
	/**
	 * Checkbox to tell if voided/reactivated documents are to be displayed or not
	 */
	private CCheckBox displayVoidDocuments = new CCheckBox();
	private CLabel lpostingType = new CLabel();

	private VDate selDateFrom = new VDate("DateFrom", false, false, true, DisplayType.Date, Services.get(IMsgBL.class).translate(Env.getCtx(), "DateFrom"));
	private VDate selDateTo = new VDate("DateTo", false, false, true, DisplayType.Date, Services.get(IMsgBL.class).translate(Env.getCtx(), "DateTo"));

	private CLabel lsel1 = new CLabel();
	private CLabel lsel2 = new CLabel();
	private CLabel lsel3 = new CLabel();
	private CLabel lsel4 = new CLabel();
	private CLabel lsel5 = new CLabel();
	private CLabel lsel6 = new CLabel();
	private CLabel lsel7 = new CLabel();
	private CLabel lsel8 = new CLabel();

	private CButton sel1 = new CButton();
	private CButton sel2 = new CButton();
	private CButton sel3 = new CButton();
	private CButton sel4 = new CButton();
	private CButton sel5 = new CButton();
	private CButton sel6 = new CButton();
	private CButton sel7 = new CButton();
	private CButton sel8 = new CButton();

	private CButton bOpenDocument = new CButton();
	private CButton bOpenDocumentAcct = new CButton();
	private CButton bRePost = new CButton();
	private CCheckBox forcePost = new CCheckBox();

	/**
	 *  Static Init.
	 *
	 *  <pre>
	 *  - mainPanel
	 *      - tabbedPane
	 *          - query
	 *          - result
	 *          - graphPanel
	 *  </pre>
	 *
	 *  @throws Exception
	 */
	private void jbInit() throws Exception
	{
		final ImageIcon icon = Images.getImageIcon2(InfoBuilder.ACTION_InfoAccount);
		if(icon != null)
		{
			setIconImage(icon.getImage());
		}

		mainLayout.setHgap(5);
		mainLayout.setVgap(5);

		mainPanel.setLayout(mainLayout);

		selectionPanel.setLayout(selectionLayout);

		final Container contentPane = getContentPane();
		contentPane.add(mainPanel, BorderLayout.CENTER);
		mainPanel.add(tabbedPane, BorderLayout.CENTER);

		final Properties ctx = Env.getCtx();

		// Selection
		selectionBorder = new TitledBorder(BorderFactory.createEtchedBorder(
				Color.white, new Color(148, 145, 140)), msgBL.getMsg(ctx, "Selection"));
		selectionPanel.setBorder(selectionBorder);
		lacctSchema.setLabelFor(selAcctSchema);
		lacctSchema.setText(msgBL.translate(ctx, "C_AcctSchema_ID"));
		lpostingType.setLabelFor(selPostingType);
		lpostingType.setText(msgBL.translate(ctx, "PostingType"));
		selDocument.setText(msgBL.getMsg(ctx, "SelectDocument"));
		selDocument.addActionListener(this);

		lOrg.setLabelFor(selOrg);
		lOrg.setText(msgBL.translate(ctx, "AD_Org_ID"));

		lAcct.setLabelFor(selAcct);
		lAcct.setText(msgBL.translate(ctx, AcctViewerData.COLUMNNAME_Account_ID));

		lDate.setLabelFor(selDateFrom);
		lDate.setText(msgBL.translate(ctx, "DateAcct"));

		lsel1.setLabelFor(sel1);
		lsel2.setLabelFor(sel2);
		lsel3.setLabelFor(sel3);
		lsel4.setLabelFor(sel4);
		lsel5.setLabelFor(sel5);
		lsel6.setLabelFor(sel6);
		lsel7.setLabelFor(sel7);
		lsel8.setLabelFor(sel8);

		// task 09243: checkbox to display voided docs or not
		displayVoidDocuments.setText(msgBL.getMsg(ctx, "DisplayVoidDocuments"));
		// open it as selected so we have the old functionality without user intervention
		displayVoidDocuments.setSelected(true);

		// Display
		displayBorder = new TitledBorder(BorderFactory.createEtchedBorder(
				Color.white, new Color(148, 145, 140)), msgBL.getMsg(ctx, "Display"));
		displayPanel.setBorder(displayBorder);
		displayPanel.setLayout(displayLayout);

		displayQty.setText(msgBL.getMsg(ctx, "DisplayQty"));
		displaySourceAmt.setText(msgBL.getMsg(ctx, "DisplaySourceInfo"));
		displayDocumentInfo.setText(msgBL.getMsg(ctx, "DisplayDocumentInfo"));
		displayEndingBalance.setText(msgBL.getMsg(ctx, "DisplayEndingBalance"));
		lSort.setText(msgBL.getMsg(ctx, "SortBy"));
		lGroup.setText(msgBL.getMsg(ctx, "GroupBy"));

		//
		// Display checkboxes
		int displayPanelRow = 0;
		{
			displayPanel.add(displayDocumentInfo, new GridBagConstraints(0, displayPanelRow++, 2, 1, 0.0, 0.0
					, GridBagConstraints.WEST, GridBagConstraints.NONE, INSETS_5_5_0_5, 0, 0));
			displayPanel.add(displaySourceAmt, new GridBagConstraints(0, displayPanelRow++, 2, 1, 0.0, 0.0
					, GridBagConstraints.WEST, GridBagConstraints.NONE, INSETS_5_5_0_5, 0, 0));
			displayPanel.add(displayQty, new GridBagConstraints(0, displayPanelRow++, 1, 1, 0.0, 0.0
					, GridBagConstraints.WEST, GridBagConstraints.NONE, INSETS_5_5_5_5, 0, 0));
			displayPanel.add(displayEndingBalance, new GridBagConstraints(0, displayPanelRow++, 1, 1, 0.0, 0.0
					, GridBagConstraints.WEST, GridBagConstraints.NONE, INSETS_5_5_5_5, 0, 0));
		}
		//
		// Sort/Group by fields
		{
			final List<Component> sortComponents = Arrays.<Component> asList(lSort, sortBy1, sortBy2, sortBy3, sortBy4);
			final List<Component> groupByComponents = Arrays.<Component> asList(lGroup, group1, group2, group3, group4);
			for (int i = 0; i < sortComponents.size(); i++)
			{
				final Component sortComponent = sortComponents.get(i);
				final Component groupByComponent = groupByComponents.get(i);
				displayPanel.add(sortComponent, new GridBagConstraints(0, displayPanelRow, 1, 1, 0.0, 0.0
						, GridBagConstraints.WEST, GridBagConstraints.NONE, INSETS_5_5_0_5, 0, 0));
				displayPanel.add(groupByComponent, new GridBagConstraints(1, displayPanelRow, 1, 1, 0.0, 0.0
						, GridBagConstraints.WEST, GridBagConstraints.NONE, INSETS_5_5_0_5, 0, 0));
				displayPanelRow++;
			}
		}

		//
		// Acct Schema dropdown
		selectionPanel.add(lacctSchema, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, INSETS_5_5_5_5, 0, 0));
		selectionPanel.add(selAcctSchema, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, INSETS_5_5_5_5, 0, 0));

		//
		// Document, Table/Record
		selectionPanel.add(selDocument, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, INSETS_10_5_10_5, 0, 0));
		selectionPanel.add(selTable, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, INSETS_10_5_10_5, 0, 0));
		selectionPanel.add(selRecord, new GridBagConstraints(2, 1, 2, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, INSETS_10_5_10_5, 0, 0));
		//
		// Posting Type
		selectionPanel.add(lpostingType, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, INSETS_5_5_0_5, 0, 0));
		selectionPanel.add(selPostingType, new GridBagConstraints(1, 2, 2, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, INSETS_5_5_0_5, 0, 0));
		//
		// Date From/To
		selectionPanel.add(lDate, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, INSETS_5_5_0_5, 0, 0));
		selectionPanel.add(selDateFrom, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
				, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, INSETS_5_5_0_5, 0, 0));
		selectionPanel.add(selDateTo, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
				, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, INSETS_5_5_0_5, 0, 0));
		//
		// Organization
		selectionPanel.add(lOrg, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, INSETS_5_0_0_5, 0, 0));
		selectionPanel.add(selOrg, new GridBagConstraints(1, 4, 2, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, INSETS_5_5_0_5, 0, 0));
		//
		// Account (From, To)
		selectionPanel.add(lAcct, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, INSETS_5_5_0_5, 0, 0));
		selectionPanel.add(selAcct, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, INSETS_5_5_0_5, 0, 0));
		selectionPanel.add(selAcctTo, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, INSETS_5_5_0_5, 0, 0));
		//
		selectionPanel.add(lsel1, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, INSETS_5_5_0_5, 0, 0));
		selectionPanel.add(lsel2, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, INSETS_5_5_0_5, 0, 0));
		selectionPanel.add(lsel3, new GridBagConstraints(0, 8, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, INSETS_5_5_0_5, 0, 0));
		selectionPanel.add(sel1, new GridBagConstraints(1, 6, 2, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, INSETS_5_5_0_5, 0, 0));
		selectionPanel.add(sel2, new GridBagConstraints(1, 7, 2, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, INSETS_5_5_0_5, 0, 0));
		selectionPanel.add(sel3, new GridBagConstraints(1, 8, 2, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, INSETS_5_5_0_5, 0, 0));
		selectionPanel.add(lsel4, new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, INSETS_5_5_0_5, 0, 0));
		selectionPanel.add(sel4, new GridBagConstraints(1, 9, 2, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, INSETS_5_5_0_5, 0, 0));
		selectionPanel.add(lsel5, new GridBagConstraints(0, 10, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, INSETS_5_5_0_5, 0, 0));
		selectionPanel.add(sel5, new GridBagConstraints(1, 10, 2, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, INSETS_5_5_0_5, 0, 0));
		selectionPanel.add(lsel6, new GridBagConstraints(0, 11, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, INSETS_5_5_0_5, 0, 0));
		selectionPanel.add(sel6, new GridBagConstraints(1, 11, 2, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, INSETS_5_5_0_5, 0, 0));
		selectionPanel.add(lsel7, new GridBagConstraints(0, 12, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, INSETS_5_5_0_5, 0, 0));
		selectionPanel.add(sel7, new GridBagConstraints(1, 12, 2, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, INSETS_5_5_0_5, 0, 0));
		selectionPanel.add(lsel8, new GridBagConstraints(0, 13, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, INSETS_5_5_0_5, 0, 0));
		selectionPanel.add(sel8, new GridBagConstraints(1, 13, 2, 1, 0.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, INSETS_5_5_0_5, 0, 0));

		selectionPanel.add(displayVoidDocuments, new GridBagConstraints(0, 14, 1, 1, 0.0, 0.0
				, GridBagConstraints.EAST, GridBagConstraints.NONE, INSETS_5_5_0_5, 0, 0));

		queryLayout.setHgap(5);
		queryLayout.setVgap(5);

		query.setLayout(queryLayout);
		query.add(selectionPanel, BorderLayout.CENTER);
		query.add(displayPanel, BorderLayout.EAST);

		tabbedPane.add(query, msgBL.getMsg(ctx, "ViewerQuery"));
		tabbedPane.add(result, msgBL.getMsg(ctx, "ViewerResult"));
		// tabbedPane.add(graphPanel, Msg.getMsg(ctx, "ViewerGraph"));
		tabbedPane.addChangeListener(this);

		result.getViewport().add(table, null);

		//
		// Add selection listener
		table.getSelectionModel().addListSelectionListener(e -> enableDisableOpenDocument());

		//
		// South
		southLayout.setHgap(5);
		southLayout.setVgap(5);
		southPanel.setLayout(southLayout);

		statusLine.setForeground(AdempierePLAF.getColor("StatusLine.foreground", Color.BLACK));
		statusLine.setBorder(BorderFactory.createLoweredBevelBorder());
		southPanel.add(statusLine, BorderLayout.CENTER);

		//
		// Left-side panel
		final CPanel leftSide = new CPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
		southPanel.add(leftSide, BorderLayout.WEST);

		//
		// Open Document Button
		bOpenDocument.setIcon(Images.getImageIcon2("ZoomAcross16"));
		bOpenDocument.setToolTipText(msgBL.getMsg(ctx, "Zoom"));
		bOpenDocument.addActionListener(this);
		bOpenDocument.setVisible(false);
		leftSide.add(bOpenDocument);

		//
		// Open Document Accounting Button
		bOpenDocumentAcct.setIcon(Images.getImageIcon2("Zoom16"));
		bOpenDocumentAcct.setToolTipText("Verbucht"); // TODO HARDCODED search for actual ref value?
		bOpenDocumentAcct.addActionListener(this);
		bOpenDocumentAcct.setVisible(false);
		leftSide.add(bOpenDocumentAcct);

		//
		// Re-post Button
		bRePost.setText(msgBL.getMsg(ctx, "RePost"));
		bRePost.setToolTipText(msgBL.getMsg(ctx, "RePostInfo"));
		bRePost.addActionListener(this);
		bRePost.setVisible(true);
		leftSide.add(bRePost);

		//
		// Force Re-post
		forcePost.setText(msgBL.getMsg(ctx, "Force"));
		forcePost.setToolTipText(msgBL.getMsg(ctx, "ForceInfo"));
		forcePost.setVisible(false);
		leftSide.add(forcePost);

		//
		// Right-side panel
		final CPanel rightSide = new CPanel(new FlowLayout(FlowLayout.TRAILING, 0, 0));
		southPanel.add(rightSide, BorderLayout.EAST);

		//
		// Export Button
		bExport.setIcon(Images.getImageIcon2("Export16"));
		bExport.setToolTipText(msgBL.getMsg(ctx, "Export"));
		bExport.setVisible(tabbedPane.getSelectedIndex() == 1);
		bExport.addActionListener(this);
		rightSide.add(bExport);

		bQuery.setIcon(Images.getImageIcon2("Refresh16"));
		bQuery.setToolTipText(msgBL.getMsg(ctx, "Refresh"));
		bQuery.addActionListener(this);
		rightSide.add(bQuery);

		//
		// Bind panel
		contentPane.add(southPanel, BorderLayout.SOUTH);
	}

	/**
	 *  Dynamic Init
	 *
	 *  @param AD_Table_ID table
	 *  @param Record_ID record
	 */
	private void dynInit(int AD_Table_ID, int Record_ID)
	{
		m_data.fillAcctSchema(selAcctSchema);
		selAcctSchema.addActionListener(this);
		actionAcctSchema();
		//
		m_data.fillTable(selTable);
		selTable.addActionListener(this);
		selRecord.setIcon(m_iFind);
		selRecord.addActionListener(this);
		selRecord.setText("");
		//
		m_data.fillPostingType(selPostingType);

		//  Mandatory Elements
		m_data.fillOrg(selOrg);
		// Account (from)
		selAcct.setActionCommand(AcctViewerData.COLUMNNAME_Account_ID);
		selAcct.addActionListener(this);
		selAcct.setText("");
		selAcct.setIcon(m_iFind);
		// Account (to)
		if (selAcctTo != null)
		{
			selAcctTo.setActionCommand(AcctViewerData.COLUMNNAME_Account_ID);
			selAcctTo.addActionListener(this);
			selAcctTo.setText("");
			selAcctTo.setIcon(m_iFind);
		}

		//  Document Select
		boolean haveDoc = AD_Table_ID != 0 && Record_ID != 0;
		selDocument.setSelected(haveDoc);
		actionDocument();
		actionTable();
		statusLine.setText(" " + msgBL.getMsg(Env.getCtx(), "ViewerOptions"));

		//  Initial Query
		if (haveDoc)
		{
			m_data.setAD_Table_ID(AD_Table_ID);
			m_data.setRecord_ID(Record_ID);
			actionQuery();
		}
	}   //  dynInit

	/**
	 *  Dispose
	 */
	@Override
	public void dispose()
	{
		if (m_data != null)
		{
			m_data.dispose();
			m_data = null;
		}

		super.dispose();
	}   //  dispose;

	/**
	 * Tab Changed
	 *
	 * @param e ChangeEvent
	 */
	@Override
	public void stateChanged(ChangeEvent e)
	{
		// log.info( "AcctViewer.stateChanged");

		enableDisableOpenDocument();

		final boolean isPost = m_data.isDocumentQuery()
				&& tabbedPane.getSelectedIndex() == 1
				&& Services.get(IPostingService.class).isEnabled();
		bRePost.setVisible(isPost);
		if (Ini.isPropertyBool(Ini.P_SHOW_ADVANCED))
		{
			forcePost.setVisible(isPost);
		}

		bExport.setVisible(tabbedPane.getSelectedIndex() == 1);
	}

	/**
	 * Action Performed (Action Listener)
	 *
	 * @param e ActionEvent
	 */
	@Override
	public void actionPerformed(final ActionEvent e)
	{
		// log.info(e.getActionCommand());

		final Object source = e.getSource();
		if (source == selAcctSchema)
		{
			actionAcctSchema();
		}
		else if (source == bQuery)
		{
			actionQuery();
		}
		else if (source == selDocument)
		{
			actionDocument();
		}
		else if (source == selTable)
		{
			actionTable();
		}
		else if (source == bOpenDocument)
		{
			actionOpenDocument();
		}
		else if (source == bOpenDocumentAcct)
		{
			actionOpenDocumentAcct();
		}
		else if (source == bRePost)
		{
			actionRePost();
		}
		else if (source == bExport)
		{
			exportExcel();
		}
		else if (source == displayVoidDocuments)
		{
			// nothing to do
		}
		else if (source instanceof CButton)
		{
			// InfoButtons
			actionButton((CButton)source);
		}

		enableDisableOpenDocument();
	}

	private final void enableDisableOpenDocument()
	{
		final I_Fact_Acct selectedFactAcct = getSelectedFactAcctOrNull();

		final boolean hasLinesSelected = table.getSelectedRowCount() == 1 // we have one and only one selected row
				&& (selectedFactAcct != null); // the row is not one for which we don't have an I_Fact_Acct (summary row)
		final boolean isDocumentSelected = hasLinesSelected && tabbedPane.getSelectedIndex() == 1;
		bOpenDocument.setVisible(isDocumentSelected);

		final boolean isRecordAlreadySelected;
		if (selectedFactAcct != null)
		{
			final int selectedTableId = selectedFactAcct.getAD_Table_ID();
			final int selectedRecordId = selectedFactAcct.getRecord_ID();

			isRecordAlreadySelected = selectedTableId == m_data.getAD_Table_ID()
					&& selectedRecordId == m_data.getRecord_ID();
		}
		else
		{
			isRecordAlreadySelected = false;
		}
		bOpenDocumentAcct.setVisible(isDocumentSelected && !isRecordAlreadySelected);
	}

	/**
	 * 	New Acct Schema
	 */
	private void actionAcctSchema()
	{
		final KeyNamePair kp = selAcctSchema.getSelectedItem();
		if (kp == null)
		{
			return;
		}
		m_data.setAcctSchema(kp);
		//
		//  Sort Options
		sortBy1.removeAllItems();
		sortBy2.removeAllItems();
		sortBy3.removeAllItems();
		sortBy4.removeAllItems();
		sortAddItem(ValueNamePair.EMPTY);
		sortAddItem(ValueNamePair.of("DateAcct", msgBL.translate(Env.getCtx(), "DateAcct")));
		sortAddItem(ValueNamePair.of("DateTrx", msgBL.translate(Env.getCtx(), "DateTrx")));
		sortAddItem(ValueNamePair.of("C_Period_ID", msgBL.translate(Env.getCtx(), "C_Period_ID")));
		//
		CLabel[] labels = new CLabel[] { lsel1, lsel2, lsel3, lsel4, lsel5, lsel6, lsel7, lsel8 };
		CButton[] buttons = new CButton[] { sel1, sel2, sel3, sel4, sel5, sel6, sel7, sel8 };
		int selectionIndex = 0;
		
		for (final AcctSchemaElement ase : m_data.getAcctSchema().getSchemaElements())
		{
			final String columnName = ase.getColumnName();
			final String displayColumnName = ase.getDisplayColumnName();
			final String displayColumnNameTrl = msgBL.translate(Env.getCtx(), displayColumnName);

			//  Add Sort Option
			sortAddItem(ValueNamePair.of(columnName, displayColumnNameTrl));

			//  Additional Elements
			if (ase.getElementType() != AcctSchemaElementType.Organization
					&& ase.getElementType() != AcctSchemaElementType.Account)
			{
				labels[selectionIndex].setText(displayColumnNameTrl);
				labels[selectionIndex].setVisible(true);
				buttons[selectionIndex].setActionCommand(columnName);
				buttons[selectionIndex].addActionListener(this);
				buttons[selectionIndex].setIcon(m_iFind);
				buttons[selectionIndex].setText("");
				buttons[selectionIndex].setVisible(true);
				selectionIndex++;
			}
		}
		//	don't show remaining
		while (selectionIndex < labels.length)
		{
			labels[selectionIndex].setVisible(false);
			buttons[selectionIndex++].setVisible(false);
		}
	}	//	actionAcctSchema

	/**
	 * 	Add to Sort
	 *
	 *	@param vn name pair
	 */
	private void sortAddItem(ValueNamePair vn)
	{
		sortBy1.addItem(vn);
		sortBy2.addItem(vn);
		sortBy3.addItem(vn);
		sortBy4.addItem(vn);
	}	//	sortAddItem

	/**
	 *  Query
	 */
	private void actionQuery()
	{
		//  Parameter Info
		final StringBuilder parametersInfo = new StringBuilder();
		//  Reset Selection Data
		m_data.setAcctSchema((KeyNamePair)null);
		m_data.setAD_Org_ID(0);

		//  Save Selection Choices

		// Accounting Schema
		{
			final KeyNamePair kp = selAcctSchema.getSelectedItem();
			m_data.setAcctSchema(kp);
			parametersInfo.append("C_AcctSchema_ID=").append(m_data.getAcctSchema());
		}
		// Posting Type
		{
			final ValueNamePair vp = selPostingType.getSelectedItem();
			m_data.setPostingType(vp.getValue());
			parametersInfo.append(", PostingType=").append(m_data.getPostingType());
		}

		//  Document
		m_data.setDocumentQuery(selDocument.isSelected());
		parametersInfo.append(", DocumentQuery=").append(m_data.isDocumentQuery());
		if (m_data.isDocumentQuery())
		{
			final int adTableId = m_data.getAD_Table_ID();
			final int recordId = m_data.getRecord_ID();
			if (adTableId <= 0 || recordId <= 0)
			{
				return;
			}
			parametersInfo.append(", AD_Table_ID=").append(adTableId)
				.append(", Record_ID=").append(recordId);
		}
		else
		{
			m_data.setDateFrom(selDateFrom.getValue());
			parametersInfo.append(", DateFrom=").append(m_data.getDateFrom());
			m_data.setDateTo(selDateTo.getValue());
			parametersInfo.append(", DateTo=").append(m_data.getDateTo());
			//
			final KeyNamePair selOrgKNP = selOrg.getSelectedItem();
			if (selOrgKNP != null)
			{
				m_data.setAD_Org_ID(selOrgKNP.getKey());
			}
			parametersInfo.append(", AD_Org_ID=").append(m_data.getAD_Org_ID());
			//
			parametersInfo.append(m_data.getAdditionalWhereClauseInfo());
		}

		//
		// Account From/To
		{
			final int accountId = getButtonSelectedId(selAcct);
			m_data.setAccount_ID(accountId);

			final int accountToId = getButtonSelectedId(selAcctTo);
			m_data.setAccountTo_ID(accountToId);
		}

		// task 09243: display void/reversed docs
		final boolean isDisplayVoidDocuments = displayVoidDocuments.isSelected();

		m_data.setDisplayVoidDocuments(isDisplayVoidDocuments);

		//  Save Display Choices
		m_data.displayQty = displayQty.isSelected();
		parametersInfo.append(" - Display Qty=").append(m_data.displayQty);
		m_data.displaySourceAmt = displaySourceAmt.isSelected();
		parametersInfo.append(", Source=").append(m_data.displaySourceAmt);
		m_data.displayDocumentInfo = displayDocumentInfo.isSelected();
		parametersInfo.append(", Doc=").append(m_data.displayDocumentInfo);
		m_data.setDisplayEndingBalance(displayEndingBalance.isSelected());
		parametersInfo.append(", EndingBalance=").append(m_data.isDisplayEndingBalance());
		
		//
		m_data.sortBy1 = (sortBy1.getSelectedItem()).getValue();
		m_data.group1 = group1.isSelected();
		parametersInfo.append(" - Sorting: ").append(m_data.sortBy1).append("/").append(m_data.group1);
		m_data.sortBy2 = (sortBy2.getSelectedItem()).getValue();
		m_data.group2 = group2.isSelected();
		parametersInfo.append(", ").append(m_data.sortBy2).append("/").append(m_data.group2);
		m_data.sortBy3 = (sortBy3.getSelectedItem()).getValue();
		m_data.group3 = group3.isSelected();
		parametersInfo.append(", ").append(m_data.sortBy3).append("/").append(m_data.group3);
		m_data.sortBy4 = (sortBy4.getSelectedItem()).getValue();
		m_data.group4 = group4.isSelected();
		parametersInfo.append(", ").append(m_data.sortBy4).append("/").append(m_data.group4);

		final Properties ctx = Env.getCtx();

		bQuery.setEnabled(false);
		try
		{
			statusLine.setText(" " + msgBL.getMsg(ctx, "Processing"));

			log.info("Parameters: {}", parametersInfo);
			Thread.yield();

			// Switch to Result pane
			tabbedPane.setSelectedIndex(1);

			// Set TableModel with Query
			tableModel = m_data.query();
			table.setModel(tableModel);

			//
			// Do not display Fact_Acct_ID column
			final TableColumnModel tcm = table.getColumnModel();
			tcm.removeColumn(tcm.getColumn(0));
		}
		finally
		{
			bQuery.setEnabled(true);
			statusLine.setText(" " + msgBL.getMsg(ctx, "ViewerOptions"));
		}
	}   // actionQuery

	/**
	 *  Document selection
	 */
	private void actionDocument()
	{
		boolean doc = selDocument.isSelected();
		selTable.setEnabled(doc);
		selRecord.setEnabled(doc);
		//
		selDateFrom.setReadWrite(!doc);
		selDateTo.setReadWrite(!doc);
		selOrg.setEnabled(!doc);
		selAcct.setEnabled(!doc);
		selAcctTo.setEnabled(!doc);
		sel1.setEnabled(!doc);
		sel2.setEnabled(!doc);
		sel3.setEnabled(!doc);
		sel4.setEnabled(!doc);
		sel5.setEnabled(!doc);
		sel6.setEnabled(!doc);
		sel7.setEnabled(!doc);
		sel8.setEnabled(!doc);
	}   //  actionDocument

	/**
	 *  Save Table selection (reset Record selection)
	 */
	private void actionTable()
	{
		final ValueNamePair vp = selTable.getSelectedItem();
		final String tableName = vp.getValue();
		m_data.setAD_Table_ID(tableName);
		log.info(tableName + " = " + m_data.getAD_Table_ID());
		//  Reset Record
		m_data.setRecord_ID(0);
		selRecord.setText("");

		final String keyColumnName = tableName + "_ID";
		selRecord.setActionCommand(keyColumnName);
	}   //  actionTable

	/**
	 * @return selected {@link I_Fact_Acct} if table was queried and if there is an actual selection
	 */
	private I_Fact_Acct getSelectedFactAcctOrNull()
	{
		if (tableModel == null)
		{
			return null;
		}

		final int selectedRow = table.getSelectedRow();
		if (selectedRow < 0)
		{
			return null;
		}

		final int factAcctIdColumnIndex = tableModel.getColumnIndex(I_Fact_Acct.COLUMNNAME_Fact_Acct_ID);
		if (factAcctIdColumnIndex < 0)
		{
			// log.warn("@NotFound@ @" + I_Fact_Acct.COLUMNNAME_Fact_Acct_ID + "@");
			return null;
		}

		final KeyNamePair factAcctKnp = (KeyNamePair)tableModel.getValueAt(selectedRow, factAcctIdColumnIndex);
		if (factAcctKnp == null)
		{
			return null;
		}
		final int factAcctId = factAcctKnp.getKey();

		// Retrieve the Fact_Acct record.
		// NOTE: we query for it because in case it was deleted (user re-posted the document in another window), we don't want to get an error here.
		final I_Fact_Acct factAcct = Services.get(IQueryBL.class)
				.createQueryBuilder(I_Fact_Acct.class, Env.getCtx(), ITrx.TRXNAME_None)
				.addEqualsFilter(I_Fact_Acct.COLUMN_Fact_Acct_ID, factAcctId)
				.create()
				.firstOnly(I_Fact_Acct.class);
		return factAcct;
	}

	/**
	 * Open document of selected line for it's combination if Table ID and Record ID
	 */
	private void actionOpenDocument()
	{
		final I_Fact_Acct factAcct = getSelectedFactAcctOrNull();
		if (factAcct == null)
		{
			final AdempiereException ex = new AdempiereException("Could not retrieve fact account for given selection");
			ADialog.error(0, this, ex);
			return;
		}

		final int adTableId = factAcct.getAD_Table_ID();
		final int recordId = factAcct.getRecord_ID();
		AEnv.zoom(adTableId, recordId);
	}

	/**
	 * Open document accounting of selected line for it's combination if Table ID and Record ID
	 */
	private void actionOpenDocumentAcct()
	{
		final I_Fact_Acct factAcct = getSelectedFactAcctOrNull();
		if (factAcct == null)
		{
			final AdempiereException ex = new AdempiereException("Could not retrieve fact account for given selection");
			ADialog.error(0, this, ex);
			return;
		}

		final int adTableId = factAcct.getAD_Table_ID();
		final int recordId = factAcct.getRecord_ID();

		//
		// Open new AcctViewer for selected entry
		new AcctViewer(Env.getAD_Client_ID(Env.getCtx()), adTableId, recordId);
	}

	/**
	 *  Action Button
	 *
	 *  @param button pressed button
	 *  @return ID
	 */
	private int actionButton(final CButton button)
	{
		final AcctSchemaElementsMap acctSchemaElements = m_data.getAcctSchema().getSchemaElements();
		
		final String keyColumn = button.getActionCommand();
		log.info(keyColumn);

		String whereClause = "(IsSummary='N' OR IsSummary IS NULL)";
		String lookupColumn = keyColumn;
		if (Check.isEmpty(keyColumn, true))
		{
			// guard againt null (shall not happen)
			setButtonSelectedId(button, 0, "");
			return 0;
		}
		else if (keyColumn.equals(AcctViewerData.COLUMNNAME_Account_ID))
		{
			lookupColumn = I_C_ElementValue.COLUMNNAME_C_ElementValue_ID;
			final AcctSchemaElement ase = acctSchemaElements.getByElementType(AcctSchemaElementType.Account);
			if (ase != null)
			{
				whereClause += " AND " + I_C_ElementValue.COLUMNNAME_C_Element_ID + "=" + ChartOfAccountsId.toRepoId(ase.getChartOfAccountsId());
			}
		}
		else if (keyColumn.equals("User1_ID"))
		{
			lookupColumn = I_C_ElementValue.COLUMNNAME_C_ElementValue_ID;
			final AcctSchemaElement ase = acctSchemaElements.getByElementType(AcctSchemaElementType.UserList1);
			if (ase != null)
			{
				whereClause += " AND " + I_C_ElementValue.COLUMNNAME_C_Element_ID + "=" + ChartOfAccountsId.toRepoId(ase.getChartOfAccountsId());
			}
		}
		else if (keyColumn.equals("User2_ID"))
		{
			lookupColumn = I_C_ElementValue.COLUMNNAME_C_ElementValue_ID;
			final AcctSchemaElement ase = acctSchemaElements.getByElementType(AcctSchemaElementType.UserList2);
			if (ase != null)
			{
				whereClause += " AND " + I_C_ElementValue.COLUMNNAME_C_Element_ID + "=" + ChartOfAccountsId.toRepoId(ase.getChartOfAccountsId());
			}
		}
		else if (keyColumn.equals("M_Product_ID"))
		{
			whereClause = "";
		}
		else if (selDocument.isSelected())
		{
			whereClause = "";
		}

		final String tableName = MQuery.getZoomTableName(lookupColumn);
		Info info = InfoBuilder.newBuilder()
				.setParentFrame(this)
				.setWindowNo(m_data.getWindowNo())
				.setModal(true)
				.setTableName(tableName)
				.setKeyColumn(lookupColumn)
				.setWhereClause(whereClause)
				.build();
		if (!info.loadedOK())
		{
			info.dispose();
			info = null;
			setButtonSelectedId(button, 0, "");
			m_data.resetAdditionalWhereClause(keyColumn);
			return 0;
		}

		// Show model Info panel and wait for it's close
		// info.setVisible(true); // task: this has no effect. need to call info.showWindow() instead (thx teo)
		info.showWindow();

		final String selectSQL = info.getSelectedSQL();       //  C_Project_ID=100 or ""
		final Integer key = (Integer)info.getSelectedKey();
		info = null;
		if (selectSQL == null || selectSQL.length() == 0 || key == null)
		{
			setButtonSelectedId(button, 0, "");
			m_data.resetAdditionalWhereClause(keyColumn);
			return 0;
		}

		//  Save for query
		log.info(keyColumn + " - " + key);
		if (button == selRecord)                            //  Record_ID
		{
			m_data.setRecord_ID(key);
		}
		else
		{
			m_data.setAdditionalWhereClause(keyColumn, key);
		}

		//  Display Selection and resize
		final String buttonText = m_data.getButtonText(tableName, lookupColumn, selectSQL);
		setButtonSelectedId(button, key, buttonText);

		pack();

		return key;
	}   //  actionButton

	/**
	 *  RePost Record
	 */
	private void actionRePost()
	{
		final int windowNo = m_data.getWindowNo();
		final int adClientId = m_data.getAD_Client_ID();
		final int adTableId = m_data.getAD_Table_ID();
		final int recordId = m_data.getRecord_ID();
		final boolean force = forcePost.isSelected();

		if (m_data.isDocumentQuery()
			&& adTableId > 0 && recordId > 0
			&& Services.get(IClientUI.class).ask(windowNo, "PostImmediate?"))
		{
			AEnv.postImmediate(windowNo, adClientId, adTableId, recordId, force);

			actionQuery();
		}
	}   //  actionRePost

	/**
	 * Export to Excel
	 */
	private void exportExcel()
	{
		RModel model = table.getRModel();
		if (model == null)
		{
			return;
		}
		try
		{
			final RModelExcelExporter exporter = RModelExcelExporter.builder()
					.model(model)
					.build();
			final File file = exporter.exportToTempFile();
			Env.startBrowser(file);
		}
		catch (Exception e)
		{
			ADialog.error(0, this, "Error", e.getLocalizedMessage());
			if (LogManager.isLevelFinest())
			{
				e.printStackTrace();
			}
		}
	}

	private static final String PROPERTY_ButtonSelectedId = AcctViewer.class.getName() + "#SelectedId";

	private void setButtonSelectedId(final CButton button, final int selectedId, final String text)
	{
		button.setText(text);
		button.putClientProperty(PROPERTY_ButtonSelectedId, selectedId);
	}

	private int getButtonSelectedId(final CButton button)
	{
		final Integer selectedId = (Integer)button.getClientProperty(PROPERTY_ButtonSelectedId);
		if (selectedId == null)
		{
			return 0;
		}
		return selectedId.intValue();
	}
}   //  AcctViewer
