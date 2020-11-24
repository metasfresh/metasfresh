package de.metas.security.permissions.bpartner_hierarchy;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Role_Record_Access_Config;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_R_Request;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.model.I_AD_User;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.event.impl.PlainEventBusFactory;
import de.metas.event.log.EventLogService;
import de.metas.event.log.EventLogsRepository;
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceId;
import de.metas.order.OrderId;
import de.metas.payment.PaymentId;
import de.metas.request.RequestId;
import de.metas.security.RoleId;
import de.metas.security.permissions.Access;
import de.metas.security.permissions.bpartner_hierarchy.handlers.BPartnerDependentDocumentHandler;
import de.metas.security.permissions.bpartner_hierarchy.handlers.impl.BPartnerToBPartnerDependentDocumentHandler;
import de.metas.security.permissions.bpartner_hierarchy.handlers.impl.InOutBPartnerDependentDocumentHandler;
import de.metas.security.permissions.bpartner_hierarchy.handlers.impl.InvoiceBPartnerDependentDocumentHandler;
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
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Builder;
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

public class BPartnerHierarchyTestHelper
{
	public final RoleId roleId = RoleId.ofRepoId(123);
	public UserId loggedInUserId;

	public PlainEventBusFactory eventBusFactory;
	public RecordAccessService recordAccessService;
	public ImmutableList<BPartnerDependentDocumentHandler> dependentDocumentHandlers;
	public BPartnerHierarchyRecordAccessHandler bpHierarchyRecordAccessHandler;

	@Builder
	private BPartnerHierarchyTestHelper(
			final boolean setupModelInterceptor,
			final boolean setupBPartnerDependentDocumentEventDispatcher,
			final boolean setupLoggedInUserId)
	{
		AdempiereTestHelper.get().init();

		SpringContextHolder.registerJUnitBean(new EventLogService(mock(EventLogsRepository.class)));
		this.eventBusFactory = new PlainEventBusFactory();

		createAD_Role_Record_Access_Config();
		final ImmutableList<RecordAccessHandler> handlers = ImmutableList.of();
		final RecordAccessConfigService configs = new RecordAccessConfigService(Optional.of(handlers));

		this.dependentDocumentHandlers = ImmutableList.of(
				new BPartnerToBPartnerDependentDocumentHandler(),
				new InOutBPartnerDependentDocumentHandler(),
				new InvoiceBPartnerDependentDocumentHandler(),
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

		if (setupModelInterceptor)
		{
			final BPartnerDependentDocumentInterceptor modelInterceptor = new BPartnerDependentDocumentInterceptor(
					recordAccessService,
					dependentDocumentHandlers,
					eventBusFactory);
			POJOLookupMap.get().addModelValidator(modelInterceptor);
		}

		if (setupBPartnerDependentDocumentEventDispatcher)
		{
			final BPartnerDependentDocumentEventDispatcher dispatcher = new BPartnerDependentDocumentEventDispatcher(
					eventBusFactory,
					bpHierarchyRecordAccessHandler);
			dispatcher.postConstruct();
		}

		if (setupLoggedInUserId)
		{
			loggedInUserId = UserId.ofRepoId(222);
			Env.setLoggedUserId(Env.getCtx(), loggedInUserId);
		}
	}

	private void createAD_Role_Record_Access_Config()
	{
		final I_AD_Role_Record_Access_Config config = newInstance(I_AD_Role_Record_Access_Config.class);
		config.setAD_Role_ID(roleId.getRepoId());
		config.setType(RecordAccessFeature.BPARTNER_HIERARCHY.getName());
		saveRecord(config);
	}

	public UserId createSalesRep(@NonNull final String name)
	{
		final I_AD_User userRecord = newInstance(I_AD_User.class);
		userRecord.setName(name);
		saveRecord(userRecord);
		return UserId.ofRepoId(userRecord.getAD_User_ID());
	}

	public BPartnerId createBPartnerId(
			@NonNull final String name,
			@Nullable final BPartnerId salesRepBPartnerId)
	{
		final I_C_BPartner bpartnerRecord = createBPartnerRecord(name, salesRepBPartnerId);
		return BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID());
	}

	public I_C_BPartner createBPartnerRecord(final String name, final BPartnerId salesRepBPartnerId)
	{
		final I_C_BPartner bpartnerRecord = newInstance(I_C_BPartner.class);
		bpartnerRecord.setName(name);
		bpartnerRecord.setC_BPartner_SalesRep_ID(BPartnerId.toRepoId(salesRepBPartnerId)); // aka Parent
		saveRecord(bpartnerRecord);
		return bpartnerRecord;
	}

	@Builder(builderMethodName = "prepareBPartnerUpdate", builderClassName = "$updateBPartnerBuilder", buildMethodName = "update")
	private void updateBPartner(
			@NonNull final BPartnerId bpartnerId,
			@Nullable final BPartnerId newBPartnerSalesRepId)
	{
		I_C_BPartner bpartnerRecord = load(bpartnerId, I_C_BPartner.class);
		bpartnerRecord.setC_BPartner_SalesRep_ID(BPartnerId.toRepoId(newBPartnerSalesRepId));
		saveRecord(bpartnerRecord);
	}

	public BPartnerContactId createBPartnerContact(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final String name)
	{
		final I_AD_User contactRecord = newInstance(I_AD_User.class);
		contactRecord.setName(name);
		contactRecord.setC_BPartner_ID(bpartnerId.getRepoId());
		saveRecord(contactRecord);
		return BPartnerContactId.ofRepoId(contactRecord.getC_BPartner_ID(), contactRecord.getAD_User_ID());
	}

	public OrderId createOrder(@Nullable final BPartnerId bpartnerId)
	{
		final I_C_Order record = newInstance(I_C_Order.class);
		record.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
		saveRecord(record);
		return OrderId.ofRepoId(record.getC_Order_ID());
	}

	public InvoiceId createInvoice(@Nullable final BPartnerId bpartnerId)
	{
		final I_C_Invoice record = newInstance(I_C_Invoice.class);
		record.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
		saveRecord(record);
		return InvoiceId.ofRepoId(record.getC_Invoice_ID());
	}

	public InOutId createInOut(@Nullable final BPartnerId bpartnerId)
	{
		final I_M_InOut record = newInstance(I_M_InOut.class);
		record.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
		saveRecord(record);
		return InOutId.ofRepoId(record.getM_InOut_ID());
	}

	public PaymentId createPayment(@Nullable final BPartnerId bpartnerId)
	{
		final I_C_Payment record = newInstance(I_C_Payment.class);
		record.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
		saveRecord(record);
		return PaymentId.ofRepoId(record.getC_Payment_ID());
	}

	public RequestId createRequest(@Nullable final BPartnerId bpartnerId)
	{
		final I_R_Request record = newInstance(I_R_Request.class);
		record.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
		saveRecord(record);
		return RequestId.ofRepoId(record.getR_Request_ID());
	}

	public void assertReadWritePermission(
			@NonNull final UserId toUserId,
			@NonNull final TableRecordReference... recordRefs)
	{
		assertReadWritePermissions(true, toUserId, recordRefs);
	}

	public void assertReadWritePermission(
			@NonNull final UserId toUserId,
			@NonNull final RepoIdAware... recordIds)
	{
		assertReadWritePermissions(true, toUserId, recordIds);
	}

	public void assertNoReadWritePermission(
			@NonNull final UserId toUserId,
			@NonNull final TableRecordReference... recordRefs)
	{
		assertReadWritePermissions(false, toUserId, recordRefs);
	}

	public void assertNoReadWritePermission(
			@NonNull final UserId toUserId,
			@NonNull final RepoIdAware... recordIds)
	{
		assertReadWritePermissions(false, toUserId, recordIds);
	}

	public void assertReadWritePermissions(
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

	public void assertReadWritePermissions(
			final boolean expected,
			@NonNull final UserId toUserId,
			@NonNull final RepoIdAware... recordIds)
	{
		for (final RepoIdAware recordId : recordIds)
		{
			final TableRecordReference recordRef = toTableRecordReference(recordId);
			for (final Access access : Arrays.asList(Access.READ, Access.WRITE))
			{
				assertThat(recordAccessService.hasRecordPermission(toUserId, roleId, recordRef, access))
						.as("toUserId=" + toUserId + ", recordRef=" + recordRef + ", access=" + access)
						.isEqualTo(expected);
			}
		}
	}

	private static TableRecordReference toTableRecordReference(@NonNull final RepoIdAware recordId)
	{
		if (recordId instanceof BPartnerId)
		{
			return TableRecordReference.of(I_C_BPartner.Table_Name, recordId);
		}
		else if (recordId instanceof BPartnerContactId)
		{
			return TableRecordReference.of(I_AD_User.Table_Name, recordId);
		}
		else if (recordId instanceof RequestId)
		{
			return TableRecordReference.of(I_R_Request.Table_Name, recordId);
		}
		else
		{
			throw new AdempiereException("Don't know how to convert " + recordId + " to " + TableRecordReference.class.getSimpleName()
					+ ". Please handle the case in this method.");
		}
	}

	public RecordAccess getRecordAccess(final String tableName, RepoIdAware recordId, final Access permission)
	{
		final TableRecordReference recordRef = TableRecordReference.of(tableName, recordId);
		return getRecordAccess(recordRef, permission);
	}

	public RecordAccess getRecordAccess(final TableRecordReference recordRef, final Access permission)
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

	public List<RecordAccess> getAllRecordAccess(final TableRecordReference... recordRefs)
	{
		final ArrayList<RecordAccess> result = new ArrayList<>();
		for (final TableRecordReference recordRef : recordRefs)
		{
			result.addAll(recordAccessService.getAccessesByRecordAndIssuer(recordRef, PermissionIssuer.AUTO_BP_HIERARCHY));
		}
		return result;
	}

	public void dumpRecordAccessFor(final String title, final TableRecordReference... recordRefs)
	{
		Check.assumeNotEmpty(recordRefs, "recordRefs is not empty");

		for (final TableRecordReference recordRef : recordRefs)
		{
			System.out.println("[ " + title + " ] - Record accesses for: " + recordRef);

			for (final RecordAccess recordAccess : getAllRecordAccess(recordRef))
			{
				System.out.println("\t" + recordAccess);
			}
		}
	}
}
