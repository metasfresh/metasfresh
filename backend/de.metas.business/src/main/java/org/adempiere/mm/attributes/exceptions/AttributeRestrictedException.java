package org.adempiere.mm.attributes.exceptions;

import java.io.Serial;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.compiere.util.Env;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.lang.SOTrx;
import de.metas.util.Services;

public class AttributeRestrictedException extends AdempiereException
{
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = 7812550089813860111L;

	private static final AdMessageKey MSG = AdMessageKey.of("de.metas.swat.Attribute.attributeRestricted");

	private static final AdMessageKey MSG_SOTransaction = AdMessageKey.of("de.metas.swat.SOTrx");
	private static final AdMessageKey MSG_POTransaction = AdMessageKey.of("de.metas.swat.POTrx");

	/**
	 * 
	 * @param ctx
	 * @param isSOTrx
	 * @param attributeValue
	 * @param referenceName name of referenced model on which given attribute value is restricted
	 */
	public AttributeRestrictedException(final Properties ctx, final SOTrx soTrx, final AttributeListValue attributeValue, final String referenceName)
	{
		super(buildMsg(ctx, soTrx, attributeValue, referenceName));

	}

	private static String buildMsg(Properties ctx, SOTrx soTrx, AttributeListValue attributeValue, final String referenceName)
	{
		final boolean isSOTrx = SOTrx.toBoolean(soTrx);
		final String transactionType = Services.get(IMsgBL.class).getMsg(ctx, (isSOTrx ? MSG_SOTransaction : MSG_POTransaction));

		final String adLanguage = Env.getAD_Language(ctx);
		final String attributeName = Services.get(IAttributesBL.class).getAttributeById(attributeValue.getAttributeId()).getName();
		return Services.get(IMsgBL.class).getMsg(adLanguage,
				MSG,
				new Object[] { attributeName, referenceName, transactionType });
	}
}
