package de.metas.contracts.commission.testhelpers;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.time.LocalDate;

import javax.annotation.Nullable;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.TimeUtil;

import de.metas.contracts.commission.CommissionConstants;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.document.engine.IDocument;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.contracts
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

@Value
@Builder
public class ContractTestRecord
{
	/** mostly used to construct a hierarchy with the contracts' BPArtners. */
	@NonNull
	String name;

	/** mostly used to construct a hierarchy with the contracts' BPArtners. */
	@Nullable
	String parentName;

	/** The flatrate term is created with start = date minus 10 days and end = date plus 10 days. */
	@NonNull
	@Default
	LocalDate date = LocalDate.now();

	/** Supposed to be invoked from {@link ConfigTestRecord}. */
	I_C_Flatrate_Term createContractData(
			@NonNull final Integer C_Flatrate_Conditions_ID,
			@NonNull final ProductId commissionProductId)
	{
		final I_C_Flatrate_Term termRecord = newInstance(I_C_Flatrate_Term.class);

		final I_C_BPartner exitingBPartnerRecord = POJOLookupMap.get().getFirstOnly(I_C_BPartner.class, bpRecord -> name.equals(bpRecord.getName()));
		if (exitingBPartnerRecord == null)
		{
			final I_C_BPartner saleRepBPartnerRecord = newInstance(I_C_BPartner.class);
			saleRepBPartnerRecord.setName(name);
			saveRecord(saleRepBPartnerRecord);
			termRecord.setBill_BPartner_ID(saleRepBPartnerRecord.getC_BPartner_ID());
		}
		else
		{
			termRecord.setBill_BPartner_ID(exitingBPartnerRecord.getC_BPartner_ID());
		}
		termRecord.setC_Flatrate_Conditions_ID(C_Flatrate_Conditions_ID);
		termRecord.setNote("name=" + name + " (parentName=" + parentName + ")");
		termRecord.setDocStatus(IDocument.STATUS_Completed);
		termRecord.setType_Conditions(CommissionConstants.TYPE_CONDITIONS_COMMISSION);
		termRecord.setM_Product_ID(commissionProductId.getRepoId());
		termRecord.setStartDate(TimeUtil.asTimestamp(date.minusDays(10)));
		termRecord.setEndDate(TimeUtil.asTimestamp(date.plusDays(10)));

		saveRecord(termRecord);

		return termRecord;
	}
}
