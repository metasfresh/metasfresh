package de.metas.ui.web.debug;

import java.util.List;

import org.adempiere.ad.dao.IQueryStatisticsLogger;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.CacheMgt;
import org.compiere.util.DisplayType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.metas.event.Event;
import de.metas.event.Event.Builder;
import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.menu.MenuTreeRepository;
import de.metas.ui.web.notification.UserNotification;
import de.metas.ui.web.notification.UserNotification.TargetType;
import de.metas.ui.web.process.ProcessInstancesRepository;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.json.JSONDocumentViewResult;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.DocumentViewResult;
import de.metas.ui.web.window.model.DocumentViewsRepository;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.ui.web.window.model.sql.SqlDocumentsRepository;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
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
	private DocumentViewsRepository documentViewsRepo;

	@Autowired
	@Lazy
	private ProcessInstancesRepository pinstancesRepo;

	@Autowired
	@Lazy
	private IQueryStatisticsLogger statisticsLogger;

	@RequestMapping(value = "/cacheReset", method = RequestMethod.GET)
	public void cacheReset()
	{
		CacheMgt.get().reset();
		documentCollection.cacheReset();
		menuTreeRepo.cacheReset();
		pinstancesRepo.cacheReset();
	}

	private static final void logResourceValueChanged(final String name, final Object value, final Object valueOld)
	{
		System.out.println("*********************************************************************************************");
		System.out.println("Changed " + name + " " + valueOld + " -> " + value);
		System.out.println("*********************************************************************************************");

	}

	// NOTE: using String parameter because when using boolean parameter, we get following error in swagger-ui:
	// swagger-ui.min.js:10 Uncaught TypeError: Cannot read property 'toLowerCase' of undefined
	@RequestMapping(value = "/showColumnNamesForCaption", method = RequestMethod.PUT)
	public void setShowColumnNamesForCaption(@RequestBody final String showColumnNamesForCaptionStr)
	{
		final boolean showColumnNamesForCaption = DisplayType.toBoolean(showColumnNamesForCaptionStr);
		final Object showColumnNamesForCaptionOldObj = userSession.setProperty(JSONOptions.SESSION_ATTR_ShowColumnNamesForCaption, showColumnNamesForCaption);
		logResourceValueChanged("ShowColumnNamesForCaption", showColumnNamesForCaption, showColumnNamesForCaptionOldObj);
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
	public List<JSONDocumentViewResult> getViewsList()
	{
		return documentViewsRepo.getViews()
				.stream()
				.map(DocumentViewResult::of)
				.map(JSONDocumentViewResult::of)
				.collect(GuavaCollectors.toImmutableList());
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
	, @RequestParam(name = "targetDocumentType", required = false, defaultValue = "143") final int targetDocumentType//
	, @RequestParam(name = "targetDocumentId", required = false) final String targetDocumentId//
	)
	{
		final Topic topic = Topic.builder()
				.setName(topicName)
				.setType(Type.LOCAL)
				.build();

		final Builder eventBuilder = Event.builder()
				.setSummary("summary")
				.setDetailPlain(message)
				.putProperty(UserNotification.EVENT_PARAM_Important, important);
		if (toUserId > 0)
		{
			eventBuilder.addRecipient_User_ID(toUserId);
		}

		final TargetType targetType = Check.isEmpty(targetTypeStr) ? null : TargetType.forJsonValue(targetTypeStr);
		if (targetType == TargetType.Window)
		{
			final String targetTableName = documentCollection.getDocumentDescriptorFactory()
					.getDocumentDescriptor(targetDocumentType)
					.getEntityDescriptor()
					.getTableName();

			final TableRecordReference targetRecord = TableRecordReference.of(targetTableName, Integer.parseInt(targetDocumentId));
			eventBuilder.setRecord(targetRecord);
		}

		final Event event = eventBuilder.build();

		Services.get(IEventBusFactory.class)
				.getEventBus(topic)
				.postEvent(event);
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

}
