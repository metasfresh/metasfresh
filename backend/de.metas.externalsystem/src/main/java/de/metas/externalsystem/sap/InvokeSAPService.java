/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.externalsystem.sap;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPGroup;
import de.metas.bpartner.BPGroupRepository;
import de.metas.common.externalsystem.JsonExternalMapping;
import de.metas.common.externalsystem.JsonExternalSAPBPartnerImportSettings;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.externalreference.ByTypeAndSystemConfigIdQuery;
import de.metas.externalreference.ExternalReference;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.IExternalReferenceType;
import de.metas.externalreference.productcategory.ProductCategoryExternalReferenceType;
import de.metas.externalreference.uom.UOMExternalReferenceType;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.producttype.ProductTypeExternalMapping;
import de.metas.externalsystem.producttype.ProductTypeExternalMappingRepo;
import de.metas.externalsystem.sap.importsettings.SAPBPartnerImportSettings;
import de.metas.externalsystem.sap.source.SAPContentSourceLocalFile;
import de.metas.externalsystem.sap.source.SAPContentSourceSFTP;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.IMsgBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.ObjectMapperUtil;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_CHECK_DESCRIPTION_FOR_MATERIAL_TYPE;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_CHILD_CONFIG_VALUE;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_APPROVED_BY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_BPARTNER_FILE_NAME_PATTERN;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_BPARTNER_TARGET_DIRECTORY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_CONVERSION_RATE_FILENAME_PATTERN;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_CONVERSION_RATE_TARGET_DIRECTORY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_CREDIT_LIMIT_FILENAME_PATTERN;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_CREDIT_LIMIT_TARGET_DIRECTORY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_ERRORED_DIRECTORY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_POLLING_FREQUENCY_MS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_PROCESSED_DIRECTORY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_PRODUCT_FILE_NAME_PATTERN;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_PRODUCT_TARGET_DIRECTORY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_LOCAL_FILE_ROOT_LOCATION;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_PRODUCT_CATEGORY_MAPPINGS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_PRODUCT_TYPE_MAPPINGS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SAP_BPARTNER_IMPORT_SETTINGS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_APPROVED_BY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_BPARTNER_FILE_NAME_PATTERN;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_BPARTNER_TARGET_DIRECTORY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_CONVERSION_RATE_FILENAME_PATTERN;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_CONVERSION_RATE_TARGET_DIRECTORY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_CREDIT_LIMIT_FILENAME_PATTERN;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_CREDIT_LIMIT_TARGET_DIRECTORY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_ERRORED_DIRECTORY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_HOST_NAME;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_PASSWORD;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_POLLING_FREQUENCY_MS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_PORT;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_PROCESSED_DIRECTORY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_PRODUCT_FILE_NAME_PATTERN;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_PRODUCT_TARGET_DIRECTORY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_SFTP_USERNAME;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_UOM_MAPPINGS;

@Service
public class InvokeSAPService
{
	@NonNull
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	@NonNull
	private final ExternalReferenceRepository externalReferenceRepository;

	@NonNull
	private final ProductTypeExternalMappingRepo productTypeExternalMappingRepo;

	@NonNull
	private final BPGroupRepository bpGroupRepository;

	public InvokeSAPService(
			@NonNull final ExternalReferenceRepository externalReferenceRepository,
			@NonNull final ProductTypeExternalMappingRepo productTypeExternalMappingRepo,
			@NonNull final BPGroupRepository bpGroupRepository)
	{
		this.externalReferenceRepository = externalReferenceRepository;
		this.productTypeExternalMappingRepo = productTypeExternalMappingRepo;
		this.bpGroupRepository = bpGroupRepository;
	}

	@NonNull
	public Map<String, String> getParameters(
			@NonNull final ExternalSystemSAPConfig sapConfig,
			@NonNull final String externalRequest)
	{
		final Map<String, String> parameters = new HashMap<>();

		parameters.put(PARAM_CHILD_CONFIG_VALUE, sapConfig.getValue());
		parameters.put(PARAM_CHECK_DESCRIPTION_FOR_MATERIAL_TYPE, String.valueOf(sapConfig.isCheckDescriptionForMaterialType()));
		parameters.putAll(extractContentSourceParameters(sapConfig, externalRequest));

		parameters.putAll(getMappingParameters(sapConfig.getParentId()));

		parameters.put(PARAM_SAP_BPARTNER_IMPORT_SETTINGS, ObjectMapperUtil.writeAsStringUnchecked(toJsonBPartnerImportSettingsList(sapConfig.getBPartnerImportSettings())));

		return parameters;
	}

	@NonNull
	private ImmutableMap<String, String> getMappingParameters(@NonNull final ExternalSystemParentConfigId parentConfigId)
	{
		final ByTypeAndSystemConfigIdQuery externalReferenceQuery = ByTypeAndSystemConfigIdQuery.builder()
				.externalSystemParentConfigId(parentConfigId.getRepoId())
				.externalReferenceTypeSet(ImmutableSet.of(UOMExternalReferenceType.UOM, ProductCategoryExternalReferenceType.PRODUCT_CATEGORY))
				.build();

		final Map<IExternalReferenceType, List<ExternalReference>> type2ExternalRefList = externalReferenceRepository.getExternalReferenceByConfigIdAndType(externalReferenceQuery);

		final ImmutableList<JsonExternalMapping> productCategoryMappings = Optional
				.ofNullable(type2ExternalRefList.get(ProductCategoryExternalReferenceType.PRODUCT_CATEGORY))
				.orElseGet(ImmutableList::of)
				.stream()
				.map(InvokeSAPService::getMappingForProductCategoryExtReference)
				.collect(ImmutableList.toImmutableList());

		final ImmutableList<JsonExternalMapping> uomMappings = Optional
				.ofNullable(type2ExternalRefList.get(UOMExternalReferenceType.UOM))
				.orElseGet(ImmutableList::of)
				.stream()
				.map(this::getMappingForUOMExtReference)
				.collect(ImmutableList.toImmutableList());

		final ImmutableList<ProductTypeExternalMapping> productTypeExternalMapping = productTypeExternalMappingRepo.getByConfigId(parentConfigId);
		final ImmutableList<JsonExternalMapping> productTypeMappingsAsString = productTypeExternalMapping.stream()
				.map(InvokeSAPService::getMappingFor)
				.collect(ImmutableList.toImmutableList());

		final Map<String, String> paramName2JsonExtMappingsAsString = new HashMap<>();

		paramName2JsonExtMappingsAsString.put(PARAM_PRODUCT_CATEGORY_MAPPINGS,
											  ObjectMapperUtil.writeAsStringUnchecked(productCategoryMappings));

		paramName2JsonExtMappingsAsString.put(PARAM_UOM_MAPPINGS,
											  ObjectMapperUtil.writeAsStringUnchecked(uomMappings));

		paramName2JsonExtMappingsAsString.put(PARAM_PRODUCT_TYPE_MAPPINGS,
											  ObjectMapperUtil.writeAsStringUnchecked(productTypeMappingsAsString));

		return ImmutableMap.copyOf(paramName2JsonExtMappingsAsString);
	}

	@NonNull
	private JsonExternalMapping getMappingForUOMExtReference(@NonNull final ExternalReference externalReference)
	{
		Check.assume(externalReference.getExternalReferenceType().getTableName().equals(UOMExternalReferenceType.UOM.getTableName()),
					 "externalReference must have UOMExternalReferenceType type");

		final I_C_UOM uomRecord = uomDAO.getById(UomId.ofRepoId(externalReference.getRecordId()));

		return JsonExternalMapping.of(externalReference.getExternalReference(), uomRecord.getX12DE355());
	}

	@NonNull
	private Map<String, String> extractContentSourceParameters(
			@NonNull final ExternalSystemSAPConfig sapConfig,
			@NonNull final String externalRequest)
	{
		final SAPExternalRequest sapExternalRequest = SAPExternalRequest.ofCode(externalRequest);

		if (sapExternalRequest.isSFTPSpecific())
		{
			final SAPContentSourceSFTP sapContentSourceSFTP = Optional.ofNullable(sapConfig.getContentSourceSFTP())
					.orElseThrow(() -> new AdempiereException("No SFTP config found for SAP!")
							.appendParametersToMessage()
							.setParameter("ExternalSystemConfigId", sapConfig.getParentId().getRepoId()));

			throwErrorIfFalse(sapContentSourceSFTP.isStartServicePossible(sapExternalRequest, sapConfig.getParentId(), msgBL));

			return extractSFTPSourceParameters(sapContentSourceSFTP);
		}
		else if (sapExternalRequest.isLocalFileSpecific())
		{
			final SAPContentSourceLocalFile sapContentSourceLocalFile = Optional.ofNullable(sapConfig.getContentSourceLocalFile())
					.orElseThrow(() -> new AdempiereException("No LocalFile config found for SAP!")
							.appendParametersToMessage()
							.setParameter("ExternalSystemConfigId", sapConfig.getParentId().getRepoId()));

			throwErrorIfFalse(sapContentSourceLocalFile.isStartServicePossible(sapExternalRequest, sapConfig.getParentId(), msgBL));

			return extractLocalFileSourceParameters(sapContentSourceLocalFile);
		}

		throw new AdempiereException("Unexpected External_Request: " + sapExternalRequest);
	}

	@NonNull
	private static Map<String, String> extractSFTPSourceParameters(final @NonNull SAPContentSourceSFTP contentSourceSFTP)
	{
		final Map<String, String> parameters = new HashMap<>();

		parameters.put(PARAM_SFTP_HOST_NAME, contentSourceSFTP.getHostName());
		parameters.put(PARAM_SFTP_PORT, contentSourceSFTP.getPort());
		parameters.put(PARAM_SFTP_USERNAME, contentSourceSFTP.getUsername());
		parameters.put(PARAM_SFTP_PASSWORD, contentSourceSFTP.getPassword());

		parameters.put(PARAM_SFTP_PROCESSED_DIRECTORY, contentSourceSFTP.getProcessedDirectory());
		parameters.put(PARAM_SFTP_ERRORED_DIRECTORY, contentSourceSFTP.getErroredDirectory());
		parameters.put(PARAM_SFTP_POLLING_FREQUENCY_MS, String.valueOf(contentSourceSFTP.getPollingFrequency().toMillis()));

		parameters.put(PARAM_SFTP_PRODUCT_TARGET_DIRECTORY, contentSourceSFTP.getTargetDirectoryProduct());
		parameters.put(PARAM_SFTP_PRODUCT_FILE_NAME_PATTERN, contentSourceSFTP.getFileNamePatternProduct());

		parameters.put(PARAM_SFTP_BPARTNER_TARGET_DIRECTORY, contentSourceSFTP.getTargetDirectoryBPartner());
		parameters.put(PARAM_SFTP_BPARTNER_FILE_NAME_PATTERN, contentSourceSFTP.getFileNamePatternBPartner());

		parameters.put(PARAM_SFTP_CREDIT_LIMIT_TARGET_DIRECTORY, contentSourceSFTP.getTargetDirectoryCreditLimit());
		parameters.put(PARAM_SFTP_CREDIT_LIMIT_FILENAME_PATTERN, contentSourceSFTP.getFileNamePatternCreditLimit());

		parameters.put(PARAM_SFTP_CONVERSION_RATE_TARGET_DIRECTORY, contentSourceSFTP.getTargetDirectoryConversionRate());
		parameters.put(PARAM_SFTP_CONVERSION_RATE_FILENAME_PATTERN, contentSourceSFTP.getFileNamePatternConversionRate());

		parameters.put(PARAM_SFTP_APPROVED_BY, String.valueOf(UserId.toRepoId(contentSourceSFTP.getApprovedBy())));

		return parameters;
	}

	@NonNull
	private static Map<String, String> extractLocalFileSourceParameters(final @NonNull SAPContentSourceLocalFile contentSourceLocalFile)
	{
		final Map<String, String> parameters = new HashMap<>();

		parameters.put(PARAM_LOCAL_FILE_PROCESSED_DIRECTORY, contentSourceLocalFile.getProcessedDirectory());
		parameters.put(PARAM_LOCAL_FILE_ERRORED_DIRECTORY, contentSourceLocalFile.getErroredDirectory());
		parameters.put(PARAM_LOCAL_FILE_POLLING_FREQUENCY_MS, String.valueOf(contentSourceLocalFile.getPollingFrequency().toMillis()));
		parameters.put(PARAM_LOCAL_FILE_ROOT_LOCATION, contentSourceLocalFile.getRootLocation());

		parameters.put(PARAM_LOCAL_FILE_PRODUCT_TARGET_DIRECTORY, contentSourceLocalFile.getTargetDirectoryProduct());
		parameters.put(PARAM_LOCAL_FILE_PRODUCT_FILE_NAME_PATTERN, contentSourceLocalFile.getFileNamePatternProduct());

		parameters.put(PARAM_LOCAL_FILE_BPARTNER_TARGET_DIRECTORY, contentSourceLocalFile.getTargetDirectoryBPartner());
		parameters.put(PARAM_LOCAL_FILE_BPARTNER_FILE_NAME_PATTERN, contentSourceLocalFile.getFileNamePatternBPartner());

		parameters.put(PARAM_LOCAL_FILE_CREDIT_LIMIT_TARGET_DIRECTORY, contentSourceLocalFile.getTargetDirectoryCreditLimit());
		parameters.put(PARAM_LOCAL_FILE_CREDIT_LIMIT_FILENAME_PATTERN, contentSourceLocalFile.getFileNamePatternCreditLimit());

		parameters.put(PARAM_LOCAL_FILE_CONVERSION_RATE_TARGET_DIRECTORY, contentSourceLocalFile.getTargetDirectoryConversionRate());
		parameters.put(PARAM_LOCAL_FILE_CONVERSION_RATE_FILENAME_PATTERN, contentSourceLocalFile.getFileNamePatternConversionRate());

		parameters.put(PARAM_LOCAL_FILE_APPROVED_BY, String.valueOf(UserId.toRepoId(contentSourceLocalFile.getApprovedBy())));

		return parameters;
	}

	private static void throwErrorIfFalse(@NonNull final BooleanWithReason booleanWithReason)
	{
		if (booleanWithReason.isFalse())
		{
			throw new AdempiereException(booleanWithReason.getReason()).markAsUserValidationError();
		}
	}

	@NonNull
	private static JsonExternalMapping getMappingForProductCategoryExtReference(@NonNull final ExternalReference externalReference)
	{
		Check.assume(externalReference.getExternalReferenceType().getTableName().equals(ProductCategoryExternalReferenceType.PRODUCT_CATEGORY.getTableName()),
					 "externalReference must have ProductCategoryExternalReferenceType type");

		final JsonMetasfreshId metasfreshId = JsonMetasfreshId.of(externalReference.getRecordId());

		return JsonExternalMapping.of(externalReference.getExternalReference(), metasfreshId);
	}

	@NonNull
	private static JsonExternalMapping getMappingFor(@NonNull final ProductTypeExternalMapping productTypeExternalMapping)
	{
		return JsonExternalMapping.of(productTypeExternalMapping.getExternalValue(), productTypeExternalMapping.getValue());
	}

	@NonNull
	private ImmutableList<JsonExternalSAPBPartnerImportSettings> toJsonBPartnerImportSettingsList(@NonNull final ImmutableList<SAPBPartnerImportSettings> sapBPartnerImportSettingsList)
	{
		return sapBPartnerImportSettingsList.stream()
				.map(this::toJsonBPartnerImportSettings)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private JsonExternalSAPBPartnerImportSettings toJsonBPartnerImportSettings(@NonNull final SAPBPartnerImportSettings sapbPartnerImportSettings)
	{
		return JsonExternalSAPBPartnerImportSettings.builder()
				.partnerCodePattern(sapbPartnerImportSettings.getPartnerCodePattern())
				.isSingleBPartner(sapbPartnerImportSettings.isSingleBPartner())
				.seqNo(sapbPartnerImportSettings.getSeqNo())
				.bpGroupName(Optional.ofNullable(sapbPartnerImportSettings.getBpGroupId())
									 .map(bpGroupRepository::getbyId)
									 .map(BPGroup::getName)
									 .orElse(null))
				.build();
	}
}
