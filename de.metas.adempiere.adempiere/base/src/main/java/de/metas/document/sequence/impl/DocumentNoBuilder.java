package de.metas.document.sequence.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.adempiere.util.time.SimpleDateFormatThreadLocal;
import org.compiere.model.I_C_DocType;
import org.compiere.model.MSequence;
import org.compiere.util.DB;
import org.compiere.util.DB.OnFail;
import org.compiere.util.Env;
import org.compiere.util.ISqlUpdateReturnProcessor;
import org.slf4j.Logger;

import com.google.common.base.Objects;
import com.google.common.base.Suppliers;

import de.metas.document.DocTypeSequenceMap;
import de.metas.document.DocumentNoBuilderException;
import de.metas.document.DocumentSequenceInfo;
import de.metas.document.IDocumentSequenceDAO;
import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequenceno.CustomSequenceNoProvider;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import lombok.NonNull;

/**
 * Increment and builds document numbers. Use {@link IDocumentNoBuilderFactory} to get an instance.
 *
 * @author tsa
 *
 */
class DocumentNoBuilder implements IDocumentNoBuilder
{
	private static final String PROVIDER_NOT_APPLICABLE = "de.metas.document.CustomSequenceNotProviderNoApplicable";
	// services
	private static final transient Logger logger = LogManager.getLogger(DocumentNoBuilder.class);
	private final transient IDocumentSequenceDAO documentSequenceDAO = Services.get(IDocumentSequenceDAO.class);
	final IMsgBL msgBL = Services.get(IMsgBL.class);

	private static final int QUERY_TIME_OUT = MSequence.QUERY_TIME_OUT;
	private static final transient SimpleDateFormatThreadLocal DATEFORMAT_CalendarYear = new SimpleDateFormatThreadLocal("yyyy");

	private Integer _adClientId;
	private Boolean _isAdempiereSys;
	private Object _documentModel;
	private Supplier<DocumentSequenceInfo> _documentSeqInfoSupplier;

	private String _sequenceNo = null;
	private boolean _failOnError = false; // default=false, for backward compatibility
	private boolean _usePreliminaryDocumentNo = false; // default=false, for backward compatibility

	DocumentNoBuilder()
	{
	}

	@Override
	public String build() throws DocumentNoBuilderException
	{
		try
		{
			return build0();
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

	private final String build0() throws Exception
	{
		//
		// Get the sequence number that we shall use
		final String sequenceNo = getSequenceNoToUse();
		if (Objects.equal(NO_DOCUMENTNO, sequenceNo))
		{
			return NO_DOCUMENTNO;
		}

		//
		// Build DocumentNo
		final StringBuilder documentNo = new StringBuilder();

		//
		// DocumentNo - Prefix
		final DocumentSequenceInfo docSeqInfo = getDocumentSequenceInfo();
		final String prefix = docSeqInfo.getPrefix();
		if (!Check.isEmpty(prefix, true))
		{
			documentNo.append(prefix);
		}

		//
		// DocumentNo - SequenceNo
		final String decimalPattern = docSeqInfo.getDecimalPattern();
		final String sequenceNoFinal;
		if (!Check.isEmpty(decimalPattern) && stringCanBeParsedAsInt(sequenceNo))
		{
			try
			{
				final int seqNoAsInt = Integer.parseInt(sequenceNo);
				sequenceNoFinal = new DecimalFormat(decimalPattern).format(seqNoAsInt);
			}
			catch (final Exception e)
			{
				throw new DocumentNoBuilderException("Failed formatting sequenceNo '" + sequenceNo + "' using format '" + decimalPattern + "'");
			}
		}
		else
		{
			sequenceNoFinal = String.valueOf(sequenceNo);
		}
		documentNo.append(sequenceNoFinal);

		//
		// DocumentNo - Suffix
		final String suffix = docSeqInfo.getSuffix();
		if (!Check.isEmpty(suffix, true))
		{
			documentNo.append(suffix);
		}

		//
		// Append preliminary markers if needed
		if (isUsePreliminaryDocumentNo())
		{
			documentNo
					.insert(0, IPreliminaryDocumentNoBuilder.DOCUMENTNO_MARKER_BEGIN)
					.append(IPreliminaryDocumentNoBuilder.DOCUMENTNO_MARKER_END);
		}

		//
		return documentNo.toString();
	}

	private String getCalendarYear(final String dateColumn)
	{
		final Object documentModel = getDocumentModelOrNull();
		if (documentModel != null && !Check.isEmpty(dateColumn, true))
		{
			final java.util.Date docDate = InterfaceWrapperHelper.getValueOrNull(documentModel, dateColumn);
			if (docDate != null)
			{
				final String calendarYear = DATEFORMAT_CalendarYear.format(docDate);
				return calendarYear;
			}
		}

		// Fallback: use current year
		final String calendarYear = DATEFORMAT_CalendarYear.format(new Date());
		return calendarYear;
	}

	/**
	 * @return sequenceNo to be used or <code>-1</code> in case the DocumentNo generation shall be skipped.
	 */
	private String getSequenceNoToUse()
	{
		// If manual sequenceNo was provided, then used
		if (_sequenceNo != null)
		{
			return _sequenceNo;
		}

		//
		// Don't increment sequence number if it's not Auto
		final DocumentSequenceInfo docSeqInfo = getDocumentSequenceInfo();

		final CustomSequenceNoProvider customSequenceNoProvider = docSeqInfo.getCustomSequenceNoProvider();
		if (customSequenceNoProvider != null)
		{
			if (!customSequenceNoProvider.isApplicable(_documentModel))
			{
				final ITranslatableString msg = msgBL.getTranslatableMsgText(PROVIDER_NOT_APPLICABLE, docSeqInfo.getName());
				throw new DocumentNoBuilderException(msg)
						.appendParametersToMessage()
						.setParameter("documentModel", _documentModel);
			}

			return customSequenceNoProvider.provideSequenceNo(_documentModel);
		}

		if (!docSeqInfo.isAutoSequence())
		{
			logger.info("Skip getting and incrementing the sequence because it's not an auto sequence: {}", docSeqInfo);
			return NO_DOCUMENTNO;
		}

		//
		// Get and increment sequence number from database
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

	private int retrieveAndIncrementSequenceCurrentNext(final DocumentSequenceInfo docSeqInfo)
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
		DB.executeUpdate(sql,
				sqlParams.toArray(),
				OnFail.ThrowException,
				trxName,
				QUERY_TIME_OUT,
				new ISqlUpdateReturnProcessor()
				{
					@Override
					public void process(final ResultSet rs) throws SQLException
					{
						currentSeq.setValue(rs.getInt(1));
					}
				});

		return currentSeq.getValue();
	}

	private int retrieveSequenceCurrentNext(final DocumentSequenceInfo docSeqInfo)
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

	private String getTrxName()
	{
		return ITrx.TRXNAME_None;
	}

	private final boolean isAdempiereSys()
	{
		if (_isAdempiereSys != null)
		{
			return _isAdempiereSys;
		}

		final int adClientId = getAD_Client_ID();
		_isAdempiereSys = MSequence.isAdempiereSys(adClientId);
		return _isAdempiereSys;
	}

	@Override
	public DocumentNoBuilder setAD_Client_ID(final int adClientId)
	{
		_adClientId = adClientId;
		_isAdempiereSys = MSequence.isAdempiereSys(adClientId);
		return this;
	}

	private final int getAD_Client_ID()
	{
		if (_adClientId != null)
		{
			return _adClientId;
		}

		final Object documentModel = getDocumentModelOrNull();
		if (documentModel != null)
		{
			final Integer adClientId = InterfaceWrapperHelper.getValueOrNull(documentModel, "AD_Client_ID");
			if (adClientId != null)
			{
				_adClientId = adClientId;
				return adClientId;
			}
		}

		throw new DocumentNoBuilderException("Cannot find AD_Client_ID");
	}

	private final int getAD_Org_ID()
	{
		final Object documentModel = getDocumentModelOrNull();
		if (documentModel != null)
		{
			final Integer adOrgId = InterfaceWrapperHelper.getValueOrNull(documentModel, "AD_Org_ID");
			if (adOrgId != null)
			{
				return adOrgId;
			}
		}

		return Env.CTXVALUE_AD_Org_ID_Any;
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
	public IDocumentNoBuilder setDocumentSequenceInfoBySequenceId(int AD_Sequence_ID)
	{
		setDocumentSequenceInfo(() -> documentSequenceDAO.retriveDocumentSequenceInfo(AD_Sequence_ID));
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

		final int docSequenceId;
		if (useDefiniteSequence)
		{
			if (!docType.isOverwriteSeqOnComplete())
			{
				throw new DocumentNoBuilderException("DocType " + docType + " does not have OverrideSeqOnComplete set")
						.setSkipGenerateDocumentNo(true);
			}

			docSequenceId = docType.getDefiniteSequence_ID();
			if (docSequenceId <= 0)
			{
				throw new DocumentNoBuilderException("No Definite Sequence for DocType - " + docType);
			}
		}
		else
		{
			final DocTypeSequenceMap docTypeSequenceMap = documentSequenceDAO.retrieveDocTypeSequenceMap(docType);
			docSequenceId = docTypeSequenceMap.getDocNoSequence_ID(getAD_Client_ID(), getAD_Org_ID());
			if (docSequenceId <= 0)
			{
				throw new DocumentNoBuilderException("No Sequence for DocType - " + docType);
			}

		}
		final DocumentSequenceInfo documentSeqInfo = documentSequenceDAO.retriveDocumentSequenceInfo(docSequenceId);
		return documentSeqInfo;

	}

	private Object getDocumentModelOrNull()
	{
		return _documentModel;
	}

	@Override
	public DocumentNoBuilder setDocumentModel(final Object documentModel)
	{
		_documentModel = documentModel;
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

	private final boolean isFailOnError()
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
