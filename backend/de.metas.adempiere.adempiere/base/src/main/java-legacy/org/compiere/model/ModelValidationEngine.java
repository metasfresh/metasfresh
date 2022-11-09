/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import de.metas.impexp.processing.IImportInterceptor;
import de.metas.impexp.processing.IImportProcess;
import de.metas.logging.LogManager;
import de.metas.monitoring.adapter.NoopPerformanceMonitoringService;
import de.metas.monitoring.adapter.PerformanceMonitoringService;
import de.metas.monitoring.adapter.PerformanceMonitoringService.Metadata;
import de.metas.monitoring.adapter.PerformanceMonitoringService.Type;
import de.metas.script.IADRuleDAO;
import de.metas.script.ScriptEngineFactory;
import de.metas.security.IUserLoginListener;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.AnnotatedModelInterceptorFactory;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.modelvalidator.IModelInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.ModelInterceptor2ModelValidatorWrapper;
import org.adempiere.ad.modelvalidator.ModelInterceptorInitException;
import org.adempiere.ad.modelvalidator.ModuleActivatorDescriptor;
import org.adempiere.ad.modelvalidator.ModuleActivatorDescriptorsCollection;
import org.adempiere.ad.modelvalidator.ModuleActivatorDescriptorsRepository;
import org.adempiere.ad.modelvalidator.TimingType;
import org.adempiere.ad.persistence.EntityTypesCache;
import org.adempiere.ad.service.ADSystemInfo;
import org.adempiere.ad.service.IADTableScriptValidatorDAO;
import org.adempiere.ad.service.ISystemBL;
import org.adempiere.ad.session.MFSession;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxRunConfig;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableFail;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableSuccess;
import org.adempiere.ad.trx.api.ITrxRunConfig.TrxPropagation;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.Adempiere.RunMode;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.context.ApplicationContext;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
 *         <li>BF [ 2819617 ] NPE if script validator rule returns null https://sourceforge.net/tracker/?func=detail&aid=2819617&group_id=176962&atid=879332
 *         </ul>
 * @author Tobias Schoeneberg, t.schoeneberg@metas.de
 *         <li>FR [ADEMPIERE-28] ModelValidatorException https://adempiere.atlassian.net/browse/ADEMPIERE-28
 */
public class ModelValidationEngine implements IModelValidationEngine
{

	private static final String PERF_MON_SYSCONFIG_NAME = "de.metas.monitoring.modelInterceptor.enable";
	private static final boolean SYS_CONFIG_DEFAULT_VALUE = false;
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private static PerformanceMonitoringService _performanceMonitoringService;

	/**
	 * Get Singleton
	 */
	public synchronized static ModelValidationEngine get()
	{
		if (s_engine == null)
		{
			// NOTE: we need to instantiate and assign it to static variable immediatelly
			// else, in init() method, this get() is called indirectly which leads us to have 2 ModelValidationEngine instances
			s_engine = new ModelValidationEngine();
		}
		if (State.TO_BE_INITALIZED.equals(state))
		{
			logger.info("Start initializing ModelValidationEngine");

			state = State.INITIALIZING;

			final Stopwatch stopwatch = Stopwatch.createStarted();
			s_engine.init();
			stopwatch.stop();

			state = State.INITIALIZED;

			logger.info("Done initializing ModelValidationEngine; it took {}; m_globalValidators.size={}; m_validators.size={}",
					stopwatch, s_engine.m_globalValidators.size(), s_engine.m_validators.size());
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
	 * <li>{@link #INITENTITYTYPE_Minimal} - only the core entity types. It is used when we need to start adempiere from other tools and we don't want to start the servers, processors and stuff.
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
			logger.warn(ex.getLocalizedMessage(), ex);
		}

		_initEntityTypes = ImmutableList.copyOf(initEntityTypes);
		logger.info("IMPORTANT: Only following ADempiere modules will be activated: " + _initEntityTypes);
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

	private static Boolean _failOnMissingModelInteceptors = null;
	/** List of errors that we encounted while initializing the model interceptors */
	private static final List<ModelInterceptorInitException> _modelInterceptorInitErrors = new ArrayList<>();

	/**************************************************************************
	 * Constructor. Creates Model Validators
	 */
	private ModelValidationEngine()
	{
		// metas: tsa: begin: break this in 2 parts because if the get() method is called during initialization we will end with multiple instances of ModelVaidationEngine
	}

	private void init()
	{
		// metas: tsa: end

		final List<String> initEntityTypes = getInitEntityTypes(); // metas: 03023

		String currentClassName = null; // className of current model interceptor which is about to be initialized
		try
		{
			final Stopwatch stopwatch = Stopwatch.createStarted();

			final ModuleActivatorDescriptorsRepository moduleActivatorDescriptorsRepo = SpringContextHolder.instance.getBean(ModuleActivatorDescriptorsRepository.class);
			final ModuleActivatorDescriptorsCollection moduleActivatorDescriptors = moduleActivatorDescriptorsRepo.getDescriptors();

			final Collection<Object> springInterceptors = getSpringInterceptors();
			final ImmutableListMultimap<Object, Object> springInterceptorsByClassname = Multimaps.index(springInterceptors, springInterceptor -> springInterceptor.getClass().getName());

			// Register model interceptors defined in database
			for (final ModuleActivatorDescriptor moduleActivatorDescriptor : moduleActivatorDescriptors)
			{
				if (!moduleActivatorDescriptor.isActive())
				{
					logger.info("Skip {} (not active)", moduleActivatorDescriptor);
					continue;
				}

				//
				// Skip loading the model interceptor if entity type is not active
				final String entityType = moduleActivatorDescriptor.getEntityType();
				if (!EntityTypesCache.instance.isActive(entityType))
				{
					logger.info("Skip {} (entityType is not active)", moduleActivatorDescriptor);
					continue;
				}

				//
				// Skip model validator if entity type is not in list of allowed entity types (task 03023)
				if (initEntityTypes != null && !initEntityTypes.contains(entityType))
				{
					logger.info("Skip {} (entityType not in initEntityTypes=" + initEntityTypes + ")", moduleActivatorDescriptor);
					continue;
				}

				final String moduleActivatorClassname = moduleActivatorDescriptor.getClassname();
				currentClassName = moduleActivatorClassname;

				final ImmutableList<Object> existingSpringInstances = springInterceptorsByClassname.get(moduleActivatorClassname);
				final Object existingSpringInstance;
				if (existingSpringInstances.isEmpty())
				{
					existingSpringInstance = null;
				}
				else if (existingSpringInstances.size() == 1)
				{
					existingSpringInstance = existingSpringInstances.get(0);
				}
				else
				{
					logger.warn("More than one spring interceptors found for module activator class '{}'. Ignoring then and will try to create a new instance. -- {}", moduleActivatorClassname, existingSpringInstances);
					existingSpringInstance = null;
				}

				loadModuleActivatorClass(moduleActivatorClassname, existingSpringInstance);
			}
			currentClassName = null;

			stopwatch.stop();
			logger.debug("Done initializing database registered interceptors; it took {}", stopwatch);

			//
			// Register from Spring context
			stopwatch.reset().start();
			currentClassName = null;
			for (final Object springInterceptor : springInterceptors)
			{
				currentClassName = springInterceptor.getClass().getName();
				if (moduleActivatorDescriptors.containsClassname(currentClassName))
				{
					logger.debug("Skip {} because was already considered", springInterceptor);
					continue;
				}

				addModelValidator(springInterceptor);
			}
			currentClassName = null;

			logger.debug("Done initializing spring based interceptors; it took {}", stopwatch);
		}
		catch (final Exception ex)
		{
			addModelInterceptorInitError(currentClassName, ex);
		}

		if (!Ini.isSwingClient() && hasInitErrors())
		{
			logModelInterceptorInitErrors();
			if (isFailOnMissingModelInteceptors())
			{
				System.exit(1);
			}
		}
	}	// ModelValidatorEngine

	private static Collection<Object> getSpringInterceptors()
	{
		final ApplicationContext context = SpringContextHolder.instance.getApplicationContext();
		if (context == null)
		{
			// NOTE: atm it returns null only when started from our tools (like the "model generator")
			// but it's not preventing the tool execution because this is the last thing we do here and also because usually it's configured to not fail on init error.
			throw new AdempiereException("Cannot fetch Spring interceptors because spring context is not available");
		}

		final LinkedHashMap<String, Object> interceptorsByName = new LinkedHashMap<>();
		interceptorsByName.putAll(context.getBeansWithAnnotation(org.adempiere.ad.modelvalidator.annotations.Interceptor.class));
		interceptorsByName.putAll(context.getBeansOfType(IModelInterceptor.class));

		return interceptorsByName.values();
	}

	private final void addModelInterceptorInitError(final String modelInterceptorClassName, final Throwable error)
	{
		final ModelInterceptorInitException initException = new ModelInterceptorInitException(modelInterceptorClassName, error);
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
		final Boolean failOnMissingModelInteceptorsOverride = _failOnMissingModelInteceptors;
		if (failOnMissingModelInteceptorsOverride != null)
		{
			return failOnMissingModelInteceptorsOverride;
		}

		final ADSystemInfo system = Services.get(ISystemBL.class).get();
		return system.isFailOnMissingModelValidator();
	}

	public static final void setFailOnMissingModelInteceptors(final boolean failOnMissingModelInteceptors)
	{
		_failOnMissingModelInteceptors = failOnMissingModelInteceptors;
	}

	private void loadModuleActivatorClass(@NonNull final String className, @Nullable final Object existingInstance)
	{
		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		try
		{
			// Load main activator
			final Object moduleActivator;
			if (existingInstance != null)
			{
				moduleActivator = existingInstance;
			}
			else
			{
				final Class<?> clazz = classLoader.loadClass(className);
				moduleActivator = clazz.newInstance();
			}
			addModelValidator(moduleActivator);

			//
			// Load swing module activation (if any)
			final RunMode runMode = Ini.getRunMode();
			if (runMode == RunMode.SWING_CLIENT)
			{
				final Class<?> swingModuleActivatorClass = getModuleActivatorClassOrNull(className + "_SwingUI");
				if (swingModuleActivatorClass != null)
				{
					final Object moduleActivatorSwing = swingModuleActivatorClass.newInstance();
					addModelValidator(moduleActivatorSwing);
				}
			}
		}
		catch (final Throwable ex)
		{
			addModelInterceptorInitError(className, ex);
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
	private static final Logger logger = LogManager.getLogger(ModelValidationEngine.class);
	// /** Change Support */
	// private VetoableChangeSupport m_changeSupport = new VetoableChangeSupport(this);

	/** Validators */
	private ArrayList<ModelValidator> m_validators = new ArrayList<>();
	/** Model Change Listeners */
	private Hashtable<String, ArrayList<ModelValidator>> m_modelChangeListeners = new Hashtable<>();
	/** Document Validation Listeners */
	private Hashtable<String, ArrayList<ModelValidator>> m_docValidateListeners = new Hashtable<>();
	/** Data Import Validation Listeners */
	private Hashtable<String, ArrayList<IImportInterceptor>> m_impValidateListeners = new Hashtable<>();

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
		{
			registerGlobal(validator);
		}
		m_validators.add(validator);

		final MClient clientPO = LegacyAdapters.convertToPO(client);
		validator.initialize(this, clientPO);
	}

	/**
	 * Called when login is complete
	 *
	 * @param AD_Client_ID client
	 * @param AD_Org_ID org
	 * @param AD_Role_ID role
	 * @param AD_User_ID user
	 * @return error message or empty/null
	 */
	public String loginComplete(int AD_Client_ID, int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		for (ModelValidator validator : m_validators)
		{
			if (appliesFor(validator, AD_Client_ID))
			{
				String error = validator.login(AD_Org_ID, AD_Role_ID, AD_User_ID);
				if (error != null && error.length() > 0)
				{
					return error;
				}
			}
		}

		// now process the script model validator login
		final Properties ctx = Env.getCtx();
		final List<I_AD_Rule> loginRules = Services.get(IADRuleDAO.class).retrieveByEventType(ctx, X_AD_Rule.EVENTTYPE_ModelValidatorLoginEvent);
		if (loginRules != null && !loginRules.isEmpty())
		{
			for (final I_AD_Rule loginRule : loginRules)
			{
				try
				{
					ScriptEngineFactory.get()
							.createExecutor(loginRule)
							.putContext(ctx, Env.WINDOW_None) // no window
							.putArgument("AD_Client_ID", AD_Client_ID)
							.putArgument("AD_Org_ID", AD_Org_ID)
							.putArgument("AD_Role_ID", AD_Role_ID)
							.putArgument("AD_User_ID", AD_User_ID)
							.setThrowExceptionIfResultNotEmpty()
							.execute(loginRule.getScript());
				}
				catch (final Exception e)
				{
					logger.warn("Failed executing login script for {}", loginRule, e);
					final String error = AdempiereException.extractMessage(e);
					return error;
				}
			}
		}
		//

		// metas: 02504: begin: on login complete, log missingModelValidationMessage errors, but only in client mode.
		// In server mode, those errors were already logged in init() method
		final boolean hasInitErrors = hasInitErrors();
		if (hasInitErrors && Ini.isSwingClient())
		{
			logModelInterceptorInitErrors();
		}
		// metas: 02504: end

		if (AD_User_ID == 0 && AD_Role_ID == 0)
		{
			// don't validate for user system on role system
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
		for (ModelValidator m_validator : m_validators)
		{
			final ModelValidator validator = m_validator;

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

	public void fireBeforeLogout(final MFSession session)
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
				logger.error(e.getLocalizedMessage(), e);
			}
		}
	}

	public void fireAfterLogout(final MFSession session)
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
				logger.error(e.getLocalizedMessage(), e);
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
		{
			return;
		}
		//
		if (listener.getAD_Client_ID() < 0)
		{
			registerGlobal(listener);
		}
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
				logger.debug("Listener " + listener + " already added for " + propertyName);
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
	public void removeModelChange(
			@Nullable final String tableName,
			@Nullable final ModelValidator listener)
	{
		if (tableName == null || listener == null)
		{
			return;
		}

		String propertyName = getPropertyName(tableName, listener);
		ArrayList<ModelValidator> list = m_modelChangeListeners.get(propertyName);
		if (list == null)
		{
			return;
		}
		list.remove(listener);
		if (list.size() == 0)
		{
			m_modelChangeListeners.remove(propertyName);
		}
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

	public void fireModelChange(@NonNull final PO po, final ModelChangeType changeType)
	{

		if(!isPerformanceMonitorActive())
		{
			fireModelChange0(po, changeType);
		}
		else
		{
			final String tableName = po.get_TableName();
			final String changeTypeStr = changeType.toString();

			performanceMonitoringService().monitor(
					() -> fireModelChange0(po, changeType),
					Metadata
							.builder()
							.className("ModelValidationEngine")
							.type(Type.MODEL_INTERCEPTOR)
							.functionName("fireModelChange")
							.label("changeType", changeTypeStr)
							.label("tableName", tableName)
							.label(PerformanceMonitoringService.LABEL_RECORD_ID, Integer.toString(po.get_ID()))
							.build());
		}
	}

	public void fireModelChange0(@Nullable final PO po, @NonNull final ModelChangeType changeType)
	{
		try (final MDCCloseable mdcCloseable = MDC.putCloseable("changeType", changeType.toString()))
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
			assertModelValidBeforeFiringEvent(po, changeType); // isDocumentValidateEvent=false

			int countInterceptors = 0;

			//
			// Retrieve system level model interceptors
			final String propertyNameSystem = getPropertyName(po.get_TableName());
			final List<ModelValidator> interceptorsSystem = m_modelChangeListeners.get(propertyNameSystem);
			final int countSystemInterceptors = interceptorsSystem != null ? interceptorsSystem.size() : 0;
			countInterceptors += countSystemInterceptors;

			//
			// Retrieve client level model interceptors
			final String propertyNameClient = getPropertyName(po.get_TableName(), po.getAD_Client_ID());
			final List<ModelValidator> interceptorsClient = m_modelChangeListeners.get(propertyNameClient);
			final int countClientInterceptors = interceptorsClient != null ? interceptorsClient.size() : 0;
			countInterceptors += countClientInterceptors;

			//
			// Retrieve script interceptors
			//
			// now process the script model validator for this event
			// metas: tsa: 02380: First check if changeType is available in tableEventValidators
			// FIXME: refactor it and have it as a regular model validator; then remove it from here
			final List<I_AD_Table_ScriptValidator> scriptValidators;
			final int countScriptingInterceptors;
			if (ModelValidator.tableEventValidators.length > changeType.toInt())
			{
				scriptValidators = Services.get(IADTableScriptValidatorDAO.class).retrieveTableScriptValidators(
						po.getCtx(),
						po.get_Table_ID(),
						ModelValidator.tableEventValidators[changeType.toInt()]);
				countScriptingInterceptors = scriptValidators != null ? scriptValidators.size() : 0;
			}
			else
			{
				scriptValidators = null;
				countScriptingInterceptors = 0;
			}
			countInterceptors += countScriptingInterceptors;

			//
			// In case there are no interceptors, do nothing
			if (countInterceptors <= 0)
			{
				return;
			}

			//
			// Execute interceptors
			final String trxName = po.get_TrxName();
			executeInTrx(trxName, changeType, () -> fireModelChange0(po, changeType, interceptorsSystem, interceptorsClient, scriptValidators));

			logger.debug("Executed: ALL {} interceptors for {}", changeType, po);
		}
	}	// fireModelChange

	private boolean isPerformanceMonitorActive()
	{
		return sysConfigBL.getBooleanValue(PERF_MON_SYSCONFIG_NAME, SYS_CONFIG_DEFAULT_VALUE);
	}

	private PerformanceMonitoringService performanceMonitoringService()
	{
		PerformanceMonitoringService performanceMonitoringService = _performanceMonitoringService;
		if (performanceMonitoringService == null || performanceMonitoringService instanceof NoopPerformanceMonitoringService)
		{
			performanceMonitoringService = _performanceMonitoringService = SpringContextHolder.instance.getBeanOr(
					PerformanceMonitoringService.class,
					NoopPerformanceMonitoringService.INSTANCE);
		}
		return performanceMonitoringService;
	}

	private final void executeInTrx(final String trxName, final TimingType changeTypeOrDocTiming, @NonNull final Runnable runnable)
	{
		final boolean runInTrx = changeTypeOrDocTiming != ModelChangeType.BEFORE_SAVE_TRX;

		if (runInTrx)
		{
			// NOTE: we are wrapping it to a transaction (savepoint) because we also want to make sure Thread TrxName is set
			final ITrxManager trxManager = Services.get(ITrxManager.class);

			if (trxManager.isNull(trxName))
			{
				throw new AdempiereException("trxName shall not be null");
			}

			final ITrxRunConfig trxRunConfig = trxManager.newTrxRunConfigBuilder()
					.setTrxPropagation(TrxPropagation.NESTED) // we expect to run into a transaction at this point
					.setOnRunnableSuccess(OnRunnableSuccess.DONT_COMMIT)
					.setOnRunnableFail(OnRunnableFail.DONT_ROLLBACK) // OnRunnableFail: don't rollback => no savepoint shall be created (avoid HUGE performance issues)
					.build();
			trxManager.run(trxName, trxRunConfig, localTrxName -> runnable.run());
		}
		//
		// Execute without wrapping to a particular transaction
		else
		{
			runnable.run();
		}

	}

	private final void fireModelChange0(
			@NonNull final PO po,
			@NonNull final ModelChangeType changeType,
			@Nullable List<ModelValidator> interceptorsSystem,
			@Nullable List<ModelValidator> interceptorsClient,
			@Nullable List<I_AD_Table_ScriptValidator> scriptValidators)
	{
		if (interceptorsSystem != null)
		{
			// ad_entitytype.modelvalidationclasses
			invokeModelChangeMethods(po, changeType, interceptorsSystem);
		}

		if (interceptorsClient != null)
		{
			// ad_client.modelvalidationclasses
			invokeModelChangeMethods(po, changeType, interceptorsClient);
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
			final TimingType changeTypeOrDocTiming,
			@Nullable final List<I_AD_Table_ScriptValidator> scriptValidators)
	{
		// if there are no script validators => do nothing
		if (scriptValidators == null || scriptValidators.isEmpty())
		{
			return;
		}

		final String ruleEventModelValidator;
		if (X_AD_Rule.EVENTTYPE_ModelValidatorTableEvent.equals(ruleEventType))
		{
			final int changeType = changeTypeOrDocTiming.toInt();
			ruleEventModelValidator = ModelValidator.tableEventValidators[changeType];
		}
		else if (X_AD_Rule.EVENTTYPE_ModelValidatorDocumentEvent.equals(ruleEventType))
		{
			final DocTimingType docTiming = (DocTimingType)changeTypeOrDocTiming;
			ruleEventModelValidator = ModelValidator.documentEventValidators.get(docTiming);
		}
		else
		{
			throw new IllegalArgumentException("Unknown rule EventType: " + ruleEventType);
		}

		for (final I_AD_Table_ScriptValidator scriptValidator : scriptValidators)
		{
			final I_AD_Rule rule = Services.get(IADRuleDAO.class).retrieveById(po.getCtx(), scriptValidator.getAD_Rule_ID());
			if (rule != null
					&& rule.isActive()
					&& rule.getEventType().equals(ruleEventType))
			{
				try
				{
					ScriptEngineFactory.get()
							.createExecutor(rule)
							.putContext(po.getCtx(), po.get_WindowNo())
							.putArgument("PO", po)
							.putArgument("Type", changeTypeOrDocTiming)
							.putArgument("Event", ruleEventModelValidator)
							.setThrowExceptionIfResultNotEmpty()
							.execute(rule.getScript());
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
	private final void assertModelValidBeforeFiringEvent(@NonNull final PO model, @NonNull final TimingType timingType)
	{
		//
		// Validate PO's transaction
		if (!ModelChangeType.isBeforeSaveTrx(timingType) // Skip BEFORE_SAVE_TRX events because those are always out of transaction
		)
		{
			final String trxName = model.get_TrxName();
			if (Services.get(ITrxManager.class).isNull(trxName))
			{
				final AdempiereException ex = new AdempiereException("When firing " + timingType + " event, PO shall have a trxName set."
						+ " Ignore it, but check the log for further issues."
						+ "\n PO: " + model
						+ "\n Event: " + timingType
						+ "\n trxName: " + trxName);
				logger.warn(ex.getLocalizedMessage(), ex);
			}
		}
	}

	private final void invokeModelChangeMethods(
			@NonNull final PO po,
			@NonNull final ModelChangeType changeType,
			@NonNull final List<ModelValidator> validators)
	{
		for (final ModelValidator validator : validators)
		{
			invokeModelChangeMethod(po, changeType, validator);
		}
	}

	private void invokeModelChangeMethod(
			@NonNull final PO po,
			@NonNull final ModelChangeType changeType,
			@NonNull final ModelValidator validator)
	{
		try (final MDCCloseable mdcCloseable = MDC.putCloseable("interceptor", validator.toString()))
		{
			if (!appliesFor(validator, po.getAD_Client_ID()))
			{
				logger.debug("Skip {} ({}) for {}", validator, changeType, po);
				return;
			}

		final Stopwatch stopwatch = Stopwatch.createStarted();
				try
		{

			// the default cause
			final String error = validator.modelChange(po, changeType.toInt());
			if (!Check.isEmpty(error))
			{
				throw new AdempiereException(error);
			}

					logger.debug("Executed in {}: {} ({}) for {}", stopwatch, validator, changeType, po);
		}
		catch (final Exception ex)
		{
					logger.debug("Failed executing in {}: {} ({}) for {}", stopwatch, validator, changeType, po, ex);
			throw AdempiereException.wrapIfNeeded(ex);
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
		{
			return;
		}
		//
		if (listener.getAD_Client_ID() < 0)
		{
			registerGlobal(listener);
		}
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
		{
			return;
		}
		String propertyName = getPropertyName(tableName, listener);
		ArrayList<ModelValidator> list = m_docValidateListeners.get(propertyName);
		if (list == null)
		{
			return;
		}
		list.remove(listener);
		if (list.size() == 0)
		{
			m_docValidateListeners.remove(propertyName);
		}
	}	// removeDocValidate

	/**
	 * Fire Document Validation. Call docValidate method of added validators
	 *
	 * @param model if <code>null</code> or if {@link InterfaceWrapperHelper#getPO(Object)} returns <code>null</code> for the given value, then the method will do nothing.
	 * @param docTimingInt see ModelValidator.TIMING_ constants
	 * @return always returns <code>null</code>; we keep this string return type only for legacy purposes (when the error message was returned)
	 * @throws AdempiereException in case of failure
	 */
	public String fireDocValidate(final Object model, final int docTimingInt)
	{
		final DocTimingType docTiming = DocTimingType.valueOf(docTimingInt);
		return fireDocValidate(model, docTiming);
	}

	public String fireDocValidate(@NonNull final Object model, @NonNull final DocTimingType docTiming)
	{
		try (final MDCCloseable mdcCloseable = MDC.putCloseable("docTiming", docTiming.toString()))
		{
			if(!isPerformanceMonitorActive())
			{
				return fireDocValidate0(model, docTiming);
			}
			else
			{
				final String tableName = InterfaceWrapperHelper.getModelTableName(model);
				final int recordId = InterfaceWrapperHelper.getId(model);
				final String docTimingStr = docTiming.toString();

				return performanceMonitoringService().monitor(
						() -> fireDocValidate0(model, docTiming),
						Metadata
								.builder()
								.className("ModelValidationEngine")
								.type(Type.MODEL_INTERCEPTOR)
								.functionName("fireDocValidate")
								.label("docTiming", docTimingStr)
								.label("tableName", tableName)
								.label(PerformanceMonitoringService.LABEL_RECORD_ID, Integer.toString(recordId))
								.build());
			}
		}
	}

	private String fireDocValidate0(final Object model, final DocTimingType docTiming)
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
		assertModelValidBeforeFiringEvent(po, docTiming); // isDocumentValidateEvent=true

		int countInterceptors = 0;

		//
		// Retrieve system level model interceptors
		final String propertyNameSystem = getPropertyName(po.get_TableName());
		final List<ModelValidator> interceptorsSystem = m_docValidateListeners.get(propertyNameSystem);
		final int countSystemInterceptors = interceptorsSystem != null ? interceptorsSystem.size() : 0;
		countInterceptors += countSystemInterceptors;

		//
		// Retrieve client level model interceptors
		final String propertyNameClient = getPropertyName(po.get_TableName(), po.getAD_Client_ID());
		final List<ModelValidator> interceptorsClient = m_docValidateListeners.get(propertyNameClient);
		final int countClientInterceptors = interceptorsClient != null ? interceptorsClient.size() : 0;
		countInterceptors += countClientInterceptors;

		//
		// Retrieve script interceptors
		//
		// now process the script model validator for this event
		// metas: tsa: 02380: First check if changeType is available in tableEventValidators
		// FIXME: refactor it and have it as a regular model validator; then remove it from here
		final List<I_AD_Table_ScriptValidator> scriptValidators;
		if (ModelValidator.documentEventValidators.containsKey(docTiming))
		{
			scriptValidators = Services.get(IADTableScriptValidatorDAO.class).retrieveTableScriptValidators(
					po.getCtx(),
					po.get_Table_ID(),
					ModelValidator.documentEventValidators.get(docTiming));
			final int countScriptingInterceptors = scriptValidators != null ? scriptValidators.size() : 0;
			countInterceptors += countScriptingInterceptors;
		}
		else
		{
			scriptValidators = null;
		}

		//
		// In case there are no interceptors, do nothing
		if (countInterceptors <= 0)
		{
			return null;
		}

		//
		// Execute interceptors
		final String trxName = po.get_TrxName();
		executeInTrx(trxName, docTiming, () -> fireDocValidate0(po, docTiming, interceptorsSystem, interceptorsClient, scriptValidators));

		logger.trace("Executed ALL {} {} interceptors for {}", countInterceptors, docTiming, po);

		return null;
	}

	private void fireDocValidate0(final PO po,
			final DocTimingType docTiming,
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

	private void fireDocValidate(
			final PO po,
			final DocTimingType docTiming,
			final List<ModelValidator> interceptors)
	{
		for (final ModelValidator interceptor : interceptors)
		{
			invokeDocValidateMethod(po, docTiming, interceptor);
		}
	}

	private void invokeDocValidateMethod(
			@NonNull final PO po,
			@NonNull final DocTimingType docTiming,
			@NonNull final ModelValidator interceptor)
	{
		if (!appliesFor(interceptor, po.getAD_Client_ID()))
		{
			logger.trace("Skip {} ({}) for {}", interceptor, docTiming, po);
			return;
		}

		final Stopwatch stopwatch = Stopwatch.createStarted();
		try
		{
			final String error = interceptor.docValidate(po, docTiming.toInt());
			if (!Check.isEmpty(error))
			{
				throw new AdempiereException(error);
			}

			logger.trace("Executed in {}: {} ({}) for {}", stopwatch, interceptor, docTiming, po);
		}
		catch (final Exception ex)
		{
			logger.trace("Failed executing in {}: {} ({}) for {}", stopwatch, interceptor, docTiming, po, ex);
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	@Override
	public void addImportInterceptor(String importTableName, IImportInterceptor listener)
	{
		String propertyName = getPropertyName(importTableName);
		ArrayList<IImportInterceptor> list = m_impValidateListeners.get(propertyName);
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
	 * Fire Import Validation. Call {@link IImportInterceptor#onImport(IImportProcess, Object, Object, int)} or registered validators.
	 *
	 * @param process import process
	 * @param importModel import record (e.g. X_I_BPartner)
	 * @param targetModel target model (e.g. MBPartner, MBPartnerLocation, MUser)
	 * @param timing see ImportValidator.TIMING_* constants
	 */
	public <ImportRecordType> void fireImportValidate(IImportProcess<ImportRecordType> process, ImportRecordType importModel, Object targetModel, int timing)
	{
		if (m_impValidateListeners.size() == 0)
		{
			return;
		}

		String propertyName = getPropertyName(process.getImportTableName());
		ArrayList<IImportInterceptor> list = m_impValidateListeners.get(propertyName);
		if (list != null)
		{
			for (IImportInterceptor intercepto : list)
			{
				intercepto.onImport(process, importModel, targetModel, timing);
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
	public StringBuilder getInfoDetail(StringBuilder sb, Properties ctx)
	{
		if (sb == null)
		{
			sb = new StringBuilder();
		}
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
					{
						m.invoke(validator, ctx);
					}
				}
				catch (Exception e)
				{
					logger.warn("" + validator + ": " + e.getLocalizedMessage());
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
					{
						m.invoke(validator);
					}
				}
				catch (Exception e)
				{
					logger.warn("" + validator + ": " + e.getLocalizedMessage());
				}
			}
		}
	}

	private final void registerGlobal(ModelValidator validator)
	{
		if (!m_globalValidators.contains(validator))
		{
			m_globalValidators.add(validator);
		}
	}

	private final boolean appliesFor(@NonNull final ModelValidator validator, final int AD_Client_ID)
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
	public void addModelValidator(@NonNull final Object validator)
	{
		final I_AD_Client client = null;
		addModelValidator(validator, client);
	}

	@Override
	public void addModelValidator(@NonNull final Object validator, @Nullable final I_AD_Client client)
	{
		if (validator instanceof ModelValidator)
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
				logger.warn("No pointcuts found for model validator: " + validator + " [SKIP]");
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

	private enum State
	{
		/** In this state, {@link #get()} does not attempt to initialize this model validator and basically returns a "no-op" instance. */
		SKIP_INITIALIZATION,

		/** In this state, the next invocation of {@link #get()} will to initialize the model validator before returning an instance. */
		TO_BE_INITALIZED,

		INITIALIZING,

		INITIALIZED
	}

	private static State state = State.TO_BE_INITALIZED;

	public static IAutoCloseable postponeInit()
	{
		changeStateToSkipInitialization();
		return ModelValidationEngine::changeStateToBeInitialized;
	}

	private static synchronized void changeStateToSkipInitialization()
	{
		Check.assumeEquals(state, State.TO_BE_INITALIZED);
		state = State.SKIP_INITIALIZATION;
	}

	private static synchronized void changeStateToBeInitialized()
	{
		if (state == State.SKIP_INITIALIZATION)
		{
			state = State.TO_BE_INITALIZED;
		}
	}
}	// ModelValidatorEngine
