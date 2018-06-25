package org.adempiere.mm.attributes.countryattribute;

import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_M_AttributeValue;

/**
 * Implementations are responsible for:
 * <ul>
 * <li>suggesting an M_Attribute_ID to be used, based on {@link ICountryAware}
 * <li>suggesting an {@link I_M_AttributeValue} to be used, based on {@link ICountryAware}
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
	 * @return M_Attribute_ID or <code>-1</code>.
	 */
	int getM_Attribute_ID(ICountryAware countryAware);

	/**
	 * Gets/creates the coresponsing {@link I_M_AttributeValue} for given {@link ICountryAware}.
	 * 
	 * @param context
	 * @param countryAware
	 * @return
	 */
	I_M_AttributeValue getCreateAttributeValue(final IContextAware context, final ICountryAware countryAware);
}
