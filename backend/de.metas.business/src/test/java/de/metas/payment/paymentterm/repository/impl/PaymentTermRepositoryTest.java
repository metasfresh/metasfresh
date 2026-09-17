/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.payment.paymentterm.repository.impl;

import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.PaymentTermId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_PaymentTerm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

public class PaymentTermRepositoryTest
{
	private static final ClientId CLIENT_ID = ClientId.ofRepoId(1000000);
	private static final ClientId OTHER_CLIENT_ID = ClientId.ofRepoId(1000001);
	private static final OrgId ORG_ID = OrgId.ofRepoId(1000000);
	private static final OrgId OTHER_ORG_ID = OrgId.ofRepoId(1000002);

	private PaymentTermRepository paymentTermRepository;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		paymentTermRepository = new PaymentTermRepository();
	}

	@Test
	public void getImmediatePaymentTermId_returnsEmpty_whenNoneMatch()
	{
		// no records at all
		final Optional<PaymentTermId> result = paymentTermRepository.getImmediatePaymentTermId(CLIENT_ID, ORG_ID);
		assertThat(result).isEmpty();
	}

	@Test
	public void getImmediatePaymentTermId_findsMatchingTerm()
	{
		final PaymentTermId expectedId = createPaymentTerm("immediate", CLIENT_ID, ORG_ID, 0, false, false, true);

		final Optional<PaymentTermId> result = paymentTermRepository.getImmediatePaymentTermId(CLIENT_ID, ORG_ID);

		assertThat(result).contains(expectedId);
	}

	@Test
	public void getImmediatePaymentTermId_skipsInactive()
	{
		final PaymentTermId inactiveId = createPaymentTerm("inactive", CLIENT_ID, ORG_ID, 0, false, false, false);

		final Optional<PaymentTermId> result = paymentTermRepository.getImmediatePaymentTermId(CLIENT_ID, ORG_ID);

		assertThat(result).isEmpty();
		assertThat(inactiveId).isNotNull(); // sanity check
	}

	@Test
	public void getImmediatePaymentTermId_skipsComplexTerm()
	{
		createPaymentTerm("complex", CLIENT_ID, ORG_ID, 0, true /*complex*/, false, true);

		final Optional<PaymentTermId> result = paymentTermRepository.getImmediatePaymentTermId(CLIENT_ID, ORG_ID);

		assertThat(result).isEmpty();
	}

	@Test
	public void getImmediatePaymentTermId_skipsNonZeroNetDays()
	{
		createPaymentTerm("net30", CLIENT_ID, ORG_ID, 30, false, false, true);

		final Optional<PaymentTermId> result = paymentTermRepository.getImmediatePaymentTermId(CLIENT_ID, ORG_ID);

		assertThat(result).isEmpty();
	}

	@Test
	public void getImmediatePaymentTermId_skipsOtherClient()
	{
		createPaymentTerm("other-client", OTHER_CLIENT_ID, ORG_ID, 0, false, false, true);

		final Optional<PaymentTermId> result = paymentTermRepository.getImmediatePaymentTermId(CLIENT_ID, ORG_ID);

		assertThat(result).isEmpty();
	}

	@Test
	public void getImmediatePaymentTermId_skipsOtherOrg()
	{
		createPaymentTerm("other-org", CLIENT_ID, OTHER_ORG_ID, 0, false, false, true);

		final Optional<PaymentTermId> result = paymentTermRepository.getImmediatePaymentTermId(CLIENT_ID, ORG_ID);

		assertThat(result).isEmpty();
	}

	@Test
	public void getImmediatePaymentTermId_fallsBackToOrgAny()
	{
		final PaymentTermId systemWideId = createPaymentTerm("system-wide", CLIENT_ID, OrgId.ANY, 0, false, false, true);

		final Optional<PaymentTermId> result = paymentTermRepository.getImmediatePaymentTermId(CLIENT_ID, ORG_ID);

		assertThat(result).contains(systemWideId);
	}

	@Test
	public void getImmediatePaymentTermId_prefersOrgSpecificOverOrgAny()
	{
		// create system-wide FIRST (lower ID) so we know we're not just picking by insertion order
		createPaymentTerm("system-wide", CLIENT_ID, OrgId.ANY, 0, false, false, true);
		final PaymentTermId orgSpecificId = createPaymentTerm("org-specific", CLIENT_ID, ORG_ID, 0, false, false, true);

		final Optional<PaymentTermId> result = paymentTermRepository.getImmediatePaymentTermId(CLIENT_ID, ORG_ID);

		assertThat(result).contains(orgSpecificId);
	}

	@Test
	public void getImmediatePaymentTermId_prefersDefaultWhenMultipleMatch()
	{
		createPaymentTerm("non-default", CLIENT_ID, ORG_ID, 0, false, false, true);
		final PaymentTermId defaultId = createPaymentTerm("default", CLIENT_ID, ORG_ID, 0, false, true /*isDefault*/, true);

		final Optional<PaymentTermId> result = paymentTermRepository.getImmediatePaymentTermId(CLIENT_ID, ORG_ID);

		assertThat(result).contains(defaultId);
	}

	@NonNull
	private PaymentTermId createPaymentTerm(
			@NonNull final String value,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			final int netDays,
			final boolean isComplex,
			final boolean isDefault,
			final boolean isActive)
	{
		final I_C_PaymentTerm record = newInstance(I_C_PaymentTerm.class);
		InterfaceWrapperHelper.setValue(record, I_C_PaymentTerm.COLUMNNAME_AD_Client_ID, clientId.getRepoId());
		record.setAD_Org_ID(orgId.getRepoId());
		record.setValue(value);
		record.setName(value);
		record.setNetDays(netDays);
		record.setIsComplex(isComplex);
		record.setIsDefault(isDefault);
		record.setIsActive(isActive);
		record.setDiscount(BigDecimal.ZERO);
		record.setDiscount2(BigDecimal.ZERO);
		save(record);
		return PaymentTermId.ofRepoId(record.getC_PaymentTerm_ID());
	}
}
