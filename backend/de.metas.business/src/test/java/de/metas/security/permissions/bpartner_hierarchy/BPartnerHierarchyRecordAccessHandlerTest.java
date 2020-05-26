package de.metas.security.permissions.bpartner_hierarchy;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import de.metas.event.log.EventLogsRepository;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Role_Record_Access_Config;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_R_Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.model.I_AD_User;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.event.impl.PlainEventBusFactory;
import de.metas.event.log.EventLogService;
import de.metas.inout.InOutId;
import de.metas.order.OrderId;
import de.metas.payment.PaymentId;
import de.metas.request.RequestId;
import de.metas.security.RoleId;
import de.metas.security.permissions.Access;
import de.metas.security.permissions.bpartner_hierarchy.handlers.BPartnerDependentDocumentHandler;
import de.metas.security.permissions.bpartner_hierarchy.handlers.impl.BPartnerToBPartnerDependentDocumentHandler;
import de.metas.security.permissions.bpartner_hierarchy.handlers.impl.InOutBPartnerDependentDocumentHandler;
import de.metas.security.permissions.bpartner_hierarchy.handlers.impl.OrderBPartnerDependentDocumentHandler;
import de.metas.security.permissions.bpartner_hierarchy.handlers.impl.PaymentBPartnerDependentDocumentHandler;
import de.metas.security.permissions.bpartner_hierarchy.handlers.impl.RequestBPartnerDependentDocumentHandler;
import de.metas.security.permissions.bpartner_hierarchy.handlers.impl.UserBPartnerDependentDocumentHandler;
import de.metas.security.permissions.record_access.PermissionIssuer;
import de.metas.security.permissions.record_access.RecordAccess;
import de.metas.security.permissions.record_access.RecordAccessConfigService;
import de.metas.security.permissions.record_access.RecordAccessFeature;
import de.metas.security.permissions.record_access.RecordAccessService;
import de.metas.security.permissions.record_access.handlers.RecordAccessChangeEventDispatcher;
import de.metas.security.permissions.record_access.handlers.RecordAccessHandler;
import de.metas.user.UserGroupRepository;
import de.metas.user.UserId;
import de.metas.util.lang.RepoIdAware;
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
	private RecordAccessService recordAccessService;

	private final RoleId roleId = RoleId.ofRepoId(123);

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		SpringContextHolder.registerJUnitBean(new EventLogService(mock(EventLogsRepository.class)));
		final PlainEventBusFactory eventBusFactory = new PlainEventBusFactory();

		createAD_Role_Record_Access_Config();
		final ImmutableList<RecordAccessHandler> handlers = ImmutableList.of();
		final RecordAccessConfigService configs = new RecordAccessConfigService(Optional.of(handlers));

		final List<BPartnerDependentDocumentHandler> dependentDocumentHandlers = ImmutableList.of(
				new BPartnerToBPartnerDependentDocumentHandler(),
				new InOutBPartnerDependentDocumentHandler(),
				// new InvoiceBPartnerDependentDocumentHandler(), // cannot used atm because InvoiceDAO is needed and that's in swat
				new OrderBPartnerDependentDocumentHandler(),
				new PaymentBPartnerDependentDocumentHandler(),
				new RequestBPartnerDependentDocumentHandler(),
				new UserBPartnerDependentDocumentHandler());

		recordAccessService = new RecordAccessService(
				configs,
				new UserGroupRepository(),
				eventBusFactory);
		bpHierarchyRecordAccessHandler = new BPartnerHierarchyRecordAccessHandler(recordAccessService, dependentDocumentHandlers);
		configs.setAllHandlers(ImmutableList.of(bpHierarchyRecordAccessHandler));

		final RecordAccessChangeEventDispatcher eventDispatcher = new RecordAccessChangeEventDispatcher(configs, eventBusFactory);
		eventDispatcher.postConstruct();
	}

	private void createAD_Role_Record_Access_Config()
	{
		final I_AD_Role_Record_Access_Config config = newInstance(I_AD_Role_Record_Access_Config.class);
		config.setAD_Role_ID(roleId.getRepoId());
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

	private BPartnerId createBPartner(
			@NonNull final String name,
			@Nullable final BPartnerId salesRepBPartnerId)
	{
		final I_C_BPartner bpartnerRecord = newInstance(I_C_BPartner.class);
		bpartnerRecord.setName(name);
		bpartnerRecord.setC_BPartner_SalesRep_ID(BPartnerId.toRepoId(salesRepBPartnerId)); // aka Parent
		saveRecord(bpartnerRecord);
		return BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID());
	}

	private BPartnerContactId createBPartnerContact(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final String name)
	{
		final I_AD_User contactRecord = newInstance(I_AD_User.class);
		contactRecord.setName(name);
		contactRecord.setC_BPartner_ID(bpartnerId.getRepoId());
		saveRecord(contactRecord);
		return BPartnerContactId.ofRepoId(contactRecord.getC_BPartner_ID(), contactRecord.getAD_User_ID());
	}

	private OrderId createOrder(@Nullable final BPartnerId bpartnerId)
	{
		final I_C_Order record = newInstance(I_C_Order.class);
		record.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
		saveRecord(record);
		return OrderId.ofRepoId(record.getC_Order_ID());
	}

	private InOutId createInOut(@Nullable final BPartnerId bpartnerId)
	{
		final I_M_InOut record = newInstance(I_M_InOut.class);
		record.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
		saveRecord(record);
		return InOutId.ofRepoId(record.getM_InOut_ID());
	}

	private PaymentId createPayment(@Nullable final BPartnerId bpartnerId)
	{
		final I_C_Payment record = newInstance(I_C_Payment.class);
		record.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
		saveRecord(record);
		return PaymentId.ofRepoId(record.getC_Payment_ID());
	}

	private RequestId createRequest(@Nullable final BPartnerId bpartnerId)
	{
		final I_R_Request record = newInstance(I_R_Request.class);
		record.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
		saveRecord(record);
		return RequestId.ofRepoId(record.getR_Request_ID());
	}

	private void assertReadWritePermission(
			@NonNull final UserId toUserId,
			@NonNull final TableRecordReference... recordRefs)
	{
		assertReadWritePermissions(true, toUserId, recordRefs);
	}

	private void assertNoReadWritePermission(
			@NonNull final UserId toUserId,
			@NonNull final TableRecordReference... recordRefs)
	{
		assertReadWritePermissions(false, toUserId, recordRefs);
	}

	private void assertReadWritePermissions(
			final boolean expected,
			@NonNull final UserId toUserId,
			@NonNull final TableRecordReference... recordRefs)
	{
		for (final TableRecordReference recordRef : recordRefs)
		{
			for (final Access access : Arrays.asList(Access.READ, Access.WRITE))
			{
				assertThat(recordAccessService.hasRecordPermission(toUserId, roleId, recordRef, access))
						.as("toUserId=" + toUserId + ", recordRef=" + recordRef + ", access=" + access)
						.isEqualTo(expected);
			}
		}
	}

	private RecordAccess getRecordAccess(final String tableName, RepoIdAware recordId, final Access permission)
	{
		final TableRecordReference recordRef = TableRecordReference.of(tableName, recordId);
		return getRecordAccess(recordRef, permission);
	}

	private RecordAccess getRecordAccess(final TableRecordReference recordRef, final Access permission)
	{
		final ImmutableList<RecordAccess> recordAccesses = recordAccessService.getAccessesByRecordAndIssuer(recordRef, PermissionIssuer.AUTO_BP_HIERARCHY)
				.stream()
				.filter(recordAccess -> permission.equals(recordAccess.getPermission()))
				.collect(ImmutableList.toImmutableList());
		if (recordAccesses.isEmpty())
		{
			throw new AdempiereException("No record access found for " + recordRef + " and " + permission);
		}
		else if (recordAccesses.size() == 1)
		{
			return recordAccesses.get(0);
		}
		else
		{
			throw new AdempiereException("More than one record access found for " + recordRef + " and " + permission + ": " + recordAccesses);
		}
	}

	private List<RecordAccess> getAllRecordAccess(final TableRecordReference[] recordRefs)
	{
		final ArrayList<RecordAccess> result = new ArrayList<>();
		for (final TableRecordReference recordRef : recordRefs)
		{
			result.addAll(recordAccessService.getAccessesByRecordAndIssuer(recordRef, PermissionIssuer.AUTO_BP_HIERARCHY));
		}
		return result;
	}

	@Test
	public void bpartnerHierarchyFeatureIsEnabled()
	{
		assertThat(bpHierarchyRecordAccessHandler.isEnabled()).isTrue();
	}

	@Test
	public void onBPartnerSalesRepChanged_checkPropagation()
	{
		final UserId salesRepId = createSalesRep("SalesRep1");

		final BPartnerId rootBPartnerId = createBPartner("BPartner", null);
		final BPartnerId bpartnerId = createBPartner("BPartner", rootBPartnerId);
		final BPartnerContactId contact1 = createBPartnerContact(bpartnerId, "Contact 1");
		final BPartnerContactId contact2 = createBPartnerContact(bpartnerId, "Contact 2");
		final RequestId requestId = createRequest(bpartnerId);
		final OrderId orderId = createOrder(bpartnerId);
		final InOutId inoutId = createInOut(bpartnerId);
		final PaymentId paymentId = createPayment(bpartnerId);

		final TableRecordReference[] allRecordRefs = new TableRecordReference[] {
				TableRecordReference.of(I_C_BPartner.Table_Name, rootBPartnerId),
				TableRecordReference.of(I_C_BPartner.Table_Name, bpartnerId),
				TableRecordReference.of(I_AD_User.Table_Name, contact1),
				TableRecordReference.of(I_AD_User.Table_Name, contact2),
				TableRecordReference.of(I_R_Request.Table_Name, requestId),
				TableRecordReference.of(I_C_Order.Table_Name, orderId),
				// TableRecordReference.of(I_C_Invoice.Table_Name, invoiceId) // see above why the invoice handler is commented out
				TableRecordReference.of(I_M_InOut.Table_Name, inoutId),
				TableRecordReference.of(I_C_Payment.Table_Name, paymentId),
		};
		assertNoReadWritePermission(salesRepId, allRecordRefs);

		bpHierarchyRecordAccessHandler.onBPartnerSalesRepChanged(BPartnerSalesRepChangedEvent.builder()
				.bpartnerId(rootBPartnerId)
				.newSalesRepId(salesRepId)
				.changedBy(UserId.ofRepoId(222))
				.build());
		assertReadWritePermission(salesRepId, allRecordRefs);

		//
		// Make sure all access records have the right CreatedBy
		for (final RecordAccess access : getAllRecordAccess(allRecordRefs))
		{
			assertThat(access.getCreatedBy()).isEqualTo(UserId.ofRepoId(222));
		}

		//
		// Make sure the Parent_ID and Root_ID are correctly set
		for (final Access permission : Arrays.asList(Access.READ, Access.WRITE))
		{
			final RecordAccess rootBPartner_access = getRecordAccess(I_C_BPartner.Table_Name, rootBPartnerId, permission);
			assertThat(rootBPartner_access.getRootId()).isEqualTo(rootBPartner_access.getId());
			assertThat(rootBPartner_access.getParentId()).isNull();

			final RecordAccess childBPartner_access = getRecordAccess(I_C_BPartner.Table_Name, bpartnerId, permission);
			assertThat(childBPartner_access.getRootId()).isEqualTo(rootBPartner_access.getId());
			assertThat(childBPartner_access.getParentId()).isEqualTo(rootBPartner_access.getId());

			// i=0 => rootBPartner
			// i=1 => childBPartner
			for (int i = 2; i < allRecordRefs.length; i++)
			{
				final TableRecordReference recordRef = allRecordRefs[i];
				final RecordAccess record_access = getRecordAccess(recordRef, permission);
				assertThat(record_access.getRootId()).isEqualTo(rootBPartner_access.getId());
				assertThat(record_access.getParentId()).isEqualTo(childBPartner_access.getId());
			}
		}

		bpHierarchyRecordAccessHandler.onBPartnerSalesRepChanged(BPartnerSalesRepChangedEvent.builder()
				.bpartnerId(rootBPartnerId)
				.oldSalesRepId(salesRepId)
				.newSalesRepId(null)
				.changedBy(UserId.ofRepoId(111))
				.build());
		assertNoReadWritePermission(salesRepId, allRecordRefs);
	}
}
