/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.contract.flatrate.model;

import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPartnerDepartmentId;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntry;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryDetail;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryDetailId;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryId;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryRepo;
import de.metas.ui.web.order.products_proposal.model.ProductASIDescription;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;

@Builder
@Value
public class DataEntryDetailsRowsLoader
{
	IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	
	@NonNull
	LookupDataSource departmentLookup;
	
	@NonNull
	LookupDataSource uomLookup;

	@NonNull
	FlatrateDataEntryId flatrateDataEntryId;

	@NonNull
	FlatrateDataEntryRepo flatrateDataEntryRepo;

	@NonNull
	public DataEntryDetailsRowsData load()
	{
		final ImmutableMap<DocumentId, DataEntryDetailsRow> rows = loadRows();

		return new DataEntryDetailsRowsData(rows);
	}

	private ImmutableMap<DocumentId, DataEntryDetailsRow> loadRows()
	{
		final FlatrateDataEntry entry = flatrateDataEntryRepo.getById(flatrateDataEntryId);

		final LookupValue uom = Check.assumeNotNull(uomLookup.findById(entry.getUomId()),
													"UOM lookup may not be null for C_UOM_ID={}; C_Flatrate_DataEntry_ID={}",
													UomId.toRepoId(entry.getUomId()),
													FlatrateDataEntryId.toRepoId(entry.getId()));

		final ImmutableMap.Builder<DocumentId, DataEntryDetailsRow> result = ImmutableMap.builder();

		for (final FlatrateDataEntryDetail detail : entry.getDetails())
		{
			final DataEntryDetailsRow row = toRow(uom, detail);
			result.put(row.getId(), row);
		}

		return result.build();
	}

	private DataEntryDetailsRow toRow(
			@NonNull final LookupValue uom,
			@NonNull final FlatrateDataEntryDetail detail)
	{
		final ProductASIDescription productASIDescription = ProductASIDescription.ofString(attributeSetInstanceBL.getASIDescriptionById(detail.getAsiId()));
		final DocumentId documentId = DocumentId.of(detail.getId());

		final LookupValue department = Check.assumeNotNull(departmentLookup.findById(detail.getBPartnerDepartmentId()),
														   "Department lookup may not be null for C_BPartner_Department_ID={}; C_Flatrate_DataEntry_Detail_ID={}",
														   BPartnerDepartmentId.toRepoIdOrNull(detail.getBPartnerDepartmentId()),
														   FlatrateDataEntryDetailId.toRepoId(detail.getId()));

		final DataEntryDetailsRow.DataEntryDetailsRowBuilder row = DataEntryDetailsRow.builder()
				.id(documentId)
				.asi(productASIDescription)
				.department(department)
				.uom(uom);

		if (detail.getQuantity() != null)
		{
			row.qty(detail.getQuantity().toBigDecimal());
		}

		return row.build();
	}
}
