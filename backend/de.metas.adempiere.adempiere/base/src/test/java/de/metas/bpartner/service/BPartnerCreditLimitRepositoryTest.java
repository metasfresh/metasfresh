/**
 *
 */
package de.metas.bpartner.service;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_CreditLimit;
import org.compiere.model.I_C_CreditLimit_Type;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.util.time.FixedTimeSource;
import de.metas.util.time.SystemTime;
import lombok.Builder;
import lombok.NonNull;

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

		SystemTime.setTimeSource(new FixedTimeSource(2018, 02, 28, 13, 13, 13));
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
		bpLimit.setC_BPartner(partner);
		bpLimit.setC_CreditLimit_Type(type);
		bpLimit.setAmount(amount);
		bpLimit.setProcessed(true);
		bpLimit.setIsActive(true);
		bpLimit.setDateFrom(dateFrom);
		save(bpLimit);
		return bpLimit;
	}
}
