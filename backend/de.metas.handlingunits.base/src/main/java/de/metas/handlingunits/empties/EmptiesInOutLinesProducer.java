package de.metas.handlingunits.empties;

import java.util.HashSet;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IReference;
import org.adempiere.util.lang.ImmutableReference;
import org.compiere.model.I_M_InOut;

import de.metas.handlingunits.IPackingMaterialDocumentLine;
import de.metas.handlingunits.IPackingMaterialDocumentLineSource;
import de.metas.handlingunits.impl.AbstractPackingMaterialDocumentLinesBuilder;
import de.metas.handlingunits.impl.PlainPackingMaterialDocumentLineSource;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.inout.IInOutBL;
import lombok.NonNull;

/**
 * Helper class used to create {@link I_M_InOutLine}s for a given inout header.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class EmptiesInOutLinesProducer extends AbstractPackingMaterialDocumentLinesBuilder
{
	public static EmptiesInOutLinesProducer newInstance(final IReference<I_M_InOut> inoutRef)
	{
		return new EmptiesInOutLinesProducer(inoutRef);
	}

	public static EmptiesInOutLinesProducer newInstance(@NonNull final I_M_InOut inout)
	{
		final IReference<I_M_InOut> inoutRef = ImmutableReference.valueOf(inout);
		return new EmptiesInOutLinesProducer(inoutRef);
	}

	//
	// Services
	private final transient IInOutBL inOutBL = Services.get(IInOutBL.class);

	private final IReference<I_M_InOut> _inoutRef;

	/** set of M_InOutLine_IDs which were created/updated by this processor */
	private final Set<Integer> affectedInOutLinesId = new HashSet<>();

	private EmptiesInOutLinesProducer(@NonNull final IReference<I_M_InOut> inoutRef)
	{
		_inoutRef = inoutRef;
	}

	@Override
	public EmptiesInOutLinesProducer create()
	{
		super.create();
		return this;
	}

	/** @return set of M_InOutLine_IDs which were created/updated by this processor */
	public Set<Integer> getAffectedInOutLinesId()
	{
		return affectedInOutLinesId;
	}

	private final I_M_InOut getM_InOut()
	{
		return _inoutRef.getValue();
	}

	public EmptiesInOutLinesProducer addSource(final I_M_HU_PackingMaterial packingMaterial, final int qty)
	{
		addSource(PlainPackingMaterialDocumentLineSource.of(packingMaterial, qty));
		return this;
	}

	@Override
	protected void assertValid(final IPackingMaterialDocumentLineSource source)
	{
		Check.assumeInstanceOf(source, PlainPackingMaterialDocumentLineSource.class, "source");
	}

	@Override
	protected IPackingMaterialDocumentLine createPackingMaterialDocumentLine(@NonNull final I_M_HU_PackingMaterial packingMaterial)
	{
		final I_M_InOut inout = getM_InOut();
		final I_M_InOutLine inoutLine = inOutBL.newInOutLine(inout, I_M_InOutLine.class);
		inoutLine.setM_Product_ID(packingMaterial.getM_Product_ID());
		// NOTE: don't save it

		return new EmptiesInOutLinePackingMaterialDocumentLine(inoutLine);
	}

	private final EmptiesInOutLinePackingMaterialDocumentLine toImpl(final IPackingMaterialDocumentLine pmLine)
	{
		return (EmptiesInOutLinePackingMaterialDocumentLine)pmLine;
	}

	@Override
	protected void removeDocumentLine(@NonNull final IPackingMaterialDocumentLine pmLine)
	{
		final EmptiesInOutLinePackingMaterialDocumentLine inoutLinePMLine = toImpl(pmLine);
		final I_M_InOutLine inoutLine = inoutLinePMLine.getM_InOutLine();
		if (!InterfaceWrapperHelper.isNew(inoutLine))
		{
			InterfaceWrapperHelper.delete(inoutLine);
		}
	}

	@Override
	protected void createDocumentLine(@NonNull final IPackingMaterialDocumentLine pmLine)
	{
		final EmptiesInOutLinePackingMaterialDocumentLine inoutLinePMLine = toImpl(pmLine);

		final I_M_InOut inout = getM_InOut();
		InterfaceWrapperHelper.save(inout); // make sure inout header is saved

		final I_M_InOutLine inoutLine = inoutLinePMLine.getM_InOutLine();
		inoutLine.setM_InOut(inout); // make sure inout line is linked to our M_InOut_ID (in case it was just saved)
		inoutLine.setIsActive(true); // just to be sure
		// task FRESH-273
		inoutLine.setIsPackagingMaterial(true);

		final boolean wasNew = InterfaceWrapperHelper.isNew(inoutLine);
		InterfaceWrapperHelper.save(inoutLine);

		if (wasNew)
		{
			affectedInOutLinesId.add(inoutLine.getM_InOutLine_ID());
		}
	}

	@Override
	protected void linkSourceToDocumentLine(final IPackingMaterialDocumentLineSource source, final IPackingMaterialDocumentLine pmLine)
	{
		// nothing
	}

}
