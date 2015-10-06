/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *                      *
 *****************************************************************************/
package org.compiere.report;

import java.util.Properties;
import java.util.logging.Level;

import net.sf.jasperreports.engine.JasperPrint;

import org.adempiere.ad.service.ITaskExecutorService;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_PInstance;
import org.compiere.process.ClientProcess;
import org.compiere.process.ProcessCall;
import org.compiere.process.ProcessInfo;
import org.compiere.report.IJasperServiceRegistry.ServiceType;
import org.compiere.util.CLogger;
import org.compiere.util.DB;

import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.report.jasper.OutputType;
import de.metas.adempiere.report.jasper.client.JRClient;

/**
 * Jasper Report Process: this process is used on all AD_Processes which are about creating jasper reports.
 * 
 * @author rlemeill originaly coming from an application note from compiere.co.uk --- Modifications: Allow Jasper
 *         Reports to be able to be run on VPN profile (i.e: no direct connection to DB). Implemented ClientProcess for
 *         it to run on client.
 * @author Ashley Ramdass
 * @author tsa
 */
public class ReportStarter implements ProcessCall, ClientProcess
{
	// services
	private static final CLogger log = CLogger.getCLogger(ReportStarter.class);
	private static JRViewerProvider viewerProvider = new SwingJRViewerProvider();

	/**
	 * Start jasper reporting process. Based on {@link ProcessInfo#isPrintPreview()}, it will:
	 * <ul>
	 * <li>directly print the report
	 * <li>will open the report viewer and it will display the report
	 * </ul>
	 */
	@Override
	public final boolean startProcess(final Properties ctx, final ProcessInfo pi, final ITrx trx)
	{
		String errorMsg = null;
		try
		{
			//
			// Create report and print it directly
			if (!pi.isPrintPreview())
			{
				// task 08283: direct print can be done in background; no need to let the user wait for this
				Services.get(ITaskExecutorService.class).submit(new Runnable()
				{
					@Override
					public void run()
					{
						try
						{
							log.log(Level.INFO, "Doing direct print without preview; ProcessInfo={0}", pi);
							startProcessDirectPrint(ctx, pi);
						}
						catch (final Exception e)
						{
							reportResult(pi.getAD_PInstance_ID(), e.getLocalizedMessage(), ITrx.TRXNAME_None);
							Services.get(IClientUI.class).warn(pi.getWindowNo(), e);
						}
					}
				});
			}
			//
			// Create report and preview it
			else
			{
				final String title = pi.getTitle();
				final JRViewerProvider viewerLauncher = getReportViewerProvider();
				final OutputType outputType = viewerLauncher.getDesiredOutputType();
				log.log(Level.INFO, "ReportStarter.startProcess run report - {0}, outputType={1}", new Object[] { title, outputType });

				final JRClient jrClient = JRClient.get();
				final byte[] data = jrClient.report(ctx, pi, outputType);
				viewerLauncher.openViewer(data, outputType, title, pi);
			}
		}
		catch (final Exception e)
		{
			errorMsg = e.getLocalizedMessage();
			log.severe("ReportStarter.startProcess: Can not run report - " + errorMsg);
			throw AdempiereException.wrapIfNeeded(e);
		}
		finally
		{
			final String trxName = trx == null ? ITrx.TRXNAME_None : trx.getTrxName();
			reportResult(pi.getAD_PInstance_ID(), errorMsg, trxName);
		}
		return true;
	}

	private void startProcessDirectPrint(final Properties ctx, final ProcessInfo pi)
	{
		final JRClient jrClient = JRClient.get();
		final JasperPrint jasperPrint = jrClient.createJasperPrint(ctx, pi);
		log.info("ReportStarter.startProcess print report -" + jasperPrint.getName());

		//
		// 08284: if we work without preview, use the mass printing framework
		final IJasperServiceRegistry jasperServiceFactory = Services.get(IJasperServiceRegistry.class);
		final IJasperService jasperService = jasperServiceFactory.getJasperService(ServiceType.MASS_PRINT_FRAMEWORK);
		final boolean displayPrintDialog = false;
		jasperService.print(jasperPrint, pi, displayPrintDialog);
	}

	/**
	 * Update {@link I_AD_PInstance} result
	 */
	private void reportResult(final int AD_PInstance_ID, final String errMsg, final String trxName)
	{
		final int result = errMsg == null ? 1 : 0;
		final String sql = "UPDATE AD_PInstance SET result=?, errormsg=? WHERE AD_PInstance_ID=?";
		final Object[] sqlParams = new Object[] { result, errMsg, AD_PInstance_ID };
		try
		{
			DB.executeUpdateEx(sql, sqlParams, trxName);
		}
		catch (final Exception e)
		{
			log.log(Level.SEVERE, e.getLocalizedMessage(), e);
		}
	}

	/**
	 * Set jasper report viewer provider.
	 *
	 * @param provider
	 */
	public static void setReportViewerProvider(final JRViewerProvider provider)
	{
		Check.assumeNotNull(provider, "provider not null");
		viewerProvider = provider;
	}

	/**
	 * Get the current jasper report viewer provider
	 *
	 * @return {@link JRViewerProvider}; never returns null
	 */
	public static JRViewerProvider getReportViewerProvider()
	{
		return viewerProvider;
	}
}