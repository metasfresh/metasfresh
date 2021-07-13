package de.metas.document;

import de.metas.document.model.IDocumentBillLocation;
import de.metas.document.model.IDocumentDeliveryLocation;
import de.metas.document.model.IDocumentHandOverLocation;
import de.metas.document.model.IDocumentLocation;
import de.metas.util.ISingletonService;

/**
 * 
 * @author tsa
 * task http://dewiki908/mediawiki/index.php/03120:_Error_in_DocumentLocation_callout_%282012080910000142%29
 */
public interface IDocumentLocationBL extends ISingletonService
{

	void setBPartnerAddress(IDocumentLocation order);

	void setBillToAddress(IDocumentBillLocation billLocation);

	void setDeliveryToAddress(IDocumentDeliveryLocation order);
	
	void setHandOverAddress(IDocumentHandOverLocation order);

}
