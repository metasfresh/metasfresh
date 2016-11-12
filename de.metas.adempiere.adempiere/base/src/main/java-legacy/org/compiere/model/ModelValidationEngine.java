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
package org.compiere.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.script.ScriptEngine;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.modelvalidator.AnnotatedModelInterceptorFactory;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.modelvalidator.IModelInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.ModelInterceptor2ModelValidatorWrapper;
import org.adempiere.ad.modelvalidator.ModelInterceptorInitException;
import org.adempiere.ad.persistence.EntityTypesCache;
import org.adempiere.ad.security.IUserLoginListener;
import org.adempiere.ad.service.IADTableScriptValidatorDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxRunConfig;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableFail;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableSuccess;
import org.adempiere.ad.trx.api.ITrxRunConfig.TrxPropagation;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.impexp.IImportProcess;
import org.adempiere.impexp.IImportValidator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.processing.model.MADProcessablePO;
import org.adempiere.processing.service.IProcessingService;
import org.adempiere.service.IClientDAO;
import org.adempiere.util.Check;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.compiere.Adempiere.RunMode;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.KeyNamePair;
import org.compiere.util.TrxRunnableAdapter;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;

/**
 * Model Validation Engine
 *
 * @author Jorg Janke
 * @version $Id: ModelValidationEngine.java,v 1.2 2006/07/30 00:58:38 jjanke Exp $
 *
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 *         <ul>
 *         <li>FR [ 1670025 ] ModelValidator.afterLoadPreferences will be useful
 *         <li>BF [ 1679692 ] fireDocValidate doesn't treat exceptions as errors
 *         <li>FR [ 1724662 ] Support Email should contain model validators info
 *         <li>FR [ 2788276 ] Data Import Validator https://sourceforge.net/tracker/?func=detail&aid=2788276&group_id=176962&atid=879335
 *         <li>BF [ 2804135 ] Global FactsValidator are not invoked https://sourceforge.net/tracker/?func=detail&aid=2804135&group_id=176962&atid=879332
 *         <li>BF [ 2819617 ] NPE if script validator rule  returns null https://sourceforge.net/tracker/?func=detail&aid=2819617&group_id=176962&atid=879332
 *         </ul>
 * @author Tobias Schoeneberg, t.schoeneberg@metas.de
 *         <li>FR [ADEMPIERE-28] ModelValidatorException https://adempiere.atlassian.net/browse/ADEMPIERE-28
 */
public class ModelValidationEngine implements IModelValidationEngine
{

	/**
	 * Get Singleton
	 *
	 * @return engine
	 */
	public synchronized static ModelValidationEngine get()
	{
		if (s_engine == null)
		{
			// NOTE: we need to instantiate and assign it to static variable immediatelly
			// else, in init() method, this get() is called indirectly which leads us to have 2 ModelValidationEngine instances
			s_engine = new ModelValidationEngine();

			s_engine.init(); // metas
		}
		return s_engine;
	}	// get

	/**
	 * Engine Singleton. Never use it directly, but use {@link #get()} instead.
	 */
	private static ModelValidationEngine s_engine = null;

	/**
	 * Sets the list of EntityTypes for which the model interceptors shall be loaded.
	 *
	 * You can provide a custom list of entity types, or you can use a predefined one:
	 * <ul>
	 * <li> {@link #INITENTITYTYPE_Minimal} - only the core entity types. It is used when we need to start adempiere from other tools and we don't want to start the servers, processors and stuff.
	 * </ul>
	 *
	 * @param initEntityTypes
	 */
	public synchronized static void setInitEntityTypes(final List<String> initEntityTypes)
	{
		Check.assumeNotNull(initEntityTypes, "initEntityTypes not null");
		if (s_engine != null)
		{
			final AdempiereException ex = new AdempiereException("Setting InitEntityTypes after the model validation engine was initialized has no effect.");
			log.warn(ex.getLocalizedMessage(), ex);
		}

		_initEntityTypes = ImmutableList.copyOf(initEntityTypes);
		log.info("IMPORTANT: Only following ADempiere modules will be activated: " + _initEntityTypes);
	}

	/** @return entity types which shall be initialized or <code>null</code> if ALL of them shall be initialized */
	private synchronized static List<String> getInitEntityTypes()
	{
		return _initEntityTypes;
	}

	private static List<String> _initEntityTypes = null;
	public static final List<String> INITENTITYTYPE_Minimal = ImmutableList.of(
			"D", // Dictionary
			"C", // Adempiere
			"U", // User maintained
			"A" // Applications
	);

	/** List of errors that we encounted while initializing the model interceptors */
	private static final List<ModelInterceptorInitException> _modelInterceptorInitErrors = new ArrayList<>();

	/**************************************************************************
	 * Constructor. Creates Model Validators
	 */
	private ModelValidationEngine()
	{
		super();
		// metas: tsa: begin: break this in 2 parts because if the get() method is called during initialization we will end with multiple instances of ModelVaidationEngine
	}

	private void init()
	{
		// metas: tsa: end
		// Load global validators

		// Create a dummy context to be used when retrieving data
		// NOTE: we do this because we don't want to affect nor to relly on current context (which might not exist)
		final Properties ctx = Env.newTemporaryCtx();

		final List<String> initEntityTypes = getInitEntityTypes(); // metas: 03023

		final I_AD_Client adClient = null; // all clients
		String className = null; // className of current model interceptor which is about to be initialized
		try
		{
			final List<I_AD_ModelValidator> modelValidators = retrieveModelValidators(ctx);
			for (final I_AD_ModelValidator modelValidator : modelValidators)
			{
				className = modelValidator.getModelValidationClass();
				if (className == null || className.length() == 0)
				{
					continue;
				}


				//
				// Skip loading the model interceptor if entity type is not active
				final String entityType = modelValidator.getEntityType();
				if (!EntityTypesCache.instance.isActive(entityType))
				{
					log.info("Skip " + className + " (EntityType '" + entityType + "' is not active)");
				}

				//
				// Skip model validator if entity type is not in list of allowed entity types (task 03023)
				if (initEntityTypes != null && !initEntityTypes.contains(entityType))
				{
					log.info("Skip " + className + " (EntityType '" + entityType + "' not in " + initEntityTypes + ")");
					continue;
				}

				loadModuleActivatorClass(adClient, className);
			}
		}
		catch (Exception e)
		{
			addModelInterceptorInitError(className, adClient, e);
		}

		// Go through all Clients and start Validators
		for (final I_AD_Client client : Services.get(IClientDAO.class).retrieveAllClients(ctx))
		{
			final String classNames = client.getModelValidationClasses();
			if (classNames == null || classNames.trim().length() == 0)
				continue;
			loadModuleActivatorClasses(client, classNames.trim());
		}
		// logging to db will try to init ModelValidationEngine again!
		// log.info(toString());
		// System.out.println(toString());

		// metas: 02504: begin
		if (!Ini.isClient() && hasInitErrors())
		{
			logModelInterceptorInitErrors();
			if (isFailOnMissingModelInteceptors())
			{
				System.exit(1);
			}
		}
		// metas: 02504: end
	}	// ModelValidatorEngine

	private final void addModelInterceptorInitError(final String modelInterceptorClassName, final I_AD_Client client, final Throwable error)
	{
		// logging to db will try to init ModelValidationEngine again!
		// log.warn(e.getLocalizedMessage());
		// System.err.println(e.getLocalizedMessage());

		final ModelInterceptorInitException initException = new ModelInterceptorInitException(modelInterceptorClassName, client, error);
		_modelInterceptorInitErrors.add(initException);
	}

	private final boolean hasInitErrors()
	{
		return !_modelInterceptorInitErrors.isEmpty();
	}

	private final String getInitErrorsAsString()
	{
		final StringBuilder msg = new StringBuilder();
		for (final ModelInterceptorInitException initError : _modelInterceptorInitErrors)
		{
			if (msg.length() > 0)
			{
				msg.append("\n");
			}
			msg.append(initError.getLocalizedMessage());
		}
		return msg.toString();
	}

	private final void logModelInterceptorInitErrors()
	{
		if (_modelInterceptorInitErrors.isEmpty())
		{
			return;
		}

		final boolean isFail = isFailOnMissingModelInteceptors();
		System.err.println("\n\n=====================================================================================================================");
		System.err.println("Got following erros on model interceptors initialization and AD_System.IsFailOnMissingModelValidator=" + isFail + ":");
		int errorNo = 0;
		for (final ModelInterceptorInitException initError : _modelInterceptorInitErrors)
		{
			errorNo++;
			System.err.print(errorNo + ". ");
			initError.printStackTrace(System.err);
		}
	}

	private final boolean isFailOnMissingModelInteceptors()
	{
		final I_AD_System system = MSystem.get(Env.getCtx());
		final boolean isFail = system.isFailOnMissingModelValidator();
		return isFail;
	}

	private List<I_AD_ModelValidator> retrieveModelValidators(final Properties ctx)
	{
		final IQueryBuilder<I_AD_ModelValidator> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_ModelValidator.class, ctx, ITrx.TRXNAME_None);

		final ICompositeQueryFilter<I_AD_ModelValidator> filters = queryBuilder.getFilters();
		filters.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_AD_ModelValidator.COLUMNNAME_SeqNo)
				.addColumn(I_AD_ModelValidator.COLUMNNAME_AD_ModelValidator_ID);

		return queryBuilder.create()
				.list();
	}

	private void loadModuleActivatorClasses(final I_AD_Client client, final String classNames)
	{
		StringTokenizer st = new StringTokenizer(classNames, ";");
		while (st.hasMoreTokens())
		{
			String className = null;
			try
			{
				className = st.nextToken();
				if (className == null)
					continue;
				className = className.trim();
				if (className.length() == 0)
					continue;
				//
				loadModuleActivatorClass(client, className);
			}
			catch (Exception e)
			{
				addModelInterceptorInitError(className, client, e);
			}
		}
	}

	private void loadModuleActivatorClass(final I_AD_Client client, final String className)
	{
		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		try
		{
			// Load main activator
			{
				final Class<?> clazz = classLoader.loadClass(className);
				final Object validatorObj = clazz.newInstance();
				addModelValidator(validatorObj, client);
			}

			//
			// Load swing module activation (if any)
			final RunMode runMode = Ini.getRunMode();
			if (runMode == RunMode.SWING_CLIENT)
			{
				final Class<?> swingModuleActivatorClass = getModuleActivatorClassOrNull(className + "_SwingUI");
				if (swingModuleActivatorClass != null)
				{
					final Object validatorObj = swingModuleActivatorClass.newInstance();
					addModelValidator(validatorObj, client);
				}
			}
			//
			// Load zkwebui module activation (if any)
			else if(runMode == RunMode.WEBUI)
			{
				final Class<?> zkwebuiModuleActivatorClass = getModuleActivatorClassOrNull(className + "_ZkwebUI");
				if (zkwebuiModuleActivatorClass != null)
				{
					final Object validatorObj = zkwebuiModuleActivatorClass.newInstance();
					addModelValidator(validatorObj, client);
				}
			}
		}
		catch (Exception e)
		{
			addModelInterceptorInitError(className, client, e);
		}

	}

	/**
	 * Loads module activator class for given name.
	 *
	 * If the class was not found this method will return null and the exception will be silently swallowed.
	 *
	 * @param classname
	 * @return module activator class or null if class was not found.
	 */
	private static final Class<?> getModuleActivatorClassOrNull(final String classname)
	{
		try
		{
			final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			return classLoader.loadClass(classname);
		}
		catch (ClassNotFoundException e)
		{
			// silently ignore it
		}

		return null;
	}

	/** Logger */
	private static Logger log = LogManager.getLogger(ModelValidationEngine.class);
	// /** Change Support */
	// private VetoableChangeSupport m_changeSupport = new VetoableChangeSupport(this);

	/** Validators */
	private ArrayList<ModelValidator> m_validators = new ArrayList<>();
	/** Model Change Listeners */
	private Hashtable<String, ArrayList<ModelValidator>> m_modelChangeListeners = new Hashtable<>();
	/** Document Validation Listeners */
	private Hashtable<String, ArrayList<ModelValidator>> m_docValidateListeners = new Hashtable<>();
	/** Data Import Validation Listeners */
	private Hashtable<String, ArrayList<IImportValidator>> m_impValidateListeners = new Hashtable<>();

	private ArrayList<ModelValidator> m_globalValidators = new ArrayList<>();

	/**
	 * Contains model validators for subsequent processing. The boolean value tells if the subsequent processing takes place directly when fireModelChange() is invoked with this type (
	 * <code>true</code>) or later on (<code>false</code>).
	 */
	private Map<ModelValidator, Boolean> m_modelChangeSubsequent = Collections.synchronizedMap(new HashMap<ModelValidator, Boolean>());

	/**
	 * Initialize and add validator
	 *
	 * @param validator
	 * @param client
	 */
	private void initialize(final ModelValidator validator, final I_AD_Client client)
	{
		if (client == null)
			registerGlobal(validator);
		m_validators.add(validator);

		final MClient clientPO = LegacyAdapters.convertToPO(client);
		validator.initialize(this, clientPO);
	}	// initialize

	/**
	 * Called when login is complete
	 *
	 * @param AD_Client_ID client
	 * @param AD_Org_ID org
	 * @param AD_Role_ID role
	 * @param AD_User_ID user
	 * @return error message or null
	 */
	public String loginComplete(int AD_Client_ID, int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		for (int i = 0; i < m_validators.size(); i++)
		{
			ModelValidator validator = m_validators.get(i);
			if (appliesFor(validator, AD_Client_ID))
			{
				String error = validator.login(AD_Org_ID, AD_Role_ID, AD_User_ID);
				if (error != null && error.length() > 0)
					return error;
			}
		}

		// now process the script model validator login
		List<MRule> loginRules = MRule.getModelValidatorLoginRules(Env.getCtx());
		if (loginRules != null)
		{
			for (MRule loginRule : loginRules)
			{
				// currently just JSR 223 supported
				if (loginRule.getRuleType().equals(MRule.RULETYPE_JSR223ScriptingAPIs)
						&& loginRule.getEventType().equals(MRule.EVENTTYPE_ModelValidatorLoginEvent))
				{
					String error;
					try
					{
						ScriptEngine engine = loginRule.getScriptEngine();

						MRule.setContext(engine, Env.getCtx(), 0);  // no window
						// now add the method arguments to the engine
						engine.put(MRule.ARGUMENTS_PREFIX + "Ctx", Env.getCtx());
						engine.put(MRule.ARGUMENTS_PREFIX + "AD_Client_ID", AD_Client_ID);
						engine.put(MRule.ARGUMENTS_PREFIX + "AD_Org_ID", AD_Org_ID);
						engine.put(MRule.ARGUMENTS_PREFIX + "AD_Role_ID", AD_Role_ID);
						engine.put(MRule.ARGUMENTS_PREFIX + "AD_User_ID", AD_User_ID);

						Object retval = engine.eval(loginRule.getScript());
						error = (retval == null ? "" : retval.toString());
					}
					catch (Exception e)
					{
						e.printStackTrace();
						error = e.toString();
					}
					if (error != null && error.length() > 0)
						return error;
				}
			}
		}
		//

		// metas: 02504: begin: on login complete, log missingModelValidationMessage errors, but only in client mode.
		// In server mode, those errors were already logged in init() method
		final boolean hasInitErrors = hasInitErrors();
		if (hasInitErrors && Ini.isClient())
		{
			logModelInterceptorInitErrors();
		}
		// metas: 02504: end

		if (AD_User_ID == 0 && AD_Role_ID == 0)
		{
			; // don't validate for user system on role system
		}
		else if (hasInitErrors)
		{
			if (isFailOnMissingModelInteceptors())
			{
				return "Errors on model interceptor initialization:\n" + getInitErrorsAsString();
			}
		}
		return null;
	}	// loginComplete

	private List<IUserLoginListener> getUserLoginListener(final int adClientId)
	{
		final List<IUserLoginListener> listeners = new ArrayList<>();
		for (int i = 0; i < m_validators.size(); i++)
		{
			final ModelValidator validator = m_validators.get(i);

			if (!(validator instanceof IUserLoginListener))
			{
				continue;
			}

			if (!appliesFor(validator, adClientId))
			{
				continue;
			}

			final IUserLoginListener loginListener = (IUserLoginListener)validator;
			listeners.add(loginListener);
		}
		return listeners;
	}

	public void fireBeforeLogout(final I_AD_Session session)
	{
		final int adClientId = session.getAD_Client_ID();
		final List<IUserLoginListener> listeners = getUserLoginListener(adClientId);
		for (final IUserLoginListener listener : listeners)
		{
			try
			{
				listener.beforeLogout(session);
			}
			catch (Exception e)
			{
				log.error(e.getLocalizedMessage(), e);
			}
		}
	}

	public void fireAfterLogout(final I_AD_Session session)
	{
		final int adClientId = session.getAD_Client_ID();
		final List<IUserLoginListener> listeners = getUserLoginListener(adClientId);
		for (final IUserLoginListener listener : listeners)
		{
			try
			{
				listener.afterLogout(session);
			}
			catch (Exception e)
			{
				log.error(e.getLocalizedMessage(), e);
			}
		}
	}

	/**************************************************************************
	 * Add Model Change Listener
	 *
	 * @param tableName table name
	 * @param listener listener
	 */
	public void addModelChange(String tableName, ModelValidator listener)
	{
		if (tableName == null || listener == null)
			return;
		//
		if (listener.getAD_Client_ID() < 0)
			registerGlobal(listener);
		String propertyName = getPropertyName(tableName, listener);
		ArrayList<ModelValidator> list = m_modelChangeListeners.get(propertyName);
		if (list == null)
		{
			list = new ArrayList<>();
			list.add(listener);
			m_modelChangeListeners.put(propertyName, list);
		}
		else
		{
			// metas: add listener only if is not already added
			if (!list.contains(listener))
			{
				list.add(listener);
			}
			else
			{
				log.debug("Listener " + listener + " already added for " + propertyName);
			}
		}
	}	// addModelValidator

	@Override
	public void addModelChange(String tableName, IModelInterceptor interceptor)
	{
		if (interceptor == null)
		{
			return;
		}

		final ModelValidator modelValidator = ModelInterceptor2ModelValidatorWrapper.wrapIfNeeded(interceptor);
		this.addModelChange(tableName, modelValidator);
	}

	/**
	 * Remove Model Change Listener
	 *
	 * @param tableName table name
	 * @param listener listener
	 */
	public void removeModelChange(String tableName, ModelValidator listener)
	{
		if (tableName == null || listener == null)
			return;
		String propertyName = getPropertyName(tableName, listener);
		ArrayList<ModelValidator> list = m_modelChangeListeners.get(propertyName);
		if (list == null)
			return;
		list.remove(listener);
		if (list.size() == 0)
			m_modelChangeListeners.remove(propertyName);
	}	// removeModelValidator

	@Override
	public void removeModelChange(String tableName, IModelInterceptor interceptor)
	{
		if (interceptor == null)
		{
			return;
		}

		final ModelValidator modelValidator = ModelInterceptor2ModelValidatorWrapper.wrapIfNeeded(interceptor);
		this.removeModelChange(tableName, modelValidator);
	}

	/**
	 * Fire Model Change. Call modelChange method of added validators
	 *
	 * @param po persistent objects
	 * @param type ModelValidator.TYPE_*
	 * @return error message or NULL for no veto
	 */
	public void fireModelChange(final PO po, final int changeType)
	{
		if (po == null || m_modelChangeListeners.isEmpty())
		{
			return;
		}
		final Boolean dynAttributeDoNotFire = InterfaceWrapperHelper.getDynAttribute(po, DYNATTR_DO_NOT_INVOKE_ON_MODEL_CHANGE);
		if (dynAttributeDoNotFire != null && dynAttributeDoNotFire)
		{
			return; // nothing to do
		}

		//
		// Make sure model if valid before firing the listeners
		assertModelValidBeforeFiringEvent(po, false, changeType); // isDocumentValidateEvent=false

		boolean haveInterceptors = false;

		//
		// Retrieve system level model interceptors
		final String propertyNameSystem = getPropertyName(po.get_TableName());
		final List<ModelValidator> interceptorsSystem = m_modelChangeListeners.get(propertyNameSystem);
		final boolean haveSystemInterceptors = interceptorsSystem != null && !interceptorsSystem.isEmpty();
		haveInterceptors = haveInterceptors || haveSystemInterceptors;

		//
		// Retrieve client level model interceptors
		final String propertyNameClient = getPropertyName(po.get_TableName(), po.getAD_Client_ID());
		final List<ModelValidator> interceptorsClient = m_modelChangeListeners.get(propertyNameClient);
		final boolean haveClientInterceptors = interceptorsClient != null && !interceptorsClient.isEmpty();
		haveInterceptors = haveInterceptors || haveClientInterceptors;

		//
		// Retrieve script interceptors
		//
		// now process the script model validator for this event
		// metas: tsa: 02380: First check if changeType is available in tableEventValidators
		// FIXME: refactor it and have it as a regular model validator; then remove it from here
		final List<I_AD_Table_ScriptValidator> scriptValidators;
		final boolean haveScriptingInterceptors;
		if (ModelValidator.tableEventValidators.length > changeType)
		{
			scriptValidators = Services.get(IADTableScriptValidatorDAO.class).retrieveTableScriptValidators(
					po.getCtx(),
					po.get_Table_ID(),
					ModelValidator.tableEventValidators[changeType]);
			haveScriptingInterceptors = scriptValidators != null && !scriptValidators.isEmpty();
		}
		else
		{
			scriptValidators = null;
			haveScriptingInterceptors = false;
		}
		haveInterceptors = haveInterceptors || haveScriptingInterceptors;

		//
		// In case there are no interceptors, do nothing
		if (!haveInterceptors)
		{
			return;
		}

		//
		// Execute interceptors
		final String trxName = po.get_TrxName();
		executeInTrx(trxName, changeType, new Runnable()
		{
			@Override
			public void run()
			{
				fireModelChange0(po, changeType, interceptorsSystem, interceptorsClient, scriptValidators);
			}
		});
	}	// fireModelChange

	private final void executeInTrx(final String trxName, final int changeTypeOrDocTiming, final Runnable runnable)
	{
		final boolean runInTrx = changeTypeOrDocTiming != ModelValidator.TYPE_BEFORE_SAVE_TRX;

		if (runInTrx)
		{
			// NOTE: we are wrapping it to a transaction (savepoint) because we also want to make sure Thread TrxName is set
			final ITrxManager trxManager = Services.get(ITrxManager.class);

			final ITrxRunConfig trxRunConfig = trxManager.newTrxRunConfigBuilder()
					.setTrxPropagation(TrxPropagation.NESTED) // we expect to run into a transaction at this point
					.setOnRunnableSuccess(OnRunnableSuccess.DONT_COMMIT)
					.setOnRunnableFail(OnRunnableFail.DONT_ROLLBACK) // OnRunnableFail: don't rollback => no savepoint shall be created (avoid HUGE performance issues)
					.build();
			trxManager.run(trxName, trxRunConfig, new TrxRunnableAdapter()
			{
				@Override
				public void run(String localTrxName) throws Exception
				{
					runnable.run();
				}
			});
		}
		//
		// Execute without wrapping to a particular transaction
		else
		{
			runnable.run();
		}

	}

	private final void fireModelChange0(final PO po, final int changeType,
			List<ModelValidator> interceptorsSystem,
			List<ModelValidator> interceptorsClient,
			List<I_AD_Table_ScriptValidator> scriptValidators)
	{
		if (interceptorsSystem != null)
		{
			// ad_entitytype.modelvalidationclasses
			fireModelChange(po, changeType, interceptorsSystem);
		}

		if (interceptorsClient != null)
		{
			// ad_client.modelvalidationclasses
			fireModelChange(po, changeType, interceptorsClient);
		}

		//
		// now process the script model validator for this event
		// metas: tsa: 02380: First check if changeType is available in tableEventValidators
		// FIXME: refactor it and have it as a regular model validator; then remove it from here
		{
			fireModelChangeForScriptValidators(po,
					X_AD_Rule.EVENTTYPE_ModelValidatorTableEvent,
					changeType,
					scriptValidators);
		}
	}

	private final void fireModelChangeForScriptValidators(
			final PO po,
			final String ruleEventType,
			final int changeTypeOrDocTiming,
			final List<I_AD_Table_ScriptValidator> scriptValidators)
	{
		// if there are no script validators => do nothing
		if (scriptValidators == null || scriptValidators.isEmpty())
		{
			return;
		}

		final String ruleEventModelValidator;
		if (X_AD_Rule.EVENTTYPE_ModelValidatorTableEvent.equals(ruleEventType))
		{
			final int changeType = changeTypeOrDocTiming;
			ruleEventModelValidator = ModelValidator.tableEventValidators[changeType];
		}
		else if (X_AD_Rule.EVENTTYPE_ModelValidatorDocumentEvent.equals(ruleEventType))
		{
			final int docTiming = changeTypeOrDocTiming;
			ruleEventModelValidator = ModelValidator.documentEventValidators.get(docTiming);
		}
		else
		{
			throw new IllegalArgumentException("Unknown rule EventType: " + ruleEventType);
		}

		for (final I_AD_Table_ScriptValidator scriptValidator : scriptValidators)
		{
			final MRule rule = MRule.get(po.getCtx(), scriptValidator.getAD_Rule_ID());
			// currently just JSR 223 supported
			if (rule != null
					&& rule.isActive()
					&& rule.getRuleType().equals(X_AD_Rule.RULETYPE_JSR223ScriptingAPIs)
					&& rule.getEventType().equals(ruleEventType) // MRule.EVENTTYPE_ModelValidatorTableEvent)
			)
			{
				try
				{
					final ScriptEngine engine = rule.getScriptEngine();
					final int windowNo = po.get_WindowNo();
					MRule.setContext(engine, po.getCtx(), windowNo);
					// now add the method arguments to the engine
					engine.put(MRule.ARGUMENTS_PREFIX + "Ctx", po.getCtx());
					engine.put(MRule.ARGUMENTS_PREFIX + "PO", po);
					engine.put(MRule.ARGUMENTS_PREFIX + "Type", changeTypeOrDocTiming);
					engine.put(MRule.ARGUMENTS_PREFIX + "Event", ruleEventModelValidator);

					final Object retval = engine.eval(rule.getScript());
					final String error = (retval == null ? "" : retval.toString());
					if (error != null && error.length() > 0)
					{
						throw new AdempiereException(error);
					}
				}
				catch (Exception e)
				{
					throw AdempiereException.wrapIfNeeded(e);
				}
			}
		}
	}

	/**
	 * Makes sure given <code>model</code> is valid before firing listeners.<br>
	 * Logs (but doesn't throw!) an exception if the given model has no trxName.
	 *
	 * Background: if trxName is null when firing the events that is usually some development/framework error.<br>
	 * We rely on this trxName when creating <b>further</b> objects, so if trxName is <code>null</code> then we can't cleanly roll back and will end in some inconsistency fxxx-up in case something
	 * fails.
	 * <p>
	 * Note: In future we might throw the exception instead of just logging it.
	 *
	 * @param model
	 */
	private final void assertModelValidBeforeFiringEvent(final PO model, final boolean isDocumentValidateEvent, final int timing)
	{
		Check.assumeNotNull(model, "model not null");

		//
		// Validate PO's transaction
		final boolean isBeforeSaveTrxEvent = !isDocumentValidateEvent && timing == ModelValidator.TYPE_BEFORE_SAVE_TRX;
		if (!isBeforeSaveTrxEvent // Skip BEFORE_SAVE_TRX events because those are always out of transaction
		)
		{
			final String trxName = model.get_TrxName();
			if (Services.get(ITrxManager.class).isNull(trxName))
			{
				final Object event = isDocumentValidateEvent ? DocTimingType.valueOf(timing) : ModelChangeType.valueOf(timing);
				final AdempiereException ex = new AdempiereException("When firing " + event + " event, PO shall have a trxName set."
						+ " Ignore it, but check the log for further issues."
						+ "\n PO: " + model
						+ "\n Event: " + event
						+ "\n trxName: " + trxName);
				log.warn(ex.getLocalizedMessage(), ex);
			}
		}
	}

	private final void fireModelChange(final PO po, final int changeType, final List<ModelValidator> list)
	{
		for (final ModelValidator validator : list)
		{
			try
			{
				if (appliesFor(validator, po.getAD_Client_ID()))
				{
					if (changeType == ModelValidator.TYPE_SUBSEQUENT)
					{
						if (m_modelChangeSubsequent.containsKey(validator))
						{
							// create a queue record
							final MADProcessablePO processablePO = MADProcessablePO.createOrRetrieveFor(po, validator);

							if (m_modelChangeSubsequent.get(validator))
							{
								// process 'po' right now. If a problem occurs, record it in 'processablePO'
								Services.get(IProcessingService.class).process(processablePO, null);
							}
						}
					}
					else
					{
						// the default cause
						String error = validator.modelChange(po, changeType);
						if (error != null && error.length() > 0)
						{
							throw new AdempiereException(error);
						}
					}
				}
			}
			catch (Exception e)
			{
				throw AdempiereException.wrapIfNeeded(e);
			}
		}
	}

	/**************************************************************************
	 * Add Document Validation Listener
	 *
	 * @param tableName table name
	 * @param listener listener
	 */
	public void addDocValidate(String tableName, ModelValidator listener)
	{
		if (tableName == null || listener == null)
			return;
		//
		if (listener.getAD_Client_ID() < 0)
			registerGlobal(listener);
		String propertyName = getPropertyName(tableName, listener);
		ArrayList<ModelValidator> list = m_docValidateListeners.get(propertyName);
		if (list == null)
		{
			list = new ArrayList<>();
			list.add(listener);
			m_docValidateListeners.put(propertyName, list);
		}
		else if (!list.contains(listener))
		{
			list.add(listener);
		}
	}	// addDocValidate

	@Override
	public void addDocValidate(String tableName, IModelInterceptor interceptor)
	{
		if (interceptor == null)
		{
			return;
		}

		final ModelValidator modelValidator = ModelInterceptor2ModelValidatorWrapper.wrapIfNeeded(interceptor);
		this.addDocValidate(tableName, modelValidator);
	}

	/**
	 * Remove Document Validation Listener
	 *
	 * @param tableName table name
	 * @param listener listener
	 */
	public void removeDocValidate(String tableName, ModelValidator listener)
	{
		if (tableName == null || listener == null)
			return;
		String propertyName = getPropertyName(tableName, listener);
		ArrayList<ModelValidator> list = m_docValidateListeners.get(propertyName);
		if (list == null)
			return;
		list.remove(listener);
		if (list.size() == 0)
			m_docValidateListeners.remove(propertyName);
	}	// removeDocValidate

	/**
	 * Fire Document Validation. Call docValidate method of added validators
	 *
	 * @param model if <code>null</code> or if {@link InterfaceWrapperHelper#getPO(Object)} returns <code>null</code> for the given value, then the method will do nothing.
	 * @param docTiming see ModelValidator.TIMING_ constants
	 * @return always returns <code>null</code>; we keep this string return type only for legacy purposes (when the error message was returned)
	 * @throws AdempiereException in case of failure
	 */
	public String fireDocValidate(final Object model, final int docTiming)
	{
		if (model == null)
		{
			return null; // avoid InterfaceWrapperHelper from throwing an exception under any circumstances
		}

		final PO po = InterfaceWrapperHelper.getPO(model);
		if (po == null || m_docValidateListeners.isEmpty())
		{
			return null;
		}
		//
		// Make sure model if valid before firing the listeners
		assertModelValidBeforeFiringEvent(po, true, docTiming); // isDocumentValidateEvent=true

		boolean haveInterceptors = false;

		//
		// Retrieve system level model interceptors
		final String propertyNameSystem = getPropertyName(po.get_TableName());
		final List<ModelValidator> interceptorsSystem = m_docValidateListeners.get(propertyNameSystem);
		final boolean haveSystemInterceptors = interceptorsSystem != null && !interceptorsSystem.isEmpty();
		haveInterceptors = haveInterceptors || haveSystemInterceptors;

		//
		// Retrieve client level model interceptors
		final String propertyNameClient = getPropertyName(po.get_TableName(), po.getAD_Client_ID());
		final List<ModelValidator> interceptorsClient = m_docValidateListeners.get(propertyNameClient);
		final boolean haveClientInterceptors = interceptorsClient != null && !interceptorsClient.isEmpty();
		haveInterceptors = haveInterceptors || haveClientInterceptors;

		//
		// Retrieve script interceptors
		//
		// now process the script model validator for this event
		// metas: tsa: 02380: First check if changeType is available in tableEventValidators
		// FIXME: refactor it and have it as a regular model validator; then remove it from here
		final List<I_AD_Table_ScriptValidator> scriptValidators;
		final boolean haveScriptingInterceptors;
		if (ModelValidator.tableEventValidators.length > docTiming)
		{
			scriptValidators = Services.get(IADTableScriptValidatorDAO.class).retrieveTableScriptValidators(
					po.getCtx(),
					po.get_Table_ID(),
					ModelValidator.documentEventValidators.get(docTiming));
			haveScriptingInterceptors = scriptValidators != null && !scriptValidators.isEmpty();
		}
		else
		{
			scriptValidators = null;
			haveScriptingInterceptors = false;
		}
		haveInterceptors = haveInterceptors || haveScriptingInterceptors;

		//
		// In case there are no interceptors, do nothing
		if (!haveInterceptors)
		{
			return null;
		}

		//
		// Execute interceptors
		final String trxName = po.get_TrxName();
		executeInTrx(trxName, docTiming, new Runnable()
		{
			@Override
			public void run()
			{
				fireDocValidate0(po, docTiming, interceptorsSystem, interceptorsClient, scriptValidators);
			}
		});

		return null;
	}	// fireDocValidate

	private void fireDocValidate0(final PO po,
			final int docTiming,
			final List<ModelValidator> interceptorsSystem,
			final List<ModelValidator> interceptorsClient,
			final List<I_AD_Table_ScriptValidator> scriptValidators)
	{
		if (interceptorsSystem != null)
		{
			// ad_entitytype.modelvalidationclasses
			fireDocValidate(po, docTiming, interceptorsSystem);
		}

		if (interceptorsClient != null)
		{
			// ad_client.modelvalidationclasses
			fireDocValidate(po, docTiming, interceptorsClient);
		}

		//
		// now process the script model validator for this docTiming
		// FIXME: refactor it and have it as a regular model validator; then remove it from here
		{
			fireModelChangeForScriptValidators(po,
					X_AD_Rule.EVENTTYPE_ModelValidatorDocumentEvent,
					docTiming,
					scriptValidators);
		}
	}

	private void fireDocValidate(final PO po, final int docTiming, final List<ModelValidator> list)
	{
		for (int i = 0; i < list.size(); i++)
		{
			ModelValidator validator = null;
			try
			{
				validator = list.get(i);
				if (appliesFor(validator, po.getAD_Client_ID()))
				{
					final String error = validator.docValidate(po, docTiming);
					if (error != null && error.length() > 0)
					{
						throw new AdempiereException(error);
					}
				}
			}
			catch (Exception e)
			{
				throw AdempiereException.wrapIfNeeded(e);
			}
		}
	}

	/**************************************************************************
	 * Add Date Import Validation Listener
	 *
	 * @param tableName table name
	 * @param listener listener
	 */
	public void addImportValidate(String importTableName, IImportValidator listener)
	{
		String propertyName = getPropertyName(importTableName);
		ArrayList<IImportValidator> list = m_impValidateListeners.get(propertyName);
		if (list == null)
		{
			list = new ArrayList<>();
			list.add(listener);
			m_impValidateListeners.put(propertyName, list);
		}
		else
		{
			list.add(listener);
		}
	}

	/**
	 * Fire Import Validation. Call {@link IImportValidator#validate(IImportProcess, Object, Object, int)} or registered validators.
	 *
	 * @param process import process
	 * @param importModel import record (e.g. X_I_BPartner)
	 * @param targetModel target model (e.g. MBPartner, MBPartnerLocation, MUser)
	 * @param timing see ImportValidator.TIMING_* constants
	 */
	public <ImportRecordType> void fireImportValidate(IImportProcess<ImportRecordType> process, ImportRecordType importModel, Object targetModel, int timing)
	{
		if (m_impValidateListeners.size() == 0)
			return;

		String propertyName = getPropertyName(process.getImportTableName());
		ArrayList<IImportValidator> list = m_impValidateListeners.get(propertyName);
		if (list != null)
		{
			for (IImportValidator validator : list)
			{
				validator.validate(process, importModel, targetModel, timing);
			}
		}
	}

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("ModelValidationEngine[");
		sb.append("Validators=#").append(m_validators.size())
				.append(", ModelChange=#").append(m_modelChangeListeners.size())
				.append(", DocValidate=#").append(m_docValidateListeners.size())
				.append("]");
		return sb.toString();
	}	// toString

	/**
	 * Create Model Validators Info
	 *
	 * @param sb optional string buffer
	 * @param ctx context
	 * @return Model Validators Info
	 *
	 * @author Teo Sarca, FR [ 1724662 ]
	 */
	public StringBuffer getInfoDetail(StringBuffer sb, Properties ctx)
	{
		if (sb == null)
			sb = new StringBuffer();
		sb.append("=== ModelValidationEngine ===").append(Env.NL);
		sb.append("Validators #").append(m_validators.size()).append(Env.NL);
		for (ModelValidator mv : m_validators)
		{
			sb.append(mv.toString()).append(Env.NL);
		}
		sb.append(Env.NL).append(Env.NL);
		//
		sb.append("ModelChange #").append(m_modelChangeListeners.size()).append(Env.NL);
		Iterator<String> it = m_modelChangeListeners.keySet().iterator();
		while (it.hasNext())
		{
			String key = it.next();
			ArrayList<ModelValidator> list = m_modelChangeListeners.get(key);
			for (ModelValidator mv : list)
			{
				sb.append(key).append(": ").append(mv.toString()).append(Env.NL);
			}
		}
		sb.append(Env.NL).append(Env.NL);
		//
		sb.append("DocValidate #").append(m_docValidateListeners.size()).append(Env.NL);
		it = m_docValidateListeners.keySet().iterator();
		while (it.hasNext())
		{
			String key = it.next();
			ArrayList<ModelValidator> list = m_docValidateListeners.get(key);
			for (ModelValidator mv : list)
			{
				sb.append(key).append(": ").append(mv.toString()).append(Env.NL);
			}
		}
		sb.append(Env.NL).append(Env.NL);
		//
		return sb;
	}

	/**
	 * After Load Preferences into Context for selected client.
	 *
	 * @param ctx context
	 * @see org.compiere.util.Login#loadPreferences(KeyNamePair, KeyNamePair, java.sql.Timestamp, String)
	 * @author Teo Sarca - FR [ 1670025 ] - https://sourceforge.net/tracker/index.php?func=detail&aid=1670025&group_id=176962&atid=879335
	 */
	public void afterLoadPreferences(Properties ctx)
	{
		int AD_Client_ID = Env.getAD_Client_ID(ctx);
		for (int i = 0; i < m_validators.size(); i++)
		{
			ModelValidator validator = m_validators.get(i);
			if (AD_Client_ID == validator.getAD_Client_ID()
					|| m_globalValidators.contains(validator))
			{
				java.lang.reflect.Method m = null;
				try
				{
					m = validator.getClass().getMethod("afterLoadPreferences", new Class[] { Properties.class });
				}
				catch (NoSuchMethodException e)
				{
					// ignore
				}
				try
				{
					if (m != null)
						m.invoke(validator, ctx);
				}
				catch (Exception e)
				{
					log.warn("" + validator + ": " + e.getLocalizedMessage());
				}
			}
		}
	}

	/**
	 * Before Save Properties for selected client.
	 */
	public void beforeSaveProperties()
	{
		int AD_Client_ID = Env.getAD_Client_ID(Env.getCtx());
		for (int i = 0; i < m_validators.size(); i++)
		{
			ModelValidator validator = m_validators.get(i);
			if (AD_Client_ID == validator.getAD_Client_ID()
					|| m_globalValidators.contains(validator))
			{
				java.lang.reflect.Method m = null;
				try
				{
					m = validator.getClass().getMethod("beforeSaveProperties");
				}
				catch (NoSuchMethodException e)
				{
					// ignore
				}
				try
				{
					if (m != null)
						m.invoke(validator);
				}
				catch (Exception e)
				{
					log.warn("" + validator + ": " + e.getLocalizedMessage());
				}
			}
		}
	}

	private final void registerGlobal(ModelValidator validator)
	{
		if (!m_globalValidators.contains(validator))
			m_globalValidators.add(validator);
	}

	private final boolean appliesFor(ModelValidator validator, int AD_Client_ID)
	{
		return AD_Client_ID == validator.getAD_Client_ID()
				|| m_globalValidators.contains(validator);
	}

	private final String getPropertyName(String tableName)
	{
		return tableName + "*";
	}

	private final String getPropertyName(String tableName, int AD_Client_ID)
	{
		return tableName + AD_Client_ID;
	}

	private final String getPropertyName(String tableName, ModelValidator listener)
	{
		if (m_globalValidators.contains(listener))
		{
			return getPropertyName(tableName);
		}
		else
		{
			return getPropertyName(tableName, listener.getAD_Client_ID());
		}
	}

	@Override
	public void addModelValidator(final Object validator, final I_AD_Client client)
	{
		if (validator == null)
		{
			throw new IllegalArgumentException("validator can not be null");
		}
		else if (validator instanceof ModelValidator)
		{
			initialize((ModelValidator)validator, client);
		}
		else if (validator instanceof IModelInterceptor)
		{
			final IModelInterceptor interceptor = (IModelInterceptor)validator;
			final ModelValidator interceptor2validator = ModelInterceptor2ModelValidatorWrapper.wrapIfNeeded(interceptor);
			initialize(interceptor2validator, client);
		}
		else
		{
			final IModelInterceptor annotatedInterceptor = AnnotatedModelInterceptorFactory.get().createModelInterceptor(validator);
			if (annotatedInterceptor == null)
			{
				log.warn("No pointcuts found for model validator: " + validator + " [SKIP]");
			}
			else
			{
				final ModelValidator annotatedValidator = ModelInterceptor2ModelValidatorWrapper.wrapIfNeeded(annotatedInterceptor);
				initialize(annotatedValidator, client);
			}
		}
	}

	/**
	 * Specify if model changed events with type=deferred should be processed when they occur (still after the po has been saved an all other logic has been finished) or some time later.
	 */
	public void enableModelValidatorSubsequentProcessing(final ModelValidator validator, final boolean processDirectly)
	{
		m_modelChangeSubsequent.put(validator, processDirectly);
	}

	public void disableModelValidatorSubsequentProcessing(final ModelValidator validator)
	{
		m_modelChangeSubsequent.remove(validator);
	}

	// public static final String CTX_InitEntityTypes = ModelValidationEngine.class.getCanonicalName() + "#InitEntityTypes";

	/**
	 * Name of a dynamic attribute to disable model interceptors (i.e. validators) on ModelChange for a particular PO. Set the value to <code>true</code> if you want to bypass <b>all</b> model
	 * validators.
	 * <p>
	 *
	 * @FIXME [12:09:52] Teo metas: use org.adempiere.ad.persistence.ModelDynAttributeAccessor<ModelType, AttributeType> to define the dynamic attribute
	 */
	public static final String DYNATTR_DO_NOT_INVOKE_ON_MODEL_CHANGE = "DO_NOT_INVOKE_ON_MODEL_CHANGE";

}	// ModelValidatorEngine
