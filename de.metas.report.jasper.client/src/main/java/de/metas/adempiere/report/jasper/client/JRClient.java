package de.metas.adempiere.report.jasper.client;

/*
 * #%L
 * adempiereJasper-client
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


import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Properties;
import java.util.logging.Level;

import net.sf.jasperreports.engine.JasperPrint;

import org.adempiere.ad.api.ILanguageBL;
import org.adempiere.bpartner.service.IBPartnerBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.print.MPrintFormat;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.util.CLogger;
import org.compiere.util.CacheInterface;
import org.compiere.util.CacheMgt;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.Language;

import de.metas.adempiere.report.jasper.IJasperServer;
import de.metas.adempiere.report.jasper.OutputType;

public class JRClient
{
	private static final JRClient instance = new JRClient();
	
	public static JRClient get()
	{
		return instance;
	}
	
	public static final String SYSCONFIG_JRServerClass = "de.metas.adempiere.report.jasper.JRServerClass";
	public static final String SYSCONFIG_JRServerClass_DEFAULT = "de.metas.adempiere.report.jasper.server.RemoteServletServer";

	private final CLogger logger = CLogger.getCLogger(getClass());

	private IJasperServer server = null;
	
	/**
	 * Force the Jasper servlet cache to reset together with the others.
	 */
	private CacheInterface cacheListener = new CacheInterface()
	{
		@Override
		public int size()
		{
			return 1;
		}
		
		@Override
		public int reset()
		{
			final IJasperServer server = getJasperServer();
			if (server != null)
			{
				server.cacheReset();
			}
			return 1;
		}
	};

	private JRClient()
	{
		init();
	}

	private void init()
	{
		// If the instance is not a client, reset the Jasper servlet cache.
		if (!Ini.isClient())
		{
			CacheMgt.get().register(cacheListener);
			logger.config("Registered cache listener for JasperReports");
		}
	}
	
	public JasperPrint createJasperPrint(final Properties ctx, final ProcessInfo pi)
	{
		try
		{
			final Language language = extractLanguage(ctx, pi);
			return createJasperPrint(pi.getAD_Process_ID(), pi.getAD_PInstance_ID(), language);
		}
		catch (Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}


	public JasperPrint createJasperPrint(final int AD_Process_ID, final int AD_PInstance_ID, final Language language)
	{
		try
		{
			return createJasperPrint0(AD_Process_ID, AD_PInstance_ID, language);
		}
		catch (Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	private JasperPrint createJasperPrint0(
			final int AD_Process_ID, final int AD_PInstance_ID, final Language language) throws Exception
	{
		byte[] data = getJasperServer().report(AD_Process_ID, AD_PInstance_ID, language.getAD_Language(), OutputType.JasperPrint);
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
		JasperPrint jasperPrint = (JasperPrint)ois.readObject();
		return jasperPrint;
	}

	public byte[] report(final int AD_Process_ID, final int AD_PInstance_ID, final Language language, final OutputType outputType)
	{
		try
		{
			return getJasperServer().report(AD_Process_ID, AD_PInstance_ID, language.getAD_Language(), outputType);
		}
		catch (Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}
	
	public byte[] report(final Properties ctx, final ProcessInfo pi, final OutputType outputType)
	{
		final JRClient jrClient = JRClient.get();
		try
		{
			final Language language = extractLanguage(ctx, pi);
			final byte[] data = jrClient.report(pi.getAD_Process_ID(), pi.getAD_PInstance_ID(), language, outputType);
			return data;
		}
		catch (Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	
	public synchronized IJasperServer getJasperServer()
	{
		if (server != null)
		{
			return server;
		}
		
		final String jrClassname = Services.get(ISysConfigBL.class).getValue(SYSCONFIG_JRServerClass, SYSCONFIG_JRServerClass_DEFAULT);
		logger.log(Level.CONFIG, "JasperServer classname: {0}", jrClassname);
		
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		if (cl == null)
			cl = getClass().getClassLoader();
		try
		{
			server = (IJasperServer)cl.loadClass(jrClassname).newInstance();
		}
		catch (Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
		
		logger.config("JasperServer instance: "+server);
		
		return server;
	}
	
	private static Language extractLanguage(final Properties ctx, final ProcessInfo pi)
	{
		//
		// Get Language from ProcessInfo, if any (08023)
		Language lang = pi.getReportLanguage();
		if (lang != null)
		{
			return lang;
		}

		//
		// Get Language directly from window context, if any (08966)
		if (lang == null && pi.getWindowNo() > 0)
		{
			// Note: onlyWindow is true, otherwise the login language would be returned if no other language was found
			final String languageString = Env.getContext(ctx, pi.getWindowNo(), "AD_Language", true);
			if(languageString != null && !languageString.equals(""))
			{
				lang = Language.getLanguage(languageString);
			}
		}

		//
		// Get Language from the BPartner set in window context, if any (03040)
		if (lang == null && pi.getWindowNo() > 0)
		{
			final Integer C_BPartner_ID = Env.getContextAsInt(ctx, pi.getWindowNo(), "C_BPartner_ID");
			if (C_BPartner_ID != null)
			{
				lang = Services.get(IBPartnerBL.class).getLanguage(ctx, C_BPartner_ID);
			}
		}

		//
		// Get Organization Language if any (03040)
		if (null == lang)
		{
			lang = Services.get(ILanguageBL.class).getOrgLanguage(ctx, pi.getAD_Org_ID());
		}

		// If we got an Language already, return it
		if (null != lang)
		{
			return lang;
		}

		//
		// Fallback: get it from Print Format
		for (final ProcessInfoParameter pip : pi.getParameter())
		{
			if (ProcessInfoParameter.PARAM_PRINT_FORMAT.equals(pip.getParameterName()))
			{
				final MPrintFormat pf = (MPrintFormat)pip.getParameter();
				return pf.getLanguage();
			}
		}

		//
		// Fallback: get it from client context
		return Env.getLanguage(Env.getCtx());
	}

}
