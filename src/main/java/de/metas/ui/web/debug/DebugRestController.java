package de.metas.ui.web.debug;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.ad.dao.IQueryStatisticsLogger;
import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.CacheMgt;
import org.compiere.util.DisplayType;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import ch.qos.logback.classic.Level;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationRequest.UserNotificationRequestBuilder;
import de.metas.notification.UserNotificationTargetType;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.menu.MenuTreeRepository;
import de.metas.ui.web.process.ProcessRestController;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.SqlViewFactory;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.ViewResult;
import de.metas.ui.web.view.ViewRowOverridesHelper;
import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.view.json.JSONViewResult;
import de.metas.ui.web.websocket.WebsocketEventLogRecord;
import de.metas.ui.web.websocket.WebsocketSender;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.ui.web.window.model.sql.SqlDocumentsRepository;
import io.swagger.annotations.ApiParam;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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
	private WebsocketSender websocketSender;

	@RequestMapping(value = "/cacheReset", method = RequestMethod.GET)
	public void cacheReset()
	{
		CacheMgt.get().reset();
		documentCollection.cacheReset();
		menuTreeRepo.cacheReset();
		processesController.cacheReset();
		ViewColumnHelper.cacheReset();
		Services.get(IUserRolePermissionsDAO.class).resetLocalCache();

		System.gc();
	}

	// NOTE: using String parameter because when using boolean parameter, we get following error in swagger-ui:
	// swagger-ui.min.js:10 Uncaught TypeError: Cannot read property 'toLowerCase' of undefined
	@RequestMapping(value = "/showColumnNamesForCaption", method = RequestMethod.PUT)
	public void setShowColumnNamesForCaption(@RequestBody final String showColumnNamesForCaptionStr)
	{
		userSession.setShowColumnNamesForCaption(DisplayType.toBoolean(showColumnNamesForCaptionStr));
	}

	@RequestMapping(value = "/allowDeprecatedRestAPI", method = RequestMethod.PUT)
	public void setAllowDeprecatedRestAPI(@RequestBody final String allowDeprecatedRestAPI)
	{
		userSession.setAllowDeprecatedRestAPI(DisplayType.toBoolean(allowDeprecatedRestAPI));
	}

	@RequestMapping(value = "/disableDeprecatedRestAPI", method = RequestMethod.GET)
	public boolean isAllowDeprecatedRestAPI()
	{
		return userSession.isAllowDeprecatedRestAPI();
	}

	@RequestMapping(value = "/traceSqlQueries", method = RequestMethod.GET)
	public void setTraceSqlQueries(@RequestParam("enabled") final boolean enabled)
	{
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
		WindowConstants.setProtocolDebugging(enabled);
	}

	@RequestMapping(value = "/views/list", method = RequestMethod.GET)
	public List<JSONViewResult> getViewsList()
	{
		final String adLanguage = userSession.getAD_Language();

		return viewsRepo.getViews()
				.stream()
				.map(ViewResult::ofView)
				.map(viewResult -> JSONViewResult.of(viewResult, ViewRowOverridesHelper.NULL, adLanguage))
				.collect(GuavaCollectors.toImmutableList());
	}

	@PostMapping("/viewDefaultProfile/{windowId}")
	public void setDefaultViewProfile(@PathVariable("windowId") final String windowIdStr, @RequestBody final String profileIdStr)
	{
		sqlViewFactory.setDefaultProfileId(WindowId.fromJson(windowIdStr), ViewProfileId.fromJson(profileIdStr));
	}

	@RequestMapping(value = "/lookups/cacheStats", method = RequestMethod.GET)
	public List<String> getLookupCacheStats()
	{
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
		final Topic topic = Topic.builder()
				.name(topicName)
				.type(Type.LOCAL)
				.build();

		final UserNotificationRequestBuilder request = UserNotificationRequest.builder()
				.topic(topic)
				.recipientUserId(toUserId)
				.important(important);

		final UserNotificationTargetType targetType = Check.isEmpty(targetTypeStr) ? null : UserNotificationTargetType.forJsonValue(targetTypeStr);
		if (targetType == UserNotificationTargetType.Window)
		{
			final String targetTableName = documentCollection.getDocumentDescriptorFactory()
					.getDocumentDescriptor(WindowId.fromJson(targetDocumentType))
					.getEntityDescriptor()
					.getTableName();

			final TableRecordReference targetRecord = TableRecordReference.of(targetTableName, Integer.parseInt(targetDocumentId));
			request.targetRecord(targetRecord);
		}

		Services.get(INotificationBL.class).notifyUser(request.build());
	}

	@PostMapping("/websocket/post")
	public void postToWebsocket(
			@RequestParam("endpoint") final String endpoint, @RequestBody final String messageStr)
	{
		final Charset charset = Charset.forName("UTF-8");
		final Map<String, Object> headers = ImmutableMap.<String, Object> builder()
				.put("simpMessageType", SimpMessageType.MESSAGE)
				.put("contentType", new MimeType("application", "json", charset))
				.build();
		final Message<?> message = new GenericMessage<>(messageStr.getBytes(charset), headers);
		websocketSender.sendMessage(endpoint, message);
	}

	@RequestMapping(value = "/sql/loadLimit/warn", method = RequestMethod.PUT)
	public void setSqlLoadLimitWarn(@RequestBody final int limit)
	{
		SqlDocumentsRepository.instance.setLoadLimitWarn(limit);
	}

	@RequestMapping(value = "/sql/loadLimit/max", method = RequestMethod.PUT)
	public void setSqlLoadLimitMax(@RequestBody final int limit)
	{
		SqlDocumentsRepository.instance.setLoadLimitMax(limit);
	}

	@GetMapping("/logger/{loggerName}/_getUpToRoot")
	public List<Map<String, Object>> getLoggersUpToRoot(@PathVariable("loggerName") final String loggerName)
	{
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

	public static enum LoggingModule
	{
		websockets(de.metas.ui.web.websocket.WebSocketConfig.class.getPackage().getName()), view(de.metas.ui.web.view.IView.class.getPackage().getName()), cache(
				org.compiere.util.CCache.class.getName() //
				, org.compiere.util.CacheMgt.class.getName() //
				, org.adempiere.ad.dao.cache.IModelCacheService.class.getName() // model caching
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
		final long httpCacheMaxAgeOld = userSession.getHttpCacheMaxAge();
		userSession.setHttpCacheMaxAge(httpCacheMaxAge);
		return ImmutableMap.of("value", httpCacheMaxAge, "valueOld", httpCacheMaxAgeOld);
	}

	@GetMapping("http.use.AcceptLanguage")
	public Map<String, Object> setUseHttpAcceptLanguage(@RequestParam("value") @ApiParam("Cache-control's max age in seconds") final boolean useHttpAcceptLanguage)
	{
		final boolean useHttpAcceptLanguageOld = userSession.isUseHttpAcceptLanguage();
		userSession.setUseHttpAcceptLanguage(useHttpAcceptLanguage);
		return ImmutableMap.of("value", useHttpAcceptLanguage, "valueOld", useHttpAcceptLanguageOld);
	}

	@GetMapping("websocketLogging")
	public void setWebsocketLoggingConfig(
			@RequestParam("enabled") final boolean enabled,
			@RequestParam(value = "maxLoggedEvents", defaultValue = "500") final int maxLoggedEvents)
	{
		websocketSender.setLogEventsEnabled(enabled);
		websocketSender.setLogEventsMaxSize(maxLoggedEvents);
	}

	@GetMapping("websocketEvents")
	public List<WebsocketEventLogRecord> getWebsocketLoggedEvents(@RequestParam(value = "destinationFilter", required = false) final String destinationFilter)
	{
		return websocketSender.getLoggedEvents(destinationFilter);
	}

}
