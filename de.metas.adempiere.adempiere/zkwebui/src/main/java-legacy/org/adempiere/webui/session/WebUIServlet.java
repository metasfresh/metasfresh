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

package org.adempiere.webui.session;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.adempiere.webui.window.ZkReportViewerProvider;
import org.compiere.Adempiere.RunMode;
import org.compiere.print.ReportCtl;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.zkoss.zk.ui.http.DHtmlLayoutServlet;

/**
 * 
 * @author <a href="mailto:agramdass@gmail.com">Ashley G Ramdass</a>
 * @date Feb 25, 2007
 * @version $Revision: 0.10 $
 */
public class WebUIServlet extends DHtmlLayoutServlet
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 261899419681731L;
	
	/** Logger for the class * */
    private static final Logger logger;

    static
    {
        logger = LogManager.getLogger(WebUIServlet.class); 
    }

	/**
	 * Set a WebUI specific Env-Context-Provider and starts the ADempiere environment in run mode {@link RunMode#WEBUI} if it is not yet running.
	 */
    @Override
	public void init(ServletConfig servletConfig) throws ServletException
    {
        super.init(servletConfig);

        // Initialize context for the current thread
        // task 08859: we don't use the ZkContextProvider as contextprovider anymore.

        /**
         * Start ADempiere
         */
		// task 04585: starting with RunMode.WEBUI.
        final boolean started = Env.getSingleAdempiereInstance().startup(RunMode.WEBUI);
        if(!started)
        {
            throw new ServletException("Could not start ADempiere");
        }
        
        // hengsin: temporary solution for problem with zk client
        Ini.setProperty(Ini.P_ADEMPIERESYS, false);
        ReportCtl.setReportViewerProvider(new ZkReportViewerProvider());
        // ReportStarter.setReportViewerProvider(new ZkJRViewerProvider()); // metas: tsa: commented out because jasper is not a dependency: see the swat (de.metas.swat.WebUIServletListener)
        
        WebUIServletListeners.get().init(servletConfig); // metas: 02899
        
        logger.info("ADempiere started successfully");
        /**
         * End ADempiere Start
         */
    }

    @Override
	protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException
    {
    	WebUIServletListeners.get().doGet(request, response);
    	
        super.doGet(request, response);
    }

    @Override
	protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException
    {
    	WebUIServletListeners.get().doPost(request, response);
    	
        super.doPost(request, response);
    }

    @Override
	public void service(ServletRequest request, ServletResponse response)
            throws ServletException, IOException
    {
        super.service(request, response);
    }

    @Override
	public ServletConfig getServletConfig()
    {
        return super.getServletConfig();
    }

    @Override
	public String getServletInfo()
    {
        return super.getServletInfo();
    }

    @Override
	public void destroy()
    {
        WebUIServletListeners.get().destroy(); // metas: 02899
        super.destroy();
    }
}
