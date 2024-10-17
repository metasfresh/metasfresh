package de.metas.ordercandidate.api;

import com.google.common.base.MoreObjects;
import de.metas.document.references.related_documents.relation_type.RelationTypeRelatedDocumentsProvidersFactory;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.model.I_C_OLCandProcessor;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.PO;
import org.compiere.util.Env;

import java.util.stream.Stream;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

final class RelationTypeOLCandSource implements OLCandSource
{
	private final OLCandOrderDefaults orderDefaults;
	private final int olCandProcessorId;
	private final String relationTypeInternalName;

	@Builder
	private RelationTypeOLCandSource(
			@NonNull final OLCandOrderDefaults orderDefaults,
			final int olCandProcessorId)
	{
		Check.assume(olCandProcessorId > 0, "olCandProcessorId > 0");

		this.olCandProcessorId = olCandProcessorId;
		this.orderDefaults = orderDefaults;
		this.relationTypeInternalName = mkRelationTypeInternalNameForOLCandProcessorId(olCandProcessorId);
	}

	private static String mkRelationTypeInternalNameForOLCandProcessorId(final int olCandProcessorId)
	{
		return I_C_OLCandProcessor.Table_Name + "_" + olCandProcessorId + "<=>" + I_C_OLCand.Table_Name;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("relationTypeInternalName", relationTypeInternalName)
				.add("olCandProcessorId", olCandProcessorId)
				.toString();
	}

	@Override
	public Stream<OLCand> streamOLCands()
	{
		// FIXME: get rid of it
		final PO processorPO = InterfaceWrapperHelper.getPO(InterfaceWrapperHelper.loadOutOfTrx(olCandProcessorId, I_C_OLCandProcessor.class));

		final RelationTypeRelatedDocumentsProvidersFactory relationTypeRelatedDocumentsProvidersFactory = SpringContextHolder.instance.getBean(RelationTypeRelatedDocumentsProvidersFactory.class);
		return relationTypeRelatedDocumentsProvidersFactory
				.getRelatedDocumentsProviderBySourceTableNameAndInternalName(I_C_OLCand.Table_Name, relationTypeInternalName)
				.retrieveDestinations(Env.getCtx(), processorPO, I_C_OLCand.class, ITrx.TRXNAME_ThreadInherited)
				.stream()
				.map(this::toOLCand);
	}

	private OLCand toOLCand(@NonNull final I_C_OLCand olCandRecord)
	{
		final OLCandFactory olCandFactory = new OLCandFactory();
		return olCandFactory.toOLCand(olCandRecord,orderDefaults);
	}
}
