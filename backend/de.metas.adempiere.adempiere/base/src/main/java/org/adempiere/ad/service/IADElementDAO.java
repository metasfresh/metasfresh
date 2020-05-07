/**
 * 
 */
package org.adempiere.ad.service;

import java.util.List;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Field;

/**
 * @author cg
 *
 */
public interface IADElementDAO extends ISingletonService
{
	List<I_AD_Column> retrieveColumns(int elementId);

	List<I_AD_Field> retrieveFields(String columnName);

	I_AD_Element getADElement(String columnName);
}
