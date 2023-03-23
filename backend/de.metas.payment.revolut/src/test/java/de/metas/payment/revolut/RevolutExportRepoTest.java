/*
 * #%L
 * de.metas.payment.revolut
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

package de.metas.payment.revolut;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import com.google.common.collect.ImmutableList;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.location.CountryId;
import de.metas.organization.OrgId;
import de.metas.payment.revolut.model.RecipientType;
import de.metas.payment.revolut.model.RevolutPaymentExport;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJONextIdSuppliers;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_PaySelection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SnapshotExtension.class)
public class RevolutExportRepoTest
{
	private RevolutExportRepo revolutExportRepo;
	private Expect expect;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		POJOLookupMap.setNextIdSupplier(POJONextIdSuppliers.newPerTableSequence());
		revolutExportRepo = new RevolutExportRepo();
	}

	@Test
	public void saveAll()
	{
		//given
		final List<RevolutPaymentExport> list = ImmutableList.of(createMockRevolutPaymentExportModel());

		//when
		final List<RevolutPaymentExport> savedList = revolutExportRepo.saveAll(list);

		//then
		assertThat(savedList.size()).isEqualTo(1);
		expect.serializer("orderedJson").toMatchSnapshot(savedList);
	}

	private RevolutPaymentExport createMockRevolutPaymentExportModel()
	{
		return RevolutPaymentExport.builder()
				.recordReference(TableRecordReference.of(I_C_PaySelection.Table_Name, 1))
				.name("name")
				.orgId(OrgId.ofRepoId(1))
				.amount(Amount.of(BigDecimal.TEN, CurrencyCode.CHF))
				.accountNo("accountNo")
				.routingNo("routingNo")
				.IBAN("iban")
				.SwiftCode("SwiftCode")
				.paymentReference("payReference")
				.regionName("region")
				.addressLine1("addr1")
				.addressLine2("addr2")
				.city("city")
				.postalCode("postal")
				.recipientCountryId(CountryId.ofRepoId(5))
				.recipientBankCountryId(CountryId.ofRepoId(4))
				.recipientType(RecipientType.COMPANY)
				.build();
	}
}
