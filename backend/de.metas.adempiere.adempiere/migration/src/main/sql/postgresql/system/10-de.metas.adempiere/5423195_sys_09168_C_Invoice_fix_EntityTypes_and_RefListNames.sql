-- Change C_Invoice.C_RecurrentPaymentLine_ID's entitytype from "A" to "de.metas.banking"
update AD_Column set EntityType='de.metas.banking' where AD_Column_ID=541793;

-- Fix InvoiceCollectionType
update AD_Ref_List set ValueName='Dunning' where AD_Reference_ID=394 and Value='D';
update AD_Ref_List set ValueName='CollectionAgency' where AD_Reference_ID=394 and Value='C';
update AD_Ref_List set ValueName='LegalProcedure' where AD_Reference_ID=394 and Value='L';
update AD_Ref_List set ValueName='Uncollectable' where AD_Reference_ID=394 and Value='U';
