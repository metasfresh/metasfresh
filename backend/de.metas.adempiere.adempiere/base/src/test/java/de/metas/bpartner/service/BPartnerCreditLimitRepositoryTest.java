/**
 *
 */
package de.metas.bpartner.service;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.OrgMappingId;
import de.metas.bpartner.creditLimit.BPartnerCreditLimit;
import de.metas.bpartner.creditLimit.BPartnerCreditLimitCreateRequest;
import de.metas.bpartner.creditLimit.BPartnerCreditLimitId;
import de.metas.bpartner.creditLimit.CreditLimitTypeId;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.ICurrencyBL;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_CreditLimit;
import org.compiere.model.I_C_CreditLimit_Type;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class BPartnerCreditLimitRepositoryTest
{
	private BPartnerCreditLimitRepository repository;
	private I_C_CreditLimit_Type typeInsurance;
	private I_C_CreditLimit_Type typeManagement;

	private CurrencyId mockOrgCurrencyId;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repository = new BPartnerCreditLimitRepository();

		typeInsurance = newCreditLimitType()
				.name("Insurance")
				.seqNo(1)
				.build();

		typeManagement = newCreditLimitType()
				.name("Management")
				.seqNo(2)
				.build();

		SystemTime.setFixedTimeSource("2018-02-28T13:13:13+01:00[Europe/Berlin]");

		//dev-note: the ICurrencyBL returned it's a PlainCurrencyBL so the ClientId.METASFRESH, OrgId.MAIN sent as param don't matter
		mockOrgCurrencyId = Services.get(ICurrencyBL.class).getBaseCurrencyId(ClientId.METASFRESH, OrgId.MAIN);
	}

	@Test
	public void retrieveLimitWhenHavingSameTypeWithDifferentDates()
	{
		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		save(partner);

		newBPCreditLimit()
				.partner(partner)
				.amount(BigDecimal.valueOf(100))
				.type(typeInsurance)
				.dateFrom(TimeUtil.parseTimestamp("2018-02-10"))
				.build();

		newBPCreditLimit()
				.partner(partner)
				.amount(BigDecimal.valueOf(200))
				.type(typeInsurance)
				.dateFrom(TimeUtil.parseTimestamp("2018-02-20"))
				.build();

		final Timestamp today = SystemTime.asDayTimestamp();

		final BigDecimal limitAmount = repository.retrieveCreditLimitByBPartnerId(partner.getC_BPartner_ID(), today);

		assertThat(limitAmount).isEqualTo(BigDecimal.valueOf(200));

	}

	@Test
	public void retrieveLimitWhenHavingDifferentTypeWithDifferentDates1()
	{
		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		save(partner);

		newBPCreditLimit()
				.partner(partner)
				.amount(BigDecimal.valueOf(100))
				.type(typeManagement)
				.dateFrom(TimeUtil.parseTimestamp("2018-02-10"))
				.build();

		newBPCreditLimit()
				.partner(partner)
				.amount(BigDecimal.valueOf(200))
				.type(typeInsurance)
				.dateFrom(TimeUtil.parseTimestamp("2018-02-20"))
				.build();

		final Timestamp today = SystemTime.asDayTimestamp();

		final BigDecimal limitAmount = repository.retrieveCreditLimitByBPartnerId(partner.getC_BPartner_ID(), today);

		assertThat(limitAmount).isEqualTo(BigDecimal.valueOf(200));
	}

	@Test
	public void retrieveLimitWhenHavingDifferentTypeWithDifferentDates2()
	{
		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		save(partner);

		newBPCreditLimit()
				.partner(partner)
				.amount(BigDecimal.valueOf(100))
				.type(typeInsurance)
				.dateFrom(TimeUtil.parseTimestamp("2018-02-10"))
				.build();

		newBPCreditLimit()
				.partner(partner)
				.amount(BigDecimal.valueOf(200))
				.type(typeManagement)
				.dateFrom(TimeUtil.parseTimestamp("2018-02-20"))
				.build();

		final Timestamp today = SystemTime.asDayTimestamp();

		final BigDecimal limitAmount = repository.retrieveCreditLimitByBPartnerId(partner.getC_BPartner_ID(), today);

		assertThat(limitAmount).isEqualTo(BigDecimal.valueOf(200));
	}

	@Test
	public void givenCreateCreditLimitReq_withAllFieldSet_whenCreateOrUpdate_thenSuccess()
	{
		final BPartnerCreditLimitCreateRequest request = BPartnerCreditLimitCreateRequest.builder()
				.bpartnerId(BPartnerId.ofRepoId(1))
				.orgId(OrgId.ofRepoId(2))
				.creditLimit(BPartnerCreditLimit.builder()
									 .active(true)
									 .processed(true)
									 .amount(Money.of(10, mockOrgCurrencyId))
									 .creditLimitTypeId(CreditLimitTypeId.ofRepoId(3))
									 .dateFrom(Instant.ofEpochMilli(0))
									 .orgMappingId(OrgMappingId.ofRepoId(4))
									 .build())
				.build();

		final BPartnerCreditLimit storedCreditLimit = repository.createOrUpdate(request);

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(storedCreditLimit.getBPartnerId()).isEqualTo(request.getBpartnerId());
		softly.assertThat(storedCreditLimit.isActive()).isEqualTo(request.getCreditLimit().isActive());
		softly.assertThat(storedCreditLimit.isProcessed()).isEqualTo(request.getCreditLimit().isProcessed());
		softly.assertThat(storedCreditLimit.getAmount()).isEqualTo(request.getCreditLimit().getAmount());
		softly.assertThat(storedCreditLimit.getCreditLimitTypeId()).isEqualTo(request.getCreditLimit().getCreditLimitTypeId());
		softly.assertThat(storedCreditLimit.getDateFrom()).isEqualTo(request.getCreditLimit().getDateFrom());
		softly.assertThat(storedCreditLimit.getOrgMappingId()).isEqualTo(request.getCreditLimit().getOrgMappingId());

		softly.assertAll();
	}

	@Test
	public void givenCreateCreditLimitReq_withOnlyMandatoryFieldSet_whenCreateOrUpdate_thenSuccess()
	{
		final BPartnerCreditLimitCreateRequest request = BPartnerCreditLimitCreateRequest.builder()
				.bpartnerId(BPartnerId.ofRepoId(1))
				.orgId(OrgId.ofRepoId(2))
				.creditLimit(BPartnerCreditLimit.builder()
									 .amount(Money.of(10, mockOrgCurrencyId))
									 .creditLimitTypeId(CreditLimitTypeId.ofRepoId(3))
									 .build())
				.build();

		final BPartnerCreditLimit storedCreditLimit = repository.createOrUpdate(request);

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(storedCreditLimit.getBPartnerId()).isEqualTo(request.getBpartnerId());
		softly.assertThat(storedCreditLimit.isActive()).isEqualTo(false);
		softly.assertThat(storedCreditLimit.isProcessed()).isEqualTo(false);
		softly.assertThat(storedCreditLimit.getAmount()).isEqualTo(request.getCreditLimit().getAmount());
		softly.assertThat(storedCreditLimit.getCreditLimitTypeId()).isEqualTo(request.getCreditLimit().getCreditLimitTypeId());

		softly.assertAll();
	}

	@Test
	public void givenBPartner_andCreditLimitIdToExclude_whenDeactivateCreditLimitsByBPartnerExcept_thenSuccess()
	{
		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		save(partner);

		newBPCreditLimit()
				.partner(partner)
				.amount(BigDecimal.valueOf(100))
				.type(typeInsurance)
				.dateFrom(TimeUtil.parseTimestamp("2018-02-10"))
				.build();

		final I_C_BPartner_CreditLimit creditLimitToKeep = newBPCreditLimit()
				.partner(partner)
				.amount(BigDecimal.valueOf(200))
				.type(typeManagement)
				.dateFrom(TimeUtil.parseTimestamp("2018-02-20"))
				.build();

		repository.deactivateCreditLimitsByBPartnerExcept(BPartnerId.ofRepoId(partner.getC_BPartner_ID()),
														  ImmutableList.of(BPartnerCreditLimitId.ofRepoId(creditLimitToKeep.getC_BPartner_CreditLimit_ID())));

		final List<I_C_BPartner_CreditLimit> activeCreditLimitRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner_CreditLimit.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner_CreditLimit.COLUMNNAME_C_BPartner_ID, partner.getC_BPartner_ID())
				.create()
				.list();

		final SoftAssertions softly = new SoftAssertions();
		softly.assertThat(activeCreditLimitRecords.size()).isEqualTo(1);
		softly.assertThat(activeCreditLimitRecords.get(0).getC_BPartner_CreditLimit_ID()).isEqualTo(creditLimitToKeep.getC_BPartner_CreditLimit_ID());
		softly.assertAll();
	}

	@Builder(builderMethodName = "newCreditLimitType")
	public static I_C_CreditLimit_Type createCreditLimitType(@NonNull final String name, final int seqNo, final boolean isAutoApproval)
	{
		final I_C_CreditLimit_Type type = newInstance(I_C_CreditLimit_Type.class);
		type.setName(name);
		type.setSeqNo(seqNo);
		type.setIsActive(true);
		type.setIsAutoApproval(isAutoApproval);
		save(type);
		return type;
	}

	@Builder(builderMethodName = "newBPCreditLimit")
	public static I_C_BPartner_CreditLimit createBPCreditLimit(@NonNull final I_C_BPartner partner, @NonNull final I_C_CreditLimit_Type type,
			@NonNull final BigDecimal amount, @NonNull final Timestamp dateFrom)
	{
		final I_C_BPartner_CreditLimit bpLimit = newInstance(I_C_BPartner_CreditLimit.class);
		bpLimit.setC_BPartner_ID(partner.getC_BPartner_ID());
		bpLimit.setC_CreditLimit_Type(type);
		bpLimit.setAmount(amount);
		bpLimit.setProcessed(true);
		bpLimit.setIsActive(true);
		bpLimit.setDateFrom(dateFrom);
		save(bpLimit);
		return bpLimit;
	}
}
