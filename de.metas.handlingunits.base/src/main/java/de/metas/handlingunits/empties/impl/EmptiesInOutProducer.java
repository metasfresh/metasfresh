package de.metas.handlingunits.empties.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.IContextAware;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.handlingunits.IPackingMaterialDocumentLineSource;
import de.metas.handlingunits.empties.EmptiesInOutLinesProducer;
import de.metas.handlingunits.impl.PlainPackingMaterialDocumentLineSource;
import de.metas.handlingunits.inout.IReturnsInOutProducer;
import de.metas.handlingunits.inout.impl.AbstractReturnsInOutProducer;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.logging.LogManager;

public class EmptiesInOutProducer extends AbstractReturnsInOutProducer
{

	// Services

	private static final transient Logger logger = LogManager.getLogger(EmptiesInOutProducer.class);

	private final List<IPackingMaterialDocumentLineSource> sources = new ArrayList<>();

	private final EmptiesInOutLinesProducer inoutLinesBuilder = EmptiesInOutLinesProducer.newInstance(inoutRef);


	public EmptiesInOutProducer(final Properties ctx)
	{
		super(ctx);
	}

	
	@Override
	protected IContextAware getContextProvider()
	{
		final Properties ctx = getCtx();

		final String trxName = trxManager.getThreadInheritedTrxName();
		trxManager.assertTrxNameNotNull(trxName);

		return PlainContextAware.newWithTrxName(ctx, trxName);
	}

	@Override
	public final boolean isEmpty()
	{
		return sources.isEmpty();
	}

	@Override
	public IReturnsInOutProducer addPackingMaterial(final I_M_HU_PackingMaterial packingMaterial, final int qty)
	{
		Check.assume(!executed, "inout shall not be generated yet");

		sources.add(PlainPackingMaterialDocumentLineSource.of(packingMaterial, qty));

		return this;
	}

	/**
	 * Asserts this producer is in configuration stage (nothing produced yet)
	 */
	private final void assertConfigurable()
	{
		Check.assume(!executed, "producer shall not be executed");
		Check.assume(!inoutRef.isInitialized(), "inout not created yet");
	}

	@Override
	public IReturnsInOutProducer setC_BPartner(final I_C_BPartner bpartner)
	{
		assertConfigurable();
		_bpartner = bpartner;
		return this;
	}

	@Override
	public IReturnsInOutProducer setC_BPartner_Location(final I_C_BPartner_Location bpLocation)
	{
		assertConfigurable();
		Check.assumeNotNull(bpLocation, "bpLocation not null");
		_bpartnerLocationId = bpLocation.getC_BPartner_Location_ID();
		return this;
	}

	@Override
	public IReturnsInOutProducer setMovementType(final String movementType)
	{
		assertConfigurable();

		_movementType = movementType;
		return this;
	}

	@Override
	public IReturnsInOutProducer setM_Warehouse(final I_M_Warehouse warehouse)
	{
		assertConfigurable();

		_warehouse = warehouse;
		return this;
	}

	private static I_C_DocType getEmptiesDocType(final String docBaseType, final int adClientId, final int adOrgId, final boolean isSOTrx)
	{
		//
		// Search for specific empties shipment/receipt document sub-type (task 07694)
		// 07694: using the empties-subtype for receipts.
		final String docSubType = isSOTrx ? X_C_DocType.DOCSUBTYPE_Leergutanlieferung : X_C_DocType.DOCSUBTYPE_Leergutausgabe;

		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
		final List<I_C_DocType> docTypes = docTypeDAO.retrieveDocTypesByBaseType(DocTypeQuery.builder()
				.docBaseType(docBaseType)
				.docSubType(DocTypeQuery.DOCSUBTYPE_Any)
				.adClientId(adClientId)
				.adOrgId(adOrgId)
				.build());
		if (docTypes == null)
		{
			logger.warn("No document types found for docBaseType={}, adClientId={}, adOrgId={}", docBaseType, adClientId, adOrgId);
			return null;
		}

		for (final I_C_DocType docType : docTypes)
		{
			final String subType = docType.getDocSubType();
			if (docSubType.equals(subType))
			{
				return docType;
			}
		}

		//
		// If the empties doc type was not found (should not happen) fallback to the default one
		{
			final I_C_DocType defaultDocType = docTypes.get(0);
			logger.warn("No empties document type found for docBaseType={}, docSubType={}, adClientId={}, adOrgId={}. Using fallback docType={}", docBaseType, docSubType, adClientId, adOrgId, defaultDocType);
			return defaultDocType;
		}
	}

	@Override
	protected void createLines()
	{

		inoutLinesBuilder.addSources(sources);

		inoutLinesBuilder.create();

	}

	@Override
	protected int getReturnsDocTypeId(final String docBaseType, final boolean isSOTrx, final int adClientId, final int adOrgId)
	{
		final I_C_DocType docType = getEmptiesDocType(docBaseType, adClientId, adOrgId, isSOTrx);

		int docTypeId = docType == null ? -1 : docType.getC_DocType_ID();

		// If the empties doc type was not found (should not happen) fallback to the default one
		if (docTypeId <= 0)
		{
			final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
			docTypeId = docTypeDAO.getDocTypeId(Env.getCtx(), docBaseType, adClientId, adOrgId, ITrx.TRXNAME_None);
		}

		return docTypeId;
	}
}
