package org.adempiere.document.service.impl;

/*
 * #%L
 * ADempiere ERP - Base
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
import java.util.logging.Level;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.document.DocumentNoBuilderException;
import org.adempiere.document.service.DocumentSequenceInfo;
import org.adempiere.document.service.IDocumentNoBuilder;
import org.adempiere.document.service.IDocumentSequenceDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.adempiere.util.time.SimpleDateFormatThreadLocal;
import org.compiere.model.MDocType;
import org.compiere.model.MSequence;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DB.OnFail;
import org.compiere.util.Env;
import org.compiere.util.ISqlUpdateReturnProcessor;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

/**
 * Increment and builds document numbers.
 * 
 * @author tsa
 *
 */
class DocumentNoBuilder implements IDocumentNoBuilder
{

	private static final transient CLogger logger = CLogger.getCLogger(DocumentNoBuilder.class);

	public static final String PREFIX_DOCSEQ = MSequence.PREFIX_DOCSEQ; // "public" ...to be used in tests
	private static final int QUERY_TIME_OUT = MSequence.QUERY_TIME_OUT;
	private static final transient SimpleDateFormatThreadLocal DATEFORMAT_CalendarYear = new SimpleDateFormatThreadLocal("yyyy");

	private Integer _adClientId;
	private Boolean _isAdempiereSys;
	private PO _po;
	private Supplier<DocumentSequenceInfo> _documentSeqInfoSupplier;

	private Integer _sequenceNo = null;
	private boolean _failOnError = false; // default=false, for backward compatibility

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
				logger.log(Level.FINE, "Skip generating documentNo sequence. Returning NO_DOCUMENTNO.", e);

			}
			else if (isFailOnError())
			{
				throw docNoEx;
			}
			else
			{
				logger.log(Level.WARNING, "Failed building the sequence. Returning NO_DOCUMENTNO", e);
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

		// DocumentNo - Prefix
		final String prefix = docSeqInfo.getPrefix();
		documentNo.append(parseVariables(prefix));

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

		// DocumentNo - Suffix
		final String suffix = docSeqInfo.getSuffix();
		documentNo.append(parseVariables(suffix));

		return documentNo.toString();
	}

	/**
	 * Parse context variables from given string.
	 *
	 * @param string
	 * @return parsed string
	 */
	private final String parseVariables(final String string)
	{
		if (string == null || string.isEmpty())
		{
			return "";
		}

		final PO po = getPO_OrNull();
		final String trxName = getTrxName();
		final boolean keepUnparseable = false;
		final String stringParsed = Env.parseVariable(string, po, trxName, keepUnparseable);
		return stringParsed;
	}

	private String getCalendarYear(final String dateColumn)
	{
		final PO po = getPO_OrNull();
		if (po != null && !Check.isEmpty(dateColumn, true))
		{
			final Date docDate = (Date)po.get_Value(dateColumn);
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
			logger.log(Level.INFO, "Skip getting and incrementing the sequence because it's not an auto sequence: {0}", docSeqInfo);
			return -1;
		}

		//
		// Get and increment sequence number from database
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

		final PO po = getPO_OrNull();
		if (po != null)
		{
			_adClientId = po.getAD_Client_ID();
			return _adClientId;
		}

		throw new DocumentNoBuilderException("Cannot find AD_Client_ID");
	}

	private DocumentSequenceInfo getDocumentSequenceInfo()
	{
		Check.assumeNotNull(_documentSeqInfoSupplier, DocumentNoBuilderException.class, "DocumentSequenceInfo supplier shall be set");
		final DocumentSequenceInfo documentSeqInfo = _documentSeqInfoSupplier.get();
		Check.assumeNotNull(documentSeqInfo, DocumentNoBuilderException.class, "documentSeqInfo not null");
		return documentSeqInfo;
	}

	@Override
	public DocumentNoBuilder setDocumentSequenceInfo(final Supplier<DocumentSequenceInfo> documentSeqInfoSupplier)
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
		setDocumentSequenceInfo(new Supplier<DocumentSequenceInfo>()
		{

			@Override
			public DocumentSequenceInfo get()
			{
				final DocumentSequenceInfo documentSeqInfo = Services.get(IDocumentSequenceDAO.class).retriveDocumentSequenceInfo(sequenceName, adClientId, adOrgId);
				return documentSeqInfo;
			}
		});

		return this;
	}

	@Override
	public DocumentNoBuilder setSequenceByDocTypeId(final int C_DocType_ID, final boolean useDefiniteSequence)
	{
		setDocumentSequenceInfo(new Supplier<DocumentSequenceInfo>()
		{
			@Override
			public DocumentSequenceInfo get()
			{
				Check.assume(C_DocType_ID > 0, DocumentNoBuilderException.class, "C_DocType_ID > 0");

				final MDocType dt = MDocType.get(Env.getCtx(), C_DocType_ID);	// wrong for SERVER, but r/o
				Check.assumeNotNull(dt, DocumentNoBuilderException.class, "docType not null");

				if (!dt.isDocNoControlled())
				{
					throw new DocumentNoBuilderException("DocType " + dt + " Not DocNo controlled")
							// If we were asked to NOT fail on error, we assume this is a common use case which is safe to not log it as a warning.
							.setSkipGenerateDocumentNo(!isFailOnError());
				}

				final int docSequenceId;
				if (useDefiniteSequence)
				{
					if (!dt.isOverwriteSeqOnComplete())
					{
						throw new DocumentNoBuilderException("DocType " + dt + " does not have OverrideSeqOnComplete set")
								.setSkipGenerateDocumentNo(true);
					}

					docSequenceId = dt.getDefiniteSequence_ID();
					if (docSequenceId <= 0)
					{
						throw new DocumentNoBuilderException("No Definite Sequence for DocType - " + dt);
					}
				}
				else
				{
					docSequenceId = dt.getDocNoSequence_ID();
					if (docSequenceId <= 0)
					{
						throw new DocumentNoBuilderException("No Sequence for DocType - " + dt);
					}

				}
				final DocumentSequenceInfo documentSeqInfo = Services.get(IDocumentSequenceDAO.class).retriveDocumentSequenceInfo(docSequenceId);
				return documentSeqInfo;
			}
		});

		return this;
	}

	private PO getPO_OrNull()
	{
		return _po;
	}

	@Override
	public DocumentNoBuilder setPO(final PO po)
	{
		_po = po;
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
}
