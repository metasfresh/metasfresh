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

import com.google.common.collect.ImmutableSet;
import de.metas.business.BusinessTestHelper;
import de.metas.cache.model.ModelCacheInvalidationService;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.exportaudit.APIExportStatus;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_Recompute;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
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

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
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
		shipmentScheduleRepository = new ShipmentScheduleRepository(ModelCacheInvalidationService.newInstanceForUnitTesting());

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
				.includeInvalid(false)
				.build();
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
				.includeInvalid(false)
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

	/**
	 * Mirrors the async carrier advise: it loads the shipment schedule once, saves it as InProgress and later as Completed.
	 * In between, the shipment candidate export API sets the ExportStatus directly in the DB.
	 * The carrier advise's saves must not write its stale ExportStatus back.
	 */
	@Test
	void save_doesNotOverwriteExportStatusThatWasChangedConcurrently()
	{
		// given
		final ShipmentScheduleId shipmentScheduleId = createShipmentScheduleRecord(APIExportStatus.Pending, CarrierAdviseStatus.Requested);
		final ShipmentSchedule carrierAdviseSchedule = shipmentScheduleRepository.getById(shipmentScheduleId);
		carrierAdviseSchedule.setCarrierAdvisingStatus(CarrierAdviseStatus.InProgress);
		shipmentScheduleRepository.save(carrierAdviseSchedule);

		shipmentScheduleRepository.exportStatusMassUpdate(ImmutableSet.of(shipmentScheduleId), APIExportStatus.Exported);

		// when
		carrierAdviseSchedule.setCarrierAdvisingStatus(CarrierAdviseStatus.Completed);
		shipmentScheduleRepository.save(carrierAdviseSchedule);

		// then
		final I_M_ShipmentSchedule record = load(shipmentScheduleId, I_M_ShipmentSchedule.class);
		assertThat(record.getExportStatus()).isEqualTo(APIExportStatus.Exported.getCode());
		assertThat(record.getCarrier_Advising_Status()).isEqualTo(CarrierAdviseStatus.Completed.getCode());
	}

	/**
	 * Mirrors the export-result callback (it loads the schedules and sets their ExportStatus) while the carrier advise completes concurrently.
	 * The callback's save must not write its stale carrier advising status back.
	 */
	@Test
	void save_doesNotOverwriteCarrierAdvisingStatusThatWasChangedConcurrently()
	{
		// given
		final ShipmentScheduleId shipmentScheduleId = createShipmentScheduleRecord(APIExportStatus.Exported, CarrierAdviseStatus.InProgress);
		final ShipmentSchedule exportResultSchedule = shipmentScheduleRepository.getById(shipmentScheduleId);

		final I_M_ShipmentSchedule concurrentlyUpdatedRecord = load(shipmentScheduleId, I_M_ShipmentSchedule.class);
		concurrentlyUpdatedRecord.setCarrier_Advising_Status(CarrierAdviseStatus.Completed.getCode());
		saveRecord(concurrentlyUpdatedRecord);

		// when
		exportResultSchedule.setExportStatus(APIExportStatus.ExportedAndForwarded);
		shipmentScheduleRepository.save(exportResultSchedule);

		// then
		final I_M_ShipmentSchedule record = load(shipmentScheduleId, I_M_ShipmentSchedule.class);
		assertThat(record.getExportStatus()).isEqualTo(APIExportStatus.ExportedAndForwarded.getCode());
		assertThat(record.getCarrier_Advising_Status()).isEqualTo(CarrierAdviseStatus.Completed.getCode());
	}

	/**
	 * A field written by one save is not written again by a later save of the same instance, unless it was changed again in between.
	 */
	@Test
	void save_doesNotRewriteFieldWrittenByEarlierSave()
	{
		// given
		final ShipmentScheduleId shipmentScheduleId = createShipmentScheduleRecord(APIExportStatus.Pending, CarrierAdviseStatus.Requested);
		final ShipmentSchedule schedule = shipmentScheduleRepository.getById(shipmentScheduleId);
		schedule.setExportStatus(APIExportStatus.Exported);
		shipmentScheduleRepository.save(schedule);
		assertThat(load(shipmentScheduleId, I_M_ShipmentSchedule.class).getExportStatus()).isEqualTo(APIExportStatus.Exported.getCode());

		shipmentScheduleRepository.exportStatusMassUpdate(ImmutableSet.of(shipmentScheduleId), APIExportStatus.Pending);

		// when
		schedule.setCarrierAdvisingStatus(CarrierAdviseStatus.InProgress);
		shipmentScheduleRepository.save(schedule);

		// then
		final I_M_ShipmentSchedule record = load(shipmentScheduleId, I_M_ShipmentSchedule.class);
		assertThat(record.getExportStatus()).isEqualTo(APIExportStatus.Pending.getCode());
		assertThat(record.getCarrier_Advising_Status()).isEqualTo(CarrierAdviseStatus.InProgress.getCode());
	}

	private ShipmentScheduleId createShipmentScheduleRecord(@NonNull final APIExportStatus exportStatus, @NonNull final CarrierAdviseStatus carrierAdviseStatus)
	{
		final I_M_ShipmentSchedule record = createShipmentScheduleRecord();
		record.setExportStatus(exportStatus.getCode());
		record.setCarrier_Advising_Status(carrierAdviseStatus.getCode());
		saveRecord(record);
		return ShipmentScheduleId.ofRepoId(record.getM_ShipmentSchedule_ID());
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
		record.setCarrier_Advising_Status(CarrierAdviseStatus.NotRequested.getCode());
		record.setM_Warehouse_ID(WarehouseId.MAIN.getRepoId());
		saveRecord(record);

		return record;
	}

}