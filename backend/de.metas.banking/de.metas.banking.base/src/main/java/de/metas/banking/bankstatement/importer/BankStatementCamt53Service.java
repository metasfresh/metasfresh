/*
 * #%L
 * de.metas.banking.base
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

package de.metas.banking.bankstatement.importer;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableSet;
import de.metas.banking.BankStatementId;
import de.metas.banking.api.BankAccountService;
import de.metas.banking.bankstatement.importer.wrapper.AccountStatement2Wrapper;
import de.metas.banking.bankstatement.importer.wrapper.NoBatchBankToCustomerStatementV02Wrapper;
import de.metas.banking.bankstatement.importer.wrapper.NoBatchReportEntry2Wrapper;
import de.metas.banking.jaxb.camt053_001_02.AccountStatement2;
import de.metas.banking.jaxb.camt053_001_02.BankToCustomerStatementV02;
import de.metas.banking.jaxb.camt053_001_02.Document;
import de.metas.banking.jaxb.camt053_001_02.ObjectFactory;
import de.metas.banking.jaxb.camt053_001_02.ReportEntry2;
import de.metas.banking.service.BankStatementCreateRequest;
import de.metas.banking.service.IBankStatementDAO;
import de.metas.currency.CurrencyRepository;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.util.Optional;

@Service
public class BankStatementCamt53Service
{
	private static final Logger logger = LogManager.getLogger(BankStatementCamt53Service.class);

	private final IBankStatementDAO bankStatementDAO = Services.get(IBankStatementDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

	private final BankAccountService bankAccountService;
	private final CurrencyRepository currencyRepository;

	public BankStatementCamt53Service(
			@NonNull final BankAccountService bankAccountService,
			@NonNull final CurrencyRepository currencyRepository)
	{
		this.bankAccountService = bankAccountService;
		this.currencyRepository = currencyRepository;
	}

	@NonNull
	public ImmutableSet<BankStatementId> importBankToCustomerStatement(@NonNull final InputStream inputStream)
	{
		final NoBatchBankToCustomerStatementV02Wrapper noBatchBankToCustomerStatementV02Wrapper = NoBatchBankToCustomerStatementV02Wrapper
				.of(loadCamt53Document(inputStream));

		return noBatchBankToCustomerStatementV02Wrapper
				.getAccountStatements()
				.stream()
				.map(this::importBankStatement)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	private Optional<BankStatementId> importBankStatement(@NonNull final AccountStatement2 accountStatement2)
	{
		final AccountStatement2Wrapper accountStatement2Wrapper = buildAccountStatement2Wrapper(accountStatement2);

		final BankStatementCreateRequest bankStatementCreateRequest = accountStatement2Wrapper.buildBankStatementCreateRequest()
				.orElse(null);

		if (bankStatementCreateRequest == null)
		{
			return Optional.empty();
		}

		final BankStatementId bankStatementId = bankStatementDAO.createBankStatement(bankStatementCreateRequest);

		Loggables.withLogger(logger, Level.DEBUG).addLog(
				"One bank statement with id={} created for BankStatementCreateRequest={}", bankStatementId, bankStatementCreateRequest);

		accountStatement2.getNtry()
				.stream()
				.map(this::buildNoBatchReportEntry2Wrapper)
				.forEach(entry -> importBankStatementLine(entry, bankStatementId, bankStatementCreateRequest.getOrgId()));

		return Optional.of(bankStatementId);
	}

	private void importBankStatementLine(
			@NonNull final NoBatchReportEntry2Wrapper entryWrapper,
			@NonNull final BankStatementId bankStatementId,
			@NonNull final OrgId orgId)
	{
		entryWrapper.buildBankStatementLineCreateRequest(bankStatementId, orgId)
				.ifPresent(bankStatementDAO::createBankStatementLine);
	}

	@NonNull
	private NoBatchReportEntry2Wrapper buildNoBatchReportEntry2Wrapper(@NonNull final ReportEntry2 reportEntry2)
	{
		return NoBatchReportEntry2Wrapper.builder()
				.entry(reportEntry2)
				.invoiceDAO(invoiceDAO)
				.currencyRepository(currencyRepository)
				.orgDAO(orgDAO)
				.build();
	}

	@NonNull
	private AccountStatement2Wrapper buildAccountStatement2Wrapper(@NonNull final AccountStatement2 accountStatement2)
	{
		return AccountStatement2Wrapper.builder()
				.accountStatement2(accountStatement2)
				.bankAccountService(bankAccountService)
				.currencyRepository(currencyRepository)
				.orgDAO(orgDAO)
				.build();
	}

	@NonNull
	private static BankToCustomerStatementV02 loadCamt53Document(@NonNull final InputStream dataInputStream)
	{
		try
		{
			final JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
			final Unmarshaller unmarshaller = context.createUnmarshaller();

			@SuppressWarnings("unchecked") final JAXBElement<Document> e = (JAXBElement<Document>)unmarshaller.unmarshal(getXMLStreamReader(dataInputStream));

			return e.getValue().getBkToCstmrStmt();
		}
		catch (final JAXBException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	@NonNull
	private static XMLStreamReader getXMLStreamReader(@NonNull final InputStream data)
	{
		try
		{
			final XMLInputFactory xif = XMLInputFactory.newInstance();
			return xif.createXMLStreamReader(data);
		}
		catch (final XMLStreamException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}
}
