package de.metas.acct.gljournal_sap.service;

import de.metas.acct.api.AccountId;
import de.metas.acct.api.IAccountDAO;
import de.metas.acct.api.impl.ElementValueId;
import de.metas.acct.gljournal_sap.SAPGLJournal;
import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.acct.gljournal_sap.SAPGLJournalLineId;
import de.metas.acct.model.I_SAP_GLJournal;
import de.metas.acct.model.I_SAP_GLJournalLine;
import de.metas.acct.open_items.FAOpenItemTrxInfo;
import de.metas.acct.open_items.FAOpenItemTrxInfoComputeRequest;
import de.metas.acct.open_items.FAOpenItemsService;
import de.metas.document.engine.DocStatus;
import de.metas.elementvalue.ElementValue;
import de.metas.elementvalue.ElementValueService;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
public class SAPGLJournalService
{
	private final IAccountDAO accountDAO = Services.get(IAccountDAO.class);
	private final SAPGLJournalRepository glJournalRepository;
	@Getter
	private final SAPGLJournalCurrencyConverter currencyConverter;
	@Getter
	private final SAPGLJournalTaxProvider taxProvider;
	private final ElementValueService elementValueService;
	private final FAOpenItemsService faOpenItemsService;

	public SAPGLJournalService(
			@NonNull final SAPGLJournalRepository glJournalRepository,
			@NonNull final SAPGLJournalCurrencyConverter currencyConverter,
			@NonNull final SAPGLJournalTaxProvider taxProvider,
			@NonNull final ElementValueService elementValueService,
			@NonNull final FAOpenItemsService faOpenItemsService)
	{
		this.glJournalRepository = glJournalRepository;
		this.currencyConverter = currencyConverter;
		this.taxProvider = taxProvider;
		this.elementValueService = elementValueService;
		this.faOpenItemsService = faOpenItemsService;
	}

	@NonNull
	public SAPGLJournal getById(@NonNull final SAPGLJournalId id) {return glJournalRepository.getById(id);}

	public SeqNo getNextSeqNo(@NonNull final SAPGLJournalId glJournalId)
	{
		return glJournalRepository.getNextSeqNo(glJournalId);
	}

	public void updateTotalsFromLines(@NonNull final SAPGLJournalId glJournalId)
	{
		updateById(glJournalId, SAPGLJournal::updateTotals);
	}

	public void updateWhileSaving(@NonNull final I_SAP_GLJournal record, @NonNull final Consumer<SAPGLJournal> consumer)
	{
		glJournalRepository.updateWhileSaving(record, consumer);
	}

	public void updateById(@NonNull final SAPGLJournalId glJournalId, @NonNull final Consumer<SAPGLJournal> consumer)
	{
		glJournalRepository.updateById(glJournalId, consumer);
	}

	@NonNull
	public SAPGLJournalLineId createLine(@NonNull final SAPGLJournalLineCreateRequest request, @NonNull final SAPGLJournalId id)
	{
		final SAPGLJournalLineCreateRequest requestEffective = prepareBeforeCreate(request, id);
		final Supplier<SAPGLJournalLineId> lineIdSupplier = glJournalRepository.updateById(
				id,
				glJournal -> {return glJournal.addLine(requestEffective, currencyConverter);}
		);

		final SAPGLJournalLineId lineId = lineIdSupplier.get();
		if (lineId == null)
		{
			// shall not happen
			throw new AdempiereException("Failed creating line for " + request);
		}

		return lineId;
	}

	private SAPGLJournalLineCreateRequest prepareBeforeCreate(
			final @NonNull SAPGLJournalLineCreateRequest requestParam,
			final @NonNull SAPGLJournalId id)
	{
		SAPGLJournalLineCreateRequest requestEff = requestParam;
		if (requestEff.getOpenItemTrxInfo() == null)
		{
			final SAPGLJournalLineId lineId;
			if (requestEff.getAlreadyReservedId() == null)
			{
				lineId = glJournalRepository.acquireLineId(id);
				requestEff = requestEff.withAlreadyReservedId(lineId);
			}
			else
			{
				lineId = requestEff.getAlreadyReservedId();
				Check.assumeEquals(lineId.getGlJournalId(), id, "Reserved line ID and SAP_GLJournalLine_ID shall match: {}, {}", id, lineId);
			}

			final AccountId accountId = requestEff.getAccount().getAccountId();
			final FAOpenItemTrxInfo openItemTrxInfo = computeTrxInfo(accountId, lineId).orElse(null);
			requestEff = requestEff.withOpenItemTrxInfo(openItemTrxInfo);
		}

		return requestEff;
	}

	public void createLines(@NonNull final List<SAPGLJournalLineCreateRequest> requests, @NonNull final SAPGLJournalId id)
	{
		if (requests.isEmpty())
		{
			return;
		}

		glJournalRepository.updateById(id, glJournal -> {
			glJournal.addLines(requests, currencyConverter);
		});
	}

	public void regenerateTaxLines(final SAPGLJournalId glJournalId)
	{
		updateById(glJournalId, glJournal -> glJournal.regenerateTaxLines(taxProvider, currencyConverter));
	}

	public DocStatus getDocStatus(final SAPGLJournalId glJournalId)
	{
		return glJournalRepository.getDocStatus(glJournalId);
	}

	@NonNull
	public SAPGLJournal reverse(@NonNull final SAPGLJournalReverseRequest reverseJournalRequest)
	{
		final SAPGLJournal journalToBeReversed = glJournalRepository.getById(reverseJournalRequest.getSourceJournalId());

		final SAPGLJournal reversal = glJournalRepository.create(journalToBeReversed.getReversal(reverseJournalRequest.getDateDoc()),
				currencyConverter);

		glJournalRepository.save(journalToBeReversed.withDocStatus(DocStatus.Reversed));

		return reversal;
	}

	public void updateTrxInfo(final I_SAP_GLJournalLine glJournalLine)
	{
		final FAOpenItemTrxInfo openItemTrxInfo;
		final AccountId accountId = AccountId.ofRepoIdOrNull(glJournalLine.getC_ValidCombination_ID());
		if (accountId != null)
		{
			final SAPGLJournalId glJournalId = SAPGLJournalId.ofRepoId(glJournalLine.getSAP_GLJournal_ID());
			final SAPGLJournalLineId lineId;
			if (glJournalLine.getSAP_GLJournalLine_ID() <= 0)
			{
				lineId = glJournalRepository.acquireLineId(glJournalId);
				glJournalLine.setSAP_GLJournalLine_ID(lineId.getRepoId());
			}
			else
			{
				lineId = SAPGLJournalLineId.ofRepoId(glJournalId, glJournalLine.getSAP_GLJournalLine_ID());
			}

			openItemTrxInfo = computeTrxInfo(accountId, lineId).orElse(null);
		}
		else
		{
			openItemTrxInfo = null;
		}

		SAPGLJournalLoaderAndSaver.updateRecordFromOpenItemTrxInfo(glJournalLine, openItemTrxInfo);
	}

	private Optional<FAOpenItemTrxInfo> computeTrxInfo(
			@NonNull final AccountId accountId,
			@NonNull final SAPGLJournalLineId sapGLJournalLineId)
	{
		final ElementValueId elementValueId = accountDAO.getElementValueIdByAccountId(accountId);
		final ElementValue elementValue = elementValueService.getById(elementValueId);
		if (elementValue.isOpenItem())
		{
			return faOpenItemsService.computeTrxInfo(FAOpenItemTrxInfoComputeRequest.builder()
					.elementValueId(elementValueId)
					.tableName(I_SAP_GLJournal.Table_Name)
					.recordId(sapGLJournalLineId.getGlJournalId().getRepoId())
					.lineId(sapGLJournalLineId.getRepoId())
					.build());
		}
		else
		{
			return Optional.empty();
		}
	}

	public void fireAfterComplete(final I_SAP_GLJournal record)
	{
		final SAPGLJournal glJournal = glJournalRepository.getByRecord(record);
		faOpenItemsService.fireGLJournalCompleted(glJournal);
	}

	public void fireBeforeReactivate(final I_SAP_GLJournal record)
	{
		final SAPGLJournal glJournal = glJournalRepository.getByRecord(record);
		faOpenItemsService.fireGLJournalBeforeReactivate(glJournal);
	}
}
