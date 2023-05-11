package de.metas.deliveryplanning;

import de.metas.util.ColorId;
import de.metas.util.IColorRepository;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

@Service
public class DeliveryStatusColorPaletteService
{
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IColorRepository colorRepository = Services.get(IColorRepository.class);

	private static final String SYSCONFIG_Delivered_ColorName = "M_DeliveryPlanning.DeliveredColorName";
	private static final String SYSCONFIG_NotDelivered_ColorName = "M_DeliveryPlanning.NotDeliveredColorName";

	public DeliveryStatusColorPalette get()
	{
		return DeliveryStatusColorPalette.builder()
				.deliveredColorId(getColorIdFromSysconfig(SYSCONFIG_Delivered_ColorName, "Gruen"))
				.notDeliveredColorId(getColorIdFromSysconfig(SYSCONFIG_NotDelivered_ColorName, "Rot"))
				.build();
	}

	@Nullable
	private ColorId getColorIdFromSysconfig(
			@NonNull final String sysconfigName,
			@NonNull final String defaultColorName)
	{
		String colorName = StringUtils.trimBlankToOptional(sysConfigBL.getValue(sysconfigName))
				.orElse(defaultColorName);
		if ("-".equals(colorName))
		{
			return null;
		}

		return colorRepository.getColorIdByName(colorName);
	}
}
