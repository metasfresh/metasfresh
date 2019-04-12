package de.metas.security.permissions.record_access;

import org.adempiere.ad.modelvalidator.AbstractModelInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Client;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableSet;

import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.security.Principal;
import de.metas.security.permissions.Access;
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

@Component
class BPartnerUserGroupAccessChangeListener_DependentDocumentsInterceptor extends AbstractModelInterceptor
{
	private final UserGroupRecordAccessService userGroupRecordAccessService;
	private final ImmutableSet<String> dependentDocumentTableNames;
	private final IEventBus eventBus;

	public BPartnerUserGroupAccessChangeListener_DependentDocumentsInterceptor(
			@NonNull final UserGroupRecordAccessService userGroupRecordAccessService,
			@NonNull final BPartnerUserGroupAccessChangeListener listener,
			@NonNull final IEventBusFactory eventBusFactory)
	{
		this.userGroupRecordAccessService = userGroupRecordAccessService;
		this.dependentDocumentTableNames = ImmutableSet.copyOf(listener.getDependentDocumentTableNames());
		eventBus = eventBusFactory.getEventBus(BPartnerDependentDocumentCreatedEventDispatcher.EVENTS_TOPIC);
	}

	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		dependentDocumentTableNames.forEach(dependentTableName -> engine.addModelChange(dependentTableName, this));
	}

	@Override
	public void onModelChange(final Object model, final ModelChangeType changeType)
	{
		if (changeType.isAfter() && changeType.isNew())
		{
			final TableRecordReference documentRef = TableRecordReference.of(model);
			grantReadWritePermissionsToCreator(documentRef);
			fireBPartnerDependentDocumentCreatedEvent(documentRef);
		}
	}

	private void grantReadWritePermissionsToCreator(final TableRecordReference documentRef)
	{
		userGroupRecordAccessService.grantAccess(UserGroupRecordAccessGrantRequest.builder()
				.recordRef(documentRef)
				.principal(Principal.userId(Env.getLoggedUserId()))
				.permission(Access.READ)
				.permission(Access.WRITE)
				.build());
	}

	private void fireBPartnerDependentDocumentCreatedEvent(final TableRecordReference documentRef)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		final BPartnerDependentDocumentCreatedEvent event = BPartnerDependentDocumentCreatedEvent.of(documentRef);
		trxManager.runAfterCommit(() -> eventBus.postObject(event));
	}

}
