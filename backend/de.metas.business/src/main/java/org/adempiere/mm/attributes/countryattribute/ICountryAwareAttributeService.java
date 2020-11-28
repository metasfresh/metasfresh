package org.adempiere.mm.attributes.countryattribute;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.util.lang.IContextAware;

/**
 * Implementations are responsible for:
 * <ul>
 * <li>suggesting an M_Attribute_ID to be used, based on {@link ICountryAware}
 * <li>suggesting an attribute value to be used, based on {@link ICountryAware}
 * </ul>
 * 
 * @author tsa
 *
 */
public interface ICountryAwareAttributeService
{
	/**
	 * Gets the M_Attribute_ID to use for given {@link ICountryAware}.
	 * 
	 * @param countryAware
	 * @return AttributeId or null
	 */
	AttributeId getAttributeId(ICountryAware countryAware);

	/**
	 * Gets/creates the corresponding attribute value for given {@link ICountryAware}.
	 * 
	 * @param context
	 * @param countryAware
	 * @return
	 */
	AttributeListValue getCreateAttributeValue(final IContextAware context, final ICountryAware countryAware);
}
