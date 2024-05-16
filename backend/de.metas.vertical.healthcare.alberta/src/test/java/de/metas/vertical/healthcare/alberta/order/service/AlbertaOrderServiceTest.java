/*
 * #%L
 * de.metas.vertical.healthcare.alberta
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.vertical.healthcare.alberta.order.service;

import de.metas.bpartner.BPartnerId;
import de.metas.ordercandidate.api.OLCandId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import de.metas.vertical.healthcare.alberta.model.I_Alberta_Order;
import de.metas.vertical.healthcare.alberta.model.I_Alberta_OrderedArticleLine;
import de.metas.vertical.healthcare.alberta.model.I_C_OLCand_AlbertaTherapy;
import de.metas.vertical.healthcare.alberta.model.I_C_OLCand_AlbertaTherapyType;
import de.metas.vertical.healthcare.alberta.order.AlbertaOrderInfo;
import de.metas.vertical.healthcare.alberta.order.AlbertaOrderLineInfo;
import de.metas.vertical.healthcare.alberta.order.dao.AlbertaOrderDAO;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class AlbertaOrderServiceTest
{
	private AlbertaOrderService albertaOrderService;
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		albertaOrderService = new AlbertaOrderService(new AlbertaOrderDAO());
	}

	@Test
	public void saveAlbertaOrderCompositeInfo()
	{
		// given
		final Instant creationDate = Instant.now();
		final Instant updated = Instant.now().minusSeconds(10);
		final Instant updatedLine = Instant.now().minusSeconds(20);
		final LocalDate startDate = LocalDate.now();
		final LocalDate endDate = LocalDate.now().plusDays(2);
		final LocalDate nextDelivery = LocalDate.now().plusDays(10);
		final List<String> therapyTypes = Arrays.asList("therapyType1", "therapyType2");

		final AlbertaOrderInfo albertaOrderInfo = AlbertaOrderInfo.builder()
				.olCandId(OLCandId.ofRepoId(1))
				.orgId(OrgId.ofRepoId(2))
				.externalId("orderExternalId")
				.rootId("rootId")
				.creationDate(creationDate)
				.startDate(startDate)
				.endDate(endDate)
				.dayOfDelivery(BigDecimal.ONE)
				.nextDelivery(nextDelivery)
				.doctorBPartnerId(BPartnerId.ofRepoId(3))
				.pharmacyBPartnerId(BPartnerId.ofRepoId(4))
				.isInitialCare(true)
				.isSeriesOrder(true)
				.isArchived(true)
				.annotation("annotation")
				.deliveryInformation("deliveryInformation")
				.deliveryNote("deliveryNote")
				.updated(updated)
				.orderLine(AlbertaOrderLineInfo.builder()
								   .externalId("orderLineExternalId")
								   .salesLineId("salesLineId")
								   .olCandId(OLCandId.ofRepoId(1))
								   .unit("unit")
								   .isRentalEquipment(true)
								   .isPrivateSale(true)
								   .updated(updatedLine)
								   .durationAmount(BigDecimal.TEN)
								   .timePeriod(BigDecimal.ZERO)
								   .build())
				.therapy("therapy")
				.therapyTypes(therapyTypes)
				.build();

		// when
		albertaOrderService.saveAlbertaOrderInfo(albertaOrderInfo);

		// then
		final I_C_OLCand_AlbertaTherapy albertaTherapy = queryBL.createQueryBuilder(I_C_OLCand_AlbertaTherapy.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_OLCand_AlbertaTherapy.COLUMNNAME_Therapy, "therapy")
				.create()
				.firstOnlyOptional(I_C_OLCand_AlbertaTherapy.class)
				.orElse(null);

		assertThat(albertaTherapy).isNotNull();

		final I_C_OLCand_AlbertaTherapyType albertaTherapyType1 = queryBL.createQueryBuilder(I_C_OLCand_AlbertaTherapyType.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_OLCand_AlbertaTherapyType.COLUMNNAME_TherapyType, therapyTypes.get(0))
				.create()
				.firstOnlyOptional(I_C_OLCand_AlbertaTherapyType.class)
				.orElse(null);

		assertThat(albertaTherapyType1).isNotNull();

		final I_C_OLCand_AlbertaTherapyType albertaTherapyType2 = queryBL.createQueryBuilder(I_C_OLCand_AlbertaTherapyType.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_OLCand_AlbertaTherapyType.COLUMNNAME_TherapyType, therapyTypes.get(1))
				.create()
				.firstOnlyOptional(I_C_OLCand_AlbertaTherapyType.class)
				.orElse(null);

		assertThat(albertaTherapyType2).isNotNull();

		final I_Alberta_Order albertaOrder = queryBL.createQueryBuilder(I_Alberta_Order.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_Alberta_Order.COLUMNNAME_ExternalId, "orderExternalId")
				.create()
				.firstOnlyOptional(I_Alberta_Order.class)
				.orElse(null);

		assertThat(albertaOrder).isNotNull();
		assertThat(albertaOrder.getAD_Org_ID()).isEqualTo(2);
		assertThat(albertaOrder.getRootId()).isEqualTo("rootId");
		assertThat(albertaOrder.getCreationDate()).isEqualTo(Timestamp.from(creationDate));
		assertThat(albertaOrder.getStartDate()).isEqualTo(Timestamp.valueOf(startDate.atStartOfDay()));
		assertThat(albertaOrder.getEndDate()).isEqualTo(Timestamp.valueOf(endDate.atStartOfDay()));
		assertThat(albertaOrder.getDayOfDelivery()).isEqualTo(BigDecimal.ONE);
		assertThat(albertaOrder.getNextDelivery()).isEqualTo(Timestamp.valueOf(nextDelivery.atStartOfDay()));
		assertThat(albertaOrder.getC_Doctor_BPartner_ID()).isEqualTo(3);
		assertThat(albertaOrder.getC_Pharmacy_BPartner_ID()).isEqualTo(4);
		assertThat(albertaOrder.isInitialCare()).isEqualTo(true);
		assertThat(albertaOrder.isSeriesOrder()).isEqualTo(true);
		assertThat(albertaOrder.isArchived()).isEqualTo(true);
		assertThat(albertaOrder.getAnnotation()).isEqualTo("annotation");
		assertThat(albertaOrder.getDeliveryInfo()).isEqualTo("deliveryInformation");
		assertThat(albertaOrder.getDeliveryNote()).isEqualTo("deliveryNote");
		assertThat(albertaOrder.getExternallyUpdatedAt()).isEqualTo(Timestamp.from(updated));

		final I_Alberta_OrderedArticleLine albertaOrderedArticleLine = queryBL.createQueryBuilder(I_Alberta_OrderedArticleLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_Alberta_OrderedArticleLine.COLUMNNAME_ExternalId, "orderLineExternalId")
				.create()
				.firstOnlyOptional(I_Alberta_OrderedArticleLine.class)
				.orElse(null);

		assertThat(albertaOrderedArticleLine).isNotNull();
		assertThat(albertaOrderedArticleLine.getSalesLineId()).isEqualTo("salesLineId");
		assertThat(albertaOrderedArticleLine.getC_OLCand_ID()).isEqualTo(1);
		assertThat(albertaOrderedArticleLine.getUnit()).isEqualTo("unit");
		assertThat(albertaOrderedArticleLine.isRentalEquipment()).isEqualTo(true);
		assertThat(albertaOrderedArticleLine.isPrivateSale()).isEqualTo(true);
		assertThat(albertaOrderedArticleLine.getExternallyUpdatedAt()).isEqualTo(Timestamp.from(updatedLine));
		assertThat(albertaOrderedArticleLine.getDurationAmount()).isEqualTo(BigDecimal.TEN);
		assertThat(albertaOrderedArticleLine.getTimePeriod()).isEqualTo(BigDecimal.ZERO);
	}
}