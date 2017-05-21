package org.adempiere.ad.service.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;

import de.metas.i18n.Language;
import de.metas.logging.LogManager;

import org.adempiere.ad.persistence.EntityTypesCache;
import org.adempiere.ad.service.IADMessageDAO;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Message;
import org.compiere.model.M_Element;
import org.compiere.model.PO;
import org.compiere.model.X_AD_Message;
import org.compiere.util.CacheMgt;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Ini;

/**
 * Developer Model BL Implementation
 * 
 * @author tsa
 * @see http://dewiki908/mediawiki/index.php/02664:_Introduce_ADempiere_Developer_Mode_%282012040510000121%29
 */
public class DeveloperModeBL implements IDeveloperModeBL
{
	public static final DeveloperModeBL instance = new DeveloperModeBL();

	public static final String SYSCONFIG_DeveloperMode = "de.metas.adempiere.debug";
	
	private final Logger logger = LogManager.getLogger(getClass());

	protected DeveloperModeBL()
	{
		super();
	}

	@Override
	public boolean isEnabled()
	{
		//
		// Get the DeveloperMode sysconfig
		// NOTE: this method shall be as less disruptive as possible, so
		// if there is no database connection or retrieving the sysconfig fails for some reason simply return false,
		// but never ever fail.
		try
		{
			if (Adempiere.isUnitTestMode())
			{
				return true;
			}
			
			if (!DB.isConnected())
			{
				return false;
			}
			return Services.get(ISysConfigBL.class).getBooleanValue(SYSCONFIG_DeveloperMode, false);
		}
		catch (Exception e)
		{
			logger.warn("Failed retrieving the DeveloperMode sysconfig. Considering not enabled.", e);
			return false;
		}
	}

	@Override
	public boolean createMessageOrElement(final String adLanguage, final String text, final boolean checkMessage, final boolean checkElement)
	{
		final boolean[] retValue = new boolean[] { false };
		try
		{
			executeAsSystem(new ContextRunnable()
			{

				@Override
				public void run(Properties sysCtx)
				{
					retValue[0] = createMessageOrElement0(sysCtx, adLanguage, text, checkMessage, checkElement);
				}
			});
		}
		catch (final Exception e)
		{
			logger.error(e.getLocalizedMessage(), e);
			return false;
		}

		return retValue[0];
	}

	private boolean createMessageOrElement0(Properties ctx, final String adLanguage, final String text, boolean checkMessage, boolean checkElement)
	{
		final String trxName = null;

		final I_AD_Message message = checkMessage ? Services.get(IADMessageDAO.class).retrieveByValue(ctx, text) : null;
		if (message != null)
		{
			return checkInsertTrl(message, adLanguage);
		}

		final M_Element element = checkElement ? M_Element.get(ctx, text) : null;
		if (element != null)
		{
			return checkInsertTrl(element, adLanguage);
		}

		boolean createMessage = checkMessage && isValidMessageValue(text);
		boolean createElement = checkElement && isValidColumnName(text);

		if (!createElement && !createMessage)
		{
			return false;
		}

		if (createElement && createMessage)
		{
			// TODO: ask developer what to do
			createMessage = true;
			createElement = false;
		}

		if (createMessage)
		{
			final I_AD_Message messageNew = InterfaceWrapperHelper.create(ctx, I_AD_Message.class, trxName);
			messageNew.setValue(text);
			messageNew.setMsgType(X_AD_Message.MSGTYPE_Information);
			messageNew.setMsgText(text);
			messageNew.setEntityType(getEntityType(ctx));
			// The save will trigger CCache reset for "AD_Message" which will clear message from Msg class
			InterfaceWrapperHelper.save(messageNew);
			logger.warn("Created: " + messageNew + ", Value=" + messageNew.getValue() + ", EntityType=" + messageNew.getEntityType(), new Exception());
		}
		if (createElement)
		{
			final M_Element elementNew = new M_Element(ctx, 0, trxName);
			elementNew.setColumnName(text);
			elementNew.setName(text);
			elementNew.setPrintName(text);
			elementNew.setEntityType(getEntityType(ctx));
			elementNew.saveEx();
			logger.warn("Created: " + element + ", ColumnName=" + elementNew.getColumnName() + ", EntityType=" + elementNew.getEntityType(), new Exception());
		}
		return createMessage || createElement;
	}

	/**
	 * Check and insert translation records if they don't exist
	 * 
	 * @param model
	 * @param adLanguage
	 * @return true if translations were inserted or they are already available
	 */
	private boolean checkInsertTrl(Object model, String adLanguage)
	{
		if (hasTranslation(model, adLanguage))
		{
			return true;
		}

		final PO po = InterfaceWrapperHelper.getPO(model);
		final boolean changed = po.insertTranslations();
		if (changed)
		{
			// Trigger org.compiere.util.Msg cache reset
			CacheMgt.get().reset(I_AD_Message.Table_Name);
		}
		return changed;
	}

	private boolean isValidColumnName(String columnName)
	{
		return !Check.isEmpty(columnName, true)
				&& columnName.indexOf(" ") == -1
				&& columnName.indexOf(".") == -1
				&& columnName.indexOf("?") == -1
				&& columnName.indexOf("{") == -1
				&& columnName.indexOf("}") == -1;
	}

	private boolean isValidMessageValue(String adMessage)
	{
		return !Check.isEmpty(adMessage, true)
				&& adMessage.indexOf(" ") == -1;
	}

	private String getEntityType(Properties ctx)
	{
		final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		boolean skipStackTraceElement = true;
		for (StackTraceElement ste : stackTrace)
		{
			final String classnameFQ = ste.getClassName();
			if (classnameFQ == null)
			{
				continue;
			}

			if (classnameFQ.startsWith(getClass().getName()))
			{
				skipStackTraceElement = false;
				continue;
			}
			if (skipStackTraceElement
					|| classnameFQ.startsWith("java.lang.")
					|| classnameFQ.startsWith("sun."))
			{
				continue;
			}

			int idx = classnameFQ.lastIndexOf(".");
			if (idx <= 0)
				continue;

			final String packageName = classnameFQ.substring(0, idx);
			// final String className = classnameFQ.substring(idx);

			String entityType = null;
			//
			// Get EntityType by PackageName
			final List<String> entityTypes = EntityTypesCache.instance.getEntityTypeNames();
			for (String et : entityTypes)
			{
				String modelPackage = EntityTypesCache.instance.getModelPackage(et);
				if (Check.isEmpty(modelPackage, true)
						|| "org.compiere.model".equals(modelPackage)
						|| "org.adempiere.model".equals(modelPackage))
				{
					continue;
				}
				if (modelPackage.endsWith(".model"))
				{
					modelPackage = modelPackage.substring(0, modelPackage.length() - ".model".length());
				}
				if (packageName.equals(modelPackage) || packageName.startsWith(modelPackage + "."))
				{
					entityType = et;
					break;
				}
			}

			if (entityType != null)
			{
				return entityType;
			}

		}

		String entityType = Env.getContext(ctx, "#EntityType");
		if (Check.isEmpty(entityType))
		{
			entityType = "U";
		}
		return entityType;
	}

	@Override
	public void executeAsSystem(ContextRunnable runnable)
	{
		final Properties sysCtx = Env.createSysContext(Env.getCtx());

		final String logMigrationScriptsOld = Ini.getProperty(Ini.P_LOGMIGRATIONSCRIPT);
		DB.saveConstraints();
		try (final IAutoCloseable contextRestorer = Env.switchContext(sysCtx))
		{
			DB.getConstraints().addAllowedTrxNamePrefix(ITrx.TRXNAME_PREFIX_LOCAL);
			DB.getConstraints().incMaxTrx(1);
			
			Ini.setProperty(Ini.P_LOGMIGRATIONSCRIPT, true);

			runnable.run(sysCtx);
		}
		finally
		{
			Ini.setProperty(Ini.P_LOGMIGRATIONSCRIPT, logMigrationScriptsOld);
			
			DB.restoreConstraints();
		}
	}

	private boolean hasTranslation(Object model, String adLanguage)
	{
		if (Language.isBaseLanguage(adLanguage))
		{
			return true;
		}

		final String trxName = null;
		final String tableName = InterfaceWrapperHelper.getModelTableName(model);
		final int recordId = InterfaceWrapperHelper.getId(model);
		final String sql = "SELECT COUNT(*) FROM " + tableName + "_Trl WHERE " + tableName + "_ID=? AND AD_Language=?";

		int count = DB.getSQLValueEx(trxName, sql, recordId, adLanguage);
		return count == 1;
	}
}
