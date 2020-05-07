package de.metas.contracts.refund;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.invoice.service.InvoiceScheduleRepository;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
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

public class RefundContractRepositoryTest
{
	private static final LocalDate NOW = LocalDate.now();
	private static final BPartnerId BPARTNER_ID = BPartnerId.ofRepoId(10);

	private RefundContractRepository refundContractRepository;
	private I_C_UOM uomRecord;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		refundContractRepository = new RefundContractRepository(new RefundConfigRepository(new InvoiceScheduleRepository()));

		uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);
	}

	@Test
	public void getById()
	{
		final I_C_Flatrate_Conditions conditionsRecord = newInstance(I_C_Flatrate_Conditions.class);
		conditionsRecord.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refund);
		saveRecord(conditionsRecord);

		final ConditionsId conditionsId = ConditionsId.ofRepoId(conditionsRecord.getC_Flatrate_Conditions_ID());
		final List<I_C_Flatrate_RefundConfig> //
		refundConfigRecords = RefundConfigRepositoryTest.createThreeRefundConfigRecords(conditionsId);

		// make sure that we don't have a record with qty=zero (needed for the test further down)
		final I_C_Flatrate_RefundConfig configWithZeroQty = refundConfigRecords
				.stream()
				.filter(r -> r.getMinQty().signum() == 0)
				.findFirst()
				.get();
		configWithZeroQty.setMinQty(ONE);
		saveRecord(configWithZeroQty);

		final I_C_Flatrate_Term contractRecord = createContractRecord(conditionsRecord);

		// invoke the method under test
		final FlatrateTermId contractId = FlatrateTermId.ofRepoId(contractRecord.getC_Flatrate_Term_ID());
		final RefundContract contract = refundContractRepository.getById(contractId);

		assertThat(contract.getStartDate()).isEqualTo(NOW);
		assertThat(contract.getBPartnerId()).isEqualTo(BPARTNER_ID);
		assertThat(contract.getRefundConfigs()).hasSize(4); // we expect a 4th "artificial" config with qty=zero
		assertThat(contract.getRefundConfig(ZERO).getPercent().isZero()).isTrue();
	}

	private static I_C_Flatrate_Term createContractRecord(@NonNull final I_C_Flatrate_Conditions conditionsRecord)
	{
		final I_C_Flatrate_Term contractRecord = newInstance(I_C_Flatrate_Term.class);

		contractRecord.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Refund);
		contractRecord.setC_Flatrate_Conditions(conditionsRecord);
		contractRecord.setM_Product_ID(30);
		contractRecord.setStartDate(TimeUtil.asTimestamp(NOW));
		contractRecord.setEndDate(TimeUtil.asTimestamp(NOW.plusDays(10)));
		contractRecord.setBill_BPartner_ID(BPARTNER_ID.getRepoId());
		saveRecord(contractRecord);
		return contractRecord;
	}
}
