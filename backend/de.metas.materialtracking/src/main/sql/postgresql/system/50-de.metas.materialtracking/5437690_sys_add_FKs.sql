
ALTER TABLE M_Material_Tracking_Report DROP CONSTRAINT IF EXISTS CPeriod_MMaterialTrackingRepor;

ALTER TABLE M_QualityInsp_LagerKonf_Version DROP CONSTRAINT IF EXISTS CUOMScrap_MQualityInspLagerKon;

ALTER TABLE M_QualityInsp_LagerKonf_ProcessingFee DROP CONSTRAINT IF EXISTS CUOM_MQualityInspLagerKonfProc;

ALTER TABLE M_QualityInsp_LagerKonf_Month_Adj DROP CONSTRAINT IF EXISTS CUOM_MQualityInspLagerKonfMont;

ALTER TABLE M_Material_Tracking_Report_Line_Alloc DROP CONSTRAINT IF EXISTS MMaterialTracking_MMaterialTra;

ALTER TABLE M_QualityInsp_LagerKonf_AdditionalFee DROP CONSTRAINT IF EXISTS MQualityInspLagerKonfVersion_M;

ALTER TABLE M_QualityInsp_LagerKonf_Version DROP CONSTRAINT IF EXISTS MProductScrap_MQualityInspLage;

ALTER TABLE M_Material_Tracking_Report_Line_Alloc DROP CONSTRAINT IF EXISTS MMaterialTrackingReportLine_MM;

ALTER TABLE M_Material_Tracking_Ref DROP CONSTRAINT IF EXISTS ADTable_MMaterialTrackingRef;

ALTER TABLE M_Material_Tracking DROP CONSTRAINT IF EXISTS SalesRep_MMaterialTracking;

ALTER TABLE M_Material_Tracking_Report_Line DROP CONSTRAINT IF EXISTS MMaterialTrackingReport_MMater;

ALTER TABLE M_Material_Tracking_Ref DROP CONSTRAINT IF EXISTS MMaterialTracking_MMaterialTra;

ALTER TABLE M_QualityInsp_LagerKonf_Version DROP CONSTRAINT IF EXISTS MProductWitholding_MQualityIns;

ALTER TABLE M_QualityInsp_LagerKonf_Version DROP CONSTRAINT IF EXISTS MProductProcessingFee_MQuality;

ALTER TABLE M_QualityInsp_LagerKonf_Version DROP CONSTRAINT IF EXISTS MQualityInspLagerKonf_MQuality;

ALTER TABLE M_Material_Tracking DROP CONSTRAINT IF EXISTS CAllotment_MMaterialTracking;

ALTER TABLE M_QualityInsp_LagerKonf_Version DROP CONSTRAINT IF EXISTS MProductRegularPPOrder_MQualit;

ALTER TABLE M_Material_Tracking_Report_Line DROP CONSTRAINT IF EXISTS MProduct_MMaterialTrackingRepo;

ALTER TABLE M_Material_Tracking DROP CONSTRAINT IF EXISTS MAttributeValue_MMaterialTrack;

ALTER TABLE M_Material_Tracking DROP CONSTRAINT IF EXISTS MQualityInspLagerKonfVersion_M;

ALTER TABLE M_QualityInsp_LagerKonf_AdditionalFee DROP CONSTRAINT IF EXISTS MProduct_MQualityInspLagerKonf;

ALTER TABLE M_Material_Tracking DROP CONSTRAINT IF EXISTS CFlatrateTerm_MMaterialTrackin;

ALTER TABLE M_Material_Tracking_Report_Line_Alloc DROP CONSTRAINT IF EXISTS ADTable_MMaterialTrackingRepor;

ALTER TABLE M_QualityInsp_LagerKonf_ProcessingFee DROP CONSTRAINT IF EXISTS MQualityInspLagerKonfVersion_M;

ALTER TABLE M_QualityInsp_LagerKonf_Month_Adj DROP CONSTRAINT IF EXISTS MQualityInspLagerKonfVersion_M;

ALTER TABLE M_QualityInsp_LagerKonf_Version DROP CONSTRAINT IF EXISTS CCurrency_MQualityInspLagerKon;

ALTER TABLE M_Material_Tracking_Report DROP CONSTRAINT IF EXISTS CYear_MMaterialTrackingReport;

ALTER TABLE M_Material_Tracking_Report_Line DROP CONSTRAINT IF EXISTS MAttributeSetInstance_MMateria;

ALTER TABLE M_InOutLine DROP CONSTRAINT IF EXISTS MMaterialTracking_MInOutLine;

ALTER TABLE PP_Order DROP CONSTRAINT IF EXISTS MMaterialTracking_PPOrder;

ALTER TABLE C_Flatrate_Conditions DROP CONSTRAINT IF EXISTS MQualityInspLagerKonf_CFlatrat;

ALTER TABLE C_Invoice_Detail DROP CONSTRAINT IF EXISTS PPOrder_CInvoiceDetail;

ALTER TABLE C_Invoice_Candidate DROP CONSTRAINT IF EXISTS MMaterialTracking_CInvoiceCand;

--------------------------------



ALTER TABLE M_Material_Tracking_Report ADD CONSTRAINT CPeriod_MMaterialTrackingRepor FOREIGN KEY (C_Period_ID) REFERENCES C_Period DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_QualityInsp_LagerKonf_Version ADD CONSTRAINT CUOMScrap_MQualityInspLagerKon FOREIGN KEY (C_UOM_Scrap_ID) REFERENCES C_UOM DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_QualityInsp_LagerKonf_ProcessingFee ADD CONSTRAINT CUOM_MQualityInspLagerKonfProc FOREIGN KEY (C_UOM_ID) REFERENCES C_UOM DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_QualityInsp_LagerKonf_Month_Adj ADD CONSTRAINT CUOM_MQualityInspLagerKonfMont FOREIGN KEY (C_UOM_ID) REFERENCES C_UOM DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_Material_Tracking_Report_Line_Alloc ADD CONSTRAINT MMaterialTracking_MMaterialTra FOREIGN KEY (M_Material_Tracking_ID) REFERENCES M_Material_Tracking DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_QualityInsp_LagerKonf_AdditionalFee ADD CONSTRAINT MQualityInspLagerKonfVersion_M FOREIGN KEY (M_QualityInsp_LagerKonf_Version_ID) REFERENCES M_QualityInsp_LagerKonf_Version DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_QualityInsp_LagerKonf_Version ADD CONSTRAINT MProductScrap_MQualityInspLage FOREIGN KEY (M_Product_Scrap_ID) REFERENCES M_Product DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_Material_Tracking_Report_Line_Alloc ADD CONSTRAINT MMaterialTrackingReportLine_MM FOREIGN KEY (M_Material_Tracking_Report_Line_ID) REFERENCES M_Material_Tracking_Report_Line DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_Material_Tracking_Ref ADD CONSTRAINT ADTable_MMaterialTrackingRef FOREIGN KEY (AD_Table_ID) REFERENCES AD_Table DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_Material_Tracking ADD CONSTRAINT SalesRep_MMaterialTracking FOREIGN KEY (SalesRep_ID) REFERENCES AD_User DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_Material_Tracking_Report_Line ADD CONSTRAINT MMaterialTrackingReport_MMater FOREIGN KEY (M_Material_Tracking_Report_ID) REFERENCES M_Material_Tracking_Report DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_Material_Tracking_Ref ADD CONSTRAINT MMaterialTracking_MMaterialTra FOREIGN KEY (M_Material_Tracking_ID) REFERENCES M_Material_Tracking DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_QualityInsp_LagerKonf_Version ADD CONSTRAINT MProductWitholding_MQualityIns FOREIGN KEY (M_Product_Witholding_ID) REFERENCES M_Product DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_QualityInsp_LagerKonf_Version ADD CONSTRAINT MProductProcessingFee_MQuality FOREIGN KEY (M_Product_ProcessingFee_ID) REFERENCES M_Product DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_QualityInsp_LagerKonf_Version ADD CONSTRAINT MQualityInspLagerKonf_MQuality FOREIGN KEY (M_QualityInsp_LagerKonf_ID) REFERENCES M_QualityInsp_LagerKonf DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_Material_Tracking ADD CONSTRAINT CAllotment_MMaterialTracking FOREIGN KEY (C_Allotment_ID) REFERENCES C_Allotment DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_QualityInsp_LagerKonf_Version ADD CONSTRAINT MProductRegularPPOrder_MQualit FOREIGN KEY (M_Product_RegularPPOrder_ID) REFERENCES M_Product DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_Material_Tracking_Report_Line ADD CONSTRAINT MProduct_MMaterialTrackingRepo FOREIGN KEY (M_Product_ID) REFERENCES M_Product DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_Material_Tracking ADD CONSTRAINT MAttributeValue_MMaterialTrack FOREIGN KEY (M_AttributeValue_ID) REFERENCES M_AttributeValue DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_Material_Tracking ADD CONSTRAINT MQualityInspLagerKonfVersion_M FOREIGN KEY (M_QualityInsp_LagerKonf_Version_ID) REFERENCES M_QualityInsp_LagerKonf_Version DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_QualityInsp_LagerKonf_AdditionalFee ADD CONSTRAINT MProduct_MQualityInspLagerKonf FOREIGN KEY (M_Product_ID) REFERENCES M_Product DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_Material_Tracking ADD CONSTRAINT CFlatrateTerm_MMaterialTrackin FOREIGN KEY (C_Flatrate_Term_ID) REFERENCES C_Flatrate_Term DEFERRABLE INITIALLY DEFERRED;

--ALTER TABLE M_Material_Tracking_Report_Line_Alloc ADD CONSTRAINT ADTable_MMaterialTrackingRepor FOREIGN KEY (AD_Table_ID) REFERENCES AD_Table DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_QualityInsp_LagerKonf_ProcessingFee ADD CONSTRAINT MQualityInspLagerKonfVersion_M FOREIGN KEY (M_QualityInsp_LagerKonf_Version_ID) REFERENCES M_QualityInsp_LagerKonf_Version DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_QualityInsp_LagerKonf_Month_Adj ADD CONSTRAINT MQualityInspLagerKonfVersion_M FOREIGN KEY (M_QualityInsp_LagerKonf_Version_ID) REFERENCES M_QualityInsp_LagerKonf_Version DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_QualityInsp_LagerKonf_Version ADD CONSTRAINT CCurrency_MQualityInspLagerKon FOREIGN KEY (C_Currency_ID) REFERENCES C_Currency DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_Material_Tracking_Report ADD CONSTRAINT CYear_MMaterialTrackingReport FOREIGN KEY (C_Year_ID) REFERENCES C_Year DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_Material_Tracking_Report_Line ADD CONSTRAINT MAttributeSetInstance_MMateria FOREIGN KEY (M_AttributeSetInstance_ID) REFERENCES M_AttributeSetInstance DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE M_InOutLine ADD CONSTRAINT MMaterialTracking_MInOutLine FOREIGN KEY (M_Material_Tracking_ID) REFERENCES M_Material_Tracking DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE PP_Order ADD CONSTRAINT MMaterialTracking_PPOrder FOREIGN KEY (M_Material_Tracking_ID) REFERENCES M_Material_Tracking DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE C_Flatrate_Conditions ADD CONSTRAINT MQualityInspLagerKonf_CFlatrat FOREIGN KEY (M_QualityInsp_LagerKonf_ID) REFERENCES M_QualityInsp_LagerKonf DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE C_Invoice_Detail ADD CONSTRAINT PPOrder_CInvoiceDetail FOREIGN KEY (PP_Order_ID) REFERENCES PP_Order DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE C_Invoice_Candidate ADD CONSTRAINT MMaterialTracking_CInvoiceCand FOREIGN KEY (M_Material_Tracking_ID) REFERENCES M_Material_Tracking DEFERRABLE INITIALLY DEFERRED;

