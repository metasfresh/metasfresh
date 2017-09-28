/**
 * This package is about <a href="http://dewiki908/mediawiki/index.php/07344_Karottenabrechnung_%28108429238530%29">07344 Karottenabrechnung (108429238530)</a>.
 * <p>
 * The rough workflow from a user's perspective is as follows:
 * <ul>
 * <li>The system is preconfigured with a {@link de.metas.contracts.model.I_C_Flatrate_Conditions I_C_Flatrate_Conditions} record that has {@link de.metas.contracts.model.I_C_Flatrate_Conditions#COLUMN_Type_Conditions Type_Conditions} ==
 * {@link de.metas.materialtracking.ch.lagerkonf.callout.interfaces.I_C_Flatrate_Conditions#TYPE_CONDITIONS_QualityBasedInvoicing QualityBasedInvoicing}</li>
 * <li>Also, there is a vendor (<code>C_BPartner</code>) which has a running contract (i.e. {@link de.metas.contracts.model.I_C_Flatrate_Term I_C_Flatrate_Term}) with that conditions</li>
 * <li>The user generates a {@link de.metas.materialtracking.model.I_M_Material_Tracking I_M_Material_Tracking} record for the vendor with the contract and the product that is the subject of the contract</li>
 * <li>The user creates a purchase order with the vendor and product</li>
 * <li>In the product's purchase order line's ASI, the user selects the <code>M_Material_Tracking</code> that was created further up
 * <ul>
 * <li>Note 1: when the purchase order was completed, the system will generate an {@link de.metas.invoicecandidate.model.I_C_Invoice_Candidate I_C_Invoice_Candidate} with
 * {@link de.metas.invoicecandidate.model.I_C_Invoice_Candidate#COLUMN_IsToClear IsToClear} <code>=='Y'</code></li>
 * <li>Note 2: Both the purchase <code>C_OrderLine</code> and the <code>C_Invoice_Candidate</code> are referenced from {@link de.metas.materialtracking.model.I_M_Material_Tracking_Ref I_M_Material_Tracking_Ref}
 * and can be found in a subtab of the Material-Tracking window ("Material-Vorgang")</li>
 * </ul></li>
 * <li>The user receives the purchase; the receipt's {@link org.compiere.model.I_M_InOutLine I_M_InOutLine} is also referenced via <code>M_Material_Tracking_Ref</code>, <b>and</b> the <code>M_Material_Tracking</code>
 * is added as a <code>M_HU_Attribute</code></li>
 * <li>The user creates a <b>Quality Inspection</b> Manufacturing Order (i.e. {@link de.metas.materialtracking.model.I_PP_Order I_PP_Order}); "Quality Inspection" is a special DocType introduced my this module.</li>
 * <li>The user selects one of the HUs that were received under the current <code>M_Material_Tracking</code> and performs some issues and receipts with that Manufacturing Order
 * <ul>
 * <li>Those issues and receipts are the actual quality inspection. The user will only issue a small <b>sample</b> from the whole receipt</li>
 * </ul></li>
 * <li>When the Quality Inspection Manufacturing Order is closed, the system will generate
 * <ul>
 * <li><code>C_Invoice_Candidates</code>, and will link them to the original IC (the one with <code>IsToClear='Y'</code>, see above) via {@link de.metas.contracts.model.I_C_Invoice_Clearing_Alloc I_C_Invoice_Clearing_Alloc} records.</li>
 * <li>{@link de.metas.materialtracking.model.I_C_Invoice_Detail I_C_Invoice_Detail} lines linked to the ICs.</li>
 * <li>{@link de.metas.materialtracking.model.I_PP_Order_Report I_PP_Order_Report} lines.</li>
 * </ul></li>
 * <li>the <code>PP_Order_Report</code> lines are used to display the PP_Order in jasper</li>
 * <li>the C_Invoice_Detail</li> lines will later be used to display the quality-inspection C_Invoice in jasper.
 * </ul>
 *
 *
 * <br/>
 * Technically speaking, the data flow is following:
 * <ul>
 * <li> {@link de.metas.materialtracking.qualityBasedInvoicing.IProductionMaterial} - a material(product) involved in quality inspection order (e.g. Raw material, co-product, finished good etc)
 * 	<ul>
 * 		<li>created as part of {@link de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionOrder}
 * 	</ul>
 * </li>
 * <li> {@link de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionLine} - is a component of quality order (e.g. Raw materials, Scrap, Produced Total without Scrap etc)
 * 	<ul>
 * 		<li>created based on {@link de.metas.materialtracking.qualityBasedInvoicing.IProductionMaterial}s
 * 		<li>created by {@link de.metas.materialtracking.qualityBasedInvoicing.impl.QualityInspectionLinesBuilder}
 * 	</ul>
 * </li>
 * <li> {@link de.metas.materialtracking.qualityBasedInvoicing.invoicing.IQualityInvoiceLineGroup}s - contains informations that we go into an invoice line or invoice candidate (including invoice details)
 * <li>
 * 	<ul>
 * 		<li>created based on {@link de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionLine}s
 * 		<li>created by {@link de.metas.materialtracking.ch.lagerkonf.invoicing.impl.QualityInvoiceLineGroupsBuilder}
 * 			<p><b>Note:</b> this implementation is responsible for the actual lines and layout that will end up on the <code>PP_Order</code> as well as <code>C_Invoice</code> jasper.
 * 	</ul>
 * </li>
 * <li> {@link de.metas.invoicecandidate.model.I_C_Invoice_Candidate} - invoice candidates
 * 	<ul>
 * 		<li>created based on {@link de.metas.materialtracking.qualityBasedInvoicing.invoicing.IQualityInvoiceLineGroup}
 * 		<li>created by {@link de.metas.materialtracking.qualityBasedInvoicing.ic.spi.impl.InvoiceCandidateWriter}
 * 	</ul>
 * </li>
 * </ul>
 *
 *
 *
 * @author ts
 * @author tsa
 *
 */
package de.metas.materialtracking;

