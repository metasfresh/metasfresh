package de.metas.material.dispo.commons.repository.repohelpers;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.material.dispo.commons.candidate.businesscase.PurchaseDetail;
import de.metas.material.dispo.model.I_MD_Candidate_Purchase_Detail;

/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class PurchaseDetailRepoHelperTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void ofRecord()
	{
		final I_MD_Candidate_Purchase_Detail purchaseDetailRecord = newInstance(I_MD_Candidate_Purchase_Detail.class);
		purchaseDetailRecord.setC_BPartner_Vendor_ID(10);

		// invoke the method under test
		final PurchaseDetail result = PurchaseDetailRepoHelper.ofRecord(purchaseDetailRecord);

		assertThat(result).isNotNull();
		assertThat(result.getVendorRepoId()).isEqualTo(10);
	}

}
