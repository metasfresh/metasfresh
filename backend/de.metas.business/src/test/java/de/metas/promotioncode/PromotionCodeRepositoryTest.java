/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.promotioncode;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_PromotionCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests {@link PromotionCodeRepository#getPromotionCodeIdByValue(String)} lookup-by-Value.
 */
@ExtendWith(AdempiereTestWatcher.class)
class PromotionCodeRepositoryTest
{
	private PromotionCodeRepository promotionCodeRepository;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		promotionCodeRepository = new PromotionCodeRepository();
	}

	@Test
	void promotionCode_resolvedById()
	{
		// stage a C_PromotionCode record with Value = "PROMO1"
		final I_C_PromotionCode promoRecord = newInstance(I_C_PromotionCode.class);
		promoRecord.setValue("PROMO1");
		promoRecord.setName("PROMO1");
		promoRecord.setIsActive(true);
		saveRecord(promoRecord);

		final PromotionCodeId expectedId = PromotionCodeId.ofRepoId(promoRecord.getC_PromotionCode_ID());

		// when
		final PromotionCodeId resolvedId = promotionCodeRepository.getPromotionCodeIdByValue("PROMO1");

		// then
		assertThat(resolvedId).isEqualTo(expectedId);
	}

	@Test
	void promotionCode_unknownValue_throwsValidationError()
	{
		assertThatThrownBy(() -> promotionCodeRepository.getPromotionCodeIdByValue("DOES_NOT_EXIST"))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("DOES_NOT_EXIST");
	}
}
