/**
 *
 */
package org.adempiere.ad.service;

import java.util.List;

import org.adempiere.ad.element.api.AdElementId;
import org.adempiere.ad.element.api.CreateADElementRequest;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Field;

import de.metas.util.ISingletonService;

/**
 * @author cg
 *
 */
public interface IADElementDAO extends ISingletonService
{
	List<I_AD_Column> retrieveColumns(int elementId);

	List<I_AD_Field> retrieveFields(String columnName);

	I_AD_Element getADElement(String columnName);


	void makeElementMandatoryInApplicationDictionaryTables();

	I_AD_Element getById(int elementId);

	AdElementId createNewElement(CreateADElementRequest request);
}
