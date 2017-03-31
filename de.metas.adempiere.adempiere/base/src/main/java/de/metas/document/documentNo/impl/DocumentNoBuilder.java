package de.metas.document.documentNo.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import de.metas.document.DocTypeSequenceMap;
import de.metas.document.DocumentNoBuilderException;
import de.metas.document.DocumentSequenceInfo;
import de.metas.document.IDocumentSequenceDAO;
import de.metas.document.documentNo.IDocumentNoBuilder;
import de.metas.logging.LogManager;

/**
 * Increment and builds document numbers.
 * 
 * @author tsa
 *
 */
class DocumentNoBuilder implements IDocumentNoBuilder
{
	// services
	private static final transient Logger logger = LogManager.getLogger(DocumentNoBuilder.class);
	private final transient IDocumentSequenceDAO documentSequenceDAO = Services.get(IDocumentSequenceDAO.class);

	public static final String PREFIX_DOCSEQ = MSequence.PREFIX_DOCSEQ; // "public" ...to be used in tests
	private static final int QUERY_TIME_OUT = MSequence.QUERY_TIME_OUT;
	private static final transient SimpleDateFormatThreadLocal DATEFORMAT_CalendarYear = new SimpleDateFormatThreadLocal("yyyy");

	private Integer _adClientId;
	private Boolean _isAdempiereSys;
	private Object _documentModel;
	private Supplier<DocumentSequenceInfo> _documentSeqInfoSupplier;

	private Integer _sequenceNo = null;
	private boolean _failOnError = false; // default=false, for backward compatibility
	private boolean _usePreliminaryDocumentNo = false; // default=false, for backward compatibility

	DocumentNoBuilder()
	{
		super();
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
		final DocumentSequenceInfo docSeqInfo = getDocumentSequenceInfo();

		//
		// Get the sequence number that we shall use
		final int sequenceNo = getSequenceNoToUse();
		if (sequenceNo < 0)
		{
			return NO_DOCUMENTNO;
		}

		//
		// Build DocumentNo
		final StringBuilder documentNo = new StringBuilder();

		//
		// DocumentNo - Prefix
		final String prefix = docSeqInfo.getPrefix();
		if (!Check.isEmpty(prefix, true))
		{
			documentNo.append(prefix);
		}

		//
		// DocumentNo - SequenceNo
		final String decimalPattern = docSeqInfo.getDecimalPattern();
		final String sequenceNoStr;
		if (decimalPattern != null && decimalPattern.length() > 0)
		{
			try
			{
				sequenceNoStr = new DecimalFormat(decimalPattern).format(sequenceNo);
			}
			catch (final Exception e)
			{
				throw new DocumentNoBuilderException("Failed formatting sequenceNo '" + sequenceNo + "' using format '" + decimalPattern + "'");
			}
		}
		else
		{
			sequenceNoStr = String.valueOf(sequenceNo);
		}
		documentNo.append(sequenceNoStr);

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
			documentNo.insert(0, IPreliminaryDocumentNoBuilder.DOCUMENTNO_MARKER_BEGIN).append(IPreliminaryDocumentNoBuilder.DOCUMENTNO_MARKER_END);
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
	private int getSequenceNoToUse()
	{
		// If manual sequenceNo was provided, then used
		if (_sequenceNo != null)
		{
			return _sequenceNo;
		}

		//
		// Don't increment sequence number if it's not Auto
		final DocumentSequenceInfo docSeqInfo = getDocumentSequenceInfo();
		if (!docSeqInfo.isAutoSequence())
		{
			logger.info("Skip getting and incrementing the sequence because it's not an auto sequence: {}", docSeqInfo);
			return -1;
		}

		//
		// Get and increment sequence number from database
		if (isUsePreliminaryDocumentNo())
		{
			return retrieveSequenceCurrentNext(docSeqInfo);
		}
		else
		{
			return retrieveAndIncrementSequenceCurrentNext(docSeqInfo);
		}
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
			sqlParams.add(docSeqInfo.getAD_Sequence_ID());
			sqlParams.add(docSeqInfo.getIncrementNo());
		}
		else if (docSeqInfo.isStartNewYear())
		{
			final String calendarYear = getCalendarYear(docSeqInfo.getDateColumn());

			sql = "UPDATE AD_Sequence_No SET CurrentNext = CurrentNext + ? WHERE AD_Sequence_ID = ? AND CalendarYear = ? RETURNING CurrentNext - ?";
			sqlParams.add(docSeqInfo.getIncrementNo());
			sqlParams.add(docSeqInfo.getAD_Sequence_ID());
			sqlParams.add(calendarYear);
			sqlParams.add(docSeqInfo.getIncrementNo());

		}
		else
		{
			sql = "UPDATE AD_Sequence SET CurrentNext = CurrentNext + ? WHERE AD_Sequence_ID = ? RETURNING CurrentNext - ?";
			sqlParams.add(docSeqInfo.getIncrementNo());
			sqlParams.add(docSeqInfo.getAD_Sequence_ID());
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
		final int adSequenceId = docSeqInfo.getAD_Sequence_ID();
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

	@Override
	public DocumentNoBuilder setTrxName(final String trxName)
	{
		// FIXME: 08240 because we had big issues with AD_Sequence getting locked, we decided to acquire next sequence out of transaction (as a workaround)
		// NOTE: we are keeping this method here just to keep the old logic (in case we want to turn on trxName usage)
		return this;
	}

	private final String getTrxName()
	{
		// FIXME: 08240 because we had big issues with AD_Sequence getting locked, we decided to acquire next sequence out of transaction (as a workaround)
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

	private DocumentNoBuilder setDocumentSequenceInfo(final Supplier<DocumentSequenceInfo> documentSeqInfoSupplier)
	{
		_documentSeqInfoSupplier = Suppliers.memoize(documentSeqInfoSupplier);
		return this;
	}

	@Override
	public DocumentNoBuilder setDocumentSequenceInfo(final DocumentSequenceInfo documentSeqInfo)
	{
		setDocumentSequenceInfo(Suppliers.ofInstance(documentSeqInfo));
		return this;
	}

	@Override
	public DocumentNoBuilder setDocumentSequenceInfoByTableName(final String tableName, final int adClientId, final int adOrgId)
	{
		Check.assumeNotEmpty(tableName, DocumentNoBuilderException.class, "tableName not empty");
		final String sequenceName = PREFIX_DOCSEQ + tableName;
		setDocumentSequenceInfo(() -> documentSequenceDAO.retriveDocumentSequenceInfo(sequenceName, adClientId, adOrgId));
		return this;
	}

	@Override
	public DocumentNoBuilder setDocumentSequenceByDocTypeId(final int C_DocType_ID, final boolean useDefiniteSequence)
	{
		setDocumentSequenceInfo(() -> retrieveDocumentSequenceInfoByDocTypeId(C_DocType_ID, useDefiniteSequence));
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
	public DocumentNoBuilder setSequenceNo(final int sequenceNo)
	{
		Check.assume(sequenceNo >= 0, DocumentNoBuilderException.class, "sequenceNo >= 0");
		_sequenceNo = sequenceNo;
		return this;
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
