/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.document.sequence.impl;

import com.google.common.base.Suppliers;
import de.metas.common.util.time.SystemTime;
import de.metas.document.DocTypeSequenceMap;
import de.metas.document.DocumentNoBuilderException;
import de.metas.document.DocumentSequenceInfo;
import de.metas.document.IDocumentSequenceDAO;
import de.metas.document.sequence.DocSequenceId;
import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequenceno.CustomSequenceNoProvider;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.time.SimpleDateFormatThreadLocal;
import lombok.NonNull;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.I_C_DocType;
import org.compiere.model.MSequence;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Increment and builds document numbers. Use {@link IDocumentNoBuilderFactory} to get an instance.
 *
 * @author tsa
 */
class DocumentNoBuilder implements IDocumentNoBuilder
{
	// services
	private static final transient Logger logger = LogManager.getLogger(DocumentNoBuilder.class);
	private final transient IDocumentSequenceDAO documentSequenceDAO = Services.get(IDocumentSequenceDAO.class);
	final IMsgBL msgBL = Services.get(IMsgBL.class);

	private static final AdMessageKey MSG_PROVIDER_NOT_APPLICABLE = AdMessageKey.of("de.metas.document.CustomSequenceNotProviderNoApplicable");

	private static final int QUERY_TIME_OUT = MSequence.QUERY_TIME_OUT;
	private static final transient SimpleDateFormatThreadLocal DATEFORMAT_CalendarYear = new SimpleDateFormatThreadLocal("yyyy");

	private ClientId _adClientId;
	private Boolean _isAdempiereSys;
	private Evaluatee _evalContext = Evaluatees.empty();
	private Supplier<DocumentSequenceInfo> _documentSeqInfoSupplier;

	private String _sequenceNo = null;
	private boolean _failOnError = false; // default=false, for backward compatibility
	private boolean _usePreliminaryDocumentNo = false; // default=false, for backward compatibility

	DocumentNoBuilder()
	{
	}

	@Override
	public @Nullable
	String build() throws DocumentNoBuilderException
	{
		try
		{
			return build0(buildParts());
		}
		catch (final Exception e)
		{
			final DocumentNoBuilderException docNoEx = DocumentNoBuilderException.wrapIfNeeded(e);
			if (docNoEx.isSkipGenerateDocumentNo())
			{
				logger.debug("Skip generating documentNo sequence. Returning NO_DOCUMENTNO.", e);

			}
			else if (isFailOnError())
			{
				throw docNoEx;
			}
			else
			{
				logger.warn("Failed building the sequence. Returning NO_DOCUMENTNO", e);
			}
		}

		return NO_DOCUMENTNO;
	}

	@Nullable
	@Override
	public DocumentNoParts buildParts()
	{
		final DocumentSequenceInfo docSeqInfo = getDocumentSequenceInfo();

		//
		// SequenceNo
		final String sequenceNoEvaluated;
		{
			final String sequenceNo = getSequenceNoToUse();
			if (sequenceNo == null)
			{
				return null;
			}

			final String decimalPattern = docSeqInfo.getDecimalPattern();
			if (!Check.isEmpty(decimalPattern) && stringCanBeParsedAsInt(sequenceNo))
			{
				try
				{
					final int seqNoAsInt = Integer.parseInt(sequenceNo);
					sequenceNoEvaluated = new DecimalFormat(decimalPattern).format(seqNoAsInt);
				}
				catch (final Exception e)
				{
					throw new DocumentNoBuilderException("Failed formatting sequenceNo '" + sequenceNo + "' using format '" + decimalPattern + "'");
				}
			}
			else
			{
				sequenceNoEvaluated = sequenceNo;
			}
		}

		//
		// Prefix
		final String prefixEvaluated;
		{
			final IStringExpression prefix = docSeqInfo.getPrefix();
			if (!prefix.isNullExpression())
			{
				prefixEvaluated = prefix.evaluate(getEvaluationContext(), OnVariableNotFound.Fail);
			}
			else
			{
				prefixEvaluated = "";
			}
		}

		//
		// Suffix
		final String suffixEvaluated;
		{
			final IStringExpression suffix = docSeqInfo.getSuffix();
			if (!suffix.isNullExpression())
			{
				suffixEvaluated = suffix.evaluate(getEvaluationContext(), OnVariableNotFound.Fail);
			}
			else
			{
				suffixEvaluated = "";
			}
		}

		return DocumentNoParts.builder()
				.prefix(prefixEvaluated)
				.suffix(suffixEvaluated)
				.sequenceNumber(sequenceNoEvaluated)
				.build();

	}

	@Nullable
	private String build0(@Nullable final DocumentNoParts documentNoParts)
	{
		if (documentNoParts == null)
		{
			return NO_DOCUMENTNO;
		}

		//
		// Build DocumentNo
		final StringBuilder finalDocumentNo = new StringBuilder()
				.append(documentNoParts.getPrefix())
				.append(documentNoParts.getSequenceNumber())
				.append(documentNoParts.getSuffix());

		//
		// Append preliminary markers if needed
		if (isUsePreliminaryDocumentNo())
		{
			finalDocumentNo
					.insert(0, IPreliminaryDocumentNoBuilder.DOCUMENTNO_MARKER_BEGIN)
					.append(IPreliminaryDocumentNoBuilder.DOCUMENTNO_MARKER_END);
		}

		//
		return finalDocumentNo.toString();
	}

	private String getCalendarYear(final String dateColumn)
	{
		final Evaluatee evalContext = getEvaluationContext();
		if (!Check.isEmpty(dateColumn, true))
		{
			final java.util.Date docDate = evalContext.get_ValueAsDate(dateColumn, null);
			if (docDate != null)
			{
				return DATEFORMAT_CalendarYear.format(docDate);
			}
		}

		// Fallback: use current year
		return DATEFORMAT_CalendarYear.format(SystemTime.asDate());
	}

	/**
	 * @return sequenceNo to be used or <code>-1</code> in case the DocumentNo generation shall be skipped.
	 */
	@Nullable
	private String getSequenceNoToUse()
	{
		// If manual sequenceNo was provided, then uset
		if (_sequenceNo != null)
		{
			logger.debug("getSequenceNoToUse - return sequenceNo={} which was provided via setter", _sequenceNo);
			return _sequenceNo;
		}

		final DocumentSequenceInfo docSeqInfo = getDocumentSequenceInfo();
		final String result;

		final CustomSequenceNoProvider customSequenceNoProvider = docSeqInfo.getCustomSequenceNoProvider();
		if (customSequenceNoProvider != null)
		{
			logger.debug("getSequenceNoToUse - going to invoke customSequenceNoProvider={}", customSequenceNoProvider);

			final Evaluatee evalContext = getEvaluationContext();
			if (!customSequenceNoProvider.isApplicable(evalContext))
			{
				final ITranslatableString msg = msgBL.getTranslatableMsgText(MSG_PROVIDER_NOT_APPLICABLE, docSeqInfo.getName());
				throw new DocumentNoBuilderException(msg)
						.appendParametersToMessage()
						.setParameter("context", evalContext);
			}

			final String customSequenceNumber = customSequenceNoProvider.provideSequenceNo(evalContext);
			logger.debug("getSequenceNoToUse - The customSequenceNoProvider returned customSequenceNumber={}" + customSequenceNumber);

			if (customSequenceNoProvider.isUseIncrementSeqNoAsPrefix())
			{
				logger.debug("getSequenceNoToUse - The customSequenceNoProvider.isUseIncrementSeqNoAsPrefix()=true; -> going to prepend an incremental sequence number to it");
				if (!docSeqInfo.isAutoSequence())
				{

					throw new AdempiereException("The current customSequenceNoProvider requires this sequence to be configured as auto-sequence")
							.appendParametersToMessage()
							.setParameter("customSequenceNoProvider", customSequenceNoProvider)
							.setParameter("docSeqInfo", docSeqInfo);
				}
				result = customSequenceNumber + "-" + retrieveAndIncrementSeqNo(docSeqInfo);
			}
			else
			{
				result = customSequenceNumber;
			}
		}
		else
		{
			logger.debug("getSequenceNoToUse - going to get incremental seuqnce number" + customSequenceNoProvider);

			//
			// Don't increment sequence number if it's not Auto
			if (!docSeqInfo.isAutoSequence())
			{
				logger.debug("Skip getting and incrementing the sequence because it's not an auto sequence: {}", docSeqInfo);
				result = NO_DOCUMENTNO;
			}
			else
			{
				result = retrieveAndIncrementSeqNo(docSeqInfo);
			}
		}

		logger.debug("getSequenceNoToUse - returning result={}", result);
		return result;
	}

	/**
	 * Get and increment sequence number from database
	 */
	private String retrieveAndIncrementSeqNo(@NonNull final DocumentSequenceInfo docSeqInfo)
	{
		final int sequenceNo;
		if (isUsePreliminaryDocumentNo())
		{
			sequenceNo = retrieveSequenceCurrentNext(docSeqInfo);
		}
		else
		{
			sequenceNo = retrieveAndIncrementSequenceCurrentNext(docSeqInfo);
		}
		return Integer.toString(sequenceNo);
	}

	private int retrieveAndIncrementSequenceCurrentNext(@NonNull final DocumentSequenceInfo docSeqInfo)
	{
		final String trxName = getTrxName();
		final List<Object> sqlParams = new ArrayList<>();
		final String sql;
		if (isAdempiereSys())
		{
			sql = "UPDATE AD_Sequence SET CurrentNextSys = CurrentNextSys + ? WHERE AD_Sequence_ID=? RETURNING CurrentNextSys - ?";
			sqlParams.add(docSeqInfo.getIncrementNo());
			sqlParams.add(docSeqInfo.getAdSequenceId());
			sqlParams.add(docSeqInfo.getIncrementNo());
		}
		else if (docSeqInfo.isStartNewYear())
		{
			final String calendarYear = getCalendarYear(docSeqInfo.getDateColumn());

			sql = "UPDATE AD_Sequence_No SET CurrentNext = CurrentNext + ? WHERE AD_Sequence_ID = ? AND CalendarYear = ? RETURNING CurrentNext - ?";
			sqlParams.add(docSeqInfo.getIncrementNo());
			sqlParams.add(docSeqInfo.getAdSequenceId());
			sqlParams.add(calendarYear);
			sqlParams.add(docSeqInfo.getIncrementNo());

		}
		else
		{
			sql = "UPDATE AD_Sequence SET CurrentNext = CurrentNext + ? WHERE AD_Sequence_ID = ? RETURNING CurrentNext - ?";
			sqlParams.add(docSeqInfo.getIncrementNo());
			sqlParams.add(docSeqInfo.getAdSequenceId());
			sqlParams.add(docSeqInfo.getIncrementNo());
		}

		final IMutable<Integer> currentSeq = new Mutable<>(-1);
		DB.executeUpdateEx(sql,
						   sqlParams.toArray(),
						   trxName,
						   QUERY_TIME_OUT,
						   rs -> currentSeq.setValue(rs.getInt(1)));

		return currentSeq.getValue();
	}

	private int retrieveSequenceCurrentNext(@NonNull final DocumentSequenceInfo docSeqInfo)
	{
		final int adSequenceId = docSeqInfo.getAdSequenceId();
		final String trxName = getTrxName();

		if (isAdempiereSys())
		{
			final String sql = "SELECT CurrentNextSys FROM AD_Sequence WHERE AD_Sequence_ID=?";
			return DB.getSQLValueEx(trxName, sql, adSequenceId);
		}
		else if (docSeqInfo.isStartNewYear())
		{
			final String calendarYear = getCalendarYear(docSeqInfo.getDateColumn());

			final String sql = "SELECT CurrentNext AD_Sequence_No WHERE AD_Sequence_ID = ? AND CalendarYear = ?";
			return DB.getSQLValueEx(trxName, sql, adSequenceId, calendarYear);
		}
		else
		{
			final String sql = "SELECT CurrentNext FROM AD_Sequence WHERE AD_Sequence_ID=?";
			return DB.getSQLValueEx(trxName, sql, adSequenceId);
		}
	}

	@Nullable
	private String getTrxName()
	{
		return ITrx.TRXNAME_None;
	}

	private boolean isAdempiereSys()
	{
		if (_isAdempiereSys != null)
		{
			return _isAdempiereSys;
		}

		final ClientId adClientId = getClientId();
		_isAdempiereSys = MSequence.isAdempiereSys(adClientId.getRepoId());
		return _isAdempiereSys;
	}

	@Override
	public DocumentNoBuilder setClientId(@NonNull final ClientId clientId)
	{
		_adClientId = clientId;
		_isAdempiereSys = null; // to be computed
		return this;
	}

	@NonNull
	private ClientId getClientId()
	{
		if (_adClientId != null)
		{
			return _adClientId;
		}

		final Evaluatee evalCtx = getEvaluationContext();
		if (evalCtx != null)
		{
			final ClientId adClientId = ClientId.ofRepoIdOrNull(evalCtx.get_ValueAsInt("AD_Client_ID", -1));
			if (adClientId != null)
			{
				_adClientId = adClientId;
				return adClientId;
			}
		}

		throw new DocumentNoBuilderException("Cannot find AD_Client_ID");
	}

	private OrgId getOrgId()
	{
		final Evaluatee evalCtx = getEvaluationContext();
		if (evalCtx != null)
		{
			return OrgId.ofRepoIdOrAny(evalCtx.get_ValueAsInt("AD_Org_ID", -1));
		}
		else
		{
			return OrgId.ANY;
		}
	}

	private DocumentSequenceInfo getDocumentSequenceInfo()
	{
		Check.assumeNotNull(_documentSeqInfoSupplier, DocumentNoBuilderException.class, "DocumentSequenceInfo supplier shall be set");
		final DocumentSequenceInfo documentSeqInfo = _documentSeqInfoSupplier.get();

		Check.assumeNotNull(documentSeqInfo, DocumentNoBuilderException.class, "documentSeqInfo not null");
		return documentSeqInfo;
	}

	@Override
	public DocumentNoBuilder setDocumentSequenceInfo(final DocumentSequenceInfo documentSeqInfo)
	{
		setDocumentSequenceInfo(Suppliers.ofInstance(documentSeqInfo));
		return this;
	}

	/**
	 * @param useDefiniteSequence if {@code true}, then the doc type's {@code DefiniteSequence_ID} is used.
	 */
	public DocumentNoBuilder setDocumentSequenceByDocTypeId(final int C_DocType_ID, final boolean useDefiniteSequence)
	{
		setDocumentSequenceInfo(() -> retrieveDocumentSequenceInfoByDocTypeId(C_DocType_ID, useDefiniteSequence));
		return this;
	}

	@Override
	public IDocumentNoBuilder setDocumentSequenceInfoBySequenceId(@NonNull final DocSequenceId sequenceId)
	{
		setDocumentSequenceInfo(() -> documentSequenceDAO.retriveDocumentSequenceInfo(sequenceId));
		return this;
	}

	private DocumentNoBuilder setDocumentSequenceInfo(@NonNull final Supplier<DocumentSequenceInfo> documentSeqInfoSupplier)
	{
		_documentSeqInfoSupplier = documentSeqInfoSupplier;
		return this;
	}

	private DocumentSequenceInfo retrieveDocumentSequenceInfoByDocTypeId(final int C_DocType_ID, final boolean useDefiniteSequence)
	{
		Check.assume(C_DocType_ID > 0, DocumentNoBuilderException.class, "C_DocType_ID > 0");

		final I_C_DocType docType = InterfaceWrapperHelper.create(Env.getCtx(), C_DocType_ID, I_C_DocType.class, ITrx.TRXNAME_None);
		Check.assumeNotNull(docType, DocumentNoBuilderException.class, "docType not null");

		if (!docType.isDocNoControlled())
		{
			throw new DocumentNoBuilderException("DocType " + docType + " Not DocNo controlled")
					// If we were asked to NOT fail on error, we assume this is a common use case which is safe to not log it as a warning.
					.setSkipGenerateDocumentNo(!isFailOnError());
		}

		final DocSequenceId docSequenceId;
		if (useDefiniteSequence)
		{
			if (!docType.isOverwriteSeqOnComplete())
			{
				throw new DocumentNoBuilderException("DocType " + docType + " does not have OverrideSeqOnComplete set")
						.setSkipGenerateDocumentNo(true);
			}

			docSequenceId = DocSequenceId.ofRepoIdOrNull(docType.getDefiniteSequence_ID());
			if (docSequenceId == null)
			{
				throw new DocumentNoBuilderException("No Definite Sequence for DocType - " + docType);
			}
		}
		else
		{
			final DocTypeSequenceMap docTypeSequenceMap = documentSequenceDAO.retrieveDocTypeSequenceMap(docType);
			docSequenceId = docTypeSequenceMap.getDocNoSequenceId(getClientId(), getOrgId());
			if (docSequenceId == null)
			{
				throw new DocumentNoBuilderException("No Sequence for DocType - " + docType);
			}

		}
		return documentSequenceDAO.retriveDocumentSequenceInfo(docSequenceId);

	}

	private Evaluatee getEvaluationContext()
	{
		return _evalContext;
	}

	@Override
	public IDocumentNoBuilder setEvaluationContext(@NonNull final Evaluatee evalContext)
	{
		this._evalContext = evalContext;
		return this;
	}

	@Override
	public DocumentNoBuilder setSequenceNo(@NonNull final String sequenceNo)
	{
		_sequenceNo = Check.assumeNotEmpty(sequenceNo, DocumentNoBuilderException.class, "sequenceNo");
		return this;
	}

	private boolean stringCanBeParsedAsInt(@NonNull final String string)
	{
		return string.matches("\\d+");
	}

	@Override
	public DocumentNoBuilder setFailOnError(final boolean failOnError)
	{
		_failOnError = failOnError;
		return this;
	}

	private boolean isFailOnError()
	{
		return _failOnError;
	}

	@Override
	public DocumentNoBuilder setUsePreliminaryDocumentNo(final boolean usePreliminaryDocumentNo)
	{
		this._usePreliminaryDocumentNo = usePreliminaryDocumentNo;
		return this;
	}

	private boolean isUsePreliminaryDocumentNo()
	{
		return _usePreliminaryDocumentNo;
	}
}
