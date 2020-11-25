package org.adempiere.exceptions;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;

/**
 * Any exception that occurs when no UOM conversion rate was found
 *
 * @author Teo Sarca, http://www.arhipac.ro
 */
public class NoUOMConversionException extends AdempiereException
{
	private static final AdMessageKey MSG = AdMessageKey.of("NoUOMConversion");

	public NoUOMConversionException(
			@Nullable final ProductId productId,
			@Nullable final UomId fromUomId,
			@Nullable final UomId toUomId)
	{
		super(buildMessage(productId, fromUomId, toUomId));
	}

	private static ITranslatableString buildMessage(
			@Nullable final ProductId productId,
			@Nullable final UomId fromUomId,
			@Nullable final UomId toUomId)
	{
		return TranslatableStrings.builder()
				.appendADMessage(MSG)
				.append(" ").appendADElement("M_Product_ID").append(": ").append(extractProductName(productId))
				.append(" ").appendADElement("C_UOM_ID").append(": ").append(extractUOMSymbol(fromUomId))
				.append(" ").appendADElement("C_UOM_To_ID").append(": ").append(extractUOMSymbol(toUomId))
				.build();
	}

	private static String extractProductName(@Nullable final ProductId productId)
	{
		if (productId == null)
		{
			return "<none>";
		}

		return Services.get(IProductBL.class).getProductValueAndName(productId);
	}

	private static String extractUOMSymbol(@Nullable final UomId uomId)
	{
		if (uomId == null)
		{
			return "<none>";
		}

		try
		{
			final I_C_UOM uom = Services.get(IUOMDAO.class).getById(uomId);
			if (uom == null)
			{
				return "<" + uomId.getRepoId() + ">";
			}

			return uom.getUOMSymbol();
		}
		catch (final Exception ex)
		{
			return "<" + uomId.getRepoId() + ">";
		}
	}
}
