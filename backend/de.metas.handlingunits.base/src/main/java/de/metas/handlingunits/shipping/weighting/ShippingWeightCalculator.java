package de.metas.handlingunits.shipping.weighting;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.weightable.IWeightable;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.inout.IInOutBL;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@Builder
public class ShippingWeightCalculator
{
	//
	// Services
	@NonNull private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	//
	// Params
	@NonNull private final ShippingWeightSourceTypes weightSourceTypes;

	//
	// State
	@NonNull private final HashMap<ProductId, Optional<Quantity>> productWeights = new HashMap<>();

	public Optional<Quantity> calculateWeightInKilograms(@NonNull final I_M_InOut inOut)
	{
		return inOutBL.getLines(inOut)
				.stream()
				.map(line -> calculateWeightInKilograms(line).orElse(null))
				.filter(Objects::nonNull)
				.reduce(Quantity::add);
	}

	private static Optional<ProductId> extractProductId(final I_M_InOutLine line) {return ProductId.optionalOfRepoId(line.getM_Product_ID());}

	private Optional<Quantity> calculateWeightInKilograms(I_M_InOutLine line)
	{
		final ProductId productId = extractProductId(line).orElse(null);
		if (productId == null)
		{
			// skip comment lines (i.e. lines without a product)
			return Optional.empty();
		}

		return weightSourceTypes.calculateWeight(weightSourceType -> calculateWeight(line, weightSourceType))
				.map(weight -> toKilograms(weight, productId));
	}

	private Optional<Quantity> calculateWeight(final I_M_InOutLine line, final ShippingWeightSourceType weightSourceType)
	{
		switch (weightSourceType)
		{
			case CatchWeight:
				return calculateWeight_usingCatchWeight(line);
			case ProductWeight:
				return calculateWeight_usingNominalWeight(line);
			case HUWeightGross:
				return Optional.empty();
			default:
				throw new AdempiereException("Unknown weight source type: " + weightSourceType);
		}
	}

	private Optional<Quantity> calculateWeight_usingCatchWeight(final I_M_InOutLine line)
	{
		final UomId catchWeightUomId = UomId.ofRepoIdOrNull(line.getCatch_UOM_ID());
		if (catchWeightUomId == null)
		{
			return Optional.empty();
		}

		return Optional.of(Quantitys.of(line.getQtyDeliveredCatch(), catchWeightUomId));
	}

	private Quantity toKilograms(final Quantity weight, final ProductId productId)
	{
		return uomConversionBL.convertToKilogram(weight, productId);
	}

	private Optional<Quantity> calculateWeight_usingNominalWeight(final I_M_InOutLine line)
	{
		final ProductId productId = extractProductId(line).orElse(null);
		if (productId == null)
		{
			// skip comment lines (i.e. lines without a product)
			return Optional.empty();
		}

		final Quantity productWeight = getProductWeight(productId).orElse(null);
		if (productWeight == null)
		{
			return Optional.empty();
		}

		final Quantity qty = inOutBL.getMovementQty(line);
		final Quantity lineWeight = productWeight.multiply(qty.toBigDecimal());
		return Optional.of(lineWeight);
	}

	private Optional<Quantity> getProductWeight(@NonNull final ProductId productId)
	{
		return productWeights.computeIfAbsent(productId, productBL::getWeight);
	}

	public Optional<Quantity> calculateWeightInKg(@NonNull final I_M_HU hu)
	{
		return weightSourceTypes.calculateWeight(weightSourceType -> calculateWeightInKg(hu, weightSourceType));
	}

	private Optional<Quantity> calculateWeightInKg(@NonNull final I_M_HU hu, @NonNull final ShippingWeightSourceType weightSourceType)
	{
		switch (weightSourceType)
		{
			case CatchWeight:
				return Optional.empty();
			case ProductWeight:
				return calculateWeight_usingNominalWeight(hu);
			case HUWeightGross:
				return calculateWeight_usingWeightGrossAttribute(hu);
			default:
				throw new AdempiereException("Unknown weight source type: " + weightSourceType);
		}
	}

	private Optional<Quantity> calculateWeight_usingNominalWeight(final I_M_HU hu)
	{
		Quantity result = null;
		for (final IHUProductStorage huProductStorage : handlingUnitsBL.getStorageFactory().getStorage(hu).getProductStorages())
		{
			final Quantity huProductStorageWeight = calculateWeight_usingNominalWeight(huProductStorage).orElse(null);
			if (huProductStorageWeight == null)
			{
				return Optional.empty();
			}

			result = Quantity.addNullables(result, huProductStorageWeight);
		}

		return Optional.ofNullable(result);
	}

	private Optional<Quantity> calculateWeight_usingNominalWeight(final IHUProductStorage huProductStorage)
	{
		final ProductId productId = huProductStorage.getProductId();
		final Quantity productWeight = getProductWeight(productId).orElse(null);
		if (productWeight == null)
		{
			return Optional.empty();
		}

		final Quantity qty = uomConversionBL.convertToProductUOM(huProductStorage.getQty(), productId);
		final Quantity weight = productWeight.multiply(qty.toBigDecimal()).roundToUOMPrecision();
		return Optional.of(weight);
	}

	private Optional<Quantity> calculateWeight_usingWeightGrossAttribute(final I_M_HU hu)
	{
		final IMutableHUContext huContext = handlingUnitsBL.createMutableHUContext();
		final IAttributeStorage huAttributes = huContext.getHUAttributeStorageFactory().getAttributeStorage(hu);
		final IWeightable weightable = Weightables.wrap(huAttributes);
		if (!weightable.hasWeightGross())
		{
			return Optional.empty();
		}

		final Quantity weightGross = weightable.getWeightGrossAsQuantity();
		return weightGross.signum() > 0 ? Optional.of(weightGross) : Optional.empty();
	}

}
