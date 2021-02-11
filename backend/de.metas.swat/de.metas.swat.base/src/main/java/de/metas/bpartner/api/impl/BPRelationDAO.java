package de.metas.bpartner.api.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.api.IBPRelationDAO;
import de.metas.bpartner.service.BPRelation;
import de.metas.bpartner.service.BPRelationId;
import de.metas.interfaces.I_C_BP_Relation;
import de.metas.organization.OrgId;
import de.metas.security.permissions.Access;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;

import java.util.Optional;
import java.util.stream.Stream;

import static de.metas.common.util.CoalesceUtil.coalesce;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class BPRelationDAO implements IBPRelationDAO
{
	public static final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public I_C_BP_Relation retrieveHandoverBPRelation(final I_C_BPartner partner, final I_C_BPartner relationPartner)
	{
		final IQueryBuilder<I_C_BP_Relation> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BP_Relation.class, partner);

		// only active
		queryBuilder.addOnlyActiveRecordsFilter();

		// partner condition
		queryBuilder.addEqualsFilter(I_C_BP_Relation.COLUMNNAME_C_BPartner_ID, partner.getC_BPartner_ID());

		// relation partner condition
		queryBuilder.addEqualsFilter(I_C_BP_Relation.COLUMNNAME_C_BPartnerRelation_ID, relationPartner.getC_BPartner_ID());

		// must ne of type handover
		queryBuilder.addEqualsFilter(I_C_BP_Relation.COLUMNNAME_IsHandOverLocation, true);

		// retrieve the last created one in case of several handover relations for the same partner
		queryBuilder.orderBy()
				.addColumn(I_C_BP_Relation.COLUMNNAME_C_BP_Relation_ID, Direction.Descending, Nulls.Last);

		return queryBuilder.create()
				.first();
	}

	private Stream<I_C_BP_Relation> getRelationsForSourceBpartner(@NonNull final BPartnerId bPartnerId)
	{
		final IQueryBuilder<I_C_BP_Relation> queryBuilder = queryBL
				.createQueryBuilder(I_C_BP_Relation.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BP_Relation.COLUMNNAME_C_BPartner_ID, bPartnerId.getRepoId());
		return queryBuilder.create().setRequiredAccess(Access.READ).iterateAndStream();
	}

	@Override
	public Stream<BPRelation> getRelationsForBpartner(@NonNull final OrgId orgId, @NonNull final BPartnerId bPartnerId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_C_BP_Relation> queryBuilder = queryBL
				.createQueryBuilder(I_C_BP_Relation.class)
				.setJoinOr()
				.setOption(IQueryBuilder.OPTION_Explode_OR_Joins_To_SQL_Unions, true);
		final ICompositeQueryFilter<I_C_BP_Relation> filterSource = queryBL.createCompositeQueryFilter(I_C_BP_Relation.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BP_Relation.COLUMNNAME_C_BPartner_ID, bPartnerId.getRepoId());
		queryBuilder.filter(filterSource);
		final ICompositeQueryFilter<I_C_BP_Relation> filterDest = queryBL.createCompositeQueryFilter(I_C_BP_Relation.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BP_Relation.COLUMNNAME_C_BPartnerRelation_ID, bPartnerId.getRepoId());
		queryBuilder.filter(filterDest);

		return queryBL.createQueryBuilder(I_C_BP_Relation.class)
				.addInSubQueryFilter(I_C_BP_Relation.COLUMNNAME_C_BPartnerRelation_ID, I_C_BP_Relation.COLUMNNAME_C_BPartnerRelation_ID, queryBuilder.create())
				.create()
				.setRequiredAccess(Access.READ)
				.iterateAndStream()
				.map(this::toBPRelation);
	}

	private BPRelation toBPRelation(final I_C_BP_Relation relation)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(relation.getC_BPartner_ID());
		final BPartnerId targetBpartnerId = BPartnerId.ofRepoId(relation.getC_BPartnerRelation_ID());
		return BPRelation.builder()
				.bpRelationId(BPRelationId.ofRepoId(relation.getC_BP_Relation_ID()))
				.bpartnerId(bpartnerId)
				.bpLocationId(BPartnerLocationId.ofRepoIdOrNull(bpartnerId, relation.getC_BPartner_Location_ID()))
				.targetBPartnerId(targetBpartnerId)
				.targetBPLocationId(BPartnerLocationId.ofRepoId(targetBpartnerId, relation.getC_BPartnerRelation_Location_ID()))
				.active(relation.isActive())
				.name(relation.getName())
				.description(relation.getDescription())
				.role(BPRelation.BPRelationRole.ofNullableCode(relation.getRole()))
				.externalId(ExternalId.ofOrNull(relation.getExternalId()))
				.billTo(relation.isBillTo())
				.fetchedFrom(relation.isFetchedFrom())
				.handoverLocation(relation.isHandOverLocation())
				.payFrom(relation.isPayFrom())
				.remitTo(relation.isRemitTo())
				.shipTo(relation.isShipTo())
				.build();
	}

	@Override
	public void saveOrUpdate(final @NonNull OrgId orgId, final BPRelation rel)
	{
		final I_C_BP_Relation relation;
		if (rel.getBpRelationId() != null)
		{
			relation = InterfaceWrapperHelper.load(rel.getBpRelationId(), I_C_BP_Relation.class);
		}
		else
		{
			final Optional<I_C_BP_Relation> bpartnerRelation = getRelationsForSourceBpartner(rel.getBpartnerId()).filter(r -> r.getC_BPartnerRelation_ID() == rel.getTargetBPartnerId().getRepoId()).findFirst();
			relation = bpartnerRelation.orElseGet(() -> InterfaceWrapperHelper.newInstance(I_C_BP_Relation.class));
		}
		relation.setAD_Org_ID(orgId.getRepoId());
		relation.setC_BPartner_ID(rel.getBpartnerId().getRepoId());
		if (rel.getBpRelationId() != null)
		{
			relation.setC_BPartner_Location_ID(rel.getBpRelationId().getRepoId());
		}
		relation.setC_BPartnerRelation_ID(rel.getTargetBPartnerId().getRepoId());
		relation.setC_BPartnerRelation_Location_ID(rel.getTargetBPLocationId().getRepoId());
		relation.setName(coalesce(rel.getName(), relation.getName()));
		relation.setDescription(coalesce(rel.getDescription(), relation.getDescription()));
		relation.setRole(rel.getRole() != null ? rel.getRole().getCode() : relation.getRole());
		if (rel.getExternalId() != null)
		{
			relation.setExternalId(rel.getExternalId().getValue());
		}
		relation.setIsActive(coalesce(rel.getActive(), relation.isActive(), true));
		relation.setIsBillTo(coalesce(rel.getBillTo(), relation.isBillTo()));
		relation.setIsFetchedFrom(coalesce(rel.getFetchedFrom(), relation.isFetchedFrom()));
		relation.setIsHandOverLocation(coalesce(rel.getHandoverLocation(), relation.isHandOverLocation()));
		relation.setIsPayFrom(coalesce(rel.getPayFrom(), relation.isPayFrom()));
		relation.setIsRemitTo(coalesce(rel.getRemitTo(), relation.isRemitTo()));
		relation.setIsShipTo(coalesce(rel.getShipTo(), relation.isShipTo()));
		InterfaceWrapperHelper.save(relation);
	}

}
