/*
 * #%L
 * de.metas.business
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

package de.metas.invoice.detail;

import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import de.metas.organization.OrgId;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Invoice_Detail;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class InvoiceWithDetailsRepositoryTest
{
	private final ZoneId timeZone = ZoneId.of("Europe/Berlin");
	private OrgId orgId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		orgId = AdempiereTestHelper.createOrgWithTimeZone("org", timeZone);
	}

	@Test
	void save()
	{
		final LocalDate localDate = LocalDate.parse("2020-06-17");

		// given
		final InvoiceWithDetails invoiceWithDetails = InvoiceWithDetails.builder()
				.id(InvoiceId.ofRepoId(10))
				.orgId(orgId)
				.detailItem(InvoiceDetailItem.builder().label("i10_d1_descr").description("descr1").build())
				.detailItem(InvoiceDetailItem.builder().label("i10_d2_date").date(localDate).build())
				.line(InvoiceLineWithDetails.builder()
						.id(InvoiceAndLineId.ofRepoId(10, 10))
						.detailItem(InvoiceDetailItem.builder().label("i10_l10_d1_descr").description("descr1").build())
						.detailItem(InvoiceDetailItem.builder().label("i10_l10_d1_date").date(localDate).build())
						.build())
				.line(InvoiceLineWithDetails.builder()
						.id(InvoiceAndLineId.ofRepoId(10, 20))
						.detailItem(InvoiceDetailItem.builder().label("i10_l20_d1_descr").description("descr1").build())
						.detailItem(InvoiceDetailItem.builder().label("i10_l20_d1_date").date(localDate).build())
						.build())
				.build();

		// when
		new InvoiceWithDetailsRepository().save(invoiceWithDetails);

		// then
		final List<I_C_Invoice_Detail> results = POJOLookupMap.get().getRecords(I_C_Invoice_Detail.class);

		final int orgId = this.orgId.getRepoId();
		assertThat(results).hasSize(6)
				.extracting("AD_Org_ID", "C_Invoice_ID", "C_InvoiceLine_ID", "Description", "Date")
				.containsExactlyInAnyOrder(
						tuple(orgId, 10, 0, "descr1", null),
						tuple(orgId, 10, 0, null, TimeUtil.asTimestamp(localDate, timeZone)),
						tuple(orgId, 10, 10, "descr1", null),
						tuple(orgId, 10, 10, null, TimeUtil.asTimestamp(localDate, timeZone)),
						tuple(orgId, 10, 20, "descr1", null),
						tuple(orgId, 10, 20, null, TimeUtil.asTimestamp(localDate, timeZone))
				);
	}
}
