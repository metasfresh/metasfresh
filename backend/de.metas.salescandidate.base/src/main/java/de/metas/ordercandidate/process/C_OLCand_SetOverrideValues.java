package de.metas.ordercandidate.process;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.GLN;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.ordercandidate.api.IOLCandUpdateBL;
import de.metas.ordercandidate.api.OLCandUpdateResult;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.ProcessParams;
import de.metas.security.permissions.Access;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.ActiveRecordQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.api.IParams;
import org.compiere.model.I_C_BPartner_Location;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static de.metas.ordercandidate.api.impl.OLCandUpdater.PARAM_C_BPARTNER_LOCATION_MAP;

public class C_OLCand_SetOverrideValues extends JavaProcess
{
	//"No Location provided and no GLN found for the given records"
	private final static AdMessageKey NO_GLNS = AdMessageKey.of("de.metas.ordercandidate.process.C_OLCand_SetOverrideValues.NoGLNs");
	//"No Location with GLN: {0} found for the given business partner"
	private final static AdMessageKey NO_LOCATION_FOR_GLN = AdMessageKey.of("de.metas.ordercandidate.process.C_OLCand_SetOverrideValues.NoLocationForGLN");

	private final static String PARAM_BPartner = I_C_OLCand.COLUMNNAME_C_BPartner_Override_ID;
	private final static String PARAM_Location = I_C_OLCand.COLUMNNAME_C_BP_Location_Override_ID;
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

	private IParams params = null;

	@Override
	protected void prepare()
	{
		final List<ProcessInfoParameter> parameterList = new ArrayList<>(getProcessInfo().getParameter());
		final Map<String, ProcessInfoParameter> parameters = parameterList.stream().collect(Collectors.toMap(ProcessInfoParameter::getParameterName, param -> param));
		final int bpartnerId = parameters.get(PARAM_BPartner).getParameterAsInt(-1);
		if (bpartnerId > -1 && Check.isBlank(parameters.get(PARAM_Location).getParameterAsString()))
		{
			parameters.put(PARAM_C_BPARTNER_LOCATION_MAP, ProcessInfoParameter.ofValueObject(PARAM_C_BPARTNER_LOCATION_MAP, getBPartnerLocationIdMap(bpartnerId)));
		}
		params = new ProcessParams(ImmutableList.copyOf(parameters.values()));
	}

	private Map<BPartnerLocationId, BPartnerLocationId> getBPartnerLocationIdMap(final int bpartnerId)
	{
		final Map<BPartnerLocationId, GLN> oldLocationToGlnMap = getOldLocationIdToGlnMap();
		final Map<GLN, BPartnerLocationId> glnToNewLocationMap = getGlnToNewLocationMap(bpartnerId);

		oldLocationToGlnMap.forEach((oldLocation, gln) -> {
			if (!glnToNewLocationMap.containsKey(gln))
			{
				throw new AdempiereException(NO_LOCATION_FOR_GLN, gln);
			}
		});

		return oldLocationToGlnMap.keySet()
				.stream()
				.collect(ImmutableMap.toImmutableMap(oldLocation -> oldLocation,
						oldLocation -> glnToNewLocationMap.get(oldLocationToGlnMap.get(oldLocation))));
	}

	@NonNull
	private Map<GLN, BPartnerLocationId> getGlnToNewLocationMap(final int bpartnerId)
	{
		return bPartnerDAO.retrieveBPartnerLocations(BPartnerId.ofRepoId(bpartnerId))
				.stream()
				.filter(loc -> !Check.isBlank(loc.getGLN()))
				.collect(Collectors.toMap(param -> GLN.ofString(param.getGLN()), param -> BPartnerLocationId.ofRepoId(param.getC_BPartner_ID(), param.getC_BPartner_Location_ID())));
	}

	@NonNull
	private Map<BPartnerLocationId, GLN> getOldLocationIdToGlnMap()
	{
		final Map<BPartnerLocationId, GLN> oldLocationToGlnMap = createQueryBuilder()
			.andCollect(I_C_OLCand.COLUMNNAME_C_BPartner_Location_ID, I_C_BPartner_Location.class)
			.addOnlyActiveRecordsFilter()
			.create()
				.stream()
				.filter(Objects::nonNull)
				.filter(loc -> !Check.isBlank(loc.getGLN()))
				.collect(Collectors.toMap(param -> BPartnerLocationId.ofRepoId(param.getC_BPartner_ID(), param.getC_BPartner_Location_ID()), param -> GLN.ofString(param.getGLN())));
		if (oldLocationToGlnMap.size() == 0)
		{
			throw new AdempiereException(NO_GLNS);
		}
		return oldLocationToGlnMap;
	}

	@Override
	protected String doIt() throws Exception
	{
		final OLCandUpdateResult result = Services.get(IOLCandUpdateBL.class).updateOLCands(getCtx(), createIterator(), params);

		return "@Success@: " + result.getUpdated() + " @Processed@, " + result.getSkipped() + " @Skipped@";
	}

	private Iterator<I_C_OLCand> createIterator()
	{
		return createQueryBuilder()
				.create()
				.setRequiredAccess(Access.READ) // 04471: enqueue only those records on which user has access to
				.iterate(I_C_OLCand.class);
	}

	private IQueryBuilder<I_C_OLCand> createQueryBuilder()
	{
		final IQueryFilter<I_C_OLCand> queryFilter = getQueryFilter();

		final IQueryBuilder<I_C_OLCand> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_C_OLCand.class, getCtx(), get_TrxName())
				.filter(queryFilter)
				.filter(ActiveRecordQueryFilter.getInstance(I_C_OLCand.class));

		queryBuilder.orderBy()
				.addColumn(I_C_OLCand.COLUMNNAME_C_OLCand_ID);

		return queryBuilder;
	}

	@VisibleForTesting
	protected IQueryFilter<I_C_OLCand> getQueryFilter()
	{
		return getProcessInfo().getQueryFilterOrElseFalse();
	}
}
