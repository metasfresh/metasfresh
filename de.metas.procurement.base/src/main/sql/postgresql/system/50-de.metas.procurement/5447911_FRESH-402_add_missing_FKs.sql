update AD_Column c set DDL_NoForeignKey='Y'
from AD_Table t where t.AD_Table_ID=c.AD_Table_ID
and t.TableName='PMM_Balance'
and c.ColumnName in (
'C_BPartner_ID'
, 'C_Flatrate_DataEntry_ID'
, 'M_AttributeSetInstance_ID'
, 'M_HU_PI_Item_Product_ID'
, 'M_Product_ID'
);

update AD_Column c set DDL_NoForeignKey='Y'
from AD_Table t where t.AD_Table_ID=c.AD_Table_ID
and t.TableName='PMM_QtyReport_Event'
and c.ColumnName in (
'C_BPartner_ID'
, 'C_Currency_ID'
, 'C_Flatrate_DataEntry_ID'
, 'C_Flatrate_Term_ID'
, 'C_UOM_ID'
, 'M_AttributeSetInstance_ID'
, 'M_HU_PI_Item_Product_ID'
, 'M_PriceList_ID'
, 'M_PricingSystem_ID'
, 'M_Product_ID'
, 'M_Warehouse_ID'
);




update AD_Column c set DDL_NoForeignKey='Y'
from AD_Table t where t.AD_Table_ID=c.AD_Table_ID
and t.TableName='PMM_RfQResponse_ChangeEvent'
and c.ColumnName in (
'C_RfQResponseLine_ID'
, 'C_RfQResponseLineQty_ID'
, 'PMM_Product_ID'
);


update AD_Column c set DDL_NoForeignKey='Y'
from AD_Table t where t.AD_Table_ID=c.AD_Table_ID
and t.TableName='PMM_Week'
and c.ColumnName in (
'C_BPartner_ID'
, 'Last_WeekReport_Event_ID'
, 'M_AttributeSetInstance_ID'
, 'M_HU_PI_Item_Product_ID'
, 'M_Product_ID'
);

update AD_Column c set DDL_NoForeignKey='Y'
from AD_Table t where t.AD_Table_ID=c.AD_Table_ID
and t.TableName='PMM_WeekReport_Event'
and c.ColumnName in (
'C_BPartner_ID'
, 'M_AttributeSetInstance_ID'
, 'M_HU_PI_Item_Product_ID'
, 'M_Product_ID'
);





ALTER TABLE C_Flatrate_Term ADD CONSTRAINT CRfQResponseLine_CFlatrateTerm FOREIGN KEY (C_RfQResponseLine_ID) REFERENCES C_RfQResponseLine DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE C_RfQ ADD CONSTRAINT CFlatrateConditions_CRfQ FOREIGN KEY (C_Flatrate_Conditions_ID) REFERENCES C_Flatrate_Conditions DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE C_RfQ_Topic ADD CONSTRAINT CFlatrateConditions_CRfQTopic FOREIGN KEY (C_Flatrate_Conditions_ID) REFERENCES C_Flatrate_Conditions DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE C_RfQLine ADD CONSTRAINT PMMProduct_CRfQLine FOREIGN KEY (PMM_Product_ID) REFERENCES PMM_Product DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE C_RfQResponseLine ADD CONSTRAINT CFlatrateTerm_CRfQResponseLine FOREIGN KEY (C_Flatrate_Term_ID) REFERENCES C_Flatrate_Term DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE C_RfQResponseLine ADD CONSTRAINT PMMProduct_CRfQResponseLine FOREIGN KEY (PMM_Product_ID) REFERENCES PMM_Product DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE PMM_Product ADD CONSTRAINT CBPartner_PMMProduct FOREIGN KEY (C_BPartner_ID) REFERENCES C_BPartner DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE PMM_Product ADD CONSTRAINT MAttributeSetInstance_PMMProdu FOREIGN KEY (M_AttributeSetInstance_ID) REFERENCES M_AttributeSetInstance DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE PMM_Product ADD CONSTRAINT MHUPIItemProduct_PMMProduct FOREIGN KEY (M_HU_PI_Item_Product_ID) REFERENCES M_HU_PI_Item_Product DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE PMM_Product ADD CONSTRAINT MProduct_PMMProduct FOREIGN KEY (M_Product_ID) REFERENCES M_Product DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE PMM_Product ADD CONSTRAINT MWarehouse_PMMProduct FOREIGN KEY (M_Warehouse_ID) REFERENCES M_Warehouse DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE PMM_PurchaseCandidate ADD CONSTRAINT CBPartner_PMMPurchaseCandidate FOREIGN KEY (C_BPartner_ID) REFERENCES C_BPartner DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE PMM_PurchaseCandidate ADD CONSTRAINT CCurrency_PMMPurchaseCandidate FOREIGN KEY (C_Currency_ID) REFERENCES C_Currency DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE PMM_PurchaseCandidate ADD CONSTRAINT CFlatrateDataEntry_PMMPurchase FOREIGN KEY (C_Flatrate_DataEntry_ID) REFERENCES C_Flatrate_DataEntry DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE PMM_PurchaseCandidate ADD CONSTRAINT CUOM_PMMPurchaseCandidate FOREIGN KEY (C_UOM_ID) REFERENCES C_UOM DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE PMM_PurchaseCandidate ADD CONSTRAINT MAttributeSetInstance_PMMPurch FOREIGN KEY (M_AttributeSetInstance_ID) REFERENCES M_AttributeSetInstance DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE PMM_PurchaseCandidate ADD CONSTRAINT MHUPIItemProduct_PMMPurchaseCa FOREIGN KEY (M_HU_PI_Item_Product_ID) REFERENCES M_HU_PI_Item_Product DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE PMM_PurchaseCandidate ADD CONSTRAINT MHUPIItemProductOverride_PMMPu FOREIGN KEY (M_HU_PI_Item_Product_Override_ID) REFERENCES M_HU_PI_Item_Product DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE PMM_PurchaseCandidate ADD CONSTRAINT MPriceList_PMMPurchaseCandidat FOREIGN KEY (M_PriceList_ID) REFERENCES M_PriceList DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE PMM_PurchaseCandidate ADD CONSTRAINT MPricingSystem_PMMPurchaseCand FOREIGN KEY (M_PricingSystem_ID) REFERENCES M_PricingSystem DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE PMM_PurchaseCandidate ADD CONSTRAINT MProduct_PMMPurchaseCandidate FOREIGN KEY (M_Product_ID) REFERENCES M_Product DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE PMM_PurchaseCandidate ADD CONSTRAINT MWarehouse_PMMPurchaseCandidat FOREIGN KEY (M_Warehouse_ID) REFERENCES M_Warehouse DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE PMM_PurchaseCandidate_OrderLine ADD CONSTRAINT COrderLine_PMMPurchaseCandidat FOREIGN KEY (C_OrderLine_ID) REFERENCES C_OrderLine DEFERRABLE INITIALLY DEFERRED;

