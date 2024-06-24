/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.qrcodes.leich_und_mehl;

import de.metas.handlingunits.attribute.weightable.Weightables;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class LMQRCodeTest
{
	@Nested
	class fromGlobalQRCodeJsonString
	{
		@Test
		void standard()
		{
			final LMQRCode qrCode = LMQRCode.fromGlobalQRCodeJsonString("LMQ#1#123.456#13.12.2024#lot3");
			assertThat(qrCode.getWeightInKg()).isEqualTo(new BigDecimal("123.456"));
			assertThat(qrCode.getBestBeforeDate()).isEqualTo(LocalDate.parse("2024-12-13"));
			assertThat(qrCode.getLotNumber()).isEqualTo("lot3");
			assertThat(qrCode.getProductNo()).isNull();

			assertThat(qrCode.getAttributeValueAsString(Weightables.ATTR_WeightNet)).contains("123.456");
			assertThat(qrCode.getAttributeValueAsString(AttributeConstants.ATTR_BestBeforeDate)).contains("2024-12-13");
			assertThat(qrCode.getAttributeValueAsString(AttributeConstants.ATTR_LotNumber)).contains("lot3");
		}

		@Test
		void with_productNo()
		{
			final LMQRCode qrCode = LMQRCode.fromGlobalQRCodeJsonString("LMQ#1#123.456#13.12.2024#lot3#productNo88");
			assertThat(qrCode.getWeightInKg()).isEqualTo(new BigDecimal("123.456"));
			assertThat(qrCode.getBestBeforeDate()).isEqualTo(LocalDate.parse("2024-12-13"));
			assertThat(qrCode.getLotNumber()).isEqualTo("lot3");
			assertThat(qrCode.getProductNo()).isEqualTo("productNo88");

			assertThat(qrCode.getAttributeValueAsString(Weightables.ATTR_WeightNet)).contains("123.456");
			assertThat(qrCode.getAttributeValueAsString(AttributeConstants.ATTR_BestBeforeDate)).contains("2024-12-13");
			assertThat(qrCode.getAttributeValueAsString(AttributeConstants.ATTR_LotNumber)).contains("lot3");
		}
	}
}
