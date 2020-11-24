package de.metas.report.client;

/*
 * #%L
 * de.metas.report.report.client
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.api.IRangeAwareParams;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.cache.CacheMgt;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.i18n.ILanguageBL;
import de.metas.i18n.Language;
import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfo.ProcessInfoBuilder;
import de.metas.report.jasper.client.RemoteServletInvoker;
import de.metas.report.server.IReportServer;
import de.metas.report.server.OutputType;
import de.metas.report.server.ReportResult;
import de.metas.util.Services;
import de.metas.common.util.CoalesceUtil;
import lombok.NonNull;
import net.sf.jasperreports.engine.JasperPrint;

public final class ReportsClient
{
	public static ReportsClient get()
	{
		return instance;
	}

	private static final String SYSCONFIG_ReportsServerClass = "de.metas.report.jasper.client.JRServerClass";
	private static final String SYSCONFIG_ReportsServerClass_DEFAULT = RemoteServletInvoker.class.getName();

	private static final Logger logger = LogManager.getLogger(ReportsClient.class);

	// NOTE: keep this one after all other static declarations, to avoid NPE on initialization
	private static final ReportsClient instance = new ReportsClient();

	/** Reports server supplier */
	private final ExtendedMemorizingSupplier<IReportServer> serverSupplier = ExtendedMemorizingSupplier.of(() -> createReportServer());

	private ReportsClient()
	{
		// If the instance is not a client, reset the reports server cache
		if (!Ini.isSwingClient())
		{
			CacheMgt.get().addCacheResetListener(this::onCacheReset);
			logger.info("Registered cache listener for report server");
		}
	}

	private int onCacheReset(final CacheInvalidateMultiRequest multiRequest)
	{
		if (!multiRequest.isResetAll())
		{
			return 0;
		}

		// Force recreating of reports server, just in case the config changed
		serverSupplier.forget();

		final IReportServer server = serverSupplier.get();
		if (server != null)
		{
			server.cacheReset();
		}
		return 1;
	}

	public JasperPrint createJasperPrint(final ProcessInfo pi)
	{
		final Language language = extractLanguage(pi);
		return createJasperPrint(pi.getAdProcessId(), pi.getPinstanceId(), language);
	}

	private JasperPrint createJasperPrint(final AdProcessId adProcessId, final PInstanceId pinstanceId, final Language language)
	{
		return createJasperPrint0(adProcessId, pinstanceId, language);
	}

	private JasperPrint createJasperPrint0(
			final AdProcessId adProcessId,
			final PInstanceId pinstanceId,
			final Language language)
	{
		final IReportServer server = serverSupplier.get();
		final ReportResult reportResult = server.report(adProcessId.getRepoId(), pinstanceId.getRepoId(), language.getAD_Language(), OutputType.JasperPrint);
		try
		{
			final ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(reportResult.getReportContent()));
			final JasperPrint jasperPrint = (JasperPrint)ois.readObject();
			return jasperPrint;
		}
		catch (IOException | ClassNotFoundException e)
		{
			throw new AdempiereException(
					"Caught " + e.getClass().getSimpleName() + " while trying to convert reports server data byte[] to JasperPrint", e);
		}
	}

	private ReportResult report(final AdProcessId adProcessId, final PInstanceId pinstanceId, final Language language, final OutputType outputType)
	{
		final IReportServer server = serverSupplier.get();
		return server.report(adProcessId.getRepoId(), PInstanceId.toRepoId(pinstanceId), language.getAD_Language(), outputType);
	}

	public ReportResult report(final ProcessInfo pi)
	{
		return report(pi, pi.getJRDesiredOutputType());
	}

	public ReportResult report(@NonNull final ProcessInfo pi, @Nullable final OutputType outputType)
	{
		// Make sure the ProcessInfo is persisted because we will need to access it's data (like AD_Table_ID/Record_ID etc)
		if (pi.getPinstanceId() == null)
		{
			Services.get(IADPInstanceDAO.class).saveProcessInfoOnly(pi);
		}

		final Language language = extractLanguage(pi);
		final OutputType outputTypeEffective = CoalesceUtil.coalesce(outputType, pi.getJRDesiredOutputType());
		return report(pi.getAdProcessId(), pi.getPinstanceId(), language, outputTypeEffective);
	}

	private IReportServer createReportServer()
	{
		final String serverClassname = Services.get(ISysConfigBL.class).getValue(SYSCONFIG_ReportsServerClass, SYSCONFIG_ReportsServerClass_DEFAULT);
		logger.info("Reports server classname: {}", serverClassname);

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null)
		{
			classLoader = getClass().getClassLoader();
		}
		try
		{
			final IReportServer server = (IReportServer)classLoader
					.loadClass(serverClassname)
					.newInstance();
			logger.info("Reports server instance: " + server);
			return server;
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException e)
		{
			throw new AdempiereException("Reports server class with name " + serverClassname + " could not be instantated", e)
					.appendParametersToMessage()
					.setParameter("jrClassname", serverClassname)
					.setParameter("classLoader", classLoader);
		}
	}

	/**
	 * Extracts reporting language from given {@link ProcessInfo}.
	 *
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
		// TODO: i think this one is no longer needed because we already checking this case in ProcessInfo.findReportingLanguage()
		if (lang == null)
		{
			final IRangeAwareParams parameterAsIParams = pi.getParameterAsIParams();
			final int bPartnerID = parameterAsIParams.getParameterAsInt(I_C_BPartner.COLUMNNAME_C_BPartner_ID, -1);
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
