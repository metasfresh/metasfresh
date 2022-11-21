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

import com.google.common.base.Strings;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.Duration;

@Value
public class ExternalSystemSAPConfig implements IExternalSystemChildConfig
{
	private static final AdMessageKey MSG_DUPLICATE_SFTP_FILE_LOOKUP_DETAILS = AdMessageKey.of("ExternalSystemConfigSAPDuplicateSFTPFileLookupDetails");

	@NonNull
	ExternalSystemSAPConfigId id;

	@NonNull
	ExternalSystemParentConfigId parentId;

	@NonNull
	String value;

	@NonNull
	String sftpHostName;

	@NonNull
	String sftpPort;

	@NonNull
	String sftpUsername;

	@NonNull
	String sftpPassword;

	@NonNull
	String processedDirectory;

	@NonNull
	String erroredDirectory;

	@NonNull
	Duration pollingFrequency;

	//product
	@Nullable
	String sftpTargetDirectoryProduct;
	@Nullable
	String sftpFileNamePatternProduct;

	//bpartner
	@Nullable
	String sftpTargetDirectoryBPartner;
	@Nullable
	String sftpFileNamePatternBPartner;

	//credit limit
	@Nullable
	String sftpCreditLimitTargetDirectory;
	@Nullable
	String sftpCreditLimitFileNamePattern;

	@Builder
	public ExternalSystemSAPConfig(
			@NonNull final ExternalSystemSAPConfigId id,
			@NonNull final ExternalSystemParentConfigId parentId,
			@NonNull final String value,
			@NonNull final String sftpHostName,
			@NonNull final String sftpPort,
			@NonNull final String sftpUsername,
			@NonNull final String sftpPassword,
			@Nullable final String sftpTargetDirectoryProduct,
			@Nullable final String sftpTargetDirectoryBPartner,
			@NonNull final String processedDirectory,
			@NonNull final String erroredDirectory,
			@NonNull final Duration pollingFrequency,
			@Nullable final String sftpFileNamePatternProduct,
			@Nullable final String sftpFileNamePatternBPartner,
			@Nullable final String sftpCreditLimitTargetDirectory,
			@Nullable final String sftpCreditLimitFileNamePattern)
	{
		this.id = id;
		this.parentId = parentId;
		this.value = value;
		this.sftpHostName = sftpHostName;
		this.sftpPort = sftpPort;
		this.sftpUsername = sftpUsername;
		this.sftpPassword = sftpPassword;
		this.sftpTargetDirectoryProduct = sftpTargetDirectoryProduct;
		this.sftpTargetDirectoryBPartner = sftpTargetDirectoryBPartner;
		this.processedDirectory = processedDirectory;
		this.erroredDirectory = erroredDirectory;
		this.pollingFrequency = pollingFrequency;
		this.sftpFileNamePatternProduct = sftpFileNamePatternProduct;
		this.sftpFileNamePatternBPartner = sftpFileNamePatternBPartner;
		this.sftpCreditLimitTargetDirectory = sftpCreditLimitTargetDirectory;
		this.sftpCreditLimitFileNamePattern = sftpCreditLimitFileNamePattern;
	}

	@NonNull
	public static ExternalSystemSAPConfig cast(@NonNull final IExternalSystemChildConfig childCondig)
	{
		return (ExternalSystemSAPConfig)childCondig;
	}

	@NonNull
	public BooleanWithReason isStartServicePossible(@NonNull final SAPExternalRequest sapExternalRequest, @NonNull final IMsgBL msgBL)
	{
		if (!sapExternalRequest.isStartService())
		{
			return BooleanWithReason.TRUE;
		}

		final String productFileLookupInfo = Strings.nullToEmpty(sftpTargetDirectoryProduct)
				.concat(Strings.nullToEmpty(sftpFileNamePatternProduct));

		final String bpartnerFileLookupInfo = Strings.nullToEmpty(sftpTargetDirectoryBPartner)
				.concat(Strings.nullToEmpty(sftpFileNamePatternBPartner));

		final String creditLimitFileLookupInfo = Strings.nullToEmpty(sftpCreditLimitTargetDirectory)
				.concat(Strings.nullToEmpty(sftpCreditLimitFileNamePattern));

		final boolean isFileLookupInfoDuplicated;
		switch (sapExternalRequest)
		{
			case START_BPARTNER_SYNC:
				isFileLookupInfoDuplicated = bpartnerFileLookupInfo.equals(productFileLookupInfo) || bpartnerFileLookupInfo.equals(creditLimitFileLookupInfo);
				break;
			case START_PRODUCT_SYNC:
				isFileLookupInfoDuplicated = productFileLookupInfo.equals(bpartnerFileLookupInfo) || productFileLookupInfo.equals(creditLimitFileLookupInfo);
				break;
			case START_CREDIT_LIMITS_SYNC:
				isFileLookupInfoDuplicated = creditLimitFileLookupInfo.equals(productFileLookupInfo) || creditLimitFileLookupInfo.equals(bpartnerFileLookupInfo);
				break;
			default:
				throw new AdempiereException("Unexpected sapExternalRequest=" + sapExternalRequest.getCode());
		}

		if (isFileLookupInfoDuplicated)
		{
			final ITranslatableString duplicateFileLookupInfoErrorMsg = msgBL.getTranslatableMsgText(MSG_DUPLICATE_SFTP_FILE_LOOKUP_DETAILS,
																									 sapExternalRequest.getCode(),
																									 parentId.getRepoId());

			return BooleanWithReason.falseBecause(duplicateFileLookupInfoErrorMsg);
		}

		return BooleanWithReason.TRUE;
	}
}
