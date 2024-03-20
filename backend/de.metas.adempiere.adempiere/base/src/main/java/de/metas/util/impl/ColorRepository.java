package de.metas.util.impl;

import com.google.common.collect.ImmutableMap;
import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.util.ColorId;
import de.metas.util.IColorRepository;
import de.metas.util.MFColor;
import de.metas.util.MFColorType;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_Color;
import org.compiere.model.MImage;
import org.compiere.model.X_AD_Color;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.awt.*;
import java.math.BigDecimal;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class ColorRepository implements IColorRepository
{
	private static final Logger logger = LogManager.getLogger(ColorRepository.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private static final CCache<Integer, ColorsMap> cache = CCache.<Integer, ColorsMap>builder()
			.tableName(I_AD_Color.Table_Name)
			.initialCapacity(1)
			.build();

	@Override
	public MFColor getColorById(@NonNull final ColorId adColorId)
	{
		return getColorsMap().getMFColorById(adColorId);
	}

	@Override
	public ColorId saveFlatColorAndReturnId(@NonNull final String flatColorHexString)
	{
		final Color flatColor = Color.decode(flatColorHexString);

		return getColorsMap()
				.getColorIdByFlatColor(flatColor)
				.orElseGet(() -> createFlatColor(flatColor));
	}

	@NonNull
	private static ColorId createFlatColor(@NonNull final Color flatColor)
	{
		final I_AD_Color newColorRecord = InterfaceWrapperHelper.newInstanceOutOfTrx(I_AD_Color.class);
		newColorRecord.setAD_Org_ID(Env.CTXVALUE_AD_Org_ID_Any);
		newColorRecord.setName(toHexString(flatColor));
		newColorRecord.setColorType(X_AD_Color.COLORTYPE_NormalFlat);
		newColorRecord.setRed(flatColor.getRed());
		newColorRecord.setGreen(flatColor.getGreen());
		newColorRecord.setBlue(flatColor.getBlue());
		newColorRecord.setAlpha(0);
		newColorRecord.setImageAlpha(BigDecimal.ZERO);
		InterfaceWrapperHelper.save(newColorRecord);

		return ColorId.ofRepoId(newColorRecord.getAD_Color_ID());
	}

	private static String toHexString(@NonNull final Color color)
	{
		return "#" + Integer.toHexString(color.getRGB()).substring(2).toLowerCase();
	}

	@Override
	public ColorId getColorIdByName(final String colorName)
	{
		return getColorsMap().getColorIdByName(colorName);
	}

	private ColorsMap getColorsMap() {return cache.getOrLoad(0, this::retrieveColorsMap);}

	private ColorsMap retrieveColorsMap()
	{
		final List<I_AD_Color> records = queryBL.createQueryBuilderOutOfTrx(I_AD_Color.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_AD_Color.COLUMNNAME_AD_Client_ID, ClientId.METASFRESH, ClientId.SYSTEM)
				.orderByDescending(I_AD_Color.COLUMNNAME_AD_Client_ID)
				.orderBy(I_AD_Color.COLUMNNAME_AD_Color_ID)
				.create()
				.list();

		return new ColorsMap(records);
	}

	@Nullable
	private static MFColor toMFColor(@NonNull final I_AD_Color colorRecord)
	{
		// Color Type
		final String colorTypeCode = colorRecord.getColorType();
		if (colorTypeCode == null)
		{
			logger.warn("No ColorType: {}", colorRecord);
			return null;
		}

		final MFColorType colorType = MFColorType.ofCode(colorTypeCode);
		if (colorType == MFColorType.FLAT)
		{
			return MFColor.ofFlatColor(extractPrimaryAWTColor(colorRecord));
		}
		else if (colorType == MFColorType.GRADIENT)
		{
			final int repeatDistance = colorRecord.getRepeatDistance();
			final String startPointStr = colorRecord.getStartPoint();
			final int startPoint = startPointStr == null ? MFColor.DEFAULT_GradientStartPoint : Integer.parseInt(startPointStr);
			return MFColor.ofGradientColor(extractPrimaryAWTColor(colorRecord), extractSecondaryAWTColor(colorRecord), startPoint, repeatDistance);
		}
		else if (colorType == MFColorType.LINES)
		{
			final int lineWidth = colorRecord.getLineWidth();
			final int lineDistance = colorRecord.getLineDistance();
			return MFColor.ofLinesColor(extractSecondaryAWTColor(colorRecord), extractPrimaryAWTColor(colorRecord), lineWidth, lineDistance);
		}
		else if (colorType == MFColorType.TEXTURE)
		{
			final int adImageId = colorRecord.getAD_Image_ID();
			final URL url = MImage.getURLOrNull(adImageId);
			if (url == null)
			{
				return null;
			}
			final BigDecimal imageAlphaBD = colorRecord.getImageAlpha();
			final float compositeAlpha = imageAlphaBD == null ? MFColor.DEFAULT_TextureCompositeAlpha : imageAlphaBD.floatValue();
			return MFColor.ofTextureColor(url, extractPrimaryAWTColor(colorRecord), compositeAlpha);
		}
		else
		{
			return null;
		}
	}

	private static Color extractPrimaryAWTColor(@NonNull final I_AD_Color colorRecord)
	{
		return new Color(colorRecord.getRed(), colorRecord.getGreen(), colorRecord.getBlue());
	}

	private static Color extractSecondaryAWTColor(@NonNull final I_AD_Color colorRecord)
	{
		return new Color(colorRecord.getRed(), colorRecord.getGreen(), colorRecord.getBlue());
	}

	//
	//
	//

	private static class ColorsMap
	{
		private final ImmutableMap<ColorId, MFColor> colorsById;
		private final ImmutableMap<String, ColorId> idsByName;
		private final ImmutableMap<Color, ColorId> idsByFlatColor;

		private ColorsMap(final List<I_AD_Color> records)
		{
			this.colorsById = records.stream().collect(ImmutableMap.toImmutableMap(
					record -> ColorId.ofRepoId(record.getAD_Color_ID()),
					ColorRepository::toMFColor
			));

			this.idsByName = records.stream().collect(ImmutableMap.toImmutableMap(
					I_AD_Color::getName,
					record -> ColorId.ofRepoId(record.getAD_Color_ID())
			));

			final HashMap<Color, ColorId> idsByFlatColor = new HashMap<>();
			colorsById.forEach((colorId, mfColor) -> {
				if (mfColor.isFlat() && mfColor.getFlatColor() != null)
				{
					idsByFlatColor.computeIfAbsent(mfColor.getFlatColor(), k -> colorId);
				}
			});
			this.idsByFlatColor = ImmutableMap.copyOf(idsByFlatColor);
		}

		public MFColor getMFColorById(@NonNull final ColorId colorId)
		{
			final MFColor mfColor = colorsById.get(colorId);
			if (mfColor == null)
			{
				throw new AdempiereException("No color found for " + colorId);
			}
			return mfColor;
		}

		public ColorId getColorIdByName(final String name)
		{
			final ColorId colorId = idsByName.get(name);
			if (colorId == null)
			{
				throw new AdempiereException("No color found for `" + name + "`");
			}
			return colorId;
		}

		public Optional<ColorId> getColorIdByFlatColor(final Color flatColor)
		{
			return Optional.ofNullable(idsByFlatColor.get(flatColor));
		}
	}
}
