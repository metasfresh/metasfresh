/*
 * #%L
 * de.metas.swat.base
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

package de.metas.inoutcandidate;

import de.metas.business.BusinessTestHelper;
import de.metas.inoutcandidate.ShipmentScheduleRepository.ShipmentScheduleQuery;
import de.metas.inoutcandidate.exportaudit.APIExportStatus;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_Recompute;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

class ShipmentScheduleRepositoryTest
{
	private ShipmentScheduleRepository shipmentScheduleRepository;

	private I_C_BPartner bPartner;
	private I_C_BPartner_Location bPartnerLocation;
	private I_C_BPartner bpartnerOverride;
	private I_C_BPartner_Location bPartnerLocationOverride;
	private I_C_UOM uom;
	private I_M_Product product;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		shipmentScheduleRepository = new ShipmentScheduleRepository();

		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_Client_ID, ClientId.METASFRESH.getRepoId());
		bPartner = BusinessTestHelper.createBPartner("bpartner");
		bPartnerLocation = BusinessTestHelper.createBPartnerLocation(bPartner);

		bpartnerOverride = BusinessTestHelper.createBPartner("bpartnerOverride");
		bPartnerLocationOverride = BusinessTestHelper.createBPartnerLocation(bpartnerOverride);

		uom = BusinessTestHelper.createUOM("stockUOM");
		product = BusinessTestHelper.createProduct("product", uom);
	}

	@Test
	void getBy_status_not_matching()
	{
		final I_M_ShipmentSchedule shipmentScheduleRecord = createShipmentScheduleRecord();
		shipmentScheduleRecord.setExportStatus(APIExportStatus.Exported.getCode());
		saveRecord(shipmentScheduleRecord);
		// when

		final ShipmentScheduleQuery query = ShipmentScheduleQuery.builder()
				.exportStatus(APIExportStatus.Pending).build();
		final List<ShipmentSchedule> result = shipmentScheduleRepository.getBy(query);

		// then
		assertThat(result).isEmpty();
	}

	@Test
	void getBy_canBeExportedFrom_not_matching()
	{
		final Timestamp canBeExportedFrom = Timestamp.valueOf("2020-07-16 07:15:00");

		final I_M_ShipmentSchedule shipmentScheduleRecord = createShipmentScheduleRecord();
		shipmentScheduleRecord.setExportStatus(APIExportStatus.Pending.getCode());
		shipmentScheduleRecord.setCanBeExportedFrom(canBeExportedFrom);
		saveRecord(shipmentScheduleRecord);

		// when
		final ShipmentScheduleQuery query = ShipmentScheduleQuery.builder()
				.exportStatus(APIExportStatus.Pending)
				.canBeExportedFrom(canBeExportedFrom.toInstant().minusMillis(1000))
				.build();
		final List<ShipmentSchedule> result = shipmentScheduleRepository.getBy(query);

		// then
		assertThat(result).isEmpty();
	}

	@Test
	void getBy_invalid()
	{
		// given
		final Timestamp canBeExportedFrom = Timestamp.valueOf("2020-07-16 07:15:00");

		final I_M_ShipmentSchedule shipmentScheduleRecord = createShipmentScheduleRecord();
		shipmentScheduleRecord.setExportStatus(APIExportStatus.Pending.getCode());
		shipmentScheduleRecord.setCanBeExportedFrom(canBeExportedFrom);
		saveRecord(shipmentScheduleRecord);

		final I_M_ShipmentSchedule_Recompute recompute = newInstance(I_M_ShipmentSchedule_Recompute.class);
		recompute.setM_ShipmentSchedule_ID(shipmentScheduleRecord.getM_ShipmentSchedule_ID());
		saveRecord(recompute);

		// when
		final ShipmentScheduleQuery query = ShipmentScheduleQuery.builder()
				.exportStatus(APIExportStatus.Pending)
				.canBeExportedFrom(canBeExportedFrom.toInstant())
				.build();
		assertThat(query.isIncludeInvalid()).isFalse(); // guard
		final List<ShipmentSchedule> result = shipmentScheduleRepository.getBy(query);

		// then
		assertThat(result).isEmpty();
	}

	@Test
	void getBy()
	{
		final Timestamp canBeExportedFrom = Timestamp.valueOf("2020-07-16 07:15:00");

		final I_M_ShipmentSchedule shipmentScheduleRecord = createShipmentScheduleRecord();
		shipmentScheduleRecord.setExportStatus(APIExportStatus.Pending.getCode());
		shipmentScheduleRecord.setCanBeExportedFrom(canBeExportedFrom);
		saveRecord(shipmentScheduleRecord);

		// when
		final ShipmentScheduleQuery query = ShipmentScheduleQuery.builder()
				.exportStatus(APIExportStatus.Pending)
				.canBeExportedFrom(canBeExportedFrom.toInstant())
				.build();
		final List<ShipmentSchedule> result = shipmentScheduleRepository.getBy(query);

		// then
		assertThat(result).hasSize(1);
		assertThat(result.get(0).getShipBPartnerId().getRepoId()).isEqualTo(bpartnerOverride.getC_BPartner_ID());
		assertThat(result.get(0).getShipLocationId().getBpartnerId().getRepoId()).isEqualTo(bPartnerLocationOverride.getC_BPartner_ID());
		assertThat(result.get(0).getShipLocationId().getRepoId()).isEqualTo(bPartnerLocationOverride.getC_BPartner_Location_ID());
		assertThat(result.get(0).getProductId().getRepoId()).isEqualTo(product.getM_Product_ID());
		assertThat(result.get(0).getShipContactId()).isNull();

		assertThat(result.get(0).getId().getRepoId()).isEqualTo(shipmentScheduleRecord.getM_ShipmentSchedule_ID());
	}

	private I_M_ShipmentSchedule createShipmentScheduleRecord()
	{
		final I_M_ShipmentSchedule record = newInstance(I_M_ShipmentSchedule.class);
		record.setC_BPartner_ID(bPartner.getC_BPartner_ID());
		record.setC_BPartner_Location_ID(bPartnerLocation.getC_BPartner_Location_ID());
		record.setC_BPartner_Override_ID(bpartnerOverride.getC_BPartner_ID());
		record.setC_BP_Location_Override_ID(bPartnerLocationOverride.getC_BPartner_Location_ID());
		record.setM_Product_ID(product.getM_Product_ID());
		record.setQtyToDeliver(BigDecimal.ONE);
		saveRecord(record);

		return record;
	}

}