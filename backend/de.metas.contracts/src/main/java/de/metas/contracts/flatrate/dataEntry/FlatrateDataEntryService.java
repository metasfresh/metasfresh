/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.flatrate.dataEntry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.department.BPartnerDepartment;
import de.metas.bpartner.department.BPartnerDepartmentId;
import de.metas.bpartner.department.BPartnerDepartmentRepo;
import de.metas.contracts.FlatrateTerm;
import de.metas.contracts.FlatrateTermRepo;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import org.compiere.model.I_M_AttributeSetInstance;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlatrateDataEntryService
{
	private static final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	@NonNull
	private final FlatrateDataEntryRepo flatrateDataEntryRepo;
	private final FlatrateTermRepo flatrateTermRepo;
	private final BPartnerDepartmentRepo bPartnerDepartmentRepo;

	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

	public FlatrateDataEntryService(
			@NonNull final FlatrateDataEntryRepo flatrateDataEntryRepo,
			@NonNull final FlatrateTermRepo flatrateTermTermRepo,
			@NonNull final BPartnerDepartmentRepo bPartnerDepartmentRepo)
	{
		this.flatrateDataEntryRepo = flatrateDataEntryRepo;
		this.flatrateTermRepo = flatrateTermTermRepo;
		this.bPartnerDepartmentRepo = bPartnerDepartmentRepo;
	}

	/**
	 * @return a copy based on the given entry, but will all defaults that are implied by the entry's term's product and bpartner.
	 */
	public FlatrateDataEntry addMissingDetails(@NonNull final FlatrateDataEntry entry)
	{
		final FlatrateTerm flatrateTerm = flatrateTermRepo.getById(entry.getId().getFlatrateTermId());

		final ProductId productId = Check.assumeNotNull(flatrateTerm.getProductId(), "C_FlatrateTerm_ID={} needs to have an M_Product", entry.getId().getFlatrateTermId().getRepoId());

		final BPartnerId bpartnerId = flatrateTerm.getShipOrBillPartnerId();

		final AttributeSetId attributeSetId = productBL.getMasterDataSchemaAttributeSetId(productId);
		final List<AttributeListValue> attributeListValues = attributeDAO.retrieveAttributeValuesByAttributeSetId(attributeSetId);

		final List<BPartnerDepartment> departments = retrieveBPartnerDepartments(bpartnerId);

		final ImmutableSet<FlatrateDataEntryDetailKey> existingCombinations = extractExistingDetailKeys(entry);

		final List<FlatrateDataEntryDetail> additionalDetails = createAllCombinations(
				attributeListValues,
				departments,
				existingCombinations);

		final FlatrateDataEntry entryWithAllDetails = entry.toBuilder().details(additionalDetails).build();
		return flatrateDataEntryRepo.save(entryWithAllDetails);
	}

	/**
	 * Create all combinations of departnments and different attribute-values
	 *
	 * @param existingCombinations specified which combos already exist and shall therefore be skipped
	 */
	private List<FlatrateDataEntryDetail> createAllCombinations(
			@NonNull final List<AttributeListValue> attributeListValues,
			@NonNull final List<BPartnerDepartment> departments,
			@NonNull final ImmutableSet<FlatrateDataEntryDetailKey> existingCombinations)
	{
		final List<ImmutableAttributeSet> attributeSets = CreateAllAttributeSetsCommand.withValues(attributeListValues)
				.run();
		final List<AttributeSetInstanceId> asiIds = createAllASIs(attributeSets);

		final ImmutableList.Builder<FlatrateDataEntryDetail> result = ImmutableList.builder();
		for (final BPartnerDepartment department : departments)
		{
			for (final AttributeSetInstanceId asiId : asiIds)
			{
				final AttributesKey attributesKey = createAttributesKey(asiId);

				final FlatrateDataEntryDetailKey detailKey = new FlatrateDataEntryDetailKey(department.getId(), attributesKey);
				if (!existingCombinations.contains(detailKey))
				{
					final FlatrateDataEntryDetail newEntryDetail = FlatrateDataEntryDetail.builder()
							.asiId(asiId)
							.bPartnerDepartment(department).build();
					result.add(newEntryDetail);
				}
			}
		}
		return result.build();
	}

	private List<AttributeSetInstanceId> createAllASIs(@NonNull final List<ImmutableAttributeSet> attributeSets)
	{
		final ImmutableList.Builder<AttributeSetInstanceId> result = ImmutableList.builder();
		for (final ImmutableAttributeSet attributeSet : attributeSets)
		{
			final I_M_AttributeSetInstance asiRecord = attributeSetInstanceBL.createASIFromAttributeSet(attributeSet);
			result.add(AttributeSetInstanceId.ofRepoId(asiRecord.getM_AttributeSetInstance_ID()));
		}
		return result.build();
	}

	private static ImmutableSet<FlatrateDataEntryDetailKey> extractExistingDetailKeys(final FlatrateDataEntry entry)
	{
		return entry
				.getDetails()
				.stream()
				.map(detail -> new FlatrateDataEntryDetailKey(
						detail.getBPartnerDepartment().getId(),
						createAttributesKey(detail.getAsiId()))
				)
				.collect(ImmutableSet.toImmutableSet());
	}

	private static AttributesKey createAttributesKey(@NonNull final AttributeSetInstanceId asiId)
	{
		return AttributesKeys
				.createAttributesKeyFromASIAllAttributes(asiId)
				.orElse(AttributesKey.NONE);
	}

	private List<BPartnerDepartment> retrieveBPartnerDepartments(@NonNull final BPartnerId bPartnerId)
	{
		final List<BPartnerDepartment> departments = bPartnerDepartmentRepo.getByBPartnerId(bPartnerId);
		if (departments.isEmpty())
		{
			return ImmutableList.of(BPartnerDepartment.none(bPartnerId));
		}
		return departments;
	}

	@Value
	static class FlatrateDataEntryDetailKey
	{
		@NonNull
		BPartnerDepartmentId bPartnerDepartmentId;
		@NonNull
		AttributesKey attributesKey;
	}

}
