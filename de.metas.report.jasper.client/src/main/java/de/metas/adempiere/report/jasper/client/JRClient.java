package de.metas.adempiere.report.jasper.client;

/*
 * #%L
 * de.metas.report.jasper.client
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

import org.adempiere.ad.api.ILanguageBL;
import org.adempiere.bpartner.service.IBPartnerBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.adempiere.util.api.IRangeAwareParams;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.CacheInterface;
import org.compiere.util.CacheMgt;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.Language;
import org.compiere.util.Util;
import org.slf4j.Logger;

import de.metas.adempiere.report.jasper.IJasperServer;
import de.metas.adempiere.report.jasper.OutputType;
import de.metas.logging.LogManager;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfo.ProcessInfoBuilder;
import net.sf.jasperreports.engine.JasperPrint;

public final class JRClient
{
	public static JRClient get()
	{
		return instance;
	}

	public static final String SYSCONFIG_JRServerClass = "de.metas.adempiere.report.jasper.JRServerClass";
	public static final String SYSCONFIG_JRServerClass_DEFAULT = "de.metas.adempiere.report.jasper.server.RemoteServletServer";

	private static final Logger logger = LogManager.getLogger(JRClient.class);

	// NOTE: keep this one after all other static declarations, to avoid NPE on initialization
	private static final JRClient instance = new JRClient();

	//
	// ---------------------
	//

	/** Jasper server supplier */
	private final ExtendedMemorizingSupplier<IJasperServer> serverSupplier = ExtendedMemorizingSupplier.of(() -> createJasperServer());

	/**
	 * Force the Jasper servlet cache to reset together with the others.
	 */
	private final CacheInterface cacheListener = new CacheInterface()
	{
		@Override
		public int size()
		{
			return 1;
		}

		@Override
		public int reset()
		{
			// Force recreating of jasper server, just in case the config changed
			serverSupplier.forget();

			final IJasperServer server = serverSupplier.get();
			if (server != null)
			{
				server.cacheReset();
			}
			return 1;
		}
	};

	private JRClient()
	{
		super();
		
		// If the instance is not a client, reset the Jasper servlet cache.
		if (!Ini.isClient())
		{
			CacheMgt.get().register(cacheListener);
			logger.info("Registered cache listener for JasperReports");
		}
	}

	public JasperPrint createJasperPrint(final ProcessInfo pi)
	{
		try
		{
			final Language language = extractLanguage(pi);
			return createJasperPrint(pi.getAD_Process_ID(), pi.getAD_PInstance_ID(), language);
		}
		catch (Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	private JasperPrint createJasperPrint(final int AD_Process_ID, final int AD_PInstance_ID, final Language language)
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

	private JasperPrint createJasperPrint0(final int AD_Process_ID, final int AD_PInstance_ID, final Language language) throws Exception
	{
		final IJasperServer server = serverSupplier.get();
		byte[] data = server.report(AD_Process_ID, AD_PInstance_ID, language.getAD_Language(), OutputType.JasperPrint);
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
		JasperPrint jasperPrint = (JasperPrint)ois.readObject();
		return jasperPrint;
	}

	private byte[] report(final int AD_Process_ID, final int AD_PInstance_ID, final Language language, final OutputType outputType)
	{
		try
		{
			final IJasperServer server = serverSupplier.get();
			return server.report(AD_Process_ID, AD_PInstance_ID, language.getAD_Language(), outputType);
		}
		catch (Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	public byte[] report(final ProcessInfo pi)
	{
		return report(pi, pi.getJRDesiredOutputType());
	}

	public byte[] report(final ProcessInfo pi, final OutputType outputType)
	{
		try
		{
			// Make sure the ProcessInfo is persisted because we will need to access it's data (like AD_Table_ID/Record_ID etc)
			if (pi.getAD_PInstance_ID() <= 0)
			{
				Services.get(IADPInstanceDAO.class).saveProcessInfoOnly(pi);
			}

			final Language language = extractLanguage(pi);
			final OutputType outputTypeEffective = Util.coalesce(outputType, pi.getJRDesiredOutputType());
			final byte[] data = report(pi.getAD_Process_ID(), pi.getAD_PInstance_ID(), language, outputTypeEffective);
			return data;
		}
		catch (Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	private final IJasperServer createJasperServer()
	{
		final String jrClassname = Services.get(ISysConfigBL.class).getValue(SYSCONFIG_JRServerClass, SYSCONFIG_JRServerClass_DEFAULT);
		logger.info("JasperServer classname: {}", jrClassname);

		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		if (cl == null)
			cl = getClass().getClassLoader();
		try
		{
			final IJasperServer server = (IJasperServer)cl.loadClass(jrClassname).newInstance();
			logger.info("JasperServer instance: " + server);
			return server;
		}
		catch(ClassNotFoundException e)
		{
			throw new AdempiereException("Jasper server class not found", e);
		}
		catch (Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	/**
	 * Extracts reporting language from given {@link ProcessInfo}.
	 * 
	 * @param pi
	 * @return Language; never returns null
	 * 
	 * @implNote Usually the ProcessInfo already has the language set, so this method is just a fallback.
	 *           If you are thinking to extend how the reporting language is fetched, please check {@link ProcessInfoBuilder}'s getReportLanguage() method.
	 */
	private static Language extractLanguage(final ProcessInfo pi)
	{
		//
		// Get Language from ProcessInfo, if any (08023)
		Language lang = pi.getReportLanguage();
		if (lang != null)
		{
			return lang;
		}

		// task 09740
		// In case the report is not linked to a window but it has C_BPartner_ID as parameter and it is set, take the language of that bpartner
		if (lang == null)
		{
			final IRangeAwareParams parameterAsIParams = pi.getParameterAsIParams();
			final int bPartnerID = parameterAsIParams.getParameterAsInt(I_C_BPartner.COLUMNNAME_C_BPartner_ID);
			if (bPartnerID > 0)
			{
				lang = Services.get(IBPartnerBL.class).getLanguage(pi.getCtx(), bPartnerID);
				return lang;
			}
		}

		//
		// Get Organization Language if any (03040)
		if (null == lang)
		{
			lang = Services.get(ILanguageBL.class).getOrgLanguage(pi.getCtx(), pi.getAD_Org_ID());
		}

		// If we got an Language already, return it
		if (null != lang)
		{
			return lang;
		}

		//
		// Fallback: get it from client context
		return Env.getLanguage(Env.getCtx());
	}
}
