package de.metas.security.permissions.record_access;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import de.metas.event.log.EventLogsRepository;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.common.collect.ImmutableList;

import de.metas.event.impl.PlainEventBusFactory;
import de.metas.event.log.EventLogService;
import de.metas.security.Principal;
import de.metas.security.permissions.Access;
import de.metas.security.permissions.record_access.handlers.RecordAccessHandler;
import de.metas.user.UserGroupRepository;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@ExtendWith(AdempiereTestWatcher.class)
public class UserGroupRecordAccessServiceTest
{
	private static final PermissionIssuer ISSUER = PermissionIssuer.ofCode("bla");
	private static final Principal USER_ID = Principal.userId(UserId.ofRepoId(1));

	private RecordAccessService userGroupRecordAccessService;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(new EventLogService(mock(EventLogsRepository.class)));

		userGroupRecordAccessService = new RecordAccessService(
				new RecordAccessConfigService(Optional.<List<RecordAccessHandler>> empty()),
				new UserGroupRepository(),
				new PlainEventBusFactory());
	}

	private RecordAccess grantAccessAndTest(@NonNull final TableRecordReference recordRef, final Access permission)
	{
		final ImmutableList<RecordAccess> recordAcceses = grantAccessesAndTest(recordRef, permission);
		assertThat(recordAcceses).hasSize(1);
		return recordAcceses.get(0);
	}

	private ImmutableList<RecordAccess> grantAccessesAndTest(@NonNull final TableRecordReference recordRef, final Access... permissions)
	{
		Check.assumeNotEmpty(permissions, "permissions is not empty");

		userGroupRecordAccessService.grantAccess(RecordAccessGrantRequest.builder()
				.recordRef(recordRef)
				.principal(USER_ID)
				.permissions(Arrays.asList(permissions))
				.issuer(ISSUER)
				.requestedBy(UserId.ofRepoId(111))
				.build());

		final ImmutableList<RecordAccess> accessRecords = userGroupRecordAccessService.getAccessesByRecordAndIssuer(recordRef, ISSUER);
		assertThat(accessRecords).hasSize(permissions.length);

		for (int i = 0; i < accessRecords.size(); i++)
		{
			final RecordAccess accessRecord = accessRecords.get(i);
			final Access expectedPermission = permissions[i];

			assertThat(accessRecord.getRecordRef()).isEqualTo(recordRef);
			assertThat(accessRecord.getPrincipal()).isEqualTo(USER_ID);
			assertThat(accessRecord.getPermission()).isEqualTo(expectedPermission);
			assertThat(accessRecord.getIssuer()).isEqualTo(ISSUER);
			assertThat(accessRecord.getCreatedBy()).isEqualTo(UserId.ofRepoId(111));

			assertThat(accessRecord.getId()).isNotNull();
			assertThat(accessRecord.getRootId()).isEqualTo(accessRecord.getId());
		}

		return accessRecords;
	}

	@Test
	public void grantAccess_READ_and_WRITE()
	{
		final TableRecordReference order = TableRecordReference.of("C_Order", 123);
		grantAccessesAndTest(order, Access.READ, Access.WRITE);
	}

	@Nested
	public class copyAccess
	{
		@Test
		public void existingRW_grantRW_revokeRW()
		{
			final TableRecordReference order = TableRecordReference.of("C_Order", 123);
			final TableRecordReference newBPartner = TableRecordReference.of("C_BPartner", 1);
			final TableRecordReference previousBPartner = TableRecordReference.of("C_BPartner", 2);

			grantAccessesAndTest(order, Access.READ, Access.WRITE);
			grantAccessesAndTest(newBPartner, Access.READ, Access.WRITE);
			grantAccessesAndTest(previousBPartner, Access.READ, Access.WRITE);

			userGroupRecordAccessService.copyAccess(RecordAccessCopyRequest.builder()
					.target(order)
					.grantFrom(newBPartner)
					.revokeFrom(previousBPartner)
					.issuer(ISSUER)
					.requestedBy(UserId.ofRepoId(555))
					.build());

			final ImmutableList<RecordAccess> accessRecords = userGroupRecordAccessService.getAccessesByRecordAndIssuer(order, ISSUER);
			assertThat(accessRecords).hasSize(2);

			{
				final RecordAccess accessRecord = accessRecords.get(0);
				assertThat(accessRecord.getRecordRef()).isEqualTo(order);
				assertThat(accessRecord.getPrincipal()).isEqualTo(USER_ID);
				assertThat(accessRecord.getPermission()).isEqualTo(Access.READ);
				assertThat(accessRecord.getIssuer()).isEqualTo(ISSUER);
				assertThat(accessRecord.getCreatedBy()).isEqualTo(UserId.ofRepoId(111));
				assertThat(accessRecord.getId()).isNotNull();
				assertThat(accessRecord.getParentId()).isNull();
				assertThat(accessRecord.getRootId()).isEqualTo(accessRecord.getId());
			}
			{
				final RecordAccess accessRecord = accessRecords.get(1);
				assertThat(accessRecord.getRecordRef()).isEqualTo(order);
				assertThat(accessRecord.getPrincipal()).isEqualTo(USER_ID);
				assertThat(accessRecord.getPermission()).isEqualTo(Access.WRITE);
				assertThat(accessRecord.getIssuer()).isEqualTo(ISSUER);
				assertThat(accessRecord.getCreatedBy()).isEqualTo(UserId.ofRepoId(111));
				assertThat(accessRecord.getId()).isNotNull();
				assertThat(accessRecord.getParentId()).isNull();
				assertThat(accessRecord.getRootId()).isEqualTo(accessRecord.getId());
			}
		}

		@Test
		public void existingNONE_grantREAD()
		{
			final TableRecordReference order = TableRecordReference.of("C_Order", 123);
			final TableRecordReference newBPartner = TableRecordReference.of("C_BPartner", 1);
			final TableRecordReference previousBPartner = TableRecordReference.of("C_BPartner", 2);

			final RecordAccess newBPartner_read = grantAccessAndTest(newBPartner, Access.READ);

			userGroupRecordAccessService.copyAccess(RecordAccessCopyRequest.builder()
					.target(order)
					.grantFrom(newBPartner)
					.revokeFrom(previousBPartner)
					.issuer(ISSUER)
					.requestedBy(UserId.ofRepoId(555))
					.build());

			final ImmutableList<RecordAccess> accessRecords = userGroupRecordAccessService.getAccessesByRecordAndIssuer(order, ISSUER);
			assertThat(accessRecords).hasSize(1);

			{
				final RecordAccess accessRecord = accessRecords.get(0);
				assertThat(accessRecord.getRecordRef()).isEqualTo(order);
				assertThat(accessRecord.getPrincipal()).isEqualTo(USER_ID);
				assertThat(accessRecord.getPermission()).isEqualTo(Access.READ);
				assertThat(accessRecord.getIssuer()).isEqualTo(ISSUER);
				assertThat(accessRecord.getCreatedBy()).isEqualTo(UserId.ofRepoId(555));
				assertThat(accessRecord.getId()).isNotNull();
				assertThat(accessRecord.getParentId()).isEqualTo(newBPartner_read.getId());
				assertThat(accessRecord.getRootId()).isEqualTo(newBPartner_read.getRootId());
			}
		}

		@Test
		public void existingREAD_revokeREAD()
		{
			final TableRecordReference order = TableRecordReference.of("C_Order", 123);
			final TableRecordReference newBPartner = TableRecordReference.of("C_BPartner", 1);
			final TableRecordReference previousBPartner = TableRecordReference.of("C_BPartner", 2);

			grantAccessAndTest(order, Access.READ);
			grantAccessAndTest(previousBPartner, Access.READ);

			userGroupRecordAccessService.copyAccess(RecordAccessCopyRequest.builder()
					.target(order)
					.grantFrom(newBPartner)
					.revokeFrom(previousBPartner)
					.issuer(ISSUER)
					.requestedBy(UserId.ofRepoId(555))
					.build());

			final ImmutableList<RecordAccess> accessRecords = userGroupRecordAccessService.getAccessesByRecordAndIssuer(order, ISSUER);
			assertThat(accessRecords).hasSize(0);
		}

		@Test
		public void existingREAD_revokeREAD_grantREAD()
		{
			final TableRecordReference order = TableRecordReference.of("C_Order", 123);
			final TableRecordReference newBPartner = TableRecordReference.of("C_BPartner", 1);
			final TableRecordReference previousBPartner = TableRecordReference.of("C_BPartner", 2);

			final RecordAccess order_read = grantAccessAndTest(order, Access.READ);
			grantAccessAndTest(newBPartner, Access.READ);
			grantAccessAndTest(previousBPartner, Access.READ);

			userGroupRecordAccessService.copyAccess(RecordAccessCopyRequest.builder()
					.target(order)
					.grantFrom(newBPartner)
					.revokeFrom(previousBPartner)
					.issuer(ISSUER)
					.requestedBy(UserId.ofRepoId(555))
					.build());

			final ImmutableList<RecordAccess> accessRecords = userGroupRecordAccessService.getAccessesByRecordAndIssuer(order, ISSUER);
			assertThat(accessRecords).hasSize(1);

			assertThat(accessRecords.get(0)).isEqualTo(order_read);
		}
	}
}
