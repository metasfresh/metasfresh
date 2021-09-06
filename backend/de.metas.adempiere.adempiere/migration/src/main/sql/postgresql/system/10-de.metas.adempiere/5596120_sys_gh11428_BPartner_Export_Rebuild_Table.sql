

DROP TABLE C_BPartner_Export
;



-- 2021-07-01T09:35:14.934Z
-- URL zum Konzept
/* DDL */

CREATE TABLE public.C_BPartner_Export
(
    AD_Client_ID                  NUMERIC(10)                                                       NOT NULL,
    Address1                      VARCHAR(100),
    Address2                      VARCHAR(100),
    Address3                      VARCHAR(100),
    Address4                      VARCHAR(100),
    AD_Language                   VARCHAR(10),
    AD_Org_ID                     NUMERIC(10),
    Birthday                      TIMESTAMP WITH TIME ZONE,
    BPName                        VARCHAR(60),
    BPValue                       VARCHAR(40),
    Category                      VARCHAR(255),
    C_BPartner_Export_ID          NUMERIC(10),
    C_BPartner_ID                 NUMERIC(10),
    C_CompensationGroup_Schema_ID NUMERIC(10),
    C_Country_ID                  NUMERIC(10),
    C_Flatrate_Term_ID            NUMERIC(10),
    C_Greeting_ID                 NUMERIC(10),
    City                          VARCHAR(60),
    Companyname                   VARCHAR(255),
    ContractStatus                VARCHAR(250),
    C_Order_ID                    NUMERIC(10),
    C_Postal_ID                   NUMERIC(10),
    Created                       TIMESTAMP WITH TIME ZONE                                          NOT NULL,
    CreatedBy                     NUMERIC(10)                                                       NOT NULL,
    EMailUser                     VARCHAR(60),
    ExcludeFromPromotions         CHAR(1) DEFAULT 'N' CHECK (ExcludeFromPromotions IN ('Y', 'N'))   NOT NULL,
    Firstname                     VARCHAR(255),
    Greeting                      VARCHAR(60),
    HasDifferentBillPartner       CHAR(1) DEFAULT 'N' CHECK (HasDifferentBillPartner IN ('Y', 'N')) NOT NULL,
    IsActive                      CHAR(1) CHECK (IsActive IN ('Y', 'N'))                            NOT NULL,
    Lastname                      VARCHAR(255),
    Letter_Salutation             VARCHAR(60),
    MasterEndDate                 TIMESTAMP WITH TIME ZONE,
    MasterStartDate               TIMESTAMP WITH TIME ZONE,
    MKTG_Campaign_ID              NUMERIC(10),
    Postal                        VARCHAR(10),
    TerminationReason             VARCHAR(3),
    Updated                       TIMESTAMP WITH TIME ZONE                                          NOT NULL,
    UpdatedBy                     NUMERIC(10)                                                       NOT NULL,
    CONSTRAINT ADLangu_CBPartnerExport FOREIGN KEY (AD_Language) REFERENCES public.AD_Language DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT C_BPartner_Export_Key PRIMARY KEY (C_BPartner_Export_ID),
    CONSTRAINT CBPartner_CBPartnerExport FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT CCompensationGroupSchema_CBPartnerExport FOREIGN KEY (C_CompensationGroup_Schema_ID) REFERENCES public.C_CompensationGroup_Schema DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT CCountry_CBPartnerExport FOREIGN KEY (C_Country_ID) REFERENCES public.C_Country DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT CFlatrateTerm_CBPartnerExport FOREIGN KEY (C_Flatrate_Term_ID) REFERENCES public.C_Flatrate_Term DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT CGreeting_CBPartnerExport FOREIGN KEY (C_Greeting_ID) REFERENCES public.C_Greeting DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT COrder_CBPartnerExport FOREIGN KEY (C_Order_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT CPostal_CBPartnerExport FOREIGN KEY (C_Postal_ID) REFERENCES public.C_Postal DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT MKTGCampaign_CBPartnerExport FOREIGN KEY (MKTG_Campaign_ID) REFERENCES public.MKTG_Campaign DEFERRABLE INITIALLY DEFERRED
)
;

-- 2021-07-01T09:36:36.150Z
-- URL zum Konzept
CREATE INDEX C_BPartner_Export_BPName ON C_BPartner_Export (BPName)
;



DELETE FROM ad_userquery WHERE name = 'C_BPartner_Export_Postal1';


select update_c_bpartner_export();