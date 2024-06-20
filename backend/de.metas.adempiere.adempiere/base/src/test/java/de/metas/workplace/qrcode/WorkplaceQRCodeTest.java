/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.workplace.qrcode;

import de.metas.workplace.Workplace;
import de.metas.workplace.WorkplaceId;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WorkplaceQRCodeTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void ofGlobalQRCodeJsonString_toGlobalQRCodeJsonString()
	{
		final Workplace workplace = Workplace.builder()
				.id(WorkplaceId.ofRepoId(1000001))
				.name("workplace1")
				.build();
		final WorkplaceQRCode qrCode = WorkplaceQRCode.ofWorkplace(workplace);
		System.out.println("QRCode: " + qrCode.toGlobalQRCodeJsonString());

		Assertions.assertThat(WorkplaceQRCode.ofGlobalQRCodeJsonString(qrCode.toGlobalQRCodeJsonString()))
				.usingRecursiveComparison()
				.isEqualTo(qrCode);
	}

	/**
	 * IMPORTANT: Makes sure that already printed QR codes are still valid.
	 */
	@Test
	void fromGlobalQRCode_v1()
	{
		Assertions.assertThat(WorkplaceQRCode.ofGlobalQRCodeJsonString("WORKPLACE#1#{\"workplaceId\":1000001,\"caption\":\"workplace1\"}"))
				.usingRecursiveComparison()
				.isEqualTo(WorkplaceQRCode.builder()
						.workplaceId(WorkplaceId.ofRepoId(1000001))
						.caption("workplace1")
						.build());
	}
}
