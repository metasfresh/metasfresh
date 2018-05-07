package org.adempiere.mm.attributes.api;

import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_M_AttributeValue;

/**
 * Implementations are responsible for:
 * <ul>
 * <li>suggesting an M_Attribute_ID to be used, based on {@link IBPartnerAware}
 * <li>suggesting an {@link I_M_AttributeValue} to be used, based on {@link IBPartnerAware}
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
	int getM_Attribute_ID(IBPartnerAware bpartnerAware);

	/**
	 * Gets/creates the coresponsing {@link I_M_AttributeValue} for given {@link IBPartnerAware}.
	 * 
	 * @param context
	 * @param bpartnerAware
	 * @return
	 */
	I_M_AttributeValue getCreateAttributeValue(final IContextAware context, final IBPartnerAware bpartnerAware);

}
