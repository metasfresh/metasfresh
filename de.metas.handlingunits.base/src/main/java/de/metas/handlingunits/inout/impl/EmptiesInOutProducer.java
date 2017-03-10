package de.metas.handlingunits.inout.impl;

import java.util.List;
import java.util.Properties;

import org.adempiere.model.IContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_InOut;
import org.compiere.model.X_C_DocType;

import de.metas.document.IDocTypeDAO;
import de.metas.handlingunits.inout.IReturnsInOutProducer;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;

/* package */class EmptiesInOutProducer extends AbstractReturnsInOutProducer
// implements IReturnsInOutProducer
{
	//
	// Services
	private final transient IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

//	private final Properties _ctx;
	private boolean executed = false;

	/** InOut header reference. It will be created just when it is needed. */

	private final EmptiesInOutLinesBuilder inoutLinesBuilder = EmptiesInOutLinesBuilder.newBuilder(inoutRef);

	public EmptiesInOutProducer(final Properties ctx)
	{
		super();

		Check.assumeNotNull(ctx, "ctx not null");
		setCtx(ctx);
	}

	@Override
	public final boolean isEmpty()
	{
		return inoutLinesBuilder.isEmpty();
	}

	public IReturnsInOutProducer addPackingMaterial(final I_M_HU_PackingMaterial packingMaterial, final int qty)
	{
		Check.assume(!executed, "inout shall not be generated yet");
		inoutLinesBuilder.addSource(packingMaterial, qty);
		return this;
	}

	private I_C_DocType getEmptiesDocType(final Properties ctx, final String docBaseType, final int adClientId, final int adOrgId, final boolean isSOTrx, final String trxName)
	{
		final List<I_C_DocType> docTypes = docTypeDAO.retrieveDocTypesByBaseType(ctx, docBaseType, adClientId, adOrgId, trxName);

		final String docSubType = isSOTrx ? X_C_DocType.DOCSUBTYPE_Leergutanlieferung : X_C_DocType.DOCSUBTYPE_Leergutausgabe;

		for (final I_C_DocType docType : docTypes)
		{
			final String subType = docType.getDocSubType();

			if (docSubType.equals(subType))
			{
				return docType;
			}
		}
		return null;
	}

	@Override
	protected void createLines()
	{
		inoutLinesBuilder.create();

	}

	@Override
	protected int getReturnsDocTypeId(
			final IContextAware contextProvider,
			final boolean isSOTrx,
			final I_M_InOut inout,
			final String docBaseType)
	{
		final Properties ctx = contextProvider.getCtx();
		final String trxName = contextProvider.getTrxName();

		final I_C_DocType docType = getEmptiesDocType(ctx,
				docBaseType,
				inout.getAD_Client_ID(),
				inout.getAD_Org_ID(),
				isSOTrx,
				trxName);

		int docTypeId = docType == null ? -1 : docType.getC_DocType_ID();

		// If the empties doc type was not found (should not happen) fallback to the default one
		if (docTypeId <= 0)
		{
			docTypeId = docTypeDAO.getDocTypeId(ctx,
					docBaseType,
					inout.getAD_Client_ID(),
					inout.getAD_Org_ID(),
					trxName);
		}

		return docTypeId;
	}
}
