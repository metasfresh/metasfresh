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

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.department.BPartnerDepartment;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntry;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryDetail;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryId;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryRepo;
import de.metas.contracts.flatrate.dataEntry.FlatrateDataEntryService;
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

import java.util.function.Predicate;

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
	FlatrateDataEntryService flatrateDataEntryService;

	@NonNull
	FlatrateDataEntryRepo flatrateDataEntryRepo;

	@NonNull
	FlatrateDataEntryId flatrateDataEntryId;

	@NonNull
	public DataEntryDetailsRowsData load()
	{
		final ImmutableList<DataEntryDetailsRow> rows = loadRows();

		return new DataEntryDetailsRowsData(flatrateDataEntryId, rows, flatrateDataEntryRepo, this);
	}

	private ImmutableList<DataEntryDetailsRow> loadRows()
	{
		return loadAllRows();
	}

	private ImmutableList<DataEntryDetailsRow> loadAllRows()
	{
		final Predicate<FlatrateDataEntryDetail> all = detail -> true;

		return loadMatchingRows(all);
	}

	public ImmutableList<DataEntryDetailsRow> loadMatchingRows(@NonNull final Predicate<FlatrateDataEntryDetail> filter)
	{

		final FlatrateDataEntry entry = flatrateDataEntryRepo.getById(flatrateDataEntryId);
		final FlatrateDataEntry entryWithAllDetails;
		if (!entry.isProcessed())
		{
			entryWithAllDetails = flatrateDataEntryService.addMissingDetails(entry);
		}
		else
		{
			entryWithAllDetails = entry;
		}

		final LookupValue uom = Check.assumeNotNull(uomLookup.findById(entryWithAllDetails.getUomId()),
													"UOM lookup may not be null for C_UOM_ID={}; C_Flatrate_DataEntry_ID={}",
													UomId.toRepoId(entryWithAllDetails.getUomId()),
													FlatrateDataEntryId.toRepoId(entryWithAllDetails.getId()));

		final ImmutableList.Builder<DataEntryDetailsRow> result = ImmutableList.builder();

		for (final FlatrateDataEntryDetail detail : entryWithAllDetails.getDetails())
		{
			if (filter.test(detail))
			{
				final DataEntryDetailsRow row = toRow(entryWithAllDetails.isProcessed(), uom, detail);
				result.add(row);
			}
		}

		return result.build();
	}

	private DataEntryDetailsRow toRow(
			final boolean processed,
			@NonNull final LookupValue uom,
			@NonNull final FlatrateDataEntryDetail detail)
	{
		final ProductASIDescription productASIDescription = ProductASIDescription.ofString(attributeSetInstanceBL.getASIDescriptionById(detail.getAsiId()));
		final DocumentId documentId = DataEntryDetailsRowUtil.createDocumentId(detail);

		final BPartnerDepartment bPartnerDepartment = detail.getBPartnerDepartment();

		final LookupValue department;
		if (bPartnerDepartment.isNone())
		{
			department = null;
		}
		else
		{
			department = departmentLookup.findById(bPartnerDepartment.getId());
		}

		final DataEntryDetailsRow.DataEntryDetailsRowBuilder row = DataEntryDetailsRow.builder()
				.processed(processed)
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
