package de.metas.security.permissions.bpartner_hierarchy;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Role_Record_Access_Config;
import org.compiere.model.I_AD_User_Record_Access;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_R_Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.model.I_AD_User;
import de.metas.bpartner.BPartnerId;
import de.metas.event.impl.PlainEventBusFactory;
import de.metas.event.log.EventLogService;
import de.metas.request.RequestId;
import de.metas.security.permissions.bpartner_hierarchy.handlers.BPartnerDependentDocumentHandler;
import de.metas.security.permissions.bpartner_hierarchy.handlers.impl.BPartnerToBPartnerDependentDocumentHandler;
import de.metas.security.permissions.bpartner_hierarchy.handlers.impl.InOutBPartnerDependentDocumentHandler;
import de.metas.security.permissions.bpartner_hierarchy.handlers.impl.InvoiceBPartnerDependentDocumentHandler;
import de.metas.security.permissions.bpartner_hierarchy.handlers.impl.OrderBPartnerDependentDocumentHandler;
import de.metas.security.permissions.bpartner_hierarchy.handlers.impl.PaymentBPartnerDependentDocumentHandler;
import de.metas.security.permissions.bpartner_hierarchy.handlers.impl.RequestBPartnerDependentDocumentHandler;
import de.metas.security.permissions.bpartner_hierarchy.handlers.impl.UserBPartnerDependentDocumentHandler;
import de.metas.security.permissions.record_access.RecordAccessConfigService;
import de.metas.security.permissions.record_access.RecordAccessFeature;
import de.metas.security.permissions.record_access.RecordAccessService;
import de.metas.security.permissions.record_access.handlers.CompositeRecordAccessHandler;
import de.metas.security.permissions.record_access.handlers.RecordAccessChangeEventDispatcher;
import de.metas.security.permissions.record_access.handlers.RecordAccessHandler;
import de.metas.user.UserGroupRepository;
import de.metas.user.UserId;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
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

@ExtendWith(AdempiereTestWatcher.class)
public class BPartnerHierarchyRecordAccessHandlerTest
{
	private BPartnerHierarchyRecordAccessHandler bpHierarchyRecordAccessHandler;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		SpringContextHolder.registerJUnitBean(new EventLogService());
		final PlainEventBusFactory eventBusFactory = new PlainEventBusFactory();

		createAD_Role_Record_Access_Config();
		final ImmutableList<RecordAccessHandler> handlers = ImmutableList.of();
		final RecordAccessConfigService configs = new RecordAccessConfigService(Optional.of(handlers));

		final List<BPartnerDependentDocumentHandler> dependentDocumentHandlers = ImmutableList.of(
				new BPartnerToBPartnerDependentDocumentHandler(),
				new InOutBPartnerDependentDocumentHandler(),
				new InvoiceBPartnerDependentDocumentHandler(),
				new OrderBPartnerDependentDocumentHandler(),
				new PaymentBPartnerDependentDocumentHandler(),
				new RequestBPartnerDependentDocumentHandler(),
				new UserBPartnerDependentDocumentHandler());

		bpHierarchyRecordAccessHandler = new BPartnerHierarchyRecordAccessHandler(
				new RecordAccessService(
						configs,
						new UserGroupRepository(),
						eventBusFactory),
				dependentDocumentHandlers);

		final RecordAccessChangeEventDispatcher eventDispatcher = new RecordAccessChangeEventDispatcher(configs, eventBusFactory)
		{
			@Override
			public CompositeRecordAccessHandler getHandlers()
			{
				return CompositeRecordAccessHandler.of(ImmutableList.of(bpHierarchyRecordAccessHandler));
			}
		};
		eventDispatcher.postConstruct();
	}

	private void createAD_Role_Record_Access_Config()
	{
		final I_AD_Role_Record_Access_Config config = newInstance(I_AD_Role_Record_Access_Config.class);
		config.setType(RecordAccessFeature.BPARTNER_HIERARCHY.getName());
		saveRecord(config);
	}

	private UserId createSalesRep(@NonNull final String name)
	{
		final I_AD_User userRecord = newInstance(I_AD_User.class);
		userRecord.setName(name);
		saveRecord(userRecord);
		return UserId.ofRepoId(userRecord.getAD_User_ID());
	}

	private BPartnerId createBPartner(@NonNull final String name)
	{
		final I_C_BPartner bpartnerRecord = newInstance(I_C_BPartner.class);
		bpartnerRecord.setName(name);
		saveRecord(bpartnerRecord);
		return BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID());
	}

	private UserId createBPartnerContact(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final String name)
	{
		final I_AD_User userRecord = newInstance(I_AD_User.class);
		userRecord.setName(name);
		userRecord.setC_BPartner_ID(bpartnerId.getRepoId());
		saveRecord(userRecord);
		return UserId.ofRepoId(userRecord.getAD_User_ID());
	}

	private RequestId createRequest(@Nullable final BPartnerId bpartnerId)
	{
		final I_R_Request record = newInstance(I_R_Request.class);
		record.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
		saveRecord(record);
		return RequestId.ofRepoId(record.getR_Request_ID());
	}

	@Test
	public void test()
	{
		assertThat(bpHierarchyRecordAccessHandler.isEnabled()).isTrue();

		final UserId salesRepId = createSalesRep("SalesRep1");
		final BPartnerId bpartnerId = createBPartner("BPartner1");
		createBPartnerContact(bpartnerId, "Contact 1");
		createBPartnerContact(bpartnerId, "Contact 2");

		createRequest(bpartnerId);

		bpHierarchyRecordAccessHandler.onBPartnerSalesRepChanged(BPartnerSalesRepChangedEvent.builder()
				.bpartnerId(bpartnerId)
				.newSalesRepId(salesRepId)
				.build());

		POJOLookupMap.get().dumpStatus("", I_AD_User_Record_Access.Table_Name);
	}
}
