DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.OpenItems_Report(date,
                                                                            character varying,
                                                                            numeric)
;


DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.OpenItems_Report_Details(IN p_AD_Org_ID             numeric,
                                                                                    IN p_C_BPartner_ID         numeric,
                                                                                    IN p_IsSOTrx               character varying,
                                                                                    IN p_DaysDue               numeric,
                                                                                    IN p_InvoiceCollectionType character varying,
                                                                                    IN p_StartDate             date,
                                                                                    IN p_EndDate               date,
                                                                                    IN p_Reference_Date        date,
                                                                                    IN p_PassForPayment        character varying,
                                                                                    IN p_switchDate            character varying)
;

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.OpenItems_Report_Tabular(IN AD_Org_ID             numeric,
                                                                                    IN C_BPartner_ID         numeric,
                                                                                    IN IsSOTrx               character varying,
                                                                                    IN DaysDue               numeric,
                                                                                    IN InvoiceCollectionType character varying,
                                                                                    IN StartDate             date,
                                                                                    IN EndDate               date,
                                                                                    IN Reference_Date        date,
                                                                                    IN PassForPayment        character varying,
                                                                                    IN switchDate            character varying)
;

DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.OpenItems_Report_Title(IN AD_Org_ID             numeric,
                                                                                  IN C_BPartner_ID         numeric,
                                                                                  IN IsSOTrx               character varying,
                                                                                  IN InvoiceCollectionType character varying)
;
