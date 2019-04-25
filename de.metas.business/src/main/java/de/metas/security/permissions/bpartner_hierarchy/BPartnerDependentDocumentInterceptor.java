package de.metas.security.permissions.bpartner_hierarchy;

import java.util.List;

import org.adempiere.ad.modelvalidator.AbstractModelInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Client;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.logging.LogManager;
import de.metas.security.Principal;
import de.metas.security.permissions.Access;
import de.metas.security.permissions.bpartner_hierarchy.handlers.BPartnerDependentDocument;
import de.metas.security.permissions.bpartner_hierarchy.handlers.BPartnerDependentDocumentHandler;
import de.metas.security.permissions.bpartner_hierarchy.handlers.BPartnerDependentDocumentHandlersMap;
import de.metas.security.permissions.record_access.RecordAccessFeature;
import de.metas.security.permissions.record_access.RecordAccessGrantRequest;
import de.metas.security.permissions.record_access.RecordAccessService;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

/**
 * Intercepts BPartner related documents and fires {@link BPartnerDependentDocumentEvent} which will be dispatched asynchronously by {@link BPartnerDependentDocumentEventDispatcher}.
 */
@Component
class BPartnerDependentDocumentInterceptor extends AbstractModelInterceptor
{
	private static final Logger logger = LogManager.getLogger(BPartnerDependentDocumentInterceptor.class);

	private final RecordAccessService recordAccessService;
	private final BPartnerDependentDocumentHandlersMap dependentDocumentHandlers;
	private final IEventBus eventBus;

	public BPartnerDependentDocumentInterceptor(
			@NonNull final RecordAccessService recordAccessService,
			@NonNull final List<BPartnerDependentDocumentHandler> dependentDocumentHandlers,
			@NonNull final IEventBusFactory eventBusFactory)
	{
		this.recordAccessService = recordAccessService;

		this.dependentDocumentHandlers = BPartnerDependentDocumentHandlersMap.of(dependentDocumentHandlers);
		logger.info("{}", this.dependentDocumentHandlers);

		eventBus = eventBusFactory.getEventBus(BPartnerDependentDocumentEventDispatcher.EVENTS_TOPIC);
	}

	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		dependentDocumentHandlers
				.getTableNames()
				.forEach(dependentTableName -> engine.addModelChange(dependentTableName, this));
	}

	private boolean isEnabled()
	{
		return recordAccessService.isFeatureEnabled(RecordAccessFeature.BPARTNER_HIERARCHY);
	}

	@Override
	public void onModelChange(final Object model, final ModelChangeType changeType)
	{
		if (!isEnabled())
		{
			return;
		}

		if (changeType.isAfter())
		{
			if (changeType.isNew())
			{
				final BPartnerDependentDocument doc = dependentDocumentHandlers.extractBPartnerDependentDocumentFromDocumentObj(model).orElse(null);

				// guard against null, shall not happen
				if (doc == null)
				{
					return;
				}

				grantReadWritePermissionsToCreator(doc.getDocumentRef());
				fireBPartnerDependentDocumentEvent(BPartnerDependentDocumentEvent.newRecord(doc));
			}
			else if (changeType.isChange())
			{
				final BPartnerDependentDocument doc = dependentDocumentHandlers.extractBPartnerDependentDocumentFromDocumentObj(model).orElse(null);

				// guard against null, shall not happen
				if (doc == null)
				{
					return;
				}

				if (!doc.isBPartnerChanged())
				{
					return;
				}

				fireBPartnerDependentDocumentEvent(BPartnerDependentDocumentEvent.bpartnerChanged(doc));
			}
		}
	}

	private void grantReadWritePermissionsToCreator(final TableRecordReference documentRef)
	{
		recordAccessService.grantAccess(RecordAccessGrantRequest.builder()
				.recordRef(documentRef)
				.principal(Principal.userId(Env.getLoggedUserId()))
				.permission(Access.READ)
				.permission(Access.WRITE)
				.build());
	}

	private void fireBPartnerDependentDocumentEvent(final BPartnerDependentDocumentEvent event)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager.runAfterCommit(() -> eventBus.postObject(event));
	}
}
