package de.metas.security.permissions.record_access;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.assertj.core.api.IterableAssert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.ImmutableSet;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class, EventLogService.class })
public class UserGroupRecordAccessServiceTest
{
	private static final Principal userId = Principal.userId(UserId.ofRepoId(1));

	private RecordAccessService userGroupRecordAccessService;

	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		userGroupRecordAccessService = new RecordAccessService(
				new RecordAccessConfigService(Optional.<List<RecordAccessHandler>> empty()),
				new UserGroupRepository(),
				new PlainEventBusFactory());
	}

	@Test
	public void grantAccess_READ_and_WRITE()
	{
		final TableRecordReference orderRef = TableRecordReference.of("C_Order", 123);
		grantAccessesAndTest(orderRef, Access.READ, Access.WRITE);
	}

	private void grantAccessesAndTest(@NonNull final TableRecordReference recordRef, final Access... accesses)
	{
		Check.assumeNotEmpty(accesses, "accesses is not empty");

		userGroupRecordAccessService.grantAccess(RecordAccessGrantRequest.builder()
				.recordRef(recordRef)
				.principal(userId)
				.permissions(Arrays.asList(accesses))
				.issuer(PermissionIssuer.MANUAL)
				.build());

		assertRecordAccesses(recordRef).isEqualTo(userGroupRecordAccesses(recordRef, accesses));
	}

	private IterableAssert<RecordAccess> assertRecordAccesses(final TableRecordReference recordRef)
	{
		return assertThat(userGroupRecordAccessService.getAccessesByRecord(recordRef));
	}

	private ImmutableSet<RecordAccess> userGroupRecordAccesses(
			@NonNull final TableRecordReference recordRef,
			@NonNull final Access... accesses)
	{
		return Stream.of(accesses)
				.map(access -> RecordAccess.builder()
						.recordRef(recordRef)
						.principal(userId)
						.permission(access)
						.issuer(PermissionIssuer.MANUAL)
						.build())
				.collect(ImmutableSet.toImmutableSet());

	}

	@Test
	public void copyAccess_SamePermissions()
	{
		final TableRecordReference orderRef = TableRecordReference.of("C_Order", 123);
		final TableRecordReference bpartnerRef1 = TableRecordReference.of("C_BPartner", 1);
		final TableRecordReference bpartnerRef2 = TableRecordReference.of("C_BPartner", 2);

		grantAccessesAndTest(orderRef, Access.READ, Access.WRITE);
		grantAccessesAndTest(bpartnerRef1, Access.READ, Access.WRITE);
		grantAccessesAndTest(bpartnerRef2, Access.READ, Access.WRITE);

		userGroupRecordAccessService.copyAccess(orderRef, bpartnerRef1, bpartnerRef2);
		assertRecordAccesses(orderRef).isEqualTo(userGroupRecordAccesses(orderRef, Access.READ, Access.WRITE));
	}
}
