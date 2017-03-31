package de.metas.document.documentNo.impl;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_DocType;
import org.compiere.model.MSequence;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.google.common.base.Optional;

import de.metas.document.DocTypeSequenceMap;
import de.metas.document.DocumentNoBuilderException;
import de.metas.document.DocumentSequenceInfo;
import de.metas.document.IDocumentSequenceDAO;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/* package */final class PreliminaryDocumentNoBuilder implements IPreliminaryDocumentNoBuilder
{
	// services
	private final transient IDocumentSequenceDAO documentSequenceDAO = Services.get(IDocumentSequenceDAO.class);

	private static final String DOCSUBTYPE_NONE = "--";

	//
	// Parameters
	private I_C_DocType _newDocType;
	private int _oldDocType_ID = -1;
	private I_C_DocType _oldDocType; // lazy
	private String _oldDocumentNo = null;
	private Object _documentModel;
	private Integer _adClientId; // lazy
	private Integer _adOrgId; // lazy

	//
	// State
	private final AtomicBoolean _built = new AtomicBoolean(false);
	private Integer _oldSequence_ID = null;  // lazy

	/* package */ PreliminaryDocumentNoBuilder()
	{
		super();
	}

	@Override
	public final IDocumentNoInfo buildOrNull()
	{
		// Check if already built
		if (_built.getAndSet(true))
		{
			throw new IllegalStateException("Already built");
		}

		try
		{
			return buildOrNull0();
		}
		catch (final Exception ex)
		{
			throw DocumentNoBuilderException.wrapIfNeeded(ex);
		}
	}

	private final IDocumentNoInfo buildOrNull0()
	{
		final I_C_DocType newDocType = getNewDocType();
		if (newDocType == null)
		{
			return null;
		}

		final String docBaseType = newDocType.getDocBaseType();
		final String docSubType = Util.coalesce(newDocType.getDocSubType(), DOCSUBTYPE_NONE);
		final boolean isSOTrx = newDocType.isSOTrx();
		final boolean hasChanges = newDocType.isHasCharges();

		// DocumentNo
		final String newDocumentNo;
		final boolean isDocNoControlled = newDocType.isDocNoControlled();
		if (isDocNoControlled)
		{
			final DocTypeSequenceMap newDocTypeSequenceMap = documentSequenceDAO.retrieveDocTypeSequenceMap(newDocType);
			final int newDocSequence_ID = newDocTypeSequenceMap.getDocNoSequence_ID(getAD_Client_ID(), getAD_Org_ID());
			final boolean isNewDocumentNo = isNewDocumentNo() || newDocSequence_ID != getOldSequence_ID();

			if (isNewDocumentNo)
			{
				newDocumentNo = retrieveCurrentDocumentNo(newDocSequence_ID);
			}
			else
			{
				// keep the old documentNo
				newDocumentNo = getOldDocumentNo();
			}
		}
		else
		{
			newDocumentNo = null;
		}

		return DocumentNoInfo.builder()
				.setDocumentNo(newDocumentNo)
				.setDocBaseType(docBaseType)
				.setDocSubType(docSubType)
				.setIsSOTrx(isSOTrx)
				.setHasChanges(hasChanges)
				.setDocNoControlled(isDocNoControlled)
				.build();
	}

	private String retrieveCurrentDocumentNo(final int docSequence_ID)
	{
		final int adClientId = getAD_Client_ID();
		if (MSequence.isAdempiereSys(adClientId))
		{
			final String documentNo = documentSequenceDAO.retrieveDocumentNoSys(docSequence_ID);
			return IPreliminaryDocumentNoBuilder.withPreliminaryMarkers(documentNo);
		}
		else
		{
			final DocumentSequenceInfo newDocumentSeqInfo = documentSequenceDAO.retriveDocumentSequenceInfo(docSequence_ID);
			final boolean isStartNewYear = newDocumentSeqInfo != null && newDocumentSeqInfo.isStartNewYear();
			if (isStartNewYear)
			{
				final String dateColumnName = newDocumentSeqInfo.getDateColumn();
				final Date date = getDocumentDate(dateColumnName);
				final String documentNo = documentSequenceDAO.retrieveDocumentNoByYear(docSequence_ID, date);
				return IPreliminaryDocumentNoBuilder.withPreliminaryMarkers(documentNo);
			}
			else
			{
				final String documentNo = documentSequenceDAO.retrieveDocumentNo(docSequence_ID);
				return IPreliminaryDocumentNoBuilder.withPreliminaryMarkers(documentNo);
			}
		}
	}

	private final void assertNotBuilt()
	{
		Check.assume(!_built.get(), "not already built");
	}

	private final int getOldSequence_ID()
	{
		if (_oldSequence_ID == null)
		{
			_oldSequence_ID = retrieveOldSequence_ID();
		}
		return _oldSequence_ID;
	}

	private final int retrieveOldSequence_ID()
	{
		final boolean isNewDocumentNo = isNewDocumentNo();
		if (isNewDocumentNo)
		{
			return -1;
		}

		final I_C_DocType oldDocType = getOldDocType();
		if (oldDocType == null)
		{
			return -1;
		}

		final DocTypeSequenceMap oldDocTypeSequenceMap = documentSequenceDAO.retrieveDocTypeSequenceMap(oldDocType);
		final int oldDocSequence_ID = oldDocTypeSequenceMap.getDocNoSequence_ID(getAD_Client_ID(), getAD_Org_ID());
		return oldDocSequence_ID;
	}

	private Properties getCtx()
	{
		return Env.getCtx();
	}

	private final int getAD_Client_ID()
	{
		if (_adClientId == null)
		{
			_adClientId = extractAD_Client_ID();
		}
		return _adClientId;
	}

	private final int extractAD_Client_ID()
	{
		final Object documentModel = getDocumentModel();
		if (documentModel != null)
		{
			final Integer adClientId = InterfaceWrapperHelper.getValueOrNull(documentModel, "AD_Client_ID");
			if (adClientId != null)
			{
				return adClientId;
			}
		}

		// shall not happen
		throw new DocumentNoBuilderException("Could not get AD_Client_ID");
	}

	private final int getAD_Org_ID()
	{
		if (_adOrgId == null)
		{
			_adOrgId = extractAD_Org_ID();
		}
		return _adOrgId;
	}

	private final int extractAD_Org_ID()
	{
		final Object documentModel = getDocumentModel();
		if (documentModel != null)
		{
			final Integer adOrgId = InterfaceWrapperHelper.getValueOrNull(documentModel, "AD_Org_ID");
			if (adOrgId != null)
			{
				return adOrgId;
			}
		}

		// shall not happen
		throw new DocumentNoBuilderException("Could not get AD_Org_ID");
	}

	@Override
	public IPreliminaryDocumentNoBuilder setNewDocType(final I_C_DocType newDocType)
	{
		assertNotBuilt();
		_newDocType = newDocType;
		return this;
	}

	private final I_C_DocType getNewDocType()
	{
		return _newDocType;
	}

	@Override
	public IPreliminaryDocumentNoBuilder setOldDocType_ID(final int oldDocType_ID)
	{
		assertNotBuilt();
		_oldDocType_ID = oldDocType_ID > 0 ? oldDocType_ID : -1;
		return this;
	}

	private final I_C_DocType getOldDocType()
	{
		if (_oldDocType == null)
		{
			final int oldDocTypeId = _oldDocType_ID;
			if (oldDocTypeId > 0)
			{
				_oldDocType = InterfaceWrapperHelper.create(getCtx(), oldDocTypeId, I_C_DocType.class, ITrx.TRXNAME_None);
			}
		}
		return _oldDocType;
	}

	@Override
	public IPreliminaryDocumentNoBuilder setOldDocumentNo(final String oldDocumentNo)
	{
		assertNotBuilt();
		_oldDocumentNo = oldDocumentNo;
		return this;
	}

	private final String getOldDocumentNo()
	{
		return _oldDocumentNo;
	}

	private final boolean isNewDocumentNo()
	{
		final String oldDocumentNo = getOldDocumentNo();
		if (oldDocumentNo == null)
		{
			return true;
		}
		if (IPreliminaryDocumentNoBuilder.hasPreliminaryMarkers(oldDocumentNo))
		{
			return true;
		}

		return false;
	}

	@Override
	public IPreliminaryDocumentNoBuilder setDocumentModel(final Object documentModel)
	{
		_documentModel = documentModel;
		return this;
	}

	private final Object getDocumentModel()
	{
		Check.assumeNotNull(_documentModel, "_documentModel not null");
		return _documentModel;
	}

	private java.util.Date getDocumentDate(final String dateColumnName)
	{
		final Object documentModel = getDocumentModel();
		final Optional<java.util.Date> date = InterfaceWrapperHelper.getValue(documentModel, dateColumnName);
		return date.orNull();
	}
}
