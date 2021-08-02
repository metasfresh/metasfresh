/*
 * #%L
 * de.metas.elasticsearch
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.fulltextsearch.indexer.handler;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.fulltextsearch.config.ESDocumentToIndex;
import de.metas.fulltextsearch.config.ESDocumentToIndexTemplate;
import de.metas.fulltextsearch.config.FTSConfig;
import de.metas.fulltextsearch.indexer.queue.ModelToIndex;
import de.metas.fulltextsearch.indexer.queue.ModelToIndexEventType;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.util.Optionals;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.util.Evaluatee;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class BPartnerFTSModelIndexer implements FTSModelIndexer
{
	private final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);
	private final ILocationDAO locationDAO = Services.get(ILocationDAO.class);

	@Override
	public Set<String> getHandledSourceTableNames()
	{
		return ImmutableSet.of(I_C_BPartner.Table_Name, I_C_BPartner_Location.Table_Name, I_AD_User.Table_Name);
	}

	@Override
	public List<ESDocumentToIndex> createDocumentsToIndex(
			@NonNull final List<ModelToIndex> requests,
			@NonNull final FTSConfig config)
	{
		//
		// Identify BPartners to add and to remove from Elasticsearch Index
		final HashSet<BPartnerId> bpartnerIdsToAdd = new HashSet<>();
		final HashSet<BPartnerId> bpartnerIdsToRemove = new HashSet<>();
		{
			final HashSet<Integer> bpartnerLocationRepoIds = new HashSet<>();
			final HashSet<Integer> bpartnerContactRepoIds = new HashSet<>();

			for (final ModelToIndex request : requests)
			{
				final TableRecordReference sourceModelRef = request.getSourceModelRef();
				final String sourceTableName = sourceModelRef.getTableName();
				if (I_C_BPartner.Table_Name.equals(sourceTableName))
				{
					final ModelToIndexEventType eventType = request.getEventType();
					if (eventType == ModelToIndexEventType.CREATED_OR_UPDATED)
					{
						bpartnerIdsToAdd.add(BPartnerId.ofRepoId(sourceModelRef.getRecord_ID()));
					}
					else if (eventType == ModelToIndexEventType.REMOVED)
					{
						bpartnerIdsToRemove.add(BPartnerId.ofRepoId(sourceModelRef.getRecord_ID()));
					}
				}
				else if (I_C_BPartner_Location.Table_Name.equals(sourceTableName))
				{
					bpartnerLocationRepoIds.add(sourceModelRef.getRecord_ID());
				}
				else if (I_AD_User.Table_Name.equals(sourceTableName))
				{
					bpartnerContactRepoIds.add(sourceModelRef.getRecord_ID());
				}
			}

			partnerDAO.getBPartnerLocationIdsByRepoIds(bpartnerLocationRepoIds)
					.stream()
					.map(BPartnerLocationId::getBpartnerId)
					.forEach(bpartnerIdsToAdd::add);

			partnerDAO.getContactIdsByRepoIds(bpartnerContactRepoIds)
					.stream()
					.map(BPartnerContactId::getBpartnerId)
					.forEach(bpartnerIdsToAdd::add);

			bpartnerIdsToAdd.removeAll(bpartnerIdsToRemove);
		}

		final ArrayList<ESDocumentToIndex> result = new ArrayList<>();

		//
		// Collect documents to add to Elasticsearch index
		{
			final ESDocumentToIndexTemplate documentToIndexTemplate = config.getDocumentToIndexTemplate();
			final List<I_C_BPartner> bpartnerRecords = partnerDAO.retrieveByIds(bpartnerIdsToAdd);
			for (final I_C_BPartner bpartnerRecord : bpartnerRecords)
			{
				final Evaluatee evalCtx = createEvaluationContext(bpartnerRecord);
				final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID());
				final ESDocumentToIndex documentToIndex = documentToIndexTemplate.resolve(evalCtx, bpartnerId);
				result.add(documentToIndex);
			}
		}

		//
		// Collect documents to remove from Elasticsearch index
		for (final BPartnerId bpartnerId : bpartnerIdsToRemove)
		{
			result.add(ESDocumentToIndex.remove(bpartnerId));
		}

		//
		return result;
	}

	private Evaluatee createEvaluationContext(final I_C_BPartner bpartnerRecord)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID());

		final I_C_BPartner_Location bpLocationRecord = partnerDAO.retrieveBPartnerLocation(IBPartnerDAO.BPartnerLocationQuery.builder()
				.bpartnerId(bpartnerId)
				.type(IBPartnerDAO.BPartnerLocationQuery.Type.SHIP_TO)
				.applyTypeStrictly(false)
				.build());

		final I_C_Location locationRecord = bpLocationRecord != null
				? locationDAO.getById(LocationId.ofRepoId(bpLocationRecord.getC_Location_ID()))
				: null;

		final I_AD_User bpContactRecord = partnerDAO.retrieveDefaultContactOrNull(bpartnerRecord, I_AD_User.class);

		return new BPartnerEvaluationContext(bpartnerRecord, bpLocationRecord, locationRecord, bpContactRecord);
	}

	private static class BPartnerEvaluationContext implements Evaluatee
	{
		private static final String PREFIX_BPARTNER = "C_BPartner.";
		private static final String PREFIX_BPARTNER_LOCATION = "C_BPartner_Location.";
		private static final String PREFIX_LOCATION = "C_Location.";
		private static final String PREFIX_BPARTNER_CONTACT = "AD_User.";

		@NonNull
		private final I_C_BPartner bpartner;
		@Nullable
		private final I_C_BPartner_Location bpartnerLocation;
		@Nullable
		private final I_C_Location location;
		@Nullable
		private final I_AD_User bpartnerContact;

		public BPartnerEvaluationContext(
				@NonNull final I_C_BPartner bpartner,
				@Nullable final I_C_BPartner_Location bpartnerLocation,
				@Nullable final I_C_Location location,
				@Nullable final I_AD_User bpartnerContact)
		{
			this.bpartner = bpartner;
			this.bpartnerLocation = bpartnerLocation;
			this.location = location;
			this.bpartnerContact = bpartnerContact;
		}

		@Override
		public String get_ValueAsString(@NonNull final String variableName)
		{
			final Object valueObj = get_ValueAsObject(variableName);
			return convertValueToString(valueObj);
		}

		@Override
		@SuppressWarnings("unchecked")
		public <T> T get_ValueAsObject(final String variableName)
		{
			if (variableName.startsWith(PREFIX_BPARTNER))
			{
				final String variableNameNorm = variableName.substring(PREFIX_BPARTNER.length());
				return (T)getBPartnerVariable(variableNameNorm).orElse(null);
			}
			else if (variableName.startsWith(PREFIX_BPARTNER_LOCATION))
			{
				final String variableNameNorm = variableName.substring(PREFIX_BPARTNER_LOCATION.length());
				return (T)getBPartnerLocationVariable(variableNameNorm).orElse(null);
			}
			else if (variableName.startsWith(PREFIX_LOCATION))
			{
				final String variableNameNorm = variableName.substring(PREFIX_LOCATION.length());
				return (T)getLocationVariable(variableNameNorm).orElse(null);
			}
			else if (variableName.startsWith(PREFIX_BPARTNER_CONTACT))
			{
				final String variableNameNorm = variableName.substring(PREFIX_BPARTNER_CONTACT.length());
				return (T)getBPartnerContactVariable(variableNameNorm).orElse(null);
			}
			else
			{
				return (T)Optionals.firstPresentOfSuppliers(
						() -> getBPartnerVariable(variableName),
						() -> getBPartnerLocationVariable(variableName),
						() -> getLocationVariable(variableName),
						() -> getBPartnerContactVariable(variableName));
			}
		}

		private Optional<Object> getBPartnerVariable(final String variableName)
		{
			return InterfaceWrapperHelper.getValueOptional(bpartner, variableName);
		}

		private Optional<Object> getBPartnerLocationVariable(final String variableName)
		{
			return bpartnerLocation != null
					? InterfaceWrapperHelper.getValueOptional(bpartnerLocation, variableName)
					: Optional.empty();
		}

		private Optional<Object> getLocationVariable(final String variableName)
		{
			return location != null
					? InterfaceWrapperHelper.getValueOptional(location, variableName)
					: Optional.empty();
		}

		private Optional<Object> getBPartnerContactVariable(final String variableName)
		{
			return bpartnerContact != null
					? InterfaceWrapperHelper.getValueOptional(bpartnerContact, variableName)
					: Optional.empty();
		}

		private static String convertValueToString(@Nullable final Object value)
		{
			return value != null ? value.toString() : "null";
		}
	}
}
