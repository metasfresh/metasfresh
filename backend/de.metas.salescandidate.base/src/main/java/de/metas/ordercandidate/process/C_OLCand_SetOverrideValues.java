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

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.GLN;
import de.metas.bpartner.service.impl.BPartnerDAO;
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
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.ActiveRecordQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.api.IParams;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner_Location;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class C_OLCand_SetOverrideValues extends JavaProcess
{
	//"No Location provided and more than one GLN found for the given records"
	private final static AdMessageKey MULTIPLE_GLNS = AdMessageKey.of("de.metas.ordercandidate.process.C_OLCand_SetOverrideValues.MultipleGLNs");
	//"No Location provided and no GLN found for the given records"
	private final static AdMessageKey NO_GLNS = AdMessageKey.of("de.metas.ordercandidate.process.C_OLCand_SetOverrideValues.NoGLNs");
	//"No Location with GLN: {0} found for the given business partner"
	private final static AdMessageKey NO_LOCATION_FOR_GLN = AdMessageKey.of("de.metas.ordercandidate.process.C_OLCand_SetOverrideValues.NoLocationForGLN");

	private final static String PARAM_BPartner = I_C_OLCand.COLUMNNAME_C_BPartner_Override_ID;
	private final static String PARAM_Location = I_C_OLCand.COLUMNNAME_C_BP_Location_Override_ID;
	private final BPartnerDAO bPartnerDAO = Services.get(BPartnerDAO.class);

	private IParams params = null;

	@Override
	protected void prepare()
	{
		final List<ProcessInfoParameter> parameterList = new ArrayList<>(getProcessInfo().getParameter());
		final Map<String, ProcessInfoParameter> parameters = parameterList.stream().collect(Collectors.toMap(ProcessInfoParameter::getParameterName, param -> param));
		final int bpartnerId = parameters.get(PARAM_BPartner).getParameterAsInt(-1);
		if (bpartnerId > -1 && Check.isBlank(parameters.get(PARAM_Location).getParameterAsString()))
		{
			parameters.put(PARAM_Location, ProcessInfoParameter.of(PARAM_Location, getBPartnerLocationId(bpartnerId).getRepoId()));
		}
		params = new ProcessParams(ImmutableList.copyOf(parameters.values()));
	}

	private BPartnerLocationId getBPartnerLocationId(final int bpartnerId)
	{
		final Set<GLN> glns = createQuery().stream()
				.map(record -> BPartnerLocationId.ofRepoId(record.getC_BPartner_ID(), record.getC_BPartner_Location_ID()))
				.map(bPartnerDAO::getBPartnerLocationByIdEvenInactive)
				.filter(Objects::nonNull)
				.map(I_C_BPartner_Location::getGLN)
				.filter(Objects::nonNull)
				.map(GLN::ofString)
				.collect(Collectors.toSet());
		if (glns.size() > 1)
		{
			throw new AdempiereException(MULTIPLE_GLNS);
		}
		if (glns.size() == 0)
		{
			throw new AdempiereException(NO_GLNS);
		}
		final GLN gln = glns.iterator().next();
		final Optional<BPartnerLocationId> bPartnerLocationIdByGln = bPartnerDAO.getBPartnerLocationIdByGln(BPartnerId.ofRepoId(bpartnerId), gln);
		return bPartnerLocationIdByGln.orElseThrow(() -> new AdempiereException(NO_LOCATION_FOR_GLN, gln));
	}

	@Override
	protected String doIt() throws Exception
	{
		final OLCandUpdateResult result = Services.get(IOLCandUpdateBL.class).updateOLCands(getCtx(), createIterator(), params);

		return "@Success@: " + result.getUpdated() + " @Processed@, " + result.getSkipped() + " @Skipped@";
	}

	private Iterator<I_C_OLCand> createIterator()
	{
		return createQuery().iterate(I_C_OLCand.class);
	}

	private IQuery<I_C_OLCand> createQuery()
	{
		final IQueryFilter<I_C_OLCand> queryFilter = getProcessInfo().getQueryFilterOrElseFalse();

		final IQueryBuilder<I_C_OLCand> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_C_OLCand.class, getCtx(), get_TrxName())
				.filter(queryFilter)
				.filter(ActiveRecordQueryFilter.getInstance(I_C_OLCand.class));

		queryBuilder.orderBy()
				.addColumn(I_C_OLCand.COLUMNNAME_C_OLCand_ID);

		return queryBuilder.create()
				.setRequiredAccess(Access.READ); // 04471: enqueue only those records on which user has access to
	}
}
