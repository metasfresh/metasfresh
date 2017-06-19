package de.metas.i18n;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.apps.StatusBar;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.swing.CPanel;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.KeyNamePair;
import org.compiere.util.ValueNamePair;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Translation Dialog Import + Export.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @author based on initial version of Jorg Janke
 */
public class VTranslationImpExpDialog extends TranslationController
		implements FormPanel, ActionListener
{
	private static final transient Logger log = LogManager.getLogger(VTranslationImpExpDialog.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	private FormFrame m_frame;
	//
	private final CPanel panel = new CPanel();
	private final JComboBox<ValueNamePair> cbLanguage = new JComboBox<>();
	private final JComboBox<ValueNamePair> cbTable = new JComboBox<>();
	private final JButton bExport = new JButton();
	private final JButton bImport = new JButton();
	//
	private final StatusBar statusBar = new StatusBar();
	private final JComboBox<KeyNamePair> cbClient = new JComboBox<>();

	/**
	 * Static Init
	 *
	 * @throws Exception
	 */
	private void jbInit() throws Exception
	{
		panel.setLayout(new GridBagLayout());

		final JLabel lClient = new JLabel();
		lClient.setText(msgBL.translate(Env.getCtx(), "AD_Client_ID"));

		final JLabel lLanguage = new JLabel();
		lLanguage.setText(msgBL.translate(Env.getCtx(), "AD_Language"));
		lLanguage.setToolTipText(msgBL.translate(Env.getCtx(), "IsSystemLanguage"));

		final JLabel lTable = new JLabel();
		lTable.setText(msgBL.translate(Env.getCtx(), "AD_Table_ID"));
		//
		bExport.setText(msgBL.getMsg(Env.getCtx(), "Export"));
		bExport.addActionListener(this);
		bImport.setText(msgBL.getMsg(Env.getCtx(), "Import"));
		bImport.addActionListener(this);
		//
		panel.add(cbLanguage, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		panel.add(lLanguage, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		panel.add(lTable, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		panel.add(cbTable, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
		panel.add(bExport, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		panel.add(bImport, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		panel.add(lClient, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
		panel.add(cbClient, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
	}	// jbInit

	/**
	 * Dynamic Init.
	 * - fill Language & Table
	 */
	private void dynInit()
	{
		// Fill Client
		final List<KeyNamePair> clients = getClientList();
		for (final KeyNamePair client : clients)
		{
			cbClient.addItem(client);
		}

		// Fill Language
		final List<ValueNamePair> languages = getLanguageList();
		for (final ValueNamePair language : languages)
		{
			cbLanguage.addItem(language);
		}

		// Fill Table
		final List<ValueNamePair> tables = getTableList();
		for (final ValueNamePair table : tables)
		{
			cbTable.addItem(table);
		}

		// Info
		clearStatusBar(statusBar);
	}	// dynInit

	/**
	 * Initialize Panel
	 *
	 * @param WindowNo window
	 * @param frame frame
	 */
	@Override
	public void init(final int WindowNo, final FormFrame frame)
	{
		log.info("");
		m_WindowNo = WindowNo;
		m_frame = frame;
		Env.setContext(Env.getCtx(), m_WindowNo, Env.CTXNAME_IsSOTrx, true);
		try
		{
			jbInit();
			dynInit();
			frame.getContentPane().add(panel, BorderLayout.CENTER);
			frame.getContentPane().add(statusBar, BorderLayout.SOUTH);
		}
		catch (final Exception ex)
		{
			log.error("", ex);
		}
	}	// init

	/**
	 * Dispose
	 */
	@Override
	public void dispose()
	{
		m_frame.dispose();
	}	// dispose

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		try
		{
			actionPerformed0(e);
		}
		catch (final Exception ex)
		{
			log.warn("", ex);
			statusBar.setStatusLine(ex.getLocalizedMessage(), true);
		}
	}

	private void actionPerformed0(final ActionEvent e)
	{
		final ValueNamePair AD_Language = (ValueNamePair)cbLanguage.getSelectedItem();
		if (AD_Language == null)
		{
			throw new AdempiereException("@LanguageSetupError@");
		}

		ValueNamePair AD_Table = (ValueNamePair)cbTable.getSelectedItem();
		if (AD_Table == null)
		{
			return;
		}

		final boolean imp = e.getSource() == bImport;
		final KeyNamePair AD_Client = (KeyNamePair)cbClient.getSelectedItem();
		int AD_Client_ID = -1;
		if (AD_Client != null)
		{
			AD_Client_ID = AD_Client.getKey();
		}

		final String startDir = Ini.getMetasfreshHome() + File.separator + "data";
		final JFileChooser chooser = new JFileChooser(startDir);
		chooser.setMultiSelectionEnabled(false);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		final int returnVal = imp ? chooser.showOpenDialog(panel) : chooser.showSaveDialog(panel);
		if (returnVal != JFileChooser.APPROVE_OPTION)
		{
			return;
		}
		final String directory = chooser.getSelectedFile().getAbsolutePath();
		//
		statusBar.setStatusLine(directory);
		panel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		try
		{
			final TranslationImpExp t = TranslationImpExp.newInstance();
			t.validateLanguage(AD_Language.getValue());

			// All Tables
			if (AD_Table.getValue().equals(""))
			{
				String msg = null;

				for (int i = 1; i < cbTable.getItemCount(); i++)
				{
					AD_Table = cbTable.getItemAt(i);
					msg = imp
							? t.importTrl(directory, AD_Client_ID, AD_Language.getValue(), AD_Table.getValue())
							: t.exportTrl(directory, AD_Client_ID, AD_Language.getValue(), AD_Table.getValue());
				}

				if (msg == null || msg.length() == 0)
				{
					msg = (imp ? "Import" : "Export") + " Successful. [" + directory + "]";
				}

				statusBar.setStatusLine(directory);
			}
			else	// single table
			{
				String msg = imp
						? t.importTrl(directory, AD_Client_ID, AD_Language.getValue(), AD_Table.getValue())
						: t.exportTrl(directory, AD_Client_ID, AD_Language.getValue(), AD_Table.getValue());

				if (msg == null || msg.length() == 0)
				{
					msg = (imp ? "Import" : "Export") + " Successful. [" + directory + "]";
				}

				statusBar.setStatusLine(msg);
			}
		}
		finally
		{
			panel.setCursor(Cursor.getDefaultCursor());
		}
	}	// actionPerformed

}
