/*
 * #%L
 * de.metas.postfinance
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.postfinance.paperBillReferences;

import de.metas.cache.CCache;
import de.metas.organization.OrgId;
import de.metas.postfinance.model.I_AD_Org_PostFinance_PaperBill_References;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaperBillReferencesRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<OrgId, List<PaperBillReference>> paperBillReferences = CCache.<OrgId, List<PaperBillReference>>builder()
			.initialCapacity(10)
			.tableName(I_AD_Org_PostFinance_PaperBill_References.Table_Name)
			.build();

	public List<PaperBillReference> getPaperBillReferences(@NonNull final OrgId orgId)
	{
		return paperBillReferences.getOrLoad(orgId, this::retrievePaperBillReferences);
	}

	private List<PaperBillReference> retrievePaperBillReferences(@NonNull final OrgId orgId)
	{
		return queryBL.createQueryBuilder(I_AD_Org_PostFinance_PaperBill_References.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Org_PostFinance_PaperBill_References.COLUMNNAME_AD_Org_ID, orgId)
				.create()
				.stream()
				.map(this::toPaperBillReference)
				.toList();
	}

	private PaperBillReference toPaperBillReference(@NonNull final I_AD_Org_PostFinance_PaperBill_References paperBillReferenceRecord)
	{
		return PaperBillReference.builder()
				.referencePosition(paperBillReferenceRecord.getReference_Position())
				.referenceType(paperBillReferenceRecord.getReference_Type())
				.referenceValue(paperBillReferenceRecord.getReference_Value())
				.build();
	}
}
