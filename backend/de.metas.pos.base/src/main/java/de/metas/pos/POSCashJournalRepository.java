package de.metas.pos;

import com.google.common.collect.ImmutableList;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.pos.repository.model.I_C_POS_Journal;
import de.metas.pos.repository.model.I_C_POS_JournalLine;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.function.Consumer;

@Repository
public class POSCashJournalRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public POSCashJournal getById(@NonNull final POSCashJournalId cashJournalId)
	{
		final I_C_POS_Journal record = retrieveRecordById(cashJournalId);
		final List<I_C_POS_JournalLine> lineRecords = retrieveLineRecordsByJournalId(cashJournalId);
		return fromRecord(record, lineRecords);
	}

	private static @NonNull I_C_POS_Journal retrieveRecordById(final @NonNull POSCashJournalId cashJournalId)
	{
		final I_C_POS_Journal record = InterfaceWrapperHelper.load(cashJournalId, I_C_POS_Journal.class);
		if (record == null)
		{
			throw new AdempiereException("No POS cash journal with id " + cashJournalId + " found");
		}
		return record;
	}

	private List<I_C_POS_JournalLine> retrieveLineRecordsByJournalId(final @NonNull POSCashJournalId cashJournalId)
	{
		return queryBL.createQueryBuilder(I_C_POS_JournalLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_POS_JournalLine.COLUMNNAME_C_POS_Journal_ID, cashJournalId)
				.orderBy(I_C_POS_JournalLine.COLUMN_C_POS_JournalLine_ID)
				.create()
				.list();
	}

	public POSCashJournal newJournal(final POSCashJournalCreateRequest request)
	{
		final I_C_POS_Journal record = InterfaceWrapperHelper.newInstance(I_C_POS_Journal.class);
		record.setAD_Org_ID(request.getOrgId().getRepoId());
		record.setC_POS_ID(request.getTerminalId().getRepoId());
		record.setDateTrx(Timestamp.from(request.getDateTrx()));
		record.setC_Currency_ID(request.getCashBeginningBalance().getCurrencyId().getRepoId());
		record.setCashBeginningBalance(request.getCashBeginningBalance().toBigDecimal());
		record.setCashEndingBalance(request.getCashBeginningBalance().toBigDecimal());
		record.setOpeningNote(request.getOpeningNote());
		record.setIsClosed(false);
		InterfaceWrapperHelper.save(record);

		return fromRecord(record, ImmutableList.of());
	}

	private static POSCashJournal fromRecord(final I_C_POS_Journal record, final List<I_C_POS_JournalLine> lineRecords)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());

		return POSCashJournal.builder()
				.id(POSCashJournalId.ofRepoId(record.getC_POS_Journal_ID()))
				.terminalId(POSTerminalId.ofRepoId(record.getC_POS_ID()))
				.dateTrx(record.getDateTrx().toInstant())
				.cashBeginningBalance(Money.of(record.getCashBeginningBalance(), currencyId))
				.cashEndingBalance(Money.of(record.getCashEndingBalance(), currencyId))
				.openingNote(record.getOpeningNote())
				.closingNote(record.getClosingNote())
				.isClosed(record.isClosed())
				.lines(lineRecords.stream()
						.map(lineRecord -> fromRecord(lineRecord, currencyId))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private static POSCashJournalLine fromRecord(final I_C_POS_JournalLine record, final CurrencyId currencyId)
	{
		return POSCashJournalLine.builder()
				.type(POSCashJournalLineType.ofCode(record.getType()))
				.amount(Money.of(record.getAmount(), currencyId))
				.cashierId(UserId.ofRepoId(record.getCashier_ID()))
				.description(record.getDescription())
				.posOrderAndPaymentId(POSOrderAndPaymentId.ofRepoIdsOrNull(record.getC_POS_Order_ID(), record.getC_POS_Payment_ID()))
				.build();
	}

	private static void updateRecord(final I_C_POS_Journal record, final POSCashJournal from)
	{
		record.setC_POS_ID(from.getTerminalId().getRepoId());
		record.setDateTrx(Timestamp.from(from.getDateTrx()));
		record.setC_Currency_ID(from.getCurrencyId().getRepoId());
		record.setCashBeginningBalance(from.getCashBeginningBalance().toBigDecimal());
		record.setCashEndingBalance(from.getCashEndingBalance().toBigDecimal());
		record.setOpeningNote(from.getOpeningNote());
		record.setClosingNote(from.getClosingNote());
		record.setIsClosed(from.isClosed());
	}

	private static void updateRecord(final I_C_POS_JournalLine record, final POSCashJournalLine from)
	{
		record.setType(from.getType().getCode());
		record.setAmount(from.getAmount().toBigDecimal());
		record.setCashier_ID(from.getCashierId().getRepoId());
		record.setDescription(from.getDescription());
		record.setC_POS_Order_ID(from.getPosOrderAndPaymentId() != null ? from.getPosOrderAndPaymentId().getOrderId().getRepoId() : -1);
		record.setC_POS_Payment_ID(from.getPosOrderAndPaymentId() != null ? from.getPosOrderAndPaymentId().getPaymentId().getRepoId() : -1);
	}

	public POSCashJournal changeJournalById(@NonNull final POSCashJournalId cashJournalId, @NonNull final Consumer<POSCashJournal> updater)
	{
		final I_C_POS_Journal record = retrieveRecordById(cashJournalId);
		final List<I_C_POS_JournalLine> lineRecords = retrieveLineRecordsByJournalId(cashJournalId);

		final POSCashJournal journal = fromRecord(record, lineRecords);
		updater.accept(journal);

		updateRecord(record, journal);
		InterfaceWrapperHelper.save(record);

		final ImmutableList<POSCashJournalLine> lines = journal.getLines();
		for (int i = 0; i < lines.size(); i++)
		{
			final POSCashJournalLine line = lines.get(i);
			I_C_POS_JournalLine lineRecord = i < lineRecords.size() ? lineRecords.get(i) : null;
			if (lineRecord == null)
			{
				lineRecord = InterfaceWrapperHelper.newInstance(I_C_POS_JournalLine.class);
				lineRecord.setC_POS_Journal_ID(record.getC_POS_Journal_ID());
			}
			lineRecord.setAD_Org_ID(record.getAD_Org_ID());
			updateRecord(lineRecord, line);
			InterfaceWrapperHelper.save(lineRecord);
		}
		for (int i = lines.size(); i < lineRecords.size(); i++)
		{
			InterfaceWrapperHelper.delete(lineRecords.get(i));
		}

		return journal;
	}
}
