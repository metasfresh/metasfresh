package de.metas.util.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.awt.Color;
import java.math.BigDecimal;
import java.net.URL;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Color;
import org.compiere.model.MImage;
import org.compiere.model.X_AD_Color;
import org.compiere.util.CCache;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.util.IColorRepository;
import de.metas.util.MFColor;
import de.metas.util.MFColorType;
import lombok.NonNull;

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

	private static final CCache<Integer, MFColor> colorValuesById = CCache.<Integer, MFColor> newCache(I_AD_Color.Table_Name, 20, CCache.EXPIREMINUTES_Never);
	private static final CCache<String, Integer> colorIdByName = CCache.<String, Integer> newCache(I_AD_Color.Table_Name + "#by#Name", 10, CCache.EXPIREMINUTES_Never);

	@Override
	public MFColor getColorById(final int adColorId)
	{
		if (adColorId <= 0)
		{
			return null;
		}

		return colorValuesById.getOrLoad(adColorId, () -> createMFColorById(adColorId));
	}

	private static MFColor createMFColorById(final int adColorId)
	{
		final I_AD_Color colorRecord = loadOutOfTrx(adColorId, I_AD_Color.class);
		if (colorRecord == null)
		{
			logger.warn("No color found for ID={}. Returning null.", adColorId);
			return null;
		}

		// Color Type
		final String colorTypeCode = colorRecord.getColorType();
		if (colorTypeCode == null)
		{
			logger.warn("No ColorType: {}", colorRecord);
			return null;
		}

		final MFColor color;
		final MFColorType colorType = MFColorType.ofCode(colorTypeCode);
		if (colorType == MFColorType.FLAT)
		{
			color = MFColor.ofFlatColor(extractPrimaryAWTColor(colorRecord));
		}
		else if (colorType == MFColorType.GRADIENT)
		{
			final int repeatDistance = colorRecord.getRepeatDistance();
			final String startPointStr = colorRecord.getStartPoint();
			final int startPoint = startPointStr == null ? MFColor.DEFAULT_GradientStartPoint : Integer.parseInt(startPointStr);
			color = MFColor.ofGradientColor(extractPrimaryAWTColor(colorRecord), extractSecondaryAWTColor(colorRecord), startPoint, repeatDistance);
		}
		else if (colorType == MFColorType.LINES)
		{
			final int lineWidth = colorRecord.getLineWidth();
			final int lineDistance = colorRecord.getLineDistance();
			color = MFColor.ofLinesColor(extractSecondaryAWTColor(colorRecord), extractPrimaryAWTColor(colorRecord), lineWidth, lineDistance);
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
			color = MFColor.ofTextureColor(url, extractPrimaryAWTColor(colorRecord), compositeAlpha);
		}
		else
		{
			color = null;
		}
		return color;
	}

	private static Color extractPrimaryAWTColor(@NonNull final I_AD_Color colorRecord)
	{
		return new Color(colorRecord.getRed(), colorRecord.getGreen(), colorRecord.getBlue());
	}

	private static Color extractSecondaryAWTColor(@NonNull final I_AD_Color colorRecord)
	{
		return new Color(colorRecord.getRed(), colorRecord.getGreen(), colorRecord.getBlue());
	}

	@Override
	public int saveFlatColorAndReturnId(@NonNull String flatColorHexString)
	{
		final Color flatColor = MFColor.ofFlatColorHexString(flatColorHexString).getFlatColor();
		final I_AD_Color existingColorRecord = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_Color.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClientOrSystem()
				.addEqualsFilter(I_AD_Color.COLUMNNAME_ColorType, X_AD_Color.COLORTYPE_NormalFlat)
				.addEqualsFilter(I_AD_Color.COLUMNNAME_Red, flatColor.getRed())
				.addEqualsFilter(I_AD_Color.COLUMNNAME_Green, flatColor.getGreen())
				.addEqualsFilter(I_AD_Color.COLUMNNAME_Blue, flatColor.getBlue())
				.orderByDescending(I_AD_Color.COLUMNNAME_AD_Client_ID)
				.orderBy(I_AD_Color.COLUMNNAME_AD_Color_ID)
				.create()
				.first();
		if (existingColorRecord != null)
		{
			return existingColorRecord.getAD_Color_ID();
		}

		final I_AD_Color newColorRecord = InterfaceWrapperHelper.newInstanceOutOfTrx(I_AD_Color.class);
		newColorRecord.setAD_Org_ID(Env.CTXVALUE_AD_Org_ID_Any);
		newColorRecord.setName(flatColorHexString.toLowerCase());
		newColorRecord.setColorType(X_AD_Color.COLORTYPE_NormalFlat);
		newColorRecord.setRed(flatColor.getRed());
		newColorRecord.setGreen(flatColor.getGreen());
		newColorRecord.setBlue(flatColor.getBlue());
		//
		newColorRecord.setAlpha(0);
		newColorRecord.setImageAlpha(BigDecimal.ZERO);
		//
		InterfaceWrapperHelper.save(newColorRecord);

		return newColorRecord.getAD_Color_ID();
	}

	

	@Override
	public int getColorIdByName(final String colorName)
	{
		return colorIdByName.getOrLoad(colorName, () -> retrieveColorIdByName(colorName));
	}

	private int retrieveColorIdByName(final String colorName)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Color.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClientOrSystem()
				.addEqualsFilter(I_AD_Color.COLUMNNAME_Name, colorName)
				.create()
				.firstIdOnly();
	}

}
