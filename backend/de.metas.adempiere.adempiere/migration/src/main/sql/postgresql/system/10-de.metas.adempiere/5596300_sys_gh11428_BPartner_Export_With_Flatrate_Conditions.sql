
-- 2021-07-01T15:56:33.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574920,541423,0,30,541731,'C_Flatrate_Conditions_ID',TO_TIMESTAMP('2021-07-01 18:56:33','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Vertragsbedingungen',0,0,TO_TIMESTAMP('2021-07-01 18:56:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-07-01T15:56:33.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574920 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-07-01T15:56:33.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(541423) 
;

-- 2021-07-01T15:56:34.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_Export','ALTER TABLE public.C_BPartner_Export ADD COLUMN C_Flatrate_Conditions_ID NUMERIC(10)')
;

-- 2021-07-01T15:56:34.774Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_BPartner_Export ADD CONSTRAINT CFlatrateConditions_CBPartnerExport FOREIGN KEY (C_Flatrate_Conditions_ID) REFERENCES public.C_Flatrate_Conditions DEFERRABLE INITIALLY DEFERRED
;

-- 2021-07-01T15:57:14.873Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2021-07-01 18:57:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574920
;

-- 2021-07-01T15:57:40.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='N',Updated=TO_TIMESTAMP('2021-07-01 18:57:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574880
;

-- 2021-07-01T15:58:42.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='N', SelectionColumnSeqNo=0,Updated=TO_TIMESTAMP('2021-07-01 18:58:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574880
;

-- 2021-07-01T15:58:43.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=100,Updated=TO_TIMESTAMP('2021-07-01 18:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574920
;

-- 2021-07-01T15:59:25.369Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,574920,649942,0,544118,0,TO_TIMESTAMP('2021-07-01 18:59:25','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Vertragsbedingungen',0,10,0,1,1,TO_TIMESTAMP('2021-07-01 18:59:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-01T15:59:25.371Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=649942 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-07-01T15:59:25.373Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541423) 
;

-- 2021-07-01T15:59:25.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=649942
;

-- 2021-07-01T15:59:25.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(649942)
;

-- 2021-07-01T15:59:53.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2021-07-01 18:59:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586924
;





-- 2021-07-01T16:00:14.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,649942,0,544118,546167,586950,'F',TO_TIMESTAMP('2021-07-01 19:00:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Vertragsbedingungen',80,0,0,TO_TIMESTAMP('2021-07-01 19:00:13','YYYY-MM-DD HH24:MI:SS'),100)
;






-- 2021-07-01T17:03:18.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bpartner_export','C_Flatrate_Conditions_ID','NUMERIC(10)',null,null)
;

-- 2021-07-01T17:03:32.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=540280, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2021-07-01 20:03:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574920
;









CREATE OR REPLACE FUNCTION public.Update_C_BPartner_Export(
	)
    RETURNS boolean
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE 
AS $BODY$
BEGIN
 
 TRUNCATE C_BPartner_Export;
 
DROP VIEW IF EXISTS C_BPartner_Export_View
;

CREATE VIEW C_BPartner_Export_View AS
SELECT bp.created,
       bp.updated,
       bp.createdby,
       bp.updatedby,
       bp.isactive,
       bp.ad_client_id,

       bp.C_Bpartner_ID                                AS C_BPartner_Export_ID,
       bp.c_bpartner_id,
       bp.AD_Org_ID,

       CASE
           WHEN term.c_flatrate_term_id IS NOT NULL THEN 'Mitglied'
           WHEN o.c_order_id IS NOT NULL            THEN 'Kunde'
                                                    ELSE 'Abonnent'
       END                                             AS Category,

       term.contractstatus,
       bp.Value                                        AS bpValue,
       CASE WHEN (bp.iscompany = 'N') THEN bp.name END AS bpName,
       bp.companyname,
       bp.ad_language,
       bp.excludefrompromotions,
       u.C_Greeting_ID,
       g.greeting,
       g.letter_salutation,

       u.firstname,
       u.lastname,
       u.birthday,
       l.address1,
       l.address2,
       l.address3,
       l.address4,
       l.C_Postal_ID,
       l.postal,
       l.city,
       l.c_country_id,
       u.emailuser,


       term.c_flatrate_term_id,
       term.masterstartdate,
       term.masterenddate,
       term.terminationreason,

       cond.c_flatrate_conditions_id,


       o.C_Order_ID,
       CASE
           WHEN ((o.c_bpartner_id != o.bill_bpartner_id) OR (o.c_bpartner_location_id != o.bill_location_id))
               THEN 'Y'
               ELSE 'N'
       END                                             AS hasDifferentBillpartner,

       schema.c_compensationgroup_schema_id,
       bp.mktg_campaign_id


FROM C_BPartner bp
         LEFT JOIN AD_User u
                   ON bp.C_BPartner_Id = u.C_BPartner_ID AND u.AD_User_ID = (SELECT AD_User_ID
                                                                             FROM AD_User
                                                                             WHERE C_BPartner_ID = bp.C_BPartner_ID
                                                                             ORDER BY isactive DESC, IsDefaultContact DESC
                                                                             LIMIT 1)
         LEFT JOIN C_BPartner_Location bpl
                   ON bp.C_BPartner_ID = bpl.C_BPartner_ID
                       AND bpl.c_bpartner_location_id = (SELECT c_bpartner_location_id
                                                         FROM c_bpartner_location
                                                         WHERE c_bpartner_id = bp.c_bpartner_id
                                                         ORDER BY isActive DESC, isbilltodefault DESC, isbillto DESC
                                                         LIMIT 1)
         LEFT JOIN C_Location l ON bpl.c_location_id = l.c_location_id
         LEFT JOIN C_Greeting g ON u.C_Greeting_ID = g.c_greeting_id
         LEFT JOIN C_Flatrate_Term term
                   ON term.bill_bpartner_id = bp.c_bpartner_id
                       AND term.C_Flatrate_Term_ID = (SELECT C_Flatrate_Term_ID
                                                      FROM C_Flatrate_Term t
                                                               JOIN M_Product tp ON t.m_product_id = tp.m_product_id
                                                      WHERE t.bill_bpartner_id = bp.c_bpartner_id
                                                        AND DocStatus IN ('CO', 'CL')
                                                      ORDER BY (COALESCE(masterenddate, endDate)) DESC, tp.c_compensationgroup_schema_id NULLS LAST
                                                      LIMIT 1)
         LEFT JOIN M_Product termProduct ON term.m_product_id = termProduct.m_product_id
         LEFT JOIN c_compensationgroup_schema schema ON termProduct.c_compensationgroup_schema_id = schema.c_compensationgroup_schema_id
         LEFT JOIN c_flatrate_conditions cond ON term.c_flatrate_conditions_id = cond.c_flatrate_conditions_id
         LEFT JOIN C_Order o
                   ON bp.c_bpartner_id = o.bill_bpartner_id
                       AND o.c_order_ID = (SELECT c_order_ID
                                           FROM C_Order
                                           WHERE c_bpartner_id = bp.c_bpartner_id
                                             AND issotrx = 'Y'
                                             AND docstatus IN ('CO', 'CL')
                                           ORDER BY dateordered DESC
                                           LIMIT 1)

;








INSERT INTO c_bpartner_export
(ad_client_id, address1, address2, address3, address4, ad_org_id, birthday, bpname, bpvalue, category, c_bpartner_export_id,
 c_country_id, c_greeting_id, city, created, createdby, emailuser, firstname, greeting, isactive, lastname, letter_salutation,
 masterenddate, masterstartdate, postal, terminationreason, updated, updatedby, c_postal_id, ad_language, companyname, contractstatus,
 mktg_campaign_id, c_compensationgroup_schema_id, hasdifferentbillpartner, c_flatrate_term_id, C_Order_ID, C_BPartner_ID, c_flatrate_conditions_id)

SELECT ad_client_id,
       address1,
       address2,
       address3,
       address4,
       ad_org_id,
       birthday,
       bpname,
       bpvalue,
       category,
       c_bpartner_export_id,
       c_country_id,
       c_greeting_id,
       city,
       created,
       createdby,
       emailuser,
       firstname,
       greeting,
       isactive,
       lastname,
       letter_salutation,
       masterenddate,
       masterstartdate,
       postal,
       terminationreason,
       updated,
       updatedby,
       c_postal_id,
       ad_language,
       companyname,
       contractstatus,
       mktg_campaign_id,
       c_compensationgroup_schema_id,
       hasdifferentbillpartner,
       c_flatrate_term_id,
       C_Order_ID,
       C_BPartner_ID,
	   c_flatrate_conditions_id

FROM c_bpartner_export_view
;



 return true;
END;
$BODY$;

ALTER FUNCTION public.Update_C_BPartner_Export()
    OWNER TO metasfresh;

COMMENT ON FUNCTION public.Update_C_BPartner_Export()
    IS 'This function truncates the table C_BPartner_Export, recreates the view C_BPartner_Export_View and inserts from the view into the table.
	It will be called once every night via a scheduler.' ;
	
	-- Select Update_C_BPartner_Export();
	
	
	
	
	
	
	
	
	
	
	
	
	


DROP VIEW IF EXISTS C_BPartner_Export_View
;

CREATE VIEW C_BPartner_Export_View AS
SELECT bp.created,
       bp.updated,
       bp.createdby,
       bp.updatedby,
       bp.isactive,
       bp.ad_client_id,

       bp.C_Bpartner_ID                                AS C_BPartner_Export_ID,
       bp.c_bpartner_id,
       bp.AD_Org_ID,

       CASE
           WHEN term.c_flatrate_term_id IS NOT NULL THEN 'Mitglied'
           WHEN o.c_order_id IS NOT NULL            THEN 'Kunde'
                                                    ELSE 'Abonnent'
       END                                             AS Category,

       term.contractstatus,
       bp.Value                                        AS bpValue,
       CASE WHEN (bp.iscompany = 'N') THEN bp.name END AS bpName,
       bp.companyname,
       bp.ad_language,
       bp.excludefrompromotions,
       u.C_Greeting_ID,
       g.greeting,
       g.letter_salutation,

       u.firstname,
       u.lastname,
       u.birthday,
       l.address1,
       l.address2,
       l.address3,
       l.address4,
       l.C_Postal_ID,
       l.postal,
       l.city,
       l.c_country_id,
       u.emailuser,


       term.c_flatrate_term_id,
       term.masterstartdate,
       term.masterenddate,
       term.terminationreason,

       cond.c_flatrate_conditions_id,


       o.C_Order_ID,
       CASE
           WHEN ((o.c_bpartner_id != o.bill_bpartner_id) OR (o.c_bpartner_location_id != o.bill_location_id))
               THEN 'Y'
               ELSE 'N'
       END                                             AS hasDifferentBillpartner,

       schema.c_compensationgroup_schema_id,
       bp.mktg_campaign_id


FROM C_BPartner bp
         LEFT JOIN AD_User u
                   ON bp.C_BPartner_Id = u.C_BPartner_ID AND u.AD_User_ID = (SELECT AD_User_ID
                                                                             FROM AD_User
                                                                             WHERE C_BPartner_ID = bp.C_BPartner_ID
                                                                             ORDER BY isactive DESC, IsDefaultContact DESC
                                                                             LIMIT 1)
         LEFT JOIN C_BPartner_Location bpl
                   ON bp.C_BPartner_ID = bpl.C_BPartner_ID
                       AND bpl.c_bpartner_location_id = (SELECT c_bpartner_location_id
                                                         FROM c_bpartner_location
                                                         WHERE c_bpartner_id = bp.c_bpartner_id
                                                         ORDER BY isActive DESC, isbilltodefault DESC, isbillto DESC
                                                         LIMIT 1)
         LEFT JOIN C_Location l ON bpl.c_location_id = l.c_location_id
         LEFT JOIN C_Greeting g ON u.C_Greeting_ID = g.c_greeting_id
         LEFT JOIN C_Flatrate_Term term
                   ON term.bill_bpartner_id = bp.c_bpartner_id
                       AND term.C_Flatrate_Term_ID = (SELECT C_Flatrate_Term_ID
                                                      FROM C_Flatrate_Term t
                                                               JOIN M_Product tp ON t.m_product_id = tp.m_product_id
                                                      WHERE t.bill_bpartner_id = bp.c_bpartner_id
                                                        AND DocStatus IN ('CO', 'CL')
                                                      ORDER BY (COALESCE(masterenddate, endDate)) DESC, tp.c_compensationgroup_schema_id NULLS LAST
                                                      LIMIT 1)
         LEFT JOIN M_Product termProduct ON term.m_product_id = termProduct.m_product_id
         LEFT JOIN c_compensationgroup_schema schema ON termProduct.c_compensationgroup_schema_id = schema.c_compensationgroup_schema_id
         LEFT JOIN c_flatrate_conditions cond ON term.c_flatrate_conditions_id = cond.c_flatrate_conditions_id
         LEFT JOIN C_Order o
                   ON bp.c_bpartner_id = o.bill_bpartner_id
                       AND o.c_order_ID = (SELECT c_order_ID
                                           FROM C_Order
                                           WHERE c_bpartner_id = bp.c_bpartner_id
                                             AND issotrx = 'Y'
                                             AND docstatus IN ('CO', 'CL')
                                           ORDER BY dateordered DESC
                                           LIMIT 1)
										   
										   
;


Select Update_C_BPartner_Export();