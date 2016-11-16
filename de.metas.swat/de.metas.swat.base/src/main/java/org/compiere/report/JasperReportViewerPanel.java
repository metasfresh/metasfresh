/*
 * Class JRViewer.
 */
package org.compiere.report;

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


import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.service.ITaskExecutorService;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.apps.ADialog;
import org.compiere.apps.EMailDialog;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_PrintFormat;
import org.compiere.model.MRole;
import org.compiere.model.PrintInfo;
import org.compiere.report.IJasperServiceRegistry.ServiceType;
import org.compiere.report.email.service.IEmailParameters;
import org.compiere.report.email.service.IEmailParamsFactory;
import org.compiere.report.email.service.impl.EMailParamsFactory;
import org.compiere.swing.CComboBox;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Util;
import org.slf4j.Logger;

import de.metas.adempiere.report.jasper.OutputType;
import de.metas.adempiere.report.jasper.client.JRClient;
import de.metas.logging.LogManager;
import de.metas.logging.MetasfreshLastError;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JRViewer;

/**
 * Jasper Report Viewer Panel
 *
 * @author tsa
 *
 */
class JasperReportViewerPanel extends JRViewer
{

	private static final long serialVersionUID = -7988455595896562947L;

	/**
	 * This SQL fragment is digestible by {@link MRole#addAccessSQL(String, String, boolean, boolean)}.
	 */
	private final static String SQL_ALTERNATE_REPORTS_PART1 = "SELECT JasperProcess_ID, Name, Description "
			+ "FROM  " //
			+ "   AD_PrintFormat "
			+ "WHERE " //
			+ "   JasperProcess_ID is not NULL"
			+ "   AND IsActive='Y'";

	/**
	 * Together with this SQL fragment, {{@value #SQL_ALTERNATE_REPORTS_PART1} would not be digestible by {@link MRole#addAccessSQL(String, String, boolean, boolean)}, because it contains a sub
	 * select.
	 */
	private final static String SQL_ALTERNATE_REPORTS_PART2 = //
			"   AND (" //
					+ "      (" //
					// -- PF is in is the same group as the currently selected one
					+ "         AD_PrintFormat_Group != 'None'" //
					+ "         AND AD_PrintFormat_Group = (SELECT max(AD_PrintFormat_Group) FROM AD_PrintFormat WHERE JasperProcess_ID=?)"
					+ "      )"
					// -- PF is the currently selected One
					+ "      OR (JasperProcess_ID=?)" //
					+ "   )" //
					+ "ORDER BY Name";

	// Services
	private static final Logger log = LogManager.getLogger(JasperReportViewerPanel.class);
	private static final IMsgBL msgBL = Services.get(IMsgBL.class);

	//
	// Parameters
	private ProcessInfo _processInfo;
	private JasperPrint _jasperPrint;

	private final JComboBox<OutputType> comboExportFormat;
	private final CComboBox<KeyNamePair> comboReport = new CComboBox<KeyNamePair>();

	private final ActionListener alternateReportListener = new ActionListener()
	{

		@Override
		public void actionPerformed(final ActionEvent e)
		{

			final KeyNamePair pp = comboReport.getSelectedItem();
			if (pp == null)
			{
				return;
			}
			//
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			final int jasperProcessId = pp.getKey();

			final ProcessInfo oldPi = getProcessInfo();

			final ProcessInfo newPi = ProcessInfo.builder()
					.setCtx(oldPi.getCtx())
					.setAD_Process_ID(jasperProcessId)
					.setTitle(oldPi.getTitle())
					.setRecord(oldPi.getTable_ID(), oldPi.getRecord_ID())
					.addParameters(oldPi.getParameter())
					.build();

			JasperPrint newJasperPrint = null;
			try
			{
				newJasperPrint = JRClient.get().createJasperPrint(newPi);
			}
			catch (final Exception e1)
			{
				MetasfreshLastError.saveError(log, "Unable to create jasper report with AD_Process_ID " + newPi.getAD_Process_ID(), e1);
			}
			finally
			{
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}

			if (newJasperPrint != null)
			{
				_jasperPrint = newJasperPrint;
				_processInfo = newPi;
				loadReport(newJasperPrint);
				refreshPage();
			}
		}
	};

	public JasperReportViewerPanel(final JasperReportViewerFrame jasperViewerFrame, final JasperPrint jasperPrint) throws JRException
	{
		super(jasperPrint);
		this._processInfo = jasperViewerFrame.getProcessInfo();
		this._jasperPrint = jasperPrint;

		tlbToolBar.add(new JSeparator(SwingConstants.VERTICAL));
		final JButton btnSendByEmail = new JButton();
		btnSendByEmail.setToolTipText(msgBL.getMsg(Env.getCtx(), "SendMail"));
		btnSendByEmail.setText(msgBL.getMsg(Env.getCtx(), "SendMail"));
		btnSendByEmail.setPreferredSize(new java.awt.Dimension(85, 23));
		btnSendByEmail.setMaximumSize(new java.awt.Dimension(85, 23));
		btnSendByEmail.setMinimumSize(new java.awt.Dimension(85, 23));
		btnSendByEmail.addActionListener(new SendByEmailListener(this));
		tlbToolBar.add(btnSendByEmail);
		tlbToolBar.add(new JSeparator(SwingConstants.VERTICAL));

		final JButton btnExport = new JButton();
		btnExport.setToolTipText(msgBL.getMsg(Env.getCtx(), "Export"));
		btnExport.setText(msgBL.getMsg(Env.getCtx(), "Export"));
		btnExport.setPreferredSize(new java.awt.Dimension(85, 23));
		btnExport.setMaximumSize(new java.awt.Dimension(85, 23));
		btnExport.setMinimumSize(new java.awt.Dimension(85, 23));
		btnExport.addActionListener(new ExportListener(this));
		tlbToolBar.add(btnExport);

		comboExportFormat = new JComboBox<>(new OutputType[] { OutputType.PDF, OutputType.XLS });
		comboExportFormat.setPreferredSize(new java.awt.Dimension(80, 23));
		comboExportFormat.setMaximumSize(new java.awt.Dimension(80, 23));
		comboExportFormat.setMinimumSize(new java.awt.Dimension(80, 23));
		comboExportFormat.setSelectedItem(OutputType.PDF);
		tlbToolBar.add(comboExportFormat);

		fillComboReport();
		comboReport.setPreferredSize(new java.awt.Dimension(240, 23));
		comboReport.setMaximumSize(new java.awt.Dimension(240, 23));
		comboReport.setMinimumSize(new java.awt.Dimension(240, 23));
		tlbToolBar.add(comboReport);
		comboReport.setToolTipText(msgBL.translate(Env.getCtx(), "AD_PrintFormat_ID"));

		// Set default viewer zoom level
		btnFitPage.setSelected(true);

		// metas: resetting print button, because we want to handle printing
		// ourselves
		tlbToolBar.remove(btnPrint);

		final KeyListener[] keyListerns = btnPrint.getKeyListeners();

		btnPrint = new javax.swing.JButton();
		btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/net/sf/jasperreports/view/images/print.GIF")));
		btnPrint.setToolTipText(getBundleString("print"));
		btnPrint.setMargin(new java.awt.Insets(2, 2, 2, 2));
		btnPrint.setMaximumSize(new java.awt.Dimension(23, 23));
		btnPrint.setMinimumSize(new java.awt.Dimension(23, 23));
		btnPrint.setPreferredSize(new java.awt.Dimension(23, 23));

		// for some reason, the following commented out statement doesn'nt work
		// when building in hudson
		// btnPrint.addKeyListener(keyNavigationListener);
		for (final KeyListener keyListener : keyListerns)
		{
			btnPrint.addKeyListener(keyListener);
		}

		tlbToolBar.add(btnPrint, 1);
		btnPrint.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(final ActionEvent evt)
			{
				handleReportPrint();
			}
		});
		// metas end
		setZooms();
	}

	final Properties getCtx()
	{
		return Env.getCtx();
	}

	final ProcessInfo getProcessInfo()
	{
		return _processInfo;
	}

	final JasperPrint getJasperPrint()
	{
		return _jasperPrint;
	}

	final OutputType getExportFormat()
	{
		return (OutputType)comboExportFormat.getSelectedItem();
	}

	final static PrintInfo extractPrintInfo(final ProcessInfo pi)
	{
		PrintInfo printInfo = null;
		for (final ProcessInfoParameter pip : pi.getParameter())
		{
			if (pip.getParameter() instanceof PrintInfo)
			{
				if (printInfo != null)
				{
					throw new AdempiereException("More then one PrintInfo parameters found");
				}
				printInfo = (PrintInfo)pip.getParameter();
			}
		}
		return printInfo;
	}

	/**
	 * Fill ComboBox comboReport (report options)
	 *
	 * @param AD_PrintFormat_ID item to be selected
	 */
	private void fillComboReport()
	{
		final int currentProcessId = getProcessInfo().getAD_Process_ID();

		comboReport.removeActionListener(alternateReportListener);
		comboReport.removeAllItems();

		KeyNamePair selectValue = null;
		// fill Report Options
		final String sqlPart1 = Env.getUserRolePermissions().addAccessSQL(SQL_ALTERNATE_REPORTS_PART1, I_AD_PrintFormat.Table_Name, IUserRolePermissions.SQL_NOTQUALIFIED, IUserRolePermissions.SQL_RO);
		final String sql = sqlPart1 + SQL_ALTERNATE_REPORTS_PART2;
		final Object[] sqlParams = new Object[] { currentProcessId, currentProcessId };
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				final KeyNamePair pp = new KeyNamePair(rs.getInt(1), rs.getString(2));
				comboReport.addItem(pp);

				if (rs.getInt(1) == currentProcessId)
				{
					selectValue = pp;
				}
			}
		}
		catch (final SQLException e)
		{
			final DBException dbEx = new DBException(e, sql, sqlParams);
			log.error(dbEx.getLocalizedMessage(), dbEx);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
		if (selectValue != null)
		{
			comboReport.setSelectedItem(selectValue);
		}

		comboReport.addActionListener(alternateReportListener);
	} // fillComboReport

	/**
	 * This method is very similar to {@link JRViewer#btnPrintActionPerformed(java.awt.event.ActionEvent evt)}.
	 */
	private void handleReportPrint()
	{
		Services.get(ITaskExecutorService.class).submit(new Runnable()
		{
			@Override
			public void run()
			{
				// 08284: if we work with the prwview window, use the direct-print framework
				final IJasperServiceRegistry jasperServiceFactory = Services.get(IJasperServiceRegistry.class);
				final IJasperService jasperService = jasperServiceFactory.getJasperService(ServiceType.DIRECT_PRINT_FRAMEWORK);
				final boolean displayPrintDialog = true;
				jasperService.print(getJasperPrint(), getProcessInfo(), displayPrintDialog);
			}
		},
		this.getClass().getSimpleName());
	}

	/**
	 * Export current jasper report, using current export format to given file.
	 *
	 * @param file
	 * @return created {@link I_AD_Archive} or <code>null</code> if no archive was created
	 */
	final I_AD_Archive exportToFile(final File file)
	{
		Check.assumeNotNull(file, "file not null");

		final OutputType exportFormat = getExportFormat();
		Check.assumeNotNull(exportFormat, "exportFormat not null"); // shall not happen

		I_AD_Archive archive = null;
		try
		{
			if (OutputType.PDF == exportFormat)
			{
				final IJasperServiceRegistry jasperServiceFactory = Services.get(IJasperServiceRegistry.class);
				final IJasperService jasperService = jasperServiceFactory.getJasperService(ServiceType.DIRECT_PRINT_FRAMEWORK);

				final JasperPrint jasperPrint = getJasperPrint();
				final byte[] exportData = jasperService.exportToPdf(jasperPrint);

				final ProcessInfo pi = getProcessInfo();
				if (pi.getParameter() != null)
				{

					final PrintInfo printInfo = JasperReportViewerPanel.extractPrintInfo(pi);
					final IArchiveBL archiveService = Services.get(IArchiveBL.class);

					archive = archiveService.archive(exportData, printInfo, false); // force=false => archive only if configured
				}

				Util.writeBytes(file, exportData);
			}
			else if (OutputType.HTML == exportFormat)
			{
				final JasperPrint jasperPrint = getJasperPrint();
				JasperExportManager.exportReportToHtmlFile(jasperPrint, file.getAbsolutePath());
			}
			//
			// Ask the Jasper client to create the report using desired format.
			// Reason: in some cases we are using an alternative jasper report to better layout the data for given export format (e.g. excel)
			else
			{
				final byte[] exportData = JRClient.get().report(getProcessInfo(), exportFormat);
				Util.writeBytes(file, exportData);
			}
		}
		catch (final Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}

		return archive;
	}
}

final class ExportListener implements ActionListener
{
	private final JasperReportViewerPanel _viewer;

	public ExportListener(final JasperReportViewerPanel viewer)
	{
		super();
		Check.assumeNotNull(viewer, "viewer not null");
		this._viewer = viewer;
	}

	private final JasperPrint getJasperPrint()
	{
		return _viewer.getJasperPrint();
	}

	private final String getReportName()
	{
		return getJasperPrint().getName();
	}

	private final OutputType getExportFormat()
	{
		return _viewer.getExportFormat();
	}

	private final ProcessInfo getProcessInfo()
	{
		return _viewer.getProcessInfo();
	}

	@Override
	public void actionPerformed(final ActionEvent event)
	{
		final File file = getFileToExport();
		if (file == null)
		{
			return;
		}

		try
		{
			_viewer.exportToFile(file);
		}
		catch (Exception e)
		{
			ADialog.error(0, _viewer, e);
		}
	}

	/**
	 * @return file or <code>null</code> if user canceled.
	 */
	private File getFileToExport()
	{
		final JFileChooser fileChooser = new JFileChooser();

		final StringBuilder fileName = new StringBuilder();
		final IEmailParamsFactory factory = new EMailParamsFactory();

		final IEmailParameters emailDialogParams = factory.getInstanceForPI(getProcessInfo());

		fileName.append(emailDialogParams.getAttachmentPrefix(getReportName()));
		fileName.append('.');
		fileName.append(getExportFormat().getFileExtension());

		fileChooser.setSelectedFile(new File(fileName.toString()));

		if (fileChooser.showSaveDialog(_viewer) != JFileChooser.APPROVE_OPTION)
		{
			return null;
		}

		return fileChooser.getSelectedFile();
	}
}

final class SendByEmailListener implements ActionListener
{
	private final JasperReportViewerPanel viewer;

	public SendByEmailListener(final JasperReportViewerPanel viewer)
	{
		super();
		Check.assumeNotNull(viewer, "viewer not null");
		this.viewer = viewer;
	}

	@Override
	public void actionPerformed(final ActionEvent event)
	{
		final ProcessInfo pi = viewer.getProcessInfo();
		final OutputType exportFormat = viewer.getExportFormat();

		final IEmailParamsFactory factory = new EMailParamsFactory();
		final IEmailParameters emailParams = factory.getInstanceForPI(pi);

		File attachment = null;
		I_AD_Archive archive;
		try
		{
			attachment = File.createTempFile(emailParams.getAttachmentPrefix("mail") + '_', "." + exportFormat.getFileExtension());
			archive = viewer.exportToFile(attachment);
		}
		catch (final Exception e)
		{
			ADialog.error(0, viewer, e);
			return;
		}

		//
		// Start EMail dialog
		new EMailDialog(
				Env.getFrame(viewer), // parent frame
				emailParams.getTitle(),
				emailParams.getFrom(),
				emailParams.getTo(),
				emailParams.getSubject(),
				emailParams.getMessage(),
				attachment,
				emailParams.getDefaultTextPreset(),
				archive);
	}
}
