/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.debug;

import ch.qos.logback.classic.Level;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CacheMgt;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.TargetRecordAction;
import de.metas.notification.UserNotificationRequest.UserNotificationRequestBuilder;
import de.metas.notification.UserNotificationTargetType;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.ui.web.base.model.I_T_WEBUI_ViewSelection;
import de.metas.ui.web.comments.CommentsService;
import de.metas.ui.web.comments.ViewRowCommentsSummary;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.debug.JSONCacheResetResult.JSONCacheResetResultBuilder;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.menu.MenuTreeRepository;
import de.metas.ui.web.process.ProcessRestController;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.SqlViewFactory;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.ViewResult;
import de.metas.ui.web.view.ViewRowOverridesHelper;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.view.event.ViewChangesCollector;
import de.metas.ui.web.view.json.JSONViewResult;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.adempiere.ad.dao.IQueryStatisticsLogger;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@ApiResponses(value = { @ApiResponse(code = 401, message = "Unauthorized") })
@RestController
@RequestMapping(value = DebugRestController.ENDPOINT)
public class DebugRestController
{
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/debug";

	@Autowired
	private UserSession userSession;

	@Autowired
	private DocumentCollection documentCollection;

	@Autowired
	private MenuTreeRepository menuTreeRepo;

	@Autowired
	@Lazy
	private IViewsRepository viewsRepo;

	@Autowired
	@Lazy
	private SqlViewFactory sqlViewFactory;

	@Autowired
	@Lazy
	private ProcessRestController processesController;

	@Autowired
	@Lazy
	private IQueryStatisticsLogger statisticsLogger;

	@Autowired
	@Lazy
	private ObjectMapper sharedJsonObjectMapper;

	private JSONOptions newJSONOptions()
	{
		return JSONOptions.of(userSession);
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "cache reset done") })
	@RequestMapping(value = "/cacheReset", method = RequestMethod.GET)
	public JSONCacheResetResult cacheReset(
			@RequestParam(name = "forgetNotSavedDocuments", defaultValue = "false", required = false) final boolean forgetNotSavedDocuments)
	{
		userSession.assertLoggedIn();

		final JSONCacheResetResultBuilder result = JSONCacheResetResult.builder();

		//
		{
			final long count = CacheMgt.get().reset();
			result.log("CacheMgt: invalidate " + count + " items");
		}

		//
		{
			final String documentsResult = documentCollection.cacheReset(forgetNotSavedDocuments);
			result.log("documents: " + documentsResult);
		}

		//
		{
			menuTreeRepo.cacheReset();
			result.log("menuTreeRepo: cache invalidated");
		}

		//
		{
			processesController.cacheReset();
			result.log("processesController: cache invalidated");
		}

		//
		{
			ViewColumnHelper.cacheReset();
			result.log("viewColumnHelper: cache invalidated");
		}

		//
		{
			Services.get(IUserRolePermissionsDAO.class).resetLocalCache();
			result.log("user/role permissions: cache invalidated");
		}

		//
		{
			System.gc();
			result.log("system: garbage collected");
		}

		return result.build();
	}

	// NOTE: using String parameter because when using boolean parameter, we get following error in swagger-ui:
	// swagger-ui.min.js:10 Uncaught TypeError: Cannot read property 'toLowerCase' of undefined
	@RequestMapping(value = "/showColumnNamesForCaption", method = RequestMethod.PUT)
	public void setShowColumnNamesForCaption(@RequestBody final String showColumnNamesForCaptionStr)
	{
		userSession.assertLoggedIn();

		final Boolean showColumnNamesForCaption = DisplayType.toBoolean(showColumnNamesForCaptionStr, null);
		if (showColumnNamesForCaption == null)
		{
			throw new AdempiereException("Invalid boolean value: `" + showColumnNamesForCaptionStr + "`");
		}
		userSession.setShowColumnNamesForCaption(showColumnNamesForCaption);

		final boolean forgetNotSavedDocuments = true;
		cacheReset(forgetNotSavedDocuments);
	}

	@RequestMapping(value = "/allowDeprecatedRestAPI", method = RequestMethod.PUT)
	public void setAllowDeprecatedRestAPI(@RequestBody final String allowDeprecatedRestAPI)
	{
		userSession.assertLoggedIn();

		userSession.setAllowDeprecatedRestAPI(DisplayType.toBoolean(allowDeprecatedRestAPI));
	}

	@RequestMapping(value = "/disableDeprecatedRestAPI", method = RequestMethod.GET)
	public boolean isAllowDeprecatedRestAPI()
	{
		userSession.assertLoggedIn();

		return userSession.isAllowDeprecatedRestAPI();
	}

	@RequestMapping(value = "/traceSqlQueries", method = RequestMethod.GET)
	public void setTraceSqlQueries(

			@ApiParam(value = "If Enabled, all SQL queries are logged with loglevel=WARN, or if the system property <code>" + IQueryStatisticsLogger.SYSTEM_PROPERTY_LOG_TO_SYSTEM_ERROR + "</code> is set to <code>true</code>, they will be written to std-err.", //
					allowEmptyValue = false) //
			@RequestParam("enabled") final boolean enabled)
	{
		userSession.assertLoggedIn();

		if (enabled)
		{
			statisticsLogger.enableWithSqlTracing();
		}
		else
		{
			statisticsLogger.disable();
		}
	}

	@RequestMapping(value = "/debugProtocol", method = RequestMethod.GET)
	public void setDebugProtocol(@RequestParam("enabled") final boolean enabled)
	{
		userSession.assertLoggedIn();

		WindowConstants.setProtocolDebugging(enabled);
	}

	@RequestMapping(value = "/views/list", method = RequestMethod.GET)
	public List<JSONViewResult> getViewsList()
	{
		userSession.assertLoggedIn();

		final JSONOptions jsonOpts = newJSONOptions();

		return viewsRepo.getViews()
				.stream()
				.map(ViewResult::ofView)
				.map(viewResult -> JSONViewResult.of(viewResult, ViewRowOverridesHelper.NULL, jsonOpts, ViewRowCommentsSummary.EMPTY))
				.collect(GuavaCollectors.toImmutableList());
	}

	@PostMapping("/viewDefaultProfile/{windowId}")
	public void setDefaultViewProfile(@PathVariable("windowId") final String windowIdStr, @RequestBody final String profileIdStr)
	{
		userSession.assertLoggedIn();

		sqlViewFactory.setDefaultProfileId(WindowId.fromJson(windowIdStr), ViewProfileId.fromJson(profileIdStr));
	}

	@RequestMapping(value = "/lookups/cacheStats", method = RequestMethod.GET)
	public List<String> getLookupCacheStats()
	{
		userSession.assertLoggedIn();

		return LookupDataSourceFactory.instance.getCacheStats()
				.stream()
				.map(stats -> stats.toString())
				.collect(GuavaCollectors.toImmutableList());
	}

	@RequestMapping(value = "/eventBus/postEvent", method = RequestMethod.GET)
	public void postEvent(
			@RequestParam(name = "topicName", defaultValue = "de.metas.event.GeneralNotifications") final String topicName //
			, @RequestParam(name = "message", defaultValue = "test message") final String message//
			, @RequestParam(name = "toUserId", defaultValue = "-1") final int toUserId//
			, @RequestParam(name = "important", defaultValue = "false") final boolean important//
			//
			, @RequestParam(name = "targetType", required = false) final String targetTypeStr//
			, @RequestParam(name = "targetDocumentType", required = false, defaultValue = "143") final String targetDocumentType//
			, @RequestParam(name = "targetDocumentId", required = false) final String targetDocumentId//
	)
	{
		userSession.assertLoggedIn();

		final Topic topic = Topic.builder()
				.name(topicName)
				.type(Type.LOCAL)
				.build();

		final UserNotificationRequestBuilder request = UserNotificationRequest.builder()
				.topic(topic)
				.recipientUserId(UserId.ofRepoIdOrNull(toUserId))
				.important(important);

		final UserNotificationTargetType targetType = Check.isEmpty(targetTypeStr) ? null : UserNotificationTargetType.forJsonValue(targetTypeStr);
		if (targetType == UserNotificationTargetType.Window)
		{
			final String targetTableName = documentCollection.getDocumentDescriptorFactory()
					.getDocumentDescriptor(WindowId.fromJson(targetDocumentType))
					.getEntityDescriptor()
					.getTableName();

			final TableRecordReference targetRecord = TableRecordReference.of(targetTableName, Integer.parseInt(targetDocumentId));
			request.targetAction(TargetRecordAction.of(targetRecord));
		}

		Services.get(INotificationBL.class).send(request.build());
	}

	@GetMapping("/logger/{loggerName}/_getUpToRoot")
	public List<Map<String, Object>> getLoggersUpToRoot(@PathVariable("loggerName") final String loggerName)
	{
		userSession.assertLoggedIn();

		final Logger logger = LogManager.getLogger(loggerName);
		if (logger == null)
		{
			throw new EntityNotFoundException("No logger found for " + loggerName);
		}

		final List<Map<String, Object>> loggerInfos = new ArrayList<>();
		//
		LogManager.forAllLevelsUpToRoot(logger, currentLogger -> {
			final Map<String, Object> info = new HashMap<>();
			info.put("name", currentLogger.getName());
			info.put("id", System.identityHashCode(currentLogger));
			if (currentLogger instanceof ch.qos.logback.classic.Logger)
			{
				final ch.qos.logback.classic.Logger logbackLogger = (ch.qos.logback.classic.Logger)currentLogger;
				final Level level = logbackLogger.getLevel();
				final Level effectiveLevel = logbackLogger.getEffectiveLevel();
				info.put("level", level == null ? null : level.toString());
				info.put("level-effective", effectiveLevel == null ? null : effectiveLevel.toString());
			}
			else
			{
				info.put("warning", "unknown level for logger object " + currentLogger + " (" + currentLogger.getClass() + ")");
			}

			loggerInfos.add(info);
		});
		//
		return loggerInfos;
	}

	public enum LoggingModule
	{
		websockets_and_invalidation(
				de.metas.ui.web.websocket.WebSocketConfig.class.getPackage().getName(),
				de.metas.ui.web.window.invalidation.DocumentCacheInvalidationDispatcher.class.getName()), //
		view(de.metas.ui.web.view.IView.class.getPackage().getName()), //
		cache(
				de.metas.cache.CCache.class.getName(),
				de.metas.cache.CacheMgt.class.getName(),
				de.metas.cache.model.IModelCacheService.class.getName() // model caching
		), //
		model_interceptors(
				org.compiere.model.ModelValidationEngine.class.getName(), //
				org.adempiere.ad.modelvalidator.IModelValidationEngine.class.getPackage().getName() // for annotated interceptors etc
		) //
		;

		private final Set<String> loggerNames;

		private LoggingModule(final String... loggerNames)
		{
			this.loggerNames = ImmutableSet.copyOf(loggerNames);
		}

		// @JsonValue
		public Set<String> getLoggerNames()
		{
			return loggerNames;
		}
	}

	@GetMapping("/logger/_setLevel/{level}")
	public Set<String> setLoggerLevel(
			@RequestParam(name = "module", required = false) final LoggingModule module //
			, @RequestParam(name = "loggerName", required = false) final String loggerName //
			, @PathVariable("level") final String levelStr //
	)
	{
		userSession.assertLoggedIn();

		//
		// Get Level to set
		final Level level;
		if (Check.isEmpty(levelStr, true))
		{
			level = null;
		}
		else
		{
			level = LogManager.asLogbackLevel(levelStr);
			if (level == null)
			{
				throw new IllegalArgumentException("level is not valid");
			}
		}

		//
		// Get logger names
		final Set<String> loggerNamesEffective = new LinkedHashSet<>();
		if (module != null)
		{
			loggerNamesEffective.addAll(module.getLoggerNames());
		}
		if (!Check.isEmpty(loggerName, true))
		{
			loggerNamesEffective.add(loggerName.trim());
		}

		//
		// Set level to effective logger names
		for (final String loggerNameEffective : loggerNamesEffective)
		{
			final Logger logger = LogManager.getLogger(loggerNameEffective);
			if (logger == null)
			{
				throw new EntityNotFoundException("No logger found for " + loggerNameEffective);
			}

			final boolean set = LogManager.setLoggerLevel(logger, level);
			if (!set)
			{
				throw new IllegalStateException("For some reason " + logger + " could not be set to level " + level);
			}
		}

		return loggerNamesEffective;
	}

	@GetMapping("http.cache.maxAge")
	public Map<String, Object> setHttpCacheMaxAge(@RequestParam("value") @ApiParam("Cache-control's max age in seconds") final int httpCacheMaxAge)
	{
		userSession.assertLoggedIn();

		final long httpCacheMaxAgeOld = userSession.getHttpCacheMaxAge();
		userSession.setHttpCacheMaxAge(httpCacheMaxAge);
		return ImmutableMap.of("value", httpCacheMaxAge, "valueOld", httpCacheMaxAgeOld);
	}

	@GetMapping("http.use.AcceptLanguage")
	public Map<String, Object> setUseHttpAcceptLanguage(@RequestParam("value") @ApiParam("Cache-control's max age in seconds") final boolean useHttpAcceptLanguage)
	{
		userSession.assertLoggedIn();

		final boolean useHttpAcceptLanguageOld = userSession.isUseHttpAcceptLanguage();
		userSession.setUseHttpAcceptLanguage(useHttpAcceptLanguage);
		return ImmutableMap.of("value", useHttpAcceptLanguage, "valueOld", useHttpAcceptLanguageOld);
	}

	@PostMapping("/view/{viewId}/deleteRows")
	public String viewDeleteRowIds(
			@PathVariable("viewId") final String viewIdStr,
			@RequestParam("ids") final String rowIdsStr)
	{
		userSession.assertLoggedIn();

		final ViewId viewId = ViewId.ofViewIdString(viewIdStr);
		final DocumentIdsSelection rowIds = DocumentIdsSelection.ofCommaSeparatedString(rowIdsStr);

		//
		// Delete from DB selection
		String sql = "DELETE FROM " + I_T_WEBUI_ViewSelection.Table_Name
				+ " WHERE " + I_T_WEBUI_ViewSelection.COLUMNNAME_UUID + "=" + DB.TO_STRING(viewId.getViewId())
				+ " AND " + I_T_WEBUI_ViewSelection.COLUMNNAME_IntKey1 + "=" + DB.buildSqlList(rowIds.toIntSet());
		final int countDeleted = DB.executeUpdateEx(sql, ITrx.TRXNAME_None);

		//
		// Clear view's cache
		final IView view = viewsRepo.getView(viewId);
		rowIds.forEach(view::invalidateRowById);

		//
		// Notify
		ViewChangesCollector.getCurrentOrAutoflush().collectRowsChanged(view, rowIds);

		return "Deleted " + countDeleted + " rows";
	}

	@GetMapping("/changeJsonEngineCofiguration")
	public void changeJsonEngineConfiguration(
			@RequestParam(value = "failOnUnknownProperties", required = false) final Boolean failOnUnknownProperties)
	{
		if (failOnUnknownProperties != null)
		{
			sharedJsonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, failOnUnknownProperties);
		}
	}

	@GetMapping("/conext")
	public Map<String, String> getContext()
	{
		userSession.assertLoggedIn();

		final LinkedHashMap<String, String> map = new LinkedHashMap<>();

		final Properties ctx = Env.getCtx();

		final ArrayList<String> keys = new ArrayList<>(ctx.stringPropertyNames());
		Collections.sort(keys);

		for (final String key : keys)
		{
			final String value = ctx.getProperty(key);
			map.put(key, value);
		}

		return map;
	}
}
