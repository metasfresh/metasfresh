package de.metas.handlingunits.shipping;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.shipping.impl.HUShipperTransportationBL;
import de.metas.handlingunits.shipping.weighting.ShippingWeightCalculator;
import de.metas.handlingunits.shipping.weighting.ShippingWeightSourceTypes;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.service.ISysConfigBL;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
public class PackedHUShippingInfoService
{
	private static final AttributeCode ATTR_COUNTRY_OF_ORIGIN = HUAttributeConstants.ATTR_CountryOfOrigin;

	@NonNull
	public PackedHUShippingInfo of(@NonNull final I_M_HU hu)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		return PackedHUShippingInfo.builder()
				.weightInKg(calculateWeightInKg(hu))
				.dimensions(Services.get(IHUPackageBL.class).getPackageDimensions(hu))
				.topLevelType(deriveTopLevelType(hu, handlingUnitsBL))
				.countryOfOrigin(readCountryOfOrigin(hu, handlingUnitsBL))
				.build();
	}

	@Nullable
	private Quantity calculateWeightInKg(@NonNull final I_M_HU hu)
	{
		return newWeightCalculator().calculateWeightInKg(hu).orElse(null);
	}

	private ShippingWeightCalculator newWeightCalculator()
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final ShippingWeightSourceTypes weightSourceTypes = ShippingWeightSourceTypes
				.ofCommaSeparatedString(sysConfigBL.getValue(HUShipperTransportationBL.SYSCONFIG_WeightSourceTypes))
				.orElse(ShippingWeightSourceTypes.DEFAULT);

		return ShippingWeightCalculator.builder()
				.weightSourceTypes(weightSourceTypes)
				.build();
	}

	@NonNull
	private String deriveTopLevelType(@NonNull final I_M_HU hu, @NonNull final IHandlingUnitsBL handlingUnitsBL)
	{
		final String huUnitType = handlingUnitsBL.getHU_UnitType(hu);

		if (X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit.equals(huUnitType))
		{
			return "LU";
		}
		else if (X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit.equals(huUnitType))
		{
			return "TU";
		}
		else
		{
			// Virtual HU (HU_UnitType='V') or self-packed product with no packing material
			return "CU";
		}
	}

	@Nullable
	private String readCountryOfOrigin(@NonNull final I_M_HU hu, @NonNull final IHandlingUnitsBL handlingUnitsBL)
	{
		final IAttributeStorage huAttributes = handlingUnitsBL.getAttributeStorage(hu);

		if (!huAttributes.hasAttribute(ATTR_COUNTRY_OF_ORIGIN))
		{
			return null;
		}
		return huAttributes.getValueAsString(ATTR_COUNTRY_OF_ORIGIN);
	}
}
