-- Name: C_BPartner_Description_Filter
-- 2025-04-09T16:18:46.138Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541936,TO_TIMESTAMP('2025-04-09 16:18:45.246000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Filter using the bpartner description','D','- Filter using the bpartner description in:
    - `Auftraggeber` and `Lieferung an` in sales order window
    -  `Rechnung an` in sales invoice window','Y','N','C_BPartner_Description_Filter',TO_TIMESTAMP('2025-04-09 16:18:45.246000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2025-04-09T16:18:46.193Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541936 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: C_BPartner_Description_Filter
-- Table: C_BPartner
-- Key: C_BPartner.C_BPartner_ID
-- 2025-04-09T16:19:29.609Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,2893,0,541936,291,TO_TIMESTAMP('2025-04-09 16:19:28.872000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Y','N',TO_TIMESTAMP('2025-04-09 16:19:28.872000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_BPartner.IsSummary=''N'' AND C_BPartner.IsActive=''Y''')
;

-- Column: C_Order.DropShip_BPartner_ID
-- Column: C_Order.DropShip_BPartner_ID
-- 2025-04-09T16:21:58.485Z
UPDATE AD_Column SET AD_Reference_Value_ID=541936,Updated=TO_TIMESTAMP('2025-04-09 16:21:58.484000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=55314
;

