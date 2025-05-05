package de.metas.inoutcandidate.expectations;

import de.metas.document.dimension.Dimension;
import de.metas.document.dimension.InOutLineDimensionFactory;
import de.metas.document.engine.IDocument;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.impl.AttributeInstanceExpectation;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.test.AbstractExpectation;
import org.adempiere.util.test.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Product;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

public class InOutLineExpectation<ParentExpectationType> extends AbstractExpectation<ParentExpectationType>
{
	private String asiDescription = null;
	private boolean asiDescriptionSet = false;
	private StockQtyAndUOMQty qtys = null; // uomQty - if not null - is the catch-qty!
	private UomId uomId;

	private BigDecimal qualityDiscountPercent;
	private boolean qualityDiscountPercentSet;
	private String qualityNote;
	private boolean qualityNoteSet;
	private Boolean inDispute;
	private Boolean packagingmaterial;
	private final List<AttributeInstanceExpectation<InOutLineExpectation<ParentExpectationType>>> attributeInstanceExpectations = new ArrayList<>();
	private Dimension dimension;

	public InOutLineExpectation(final ParentExpectationType parent, final IContextAware context)
	{
		super(parent);
		setContext(context);
	}

	@OverridingMethodsMustInvokeSuper
	public InOutLineExpectation<ParentExpectationType> assertExpected(final I_M_InOutLine inoutLine)
	{
		ErrorMessage message = newErrorMessage()
				.addContextInfo(inoutLine);

		final I_M_Product expectedProduct = loadOutOfTrx(qtys.getProductId(), I_M_Product.class);
		final I_M_Product actualProduct = loadOutOfTrx(inoutLine.getM_Product_ID(), I_M_Product.class);
		assertModelEquals(message.expect("Invalid product"), expectedProduct, actualProduct);

		if (asiDescriptionSet)
		{
			final String expectedASIDescription = getASIDescription();
			final I_M_AttributeSetInstance asi = inoutLine.getM_AttributeSetInstance();
			final String actualASIDescription;
			if (asi != null)
			{
				actualASIDescription = asi.getDescription();
			}
			else
			{
				actualASIDescription = null;
			}
			assertEquals(message.expect("ASI"), expectedASIDescription, actualASIDescription);
		}
		if (!attributeInstanceExpectations.isEmpty())
		{
			final I_M_AttributeSetInstance asi = inoutLine.getM_AttributeSetInstance();
			for (final AttributeInstanceExpectation<InOutLineExpectation<ParentExpectationType>> attributeExpectation : attributeInstanceExpectations)
			{
				attributeExpectation.assertExpected(message, asi);
			}
		}

		final BigDecimal expectedMovementQty = qtys.getStockQty().toBigDecimal();
		final BigDecimal actualMovementQty = inoutLine.getMovementQty();
		assertEquals(message.expect("movement qty"), expectedMovementQty, actualMovementQty);

		assertEquals(message.expect("QtyEntered"), qtys.getUOMQtyNotNull().toBigDecimal(), inoutLine.getQtyEntered());

		if (qualityDiscountPercentSet)
		{
			assertEquals(message.expect("qualityDiscountPercent"), qualityDiscountPercent, inoutLine.getQualityDiscountPercent());
		}

		if (qualityNoteSet)
		{
			final String qualityNoteActual = inoutLine.getQualityNote();
			if (qualityNote == null)
			{
				assertEquals(message.expect("QualityNote shall be empty"), true, Check.isEmpty(qualityNoteActual, false));
			}
			else
			{
				assertEquals(message.expect("QualityNote"), qualityNote, qualityNoteActual);
			}
		}

		if (inDispute != null)
		{
			assertEquals(message.expect("InDispute"), inDispute, inoutLine.isInDispute());
		}
		if (packagingmaterial != null)
		{
			assertEquals(message.expect("PackagingMaterial"), packagingmaterial, inoutLine.isPackagingMaterial());
		}

		if (dimension != null)
		{
			Assertions.assertThat(new InOutLineDimensionFactory().getFromRecord(inoutLine))
					.as(message.expect("Dimension").toString())
					.usingRecursiveComparison()
					.isEqualTo(dimension);
		}

		return this;
	}

	public <InOutLineType extends I_M_InOutLine> InOutLineType createInOutLine(final Class<InOutLineType> inoutLineClass)
	{
		// gh 1566: create a completed and active inout, otherwise the line won't be counted properly
		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class, getContext());
		inout.setIsActive(true);
		inout.setDocStatus(IDocument.STATUS_Completed);
		InterfaceWrapperHelper.save(inout);

		final InOutLineType inoutLine = InterfaceWrapperHelper.newInstance(inoutLineClass, getContext());
		inoutLine.setM_InOut(inout);
		populateModel(inoutLine);
		InterfaceWrapperHelper.save(inoutLine);
		return inoutLine;
	}

	/**
	 * Populate model which values from this expectation
	 */
	@OverridingMethodsMustInvokeSuper
	protected void populateModel(final I_M_InOutLine inoutLine)
	{
		inoutLine.setM_Product_ID(qtys.getProductId().getRepoId());
		inoutLine.setMovementQty(qtys.getStockQty().toBigDecimal());

		final Quantity qtyEntered = Services.get(IUOMConversionBL.class).convertQuantityTo(
				qtys.getStockQty(),
				UOMConversionContext.of(qtys.getProductId()),
				uomId);
		inoutLine.setQtyEntered(qtyEntered.toBigDecimal());
		inoutLine.setC_UOM_ID(uomId.getRepoId());

		if (qtys.getUOMQtyOpt().isPresent())
		{
			inoutLine.setQtyDeliveredCatch(qtys.getUOMQtyNotNull().toBigDecimal());
			inoutLine.setCatch_UOM_ID(qtys.getUOMQtyNotNull().getUomId().getRepoId());
		}

		if (inDispute != null)
		{
			inoutLine.setIsInDispute(inDispute);
		}
		inoutLine.setQualityDiscountPercent(qualityDiscountPercent);
		inoutLine.setQualityNote(qualityNote);

		if (dimension != null)
		{
			new InOutLineDimensionFactory().updateRecord(inoutLine, dimension);
		}
	}

	public ProductId getProductId()
	{
		return qtys.getProductId();
	}

	public final InOutLineExpectation<ParentExpectationType> asiDescription(final String asiDescription)
	{
		this.asiDescription = asiDescription;
		this.asiDescriptionSet = true;
		return this;
	}

	public InOutLineExpectation<ParentExpectationType> noASIDescription()
	{
		return asiDescription(null);
	}

	public String getASIDescription()
	{
		return asiDescription;
	}

	public InOutLineExpectation<ParentExpectationType> stockQtyAndMaybeCatchQty(@NonNull final StockQtyAndUOMQty qtys)
	{
		this.qtys = qtys;
		return this;
	}

	public InOutLineExpectation<ParentExpectationType> uomId(@NonNull final UomId uomId)
	{
		this.uomId = uomId;
		return this;
	}

	public InOutLineExpectation<ParentExpectationType> qualityDiscountPercent(final BigDecimal qualityDiscountPercent)
	{
		this.qualityDiscountPercent = qualityDiscountPercent;
		this.qualityDiscountPercentSet = true;
		return this;
	}

	public InOutLineExpectation<ParentExpectationType> qualityDiscountPercent(final String qualityDiscountPercent)
	{
		return qualityDiscountPercent(new BigDecimal(qualityDiscountPercent));
	}

	public InOutLineExpectation<ParentExpectationType> noQualityDiscountPercent()
	{
		return qualityDiscountPercent((BigDecimal)null);
	}

	public InOutLineExpectation<ParentExpectationType> qualityNote(final String qualityNote)
	{
		this.qualityNote = qualityNote;
		this.qualityNoteSet = true;
		return this;
	}

	public InOutLineExpectation<ParentExpectationType> noQualityNote()
	{
		return qualityNote(null);
	}

	public InOutLineExpectation<ParentExpectationType> inDispute(final boolean inDispute)
	{
		this.inDispute = inDispute;
		return this;
	}

	public InOutLineExpectation<ParentExpectationType> packagingmaterialLine(boolean packagingmaterial)
	{
		this.packagingmaterial = packagingmaterial;
		return this;
	}

	public AttributeInstanceExpectation<InOutLineExpectation<ParentExpectationType>> newAttributeExpectation()
	{
		final AttributeInstanceExpectation<InOutLineExpectation<ParentExpectationType>> expectation = new AttributeInstanceExpectation<>(this);
		attributeInstanceExpectations.add(expectation);
		return expectation;
	}

	public InOutLineExpectation<ParentExpectationType> attribute(final I_M_Attribute attribute, final String valueString)
	{
		newAttributeExpectation().attribute(attribute).valueString(valueString);
		return this;
	}

	public InOutLineExpectation<ParentExpectationType> attribute(final I_M_Attribute attribute, final BigDecimal valueNumber)
	{
		newAttributeExpectation().attribute(attribute).valueNumber(valueNumber);
		return this;
	}

	public InOutLineExpectation<ParentExpectationType> attribute(final I_M_Attribute attribute, final Date valueDate)
	{
		newAttributeExpectation().attribute(attribute).valueDate(valueDate);
		return this;
	}

	public InOutLineExpectation<ParentExpectationType> attribute(final I_M_Attribute attribute, final LocalDate valueDate)
	{
		newAttributeExpectation().attribute(attribute).valueDate(valueDate);
		return this;
	}

	public InOutLineExpectation<ParentExpectationType> dimension(final Dimension dimension)
	{
		this.dimension = dimension;
		return this;
	}

}
