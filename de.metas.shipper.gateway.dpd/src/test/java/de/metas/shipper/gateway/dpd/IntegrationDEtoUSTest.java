/*
 * #%L
 * de.metas.shipper.gateway.dpd
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

package de.metas.shipper.gateway.dpd;

import com.google.common.collect.ImmutableList;
import com.jgoodies.common.base.Strings;
import de.metas.attachments.AttachmentEntryService;
import de.metas.shipper.gateway.commons.ShipperTestHelper;
import de.metas.shipper.gateway.dpd.logger.DpdDatabaseClientLogger;
import de.metas.shipper.gateway.dpd.model.DpdClientConfig;
import de.metas.shipper.gateway.dpd.model.DpdClientConfigRepository;
import de.metas.shipper.gateway.dpd.model.DpdOrderCustomDeliveryData;
import de.metas.shipper.gateway.dpd.model.DpdServiceType;
import de.metas.shipper.gateway.dpd.model.I_DPD_StoreOrder;
import de.metas.shipper.gateway.dpd.model.I_DPD_StoreOrder_Log;
import de.metas.shipper.gateway.spi.model.CustomDeliveryData;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryOrderLine;
import de.metas.shipper.gateway.spi.model.PickupDate;
import de.metas.shipper.gateway.spi.model.ServiceType;
import de.metas.shipping.ShipperId;
import de.metas.shipping.api.ShipperTransportationId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Disabled("Makes ACTUAL calls to DPD api and needs auth")
public class IntegrationDEtoUSTest
{
	// todo to be implemented at a later time; depends on: https://github.com/metasfresh/me03/issues/3157
	// @BeforeEach
	// void setUp()
	// {
	// 	AdempiereTestHelper.get().init();
	// }
	//
	// @Test
	// @DisplayName("Delivery Order DE -> DE, DPD E12 + test persistence after all steps")
	// void E12()
	// {
	// 	DpdTestHelper.testAllSteps(DpdTestHelper.createDummyDeliveryOrderDEtoUS(DpdServiceType.DPD_E12));
	// }
	//
	// @Test
	// @DisplayName("Delivery Order DE -> DE, DPD Classic + test persistence after all steps")
	// void Classic()
	// {
	// 	DpdTestHelper.testAllSteps(DpdTestHelper.createDummyDeliveryOrderDEtoUS(DpdServiceType.DPD_CLASSIC));
	// }
	//
	// @Test
	// @DisplayName("Delivery Order DE -> DE, DPD Express + test persistence after all steps")
	// void Express()
	// {
	// 	DpdTestHelper.testAllSteps(DpdTestHelper.createDummyDeliveryOrderDEtoUS(DpdServiceType.DPD_EXPRESS));
	// }
}
