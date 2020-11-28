package de.metas.security.permissions.record_access;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Optional;

import de.metas.event.log.EventLogsRepository;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.assertj.core.api.AbstractBooleanAssert;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Role_Record_Access_Config;
import org.compiere.model.I_AD_User_Record_Access;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.metas.event.impl.PlainEventBusFactory;
import de.metas.event.log.EventLogService;
import de.metas.security.Principal;
import de.metas.security.RoleId;
import de.metas.security.permissions.Access;
import de.metas.user.UserGroupRepository;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
public class RecordAccessServiceTest
{
	private final UserId userId = UserId.ofRepoId(123);
	private final RoleId roleId = RoleId.ofRepoId(124);

	private RecordAccessConfigService configs;
	private RecordAccessService recordAccessService;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		SpringContextHolder.registerJUnitBean(new EventLogService(mock(EventLogsRepository.class)));
		final PlainEventBusFactory eventBusFactory = new PlainEventBusFactory();

		createAD_Role_Record_Access_Config("MyTable");
		// final ImmutableList<RecordAccessHandler> handlers = ImmutableList.of(
		// ManualRecordAccessHandler.ofTableNames(ImmutableList.of("MyTable")));
		this.configs = new RecordAccessConfigService(Optional.empty());

		recordAccessService = new RecordAccessService(
				configs,
				new UserGroupRepository(),
				eventBusFactory);
	}

	private void createAD_Role_Record_Access_Config(@NonNull final String tableName)
	{
		final I_AD_Role_Record_Access_Config config = newInstance(I_AD_Role_Record_Access_Config.class);
		config.setAD_Role_ID(roleId.getRepoId());
		config.setType(RecordAccessFeature.MANUAL_TABLE.getName());
		config.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(tableName));
		saveRecord(config);
	}

	private AbstractBooleanAssert<?> assertHasPermission(
			@NonNull final TableRecordReference recordRef,
			@NonNull Access access)
	{
		return assertThat(recordAccessService.hasRecordPermission(userId, roleId, recordRef, access));
	}

	private RecordAccess getRecordAccessById(@NonNull final RecordAccessId id)
	{
		final I_AD_User_Record_Access record = InterfaceWrapperHelper.load(id, I_AD_User_Record_Access.class);
		return RecordAccessService.toUserGroupRecordAccess(record);
	}

	@Test
	public void saveNew_load()
	{
		final RecordAccess access = RecordAccess.builder()
				.recordRef(TableRecordReference.of("MyTable", 123))
				.principal(Principal.userId(UserId.ofRepoId(111)))
				.permission(Access.READ)
				.issuer(PermissionIssuer.ofCode("bla"))
				.createdBy(UserId.ofRepoId(222))
				.description("some description")
				//
				.id(null)
				.parentId(RecordAccessId.ofRepoId(444))
				.rootId(RecordAccessId.ofRepoId(555))
				//
				.build();
		recordAccessService.saveNew(access);

		final RecordAccess accessLoaded = getRecordAccessById(access.getId());

		assertThat(accessLoaded).isEqualTo(access);
	}

	@Test
	public void saveNew_load_assertRootIdSet()
	{
		final RecordAccess access = RecordAccess.builder()
				.recordRef(TableRecordReference.of("MyTable", 123))
				.principal(Principal.userId(UserId.ofRepoId(111)))
				.permission(Access.READ)
				.issuer(PermissionIssuer.ofCode("bla"))
				.createdBy(UserId.ofRepoId(222))
				.build();
		recordAccessService.saveNew(access);

		assertThat(access.getId()).isNotNull();
		assertThat(access.getRootId()).isEqualTo(access.getId());

		final RecordAccess accessLoaded = getRecordAccessById(access.getId());
		assertThat(accessLoaded.getRootId()).isEqualTo(access.getId());
	}

	@Test
	public void grand_and_revoke_with_different_issuers()
	{
		assertThat(configs.getByRoleId(roleId).isTableHandled("MyTable")).isTrue();

		final TableRecordReference recordRef = TableRecordReference.of("MyTable", 123);
		assertHasPermission(recordRef, Access.READ).isFalse();

		recordAccessService.grantAccess(RecordAccessGrantRequest.builder()
				.recordRef(recordRef)
				.principal(Principal.userId(userId))
				.permission(Access.READ)
				.issuer(PermissionIssuer.ofCode("Issuer1"))
				.requestedBy(UserId.ofRepoId(111))
				.build());
		assertHasPermission(recordRef, Access.READ).isTrue();

		recordAccessService.grantAccess(RecordAccessGrantRequest.builder()
				.recordRef(recordRef)
				.principal(Principal.userId(userId))
				.permission(Access.READ)
				.issuer(PermissionIssuer.ofCode("Issuer2"))
				.requestedBy(UserId.ofRepoId(111))
				.build());
		assertHasPermission(recordRef, Access.READ).isTrue();

		recordAccessService.revokeAccess(RecordAccessRevokeRequest.builder()
				.recordRef(recordRef)
				.principal(Principal.userId(userId))
				.permission(Access.READ)
				.issuer(PermissionIssuer.ofCode("Issuer1"))
				.requestedBy(UserId.ofRepoId(111))
				.build());
		assertHasPermission(recordRef, Access.READ).isTrue();

		recordAccessService.revokeAccess(RecordAccessRevokeRequest.builder()
				.recordRef(recordRef)
				.principal(Principal.userId(userId))
				.permission(Access.READ)
				.issuer(PermissionIssuer.ofCode("Issuer2"))
				.requestedBy(UserId.ofRepoId(111))
				.build());
		assertHasPermission(recordRef, Access.READ).isFalse();
	}
}
