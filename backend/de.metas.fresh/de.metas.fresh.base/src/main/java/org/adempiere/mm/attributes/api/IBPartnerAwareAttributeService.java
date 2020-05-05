package org.adempiere.mm.attributes.api;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.util.lang.IContextAware;

/**
 * Implementations are responsible for:
 * <ul>
 * <li>suggesting an M_Attribute_ID to be used, based on {@link IBPartnerAware}
 * <li>suggesting an attribute value to be used, based on {@link IBPartnerAware}
 * </ul>
 * 
 * @author tsa
 *
 */
public interface IBPartnerAwareAttributeService
{
	/**
	 * Gets the M_Attribute_ID to use for given {@link IBPartnerAware}.
	 * 
	 * @param bpartnerAware
	 * @return M_Attribute_ID or <code>-1</code>.
	 */
	AttributeId getAttributeId(IBPartnerAware bpartnerAware);

	/**
	 * Gets/creates the corresponding attribute value for given {@link IBPartnerAware}.
	 * 
	 * @param context
	 * @param bpartnerAware
	 * @return
	 */
	AttributeListValue getCreateAttributeValue(final IContextAware context, final IBPartnerAware bpartnerAware);

}
