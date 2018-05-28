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
package org.compiere.apps.form;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.impexp.IImportProcessFactory;
import org.adempiere.impexp.spi.IAsyncImportProcessBuilder;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.form.fileimport.FileImportPreviewColumnFactory;
import org.compiere.apps.form.fileimport.FileImportPreviewTableModel;
import org.compiere.impexp.FileImportReader;
import org.compiere.impexp.ImpDataLine;
import org.compiere.impexp.ImpFormat;
import org.compiere.impexp.ImportStatus;
import org.compiere.model.I_AD_ImpFormat;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CPanel;
import org.compiere.swing.ListComboBoxModel;
import org.compiere.swing.ToStringListCellRenderer;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.jdesktop.swingx.JXTable;
import org.slf4j.Logger;

import de.metas.adempiere.form.IClientUI;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import lombok.NonNull;

/**
 * Fixed length file import
 *
 * @author Jorg Janke
 * @version $Id: VFileImport.java,v 1.2 2006/07/30 00:51:28 jjanke Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *         <ul>
 *         <li>FR [ 1658127 ] Select charset encoding on import
 *         <li>BF [ 1619158 ] Import is not working with UTF-8
 *         <li>BF [ 1738641 ] Import Formats are accesible for all tenants
 *         <li>BF [ 1778356 ] VFileImport: IndexOfBound exp if the file is not loaded
 *         </ul>
 */
public class VFileImport extends CPanel
		implements FormPanel, ActionListener
{
	/**
	 *
	 */
	private static final long serialVersionUID = 3996535986364873964L;

	// services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient IClientUI clientUI = Services.get(IClientUI.class);

	private static final int MAX_SHOWN_LINES = 10;

	/**
	 * Initialize Panel
	 *
	 * @param WindowNo window
	 * @param frame frame
	 */
	@Override
	public void init(final int WindowNo, final FormFrame frame)
	{
		logger.info("");
		m_WindowNo = WindowNo;
		m_frame = frame;
		m_frame.setMaximize(true);
		m_frame.setMinimumSize(new Dimension(1000, 520));
		m_frame.setPreferredSize(new Dimension(1000, 520));
		try
		{
			jbInit();
			dynInit();

			final Container frameContentPane = frame.getContentPane();
			frameContentPane.setLayout(new BorderLayout());
			frameContentPane.add(northPanel, BorderLayout.NORTH);
			frameContentPane.add(centerPanel, BorderLayout.CENTER);
			frameContentPane.add(confirmPanel, BorderLayout.SOUTH);
		}
		catch (final Exception e)
		{
			logger.error("init", e);
		}
	}	// init

	/** Window No */
	private int m_WindowNo = Env.WINDOW_None;
	/** FormFrame */
	private FormFrame m_frame;

	/** Current selected file to import */
	private File _file = null;
	/** Logger */
	private static final Logger logger = LogManager.getLogger(VFileImport.class);

	//
	private final CPanel northPanel = new CPanel();
	private final JButton bFile = new JButton();
	private final CComboBox<Charset> fCharset = new CComboBox<>(Ini.getAvailableCharsets());
	private final JComboBox<I_AD_ImpFormat> pickImpFormat = new JComboBox<>();
	private final CPanel centerPanel = new CPanel();
	private final JTextArea rawDataPreview = new JTextArea();
	private final FileImportPreviewTableModel previewTableModel = new FileImportPreviewTableModel();
	private final JXTable previewTable = new JXTable(previewTableModel);
	private final ConfirmPanel confirmPanel = ConfirmPanel.builder()
			.withCancelButton(true)
			.build();
	private final JLabel dataLinesSummary = new JLabel();

	/**
	 * Static Init
	 *
	 * @throws Exception
	 */
	private void jbInit() throws Exception
	{
		AdempierePLAF.setDefaultBackground(this);

		bFile.addActionListener(this);

		fCharset.setToolTipText(msgBL.getMsg(Env.getCtx(), "Charset", false));
		//
		pickImpFormat.setRenderer(new ToStringListCellRenderer<I_AD_ImpFormat>()
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected String renderToString(final I_AD_ImpFormat value)
			{
				return value == null ? "----" : value.getName();
			}
		});
		//
		final JLabel labelFormat = new JLabel();
		labelFormat.setText(msgBL.translate(Env.getCtx(), "AD_ImpFormat_ID"));
		//
		northPanel.setBorder(BorderFactory.createEmptyBorder());
		northPanel.add(bFile, null);
		northPanel.add(fCharset);
		northPanel.add(dataLinesSummary, null);
		northPanel.add(labelFormat, null);
		northPanel.add(pickImpFormat, null);

		//
		// Center panel
		{
			// Raw data preview
			{
				centerPanel.setLayout(new BorderLayout());
				rawDataPreview.setFont(new java.awt.Font("Monospaced", 0, 10));
				rawDataPreview.setColumns(80);
				rawDataPreview.setRows(MAX_SHOWN_LINES);
				final JScrollPane rawDataPane = new JScrollPane();
				rawDataPane.getViewport().add(rawDataPreview, null);
				centerPanel.add(rawDataPane, BorderLayout.NORTH);
			}
			// Tabular data preview
			{
				previewTable.setColumnFactory(FileImportPreviewColumnFactory.instance);
				previewTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

				final JScrollPane previewPane = new JScrollPane(previewTable);
				previewPane.setPreferredSize(new Dimension(700, 200));

				centerPanel.add(previewPane, BorderLayout.CENTER);
			}
		}
		//
		confirmPanel.setActionListener(this);
	}	// jbInit

	/**
	 * Dispose
	 */
	@Override
	public void dispose()
	{
		if (m_frame != null)
		{
			m_frame.dispose();
		}
		m_frame = null;
	}	// dispose

	/**
	 * Dynamic Init
	 */
	private void dynInit()
	{
		loadFile(null); // no file

		// Load Formats
		try
		{
			final List<I_AD_ImpFormat> impFormats = Services.get(IQueryBL.class).createQueryBuilder(I_AD_ImpFormat.class, Env.getCtx(), ITrx.TRXNAME_None)
					.addOnlyActiveRecordsFilter()
					.orderBy()
					.addColumn(I_AD_ImpFormat.COLUMNNAME_Name)
					.endOrderBy()
					.create()
					.setApplyAccessFilterRW(false) // RO
					.list(I_AD_ImpFormat.class);

			final ListComboBoxModel<I_AD_ImpFormat> pickFormatModel = new ListComboBoxModel<>();
			pickFormatModel.add(null);
			pickFormatModel.addAll(impFormats);
			pickImpFormat.setModel(pickFormatModel);
		}
		catch (final Exception e)
		{
			logger.error("Failed loading import formats", e);
		}
		pickImpFormat.setSelectedIndex(0);
		pickImpFormat.addActionListener(this);
		//
		fCharset.setSelectedItem(Ini.getCharset());
		fCharset.addActionListener(this);
		//
		updateButtonsStatus();
	}	// dynInit

	/**************************************************************************
	 * Action Listener
	 *
	 * @param e event
	 */
	@Override
	public void actionPerformed(final ActionEvent e)
	{
		try
		{
			actionPerformed0(e);
		}
		catch (final Exception ex)
		{
			clientUI.error(m_WindowNo, ex);
		}
	}

	private final void actionPerformed0(final ActionEvent e)
	{
		if (e.getSource() == bFile)
		{
			cmd_loadFile();
			invalidate();
			// m_frame.pack();
		}
		else if (e.getSource() == fCharset)
		{
			cmd_reloadFile();
		}
		else if (e.getSource() == pickImpFormat)
		{
			cmd_loadImpFormat();
			invalidate();
			// m_frame.pack();
		}
		else if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			cmd_importPrepare();
			return; // return here because this process runs async
		}
		else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			dispose();
			return;
		}

		updateButtonsStatus();
	}	// actionPerformed

	/**************************************************************************
	 * Load File
	 */
	private void cmd_loadFile()
	{
		String directory = null;
		File fileToSelect = null;
		
		// Use the directory of previous selected file
		final File currentFile = getFile();
		if (currentFile != null)
		{
			final File currentDir = currentFile.getParentFile();
			if (currentDir.exists() && currentDir.isDirectory())
			{
				directory = currentDir.getAbsolutePath();
				fileToSelect = currentFile;
			}
		}
		
		// Default: use the METASFRESH_HOME/data/import directory
		if (directory == null)
		{
			directory = org.compiere.Adempiere.getMetasfreshHome()
					+ File.separator + "data"
					+ File.separator + "import";
			fileToSelect = null;
		}
		logger.info(directory);

		// File filter: only supported types
		FileFilter filter = new FileNameExtensionFilter("Text (*.csv, *.txt)", new String[] {"txt", "csv"});
		
		//
		// Ask user to pick a file
		final JFileChooser chooser = new JFileChooser(directory);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setFileFilter(filter);
		chooser.addChoosableFileFilter(filter);
		chooser.setMultiSelectionEnabled(false);
		if(fileToSelect != null)
		{
			chooser.setSelectedFile(fileToSelect);
		}
		chooser.setDialogTitle(msgBL.getMsg(Env.getCtx(), "FileImportFileInfo"));
		if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
		{
			return;
		}

		//
		// Load the selected file
		loadFile(chooser.getSelectedFile());
	}

	/**
	 * @param file file to load or <code>null</code>
	 */
	private void loadFile(final File file)
	{
		_file = file;

		if (file != null)
		{
			bFile.setText(file.getName());
			bFile.setToolTipText(file.getAbsolutePath());
		}
		else
		{
			bFile.setText(msgBL.getMsg(Env.getCtx(), "FileImportFile"));
			bFile.setToolTipText(msgBL.getMsg(Env.getCtx(), "FileImportFileInfo"));
		}

		cmd_reloadFile();
	}

	private File getFile()
	{
		return _file;
	}

	
	/**
	 * Reload/Load file
	 */
	private void cmd_reloadFile()
	{
		final File file = getFile();

		List<String> loadedDataLines = new ArrayList<>();
		String loadedDataPreview = "";
		boolean loadOK = false;

		try
		{
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			if (file != null)
			{
				final Charset charset = fCharset.getSelectedItem();
				loadedDataLines = readLines(file, charset);
				//
				// build the preview string
				loadedDataPreview = FileImportReader.buildDataPreview(loadedDataLines);
			}
			loadOK = true;
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Failed loading " + (file == null ? "file" : file), e);
		}
		finally
		{
			//
			// Set loaded data
			if (loadOK)
			{
				rawDataPreview.setText(loadedDataPreview.toString());
				rawDataPreview.setCaretPosition(0);
				previewTableModel.setDataLines(loadedDataLines);
				previewTable.packAll();

				dataLinesSummary.setText(previewTableModel.getSummary());
			}

			setCursor(Cursor.getDefaultCursor());
		}
	}	// cmd_loadFile

	private List<String> readLines(@NonNull final File file, @NonNull final Charset charset) throws IOException
	{
		if (isMultiLineFormat())
		{
			return FileImportReader.readMultiLines(file, charset);
		}
		return FileImportReader.readRegularLines(file, charset);
	}
	
	private boolean isMultiLineFormat()
	{
		final I_AD_ImpFormat impFormatModel = (I_AD_ImpFormat)pickImpFormat.getSelectedItem();
		if (impFormatModel == null)
		{
			return false;
		}
		return impFormatModel.isMultiLine();
	}
	
	
	
	private final void updateButtonsStatus()
	{
		final ImpFormat impFormat = previewTableModel.getImpFormat();

		final boolean hasData = previewTableModel.getRowCount() > 0
				&& impFormat != null && impFormat.getRowCount() > 0; // format loaded

		confirmPanel.getOKButton().setEnabled(hasData);
	}

	/**
	 * Load Format
	 */
	private void cmd_loadImpFormat()
	{
		//
		final I_AD_ImpFormat impFormatModel = (I_AD_ImpFormat)pickImpFormat.getSelectedItem();
		if (impFormatModel == null)
		{
			return;
		}

		final ImpFormat impFormat = ImpFormat.load(impFormatModel);
		previewTableModel.setImpFormat(impFormat);
		if (impFormat == null)
		{
			throw new AdempiereException("@FileImportNoFormat@: " + impFormatModel.getName());
		}
	}

	/**
	 * Import selected rows to import table.
	 */
	private void cmd_importPrepare()
	{
		m_frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		confirmPanel.setEnabled(false);
		m_frame.setBusy(true);
		m_frame.setBusyMessage(null);

		final SwingWorker<Void, ImpDataLine> worker = new SwingWorker<Void, ImpDataLine>()
		{
			private int countImported = 0;
			private int countAll = 0;

			private String statusMessagePrefix = "";

			@Override
			protected Void doInBackground() throws Exception
			{
				statusMessagePrefix = msgBL.getMsg(Env.getCtx(), "Processing") + ": ";

				final List<ImpDataLine> lines = previewTableModel.getImpDataLines();
				countImported = 0;
				countAll = lines.size();

				for (final ImpDataLine line : lines)
				{
					if (!line.isToImport())
					{
						continue;
					}

					line.importToDB();
					if (ImportStatus.ImportPrepared == line.getImportStatus())
					{
						line.setToImport(false);
						countImported++;
					}

					publish(line);
				}

				cmd_importSchedule(); // TODO: just for now

				return null;
			}

			@Override
			protected void process(final List<ImpDataLine> chunks)
			{
				m_frame.setBusyMessagePlain(statusMessagePrefix + countImported + "/" + countAll);
			}

			@Override
			protected void done()
			{
				logErrorIfAny();

				previewTableModel.fireTableDataChanged();
				updateButtonsStatus();

				m_frame.setBusy(false);
				confirmPanel.setEnabled(true);
				m_frame.setCursor(Cursor.getDefaultCursor());

				clientUI.info(m_WindowNo, "FileImportR/I", "#" + countImported);
			}

			private final void logErrorIfAny()
			{
				try
				{
					get();
				}
				catch (InterruptedException e)
				{
					logger.warn("", e);
				}
				catch (ExecutionException e)
				{
					logger.warn("", e.getCause());
				}
			}
		};
		worker.execute();
	}

	private void cmd_importSchedule()
	{
		try
		{
			final IAsyncImportProcessBuilder builder = Services.get(IImportProcessFactory.class)
					.newAsyncImportProcessBuilder()
					.setCtx(Env.getCtx())
					.setImportTableName(previewTableModel.getImpFormat().getTableName());

			final List<ImpDataLine> lines = previewTableModel.getImpDataLines();
			final List<ImpDataLine> linesScheduled = new ArrayList<>(lines.size());
			for (final ImpDataLine line : lines)
			{
				// Skip those which were not prepared yet
				final ITableRecordReference importRecordRef = line.getImportRecordRef();
				if (importRecordRef == null)
				{
					continue;
				}

				// Skip those already scheduled
				if (ImportStatus.ImportScheduled == line.getImportStatus())
				{
					continue;
				}

				builder.addImportRecord(importRecordRef);
				linesScheduled.add(line);
			}

			builder.buildAndEnqueue();

			// Update import status
			for (final ImpDataLine line : linesScheduled)
			{
				line.setImportStatus_Scheduled();
			}
		}
		catch (Exception e)
		{
			throw new AdempiereException("Failed to schedules lines to be imported", e);
		}
	}
}
