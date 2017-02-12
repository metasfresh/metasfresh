package de.metas.callcenter.form;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.Timer;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.images.Images;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.plaf.AdempierePLAF;
import org.compiere.apps.AEnv;
import org.compiere.apps.AWindow;
import org.compiere.apps.AWindowListener;
import org.compiere.apps.StatusBar;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.grid.GridController;
import org.compiere.grid.VTable;
import org.compiere.grid.ed.VLookup;
import org.compiere.grid.ed.VText;
import org.compiere.model.GridTab;
import org.compiere.model.GridTab.DataNewCopyMode;
import org.compiere.model.MQuery;
import org.compiere.model.MSysConfig;
import org.compiere.swing.CButton;
import org.compiere.swing.CCheckBox;
import org.compiere.swing.CLabel;
import org.compiere.swing.CMenuItem;
import org.compiere.swing.CPanel;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.jdesktop.swingx.JXTaskPane;
import org.jdesktop.swingx.JXTaskPaneContainer;
import org.slf4j.Logger;

import de.metas.callcenter.model.I_RV_R_Group_Prospect;
import de.metas.callcenter.model.I_R_Request;
import de.metas.logging.LogManager;

/**
 *
 * @author Teo Sarca, teo.sarca@gmail.com
 */
public class CallCenterForm
		implements FormPanel, VetoableChangeListener, ActionListener, WindowStateListener, PropertyChangeListener
{
	public static final String SYSCONFIG_CheckInterval = "de.metas.callcenter.CallCenterForm.CheckInterval";

	private static final Logger log = LogManager.getLogger(CallCenterForm.class);

	private static CallCenterForm s_instance = null;

	private CallCenterModel m_model = null;
	private FormFrame m_frame = null;
	private VLookup fBundles = null;
	private CCheckBox fShowFromAllBundles = null;
	final private CLabel lBundleInfo = new CLabel("                                                                                ");
	private CCheckBox fShowOnlyDue = null;
	private CCheckBox fShowOnlyOpen = null;
	private CButton butCall = null;
	final private CButton butRefresh = new CButton();
	private CButton butUnlock = null;
	final private VText fUserComments = new VText(I_RV_R_Group_Prospect.COLUMNNAME_Comments, false, true, false, 30, 30);
	/** Map AD_Table_ID -> VTable */
	private Map<Integer, VTable> m_mapVTables = new HashMap<Integer, VTable>();
	final private CPanel panelContactControl = new CPanel();
	final private StatusBar m_statusBar = new StatusBar();
	private Window m_requestFrame = null;

	private Timer m_updater = null;

	// @Override
	@Override
	public void init(int WindowNo, FormFrame frame)
	{
		if (s_instance != null)
		{
			AEnv.showWindow(s_instance.m_frame);
			throw new AdempiereException("@de.metas.callcenter.FrontPanelAlreadyOpened@");
		}
		//
		int checkIntervalSec = MSysConfig.getIntValue(SYSCONFIG_CheckInterval, 60, Env.getAD_Client_ID(Env.getCtx()));
		m_updater = new Timer(checkIntervalSec * 1000, new ActionListener()
		{
			// @Override
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					refreshAll(false);
				}
				catch (Exception ex1)
				{
					log.error("Error", ex1);
				}
			}
		});

		//
		m_frame = frame;
		m_model = new CallCenterModel(Env.getCtx(), WindowNo);
		m_frame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowActivated(WindowEvent e)
			{
				if (!m_frame.isEnabled() && e.getSource() == m_frame && m_requestFrame != null)
				{
					AEnv.showWindow(m_requestFrame);
				}
			}

			@Override
			public void windowClosed(WindowEvent e)
			{
				dispose();
			}

		});
		m_frame.setIconImage(getImage("callcenter.png"));
		s_instance = this;
		//
		m_updater.setInitialDelay(0);
		m_updater.setRepeats(true);
		m_updater.setCoalesce(true);
		//
		dynInit();
	}

	// @Override
	@Override
	public void dispose()
	{
		if (m_updater != null)
			m_updater.stop();
		m_updater = null;
		//
		if (s_instance == this)
			s_instance = null;
		m_frame = null;
		m_model = null;
	}

	private void dynInit()
	{
		fBundles = new VLookup("R_Group_ID", true, false, true, m_model.getBundlesLookup());
		// fBundles.setCustomZoomWindow_ID(Env.getContextAsInt(Env.getCtx(), CallCenterValidator.PROP_Bundles_Window_ID));
		fBundles.addVetoableChangeListener(this);
		fShowFromAllBundles = new CCheckBox(Msg.translate(Env.getCtx(), "de.metas.callcenter.ShowFromAllBundles"), false);
		fShowFromAllBundles.addActionListener(this);
		// m_requestTable = new MiniTable();
		fShowOnlyDue = new CCheckBox(Msg.translate(Env.getCtx(), "de.metas.callcenter.ShowOnlyDue"), true);
		fShowOnlyDue.addActionListener(this);
		fShowOnlyOpen = new CCheckBox(Msg.translate(Env.getCtx(), "de.metas.callcenter.ShowOnlyOpen"), true);
		fShowOnlyOpen.addActionListener(this);

		m_frame.setLayout(new BorderLayout());

		//
		// Center Panel - contains Top Panel and Main Panel
		final CPanel centerPanel = new CPanel();
		centerPanel.setLayout(new BorderLayout());
		m_frame.add(centerPanel, BorderLayout.CENTER);

		//
		// Top Panel
		final CPanel topPanel = new CPanel();
		centerPanel.add(topPanel, BorderLayout.NORTH);
		fBundles.setPreferredSize(new Dimension(200, 25));
		adjustFont(fBundles, Font.BOLD, 16);
		lBundleInfo.setPreferredSize(new Dimension(200, 25));
		adjustFont(lBundleInfo, Font.BOLD, 0);
		topPanel.setLayout(new GridBagLayout());
		topPanel.add(new CLabel(Msg.translate(Env.getCtx(), "de.metas.callcenter.Bundle")),
				new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
		topPanel.add(fBundles,
				new GridBagConstraints(1, 0, 2, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0), 0, 0));
		topPanel.add(lBundleInfo,
				new GridBagConstraints(1, 1, 2, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 0, 0, 0), 0, 0));
		// Refresh button
		// butRefresh.setText(Msg.translate(Env.getCtx(), "Refresh"));
		butRefresh.setPreferredSize(new Dimension(50, 50));
		butRefresh.setIcon(Images.getImageIcon2("Refresh24"));
		butRefresh.addActionListener(this);
		butRefresh.setEnabled(false);
		topPanel.add(butRefresh,
				new GridBagConstraints(4, 0, 2, 2, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 0), 0, 0));
		// Check boxes
		topPanel.add(fShowFromAllBundles,
				new GridBagConstraints(1, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
		topPanel.add(fShowOnlyDue,
				new GridBagConstraints(1, 3, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
		topPanel.add(fShowOnlyOpen,
				new GridBagConstraints(1, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));
		//
		panelContactControl.setLayout(new GridLayout(1, 2));
		butCall = addContactControlButton("de.metas.callcenter.CallButton", "Request24");
		butUnlock = addContactControlButton("de.metas.callcenter.UnlockButton", "Lock24");
		topPanel.add(panelContactControl,
				new GridBagConstraints(0, 3, 1, 4, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 5), 0, 0));

		//
		// Center Panel
		final GridController gc = createGridController(m_model.getContactsGridTab(), 500, 400);
		centerPanel.add(gc, BorderLayout.CENTER);

		//
		// Right Panel
		final JXTaskPaneContainer rightPanel = new JXTaskPaneContainer();
		rightPanel.setBackground(AdempierePLAF.getFormBackground());
		//
		final GridController gcRequestUpdates = createGridController(m_model.getRequestUpdatesGridTab(), 300, 200);
		final JXTaskPane gcRequestUpdatesPane = new JXTaskPane();
		// gcRequestUpdatesPane.setUI(new AdempiereTaskPaneUI());
		// gcRequestUpdatesPane.getContentPane().setBackground(AdempierePLAF.getFormBackground());
		gcRequestUpdatesPane.setTitle(Msg.translate(Env.getCtx(), "de.metas.callcenter.HistoryPanelTitle"));
		gcRequestUpdatesPane.add(gcRequestUpdates);
		rightPanel.add(gcRequestUpdatesPane);
		//
		rightPanel.add(fUserComments);
		//
		final GridController gcContactInterest = createGridController(m_model.getContactInterestGridTab(), 400, 100);
		final JXTaskPane gcContactInterestPane = new JXTaskPane();
		// gcContactInterestPane.setUI(new AdempiereTaskPaneUI());
		// gcContactInterestPane.getContentPane().setBackground(AdempierePLAF.getFormBackground());
		gcContactInterestPane.setTitle(Msg.translate(Env.getCtx(), "de.metas.callcenter.ContactInterestTitle"));
		gcContactInterestPane.add(gcContactInterest);
		// gcContactInterestPane.setExpanded(false);
		rightPanel.add(gcContactInterestPane);
		//
		final GridController gcOtherRequests = createGridController(m_model.getOtherRequestsGridTab(), 300, 200);
		final JXTaskPane gcOtherRequestsPane = new JXTaskPane();
		// gcOtherRequestsPane.setUI(new AdempiereTaskPaneUI());
		// gcOtherRequestsPane.getContentPane().setBackground(AdempierePLAF.getFormBackground());
		gcOtherRequestsPane.setTitle(Msg.translate(Env.getCtx(), "de.metas.callcenter.OtherRequestsPanelTitle"));
		gcOtherRequestsPane.add(gcOtherRequests);
		rightPanel.add(gcOtherRequestsPane);
		// Right Panel Scroll
		JScrollPane rightPanelScroll = new JScrollPane();
		rightPanelScroll.getViewport().add(rightPanel);

		//
		// Split between contacts and history/other requests
		final JSplitPane centralPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, centerPanel, rightPanelScroll);
		centralPanel.setDividerLocation(700);
		m_frame.add(centralPanel, BorderLayout.CENTER);

		//
		// Bottom Panel - Status Bar
		m_statusBar.setStatusLine("", false);
		m_frame.add(m_statusBar, BorderLayout.SOUTH);
	}

	private GridController createGridController(GridTab tab, int width, int height)
	{
		final GridController gc = new GridController();
		gc.initGrid(tab, true, m_model.getWindowNo(), null, null);
		if (width > 0 && height > 0)
			gc.setPreferredSize(new Dimension(width, height));
		tab.addPropertyChangeListener(this);
		m_mapVTables.put(tab.getAD_Table_ID(), gc.getTable());
		return gc;
	}

	private CButton addContactControlButton(String text, String icon)
	{
		final CButton b = new CButton();
		b.setText(Msg.translate(Env.getCtx(), text));
		b.setIcon(Images.getImageIcon2(icon));
		b.addActionListener(this);
		b.setPreferredSize(new Dimension(150, 40));
		adjustFont(b, Font.BOLD, 11);
		b.setEnabled(false);
		panelContactControl.add(b, BorderLayout.NORTH);
		return b;
	}

	private void adjustFont(Component c, int style, int size)
	{
		Font f = c.getFont();
		if (f == null)
			return;
		if (style >= 0)
		{
			f = f.deriveFont(style);
		}
		if (size > 0)
		{
			System.out.println("Old size = " + f.getSize());
			f = f.deriveFont(size);
		}
		c.setFont(f);
	}

	public void callContact()
	{
		JPopupMenu popup = new JPopupMenu("RequestMenu");
		List<ContactPhoneNo> phoneList = m_model.getContactPhoneNumbers();
		for (final ContactPhoneNo phoneNo : phoneList)
		{
			final CMenuItem mi = new CMenuItem();
			mi.setAction(new AbstractAction()
			{
				private static final long serialVersionUID = 5528970753808749046L;

				@Override
				public void actionPerformed(ActionEvent e)
				{
					callContact(phoneNo);
				}
			});
			mi.setText(phoneNo.toString());
			popup.add(mi);
		}
		if (phoneList.size() > 0)
		{
			popup.show(butCall, 0, butCall.getHeight());
		}
		else
		{
			callContact(null);
		}
	}

	public void callContact(ContactPhoneNo phoneNo)
	{
		// If not saved(?) record, do nothing
		final I_RV_R_Group_Prospect contact = m_model.getRV_R_Group_Prospect(true);
		if (contact == null || contact.getR_Group_ID() <= 0)
		{
			return;
		}

		m_frame.setEnabled(false);
		m_model.lockContact(contact);
		boolean ok = true;
		try
		{
			ok = openRequestWindow(contact, phoneNo);
		}
		catch (Exception e)
		{
			ok = false;
			throw new RuntimeException(e);
		}
		finally
		{
			if (!ok)
			{
				m_frame.setEnabled(true);
				m_model.unlockContact(contact);
			}
		}
		//
	}

	private boolean openRequestWindow(I_RV_R_Group_Prospect contact, ContactPhoneNo phoneNo)
	{
		MQuery query;
		if (contact.getR_Request_ID() > 0)
		{
			query = MQuery.getEqualQuery(I_R_Request.COLUMNNAME_R_Request_ID, contact.getR_Request_ID());
		}
		else
		{
			query = MQuery.getNoRecordQuery(I_R_Request.Table_Name, true);
		}

		AWindow frame = new AWindow();
		new AWindowListener(frame, this);
		final int AD_Window_ID = m_model.getRequest_Window_ID();
		if (!frame.initWindow(AD_Window_ID, query))
			return false;
		AEnv.addToWindowManager(frame);

		final GridTab tab = frame.getAPanel().getCurrentTab();
		final boolean isNew = (contact.getR_Request_ID() <= 0);
		if (isNew)
		{
			tab.dataNew(DataNewCopyMode.NoCopy);
		}
		final I_R_Request request = InterfaceWrapperHelper.create(tab, I_R_Request.class);
		if (isNew)
		{
			m_model.setRequestDefaults(request, contact);
		}
		m_model.setContactPhoneNo(request, phoneNo);

		AEnv.showCenterScreen(frame);
		m_requestFrame = frame;
		return true;
	}

	private void vtableAutoSizeAll()
	{
		for (VTable t : m_mapVTables.values())
		{
			t.autoSize(true);
		}
	}

	private void query()
	{
		try
		{
			m_frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			//
			m_model.query(getR_Group_ID(), isShowOnlyDue(), isShowOnlyOpen());
			m_updater.start();
			vtableAutoSizeAll();
		}
		finally
		{
			m_frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	private synchronized void refreshAll(boolean force)
	{
		if (!m_frame.isEnabled() && !force)
		{
			log.debug("Not refreshing since inactive");
			return;
		}

		log.debug("Refreshing");
		try
		{
			m_frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			//
			m_model.getContactsGridTab().dataRefreshAll();
			m_model.queryDetails();
			lBundleInfo.setText(m_model.getBundleInfo(getR_Group_ID()));
		}
		finally
		{
			m_frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		vtableAutoSizeAll();

		// Set status
		final Timestamp now = new Timestamp(System.currentTimeMillis());
		final DateFormat df = DisplayType.getDateFormat(DisplayType.DateTime);
		m_statusBar.setStatusDB(Msg.translate(Env.getCtx(), "de.metas.callcenter.LastUpdated")
				+ " " + df.format(now));
	}

	public int getR_Group_ID()
	{
		if (isShowFromAllBundles())
			return CallCenterModel.R_Group_AllBundles;
		//
		Integer value = (Integer)fBundles.getValue();
		int R_Group_ID = value == null ? 0 : value;
		return R_Group_ID;
	}

	public boolean isShowFromAllBundles()
	{
		return fShowFromAllBundles.isSelected();
	}

	public boolean isShowOnlyDue()
	{
		return fShowOnlyDue.isSelected();
	}

	public boolean isShowOnlyOpen()
	{
		return fShowOnlyOpen.isSelected();
	}

	private void updateFieldsStatus()
	{
		final boolean selected = m_model.isContactSelected();
		final I_RV_R_Group_Prospect contact = m_model.getRV_R_Group_Prospect(false);
		final boolean locked = selected ? contact.isLocked() : false;

		butRefresh.setEnabled(getR_Group_ID() > 0 || isShowFromAllBundles());
		butCall.setEnabled(selected);
		butUnlock.setEnabled(locked);
		fUserComments.setText(contact == null ? "" : contact.getComments());
	}

	// @Override
	@Override
	public void vetoableChange(PropertyChangeEvent evt)
			throws PropertyVetoException
	{
		if (evt.getSource() == fBundles)
		{
			final int R_Group_ID = getR_Group_ID();
			lBundleInfo.setText(m_model.getBundleInfo(R_Group_ID));
			query();
			updateFieldsStatus();
		}
	}

	// @Override
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == butCall)
		{
			callContact();
		}
		else if (e.getSource() == butRefresh)
		{
			refreshAll(true);
			m_updater.start();
		}
		else if (e.getSource() == fShowFromAllBundles)
		{
			// fBundles.setEnabled(!fShowFromAllBundles.isSelected());
			fBundles.setReadWrite(!fShowFromAllBundles.isSelected());
			query();
		}
		else if (e.getSource() == fShowOnlyDue)
		{
			query();
		}
		else if (e.getSource() == fShowOnlyOpen)
		{
			query();
		}
	}

	// @Override
	@Override
	public void windowStateChanged(WindowEvent e)
	{
		// The Customize Window was closed
		if (e.getID() == WindowEvent.WINDOW_CLOSED && e.getSource() == m_requestFrame
				&& m_model != null // sometimes we get NPE
		)
		{
			final I_RV_R_Group_Prospect contact = m_model.getRV_R_Group_Prospect(false);
			m_model.unlockContact(contact);
			m_frame.setEnabled(true);
			AEnv.showWindow(m_frame);
			m_requestFrame = null;
			refreshAll(true);
			m_updater.start();
		}
	}

	// @Override
	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{
		if (evt.getSource() == m_model.getContactsGridTab() && evt.getPropertyName().equals(GridTab.PROPERTY))
		{
			updateFieldsStatus();
			m_model.queryDetails();
			vtableAutoSizeAll();
		}

	}

	public static Image getImage(String fileNameInImageDir)
	{
		URL url = CallCenterForm.class.getResource("images/" + fileNameInImageDir);
		if (url == null)
		{
			log.error("Not found: " + fileNameInImageDir);
			return null;
		}
		Toolkit tk = Toolkit.getDefaultToolkit();
		return tk.getImage(url);
	}
}
