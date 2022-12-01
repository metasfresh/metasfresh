package de.metas.payment.esr.dataimporter.impl.camt54;

import ch.qos.logback.classic.Level;
import com.google.common.annotations.VisibleForTesting;
import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountId;
import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.logging.LogManager;
import de.metas.money.CurrencyId;
import de.metas.payment.camt054_001_02.BankToCustomerDebitCreditNotificationV02;
import de.metas.payment.camt054_001_06.BankToCustomerDebitCreditNotificationV06;
import de.metas.payment.esr.dataimporter.ESRStatement;
import de.metas.payment.esr.dataimporter.IESRDataImporter;
import de.metas.payment.esr.model.I_ESR_ImportFile;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Properties;

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
 * Creates an {@link ESRStatement} from camt.54-XML data.
 * This implementation assumes the incoming XML to comply with the version <b><code>camt.054.001.06</code></b>.
 * However, XML which have versions <code>camt.054.001.04</code> and <code>camt.054.001.05</code> will also work (gh #1093).
 *
 * <p>
 * The XSD file for the xml which this implementation imports is available at <a href="https://www.iso20022.org/documents/messages/camt/schemas/camt.054.001.06.zip">BankToCustomerDebitCreditNotificationV04</a>.<br>
 * Note that
 * <ul>
 * <li>the latest version of the camt.54 XSD can be found <a href="https://www.iso20022.org/payments_messages.page">here</a> and</li>
 * <li>an archive of all older message specifications messages can be found <a href="https://www.iso20022.org/message_archive.page">here</a>.</li>
 * </ul>
 * <p>
 * Also see <a href="https://www.six-interbank-clearing.com/dam/downloads/en/standardization/iso/swiss-recommendations/implementation-guidelines-camt.pdf">Swiss Implementation Guidelines for Cash Management</a>.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class ESRDataImporterCamt54 implements IESRDataImporter
{
	private static final Logger logger = LogManager.getLogger(ESRDataImporterCamt54.class);

	@VisibleForTesting
	static final BigDecimal CTRL_QTY_AT_LEAST_ONE_NULL = BigDecimal.ONE.negate();

	@VisibleForTesting
	static final BigDecimal CTRL_QTY_NOT_YET_SET = BigDecimal.TEN.negate();

	protected static final AdMessageKey MSG_UNSUPPORTED_CREDIT_DEBIT_CODE_1P = AdMessageKey.of("ESR_CAMT54_UnsupportedCreditDebitCode");
	protected static final AdMessageKey MSG_BANK_ACCOUNT_MISMATCH_2P = AdMessageKey.of("ESR_CAMT54_BankAccountMismatch");
	protected static final AdMessageKey MSG_MULTIPLE_TRANSACTIONS_TYPES = AdMessageKey.of("ESR_CAMT54_MultipleTransactionsTypes");

	@NonNull private final InputStream input;
	@Nullable private final CurrencyCode bankAccountCurrencyCode;
	@NonNull private final String adLanguage;

	public ESRDataImporterCamt54(
			@NonNull final I_ESR_ImportFile header,
			@NonNull final InputStream input)
	{
		this.input = input;
		this.bankAccountCurrencyCode = extractBankAccountCurrencyCodeOrNull(header);
		this.adLanguage = extractAdLanguage(header);
	}

	@Nullable
	private static CurrencyCode extractBankAccountCurrencyCodeOrNull(@NonNull final I_ESR_ImportFile header)
	{
		final BankAccountId bankAccountId = BankAccountId.ofRepoIdOrNull(header.getC_BP_BankAccount_ID());
		if (bankAccountId != null)
		{
			final IBPBankAccountDAO bpBankAccountRepo = Services.get(IBPBankAccountDAO.class);
			final BankAccount bankAccount = bpBankAccountRepo.getById(bankAccountId);
			final CurrencyId bankAccountCurrencyId = bankAccount.getCurrencyId();

			final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
			return currencyDAO.getCurrencyCodeById(bankAccountCurrencyId);
		}
		else
		{
			return null;
		}
	}

	private static String extractAdLanguage(@NonNull final I_ESR_ImportFile header)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(header);
		return Env.getADLanguageOrBaseLanguage(ctx);
	}

	private static boolean isVersion2Schema(@NonNull final String namespaceURI)
	{
		return Objects.equals("urn:iso:std:iso:20022:tech:xsd:camt.054.001.02", namespaceURI);
	}

	private static String getNameSpaceURI(@NonNull final XMLStreamReader reader) throws XMLStreamException
	{
		while (reader.hasNext())
		{
			int event = reader.next();
			if (XMLStreamConstants.START_ELEMENT == event && reader.getNamespaceCount() > 0)
			{
				for (int nsIndex = 0; nsIndex < reader.getNamespaceCount(); nsIndex++)
				{
					final String nsId = reader.getNamespaceURI(nsIndex);
					if (nsId.startsWith("urn:iso:std:iso:20022:tech:xsd"))
					{
						return nsId;
					}
				}
			}
		}
		return null;
	}

	@Override
	public ESRStatement importData()
	{
		XMLStreamReader xsr = null;
		try
		{
			final XMLInputFactory xif = XMLInputFactory.newInstance();
			xsr = xif.createXMLStreamReader(input);

			// https://github.com/metasfresh/metasfresh/issues/1903
			// use a delegate to make sure that the unmarshaller won't refuse camt.054.001.04 and amt.054.001.05
			final MultiVersionStreamReaderDelegate mxsr = new MultiVersionStreamReaderDelegate(xsr);

			if (isVersion2Schema(getNameSpaceURI(mxsr)))
			{
				return importCamt54v02(mxsr);
			}
			else
			{
				return importCamt54v06(mxsr);
			}

		}
		catch (final XMLStreamException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
		finally
		{
			closeXmlReaderAndInputStream(xsr);
		}

	}

	private ESRStatement importCamt54v02(final MultiVersionStreamReaderDelegate mxsr)
	{
		final BankToCustomerDebitCreditNotificationV02 bkToCstmrDbtCdtNtfctn = ESRDataImporterCamt54v02.loadXML(mxsr);
		if (bkToCstmrDbtCdtNtfctn.getGrpHdr() != null && bkToCstmrDbtCdtNtfctn.getGrpHdr().getAddtlInf() != null)
		{
			Loggables.withLogger(logger, Level.INFO).addLog("The given input is a test file: bkToCstmrDbtCdtNtfctn/grpHdr/addtlInf={}", bkToCstmrDbtCdtNtfctn.getGrpHdr().getAddtlInf());
		}

		return ESRDataImporterCamt54v02.builder()
				.bankAccountCurrencyCode(bankAccountCurrencyCode)
				.adLanguage(adLanguage)
				.build()
				.createESRStatement(bkToCstmrDbtCdtNtfctn);
	}

	private ESRStatement importCamt54v06(final MultiVersionStreamReaderDelegate mxsr)
	{
		final BankToCustomerDebitCreditNotificationV06 bkToCstmrDbtCdtNtfctn = ESRDataImporterCamt54v06.loadXML(mxsr);
		if (bkToCstmrDbtCdtNtfctn.getGrpHdr() != null && bkToCstmrDbtCdtNtfctn.getGrpHdr().getAddtlInf() != null)
		{
			Loggables.withLogger(logger, Level.INFO).addLog("The given input is a test file: bkToCstmrDbtCdtNtfctn/grpHdr/addtlInf={}", bkToCstmrDbtCdtNtfctn.getGrpHdr().getAddtlInf());
		}

		return ESRDataImporterCamt54v06.builder()
				.bankAccountCurrencyCode(bankAccountCurrencyCode)
				.adLanguage(adLanguage)
				.build()
				.createESRStatement(bkToCstmrDbtCdtNtfctn);
	}

	private void closeXmlReaderAndInputStream(@Nullable final XMLStreamReader xsr)
	{
		try
		{
			if (xsr != null)
			{
				xsr.close();
			}
			input.close();
		}
		catch (XMLStreamException | IOException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}
}
