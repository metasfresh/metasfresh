/**
 *
 */
package de.metas.document.sequence.impl;

import static org.adempiere.model.InterfaceWrapperHelper.create;

import java.util.List;
import java.util.Optional;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.IClientOrgAware;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.document.DocumentNoBuilderException;
import de.metas.document.DocumentSequenceInfo;
import de.metas.document.IDocumentSequenceDAO;
import de.metas.document.sequence.DocSequenceId;
import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.ValueSequenceInfoProvider;
import de.metas.document.sequence.ValueSequenceInfoProvider.ProviderResult;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

@Service
public class DocumentNoBuilderFactory implements IDocumentNoBuilderFactory
{
	private final List<ValueSequenceInfoProvider> additionalProviders;

	public DocumentNoBuilderFactory(@NonNull final Optional<List<ValueSequenceInfoProvider>> providers)
	{
		this.additionalProviders = ImmutableList.copyOf(providers.orElse(ImmutableList.of()));
	}

	private final ValueSequenceInfoProvider tableNameBasedProvider = new ValueSequenceInfoProvider()
	{
		@Override
		public ProviderResult computeValueInfo(@NonNull final Object modelRecord)
		{
			final IClientOrgAware clientOrg = create(modelRecord, IClientOrgAware.class);

			final String tableName = InterfaceWrapperHelper.getModelTableName(modelRecord);

			final DocumentSequenceInfo documentSequenceInfo = computeDocumentSequenceInfoByTableName(tableName, clientOrg.getAD_Client_ID(), clientOrg.getAD_Org_ID());
			if (documentSequenceInfo == null)
			{
				return ProviderResult.EMPTY;
			}
			return ProviderResult.of(documentSequenceInfo);
		};
	};

	@Override
	public IPreliminaryDocumentNoBuilder createPreliminaryDocumentNoBuilder()
	{
		return new PreliminaryDocumentNoBuilder();
	}

	@Override
	public IDocumentNoBuilder forTableName(final String tableName, final int adClientId, final int adOrgId)
	{
		Check.assumeNotEmpty(tableName, "Given tableName parameter may not not ne empty");

		return createDocumentNoBuilder()
				.setDocumentSequenceInfo(computeDocumentSequenceInfoByTableName(tableName, adClientId, adOrgId))
				.setClientId(ClientId.ofRepoId(adClientId))
				.setFailOnError(false);
	}

	private DocumentSequenceInfo computeDocumentSequenceInfoByTableName(final String tableName, final int adClientId, final int adOrgId)
	{
		Check.assumeNotEmpty(tableName, DocumentNoBuilderException.class, "tableName not empty");

		final IDocumentSequenceDAO documentSequenceDAO = Services.get(IDocumentSequenceDAO.class);

		final String sequenceName = IDocumentNoBuilder.PREFIX_DOCSEQ + tableName;
		return documentSequenceDAO.retriveDocumentSequenceInfo(sequenceName, adClientId, adOrgId);
	}

	@Override
	public IDocumentNoBuilder forDocType(final int C_DocType_ID, final boolean useDefiniteSequence)
	{
		return createDocumentNoBuilder()
				.setDocumentSequenceByDocTypeId(C_DocType_ID, useDefiniteSequence);
	}
	
	@Override
	public IDocumentNoBuilder forSequenceId(final DocSequenceId sequenceId)
	{
		return createDocumentNoBuilder()
				.setDocumentSequenceInfoBySequenceId(sequenceId);
	}


	@Override
	public DocumentNoBuilder createDocumentNoBuilder()
	{
		return new DocumentNoBuilder();
	}

	@Override
	public IDocumentNoBuilder createValueBuilderFor(@NonNull final Object modelRecord)
	{
		final IClientOrgAware clientOrg = create(modelRecord, IClientOrgAware.class);
		final ClientId clientId = ClientId.ofRepoId(clientOrg.getAD_Client_ID());

		final ProviderResult providerResult = getDocumentSequenceInfo(modelRecord);

		return createDocumentNoBuilder()
				.setDocumentSequenceInfo(providerResult.getInfoOrNull())
				.setClientId(clientId)
				.setDocumentModel(modelRecord)
				.setFailOnError(false);
	}

	private ProviderResult getDocumentSequenceInfo(@NonNull final Object modelRecord)
	{
		for (final ValueSequenceInfoProvider provider : additionalProviders)
		{
			final ProviderResult result = provider.computeValueInfo(modelRecord);
			if (result.hasInfo())
			{
				return result;
			}
		}
		return tableNameBasedProvider.computeValueInfo(modelRecord);
	}
}
