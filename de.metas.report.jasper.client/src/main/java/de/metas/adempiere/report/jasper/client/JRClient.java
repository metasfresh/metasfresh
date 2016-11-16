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
import java.util.Properties;

import org.adempiere.ad.api.ILanguageBL;
import org.adempiere.ad.persistence.TableModelClassLoader;
import org.adempiere.bpartner.service.IBPartnerBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IRangeAwareParams;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.X_C_DocType;
import org.compiere.util.CacheInterface;
import org.compiere.util.CacheMgt;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.Language;
import org.slf4j.Logger;

import de.metas.adempiere.report.jasper.IJasperServer;
import de.metas.adempiere.report.jasper.OutputType;
import de.metas.document.engine.IDocActionBL;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfo;
import net.sf.jasperreports.engine.JasperPrint;

public class JRClient
{
	private static final JRClient instance = new JRClient();

	public static JRClient get()
	{
		return instance;
	}

	public static final String SYSCONFIG_JRServerClass = "de.metas.adempiere.report.jasper.JRServerClass";
	public static final String SYSCONFIG_JRServerClass_DEFAULT = "de.metas.adempiere.report.jasper.server.RemoteServletServer";
	public static final String SYSCONFIG_JasperLanguage = "de.metas.report.jasper.OrgLanguageForDraftDocuments";

	private final Logger logger = LogManager.getLogger(getClass());

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

	private JasperPrint createJasperPrint0(final int AD_Process_ID, final int AD_PInstance_ID, final Language language) throws Exception
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

	public byte[] report(final ProcessInfo pi, final OutputType outputType)
	{
		try
		{
			final Language language = extractLanguage(pi);
			final byte[] data = report(pi.getAD_Process_ID(), pi.getAD_PInstance_ID(), language, outputType);
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
		logger.info("JasperServer classname: {}", jrClassname);

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

		logger.info("JasperServer instance: " + server);

		return server;
	}

	private static Language extractLanguage(final ProcessInfo pi)
	{
		//
		// Get Language from ProcessInfo, if any (08023)
		Language lang = pi.getReportLanguage();
		if (lang != null)
		{
			return lang;
		}

		//
		// Get status of the InOut Document, if any, to have de_CH in case that document in dr or ip (03614)

		if (lang == null && pi.getWindowNo() > 0)
		{
			lang = takeLanguageFromDraftInOut(pi);
		}

		//
		// Get Language directly from window context, if any (08966)
		if (lang == null && pi.getWindowNo() > 0)
		{
			// Note: onlyWindow is true, otherwise the login language would be returned if no other language was found
			final String languageString = Env.getContext(pi.getCtx(), pi.getWindowNo(), "AD_Language", true);
			if (languageString != null && !languageString.equals(""))
			{
				lang = Language.getLanguage(languageString);
			}
		}

		//
		// Get Language from the BPartner set in window context, if any (03040)
		if (lang == null && pi.getWindowNo() > 0)
		{
			final Integer C_BPartner_ID = Env.getContextAsInt(pi.getCtx(), pi.getWindowNo(), "C_BPartner_ID");
			if (C_BPartner_ID != null)
			{
				lang = Services.get(IBPartnerBL.class).getLanguage(pi.getCtx(), C_BPartner_ID);
			}
		}

		
		// task 09740
		// In case the report is not linked to a window but it has C_BPartner_ID as parameter and it is set, take the language of that bpartner
		if (lang == null)
		{
			final IRangeAwareParams parameterAsIParams = pi.getParameterAsIParams();
			final int bPartnerID = parameterAsIParams.getParameterAsInt(I_C_BPartner.COLUMNNAME_C_BPartner_ID);
			if(bPartnerID > 0)
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

	/**
	 * Method to extract the language from login in case of drafted documents with docType {@link X_C_DocType#DOCBASETYPE_MaterialDelivery}.
	 * <p>
	 * TODO: extract some sort of language-provider-SPI
	 *
	 * @param ctx
	 * @param pi
	 * @return the login language if conditions fulfilled, null otherwise.
	 * @task http://dewiki908/mediawiki/index.php/09614_Support_de_DE_Language_in_Reports_%28101717274915%29
	 */
	private static Language takeLanguageFromDraftInOut(final ProcessInfo pi)
	{

		final boolean isUseLoginLanguage = Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_JasperLanguage, true);

		// in case the sys config is not set, there is no need to continue
		if (!isUseLoginLanguage)
		{
			return null;
		}

		final String tablename = pi.getTableNameOrNull();

		// the process might not be assigned to a table, but these processes are not covered by this logic
		// for the document processes, there is always a table
		if (Check.isEmpty(tablename, true))
		{
			return null;
		}

		final IDocActionBL docActionBL = Services.get(IDocActionBL.class);
		final boolean isDocument = docActionBL.isDocumentTable(tablename); // fails for processes

		// Make sure the process is for a document
		if (!isDocument)
		{
			return null;
		}

		final Class<?> clazz = TableModelClassLoader.instance.getClass(tablename);

		final Object document = pi.getRecord(clazz);

		final I_C_DocType doctype = docActionBL.getDocTypeOrNull(document);

		// make sure the document has a doctype
		if (doctype == null)
		{
			return null; // this shall never happen
		}

		final String docBaseType = doctype.getDocBaseType();

		// make sure the doctype has a base doctype
		if (docBaseType == null)
		{
			return null;
		}

		// Nothing to do if not dealing with a sales inout.
		if (!X_C_DocType.DOCBASETYPE_MaterialDelivery.equals(docBaseType))
		{
			return null;
		}

		// Nothing to do if the document is not a draft or in progress.
		if (!docActionBL.isStatusDraftedOrInProgress(document))
		{
			return null;
		}

		// If all the conditions described above are fulfilled, take the language from the login
		final Properties ctx = pi.getCtx();
		final String languageString = Env.getAD_Language(ctx);

		return Language.getLanguage(languageString);
	}
}
