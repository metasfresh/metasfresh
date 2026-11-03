package de.metas.frontend_testing.expectations;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodePackingInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeProductInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUnitType;
import de.metas.handlingunits.qrcodes.service.HUQRCodesRepository;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.product.ProductId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 * #%L
 * de.metas.frontend-testing
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

/**
 * The QR-code lookup the {@code Backend.expect({hus: {...}})} expectations go through.
 */
public class AssertExpectationsCommandServicesTest
{
	private static final HuId HU_ID = HuId.ofRepoId(667);

	private HUQRCodesRepository huQRCodesRepository;
	private HUQRCodesService huQRCodesService;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		huQRCodesRepository = new HUQRCodesRepository();
		huQRCodesService = HUQRCodesService.newInstanceForUnitTesting();
	}

	private static HUQRCode newQRCode()
	{
		return HUQRCode.builder()
				.id(HUQRCodeUniqueId.ofUUID(UUID.randomUUID()))
				.packingInfo(HUQRCodePackingInfo.builder()
						.huUnitType(HUQRCodeUnitType.TU)
						.packingInstructionsId(HuPackingInstructionsId.ofRepoId(123))
						.caption("Some TU")
						.build())
				.product(HUQRCodeProductInfo.builder()
						.id(ProductId.ofRepoId(111))
						.code("productCode")
						.name("productName")
						.build())
				.attributes(ImmutableList.of())
				.build();
	}

	@Test
	public void anActiveAssignmentResolves()
	{
		final HUQRCode qrCode = newQRCode();
		huQRCodesRepository.createNew(qrCode, HU_ID);

		assertThat(AssertExpectationsCommandServices.getHuIdByQRCode(huQRCodesService, qrCode)).isEqualTo(HU_ID);
	}

	/**
	 * A destroyed HU keeps its QR code assertable: the HU-destroy interceptor soft-deletes the
	 * {@code M_HU_QRCode_Assignment} row, but {@code Backend.expect({hus: {qrCode: {huStatus: 'D'}}})}
	 * still has to find the HU behind that QR code.
	 */
	@Test
	public void aSoftDeletedAssignmentStillResolves()
	{
		final HUQRCode qrCode = newQRCode();
		huQRCodesRepository.createNew(qrCode, HU_ID);
		huQRCodesRepository.deactivateAssignmentsByHuId(HU_ID);

		assertThat(AssertExpectationsCommandServices.getHuIdByQRCode(huQRCodesService, qrCode)).isEqualTo(HU_ID);
	}

	@Test
	public void anUnknownQRCodeStillFails()
	{
		assertThatThrownBy(() -> AssertExpectationsCommandServices.getHuIdByQRCode(huQRCodesService, newQRCode()))
				.isInstanceOf(AdempiereException.class);
	}
}
