package de.metas.payment.esr.dataimporter.impl.camt54.v02;

import de.metas.i18n.TranslatableStrings;
import de.metas.payment.camt054_001_02.CreditorReferenceInformation2;
import de.metas.payment.camt054_001_02.EntryTransaction2;
import de.metas.payment.camt054_001_02.StructuredRemittanceInformation7;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.dataimporter.ESRTransaction.ESRTransactionBuilder;
import de.metas.payment.esr.dataimporter.ESRType;
import de.metas.payment.esr.dataimporter.impl.camt54.ESRDataImporterCamt54;
import lombok.NonNull;
import org.compiere.util.Env;

import java.util.Objects;
import java.util.Optional;

/*
 * #%L
 * de.metas.payment.esr
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

/**
 * This little helper class has the job of getting the (ESR) reference and passing it to an {@link ESRTransactionBuilder}.
 * There is also some fallback and error messages to be done in case of some not-so-happy scenarios.
 * <p>
 * Note: codebeat keeps complaining about how spagetti {@link ESRDataImporterCamt54} is, so from time to time I extract something :-D.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class ReferenceStringHelperv02
{

	private static boolean isSupportedESRType(de.metas.payment.camt054_001_02.CreditorReferenceInformation2 cdtrRefInf)
	{
		return cdtrRefInf != null
				&& cdtrRefInf.getTp() != null
				&& cdtrRefInf.getTp().getCdOrPrtry() != null
				&& (cdtrRefInf.getTp().getCdOrPrtry().getPrtry().equals(ESRType.TYPE_ESR.getCode())
				|| cdtrRefInf.getTp().getCdOrPrtry().getPrtry().equals(ESRType.TYPE_QRR.getCode()));
	}

	/**
	 * extractAndSetEsrReference for version 2 <code>BankToCustomerDebitCreditNotificationV02</code>
	 */
	public void extractAndSetEsrReference(
			@NonNull final EntryTransaction2 txDtls,
			@NonNull final ESRTransactionBuilder trxBuilder)
	{
		final Optional<String> esrReferenceNumberString = extractEsrReference(txDtls);
		if (esrReferenceNumberString.isPresent())
		{
			trxBuilder.esrReferenceNumber(esrReferenceNumberString.get());
		}
		else
		{
			final Optional<String> fallback = extractReferenceFallback(txDtls);
			if (fallback.isPresent())
			{
				trxBuilder.esrReferenceNumber(fallback.get());
				trxBuilder.errorMsg(TranslatableStrings.adMessage(ESRConstants.MSG_AMBIGOUS_REFERENCE).translate((Env.getAD_Language())));
			}
			else
			{
				trxBuilder.errorMsg(TranslatableStrings.adMessage(ESRConstants.MSG_MISSING_ESR_REFERENCE).translate((Env.getAD_Language())));
			}
		}
	}

	/**
	 * Gets <code>TxDtls/RmtInf/Strd/CdtrRefInf/Ref</code><br>
	 * from a <code>CdtrRefInf</code> element<br>
	 * that has <code>CdtrRefInf/Tp/CdOrPrtry == "ISR Reference"</code>.
	 * extractEsrReference for version 2 <code>BankToCustomerDebitCreditNotificationV02</code>
	 *
	 * @task https://github.com/metasfresh/metasfresh/issues/2107
	 */
	private Optional<String> extractEsrReference(@NonNull final EntryTransaction2 txDtls)
	{
		// get the esr reference string out of the XML tree
		// it's stored in the cdtrRefInf records whose cdtrRefInf/tp/cdOrPrtry/prtr equals to ISR_REFERENCE or QRR_REFERENCE
		return txDtls.getRmtInf().getStrd().stream()
				.map(StructuredRemittanceInformation7::getCdtrRefInf)

				// it's stored in the cdtrRefInf records whose cdtrRefInf/tp/cdOrPrtry/prtr equals to ISR_REFERENCE or QRR_REFERENCE
				.filter(ReferenceStringHelperv02::isSupportedESRType)
				.map(CreditorReferenceInformation2::getRef)
				.findFirst();
	}

	/*
	 * extractAndSetType for version 2 <code>BankToCustomerDebitCreditNotificationV02</code>
	 */
	public void extractAndSetType(
			@NonNull final EntryTransaction2 txDtls,
			@NonNull final ESRTransactionBuilder trxBuilder)
	{
		final Optional<ESRType> type = extractType(txDtls);
		if (type.isPresent())
		{
			trxBuilder.type(type.get());
		}
		else
		{
			// fallback to esr type
			trxBuilder.type(ESRType.TYPE_ESR);
		}
	}

	private Optional<ESRType> extractType(@NonNull final EntryTransaction2 txDtls)
	{
		return txDtls.getRmtInf().getStrd().stream()
				.map(StructuredRemittanceInformation7::getCdtrRefInf)
				.filter(ReferenceStringHelperv02::isSupportedESRType)
				.map(cdtrRefInf -> cdtrRefInf.getTp().getCdOrPrtry().getPrtry())
				.map(ESRType::ofNullableCode)
				.findFirst();
	}

	/**
	 * extractReferenceFallback for version 2 <code>BankToCustomerDebitCreditNotificationV02</code>
	 *
	 * @task https://github.com/metasfresh/metasfresh/issues/2107
	 */
	private Optional<String> extractReferenceFallback(@NonNull final EntryTransaction2 txDtls)
	{
		// get the esr reference string out of the XML tree
		return txDtls.getRmtInf().getStrd().stream()
				.map(StructuredRemittanceInformation7::getCdtrRefInf)
				.filter(Objects::nonNull)
				.map(CreditorReferenceInformation2::getRef)
				.findFirst();
	}
}