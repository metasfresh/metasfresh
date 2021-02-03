package org.eevolution.api.impl;

import de.metas.common.util.time.SystemTime;
import de.metas.manufacturing.order.exportaudit.APIExportStatus;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.Builder;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.model.I_PP_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class PPOrderBLTest
{
	private PPOrderBL ppOrderBL;
	private ISysConfigBL sysConfigBL;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		ppOrderBL = (PPOrderBL)Services.get(IPPOrderBL.class);
		sysConfigBL = Services.get(ISysConfigBL.class);
	}

	@Builder(builderMethodName = "manufacturingOrder", builderClassName = "ManufacturingOrderBuilder")
	private I_PP_Order createManufacturingOrder(
			@Nullable final APIExportStatus exportStatus)
	{
		final I_PP_Order order = newInstance(I_PP_Order.class);
		if (exportStatus != null)
		{
			order.setExportStatus(exportStatus.getCode());
		}

		saveRecord(order);

		return order;
	}

	@Nested
	public class updateCanBeExportedAfter
	{
		private ZonedDateTime now;

		@BeforeEach
		public void beforeEach()
		{
			now = ZonedDateTime.now();
			SystemTime.setFixedTimeSource(now);
		}

		private void setCanBeExportedAfterSeconds(int seconds)
		{
			sysConfigBL.setValue(
					PPOrderBL.SYSCONFIG_CAN_BE_EXPORTED_AFTER_SECONDS,
					seconds,
					ClientId.SYSTEM,
					OrgId.ANY);
		}

		@Test
		public void exportStatusPending_canBeExportedAfter_0seconds()
		{
			final I_PP_Order order = manufacturingOrder().exportStatus(APIExportStatus.Pending).build();

			ppOrderBL.updateCanBeExportedAfter(order);
			assertThat(order.getCanBeExportedFrom()).isEqualTo(TimeUtil.asTimestamp(now));
		}

		@Test
		public void exportStatusPending_canBeExportedAfter_15seconds()
		{
			setCanBeExportedAfterSeconds(15);
			final I_PP_Order order = manufacturingOrder().exportStatus(APIExportStatus.Pending).build();

			ppOrderBL.updateCanBeExportedAfter(order);
			assertThat(order.getCanBeExportedFrom()).isEqualTo(TimeUtil.asTimestamp(now.plusSeconds(15)));
		}

		@ParameterizedTest
		@EnumSource(mode = EnumSource.Mode.EXCLUDE, names = "Pending")
		public void exportStatus_NotPending(final APIExportStatus exportStatus)
		{
			setCanBeExportedAfterSeconds(15);
			final I_PP_Order order = manufacturingOrder().exportStatus(exportStatus).build();

			ppOrderBL.updateCanBeExportedAfter(order);
			assertThat(order.getCanBeExportedFrom()).isEqualTo(Env.MAX_DATE);
		}

	}
}
